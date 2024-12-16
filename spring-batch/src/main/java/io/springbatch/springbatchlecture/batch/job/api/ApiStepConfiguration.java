package io.springbatch.springbatchlecture.batch.job.api;

import io.springbatch.springbatchlecture.batch.chunk.processor.ApiItemProcessor1;
import io.springbatch.springbatchlecture.batch.chunk.processor.ApiItemProcessor2;
import io.springbatch.springbatchlecture.batch.chunk.processor.ApiItemProcessor3;
import io.springbatch.springbatchlecture.batch.chunk.processor.ProcessorClassifier;
import io.springbatch.springbatchlecture.batch.chunk.writer.ApiItemWriter1;
import io.springbatch.springbatchlecture.batch.chunk.writer.ApiItemWriter2;
import io.springbatch.springbatchlecture.batch.chunk.writer.ApiItemWriter3;
import io.springbatch.springbatchlecture.batch.chunk.writer.WriterClassifier;
import io.springbatch.springbatchlecture.batch.domain.ApiRequestVO;
import io.springbatch.springbatchlecture.batch.domain.ProductVO;
import io.springbatch.springbatchlecture.batch.partition.ProductPartitioner;
import io.springbatch.springbatchlecture.service.ApiService1;
import io.springbatch.springbatchlecture.service.ApiService2;
import io.springbatch.springbatchlecture.service.ApiService3;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ApiStepConfiguration {

    private final StepBuilderFactory stepBuilderFactory; // 스텝 생성 팩토리 주입
    private final DataSource dataSource; // 데이터베이스 연결을 위한 DataSource 주입

    private int chunkSize = 10; // 청크 크기 설정

    @Bean
    public Step apiMasterStep() throws Exception {
        // 데이터베이스에서 ProductVO 배열을 조회
        ProductVO[] productList = QueryGenerator.getProductList(dataSource);

        return stepBuilderFactory.get("apiMasterStep") // 스텝 이름 설정
                .partitioner(apiSlaveStep().getName(), partitioner()) // 파티셔너 설정
                .step(apiSlaveStep()) // 슬레이브 스텝 설정
                .gridSize(productList.length) // 파티션 수 설정
                .taskExecutor(taskExecutor()) // 비동기 실행을 위한 TaskExecutor 설정
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3); // 기본 스레드 수 설정
        taskExecutor.setMaxPoolSize(6); // 최대 스레드 수 설정
        taskExecutor.setThreadNamePrefix("api-thread-"); // 스레드 이름 접두사 설정

        return taskExecutor; // 설정된 TaskExecutor 반환
    }

    @Bean
    public Step apiSlaveStep() throws Exception {
        return stepBuilderFactory.get("apiSlaveStep") // 슬레이브 스텝 이름 설정
                .<ProductVO, ProductVO>chunk(chunkSize) // 청크 단위 설정 (읽기, 처리, 쓰기 10개씩)
                .reader(itemReader(null)) // 아이템 리더 설정
                .processor(itemProcessor()) // 아이템 프로세서 설정
                .writer(itemWriter()) // 아이템 라이터 설정
                .build();
    }

    @Bean
    public ProductPartitioner partitioner() {
        ProductPartitioner productPartitioner = new ProductPartitioner();
        productPartitioner.setDataSource(dataSource); // 데이터 소스 설정
        return productPartitioner; // 파티셔너 반환
    }

    @Bean
    @StepScope // 스텝 범위로 설정하여 잡 파라미터 주입 가능
    public ItemReader<ProductVO> itemReader(@Value("#{stepExecutionContext['product']}") ProductVO productVO) throws Exception {
        JdbcPagingItemReader<ProductVO> reader = new JdbcPagingItemReader<>();

        reader.setDataSource(dataSource); // 데이터 소스 설정
        reader.setPageSize(chunkSize); // 페이지 크기 설정
        reader.setRowMapper(new BeanPropertyRowMapper<>(ProductVO.class)); // 행 매퍼 설정

        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("id, name, price, type"); // SELECT 절 설정
        queryProvider.setFromClause("from product"); // FROM 절 설정
        queryProvider.setWhereClause("where type = :type"); // WHERE 절 설정

        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("id", Order.DESCENDING); // 정렬 기준 설정
        queryProvider.setSortKeys(sortKeys); // 정렬 키 설정

        // 쿼리 파라미터 설정
        reader.setParameterValues(QueryGenerator.getParameterForQuery("type", productVO.getType()));
        reader.setQueryProvider(queryProvider); // 쿼리 프로바이더 설정
        reader.afterPropertiesSet(); // 프로퍼티 설정 후 초기화

        return reader; // 설정된 리더 반환
    }

    @Bean
    public ItemProcessor itemProcessor() {
        // 분류기를 사용한 복합 아이템 프로세서 설정
        ClassifierCompositeItemProcessor<ProductVO, ApiRequestVO> processor = new ClassifierCompositeItemProcessor<>();

        ProcessorClassifier<ProductVO, ItemProcessor<?, ? extends ApiRequestVO>> classifier = new ProcessorClassifier();

        Map<String, ItemProcessor<ProductVO, ApiRequestVO>> processorMap = new HashMap<>();
        processorMap.put("1", new ApiItemProcessor1()); // 타입 "1"에 대한 프로세서 매핑
        processorMap.put("2", new ApiItemProcessor2()); // 타입 "2"에 대한 프로세서 매핑
        processorMap.put("3", new ApiItemProcessor3()); // 타입 "3"에 대한 프로세서 매핑

        classifier.setProcessorMap(processorMap); // 분류기에 프로세서 맵 설정

        processor.setClassifier(classifier); // 프로세서에 분류기 설정

        return processor; // 설정된 프로세서 반환
    }

    @Bean
    public ItemWriter itemWriter() {
        // 분류기를 사용한 복합 아이템 라이터 설정
        ClassifierCompositeItemWriter<ApiRequestVO> writer = new ClassifierCompositeItemWriter<>();

        WriterClassifier<ApiRequestVO, ItemWriter<? super ApiRequestVO>> classifier = new WriterClassifier();

        Map<String, ItemWriter<ApiRequestVO>> writerMap = new HashMap<>();
        writerMap.put("1", new ApiItemWriter1(new ApiService1())); // 타입 "1"에 대한 라이터 매핑
        writerMap.put("2", new ApiItemWriter2(new ApiService2())); // 타입 "2"에 대한 라이터 매핑
        writerMap.put("3", new ApiItemWriter3(new ApiService3())); // 타입 "3"에 대한 라이터 매핑

        classifier.setWriterMap(writerMap); // 분류기에 라이터 맵 설정

        writer.setClassifier(classifier); // 라이터에 분류기 설정

        return writer; // 설정된 라이터 반환
    }

}

package io.springbatch.springbatchlecture.batch.job.file;

import io.springbatch.springbatchlecture.batch.chunk.processor.*;
import io.springbatch.springbatchlecture.batch.domain.Product;
import io.springbatch.springbatchlecture.batch.domain.ProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
public class FileJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory; // 잡 생성 팩토리 주입
    private final StepBuilderFactory stepBuilderFactory; // 스텝 생성 팩토리 주입
    private final EntityManagerFactory entityManagerFactory; // JPA 엔티티 매니저 팩토리 주입

    @Bean
    public Job fileJob() {
        return jobBuilderFactory.get("fileJob") // 잡 이름 설정
                .start(fileStep1()) // 첫 번째 스텝 설정
                .build();
    }

    @Bean
    public Step fileStep1() {
        return stepBuilderFactory.get("fileStep1") // 스텝 이름 설정
                .<ProductVO, Product>chunk(10) // 청크 단위 설정 (읽기, 처리, 쓰기 10개씩)
                .reader(fileItemReader(null)) // 아이템 리더 설정
                .processor(fileItemProcessor()) // 아이템 프로세서 설정
                .writer(fileItemWriter()) // 아이템 라이터 설정
                .build();
    }

    @Bean
    @StepScope // 스텝 범위로 설정하여 잡 파라미터 주입 가능
    public FlatFileItemReader<ProductVO> fileItemReader(@Value("#{jobParameters['requestDate']}") String requestDate) {
        return new FlatFileItemReaderBuilder<ProductVO>()
                .name("flatFile") // 리더 이름 설정
                .resource(new ClassPathResource("product_" + requestDate +".csv")) // 읽을 파일 경로 설정 (잡 파라미터 기반)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>()) // 필드 매핑 설정
                .targetType(ProductVO.class) // 대상 타입 설정
                .linesToSkip(1) // 헤더 라인 스킵 설정
                .delimited().delimiter(",") // 구분자 설정 (쉼표)
                .names("id","name","price","type") // 필드 이름 설정
                .build();
    }

    @Bean
    public ItemProcessor<ProductVO, Product> fileItemProcessor() {
        return new FileItemProcessor(); // 커스텀 프로세서 인스턴스 반환
    }

    @Bean
    public JpaItemWriter<Product> fileItemWriter() {
        return new JpaItemWriterBuilder<Product>()
                .entityManagerFactory(entityManagerFactory) // 엔티티 매니저 팩토리 설정
                .usePersist(true) // JPA의 persist 메서드 사용 설정
                .build();
    }
    
}

package io.springbatch.springbatchlecture.batch.chunk.writer;

import io.springbatch.springbatchlecture.batch.domain.ApiRequestVO;
import io.springbatch.springbatchlecture.batch.domain.ApiResponseVO;
import io.springbatch.springbatchlecture.service.AbstractApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ApiItemWriter1 extends FlatFileItemWriter<ApiRequestVO> {

    private final AbstractApiService apiService; // 외부 API 호출을 위한 서비스 의존성

    public ApiItemWriter1(AbstractApiService apiService) {
        this.apiService = apiService; // 의존성 주입을 통해 서비스 인스턴스 할당
    }

    @Override
    public void write(List<? extends ApiRequestVO> items) throws Exception {

    	// 디버깅을 위한 출력
        System.out.println("----------------------------------");
        items.forEach(item -> System.out.println("items = " + item));
        System.out.println("----------------------------------");

        // API 서비스를 호출하여 응답 받기
        ApiResponseVO response = apiService.service(items);
        System.out.println("response = " + response);

        // 응답을 각 아이템에 설정
        items.forEach(item -> item.setApiResponseVO(response));

        // 파일 쓰기 설정
        super.setResource(new FileSystemResource("C:\\jsw\\inflearn\\spring-batch-lecture\\src\\main\\resources\\product1.txt")); // 출력 파일 경로 설정
        super.open(new ExecutionContext()); // 실행 컨텍스트 열기
        super.setLineAggregator(new DelimitedLineAggregator<>()); // 라인 집계기 설정 (기본 구분자 사용)
        super.setAppendAllowed(true); // 파일에 추가 모드 설정
        super.write(items); // 실제 파일에 아이템 쓰기
    }
}

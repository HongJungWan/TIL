package io.springbatch.springbatchlecture.service;

import io.springbatch.springbatchlecture.batch.domain.ApiInfo;
import io.springbatch.springbatchlecture.batch.domain.ApiRequestVO;
import io.springbatch.springbatchlecture.batch.domain.ApiResponseVO;
import io.springbatch.springbatchlecture.batch.domain.ProductVO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public abstract class AbstractApiService {

    /**
     * API 서비스를 호출하는 템플릿 메서드.
     * 공통 로직을 처리하고, 구체적인 API 호출은 서브클래스에서 구현.
     *
     * @param apiRequest API 요청 데이터 리스트
     * @return API 응답 데이터
     */
    public ApiResponseVO service(List<? extends ApiRequestVO> apiRequest) {

    	// RestTemplateBuilder를 사용하여 RestTemplate 인스턴스 생성 및 설정
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder.errorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException { // 커스텀 에러 핸들러 설정
                return false; // 에러가 있어도 예외를 던지지 않음
            }

            @Override
            public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
            	// 에러 처리 로직 (현재는 비워둠)
            }
        }).build();

        // HTTP 요청 팩토리 설정 (예: 연결 타임아웃 등)
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        
        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // JSON 형식으로 데이터 전송

        // ApiInfo 객체 생성 (빌더 패턴 사용)
        ApiInfo apiInfo = ApiInfo.builder()
                .apiRequestList(apiRequest)
                .build();
        
        // HTTP 요청 엔티티 생성 (헤더와 바디 포함)
        HttpEntity<ApiInfo> reqEntity = new HttpEntity<>(apiInfo, headers);

        // 구체적인 API 호출은 서브클래스에서 구현
        return doApiService(restTemplate, apiInfo);

    }

    /**
     * 구체적인 API 호출을 수행하는 추상 메서드.
     * 서브클래스에서 구현해야 함.
     *
     * @param restTemplate RestTemplate 인스턴스
     * @param apiInfo      API 요청 정보
     * @return API 응답 데이터
     */
    protected abstract ApiResponseVO doApiService(RestTemplate restTemplate, ApiInfo apiInfo);
}

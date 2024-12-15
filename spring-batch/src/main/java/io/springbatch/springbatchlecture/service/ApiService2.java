package io.springbatch.springbatchlecture.service;

import io.springbatch.springbatchlecture.batch.domain.ApiInfo;
import io.springbatch.springbatchlecture.batch.domain.ApiResponseVO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService2 extends AbstractApiService{

    /**
     * ApiService2의 구체적인 API 호출 구현.
     * 특정 URL로 POST 요청을 보내고 응답을 ApiResponseVO로 변환.
     *
     * @param restTemplate RestTemplate 인스턴스
     * @param apiInfo      API 요청 정보
     * @return API 응답 데이터
     */
    @Override
    public ApiResponseVO doApiService(RestTemplate restTemplate, ApiInfo apiInfo){

        // 지정된 URL로 POST 요청 보내기
        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:8081/api/product/2", // API 엔드포인트 URL
                apiInfo, // 요청 바디
                String.class // 응답 타입
        );
        
        // 응답 상태 코드와 응답 본문을 사용하여 ApiResponseVO 생성
        int statusCodeValue = response.getStatusCodeValue();
        ApiResponseVO apiResponseVO = new ApiResponseVO(statusCodeValue + "", response.getBody());

        return apiResponseVO;
    }
}

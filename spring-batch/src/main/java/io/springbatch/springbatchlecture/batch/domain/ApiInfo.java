package io.springbatch.springbatchlecture.batch.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApiInfo {

    private String url;
    
    // List<? extends ApiRequestVO>는 제네릭 와일드카드(?)를 사용한 선언으로, ApiRequestVO를 상속하거나 구현한 어떤 하위 타입의 리스트
    // 즉, 해당 리스트는 ApiRequestVO 또는 이를 상속한 클래스의 인스턴스들을 원소로 가질 수 있다.
    private List<? extends ApiRequestVO> apiRequestList;
}

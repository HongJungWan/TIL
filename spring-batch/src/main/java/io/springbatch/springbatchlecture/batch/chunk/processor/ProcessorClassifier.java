package io.springbatch.springbatchlecture.batch.chunk.processor;

import io.springbatch.springbatchlecture.batch.domain.ApiRequestVO;
import io.springbatch.springbatchlecture.batch.domain.ProductVO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;

import java.util.HashMap;
import java.util.Map;

// Classifier<C, T>: 입력 C를 받아 분류된 출력 T를 반환하는 인터페이스
public class ProcessorClassifier<C,T> implements Classifier<C, T> {

	// 타입별로 다른 ItemProcessor를 매핑하기 위한 맵
    private Map<String, ItemProcessor<ProductVO, ApiRequestVO>> processorMap = new HashMap<>();

    @Override
    public T classify(C classifiable) {
    	// 다형성: ProductVO의 타입에 따라 적절한 ItemProcessor를 반환
        return (T)processorMap.get(((ProductVO)classifiable).getType());
    }

    // 외부에서 프로세서 맵을 설정할 수 있는 메서드
    public void setProcessorMap(Map<String, ItemProcessor<ProductVO, ApiRequestVO>> processorMap) {
        this.processorMap = processorMap;
    }
}
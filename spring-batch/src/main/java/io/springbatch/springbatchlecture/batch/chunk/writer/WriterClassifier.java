package io.springbatch.springbatchlecture.batch.chunk.writer;

import io.springbatch.springbatchlecture.batch.domain.ApiRequestVO;
import io.springbatch.springbatchlecture.batch.domain.ProductVO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.classify.Classifier;

import java.util.HashMap;
import java.util.Map;

// Classifier<C, T>: 입력 C를 받아 분류된 출력 T를 반환하는 Spring 프레임워크의 인터페이스.
public class WriterClassifier<C,T> implements Classifier<C, T> {

	// 각 타입에 맞는 ItemWriter를 매핑하기 위한 맵
    private Map<String, ItemWriter<ApiRequestVO>> writerMap = new HashMap<>();

    @Override
    public T classify(C classifiable) {
    	// classifiable 객체를 ApiRequestVO로 캐스팅하고, 타입에 따라 적절한 ItemWriter 반환
        return (T)writerMap.get(((ApiRequestVO)classifiable).getProductVO().getType());
    }

    // 외부에서 writer 맵을 설정할 수 있는 메서드
    public void setWriterMap(Map<String, ItemWriter<ApiRequestVO>> writerMap) {
        this.writerMap = writerMap;
    }
}
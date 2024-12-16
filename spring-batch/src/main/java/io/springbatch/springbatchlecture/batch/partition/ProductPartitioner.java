package io.springbatch.springbatchlecture.batch.partition;

import io.springbatch.springbatchlecture.batch.domain.ProductVO;
import io.springbatch.springbatchlecture.batch.job.api.QueryGenerator;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class ProductPartitioner implements Partitioner {

    private DataSource dataSource; // 데이터베이스 연결을 위한 DataSource 의존성

    /**
     * DataSource를 설정하는 Setter 메서드
     *
     * @param dataSource 데이터베이스 연결 정보
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 파티셔닝을 수행하는 메서드
     *
     * @param gridSize 분할할 파티션의 수
     * @return 파티션 이름과 해당 파티션에 대한 ExecutionContext 맵
     */
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {

        // 데이터베이스에서 ProductVO 배열을 조회
        ProductVO[] productList = QueryGenerator.getProductList(dataSource);
        Map<String, ExecutionContext> result = new HashMap<>();
        int number = 0;

        // 각 ProductVO를 개별 파티션으로 분할
        for (int i = 0; i < productList.length; i++) {

            ExecutionContext value = new ExecutionContext(); // 각 파티션에 대한 ExecutionContext 생성

            result.put("partition" + number, value); // 파티션 이름과 ExecutionContext를 맵에 추가
            value.put("product", productList[i]); // 해당 파티션에 처리할 ProductVO 객체를 ExecutionContext에 저장

            number++;
        }

        return result; // 모든 파티션 정보 반환
    }
    
}

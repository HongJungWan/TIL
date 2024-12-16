package io.springbatch.springbatchlecture.batch.job.api;

import io.springbatch.springbatchlecture.batch.domain.ProductVO;
import io.springbatch.springbatchlecture.batch.rowmapper.ProductRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryGenerator {

    /**
     * 데이터베이스에서 제품 유형별로 ProductVO 배열을 조회
     *
     * @param dataSource 데이터베이스 연결 정보
     * @return ProductVO 배열
     */
    public static ProductVO[] getProductList(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource); // JdbcTemplate 초기화
        List<ProductVO> productList = jdbcTemplate.query(
                "select type as type from product group by type", // 쿼리 실행
                new ProductRowMapper() { // 커스텀 RowMapper 사용
                    @Override
                    public ProductVO mapRow(ResultSet rs, int i) throws SQLException {
                        return ProductVO.builder()
                                .type(rs.getString("type")) // ResultSet에서 'type' 컬럼 값 매핑
                                .build();
                    }
                }
        );

        return productList.toArray(new ProductVO[]{}); // 리스트를 배열로 변환하여 반환
    }

    /**
     * 쿼리 파라미터를 위한 맵 생성
     *
     * @param parameter 파라미터 이름
     * @param value     파라미터 값
     * @return 파라미터 맵
     */
    public static Map<String, Object> getParameterForQuery(String parameter, String value) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put(parameter, value); // 파라미터 추가
        return parameters; // 파라미터 맵 반환
    }

}

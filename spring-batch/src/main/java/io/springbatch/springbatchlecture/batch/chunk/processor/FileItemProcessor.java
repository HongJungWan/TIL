package io.springbatch.springbatchlecture.batch.chunk.processor;

import io.springbatch.springbatchlecture.batch.domain.Product;
import io.springbatch.springbatchlecture.batch.domain.ProductVO;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;

public class FileItemProcessor implements ItemProcessor<ProductVO, Product> {

    @Override
    public Product process(ProductVO item) throws Exception {
    	// ModelMapper를 사용하여 ProductVO를 Product로 매핑
        ModelMapper modelMapper = new ModelMapper();
        Product product = modelMapper.map(item, Product.class);

        return product;
    }
}

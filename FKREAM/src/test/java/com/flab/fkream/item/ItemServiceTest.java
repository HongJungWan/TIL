package com.flab.fkream.item;

import com.flab.fkream.brand.Brand;
import com.flab.fkream.brand.BrandService;
import com.flab.fkream.AutoComplete.Trie;
import com.flab.fkream.mapper.ItemMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    ItemMapper itemMapper;
    @Mock
    BrandService brandService;

    @Mock
    Trie trie;
    @InjectMocks
    ItemService itemService;

    Brand brand = Brand.builder().brandName("구찌").luxury(true).build();

    Item itemInfo =
        Item.builder()
            .itemName("나이키 에어포스")
            .modelNumber("NK22035")
            .categoryId(1L)
            .detailedCategoryId(2L)
            .releaseDate(LocalDate.now())
            .representativeColor("Black")
            .releasedPrice(10000)
            .brand(brand)
            .build();

    @Test
    void 아이템_생성() {
        given(itemMapper.save(itemInfo)).willReturn(1);
//        doNothing().when(trie).insert(itemInfo.getItemName());
        itemService.addItem(itemInfo);
        then(itemMapper).should().save(itemInfo);
    }

    @Test
    void 아이템_조회() {
        given(itemMapper.findOne(1L)).willReturn(itemInfo);
        given(brandService.findOne(brand.getId())).willReturn(brand);
        itemService.findOne(1L);
        then(itemMapper).should().findOne(1L);
    }

    @Test
    void 아이템_리스팅() {
        given(itemMapper.findAll()).willReturn(List.of(itemInfo));
        given(brandService.findOne(brand.getId())).willReturn(brand);
        itemService.findAll();
        then(itemMapper).should().findAll();
    }

    @Test
    void 아이템_업데이트() {
        given(itemMapper.update(itemInfo)).willReturn(1);
        itemService.update(itemInfo);
        then(itemMapper).should().update(itemInfo);
    }

    @Test
    void 아이템_삭제() {
        given(itemMapper.delete(1L)).willReturn(1);
        itemService.delete(1L);
        then(itemMapper).should().delete(1L);
    }
}

package com.flab.fkream.Integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.fkream.deal.Deal;
import com.flab.fkream.deal.DealType;
import com.flab.fkream.deal.DealStatus;
import com.flab.fkream.item.Item;
import com.flab.fkream.itemSizePrice.ItemSizePrice;
import com.flab.fkream.itemSizePrice.ItemSizePriceService;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class DealTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ItemSizePriceService itemSizePriceService;

    @Test
    void buySellTest() throws Exception {

        ItemSizePrice byItemIdAndSize = itemSizePriceService.findByItemIdAndSize(23L, "260");
        int highestPurchasePrice = byItemIdAndSize.getImmediateSalePrice();

        System.out.println(
            "HighestPurchasePrice : " + itemSizePriceService.findByItemIdAndSize(23L, "260")
                .getImmediateSalePrice());
        System.out.println(
            "LowestSellingPrice : " + itemSizePriceService.findByItemIdAndSize(23L, "260")
                .getImmediatePurchasePrice());

        Deal deal = Deal.builder().item(Item.builder().id(53L).build())
            .userId(1L).price(1_000_000).size("260").utilizationPolicy(true).dealType(DealType.SALE)
            .dealStatus(DealStatus.BIDDING)
            .salesCondition(true).period(LocalDate.now().plusMonths(1)).build();

        Deal deal2 = Deal.builder().item(Item.builder().id(53L).build())
            .userId(1L).price(10_000).size("260").utilizationPolicy(true)
            .dealType(DealType.PURCHASE).dealStatus(DealStatus.BIDDING)
            .salesCondition(true).period(LocalDate.now().plusMonths(1)).build();

        Deal deal3 = Deal.builder().item(Item.builder().id(53L).build())
            .userId(1L).price(1_000_000).size("260").utilizationPolicy(true)
            .dealType(DealType.PURCHASE).dealStatus(DealStatus.PROGRESS)
            .salesCondition(true).period(LocalDate.now().plusMonths(1)).build();

        Deal deal4 = Deal.builder().item(Item.builder().id(53L).build())
            .userId(1L).price(10_000).size("260").utilizationPolicy(true).dealType(DealType.SALE)
            .dealStatus(DealStatus.PROGRESS)
            .salesCondition(true).period(LocalDate.now().plusMonths(1)).build();

//        mockMvc.perform(post("/deals/sales")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(deal)))
//            .andExpect(status().isOk());

        System.out.println(
            "HighestPurchasePrice : " + itemSizePriceService.findByItemIdAndSize(23L, "260")
                .getImmediateSalePrice());
        System.out.println(
            "LowestSellingPrice : " + itemSizePriceService.findByItemIdAndSize(23L, "260")
                .getImmediatePurchasePrice());

//        mockMvc.perform(post("/deals/purchases")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(deal2)))
//            .andExpect(status().isOk());

        System.out.println(
            "HighestPurchasePrice : " + itemSizePriceService.findByItemIdAndSize(23L, "260")
                .getImmediateSalePrice());
        System.out.println(
            "LowestSellingPrice : " + itemSizePriceService.findByItemIdAndSize(23L, "260")
                .getImmediatePurchasePrice());

        mockMvc.perform(post("/deals/sales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(deal4)))
            .andExpect(status().isOk());
        // 즉시 판매를 하고 싶어
        // 여기서 현재, 즉시 판매가는 구매가중 가장 높은 것, 현재는 만원이야
        // 5만원을 하니 통과가 됨

        System.out.println(
            "HighestPurchasePrice : " + itemSizePriceService.findByItemIdAndSize(23L, "260")
                .getImmediateSalePrice());
        System.out.println(
            "LowestSellingPrice : " + itemSizePriceService.findByItemIdAndSize(23L, "260")
                .getImmediatePurchasePrice());

//        mockMvc.perform(post("/deals/purchases")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(deal3)))
//            .andExpect(status().isOk());

        //----------------------------------------------------------------------------------------------

        System.out.println(
            "HighestPurchasePrice : " + itemSizePriceService.findByItemIdAndSize(23L, "260")
                .getImmediateSalePrice());
        System.out.println(
            "LowestSellingPrice : " + itemSizePriceService.findByItemIdAndSize(23L, "260")
                .getImmediatePurchasePrice());

    }
}

package com.flab.fkream.mapper;

import com.flab.fkream.deal.BiddingPriceDto;
import com.flab.fkream.deal.Deal;
import com.flab.fkream.deal.DealHistoryCountDto;
import com.flab.fkream.deal.DealHistoryDto;
import com.flab.fkream.deal.DealStatus;
import com.flab.fkream.deal.DealType;
import com.flab.fkream.deal.MarketPriceDto;
import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface DealMapper {

    int save(Deal deal);

    @Transactional(readOnly = true)
    List<Deal> findByUserId(Long userId);

    @Transactional(readOnly = true)
    Deal findById(Long id);

    int update(Deal deal);

    int delete(Long id);

    @Transactional(readOnly = true)
    Deal findBuyNowDealByItemIdAndSizeAndPrice(Long id, String size, int price);

    @Transactional(readOnly = true)
    Deal findSellNowDealByItemIdAndSizeAndPrice(Long id, String size, int price);

    @Transactional(readOnly = true)
    Integer findHighestPurchasePriceByItemIdAndSize(Long itemId, String size);

    @Transactional(readOnly = true)
    Integer findLowestSalePriceByItemIdAndSize(Long itemId, String size);

    @Transactional(readOnly = true)
    List<MarketPriceDto> findMarketPrices(Long itemId, String size);

    @Transactional(readOnly = true)
    List<MarketPriceDto> findMarketPricesInGraph(Long itemId, LocalDate period, String size);

    @Transactional(readOnly = true)
    List<BiddingPriceDto> findBiddingPrices(Long itemId, String size, DealType dealType);

    @Transactional(readOnly = true)
    List<DealHistoryCountDto> findHistoryCount(Long userId, DealType dealType);

    @Transactional(readOnly = true)
    List<DealHistoryDto> findPurchaseHistories(Long userId, DealStatus dealStatus);

    @Transactional(readOnly = true)
    List<DealHistoryDto> findSaleHistories(Long userId, DealStatus dealStatus);
}

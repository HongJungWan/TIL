package com.flab.fkream.itemSizePrice;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemSizePrice {
	private Long id;
    @NotNull
	private Long itemId;
	private String size;
	@Setter
	private Integer immediatePurchasePrice;
	@Setter
	private Integer immediateSalePrice;
	private LocalDateTime modifiedAt;

	public void changePrice(Integer immediateSalePrice, Integer immediatePurchasePrice) {
		this.immediateSalePrice = immediateSalePrice;
		this.immediatePurchasePrice = immediatePurchasePrice;
	}
}

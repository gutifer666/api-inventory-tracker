package com.javiergutierrez.inventory_tracker_api.modules.transactions.infrastructure.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString()
@EqualsAndHashCode()
public class TransactionDTO {

	private Long userId;
	private String clientName;
	private Long productId;
	private Integer quantity;

}

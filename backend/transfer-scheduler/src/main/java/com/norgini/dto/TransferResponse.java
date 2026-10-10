package com.norgini.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class TransferResponse {

	private Long id;
	private String sourceAccount;
	private String destinationAccount;
	private BigDecimal amount;
	private BigDecimal fee;
	private LocalDate transferDate;
	private LocalDate scheduledDate;

}

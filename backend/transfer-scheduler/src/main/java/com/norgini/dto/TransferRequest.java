package com.norgini.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class TransferRequest {

	@NotBlank(message = "A conta de origem é obrigatória.")
	@Pattern(regexp = "\\d{10}", message = "A conta de origem deve possuir exatamente 10 dígitos numéricos.")
	private String sourceAccount;

	@NotBlank(message = "A conta de destino é obrigatória.")
	@Pattern(regexp = "\\d{10}", message = "A conta de destino deve possuir exatamente 10 dígitos numéricos.")
	private String destinationAccount;

	@NotNull(message = "O valor da transferência é obrigatório.")
	@Positive(message = "O valor da transferência deve ser maior que zero.")
	private BigDecimal amount;

	@NotNull(message = "A data da transferência é obrigatória.")
	@FutureOrPresent(message = "A data da transferência não pode ser retroativa.")
	private LocalDate transferDate;

}

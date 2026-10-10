package com.norgini.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "tb_transfer")
public class Transfer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String sourceAccount;
	private String destinationAccount;
	private BigDecimal amount;
	private BigDecimal fee;
	private LocalDate transferDate;
	private LocalDate scheduledDate;

}

package com.norgini.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "tb_transfer")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Transfer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false, length = 10)
	private String sourceAccount;

	@Column(nullable = false, length = 10)
	private String destinationAccount;

	@Column(nullable = false)
	private BigDecimal amount;

	@Column(nullable = false)
	private BigDecimal fee;

	@Column(nullable = false)
	private LocalDate transferDate;

	@Column(nullable = false, updatable = false)
	private LocalDate scheduledDate;

}

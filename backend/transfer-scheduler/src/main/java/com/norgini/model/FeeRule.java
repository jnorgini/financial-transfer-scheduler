package com.norgini.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Optional;

public enum FeeRule {
	RANGE_0(0, 0, new BigDecimal("3.00"), new BigDecimal("2.5")),
	RANGE_1_10(1, 10, new BigDecimal("12.00"), BigDecimal.ZERO),
	RANGE_11_20(11, 20, BigDecimal.ZERO, new BigDecimal("8.2")),
	RANGE_21_30(21, 30, BigDecimal.ZERO, new BigDecimal("6.9")),
	RANGE_31_40(31, 40, BigDecimal.ZERO, new BigDecimal("4.7")),
	RANGE_41_50(41, 50, BigDecimal.ZERO, new BigDecimal("1.7"));

	private final long minDays;
	private final long maxDays;
	private final BigDecimal fixedFee;
	private final BigDecimal percentage;

	FeeRule(long minDays, long maxDays, BigDecimal fixedFee, BigDecimal percentage) {
		this.minDays = minDays;
		this.maxDays = maxDays;
		this.fixedFee = fixedFee;
		this.percentage = percentage;
	}

	public BigDecimal getFixedFee() {
		return fixedFee;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public static Optional<FeeRule> findRule(long days) {
		return Arrays.stream(values())
				.filter(rule -> days >= rule.minDays && days <= rule.maxDays)
				.findFirst();
	}
	
	public BigDecimal calculateFee(BigDecimal amount) {
		BigDecimal percentageFee = amount.multiply(this.percentage)
				.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);

		return this.fixedFee.add(percentageFee).setScale(2, RoundingMode.HALF_UP);
	}
	
}

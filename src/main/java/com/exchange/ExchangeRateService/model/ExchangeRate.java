package com.exchange.ExchangeRateService.model;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Data Object for Exchange Rate Information
 * 
 * @author florianbraun
 *
 */
public class ExchangeRate {

	private Long id;
	private String currency_code_from;
	private String currency_code_to;
	private BigDecimal conversion_rate;
	private AtomicLong accessCounter = new AtomicLong(0);

	public AtomicLong getAccessCounter() {
		return accessCounter;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurrency_code_from() {
		return currency_code_from;
	}

	public void setCurrency_code_from(String currency_code_from) {
		this.currency_code_from = currency_code_from;
	}

	public String getCurrency_code_to() {
		return currency_code_to;
	}

	public void setCurrency_code_to(String currency_code_to) {
		this.currency_code_to = currency_code_to;
	}

	public BigDecimal getConversion_rate() {
		accessCounter.incrementAndGet();
		return conversion_rate;
	}

	public void setConversion_rate(BigDecimal conversion_rate) {
		this.conversion_rate = conversion_rate;
	}

}

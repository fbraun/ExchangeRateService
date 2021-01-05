package com.exchange.ExchangeRateService.model;

/**
 * @author florianbraun
 * 
 * Data Object for Currency Information
 *
 */
public class Currency {

	private String currencyCode;
	private int accessCounter = 0;

	public String getCurrencyCode() {
		return currencyCode;
	}
	
	public void count() {
		accessCounter++;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public int getAccessCounter() {
		return accessCounter;
	}

}

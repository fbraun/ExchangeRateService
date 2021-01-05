package com.exchange.ExchangeRateService.service;

import java.math.BigDecimal;
import java.util.List;

import com.exchange.ExchangeRateService.model.Currency;

/**
 * Interface to retrieve supported currency and exchange rate information
 * 
 * @author florianbraun
 *
 */
public interface IExchangeRateService {
	
	/**
	 * returns the conversion rate for a currency pair
	 * 
	 * @param from - source currency code
	 * @param to - target currency code
	 * @return - Conversion rate
	 */
	BigDecimal getRate(String from, String to);
	
	
	/**
	 * returns a list of all supported currencies
	 * 
	 * @return List of supported currencies
	 */
	List<Currency> getSupportedCurrencies();
}



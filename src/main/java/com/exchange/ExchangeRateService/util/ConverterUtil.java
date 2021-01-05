package com.exchange.ExchangeRateService.util;

import java.math.BigDecimal;

/**
 * Converter Utility to calculate target amount
 */
public class ConverterUtil {
	
	
	/**
	 * Method to calculate amount in target currency based on input amount and exchange rate
	 * 
	 * @param amount - the amount in the from currency
	 * @param exchangeRate - conversion rate
	 * @return - converted amount in target currency
	 */
	public static final BigDecimal convert(BigDecimal amount, BigDecimal exchangeRate) { 
		
		return amount.multiply(exchangeRate);
	}
}

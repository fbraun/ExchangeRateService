package com.exchange.ExchangeRateService.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.exchange.ExchangeRateService.model.Currency;
import com.exchange.ExchangeRateService.model.ExchangeRate;

@Service
public class ExchangeRateService implements IExchangeRateService {

	private static final String SELECT_SUPPORTED_CURRENCIES = "SELECT * FROM SUPPORTED_CURRENCIES";

	private static final String SELECT_STATEMENT_END = "'";

	private static final String SELECT_STATEMENT = "SELECT * FROM TBL_RATES WHERE currency_code_from = 'EUR' and currency_code_to= '";

	public static final String EUR = "EUR";

	private List<Currency> supportedCurrencies = null;
	
	private static final Logger log = 
	        LoggerFactory.getLogger(ExchangeRateService.class);

	@Autowired
	private JdbcTemplate jtm;

	
	/**
	 * returns the conversion rate for a currency pair
	 * 
	 * @param from - source currency code
	 * @param to - target currency code
	 * @return - Conversion rate
	 */
	@Override
	public BigDecimal getRate(final String currency_code_from, final String currency_code_to) {

		countCurrencyRequest(currency_code_from);
		countCurrencyRequest(currency_code_to);

		String sql = "";

		BigDecimal rate = null;
		if (EUR.equals(currency_code_from) && !EUR.equals(currency_code_to)) {

			// get EUR -> Non-Euro rate
			sql = SELECT_STATEMENT + currency_code_to + SELECT_STATEMENT_END;
			ExchangeRate exchangeRate = executeExchangeRateQuery(sql);

			rate = exchangeRate.getConversion_rate();

		} else if (!EUR.equals(currency_code_from) && EUR.equals(currency_code_to)) {

			// get EUR -> Non-Euro rate
			// calculate reverse rate

			sql = SELECT_STATEMENT + currency_code_from + SELECT_STATEMENT_END;
			ExchangeRate exchangeRate = executeExchangeRateQuery(sql);

			BigDecimal tempRate = exchangeRate.getConversion_rate();
			rate = new BigDecimal(1).divide(tempRate, 4, RoundingMode.HALF_EVEN);

		} else if (currency_code_from != "EUR" && currency_code_to != "EUR") {
			// get EUR -> Non-Euro rates for both currencies
			// calculate conversion rate

			sql = SELECT_STATEMENT + currency_code_from + SELECT_STATEMENT_END;
			ExchangeRate eur_to_from_rate = executeExchangeRateQuery(sql);

			sql = SELECT_STATEMENT + currency_code_to + SELECT_STATEMENT_END;
			ExchangeRate eur_to_target_rate = executeExchangeRateQuery(sql);

			rate = new BigDecimal(1).divide(eur_to_from_rate.getConversion_rate(), 4, RoundingMode.HALF_EVEN)
					.multiply(eur_to_target_rate.getConversion_rate());
		}

		return rate;
	}
	

	/**
	 * returns a list of all supported currencies
	 * 
	 * @return List of supported currencies
	 */
	@Override
	public List<Currency> getSupportedCurrencies() {

		if (supportedCurrencies == null) {

			String sql = SELECT_SUPPORTED_CURRENCIES;

			supportedCurrencies = Collections
					.synchronizedList(jtm.query(sql, new BeanPropertyRowMapper<>(Currency.class)));
		}
		return supportedCurrencies;
	}

	/**
	 * executes the query to get an exchange rate
	 * @param sql - SQL statement
	 * @return - ExchangeRate object
	 */
	private ExchangeRate executeExchangeRateQuery(String sql) {
		return jtm.queryForObject(sql, new BeanPropertyRowMapper<>(ExchangeRate.class));
	}

	/**
	 * keeps track of how often each currency code was requested
	 * 
	 * @param currency_code - code of the currency code to track
	 */
	private void countCurrencyRequest(String currency_code) {
		Currency result2 = getSupportedCurrencies().stream()
				.filter(item -> item.getCurrencyCode().equals(currency_code)).findFirst().orElse(null);
		if(result2 != null) {
			result2.count();
		} else {
			log.error("Currency code not found in suppported currency list.");
		}
	}
}

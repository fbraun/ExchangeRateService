package com.exchange.ExchangeRateService.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exchange.ExchangeRateService.model.Currency;
import com.exchange.ExchangeRateService.service.IExchangeRateService;
import com.exchange.ExchangeRateService.util.ConverterUtil;

@RestController
public class ExchangeRateController {

	@Autowired
	private IExchangeRateService exchangeRateService;


	/**
	 * Return all supported currencies
	 * 
	 * @return List of supported currency objects
	 */
	@RequestMapping(path = "/exchangerate/supportedCurrencies", method = RequestMethod.GET)
	public List<Currency> getSupportedCurrencies() {

		return exchangeRateService.getSupportedCurrencies();
	}

	/**
	 * retrieve the exchange rate for a specific currency pair
	 * 
	 * @param from - source currency code
	 * @param to - target currency code
	 * @return - exchange rate
	 */
	@RequestMapping(path = "/exchangerate/from/{from}/to/{to}", method = RequestMethod.GET)
	public BigDecimal getRate(@PathVariable String from, @PathVariable String to) {

		return exchangeRateService.getRate(from, to);

	}

	/**
	 * Return a web link with information about the specified currency pair.
	 * @param from - source currency code
	 * @param to - target currency code
	 * @return - URL to web site.
	 */
	@RequestMapping(path = "/exchangerate/weblink/from/{from}/to/{to}", method = RequestMethod.GET)
	public String getWebLink(@PathVariable String from, @PathVariable String to) {

		// TODO: URL type?

		return "https://www.xe.com/currencyconverter/convert/?From=" + from + "&To=" + to;
	}

	/**
	 * conversion of a specific amount from source currency to target currency
	 * 
	 * @param from - source currency code
	 * @param to - target currency code
	 * @param amount - amount to be converted
	 * 
	 * @return - converted amount
	 */
	@RequestMapping(path = "/exchangerate/convert/from/{from}/to/{to}/amount/{amount}", method = RequestMethod.POST)
	public BigDecimal convert(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal amount) {

		return ConverterUtil.convert(amount, exchangeRateService.getRate(from, to));
	}

	/**
	 * Exception Handler for error scenarios where no currency information was found
	 * @param e
	 * @return Not found http status code along with error information.
	 */
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<String> noExchangeRatesFound(EmptyResultDataAccessException e) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Rates found");
	}
}
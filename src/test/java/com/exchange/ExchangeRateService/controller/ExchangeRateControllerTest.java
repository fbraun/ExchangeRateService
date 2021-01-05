package com.exchange.ExchangeRateService.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ExchangeRateControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void supportedCurrenciesShouldIncludeEUR() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/exchangerate/supportedCurrencies",
				String.class)).contains("EUR");
	}

	@Test
	public void webLinkEURtoUSD() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/exchangerate/weblink/from/EUR/to/USD",
				String.class)).isEqualTo("https://www.xe.com/currencyconverter/convert/?From=EUR&To=USD");
	}

	@Test
	public void exchangerateEURtoUSDNotEmpty() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/exchangerate/from/EUR/to/USD",
				String.class)).isNotEmpty();

	}
}
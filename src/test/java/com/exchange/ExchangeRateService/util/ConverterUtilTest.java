package com.exchange.ExchangeRateService.util;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConverterUtilTest {
	
	@Test
	void converter() {
		assertTrue(ConverterUtil.convert(new BigDecimal(10), new BigDecimal(2)).equals(new BigDecimal(20)));
	}
}


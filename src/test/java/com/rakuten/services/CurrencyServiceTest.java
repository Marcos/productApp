package com.rakuten.services;

import static com.rakuten.services.Tests.toList;
import static java.math.BigDecimal.valueOf;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.rakuten.dtos.CotationDTO;
import com.rakuten.repositories.CurrencyRepository;
import com.rakuten.services.exceptions.CurrencyNotFoundException;

public class CurrencyServiceTest {

	@Mock
	private CurrencyRepository repository;
	
	@InjectMocks
	private CurrencyService service;

	@Before
	public void setup() {
		initMocks(this);
	}

	@Test
	public void getAvailableCurrencies() {
		Mockito.when(repository.getContation()).thenReturn(getCotation());
		List<String> currencies = toList(service.getAvailableCurrencies());
		assertThat(currencies, hasSize(1));
		assertThat(currencies.get(0), equalTo("USD"));
	}
	
	@Test
	public void getRate() throws CurrencyNotFoundException {
		Mockito.when(repository.getContation()).thenReturn(getCotation());
		BigDecimal rate = service.getRate("USD");
		assertThat(rate, comparesEqualTo(valueOf(1.063)));
	}
	
	@Test(expected=CurrencyNotFoundException.class)
	public void getRateWhenDoesNotExist() throws CurrencyNotFoundException {
		Mockito.when(repository.getContation()).thenReturn(getCotation());
		service.getRate("");
	}

	private CotationDTO getCotation() {
		Map<String, String> rates = new HashMap<>();
		rates.put("USD", "1.063");
		return CotationDTO.builder()
				.rates(rates)
				.build();
	}



}

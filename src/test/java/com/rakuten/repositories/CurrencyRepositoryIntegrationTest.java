package com.rakuten.repositories;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import com.rakuten.dtos.CotationDTO;

public class CurrencyRepositoryIntegrationTest {

	@InjectMocks
	private CurrencyRepository repository;
	
	
	@Before
	public void setup(){
		initMocks(this);
	}
	
	@Test
	public void getContation(){
		CotationDTO cotation = repository.getContation();
		assertThat(cotation, notNullValue());
		assertThat(cotation.getBase(), notNullValue());
		assertThat(cotation.getDate(), notNullValue());
		assertThat(cotation.getRates(), notNullValue());
	}
	
}

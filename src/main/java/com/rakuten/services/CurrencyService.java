package com.rakuten.services;

import java.math.BigDecimal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rakuten.repositories.CurrencyRepository;
import com.rakuten.services.exceptions.CurrencyNotFoundException;

@Service
public class CurrencyService {
	
	@Autowired
	private CurrencyRepository currencyRepository;

	public Collection<String> getAvailableCurrencies() {	
		return currencyRepository.getContation().getRates().keySet();
	}

	public BigDecimal getRate(String currency) throws CurrencyNotFoundException {
		String rate = currencyRepository.getContation().getRates().get(currency);
		if(rate!=null)
			return BigDecimal.valueOf(Double.parseDouble(rate));
		throw new CurrencyNotFoundException();
	}

}

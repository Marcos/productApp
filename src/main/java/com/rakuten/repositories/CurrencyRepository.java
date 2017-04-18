package com.rakuten.repositories;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.rakuten.dtos.CotationDTO;

@Repository
public class CurrencyRepository {
	
	@Cacheable("cotation")
	public CotationDTO getContation(){
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject("http://api.fixer.io/latest?base=EUR", CotationDTO.class);
	}

}

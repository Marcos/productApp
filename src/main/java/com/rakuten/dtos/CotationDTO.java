package com.rakuten.dtos;

import java.util.Date;
import java.util.Map;

import groovy.transform.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CotationDTO {
	
	private String base;
	
	private Date date;
	
	private Map<String, String> rates;
	
}

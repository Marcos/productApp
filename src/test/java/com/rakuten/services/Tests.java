package com.rakuten.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Tests {

	public static <T> List<T> toList(Iterable<T> iterable) {
		return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
	}
	
}

package com.rakuten.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rakuten.entities.Category;
import com.rakuten.entities.Product;
import com.rakuten.repositories.ProductRepository;
import com.rakuten.services.exceptions.CurrencyNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CurrencyService currencyService;

	public Iterable<Product> findAll() {
		return repository.findAll();
	}

	public void save(Product product) throws CurrencyNotFoundException {
		setEuroPrice(product);
		repository.save(product);
	}

	private void setEuroPrice(Product product) throws CurrencyNotFoundException {
		if (useOtherCurrency(product)) {
			BigDecimal rate = currencyService.getRate(product.getCurrency());
			product.setPrice(product.getPrice().divide(rate, RoundingMode.HALF_UP));
		}
	}

	private boolean useOtherCurrency(Product product) {
		return product.getPrice() != null && product.getCurrency() != null && product.getCurrency().length() > 0;
	}

	public Product find(Long id) throws CurrencyNotFoundException {
		Product product = repository.findOne(id);
		setCurrencyPrice(product);
		return product;
	}

	private void setCurrencyPrice(Product product) throws CurrencyNotFoundException {
		if (useOtherCurrency(product)) {
			BigDecimal rate = currencyService.getRate(product.getCurrency());
			product.setPrice(product.getPrice().multiply(rate).setScale(4, RoundingMode.HALF_UP));
		}
	}

	public void delete(Long id) {
		repository.delete(id);
	}

	public Collection<String> getAvailableCurrencies() {
		return currencyService.getAvailableCurrencies();
	}

	public Iterable<Category> findAvailableCategories() {
		return categoryService.findAll();
	}

}

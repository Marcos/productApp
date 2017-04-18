package com.rakuten.services;

import static com.rakuten.services.Tests.toList;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.rakuten.entities.Category;
import com.rakuten.entities.Product;
import com.rakuten.repositories.ProductRepository;
import com.rakuten.services.exceptions.CurrencyNotFoundException;

public class ProductServiceTest {

	private static final String USD = "USD";
	private static final Long COMPUTER_ID = 12L;
	private static final Long PANTS_ID = 13L;
	private static final Long HOME_CATEGORY_ID = 20L;
	private static final Category HOME_CATEGORY = Category.builder().id(HOME_CATEGORY_ID).build();

	@Mock
	private ProductRepository repository;

	@Mock
	private CategoryService categoryService;

	@Mock
	private CurrencyService currencyService;

	@InjectMocks
	private ProductService service;

	@Before
	public void setup() {
		initMocks(this);
	}

	@Test
	public void findAll() {
		when(repository.findAll()).thenReturn(getAllProducts());
		List<Product> products = toList(service.findAll());
		assertThat(products, hasSize(2));
		assertThat(products.get(0).getId(), equalTo(COMPUTER_ID));
		assertThat(products.get(1).getId(), equalTo(PANTS_ID));
		verify(repository).findAll();
	}

	@Test
	public void save() throws CurrencyNotFoundException {
		Product product = createProduct(PANTS_ID);
		service.save(product);
		verify(repository).save(product);
	}
	
	@Test
	public void saveWhenHasOtherCurrency() throws CurrencyNotFoundException {
		when(currencyService.getRate(USD)).thenReturn(valueOf(1.063));
		Product product = createProductFromUS(COMPUTER_ID);
		service.save(product);
		assertThat(product.getPrice(), comparesEqualTo(valueOf(9)));
		verify(repository).save(product);
		verify(currencyService).getRate(USD);
	}
	
	@Test
	public void saveWhenHasOtherCurrencyButNoPrice() throws CurrencyNotFoundException {
		Product product = createProductFromUS(COMPUTER_ID);
		product.setPrice(null);
		service.save(product);
		assertThat(product.getPrice(), equalTo(null));
		verify(repository).save(product);
	}
	
	@Test
	public void saveWhenHasEmptyCurrency() throws CurrencyNotFoundException {
		Product product = createProductFromUS(COMPUTER_ID);
		product.setCurrency("");
		service.save(product);
		assertThat(product.getPrice(), comparesEqualTo(TEN));
		verify(repository).save(product);
		verify(currencyService, never()).getRate(USD);
	}

	@Test
	public void find() throws CurrencyNotFoundException {
		when(repository.findOne(PANTS_ID)).thenReturn(createProduct(PANTS_ID));
		Product product = service.find(PANTS_ID);
		assertThat(product.getId(), equalTo(PANTS_ID));
		assertThat(product.getPrice(), comparesEqualTo(TEN));
		verify(repository).findOne(PANTS_ID);
	}
	
	@Test
	public void findWhenHasOtherCurrency() throws CurrencyNotFoundException {
		when(currencyService.getRate(USD)).thenReturn(valueOf(1.063));
		when(repository.findOne(PANTS_ID)).thenReturn(createProductFromUS(COMPUTER_ID));
		Product product = service.find(PANTS_ID);
		assertThat(product.getId(), equalTo(COMPUTER_ID));
		assertThat(product.getPrice(), comparesEqualTo(valueOf(10.63)));
		verify(repository).findOne(PANTS_ID);
	}

	@Test
	public void delete() {
		service.delete(PANTS_ID);
		verify(repository).delete(PANTS_ID);
	}

	@Test
	public void getAvailableCurrencies() {
		when(currencyService.getAvailableCurrencies()).thenReturn(Arrays.asList(USD));
		List<String> currencies = toList(service.getAvailableCurrencies());
		assertThat(currencies, hasSize(1));
		assertThat(currencies.get(0), equalTo("USD"));
		verify(currencyService).getAvailableCurrencies();
	}

	@Test
	public void findAvailableCategories() {
		when(categoryService.findAll()).thenReturn(asList(HOME_CATEGORY));
		List<Category> categories = toList(service.findAvailableCategories());
		assertThat(categories, hasSize(1));
		assertThat(categories.get(0).getId(), equalTo(HOME_CATEGORY_ID));
		verify(categoryService).findAll();
	}

	private List<Product> getAllProducts() {
		return asList(createProduct(COMPUTER_ID), createProduct(PANTS_ID));
	}

	private Product createProduct(Long productId) {
		return Product.builder().id(productId).price(TEN).build();
	}
	private Product createProductFromUS(Long productId) {
		return Product.builder().id(productId).currency(USD).price(TEN).build();
	}


}

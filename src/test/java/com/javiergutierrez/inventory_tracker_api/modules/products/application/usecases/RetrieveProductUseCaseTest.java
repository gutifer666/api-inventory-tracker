package com.javiergutierrez.inventory_tracker_api.modules.products.application.usecases;

import com.javiergutierrez.inventory_tracker_api.modules.categories.domain.Category;
import com.javiergutierrez.inventory_tracker_api.modules.products.domain.Product;
import com.javiergutierrez.inventory_tracker_api.modules.products.infrastructure.adapters.ProductRepositoryAdapter;
import com.javiergutierrez.inventory_tracker_api.modules.suppliers.domain.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class RetrieveProductUseCaseTest {

	@Mock
	private ProductRepositoryAdapter productRepositoryAdapter;
	@InjectMocks
	private RetrieveProductUseCase retrieveProductUseCase;

	private static AutoCloseable closeable;

	private Product product;
	private Product expectedProduct;

	@BeforeEach
	void beforeEach() {
		log.info("Start to prepare beforeEach");

		closeable = MockitoAnnotations.openMocks(this);

		Category category = Category.builder()
				.id(1L)
				.name("General")
				.description("Productos de uso general")
				.build();

		Supplier supplier = Supplier.builder()
				.id(1L)
				.name("BH")
				.build();

		product = Product.builder()
				.id(1L)
				.code("GEN001")
				.name("Bicicleta")
				.description("Bicicleta BH de montaña")
				.category(category)
				.supplier(supplier)
				.costPrice(500.00)
				.quantity(1)
				.build();

		expectedProduct = product.clone();

		log.info("End to prepare beforeEach");

	}
	@AfterEach
	void afterEach() throws Exception{
		closeable.close();
	}

	@AfterAll
	static void afterAll() throws Exception {
		log.info("Start to AfterAll");
		log.info("End to AfterAll");
		log.info("RetrieveProductUseCaseTest: End to tests");
	}

	@Test
	void when_find_all_products_should_be_a_list() {
		String method = "when_find_all_products_should_be_a_list";
		log.info("Start {}", method);

		List<Product> productList = List.of(product);

		when(productRepositoryAdapter.findAllProducts()).thenReturn(Optional.of(productList));

		Optional<List<Product>> resultList = retrieveProductUseCase.findAllProducts();

		verify(productRepositoryAdapter).findAllProducts();
		assertTrue(resultList.isPresent(), "Result should be present");
		assertEquals(1, resultList.get().size(), "Should return one product");
		assertProduct(resultList.get().get(0));

		log.info("End {}.", method);
	}

	@Test
	void when_find_product_by_id_should_return_product() {
		String method = "when_find_product_by_id_should_return_product";
		log.info("Start {}.", method);

		when(productRepositoryAdapter.findProductById(1L)).thenReturn(Optional.of(product));

		Optional<Product> resultOptional = retrieveProductUseCase.findProductById(1L);

		verify(productRepositoryAdapter).findProductById(1L);
		assertTrue(resultOptional.isPresent(), "Result should be present");
		assertProduct(resultOptional.get());

		log.info("End {}.", method);
	}

	private void assertProduct(Product product) {

		assertNotNull(product, "Result should not be null.");
		assertEquals(expectedProduct.getId(), product.getId(), "Id should be the same.");
		assertEquals(expectedProduct.getCode(), product.getCode(), "Code should be the same.");
		assertEquals(expectedProduct.getName(), product.getName(), "Name should be the same.");
		assertEquals(expectedProduct.getDescription(), product.getDescription(), "Description should be the same.");
		assertEquals(expectedProduct.getCategory(), product.getCategory(), "Category should be the same.");
		assertEquals(expectedProduct.getSupplier(), product.getSupplier(), "Supplier should be the same.");
		assertEquals(expectedProduct.getCostPrice(), product.getCostPrice(), "Price should be the same.");
		assertEquals(expectedProduct.getQuantity(), product.getQuantity(), "Quantity should be the same.");

	}

}

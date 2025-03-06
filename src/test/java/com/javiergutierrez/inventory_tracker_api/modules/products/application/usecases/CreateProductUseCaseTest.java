package com.javiergutierrez.inventory_tracker_api.modules.products.application.usecases;

import com.javiergutierrez.inventory_tracker_api.modules.categories.domain.Category;
import com.javiergutierrez.inventory_tracker_api.modules.products.domain.Product;
import com.javiergutierrez.inventory_tracker_api.modules.products.infrastructure.adapters.ProductRepositoryAdapter;
import com.javiergutierrez.inventory_tracker_api.modules.suppliers.domain.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class CreateProductUseCaseTest {

	@Mock
	private ProductRepositoryAdapter productRepositoryAdapter;
	@InjectMocks
	private CreateProductUseCase createProductUseCase;

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

		log.info("End to BeforeEach.");
	}

	@AfterEach
	void afterEach() throws Exception{
		closeable.close();
	}

	@Test
	void when_create_product_and_is_not_present_should_be_a_new_product() {
		String method = "when_create_product_and_is_not_present_should_be_a_new_product";
		log.info("Start to test: {}", method);

		when(productRepositoryAdapter.createProduct(product)).thenReturn(Optional.of(expectedProduct));

		Optional<Product> resultOptional = createProductUseCase.createProduct(product);
		assertTrue(resultOptional.isPresent(), "Result should be present.");
		Product result = resultOptional.get();

		verify(productRepositoryAdapter).createProduct(product);

		assertProduct(result);

		log.info("End {}.", method);
	}

	@Test
	void  when_create_product_and_is_present_should_update_product_and_increment_quantity() {
		String method = "when_create_product_and_is_present_should_update_product_and_increment_quantity";
		log.info("Start {}.", method);

		Product existingProduct = product.clone();
		Product updatedProduct = product.clone();
		updatedProduct.setQuantity(existingProduct.getQuantity() + 1);

		when(productRepositoryAdapter.findProductByCode(product.getCode())).thenReturn(Optional.of(existingProduct));
		when(productRepositoryAdapter.updateProduct(existingProduct)).thenReturn(Optional.of(updatedProduct));

		Optional<Product> resultOptional = createProductUseCase.createProduct(product);
		assertTrue(resultOptional.isPresent(), "Result should be present.");
		Product result = resultOptional.get();

		verify(productRepositoryAdapter).findProductByCode(product.getCode());
		verify(productRepositoryAdapter).updateProduct(existingProduct);

		assertEquals(2, result.getQuantity(), "Quantity should be incremented.");

	}

	@Test
	void when_create_product_fails_should_throw_exception() {
		String method = "when_create_product_fails_should_throw_exception";
		log.info("Start {}.", method);

		when(productRepositoryAdapter.createProduct(product)).thenReturn(Optional.empty());

		assertThrows(IllegalStateException.class, () -> createProductUseCase.createProduct(product));

		verify(productRepositoryAdapter).createProduct(product);

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

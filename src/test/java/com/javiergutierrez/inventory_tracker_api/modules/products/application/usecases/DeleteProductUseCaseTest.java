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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class DeleteProductUseCaseTest {

	@Mock
	private ProductRepositoryAdapter productRepositoryAdapter;
	@InjectMocks
	private DeleteProductUseCase deleteProductUseCase;

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
	void when_delete_product_and_quantity_is_greater_than_one_then_decrement_product_quantity() {
		String method = "when_delete_product_and_quantity_is_greater_than_one_then_decrement_product_quantity";
		log.info("Start {}.", method);

		Product productWith2Quantity = product.clone();
		productWith2Quantity.setQuantity(2);

		when(productRepositoryAdapter.findProductById(product.getId())).thenReturn(Optional.of(productWith2Quantity));
		when(productRepositoryAdapter.updateProduct(product)).thenReturn(Optional.of(product));

		Optional<Product> resultOptional = deleteProductUseCase.deleteProduct(product.getId());

		verify(productRepositoryAdapter).findProductById(product.getId());
		verify(productRepositoryAdapter).updateProduct(product);

		assertTrue(resultOptional.isPresent(), "Result should be present.");
		assertEquals(1, resultOptional.get().getQuantity(), "Quantity should be 1");

		log.info("End {}.", method);

	}

	@Test
	void when_delete_product_and_quantity_is_one_then_delete_product() {
		String method = "when_delete_product_and_quantity_is_one_then_delete_product";
		log.info("Start {}.", method);

		when(productRepositoryAdapter.findProductById(product.getId())).thenReturn(Optional.of(product));
		when(productRepositoryAdapter.deleteProduct(product.getId())).thenReturn(Optional.empty());

		Optional<Product> resultOptional = deleteProductUseCase.deleteProduct(product.getId());

		verify(productRepositoryAdapter).findProductById(product.getId());
		verify(productRepositoryAdapter).deleteProduct(product.getId());

		assertTrue(resultOptional.isEmpty(), "Result should be empty.");

		log.info("End {}.", method);
	}

	@Test
	void when_delete_product_fails_should_throw_exception() {
		String method = "when_delete_product_fails_should_throw_exception";
		log.info("Start {}.", method);

		when(productRepositoryAdapter.deleteProduct(1L)).thenThrow(new IllegalStateException("Failed to delete product: 1"));
		assertThrows(IllegalStateException.class, () -> deleteProductUseCase.deleteProduct(1L));

		verify(productRepositoryAdapter).deleteProduct(1L);

		log.info("End {}.", method);

	}

}

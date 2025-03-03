package com.javiergutierrez.inventory_tracker_api.modules.products.application.usecases;

import com.javiergutierrez.inventory_tracker_api.modules.products.domain.Product;
import com.javiergutierrez.inventory_tracker_api.modules.products.infrastructure.adapters.ProductRepositoryAdapter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Transactional
@Component
public class CreateProductUseCase {

	private ProductRepositoryAdapter productRepositoryAdapter;

	public Optional<Product> createProduct(Product product) {
		log.info("Call to createProduct {}", product);
		log.debug("Product to create: {}", product);

		Optional<Product> existingProduct = productRepositoryAdapter.findProductByCode(product.getCode());

		if (existingProduct.isPresent()) {

			int totalQuantity = existingProduct.get().getQuantity() + product.getQuantity();

			Product productToUpdate = existingProduct.get();

			productToUpdate.setQuantity(totalQuantity);

			log.info("Product exists, updated quantity to: {}", totalQuantity);

			Optional<Product> updatedProduct = productRepositoryAdapter.updateProduct(productToUpdate);

			return updatedProduct;
		}

		Optional<Product> createdProduct = productRepositoryAdapter.createProduct(product);
		if (createdProduct.isEmpty()) {
			throw new IllegalStateException("Failed to create product: " + product);
		}
		return createdProduct;
	}

}

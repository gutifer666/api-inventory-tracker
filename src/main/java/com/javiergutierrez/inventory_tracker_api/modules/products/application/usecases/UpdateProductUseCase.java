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
public class UpdateProductUseCase {

	private ProductRepositoryAdapter productRepositoryAdapter;

	public Optional<Product> updateProduct(Long id, Product product) {
		log.info("Call to updateProduct with ID: {}.", id);
		log.debug(product.toString());

		Product existingProduct = productRepositoryAdapter.findProductById(id)
				.orElseThrow(() -> new IllegalStateException("Failed to get product with ID: " + id));

		existingProduct.setCode(product.getCode());
		existingProduct.setName(product.getName());
		existingProduct.setDescription(product.getDescription());
		existingProduct.getCategory().setId(product.getCategory().getId());
		existingProduct.getSupplier().setId(product.getSupplier().getId());
		existingProduct.setCostPrice(product.getCostPrice());
		existingProduct.setRetailPrice(product.getRetailPrice());
		existingProduct.setQuantity(product.getQuantity());

		log.debug("Product after update. {}", existingProduct);

		return productRepositoryAdapter.updateProduct(existingProduct);

	}

}

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
public class DeleteProductUseCase {

	private ProductRepositoryAdapter productRepositoryAdapter;

	public Optional<Product> deleteProduct(long id) throws IllegalStateException {
		log.info("Call to deleteProduct {}", id);
		log.debug("Product to delete: {}", id);

		Optional<Product> productOptional = productRepositoryAdapter.findProductById(id);

		if (productOptional.isPresent()) {
			Product product = productOptional.get();
			if (product.getQuantity() > 1) {
				return decrementProductQuantity(product);
			}
		}

		return deleteProductByIdOrElseThrow(id);
	}

	private Optional<Product> decrementProductQuantity(Product product) {
		int reducedQuantity = product.getQuantity() - 1;
		product.setQuantity(reducedQuantity);
		productRepositoryAdapter.updateProduct(product);
		log.info("Reduced product quantity to {}", reducedQuantity);
		return Optional.of(product);
	}

	private Optional<Product> deleteProductByIdOrElseThrow(long id) throws IllegalStateException {
		try {
			productRepositoryAdapter.deleteProduct(id);
			log.info("Product with id {} deleted successfully", id);
			return Optional.empty();
		} catch (Exception e) {
			log.error("Failed to delete product with id {}", id, e);
			throw new IllegalStateException("Failed to delete product: " + id, e);
		}
	}

}

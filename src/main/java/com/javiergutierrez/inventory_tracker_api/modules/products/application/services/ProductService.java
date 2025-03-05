package com.javiergutierrez.inventory_tracker_api.modules.products.application.services;

import com.javiergutierrez.inventory_tracker_api.modules.products.application.usecases.CreateProductUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.products.application.usecases.DeleteProductUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.products.application.usecases.RetrieveProductUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.products.application.usecases.UpdateProductUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.products.domain.Product;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class ProductService {
	private CreateProductUseCase createProductUseCase;
	private RetrieveProductUseCase retrieveProductUseCase;
	private UpdateProductUseCase updateProductUseCase;
	private DeleteProductUseCase deleteProductUseCase;

	public Optional<Product> createProduct(Product product) {
		log.info("Call to createProduct.");
		log.debug(product.toString());
		return createProductUseCase.createProduct(product);
	}

	public Optional<Product> findProductById(Long id) {
		log.info("Call to findProductById with id {}.", id);
		return retrieveProductUseCase.findProductById(id);
	}

	public Optional<Product> updateProduct(Long id, Product product) {
		log.info("Call to updateProduct with id {}.", id);
		log.debug(product.toString());
		return updateProductUseCase.updateProduct(id, product);
	}

	public Optional<Product> deleteProduct(Long id) {
		log.info("Call to deleteProduct with id {}.", id);
		return deleteProductUseCase.deleteProduct(id);
	}

	public Optional<List<Product>> findAllProducts() {
		log.info("Call to findAllProducts.");
		return retrieveProductUseCase.findAllProducts();
	}
}

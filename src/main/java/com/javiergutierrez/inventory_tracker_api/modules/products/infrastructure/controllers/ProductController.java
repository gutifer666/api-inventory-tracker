package com.javiergutierrez.inventory_tracker_api.modules.products.infrastructure.controllers;

import com.javiergutierrez.inventory_tracker_api.modules.products.application.services.ProductService;
import com.javiergutierrez.inventory_tracker_api.modules.products.domain.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Slf4j
@Validated
@RestController
public class ProductController {

	private final ProductService productService;

	@PostMapping
	public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
		log.info("Call to createProduct {}", product);
		Product createdProduct = productService.createProduct(product).orElseThrow(() -> {
			log.error("Failed to create product {}", product);
			return new IllegalStateException("Failed to create product.");
		});
		log.info("Product created {}", createdProduct);
		return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> findProductById(@PathVariable Long id) {
		log.info("Call to findProductById with ID: {}", id);
		Product product = productService.findProductById(id).orElseThrow(() -> {
			log.error("Failed to get product with id: {}", id);
			return new IllegalStateException("Failed to find product with ID: " + id);
		});
		log.info("Successfully retrieved product: {}.", product);
		return ResponseEntity.ok(product);
	}

	@GetMapping
	public ResponseEntity<List<Product>> findAllProducts() {
		log.info("Call to findAllProducts.");
		List<Product> productList = productService.findAllProducts().orElseThrow(() -> {
			log.error("Failed to find products.");
			return new IllegalStateException("Failed to find products.");
		});
		log.info("Successfully retrieved products: {}.", productList);
		return ResponseEntity.ok(productList);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
		log.info("Call to updateProduct with id {}.", id);
		Product updatedProduct = productService.updateProduct(id, product).orElseThrow(() -> {
			log.error("Failed to update product with id: {}", id);
			return new IllegalStateException("Failed to update product with ID: " + id);
		});
		log.info("Successfully updated product: {}.", updatedProduct);
		return ResponseEntity.ok(updatedProduct);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
		log.info("Call to deleteProduct with id {}.", id);
		Product deletedProduct = productService.deleteProduct(id).orElseThrow(() -> {
			log.error("Failed to delete product with id: {}", id);
			return new IllegalStateException("Failed to delete product with ID: " + id);
		});
		log.info("Successfully deleted product: {}.", deletedProduct);
		return ResponseEntity.ok(deletedProduct);
	}
}

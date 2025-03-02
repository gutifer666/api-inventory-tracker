package com.javiergutierrez.inventory_tracker_api.modules.products.infrastructure.adapters;

import com.javiergutierrez.inventory_tracker_api.modules.products.domain.Product;
import com.javiergutierrez.inventory_tracker_api.modules.products.infrastructure.entities.ProductEntity;
import com.javiergutierrez.inventory_tracker_api.modules.products.infrastructure.mappers.ProductMapper;
import com.javiergutierrez.inventory_tracker_api.modules.products.infrastructure.repositories.IJpaProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Component
public class ProductRepositoryAdapter {

	private final IJpaProductRepository iJpaProductRepository;
	private final ProductMapper productMapper;

	public Optional<Product> createProduct(Product product) {
		log.info("Call to createProduct {}", product);
		log.debug("Product to create: {}", product);
		ProductEntity productEntity = productMapper.toEntity(product);
		ProductEntity createdProductEntity = iJpaProductRepository.save(productEntity);
		log.info("Created product with ID: {}", product.getId());
		log.debug("Product created: {}", createdProductEntity);
		return Optional.of(productMapper.toModel(createdProductEntity));
	}

	public Optional<List<Product>> findAllProducts() {
		log.info("Call to findAllProducts.");
		List<Product> productList = iJpaProductRepository.findAll().stream()
				.map(productMapper::toModel)
				.collect(Collectors.toList());
		log.info("Found {} products.", productList.size());
		log.debug("Products found: {}", productList);
		return Optional.of(productList);
	}

	public Optional<Product> findProductById(Long id) {
		log.info("Call to findProductById with ID: {}.", id);
		Optional<Product> product = iJpaProductRepository.findById(id)
				.map(productMapper::toModel);
		if (product.isPresent()) {
			log.info("Found product with ID: {}.", id);
		} else {
			log.error("No product found with ID: {}.", id);
		}
		return product;
	}

	public Optional<Product> findProductByCode(String code) {
		log.info("Call to findProductByCode with code: {}.", code);
		Optional<Product> product = iJpaProductRepository.findByCode(code)
				.map(productMapper::toModel);
		if (product.isPresent()) {
			log.info("Found product with code: {}.", code);
		} else {
			log.error("No product found with code: {}.", code);
		}
		return product;
	}

	public Optional<Product> updateProduct(Product product) {
		log.info("Call to updateProduct with ID: {}.", product.getId());
		ProductEntity productEntity = productMapper.toEntity(product);
		ProductEntity updatedProductEntity = iJpaProductRepository.save(productEntity);
		log.info("Updated product with ID: {}.", product.getId());
		return Optional.of(productMapper.toModel(updatedProductEntity));
	}

	public Optional<Product> deleteProduct(Long id) {
		log.info("Call to deleteProduct with ID: {}.", id);

		Optional<Product> product = iJpaProductRepository.findById(id).map(productMapper::toModel);

		if (product.isPresent()) {
			iJpaProductRepository.deleteById(id);
			log.info("Deleted product with ID: {}.", id);
			return product;
		}

		log.error("No product found with ID: {}.", id);

		return product;

	}
}

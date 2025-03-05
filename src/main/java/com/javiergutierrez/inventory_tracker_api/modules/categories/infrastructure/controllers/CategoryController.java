package com.javiergutierrez.inventory_tracker_api.modules.categories.infrastructure.controllers;

import com.javiergutierrez.inventory_tracker_api.modules.categories.application.services.CategoryService;
import com.javiergutierrez.inventory_tracker_api.modules.categories.domain.Category;
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
@RequestMapping("/api/categories")
@Slf4j
@Validated
@RestController
public class CategoryController {

	private final CategoryService categoryService;

	@PostMapping
	public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
		log.info("Call to createCategory {}", category);
		Category createdCategory = categoryService.createCategory(category).orElseThrow(() -> {
			log.error("Failed to create category {}", category);
			return new IllegalStateException("Failed to create category.");
		});
		log.info("Category created {}", createdCategory);
		return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Category> findCategoryById(@PathVariable Long id) {
		log.info("Call to findCategoryById with ID: {}", id);
		Category category = categoryService.findCategoryById(id).orElseThrow(() -> {
			log.error("Failed to get category with id: {}", id);
			return new IllegalStateException("Failed to find category with ID: " + id);
		});
		log.info("Successfully retrieved category: {}.", category);
		return ResponseEntity.ok(category);
	}

	@GetMapping
	public ResponseEntity<List<Category>> findAllCategories() {
		log.info("Call to findAllCategories.");
		List<Category> categoryList = categoryService.findAllCategories().orElseThrow(() -> {
			log.error("Failed to find Categories.");
			return new IllegalStateException("Failed to find categories.");
		});
		log.info("Successfully retrieved categories: {}.", categoryList);
		return ResponseEntity.ok(categoryList);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody Category category) {
		log.info("Call to updateCategory with id {}.", id);
		Category updatedCategory = categoryService.updateCategory(id, category).orElseThrow(() -> {
			log.error("Failed to update category with id: {}", id);
			return new IllegalStateException("Failed to update category with ID: " + id);
		});
		log.info("Successfully updated category: {}.", updatedCategory);
		return ResponseEntity.ok(updatedCategory);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Category> deleteCategory(@PathVariable Long id) {
		log.info("Call to deleteCategory with id {}.", id);
		Category deletedCategory = categoryService.deleteCategory(id).orElseThrow(() -> {
			log.error("Failed to delete category with id: {}", id);
			return new IllegalStateException("Failed to delete category with ID: " + id);
		});
		log.info("Successfully deleted category: {}.", deletedCategory);
		return ResponseEntity.ok(deletedCategory);
	}
}

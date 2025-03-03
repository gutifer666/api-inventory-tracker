package com.javiergutierrez.inventory_tracker_api.modules.categories.application.usecases;

import com.javiergutierrez.inventory_tracker_api.modules.categories.domain.Category;
import com.javiergutierrez.inventory_tracker_api.modules.categories.infrastructure.adapters.CategoryRepositoryAdapter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Transactional
@Component
public class UpdateCategoryUseCase {

	private CategoryRepositoryAdapter categoryRepositoryAdapter;

	public Optional<Category> updateCategory(Long id, Category category) {
		log.info("Call to updateCategory with ID: {}.", id);
		log.debug(category.toString());

		Category existingCategory = categoryRepositoryAdapter.findCategoryById(id)
				.orElseThrow(() -> new IllegalStateException("Failed to get category with ID: " + id));

		existingCategory.setName(category.getName());
		existingCategory.setDescription(category.getDescription());

		log.debug("Category after update. {}", existingCategory);

		return categoryRepositoryAdapter.updateCategory(existingCategory);

	}

}

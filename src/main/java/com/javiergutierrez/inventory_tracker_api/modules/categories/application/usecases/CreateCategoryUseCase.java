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
public class CreateCategoryUseCase {

	private CategoryRepositoryAdapter categoryRepositoryAdapter;

	public Optional<Category> createCategory(Category category) {
		log.info("Call to createCategory {}", category);
		log.debug("Category to create: {}", category);

		Optional<Category> createdCategory = categoryRepositoryAdapter.createCategory(category);

		if (createdCategory.isEmpty()) {
			throw new IllegalStateException("Failed to create category: " + category);
		}

		return createdCategory;

	}

}

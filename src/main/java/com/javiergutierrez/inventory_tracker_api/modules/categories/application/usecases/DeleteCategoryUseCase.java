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
public class DeleteCategoryUseCase {

	private CategoryRepositoryAdapter categoryRepositoryAdapter;

	public Optional<Category> deleteCategory(long id) throws IllegalStateException {
		log.info("Call to deleteCategory {}", id);
		log.debug("Category to delete: {}", id);

		Optional<Category> categoryOptional = categoryRepositoryAdapter.findCategoryById(id);

		if (categoryOptional.isEmpty()) {
			throw new IllegalStateException("Category with ID: " + id + " does not exist.");
		}

		return categoryOptional;

	}

}

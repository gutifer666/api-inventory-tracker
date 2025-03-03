package com.javiergutierrez.inventory_tracker_api.modules.categories.application.usecases;

import com.javiergutierrez.inventory_tracker_api.modules.categories.domain.Category;
import com.javiergutierrez.inventory_tracker_api.modules.categories.infrastructure.adapters.CategoryRepositoryAdapter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Component
public class RetrieveCategoryUseCase {

	private CategoryRepositoryAdapter categoryRepositoryAdapter;

	public Optional<List<Category>> findAllCategories() {
		log.info("Call to findAllCategories.");

		Optional<List<Category>> categoryList = categoryRepositoryAdapter.findAllCategories();

		log.debug(categoryList.toString());

		return categoryList;
	}

	public Optional<Category> findCategoryById(Long id) {
		log.info("Call to findCategoryById.");
		return categoryRepositoryAdapter.findCategoryById(id);
	}
}

package com.javiergutierrez.inventory_tracker_api.modules.categories.application.services;

import com.javiergutierrez.inventory_tracker_api.modules.categories.application.usecases.CreateCategoryUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.categories.application.usecases.DeleteCategoryUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.categories.application.usecases.RetrieveCategoryUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.categories.application.usecases.UpdateCategoryUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.categories.domain.Category;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class CategoryService {
	private CreateCategoryUseCase createCategoryUseCase;
	private RetrieveCategoryUseCase retrieveCategoryUseCase;
	private UpdateCategoryUseCase updateCategoryUseCase;
	private DeleteCategoryUseCase deleteCategoryUseCase;

	public Optional<Category> createCategory(Category category) {
		log.info("Call to createCategory.");
		log.debug(category.toString());
		return createCategoryUseCase.createCategory(category);
	}

	public Optional<Category> findCategoryById(Long id) {
		log.info("Call to findCategoryById with id {}.", id);
		return retrieveCategoryUseCase.findCategoryById(id);
	}

	public Optional<Category> updateCategory(Long id, Category category) {
		log.info("Call to updateCategory with id {}.", id);
		log.debug(category.toString());
		return updateCategoryUseCase.updateCategory(id, category);
	}

	public Optional<Category> deleteCategory(Long id) {
		log.info("Call to deleteCategory with id {}.", id);
		return deleteCategoryUseCase.deleteCategory(id);
	}

	public Optional<List<Category>> findAllCategories() {
		log.info("Call to findAllCategories.");
		return retrieveCategoryUseCase.findAllCategories();
	}
}

package com.javiergutierrez.inventory_tracker_api.modules.categories.infrastructure.adapters;

import com.javiergutierrez.inventory_tracker_api.modules.categories.domain.Category;
import com.javiergutierrez.inventory_tracker_api.modules.categories.infrastructure.entities.CategoryEntity;
import com.javiergutierrez.inventory_tracker_api.modules.categories.infrastructure.mappers.CategoryMapper;
import com.javiergutierrez.inventory_tracker_api.modules.categories.infrastructure.repositories.IJpaCategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Component
public class CategoryRepositoryAdapter {

	private final IJpaCategoryRepository iJpaCategoryRepository;
	private final CategoryMapper categoryMapper;

	public Optional<Category> createCategory(Category category) {
		log.info("Call to createCategory {}", category);
		log.debug("Category to create: {}", category);
		CategoryEntity categoryEntity = categoryMapper.toEntity(category);
		CategoryEntity createdCategoryEntity = iJpaCategoryRepository.save(categoryEntity);
		log.info("Created category with ID: {}", category.getId());
		log.debug("Category created: {}", createdCategoryEntity);
		return Optional.of(categoryMapper.toModel(createdCategoryEntity));
	}

	public Optional<List<Category>> findAllCategories() {
		log.info("Call to findAllCategories.");
		List<Category> categoryList = iJpaCategoryRepository.findAll().stream()
				.map(categoryMapper::toModel)
				.collect(Collectors.toList());
		log.info("Found {} categories.", categoryList.size());
		log.debug("Categories found: {}", categoryList);
		return Optional.of(categoryList);
	}

	public Optional<Category> findCategoryById(Long id) {
		log.info("Call to findCategoryById with ID: {}.", id);
		Optional<Category> category = iJpaCategoryRepository.findById(id)
				.map(categoryMapper::toModel);
		if (category.isPresent()) {
			log.info("Found category with ID: {}.", id);
		} else {
			log.error("No category found with ID: {}.", id);
		}
		return category;
	}

	public Optional<Category> updateCategory(Category category) {
		log.info("Call to updateCategory with ID: {}.", category.getId());
		CategoryEntity categoryEntity = categoryMapper.toEntity(category);
		CategoryEntity updatedCategoryEntity = iJpaCategoryRepository.save(categoryEntity);
		log.info("Updated category with ID: {}.", category.getId());
		return Optional.of(categoryMapper.toModel(updatedCategoryEntity));
	}

	public boolean deleteCategory(Long id) {
		log.info("Call to deleteCategory with ID: {}.", id);

		if (iJpaCategoryRepository.existsById(id)) {
			iJpaCategoryRepository.deleteById(id);
			log.info("Deleted category with ID: {}.", id);
			return true;
		}
		else {
			log.error("No category found with ID: {}.", id);
			return false;
		}

	}
}

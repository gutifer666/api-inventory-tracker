package com.javiergutierrez.inventory_tracker_api.modules.categories.infrastructure.mappers;

import com.javiergutierrez.inventory_tracker_api.common.GenericMapper;
import com.javiergutierrez.inventory_tracker_api.modules.categories.domain.Category;
import com.javiergutierrez.inventory_tracker_api.modules.categories.infrastructure.entities.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends GenericMapper<CategoryEntity, Category> {
}

package com.javiergutierrez.inventory_tracker_api.modules.products.infrastructure.mappers;

import com.javiergutierrez.inventory_tracker_api.common.GenericMapper;
import com.javiergutierrez.inventory_tracker_api.modules.products.domain.Product;
import com.javiergutierrez.inventory_tracker_api.modules.products.infrastructure.entities.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends GenericMapper<ProductEntity, Product> {
}

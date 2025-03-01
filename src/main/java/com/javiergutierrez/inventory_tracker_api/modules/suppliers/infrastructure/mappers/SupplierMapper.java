package com.javiergutierrez.inventory_tracker_api.modules.suppliers.infrastructure.mappers;

import com.javiergutierrez.inventory_tracker_api.common.GenericMapper;
import com.javiergutierrez.inventory_tracker_api.modules.suppliers.domain.Supplier;
import com.javiergutierrez.inventory_tracker_api.modules.suppliers.infrastructure.entities.SupplierEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper extends GenericMapper<SupplierEntity, Supplier> {
}

package com.javiergutierrez.inventory_tracker_api.modules.suppliers.infrastructure.repositories;

import com.javiergutierrez.inventory_tracker_api.modules.suppliers.infrastructure.entities.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJpaSupplierRepository extends JpaRepository<SupplierEntity, Long> {
}

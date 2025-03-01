package com.javiergutierrez.inventory_tracker_api.modules.categories.infrastructure.repositories;

import com.javiergutierrez.inventory_tracker_api.modules.categories.infrastructure.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJpaCategoryRepository extends JpaRepository<CategoryEntity, Long> {
}

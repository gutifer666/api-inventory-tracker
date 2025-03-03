package com.javiergutierrez.inventory_tracker_api.modules.suppliers.application.usecases;

import com.javiergutierrez.inventory_tracker_api.modules.suppliers.domain.Supplier;
import com.javiergutierrez.inventory_tracker_api.modules.suppliers.infrastructure.adapters.SupplierRepositoryAdapter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Transactional
@Component
public class CreateSupplierUseCase {

	private SupplierRepositoryAdapter supplierRepositoryAdapter;

	public Optional<Supplier> createSupplier(Supplier supplier) {
		log.info("Call to createSupplier {}", supplier);
		log.debug("Supplier to create: {}", supplier);

		Optional<Supplier> createdSupplier = supplierRepositoryAdapter.createSupplier(supplier);

		if (createdSupplier.isEmpty()) {
			throw new IllegalStateException("Failed to create supplier: " + supplier);
		}

		return createdSupplier;

	}

}

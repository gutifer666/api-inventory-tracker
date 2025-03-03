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
public class UpdateSupplierUseCase {

	private SupplierRepositoryAdapter supplierRepositoryAdapter;

	public Optional<Supplier> updateSupplier(Long id, Supplier supplier) {
		log.info("Call to updateSupplier with ID: {}.", id);
		log.debug(supplier.toString());

		Supplier existingSupplier = supplierRepositoryAdapter.findSupplierById(id)
				.orElseThrow(() -> new IllegalStateException("Failed to get supplier with ID: " + id));

		existingSupplier.setName(supplier.getName());

		log.debug("Supplier after update. {}", existingSupplier);

		return supplierRepositoryAdapter.updateSupplier(existingSupplier);

	}

}

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
public class DeleteSupplierUseCase {

	private SupplierRepositoryAdapter supplierRepositoryAdapter;

	public Optional<Supplier> deleteSupplier(long id) throws IllegalStateException {
		log.info("Call to deleteSupplier {}", id);
		log.debug("Supplier to delete: {}", id);

		Optional<Supplier> supplierOptional = supplierRepositoryAdapter.findSupplierById(id);

		if (supplierOptional.isEmpty()) {
			throw new IllegalStateException("Supplier with ID: " + id + " does not exist.");
		}

		return supplierOptional;

	}

}

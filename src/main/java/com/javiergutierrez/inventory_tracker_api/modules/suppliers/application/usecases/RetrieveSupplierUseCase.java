package com.javiergutierrez.inventory_tracker_api.modules.suppliers.application.usecases;

import com.javiergutierrez.inventory_tracker_api.modules.suppliers.domain.Supplier;
import com.javiergutierrez.inventory_tracker_api.modules.suppliers.infrastructure.adapters.SupplierRepositoryAdapter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Component
public class RetrieveSupplierUseCase {

	private SupplierRepositoryAdapter supplierRepositoryAdapter;

	public Optional<List<Supplier>> findAllSuppliers() {
		log.info("Call to findAllSuppliers.");

		Optional<List<Supplier>> supplierList = supplierRepositoryAdapter.findAllSuppliers();

		log.debug(supplierList.toString());

		return supplierList;
	}

	public Optional<Supplier> findSupplierById(Long id) {
		log.info("Call to findSupplierById.");
		return supplierRepositoryAdapter.findSupplierById(id);
	}
}

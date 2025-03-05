package com.javiergutierrez.inventory_tracker_api.modules.suppliers.application.services;

import com.javiergutierrez.inventory_tracker_api.modules.suppliers.application.usecases.CreateSupplierUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.suppliers.application.usecases.DeleteSupplierUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.suppliers.application.usecases.RetrieveSupplierUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.suppliers.application.usecases.UpdateSupplierUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.suppliers.domain.Supplier;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class SupplierService {
	private CreateSupplierUseCase createSupplierUseCase;
	private RetrieveSupplierUseCase retrieveSupplierUseCase;
	private UpdateSupplierUseCase updateSupplierUseCase;
	private DeleteSupplierUseCase deleteSupplierUseCase;

	public Optional<Supplier> createSupplier(Supplier supplier) {
		log.info("Call to createSupplier.");
		log.debug(supplier.toString());
		return createSupplierUseCase.createSupplier(supplier);
	}

	public Optional<Supplier> findSupplierById(Long id) {
		log.info("Call to findSupplierById with id {}.", id);
		return retrieveSupplierUseCase.findSupplierById(id);
	}

	public Optional<Supplier> updateSupplier(Long id, Supplier supplier) {
		log.info("Call to updateSupplier with id {}.", id);
		log.debug(supplier.toString());
		return updateSupplierUseCase.updateSupplier(id, supplier);
	}

	public Optional<Supplier> deleteSupplier(Long id) {
		log.info("Call to deleteSupplier with id {}.", id);
		return deleteSupplierUseCase.deleteSupplier(id);
	}

	public Optional<List<Supplier>> findAllSuppliers() {
		log.info("Call to findAllSuppliers.");
		return retrieveSupplierUseCase.findAllSuppliers();
	}
}

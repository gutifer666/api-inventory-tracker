package com.javiergutierrez.inventory_tracker_api.modules.suppliers.infrastructure.adapters;

import com.javiergutierrez.inventory_tracker_api.modules.suppliers.domain.Supplier;
import com.javiergutierrez.inventory_tracker_api.modules.suppliers.infrastructure.entities.SupplierEntity;
import com.javiergutierrez.inventory_tracker_api.modules.suppliers.infrastructure.mappers.SupplierMapper;
import com.javiergutierrez.inventory_tracker_api.modules.suppliers.infrastructure.repositories.IJpaSupplierRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Component
public class SupplierRepositoryAdapter {

	private final IJpaSupplierRepository iJpaSupplierRepository;
	private final SupplierMapper supplierMapper;

	public Optional<Supplier> createSupplier(Supplier supplier) {
		log.info("Call to createSupplier {}", supplier);
		log.debug("Supplier to create: {}", supplier);
		SupplierEntity supplierEntity = supplierMapper.toEntity(supplier);
		SupplierEntity createdSupplierEntity = iJpaSupplierRepository.save(supplierEntity);
		log.info("Created supplier with ID: {}", supplier.getId());
		log.debug("Supplier created: {}", createdSupplierEntity);
		return Optional.of(supplierMapper.toModel(createdSupplierEntity));
	}

	public Optional<List<Supplier>> findAllSuppliers() {
		log.info("Call to findAllSuppliers.");
		List<Supplier> supplierList = iJpaSupplierRepository.findAll().stream()
				.map(supplierMapper::toModel)
				.collect(Collectors.toList());
		log.info("Found {} suppliers.", supplierList.size());
		log.debug("Suppliers found: {}", supplierList);
		return Optional.of(supplierList);
	}

	public Optional<Supplier> findSupplierById(Long id) {
		log.info("Call to findSupplierById with ID: {}.", id);
		Optional<Supplier> supplier = iJpaSupplierRepository.findById(id)
				.map(supplierMapper::toModel);
		if (supplier.isPresent()) {
			log.info("Found supplier with ID: {}.", id);
		} else {
			log.error("No supplier found with ID: {}.", id);
		}
		return supplier;
	}

	public Optional<Supplier> updateSupplier(Supplier supplier) {
		log.info("Call to updateSupplier with ID: {}.", supplier.getId());
		SupplierEntity supplierEntity = supplierMapper.toEntity(supplier);
		SupplierEntity updatedSupplierEntity = iJpaSupplierRepository.save(supplierEntity);
		log.info("Updated supplier with ID: {}.", supplier.getId());
		return Optional.of(supplierMapper.toModel(updatedSupplierEntity));
	}

	public boolean deleteSupplier(Long id) {
		log.info("Call to deleteSupplier with ID: {}.", id);

		if (iJpaSupplierRepository.existsById(id)) {
			iJpaSupplierRepository.deleteById(id);
			log.info("Deleted supplier with ID: {}.", id);
			return true;
		} else {
			log.error("No supplier found with ID: {}.", id);
			return false;
		}
	}
}

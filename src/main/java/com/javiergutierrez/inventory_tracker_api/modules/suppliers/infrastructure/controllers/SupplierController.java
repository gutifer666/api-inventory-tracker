package com.javiergutierrez.inventory_tracker_api.modules.suppliers.infrastructure.controllers;

import com.javiergutierrez.inventory_tracker_api.modules.suppliers.application.services.SupplierService;
import com.javiergutierrez.inventory_tracker_api.modules.suppliers.domain.Supplier;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/suppliers")
@Slf4j
@Validated
@RestController
public class SupplierController {

	private final SupplierService supplierService;

	@PostMapping
	public ResponseEntity<Supplier> createSupplier(@Valid @RequestBody Supplier supplier) {
		log.info("Call to createSupplier {}", supplier);
		Supplier createdSupplier = supplierService.createSupplier(supplier).orElseThrow(() -> {
			log.error("Failed to create supplier {}", supplier);
			return new IllegalStateException("Failed to create supplier.");
		});
		log.info("Supplier created {}", createdSupplier);
		return new ResponseEntity<>(createdSupplier, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Supplier> findSupplierById(@PathVariable Long id) {
		log.info("Call to findSupplierById with ID: {}", id);
		Supplier supplier = supplierService.findSupplierById(id).orElseThrow(() -> {
			log.error("Failed to get supplier with id: {}", id);
			return new IllegalStateException("Failed to find supplier with ID: " + id);
		});
		log.info("Successfully retrieved supplier: {}.", supplier);
		return ResponseEntity.ok(supplier);
	}

	@GetMapping
	public ResponseEntity<List<Supplier>> findAllSuppliers() {
		log.info("Call to findAllSuppliers.");
		List<Supplier> supplierList = supplierService.findAllSuppliers().orElseThrow(() -> {
			log.error("Failed to find Suppliers.");
			return new IllegalStateException("Failed to find suppliers.");
		});
		log.info("Successfully retrieved suppliers: {}.", supplierList);
		return ResponseEntity.ok(supplierList);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Supplier> updateSupplier(@PathVariable Long id, @Valid @RequestBody Supplier supplier) {
		log.info("Call to updateSupplier with id {}.", id);
		Supplier updatedSupplier = supplierService.updateSupplier(id, supplier).orElseThrow(() -> {
			log.error("Failed to update supplier with id: {}", id);
			return new IllegalStateException("Failed to update supplier with ID: " + id);
		});
		log.info("Successfully updated supplier: {}.", updatedSupplier);
		return ResponseEntity.ok(updatedSupplier);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Supplier> deleteSupplier(@PathVariable Long id) {
		log.info("Call to deleteSupplier with id {}.", id);
		Supplier deletedSupplier = supplierService.deleteSupplier(id).orElseThrow(() -> {
			log.error("Failed to delete supplier with id: {}", id);
			return new IllegalStateException("Failed to delete supplier with ID: " + id);
		});
		log.info("Successfully deleted supplier: {}.", deletedSupplier);
		return ResponseEntity.ok(deletedSupplier);
	}
}

package com.javiergutierrez.inventory_tracker_api.modules.transactions.infrastructure.controllers;

import com.javiergutierrez.inventory_tracker_api.modules.transactions.application.services.TransactionService;
import com.javiergutierrez.inventory_tracker_api.modules.transactions.domain.Transaction;
import com.javiergutierrez.inventory_tracker_api.modules.transactions.infrastructure.dtos.TransactionDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
@Slf4j
@Validated
@RestController
public class TransactionController {

	private final TransactionService transactionService;

	@PostMapping
	public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
		log.info("Call to createTransaction {}", transactionDTO);

		Transaction createdTransaction = transactionService.createTransaction(transactionDTO).orElseThrow(() -> {
			log.error("Failed to create transactionDTO.");
			return new IllegalStateException("Failed to create transactionDTO.");
		});
		log.info("Transaction created: {}", createdTransaction);

		return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<Transaction>> findAllTransactions() {
		log.info("Call to findAllTransactions.");
		List<Transaction> transactionList = transactionService.findAllTransactions().orElseThrow(() -> {
			log.error("Failed to find transactions.");
			return new IllegalStateException("Failed to find transactions.");
		});
		log.info("Successfully retrieved transactions: {}.", transactionList);
		return ResponseEntity.ok(transactionList);
	}
}

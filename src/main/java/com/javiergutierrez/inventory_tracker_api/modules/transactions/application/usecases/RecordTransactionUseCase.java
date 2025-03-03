package com.javiergutierrez.inventory_tracker_api.modules.transactions.application.usecases;

import com.javiergutierrez.inventory_tracker_api.modules.transactions.domain.Transaction;
import com.javiergutierrez.inventory_tracker_api.modules.transactions.infrastructure.adapters.TransactionRepositoryAdapter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Transactional
@Component
public class RecordTransactionUseCase {

	private TransactionRepositoryAdapter transactionRepositoryAdapter;

	public Optional<Transaction> createTransaction(Transaction transaction) {
		log.info("Call to createUsers {}", transaction);
		log.debug("Users to create: {}", transaction);

		Optional<Transaction> createdTransaction = transactionRepositoryAdapter.createTransaction(transaction);
		if (createdTransaction.isEmpty()) {
			throw new IllegalStateException("Failed to create transaction: " + transaction);
		}
		return createdTransaction;
	}
}

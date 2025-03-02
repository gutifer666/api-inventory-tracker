package com.javiergutierrez.inventory_tracker_api.modules.transactions.infrastructure.adapters;

import com.javiergutierrez.inventory_tracker_api.modules.transactions.domain.Transaction;
import com.javiergutierrez.inventory_tracker_api.modules.transactions.infrastructure.entities.TransactionEntity;
import com.javiergutierrez.inventory_tracker_api.modules.transactions.infrastructure.mappers.TransactionMapper;
import com.javiergutierrez.inventory_tracker_api.modules.transactions.infrastructure.repositories.IJpaTransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Component
public class TransactionRepositoryAdapter {

	private final IJpaTransactionRepository iJpaTransactionRepository;
	private final TransactionMapper transactionMapper;

	public Optional<Transaction> createTransaction(Transaction transaction) {
		log.info("Call to createUsers {}", transaction);
		log.debug("Users to create: {}", transaction);

		TransactionEntity transactionEntity = transactionMapper.toEntity(transaction);
		TransactionEntity createdTransactionEntity = iJpaTransactionRepository.save(transactionEntity);

		log.info("Created transaction with ID: {}", transaction.getId());
		log.debug("Transaction created: {}", createdTransactionEntity);

		return Optional.of(transactionMapper.toModel(createdTransactionEntity));
	}

	public Optional<List<Transaction>> findAllTransactions() {
		log.info("Call to findAllTransactions.");
		List<Transaction> transactionList = iJpaTransactionRepository.findAll().stream()
				.map(transactionMapper::toModel)
				.collect(Collectors.toList());
		log.info("Found {} transactions.", transactionList.size());
		log.debug("Transactions found: {}", transactionList);
		return Optional.of(transactionList);
	}
}

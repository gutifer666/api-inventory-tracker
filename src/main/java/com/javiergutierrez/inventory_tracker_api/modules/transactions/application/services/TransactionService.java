package com.javiergutierrez.inventory_tracker_api.modules.transactions.application.services;

import com.javiergutierrez.inventory_tracker_api.modules.products.application.usecases.RetrieveProductUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.products.application.usecases.UpdateProductUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.products.domain.Product;
import com.javiergutierrez.inventory_tracker_api.modules.transactions.application.usecases.RecordTransactionUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.transactions.application.usecases.RetrieveTransactionUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.transactions.domain.Transaction;
import com.javiergutierrez.inventory_tracker_api.modules.transactions.infrastructure.dtos.TransactionDTO;
import com.javiergutierrez.inventory_tracker_api.modules.users.application.usecases.RetrieveUserUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.users.application.usecases.UpdateUserUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.users.domain.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class TransactionService {

	private RecordTransactionUseCase recordTransactionUseCase;
	private RetrieveTransactionUseCase retrieveTransactionUseCase;
	private RetrieveUserUseCase retrieveUserUseCase;
	private UpdateUserUseCase updateUserUseCase;
	private UpdateProductUseCase updateProductUseCase;
	private RetrieveProductUseCase retrieveProductUseCase;

	public Optional<Transaction> createTransaction(TransactionDTO transactionDTO) {
		log.info("Call to createTransaction.");
		log.info(transactionDTO.toString());

		Optional<User> employee = retrieveUserUseCase.findUserById(transactionDTO.getUserId());
		Optional<Product> product = retrieveProductUseCase.findProductById(transactionDTO.getProductId());

		if (employee.isEmpty() || product.isEmpty()) {
			log.error("Failed to create transaction.");
			return Optional.empty();
		}

		Transaction transaction = new Transaction(
				employee.get(),
				transactionDTO.getClientName(),
				product.get(),
				transactionDTO.getQuantity()
		);

		updateProductStock(product.get(), transaction.getQuantity());
		updateEmployeePoints(employee.get(), transaction.getQuantity(), transaction.getTransactionPrice());

		return recordTransactionUseCase.createTransaction(transaction);

	}

	public Optional<List<Transaction>> findAllTransactions() {
		log.info("Call to findAllTransactions.");
		return retrieveTransactionUseCase.findAllTransactions();
	}

	private void updateProductStock(Product product, Integer quantity) {
		product.subtractQuantity(quantity);
		updateProductUseCase.updateProduct(product.getId(), product);
	}

	private void updateEmployeePoints(User employee, Integer quantity, Double amount) {
		employee.addSale(quantity);
		employee.addEarnings(amount);
		updateUserUseCase.updateUser(employee.getId(), employee);
	}
}

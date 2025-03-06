package com.javiergutierrez.inventory_tracker_api.modules.transactions.application.usecases;

import com.javiergutierrez.inventory_tracker_api.modules.transactions.domain.Transaction;
import com.javiergutierrez.inventory_tracker_api.modules.transactions.infrastructure.adapters.TransactionRepositoryAdapter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class RecordTransactionUseCaseTest {

	@Mock
	private TransactionRepositoryAdapter transactionRepositoryAdapter;
	@InjectMocks
	private RecordTransactionUseCase recordTransactionUseCase;

	private static AutoCloseable closeable;

	private Transaction transaction;
	private Transaction expectedTransaction;

	@BeforeEach
	void beforeEach() {
		log.info("Start to prepare beforeEach");

		closeable = MockitoAnnotations.openMocks(this);

		transaction = Transaction.builder()
				.id(1L)
				.employeeName("employeeName")
				.clientName("clientName")
				.productCode("productCode")
				.productName("productName")
				.quantity(1)
				.transactionPrice(1.0)
				.createdAt(null)
				.build();

		expectedTransaction = transaction.clone();

		log.info("End to BeforeEach.");
	}

	@AfterEach
	void afterEach() throws Exception{
		closeable.close();
	}

	@Test
	void  when_record_transaction_should_be_a_new_transaction() {
		String method = "when_record_transaction_should_be_a_new_transaction";
		log.info("Start {}.", method);

		when(transactionRepositoryAdapter.createTransaction(transaction)).thenReturn(Optional.of(transaction));


		Optional<Transaction> resultOptional = recordTransactionUseCase.createTransaction(transaction);
		assertTrue(resultOptional.isPresent(), "Result should be present.");
		Transaction result = resultOptional.get();

		verify(transactionRepositoryAdapter).createTransaction(transaction);

		assertTransaction(result);
	}

	@Test
	void when_record_transaction_fails_should_throw_exception() {
		String method = "when_create_transaction_fails_should_throw_exception";
		log.info("Start {}.", method);

		when(transactionRepositoryAdapter.createTransaction(transaction)).thenReturn(Optional.empty());

		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> recordTransactionUseCase.createTransaction(transaction));

		verify(transactionRepositoryAdapter).createTransaction(transaction);

		assertEquals("Failed to create transaction: " + transaction, thrown.getMessage(), "Message should be the same.");

		log.info("End {}.", method);
	}

	private void assertTransaction(Transaction transaction) {

		assertNotNull(transaction, "Result should not be null.");
		assertEquals(expectedTransaction.getId(), transaction.getId(), "Id should be the same.");
		assertEquals(expectedTransaction.getEmployeeName(), transaction.getEmployeeName(), "Employee name should be the same.");
		assertEquals(expectedTransaction.getClientName(), transaction.getClientName(), "Client name should be the same.");
		assertEquals(expectedTransaction.getProductCode(), transaction.getProductCode(), "Product code should be the same.");
		assertEquals(expectedTransaction.getProductName(), transaction.getProductName(), "Product name should be the same.");
		assertEquals(expectedTransaction.getQuantity(), transaction.getQuantity(), "Quantity should be the same.");
		assertEquals(expectedTransaction.getTransactionPrice(), transaction.getTransactionPrice(), "Transaction price should be the same.");
		assertEquals(expectedTransaction.getCreatedAt(), transaction.getCreatedAt(), "Created at should be the same.");

	}

}

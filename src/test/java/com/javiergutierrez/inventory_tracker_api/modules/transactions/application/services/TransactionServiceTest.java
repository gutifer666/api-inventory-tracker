package com.javiergutierrez.inventory_tracker_api.modules.transactions.application.services;

import com.javiergutierrez.inventory_tracker_api.modules.categories.domain.Category;
import com.javiergutierrez.inventory_tracker_api.modules.products.application.usecases.RetrieveProductUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.products.application.usecases.UpdateProductUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.products.domain.Product;
import com.javiergutierrez.inventory_tracker_api.modules.suppliers.domain.Supplier;
import com.javiergutierrez.inventory_tracker_api.modules.transactions.application.usecases.RecordTransactionUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.transactions.domain.Transaction;
import com.javiergutierrez.inventory_tracker_api.modules.transactions.infrastructure.dtos.TransactionDTO;
import com.javiergutierrez.inventory_tracker_api.modules.users.application.usecases.RetrieveUserUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.users.application.usecases.UpdateUserUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.users.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
class TransactionServiceTest {
	@Mock
	private RecordTransactionUseCase recordTransactionUseCase;
	@Mock
	private RetrieveUserUseCase retrieveUserUseCase;
	@Mock
	private UpdateUserUseCase updateUserUseCase;
	@Mock
	private RetrieveProductUseCase retrieveProductUseCase;
	@Mock
	private UpdateProductUseCase updateProductUseCase;

	@InjectMocks
	private TransactionService transactionService;

	private Transaction transaction;
	private Transaction expectedTransaction;
	private TransactionDTO transactionDTO;

	private User employee;

	private Product product;

	private final Category category = Category.builder()
			.id(1L)
			.name("name")
			.description("description")
			.build();

	private final Supplier supplier = Supplier.builder()
			.id(1L)
			.name("name")
			.build();

	private static AutoCloseable closeable;

	private final Date date = new Date();

	@BeforeAll
	static void beforeAll() {
		log.info("\tTransactionServiceTest: Initializing tests");
	}

	@AfterAll
	static void afterAll() {
		log.info("\tTransactionServiceTest: Finishing tests");
	}

	@BeforeEach
	void beforeEach() {
		log.info("Start to prepare beforeEach");

		closeable = MockitoAnnotations.openMocks(this);

		employee = User.builder()
				.id(1L)
				.username("username")
				.password("password")
				.roles("roles")
				.fullName("fullName")
				.sales(0)
				.earnings(0.0)
				.build();

		product = Product.builder()
				.id(1L)
				.code("code")
				.name("name")
				.description("description")
				.category(category)
				.supplier(supplier)
				.costPrice(1.0)
				.retailPrice(1.0)
				.quantity(1)
				.build();

		transaction = Transaction.builder()
				.id(null)
				.employeeName("fullName")
				.clientName("clientName")
				.productCode("productCode")
				.productName("productName")
				.quantity(1)
				.transactionPrice(1.0)
				.createdAt(date)
				.build();

		transactionDTO = TransactionDTO.builder()
				.userId(1L)
				.clientName("clientName")
				.productId(1L)
				.quantity(1)
				.build();

		expectedTransaction = transaction.clone();

		log.info("End to beforeEach");
	}

	@AfterEach
	void afterEach() throws Exception{
		closeable.close();
	}

	@Test
	void when_call_record_transaction_should_be_a_new_transaction() {
		String method = "when_call_record_transaction_should_be_a_new_transaction";
		log.info("Start {}", method);

		Product updatedProduct = product.clone();
		updatedProduct.subtractQuantity(transaction.getQuantity());

		User updatedEmployee = employee.clone();
		updatedEmployee.addSale(transaction.getQuantity());
		updatedEmployee.addEarnings(transaction.getTransactionPrice());

		when(retrieveUserUseCase.findUserById(1L)).thenReturn(Optional.of(employee));
		when(retrieveProductUseCase.findProductById(1L)).thenReturn(Optional.of(product));
		when(updateProductUseCase.updateProduct(product.getId(), updatedProduct)).thenReturn(Optional.of(updatedProduct));
		when(updateUserUseCase.updateUser(employee.getId(), updatedEmployee)).thenReturn(Optional.of(updatedEmployee));

		when(recordTransactionUseCase.createTransaction(any(Transaction.class)))
				.thenReturn(Optional.of(transaction));

		Optional<Transaction> resultOptional = transactionService.createTransaction(transactionDTO);

		assertTrue(resultOptional.isPresent(), "Result should be present");
		Transaction result = resultOptional.get();

		verify(retrieveUserUseCase).findUserById(1L);
		verify(retrieveProductUseCase).findProductById(1L);
		//verify(recordTransactionUseCase).createTransaction(transaction);

		assertTransaction(result);

		log.info("End {}", method);
	}

	private void assertTransaction(Transaction result) {
		assertNotNull(result, "Result should not be null");
		assertEquals(expectedTransaction.getId(), result.getId(), "ID should match");
		assertEquals(expectedTransaction.getEmployeeName(), result.getEmployeeName(), "Employee name should match");
		assertEquals(expectedTransaction.getClientName(), result.getClientName(), "Client name should match");
		assertEquals(expectedTransaction.getProductCode(), result.getProductCode(), "Product code should match");
		assertEquals(expectedTransaction.getProductName(), result.getProductName(), "Product name should match");
		assertEquals(expectedTransaction.getQuantity(), result.getQuantity(), "Quantity should match");
		assertEquals(expectedTransaction.getTransactionPrice(), result.getTransactionPrice(), "Transaction price should match");
		assertEquals(expectedTransaction.getCreatedAt(), result.getCreatedAt(), "Created at should match");
	}
}
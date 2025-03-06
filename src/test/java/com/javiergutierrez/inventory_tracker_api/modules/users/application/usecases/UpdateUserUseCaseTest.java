package com.javiergutierrez.inventory_tracker_api.modules.users.application.usecases;

import com.javiergutierrez.inventory_tracker_api.modules.users.domain.User;
import com.javiergutierrez.inventory_tracker_api.modules.users.infrastructure.adapters.UserRepositoryAdapter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
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
public class UpdateUserUseCaseTest {

	@Mock
	private UserRepositoryAdapter userRepositoryAdapter;
	@InjectMocks
	private UpdateUserUseCase updateUserUseCase;

	private static AutoCloseable closable;
	private User user;
	private User expectedUser;

	@BeforeEach
	void beforeEach() {
		log.info("Start to prepare beforeEach");

		closable = MockitoAnnotations.openMocks(this);

		user = User.builder()
				.id(1L)
				.username("username")
				.password("password")
				.roles("roles")
				.fullName("fullName")
				.sales(0)
				.earnings(0.0)
				.build();

		expectedUser = user.clone();
	}

	@AfterAll
	static void afterAll() throws Exception {
		log.info("End to tests");
		closable.close();
		log.info("End to AfterAll");
		log.info("\tIUpdateUserUseCaseImplTest: Finishing tests");
	}

	@Test
	void when_update_user_should_be_an_updated_user() {
		String method = "when_update_user_should_be_an_updated_user";
		log.info("Start {}.", method);

		when(userRepositoryAdapter.findUserById(1L)).thenReturn(Optional.of(user));
		when(userRepositoryAdapter.updateUser(user)).thenReturn(Optional.of(user));

		Optional<User> resultOptional = updateUserUseCase.updateUser(1L, user);

		verify(userRepositoryAdapter).findUserById(1L);
		verify(userRepositoryAdapter).updateUser(user);

		assertTrue(resultOptional.isPresent(), "Result should be present.");
		assertUser(resultOptional.get());
	}

	@Test
	void when_update_user_should_be_an_exception_when_user_not_found() {
		String method = "when_update_user_should_be_an_exception_when_user_not_found";
		log.info("Start {}.", method);

		when(userRepositoryAdapter.findUserById(1L)).thenReturn(Optional.empty());

		IllegalStateException thrownException = assertThrows(IllegalStateException.class,
				() -> updateUserUseCase.updateUser(1L, user),
				"Expected updateUser() to throw, but it didn't");

		verify(userRepositoryAdapter).findUserById(1L);

		assertTrue(thrownException.getMessage().contains("Failed to get user with ID: 1"));

		log.info("End {}.", method);
	}

	private void assertUser(User user) {

		assertNotNull(user, "Result should not be null.");
		assertEquals(expectedUser.getId(), user.getId(), "Id should be the same.");
		assertEquals(expectedUser.getUsername(), user.getUsername(), "Username should be the same.");
		assertEquals(expectedUser.getPassword(), user.getPassword(), "Password should be the same.");
		assertEquals(expectedUser.getRoles(), user.getRoles(), "Roles should be the same.");
		assertEquals(expectedUser.getFullName(), user.getFullName(), "Full name should be the same.");
		assertEquals(expectedUser.getSales(), user.getSales(), "Sales should be the same.");
		assertEquals(expectedUser.getEarnings(), user.getEarnings(), "Earnings should be the same.");
	}
}

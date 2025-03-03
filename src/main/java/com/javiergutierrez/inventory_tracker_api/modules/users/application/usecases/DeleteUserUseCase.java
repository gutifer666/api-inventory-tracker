package com.javiergutierrez.inventory_tracker_api.modules.users.application.usecases;

import com.javiergutierrez.inventory_tracker_api.modules.users.domain.User;
import com.javiergutierrez.inventory_tracker_api.modules.users.infrastructure.adapters.UserRepositoryAdapter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Transactional
@Component
public class DeleteUserUseCase {

	private UserRepositoryAdapter userRepositoryAdapter;

	public Optional<User> deleteUser(long usersId) {
		log.info("Call to deleteUser {}", usersId);
		boolean isDeleted = userRepositoryAdapter.deleteUser(usersId);
		if (!isDeleted) {
			throw new IllegalStateException("Failed to delete user with ID: " + usersId);
		}
		return Optional.empty();
	}

}

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
public class CreateUserUseCase {

	private UserRepositoryAdapter userRepositoryAdapter;

	public Optional<User> createUser(User user) {
		log.info("Call to createUser {}", user);
		log.debug("User to create: {}", user);
		Optional<User> createdUser = userRepositoryAdapter.createUser(user);
		if (createdUser.isEmpty()) {
			throw new IllegalStateException("Failed to create user: " + user);
		}
		return createdUser;
	}

}

package com.javiergutierrez.inventory_tracker_api.modules.users.application.services;


import com.javiergutierrez.inventory_tracker_api.modules.users.application.usecases.CreateUserUseCase;
import com.javiergutierrez.inventory_tracker_api.modules.users.application.usecases.DeleteUserUseCase;
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
public class UserService {
	private CreateUserUseCase createUserUseCase;
	private RetrieveUserUseCase retrieveUserUseCase;
	private UpdateUserUseCase updateUserUseCase;
	private DeleteUserUseCase deleteUserUseCase;

	public Optional<User> createUser(User user) {
		log.info("Call to createUser.");
		log.debug(user.toString());
		return createUserUseCase.createUser(user);
	}

	public Optional<User> findUserById(Long id) {
		log.info("Call to findUserById with id {}.", id);
		return retrieveUserUseCase.findUserById(id);
	}

	public Optional<User> updateUser(Long id, User user) {
		log.info("Call to updateUser with id {}.", id);
		log.debug(user.toString());
		return updateUserUseCase.updateUser(id, user);
	}

	public Optional<User> deleteUser(Long id) {
		log.info("Call to deleteUser with id {}.", id);
		return deleteUserUseCase.deleteUser(id);
	}

	public Optional<List<User>> findAllUsers() {
		log.info("Call to findAllUsers.");
		return retrieveUserUseCase.findAllUser();
	}
}

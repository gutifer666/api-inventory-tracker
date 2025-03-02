package com.javiergutierrez.inventory_tracker_api.modules.users.infrastructure.adapters;


import com.javiergutierrez.inventory_tracker_api.modules.users.domain.User;
import com.javiergutierrez.inventory_tracker_api.modules.users.infrastructure.entities.UserEntity;
import com.javiergutierrez.inventory_tracker_api.modules.users.infrastructure.mappers.UserMapper;
import com.javiergutierrez.inventory_tracker_api.modules.users.infrastructure.repositories.IJpaUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Component
public class UserRepositoryAdapter {

	private final IJpaUserRepository iJpaUserRepository;
	private final UserMapper userMapper;

	public Optional<User> createUser(User users) {
		log.info("Call to createUser {}", users);
		log.debug("Users to create: {}", users);
		UserEntity usersEntity = userMapper.toEntity(users);
		UserEntity createdUsersEntity = iJpaUserRepository.save(usersEntity);
		log.info("Created users with ID: {}", users.getId());
		log.debug("Users created: {}", createdUsersEntity);
		return Optional.of(userMapper.toModel(createdUsersEntity));
	}

	public Optional<List<User>> findAllUsers() {
		log.info("Call to findAllUsers.");
		List<User> userList = iJpaUserRepository.findAll().stream()
				.map(userMapper::toModel)
				.collect(Collectors.toList());
		log.info("Found {} users.", userList.size());
		log.debug("Users found: {}", userList);
		return Optional.of(userList);
	}

	public Optional<User> findUserById(Long id) {
		log.info("Call to findUserById with ID: {}.", id);
		Optional<User> user = iJpaUserRepository.findById(id)
				.map(userMapper::toModel);
		if (user.isPresent()) {
			log.info("Found user with ID: {}.", id);
		} else {
			log.error("No user found with ID: {}.", id);
		}
		return user;
	}

	public Optional<User> updateUser(User user) {
		log.info("Call to updateUser with ID: {}.", user.getId());
		UserEntity userEntity = userMapper.toEntity(user);
		UserEntity updatedUserEntity = iJpaUserRepository.save(userEntity);
		log.info("Updated user with ID: {}.", user.getId());
		return Optional.of(userMapper.toModel(updatedUserEntity));
	}

	public boolean deleteUser(Long id) {
		log.info("Call to deleteUser with ID: {}.", id);
		if (iJpaUserRepository.existsById(id)) {
			iJpaUserRepository.deleteById(id);
			log.info("Deleted user with ID: {}.", id);
			return true;
		} else {
			log.error("No user found with ID: {}.", id);
			return false;
		}
	}
}

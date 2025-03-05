package com.javiergutierrez.inventory_tracker_api.modules.users.infrastructure.controllers;

import com.javiergutierrez.inventory_tracker_api.modules.users.application.services.UserService;
import com.javiergutierrez.inventory_tracker_api.modules.users.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
@Validated
@RestController
public class UserController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		log.info("Call to createUser {}", user);
		User createdUser = userService.createUser(user).orElseThrow(() -> {
			log.error("Failed to create user {}", user);
			return new IllegalStateException("Failed to create user.");
		});
		log.info("User created {}", createdUser);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> findUserById(@PathVariable Long id) {
		log.info("Call to findUserById with ID: {}", id);
		User user = userService.findUserById(id).orElseThrow(() -> {
			log.error("Failed to get user with id: {}", id);
			return new IllegalStateException("Failed to find user with ID: " + id);
		});
		log.info("Successfully retrieved user: {}.", user);
		return ResponseEntity.ok(user);
	}

	@GetMapping
	public ResponseEntity<List<User>> findAllUsers() {
		log.info("Call to findAllUsers.");
		List<User> userList = userService.findAllUsers().orElseThrow(() -> {
			log.error("Failed to find users.");
			return new IllegalStateException("Failed to find users.");
		});
		log.info("Successfully retrieved users: {}.", userList);
		return ResponseEntity.ok(userList);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
		log.info("Call to updateUser with id {}.", id);
		User updatedUser = userService.updateUser(id, user).orElseThrow(() -> {
			log.error("Failed to update user with id: {}", id);
			return new IllegalStateException("Failed to update user with ID: " + id);
		});
		log.info("Successfully updated user: {}.", updatedUser);
		return ResponseEntity.ok(updatedUser);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable Long id) {
		log.info("Call to deleteUser with id {}.", id);
		User deletedUser = userService.deleteUser(id).orElseThrow(() -> {
			log.error("Failed to delete user with id: {}", id);
			return new IllegalStateException("Failed to delete users with ID: " + id);
		});
		log.info("Successfully deleted user: {}.", deletedUser);
		return ResponseEntity.ok(deletedUser);
	}
}

package com.javiergutierrez.inventory_tracker_api.modules.users.application.services;

import com.javiergutierrez.inventory_tracker_api.modules.users.domain.MyUserDetails;
import com.javiergutierrez.inventory_tracker_api.modules.users.infrastructure.entities.UserEntity;
import com.javiergutierrez.inventory_tracker_api.modules.users.infrastructure.repositories.IJpaUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	IJpaUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<UserEntity> userEntity = userRepository.findByUsername(username);

		log.info("User found: {}", userEntity);

		userEntity.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));

		return userEntity.map(MyUserDetails::new).get();

	}
}

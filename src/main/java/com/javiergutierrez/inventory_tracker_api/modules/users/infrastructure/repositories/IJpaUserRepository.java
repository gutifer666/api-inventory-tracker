package com.javiergutierrez.inventory_tracker_api.modules.users.infrastructure.repositories;

import com.javiergutierrez.inventory_tracker_api.modules.users.infrastructure.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IJpaUserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUsername(String username);
}

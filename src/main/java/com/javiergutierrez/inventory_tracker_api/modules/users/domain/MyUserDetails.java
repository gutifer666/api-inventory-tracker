package com.javiergutierrez.inventory_tracker_api.modules.users.domain;

import com.javiergutierrez.inventory_tracker_api.modules.users.infrastructure.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {

	private final String username;
	private final String password;
	private final List<GrantedAuthority> authorities;


	public MyUserDetails(UserEntity userEntity) {

		this.username = userEntity.getUsername();
		this.password = userEntity.getPassword();
		this.authorities = Arrays.stream(userEntity.getRoles().split(", "))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}

package com.chu.readinglist.po;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
@Entity
public class Reader implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	private String username;
	private String fullname;
	private String password;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 授予READER权限
		return Arrays.asList(new SimpleGrantedAuthority("READER"));
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

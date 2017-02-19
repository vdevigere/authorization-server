package com.viddu.oauth.server.provider;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class InMemoryUserDetailsService implements UserDetailsService {
	private final Map<String, UserDetails> userStore;

	public InMemoryUserDetailsService(Map<String, UserDetails> userStore) {
		this.userStore = userStore;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (userStore.containsKey(username)) {
			return userStore.get(username);
		}
		throw new UsernameNotFoundException("Username [" + username + "] not found");
	}

}

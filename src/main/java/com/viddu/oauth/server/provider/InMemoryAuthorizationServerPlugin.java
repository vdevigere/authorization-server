package com.viddu.oauth.server.provider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.viddu.oauth.server.api.AuthorizationServerPlugin;

@Component
public class InMemoryAuthorizationServerPlugin implements AuthorizationServerPlugin {

	@Override
	public boolean supports(String delimiter) {
		return true;
	}

	@Override
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());

		return authenticationProvider;
	}

	private Map<String, UserDetails> userStore() {
		Map<String, UserDetails> userStore = new ConcurrentHashMap<>();
		userStore.put("vdevigere",
				new User("vdevigere", "Pa$$w0rd123", AuthorityUtils.createAuthorityList("ROLE_USER")));
		userStore.put("vivaan", new User("vivaan", "hulahoop79", AuthorityUtils.createAuthorityList("ROLE_USER")));
		return userStore;
	}

	@Override
	public UserDetailsService userDetailsService() {
		return new InMemoryUserDetailsService(userStore());
	}

}

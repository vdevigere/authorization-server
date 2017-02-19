package com.viddu.oauth.server.api;

import org.springframework.plugin.core.Plugin;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthorizationServerPlugin extends Plugin<String>{
	public AuthenticationProvider authenticationProvider();
	
	public UserDetailsService userDetailsService();
	
}

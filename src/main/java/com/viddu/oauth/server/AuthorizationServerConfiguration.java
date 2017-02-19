package com.viddu.oauth.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viddu.oauth.server.api.AuthorizationServerPlugin;
import com.viddu.oauth.server.customizations.UserPrincipalTokenEnhancer;
import com.viddu.oauth.server.customizations.UserPrincipalUserAuthenticationConverter;

/**
 * The main configuration class for the authorization server.
 * 
 * @author vdevigere
 *
 */
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	private final AuthenticationManager authenticationManager;

	private final PluginRegistry<AuthorizationServerPlugin, String> pluginRegistry;

	private final ObjectMapper objectMapper;

	@Autowired
	public AuthorizationServerConfiguration(AuthenticationManager authenticationManager,
			PluginRegistry<AuthorizationServerPlugin, String> pluginRegistry, ObjectMapper objectMapper) {
		this.authenticationManager = authenticationManager;
		this.pluginRegistry = pluginRegistry;
		this.objectMapper = objectMapper;
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// By default the checkToken access point is secured using deny_all,
		// allow only for authenticated users to access.
		security.checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// Configure a few different types of clients using the in-memory
		// implementation.
		//@formatter:off
		 	clients.inMemory()
		        .withClient("my-trusted-client")
		            .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
		            .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
		            .scopes("read", "write", "trust")
		            .resourceIds("oauth2-resource")
		            .accessTokenValiditySeconds(600)
		    .and()
		        .withClient("my-client-with-registered-redirect")
		            .authorizedGrantTypes("authorization_code")
		            .authorities("ROLE_CLIENT")
		            .scopes("read", "trust")
		            .resourceIds("oauth2-resource")
		            .redirectUris("http://anywhere?key=value")
		    .and()
		        .withClient("my-client-with-secret")
		            .authorizedGrantTypes("client_credentials", "password", "refresh_token")
		            .authorities("ROLE_CLIENT")
		            .scopes("read")
		            .resourceIds("oauth2-resource")
		            .secret("secret");
//@formatter:on	
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager);

		endpoints.userDetailsService(pluginRegistry.getPluginFor("DEFAULT").userDetailsService());

		endpoints.tokenEnhancer(new UserPrincipalTokenEnhancer());

		DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
		UserPrincipalUserAuthenticationConverter userTokenConverter = new UserPrincipalUserAuthenticationConverter(
				objectMapper);
		accessTokenConverter.setUserTokenConverter(userTokenConverter);
		endpoints.accessTokenConverter(accessTokenConverter);
	}

}

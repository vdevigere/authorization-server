package com.viddu.oauth.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.viddu.oauth.server.api.AuthorizationServerPlugin;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final PluginRegistry<AuthorizationServerPlugin, String> pluginRegistry;

	@Autowired
	public WebSecurityConfig(PluginRegistry<AuthorizationServerPlugin, String> pluginRegistry) {
		this.pluginRegistry = pluginRegistry;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		pluginRegistry.getPlugins().stream().forEach(plugin -> {
			auth.authenticationProvider(plugin.authenticationProvider());
		});
	}
}

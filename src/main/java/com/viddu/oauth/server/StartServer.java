package com.viddu.oauth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.plugin.core.config.EnablePluginRegistries;

import com.viddu.oauth.server.api.AuthorizationServerPlugin;

/**
 * Start the main authorization server
 * 
 * @author vdevigere
 *
 */
@SpringBootApplication
@EnablePluginRegistries(AuthorizationServerPlugin.class)
public class StartServer {

	public static void main(String[] args) {
		SpringApplication.run(StartServer.class, args);
	}
}

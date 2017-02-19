package com.viddu.oauth.server.customizations;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Converts the "principal" field value to extract the authentication.
 * 
 * @author vdevigere
 *
 */
public class UserPrincipalUserAuthenticationConverter implements UserAuthenticationConverter {

	private static final String PRINICIPAL = "prinicipal";

	private final ObjectMapper objectMapper;

	public UserPrincipalUserAuthenticationConverter(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	Logger logger = LoggerFactory.getLogger(UserPrincipalUserAuthenticationConverter.class);

	/**
	 * Called by the check_token endpoint to build a response from the
	 * authentication.
	 */
	@Override
	public Map<String, ?> convertUserAuthentication(Authentication userAuthentication) {
		Map<String, Object> additionalInformation = new LinkedHashMap<>();
		additionalInformation.put(PRINICIPAL, userAuthentication.getPrincipal());
		return additionalInformation;
	}

	/**
	 * Used by the oauth2 client to extract an authentication from the
	 * check_token endpoint response.
	 */
	@Override
	public Authentication extractAuthentication(Map<String, ?> map) {
		logger.debug("Map:{}", map);
		UserDetails userDetails = objectMapper.convertValue(map.get(PRINICIPAL), UserDetails.class);
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}

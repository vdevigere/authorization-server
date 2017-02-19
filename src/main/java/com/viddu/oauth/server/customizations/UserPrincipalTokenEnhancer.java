package com.viddu.oauth.server.customizations;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 * Enhances the token with a principal field containing the full user principal.
 * @author vdevigere
 *
 */
public class UserPrincipalTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> additionalInformation = new LinkedHashMap<>();
		
		additionalInformation.put("principal", authentication.getPrincipal());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation );
		return accessToken;
	}

}

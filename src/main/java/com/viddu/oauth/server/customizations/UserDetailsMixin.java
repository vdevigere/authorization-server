package com.viddu.oauth.server.customizations;

import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Ignore the password field, we do not want password to be serialized on the response.
 * @author vdevigere
 *
 */
@JsonIgnoreProperties({"password"})
public interface UserDetailsMixin extends UserDetails {
}

package com.customauthentication.custom_authentication_in_spring_security.config.security.managers;

import com.customauthentication.custom_authentication_in_spring_security.config.security.providers.CustomAuthenticationProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    private final CustomAuthenticationProvider provider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(provider.supports(authentication.getClass())){
             return provider.authenticate(authentication);
        }
        throw new BadCredentialsException("Wrong credentials");

        //if more providers would be there then all those would be checked
        //whether if any of them supports this authentication object. But
        //since we have only one provider hence we are throwing
        //an exception if that one is also not supporting the authentication type
    }
}

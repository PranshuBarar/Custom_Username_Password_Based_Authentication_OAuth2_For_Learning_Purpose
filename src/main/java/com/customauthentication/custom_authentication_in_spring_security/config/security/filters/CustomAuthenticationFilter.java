package com.customauthentication.custom_authentication_in_spring_security.config.security.filters;

import com.customauthentication.custom_authentication_in_spring_security.CustomAuthenticationInSpringSecurityApplication;
import com.customauthentication.custom_authentication_in_spring_security.config.security.authentication.CustomAuthentication;
import com.customauthentication.custom_authentication_in_spring_security.config.security.managers.CustomAuthenticationManager;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final CustomAuthenticationManager customAuthenticationManager;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //here we will have filter implementation

        // we will create a raw authentication object whicih is not yet authenticated
        // delegate this authentication object to the custom authentication manager we created
        // get back the authentication object from the manager
        // if the object got authenticated by the custom authentication manager (using any of the authentication provider) then send the request to further filters in the chain

        String key = String.valueOf(request.getHeader("key"));
        CustomAuthentication customAuthentication = new CustomAuthentication(false, key);

        Authentication authenticationReturnedFromCustomAuthManager = customAuthenticationManager.authenticate(customAuthentication);
        //this above authentication object is the object

        if(authenticationReturnedFromCustomAuthManager.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticationReturnedFromCustomAuthManager);
            filterChain.doFilter(request, response);
        }

    }
}

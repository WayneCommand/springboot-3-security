package com.example.springboot3security.security.filter;

import com.example.springboot3security.security.SecurityConst;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 内部调用过滤器（直接放行，并给特定标记）
 */
public class InternalAuthenticationFilter extends OncePerRequestFilter {



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Get authorization header and validate
        final String header = request.getHeader(SecurityConst.AUTH_HEADER_NAME);

        // 如果是内部调用 就简单的放进API的Token
        if (SecurityConst.INTERNAL_FLAG.equals(header)) {
            System.err.println("internal call.");
            UserDetails userDetails = User.builder()
                    .username(SecurityConst.API_FLAG)
                    .password(SecurityConst.API_FLAG)
                    .authorities(SecurityConst.API_FLAG)
                    .build();

            // Get user identity and set it on the spring security context
            PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            // Set Detail
            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            // Set authentication to context
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);

    }


}

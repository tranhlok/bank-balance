package dev.codescreen.security;
import java.io.IOException;

import dev.codescreen.model.Account;
import dev.codescreen.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    /*
     *  Filter class that handles JWT authentication and authorization for each request.
     * */

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String account = null;
        String authToken = null;

        if (header != null && header.startsWith("Bearer ")) {
            authToken = header.substring(7);
            try {
                account = jwtTokenUtil.getAccountFromToken(authToken);
            } catch (Exception e) {
                // Handle token parsing or validation errors here
                System.err.println("JWT Token validation error: " + e.getMessage());
            }
        }

        if (account != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtTokenUtil.validateToken(authToken)) {
                // Assuming that the token is valid if there are no exceptions and the token is not expired
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        account, null, java.util.Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
                authentication.setDetails(new org.springframework.security.web.authentication.WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}

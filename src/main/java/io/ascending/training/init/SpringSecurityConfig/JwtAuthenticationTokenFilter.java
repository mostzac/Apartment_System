package io.ascending.training.init.SpringSecurityConfig;

import io.ascending.training.postgres.repository.interfaces.UserDAO;
import io.ascending.training.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * get JWT token from header
 * validate JWT
 * parse username from validated JWT
 * load data from users table,then build an authentication object
 * set the authentication object to Security Context
 **/

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter{

    @Qualifier("myUserDetailsService")
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private Logger logger;
    @Autowired
    private UserDAO userDAO;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        logger.info("This is JwtAuthenticationTokenFilter");
        int statusCode = authenticate(httpServletRequest);
//        if (statusCode == HttpServletResponse.SC_ACCEPTED) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
//        } else {
//            httpServletResponse.sendError(statusCode);
//        }
    }

    private int authenticate(HttpServletRequest req) {
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        try {
            String token = req.getHeader("Authorization").replaceAll("^(.*?) ", "");
            if (token != null || !token.isEmpty()) {
                Claims claims = JwtUtil.decodeJwtToken(token);

                String username = userDAO.getUserById(Long.getLong(claims.getId())).getName();
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                statusCode = HttpServletResponse.SC_ACCEPTED;
            }
        } catch (Exception e) {
            logger.error("Can NOT set user authentication -> Message: {}", e);
        }
        return statusCode;
    }
}

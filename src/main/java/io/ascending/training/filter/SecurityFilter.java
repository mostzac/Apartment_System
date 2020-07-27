package io.ascending.training.filter;

import io.ascending.training.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class SecurityFilter implements Filter {
    //    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private Logger logger;

    private final String AUTH_URI = "/api/auth";
    private final String SOCK_URL = "/api/myWebSocket";
    private final String TEST_URL = "/api/test";
    private final String API_ENTRY_URL = "/api";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        int statusCode = authorization((HttpServletRequest) servletRequest);
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (logger == null) {
            SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, servletRequest.getServletContext());
        }
        logger.info("This is Security Filter");
        if (statusCode == HttpServletResponse.SC_ACCEPTED) filterChain.doFilter(servletRequest, servletResponse);
        else {
            response.sendError(statusCode);
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    private int authorization(HttpServletRequest req) {
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        String uri = req.getRequestURI();
        String verb = req.getMethod();
//        String WebSocketToken = req.getParameter("token");

//        if (uri.equalsIgnoreCase(AUTH_URI)||WebSocketToken.isEmpty()==false) return HttpServletResponse.SC_ACCEPTED;//login doesnt go to this filter
        if (!uri.startsWith(API_ENTRY_URL) || uri.startsWith(TEST_URL) || uri.startsWith(SOCK_URL) || uri.equalsIgnoreCase(AUTH_URI))
            return HttpServletResponse.SC_ACCEPTED;//login doesnt go to this filter

        try {
            String origin = req.getHeader("Authorization");
            String token = req.getHeader("Authorization").replaceAll("^(.*?) ", "");
            if (token == null || token.isEmpty()) return statusCode;

            Claims claims = JwtUtil.decodeJwtToken(token);
//            String allowedResources = "/"; //use this if no role assigned to the user, the user will have access to all the URL
            String allowedResources = "";
            switch (verb) {
                case "GET":
                    allowedResources = (String) claims.get("allowedReadResources");
                    break;
                case "POST":
                    allowedResources = (String) claims.get("allowedCreateResources");
                    break;
                case "PUT":
                    allowedResources = (String) claims.get("allowedUpdateResources");
                    break;
                case "DELETE":
                    allowedResources = (String) claims.get("allowedDeleteResources");
                    break;
            }

            for (String s : allowedResources.split(",")) {
                if (allowedResources == "") break;
                if (uri.trim().toLowerCase().startsWith(s.trim().toLowerCase())) {
                    statusCode = HttpServletResponse.SC_ACCEPTED;
                    break;
                }
            }
//            logger.debug(String.format("Verb: %s, allowed resources: %s", verb, allowedResources));
        } catch (Exception e) {
//            logger.error(e.getMessage());
        }

        return statusCode;
    }
}

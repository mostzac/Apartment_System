package io.ascending.training.filter;

import io.ascending.training.util.JwtUtil;
import io.jsonwebtoken.Claims;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;

@WebFilter(filterName = "WebSocketFilter", urlPatterns = {"/*"}, dispatcherTypes = {})
public class WebSocketFilter implements Filter {
    private final String AUTH_URI = "/api/auth";
    private final String SOCK_URL = "/myWebSocket";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        int statusCode = authorization((HttpServletRequest) servletRequest);
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if(statusCode==HttpServletResponse.SC_ACCEPTED) filterChain.doFilter(servletRequest,servletResponse);
        else {
            response.sendError(statusCode);
        }
    }

    private int authorization(HttpServletRequest request) {
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        String uri = request.getRequestURI();
        if (uri.startsWith(SOCK_URL)) {
            String verb = request.getMethod();
            String token = request.getParameter("token");
            if(token!=null){
                return HttpServletResponse.SC_ACCEPTED;
            }
        }else {
            return HttpServletResponse.SC_ACCEPTED;//login doesnt go to this filter
        }

        return statusCode;

    }
}

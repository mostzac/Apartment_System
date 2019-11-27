package io.ascending.training.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "WebSocketFilter", urlPatterns = {"/*"}, dispatcherTypes = {})
public class WebSocketFilter implements Filter {
    private final String SOCK_URL = "/myWebSocket";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    private int authorization(HttpServletRequest request) {
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        String uri = request.getRequestURI();
        if (uri.startsWith(SOCK_URL)) {
            String verb = request.getMethod();
            String token = request.getParameter("token");
            if(token!=null){
                
            }
        }
        return statusCode;

    }
}

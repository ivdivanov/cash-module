package com.fib.cash_desk_module;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class ApiKeyFilter implements Filter {

    // Inject the API key from the properties file
    @Value("${api.key}")
    private String apiKey;

    private static final String API_KEY_HEADER = "FIB-X-AUTH";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Optional: Add any filter initialization logic if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Cast to HttpServletRequest and HttpServletResponse
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Get the API key from the request header
        String requestApiKey = httpRequest.getHeader(API_KEY_HEADER);

        // Check if the API key from the request matches the one in the properties
        if (apiKey.equals(requestApiKey)) {
            // Proceed if the API key is valid
            chain.doFilter(request, response);
        } else {
            // If the API key is invalid or missing, return a 403 Forbidden response
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpResponse.getWriter().write("Invalid API Key.");
        }
    }

    @Override
    public void destroy() {
        // Optional: Add cleanup logic if needed
    }
}

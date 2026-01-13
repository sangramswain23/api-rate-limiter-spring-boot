package com.ratelimiter.config;

import com.ratelimiter.exception.RateLimitExceededException;
import com.ratelimiter.service.RateLimiterService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimiterService rateLimiterService;

    public RateLimitFilter(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String clientIp = extractClientIp(request);

        try {
            rateLimiterService.validateRequest(clientIp);
            filterChain.doFilter(request, response); // continue request
        } catch (RateLimitExceededException ex) {

            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"message\": \"" + ex.getMessage() + "\"}"
            );
        }
    }

    private String extractClientIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}

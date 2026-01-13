package com.ratelimiter.service;

public interface RateLimiterService {

	public void validateRequest(String ipAddress);

}

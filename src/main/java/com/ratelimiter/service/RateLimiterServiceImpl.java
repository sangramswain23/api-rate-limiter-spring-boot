package com.ratelimiter.service;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.ratelimiter.exception.RateLimitExceededException;
import com.ratelimiter.model.RequestCounter;

@Service
public class RateLimiterServiceImpl implements RateLimiterService{

	private static final int MAX_REQUESTS = 5;
	private static final long WINDOW_DURATION = 60_000;

	private final ConcurrentHashMap<String, RequestCounter> requestMap= new ConcurrentHashMap<>();
	
	@Override
	public void validateRequest(String ipAddress) {
		long currentTime=System.currentTimeMillis();
		RequestCounter counter=requestMap.get(ipAddress);
		
		if(counter==null) {
			RequestCounter newCounter=new RequestCounter(ipAddress,1,currentTime);
			requestMap.put(ipAddress, newCounter);
			return;
		}
		
		if(currentTime-counter.getWindowStartTime()>WINDOW_DURATION) {
			counter.setRequestCount(1);
			counter.setWindowStartTime(currentTime);
			return;
		}
		
		if(counter.getRequestCount()>=MAX_REQUESTS) {
			throw new RateLimitExceededException(
                    "Too many requests. Please try again later."
            );
		}
		
		counter.increment();
		
	}
	
}

package com.ratelimiter.model;

public class RequestCounter {

	private String ipAddress;
	private int requestCount;
	private long windowStartTime;
	
	public RequestCounter() {
		
	}
	
	public RequestCounter(String ipAddress, int requestCount, long windowStartTime) {
		this.ipAddress=ipAddress;
		this.requestCount=requestCount;
		this.windowStartTime=windowStartTime;
	}
	
	public void increment() {
        this.requestCount++;
    }

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getRequestCount() {
		return requestCount;
	}

	public void setRequestCount(int requestCount) {
		this.requestCount = requestCount;
	}

	public long getWindowStartTime() {
		return windowStartTime;
	}

	public void setWindowStartTime(long windowStartTime) {
		this.windowStartTime = windowStartTime;
	}
	
}

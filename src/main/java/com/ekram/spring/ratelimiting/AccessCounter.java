package com.ekram.spring.ratelimiting;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AccessCounter {

    private static AccessCounter accessCounter;

    private static ConcurrentHashMap<String,AccessData> accessDetails = new ConcurrentHashMap<String, AccessData>();
    
    private AccessCounter(){
    	
    }
	public static AccessCounter getInstance() {
		if (accessCounter == null){
			accessCounter = new AccessCounter();
		}
		return accessCounter;
	}
	
	public AccessData getByIp(String ipAddress){
		if (accessDetails.containsKey(ipAddress)){
			return accessDetails.get(ipAddress);
		}
		return null;
	}
	
	public AccessData put(String ipAddress){
		if (accessDetails.containsKey(ipAddress)){
			AccessData currentAccessData = accessDetails.get(ipAddress);
			currentAccessData.getCount().getAndIncrement();
			currentAccessData.setLastUpdated(System.currentTimeMillis());
			accessDetails.put(ipAddress, currentAccessData);
			return currentAccessData;
			
		} else{
			AccessData newData = new AccessData();
			newData.setCount(new AtomicInteger(1));
			newData.setLastUpdated(System.currentTimeMillis());
			accessDetails.put(ipAddress, newData);
			return newData;
		}
	}
    

}
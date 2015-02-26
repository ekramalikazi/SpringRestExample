package com.ekram.spring.ratelimiting;

import java.util.concurrent.atomic.AtomicInteger;

public class AccessData {
    private long lastUpdated;
    private AtomicInteger count;

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public AtomicInteger getCount() {
        return count;
    }

    public void setCount(AtomicInteger count) {
        this.count = count;
    }
    
}
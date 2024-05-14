package io.collective;

import java.time.Clock;

public class SimpleAgedCache {

    private Clock clock;

    private ExpirableEntry root = null;
    
    public SimpleAgedCache(Clock clock) {
        this.clock = clock;
    }

    public SimpleAgedCache() {
        this.clock = Clock.systemUTC();
    }

    public void put(Object key, Object value, int retentionInMillis) {
        long expiresAt = this.clock.millis() + (long)retentionInMillis;
        ExpirableEntry entry = new ExpirableEntry(key, value, expiresAt);

        if (root == null) {
            root = entry;
            return;
        }
        entry.next = root;
        root = entry;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        int size = 0;
        ExpirableEntry current = root;
        while (current != null) {
            if (current.isValid(clock))
                size++;
            current = current.next;
        }
        return size;
    }

    public Object get(Object key) {
        ExpirableEntry current = root;
        while (current != null) {
            if (current.hasKey(key)) {
                return current.getValue();
            }   
            current = current.next;
        }
        return null;
    }
}


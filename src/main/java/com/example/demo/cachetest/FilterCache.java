package com.example.demo.cachetest;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;

public class FilterCache implements CacheQ {

    private final CacheQ delegate;
    private final Deque<Object> keyList;
    private int size;

    public FilterCache(CacheQ delegate) {
        this.delegate = delegate;
        this.keyList = new LinkedList();
        this.size = 1024;
    }

    public String getId() {
        return this.delegate.getId();
    }

    public int getSize() {
        return this.delegate.getSize();
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void putObject(Object key, Object value) {
        this.cycleKeyList(key);
        this.delegate.putObject(key, value);
    }

    public Object getObject(Object key) {
        return this.delegate.getObject(key);
    }

    public Object removeObject(Object key) {
        return this.delegate.removeObject(key);
    }

    public void clear() {
        this.delegate.clear();
        this.keyList.clear();
    }

    public ReadWriteLock getReadWriteLock() {
        return null;
    }

    private void cycleKeyList(Object key) {
        this.keyList.addLast(key);
        if (this.keyList.size() > this.size) {
            Object oldestKey = this.keyList.removeFirst();
            this.delegate.removeObject(oldestKey);
        }

    }
}

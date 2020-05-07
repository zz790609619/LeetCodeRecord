package com.example.demo.cachetest;


public class ConfigerCache {
    CacheQ cacheQ;
    public ConfigerCache(){

    }
    public void setCacheQ(String key,Object value) {
        cacheQ.putObject(key,value);
    }
}

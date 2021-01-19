package com.im.gateway.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.nio.channels.Channel;
import java.util.concurrent.TimeUnit;

/**
 * session 存储区
 * create by Lyon.Cao in 2021/01/19 23:27
 **/
public class MemorySessionStore {

    private static final Cache<String, Channel> SESSIONS = Caffeine.newBuilder().expireAfterAccess(90, TimeUnit.MINUTES)
            .maximumSize(30000).build();


}

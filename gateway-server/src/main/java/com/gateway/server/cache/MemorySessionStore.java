package com.gateway.server.cache;

import com.gateway.server.constant.AttributeKeys;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.netty.channel.Channel;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * session 存储区
 * create by Lyon.Cao in 2021/01/19 23:27
 **/
public final class MemorySessionStore {

    private static final AtomicInteger          CHANNEL_COUNT = new AtomicInteger();
    private static final Cache<String, Channel> SESSIONS      = Caffeine.newBuilder().expireAfterAccess(15, TimeUnit.MINUTES)
            .maximumSize(30000).build();

    private MemorySessionStore() {

    }

    private static final MemorySessionStore memorySessionStore = new MemorySessionStore();

    public static MemorySessionStore getInstance() {
        return memorySessionStore;
    }

    /**
     * 根据sessionID获取连接通道
     *
     * @param sessionId
     * @return
     */
    public Channel getChannel(String sessionId) {
        Channel channel = SESSIONS.getIfPresent(sessionId);
        return channel;
    }

    /**
     * 绑定连接
     *
     * @param sessionId
     * @param channel
     */
    public void bindChannel(String sessionId, Channel channel) {
        CHANNEL_COUNT.incrementAndGet();
        SESSIONS.put(sessionId, channel);
    }

    /**
     * 解绑连接
     *
     * @param sessionId
     */
    public void unbindChannel(String sessionId) {
        Channel channel = SESSIONS.getIfPresent(sessionId);
        if (channel != null) {
            CHANNEL_COUNT.decrementAndGet();
        }
        SESSIONS.invalidate(sessionId);
    }

    /**
     * 刷新session 的最后活动时间
     */
    public void refreshSessionActiveTime(String sessionId) {
        Channel channel = SESSIONS.getIfPresent(sessionId);
        channel.attr(AttributeKeys.SESSION_LAST_ACTIVE_TIME).set(System.currentTimeMillis());
    }
}

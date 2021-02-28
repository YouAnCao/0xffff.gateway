package com.gateway.server.cache;

import com.gateway.server.constant.AttributeKeys;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * session 存储区
 * create by Lyon.Cao in 2021/01/19 23:27
 **/
public final class MemorySessionStore {

    private static final AtomicInteger                        CHANNEL_COUNT = new AtomicInteger();
    private static final Cache<String, ChannelHandlerContext> SESSIONS      = Caffeine.newBuilder().expireAfterAccess(30, TimeUnit.SECONDS)
            .maximumSize(30000).build();
    // TODO 确定此处的最大连接数，确保内存不泄露

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
    public ChannelHandlerContext getChannelHandlerContext(String sessionId) {
        ChannelHandlerContext channel = SESSIONS.getIfPresent(sessionId);
        return channel;
    }

    /**
     * 绑定连接
     *
     * @param sessionId
     * @param channel
     */
    public void bindChannel(String sessionId, ChannelHandlerContext channel) {
        CHANNEL_COUNT.incrementAndGet();
        SESSIONS.put(sessionId, channel);
    }

    /**
     * 解绑连接
     *
     * @param sessionId
     */
    public void unbindChannel(String sessionId) {
        ChannelHandlerContext channel = SESSIONS.getIfPresent(sessionId);
        if (channel != null) {
            CHANNEL_COUNT.decrementAndGet();
        }
        SESSIONS.invalidate(sessionId);
    }

    /**
     * 刷新session 的最后活动时间
     */
    public void refreshSessionActiveTime(String sessionId) {
        ChannelHandlerContext channelHandlerContext = SESSIONS.getIfPresent(sessionId);
        Channel               channel               = channelHandlerContext.channel();
        channel.attr(AttributeKeys.SESSION_LAST_ACTIVE_TIME).set(System.currentTimeMillis());
    }

    /**
     * 获取当前连接数
     *
     * @return
     */
    public int getChannelCount() {
        return CHANNEL_COUNT.get();
    }
}

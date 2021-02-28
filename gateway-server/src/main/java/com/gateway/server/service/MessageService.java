package com.gateway.server.service;

import com.gateway.server.cache.MemorySessionGroupStore;
import com.gateway.server.cache.MemorySessionStore;
import com.gateway.wireprotocol.protocol.WireProtocol;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Service;

/**
 * 消息服务
 * create by Lyon.Cao in 2021/02/28 22:46
 **/
@Service
public class MessageService {

    /**
     * 根据分组ID发送广播消息
     */
    public void sendMessageToGroup(String group, WireProtocol protocol) {

    }

    /**
     * 根据sessionId发送消息
     */
    public void sendMessageToChannel(String sessionId, WireProtocol protocol) {
        ChannelHandlerContext ctx = MemorySessionStore.getInstance().getChannelHandlerContext(sessionId);
        if (ctx != null && ctx.channel().isOpen()) {
            ctx.writeAndFlush(protocol);
        }
    }

}

package com.gateway.server.netty.handler;

import com.gateway.server.cache.MemorySessionGroupStore;
import com.gateway.server.cache.MemorySessionStore;
import com.gateway.server.constant.AttributeKeys;
import com.gateway.wireprotocol.protocol.WireProtocol;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * create by Lyon.Cao in 2021/01/19 1:20
 **/
public class ServerHandler extends SimpleChannelInboundHandler<WireProtocol> {

    private Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, WireProtocol wireProtocol) throws Exception {

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel                channel   = ctx.channel();
        Attribute<Set<String>> attr      = channel.attr(AttributeKeys.SESSION_GROUPS);
        String                 sessionId = channel.id().asLongText();
        /* 解绑分组信息 */
        if (attr != null) {
            Set<String> groups = attr.get();
            if (groups != null) {
                MemorySessionGroupStore.removeMemberFromGroups(sessionId, groups);
            }
        }
        /* 解绑session 信息 */
        MemorySessionStore.getInstance().unbindChannel(sessionId);
        super.channelInactive(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("client connect.");
        ChannelId id        = ctx.channel().id();
        String    sessionId = id.asLongText();
        MemorySessionStore.getInstance().bindChannel(sessionId, ctx);
        super.channelActive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        /* 刷新channel 的最后存活时间 */
        MemorySessionStore.getInstance().refreshSessionActiveTime(ctx.channel().id().asLongText());
        super.channelReadComplete(ctx);
    }

}

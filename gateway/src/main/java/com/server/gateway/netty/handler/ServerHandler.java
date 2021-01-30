package com.server.gateway.netty.handler;

import com.server.ext.protocol.ConnectFlag;
import com.server.ext.protocol.WireFixedHeader;
import com.server.ext.protocol.WireProtocol;
import com.server.ext.protocol.WireVariableHeader;
import com.server.gateway.cache.MemorySessionGroupStore;
import com.server.gateway.cache.MemorySessionStore;
import com.server.gateway.constant.AttributeKeys;
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
        byte[]             data           = new byte[5000];
        ConnectFlag        connectFlag    = new ConnectFlag(true, false, true, false, true, 1);
        WireFixedHeader    fixedHeader    = new WireFixedHeader(connectFlag);
        WireVariableHeader variableHeader = new WireVariableHeader(null, 0);
        WireProtocol       result         = new WireProtocol(fixedHeader, variableHeader, data);
        channelHandlerContext.writeAndFlush(result);
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
        MemorySessionStore.getInstance().bindChannel(sessionId, ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        /* 刷新channel 的最后存活时间 */
        MemorySessionStore.getInstance().refreshSessionActiveTime(ctx.channel().id().asLongText());
        super.channelReadComplete(ctx);
    }

}

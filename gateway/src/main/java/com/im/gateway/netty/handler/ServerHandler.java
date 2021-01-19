package com.im.gateway.netty.handler;

import com.im.gateway.protocol.BiuProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 * create by Lyon.Cao in 2021/01/19 1:20
 **/
public class ServerHandler extends SimpleChannelInboundHandler<BiuProtocol> {

    Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BiuProtocol biuProtocol) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channel active");
        super.channelActive(ctx);
    }

}

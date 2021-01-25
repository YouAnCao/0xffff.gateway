package com.server.gateway.netty.handler;


import com.server.ext.protocol.WireProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 *
 * create by Lyon.Cao in 2021/01/19 1:20
 **/
public class ServerHandler extends SimpleChannelInboundHandler<WireProtocol> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, WireProtocol wireProtocol) throws Exception {

    }
}

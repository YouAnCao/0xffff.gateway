package com.server.gateway.netty.codec;

import com.server.ext.protocol.WireProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * 解压
 * create by Lyon.Cao in 2021/01/30 23:38
 **/
public class ServerCompressionEncoder extends MessageToMessageEncoder<WireProtocol> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, WireProtocol wireProtocol, List<Object> list) throws Exception {
        if (wireProtocol.getFixedHeader().getConnectFlag().isCompression()) {

        }
        list.add(wireProtocol);
    }
}

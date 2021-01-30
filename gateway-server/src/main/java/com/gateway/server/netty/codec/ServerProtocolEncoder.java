package com.gateway.server.netty.codec;

import com.gateway.wireprotocol.protocol.WireProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 协议转换
 * create by Lyon.Cao in 2021/01/19 1:19
 **/
public class ServerProtocolEncoder extends MessageToByteEncoder<WireProtocol> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, WireProtocol wireProtocol, ByteBuf byteBuf) throws Exception {
        byte[] data = wireProtocol.toByteArray();
        byteBuf.writeBytes(data);
    }
}

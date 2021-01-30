package com.gateway.server.netty.codec;

import com.gateway.wireprotocol.protocol.WireProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * 解压缩
 * create by Lyon.Cao in 2021/01/30 23:40
 **/
public class ServerCompressionDecoder extends MessageToMessageDecoder<WireProtocol> {


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, WireProtocol wireProtocol, List<Object> list) throws Exception {

        list.add(wireProtocol);
    }
}

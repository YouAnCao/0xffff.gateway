package com.server.gateway.netty.codec;

import com.server.ext.protocol.WireProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.GZIPOutputStream;

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

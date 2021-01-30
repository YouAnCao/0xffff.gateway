package com.gateway.client.codec;

import com.gateway.wireprotocol.protocol.WireProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 转换为协议消息
 * create by Lyon.Cao in 2021/01/19 1:13
 **/
public class ServerProtocolDecoder extends ByteToMessageDecoder {

    private Logger logger = LoggerFactory.getLogger(ServerProtocolDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int len = byteBuf.readableBytes();
        /* 最小协议长度1byte */
        if (len > 0) {
            byte[] data = new byte[len];
            byteBuf.readBytes(data);
            WireProtocol wireProtocol = new WireProtocol(data);
            list.add(wireProtocol);
        }
    }
}

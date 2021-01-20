package com.im.gateway.netty.codec;

import com.im.gateway.protocol.BiuProtocol;
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
        int size = byteBuf.readableBytes();
        /* 检查协议是否满足条件，连接数据包的协议必须大于 */
    }
}

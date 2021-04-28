package com.gateway.server.netty.codec;

import com.gateway.wireprotocol.protocol.WireFixedHeader;
import com.gateway.wireprotocol.protocol.WireProtocol;
import com.gateway.wireprotocol.protocol.WireVariableHeader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * @description: 协议解码器
 * @sign: created by Lyon.Cao in 2021/4/1 9:08 下午
 **/
public class WireProtocolDecoder extends ByteToMessageDecoder {

    private Logger logger = LoggerFactory.getLogger(WireProtocolDecoder.class);

    /**
     * PING包
     */
    private final static int PING = 0x3;

    /**
     * 最小包长度
     */
    private static final int BASE_LENGTH = 1;

    /**
     * 最大只允许8kb的负载长度
     */
    private static final int MAX_DATA_LENGTH = 8192;
    private static final int EXIST           = 1;
    private static final int NOT_EXIST       = 0;
    private static final String START_PREFIX = "##";

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int  beginReader;
        byte fixedHeader;
        while (true) {
            beginReader = byteBuf.readerIndex();
            /* 检查可读数据长度，如果等于1判断是否是PING包 */
            fixedHeader = byteBuf.readByte();
            if ((fixedHeader & 0x01) == 1) {
                // 初步判断为正常包
                break;
            }
            /* 丢弃第一个包,再次读一个字节,直到最低位等于1 */
            byteBuf.resetReaderIndex();
            byteBuf.readByte();
            /* 没有可读的数据包，等待后面的数据到达 */
            if (BASE_LENGTH > byteBuf.readableBytes()) {
                return;
            }
        }
        /* 判断是否为PING包 */
        if (fixedHeader == PING) {
            logger.info("获取客户端PING包");
            return;
        }

        WireFixedHeader wireFixedHeader = new WireFixedHeader(fixedHeader);
        if (byteBuf.readableBytes() < wireFixedHeader.getBaseLength()) {
            /* 缓存中数据不够读，等待后面数据传输 */
            byteBuf.readerIndex(beginReader);
            return;
        }

        WireVariableHeader variableHeader = new WireVariableHeader();

        if (wireFixedHeader.getSequenceId() == EXIST) {
            /* 序列ID为64位 */
            variableHeader.setSequenceId(byteBuf.readLong());
        }

        if (wireFixedHeader.getPayload() == EXIST) {
            /* 负载长度位16位 */
            variableHeader.setPayloadLength(byteBuf.readShort());
        }
        if (wireFixedHeader.getCommand() == EXIST) {
            /* 命令包长度8位 */
            variableHeader.setCommand(byteBuf.readByte());
        }

        /* 再次检查负载是否到齐 */
        if (wireFixedHeader.getPayload() == EXIST && variableHeader.getPayloadLength() > byteBuf.readableBytes()) {
            /* 半包,继续等待后续数据传输完成 */
            byteBuf.readerIndex(beginReader);
            return;
        }

        WireProtocol protocol = new WireProtocol();
        if (wireFixedHeader.getPayload() == EXIST) {
            byte[] payload = new byte[variableHeader.getPayloadLength()];
            byteBuf.readBytes(payload);
            protocol.setPayload(payload);
        }

        /* 比对校验位 */


        protocol.setFixedHeader(wireFixedHeader);
        protocol.setVariableHeader(variableHeader);
    }
}

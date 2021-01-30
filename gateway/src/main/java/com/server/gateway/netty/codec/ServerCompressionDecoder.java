package com.server.gateway.netty.codec;

import com.server.ext.protocol.ConnectFlag;
import com.server.ext.protocol.WireProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * 压缩
 * create by Lyon.Cao in 2021/01/30 23:40
 **/
public class ServerCompressionDecoder extends MessageToMessageDecoder<WireProtocol> {


    private final static int MIN_SIZE = 4086;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, WireProtocol wireProtocol, List<Object> list) throws Exception {
        if (wireProtocol != null
                && wireProtocol.getPayload() != null
                && wireProtocol.getPayload().length > 0
                && wireProtocol.getFixedHeader() != null
                && wireProtocol.getFixedHeader().getConnectFlag() != null
                && wireProtocol.getFixedHeader().getConnectFlag().isCompression()) {
            ConnectFlag connectFlag = wireProtocol.getFixedHeader().getConnectFlag();
            /* 只有达到最低压缩标准，才能进行压缩 */
            if (wireProtocol.getPayload().length > MIN_SIZE) {
                ByteArrayOutputStream out  = new ByteArrayOutputStream();
                GZIPOutputStream      gzip = new GZIPOutputStream(out);
                gzip.write(wireProtocol.getPayload());
                byte[] byteArray = out.toByteArray();
                wireProtocol.setPayload(byteArray);
                out.close();
            } else {
                wireProtocol.getFixedHeader().getConnectFlag().setCompression(false);
            }
        }
        list.add(wireProtocol);
    }
}

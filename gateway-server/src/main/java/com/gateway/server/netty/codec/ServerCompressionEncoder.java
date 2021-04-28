//package com.gateway.server.netty.codec;
//
//import com.gateway.wireprotocol.protocol.WireProtocol;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.MessageToMessageEncoder;
//
//import java.io.ByteArrayOutputStream;
//import java.util.List;
//import java.util.zip.GZIPOutputStream;
//
///**
// * 出战数据压缩
// * create by Lyon.Cao in 2021/01/30 23:38
// **/
//public class ServerCompressionEncoder extends MessageToMessageEncoder<WireProtocol> {
//
//    private final static int MIN_SIZE = 4086;
//
//    @Override
//    protected void encode(ChannelHandlerContext channelHandlerContext, WireProtocol wireProtocol, List<Object> list) throws Exception {
//        if (wireProtocol != null
//                && wireProtocol.getPayload() != null
//                && wireProtocol.getPayload().length > 0
//                && wireProtocol.getFixedHeader() != null
//                && wireProtocol.getFixedHeader().getConnectFlag() != null
//                && wireProtocol.getFixedHeader().getConnectFlag().isCompression()) {
//            /* 只有达到最低压缩标准才能进行压缩 */
//            if (wireProtocol.getPayload().length > MIN_SIZE) {
//                ByteArrayOutputStream out  = new ByteArrayOutputStream();
//                GZIPOutputStream      gzip = new GZIPOutputStream(out);
//                gzip.write(wireProtocol.getPayload());
//                byte[] byteArray = out.toByteArray();
//                wireProtocol.setPayload(byteArray);
//                out.close();
//                gzip.close();
//            } else {
//                wireProtocol.getFixedHeader().getConnectFlag().setCompression(false);
//            }
//        }
//        list.add(wireProtocol);
//    }
//}
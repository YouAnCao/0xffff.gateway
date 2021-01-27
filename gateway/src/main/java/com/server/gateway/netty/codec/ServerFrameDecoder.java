package com.server.gateway.netty.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * create by Lyon.Cao in 2021/01/19 1:09
 **/
public class ServerFrameDecoder extends LengthFieldBasedFrameDecoder {

    /* 最大支持数据包长度 1M */
    private static final int MAX_DATA_LEN = 1048576;

    public ServerFrameDecoder() {
        super(MAX_DATA_LEN, 0, 4, 0, 4);
    }
}

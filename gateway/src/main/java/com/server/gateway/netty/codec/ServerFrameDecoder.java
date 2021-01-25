package com.server.gateway.netty.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * TODO
 * create by Lyon.Cao in 2021/01/19 1:09
 **/
public class ServerFrameDecoder extends LengthFieldBasedFrameDecoder {

    public ServerFrameDecoder() {
        super(Integer.MAX_VALUE, 0, 4, 0, 4);
    }
}

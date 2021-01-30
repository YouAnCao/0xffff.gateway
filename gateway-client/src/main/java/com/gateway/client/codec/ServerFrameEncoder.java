package com.gateway.client.codec;

import io.netty.handler.codec.LengthFieldPrepender;

/**
 * TODO
 * create by Lyon.Cao in 2021/01/19 1:16
 **/
public class ServerFrameEncoder extends LengthFieldPrepender {
    public ServerFrameEncoder() {
        super(4);
    }
}

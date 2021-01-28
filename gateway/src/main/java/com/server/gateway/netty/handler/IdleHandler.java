package com.server.gateway.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * idle
 * create by Lyon.Cao in 2021/01/28 23:03
 **/
public class IdleHandler extends IdleStateHandler {
    public IdleHandler() {
        super(30, 0, 0);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {

            }
        }
        super.userEventTriggered(ctx, evt);
    }
}

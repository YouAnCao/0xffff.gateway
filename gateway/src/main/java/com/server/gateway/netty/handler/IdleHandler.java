package com.server.gateway.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * idle
 * create by Lyon.Cao in 2021/01/28 23:03
 **/
public class IdleHandler extends IdleStateHandler {

    private Logger logger = LoggerFactory.getLogger(IdleHandler.class);

    public IdleHandler() {
        super(10, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        if (evt == IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT) {
            ctx.close();
            return;
        }
        super.channelIdle(ctx, evt);
    }
}

package com.server.gateway.client;

import com.server.ext.protocol.WireProtocol;
import com.server.gateway.client.codec.ServerFrameDecoder;
import com.server.gateway.client.codec.ServerFrameEncoder;
import com.server.gateway.client.codec.ServerProtocolDecoder;
import com.server.gateway.client.codec.ServerProtocolEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端
 * create by Lyon.Cao in 2021/01/30 16:39
 **/
public class Client {

    private Logger logger = LoggerFactory.getLogger(Client.class);

    ChannelHandlerContext context = null;

    public void connect(String ip, int port) {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap         bootstrap = new Bootstrap();
        bootstrap.group(workGroup).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast("serverFrameDecoder", new ServerFrameDecoder());
                        pipeline.addLast("serverProtocolDecoder", new ServerProtocolDecoder());
                        pipeline.addLast("serverFrameEncoder", new ServerFrameEncoder());
                        pipeline.addLast("serverProtocolEncoder", new ServerProtocolEncoder());
                        pipeline.addLast("serverHandler", new SimpleChannelInboundHandler<WireProtocol>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, WireProtocol wireProtocol) throws Exception {

                            }

                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                context = ctx;
                                super.channelActive(ctx);
                            }
                        });
                    }
                });
        try {
            ChannelFuture sync = bootstrap.connect(ip, port).sync();
            // sync.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            //workGroup.shutdownGracefully();
        }

    }

}

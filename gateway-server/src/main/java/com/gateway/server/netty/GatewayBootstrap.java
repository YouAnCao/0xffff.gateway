package com.gateway.server.netty;

import com.gateway.server.netty.codec.*;
import com.gateway.server.netty.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 长连接启动服务
 * create by Lyon.Cao in 2021/01/19 0:38
 **/
@Component
@Order(2)
public class GatewayBootstrap implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(GatewayBootstrap.class);

    @Value("${gateway.port}")
    public int port;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        start();
    }

    private void start() {
        NioEventLoopGroup bossGroup   = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap   server      = new ServerBootstrap();

        try {
            server.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {

                        @Override
                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                            super.exceptionCaught(ctx, cause);
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            logger.info("channel read");
                            super.channelRead(ctx, msg);
                        }

                        @Override
                        protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("serverLogger", new LoggingHandler(LogLevel.INFO));
                            //pipeline.addLast("idleHandler", new IdleHandler());
                            pipeline.addLast("serverFrameDecoder", new ServerFrameDecoder());
                            pipeline.addLast("serverFrameEncoder", new ServerFrameEncoder());
                            pipeline.addLast("serverProtocolDecoder", new ServerProtocolDecoder());
                            pipeline.addLast("serverProtocolEncoder", new ServerProtocolEncoder());
                            pipeline.addLast("dataCompressionDecoder", new ServerCompressionDecoder());
                            pipeline.addLast("dataCompressionEncoder", new ServerCompressionEncoder());

                            pipeline.addLast("serverHandler", new ServerHandler());
                        }
                    });
            ChannelFuture channelFuture = server.bind(port).sync();
            channelFuture.addListener((e) -> {
                if (e.isSuccess()) {
                    logger.info("gateway server starter in port {}", port);
                }
            });
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

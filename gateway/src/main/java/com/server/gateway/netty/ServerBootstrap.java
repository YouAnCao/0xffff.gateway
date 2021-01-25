package com.server.gateway.netty;

import com.server.gateway.netty.codec.ServerFrameDecoder;
import com.server.gateway.netty.codec.ServerFrameEncoder;
import com.server.gateway.netty.codec.ServerProtocolDecoder;
import com.server.gateway.netty.codec.ServerProtocolEncoder;
import com.server.gateway.netty.handler.ServerHandler;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ServerBootstrap implements ApplicationRunner {
    Logger logger = LoggerFactory.getLogger(ServerBootstrap.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        start();
    }

    private void start() {
        NioEventLoopGroup                  bossGroup   = new NioEventLoopGroup(1);
        NioEventLoopGroup                  workerGroup = new NioEventLoopGroup();
        io.netty.bootstrap.ServerBootstrap server      = new io.netty.bootstrap.ServerBootstrap();

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
                            logger.info("read");
                            super.channelRead(ctx, msg);
                        }

                        @Override
                        protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();

                            pipeline.addLast("serverLogger", new LoggingHandler(LogLevel.INFO));

                            pipeline.addLast("serverFrameDecoder", new ServerFrameDecoder());
                            pipeline.addLast("serverFrameEncoder", new ServerFrameEncoder());
                            pipeline.addLast("serverProtocolDecoder", new ServerProtocolDecoder());
                            pipeline.addLast("serverProtocolEncoder", new ServerProtocolEncoder());


                        }
                    });
            ChannelFuture channelFuture = server.bind(2020).sync();
            channelFuture.addListener((e) -> {
                if (e.isSuccess()) {
                    logger.info("gateway server starter in port {}", 2020);
                }
            });
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

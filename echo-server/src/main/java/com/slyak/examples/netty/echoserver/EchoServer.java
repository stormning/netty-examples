package com.slyak.examples.netty.echoserver;

import com.slyak.examples.netty.echoserver.handler.EchoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.SneakyThrows;

import java.net.InetSocketAddress;

/**
 * .
 *
 * @author stormning 2017/4/14
 * @since 1.3.0
 */

public class EchoServer {

    private final int port = 9999;

    public static void main(String[] args) {
        new EchoServer().start();
    }

    @SneakyThrows(InterruptedException.class)
    private void start() {
        //
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)
                    //channel type
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    //channel handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new EchoServerHandler());
                        }
                    });
            //绑定，sync等待服务器关闭
            ChannelFuture future = bootstrap.bind().sync();

            //关闭channel和future，直到被关闭
            future.channel().closeFuture().sync();

        } finally {
            //关闭EventLoopGroup直到被关闭,并释放所有创建的线程
            group.shutdownGracefully().sync();
        }
    }
}

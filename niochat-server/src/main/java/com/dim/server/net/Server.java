/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dim.server.net;

import com.dim.server.Start;
import com.dim.server.msg.MessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author yanming_dai
 */
public class Server {

    private static final Logger logger = LogManager.getLogger(Server.class);
    
    private int port = 9600;

    public Server(int port) {
        this.port = port;
    }

    @SuppressWarnings("CallToThreadDumpStack")
    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast("frameDecoder", new LineBasedFrameDecoder(128));
                    ch.pipeline().addLast("stringEncoder",new StringEncoder());
                    ch.pipeline().addLast("stringDecoder",new StringDecoder());
                    ch.pipeline().addLast("messageHandler",new MessageHandler());
                }
            });
            ChannelFuture f = boot.bind().sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}

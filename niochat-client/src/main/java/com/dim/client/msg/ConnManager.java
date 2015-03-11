/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dim.client.msg;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import java.net.InetSocketAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author yanming_dai
 */
public class ConnManager implements Runnable {

    private Logger logger = LogManager.getLogger(ConnManager.class.getName());
    private String host = "localhost";
    private int port = 9600;
    private static ChannelOutboundHandlerAdapter messageWriteHandler = new MessageWriteHandler();
    private static ChannelInboundHandlerAdapter messageReadHandler = new MessageReadHandler();

    public ConnManager() {
        //default constructor
    }

    public ConnManager(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        connect();
    }

    private void connect() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            logger.info("start to connect...");
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).remoteAddress(new InetSocketAddress(host, port)).handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new StringEncoder());
                    ch.pipeline().addLast(messageWriteHandler);
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(messageReadHandler);
                }
            });
            ChannelFuture future = b.connect().sync();
            future.channel().write("baga");
            future.channel().closeFuture().sync();

            logger.info("end of connect");
        } catch (Exception e) {
            logger.error("connect to server faild.");
        }
    }

    public static ChannelInboundHandlerAdapter getMessageReadHandler() {
        return messageReadHandler;
    }

    public static ChannelOutboundHandlerAdapter getMessageWriteHandler() {
        return messageWriteHandler;
    }
    
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
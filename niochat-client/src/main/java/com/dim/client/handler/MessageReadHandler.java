/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dim.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author yanming_dai
 */
public class MessageReadHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private static final Logger logger = LogManager.getLogger(MessageReadHandler.class);
    private static ChannelHandlerContext ctx1;
    
    public static void write(String s){
        ctx1.write(s);
        ctx1.flush();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info("channel actived,message will send to server.");
        ctx1 = ctx;
        ctx.write(Unpooled.copiedBuffer("netty rocks", CharsetUtil.UTF_8));
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //compiled code
        logger.info(" message received from server :" + (String)msg);
        logger.info("ok");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext chc, ByteBuf in) throws Exception {
        logger.info("client received :" + ByteBufUtil.hexDump(in.readBytes(in.readableBytes())));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext chc, Throwable t) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet.");
        logger.info("exceptionCaught");
        t.printStackTrace();
    }
}
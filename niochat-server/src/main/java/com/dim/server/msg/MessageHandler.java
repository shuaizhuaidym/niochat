/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dim.server.msg;

import com.dim.server.Start;
import com.dim.server.handler.LoginHandler;
import com.dim.server.handler.RegisterHandler;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author yanming_dai
 */
public class MessageHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LogManager.getLogger(Start.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        logger.info("server received :" + msg);
        //TODO 根据消息类型，做相应处理
        String message = (String) msg;
        String type = message.substring(0, message.indexOf("|"));
        String content = message.substring(message.indexOf("|"));
        logger.info(type + ": received");
        logger.info("content: " + content);
        Integer it = Integer.valueOf(type.charAt(5));
        //register
        if (48 == it) {//(char)0=48
            String[] ap = content.split("&");
            String account=ap[0].split("=")[1];
            String password=ap[1].split("=")[1];
            
            logger.info("account: "+account);
            logger.info("password: "+password);
            RegisterMessage rmsg=new RegisterMessage(account,password);
            Boolean result = new RegisterHandler().handle(rmsg);
            ctx.write(result.toString()+"\r\n");
            //login
        }else if(49==it){
            String[] ap = content.split("&");
            String account=ap[0].split("=")[1];
            String password=ap[1].split("=")[1];
            
            logger.info("account: "+account);
            logger.info("password: "+password);
            LoginMessage lrequest=new LoginMessage(account,password);
            
            LoginMessage response = new LoginHandler().handle(lrequest);
            ctx.write(response.toBytes());
        //error
        } else {
            ctx.write("[ERROR]UNKNOWN MESSAGE TYPE");
        }
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        logger.info("read complete.");
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.catching(cause);
        ctx.close();
    }
}

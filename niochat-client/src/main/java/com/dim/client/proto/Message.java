/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dim.client.proto;

import java.nio.ByteBuffer;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author yanming_dai structure:type:content e.g.
 * "type=XXX:account=XXX&password=XXX"
 */
public class Message {
    private org.apache.logging.log4j.Logger logger = LogManager.getLogger(Message.class.getName());
    /**
     * message type 
     * 0:register
     * 1:login 
     * 2:fetch contacts 
     * 3:chat
     */
    private Integer type = 0;
    /**
     * 目的地IP，如果是聊天消息，为对方IP，否则为服务器IP，可以空
     */
    private String destination;

    private static byte[] TYPE_HEAD = "type=".getBytes();
    private static byte[] HEAD_DIV = "|".getBytes();//type content delimiter
    private byte[] LINE_DIV = "\r\n".getBytes();//line delimiter
    /**
     * message content
     */
    private String content;

    public Message(Integer type,String dst, String content) {
        this.type = type;
        this.destination=dst;
        this.content = content;
    }

    public void toBytes(ByteBuffer bb) {
        bb.put(TYPE_HEAD);
        bb.put(String.valueOf(type).getBytes());
        bb.put(HEAD_DIV);
        logger.info(content);
        bb.put(content.getBytes());
        bb.put(LINE_DIV);
    }

    public String toLineString() {
        return type + content + "\r\n";
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dim.client.net;

import com.dim.client.proto.Message;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author yanming_dai
 */
public class Client implements Runnable {

    private org.apache.logging.log4j.Logger logger = LogManager.getLogger(Client.class.getName());
    private String host;
    private Integer port;
    private String account;
    private String pwd;
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
    private SocketChannel channel;

    public Client(String host, Integer port, String account, String pwd) {
        this.host = host;
        this.port = port;
        this.account = account;
        this.pwd = pwd;
    }

    public void run() {
        try {
            connect();
        } catch (IOException ex) {
            logger.catching(ex);
        }
    }
    //connect to server

    public void connect() throws IOException {
        channel = SocketChannel.open();
        channel.connect(new InetSocketAddress(host, port));

        logger.info("connect success.");
    }
    
    /***
     * apply new account
     * @param account
     * @param password
     * @return 
     */
    public Integer applyAccount(String account, String password) throws IOException {
        Message msg = new Message(0, null, "account=" + account + "&password=" + password);
        logger.info("apply new account");
        writeBuffer.clear();
        msg.toBytes(writeBuffer);
        writeBuffer.flip();
        int i = channel.write(writeBuffer);
        logger.info(i + " writed.");

        readBuffer.clear();
        channel.read(readBuffer);

        logger.info("account apply result：" + new String(readBuffer.array()));
        //TODO deal  with result
        return 0;
    }

    /**
     * login
     * @param account
     * @param pwd
     * @return
     * @throws IOException 
     */    
    public Integer login(String account, String pwd) throws IOException {
        logger.info("start login.");
        Message msg = new Message(1,null, "account=" + account + "&password=" + pwd);
        writeBuffer.clear();
        msg.toBytes(writeBuffer);
        writeBuffer.flip();

        int i = channel.write(writeBuffer);
        logger.info(i + " writed.");

        readBuffer.clear();
        channel.read(readBuffer);
        logger.info("login result : " + new String(readBuffer.array()));
        //TODO deal  with result
        return 0;
    }

    //send message
    public void send(String data) throws IOException {
        writeBuffer.clear();
        writeBuffer.put(data.getBytes());
        writeBuffer.flip();

        channel.write(writeBuffer);
    }

    //receive message
    public void receive() throws IOException {
        channel.read(readBuffer);
    }
}

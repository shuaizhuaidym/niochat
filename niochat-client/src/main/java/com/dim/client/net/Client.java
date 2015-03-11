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
    private SocketChannel client;

    public Client(String host, Integer port, String account, String pwd) {
        this.host = host;
        this.port = port;
        this.account = account;
        this.pwd = pwd;
    }

    public void run() {
        try {
            connect();
            login(account, pwd);
        } catch (IOException ex) {
            logger.catching(ex);
        }
    }
    //connect to server

    public void connect() throws IOException {
        client = SocketChannel.open();
        client.connect(new InetSocketAddress(host, port));

        logger.info("connect success.");
    }

    //verify user account and password
    public Integer login(String account, String pwd) throws IOException {
        logger.info("start login.");
        Message msg = new Message(0,null, "account=" + account + "&password=" + pwd);
        writeBuffer.clear();
        msg.toBytes(writeBuffer);
        writeBuffer.flip();

        int i = client.write(writeBuffer);
        logger.info(i + " writed.");

        readBuffer.clear();
        client.read(readBuffer);
        logger.info("login result : " + new String(readBuffer.array()));
        return 0;
    }

    //send message
    public void send(String data) throws IOException {
        writeBuffer.clear();
        writeBuffer.put(data.getBytes());
        writeBuffer.flip();

        client.write(writeBuffer);
    }

    //receive message
    public void receive() throws IOException {
        client.read(readBuffer);
    }
}

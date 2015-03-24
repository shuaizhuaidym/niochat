/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dim.server.msg;

import java.nio.ByteBuffer;

/**
 *
 * @author yanming_dai
 */
public class LoginResponse extends Message{
    
    private String account;
    
    private String password;
    
    public LoginResponse() {
    }

    public LoginResponse(String account, String password) {
        this.account = account;
        this.password = password;
    }
    
    
    public ByteBuffer toBytes(){
        ByteBuffer buf=ByteBuffer.allocate(1024);
    
        buf.put("account=".getBytes());
        buf.put(account.getBytes());
        buf.put(MsgConst.AND);
        buf.put("password=".getBytes());
        buf.put(password.getBytes());
        buf.put(MsgConst.LINE_DIV);
        return buf;
    }
    
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}

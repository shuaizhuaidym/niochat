/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dim.server.msg;

/**
 *
 * @author yanming_dai
 */
public class RegisterMessage extends Message{
    private String account,
            password;

    public RegisterMessage() {
    }

    public RegisterMessage(String account, String password) {
        this.account = account;
        this.password = password;
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

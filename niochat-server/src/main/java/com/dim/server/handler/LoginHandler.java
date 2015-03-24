/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dim.server.handler;

import com.dim.server.data.DBTool;
import com.dim.server.msg.LoginResponse;
import com.dim.server.msg.Message;

/**
 *
 * @author yanming_dai
 */
public class LoginHandler{

    public LoginResponse handle(Message message) {
        return DBTool.login((LoginResponse)message);
    }
}

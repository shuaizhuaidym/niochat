/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dim.server.handler;

import com.dim.server.data.DBTool;
import com.dim.server.msg.Message;
import com.dim.server.msg.RegisterMessage;

/**
 *
 * @author yanming_dai
 */
public class RegisterHandler{

    public boolean handle(Message message) {
        return DBTool.register((RegisterMessage)message);
    }
}

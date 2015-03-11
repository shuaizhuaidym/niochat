/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dim.server.msg;

import java.io.Serializable;

/**
 *
 * @author yanming_dai
 */
public abstract class Message implements Serializable{

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    private String id;
    
}

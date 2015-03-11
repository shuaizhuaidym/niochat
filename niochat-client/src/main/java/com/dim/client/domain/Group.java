/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dim.client.domain;

/**
 *
 * @author daiyma
 * 联系人分组
 */
public class Group {
    private String name;

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dim.client.domain;

/**
 *
 * @author daiyma
 * 联系人
 */
public class Contact {
    private Long id;//标识
    private Long number;//QQ号码
    
    private String displayName;//名称
    private String signiture;//个性签名
    private String portrate;//头像
    
    private String IP;
    private int status;//在线状态，登录时获取一次，以后每隔一分钟获取一次

    public String getPortrate() {
        return portrate;
    }

    public void setPortrate(String portrate) {
        this.portrate = portrate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Contact(String displayName) {
        this.displayName = displayName;
    }

    
    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getSigniture() {
        return signiture;
    }

    public void setSigniture(String signiture) {
        this.signiture = signiture;
    }

    @Override
    public String toString() {
        return displayName;
    }

}

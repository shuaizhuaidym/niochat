/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dim.client.util;

/**
 *
 * @author yanming_dai
 */
public class MessageUtil {
    private static String and="&";

    public static String combin(Integer type, String... params) {
        StringBuilder buf = new StringBuilder();
        for (String p : params) {
            buf.append(p).append("type=").append(type);
            buf.append(and);
        }
        return buf.deleteCharAt(buf.length()).toString();
    }
}

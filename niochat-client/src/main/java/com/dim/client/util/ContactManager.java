/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dim.client.util;

import com.dim.client.domain.Contact;
import com.dim.client.gui.WndContactList;
import com.dim.client.msg.MessageReadHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author daiyma
 */
public class ContactManager {

    private static String serverUrl = "http://www.apache.org/";
    private static List<Contact> contacts = new ArrayList<Contact>();
    private static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(ContactManager.class);

    /**
     * 连接服务端获取联系人列表 server return json return list
     */
    public static List<Contact> getContacts() {
        // Create an instance of HttpClient.
        HttpClient client = getHttpClient(false);

        // Create a method instance.
        GetMethod method = new GetMethod(serverUrl);

        // Provide custom retry handler is necessary
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(3, false));

        try {
            // Execute the method.
            logger.info("开始链接服务器");
            int statusCode = 500;//client.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
                logger.info("连接服务器失败！");
            } else {
                // Read the response body.
                byte[] responseBody = method.getResponseBody();
                // Deal with the response.
                // Use caution: ensure correct character encoding and is not binary data
                String friends = new String(responseBody);
            }
            logger.info("获取联系人列表：");
            //System.out.println(new String(responseBody));
            return contacts;
        } catch (HttpException e) {
            System.err.println("Fatal protocol violation: " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.err.println("Fatal transport error: " + e.getMessage());
            return null;
        } finally {
            // Release the connection.
            method.releaseConnection();
        }
    }

    private static HttpClient getHttpClient(boolean proxy) {
        HttpClient httpClient = new HttpClient();
        if (!proxy) {
            return httpClient;
        }
        String proxyHost = "10.1.180.20";
        int proxyPort = 3128;
        String userName = "daiyma";
        String password = "dc_110106";

        HostConfiguration cfg = new HostConfiguration();
        cfg.setProxy(proxyHost, proxyPort);

        UsernamePasswordCredentials cred = new UsernamePasswordCredentials(userName, password);

        httpClient.setHostConfiguration(cfg);
        httpClient.getState().setProxyCredentials(AuthScope.ANY, cred);

        return httpClient;
    }
}

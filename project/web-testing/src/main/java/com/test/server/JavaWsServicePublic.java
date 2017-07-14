/**  
 * 
 * com.test.server  
 * JavaWsServicePublic.java  
 * @author wangxiaoxian
 * 2016年11月30日-下午1:40:21
 */
package com.test.server;

import javax.xml.ws.Endpoint;

import com.test.server.impl.JavaWsServiceImpl;

/**  
 * 
 * @author wangxiaoxian 
 */
public class JavaWsServicePublic {

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8081/testws/", new JavaWsServiceImpl());
    }
}

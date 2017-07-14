/**  
 * 
 * com.test.server.impl  
 * CxfWxServiceImpl.java  
 * @author wangxiaoxian
 * 2016年11月30日-下午5:48:01
 */
package com.test.server.impl;

import com.test.server.CxfWsService;

/**  
 * 
 * @author wangxiaoxian 
 */
public class CxfWxServiceImpl implements CxfWsService {

    /** 
     * {@inheritDoc} 
     */
    @Override
    public String sayHello(String text) {
        System.out.println("你好，" + text);
        return text;
    }

}

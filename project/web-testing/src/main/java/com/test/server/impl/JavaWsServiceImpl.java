/**  
 * 
 * com.test.server.impl  
 * JavaWsServiceImpl.java  
 * @author wangxiaoxian
 * 2016年11月30日-下午1:38:38
 */
package com.test.server.impl;

import javax.jws.WebService;

import com.test.server.JavaWsService;

/**  
 * 
 * @author wangxiaoxian 
 */
@WebService(endpointInterface="com.test.server.JavaWsService")
public class JavaWsServiceImpl implements JavaWsService {

    @Override
    public int add(int x, int y) {
        System.out.println(x+"+"+y+"="+(x+y));
        return x+y;
    }

    @Override
    public int dec(int x, int y) {
        System.out.println(x+"-"+y+"="+(x-y));
        return x-y;
    }

}

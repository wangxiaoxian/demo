package com.test.server;

/**  
 * 
 * @author wangxiaoxian 
 */
public class AxisService {

    public String axisMethod(String param) {
        return "this is in " + AxisService.class.getName() + "#axisMethod, " + param;
    }
}

/**  
 * 
 * com.ws.axis.server  
 * AxisService.java  
 * @author wangxiaoxian
 * 2016年3月23日-下午1:21:56
 */
package com.test.server;

/**  
 * 
 * @author wangxiaoxian 
 */
public class AxisService2 {

    public String axisMethod(String param) {
        return "this is in " + AxisService2.class.getName() + "#axisMethod, " + param;
    }
    
    public String axisMethod2(String param) {
        return "this is in " + AxisService2.class.getName() + "#axisMethod2, " + param;
    }
}

/**  
 * 
 * com.test.server  
 * CxfWsService.java  
 * @author wangxiaoxian
 * 2016年11月30日-下午5:46:59
 */
package com.test.server;

import javax.jws.WebParam;

/**  
 * 
 * @author wangxiaoxian 
 */
public interface CxfWsService {

    String sayHello(@WebParam(name = "text") String text);
}

/**  
 * 
 * com.test.server  
 * JavaWsService.java  
 * @author wangxiaoxian
 * 2016年11月30日-下午1:37:53
 */
package com.test.server;

import javax.jws.WebService;

/**  
 * 
 * @author wangxiaoxian 
 */
@WebService
public interface JavaWsService {
    
    public int add(int x, int y);
    
    public int dec(int x, int y);
}

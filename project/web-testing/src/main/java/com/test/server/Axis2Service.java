/**  
 * 
 * com.test.server  
 * Axis2Service.java  
 * @author wangxiaoxian
 * 2016年11月30日-下午7:25:20
 */
package com.test.server;

import java.util.List;
import java.util.Map;

import com.test.server.domain.User;

/**  
 * 
 * @author wangxiaoxian 
 */
public interface Axis2Service {
    public String sayHelloWorld(String name);
    public List<User> queryFamily(User user);
    public Map<String,List<User>> queryByUser(User user);
    public String meeting();
}

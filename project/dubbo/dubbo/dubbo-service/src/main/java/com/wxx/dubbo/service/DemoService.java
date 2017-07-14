/**  
 * 
 * com.wxx.dubbo.service  
 * DemoService.java  
 * @author wangxiaoxian
 * 2016年5月21日-下午12:34:10
 */
package com.wxx.dubbo.service;

import java.util.List;

import com.wxx.dubbo.entity.User;

/**  
 * 
 * @author wangxiaoxian 
 */
public interface DemoService {
    
    String sayHello(String name);  
    
    public User getUsers();
}

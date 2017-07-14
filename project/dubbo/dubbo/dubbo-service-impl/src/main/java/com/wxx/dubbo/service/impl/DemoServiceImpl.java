/**  
 * 
 * com.wxx.dubbo.service.impl  
 * DemoServiceImpl.java  
 * @author wangxiaoxian
 * 2016年5月21日-下午12:43:11
 */
package com.wxx.dubbo.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.wxx.dubbo.entity.User;
import com.wxx.dubbo.service.DemoService;

/**  
 * 
 * @author wangxiaoxian 
 */
public class DemoServiceImpl implements DemoService {

    /** 
     * {@inheritDoc} 
     */
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

    /** 
     * {@inheritDoc} 
     */
    @Override
    public User getUsers() {
        User u1 = new User();  
        u1.setName("jack");  
        u1.setAge(20);  
        u1.setSex("男");  
        return u1;
//        List<User> list = new ArrayList<User>();  
//        User u1 = new User();  
//        u1.setName("jack");  
//        u1.setAge(20);  
//        u1.setSex("男");  
//          
//        User u2 = new User();  
//        u2.setName("tom");  
//        u2.setAge(21);  
//        u2.setSex("女");  
//          
//        User u3 = new User();  
//        u3.setName("rose");  
//        u3.setAge(19);  
//        u3.setSex("女");  
//          
//        list.add(u1);  
//        list.add(u2);  
//        list.add(u3);  
//        return list;
    }

}

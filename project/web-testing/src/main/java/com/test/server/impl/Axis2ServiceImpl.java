/**  
 * 
 * com.test.server.impl  
 * Axis2ServiceImpl.java  
 * @author wangxiaoxian
 * 2016年11月30日-下午7:25:48
 */
package com.test.server.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.test.server.Axis2Service;
import com.test.server.domain.User;

/**  
 * 
 * @author wangxiaoxian 
 */
@Service("axis2Service")
public class Axis2ServiceImpl implements Axis2Service {
    @Override
    public String sayHelloWorld(String name) {
        String helloWorld = "helloWorld," + name;
        return helloWorld;
    }

    @Override
    public List<User> queryFamily(User user) {
        String name = user.getName();
        int age = user.getAge();
        System.out.println("name:" + name + ",age:" + age + ",address:" + user.getAddress().toString());
        List<String> address = user.getAddress();
        List<User> userList = new ArrayList<User>();
        for(int i=0;i<10;i++){
            User u = new User();
            u.setName(i + "|" + user.getName());
            u.setAge(i + user.getAge());
            u.setAddress(address);
            userList.add(u);
        }
        return userList;
    }

    @Override
    public Map<String, List<User>> queryByUser(User user) {
        String userName = user.getName();
        Map<String,List<User>> dataMap = new HashMap<String,List<User>>();
        List<User> danList = new ArrayList<User>();
        List<User> shuangList = new ArrayList<User>();
        for(int i=0;i<10;i++){
            User u = new User();
            u.setName(i + userName);
            u.setAge(i + user.getAge());
            if(i % 2 == 0){
                shuangList.add(u);
            }else{
                danList.add(u);
            }
        }
        dataMap.put("dan", danList);
        dataMap.put("shuang", shuangList);
        return dataMap;
    }

    @Override
    public String meeting() {
        
        return "Nice Meeting";
    }
}

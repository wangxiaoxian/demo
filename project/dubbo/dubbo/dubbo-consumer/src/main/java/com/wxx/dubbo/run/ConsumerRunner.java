/**  
 * 
 * com.wxx.dubbo.run  
 * ConsumerRunner.java  
 * @author wangxiaoxian
 * 2016年5月21日-下午2:27:28
 */
package com.wxx.dubbo.run;

import java.io.IOException;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wxx.dubbo.entity.User;
import com.wxx.dubbo.service.DemoService;

/**  
 * 
 * @author wangxiaoxian 
 */
public class ConsumerRunner {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(  
                new String[] { "dubbo-consumer-dubbo.xml" });  
        context.start();  
    
        DemoService demoService = (DemoService) context.getBean("demoService");
        String hello = demoService.sayHello("tom"); 
        System.out.println(hello); 
    
        User list = demoService.getUsers();  
        System.out.println(list);
//        if (list != null && list.size() > 0) {  
//            for (int i = 0; i < list.size(); i++) {  
//                System.out.println(list.get(i));  
//            }  
//        }  
        // System.out.println(demoService.hehe());  
//        System.in.read();
    }
}

/**  
 * 
 * com.wxx.dubbo.service.run  
 * Runner.java  
 * @author wangxiaoxian
 * 2016年5月21日-下午2:18:07
 */
package com.wxx.dubbo.service.run;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**  
 * 
 * @author wangxiaoxian 
 */
public class ProviderRunner {
    public static void main(String[] args) throws Exception {  
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] {"dubbo-service-impl-dubbo.xml"});  
        context.start();  
   
        System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟  
    }
}

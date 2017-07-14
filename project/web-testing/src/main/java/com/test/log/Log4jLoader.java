/**  
 * 
 * com.wxx.log4j  
 * Log4jLoader.java  
 * @author wangxiaoxian
 * 2016年11月21日-下午8:13:13
 */
package com.test.log;

import org.apache.log4j.xml.DOMConfigurator;

/**  
 * 
 * @author wangxiaoxian 
 */
public class Log4jLoader {

    public static void main(String[] args) {
        DOMConfigurator.configureAndWatch("", 60000L);
    }
}

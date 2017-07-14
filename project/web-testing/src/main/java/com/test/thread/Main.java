/**  
 * 
 * com.ws.thread  
 * Main.java  
 * @author wangxiaoxian
 * 2016年4月15日-下午2:11:00
 */
package com.test.thread;

/**  
 * 
 * @author wangxiaoxian 
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Runner r = new Runner();
        r.startThread(5);
    }

}

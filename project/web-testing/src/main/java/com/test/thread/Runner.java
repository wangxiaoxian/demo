/**  
 * 
 * com.ws.thread  
 * Runner.java  
 * @author wangxiaoxian
 * 2016年4月15日-下午1:40:05
 */
package com.test.thread;

import java.util.ArrayList;

/**
 * 
 * @author wangxiaoxian
 */
public class Runner implements Runnable {

    private int count = 0;
    
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName());
            count ++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void startThread(int threadNum) throws InterruptedException {
        ArrayList<Thread> threadList = new ArrayList<Thread>();
        for (int i = 0; i < threadNum; i++) {
            Thread thread = new Thread(this);
            thread.setName("thread" + i);
            thread.start();
            threadList.add(thread);
        }
        for (int i = 0; i < threadNum; i++) {
            Thread thread = threadList.get(i);
            thread.join();
        }
        
        System.out.println("子线程全部执行完毕");
        System.out.println(count);
    }
}

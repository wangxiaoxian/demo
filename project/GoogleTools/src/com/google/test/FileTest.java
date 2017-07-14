/**  
 * 
 * com.google.test  
 * FileTest.java  
 * @author wangxiaoxian
 * 2016年4月14日-下午3:44:07
 */
package com.google.test;

import java.io.File;

import org.junit.Test;

/**  
 * 
 * @author wangxiaoxian 
 */
public class FileTest {

    @Test
    public void pathTest() {
        File path = new File("C:\\Users\\Public\\Pictures\\Sample Pictures");
        File[] images = path.listFiles();
        System.out.println(path.length() + "," + path.getName());
        
        if (images != null) {
            for (File file : images) {
                System.out.println(file.getName());
            }
        }
    }
    
    @Test
    public void pathTest2() {
        System.out.println(System.getProperty("user.dir"));
    }
    
}

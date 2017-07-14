/**  
 * 
 * com.ws.io  
 * IoTest.java  
 * @author wangxiaoxian
 * 2016年9月27日-下午8:50:19
 */
package com.test.io;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

/**  
 * 
 * @author wangxiaoxian 
 */
public class IoTest {

    @Test
    public void testFile2ByteFromUrl() throws Exception {
        URL url = new URL("http://img.blog.csdn.net/20150130142527835?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdGFuZzkxNDA=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center");
        URLConnection uc = url.openConnection();
        InputStream is = uc.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
        byte[] temp = new byte[4096];
        int readLength = 0;
        while ((readLength = is.read(temp)) != -1) {
            out.write(temp, 0, readLength);
        }
        byte[] bytes = out.toByteArray();
        System.out.println(bytes);
    }
}

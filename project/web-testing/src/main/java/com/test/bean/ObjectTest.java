/**  
 * 
 * com.ws.bean  
 * ObjectTest.java  
 * @author wangxiaoxian
 * 2016年11月17日-下午9:01:24
 */
package com.test.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**  
 * 
 * @author wangxiaoxian 
 */
public class ObjectTest {
    /** 
     * 执行对象的深复制
     * @param obj
     * @return
     */
    public void deepClone() {
        Object obj = new Object();
        
        ByteArrayOutputStream bo = null;
        ObjectOutputStream oo = null;
        ByteArrayInputStream bi = null;
        ObjectInputStream oi = null;
        try {
            bo = new ByteArrayOutputStream();
            oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);//从流里读出来
            bi = new ByteArrayInputStream(bo.toByteArray());
            oi = new ObjectInputStream(bi);
            System.out.println(oi.readObject());
        } catch (Exception e) {
            return;
        } finally {
            try {
                if (bo != null) {
                    bo.close();
                }
                if (oo != null) {
                    oo.close();
                }
                if (bi != null) {
                    bi.close();
                }
                if (oo != null) {
                    oi.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

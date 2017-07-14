/**  
 * 
 * com.ws.dom  
 * DomTest.java  
 * @author wangxiaoxian
 * 2016年11月3日-上午10:32:05
 */
package com.test.dom;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.test.dom.domain.TestRequest;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**  
 * 
 * @author wangxiaoxian 
 */
public class DomTest {

    @Test
    public void xstreamTest() {
        TestRequest req = new TestRequest();
        req.setId("11");
        req.setName("jack");
        req.setNickname("jack222");
        req.setAge("25");
        
        List<String> lovers = new ArrayList<String>();
        lovers.add("rose1");
        lovers.add("rose2");
        req.setLovers(lovers);
        
        List<String> friends = new ArrayList<String>();
        friends.add("mc1");
        friends.add("mc2");
        req.setFriends(friends);
        
        //设置该初始化对象，则不用引入其他的xstrean依赖包
        XStream xStream = new XStream(new DomDriver("UTF-8", new NoNameCoder()));
        //使得注解生效
        xStream.autodetectAnnotations(true);
        
        String xml = xStream.toXML(req);
        System.out.println(xml);
        
        TestRequest req2 = (TestRequest)xStream.fromXML(xml);
        System.out.println(req2.toString());
    }
}

/**  
 * 
 * com.test.client  
 * WsClient.java  
 * @author wangxiaoxian
 * 2016年11月30日-下午1:36:01
 */
package com.test.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.axis.AxisProperties;
import org.apache.axis.client.Call;
import org.junit.Test;

import com.test.server.JavaWsService;

/**  
 * 
 * @author wangxiaoxian 
 */
public class JavaWsClient {
    
    @Test
    public void wsReqTest() {
        this.sendWsReq(null, null);
    }
    
    public String sendWsReq(String postUrl, String xmlData) {
        URL url = null;
        try {
            url = new URL("http://localhost:8081/testws/?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //浏览器直接访问以上网址，在targetNamespace和name两个属性可以拿到以下的两个值
        QName qName = new QName("http://impl.server.test.com/", "JavaWsServiceImplService");
        Service service = Service.create(url, qName);
        JavaWsService my = service.getPort(JavaWsService.class);
        System.out.println(my.add(1, 2));
        return null;
    }

    @Test
    public void wsAxisReqTest() {
        String resp = sendAxisReq("http://localhost:8080/web-testing/services/axisService?wsdl", "bbb");
        System.out.println(resp);
    }

    /**
     * 使用axis1.4包发送报文
     * @param postUrl
     * @param xmlData
     * @return
     * String
     */
    public String sendAxisReq(String postUrl, String xmlData) {
        String resp = null;
        try {
            org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            Call call = (Call)service.createCall();
            call.setTargetEndpointAddress(new URL(postUrl));

//          根据项目的不同，指定不同的QName
//            call.setOperationName(new QName("namespace_uri", "local_part"));
//            call.setOperationName("axisMethod");
            call.setOperationName(new QName("随便填。。。", "axisMethod"));

            //配置代理
            AxisProperties.setProperty("http.proxyHost", "localhost");
            AxisProperties.setProperty("http.proxyPort", "8888");

            if (postUrl.startsWith("https")) {
                AxisProperties.setProperty("axis.socketSecureFactory","com.ws.client.Clients.EmptyTrustSocketFactory");
            }

            resp = (String)call.invoke(new Object[] {xmlData});

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }
}

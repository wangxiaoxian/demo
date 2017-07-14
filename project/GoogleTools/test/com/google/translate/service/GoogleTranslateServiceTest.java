/**  
 * 
 * com.google.translate.service  
 * GoogleTranslateServiceTest.java  
 * @author wangxiaoxian
 * 2016年4月19日-下午5:49:16
 */ 
package com.google.translate.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.junit.Test;

import com.google.common.HttpClientUtil;

/**  
 * 
 * @author wangxiaoxian 
 */
public class GoogleTranslateServiceTest {

    /**
     * Test method for {@link com.google.translate.service.GoogleTranslateService#queryResults()}.
     * @throws UnsupportedEncodingException 
     */
    @Test
    public void testQueryResults() {
        GoogleTranslateService service = new GoogleTranslateService();
    	service.translate();
    }
    @Test
    public void testQueryResults2() {
    	GoogleTranslateService service = new GoogleTranslateService();
    	
    	String srcResult = HttpClientUtil.sendGet("https://translate.google.com.hk/translate_a/single?oe=UTF-8&dt=t&sl=auto&ssel=3&tl=en&rom=0&client=t&q=%E4%BD%A0%E6%98%AF%E6%88%91%E7%9A%84%E5%B0%8F%E8%8B%B9%E6%9E%9C&kc=0&ie=UTF-8&tsel=4&tk=546296.943159&hl=zh-CN", service.getHeaders());
    	System.out.println(srcResult);
    }
    @Test
    public void testStr() {
        String srcResult = "[[[\"Hello\",\"もしもし\",,,2],[,,,\"Moshimoshi\"]],[[\"感叹词\",[\"Hello!\"],[[\"Hello!\",[\"もしもし！\",\"今日は!\"],,0.37367269]],\"もしもし！\",9]],\"ja\",,,[[\"もしもし\",32000,[[\"Hello\",1000,true,false],[\"Hello The\",0,true,false]],[[0,4]],\"もしもし\",0,0]],1,,[[\"ja\"],,[1],[\"ja\"]]]";
        String subStr = srcResult.substring(4, srcResult.length());
        String targetResult = subStr.substring(0, subStr.indexOf("\""));
        System.out.println(targetResult);
    }
    
    @Test
    public void testUrlEncode() throws UnsupportedEncodingException {
        System.out.println("%D0%B0%D0%B4%D0%B0%D0%BC%20%D0%BB%D0%B0%D0%BC%D0%B1%D0%B5%D1%80%D1%82");
        System.out.println(URLEncoder.encode("адам ламберт", "UTF-8"));
    }

}

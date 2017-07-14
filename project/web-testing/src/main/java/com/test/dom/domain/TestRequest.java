/**  
 * 
 * com.ws.dom.domain  
 * TestRequest.java  
 * @author wangxiaoxian
 * 2016年11月3日-上午10:34:45
 */
package com.test.dom.domain;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**  
 * 
 * @author wangxiaoxian 
 */
@XStreamAlias("Request")
public class TestRequest {
    
    @XStreamAsAttribute//当做标签的属性而不是标签
    private String id;
    @XStreamOmitField//不序列化该属性
    private String age;
    @XStreamAlias("Name")
    private String name;
    private String nickname;
    
    @XStreamImplicit//不输出数组的标签，而是直接输出数组的内容作为标签
    private List<String> lovers;  
    private List<String> friends;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<String> getLovers() {
        return lovers;
    }
    public void setLovers(List<String> lovers) {
        this.lovers = lovers;
    }
    public List<String> getFriends() {
        return friends;
    }
    public void setFriends(List<String> friends) {
        this.friends = friends;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }  
}

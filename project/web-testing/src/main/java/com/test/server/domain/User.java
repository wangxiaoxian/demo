/**  
 * 
 * com.test.server.domain  
 * User.java  
 * @author wangxiaoxian
 * 2016年11月30日-下午7:24:16
 */
package com.test.server.domain;
import java.io.Serializable;
import java.util.List;
/**  
 * 
 * @author wangxiaoxian 
 */
public class User implements Serializable{

    private static final long serialVersionUID = 1L;
    private String name;
    private int age;
    private List<String> address;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public List<String> getAddress() {
        return address;
    }
    public void setAddress(List<String> address) {
        this.address = address;
    }
}

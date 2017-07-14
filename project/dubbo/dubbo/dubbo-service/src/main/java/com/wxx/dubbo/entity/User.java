/**  
 * 
 * com.wxx.dubbo.entity  
 * User.java  
 * @author wangxiaoxian
 * 2016年5月21日-下午12:37:51
 */
package com.wxx.dubbo.entity;

import java.io.Serializable;

/**  
 * 
 * @author wangxiaoxian 
 */
public class User implements Serializable {

    private static final long serialVersionUID = 2262741834880902471L;
    private String name;
    private Integer age;
    private String sex;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
}

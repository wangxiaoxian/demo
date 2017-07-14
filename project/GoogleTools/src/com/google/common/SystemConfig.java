package com.google.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SystemConfig {

    public static String getProperty(String key) {
        String configPath = System.getProperty("user.dir") + "/config/system.properties";
        Properties prop = null;
        try {
            prop = new Properties();
            InputStream is = new FileInputStream(configPath);
            prop.load(is);
        } catch (FileNotFoundException e) {
            System.out.println("找不到系统配置文件:" + configPath);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("系统配置文件解析出错:" + configPath);
            e.printStackTrace();
        }
        return prop.getProperty(key);
    }
}

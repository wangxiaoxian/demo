package com.test.client;

/**
 * Created by wangxiaoxian on 2017/3/8.
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 说明
 * 利用httpclient下载文件
 * maven依赖
 * <dependency>
 *           <groupId>org.apache.httpcomponents</groupId>
 *           <artifactId>httpclient</artifactId>
 *           <version>4.0.1</version>
 *       </dependency>
 *  可下载http文件、图片、压缩文件
 *  bug：获取response header中Content-Disposition中filename中文乱码问题
 * @author tanjundong
 *
 */
public class Downloader {

    public static final int cache = 10 * 1024;
    public static final boolean isWindows;
    public static final String splash;
    public static final String root;
    static {
        if (System.getProperty("os.name") != null && System.getProperty("os.name").toLowerCase().contains("windows")) {
            isWindows = true;
            splash = "\\";
            root="D:";
        } else {
            isWindows = false;
            splash = "/";
            root="/search";
        }
    }

    /**
     * 根据url下载文件，文件名从response header头中获取
     * @param url
     * @return
     */
    public static String download(String url) {
        return download(url, null);
    }

    /**
     * 根据url下载文件，保存到filepath中
     * @param url
     * @param filepath
     * @return
     */
    public static String download(String url, String filepath) {
        CloseableHttpClient client = null;
        try {
            client = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = client.execute(httpget);

            HttpEntity entity = response.getEntity();
            byte[] fileByte = EntityUtils.toByteArray(entity);

            if (filepath == null)
                filepath = getFilePath(response);
            File file = new File(filepath);
            file.getParentFile().mkdirs();

            FileOutputStream fileOut = new FileOutputStream(file);
            fileOut.write(fileByte);
            fileOut.flush();
            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    /**
     * 获取response要下载的文件的默认路径
     * @param response
     * @return
     */
    public static String getFilePath(HttpResponse response) {
        String filepath = root + splash;
        String filename = getFileName(response);

        if (filename != null) {
            filepath += filename;
        } else {
            filepath += getRandomFileName();
        }
        return filepath;
    }
    /**
     * 获取response header中Content-Disposition中的filename值
     * @param response
     * @return
     */
    public static String getFileName(HttpResponse response) {
        Header contentHeader = response.getFirstHeader("Content-Disposition");
        String filename = null;
        if (contentHeader != null) {
            HeaderElement[] values = contentHeader.getElements();
            if (values.length == 1) {
                NameValuePair param = values[0].getParameterByName("filename");
                if (param != null) {
                    try {
                        //filename = new String(param.getValue().toString().getBytes(), "utf-8");
                        //filename=URLDecoder.decode(param.getValue(),"utf-8");
                        filename = param.getValue();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return filename;
    }
    /**
     * 获取随机文件名
     * @return
     */
    public static String getRandomFileName() {
        return String.valueOf(System.currentTimeMillis());
    }
    public static void outHeaders(HttpResponse response) {
        Header[] headers = response.getAllHeaders();
        for (int i = 0; i < headers.length; i++) {
            System.out.println(headers[i]);
        }
    }
    public static void main(String[] args) {
        String url="http://pic.sc.chinaz.com/files/pic/pic9/201410/apic7065.jpg";
        String filePath = "D:\\tmp\\img\\a.jpg";
        Downloader.download(url, filePath);
    }
}

package com.test.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.Test;

public class FTPTest {

    @Test
    public void download() {
        FTPClient ftpClient = new FTPClient();
        try {
            //编码设置成服务器的编码，该设置必须在连接建立之前
            ftpClient.setControlEncoding("GB18030");
            //连接FTP服务器
            ftpClient.connect("10.1.4.60", 21);
            //登录FTP服务器
            ftpClient.login("zfftp", "zfftp");
            //验证FTP服务器是否登录成功
            int replyCode = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(replyCode)){
                return;
            }
            //切换FTP目录
            ftpClient.changeWorkingDirectory("/opt/aspire/product/zfftp/utboss/file/2016-11-11");
            
//            ftpClient.changeWorkingDirectory(new String("/opt/aspire/product/zfftp/utboss/file/2016-11-11/广东".getBytes(), "GB18030"));
            ftpClient.changeWorkingDirectory("/opt/aspire/product/zfftp/utboss/file/2016-11-11/广东");
            System.out.println(ftpClient.getCharsetName());
            System.out.println(ftpClient.printWorkingDirectory());
            String[] ftpFiles = ftpClient.listNames();
            for(String fileName : ftpFiles){
                File localFile = new File("D:\\utboss\\ftpdownload\\wait\\" + fileName);
                OutputStream os = new FileOutputStream(localFile);
                ftpClient.retrieveFile(fileName, os);
                os.close();
            }
            ftpClient.logout();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(ftpClient.isConnected()){
                try {
                    ftpClient.logout();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

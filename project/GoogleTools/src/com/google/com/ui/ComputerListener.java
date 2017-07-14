package com.google.com.ui;

import java.awt.event.*;
import javax.swing.*;

import com.google.com.start.Run;
public class ComputerListener implements ActionListener {
   JTextField inputPath,inputThreadNum; 
   JTextArea textShow;
   String fuhao;
   public void setJTextFieldOne(JTextField t) {
       inputPath = t;
   }
   public void setJTextFieldTwo(JTextField t) {
       inputThreadNum = t;
   }
   public void setJTextArea(JTextArea area) {
       textShow = area;
   }
   public void setFuhao(String s) {
       fuhao = s;
   }
   public void actionPerformed(ActionEvent e) {
      try {
    	  	String path=inputPath.getText();
    	  	Integer threadNum=Integer.parseInt(inputThreadNum.getText());
    	  	Run run=new Run();
//    		run.setPath("f:\\test");
//    		run.start(4);
    		run.setPath(path);
    		run.start(threadNum);
            textShow.append("查找成功\n");
      }
      catch(Exception exp) {
             textShow.append("检查网络和文件路径和线程数\n");
             textShow.append("文件路径中的xls和txt和图片是否正在使用！！！！！！！！！！！！！！！\n");
      }         
   }
}

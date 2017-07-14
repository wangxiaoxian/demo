package com.google.com.start;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import org.apache.http.client.ClientProtocolException;

import com.google.com.ui.PicSearch;

public class Start {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws WriteException 
	 * @throws BiffException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	      PicSearch win=new PicSearch();
	      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	      win.setBounds(screenSize.width/3,screenSize.height/4,500,390);
	      win.setTitle("图片搜索");
	}

}

package com.google.sever;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class IOSever {
	

	public void createXls(String path,ArrayList<String []> picMessage) throws IOException, WriteException{
		FileOutputStream fileOut = new FileOutputStream(path+"\\图片数据.xls");
		WritableWorkbook mWorkbook = Workbook.createWorkbook(fileOut);
		WritableSheet mSheet = mWorkbook.createSheet("图片数据", 0);
		Label mLabel = new Label(0, 0, "图片名");
		mSheet.addCell(mLabel);
		Label mLabe2 = new Label(1, 0, "图片描述");
		mSheet.addCell(mLabe2);
		Label mLabe3 = new Label(2, 0, "网页标题1");
		mSheet.addCell(mLabe3);
		Label mLabe4 = new Label(3, 0, "网页标题2");
		mSheet.addCell(mLabe4);
		for(int i=0;i<picMessage.size();i++){
			mSheet.addCell(new Label(0,i+1,picMessage.get(i)[0]));
			mSheet.addCell(new Label(1,i+1,picMessage.get(i)[1]));
			mSheet.addCell(new Label(2,i+1,picMessage.get(i)[2]));
			mSheet.addCell(new Label(3,i+1,picMessage.get(i)[3]));
		}
		mWorkbook.write();
		mWorkbook.close();
	}
	
	
	public void wirteFile(String str,String path,String fileName) {
		{
			try {
				File aFile = new File(path+fileName);// 指定存储文件名
				// 建立输出流
				FileOutputStream out = new FileOutputStream(aFile);
				byte[] b = new byte[5000];
				b = str.getBytes("UTF-8");   // 进行String到byte[]的转化
				out.write(b);         // 写入文本内容
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}
	
	
	public String readFile(File file){
		if(null == file ) return null;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			//		      BufferedReader fileReader = new BufferedReader(new FileReader(file));                            //默认字符编码:GBK
			//   BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));//默认字符编码:GBK
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8")); 
			String line = fileReader.readLine();
			while(null != line){
				stringBuffer.append(line);
				line = fileReader.readLine();
			}
			fileReader.close();//读完要及时关闭连接
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(file.getName()+"读取文件出错."+e.getMessage());
		}
		return stringBuffer.toString();
	}
}

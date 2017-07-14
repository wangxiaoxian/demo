package com.google.test;

import java.io.IOException;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

import com.google.com.start.Run;

public class Test {

	public static void main(String[] args) throws WriteException, BiffException, IOException, InterruptedException{
		// TODO Auto-generated method stub	
		Run run=new Run();
		run.setPath("C:\\Users\\Public\\Pictures\\Sample Pictures");//文件路径 ，其中\要用\\表示
		run.start(5);//线程数

	}

}

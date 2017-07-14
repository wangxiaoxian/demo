package com.google.com.start;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;


import com.google.sever.IOSever;
import com.google.sever.SearchSever;

public class Run implements Runnable {
	
	private int picNo = 0;
	private String path;
	private ArrayList<String []> picMessage=new ArrayList<String []>();
	private String unfindPic="";
	private String netProblem="";
	
	private File[] files = null;
	private Boolean[] lockQueue = null;
	
	public String getNetProblem() {
		return netProblem;
	}

	public void setNetProblem(String netProblem) {
		this.netProblem = netProblem;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
		
		File file = new File(path);
        files = file.listFiles();
        lockQueue = new Boolean[files.length];
        for (int i = 0; i < lockQueue.length; i++) {
            lockQueue[i] = false;
        }
	}

	public int getPicNo() {
		return picNo;
	}

	public synchronized int getPicNo(File[] files){
		if(picNo < files.length){
			return picNo++;
		}
		return files.length;
	}	
	
	public boolean isPic(String name){
		 if(name.trim().toLowerCase().endsWith(".bmp")) {
			  return true;
			 }
		 if(name.trim().toLowerCase().endsWith(".gif")) {
			  return true;
			 }
		 if(name.trim().toLowerCase().endsWith(".tif ")) {
			  return true;
			 }
		 if(name.trim().toLowerCase().endsWith(".webp")) {
			  return true;
			 }
		 if(name.trim().toLowerCase().endsWith(".jpg")) {
			  return true;
			 }
		 if(name.trim().toLowerCase().endsWith(".png")) {
			  return true;
			 }
		 return false;
	}
	
	@Override
	public void run() {
		
        for(int i=0; i < files.length; i++) {
            if (lockQueue[i]) {
                continue;
            }
			lockQueue[i] = true;
			
			SearchSever sever = new SearchSever();
			File curFile = files[i];
			if(!isPic(curFile.getName())){ 
				continue;
			}
			String threadName = Thread.currentThread().getName();
			System.out.println("线程：" + threadName + " 开始处理文件:" + curFile.getName());
			String html = null;
			html = sever.getPicHtml(curFile);
			if(html == "false"){
				netProblem += curFile.getName() + "\r\n";
				continue;
			}
			//	        System.out.println(html);
			String url = sever.getUrl(html);
			System.out.println("线程：" + threadName + " 对" + curFile.getName() + " 的搜索结果：" + url);
			html = sever.getPicMessageHtml(url);
			if(html == "false"){
			    System.out.println(curFile.getName() + " 出现网络问题");
				netProblem += curFile.getName()+"\r\n";
				continue;
			}
	//		wirteFile(html);
			String[] s = sever.searchPicMessage(html, curFile, path);
			if(s == null) {
			    System.out.println(curFile.getName() + " 没搜索到结果");
				unfindPic += curFile.getName()+"\r\n";
			} else {
				picMessage.add(s);
			}
        }
        
	}
	
	public void start(int threadNum) throws WriteException, IOException, BiffException, InterruptedException{
		ArrayList<Thread> threadList=new ArrayList<Thread>();
		for(int i=0;i<threadNum;i++){
			Thread thread=new Thread(this);
			thread.setName("thread"+i);
			thread.start();
			threadList.add(thread);
		}
		for(int i=0;i<threadNum;i++){
			Thread thread = threadList.get(i);
			thread.join();
		}
		IOSever io=new IOSever();
		io.createXls(path, picMessage);
		io.wirteFile(unfindPic, path,"\\未查找到的图片.txt");
		io.wirteFile(netProblem, path,"\\网络问题未查找的图片.txt");
//		for(int i=0;i<picMessage.size();i++)
//			System.out.println(picMessage.get(i)[0]+"   "+picMessage.get(i)[1]);
	}
	

	
}

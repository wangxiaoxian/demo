package com.google.translate.service;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import com.google.common.HttpClientUtil;
import com.google.common.SystemConfig;

public class GoogleTranslateService {

	@Test
    public void translate() {
        String srcUrl = SystemConfig.getProperty("google.translate.url");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("client", "t");
        params.put("sl", "auto");
        params.put("tl", "en");
        params.put("hl", "zh-CN");
        
        List<String> dt = new ArrayList<String>();
        dt.add("at");
        dt.add("bd");
        dt.add("ex");
        dt.add("ld");
        dt.add("md");
        dt.add("qca");
        dt.add("rw");
        dt.add("rm");
        dt.add("ss");
        dt.add("t");
        params.put("dt", dt);
        params.put("ie", "UTF-8");
        params.put("oe", "UTF-8");
        params.put("rom", "0");
        params.put("ssel", "0");
        params.put("tsel", "0");
        params.put("kc", "0");
        params.put("tk", SystemConfig.getProperty("google.translate.tk"));
        
//        List<String> srcData = this.readExcel();
        List<String> srcData = new ArrayList<String>();
        srcData.add("你是我的小苹果");
        srcData.add("もしもし");
        srcData.add("Bonjour");
        
        List<String> resultData = new ArrayList<String>();
        try {
			for (String src : srcData) {
				
				params.put("q", URLEncoder.encode(src, "UTF-8").replace("+", "%20"));
				String url = HttpClientUtil.appendQueryParam(srcUrl, params);
				String srcResult = HttpClientUtil.sendGet(url, this.getHeaders());
				
				String result = this.getResult(srcResult);
				System.out.println("--" + result);
				resultData.add(result);
			}
//			this.writeExcel(srcData, resultData);
		} catch (UnsupportedEncodingException e) {
			System.err.println("不支持的编码类型");
		}
    }
    
    private String getResult(String srcResult) {
        if (srcResult.length() < 4) {
            return srcResult;
        }
        String subStr = srcResult.substring(4, srcResult.length());
        String targetResult = subStr.substring(0, subStr.indexOf("\""));
		return targetResult;
	}

	public List<String> readExcel() {
    	String srcFilePath = SystemConfig.getProperty("google.translate.src.file").trim();
    	
        Workbook wb = null;  
        
        InputStream stream = null;
        List<String> result = new ArrayList<>();
        try {
        	stream = new FileInputStream(srcFilePath);  
            if (srcFilePath.endsWith(".xls")) {  
                wb = new HSSFWorkbook(stream);  
            } else if (srcFilePath.endsWith(".xlsx")) {  
                wb = new XSSFWorkbook(stream);  
            } else {  
                System.err.println("您输入的excel格式不正确"); 
                return null;
            }  
            
            Sheet sheet1 = wb.getSheetAt(0);  
            
            for (Row row : sheet1) {  
                for (Cell cell : row) {  
                    String srcStr = cell.getStringCellValue();
                    if (srcStr != null && srcStr.trim().length() != 0) {
                    	result.add(srcStr);
					}
                }  
            }
        } catch (FileNotFoundException e) {
        	System.err.println("找不到文件，请检查文件路径是否正确");
		} catch (IOException e) {
			System.err.println("读取文件出错，请检查文件是否可以正常打开");
		} finally {
            if (wb != null) {
                try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
            if (stream != null) {
            	try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
        }
        return result;
    }
    
    public void writeExcel(List<String> srcData, List<String> resultData) {
    	String targetFilePath = SystemConfig.getProperty("google.translate.target.file").trim();
    	HSSFWorkbook newWb = null;
    	FileOutputStream fileOut = null;
    	
    	try {
			newWb = new HSSFWorkbook(); //建立新HSSFWorkbook对象
			HSSFSheet sheet = newWb.createSheet("new sheet"); //建立新的sheet对象
			
			for (int i = 0; i < srcData.size(); i++) {
				HSSFRow row = sheet.createRow(i);
				row.createCell(0).setCellValue(srcData.get(i));
				row.createCell(1).setCellValue(resultData.get(i));
			}
			fileOut = new FileOutputStream(targetFilePath);
			newWb.write(fileOut);
		} catch (FileNotFoundException e) {
			System.err.println("找不到文件，请检查文件路径是否正确");
		} catch (IOException e) {
			System.err.println("生成结果文件异常");
		} finally {
			if (newWb != null) {
            	try {
            		newWb.close();
            	} catch (IOException e) {
            		e.printStackTrace();
            	}
            }
            if (fileOut != null) {
            	try {
            		fileOut.close();
            	} catch (IOException e) {
            		e.printStackTrace();
            	}
            }
		}
	}

	public Header[] getHeaders() {
        Header[] headers = new Header[]{
        		new BasicHeader("Accept", "*/*"),
        		new BasicHeader("Accept-Encoding", "gzip, deflate, sdch"),
                new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4,ja;q=0.2"),
                new BasicHeader("Cache-Control", "no-cache"),
                new BasicHeader("Connection", "keep-alive"),
                new BasicHeader("Host", "translate.google.com.hk"),
                new BasicHeader("Referer", "https://translate.google.com.hk/?hl=zh-CN&tab=wT"),
                new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36")
        };
        
        return headers;
    }
}

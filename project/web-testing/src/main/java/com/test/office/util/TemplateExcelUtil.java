package com.test.office.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.ss.usermodel.Workbook;

/** 
 * 模板excel导出操作
 * @author wangxiaoxian
 * @version 1.0.0 
 * @since 1.0.0  
 */
public class TemplateExcelUtil {

	/**
	 * 将excel文件写入输出流
	 * @param excelTemplateFilePath excel模板路径
	 * @param beans 数据
	 * @param outStream 文件输出流
	 * @throws IOException
	 */
	public static void writeExcelToStream(String excelTemplateFilePath, Map<String, ?> beans, OutputStream outStream) throws Exception {
		InputStream in = null;
		Workbook workbook = null;
		try {
			in = new BufferedInputStream(new FileInputStream(new File(excelTemplateFilePath)));
			XLSTransformer transformer = new XLSTransformer();
			workbook = transformer.transformXLS(in, beans);
			workbook.write(outStream);
			outStream.flush();
			workbook = null;
		} catch (Exception e) {
			throw e;
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	/**
	 * 输出xls
	 * @param request
	 * @param response
	 * @param fileName
	 * @param beans
	 * @param excelTemplateFilePath
	 * @throws IOException
	 */
	public static void writeExcelToRequest(HttpServletRequest request, HttpServletResponse response, String fileName, Map<String, ?> beans, String excelTemplateFilePath) throws Exception {
		response.reset();

		//设置响应头信息为excel输出
		setContentTypeByExcel(request, response, fileName);

		//获取响应输出流
		ServletOutputStream stream = response.getOutputStream();
		//写出excel数据
		writeExcelToStream(excelTemplateFilePath, beans, stream);
		stream.flush();
		stream.close();
	}

	/**
	 * 设置响应头信息为excel输出
	 * @param request
	 * @param response
	 * @param fileName
	 * @throws UnsupportedEncodingException 
	 */
	public static void setContentTypeByExcel(HttpServletRequest request, HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
		fileName += ".xls";
		//针对IE, 需要通过URLEncoder对文件名进行UTF8编码
		if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0 || request.getHeader("User-Agent").indexOf("like Gecko") > 0) {
			fileName = URLEncoder.encode(fileName, "UTF-8");
		}
		//其他的浏览器(firefox, chrome, safari, opera), 则要通过字节转换成ISO8859-1
		else {
			fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
		}

		response.setContentType("application/ms-excel;charset=UTF-8");
		response.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");
		response.addHeader("Pargam", "no-cache");
		response.addHeader("Cache-Control", "no-cache");
	}
}

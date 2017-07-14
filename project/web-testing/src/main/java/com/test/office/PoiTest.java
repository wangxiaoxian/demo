/**  
 * 
 * com.ws.office  
 * ExcelHandler.java  
 * @author wangxiaoxian
 * 2016年4月15日-上午10:44:19
 */
package com.test.office;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

/**  
 * 
 * @author wangxiaoxian 
 */
public class PoiTest {

    private final String JDBC_URL = "jdbc:oracle:thin:@//112.4.10.121:1521/jshome"; 
    private final String JDBC_USERNAME = "js_test"; 
    private final String JDBC_PASSWORD = "js_test"; 
    private final String JDBC_CLASSNAME = "oracle.jdbc.driver.OracleDriver";
    
    /**
     * 读取excel到数据库
     * 
     * @throws IOException 
     * void
     * @throws ClassNotFoundException 
     */
    @Test
    public void readExcelToDb() throws Exception {
        String filePath = "C:\\Users\\wangxiaoxian.ASPIRE\\Desktop\\phonenum_sectiono.xls";
        InputStream stream = new FileInputStream(filePath);  
        Workbook wb = null;  
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName(JDBC_CLASSNAME);
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
            conn.setAutoCommit(false);
            
            if (filePath.endsWith(".xls")) {  
                wb = new HSSFWorkbook(stream);  
            } else if (filePath.endsWith(".xlsx")) {  
                wb = new XSSFWorkbook(stream);  
            } else {  
                System.out.println("您输入的excel格式不正确"); 
                return;
            }
            String insert = " insert into T_PHONENUM_SECTION( "
                    + " PHONENUM_ID, BEGIN_MSISDN, END_MSISDN, CSP_TYPE, AREA_CODE, BUSINESS_TYPE, PROVINCE_CODE, HOME_TYPE, BEGIN_DATE, END_DATE) values ( "
                    + " SEQ_PHONENUM_SECTION.nextval,?,?,?,?,?,?,?,to_date(?,'yyyyMMddHH24miss'),to_date(?,'yyyyMMddHH24miss')) ";
            pstmt = conn.prepareStatement(insert);
            Sheet sheet1 = wb.getSheetAt(0);
            for (Row row : sheet1) {
                if (row.getRowNum() == 0) {
                   continue; 
                }
                
                String checkRecord = row.getCell(1).getStringCellValue();
                if (checkRecord == null || checkRecord.trim().length() == 0) {
                    break;
                }
                
                pstmt.setString(1, row.getCell(1).getStringCellValue());
                pstmt.setString(2, row.getCell(2).getStringCellValue());
                pstmt.setString(3, row.getCell(3).getStringCellValue());
                pstmt.setString(4, row.getCell(4).getStringCellValue());
                pstmt.setString(5, row.getCell(5).getStringCellValue());
                pstmt.setString(6, row.getCell(6).getStringCellValue());
                pstmt.setString(7, row.getCell(7).getStringCellValue());
                
                String beginHH = row.getCell(8).getStringCellValue().substring(8, 10);
                if (Integer.parseInt(beginHH) <= 23 && Integer.parseInt(beginHH) >= 0) {
                    pstmt.setString(8, row.getCell(8).getStringCellValue());
                } else {
                    System.err.print(row.getCell(1).getStringCellValue() + " ");
                    System.err.println(row.getCell(8).getStringCellValue());
                    pstmt.setString(8, "20000101015959");
                }
                
                String endHH = row.getCell(9).getStringCellValue().substring(8, 10);
                if (Integer.parseInt(endHH) <= 23 && Integer.parseInt(endHH) >= 0) {
                    pstmt.setString(9, row.getCell(9).getStringCellValue());
                } else {
                    System.err.print(row.getCell(1).getStringCellValue() + " ");
                    System.err.println(row.getCell(9).getStringCellValue());
                    pstmt.setString(9, "20990101010000");
                }
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            conn.commit();
        } finally {
            if (wb != null) {
                wb.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    @Test
    public void readExcel() throws IOException {
        String filePath = "C:\\Users\\wangxiaoxian.ASPIRE\\Desktop\\1.xls";
        InputStream stream = new FileInputStream(filePath);  
        Workbook wb = null;  
        try {
            if (filePath.endsWith(".xls")) {  
                wb = new HSSFWorkbook(stream);  
            } else if (filePath.endsWith(".xlsx")) {  
                wb = new XSSFWorkbook(stream);  
            } else {  
                System.out.println("您输入的excel格式不正确"); 
                return;
            }  
            Sheet sheet1 = wb.getSheetAt(0);  
            for (Row row : sheet1) {  
                for (Cell cell : row) {  
                    System.out.print(cell.getNumericCellValue() + "  ");  
                }  
                System.out.println();  
            }
        } finally {
            if (wb != null) {
                wb.close();
            }
        }
    }
    
    @Test
    public void writeExcel() {
        HSSFWorkbook wb = new HSSFWorkbook(); //建立新HSSFWorkbook对象
        HSSFSheet sheet = wb.createSheet("new sheet"); //建立新的sheet对象
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0); 
        cell.setCellValue(1);
        row.createCell(1).setCellValue(1.2); //设置cell浮点类型的值
        row.createCell(2).setCellValue("test"); //设置cell字符类型的值
        row.createCell(3).setCellValue(true); //设置cell布尔类型的值 
        HSSFCellStyle cellStyle = wb.createCellStyle(); //建立新的cell样式
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
        //设置cell样式为定制的日期格式
        HSSFCell dCell =row.createCell(4);
        dCell.setCellValue(new Date()); //设置cell为日期类型的值
        dCell.setCellStyle(cellStyle); //设置该cell日期的显示格式
        HSSFCell csCell =row.createCell(5);
//        csCell.setEncoding(HSSFCell.ENCODING_UTF_16);
        //设置cell编码解决中文高位字节截断
        csCell.setCellValue("中文测试_Chinese Words Test"); //设置中西文结合字符串
        row.createCell(6).setCellType(HSSFCell.CELL_TYPE_ERROR);
        //建立错误cell
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("C:\\Users\\wangxiaoxian.ASPIRE\\Desktop\\workbook.xls");
            wb.write(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            wb.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void editExcel() throws Exception {
        String filePath = "E:\\iSimTest_20161009.xls";
        InputStream stream = new FileInputStream(filePath);  
        Workbook wb = null;  
        try {
            if (filePath.endsWith(".xls")) {  
                wb = new HSSFWorkbook(stream);  
            } else if (filePath.endsWith(".xlsx")) {  
                wb = new XSSFWorkbook(stream);  
            } else {  
                System.out.println("您输入的excel格式不正确"); 
                return;
            }
            Sheet sheet0 = wb.getSheetAt(0);  
            Row row1 = sheet0.getRow(1);
            row1.getCell(0).setCellValue("111");
            
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\wangxiaoxian.ASPIRE\\Desktop\\workbook.xls");
            wb.write(fileOut);
        } finally {
            if (wb != null) {
                wb.close();
            }
        }
    }
}

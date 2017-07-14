/**  
 * 
 * com.ws.office  
 * Jxls1Test.java  
 * @author wangxiaoxian
 * 2016年4月26日-下午6:12:09
 */
package com.test.office;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

/**  
 * jxls 1.X
 * @author wangxiaoxian 
 */
public class Jxls1Test {

    @Test
    public void createByTamplateWithTag() {
        
    }
    
    @Test
    public void createByTamplate() {
        // 获取Excel模板文件
        String filePath = "C:/Users/wangxiaoxian.ASPIRE/Desktop/tamplate.xls";
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);

            // 创建测试数据
            List<Map<String, Object>> oneSheetData = new ArrayList<Map<String, Object>>();

            Map<String, Object> data1 = new HashMap<String, Object>();
            data1.put("name", "电视");
            data1.put("price", "3000");
            data1.put("desc", "3D电视机");
            data1.put("备注", "中文测试");

            Map<String, Object> data2 = new HashMap<String, Object>();
            data2.put("name", "空调");
            data2.put("price", "2000");
            data2.put("desc", "变频空调");
            data2.put("备注", "测试中文");

            oneSheetData.add(data1);
            oneSheetData.add(data2);

            List<List<Map<String, Object>>> allSheetData = new ArrayList<List<Map<String, Object>>>();
            // sheet的名称
            List<String> sheetNames = new ArrayList<String>();
            
            sheetNames.add("sheetName1");
            allSheetData.add(oneSheetData);
            
            sheetNames.add("sheetName2");
            allSheetData.add(oneSheetData);
            
            sheetNames.add("sheetName3");
            allSheetData.add(oneSheetData);
            
            sheetNames.add("sheetName4");
            allSheetData.add(oneSheetData);

            // 调用引擎生成excel报表
            XLSTransformer transformer = new XLSTransformer();
            Workbook workbook = transformer.transformMultipleSheetsList(fis,
                    allSheetData, sheetNames, "list", new HashMap<String, Object>(), 0);
            workbook.write(new FileOutputStream(
                    "C:/Users/wangxiaoxian.ASPIRE/Desktop/result.xls"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParsePropertyException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

/*
 * Copyright 2015-2020 Aspire Technologies, Inc. All rights reserved. 
 */
package com.test.office.util;

import org.apache.commons.lang3.StringUtils;

/** 
 * 导出excel的时候，处理值的转换
 * @author wangxiaoxian
 * @version 1.0.0 
 * @since 1.0.0  
 */
public class ConvertValue4Excel {

	public String convertCmnet(String cmnet) {
		if (StringUtils.isBlank(cmnet)) {
			return null;
		}
		String result = null;
		if ("1".equals(cmnet)) {
			result = "是";
		} else if ("2".equals(cmnet)) {
			result = "否";
		}
		return result;
	}
	
	public String convertCmwap(String cmwap) {
		if (StringUtils.isBlank(cmwap)) {
			return null;
		}
		String result = null;
		if ("1".equals(cmwap)) {
			result = "是";
		} else if ("2".equals(cmwap)) {
			result = "否";
		}
		return result;
	}
	
	public String convertUpDownFlag(String upDownFlag) {
		if (StringUtils.isBlank(upDownFlag)) {
			return null;
		}
		String result = null;
		if ("1".equals(upDownFlag)) {
			result = "下行";
		} else if ("2".equals(upDownFlag)) {
			result = "上行";
		} else if ("3".equals(upDownFlag)) {
			result = "下行宽度";
		}
		return result;
	}
	
	public String convertApn(String apn) {
		if (StringUtils.isBlank(apn)) {
			return null;
		}
		String result = null;
		if ("1".equals(apn)) {
			result = "CMNET";
		} else if ("2".equals(apn)) {
			result = "CMWAP";
		}
		return result;
	}
}

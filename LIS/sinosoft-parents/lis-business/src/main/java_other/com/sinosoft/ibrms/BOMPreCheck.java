package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

/**
 * Copyright (c) 2008 sinosoft Co. Ltd. All right reserved.
 */

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.regex.*;

import com.sinosoft.utility.*;

public class BOMPreCheck {
private static Logger logger = Logger.getLogger(BOMPreCheck.class);

	/**
	 * @param args
	 */
	public CErrors mErrors = new CErrors();
	/** 数据操作字符串 */
	
	
	// 校验是否为空
	public boolean CheckNotNull(String value) {
		if (value.length() == 0 && value == null) {
			CError.buildErr(this, value+"不能为空!");
			return false;
		} else {
			return true;
		}
	}
	
	// 必须是正整数
	public boolean CheckPI(Double value) {
		return this.CheckPlus(value)&&this.CheckIngeter(value);
	}
	// 必须是正数
	public boolean CheckPI(String value) {
//		if(value==null||"".equals(value)){
//			CError.buildErr(this, value+"只能为正数!");			
//			return false;
//		}
		Pattern regex = Pattern.compile("^[0-9]{1,}\\.{0,1}[0-9]{0,}[E|e]{0,1}[-]{0,1}[0-9]{0,}");
		Matcher matcher = regex.matcher(value);
		boolean isMatched = matcher.matches();
		if (!isMatched) {
			CError.buildErr(this, value+"只能为正数!");			
			return false;
		}
		return true;
	}
	
	// 检验CODE值是否有效
	public boolean CheckSequence(String value) {
	/*	String sqlSource = "select source from lrbomitem where name='"+value+"'";
		String temsql = new ExeSQL().getOneValue(sqlSource);
		String sql = temsql+" and code='"+value+"'";
		String result =  new ExeSQL().getOneValue(sql);
		if(result==null&&result.length()==0){
			CError.buildErr(this, value+"CODE值无效!");	
			return false;
		}*/
		return true;
	}
	

	// 校验是否为英文
	public boolean CheckEngLish(String value) {
		Pattern regex = Pattern.compile("[a-zA-Z]*");
		Matcher matcher = regex.matcher(value);
		boolean isMatched = matcher.matches();
		if (!isMatched) {
			//logger.debug("不是英文");
			CError.buildErr(this, value+"只能为英文!");
			return false;
		} else {
			//logger.debug("是英文");
			return true;
		}
	}

	// 必须是大于等于0的数
	public boolean CheckPlus(Double value) {
		if(value==null){
			return false;
		}
		return value.doubleValue()>=0;
	}
	// 必须是正数
	public boolean CheckPlus(String value) {
//		if(value==null||"".equals(value)){
//			CError.buildErr(this, value+"只能为正数!");			
//			return false;
//		}
		Pattern regex = Pattern.compile("^[0-9]{1,}\\.{0,1}[0-9]{0,}[E|e]{0,1}[-]{0,1}[0-9]{0,}");
		Matcher matcher = regex.matcher(value);
		boolean isMatched = matcher.matches();
		if (!isMatched) {
			CError.buildErr(this, value+"只能为正数!");			
			return false;
		}
		return true;
	}
 
	// 必须是整数
	public boolean CheckIngeter(Double value) {
		if(value==null){
			return false;
		}
		BigDecimal bigDecimal = new BigDecimal(value);  
        String result = bigDecimal.toString();
		Pattern regex = Pattern.compile("^[0-9]\\d*\\.?0*$");
		Matcher matcher = regex.matcher(result);
		boolean isMatched = matcher.matches();
		if (!isMatched) {
			CError.buildErr(this, value+"只能为整数!");
			return false;
		}
		return true;	
	}
	// 必须是整数
	public boolean CheckIngeter(String value) {
		Pattern regex = Pattern.compile("^[0-9]\\d*\\.?0*$");
		Matcher matcher = regex.matcher(value);
		boolean isMatched = matcher.matches();
		if (!isMatched) {
			CError.buildErr(this, value+"只能为整数!");
			return false;
		}
		return true;	
	}
	// 校验是否为中文
	public boolean CheckChinese(String value) {
		Pattern regex = Pattern.compile("[\u4e00-\u9fa5]*");
		Matcher matcher = regex.matcher(value);
		boolean isMatched = matcher.matches();
		if (!isMatched) {
			CError.buildErr(this, value+"只能为中文!");
			//logger.debug("value不是中文");
			return false;
		}
		//logger.debug("value是中文");
		return true;
	}

	// 校验日期
	public boolean CheckDate(String value) {
		return true;
	}

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		//new BOMPreCheck().CheckPlus("2.2");
		//new BOMPreCheck().CheckEngLish("士大夫");
	}
}

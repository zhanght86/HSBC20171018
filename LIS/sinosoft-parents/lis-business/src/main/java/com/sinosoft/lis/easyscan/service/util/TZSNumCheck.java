package com.sinosoft.lis.easyscan.service.util;
import org.apache.log4j.Logger;

public class TZSNumCheck {
private static Logger logger = Logger.getLogger(TZSNumCheck.class);
	public static String checkTZSNum(String doccode){
		if(doccode.startsWith("89"))
			return "UN101";
		if(doccode.startsWith("90"))
			return "UN102";
		if(doccode.startsWith("03"))
			return "UN103";
		if(doccode.startsWith("14"))
			return "UN104";
		if(doccode.startsWith("04"))
			return "UN105";
		throw new IllegalArgumentException("doccode error");
	}
	
}

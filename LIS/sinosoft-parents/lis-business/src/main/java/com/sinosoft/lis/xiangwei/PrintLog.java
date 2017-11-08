package com.sinosoft.lis.xiangwei;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PrintLog {
private static Logger logger = Logger.getLogger(PrintLog.class);
	private static String logFile = null;
	
	
	
	public static String getLogFile()
	{
		return logFile;
	}



	public static void setLogFile(String logFile)
	{
		PrintLog.logFile = logFile;
	}



	public static void printLog(String s)
	{
		Date dat = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("<<yyyy/MM/dd  hh:mm:ss>> ", Locale.US);
		logger.debug(sdf.format(dat)+s);
	}
}

package com.sinosoft.easyscan5.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PubFun {
	
	
	/**
	 * 将字符串补数,将sourString的<br>
	 * 前面</br>用cChar补足cLen长度的字符串,如果字符串超长，则不做处理
	 * <p>
	 * <b>Example: </b>
	 * <p>
	 * <p>
	 * LCh("Minim", "0", 10) returns "00000Minim"
	 * <p>
	 * 
	 * @param sourString
	 *            源字符串
	 * @param cChar
	 *            补数用的字符
	 * @param cLen
	 *            字符串的目标长度
	 * @return 字符串
	 */
	public static String addChar(String sourString, String cChar, int cLen) {
		int tLen = sourString.length();
		int i, iMax;
		String tReturn = "";
		if (tLen >= cLen) {
			return sourString;
		}
		iMax = cLen - tLen;
		for (i = 0; i < iMax; i++) {
			tReturn += cChar;
		}
		tReturn = tReturn.trim() + sourString.trim();
		return tReturn;
	}	

	/**
	 * 将字符串补数,将sourString的<br>
	 * 前面</br>用cChar补足cLen长度的字符串,如果字符串超长，则不做处理
	 * <p>
	 * <b>Example: </b>
	 * <p>
	 * <p>
	 * LCh("Minim", "0", 10) returns "00000Minim"
	 * <p>
	 * 
	 * @param sourString
	 *            源字符串
	 * @param cChar
	 *            补数用的字符
	 * @param cLen
	 *            字符串的目标长度
	 * @return 字符串
	 */
	public static String LCh(String sourString, String cChar, int cLen) {
		int tLen = sourString.length();
		int i, iMax;
		String tReturn = "";
		if (tLen >= cLen) {
			return sourString;
		}
		iMax = cLen - tLen;
		for (i = 0; i < iMax; i++) {
			tReturn += cChar;
		}
		tReturn = tReturn.trim() + sourString.trim();
		return tReturn;
	}
	
	
	   //add by liushuang   20131014    将InputStream转换为Byte[]
    public static byte[] InputStreamTOByte(InputStream in) throws IOException {   
     
       ByteArrayOutputStream outStream = new ByteArrayOutputStream();   
    	 
         byte[] data = new byte[4096];   
      
      int count = -1;   
    
            while ((count = in.read(data, 0, 4096)) != -1)   
  
          outStream.write(data, 0, count);   
    
          data = null;   
   
        return outStream.toByteArray();   
     
      } 
 
     // 将byte数组转换成InputStream   
      public static InputStream byteTOInputStream(byte[] in) throws Exception {   
 
        ByteArrayInputStream tByteArrayInputStream = new ByteArrayInputStream(in);   
        
        return tByteArrayInputStream;   

    }  
      
      
      /**
       * 得到当前系统日期 author: YT
       * @return 当前日期的格式字符串,日期格式为"yyyy-MM-dd"
       */
      public static String getCurrentDate()
      {
          String pattern = "yyyy-MM-dd";
          SimpleDateFormat df = new SimpleDateFormat(pattern);
          Date today = new Date();
          String tString = df.format(today);
          return tString;
      }

      /**
       * 得到当前系统时间 author: YT
       * @return 当前时间的格式字符串，时间格式为"HH:mm:ss"
       */
      public static String getCurrentTime()
      {
          String pattern = "HH:mm:ss";
          SimpleDateFormat df = new SimpleDateFormat(pattern);
          Date today = new Date();
          String tString = df.format(today);
          return tString;
      }
      /**
  	 * 获取基础路径
  	 * 如/D:/workspace2/ImageNew/WebRoot/
  	 * @return
  	 */
  	public static String getServerBasePath(){
  		URL url = PubFun.class.getResource(""); 
  		String path = url.getPath();  
  		path = path.substring(0, path.lastIndexOf("WEB-INF"));  
  		String serverBasePath = path;
  		return serverBasePath;
  	}
}

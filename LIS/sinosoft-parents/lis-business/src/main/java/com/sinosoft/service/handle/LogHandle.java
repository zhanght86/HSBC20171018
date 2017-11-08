package com.sinosoft.service.handle;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.service.Handle;
import com.sinosoft.service.IMessage;
import com.sinosoft.service.exception.ServiceException;
import com.sinosoft.utility.TransferData;
/**
* <p>Description: log日志文件</p>
*
* <p>Copyright: Copyright (c) 2008</p>
*
* <p>Company: sinosoft</p>
*
* @author litao
* @version 1.0
* */
public class LogHandle implements Handle {
private static Logger logger = Logger.getLogger(LogHandle.class);
	/**文件输出流*/
	private OutputStream out;
	/**初始化log日志文件*/
	public void init(TransferData transfer) {
		//直接输出到log4j中
//		String fileName = (String) transfer.getValueByName("file");
//		if(fileName==null || fileName.equals(""))
//		{
//			logger.error("日志文件名没有传入");
//			throw new ServiceException("日志文件名没有传入");
//			
//		}
//		try
//		{
//			File tFile = new File(fileName);
//			out = new FileOutputStream(tFile);
//		}
//		catch(Exception ex)
//		{
//			logger.error("日志文件"+fileName+"出错");
//			throw new ServiceException("日志文件"+fileName+"出错");
//		}
	}
	/**将请求服务的日期时间及请求服务名,写入log日志*/
	public void invoke(IMessage message) {
		String des = message.getBusiName();
		des = PubFun.getCurrentDate()+": :"+PubFun.getCurrentTime()+":BusiName:"+des+"\n";
		
		try{
			logger.debug(des);
			//out.write(des.getBytes());
			//out.flush();
		}
		catch(Exception ex)
		{
			logger.error("日志文件出错:"+ex.getMessage());
			throw new ServiceException("日志文件出错:"+ex.getMessage());
		}
	}

}

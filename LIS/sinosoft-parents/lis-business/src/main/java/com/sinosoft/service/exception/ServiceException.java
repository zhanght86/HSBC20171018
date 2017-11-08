package com.sinosoft.service.exception;
import org.apache.log4j.Logger;

import java.io.PrintStream;
/**
 * service瀵瑰簲寮傚父澶勭悊
 *  * <p>Description: 杞彂璁捐</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: sinosoft</p>
 *
 * @author litao
 * @version 1.0
 * */
public class ServiceException extends RuntimeException {
private static Logger logger = Logger.getLogger(ServiceException.class);
	public ServiceException(String msg)
	{
		super(msg);
	}
	public ServiceException(String msg,Throwable tExcep)
	{
		super(msg,tExcep);
	}
}

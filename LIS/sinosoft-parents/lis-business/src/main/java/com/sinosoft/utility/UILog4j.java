/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.utility;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;

import java.util.Vector;

public class UILog4j implements BusinessService{
	private static Logger  logger = Logger.getLogger(UILog4j.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public UILog4j() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		String tMsg = cInputData.get(0)==null?"":(String)cInputData.get(0);
		String tPage = cInputData.get(1)==null?"":(String)cInputData.get(1);
		if(cOperate!=null&&cOperate.toLowerCase().equals("debug"))
		{
			if(!"".equals(tPage))
			{
				//logger =  Logger.getLogger(tPage);
				logger.debug("("+tPage+ ") " +tMsg);
			}
			else
			{
				logger.debug(tMsg);
			}
		}
		else if(cOperate.toLowerCase().equals("error"))
		{
			if(!"".equals(tPage))
			{
				//logger =  Logger.getLogger(tPage);
				logger.error("("+tPage+ ") " +tMsg);
			}
			else
			{
				logger.error(tMsg);
			}
		}
		return true;
	}
	
	public VData getResult() {
		return mInputData;
	}


	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}

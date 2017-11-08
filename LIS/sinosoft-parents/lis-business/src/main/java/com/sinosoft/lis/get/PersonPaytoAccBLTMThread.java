package com.sinosoft.lis.get;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.service.CovBase;
import com.sinosoft.utility.CErrors;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.taskservice.taskinstance.ProIndSignMThread;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.tb.TbWorkFlowBL;
import com.sinosoft.workflow.tb.TbWorkFlowBLS;

/**
 * <p>
 * Title:生存金入帐户
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author tongmeng
 * @version 6.5
 */

public class PersonPaytoAccBLTMThread extends CovBase {
	private static Logger logger = Logger.getLogger(PersonPaytoAccBLTMThread.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private GlobalInput tGI=new GlobalInput();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private Vector mInputDataNew;
	/** 数据操作字符串 */
	private String mOperate;
	private HttpServletRequest httprequest;
	int nSuccCount = 0; 
	int ErrCount =0;
	public PersonPaytoAccBLTMThread() {
	}

	public static void main(String[] args) {

		
	}
	public void setObject(Object tObject) {
		System.out.println(tObject);
		//多线程的外部参数条件
		mInputDataNew = (Vector) tObject;
	}
	
	public void run() {
		
		VData mResult = new VData(); 
		MMap tMMap = new MMap();
		for(int i=0;i<this.mInputDataNew.size();i++)
		{
			
			Map tMap = new HashMap();
			tMap = (Map)mInputDataNew.get(i);
			VData mInputData = new VData();
//			mInputData.add(tMap.get("LCContSchema"));
			mInputData.add(tMap.get("TransferData"));
			mInputData.add(tMap.get("GlobalInput"));


//			LCContSchema tLCContSchema=(LCContSchema)tMap.get("LCContSchema");
			tGI=(GlobalInput)tMap.get("GlobalInput");
			PersonPaytoAccBL tPersonPaytoAccBL = new PersonPaytoAccBL();
			if (!tPersonPaytoAccBL.submitData(mInputData, "ContNo")) {
 
				ErrCount++;
				CError.buildErr(this, "生存金入帐户失败,原因是:" + tPersonPaytoAccBL.mErrors.getFirstError());
				logger.debug("生存金入帐户失败,原因是:" + tPersonPaytoAccBL.mErrors.getFirstError());
				continue;
			} 
			else
			{
				nSuccCount++;
			}
//			 日志监控,状态监控                 		
		}
		  logger.debug("\t   生存金入帐户失败: " + nSuccCount + " 成功; " + ErrCount + " 失败。");
//			日志监控,结果监控
	      PubFun.logResult (tGI,tGI.LogID[1],"生存金入帐户失败: " + nSuccCount + " 成功; " + ErrCount + " 失败。");
	      logger.debug("\t@> PersonPaytoAccBLTMThread.run() : 生存金入帐户失败完毕");

		this.close();
	}

	/**
	 * 传输数据的公共方法
	 */
	public VData getResult() {
		return mResult;
	}

}

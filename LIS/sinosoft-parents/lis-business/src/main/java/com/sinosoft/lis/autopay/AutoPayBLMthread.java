package com.sinosoft.lis.autopay;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.lis.operfee.RnDealBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LZSysCertifySchema;
import com.sinosoft.service.CovBase;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:自动垫交批处理-多线程版本
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
 * @author zhangfh
 * @version 6.5
 */

public class AutoPayBLMthread extends CovBase {
private static Logger logger = Logger.getLogger(AutoPayBLMthread.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	private GlobalInput tGI = new GlobalInput();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private Vector mInputDataNew;
	/** 数据操作字符串 */
	private String mOperate;
	int nSuccCount = 0; 
	int ErrCount =0;
	private HttpServletRequest httprequest;
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();	
	public AutoPayBLMthread() {
	}

	public static void main(String[] args) {

		
	}
	public void setObject(Object tObject) {
		//多线程的外部参数条件
		mInputDataNew = (Vector) tObject;
	}
	
	public void run() {
		MMap tMMap = new MMap();
		for(int i=0;i<this.mInputDataNew.size();i++)
		{
			
			Map tMap = new HashMap();
			tMap = (Map)mInputDataNew.get(i);
			VData mInputData = new VData();
			mInputData.add(tMap.get("LCContSchema"));
			mInputData.add(tMap.get("CalDate"));
			mInputData.add(tMap.get("GlobalInput"));
			tGI=(GlobalInput)tMap.get("GlobalInput");
			int ErrCount = 0;
			LCContSchema tLCContSchema=(LCContSchema)tMap.get("LCContSchema");
	
			AutoPayBL tAutoPayBL=new AutoPayBL();
			if (!tAutoPayBL.submitData(mInputData, "ContNo")) {
					this.mErrors.copyAllErrors(tAutoPayBL.getErrors());
//				日志监控,状态监控          
				PubFun.logState(tGI,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"自动垫交批处理，原因是:"+mErrors.getFirstError(),"0");
				ErrCount++;
				continue;
			} else {
				
				mResult.clear();
				mResult = tAutoPayBL.getResult();
				PubSubmit tSubmit = new PubSubmit();
				if (!tSubmit.submitData(mResult, "")) {
					// @@错误处理
					ErrCount++;
					this.mErrors.copyAllErrors(tSubmit.mErrors);
					return;
				}
				else
				{
					nSuccCount++;
				}
			
//				日志监控,状态监控          
				mPubLock.unLock();
			}

		}
		  logger.debug("\t   本次数据清理结果统计: " + nSuccCount + " 成功; " + ErrCount + " 失败。");
//			日志监控,结果监控
	      PubFun.logResult (tGI,tGI.LogID[1],"自动垫交批处理: " + nSuccCount + " 成功; " + ErrCount + " 失败。");
	      logger.debug("\t@> RnDealBLMthread.run() : 自动垫交批处理任务完毕");
		this.close();

}

	

	/**
	 * 传输数据的公共方法
	 */
	public VData getResult() {
		return mResult;
	}

}

package com.sinosoft.lis.get;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.service.CovBase;
import com.sinosoft.utility.CErrors;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.tb.TbWorkFlowBL;
import com.sinosoft.workflow.tb.TbWorkFlowBLS;

/**
 * <p>
 * Title:红利分配批处理-多线程版本
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

public class BonusAssignBLTMThread extends CovBase {
	private static Logger logger = Logger.getLogger(BonusAssignBLTMThread.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private GlobalInput tGI = new GlobalInput();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private Vector mInputDataNew;
	/** 数据操作字符串 */
	private String mOperate;
	private HttpServletRequest httprequest;
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
    private  int tSuc = 0, tFail = 0;
	public BonusAssignBLTMThread() {
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
			mInputData.add(tMap.get("LOBonusPolSet"));
			mInputData.add(tMap.get("LCContSchema"));
			mInputData.add(tMap.get("TransferData"));
			mInputData.add(tMap.get("GlobalInput"));

			int ErrCount = 0;
			tGI=(GlobalInput)tMap.get("GlobalInput");
			LCContSchema tLCContSchema=(LCContSchema)tMap.get("LCContSchema");
			BonusAssignBL tBonusAssignBL = new BonusAssignBL();
			if (!tBonusAssignBL.submitData(mInputData, "ContNo")) {
				logger.debug(mErrors.getFirstError()
						+ " 红利分配处理失败，保单号：" + tLCContSchema.getContNo());
				tFail++;
				 // 日志监控,状态监控
				PubFun.logState(tGI,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发失败","0");
				 // 日志监控,过程监控
				PubFun.logTrack (tGI,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发结束");
				continue;
			} else {
				tMMap.add((MMap)tBonusAssignBL.getResult().get(0));
			}
//			 数据提交
			mResult.clear();
			mResult = tBonusAssignBL.getResult();
			if (mResult.size() > 0) {
				PubSubmit tSubmit = new PubSubmit();
				if (!tSubmit.submitData(mResult, "")) {
					// @@错误处理
					this.mErrors.copyAllErrors(tSubmit.mErrors);
					tFail++;
//					 日志监控,状态监控
					PubFun.logState(tGI,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发失败","0");
					 // 日志监控,过程监控
					PubFun.logTrack (tGI,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发结束");
					continue;
				}
			}
			tSuc++;
			logger.debug("*********************保单红利分配完成！保单号："
					+ tLCContSchema.getContNo()
					+ "*********************");
			 // 日志监控,状态监控
			PubFun.logState(tGI,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发成功","1");
			 // 日志监控,过程监控
			PubFun.logTrack (tGI,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"红利派发结束");
			mPubLock.unLock();
		}
		 logger.debug("\t   红利分配批处理统计: " + tSuc + " 成功; " + tFail + " 失败。");
//	      日志监控,结果监控
	     PubFun.logResult (tGI,tGI.LogID[1],"红利分配批处理: " + tSuc + " 成功; " + tFail + " 失败。");
	     logger.debug("\t@> RnDealBLMthread.run() : 红利分配批处理任务完毕");
		 this.close();
	}

	/**
	 * 传输数据的公共方法
	 */
	public VData getResult() {
		return mResult;
	}

}

package com.sinosoft.lis.operfee;

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
import com.sinosoft.lis.taskservice.taskinstance.RnewPolAutoTMThread;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.tb.TbWorkFlowBL;
import com.sinosoft.workflow.tb.TbWorkFlowBLS;

/**
 * <p>
 * Title:个单续期续保自动催收任务-多线程版本
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

public class RnDealBLMthread extends CovBase {
	private static Logger logger = Logger.getLogger(RnDealBLMthread.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
    private GlobalInput tG = new GlobalInput();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private Vector mInputDataNew;
	/** 数据操作字符串 */
	private String mOperate;
	private HttpServletRequest httprequest;
	int nSuccCount = 0; 
	int ErrCount =0;
	public RnDealBLMthread() {
	}

	public static void main(String[] args) {

		
	}
	public void setObject(Object tObject) {
		logger.debug(tObject);
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
			mInputData.add(tMap.get("LCContSchema"));
			mInputData.add(tMap.get("StartDate"));
			mInputData.add(tMap.get("EndDate"));
			mInputData.add(tMap.get("TransferData"));
			mInputData.add(tMap.get("GlobalInput"));
			tG=(GlobalInput)tMap.get("GlobalInput");
			LCContSchema tLCContSchema=(LCContSchema)tMap.get("LCContSchema");
			RnDealBL tRnDealBL = new RnDealBL();
			if (!tRnDealBL.submitData(mInputData, "ContNo")) {
					this.mErrors.copyAllErrors(tRnDealBL.mErrors);
//				日志监控,状态监控          
				PubFun.logState(tG,tLCContSchema.getContNo(),"保单"+tLCContSchema.getContNo()+"续期抽档失败，原因是:"+mErrors.getFirstError(),"0");
				ErrCount++;
				continue;
			} else {
				tMMap.add((MMap)tRnDealBL.getResult().get(0));
			}

			mResult.add(tMMap);
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(mResult, "")) {
				ErrCount++;
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				mResult.clear();
			}
			else
			{
				nSuccCount++;
			}
		}
        logger.debug("\t   本次数据清理结果统计: " + nSuccCount + " 成功; " + ErrCount + " 失败。");
//		日志监控,结果监控
        PubFun.logResult (tG,tG.LogID[1],"个单续期续保自动催收任务: " + nSuccCount + " 成功; " + ErrCount + " 失败。");
        logger.debug("\t@> RnDealBLMthread.run() : 个单续期续保自动催收任务完毕");
		
//		TransferData mTransferData = new TransferData();
//		mTransferData = mInputDataNew.getObjectByObjectName("TransferData", 0)==null?null
//				:(TransferData)mInputDataNew.getObjectByObjectName("TransferData", 0);
//		if(mTransferData!=null)
//		{
//			String tActivityid = (String)mTransferData.getValueByName("ActivityID");
//			submitData(mInputDataNew, tActivityid);
//		}
		this.close();
	}

	/**
	 * 传输数据的公共方法
	 */
	public VData getResult() {
		return mResult;
	}

}

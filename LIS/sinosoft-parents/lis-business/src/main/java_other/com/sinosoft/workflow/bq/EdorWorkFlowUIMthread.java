package com.sinosoft.workflow.bq;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LZSysCertifySchema;
import com.sinosoft.service.CovBase;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * <p>
 * Title:保全工作流-多线程版本
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

public class EdorWorkFlowUIMthread extends CovBase {
private static Logger logger = Logger.getLogger(EdorWorkFlowUIMthread.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private Vector mInputDataNew;
	/** 数据操作字符串 */
	private String mOperate;
	private GlobalInput tG = new GlobalInput();
	private HttpServletRequest httprequest;
	int nSuccCount = 0; 
	int nFailCount =0;
	String MissionProp1="";
	public EdorWorkFlowUIMthread() {
	}

	public static void main(String[] args) {

		
	}
	public void setObject(Object tObject) {
		//多线程的外部参数条件
		mInputDataNew = (Vector) tObject;
	}
	
	public void run() {
		
		TransferData mTransferData = new TransferData();
		MMap tMMap = new MMap();
		for(int i=0;i<this.mInputDataNew.size();i++)
		{
			 
			Map tMap = new HashMap();
			tMap = (Map)mInputDataNew.get(i);
			VData tVData = new VData();
			
			tVData.add(tMap.get("tLPEdorAppSchema"));
			tVData.add(tMap.get("TransferData"));
			tVData.add(tMap.get("GlobalInput"));
			
			tG =(GlobalInput)tMap.get("GlobalInput");
			mTransferData =(TransferData)tMap.get("TransferData");
			MissionProp1=(String)mTransferData.getValueByName("MissionProp1");
			String tActivityid = (String)mTransferData.getValueByName("ActivityID");
			submitData(tVData, tActivityid);

	    }
        logger.debug("\t@> PEdorValidTask.EdorTestFinish() : 保全批处理工作流段处理完毕");
        logger.debug("\t   本次数据清理结果统计: " + nSuccCount + " 成功; " + nFailCount + " 失败。");
//		日志监控,结果监控
		PubFun.logResult (tG,tG.LogID[1],"保全批处理工作流段处理完毕: " + nSuccCount + " 成功; " + nFailCount + " 失败。");
		this.close();
	}

	
	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI();

		logger.debug("---EdorWorkFlowUI  BEGIN---");
		if (tEdorWorkFlowUI.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			 nFailCount ++;
             logger.debug("\t@> PEdorValidTask.EdorTestFinish() : 保全批处理工作流段处理完毕, 一条纪录清理失败");
             logger.debug("\t   失败纪录对应的保全受理号: " + MissionProp1);
             logger.debug("\t   该记录清理失败的原因: " + tEdorWorkFlowUI.mErrors.getFirstError());
		}
		else
        {
            nSuccCount ++;
            logger.debug("ss");
        }
		mResult = tEdorWorkFlowUI.getResult();
		return true;
	}

	/**
	 * 传输数据的公共方法
	 */
	public VData getResult() {
		return mResult;
	}

}

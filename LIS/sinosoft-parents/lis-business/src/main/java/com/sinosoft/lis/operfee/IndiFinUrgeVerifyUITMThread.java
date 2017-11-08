package com.sinosoft.lis.operfee;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.taskservice.taskinstance.RnewAutoVerifyTMThread;
import com.sinosoft.service.CovBase;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.tb.TbWorkFlowBL;
import com.sinosoft.workflow.tb.TbWorkFlowBLS;

/**
 * <p>
 * Title:续期续保核销自动运行程序-多线程版本
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

public class IndiFinUrgeVerifyUITMThread extends CovBase {
	private static Logger logger = Logger.getLogger(IndiFinUrgeVerifyUITMThread.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private Vector mInputDataNew;
	private GlobalInput tG = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;
	int nSuccCount = 0; 
	int ErrCount =0;
	private HttpServletRequest httprequest;

	public IndiFinUrgeVerifyUITMThread() {
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
			mInputData.add(tMap.get("LJTempFeeSchema"));
			mInputData.add(tMap.get("GlobalInput"));
		
			tG=(GlobalInput)tMap.get("GlobalInput");
			IndiFinUrgeVerifyUI mIndiFinUrgeVerifyUI = new IndiFinUrgeVerifyUI();
			if (!mIndiFinUrgeVerifyUI.submitData(mInputData, "VERIFY")) {
				ErrCount++;
				System.out.println(mIndiFinUrgeVerifyUI.mErrors.getFirstError());
	
			}
			else
			{
				nSuccCount++;
				tMMap.add((MMap)mIndiFinUrgeVerifyUI.getResult().get(0));
				//mResult.add(mRnDealBL.getResult());
			}
			
		}
		mResult.add(tMMap);
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			mResult.clear();
			
		}
		
		logger.debug("\t   续期续保核销自动运行程序: " + nSuccCount + " 成功; " + ErrCount + " 失败。");
//	     日志监控,结果监控
	    PubFun.logResult (tG,tG.LogID[1],"续期续保核销自动运行程序: " + nSuccCount + " 成功; " + ErrCount + " 失败。");
	    logger.debug("\t@> IndiFinUrgeVerifyUITMThread.run() : 续期续保核销自动运行程序完毕");
		this.close();
	}

	
	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		TbWorkFlowBL tTbWorkFlowBL = new TbWorkFlowBL();

		System.out.println("---TbWorkFlowBL UI BEGIN---");
		if (tTbWorkFlowBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tTbWorkFlowBL.mErrors);
			mResult.clear();
			return false;
		}
		mResult = tTbWorkFlowBL.getResult();
		
		if("0000001150".equals(cOperate))
		{
			//保單打印邏輯
			TransferData tTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
			String tContNo = (String) tTransferData.getValueByName("ContNo");
			tTransferData.setNameAndValue("ContNo",tContNo);
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql("select appflag from lccont where contno='?tContNo?'");
			sqlbv.put("tContNo", tContNo);
			String appFlag = new ExeSQL().getOneValue(sqlbv);
			if("1".equals(appFlag))
			{
				VData tVData = new VData();
				tVData.add(tTransferData);
				GlobalInput tGlobalInput = new GlobalInput();
				tGlobalInput.Operator="AUTO";
				tGlobalInput.ManageCom="HK";
				tVData.add(tGlobalInput);
//				LCContPrintBL tLCContPrintBL = new LCContPrintBL();
//				tLCContPrintBL.submitData(tVData,"");
			}
		}
		
		return true;
	}

	/**
	 * 传输数据的公共方法
	 */
	public VData getResult() {
		return mResult;
	}

}

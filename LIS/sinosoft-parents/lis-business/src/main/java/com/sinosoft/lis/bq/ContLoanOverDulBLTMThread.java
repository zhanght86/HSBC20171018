package com.sinosoft.lis.bq;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.service.CovBase;
import com.sinosoft.utility.CErrors;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOLoanSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.tb.TbWorkFlowBL;
import com.sinosoft.workflow.tb.TbWorkFlowBLS;

/**
 * <p>
 * Title:保单欠款逾期催付处理
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

public class ContLoanOverDulBLTMThread extends CovBase {
	private static Logger logger = Logger.getLogger(ContLoanOverDulBLTMThread.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private Vector mInputDataNew;
	/** 数据操作字符串 */
	private String mOperate;
	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	private HttpServletRequest httprequest;
	private MMap map = new MMap();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
    private  int tSuc = 0, tFail = 0;
    private GlobalInput tGI;
	public ContLoanOverDulBLTMThread() {
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
			mInputData.add(tMap.get("LOLoanSchema"));
			mInputData.add(tMap.get("GlobalInput"));
			int ErrCount = 0;
			LOLoanSchema tLOLoanSchema=(LOLoanSchema)tMap.get("LOLoanSchema");
			tGI=(GlobalInput)tMap.get("GlobalInput");
			if (!DealOneLoan(tLOLoanSchema)) {
				logger.debug(mErrors.getFirstError()
						+ " 保单欠款催付处理，保单号：" + tLOLoanSchema.getContNo());
				tFail++;
				// 日志监控,状态监控                 		
     			PubFun.logState(tGI,tLOLoanSchema.getEdorNo(), "保单"+tLOLoanSchema.getContNo()+"的"+tLOLoanSchema.getEdorNo()+"保全批次借款逾期，催付处理失败","0");  
				continue;
			}
			map.put(mLOPRTManagerSet, "DELETE&INSERT");
			mResult.add(map);
			if (mResult.size() > 0) {
				PubSubmit tSubmit = new PubSubmit();
				if (!tSubmit.submitData(mResult, "")) {
					// @@错误处理
					this.mErrors.copyAllErrors(tSubmit.mErrors);
					tFail++;
					continue;
				}
			}
			mLOPRTManagerSet = null;
			mResult.clear();
			tSuc++;
			logger.debug("*********************保单欠款催付处理！保单号："
					+ tLOLoanSchema.getContNo()
					+ "*********************");
//			 日志监控,状态监控                 		
 			PubFun.logState(tGI,tLOLoanSchema.getEdorNo(), "保单"+tLOLoanSchema.getContNo()+"的"+tLOLoanSchema.getEdorNo()+"保全批次借款逾期，催付处理成功","1"); 
		

		}
	      logger.debug("\t@> PEdorValidTask.EdorTestFinish() : 保单欠款逾期催付处理完毕");
	        logger.debug("\t   保单欠款逾期催付处理: " + tSuc + " 成功; " + tFail + " 失败。");
//			日志监控,结果监控
			PubFun.logResult (tGI,tGI.LogID[1],"保单欠款逾期催付处理完毕: " + tSuc + " 成功; " + tFail + " 失败。");
			this.close();
	}

	private boolean DealOneLoan(LOLoanSchema tLOLoanSchema)
	{
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(tLOLoanSchema.getContNo());
		if (tLCContDB.getInfo() == false) {
			buildError("DealOneLoan", "获得保单数据失败!");
			return false;
		}
		LCContSchema mLCContSchema = tLCContDB.getSchema(); 
	      //日志监控,过程监控        
    	PubFun.logTrack(tGI,tLOLoanSchema.getEdorNo(),"生成"+tLOLoanSchema.getContNo()+"保单的"+tLOLoanSchema.getEdorNo()+"保全批次借款逾期催付通知书");
		InsertPRT(mLCContSchema, tLOLoanSchema.getNewLoanDate(),tLOLoanSchema.getLeaveMoney());
		
		return true;
	}
	/**
	 * 往打印管理表插入万能险终止通知书打印记录
	 * 
	 * @param pLCInsureAccSchema
	 * @return boolean
	 */
	private boolean InsertPRT(LCContSchema pLCContSchema, String tLoanDate,double tSumLoanMoney) {
		LOPRTManagerSchema tLOPRTManagerSchema;
		try {

			tLOPRTManagerSchema = new LOPRTManagerSchema();
			String strNoLimit = PubFun.getNoLimit(pLCContSchema.getManageCom());
			String sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
			tLOPRTManagerSchema.setPrtSeq(sPrtSeq);
			tLOPRTManagerSchema.setOtherNo(pLCContSchema.getContNo());
			tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 险种号

			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PEdorOverDulLoanPay); // 打印类型
			tLOPRTManagerSchema.setStandbyFlag1(String.valueOf(tSumLoanMoney));// 存放借款本金
			tLOPRTManagerSchema.setStandbyFlag2(tLoanDate);// 存放借款日期
			tLOPRTManagerSchema.setManageCom(pLCContSchema.getManageCom()); // 管理机构
			tLOPRTManagerSchema.setAgentCode(pLCContSchema.getAgentCode()); // 代理人编码
			tLOPRTManagerSchema.setReqCom(tGI.ManageCom);
			tLOPRTManagerSchema.setReqOperator(tGI.Operator);

			tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
			tLOPRTManagerSchema.setStateFlag("0"); // 打印状态
			tLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
			tLOPRTManagerSchema.setMakeDate(mCurrentDate);
			tLOPRTManagerSchema.setMakeTime(mCurrentTime);

			tLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);
			mLOPRTManagerSet.add(tLOPRTManagerSchema);
		} catch (Exception e) {
			CError.buildErr(this, "插入万能险结算报告书失败!");
		}
		return true;
	}
	/**
	 * 错误构建方法
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "InsuAccBala";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
   
	/**
	 * 传输数据的公共方法
	 */
	public VData getResult() {
		return mResult;
	}

}

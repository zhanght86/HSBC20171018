package com.sinosoft.lis.bq;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LZSysCertifySchema;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.service.CovBase;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:保全客户延期未响应逾期终止批处理
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

public class PEdorOverDueBLTMThread extends CovBase {
private static Logger logger = Logger.getLogger(PEdorOverDueBLTMThread.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
    private GlobalInput tG = new GlobalInput();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	LPEdorAppSchema tLPEdorAppSchema;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private Vector mInputDataNew;
	int nSuccCount = 0; 
	int nFailCount =0;
	/** 数据操作字符串 */
	private String mOperate;
	private HttpServletRequest httprequest;

	public PEdorOverDueBLTMThread() {
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
			VData mInputData = new VData();
			mInputData.add(tMap.get("tLPEdorAppSchema"));
			mInputData.add(tMap.get("TransferData"));
			mInputData.add(tMap.get("GlobalInput"));
			
			
			tLPEdorAppSchema =(LPEdorAppSchema)tMap.get("LPEdorAppSchema");
			submitData(mInputData, "");
			
			this.close();
		}
		logger.debug("\t@> PEdorValidTask.EdorTestFinish() : 保全批处理工作流段处理完毕");
	        logger.debug("\t   本次数据清理结果统计: " + nSuccCount + " 成功; " + nFailCount + " 失败。");
//			日志监控,结果监控
		PubFun.logResult (tG,tG.LogID[1],"保全批处理工作流段处理完毕: " + nSuccCount + " 成功; " + nFailCount + " 失败。");
        logger.debug("\t@> PEdorValidTask.EdorDelayNoReplyTimeoutTerminate() : 保全客户延期未响应逾期终止批处理完毕");
	}

	
	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		tG.ComCode = "86";
		tG.Operator = "AUTO";
		tG.ManageCom = "86";

		PEdorOverDueBL tPEdorOverDueBL = new PEdorOverDueBL();

		logger.debug("---EdorWorkFlowUI  BEGIN---");
		if (!tPEdorOverDueBL.setOverDue(tLPEdorAppSchema.getEdorAcceptNo(), "4"))
		{
			// @@错误处理
			 nFailCount ++;
			this.mErrors.copyAllErrors(tPEdorOverDueBL.mErrors);
			mResult.clear();
			return false;
		}
		else
		{
			
        	LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
    		LOPRTManagerSchema mmLOPRTManagerSchema;
    		MMap map = new MMap();
    		try {
    			LCContDB tLCContDB = new LCContDB();
		        tLCContDB.setContNo(tLPEdorAppSchema.getOtherNo());
		        if (!tLCContDB.getInfo()) {
		        	CError.buildErr(this, "保单查询失败");
		        	return false;
		        }
    			String Code = PrintManagerBL.CODE_PEdorCancel;// 个险保全撤销通知书
    			mmLOPRTManagerSchema = new LOPRTManagerSchema();
    			String strNoLimit = PubFun.getNoLimit(tG.ManageCom);
    			String sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
    			mmLOPRTManagerSchema.setPrtSeq(sPrtSeq);
    			mmLOPRTManagerSchema.setOtherNo(tLPEdorAppSchema.getOtherNo());
    			mmLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
    			mmLOPRTManagerSchema.setCode(Code); // 打印类型
    			mmLOPRTManagerSchema.setManageCom(tLPEdorAppSchema.getManageCom()); // 管理机构
    			mmLOPRTManagerSchema.setAgentCode(tLCContDB.getAgentCode()); // 代理人编码
    			mmLOPRTManagerSchema.setReqCom(tG.ComCode);
    			mmLOPRTManagerSchema.setReqOperator(tG.Operator);
    			mmLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
    			mmLOPRTManagerSchema.setStateFlag("0"); // 打印状态
    			mmLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
    			mmLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
    			mmLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
    			mmLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);
    			mmLOPRTManagerSchema.setStandbyFlag1(tLPEdorAppSchema.getEdorAcceptNo());//受理号
    			//backreason
    			//modify by jiaqiangli 2009-05-17 应彭送庭修改 统一成edorcancelsreason
    			//edorcancelsreason
    			mmLOPRTManagerSchema.setStandbyFlag2("E05-");//保存申请撤销原因
    			mLOPRTManagerSet.add(mmLOPRTManagerSchema);
    			map.put(mLOPRTManagerSet, "DELETE&INSERT");
    		}
    		catch (Exception e) {
    			nFailCount ++;
    			logger.debug("EdorDelayNoReplyTimeoutTerminate"+tLPEdorAppSchema.getOtherNo()+"插入保全撤销通知书失败");
    			CError.buildErr(this, "插入保全撤销通知书失败!");
    			return false;
    		}
    		VData mInputData = new VData();
    		mInputData.clear();
    		mInputData.add(map);
    		PubSubmit tPubSubmit = new PubSubmit();
    		if (!tPubSubmit.submitData(mInputData, "")) {
    			 nFailCount ++;
    			CError.buildErr(this, "保全逾期终止数据提交失败");
    			return false;
    		}
    		else
    		{
    			nSuccCount ++;
    		}
    		 tPEdorOverDueBL = null;
             tLPEdorAppSchema = null;
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

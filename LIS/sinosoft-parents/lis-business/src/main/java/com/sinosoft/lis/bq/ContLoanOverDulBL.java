
package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.sinosoft.lis.bq.EdorVerifyBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LOBonusPolDB;
import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.get.BonusAssignBL;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LOLoanSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LOBonusPolSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 保单欠款逾期催付处理
 * 借款止期的后催付
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author pst
 * @version 1.0
 * @CreateDate： 2009-03-13
 */
public class ContLoanOverDulBL {
private static Logger logger = Logger.getLogger(ContLoanOverDulBL.class);
	/** 全局变量 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 保单合同号码 */
	private String mContNo;
	
	/**统计保单借款情况日期*/
	private String mCalDate="";

    
	private int tSuc = 0, tFail = 0;

	/** 结算报告书打印记录 */
	private LOPRTManagerSet mLOPRTManagerSet = null;

	
	private ExeSQL tExeSQL = new ExeSQL();
	
	private String 	tCom;
	
	public String mContent = "";	
	
	private MMap map = new MMap();

	// 系统当前时间
	private String mCurrentTime = PubFun.getCurrentTime();

	private String mCurrentDate = PubFun.getCurrentDate();

	public ContLoanOverDulBL() {
	}

	/**
	 * submitData
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		if (!getInputData()) {
			return false;
		}

		String tComSQL = "";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		if (!"".equals(mContNo) && mContNo != null) {
			tCom = mContNo.substring(0, 4);
			if (!dealData()) {
				return false;
			}
		} else {
			tComSQL = "select distinct substr(comcode,1,4) com from ldcom  where comcode!='86' "
					+ " order by com";
			sqlbv.sql(tComSQL);
			SSRS tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				CError.buildErr(this, "查询分支机构失败!");
				return false;
			} else {
				for (int k = 1; k <= tSSRS.MaxRow; k++) {
					// 数据操作业务处理
					tCom = tSSRS.GetText(k, 1);
					if (!dealData()) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * 业务处理
	 * 
	 * @return boolean
	 */
	public boolean dealData() {

		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  ContLoanOverDul Start !!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
	      //日志监控,过程监控        
    	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"欠款逾期催付处理开始");

			String tContSql = "select * from loloan a where 1=1"
					+ ReportPubFun.getWherePart("ContNo", ReportPubFun.getParameterStr(mContNo, "?mContNo?"))
					+ " and payoffflag='0' and "
					+ "  substr(ContNo,1,4)='"
					+ "?tCom?"
					+ "'"
					+ " and LoanType='0' and (select (MONTHS_BETWEEN(to_date('"
					+ "?mCalDate?"    
					+ "','yyyy-mm-dd'), newloandate)) from dual) >= 6"
					+ "  and not exists (select 'X' from LOPRTManager where code='BQ11' and otherno=a.contno and StandbyFlag2=a.newloandate)"
					;
			logger.debug(tContSql);
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tContSql);
			sqlbv.put("tCom", tCom);
			sqlbv.put("mCalDate", mCalDate);
			sqlbv.put("mContNo", mContNo);
			LOLoanSet tLOLoanSet = new LOLoanSet();
			RSWrapper rsWrapper = new RSWrapper();
			// 采取长连接的方式
			if (!rsWrapper.prepareData(tLOLoanSet, sqlbv)) {
				this.mErrors.copyAllErrors(rsWrapper.mErrors);
				return false;
			}
			do {
				rsWrapper.getData();
				if (tLOLoanSet == null || tLOLoanSet.size() <= 0) {
					break;
				}
				for (int k = 1; k <= tLOLoanSet.size(); k++) {
					LOLoanSchema tLOLoanSchema = tLOLoanSet.get(k);

					mLOPRTManagerSet = new LOPRTManagerSet();
					// 业务逻辑处理
					if (!DealOneLoan(tLOLoanSchema)) {
						logger.debug(mErrors.getFirstError()
								+ " 保单欠款催付处理，保单号：" + tLOLoanSchema.getContNo());
						tFail++;
						// 日志监控,状态监控                 		
	         			PubFun.logState(mGlobalInput,tLOLoanSchema.getEdorNo(), "保单"+tLOLoanSchema.getContNo()+"的"+tLOLoanSchema.getEdorNo()+"保全批次借款逾期，催付处理失败","0");  
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
//					 日志监控,状态监控                 		
         			PubFun.logState(mGlobalInput,tLOLoanSchema.getEdorNo(), "保单"+tLOLoanSchema.getContNo()+"的"+tLOLoanSchema.getEdorNo()+"保全批次借款逾期，催付处理成功","1"); 
				
				}

			} while (tLOLoanSet != null && tLOLoanSet.size() > 0);
			rsWrapper.close();
		
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  ContLoanOverDul End !!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
		mContent = "成功:" + tSuc + "单，失败: " + tFail + "单";
		logger.debug("成功:" + tSuc + "单，失败: " + tFail + "单");
//		日志监控,结果监控
		PubFun.logResult (mGlobalInput,mGlobalInput.LogID[1],"本次保单逾期欠款"+tSuc+"笔催付处理成功");
		PubFun.logResult (mGlobalInput,mGlobalInput.LogID[1],"本次保单逾期欠款"+tFail+"笔催付处理失败");	
		
	      //日志监控,过程监控        
    	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"欠款逾期催付处理结束");
		return true;
	}

	/**
	 * 传递参数 准备账户号码和账户结算日期
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	public boolean getInputData() {

		// 全局变量
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mContNo = (String) mTransferData.getValueByName("ContNo");

		mCalDate = (String) mTransferData.getValueByName("CalDate");
		if (mCalDate == null || mCalDate.equals("")) {
			mCalDate = mCurrentDate;
		}
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (mGlobalInput == null) {
			mGlobalInput = new GlobalInput();
			mGlobalInput.Operator = "001";
			mGlobalInput.ManageCom = "86";
		}
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
			tLOPRTManagerSchema.setReqCom(mGlobalInput.ManageCom);
			tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);

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
    	PubFun.logTrack(mGlobalInput,tLOLoanSchema.getEdorNo(),"生成"+tLOLoanSchema.getContNo()+"保单的"+tLOLoanSchema.getEdorNo()+"保全批次借款逾期催付通知书");
		InsertPRT(mLCContSchema, tLOLoanSchema.getNewLoanDate(),tLOLoanSchema.getLeaveMoney());
		
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
	 * getErrors
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}





	/**
	 * getResult
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}


      public static void main(String arg[]) {
		GlobalInput mGlobalInput = new GlobalInput();
		mGlobalInput.Operator = "001";
		mGlobalInput.ManageCom = "86110000";

		ContLoanOverDulBL tContLoanOverDulBL = new ContLoanOverDulBL();
		VData tVData = new VData();

		// 循环进行帐户结算
		String sStartDate = "2008-03-01";
		String sEndDate = "2008-03-25";
		String sContNo = "210110000000076";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ContNo", sContNo);

		String sBalaDate;
		tVData.add(mGlobalInput);
		tVData.add(tTransferData);
		if (!tContLoanOverDulBL.submitData(tVData, "")) {
			logger.debug("error is=="
					+ tContLoanOverDulBL.mErrors.getErrContent());
		}
	}
}

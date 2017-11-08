package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LOBonusPolDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.get.BonusAssignBL;
import com.sinosoft.lis.get.BonusAssignManuBL;
import com.sinosoft.lis.operfee.IndiDueFeePartQuery;
import com.sinosoft.lis.operfee.RnDealBL;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOLoanSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LOBonusPolSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.bq.EdorWorkFlowUI;
/**
 * <p>Title: 保单借款催付批处理</p>
 * <p>Description: 
 * </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: SinoSoft</p>
 * @author  zhangfh
 * @version 1.0
 */
public class ContLoanPayNoticeServiceTMThread extends TaskThread {
	/** 公共锁定号码类 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();	
	private static Logger logger = Logger.getLogger(ContLoanPayNoticeServiceTMThread.class);
	public CErrors mErrors = new CErrors();
    private GlobalInput tG = new GlobalInput();
    private  int tSuc = 0, tFail = 0;
	private String tCalDate = PubFun.getCurrentDate();
    private String mCurrentDate = PubFun.getCurrentDate();
	/**红利分配组号*/
	private String tGroupID;
	 private int XCount=0;
	/** 结算报告书打印记录 */
	private LOPRTManagerSet mLOPRTManagerSet = null;
	private MMap map = new MMap();
	private String tParamCalDate;
	private String tCurMakeDate = PubFun.getCurrentDate();
	private String 	tCom;
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	// 系统当前时间
	private String mCurrentTime = PubFun.getCurrentTime();
	String mCalDate;
	String mContNo;
	public ContLoanPayNoticeServiceTMThread() {
	}

	public boolean dealMain() {
		
		/* 业务处理逻辑 */
		logger.debug("进入业务逻辑处理!--保单借款催付批处理" + PubFun.getCurrentDate()
				+ "**" + PubFun.getCurrentTime());
		tG.ComCode = "86";
		tG.Operator = "AUTO";
		tG.ManageCom = "86";

		 //日志监控,初始化           	
		tG.LogID[0]="TASK"+(String)mParameters.get("TaskCode"); 		
		tG.LogID[1]=(String)mParameters.get("SerialNo"); 	
		PubFun.logPerformance (tG,tG.LogID[1],"保单借款催付批处理运行程序开始","1");
		//启动时间        	
    	Date  dNow =  new Date();         	
    	long initTime =dNow.getTime();   
		
		// 每次最大取数量,默认是100条
		int tConutMax = 100;
		String tResult = "";
		String tSQL_Count = "select sysvarvalue from ldsysvar where sysvar='ContLoanPayNoticeServiceTMCount' ";
		  SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tSQL_Count);
		ExeSQL tExeSQL = new ExeSQL();
		tResult = tExeSQL.getOneValue(sqlbv1);
		// 如果有描述的话,取描述的数据
		if (tResult != null && !tResult.equals("")) {
			tConutMax = Integer.parseInt(tResult);
		}
		Vector mTaskWaitList = new Vector();
		logger.debug("保单借款催付批处理运行开始：RnewPolAuto...");

//		处理的机构
//        tGlobalInput = (GlobalInput)mParameters.get("GlobalInput");

		tParamCalDate = (String) mParameters.get("CalDate");
		if (tParamCalDate == null || tParamCalDate.equals("")) {
			//add by jiaqiangli 2009-03-26 提前45天催付
			tParamCalDate = PubFun.calDate(tCalDate, 0, "D", null);
		}
        
		// 单欠款逾期催付处理保单号
		mContNo = (String) mParameters.get("ContNo");
		logger.debug("The ContNo is :" + mContNo);
		
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("CalDate", mCalDate);
		tTransferData.setNameAndValue("ContNo", mContNo);
		VData tVData = new VData();
		tVData.addElement(tTransferData);
		tVData.addElement(tG);
        String tComSQL = "";
		if (!"".equals(mContNo) && mContNo != null) {
			if (!dealData()) {
				return false;
			}
		} else{

			tComSQL = "select distinct substr(comcode,1,4) com from ldcom  where comcode!='86' "
					+ " order by com";
			  SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				sqlbv2.sql(tComSQL);
			SSRS tSSRS = tExeSQL.execSQL(sqlbv2);
			int xLength=0;
			if (tSSRS == null || tSSRS.MaxRow <= 0) {
				CError.buildErr(this, "查询分支机构失败!");
				return false;
			} else {
				for (int k = 1; k <= tSSRS.MaxRow; k++) {
					
					try{
					// 数据操作业务处理
					tCom = tSSRS.GetText(k, 1);
					// 红利计算组,如果为空则将其置为 ”1“

					logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  ContLoanOverDul Start !!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
				      //日志监控,过程监控        
			    	PubFun.logTrack(tG,tG.LogID[1],"欠款逾期催付处理开始");

					String tContSql = "select * from loloan a where 1=1"
						+ ReportPubFun.getWherePart("ContNo", ReportPubFun.getParameterStr(mContNo, "?mContNo?"))
						+ " and payoffflag='0' and "
						+ "  substr(ContNo,1,4)='"
						+ "?tCom?"
						+ "'"
						+ " and LoanType='0' and (select (MONTHS_BETWEEN(to_date('"
				     	+ "?tParamCalDate?"
					    + "','yyyy-mm-dd'), newloandate)) from dual) >= 5"
						+ "  and not exists (select 'X' from LOPRTManager where code='BQ10' and otherno=a.contno and StandbyFlag2=a.newloandate)"
						;
						logger.debug(tContSql);
						  SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
							sqlbv3.sql(tContSql);
							sqlbv3.put("mContNo", mContNo);
							sqlbv3.put("tCom", tCom);
							sqlbv3.put("tParamCalDate", tParamCalDate);
						LOLoanSet tLOLoanSet = new LOLoanSet();
						RSWrapper rsWrapper = new RSWrapper();
						// 采取长连接的方式
						if (!rsWrapper.prepareData(tLOLoanSet, sqlbv3)) {
							this.mErrors.copyAllErrors(rsWrapper.mErrors);
							return false;
						}
						    do{
							rsWrapper.getData();
							VData hVData = new VData();
							xLength=tLOLoanSet.size();
							for (int j = 1; j <= tLOLoanSet.size(); j++) {
								XCount++;
								LOLoanSchema tLOLoanSchema = tLOLoanSet.get(j);

								mLOPRTManagerSet = new LOPRTManagerSet();
								// 业务逻辑处理
					
								/** 分配红利金额，以LOBonusPolSet 为处理单位 */
								
					           	Map map = new HashMap();
								map.put("LOLoanSchema", tLOLoanSchema);
								map.put("GlobalInput", tG);
								hVData.add(map);
								// 业务逻辑处理
								if(j%tConutMax==0)
								{
				            		mTaskWaitList.add(hVData);
				            		hVData = new VData();
								}
								
								if(j%tConutMax!=0&&j==tLOLoanSet.size())
								{
									mTaskWaitList.add(hVData);
									hVData = new VData();
								} 
							}
						
					mPubLock.unLock();
					
				
					String TaskCode = (String) this.mParameters.get("TaskGroupCode") + ":"
					+ (String) this.mParameters.get("TaskCode") + ":"
					+ (String) this.mParameters.get("TaskPlanCode");
			// tongmeng 2009-05-21 add
			// 自适应线程数
			int tThreadCount = 5;
			tExeSQL = new ExeSQL();
			String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='ContLoanPayNoticeServiceTMThread'";
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(tSQL_ThreadCount);
			String tSignMThreadCount = tExeSQL.getOneValue(sqlbv4);
			if (tSignMThreadCount != null && !"".equals(tSignMThreadCount)
					&& Integer.parseInt(tSignMThreadCount) > 0) {
				tThreadCount = Integer.parseInt(tSignMThreadCount);
			}
			if (xLength < tThreadCount) {
				tThreadCount =xLength;
			}
			PubFun.logTrack(tG, tG.LogID[1], "保单借款催付批处理运行完毕");
			this.mServiceA = new ServiceA(
					"com.sinosoft.lis.bq.ContLoanPayNoticeBLTMThread", mTaskWaitList,
					tThreadCount, 10, TaskCode);
			this.mServiceA.start();
						    } while (tLOLoanSet != null && tLOLoanSet.size() > 0);
							rsWrapper.close();
					     }catch (Exception ex) {
							ex.printStackTrace();
						} 

	        //完成时间
	    	Date  dend = new Date();         	
	    	long  endTime = dend.getTime(); 
	    	long tt=(endTime-initTime)/1000; 
	    	//日志监控,结果监控 
	    	
	    	PubFun.logResult(tG,tG.LogID[1], "保单借款催付批处理运行"+String.valueOf(XCount)+"笔,共花费"+tt+"秒");
			return true;
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
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  ContLoanPayNoticeBL Start !!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
	      //日志监控,过程监控        
		PubFun.logTrack(tG,tG.LogID[1],"借款催付批处理(提前一个月)开始");

			String tContSql = "select * from loloan a where 1=1"
					+ ReportPubFun.getWherePart("ContNo", ReportPubFun.getParameterStr(mContNo, "?mContNo?"))
					+ " and payoffflag='0' and "
					+ "  substr(ContNo,1,4)='"
					+ "?tCom?"
					+ "'"
					+ " and LoanType='0' and (select (MONTHS_BETWEEN(to_date('"
					+ "?mCalDate?"
					+ "','yyyy-mm-dd'), newloandate)) from dual) >= 5"
					+ "  and not exists (select 'X' from LOPRTManager where code='BQ10' and otherno=a.contno and StandbyFlag2=a.newloandate)"
					;
			logger.debug(tContSql);
			 SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
				sqlbv5.sql(tContSql);
				sqlbv5.put("mContNo", mContNo);
				sqlbv5.put("tCom", tCom);
				sqlbv5.put("mCalDate", mCalDate);
			LOLoanSet tLOLoanSet = new LOLoanSet();
			RSWrapper rsWrapper = new RSWrapper();
			// 采取长连接的方式
			if (!rsWrapper.prepareData(tLOLoanSet, sqlbv5)) {
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
	         			PubFun.logState(tG,tLOLoanSchema.getEdorNo(), "保单"+tLOLoanSchema.getContNo()+"的"+tLOLoanSchema.getEdorNo()+"保全批次借款逾期，催付处理失败","0");  
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
         			PubFun.logState(tG,tLOLoanSchema.getEdorNo(), "保单"+tLOLoanSchema.getContNo()+"的"+tLOLoanSchema.getEdorNo()+"保全批次借款逾期，催付处理成功","1"); 
				
				}

			} while (tLOLoanSet != null && tLOLoanSet.size() > 0);
			rsWrapper.close();
		
		logger.debug("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝  ContLoanOverDul End !!! ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
		mContent = "成功:" + tSuc + "单，失败: " + tFail + "单";
		logger.debug("成功:" + tSuc + "单，失败: " + tFail + "单");
//		日志监控,结果监控
		PubFun.logResult (tG,tG.LogID[1],"本次保单逾期欠款"+tSuc+"笔催付处理成功");
		PubFun.logResult (tG,tG.LogID[1],"本次保单逾期欠款"+tFail+"笔催付处理失败");	
		
	      //日志监控,过程监控        
    	PubFun.logTrack(tG,tG.LogID[1],"欠款逾期催付处理结束");
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
    	PubFun.logTrack(tG,tLOLoanSchema.getEdorNo(),"生成"+tLOLoanSchema.getContNo()+"保单的"+tLOLoanSchema.getEdorNo()+"保全批次借款逾期催付通知书");
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

			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PEdorLoanPay); // 打印类型
			tLOPRTManagerSchema.setStandbyFlag1(String.valueOf(tSumLoanMoney));// 存放借款本金
			tLOPRTManagerSchema.setStandbyFlag2(tLoanDate);// 存放借款日期
			tLOPRTManagerSchema.setManageCom(pLCContSchema.getManageCom()); // 管理机构
			tLOPRTManagerSchema.setAgentCode(pLCContSchema.getAgentCode()); // 代理人编码
			tLOPRTManagerSchema.setReqCom(tG.ManageCom);
			tLOPRTManagerSchema.setReqOperator(tG.Operator);

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
   
    //==========================================================================

	public static void main(String[] args) {
		ContLoanPayNoticeServiceTMThread tContLoanPayNoticeServiceTMThread = new ContLoanPayNoticeServiceTMThread();
		tContLoanPayNoticeServiceTMThread.dealMain();
	}
}

package com.sinosoft.lis.operfee;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.ibrms.RuleUI;
import com.sinosoft.ibrms.bom.BOMCont;
import com.sinosoft.lis.bq.BqCalBL;
import com.sinosoft.lis.customer.FICustomerMain;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCDiscountDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LCRnewStateHistoryDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LMRiskPayDB;
import com.sinosoft.lis.f1print.FYDate;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.finfee.FinFeePubFun;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.InsuAccBala;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LASPayPersonSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LCRnewStateHistorySchema;
import com.sinosoft.lis.schema.LJABonusGetSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LJFIGetSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LOPRTManagerSubSchema;
import com.sinosoft.lis.tb.DiscountCalBL;
import com.sinosoft.lis.vbl.LCPremBLSet;
import com.sinosoft.lis.vschema.LASPayPersonSet;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCDiscountSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCRnewStateHistorySet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LMRiskPaySet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LOPRTManagerSubSet;
import com.sinosoft.lis.xbcheck.PRnewUWAutoChkBL;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * Title: Web业务系统
 * 
 * Copyright: Copyright (c) 2002
 * 
 * Company: Sinosoft
 * 
 * @author Gaoht
 * @version 1.0
 */

public class RnDealBL {
private static Logger logger = Logger.getLogger(RnDealBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类* */
	public CErrors mErrors = new CErrors();

	/** 往后台传输数据容器 */
	private GlobalInput tGI = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	MMap map_xbapply = new MMap();  //存放续保申请时提交数据
	MMap map = new MMap();  //存放提交最后的催收数据

	/** 数据操作字符串 */
	private String mOperate = "";

	private String CurrentDate = PubFun.getCurrentDate();

	private String CurrentTime = PubFun.getCurrentTime();

	/** 本逻辑入口保单层全局变量* */
	private String mContNo = "";

	private double oldLeavingMoney =0.0; //记录保单原有余额
	private double sumLeavingMoney = 0.0;

	private String mGetNoticeNo = "";

	private String mPrtSeqNo = "";

	private double SumPaymoney = 0;
	
	private String[] currencystr=null;
	private double[] sumpaymoneystr=null;
	private double[] sumleavingmoneystr=null;
	private double[] oldleavingmoneystr=null;//记录保单各币种原有余额

	private String mPayDate = "";

	private Date mLastPaytoDate = new Date(); // 现在的缴费对应日

	private Date mCurPayToDate = new Date(); // 下一次的缴费对应日
	
	private Date ZC_LastPayToDate = new Date(); // 正常加费情况下现在的缴费对应日

	private Date ZC_CurPayToDate = new Date(); // 正常加费情况下下一次的缴费对应日
	
	private String xb_paytodate = ""; //当次催收新生成的续保记录的交至日期(原交至日期往后推一年)

	/** EFT操作时用到的银行信息* */
	private String mBankCode = "";

	private String mBankAccNo = "";

	private String mBankAccName = "";

	private LCContSchema mLCContSchema = new LCContSchema();

	/** 定义返回数据容器* */
	private VData mResult = new VData();

	private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
	
	private LCRnewStateHistorySet update_LCRnewStateHistorySet = new LCRnewStateHistorySet();  //续保成功后将state置为4传入后台统一提交用

	private LOPRTManagerSubSet mLOPRTManagerSubSet = new LOPRTManagerSubSet();
	
	private LJSPaySet mLJSPaySet= new LJSPaySet();

	private LASPayPersonSet mLASPayPersonSet = new LASPayPersonSet();

	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();

	/** 自动续保的数据容器* */
	int AutoInt = 0;
	int NorInt = 0;
	int xCount = 0;
	String xb_sucess_flag="0";  //所有续保成功标记，初始默认为0，未成功，1为成功。
	String main_short_flag="N"; //主险短期险，主附险交至日期不一致，主险单独催收标记
	String BQ_YYXZ_Flag ="N"; //预约新增附加险存在标志
	String main_xb_flag ="0";//主险续保标记 0，非主险续保，1，主险续保
	String main_LDunJiao_flag ="0";//主险非续保趸交标记 0，不是，1，是
	String main_cvalidate=""; //主险首期生效日
	int NoNeed_count = 0;  //不需要续保的续保件的计数(续保件不符合续保年龄限制导致不续保的，其他险种正常处理)
    //保存投保单对应的操作类型,对应appflag,9是续保标记 (其他可能是保全类型)
	private String mSavePolType = "9";

	private LCPolSet mLCPolSet = new LCPolSet();

	private LCPremSet mLCPremSet = new LCPremSet();

	private LCDutySet mLCDutySet = new LCDutySet();

	private LCGetSet mLCGetSet = new LCGetSet();

	/* 不自动续保数据容器 */
	public LCPolSet mNotRenewPol = new LCPolSet();

	// 增加保单年度
	private String mMainPolYear = "";
	
	private String  mStartDate="", mEndDate="";

	private LCPremSet main_LCPremSet = new LCPremSet();

	public RnDealBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData)) {
			return false;
		}

		if (!checkdata()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}
		return true;
	}

	private boolean getInputData(VData mInputData) {

		tGI = ((GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0));
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mLCContSchema = (LCContSchema) mInputData.getObjectByObjectName(
				"LCContSchema", 0);
		mStartDate = (String) mInputData.get(1);
		mEndDate = (String) mInputData.get(2);

		if (tGI == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IndiDueFeePartBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的数据，请您确认!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mLCContSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IndiDueFeePartBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取必要保单信息失败";
			this.mErrors.addOneError(tError);
			return false;
		}
		/** @todo 1----本次处理保单号* */
		mContNo = mLCContSchema.getContNo();
		logger.debug("开始处理保单:::::::::::::::::::::" + mContNo);

		/** @todo 2----生成本次处理交费号、印刷号* */
		String tLimit = PubFun.getNoLimit(mLCContSchema.getManageCom());
		mGetNoticeNo = PubFun1.CreateMaxNo("PAYNOTICENO", tLimit);
		mPrtSeqNo = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
		logger.debug("本次处理交费号:::::::::::::::::::" + mGetNoticeNo);
		logger.debug("本次处理打印流水号::::::::::::::::" + mPrtSeqNo);
		if (mGetNoticeNo == null || mPrtSeqNo == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IndiDueFeePartBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "生成交费通知号与打印流水号失败";
			this.mErrors.addOneError(tError);
			return false;
		}

		/** @todo 3----整理EFT操作所需银行数据* */
		if (mLCContSchema.getPayLocation() != null
				&& mLCContSchema.getPayLocation().equals("0")) {
			mBankCode = mLCContSchema.getBankCode();
			mBankAccNo = mLCContSchema.getBankAccNo();
			mBankAccName = mLCContSchema.getAccName();
			logger.debug("本次处理进入EFT操作以下为银行信息");
			logger.debug("银行代码mBankCode:::::::::::::" + mBankCode);
			logger.debug("银行账号mBankAccNo:::::::::::::" + mBankAccNo);
			logger.debug("户名mBankAccName:::::::::::::::::" + mBankAccName);
		}
		return true;
	}

	private boolean checkdata() {
		/** @todo 4----校验保单级保单状态* */
		String DateSql = "select min(paytodate) from lcpol where contno='?mContNo?' and appflag='1'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(DateSql);
		sqlbv.put("mContNo", mContNo);
		SSRS nSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		nSSRS = tExeSQL.execSQL(sqlbv);
		if (nSSRS.MaxRow <= 0) {
			CError tError = new CError();
			tError.moduleName = "GetChequeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "未找到交费对应日信息";
			this.mErrors.addOneError(tError);
			return false;
		}
		String PaytoDate = nSSRS.GetText(1, 1);

		/** @todo 5----检验保单是否被挂起* */
		RNHangUp tRNHangUp = new RNHangUp(tGI);
		if (!tRNHangUp.checkHangUP(mContNo)) {
			this.mErrors.copyAllErrors(tRNHangUp.mErrors);
			return false;
		}
		//增加校验，保全CM/IC，做完CM，需要做IC的，在IC完成前不允许催收
		String checkIC_sql=" select count(*) from lpconttempinfo where edortype='CM' and contno='"+mContNo+"' and state='0'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(checkIC_sql);
		int ICCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv1));
		if (ICCount>0) 
		{
			this.mErrors.addOneError("保单号:" + mLCContSchema.getContNo()
					+ "存在未完结保全操作：客户重要资料变更，不允许催收！");
			return false;
		}
		
		/** @todo 校验是否发过催收* */
		String tSql = "select * from ljspay where 1=1 and othernotype ='2' and otherno='?mContNo?'";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("mContNo", mContNo);
		LJSPaySet tLJSPaySet = new LJSPaySet();
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPaySet = tLJSPayDB.executeQuery(sqlbv2);
		if (tLJSPaySet.size() > 0) {
			this.mErrors.addOneError("保单号:" + mLCContSchema.getContNo()
					+ "已经催收");
			return false;
		}
		//校验保单是否有死亡报案，若有，则不允许催收
		String DeadFlag_STR1="";
		DeadFlag_STR1=" select count(x.contno) from lcconthangupstate x, LLReportReason y where x.hangupno = y.RpNo  "
	    +  "  and substr(y.ReasonCode, 2) = '02' and x.contno ='?mContNo?' " ;
		//死亡报案已结案的，如果非豁免，则appflag为4，前面的查询逻辑取不进来，如果是豁免险，对于历史已结案的报案记录，不需要做校验
		/*
		String DeadFlag_STR2="";
		DeadFlag_STR2=" select count(x.contno) from llclaimpolicy x, LLAppClaimReason y where x.clmno  = y.CaseNo   "
		    +  "  and substr(y.ReasonCode, 2) = '02' and x.contno ='"+mContNo+"' " ;
		    */
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(DeadFlag_STR1);
		sqlbv3.put("mContNo", mContNo);
		int DeadCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv3));
		if (DeadCount>0) 
		{
			this.mErrors.addOneError("保单号:" + mLCContSchema.getContNo()
					+ "存在死亡报案，不允许催收！");
			return false;
		}

		return true;
	}

	private boolean dealData() {
		try
		{
			// 查询此保单所有数据
			String tPolSql = "select * from  (select a.* from lcpol a where a.contno = '?mContNo?' and a.appflag = '1' and a.polno = a.mainpolno union all "
					+ "select a.* from lcpol a where a.contno = '?mContNo?' and a.appflag = '1' and a.polno <> a.mainpolno ) g" 
					+ " order by (case when polno=mainpolno then 0 else 1 end)";
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(tPolSql);
			sqlbv4.put("mContNo", mContNo);
			logger.debug("查询所有险种sql:"+tPolSql);
			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet tLCPolSet = new LCPolSet();
			tLCPolSet = tLCPolDB.executeQuery(sqlbv4);
			if (tLCPolSet.size() == 0) {
				this.mErrors.addOneError("保单号:" + mContNo + "未找到其险种信息");
				return false;
			}
			
			//查询出保单下所有红利类型的险种
			LCPolSet HL_LCPolSet = new LCPolSet();
			String HL_LCPol = " select a.* from lcpol a where a.contno = '?mContNo?' and a.appflag = '1' and exists( select 1 from lmriskapp b where b.riskcode=a.riskcode and b.bonusflag='1')"
			+ " order by a.polno ";
			logger.debug("查询红利分配险种sql:"+HL_LCPol);
			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
			sqlbv5.sql(HL_LCPol);
			sqlbv5.put("mContNo", mContNo);
			LCPolDB xLCPolDB = new LCPolDB();
			HL_LCPolSet = xLCPolDB.executeQuery(sqlbv5);
			//红利分配处理,需要修改成客户账户的方式
			if (HL_LCPolSet.size() > 0) 
			{
				for(int a=1;a<=HL_LCPolSet.size();a++)
				{
					//增加红利抵交续期保费处理
					LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
					LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
					tLCInsureAccDB.setPolNo(HL_LCPolSet.get(a).getPolNo());
					tLCInsureAccDB.setAccType("007"); //帐户类型为抵交保费帐户，但不计息
					LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.query();
					
					if(tLCInsureAccSet.size()> 0)  //如果有红利帐户并且金额大于0的
					{
				        double  tGetMoney = tLCInsureAccSet.get(1).getInsuAccBala();
				        if(tGetMoney>0)
				        {
				        	/** 公共锁定号码类 */
			        		PubConcurrencyLock xPubLock = new PubConcurrencyLock();
				        	try
				        	{	
				        		//
					        	String LockNo= mContNo;
								if (!xPubLock.lock(LockNo, "LB0002", tGI.Operator)) 
								{
									this.mErrors.addOneError("保单号:" + mContNo + "红利挂靠保单余额前并发加锁失败。");
									return false;
								}
								if(!runAssignBonus("",HL_LCPolSet.get(a)))
								{
									return false;
								}
				        	}
				        	catch (Exception e) 
				        	{
				    			e.printStackTrace();
				    			CError.buildErr(this, e.toString());
				    			return false;
				    		} 
				        	finally 
				    		{
				    			xPubLock.unLock();// 解锁
				    		}
				        }
					}
				}
			}
			
			// 查询此保单所有余额之和，先抵缴主险-YEL，然后抵缴附加险-YEL，若最后有余额都记在主险上面+YET
			// 初始化多币种需要的数组
			//由于存在一个险种下挂多个币种的情况，因此需要从lcprem查询保单下币种类型
			String sumMoneySql = " select distinct a.currency from lcprem a where a.contno = '?mContNo?' order by a.currency";
			SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
			sqlbv6.sql(sumMoneySql);
			sqlbv6.put("mContNo", mContNo);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = tExeSQL.execSQL(sqlbv6);
			if(tSSRS!=null && tSSRS.getMaxRow()>0)
			{
				//当前处理币种
				currencystr = new String[tSSRS.getMaxRow()];
				sumpaymoneystr = new double[tSSRS.getMaxRow()];
				
				sumleavingmoneystr = new double[tSSRS.getMaxRow()];
				oldleavingmoneystr = new double[tSSRS.getMaxRow()];
				
				for(int i=1;i<=tSSRS.getMaxRow();i++)
				{
					String curleavingmoneystr = " select (case when sum(leavingmoney) is not null then sum(leavingmoney) else 0 end) from lcpol a where a.contno = '?mContNo?' and a.currency = '?currency?'";
					SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
					sqlbv7.sql(curleavingmoneystr);
					sqlbv7.put("mContNo", mContNo);
					sqlbv7.put("currency", tSSRS.GetText(i, 1));
					String curleavingmoney = tExeSQL.getOneValue(sqlbv7);
					double dcurleavingmoney = Double.parseDouble(curleavingmoney);
					
					currencystr[i-1]=tSSRS.GetText(i, 1);
					sumpaymoneystr[i-1]=0;//初始化
					sumleavingmoneystr[i-1]=dcurleavingmoney;
					oldleavingmoneystr[i-1]=sumleavingmoneystr[i-1];
					logger.debug("ContNo="+mContNo+",Currency="+currencystr[i-1]+"~~~LeavingMoney="+oldleavingmoneystr[i-1]);
				}					
			}
			
	        //置上主险的续保收费标记
			ExeSQL check_ExeSQL = new ExeSQL();
			String Rnew_check_Sql = "select count(*) from LCPol a where AppFlag='1' "
	            + "and PayToDate = PayEndDate "
	            + "and RnewFlag = '-1' "
	            + "and (StopFlag='0' or StopFlag is null) and GrpPolNo='00000000000000000000' "
	            + "and contno='?mContNo?' and polno=mainpolno "
	            + "and exists (select 1 from lmrisk where riskcode=a.riskcode and rnewflag='Y') " ;
			SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
			sqlbv8.sql(Rnew_check_Sql);
			sqlbv8.put("mContNo", mContNo);
			int RnewCheck = Integer.parseInt( check_ExeSQL.getOneValue(sqlbv8) );
			if(RnewCheck>0)
			{
				main_xb_flag="1";
			}
			
            //置上主险的非续保趸交标记
			String LongDunJiao_check_Sql = "select count(*) from LCPol a where AppFlag='1' "
	            + "and payintv = 0 and PayToDate = PayEndDate "
	            + "and RnewFlag = '-2' "
	            + "and (StopFlag='0' or StopFlag is null) and GrpPolNo='00000000000000000000' "
	            + "and contno='?mContNo?' and polno=mainpolno "
	            + "and not exists (select 1 from lmrisk where riskcode=a.riskcode and rnewflag='Y') " ;
			SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
			sqlbv9.sql(LongDunJiao_check_Sql);
			sqlbv9.put("mContNo", mContNo);
			int LongDunJiaoCheck = Integer.parseInt( check_ExeSQL.getOneValue(sqlbv9) );
			if(LongDunJiaoCheck>0)
			{
				main_LDunJiao_flag="1";
			}
			
            //取主险lacommision中的首期记录中的cvalidate用于计算paytodate时作为比较日期
			SQLwithBindVariables sqlbv10=new SQLwithBindVariables(); 
			sqlbv10.sql("select a.cvalidate from lacommision a,lcpol b " 
					+" where a.contno=b.contno and a.contno='?mContNo?'  and a.lastpaytodate='1899-12-31' "
			        +" and b.polno=b.mainpolno and b.appflag='1' and a.riskcode=b.riskcode ");
			sqlbv10.put("mContNo", mContNo);
			main_cvalidate=check_ExeSQL.getOneValue(sqlbv10);
			logger.debug("first_cvalidate:"+main_cvalidate);
			if(main_cvalidate==null || "".equals(main_cvalidate))
			{
				//tongmeng 2011-04-26 modify
				//如果查询不到,使用lccont重新查一下.
				String tSQL_cont = "select cvalidate from lccont where contno='?mContNo?' ";
				SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
				sqlbv11.sql(tSQL_cont);
				sqlbv11.put("mContNo", mContNo);
				String main_cvalidate = check_ExeSQL.getOneValue(sqlbv11);
				if(main_cvalidate==null||"".equals(main_cvalidate))
				{
					this.mErrors.addOneError("保单号:" + mContNo + "主险首期生效日期查找失败。");
					return false;
				}
			}	
			
			// 错误采集
			int nErrNo = 0;
			int aErrNo = 0;
			/** @todo 7----定义险种的处理类型* */
			/** 多险种处理不能返回false 要continue跳出循环* */
			//String PolNoDealType = DealPolNo(tLCPolSchema);	
			/**对于每一张保单，依次进行三步操作，具体处理视保单类型决定* */	
			logger.debug("-----------------开始自动续保处理---------------");
			//续保分为续保申请及续保催收两部分
			//先是续保申请
			logger.debug("-----------------开始续保申请处理---------------");
			if (!DealAutoRenewsdata_Application(mContNo)) 
			{
				aErrNo++;
				logger.debug("保单号："+mContNo+"处理续保申请失败！！");
				return false;
			}
			
			//然后是续保催收
			logger.debug("-----------------开始续保催收处理---------------");
			if(main_short_flag.equals("N")) //主险为短期险单独催收时，不进行续保催收操作
			{
				if(!DealAutoRenewsdata_deal(mContNo))
				{
					aErrNo++;
					logger.debug("保单号："+mContNo+"处理续保催收失败！！");
					return false;
				}
			}
			logger.debug("-----------------开始续期抽档处理---------------");	
			if (!DealRndata(mContNo)) 
			{
				nErrNo++;
				logger.debug("保单号："+mContNo+"续期抽档处理失败！！");
				return false;
			}
			
			xCount=NorInt+AutoInt;
			logger.debug("续期续保共处理："+xCount);
			
			//当保单下所有险种均催收成功，才生成相关的其他表的信息，并统一提交
			//tongmeng 2012-01-31 modify
			//客户账户处理,不使用YET,YEL
			/*
			if(xCount>0&&(this.xb_sucess_flag.equals("1")||this.main_short_flag.equals("Y")))
			{
				//add by xiongzh 2010-1-12 将保单余额统一记在主险上，具体修改原因见 LIS-10251
				//分币种处理2010-5-23
				for(int j=0;j<currencystr.length;j++)
				{
					sumpaymoneystr[j]=sumpaymoneystr[j] - oldleavingmoneystr[j] ;
					sumLeavingMoney = sumleavingmoneystr[j] ;
					oldLeavingMoney = oldleavingmoneystr[j] ;
					if(oldleavingmoneystr[j] > 0)//保单催收前有余额的，生成YEL，统一放主险下 //下面需要做余额保存，多币种余额处理，暂时不修改
					{
						String LCPremSql="";
						if("1".equals(main_xb_flag))
						{
							LCPremSql = "select * From lcprem where polno =" 
							+" (select polno from lcpol where contno='"+mContNo+"' and riskcode='"+tLCPolSet.get(1).getRiskCode()+"' and appflag='9')"
							+" and UrgePayFlag='Y' and PayStartDate<'"+ xb_paytodate + "' and PayEndDate>='"+ xb_paytodate + "'";
						}
						else if("1".equals(main_LDunJiao_flag))
						{
							LCPremSql = "select * From lcprem where polno = '"
								+ tLCPolSet.get(1).getPolNo()
								+ "' and UrgePayFlag='Y' and PayStartDate<='"
								+ tLCPolSet.get(1).getPaytoDate() + "' and PayEndDate='"
								+ tLCPolSet.get(1).getPaytoDate() + "'";
						}
						else
						{
							LCPremSql = "select * From lcprem where polno = '"
								+ tLCPolSet.get(1).getPolNo()
								+ "' and UrgePayFlag='Y' and PayStartDate<='"
								+ tLCPolSet.get(1).getPaytoDate() + "' and PayEndDate>'"
								+ tLCPolSet.get(1).getPaytoDate() + "'";
						}	
						LCPremDB tLCPremDB = new LCPremDB();
						main_LCPremSet = new LCPremSet();
						main_LCPremSet = tLCPremDB.executeQuery(LCPremSql);
						if (main_LCPremSet.size() == 0) {
							this.mErrors.addOneError("未找到该保单下的主险催缴保费信息");
							return false;
						}
						
						String tOperator = "YEL";
						mLJSPayPersonSet.add(PreperaLjspayperson(tLCPolSet.get(1),
								main_LCPremSet.get(1), tOperator));
					}
					// 余额减去应收后还有余额，都记在主险上面为+YET
					if (sumleavingmoneystr[j] > 0) {
						sumpaymoneystr[j] = sumpaymoneystr[j] + sumleavingmoneystr[j];//下面需要做余额保存，多币种余额处理，保存暂时不修改
						String tOperator = "YET";
						mLJSPayPersonSet.add(PreperaLjspayperson(tLCPolSet.get(1),
								main_LCPremSet.get(1), tOperator));
					}
				}				
			}*/	
			
			logger.debug("处理保单" + mContNo + "采集如下信息");
			logger.debug("==================================");
			logger.debug("处理续期::::::::::::::::::" + NorInt);
			logger.debug("其中错误::::::::::::::::::" + nErrNo);
			logger.debug("==================================");
			logger.debug("续保处理::::::::::::::::::" + AutoInt);
			logger.debug("其中错误::::::::::::::::::" + aErrNo);
			logger.debug("==================================");
			
			/** @todo 渠道的应收备份表* */
			//mLASPayPersonSet.add(PreperaLAPay(mLJSPayPersonSet));
	
			/** @todo 生成打印管理子表数据* */
			mLOPRTManagerSubSet = PreperaLOPRTManagerSub(tLCPolSet,
					mLJSPayPersonSet);
	
			//生成应收总表
			if(!dealJSPay(mLJSPayPersonSet,tLCPolSet))
				return false;
			
			/** @todo 生成打印管理表数据* */
			VData prtData = getPrintData(mLCContSchema, mLJSPaySet.get(1), mPrtSeqNo);
			mLOPRTManagerSet = (LOPRTManagerSet) prtData.getObjectByObjectName(
					"LOPRTManagerSet", 0);
			if (mLOPRTManagerSet.size() == 0) {
				this.mErrors.addOneError("保单号:" + mContNo + "生成通知书&发票数据失败");
				return false;
			}		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			CError.buildErr(this, e.toString());
			return false;
		}
		return true;
	}

	// 准备往后层输出所需要的数据
	// 输出：如果准备数据时发生错误则返回false,否则返回true
	private boolean prepareOutputData() {
		// 准备错误日志
		// FinFeePubFun tFinFeePubFun = new FinFeePubFun();
		// MMap tmap =
		// tFinFeePubFun.ErrorLogSet(mErrors,"XQCD",mGetNoticeNo,mLCContSchema.getContNo());
		// if (tmap!=null)
		// map.add(tmap);
		map = new MMap();
		if(xCount>0&&(this.xb_sucess_flag.equals("1")||this.main_short_flag.equals("Y")))
		{
			map.put(update_LCRnewStateHistorySet, "UPDATE");
			
			map.put(mLJSPayPersonSet, "INSERT");
			map.put(mLJSPaySet, "INSERT");
			//map.put(mLASPayPersonSet, "DELETE&INSERT");
			map.put(mLOPRTManagerSet, "INSERT");
			map.put(mLOPRTManagerSubSet, "INSERT");
			mResult.add(map);
			mGetNoticeNo = "";
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public MMap getMap() {
		return map;
	}

	/**
	 * 为团险返回应收个人表
	 */
	public LJSPayPersonSet getLJSPayPerson() {
		return mLJSPayPersonSet;
	}

	/**
	 * 为团险自动续保返回险种信息
	 */
	public LCPolSet getLCPol() {
		return mLCPolSet;
	}

	/***************************************************************************
	 * 为团险返回保费信息
	 **************************************************************************/
	public LCPremSet getLCPrem() {
		return mLCPremSet;
	}

	/***************************************************************************
	 * 为团险返回领取信息
	 **************************************************************************/
	public LCGetSet getLCGet() {
		return mLCGetSet;
	}

	/***************************************************************************
	 * 为团险返回责任
	 */
	public LCDutySet getLCDuty() {
		return mLCDutySet;
	}

	/**
	 * 返回是否含自动续保操作
	 */
	public String ReNewFlag() {
		if (AutoInt > 0)
			return "Y";
		else
			return "N";
	}

	private String DealPolNo(LCPolSchema tLCPolSchema) {
		String PolNoDealType = "";
		/** @todo 7.1--处理主险* */
		if (tLCPolSchema.getPolNo().equals(tLCPolSchema.getMainPolNo())) {
			/** @todo 7.1.1--主险趸交* */
			if (tLCPolSchema.getPayIntv() == 0) {
				PolNoDealType = "RenewDeal";
			}
			/** @todo 7.1.2--主险交费期满* */
			else if (tLCPolSchema.getPayEndDate().compareTo(
					tLCPolSchema.getPaytoDate()) <= 0) {
				PolNoDealType = "RenewDeal";
			} else {
				PolNoDealType = "NormalDeal";
			}
		} else {
			/** @todo 7.2--处理附加险* */
			/** @todo 7.2.1--附加险期交* */
			if (tLCPolSchema.getPayIntv() > 0) {
				PolNoDealType = "NormalDeal";
			}
			/** @todo 7.2.2--附加险自动续保* */
			else if (tLCPolSchema.getRnewFlag() == -1) {// -1 -- 自动续保
				PolNoDealType = "RenewDeal";
			} else if (tLCPolSchema.getRnewFlag() == -2) {// -2 -- 非续保
				PolNoDealType = "EdorForEN";
			} else {
				PolNoDealType = "UnKnowType";
			}
		}
		return PolNoDealType;
	}

	// 续期处理
	private boolean DealRndata(String tContNo) {
		//由于续期催收只需要生成相应的ljspayperson记录，为了保证同一contno下所有险种应收信息同时提交
		//所以该部分操作在所有需要续保催收的保单成功催收之后才能进行	
		//主险为短期险时也可以单独进行催收
		ExeSQL tExeSQL = new ExeSQL();
		if(xb_sucess_flag.equals("1")||this.main_short_flag.equals("Y"))
		{
			//查找保单号下需要做续期催收的险种
			String pol_sql=" select * from lcpol where  contno='?tContNo?' and paytodate<payenddate and paytodate < enddate "
			+" and appflag='1' and payintv>0   and (StopFlag='0' or StopFlag is null) "
			+" and exists (select riskcode from lmriskpay WHERE urgepayflag='Y' and lmriskpay.riskcode=lcpol.riskcode) "
			+" and not exists (SELECT polno FROM ljspayperson WHERE  ljspayperson.polno=lcpol.polno and paytype='ZC') "
			+" and not exists (SELECT contno FROM ljspay WHERE  ljspay.otherno=lcpol.contno and othernotype in ('02','03','2','3'))" 
			+" order by polno";
			SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
			sqlbv12.sql(pol_sql);
			sqlbv12.put("tContNo", tContNo);
			LCPolSet xq_LCPolSet= new LCPolSet();
			LCPolDB xq_LCPolDB = new LCPolDB();
			xq_LCPolSet=xq_LCPolDB.executeQuery(sqlbv12);
			for(int j=1;j<=xq_LCPolSet.size();j++)
			{
				
				LCPolSchema tLCPolSchema = new LCPolSchema();
				tLCPolSchema = xq_LCPolSet.get(j);
				
				String tPolNo = tLCPolSchema.getPolNo();
		
				logger.debug("开始处理保险下险种::::::::::" + tLCPolSchema.getRiskCode());
				logger.debug("险种号tPolNo::::::::::::::::::::" + tPolNo);
		
				/** @todo 校验险种状态LCContState.StateType* */
				if (!CheckPolNoState(tPolNo, tLCPolSchema.getPaytoDate())) {
					this.mErrors.addOneError("保单号:" + mContNo + "险种"
							+ tLCPolSchema.getRiskCode() + "已经失效或者终止");
					return false;
				}
                //为了支持保全预约新增附加险，增加特殊判断
                //新增预约附加险的paytodate会大于正常的paytodate(往后推一个交费间隔)，当为新增预约附加险时，跳过该笔，处理其他险种
                if(tLCPolSchema.getPaytoDate().compareTo(this.mEndDate)>0)
                {
                	String BQ_check = " select count(*) from lpedoritem a where a.contno='?contno?' " +
                			"  and a.edortype='NS' and a.edorstate='0'";
                	SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
                	sqlbv13.sql(BQ_check);
                	sqlbv13.put("contno", tLCPolSchema.getContNo());
                    int BQ_count = Integer.parseInt(tExeSQL.getOneValue(sqlbv13)) ;
                	if(BQ_count>0)
                	{
                		BQ_YYXZ_Flag="Y";
                		logger.debug("保单号:" + mContNo + "险种："+tLCPolSchema.getRiskCode()+"为新增附加险，跳过");
                		continue;
                	}
                }
				//校验每一条险种的交至日期是否符合条件，校验错误数据
                if(mStartDate !=null && !mStartDate.equals(""))
                {
                	if( tLCPolSchema.getPaytoDate().compareTo(this.mStartDate)<0 
 						   || tLCPolSchema.getPaytoDate().compareTo(this.mEndDate)>0 )
	 				{
	 					this.mErrors.addOneError("保单号:" + mContNo + "险种"
	 							+ tLCPolSchema.getRiskCode() + "交至日期:"+tLCPolSchema.getPaytoDate()+"  不符合要求");
	 					return false;
	 				}
                }
                else  //过期催收时传入的startdate为空
                {
                	if( tLCPolSchema.getPaytoDate().compareTo(this.mEndDate)>0 )
	 				{
	 					this.mErrors.addOneError("保单号:" + mContNo + "险种"
	 							+ tLCPolSchema.getRiskCode() + "交至日期:"+tLCPolSchema.getPaytoDate()+"  不符合要求");
	 					return false;
	 				}
                	
                }
				
				//校验同一合同号下是否有未达到催收条件的险种，如果有，不允许单独处理续期催收   add by xiongzh 08-12-17
				String str_check="";
				str_check="select '1' from LCRnewStateHistory where contno='?contno?' and state<>'5'" +
						" and state<>'3' and paytodate='?paytodate?' ";
				SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
				sqlbv14.sql(str_check);
				sqlbv14.put("contno", tLCPolSchema.getContNo());
				sqlbv14.put("paytodate", tLCPolSchema.getPaytoDate());
				if(tExeSQL.getOneValue(sqlbv14).equals("1"))
				{
					this.mErrors.addOneError("保单号:" + mContNo + "下仍有其他未达到催收条件的险种，不允许单独催收！");
					return false;
				}
				
				// 计算保单交费日期
				logger.debug("计算新的保单交费日期");
				FDate tD = new FDate();
				String PayToDate = tLCPolSchema.getPaytoDate();
				mLastPaytoDate = tD.getDate(PayToDate);
				logger.debug("原交费日期==" + tD.getString(mLastPaytoDate));
		
				// 交费间隔
				int PayIntv = tLCPolSchema.getPayIntv();

				// 添加日期CValiDate为月交保单28日后的缴费对应日转换
				mCurPayToDate = FinFeePubFun.calOFDate(mLastPaytoDate, PayIntv, "M", tD
							.getDate(main_cvalidate));

				logger.debug("现交至日期==" + tD.getString(mCurPayToDate));
		
				/** @todo 8.2交费日期=失效日期=原交至日期+2个月* */
				int ExtendDays =0; //宽限期
//				//tongmeng 2011-01-17 modify
//				//续期宽限期折算功能
//				Date tPayDate = FinFeePubFun.calOFDate(mLastPaytoDate, Integer.parseInt(tGracePeriod), tGracePreiodUnit, null);
				Date tPayDate = this.calGraceDate(tLCPolSchema);
				mPayDate = tD.getString(tPayDate);
				logger.debug("交费截止日期==" + mPayDate);
		
				/** @todo 进入保费层* */
				String LCPremSql = "select * From lcprem where polno = '?tPolNo?' and UrgePayFlag='Y' and PayStartDate<='?Date?' and PayEndDate>'?Date?'";
				SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
				sqlbv15.sql(LCPremSql);
				sqlbv15.put("tPolNo", tPolNo);
				sqlbv15.put("Date", tLCPolSchema.getPaytoDate());
				logger.debug("LCPremSql==" + LCPremSql);
				LCPremDB tLCPremDB = new LCPremDB();
				LCPremSet tLCPremSet = new LCPremSet();
				tLCPremSet = tLCPremDB.executeQuery(sqlbv15);
				if (tLCPremSet.size() == 0) {
					this.mErrors.addOneError("未找到该保单下的催缴保费信息");
					return false;
				}
				mLJSPayPersonSet.add(DealPrem(tLCPolSchema, tLCPremSet));
				NorInt++;  //处理续期计数器累加
			}
			return true;
		}
		return true;
	}

	// 自动续保申请处理
	private boolean DealAutoRenewsdata_Application(String  tContNo) {
		//首先查询出该保单底下需要做续保的险种
		//此处的保单号已经是经过筛选后的，所以直接找到保单号下的续保件即可
		String tRnewApplySql = "select * from LCPol a where AppFlag='1' "
            + "and PayToDate = PayEndDate "
            + "and RnewFlag = '-1' "
            + "and (StopFlag='0' or StopFlag is null) and GrpPolNo='00000000000000000000' "
            + "and contno='?tContNo?' "
            + "and exists (select 1 from lmrisk where riskcode=a.riskcode and rnewflag='Y') " 
            //续保轨迹表中不能有该保单的非1，5状态。(处理状态1的是为了重新处理自核失败的保单)
            + "and not exists(select '1' from LCRnewStateHistory where contno=a.ContNo " 
            + "and riskcode=a.RiskCode and state in ('2','3','4'))"
            + "order by polno ";
		SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
		sqlbv16.sql(tRnewApplySql);
		sqlbv16.put("tContNo", tContNo);
		logger.debug("查找需要续保申请的险种："+tRnewApplySql);
		ExeSQL tExeSQL = new ExeSQL();
		LCPolSet tLCPolSet = new LCPolSet();
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSet=tLCPolDB.executeQuery(sqlbv16);

        if(tLCPolSet!=null && tLCPolSet.size()>0)
        {
        	//主险续保情况下需要校验主险是否会有老系统中未换号成功的记录
    		if(main_xb_flag.equals("1"))
            {
            	String Check1="";
            	Check1=" select count(*) from lcpol where contno='?tContNo?' and polno=mainpolno and appflag='1'";
            	SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
            	sqlbv17.sql(Check1);
            	sqlbv17.put("tContNo", tContNo);
            	if(Integer.parseInt(tExeSQL.getOneValue(sqlbv17))>1)
            	{
            		logger.debug("主险续保，但在保单险种表中正常记录数大于1，此为错误数据！");
            		this.mErrors.addOneError("保单号:" + mContNo + "主险续保，但在保单险种表中正常记录数大于1，此为错误数据！");
            		return false;
            	}
            }
        	for(int i=1;i<=tLCPolSet.size();i++)
        	{
        		tLCPolSchema= new LCPolSchema();
            	tLCPolSchema=tLCPolSet.get(i);
            	
                //如果主险续保，需要校验主险先产生投保单记录
                if(main_xb_flag.equals("1")&&!tLCPolSchema.getPolNo().equals(tLCPolSchema.getMainPolNo()))
                {
                	String Check1="";
                	Check1=" select count(*) from lcpol where contno='?contno?' and polno=mainpolno and appflag='9'";
                	SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
                	sqlbv18.sql(Check1);
                	sqlbv18.put("contno", tLCPolSchema.getContNo());
                	if(Integer.parseInt(tExeSQL.getOneValue(sqlbv18))==0)
                	{
                		logger.debug("主险续保，主险必须先生成投保单记录！");
                		continue;
                	}
                }
                //为了支持保全预约新增附加险，增加特殊判断
                //新增预约附加险的paytodate会大于正常的paytodate(往后推一个交费间隔)，当为新增预约附加险时，跳过该笔，处理其他险种
                if(tLCPolSchema.getPaytoDate().compareTo(this.mEndDate)>0)
                {
                	String BQ_check = " select count(*) from lpedoritem a where a.contno='?contno?' " +
                			" and a.edortype='NS' and a.edorstate='0' ";
                	SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
                	sqlbv19.sql(BQ_check);
                	sqlbv19.put("contno", tLCPolSchema.getContNo());
                    int BQ_count = Integer.parseInt(tExeSQL.getOneValue(sqlbv19)) ;
                	if(BQ_count>0)
                	{
                		BQ_YYXZ_Flag="Y";
                		NoNeed_count++;
                		logger.debug("保单号:" + mContNo + "险种："+tLCPolSchema.getRiskCode()+"为新增附加险，跳过");
                		continue;
                	}
                	//非预约新增的话，接着往下走，底下还会有对附加险交至日期的判断，有可能是主险月交或季交，附加险续保，所以不能直接返回fales
                	/*
                	else
                	{
                		logger.debug("保单号:" + mContNo + "险种："+tLCPolSchema.getRiskCode()+"交至日期异常，为错误数据！");
                		this.mErrors.addOneError("保单号:" + mContNo + "险种："+tLCPolSchema.getRiskCode()+"交至日期异常，为错误数据！");
                		return false;
                	}
                	*/
                }
                
                //判断该保单是否已经提交过基础数据(但是在自核出现异常)，若是，则直接进入自核
        		String tstr_check="";
        		tstr_check="select (case count(*) when 0 then '0' else '1' end) from LCRnewStateHistory where contno='?contno?' " 
                   + "and riskcode='?riskcode?' and state ='1' ";
        		SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
        		sqlbv20.sql(tstr_check);
        		sqlbv20.put("contno", tLCPolSchema.getContNo());
        		sqlbv20.put("riskcode", tLCPolSchema.getRiskCode());
        		if(tExeSQL.getOneValue(sqlbv20).equals("0")) //没有记录，正常续保申请
        		{
	                //检验主险险种状态
	        		if (!CheckPolNoState(tLCPolSchema.getMainPolNo(), tLCPolSchema
	        				.getPaytoDate())) {
	        			this.mErrors.addOneError("保单号:" + mContNo + "主险已经失效或者终止，附加险不能自动续保");
	        			return false;
	        		}
	        		// 检验附加险险种状态
	        		if (!CheckPolNoState(tLCPolSchema.getPolNo(), tLCPolSchema
	        				.getPaytoDate())) {
	        			this.mErrors.addOneError("保单号:" + mContNo + "险种"
	        					+ tLCPolSchema.getRiskCode() + "已经失效或者终止");
	        			return false;
	        		}
                    //检验附加险险种交至日期
	        		if(!tLCPolSchema.getPolNo().equals(tLCPolSchema.getMainPolNo()))
	        		{
		        		if (!CheckPayTodate(tLCPolSchema))
		        		{
		        			this.mErrors.addOneError("保单号:" + mContNo + "险种"
		        					+ tLCPolSchema.getRiskCode() + "交至日期校验未通过");
		        			return false;
		        		}
	        		}
	        		// 如果主险为月交或者季交的情况，并不是次抽档都会存在自动续保的情况，
	        		// 目前的处理方法是比较主险的缴费对应日中的年、月是否到了附加险的终止日期
	        		// 注意：主险交费期满附加险自动续保的情况不比较
	        		String Sql = "select * from lcpol where polno='?polno?' and paytodate<enddate and appflag='1' and payintv<>0";
	        		SQLwithBindVariables sqlbv21=new SQLwithBindVariables();
	        		sqlbv21.sql(Sql);
	        		sqlbv21.put("polno", tLCPolSchema.getMainPolNo());
	        		LCPolDB xLCPolDB = new LCPolDB();
	        		LCPolSet xLCPolSet = new LCPolSet();
	        		xLCPolSet = xLCPolDB.executeQuery(sqlbv21);
	        		if (xLCPolSet.size() > 0) 
	        		{
	        			if (xLCPolSet.get(1).getPayIntv() > 0&& xLCPolSet.get(1).getPayIntv() != 12) 
	        			{
	        				logger.debug("主险" + xLCPolSet.get(1).getRiskCode()
	        						+ "险种，交费对应日::::::::" + xLCPolSet.get(1).getPaytoDate());
	        				logger.debug("附加险" + tLCPolSchema.getRiskCode()
	        						+ "险种，交费对应日::::::::" + tLCPolSchema.getEndDate());
	        				if (PubFun.calInterval(xLCPolSet.get(1).getPaytoDate(),
	        						tLCPolSchema.getEndDate(), "M") != 0
	        						|| PubFun.calInterval(xLCPolSet.get(1).getPaytoDate(),
	        								tLCPolSchema.getEndDate(), "Y") != 0) 
	        				{
	        					logger.debug("保单号:" + mContNo + "险种"
	        							+ tLCPolSchema.getRiskCode() + "未到自动续保日期");
	        					main_short_flag="Y";
	        					return true;  //此种情况下直接退出续保申请，处理短期主险
	        				}
	        			}
	        		}
	        		//如果以上校验都没有问题，并且非主险短期险的情况下，需要校验当前处理的续保险种的交至日期是否在催收起止期范围内
	        		//此校验为了卡住以下情况：主险长险A，短期续保险B,C,假定当前为2009年，A，B都顺利催收至2010年，但是C由于续保年龄限制或者其他原因未能催收，
	        		//A,B顺利核销至2010年，宽末之前，险种C仍然有效，催收外部查询逻辑由于C的原因将会将此保单纳入催收处理，此时C和之前催收时一样仍然无法催收，
	        		//为了防止对险种B重新续保申请（此时B能通过之前的所有校验），需要加上对paytodate的校验。
					//校验每一条险种的交至日期是否符合条件，校验错误数据
	                if(mStartDate !=null && !mStartDate.equals(""))
	                {
	                	if( tLCPolSchema.getPaytoDate().compareTo(this.mStartDate)<0 
	 						   || tLCPolSchema.getPaytoDate().compareTo(this.mEndDate)>0 )
		 				{
		 					this.mErrors.addOneError("保单号:" + mContNo + "险种"
		 							+ tLCPolSchema.getRiskCode() + "交至日期:"+tLCPolSchema.getPaytoDate()+"  不符合要求");
		 					return false;
		 				}
	                }
	                else  //过期催收时传入的startdate为空
	                {
	                	if( tLCPolSchema.getPaytoDate().compareTo(this.mEndDate)>0 )
		 				{
		 					this.mErrors.addOneError("保单号:" + mContNo + "险种"
		 							+ tLCPolSchema.getRiskCode() + "交至日期:"+tLCPolSchema.getPaytoDate()+"  不符合要求");
		 					return false;
		 				}               	
	                }
	        		
                    //校验过后开始进行具体操作
	                VData tVData = new VData();
	        		tVData.add(tLCPolSchema);
	        		tVData.add(tGI);
	        		AutoReNewDeal tAutoReNewDeal = new AutoReNewDeal();
	        		if (!tAutoReNewDeal.submitData(tVData, "Deal")) {
	        			// mNotRenewPol.add(tAutoReNewDeal.getNotRenewPol());
	        			this.mErrors.copyAllErrors(tAutoReNewDeal.mErrors);
	        			return false;
	        		}
	        		//判断是否该险种不需要续保，如果不满足续保条件,正常退出(例如:被保人年龄超出险种最大被保人年龄,则将mNoNeedFlag置为 Y ,直接进行主险的催收)
	        		String mNoNeedFlag = "";
	        		TransferData xTransferData = new TransferData();
	        		xTransferData = (TransferData) tAutoReNewDeal.getResult().getObjectByObjectName(
	        				"TransferData", 0);
	        		mNoNeedFlag = (String) xTransferData.getValueByName("NoNeedFlag");
	        		if(mNoNeedFlag.equals("Y"))
	        		{
	        			NoNeed_count++;
	        			continue;
	        		}
	        		/** @todo 生成自动续保新数据* */
	        		LCPolSchema nLCPolSchema = new LCPolSchema();
	        		LCPremSet nLCPremSet = new LCPremSet();
	        		LCGetSet nLCGetSet = new LCGetSet();
	        		LCDutySet nLCDutySet = new LCDutySet();
	
	        		nLCPolSchema = (LCPolSchema) tAutoReNewDeal.getResult()
	        				.getObjectByObjectName("LCPolSchema", 0);
	        		nLCPremSet = (LCPremSet) tAutoReNewDeal.getResult()
	        				.getObjectByObjectName("LCPremBLSet", 0);
	        		nLCGetSet = (LCGetSet) tAutoReNewDeal.getResult()
	        				.getObjectByObjectName("LCGetBLSet", 0);
	        		nLCDutySet = (LCDutySet) tAutoReNewDeal.getResult()
	        				.getObjectByObjectName("LCDutyBLSet", 0);
	
	        		//mLJSPayPersonSet.add(DealPrem(tLCPolSchema, nLCPremSet));
	        		mLCPremSet  = new LCPremSet();
	        		mLCGetSet = new LCGetSet();
	        		mLCDutySet = new LCDutySet();
	        		
	        		mLCPremSet.add(nLCPremSet);
	        		mLCGetSet.add(nLCGetSet);
	        		mLCDutySet.add(nLCDutySet);
	                //准备投保数据
	                //准备续保状态日志表
	                LCRnewStateHistorySchema mLCRnewStateHistorySchema = preLCRnewStateHistory(nLCPolSchema);
	        		
	        		//先提交以下五个表的信息以进行核保操作
	        		map_xbapply = new MMap();
	    			map_xbapply.put(nLCPolSchema, "INSERT");
	    			map_xbapply.put(mLCPremSet, "INSERT");
	    			map_xbapply.put(mLCGetSet, "INSERT");
	    			map_xbapply.put(mLCDutySet, "INSERT");
	    			map_xbapply.put(mLCRnewStateHistorySchema, "INSERT");
	        	
	                //提交数据
	    			mResult = new VData();
	    			mResult.add(map_xbapply);
					PubSubmit tSubmit = new PubSubmit();
					if (!tSubmit.submitData(mResult, "")) {
						logger.debug("保单"+tLCPolSchema.getContNo()+"下险种"+tLCPolSchema.getRiskCode()+"核保申请基础数据提交失败！");
						this.mErrors.copyAllErrors(tSubmit.mErrors);
						//ErrCount++;
						mResult.clear();
						return false;
					}
					mResult.clear();
					
					LCPolSet uw_LCPolSet = new LCPolSet();
					uw_LCPolSet.add(nLCPolSchema);
					VData uwVData = new VData();
					uwVData.add(uw_LCPolSet);
					uwVData.add(tGI);
					//开始进行自核
					PRnewUWAutoChkBL tPRnewUWAutoChkBL = new PRnewUWAutoChkBL();
			        if(tPRnewUWAutoChkBL.submitData(uwVData,"")==false)
			         {
			             CError.buildErr(this, "自动核保提交失败:",tPRnewUWAutoChkBL.mErrors);
			             return false;
			         }
        		}
        		else //有核保记录state='1'，直接进入自核
        		{
        			logger.debug("保单"+tLCPolSchema.getContNo()+"下险种"+tLCPolSchema.getRiskCode()+"直接进入自核");
        			tLCPolDB = new LCPolDB(); 
        			tLCPolDB.setContNo(tLCPolSchema.getContNo());
        			tLCPolDB.setRiskCode(tLCPolSchema.getRiskCode());
        			tLCPolDB.setAppFlag("9");
        			if(tLCPolDB.query().size()==0)
        			{
        				this.mErrors.addOneError("保单"+tLCPolSchema.getContNo()+"下险种"+tLCPolSchema.getRiskCode()+ "查找投保单记录失败");
    					return false;
        			}
        			LCPolSchema xLCPolSchema = new LCPolSchema();
        			xLCPolSchema=tLCPolDB.query().get(1);
        			LCPolSet uw_LCPolSet = new LCPolSet();
					uw_LCPolSet.add(xLCPolSchema);
					VData uwVData = new VData();
					uwVData.add(uw_LCPolSet);
					uwVData.add(tGI);
					//开始进行自核
					PRnewUWAutoChkBL tPRnewUWAutoChkBL = new PRnewUWAutoChkBL();
			        if(tPRnewUWAutoChkBL.submitData(uwVData,"")==false)
			         {
			             CError.buildErr(this, "自动核保提交失败:",tPRnewUWAutoChkBL.mErrors);
			             return false;
			         }
        		}
			 logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&");	
        	}     	
        }
		





		

		return true;
	}
	
     //自动续保催收处理
	private boolean DealAutoRenewsdata_deal(String tContNo) 
	{
		//计数：所有续保险种中， 续保状态未到待催收的(lcrnewstate.state=3为核保通过待催收)+不符合续保条件的
		String str_check=" select count(*) from LCPol a where a.AppFlag='1' "
	      +" and a.PayToDate = a.PayEndDate  and a.RnewFlag = '-1' "
	      +" and (a.StopFlag='0' or a.StopFlag is null) and a.GrpPolNo='00000000000000000000' "
	      +" and a.contno='?tContNo?' and exists (select 1 from lmrisk where riskcode=a.riskcode and rnewflag='Y') "
	      +" and ( exists(select 1 from lcrnewstatehistory b where a.contno=b.contno "
	      +" and a.enddate=b.paytodate and a.riskcode=b.riskcode  and b.state<>'3') "
	      +" or not exists(select 1 from lcrnewstatehistory b where a.contno=b.contno "
	      +" and a.enddate=b.paytodate and a.riskcode=b.riskcode ))";
		SQLwithBindVariables sqlbv22=new SQLwithBindVariables();
		sqlbv22.sql(str_check);
		sqlbv22.put("tContNo", tContNo);
		logger.debug("str_check"+str_check);
		
        ExeSQL xExeSQL = new ExeSQL();
        //续保状态不为3的记录为0或者只剩下不需要续保的险种的情况下，允许生成续保应收数据
        if(Integer.parseInt(xExeSQL.getOneValue(sqlbv22))==0 
        	||Integer.parseInt(xExeSQL.getOneValue(sqlbv22))==this.NoNeed_count)
        {
        	xb_sucess_flag="1";  //所有续保成功标志置1
            //如果所有续保保单都顺利通过虚报申请的话，统一生成ljspayperson信息，稍后统一提交
        	String getpol=" select * from LCPol a where a.AppFlag='1' "
      	      +" and a.PayToDate = a.PayEndDate  and a.RnewFlag = '-1' " ;
        	SQLwithBindVariables sqlbv23=new SQLwithBindVariables();
        
        	if(mStartDate !=null && !mStartDate.equals(""))
            {
        		getpol=getpol +" and a.paytodate>='?StartDate?' " ;
        		sqlbv23.put("StartDate", this.mStartDate);
            }
        	getpol= getpol
      	      +" and a.paytodate<='?EndDate?'"
      	      +" and (a.StopFlag='0' or a.StopFlag is null) and a.GrpPolNo='00000000000000000000' "
      	      +" and a.contno='?tContNo?' and exists (select 1 from lmrisk where riskcode=a.riskcode and rnewflag='Y')"
      	      +" and  exists(select 1 from lcrnewstatehistory b where a.contno=b.contno "
  	          +" and a.enddate=b.paytodate and a.riskcode=b.riskcode  and b.state='3') "
      	      +" order by a.polno ";
        	logger.debug(getpol);
        	sqlbv23.sql(getpol);
        	sqlbv23.put("EndDate", this.mEndDate);
        	sqlbv23.put("tContNo", tContNo);
    		LCPolDB xLCPolDB =new LCPolDB();
    		LCPolSet xLCPolSet = new LCPolSet();
    		xLCPolSet=xLCPolDB.executeQuery(sqlbv23);
    		for(int i=1;i<=xLCPolSet.size();i++)
    		{
    			AutoInt++;  //续保计算器加一
    			
    			LCPolSchema xLCPolSchema=new LCPolSchema();
    			xLCPolSchema=xLCPolSet.get(i);
    			
    			//还需要取出投保单
    			LCPolSchema tb_LCPolSchema = new LCPolSchema();
    			LCPolDB tb_LCPolDB =new LCPolDB();
    			tb_LCPolDB.setContNo(xLCPolSchema.getContNo());
    			tb_LCPolDB.setRiskCode(xLCPolSchema.getRiskCode());
    			tb_LCPolDB.setAppFlag("9");
    			if(tb_LCPolDB.query().size()==0)
    			{
    				logger.debug("保单号："+xLCPolSchema.getContNo()+"下的险种"+xLCPolSchema.getRiskCode()+"没有相应的投保单记录！！");
    				return false;
    			}
    			tb_LCPolSchema=tb_LCPolDB.query().get(1);
    			
        		//取出当次催收续保记录的交至日期
    			xb_paytodate=tb_LCPolSchema.getPaytoDate();
    			
    			LCPremSet xLCPremSet = new LCPremSet();
    			
    			LCPremDB xLCPremDB = new LCPremDB();
    			//提取续保件保费项时，由于用的是投保单记录，其paytodate为正常记录的paytodate后推一年，所以用
    			//lcprem.paystartdate<paytodate and lcprem.payenddate>=paytodate  
    			//此处处理和续期件查询lcprem记录不同，续期查询为：lcprem.paystartdate<=paytodate and lcprem.payenddate>paytodate  
    			String lcprem_sql=" select * from lcprem a where a.contno= '?contno?' and a.polno='?polno?'" 
    				+" and UrgePayFlag='Y' and PayStartDate<'?PaytoDate?' and PayEndDate>='?PaytoDate?'";
    			SQLwithBindVariables sqlbv24=new SQLwithBindVariables();
    			sqlbv24.sql(lcprem_sql);
    			sqlbv24.put("contno", tb_LCPolSchema.getContNo());
    			sqlbv24.put("polno", tb_LCPolSchema.getPolNo());
    			sqlbv24.put("PaytoDate", tb_LCPolSchema.getPaytoDate());
    			logger.debug("lcprem_sql:"+lcprem_sql);
    			xLCPremSet=xLCPremDB.executeQuery(sqlbv24);
    			
    			mLJSPayPersonSet.add(DealPrem(xLCPolSchema, xLCPremSet));
    			
               //然后还需要更新lcrnewstatehistory表，将state置为4
    			LCRnewStateHistoryDB update_LCRnewStateHistoryDB = new LCRnewStateHistoryDB();
    			LCRnewStateHistorySchema update_LCRnewStateHistorySchema = new LCRnewStateHistorySchema();
    			update_LCRnewStateHistoryDB.setProposalNo(tb_LCPolSchema.getPolNo());
    			if(!update_LCRnewStateHistoryDB.getInfo())
    			{
    				return false;
    			}
    			update_LCRnewStateHistorySchema=update_LCRnewStateHistoryDB.query().get(1);
    			update_LCRnewStateHistorySchema.setState("4");
    			update_LCRnewStateHistorySchema.setModifyDate(PubFun.getCurrentDate());
		
    			update_LCRnewStateHistorySet.add(update_LCRnewStateHistorySchema);
    		}
    		
    		
        	
        }
		

		return true;
	}

	private LJSPayPersonSet DealPrem(LCPolSchema tLCPolSchema,
			LCPremSet tLCPremSet) {
		/** @todo 根据保费信息生成应收个人纪录* */
		LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
		FDate tFDate = new FDate();
		String tOperator = "";

		// 处理正常缴费
		for (int i = 1; i <= tLCPremSet.size(); i++) {
			LCPremSchema tLCPremSchema = new LCPremSchema();
			tLCPremSchema = tLCPremSet.get(i);
			/** @todo 豁免保单的特殊处理* */
			if (tLCPremSchema.getFreeFlag() != null) {
				String PaytoDate = tFDate.getString(mLastPaytoDate);
				logger.debug("::::::::::::::::::豁免日期校对:::");
				logger.debug("交费对应日==" + PaytoDate);
				logger.debug("豁免起期==" + tLCPremSchema.getFreeStartDate());
				logger.debug("豁免止期==" + tLCPremSchema.getFreeEndDate());
				logger.debug("");

				if (tLCPremSchema.getFreeFlag().equals("1")
						&& tLCPremSchema.getFreeStartDate()
								.compareTo(PaytoDate) <= 0
						&& tLCPremSchema.getFreeEndDate().compareTo(PaytoDate) >= 0) {
					tOperator = "FREE";
				} else {
					tOperator = "ZC";
					for(int j=0;j<currencystr.length;j++)
					{
						if(tLCPremSchema.getCurrency().equals(currencystr[j]))
						{
							sumpaymoneystr[j] = sumpaymoneystr[j] + tLCPremSchema.getPrem();
							sumleavingmoneystr[j] = sumleavingmoneystr[j] - tLCPremSchema.getPrem();// 总余额减去所有应收，如果有剩余记为YET
						}
					}
				}
			} else {
				tOperator = "ZC";
				for(int j=0;j<currencystr.length;j++)
				{
					if(tLCPremSchema.getCurrency().equals(currencystr[j]))
					{
						sumpaymoneystr[j] = sumpaymoneystr[j] + tLCPremSchema.getPrem();
						sumleavingmoneystr[j] = sumleavingmoneystr[j] - tLCPremSchema.getPrem();
					}
				}
			}
			
			LJSPayPersonSet rLJSPayPersonSet = PreperaLjspayperson(tLCPolSchema,
					tLCPremSchema, tOperator);
			tLJSPayPersonSet.add(rLJSPayPersonSet);
			//折扣处理
			LCDiscountDB tLCDiscountDB = new LCDiscountDB();
			LCDiscountSet tLCDiscountSet = new LCDiscountSet();
			tLCDiscountDB.setPolNo(tLCPolSchema.getPolNo());
			tLCDiscountDB.setDutyCode(tLCPremSchema.getDutyCode());
			tLCDiscountSet = tLCDiscountDB.query();
			//************增加折扣处理 start
			if(tLCDiscountSet!=null && tLCDiscountSet.size()>0)
			{
				LCPremBLSet tLCPremBLSet =new LCPremBLSet();
				tLCPremBLSet.add(tLCPremSchema);
				DiscountCalBL tDiscountCalBL = new DiscountCalBL();
				VData tzkVData = new VData();
				tzkVData.add(tLCPolSchema);
				tzkVData.add(tLCPremBLSet);
				tzkVData.add(tLCDiscountSet);
				tzkVData.add(mGetNoticeNo);
				//得到该保单折扣减去的钱 ，为负值
				if(!tDiscountCalBL.calculate(tzkVData))
				{
					CError.buildErr(this, "折扣计算失败！");
					return null;
				}
				
				//得到折扣和应收子表数据
				VData rVData = tDiscountCalBL.getResult();
				tLCDiscountSet = new LCDiscountSet();
				LJSPayPersonSet ttLJSPayPersonSet = (LJSPayPersonSet)rVData.getObjectByObjectName("LJSPayPersonSet", 0);
				if(ttLJSPayPersonSet!=null)
					//重新置值
					tLJSPayPersonSet.add(PreperaLjspaypersonZK(rLJSPayPersonSet.get(1),
							ttLJSPayPersonSet, tOperator));
											
			}
		}		

		//余额YEL记录不再按险种级别生成，此处屏蔽 add by xiongzh 2010-1-12
		/*
		// 处理余额抵缴
		if (tLCPolSchema.getLeavingMoney() > 0) {
			tOperator = "YEL";
			SumPaymoney = SumPaymoney - tLCPolSchema.getLeavingMoney();
			tLJSPayPersonSet.add(PreperaLjspayperson(tLCPolSchema, tLCPremSet
					.get(1), tOperator));
		}
		*/

		return tLJSPayPersonSet;
	}

	private LJSPayPersonSet PreperaLjspayperson(LCPolSchema tLCPolSchema,
			LCPremSchema tLCPremSchema, String tOperator) {
		LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
		String GrpPolNo = tLCPolSchema.getGrpPolNo();
		String AgentCode = tLCPolSchema.getAgentCode();
		String AgentGroup = tLCPolSchema.getAgentGroup();
		String AgentCom = tLCPolSchema.getAgentCom();
		String AgentType = tLCPolSchema.getAgentType();
		String ManageCom = tLCPolSchema.getManageCom();
		String ApproveCode = tLCPolSchema.getApproveCode();
		String ApproveDate = tLCPolSchema.getApproveDate();
		String ApproveTime = tLCPolSchema.getApproveTime();
		String RiskCode = tLCPolSchema.getRiskCode();

		/** @todo 8.1计算新的交至日期 */
		logger.debug("计算新的交费日期");
		logger.debug("原交费日期::::::::::::::" + tLCPolSchema.getPaytoDate());
		FDate tD = new FDate();

		/* 计算此险种的宽限期 */
		Date tPayDate = new Date();

		LMRiskPayDB tLMRiskPayDB = new LMRiskPayDB();
		LMRiskPaySet tLMRiskPaySet = new LMRiskPaySet();
		tLMRiskPayDB.setRiskCode(RiskCode);
		tLMRiskPaySet = tLMRiskPayDB.query();
		int Dayintv = 0;
		String DateType = "D";
		if (tLMRiskPaySet.size() > 0 && tLMRiskPaySet.get(1).getGracePeriodUnit()!=null) {
			Dayintv = tLMRiskPaySet.get(1).getGracePeriod();
			DateType = tLMRiskPaySet.get(1).getGracePeriodUnit();
		}

		String tLastPaytoDate = "";
		String tRnewPayTypeFlag="0";  //续保交费标记 0，非续保，1，续保
		if (tLCPolSchema.getRnewFlag() == -1) {
			tLastPaytoDate = tLCPolSchema.getEndDate();
			tRnewPayTypeFlag="1";
		} else {
			tLastPaytoDate = tLCPolSchema.getPaytoDate();
		}
		tPayDate = FinFeePubFun.calOFDate(tD.getDate(tLastPaytoDate), Dayintv,
				DateType, null);
		logger.debug("险种" + RiskCode + " 宽限期为::::" + tPayDate);

		//当为YEL,YET的宽末时，如果是主险非续保趸交，直接取附加险生成ZC记录时的mPayDate
		//否则宽末会被置为非续保趸交主险的宽末，和本次催收不符。
		if( (tOperator.equals("YEL")||tOperator.equals("YET"))
				&& this.main_LDunJiao_flag.equals("1"))
		{
			tPayDate = tD.getDate(mPayDate);
		}
		else
		{
			if (!mPayDate.equals("") && mPayDate != null) {
				if (mPayDate.compareTo(tLastPaytoDate) < 0) {
					mPayDate = tD.getString(tPayDate);
				}
			} else {
				mPayDate = tD.getString(tPayDate);
			}
		}

		int PayInterval = 0;
		if (tLCPolSchema.getPayIntv() > 0) 
		{
			PayInterval = tLCPolSchema.getPayIntv();
		} 
		else 
		{
			if( (tOperator.equals("YEL")||tOperator.equals("YET"))
					&& this.main_LDunJiao_flag.equals("1"))
			{
				PayInterval = 0;
			}
			else
			{
				// 目前短期附加险都为一年期，应该用lcpol中的InsuYearFlag,InsuYear判断
				PayInterval = 12;
			}

		}

		Date tCurPayToDate = null;
		tCurPayToDate = FinFeePubFun.calOFDate(tD.getDate(tLastPaytoDate),
					PayInterval, "M", tD.getDate(main_cvalidate));
		
		logger.debug("计算交费对应日的校正参数 EndDate ============="
				+ tLCPolSchema.getPayEndDate());
		FYDate t = new FYDate(tLastPaytoDate);
		logger.debug(t.getOracleDate());
		String strNewPayToDate = tD.getString(tCurPayToDate);
		logger.debug("现交至日期::::::::::::::" + strNewPayToDate);

		// 计算保单年度
		String MainPolYearSql = "Select trunc(Months_between('?Date?',lcpol.CValiDate)/12)+1 From lcpol Where polno = '?polno?'";
		SQLwithBindVariables sqlbv25=new SQLwithBindVariables();
		sqlbv25.sql(MainPolYearSql);
		sqlbv25.put("Date", t.getOracleDate());
		sqlbv25.put("polno", tLCPolSchema.getMainPolNo());
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv25);
		String tMainPolyear = tSSRS.GetText(1, 1);

		// 正常交费
		if (tOperator.equals("ZC")) {
			LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
			tLJSPayPersonSchema.setGrpContNo(tLCPremSchema.getGrpContNo());
			tLJSPayPersonSchema.setContNo(tLCPremSchema.getContNo());
			tLJSPayPersonSchema.setMainPolYear(tMainPolyear);
			tLJSPayPersonSchema.setPolNo(tLCPremSchema.getPolNo());
			tLJSPayPersonSchema.setAppntNo(tLCPremSchema.getAppntNo());
			tLJSPayPersonSchema.setGetNoticeNo(mGetNoticeNo);
			if(tLCPolSchema.getRnewFlag()==-1)
			{
				tLJSPayPersonSchema.setPayCount(1); //续保件，交费次数为1
			}
			else
			{
				tLJSPayPersonSchema.setPayCount(tLCPremSchema.getPayTimes() + 1);
			}
			tLJSPayPersonSchema.setDutyCode(tLCPremSchema.getDutyCode());
			tLJSPayPersonSchema.setPayPlanCode(tLCPremSchema.getPayPlanCode());
			tLJSPayPersonSchema.setManageCom(ManageCom);
			tLJSPayPersonSchema.setPayIntv(tLCPolSchema.getPayIntv());
			tLJSPayPersonSchema.setAgentCode(AgentCode);
			tLJSPayPersonSchema.setGrpPolNo(GrpPolNo);
			tLJSPayPersonSchema.setAgentCom(AgentCom);
			tLJSPayPersonSchema.setAgentGroup(AgentGroup);
			tLJSPayPersonSchema.setAgentType(AgentType);
			tLJSPayPersonSchema.setPayAimClass("1");
			tLJSPayPersonSchema.setCurrency(tLCPremSchema.getCurrency());
			tLJSPayPersonSchema.setSumActuPayMoney(tLCPremSchema.getPrem());
			tLJSPayPersonSchema.setSumDuePayMoney(tLCPremSchema.getPrem());
			tLJSPayPersonSchema.setLastPayToDate(tLastPaytoDate);
			tLJSPayPersonSchema.setCurPayToDate(tCurPayToDate);
			tLJSPayPersonSchema.setPayType("ZC");
			tLJSPayPersonSchema.setPayDate(tPayDate);
			tLJSPayPersonSchema.setBankCode(mBankCode);
			tLJSPayPersonSchema.setBankAccNo(mBankAccNo);
			tLJSPayPersonSchema.setBankOnTheWayFlag("0");
			tLJSPayPersonSchema.setBankSuccFlag("0");
			tLJSPayPersonSchema.setApproveCode(ApproveCode);
			tLJSPayPersonSchema.setApproveDate(ApproveDate);
			tLJSPayPersonSchema.setApproveTime(ApproveTime);
			tLJSPayPersonSchema.setRiskCode(RiskCode);
			tLJSPayPersonSchema.setOperator(tGI.Operator);
			if (tRnewPayTypeFlag.equals("1"))
	         {
				tLJSPayPersonSchema.setPayTypeFlag("1");
	         }
			tLJSPayPersonSchema.setMakeDate(CurrentDate);
			tLJSPayPersonSchema.setMakeTime(CurrentTime);
			tLJSPayPersonSchema.setModifyDate(CurrentDate);
			tLJSPayPersonSchema.setModifyTime(CurrentTime);
			tLJSPayPersonSchema.setGrpPolNo(GrpPolNo);
			tLJSPayPersonSet.add(tLJSPayPersonSchema);
			
			//ZC情况下给上期交至日期及当期交至日期赋值
			ZC_LastPayToDate = tD.getDate(tLastPaytoDate);
			ZC_CurPayToDate = tCurPayToDate;
		}
		// 豁免
		else if (tOperator.equals("FREE")) {
			LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
			tLJSPayPersonSchema.setGrpContNo(tLCPremSchema.getGrpContNo());
			tLJSPayPersonSchema.setMainPolYear(tMainPolyear);
			tLJSPayPersonSchema.setContNo(tLCPremSchema.getContNo());
			tLJSPayPersonSchema.setPayIntv(tLCPolSchema.getPayIntv());
			tLJSPayPersonSchema.setPolNo(tLCPremSchema.getPolNo());
			tLJSPayPersonSchema.setAppntNo(tLCPremSchema.getAppntNo());
			tLJSPayPersonSchema.setGetNoticeNo(mGetNoticeNo);
			if(tLCPolSchema.getRnewFlag()==-1)
			{
				tLJSPayPersonSchema.setPayCount(1); //续保件，交费次数为1
			}
			else
			{
				tLJSPayPersonSchema.setPayCount(tLCPremSchema.getPayTimes() + 1);
			}
			tLJSPayPersonSchema.setDutyCode(tLCPremSchema.getDutyCode());
			tLJSPayPersonSchema.setPayPlanCode(tLCPremSchema.getPayPlanCode());
			tLJSPayPersonSchema.setManageCom(ManageCom);
			tLJSPayPersonSchema.setAgentCode(AgentCode);
			tLJSPayPersonSchema.setGrpPolNo(GrpPolNo);
			tLJSPayPersonSchema.setAgentCom(AgentCom);
			tLJSPayPersonSchema.setAgentGroup(AgentGroup);
			tLJSPayPersonSchema.setAgentType(AgentType);
			tLJSPayPersonSchema.setPayAimClass("1");
			tLJSPayPersonSchema.setCurrency(tLCPremSchema.getCurrency());
			tLJSPayPersonSchema.setSumActuPayMoney(tLCPremSchema.getPrem());
			tLJSPayPersonSchema.setSumDuePayMoney(tLCPremSchema.getPrem());
			tLJSPayPersonSchema.setLastPayToDate(mLastPaytoDate);
			tLJSPayPersonSchema.setCurPayToDate(mCurPayToDate);
			tLJSPayPersonSchema.setPayType("HF");
			tLJSPayPersonSchema.setPayDate(tPayDate);
			tLJSPayPersonSchema.setBankCode(mBankCode);
			tLJSPayPersonSchema.setBankAccNo(mBankAccNo);
			tLJSPayPersonSchema.setBankOnTheWayFlag("0");
			tLJSPayPersonSchema.setBankSuccFlag("0");
			tLJSPayPersonSchema.setApproveCode(ApproveCode);
			tLJSPayPersonSchema.setApproveDate(ApproveDate);
			tLJSPayPersonSchema.setApproveTime(ApproveTime);
			tLJSPayPersonSchema.setRiskCode(RiskCode);
			tLJSPayPersonSchema.setOperator(tGI.Operator);
			if (tRnewPayTypeFlag.equals("1"))
	         {
				tLJSPayPersonSchema.setPayTypeFlag("1");
	         }
			tLJSPayPersonSchema.setMakeDate(CurrentDate);
			tLJSPayPersonSchema.setMakeTime(CurrentTime);
			tLJSPayPersonSchema.setModifyDate(CurrentDate);
			tLJSPayPersonSchema.setModifyTime(CurrentTime);
			tLJSPayPersonSet.add(tLJSPayPersonSchema);

			LJSPayPersonSchema nLJSPayPersonSchema = new LJSPayPersonSchema();
			nLJSPayPersonSchema.setGrpContNo(tLCPremSchema.getGrpContNo());
			nLJSPayPersonSchema.setMainPolYear(tMainPolyear);
			nLJSPayPersonSchema.setContNo(tLCPremSchema.getContNo());
			nLJSPayPersonSchema.setPolNo(tLCPremSchema.getPolNo());
			nLJSPayPersonSchema.setAppntNo(tLCPremSchema.getAppntNo());
			nLJSPayPersonSchema.setPayIntv(tLCPolSchema.getPayIntv());
			nLJSPayPersonSchema.setGetNoticeNo(mGetNoticeNo);
			if(tLCPolSchema.getRnewFlag()==-1)
			{
				nLJSPayPersonSchema.setPayCount(1); //续保件，交费次数为1
			}
			else
			{
				nLJSPayPersonSchema.setPayCount(tLCPremSchema.getPayTimes() + 1);
			}
			nLJSPayPersonSchema.setDutyCode(tLCPremSchema.getDutyCode());
			nLJSPayPersonSchema.setPayPlanCode(tLCPremSchema.getPayPlanCode());
			nLJSPayPersonSchema.setManageCom(ManageCom);
			nLJSPayPersonSchema.setAgentCode(AgentCode);
			nLJSPayPersonSchema.setGrpPolNo(GrpPolNo);
			nLJSPayPersonSchema.setAgentCom(AgentCom);
			nLJSPayPersonSchema.setAgentGroup(AgentGroup);
			nLJSPayPersonSchema.setAgentType(AgentType);
			nLJSPayPersonSchema.setPayAimClass("1");
			nLJSPayPersonSchema.setCurrency(tLCPremSchema.getCurrency());
			nLJSPayPersonSchema.setSumActuPayMoney((-1)
					* tLCPremSchema.getPrem());
			nLJSPayPersonSchema.setSumDuePayMoney((-1)
					* tLCPremSchema.getPrem());
			nLJSPayPersonSchema.setLastPayToDate(mLastPaytoDate);
			nLJSPayPersonSchema.setCurPayToDate(mCurPayToDate);
			nLJSPayPersonSchema.setPayType("HM");
			nLJSPayPersonSchema.setPayDate(mPayDate);
			nLJSPayPersonSchema.setBankCode(mBankCode);
			nLJSPayPersonSchema.setBankAccNo(mBankAccNo);
			nLJSPayPersonSchema.setBankOnTheWayFlag("0");
			nLJSPayPersonSchema.setBankSuccFlag("0");
			nLJSPayPersonSchema.setApproveCode(ApproveCode);
			nLJSPayPersonSchema.setApproveDate(ApproveDate);
			nLJSPayPersonSchema.setApproveTime(ApproveTime);
			nLJSPayPersonSchema.setRiskCode(RiskCode);
			nLJSPayPersonSchema.setOperator(tGI.Operator);
			if (tRnewPayTypeFlag.equals("1"))
	         {
				nLJSPayPersonSchema.setPayTypeFlag("1");
	         }
			nLJSPayPersonSchema.setMakeDate(CurrentDate);
			nLJSPayPersonSchema.setMakeTime(CurrentTime);
			nLJSPayPersonSchema.setModifyDate(CurrentDate);
			nLJSPayPersonSchema.setModifyTime(CurrentTime);
			tLJSPayPersonSet.add(nLJSPayPersonSchema);
			
			//豁免情况下给上期交至日期及当期交至日期赋值
			ZC_LastPayToDate = mLastPaytoDate ;
			ZC_CurPayToDate = mCurPayToDate;
			
		} // 使用每个险种的余额
		else if (tOperator.equals("YEL")) {
			LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
			tLJSPayPersonSchema.setGrpContNo(tLCPremSchema.getGrpContNo());
			tLJSPayPersonSchema.setContNo(tLCPremSchema.getContNo());
			tLJSPayPersonSchema.setMainPolYear(tMainPolyear);
			tLJSPayPersonSchema.setPolNo(tLCPremSchema.getPolNo());
			tLJSPayPersonSchema.setAppntNo(tLCPremSchema.getAppntNo());
			tLJSPayPersonSchema.setGetNoticeNo(mGetNoticeNo);
			if(tLCPolSchema.getRnewFlag()==-1)
			{
				tLJSPayPersonSchema.setPayCount(1); //续保件，交费次数为1
			}
			else
			{
				tLJSPayPersonSchema.setPayCount(tLCPremSchema.getPayTimes() + 1);
			}
			tLJSPayPersonSchema.setDutyCode(tLCPremSchema.getDutyCode());
			tLJSPayPersonSchema.setPayPlanCode(tLCPremSchema.getPayPlanCode());
			tLJSPayPersonSchema.setManageCom(ManageCom);
			tLJSPayPersonSchema.setPayIntv(tLCPolSchema.getPayIntv());
			tLJSPayPersonSchema.setAgentCode(AgentCode);
			tLJSPayPersonSchema.setGrpPolNo(GrpPolNo);
			tLJSPayPersonSchema.setAgentCom(AgentCom);
			tLJSPayPersonSchema.setAgentGroup(AgentGroup);
			tLJSPayPersonSchema.setAgentType(AgentType);
			tLJSPayPersonSchema.setPayAimClass("1");
			tLJSPayPersonSchema.setCurrency(tLCPremSchema.getCurrency());
			tLJSPayPersonSchema.setSumActuPayMoney(-oldLeavingMoney);// 取余额的负值
			tLJSPayPersonSchema.setSumDuePayMoney(-oldLeavingMoney);// 取余额的负值
			tLJSPayPersonSchema.setLastPayToDate(ZC_LastPayToDate);
			tLJSPayPersonSchema.setCurPayToDate(ZC_CurPayToDate);
			tLJSPayPersonSchema.setPayType("YEL");
			tLJSPayPersonSchema.setPayDate(tPayDate);
			tLJSPayPersonSchema.setBankCode(mBankCode);
			tLJSPayPersonSchema.setBankAccNo(mBankAccNo);
			tLJSPayPersonSchema.setBankOnTheWayFlag("0");
			tLJSPayPersonSchema.setBankSuccFlag("0");
			tLJSPayPersonSchema.setApproveCode(ApproveCode);
			tLJSPayPersonSchema.setApproveDate(ApproveDate);
			tLJSPayPersonSchema.setApproveTime(ApproveTime);
			tLJSPayPersonSchema.setRiskCode(RiskCode);
			tLJSPayPersonSchema.setOperator(tGI.Operator);
			if (tRnewPayTypeFlag.equals("1"))
	         {
				tLJSPayPersonSchema.setPayTypeFlag("1");
	         }
			tLJSPayPersonSchema.setMakeDate(CurrentDate);
			tLJSPayPersonSchema.setMakeTime(CurrentTime);
			tLJSPayPersonSchema.setModifyDate(CurrentDate);
			tLJSPayPersonSchema.setModifyTime(CurrentTime);
			tLJSPayPersonSchema.setGrpPolNo(GrpPolNo);
			tLJSPayPersonSet.add(tLJSPayPersonSchema);
		}// 余额总额>应收的部分，记在主险上面
		else if (tOperator.equals("YET")) {
			LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
			tLJSPayPersonSchema.setGrpContNo(tLCPremSchema.getGrpContNo());
			tLJSPayPersonSchema.setContNo(tLCPremSchema.getContNo());
			tLJSPayPersonSchema.setMainPolYear(tMainPolyear);
			tLJSPayPersonSchema.setPolNo(tLCPremSchema.getPolNo());
			tLJSPayPersonSchema.setAppntNo(tLCPremSchema.getAppntNo());
			tLJSPayPersonSchema.setGetNoticeNo(mGetNoticeNo);
			if(tLCPolSchema.getRnewFlag()==-1)
			{
				tLJSPayPersonSchema.setPayCount(1); //续保件，交费次数为1
			}
			else
			{
				tLJSPayPersonSchema.setPayCount(tLCPremSchema.getPayTimes() + 1);
			}
			tLJSPayPersonSchema.setDutyCode(tLCPremSchema.getDutyCode());
			tLJSPayPersonSchema.setPayPlanCode(tLCPremSchema.getPayPlanCode());
			tLJSPayPersonSchema.setManageCom(ManageCom);
			tLJSPayPersonSchema.setPayIntv(tLCPolSchema.getPayIntv());
			tLJSPayPersonSchema.setAgentCode(AgentCode);
			tLJSPayPersonSchema.setGrpPolNo(GrpPolNo);
			tLJSPayPersonSchema.setAgentCom(AgentCom);
			tLJSPayPersonSchema.setAgentGroup(AgentGroup);
			tLJSPayPersonSchema.setAgentType(AgentType);
			tLJSPayPersonSchema.setPayAimClass("1");
			tLJSPayPersonSchema.setCurrency(tLCPremSchema.getCurrency());
			tLJSPayPersonSchema.setSumActuPayMoney(sumLeavingMoney);//原保单余额减去本次应收后剩下的金额
			tLJSPayPersonSchema.setSumDuePayMoney(sumLeavingMoney);
			tLJSPayPersonSchema.setLastPayToDate(ZC_LastPayToDate);
			tLJSPayPersonSchema.setCurPayToDate(ZC_CurPayToDate);
			tLJSPayPersonSchema.setPayType("YET");
			tLJSPayPersonSchema.setPayDate(tPayDate);
			tLJSPayPersonSchema.setBankCode(mBankCode);
			tLJSPayPersonSchema.setBankAccNo(mBankAccNo);
			tLJSPayPersonSchema.setBankOnTheWayFlag("0");
			tLJSPayPersonSchema.setBankSuccFlag("0");
			tLJSPayPersonSchema.setApproveCode(ApproveCode);
			tLJSPayPersonSchema.setApproveDate(ApproveDate);
			tLJSPayPersonSchema.setApproveTime(ApproveTime);
			tLJSPayPersonSchema.setRiskCode(RiskCode);
			tLJSPayPersonSchema.setOperator(tGI.Operator);
			if (tRnewPayTypeFlag.equals("1"))
	         {
				tLJSPayPersonSchema.setPayTypeFlag("1");
	         }
			tLJSPayPersonSchema.setMakeDate(CurrentDate);
			tLJSPayPersonSchema.setMakeTime(CurrentTime);
			tLJSPayPersonSchema.setModifyDate(CurrentDate);
			tLJSPayPersonSchema.setModifyTime(CurrentTime);
			tLJSPayPersonSchema.setGrpPolNo(GrpPolNo);
			tLJSPayPersonSet.add(tLJSPayPersonSchema);
		}

		return tLJSPayPersonSet;
	}
	
	/*
	 * 重置折扣应收信息
	 * */
	private LJSPayPersonSet PreperaLjspaypersonZK(LJSPayPersonSchema oLJSPayPersonSchema,
			LJSPayPersonSet oLJSPayPersonSet, String tOperator) {
		LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
		Reflections tReflections = new Reflections();
		// 正常交费
		if (tOperator.equals("ZC")) {
			for(int i=1;i<=oLJSPayPersonSet.size();i++)
			{
				LJSPayPersonSchema lLJSPayPersonSchema = oLJSPayPersonSet.get(i);
				
				LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();								
				tReflections.transFields(tLJSPayPersonSchema, oLJSPayPersonSchema);
				tLJSPayPersonSchema.setPayType(lLJSPayPersonSchema.getPayType());
				tLJSPayPersonSchema.setSumActuPayMoney(lLJSPayPersonSchema.getSumActuPayMoney());
				tLJSPayPersonSchema.setSumDuePayMoney(lLJSPayPersonSchema.getSumDuePayMoney());
				
				tLJSPayPersonSet.add(tLJSPayPersonSchema);
			}		
		}
		// 豁免
		else if (tOperator.equals("FREE")) {
			for(int i=1;i<=oLJSPayPersonSet.size();i++)
			{
				LJSPayPersonSchema lLJSPayPersonSchema = oLJSPayPersonSet.get(i);
				
				LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();								
				tReflections.transFields(tLJSPayPersonSchema, oLJSPayPersonSchema);
				tLJSPayPersonSchema.setPayType("HF"+lLJSPayPersonSchema.getPayType());
				tLJSPayPersonSchema.setSumActuPayMoney(lLJSPayPersonSchema.getSumActuPayMoney());
				tLJSPayPersonSchema.setSumDuePayMoney(lLJSPayPersonSchema.getSumDuePayMoney());
				tLJSPayPersonSet.add(tLJSPayPersonSchema);
				
				LJSPayPersonSchema nLJSPayPersonSchema = new LJSPayPersonSchema();								
				tReflections.transFields(nLJSPayPersonSchema, oLJSPayPersonSchema);
				nLJSPayPersonSchema.setPayType("HM"+lLJSPayPersonSchema.getPayType());
				nLJSPayPersonSchema.setSumActuPayMoney((-1)
						* lLJSPayPersonSchema.getSumActuPayMoney());
				nLJSPayPersonSchema.setSumDuePayMoney((-1)
						* lLJSPayPersonSchema.getSumDuePayMoney());
				
				tLJSPayPersonSet.add(nLJSPayPersonSchema);
			}	
			
		}

		return tLJSPayPersonSet;
	}
	
	/**
	 * 处理实收保费
	 * 目前只生成实收总表
	 * @return
	 */
	private boolean dealJSPay(LJSPayPersonSet tLJSPayPersonSet,LCPolSet tLCPolSet) {
		LJSPaySet tLJSPaySet = new LJSPaySet();
		LJSPaySchema tLJSPaySchema = new LJSPaySchema();
		//按照币种汇总LJSpay
		Hashtable tCurrencyMap = new Hashtable();
		Hashtable taxAmountMap = new Hashtable();
		Hashtable netAmountMap = new Hashtable();
		TaxCalculator.calBySchemaSet(mLJSPayPersonSet);
		for(int i=1;i<=tLJSPayPersonSet.size();i++)
		{
			//按照币种汇总
			
			String tCurrency = tLJSPayPersonSet.get(i).getCurrency();
			if(!tCurrencyMap.containsKey(tCurrency))
			{
				tCurrencyMap.put(tCurrency, tLJSPayPersonSet.get(i).getSumDuePayMoney());
				taxAmountMap.put(tCurrency, tLJSPayPersonSet.get(i).getTaxAmount());
				netAmountMap.put(tCurrency, tLJSPayPersonSet.get(i).getNetAmount());
			}
			else
			{
				double tempSumpay = PubFun.round((Double)tCurrencyMap.get(tCurrency), 2) + PubFun.round(tLJSPayPersonSet.get(i).getSumDuePayMoney(),2);
				tCurrencyMap.put(tCurrency, tempSumpay);
				double tempTaxAmount = PubFun.round((Double)taxAmountMap.get(tCurrency), 2)+PubFun.round(tLJSPayPersonSet.get(i).getTaxAmount(),2);
				taxAmountMap.put(tCurrency, tempTaxAmount);
				double tempNetAmount = PubFun.round((Double)netAmountMap.get(tCurrency), 2)+PubFun.round(tLJSPayPersonSet.get(i).getNetAmount(),2);
				netAmountMap.put(tCurrency,tempNetAmount);
			}			
		}	
		
		Enumeration eKey=tCurrencyMap.keys(); 
		while(eKey.hasMoreElements()) 
		{ 
			String key=(String)eKey.nextElement();
			double tValue = PubFun.round(((Double)tCurrencyMap.get(key)),2);
			double taxAmount = PubFun.round(((Double)taxAmountMap.get(key)),2);
			double netAmount = PubFun.round(((Double)netAmountMap.get(key)),2);
			tLJSPaySchema = new LJSPaySchema();
			tLJSPaySchema.setGetNoticeNo(mGetNoticeNo); // 通知书号
			tLJSPaySchema.setOtherNo(mLCContSchema.getContNo());
	
			// 增加渠道，增加othernotype
//			if (mLCContSchema.getSaleChnl().equals("1")) // 个人
//				tLJSPaySchema.setOtherNoType("2");
//			else if (mLCContSchema.getSaleChnl().equals("3")) // 银代
//				tLJSPaySchema.setOtherNoType("3");
//			else if (mLCContSchema.getSaleChnl().equals("5")) // 电话直销
//				tLJSPaySchema.setOtherNoType("8");
//			else {
//				tLJSPaySchema.setOtherNoType("2");
//			}
			tLJSPaySchema.setOtherNoType("2");
			
			tLJSPaySchema.setAppntNo(mLCContSchema.getAppntNo());
			tLJSPaySchema.setPayDate(mPayDate);
			logger.debug("^^^^^^^^^^^^^^^^^^^^^^^^^^"
					+ mLJSPayPersonSet.get(1).getLastPayToDate());
			tLJSPaySchema.setStartPayDate(mLJSPayPersonSet.get(1)
					.getLastPayToDate()); // 交费最早应缴日期保存上次交至日期
			tLJSPaySchema.setBankOnTheWayFlag("0");
			tLJSPaySchema.setBankSuccFlag("0");
			tLJSPaySchema.setSendBankCount(0); // 送银行次数
			tLJSPaySchema.setApproveCode(mLCContSchema.getApproveCode());
			tLJSPaySchema.setApproveDate(mLCContSchema.getApproveDate());
			tLJSPaySchema.setRiskCode(tLCPolSet.get(1).getRiskCode());  //置为主险险种
			tLJSPaySchema.setBankAccNo(mBankAccNo);
			tLJSPaySchema.setBankCode(mBankCode);
			tLJSPaySchema.setSendBankCount(0);
			tLJSPaySchema.setSerialNo(""); // 流水号
			tLJSPaySchema.setOperator(tGI.Operator);
			//新增加投保人证件类型及证件号
			LCAppntSchema tLCAppntSchema = new LCAppntSchema();
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mContNo);
			if(!tLCAppntDB.getInfo())
			{
				this.mErrors.addOneError("保单号:" + mContNo + "投保人信息查询失败！");
				return false;
			}
			tLCAppntSchema = tLCAppntDB.getSchema();
			tLJSPaySchema.setIDType(tLCAppntSchema.getIDType());
			tLJSPaySchema.setIDNo(tLCAppntSchema.getIDNo());
			
			if(tLCPolSet.get(1).getPayIntv()==0)
			//主险趸交（长期险趸交）或者续保
			{
				tLJSPaySchema.setPayTypeFlag("1");
			}
			tLJSPaySchema.setManageCom(mLCContSchema.getManageCom());
			tLJSPaySchema.setAgentCom(mLCContSchema.getAgentCom());
			tLJSPaySchema.setAgentCode(mLCContSchema.getAgentCode());
			tLJSPaySchema.setAgentType(mLCContSchema.getAgentType());
			tLJSPaySchema.setAgentGroup(mLCContSchema.getAgentGroup());
			tLJSPaySchema.setAccName(mBankAccName);
			tLJSPaySchema.setMakeDate(CurrentDate);
			tLJSPaySchema.setMakeTime(CurrentTime);
			tLJSPaySchema.setModifyDate(CurrentDate);
			tLJSPaySchema.setModifyTime(CurrentTime);
			tLJSPaySchema.setCurrency(key);
			tLJSPaySchema.setSumDuePayMoney(tValue);
			tLJSPaySchema.setTaxAmount(taxAmount);
			tLJSPaySchema.setNetAmount(netAmount);
			mLJSPaySet.add(tLJSPaySchema);
		}
		
		return true;
	}

	private boolean CheckPolNoState(String tPolNo, String tDate) {
		// MOD BY GAOHT @ 2007-3-5
		// 保全第一阶段优化方案修改
		// 1、 保全退保回退对于续期应收数据的恢复处理；
		String StateSql = "select * from LCContState where StateType in ('Available','Terminate') and State='1' and contno='?mContNo?' and polno = '?tPolNo?' and enddate is null";
		SQLwithBindVariables sqlbv26=new SQLwithBindVariables();
		sqlbv26.sql(StateSql);
		sqlbv26.put("mContNo", mContNo);
		sqlbv26.put("tPolNo", tPolNo);
		logger.debug("校验险种是否中止、失效:::::::::::::::::::" + StateSql);

		LCContStateDB tLCContStateDB = new LCContStateDB();
		LCContStateSet tLCContStateSet = new LCContStateSet();
		tLCContStateSet = tLCContStateDB.executeQuery(sqlbv26);

		if (tLCContStateSet.size() > 0) {
			// 险种中止、失效
			return false;
		}

		return true;
	}
   //校验续保附加险交至日期
	private boolean CheckPayTodate(LCPolSchema tLCPolSchema) 
	{
		LCPolSchema xmain_LCPolSchema = new LCPolSchema();
		LCPolDB xLCPolDB = new LCPolDB();
		xLCPolDB.setPolNo(tLCPolSchema.getMainPolNo());
		if(!xLCPolDB.getInfo())
		{
			this.mErrors.addOneError("保单号:" + mContNo + "主险险种查询失败！");
			return false;
		}
		xmain_LCPolSchema = xLCPolDB.getSchema();
		int rnewtype_flag=0; //主险是否是续保险种标记
		ExeSQL xxExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv27=new SQLwithBindVariables();
		sqlbv27.sql("select count(*) from lmrisk where riskcode='?riskcode?' and rnewflag='Y' ");
		sqlbv27.put("riskcode", xmain_LCPolSchema.getRiskCode());
		rnewtype_flag =Integer.parseInt
		( xxExeSQL.getOneValue(sqlbv27) );
		logger.debug("主险续保险种标记："+rnewtype_flag);
		if(xmain_LCPolSchema.getPayIntv()==12)
		{
			//主险年交，附加险续保，附加险paytodate必须等于主险paytodate
			if(!xmain_LCPolSchema.getPaytoDate().equals(tLCPolSchema.getPaytoDate()))
			{
				this.mErrors.addOneError("保单号:" + mContNo + "险种号"+tLCPolSchema.getPolNo()+"交至日期与主险不一致！");
				return false;
			}
		}
		else if(xmain_LCPolSchema.getPayIntv()==0)
		{
			if(xmain_LCPolSchema.getRnewFlag()==-1)//主险续保
			{
				//主险续保，附加险续保，附加险paytodate必须等于主险paytodate
				if(!xmain_LCPolSchema.getPaytoDate().equals(tLCPolSchema.getPaytoDate()))
				{
					this.mErrors.addOneError("保单号:" + mContNo + "险种号"+tLCPolSchema.getPolNo()+"交至日期与主险不一致！");
					return false;
				}
			}
			//主险为续保险种，但是并未续保，则其附加险也不允许续保
			if(xmain_LCPolSchema.getRnewFlag()!=-1&&rnewtype_flag==1)
			{
				this.mErrors.addOneError("保单号:" + mContNo + "主险为续保险种，但是并未续保，则其附加险也不允许续保！");
				return false;
			}
			//主险趸交且非续保险种，附加险交至日期必须在应收区间之内
			if(xmain_LCPolSchema.getRnewFlag()!=-1&&rnewtype_flag!=1)
			{
				if(mStartDate !=null && !mStartDate.equals(""))
                {
                	if( tLCPolSchema.getPaytoDate().compareTo(this.mStartDate)<0 
 						   || tLCPolSchema.getPaytoDate().compareTo(this.mEndDate)>0 )
	 				{
	 					this.mErrors.addOneError("保单号:" + mContNo + "险种"
	 							+ tLCPolSchema.getRiskCode() + "交至日期:"+tLCPolSchema.getPaytoDate()+"  不符合要求");
	 					return false;
	 				}
                }
                else  //过期催收时传入的startdate为空
                {
                	if( tLCPolSchema.getPaytoDate().compareTo(this.mEndDate)>0 )
	 				{
	 					this.mErrors.addOneError("保单号:" + mContNo + "险种"
	 							+ tLCPolSchema.getRiskCode() + "交至日期:"+tLCPolSchema.getPaytoDate()+"  不符合要求");
	 					return false;
	 				}
                	
                }
			}
			
		}
		return true;
	}
	
	// 准备渠道所用到的应收个人交费表
	private LASPayPersonSet PreperaLAPay(LJSPayPersonSet tLJSPayPersonSet) {
		VData tVData = new VData();
		LASPayPersonSet tLASPayPersonSet = new LASPayPersonSet();
		for (int i = 1; i <= tLJSPayPersonSet.size(); i++) {
			LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
			LASPayPersonSchema tLASPayPersonSchema = new LASPayPersonSchema();
			tLJSPayPersonSchema = tLJSPayPersonSet.get(i);
			tLASPayPersonSchema.setActuPayFlag("0");
			tLASPayPersonSchema
					.setAgentCode(tLJSPayPersonSchema.getAgentCode());
			tLASPayPersonSchema.setMainPolYear(tLJSPayPersonSchema
					.getMainPolYear());
			tLASPayPersonSchema.setAgentGroup(tLJSPayPersonSchema
					.getAgentGroup());
			tLASPayPersonSchema.setApproveCode(tLJSPayPersonSchema
					.getApproveCode());
			tLASPayPersonSchema.setApproveDate(tLJSPayPersonSchema
					.getApproveDate());
			tLASPayPersonSchema.setApproveTime(tLJSPayPersonSchema
					.getApproveTime());
			tLASPayPersonSchema.setContNo(tLJSPayPersonSchema.getContNo());
			tLASPayPersonSchema.setCurPayToDate(tLJSPayPersonSchema
					.getCurPayToDate());
			tLASPayPersonSchema.setDutyCode(tLJSPayPersonSchema.getDutyCode());
			tLASPayPersonSchema.setGetNoticeNo(tLJSPayPersonSchema
					.getGetNoticeNo());
			tLASPayPersonSchema.setGrpPolNo(tLJSPayPersonSchema.getGrpPolNo());
			tLASPayPersonSchema
					.setGrpContNo(tLJSPayPersonSchema.getGrpContNo());
			tLASPayPersonSchema.setLastPayToDate(tLJSPayPersonSchema
					.getLastPayToDate());
			tLASPayPersonSchema.setMakeDate(CurrentDate);
			tLASPayPersonSchema.setMakeTime(CurrentTime);
			tLASPayPersonSchema
					.setManageCom(tLJSPayPersonSchema.getManageCom());
			tLASPayPersonSchema.setModifyTime(CurrentTime);
			tLASPayPersonSchema.setModifyDate(CurrentDate);
			tLASPayPersonSchema.setOperator(tGI.Operator);
			tLASPayPersonSchema.setPayAimClass(tLJSPayPersonSchema
					.getPayAimClass());
			tLASPayPersonSchema.setPayCount(tLJSPayPersonSchema.getPayCount());
			tLASPayPersonSchema.setPayDate(tLJSPayPersonSchema.getPayDate());
			tLASPayPersonSchema.setPayIntv(tLJSPayPersonSchema.getPayIntv());
			tLASPayPersonSchema.setPayPlanCode(tLJSPayPersonSchema
					.getPayPlanCode());
			tLASPayPersonSchema.setPayTypeFlag(tLJSPayPersonSchema
					.getPayTypeFlag());
			tLASPayPersonSchema.setPayType(tLJSPayPersonSchema.getPayType());
			tLASPayPersonSchema.setPolNo(tLJSPayPersonSchema.getPolNo());
			tLASPayPersonSchema.setRiskCode(tLJSPayPersonSchema.getRiskCode());
			tLASPayPersonSchema.setSumActuPayMoney(tLJSPayPersonSchema
					.getSumActuPayMoney());
			tLASPayPersonSchema.setSumDuePayMoney(tLJSPayPersonSchema
					.getSumDuePayMoney());

			String tContNo = tLJSPayPersonSchema.getContNo();
			String tSql = "select poltype from lacommisiondetail where grpcontno = '?tContNo?'";
			SQLwithBindVariables sqlbv28=new SQLwithBindVariables();
			sqlbv28.sql(tSql);
			sqlbv28.put("tContNo", tContNo);
			SSRS nSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			nSSRS = tExeSQL.execSQL(sqlbv28);
			if (nSSRS.getMaxRow() > 0) {
				String tpoltype = nSSRS.GetText(1, 1);
				tLASPayPersonSchema.setPolType(tpoltype);
			}
			// tSql = "select BranchSeries from LABranchGroup where AgentGroup =
			// '"
			// + tLJSPayPersonSchema.getAgentGroup() + "'";
			// nSSRS = new SSRS();
			// tExeSQL = new ExeSQL();
			// nSSRS = tExeSQL.execSQL(tSql);
			// if (nSSRS.getMaxRow() > 0) {
			// String tBranchSeries = nSSRS.GetText(1, 1);
			// tLASPayPersonSchema.setBranchSeries(tBranchSeries);
			// }
			tLASPayPersonSet.add(tLASPayPersonSchema);
		}

		return tLASPayPersonSet;
	}

	private VData getPrintData(LCContSchema tLCContchema,
			LJSPaySchema mLJSPaySchema, String prtSeqNo) {
		VData tVData = new VData();
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

		try {
			tLOPRTManagerSchema.setPrtSeq(prtSeqNo);
			tLOPRTManagerSchema.setOtherNo(tLCContchema.getContNo());
			tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT);  //合同号

			//仅仅处理个险，续期全置47
		    tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PRnewNotice);


			tLOPRTManagerSchema.setManageCom(tLCContchema.getManageCom());
			tLOPRTManagerSchema.setAgentCode(tLCContchema.getAgentCode());
			tLOPRTManagerSchema.setReqCom(tLCContchema.getManageCom());
			tLOPRTManagerSchema.setReqOperator(tLCContchema.getOperator());
			tLOPRTManagerSchema.setPrtType("0");
			tLOPRTManagerSchema.setStateFlag("0");
			tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			tLOPRTManagerSchema.setOldPrtSeq(prtSeqNo);
			tLOPRTManagerSchema.setStandbyFlag1(tLCContchema.getInsuredName());
			tLOPRTManagerSchema.setStandbyFlag2(mLJSPaySchema.getGetNoticeNo());
			tLOPRTManagerSchema
					.setStandbyFlag3(mLJSPaySchema.getStartPayDate());
			tLOPRTManagerSchema.setPatchFlag("0");
		} catch (Exception ex) {
			return null;
		}

		String tLimit = PubFun.getNoLimit(tLCContchema.getManageCom());
		String tNo = PubFun1.CreateMaxNo("PAYNOTICENO", tLimit);
		// 产生打印通知书号
		String prtSeqNo1 = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
		// 发票（个人、银代）
		LOPRTManagerSchema dLOPRTManagerSchema = new LOPRTManagerSchema();
		try {
			dLOPRTManagerSchema.setPrtSeq(prtSeqNo1);
			dLOPRTManagerSchema.setOtherNo(tLCContchema.getContNo());
			dLOPRTManagerSchema.setOtherNoType("02");

            //仅仅处理个险
			dLOPRTManagerSchema.setCode("32");
			dLOPRTManagerSchema.setManageCom(tLCContchema.getManageCom());
			dLOPRTManagerSchema.setAgentCode(tLCContchema.getAgentCode());
			dLOPRTManagerSchema.setReqCom(tLCContchema.getManageCom());
			dLOPRTManagerSchema.setReqOperator(tLCContchema.getOperator());
			dLOPRTManagerSchema.setPrtType("0");
			dLOPRTManagerSchema.setStateFlag("0");
			dLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			dLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			dLOPRTManagerSchema.setOldPrtSeq(prtSeqNo1);

			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet tLCPolSet = new LCPolSet();
			String psql = "select * from lcpol where contno='?contno?' and mainpolno=polno ";
			SQLwithBindVariables sqlbv29=new SQLwithBindVariables();
			sqlbv29.sql(psql);
			sqlbv29.put("contno", tLCContchema.getContNo());
			tLCPolSet = tLCPolDB.executeQuery(sqlbv29);
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(1).getSchema(); // 目前一个合同只有一个主险
			String tpaytodate = tLCPolSchema.getPaytoDate();
			dLOPRTManagerSchema.setStandbyFlag3(tpaytodate);
			dLOPRTManagerSchema.setStandbyFlag4(String.valueOf(mLJSPaySchema
					.getSumDuePayMoney()));
			dLOPRTManagerSchema.setStandbyFlag1(mLJSPaySchema.getGetNoticeNo());
			dLOPRTManagerSchema.setPatchFlag("0");
		} catch (Exception ex) {
			return null;
		}

		LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();
		tLOPRTManagerSet.add(tLOPRTManagerSchema);
		tLOPRTManagerSet.add(dLOPRTManagerSchema);
		tVData.add(tLOPRTManagerSet);
		return tVData;
	}

	private LOPRTManagerSubSet PreperaLOPRTManagerSub(LCPolSet tLCPolSet,
			LJSPayPersonSet tLJSPayPersonSet) {
		LOPRTManagerSubSet tLOPRTManagerSubSet = new LOPRTManagerSubSet();
		//若为主险短期险单独催收，需要重新置LCPolSet
		if(main_short_flag.equals("Y"))
		{
			String lcpol_str=" select * from lcpol where contno='?contno?' and appflag='1' and mainpolno=polno ";
			SQLwithBindVariables sqlbv30=new SQLwithBindVariables();
			sqlbv30.sql(lcpol_str);
			sqlbv30.put("contno", this.mContNo);
		    LCPolDB  xxLCPolDB = new LCPolDB();
		    tLCPolSet = xxLCPolDB.executeQuery(sqlbv30);
		}
		//若存在预约新增附加险，需要重新置lcpolset，排除预约新增附加险
		if(BQ_YYXZ_Flag.equals("Y"))
		{
			String lcpol_str=" select * from lcpol where contno='?contno?' and appflag='1' " 
			+" and paytodate<='?paytodate?' ";
			SQLwithBindVariables sqlbv31=new SQLwithBindVariables();
			sqlbv31.sql(lcpol_str);
			sqlbv31.put("contno", this.mContNo);
			sqlbv31.put("paytodate", this.mEndDate);
			if(mStartDate !=null && !mStartDate.equals(""))
            {
				lcpol_str=lcpol_str +" and paytodate>='"+this.mStartDate+"' ";
            }
		    LCPolDB  xxLCPolDB = new LCPolDB();
		    tLCPolSet = xxLCPolDB.executeQuery(sqlbv31);
		}

		/** @todo 生成打印子表数据打印* */
		for (int t = 1; t <= tLCPolSet.size(); t++) {
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(t);
			String tRiskCode = tLCPolSchema.getRiskCode();
			double RiskPrem = 0;
			// 取每个险种下的保费
            //催收标记，判断保单下险种当前是否催收，没有催收数据的险种不生成打印子表信息
			String csflag="0"; 
			for (int i = 1; i <= tLJSPayPersonSet.size(); i++) {
				LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
				tLJSPayPersonSchema = tLJSPayPersonSet.get(i);
				if (tLJSPayPersonSchema.getRiskCode().equals(tRiskCode)) {
					RiskPrem = RiskPrem
							+ tLJSPayPersonSchema.getSumDuePayMoney();
					csflag="1";
				}
			}
			if(csflag.equals("0"))
			{
				logger.debug("保单号："+tLCPolSchema.getContNo()+"下险种："+tLCPolSchema.getRiskCode()+"由于此次没有催收信息，不需要产生相应打印记录。");
				continue;
			}
			LOPRTManagerSubSchema tLOPRTManagerSubSchema = new LOPRTManagerSubSchema();
			tLOPRTManagerSubSchema.setPrtSeq(mPrtSeqNo);
			tLOPRTManagerSubSchema.setGetNoticeNo(mGetNoticeNo);
			
            //如果是续保险种，此处otherno应置投保单号
			if(tLCPolSchema.getRnewFlag()==-1)
			{
				ExeSQL aExeSQL = new ExeSQL();
				//先判定是否有相应的当期续保轨迹，有可能由于投保人年龄限制不符合条件导致不续保
				int  xb_need_flag=0;
				String xb_check =" select count(a.polno) from lcpol a,lcrnewstatehistory b where a.contno='?contno?' and a.riskcode='?riskcode?' and a.appflag='1' and a.contno = b.contno "
				   +" and a.enddate = b.paytodate and a.riskcode = b.riskcode";
				SQLwithBindVariables sqlbv32=new SQLwithBindVariables();
				sqlbv32.sql(xb_check);
				sqlbv32.put("contno", tLCPolSchema.getContNo());
				sqlbv32.put("riskcode", tLCPolSchema.getRiskCode());
				xb_need_flag = Integer.parseInt(aExeSQL.getOneValue(sqlbv32));
				if(xb_need_flag==0)
				{
					logger.debug("保单号："+tLCPolSchema.getContNo()+"下险种："+tLCPolSchema.getRiskCode()+"由于投保人年龄限制不续保，不产生相应打印记录。");
					continue;
				}
				
				String tProposalNo="";  //投保单号
				
				String str_proposalno="select polno from lcpol where contno='?contno?' and riskcode='?riskcode?' and appflag='9' ";
				SQLwithBindVariables sqlbv33=new SQLwithBindVariables();
				sqlbv33.sql(str_proposalno);
				sqlbv33.put("contno", tLCPolSchema.getContNo());
				sqlbv33.put("riskcode", tLCPolSchema.getRiskCode());
				if(aExeSQL.execSQL(sqlbv33).MaxRow>0)
				{
					tProposalNo = aExeSQL.getOneValue(sqlbv33);			
				    tLOPRTManagerSubSchema.setOtherNo(tProposalNo);
				}
			}
			else
			{
				tLOPRTManagerSubSchema.setOtherNo(tLCPolSchema.getPolNo());
			}
			
			tLOPRTManagerSubSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL);  //保单号
			tLOPRTManagerSubSchema.setRiskCode(tLCPolSchema.getRiskCode());
			tLOPRTManagerSubSchema.setDuePayMoney(RiskPrem);
			tLOPRTManagerSubSchema.setAppntName(mLCContSchema.getAppntName());
			tLOPRTManagerSubSchema.setTypeFlag("1");
			tLOPRTManagerSubSet.add(tLOPRTManagerSubSchema);
		}
		return tLOPRTManagerSubSet;
	}

	public static void main(String[] args) {

		/*TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("Contno", "130210000006925"); // autorenew
		LCContSchema tLCContSchema = new LCContSchema();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo("130210000006925");
		tLCContDB.getInfo();
		tLCContSchema = tLCContDB.getSchema();
		PubFun PubFun = new PubFun();

		// modify by jiaqiangli 2008-11-03 统一修改调用为FinFeePubFun 不再调用PubFun
		// see also in PubFun.calOFDate注释
		String tCurPayToDate = FinFeePubFun.calOFDate("2006-2-28", 1, "M",
				"2010-1-29");
		logger.debug("===================" + tCurPayToDate);

		RnDealBL IndiDueFeePartBLF1 = new RnDealBL();
		GlobalInput tGI = new GlobalInput();
		tGI.ManageCom = "86";
		tGI.Operator = "001";
		VData tv = new VData();
		tv.add(tLCContSchema);
		tv.add(tTransferData);
		tv.add(tGI);

		logger.debug("准备好了数据");
		// tv.add(tTransferData);
		if (IndiDueFeePartBLF1.submitData(tv, "")) {
			logger.debug("个单批处理催收完成");
		} else {
			logger.debug("个单批处理催收信息提示："
					+ IndiDueFeePartBLF1.mErrors.getFirstError());
		}*/
		RnDealBL tRnDealBL = new RnDealBL();
		FDate tD = new FDate();
		LCPolSchema tLCPolSchema=new LCPolSchema();
		LCPolDB tLCPolDB=new LCPolDB();
		tLCPolDB.setPolNo("210110000009232");
		tLCPolSchema = tLCPolDB.query().get(1);
		Date tPayDate = tRnDealBL.calGraceDate(tLCPolSchema);
		String sPayDate = tD.getString(tPayDate);
		logger.debug("交费截止日期==" + sPayDate);
	}

    /**
     * 续保日志表
     * @param tLCPolSchema
     * @return
     */
    private LCRnewStateHistorySchema preLCRnewStateHistory(LCPolSchema nLCPolSchema)
    {
        LCRnewStateHistorySchema tLCRnewStateHistorySchema = new LCRnewStateHistorySchema();

        tLCRnewStateHistorySchema.setProposalNo(nLCPolSchema.getPolNo());
        tLCRnewStateHistorySchema.setContNo(nLCPolSchema.getContNo());
        tLCRnewStateHistorySchema.setRiskCode(nLCPolSchema.getRiskCode());
        tLCRnewStateHistorySchema.setState("1");
        tLCRnewStateHistorySchema.setAgentCode(nLCPolSchema.getAgentCode());
        tLCRnewStateHistorySchema.setAgentGroup(nLCPolSchema.getAgentGroup());
        tLCRnewStateHistorySchema.setManageCom(nLCPolSchema.getManageCom());
        tLCRnewStateHistorySchema.setMakeDate(PubFun.getCurrentDate());
        tLCRnewStateHistorySchema.setModifyDate(PubFun.getCurrentDate());
        tLCRnewStateHistorySchema.setPaytoDate(nLCPolSchema.getCValiDate());

        return tLCRnewStateHistorySchema;
    }
	private void jbInit() throws Exception {
	}
    /**
     * 分配红利程序
     * @return
     */
    public boolean runAssignBonus(String cFiscalYear,LCPolSchema cLCPolSchema)
    {
        int lastYear=Integer.valueOf(StrTool.getYear()).intValue()-1;//上一年度
        if (cFiscalYear != null && !cFiscalYear.equals(""))
        {
            lastYear = Integer.parseInt(cFiscalYear);
        }
		if(getByRePay(cLCPolSchema,lastYear)==false)
        {
			this.mErrors.addOneError("保单号:" + mContNo + "处理红利抵交续期保费出错。");
			return false;
        }
    	return true;
    }

    /**
     * 红利用于抵交续期保费的处理
     * @param tLCPolSchema
     * @param tBonusMoney
     * @param tSNo
     * @param tBonusYear
     * @return
     */
    private boolean getByRePay(LCPolSchema tLCPolSchema,int tBonusYear )
    {
    	try
        {		
    		InsuAccBala tInsuAccBala = new InsuAccBala();
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("InsuAccNo", "000007");
			tTransferData.setNameAndValue("PolNo", tLCPolSchema.getPolNo());
			tTransferData.setNameAndValue("BalaDate", PubFun.getCurrentDate());
			VData tVData = new VData();
			tVData.add(tGI);
			tVData.add(tTransferData);
			// 非万能险的账户型结算
			if (!tInsuAccBala.submitData(tVData, "NonUniversal")) {
				CError.buildErr("", "生存金结算失败！");
				return false;
			}
			VData tResult = new VData();
			tResult = tInsuAccBala.getResult();
			LCInsureAccTraceSet tLCInsureAccTraceSet =(LCInsureAccTraceSet)tResult.getObjectByObjectName("LCInsureAccTraceSet", 0);
			LCInsureAccClassSet tLCInsureAccClassSet = (LCInsureAccClassSet)tResult.getObjectByObjectName("LCInsureAccClassSet", 0);
			LCInsureAccSet tLCInsureAccSet =(LCInsureAccSet)tResult.getObjectByObjectName("LCInsureAccSet", 0);
				
            //获取财务类型
	        String trace_finType = BqCalBL.getFinType_HL_SC("HL",
	        		tLCInsureAccTraceSet.get(1).getInsuAccNo(), "LQ");
			if (trace_finType.equals("")) 
			{
				CError.buildErr(this, "红利领取时的财务类型获取失败");
				return false;
			}
			
			LCInsureAccSchema xLCInsureAccSchema = tLCInsureAccSet.get(1);
			//定义红利金额
			double tBonusMoney =0.0;
			tBonusMoney = xLCInsureAccSchema.getInsuAccGetMoney();
			
			LCInsureAccTraceSchema xLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(1);
			xLCInsureAccTraceSchema.setMoney(-tBonusMoney);
			xLCInsureAccTraceSchema.setMoneyType(trace_finType);
			xLCInsureAccTraceSchema.setBusyType("");
			xLCInsureAccTraceSchema.setShouldValueDate("");
			xLCInsureAccTraceSchema.setValueDate("");
			//帐户表余额和可领金额清0
			xLCInsureAccSchema.setInsuAccBala(0);
			xLCInsureAccSchema.setInsuAccGetMoney(0);
			//余额和可领金额清0
			LCInsureAccClassSchema xLCInsureAccClassSchema = tLCInsureAccClassSet.get(1);
			xLCInsureAccClassSchema.setInsuAccBala(0);
			xLCInsureAccClassSchema.setInsuAccGetMoney(0);
			
            //流水号
    		String tSNo="";
    		tSNo = tLCInsureAccTraceSet.get(1).getSerialNo();
	        String tLimit = PubFun.getNoLimit( tLCPolSchema.getManageCom() );
	        String tActuGetNo = PubFun1.CreateMaxNo( "GETNO", tLimit );//产生实付号码
	        String tGetNoticeNo=PubFun1.CreateMaxNo("GETNOTICENO",tLimit);//产生给付通知书号
	        String tPayNo=PubFun1.CreateMaxNo("PAYNO",tLimit);//产生实收号
	        
	        //获取合同信息
	        LCContSchema tLCContSchema = new LCContSchema();
	        LCContDB tLCContDB = new LCContDB();
	        tLCContDB.setContNo(tLCPolSchema.getContNo());
	        if(!tLCContDB.getInfo())
	        {
	        	CError.buildErr(this, "查找保单信息lccont表失败!");
				return false;
	        }
	        tLCContSchema = tLCContDB.getSchema();
	        String tappntname = "";
	        String tDrawerID="";
	        LCAppntDB tLCAppntDB = new LCAppntDB();
	        tLCAppntDB.setContNo(tLCPolSchema.getContNo());
	        tLCAppntDB.setAppntNo(tLCPolSchema.getAppntNo());
	        if(tLCAppntDB.getInfo()==false)
	        {
	            tDrawerID="";
	        }
	        else
	        {
	           tDrawerID = tLCAppntDB.getIDNo();
	           tappntname = tLCAppntDB.getAppntName();
	        }
	
	        //添加实付数据-总表
	        LJAGetSchema tLJAGetSchema = new LJAGetSchema();
	        tLJAGetSchema.setActuGetNo(tActuGetNo);
	        tLJAGetSchema.setOtherNo(tLCPolSchema.getContNo());
	        tLJAGetSchema.setOtherNoType("7");
	        tLJAGetSchema.setPayMode("5");//内部转账
	        tLJAGetSchema.setAppntNo(tLCPolSchema.getAppntNo());
	        tLJAGetSchema.setCurrency(tLCPolSchema.getCurrency());
	        tLJAGetSchema.setSumGetMoney(tBonusMoney);	        
	        tLJAGetSchema.setSaleChnl(tLCPolSchema.getSaleChnl());
	        tLJAGetSchema.setShouldDate(CurrentDate); //此处由于所取的红利有可能是多起累加的总额，此处字段不再需要对应某一期具体的应分配日期，所以直接取当前日期
	        tLJAGetSchema.setEnterAccDate(CurrentDate);
	        tLJAGetSchema.setConfDate(CurrentDate);
	        tLJAGetSchema.setApproveCode(tLCPolSchema.getApproveCode());
	        tLJAGetSchema.setApproveDate(tLCPolSchema.getApproveDate());
	        tLJAGetSchema.setGetNoticeNo(tGetNoticeNo);
	        tLJAGetSchema.setDrawer(tLCPolSchema.getAppntName());
	        tLJAGetSchema.setDrawerID(tDrawerID);
	        tLJAGetSchema.setSerialNo(tSNo);
	        tLJAGetSchema.setOperator(tLCPolSchema.getOperator());
	        tLJAGetSchema.setMakeDate(CurrentDate);
	        tLJAGetSchema.setMakeTime(CurrentTime);
	        tLJAGetSchema.setModifyDate(CurrentDate);
	        tLJAGetSchema.setModifyTime(CurrentTime);
	        tLJAGetSchema.setManageCom(tLCPolSchema.getManageCom());
	        tLJAGetSchema.setAgentCom(tLCPolSchema.getAgentCom());
	        tLJAGetSchema.setAgentType(tLCPolSchema.getAgentType());
	        tLJAGetSchema.setAgentCode(tLCPolSchema.getAgentCode());
	        tLJAGetSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
	        tLJAGetSchema.setBankCode(tLCContSchema.getBankCode());
	        tLJAGetSchema.setBankAccNo(tLCContSchema.getBankAccNo());
	
	        //添加实付子表-红利给付实付表
	        LJABonusGetSchema tLJABonusGetSchema = new LJABonusGetSchema();
	        tLJABonusGetSchema.setActuGetNo(tActuGetNo);
	        tLJABonusGetSchema.setOtherNo(tLCPolSchema.getPolNo());
	        tLJABonusGetSchema.setOtherNoType("7");
	        tLJABonusGetSchema.setPayMode("5");//内部转账
	        tLJABonusGetSchema.setBonusYear(String.valueOf(tBonusYear));//此处由于所取的红利有可能是多起累加的总额，此处字段不再需要对应某一期具体的应分配日期，所以直接取当前日期
	        tLJABonusGetSchema.setCurrency(tLCPolSchema.getCurrency());
	        tLJABonusGetSchema.setGetMoney(tBonusMoney);
	        tLJABonusGetSchema.setGetDate(CurrentDate);
	        tLJABonusGetSchema.setEnterAccDate(CurrentDate);        //修改到帐日期、核销日期与实付总表一致
	        tLJABonusGetSchema.setConfDate(CurrentDate);
	        tLJABonusGetSchema.setManageCom(tLCPolSchema.getManageCom());
	        tLJABonusGetSchema.setAgentCom(tLCPolSchema.getAgentCom());
	        tLJABonusGetSchema.setAgentType(tLCPolSchema.getAgentType());
	        tLJABonusGetSchema.setAPPntName(tLCPolSchema.getAppntName());
	        tLJABonusGetSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
	        tLJABonusGetSchema.setAgentCode(tLCPolSchema.getAgentCode());
	        tLJABonusGetSchema.setFeeFinaType(trace_finType); //红利退费
	        tLJABonusGetSchema.setFeeOperationType(trace_finType);
	        tLJABonusGetSchema.setSerialNo(tSNo);
	        tLJABonusGetSchema.setOperator(tLCPolSchema.getOperator());
	        tLJABonusGetSchema.setState("0");
	        tLJABonusGetSchema.setRiskCode(tLCPolSchema.getRiskCode());
	        tLJABonusGetSchema.setPolNo(tLCPolSchema.getPolNo());
	        tLJABonusGetSchema.setContNo(tLCPolSchema.getContNo());
	        tLJABonusGetSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
	        tLJABonusGetSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
	        tLJABonusGetSchema.setMakeDate(CurrentDate);
	        tLJABonusGetSchema.setMakeTime(CurrentTime);
	        tLJABonusGetSchema.setModifyDate(CurrentDate);
	        tLJABonusGetSchema.setModifyTime(CurrentTime);
	        tLJABonusGetSchema.setGetNoticeNo(tGetNoticeNo);
	
	        //财务实付总表
	        LJFIGetSchema tLJFIGetSchema = new LJFIGetSchema();
	        tLJFIGetSchema.setActuGetNo(tLJAGetSchema.getActuGetNo());
	        tLJFIGetSchema.setPayMode(tLJAGetSchema.getPayMode());
	        tLJFIGetSchema.setOtherNo(tLJAGetSchema.getOtherNo());
	        tLJFIGetSchema.setOtherNoType(tLJAGetSchema.getOtherNoType());
	        tLJFIGetSchema.setCurrency(tLJAGetSchema.getCurrency());
	        tLJFIGetSchema.setGetMoney(tLJAGetSchema.getSumGetMoney());
	        tLJFIGetSchema.setShouldDate(tLJAGetSchema.getShouldDate());
	        tLJFIGetSchema.setEnterAccDate(tLJAGetSchema.getEnterAccDate());
	        tLJFIGetSchema.setConfDate(tLJAGetSchema.getConfDate());
	        tLJFIGetSchema.setSaleChnl(tLJAGetSchema.getSaleChnl());
	        tLJFIGetSchema.setManageCom(tLJAGetSchema.getManageCom());
	        tLJFIGetSchema.setAPPntName(tappntname);
	        tLJFIGetSchema.setAgentCom(tLJAGetSchema.getAgentCom());
	        tLJFIGetSchema.setAgentType(tLJAGetSchema.getAgentType());
	        tLJFIGetSchema.setAgentGroup(tLJAGetSchema.getAgentGroup());
	        tLJFIGetSchema.setAgentCode(tLJAGetSchema.getAgentCode());
	        tLJFIGetSchema.setSerialNo(tLJAGetSchema.getSerialNo());
	        tLJFIGetSchema.setDrawer(tLJAGetSchema.getDrawer());
	        tLJFIGetSchema.setDrawerID(tLJAGetSchema.getDrawerID());
	        tLJFIGetSchema.setOperator(tLJAGetSchema.getOperator());
	        tLJFIGetSchema.setMakeTime(tLJAGetSchema.getMakeTime());
	        tLJFIGetSchema.setMakeDate(tLJAGetSchema.getMakeDate());
	        tLJFIGetSchema.setState("0");
	        tLJFIGetSchema.setModifyDate(tLJAGetSchema.getModifyDate());
	        tLJFIGetSchema.setModifyTime(tLJAGetSchema.getModifyTime());
	
	        //添加暂交费纪录
	        LJTempFeeSchema tLJTempFeeSchema=new LJTempFeeSchema();
	        tLJTempFeeSchema.setTempFeeNo(tGetNoticeNo);
	        tLJTempFeeSchema.setTempFeeType("7");
	        tLJTempFeeSchema.setRiskCode(tLCPolSchema.getRiskCode());
	        tLJTempFeeSchema.setPayIntv(tLCPolSchema.getPayIntv());
	        tLJTempFeeSchema.setOtherNo(tLCPolSchema.getContNo());
	        tLJTempFeeSchema.setOtherNoType("0");
	        tLJTempFeeSchema.setCurrency(tLCPolSchema.getCurrency());
	        tLJTempFeeSchema.setPayMoney(tBonusMoney);
	        tLJTempFeeSchema.setPayDate(CurrentDate);
	        tLJTempFeeSchema.setEnterAccDate(CurrentDate);
	        tLJTempFeeSchema.setConfDate(CurrentDate);
	        tLJTempFeeSchema.setSaleChnl(tLCPolSchema.getSaleChnl());
	        tLJTempFeeSchema.setManageCom(tLCPolSchema.getManageCom());
	        tLJTempFeeSchema.setAgentCom(tLCPolSchema.getAgentCom());
	        tLJTempFeeSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
	        tLJTempFeeSchema.setAgentType(tLCPolSchema.getAgentType());
	        tLJTempFeeSchema.setAPPntName(tLCPolSchema.getAppntName());
	        tLJTempFeeSchema.setAgentCode(tLCPolSchema.getAgentCode());
	        tLJTempFeeSchema.setConfFlag("1");
	        tLJTempFeeSchema.setSerialNo(tSNo);
	        tLJTempFeeSchema.setOperator(tLCPolSchema.getOperator());
	        tLJTempFeeSchema.setMakeDate(CurrentDate);
	        tLJTempFeeSchema.setMakeTime(CurrentTime);
	        tLJTempFeeSchema.setModifyDate(CurrentDate);
	        tLJTempFeeSchema.setModifyTime(CurrentTime);
	        tLJTempFeeSchema.setConfMakeDate(CurrentDate);
	        //添加暂交费子表
	        LJTempFeeClassSchema tLJTempFeeClassSchema=new LJTempFeeClassSchema();
	        tLJTempFeeClassSchema.setTempFeeNo(tGetNoticeNo);
	        tLJTempFeeClassSchema.setChequeNo(tActuGetNo);  //此处参照垫交程序
	        tLJTempFeeClassSchema.setPayMode("5");//内部转账
	        tLJTempFeeClassSchema.setCurrency(tLCPolSchema.getCurrency());
	        tLJTempFeeClassSchema.setPayMoney(tBonusMoney);
	        tLJTempFeeClassSchema.setAppntName(tLCPolSchema.getAppntName());
	        tLJTempFeeClassSchema.setPayDate(CurrentDate);
	        tLJTempFeeClassSchema.setApproveDate(tLCPolSchema.getApproveDate());
	        tLJTempFeeClassSchema.setConfDate(CurrentDate);
	        tLJTempFeeClassSchema.setEnterAccDate(CurrentDate);
	        tLJTempFeeClassSchema.setConfFlag("1");
	        tLJTempFeeClassSchema.setOtherNo(tLCPolSchema.getContNo());
	        tLJTempFeeClassSchema.setSerialNo(tSNo);
	        tLJTempFeeClassSchema.setOperator(tLCPolSchema.getOperator());
	        tLJTempFeeClassSchema.setMakeDate(CurrentDate);
	        tLJTempFeeClassSchema.setMakeTime(CurrentTime);
	        tLJTempFeeClassSchema.setModifyDate(CurrentDate);
	        tLJTempFeeClassSchema.setModifyTime(CurrentTime);
	        tLJTempFeeClassSchema.setManageCom(tLCPolSchema.getManageCom());
	        tLJTempFeeClassSchema.setConfMakeDate(CurrentDate);
	        //tongmeng 2012-02-07 modify
	        //不生成实收记录
	        //添加实收总表纪录,直接将暂收核销到客户账户中.待续期核销时再使用
	        /*
	        LJAPaySchema tLJAPaySchema = new LJAPaySchema();
	        tLJAPaySchema.setPayNo(tPayNo);
	        tLJAPaySchema.setIncomeNo(tLCPolSchema.getContNo());
	        tLJAPaySchema.setIncomeType("2");
	        tLJAPaySchema.setAppntNo(tLCPolSchema.getAppntNo());
	        tLJAPaySchema.setCurrency(tLCPolSchema.getCurrency());
	        tLJAPaySchema.setSumActuPayMoney(tBonusMoney);
	        tLJAPaySchema.setPayDate(CurrentDate);
	        tLJAPaySchema.setEnterAccDate(CurrentDate);
	        tLJAPaySchema.setConfDate(CurrentDate);
	        tLJAPaySchema.setApproveCode(tLCPolSchema.getApproveCode());
	        tLJAPaySchema.setApproveDate(tLCPolSchema.getApproveDate());
	        tLJAPaySchema.setSerialNo(tSNo);
	        tLJAPaySchema.setOperator(tLCPolSchema.getOperator());
	        tLJAPaySchema.setMakeDate(CurrentDate);
	        tLJAPaySchema.setMakeTime(CurrentTime);
	        tLJAPaySchema.setModifyDate(CurrentDate);
	        tLJAPaySchema.setModifyTime(CurrentTime);
	        tLJAPaySchema.setGetNoticeNo(tGetNoticeNo);
	        tLJAPaySchema.setManageCom(tLCPolSchema.getManageCom());
	        tLJAPaySchema.setAgentCode(tLCPolSchema.getAgentCode());
	        tLJAPaySchema.setAgentType(tLCPolSchema.getAgentType());
	        tLJAPaySchema.setAgentGroup(tLCPolSchema.getAgentGroup());
	        tLJAPaySchema.setBankCode(tLCContSchema.getBankCode());      //银行编码
	        tLJAPaySchema.setBankAccNo(tLCContSchema.getBankAccNo());   //银行帐号
	        tLJAPaySchema.setRiskCode(tLCPolSchema.getRiskCode());   // 险种编码
	
	        //添加实收个人子表纪录
	        LJAPayPersonSchema tLJAPayPersonSchema= new LJAPayPersonSchema();
	        tLJAPayPersonSchema.setPolNo(tLCPolSchema.getPolNo());
	        tLJAPayPersonSchema.setPayCount(2); //-记为续期
	        tLJAPayPersonSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
	        tLJAPayPersonSchema.setContNo(tLCPolSchema.getContNo());
	        tLJAPayPersonSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
	        tLJAPayPersonSchema.setAppntNo(tLCPolSchema.getAppntNo());
	        tLJAPayPersonSchema.setPayNo(tPayNo);

	        tLJAPayPersonSchema.setPayAimClass("1");//交费目的分类
	        tLJAPayPersonSchema.setDutyCode("0000000000");      //责任编码
	        tLJAPayPersonSchema.setPayPlanCode("00000000");//交费计划编码
	        tLJAPayPersonSchema.setCurrency(tLCPolSchema.getCurrency());
	        tLJAPayPersonSchema.setSumActuPayMoney(tBonusMoney);
	        tLJAPayPersonSchema.setSumDuePayMoney(tBonusMoney);
	        tLJAPayPersonSchema.setPayIntv(tLCPolSchema.getPayIntv());
	        tLJAPayPersonSchema.setPayDate(CurrentDate);
	        tLJAPayPersonSchema.setPayType("YET");
	        tLJAPayPersonSchema.setEnterAccDate(CurrentDate);
	        tLJAPayPersonSchema.setConfDate(CurrentDate);
	        tLJAPayPersonSchema.setSerialNo(tSNo);
	        tLJAPayPersonSchema.setOperator(tLCPolSchema.getOperator());
	        tLJAPayPersonSchema.setMakeDate(CurrentDate);
	        tLJAPayPersonSchema.setMakeTime(CurrentTime);
	        tLJAPayPersonSchema.setModifyDate(CurrentDate);
	        tLJAPayPersonSchema.setModifyTime(CurrentTime);
	        tLJAPayPersonSchema.setGetNoticeNo(tGetNoticeNo);
	        tLJAPayPersonSchema.setInInsuAccState("0");//转入保险帐户状态
	        tLJAPayPersonSchema.setManageCom(tLCPolSchema.getManageCom());
	        tLJAPayPersonSchema.setAgentCode(tLCPolSchema.getAgentCode());
	        tLJAPayPersonSchema.setAgentCom(tLCPolSchema.getAgentCom());
	        tLJAPayPersonSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
	        tLJAPayPersonSchema.setRiskCode(tLCPolSchema.getRiskCode());
*/
            /**个人保单表更新**/
            //更新个人保单表
            String strSQL2="update lcpol set LeavingMoney=?LeavingMoney?";
            strSQL2=strSQL2+",ModifyDate='?CurrentDate?',ModifyTime='?CurrentTime?'  where polno='?polno?' ";
            SQLwithBindVariables sqlbv34=new SQLwithBindVariables();
            sqlbv34.sql(strSQL2);
            sqlbv34.put("LeavingMoney", tLCPolSchema.getLeavingMoney()+tBonusMoney);
            sqlbv34.put("CurrentDate", CurrentDate);
            sqlbv34.put("CurrentTime", CurrentTime);
            sqlbv34.put("polno", tLCPolSchema.getPolNo());
            logger.debug("个人保单表更新 : " + strSQL2);

            MMap map_HL = new MMap();
            map_HL.put(xLCInsureAccTraceSchema, "INSERT");
            map_HL.put(xLCInsureAccSchema, "UPDATE");
            map_HL.put(xLCInsureAccClassSchema, "UPDATE");
            map_HL.put(tLJAGetSchema, "INSERT");
            map_HL.put(tLJABonusGetSchema, "INSERT");
            map_HL.put(tLJFIGetSchema, "INSERT");
            map_HL.put(tLJTempFeeSchema, "INSERT");
            map_HL.put(tLJTempFeeClassSchema, "INSERT");
//            map_HL.put(tLJAPaySchema, "INSERT");
 //           map_HL.put(tLJAPayPersonSchema, "INSERT");
            map_HL.put(sqlbv34, "UPDATE");
            
    	
            //2012-02-07 modify
            //红利抵交续期保费处理时,需要更新客户账户
            /***********************************/
    		// 添加客户账户处理
            LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
            LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
            tLJTempFeeSet.add(tLJTempFeeSchema);
            tLJTempFeeClassSet.add(tLJTempFeeClassSchema);
            
    		VData nInputData = new VData();		
    		nInputData.add(tLJTempFeeSet);
    		nInputData.add(tLJTempFeeClassSet);
    		//nInputData.add(tLJAGetSchema);
    		nInputData.add(tGI);
    		FICustomerMain tFICustomerMain = new FICustomerMain();
    		// 调用客户账户收费接口，传入财务标志FI
    		if (tFICustomerMain.submitData(nInputData, "FI"))
    		{
    			// 获取接口计算结果，传入MMap，方便打包直接用PubSubmit提交
    			map_HL.add(tFICustomerMain.getMMap());
    		}
    		else
    		{
    			mErrors.copyAllErrors(tFICustomerMain.mErrors);
    			return false;
    		}
            
            
            //提交数据
            VData HL_Result = new VData();
            HL_Result.add(map_HL);
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(HL_Result, "")) {
				logger.debug("保单"+tLCPolSchema.getContNo()+"处理红利挂靠保单余额时数据提交失败！");
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				//ErrCount++;
				mResult.clear();
				return false;
			}
			HL_Result.clear();
        }
        catch (Exception ex)
        {
            CError tError =new CError();
            tError.moduleName="AssignBonus";
            tError.functionName="getByRePay";
            tError.errorMessage=ex.toString();
            this.mErrors .addOneError(tError);
            return false;
        }

        return true;
    }
    
    /**
     * 续期宽限期计算函数
     * @param tLCPolSchema
     * @return
     */
    private Date calGraceDate(LCPolSchema tLCPolSchema)
    {
		//tongmeng 2011-01-17 modify
		//续期宽限期折算功能 取主险的
    	ExeSQL tExeSQL = new ExeSQL();
		String tSQL_lmriskpay = "select trim(cast(graceperiod as char(20))),graceperiodunit,gracecalcode,(select max(payintv) from lcpol where polno='?polno?') payintv from lmriskpay where riskcode=(select max(riskcode) from lcpol where polno='?polno?')";
		SQLwithBindVariables sqlbv35=new SQLwithBindVariables();
		sqlbv35.sql(tSQL_lmriskpay);
		sqlbv35.put("polno", tLCPolSchema.getMainPolNo());
		SSRS tSSRS_riskpay = new SSRS();
		tSSRS_riskpay = tExeSQL.execSQL(sqlbv35);
		String tGracePeriod = "60";
		String tGracePreiodUnit = "D";
		String tGraceCalCode = "";
		if(tSSRS_riskpay.getMaxRow()>0)
		{
			tGracePeriod = tSSRS_riskpay.GetText(1, 1);
			tGracePreiodUnit = tSSRS_riskpay.GetText(1, 2);
			tGraceCalCode = tSSRS_riskpay.GetText(1, 3);			
			if((tGracePeriod==null||tGracePeriod.equals(""))&&!("").equals(tGraceCalCode))
			{
				//如果没有描述,取算法计算结果
				BOMCont cont = new BOMCont();
				cont.setPayIntv(tSSRS_riskpay.GetText(1, 4));
			
				List list = new ArrayList();
				list.add(cont);
				
				RuleUI rule = new RuleUI();
							
				Calculator tCalculator = new Calculator();
				tCalculator.setBOMList(list);
				tCalculator.setCalCode(tGraceCalCode);
				String tCalResult = tCalculator.calculate();
				
				//获取间隔和标记
				tGracePeriod = tCalResult.replaceAll("[A-Z,a-z]","");
				tGracePreiodUnit = tCalResult.replaceAll("[0-9]","");
				logger.debug("$$$$:"+tCalculator.calculate());
			}
		}
		
		logger.debug("tGracePeriod:tGracePreiodUnit:"+tGracePeriod+":"+tGracePreiodUnit);
		
		Date tPayDate = FinFeePubFun.calOFDate(mLastPaytoDate, Integer.parseInt(tGracePeriod), tGracePreiodUnit, null);
		return tPayDate;
    }
}

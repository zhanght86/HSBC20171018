package com.sinosoft.lis.get;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.BqCalBL;
import com.sinosoft.lis.bq.BqCode;
import com.sinosoft.lis.bq.EdorCalZT;
import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.InsuAccBala;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJAGetDrawSchema;
import com.sinosoft.lis.schema.LJAGetEndorseSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LOLoanSchema;
import com.sinosoft.lis.schema.LOReturnLoanSchema;
import com.sinosoft.lis.schema.LPBnfSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.schema.LPInsureAccSchema;
import com.sinosoft.lis.schema.LPLoanSchema;
import com.sinosoft.lis.schema.LPReturnLoanSchema;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJAGetDrawSet;
import com.sinosoft.lis.vschema.LJAGetEndorseSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 生存金分配银行转账批处理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author pst
 * @version 1.0
 */
public class AliveAccAssignBL {
private static Logger logger = Logger.getLogger(AliveAccAssignBL.class);

	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/***/
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 合同号 */
	private LCContSchema mLCContSchema = new LCContSchema();

	private LCPolSchema mLCPolSchema = new LCPolSchema();

	private LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();

	private LJAGetSet tLJAGetSet = new LJAGetSet();
	
	private LPBnfSet aLPBnfSet  = new LPBnfSet();
	
	private LJAGetDrawSet tLJAGetDrawSet = new LJAGetDrawSet();
	
	private LJAGetEndorseSet mLJAGetEndorseSet = new LJAGetEndorseSet();

	/** 结算日期 */
	private String mBalaDate;

	private Reflections tReFlection = new Reflections();

	/** 封装要操作的数据，以便一次提交 */
	private MMap mMap = new MMap();

	private String tCurMakeDate = PubFun.getCurrentDate();

	private String tCurMakeTime = PubFun.getCurrentTime();

	private ExeSQL tExeSQL = new ExeSQL();

	/**
	 * 数据提交的公共方法
	 * 
	 * @param cInputData
	 * @return
	 */
	public boolean submitData(VData cInputData, String tOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = tOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		// 数据校验，包括业务校验
		if (!checkData()) {
			return false;
		}

		// 根据业务逻辑对数据进行处理
		if (!dealData()) {
			return false;
		}
      
		if(!prepareData())
		{
			return false;			
		}
		
		PubSubmit tPubSubmit = new PubSubmit();

		if(!tPubSubmit.submitData(this.mResult))
		{
			CError.buildErr(this, "数据库提交失败！"+tPubSubmit.mErrors.getFirstError());
			return false;
		}
			

		return true;
	}
	
	public static void  main(String[] args)
	{
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo("86110020080219001978");
		tLCContDB.getInfo();
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom="86";
		tGlobalInput.Operator="001";
		String date=PubFun.getCurrentDate();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("BalaDate", date);

		VData tVData = new VData ();
		tVData.add(tGlobalInput);
		tVData.add(tLCContDB.getSchema());
		tVData.add(tTransferData);
		AliveAccAssignBL tAliveAccAssignBL = new AliveAccAssignBL();
		
		tAliveAccAssignBL.submitData(tVData, "a");
		
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {

		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		mLCContSchema = (LCContSchema) mInputData.getObjectByObjectName(
				"LCContSchema", 0);
		TransferData mTransferData = (TransferData) mInputData
				.getObjectByObjectName("TransferData", 0);
		mBalaDate = (String) mTransferData.getValueByName("BalaDate");
		if (mBalaDate == null || mBalaDate.equals("")) {
			buildError("getInputData", "获得保单号失败!");
			return false;
		}
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolset = new LCPolSet();
		String tSQL = "select * from lcpol  where contno='?contno?' and getform='0'"
				+ " and polno=mainpolno";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSQL);
		sqlbv.put("", mLCContSchema.getContNo());
		tLCPolset = tLCPolDB.executeQuery(sqlbv);
		if (tLCPolset == null || tLCPolset.size() < 1) {
			buildError("getInputData", "获得主险数据失败!");
			return false;
		} else {
			mLCPolSchema = tLCPolset.get(1);
		}
		return true;
	}

	/**
	 * 校验传入的数据的合法性目前只有主险 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		if (!"0".equals(mLCPolSchema.getGetForm())) {
			buildError("checkData", "此保单尚未进行银行转账授权!");
			return false;
		}
		
		//add by jiaqiangli 2009-04-08 垫缴状态下不允许进行银行自动转账派发
		String tDJStateSQL = "select '1' from lccontstate where contno='?contno?' and statetype='PayPrem' and state='1' and startdate<='?mBalaDate?' and enddate is null ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tDJStateSQL);
		sqlbv1.put("contno", mLCPolSchema.getContNo());
		sqlbv1.put("mBalaDate", mBalaDate);
		ExeSQL tExeSQL = new ExeSQL();
		String tDJState = tExeSQL.getOneValue(sqlbv1);
		if ("1".equals(tDJState)) {
			logger.debug("AliveAccAssignBL-"+mLCPolSchema.getContNo()+"垫缴状态下生存金不允许进行银行自动转账派发");
			buildError("checkData", "垫缴状态下生存金不允许进行银行自动转账派发!");
			return false;
		}
		//add by jiaqiangli 2009-04-08 垫缴状态下不允许进行银行自动转账派发

		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @return
	 */
	private boolean dealData() {

		
		LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();

		String tInsureaccSQL = "select * from lcinsureacc where contno='?contno?'"
				+ " and acctype in ('009','005') and baladate<='?mBalaDate?'";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tInsureaccSQL);
		sqlbv2.put("contno", mLCContSchema.getContNo());
		sqlbv2.put("mBalaDate", mBalaDate);
		logger.debug("查询帐户信息"+tInsureaccSQL);
		LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB
				.executeQuery(sqlbv2);
		
		if (tLCInsureAccDB.mErrors.needDealError()) {
			CError.buildErr(this, "帐户信息查询失败");
			return false;
		}
		if (tLCInsureAccSet == null || tLCInsureAccSet.size() < 1) {
			CError.buildErr(this, "没有帐户信息");
			return false;
		}
		
		LCBnfDB tLCBnfDB = new LCBnfDB();

		//获取优先级别最高的生存受益人
		String tBnfSQL = "select * from lcBnf a where contno='?contno?'" + " and bnftype ='0' "
				+" and a.BnfGrade  in (select min(b.BnfGrade) from lcBnf b where b.contno=a.contno and b.bnftype='0' ) ";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(tBnfSQL);
		sqlbv3.put("", mLCContSchema.getContNo());
		
		logger.debug("查询生存受益人"+tBnfSQL);
		LCBnfSet tLCBnfSet = tLCBnfDB.executeQuery(sqlbv3);
		
		if (tLCBnfDB.mErrors.needDealError()) {
			CError.buildErr(this, "帐户信息查询失败");
			return false;
		}
		if (tLCBnfSet == null || tLCBnfSet.size() < 1) {
			CError.buildErr(this, "没有帐户信息");
			return false;
		}
		
		String tLimit = PubFun.getNoLimit(mLCPolSchema.getManageCom());
		String tGetNoticeNo = PubFun1.CreateMaxNo("GETNOTICENO", tLimit);// 产生即付通知书号
		String tSerNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);// 产生流水号码
		tSerNo="LG"+tSerNo.substring(2);
		for (int k = 1; k <= tLCBnfSet.size(); k++) {
			LPBnfSchema tLPBnfSchema = new LPBnfSchema();
			LCBnfSchema tLCBnfSchema = new LCBnfSchema();
			tLCBnfSchema=tLCBnfSet.get(k);
			PubFun.copySchema(tLPBnfSchema, tLCBnfSchema);
			tLPBnfSchema.setEdorNo(tSerNo);
			tLPBnfSchema.setEdorType("LG");	
			tLPBnfSchema.setModifyDate(tCurMakeDate);
			tLPBnfSchema.setModifyTime(tCurMakeTime);
			tLPBnfSchema.setOperator(mGlobalInput.Operator);
			aLPBnfSet.add(tLPBnfSchema);
		}
		// 合同帐户累计领取
		double tSumAccBala = 0;
		for (int k = 1; k <= tLCInsureAccSet.size(); k++) {
			LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
			tLCInsureAccSchema = tLCInsureAccSet.get(k);
			InsuAccBala tInsuAccBala = new InsuAccBala();
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("InsuAccNo", tLCInsureAccSchema
					.getInsuAccNo());
			tTransferData.setNameAndValue("PolNo", tLCInsureAccSchema
					.getPolNo());
			tTransferData.setNameAndValue("BalaDate", mBalaDate);
			VData tVData = new VData();
			tVData.add(mGlobalInput);
			tVData.add(tTransferData);
			// 非万能险的账户型结算
			if (!tInsuAccBala.submitData(tVData, "NonUniversal")) {
				CError.buildErr(this, "结算失败！");
				return false;
			}
			VData tResult = new VData();
			tResult = tInsuAccBala.getResult();
			
			LCInsureAccTraceSet tLCInsureAccTraceSet =(LCInsureAccTraceSet)tResult.getObjectByObjectName("LCInsureAccTraceSet", 0);
			for (int m=1;m<=tLCInsureAccTraceSet.size();m++)
			{
				LCInsureAccTraceSchema tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(1);
				
				
				String tLimit1 = PubFun.getNoLimit(tLCInsureAccTraceSchema
						.getManageCom());
				String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit1);
				tLCInsureAccTraceSchema.setSerialNo(serNo);
				String trace_finType = BqCalBL.getFinType_HL_SC("SC", tLCInsureAccTraceSchema.getInsuAccNo(), "LX");
				if(trace_finType.equals(""))
				{
					CError.buildErr(this, "生存金领取时的财务类型获取失败");
					return false;
				}
				tLCInsureAccTraceSchema.setMoneyType(trace_finType);
				tLCInsureAccTraceSchema.setPayDate(mBalaDate );
				tLCInsureAccTraceSchema.setFeeCode("000000");

				tLCInsureAccTraceSchema.setMakeDate(tCurMakeDate);
				tLCInsureAccTraceSchema.setMakeTime("00:00:00");
				tLCInsureAccTraceSchema.setModifyDate(tCurMakeDate);
				tLCInsureAccTraceSchema.setModifyTime(tCurMakeTime);
				tLCInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
				this.mLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
			}
			
			
			LCInsureAccSet rLCInsureAccSet = (LCInsureAccSet) tResult
					.getObjectByObjectName("LCInsureAccSet", 0);
			
			
			// 累加帐户
			tSumAccBala += rLCInsureAccSet.get(1).getInsuAccBala();

			String tSerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);// 产生流水号码

			// 填充保险帐户表记价履历表
			LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
			tReFlection.transFields(tLCInsureAccTraceSchema, mLCPolSchema);
			tLCInsureAccTraceSchema.setSerialNo(tSerialNo);
			tLCInsureAccTraceSchema.setPolNo(mLCPolSchema.getPolNo());
			tLCInsureAccTraceSchema.setInsuAccNo(rLCInsureAccSet.get(1).getInsuAccNo());
			
//			tLCInsureAccTraceSchema.setMoneyType("LGLQ");
			String trace_finType = BqCalBL.getFinType_HL_SC("SC", tLCInsureAccTraceSchema.getInsuAccNo(), "LQ");
			if(trace_finType.equals(""))
			{
				CError.buildErr(this, "生存领取的财务类型获取失败！");
				return false;
			}
			tLCInsureAccTraceSchema.setMoneyType(trace_finType);
			tLCInsureAccTraceSchema.setRiskCode(tLCInsureAccSchema
					.getRiskCode());
			tLCInsureAccTraceSchema.setOtherNo(tLCInsureAccSchema.getPolNo());
			tLCInsureAccTraceSchema.setOtherType("1");
			tLCInsureAccTraceSchema.setPayPlanCode("000000");
			tLCInsureAccTraceSchema.setAccAscription("1");
			tLCInsureAccTraceSchema.setMoney(-rLCInsureAccSet.get(1)
					.getInsuAccBala());
			tLCInsureAccTraceSchema.setContNo(tLCInsureAccSchema.getContNo());
			tLCInsureAccTraceSchema.setGrpPolNo(tLCInsureAccSchema
					.getGrpPolNo());
			tLCInsureAccTraceSchema.setGrpContNo(tLCInsureAccSchema
					.getGrpContNo());
			tLCInsureAccTraceSchema.setInsuAccNo(tLCInsureAccSchema
					.getInsuAccNo());
			tLCInsureAccTraceSchema.setState(tLCInsureAccSchema.getState());
			tLCInsureAccTraceSchema.setOperator(tLCInsureAccSchema
					.getOperator());
			tLCInsureAccTraceSchema.setMakeDate(tCurMakeDate);
			tLCInsureAccTraceSchema.setMakeTime("00:00:00");
			tLCInsureAccTraceSchema.setModifyDate(tCurMakeDate);
			tLCInsureAccTraceSchema.setModifyTime(tCurMakeTime);
			tLCInsureAccTraceSchema.setPayDate(mBalaDate);
			tLCInsureAccTraceSchema.setFeeCode("000000");
			mLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
			
			LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
			PubFun.copySchema(tLJSGetEndorseSchema, mLCPolSchema);
			tLJSGetEndorseSchema
					.setSubFeeOperationType(BqCode.Pay_GetDraw);
			String finType = BqCalBL.getFinType("LG", tLCInsureAccSchema
					.getInsuAccNo(), tLCInsureAccSchema.getPolNo());
			if (finType.equals("")) {
				// @@错误处理
				CError.buildErr(this, "在LDCode1中缺少保全财务接口转换类型编码!");
				return false;
			}
			tLJSGetEndorseSchema.setOtherNo(tSerNo);
			tLJSGetEndorseSchema.setOtherNoType("3");
			tLJSGetEndorseSchema.setEndorsementNo(tSerNo);
			tLJSGetEndorseSchema.setFeeOperationType("LG");
			tLJSGetEndorseSchema.setFeeFinaType(finType);
			tLJSGetEndorseSchema.setDutyCode("000000");
			tLJSGetEndorseSchema.setPayPlanCode("000000");
			tLJSGetEndorseSchema.setGetFlag("1");
			tLJSGetEndorseSchema.setGetMoney(rLCInsureAccSet.get(1)
					.getInsuAccBala());
			tLJSGetEndorseSchema.setMakeDate(this.tCurMakeDate);
			tLJSGetEndorseSchema.setMakeTime(this.tCurMakeTime);
			tLJSGetEndorseSchema.setModifyDate(this.tCurMakeDate);
			tLJSGetEndorseSchema.setModifyTime(this.tCurMakeTime);
			tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
			tLJSGetEndorseSet.add(tLJSGetEndorseSchema);
			
			
			rLCInsureAccSet.get(1).setLastAccBala(rLCInsureAccSet.get(1).getInsuAccBala());
			rLCInsureAccSet.get(1).setInsuAccBala(0);//
			rLCInsureAccSet.get(1).setBalaTime("00:00:00");
			this.mMap.put(rLCInsureAccSet, "UPDATE");
			
			LCInsureAccDB tempLCInsureAccDB = new LCInsureAccDB();
			tempLCInsureAccDB.setSchema(rLCInsureAccSet.get(1));
			if(!tempLCInsureAccDB.getInfo())
			{
				return false;
			}
			LPInsureAccSchema tLPInsureAccSchema = new LPInsureAccSchema();
			PubFun.copySchema(tLPInsureAccSchema, tempLCInsureAccDB.getSchema());
			tLPInsureAccSchema.setEdorNo(tSerNo);
			tLPInsureAccSchema.setEdorType("LG");
			this.mMap.put(tLPInsureAccSchema, "INSERT");
			
			LCInsureAccClassSet tLCInsureAccClassSet = (LCInsureAccClassSet) tResult.getObjectByObjectName("LCInsureAccClassSet", 0);
			tLCInsureAccClassSet.get(1).setLastAccBala(tLCInsureAccClassSet.get(1).getInsuAccBala());
			tLCInsureAccClassSet.get(1).setInsuAccBala(0);
			tLCInsureAccClassSet.get(1).setBalaTime("00:00:00");
			this.mMap.put(tLCInsureAccClassSet, "UPDATE");
			
			LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
			tLCInsureAccClassDB.setSchema(tLCInsureAccClassSet.get(1));
			if(!tLCInsureAccClassDB.getInfo())
			{
				return false;
			}
			LPInsureAccClassSchema tLPInsureAccClassSchema = new LPInsureAccClassSchema();
			PubFun.copySchema(tLPInsureAccClassSchema,tLCInsureAccClassDB.getSchema());
			tLPInsureAccClassSchema.setEdorNo(tSerNo);
			tLPInsureAccClassSchema.setEdorType("LG");
			this.mMap.put(tLPInsureAccClassSchema, "INSERT");
		}
		
		/*
		 *  增加扣除保单借款超出部分规则
		 *  1、抵扣前提：产生生存金后的保单现金价值的80%低于现有保单借款金额；
		 *  2、抵扣规则：抵扣金额=借款差额+借款差额产生的利息
			 *     借款差额=现有借款金额-产生生存金后保单现金价值的80%；
		 */
		String sum_loanmoney_sql = "select (case when sum(leavemoney) is not null then sum(leavemoney) else 0 end) from loloan a where contno='?contno?' and loantype='0' and payoffflag='0'";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(sum_loanmoney_sql);
		sqlbv4.put("contno", mLCContSchema.getContNo());
		ExeSQL tExeSQL = new ExeSQL();
		double sum_loanmoney = Double.parseDouble(tExeSQL.getOneValue(sqlbv4));
		//抵扣借款的顺序为：借款金额最大的先抵扣，若两笔金额相同，抵扣借款时间靠前的一笔；
		String loanmoney_sql="select * from loloan a where contno='?contno?' and loantype='0' and payoffflag='0' order by leavemoney desc, newloandate";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(loanmoney_sql);
		sqlbv5.put("contno", mLCContSchema.getContNo());
		LOLoanDB tLOLoanDB = new LOLoanDB();
		LOLoanSet tLOLoanSet = tLOLoanDB.executeQuery(sqlbv5);
		
		double getMoney = tSumAccBala;
		double CashValue = 0;
		if (sum_loanmoney > 0) {
			EdorCalZT tEdorCalZT = new EdorCalZT();
			// 现金价值净额
			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema.setContNo(mLCContSchema.getContNo());
			tLPEdorItemSchema.setEdorValiDate(this.tCurMakeDate);
			tLPEdorItemSchema.setEdorType("LG");
			tEdorCalZT.setEdorInfo(tLPEdorItemSchema);
			CashValue = tEdorCalZT.getContCashValue(tLPEdorItemSchema);
			if (PubFun.round(CashValue * 0.8, 2) - sum_loanmoney < 0) {
				// 借款差额
				double ce = PubFun.round(sum_loanmoney
						- PubFun.round(CashValue * 0.8, 2), 2);
				// 抵扣借款的顺序为：借款金额最大的先抵扣，若两笔金额相同，抵扣借款时间靠前的一笔；
				// 如果借款差额大于借款记录中金额最大记录，不经行进行抵扣
				if (ce <= tLOLoanSet.get(1).getLeaveMoney()) {
					// 抵扣借款的顺序为：借款金额最大的先抵扣，若两笔金额相同，抵扣借款时间靠前的一笔；
					double tRate = AccountManage.calMultiRateMS(tLOLoanSet.get(
							1).getLoanDate(), tLPEdorItemSchema
							.getEdorValiDate(), "000000", "00", "L", "C", "Y",tLOLoanSet.get(1).getCurrency());
					if ((tRate + 1) == 0) {
						CError.buildErr(this, "利率乘积计算失败！");
						return false;
					}
					double ceIntrest = PubFun.round(ce * tRate, 2);
					double all = ce + ceIntrest;
					if (getMoney - all <= 0) {
						CError.buildErr(this, "实际领取金额（" + getMoney
								+ "）小于或等于应抵扣保单借款的金额（" + all + "）不允许操作本次领取！");
						return false;
					}
					getMoney = getMoney - all;

					// 抵扣借款的顺序为：借款金额最大的先抵扣，若两笔金额相同，抵扣借款时间靠前的一笔；
					LOLoanSchema tLOLoanSchema = tLOLoanSet.get(1);
					// tLOLoanSchema
					// 组织数据
					LPReturnLoanSchema tLPReturnLoanSchema = new LPReturnLoanSchema();
					tLPReturnLoanSchema
							.setContNo(tLPEdorItemSchema.getContNo());
					tLPReturnLoanSchema.setPolNo(tLOLoanSchema.getPolNo());
					tLPReturnLoanSchema.setEdorType(tLPEdorItemSchema
							.getEdorType());
					tLPReturnLoanSchema
							.setSerialNo(tLOLoanSchema.getSerialNo());
					tLPReturnLoanSchema.setActuGetNo(tLOLoanSchema
							.getActuGetNo());
					tLPReturnLoanSchema
							.setLoanType(tLOLoanSchema.getLoanType());
					tLPReturnLoanSchema.setOrderNo(tLOLoanSchema.getOrderNo());
					tLPReturnLoanSchema
							.setLoanDate(tLOLoanSchema.getLoanDate());
					// 存放截止本次还款所欠金额
					tLPReturnLoanSchema.setSumMoney(tLOLoanSchema
							.getLeaveMoney());
					tLPReturnLoanSchema.setInputFlag(tLOLoanSchema
							.getInputFlag());
					tLPReturnLoanSchema.setInterestType(tLOLoanSchema
							.getInterestType());
					tLPReturnLoanSchema.setInterestRate(tLOLoanSchema
							.getInterestRate());
					tLPReturnLoanSchema.setInterestMode(tLOLoanSchema
							.getInterestMode());
					tLPReturnLoanSchema.setRateCalType(tLOLoanSchema
							.getRateCalType());
					tLPReturnLoanSchema.setRateCalCode(tLOLoanSchema
							.getRateCalCode());
					tLPReturnLoanSchema.setSpecifyRate(tLOLoanSchema
							.getSpecifyRate());
					tLPReturnLoanSchema.setLeaveMoney(tLOLoanSchema
							.getLeaveMoney()); // 余额
					// if (tLOLoanSchema.getLeaveMoney() == 0) // 还清,改变险种状态
					// {
					// tLPReturnLoanSchema.setPayOffFlag("1");
					// //tLPReturnLoanSchema.setPayOffDate(strCurrentDate);
					// } else {
					// 在此处不可能还清
					tLPReturnLoanSchema.setPayOffFlag("0");
					tLPReturnLoanSchema.setPayOffDate("");
					// }
					tLPReturnLoanSchema.setOperator(this.mGlobalInput.Operator);
					tLPReturnLoanSchema.setMakeDate(this.tCurMakeDate);
					tLPReturnLoanSchema.setMakeTime(this.tCurMakeTime);
					tLPReturnLoanSchema.setModifyDate(this.tCurMakeDate);
					tLPReturnLoanSchema.setModifyTime(this.tCurMakeTime);
					tLPReturnLoanSchema
							.setEdorNo(tSerNo);
					tLPReturnLoanSchema.setLoanNo(tLOLoanSchema.getEdorNo()); // 原批单号
																				// ，
																				// 界面传入
																				// ,
																				// 关键字段
																				// ，
																				// 对应那次还款
																				// ，
																				// 由于可以多次还款
																				// ，
																				// 则可以出现
					tLPReturnLoanSchema.setReturnMoney(ce); // 本次还款本金就是差额部分
					tLPReturnLoanSchema.setReturnInterest(ceIntrest);// 差额部分产生的利息

					// mLPReturnLoanSet.add(tLPReturnLoanSchema);
					mMap.put(tLPReturnLoanSchema, "DELETE&INSERT");
					
					Reflections tRef = new Reflections();
					//借款记录修改
					LOReturnLoanSchema tLOReturnLoanSchema = new LOReturnLoanSchema();
					tRef.transFields(tLOReturnLoanSchema,
							tLPReturnLoanSchema);
					tLOReturnLoanSchema.setOperator(this.mGlobalInput.Operator);
					tLOReturnLoanSchema.setModifyDate(this.tCurMakeDate);
					tLOReturnLoanSchema.setModifyTime(this.tCurMakeTime);
					//mMap.put(tLPReturnLoanSchema, "DELETE");
					mMap.put(tLOReturnLoanSchema, "DELETE&INSERT");
					
					
					LPLoanSchema tLPLoanSchema = new LPLoanSchema();
					tRef.transFields(tLPLoanSchema, tLOLoanSchema);
					tLPLoanSchema.setEdorNo(tSerNo);
					tLPLoanSchema.setEdorType(tLPEdorItemSchema.getEdorType());
					mMap.put(tLPLoanSchema, "INSERT");
					
//					tLOLoanSchema.setSumMoney(tLOLoanSchema.getSumMoney()-tLOReturnLoanSchema.getReturnMoney());//减去本息合计
					tLOLoanSchema.setLeaveMoney(tLOLoanSchema.getLeaveMoney()-tLOReturnLoanSchema.getReturnMoney()
							-tLOReturnLoanSchema.getReturnInterest());
					tLOLoanSchema.setModifyDate(this.tCurMakeDate);
					tLOLoanSchema.setModifyTime(this.tCurMakeTime);
					mMap.put(tLOLoanSchema, "UPDATE");
					 
					

					LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
					PubFun.copySchema(tLJSGetEndorseSchema, tLJSGetEndorseSet
							.get(1));
					tLJSGetEndorseSchema.setGetFlag("0");// 扣费
					tLJSGetEndorseSchema
							.setSubFeeOperationType(BqCode.Pay_LoanCorpus);

					tLJSGetEndorseSchema.setFeeFinaType(BqCalBL.getFinType(
							tLPEdorItemSchema.getEdorType(), "HK",
							tLPEdorItemSchema.getPolNo()));
					tLJSGetEndorseSchema.setGetMoney(ce);
					//mMap.put(tLJSGetEndorseSchema, "DELETE&INSERT");
					tLJSGetEndorseSet.add(tLJSGetEndorseSchema);

					tLJSGetEndorseSchema = new LJSGetEndorseSchema();
					PubFun.copySchema(tLJSGetEndorseSchema, tLJSGetEndorseSet
							.get(1));
					tLJSGetEndorseSchema.setGetFlag("0");// 扣费
					tLJSGetEndorseSchema
							.setSubFeeOperationType(BqCode.Pay_LoanCorpusInterest);

					tLJSGetEndorseSchema.setFeeFinaType(BqCalBL.getFinType(
							tLPEdorItemSchema.getEdorType(), "LX",
							tLPEdorItemSchema.getPolNo()));
					tLJSGetEndorseSchema.setGetMoney(ceIntrest);
					//mMap.put(tLJSGetEndorseSchema, "DELETE&INSERT");
					tLJSGetEndorseSet.add(tLJSGetEndorseSchema);
				}
			}
		} 

		
		for (int k = 1; k <= aLPBnfSet.size(); k++) {
			LPBnfSchema tLPBnfSchema = new LPBnfSchema();
			tLPBnfSchema = aLPBnfSet.get(k);
			String tActuGetNo = PubFun1.CreateMaxNo("GETNO", tLimit);// 产生实付号码
			double actmoney=0;
			for(int l=1;l<=tLJSGetEndorseSet.size();l++)
			{
				LJSGetEndorseSchema temp_LJSGetEndorseSchema = tLJSGetEndorseSet.get(l);
				LJAGetDrawSchema temp_LJAGetDrawSchema = new LJAGetDrawSchema();
				temp_LJAGetDrawSchema.setActuGetNo(tActuGetNo);
				PubFun.copySchema(temp_LJAGetDrawSchema, temp_LJSGetEndorseSchema);
				temp_LJAGetDrawSchema.setGetDutyCode("000000");//
				temp_LJAGetDrawSchema.setGetNoticeNo(tGetNoticeNo);
				temp_LJAGetDrawSchema.setGetDutyKind("000000");
				temp_LJAGetDrawSchema.setSerialNo(tSerNo);//统一用一个流水号
				temp_LJAGetDrawSchema.setMakeDate(tCurMakeDate);
				temp_LJAGetDrawSchema.setMakeTime(tCurMakeTime);
				temp_LJAGetDrawSchema.setModifyDate(tCurMakeDate);
				temp_LJAGetDrawSchema.setModifyTime(tCurMakeTime);
				temp_LJAGetDrawSchema.setRReportFlag("0");//
				temp_LJAGetDrawSchema.setComeFlag("1");
				temp_LJAGetDrawSchema.setGetDate(tCurMakeDate);
				if(aLPBnfSet.size()==1)
				{
					temp_LJAGetDrawSchema.setGetMoney(temp_LJSGetEndorseSchema.getGetMoney());
				}
				else if(k==aLPBnfSet.size())
				{
					double money = 0; 
					for(int c=1;c<=tLJAGetDrawSet.size();c++)
					{
						if(tLJAGetDrawSet.get(c).getFeeFinaType().equals(temp_LJSGetEndorseSchema.getFeeFinaType()))
						{
							money = money + tLJAGetDrawSet.get(c).getGetMoney();
						}
						//modify by jiaqiangli 2009-04-08 null pointer空指针
						if (mLJAGetEndorseSet.get(c) != null && mLJAGetEndorseSet.get(c).getFeeFinaType() != null) {
							if (mLJAGetEndorseSet.get(c).getFeeFinaType().equals(temp_LJSGetEndorseSchema.getFeeFinaType())) {
								money = money + mLJAGetEndorseSet.get(c).getGetMoney();
							}
						}
					}
					temp_LJAGetDrawSchema.setGetMoney(temp_LJSGetEndorseSchema.getGetMoney()-money);
				}
				else
				{
					temp_LJAGetDrawSchema.setGetMoney(PubFun.round(temp_LJSGetEndorseSchema.getGetMoney()*tLPBnfSchema.getBnfLot(),2));
				}
				//temp_LJAGetDrawSchema.setGetMoney(temp_LJSGetEndorseSchema.getGetMoney() * tLPBnfSchema.getBnfLot());
				if(temp_LJSGetEndorseSchema.getGetFlag().equals("0"))
				{
					actmoney =actmoney - temp_LJAGetDrawSchema.getGetMoney();
				}
				else
				{
					actmoney =actmoney + temp_LJAGetDrawSchema.getGetMoney();
				}
				if(!BqCode.Pay_LoanCorpusInterest.equals(temp_LJSGetEndorseSchema.getSubFeeOperationType())
						&&!BqCode.Pay_LoanCorpus.equals(temp_LJSGetEndorseSchema.getSubFeeOperationType()))
				{
					tLJAGetDrawSet.add(temp_LJAGetDrawSchema);
//					actmoney =actmoney + temp_LJAGetDrawSchema.getGetMoney();
				}
				//借款和借款利息才需要
				if(BqCode.Pay_LoanCorpusInterest.equals(temp_LJSGetEndorseSchema.getSubFeeOperationType())
						||BqCode.Pay_LoanCorpus.equals(temp_LJSGetEndorseSchema.getSubFeeOperationType()))
				{
					LJAGetEndorseSchema tLJAGetEndorseSchema = new LJAGetEndorseSchema();
					PubFun.copySchema(tLJAGetEndorseSchema, temp_LJSGetEndorseSchema);
					tLJAGetEndorseSchema.setGetMoney(temp_LJAGetDrawSchema.getGetMoney());
					tLJAGetEndorseSchema.setActuGetNo(tActuGetNo);
					this.mLJAGetEndorseSet.add(tLJAGetEndorseSchema);
//					actmoney =actmoney - temp_LJAGetDrawSchema.getGetMoney();
				}
			}
						
			// 添加实付数据-总表
			LJAGetSchema tLJAGetSchema = new LJAGetSchema();
			tLJAGetSchema.setActuGetNo(tActuGetNo);
			tLJAGetSchema.setOtherNo(mLCPolSchema.getContNo()); // 用什么号
			tLJAGetSchema.setOtherNoType("2");//保单号
			tLJAGetSchema.setPayMode("4");// 内部转账
			tLJAGetSchema.setAppntNo(mLCPolSchema.getAppntNo());
			tLJAGetSchema
					.setSumGetMoney(actmoney);
			tLJAGetSchema.setSaleChnl(mLCPolSchema.getSaleChnl());
			tLJAGetSchema.setShouldDate(tCurMakeDate);
			tLJAGetSchema.setEnterAccDate(tCurMakeDate);
			tLJAGetSchema.setConfDate(tCurMakeDate);

			tLJAGetSchema.setApproveCode(mLCPolSchema.getApproveCode());
			tLJAGetSchema.setApproveDate(mLCPolSchema.getApproveDate());
			tLJAGetSchema.setGetNoticeNo(tGetNoticeNo);
			tLJAGetSchema.setDrawer(tLPBnfSchema.getName());
			tLJAGetSchema.setDrawerID(tLPBnfSchema.getIDNo());
			tLJAGetSchema.setSerialNo(tSerNo);
			tLJAGetSchema.setOperator(mGlobalInput.Operator);
			tLJAGetSchema.setMakeDate(tCurMakeDate);
			tLJAGetSchema.setMakeTime(tCurMakeTime);
			tLJAGetSchema.setModifyDate(tCurMakeDate);
			tLJAGetSchema.setModifyTime(tCurMakeTime);
			tLJAGetSchema.setManageCom(mLCPolSchema.getManageCom());
			tLJAGetSchema.setAgentCom(mLCPolSchema.getAgentCom());
			tLJAGetSchema.setAgentType(mLCPolSchema.getAgentType());
			tLJAGetSchema.setAgentCode(mLCPolSchema.getAgentCode());
			tLJAGetSchema.setAgentGroup(mLCPolSchema.getAgentGroup());
			tLJAGetSchema.setActualDrawer(tLPBnfSchema.getName());
			tLJAGetSchema.setActualDrawerID(tLPBnfSchema.getIDNo());
			tLJAGetSchema.setPolicyCom(mLCPolSchema.getManageCom());
			tLJAGetSchema.setBankCode(tLPBnfSchema.getBankCode());
			tLJAGetSchema.setBankAccNo(tLPBnfSchema.getBankAccNo());
			tLJAGetSchema.setAccName(tLPBnfSchema.getAccName());
			tLJAGetSchema.setBankOnTheWayFlag("0");
			tLJAGetSchema.setBankSuccFlag("0");
			tLJAGetSchema.setSendBankCount(0);

			tLJAGetSet.add(tLJAGetSchema);
		}
		mMap.put(aLPBnfSet, "DELETE&INSERT");
		mMap.put(tLJAGetDrawSet, "DELETE&INSERT");		
		mMap.put(tLJAGetSet, "DELETE&INSERT");
		mMap.put(mLCInsureAccTraceSet, "DELETE&INSERT");
		mMap.put(mLJAGetEndorseSet, "DELETE&INSERT");
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareData() {
		mResult.clear();
		mResult.add(mMap);
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

		cError.moduleName = "AliveAccAssignBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回处理错误
	 * 
	 * @return: CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

}

package com.sinosoft.lis.autopay;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.BqCalBL;
import com.sinosoft.lis.bq.BqCode;
import com.sinosoft.lis.bq.BqPolBalBL;
import com.sinosoft.lis.bq.EdorCalZT;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.operfee.IndiFinUrgeVerifyBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJAGetEndorseSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJFIGetSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.schema.LOLoanSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPContStateSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJAGetEndorseSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LOEngBonusPolSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保费自垫批处理BL
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @ReWrite Nicholas pst for mingshenglife
 * @version 1.0 2.0
 */
public class AutoPayBL {
private static Logger logger = Logger.getLogger(AutoPayBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 错误处理类 */
	private CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** "N||CHECK"--不校验，"Y||CHECK"--校验 */
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 传入的日期，对于批处理，此日期就当日。单张处理此日期由用户确定。 */
	private String mPayDate = "";
	
	/**保单的宽限期止期*/
	private String tLaspDate="";

	/** 保单状态信息表 */
	private LCContStateSet mLCContStateSet = new LCContStateSet();

	/** 主调类的传进来的合同数据 */
	private LCContSchema mLCContSchema = new LCContSchema();
	
	/**存放主险的应收数据*/
	private LJSPayPersonSchema mLJSPayPersonSchema = new LJSPayPersonSchema();
	
	/**应收总表*/
	private LJSPaySchema mLJSPaySchema  = new LJSPaySchema();
	
	/**存放主险的数据*/
	private LCPolSchema mLCPolSchema = new LCPolSchema();

	/** 打印管理表 */
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();

	/** 保单本次自垫的记录 */
	private LOLoanSet mLOLoanSet = new LOLoanSet();

	private LJAGetEndorseSet mLJAGetEndorseSet = new LJAGetEndorseSet();
	
    private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet(); //暂交费表
    //private LJTempFeeClassSchema mLJTempFeeClassSchema = new LJTempFeeClassSchema(); //暂交费分类表
    private LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet(); //暂交费分类表

	private LJAGetSet mLJAGetSet = new LJAGetSet();

	/** 保单下所有 未终止，失效的险 */
	private LCPolSet mLCPolSet = new LCPolSet();

	/** 自垫日期。首次自垫为宽限期满当日，以后为交费日期（LJSPayPerson里的PayDate） */
	private String mAutoPayDate = "";

	/** 保单的现金净额，已减去以前自垫和贷款的本息和 */
	private double mPureCashValue = 0.0;
	
    /**本次应自垫的总金额*/
	private double sumAutoPayMoney = 0.0;

	/** 通知书号码 */
	private String mGetNoticeNo = "";

	/** mPayDate前一天的自垫贷款本息和，打印通知书用 */
	private double mSum = 0.0;

	/** 保单的现金价值，打印通知书用 */
	private double mCashValue = 0.0;

	/** 封装要操作的数据，以便一次提交 */
	private MMap mMap = new MMap();

	private String tCurMakeDate = PubFun.getCurrentDate();

	private String tCurMakeTime = PubFun.getCurrentTime();

	// @Constructor
	public AutoPayBL() {
	}

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

		// 计算自垫日期，返回false说明自垫日期未到输入的日期或根本无应收记录
		if (!getAutoPayDate()) {
			return false;
		}

		// 根据业务逻辑对数据进行处理
		if (!this.dealData()) {
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLCContSchema = (LCContSchema) mInputData.getObjectByObjectName(
					"LCContSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			try {
				mPayDate = (String) mInputData.getObjectByObjectName("String",
						0);
				if (mPayDate == null || mPayDate.equals("")) {
					mPayDate = tCurMakeDate;
				}
			} catch (Exception ex) {
				mPayDate = tCurMakeDate;
			}
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败！");
			return false;
		}

		return true;
	}

	/**
	 * 校验传入的数据的合法性目前只有主险 输出：如果发生错误则返回false,否则返回true
	 * 由于在前面的SQL中过滤了很多条件，无需再进行重复校验
	 */
	private boolean checkData() {
		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @return
	 */
	private boolean dealData() {

		LCContStateSet tLCContStateSet = null;
		LOPRTManagerSchema tLOPRTManagerSchema = null;


		// 提前声明
		SSRS tSSRS = null;
		String tSql = "";
		ExeSQL tExeSQL = new ExeSQL();

		// 算出可以垫67天的金额
		double tMinLoanMoney = 0;

		mPureCashValue = 0;
		

		String tLimit = PubFun.getNoLimit(mLCContSchema.getManageCom());
		String tActuGetNo = PubFun1.CreateMaxNo("GetNo", tLimit);
		//add by jiaqiangli 2009-09-09 号段冲突 lis-9287
		String tEdorNo = PubFun1.CreateMaxNo("EdorAppNo", tLimit);
		String tSerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);

		//自垫通知书字段- 险种名称 应交保费（元） 现金价值（元） 垫交保费（元） 垫交天数 当前现价余额 余额可垫天数 保单失效 日期
		
		// 为了自垫通知书准备数据，而字段又不够，采取拼串的方式记录即 PolNo+"-"+Data,将多张保单的数据存放在保单
		// 打印管理表的附属字段

		double rSPolPrem=0;//应交保费
		double rSPolCV=0;//现金价值
		double rAutoPayPrem=0;// 垫交保费
		int rPolLoanToDays=0;//垫交天数
		double rLeaveCV=0;//剩余现价
		String rInvaliDate="";//保单失效日期，即垫至日期的下一天（估算）
		double rPerDayMoney=0;
		//从子表累计金额
		double tContSumMoney=0;
		


		//计算出宽限期的天数
		EdorCalZT rEdorCalZT =new EdorCalZT();
		//comment by jiaqiangli 2009-11-04 这里只是借用主险的mLCPolSchema.paytodate去计算是否能够垫缴宽末60天
		//int tLapseIntv=(PubFun.calInterval(mLCPolSchema.getPaytoDate(), rEdorCalZT.CalLapseDate(mLCPolSchema.getRiskCode(), mLCPolSchema.getPaytoDate()), "D"));
		//tongmeng 2011-01-19 modify
		//支持宽限期折扣
		int tLapseIntv=(PubFun.calInterval(mLCPolSchema.getPaytoDate(), rEdorCalZT.CalLapseDateNew(mLCPolSchema,mLCPolSchema.getRiskCode(), mLCPolSchema.getPaytoDate()), "D"));
		if(tLapseIntv<=0)
		{
			CError tError = new CError();
			tError.moduleName = "AutoPayBL";
			tError.functionName = "dealData";
			tError.errorMessage = "计算宽限期失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int k = 1; k <= mLCPolSet.size(); k++) {
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = mLCPolSet.get(k);

			double tPurePolCashValue = 0;
			double tPolCashValue = 0;

			// 计算现金价值（此现金价值是退保金额，已经扣除未交保费，但没有扣除借款）
			EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);
          	LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
          	tLPEdorItemSchema.setContNo(tLCPolSchema.getContNo());
          	tLPEdorItemSchema.setEdorType("");
          	tLPEdorItemSchema.setEdorAppDate(tCurMakeDate);
          	tLPEdorItemSchema.setEdorValiDate(tCurMakeTime);
          	tEdorCalZT.setEdorInfo(tLPEdorItemSchema);
			tPolCashValue = tEdorCalZT.getCashValue(tLCPolSchema.getPolNo(),
					null, mAutoPayDate);
			if (tPolCashValue == -1) {
				mErrors.copyAllErrors(tEdorCalZT.mErrors);
				return false;
			}
			tPurePolCashValue += tPolCashValue;
			
			//记录主险才算自垫和贷款
			if(tLCPolSchema.getPolNo().equals(tLCPolSchema.getMainPolNo()))
			{
				BqPolBalBL tBqPolBalBL = new BqPolBalBL();
				// ********自垫本息和----------------->
				if (!tBqPolBalBL.calAutoPayPremAddInterest(tLCPolSchema.getPolNo(),
						mAutoPayDate)) {
					mErrors.copyAllErrors(tBqPolBalBL.mErrors);
					return false;
				}

				tPurePolCashValue -= tBqPolBalBL.getCalResult();
				// ********贷款----------------->
				tBqPolBalBL = new BqPolBalBL();
				if (!tBqPolBalBL.calLoanCorpusAddInterest(
						mLCContSchema.getContNo(), mAutoPayDate)) {
					mErrors.copyAllErrors(tBqPolBalBL.mErrors);
					return false;
				}
				tPurePolCashValue -= tBqPolBalBL.getCalResult();
			}

			mPureCashValue += tPurePolCashValue;

			//现金价值
			//modify by jiaqiangli 2009-11-03 挪到上面程序
			rSPolCV+=tPurePolCashValue;
			
			//add by jiaqiangli 2009-11-03 说明tLCPolSchema主险长期险趸交(短期附加险垫缴) 这里只处理累加现价
			//不能只判断payintv 因为短期主险趸交续保，但是此时tLCPolSchema.appflag='9'
			if( 0 == tLCPolSchema.getPayIntv() && "1".equals(tLCPolSchema.getAppFlag()) ) {
				//rLeaveCV 增加此循环的结尾处的累加变量 注意此时tAutoPayMoney=0
				rLeaveCV += (tPurePolCashValue-0);
				//接下来的ljspayperson不需要判断因为没有催收
				continue;
			}

			// 获得本次应自垫的金额
			LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
			LJSPayPersonSchema rLJSPayPersonSchema = new LJSPayPersonSchema();
			tLJSPayPersonDB.setPolNo(tLCPolSchema.getPolNo());
			tLJSPayPersonDB.setGetNoticeNo(mGetNoticeNo);
			LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
			tLJSPayPersonSet=tLJSPayPersonDB.query();
			double tPolSumPrem=0;
			if(tLJSPayPersonSet.size()<1 || tLJSPayPersonSet==null )
			{
				CError tError = new CError();
				tError.moduleName = "AutoPayBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询应自垫总金额失败！";
				this.mErrors.addOneError(tError);
				return false;
			}else
			{
				rLJSPayPersonSchema=tLJSPayPersonSet.get(1);
			}
			//其中可能包含加费的情况，但也需要进行累计

			for(int t=1;t<=tLJSPayPersonSet.size();t++)
			{
				LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();

				tLJSPayPersonSchema=tLJSPayPersonSet.get(t);
				tPolSumPrem+=tLJSPayPersonSchema.getSumDuePayMoney();
				
				// add by jiaqiangli 2009-11-04 防止长期主险趸交此时没有mLJSPayPersonSchema
				if (mLJSPayPersonSchema.getPolNo() == null || "".equals(mLJSPayPersonSchema.getPolNo()))
					mLJSPayPersonSchema = tLJSPayPersonSet.get(t);
				
				//如果是主险
				if(mLCPolSchema.getPolNo().equals(tLJSPayPersonSchema.getPolNo()))
				{
					mLJSPayPersonSchema=tLJSPayPersonSet.get(t);
				}
				
				//add by jiaqiangli 2009-11-03 长期主险趸交此时没有mLJSPayPersonSchema
				//add by jiaqiangli 2009-11-03 长期主险季缴短期附加险续保(此次续保刚好出在都续保) ljspayperson的paytodate不一致 
				
			}
			//算出每个险种整期的天数
			int rIntv = PubFun.calInterval(rLJSPayPersonSchema.getLastPayToDate(),
					rLJSPayPersonSchema.getCurPayToDate(), "D");
			tContSumMoney+=tPolSumPrem;
			double tAutoPayMoney = 0;

			tAutoPayMoney =tPolSumPrem;
			// 算出每天多少钱
			double tPerDayMoney = tAutoPayMoney / rIntv;
			
			//累计整单每天需要垫交保费
			rPerDayMoney+=tPerDayMoney; 
			// 整个保单的累计
			tMinLoanMoney += tPerDayMoney *tLapseIntv;			

			//现金价值
			//modify by jiaqiangli 2009-11-03 挪到上面程序
			//rSPolCV+=tPurePolCashValue;

			
			rLeaveCV+=(tPurePolCashValue-tAutoPayMoney);
						
	}
		//置上应垫金额
		sumAutoPayMoney=mLJSPaySchema.getSumDuePayMoney();
		if((Math.abs(tContSumMoney-sumAutoPayMoney)>=0.01)) //进行严格的差值比较
		{
			CError tError = new CError();
			tError.moduleName = "AutoPayBL";
			tError.functionName = "dealData";
			tError.errorMessage = "子表金额与总表不一致！";
			this.mErrors.addOneError(tError);
			return false;
		}
		rSPolPrem=mLJSPaySchema.getSumDuePayMoney();
		rAutoPayPrem=mLJSPaySchema.getSumDuePayMoney();
		
		//modify by jiaqiangli 2009-11-03 这里计算垫至日期 不能拿主险的paytodate 因为存在长期主险趸交的情况
		//modify by jiaqiangli 2009-11-03 长期主险季缴短期附加险续保(此次续保刚好出在都续保) 此期的paytodate可能不一致
		if (mLJSPayPersonSchema.getLastPayToDate() == null || "".equals(mLJSPayPersonSchema.getLastPayToDate())) {
			logger.debug("查询有效的短险："+mLJSPayPersonSchema.getPolNo()+"error AutoPayBL");
			return false;
		}
		//modify by jiaqiangli 2009-11-04 这里计算垫至日期 不能拿主险的paytodate 因为存在长期主险趸交的情况
		rInvaliDate=PubFun.calDate(mLJSPayPersonSchema.getLastPayToDate(), (int)(rSPolCV/rPerDayMoney), "D", "");
		
		rPolLoanToDays=(int)(rSPolCV/rPerDayMoney);
		// 比较“现金价值净额”和“本次应自垫的金额”,如果不够垫67天则作如下处理
		if (mPureCashValue < tMinLoanMoney) {
			// 钱不够，做短期险终止处理，长期险失效
			tLCContStateSet = new LCContStateSet();
			tLCContStateSet = failAutoPayMainPolNo(mLCContSchema.getContNo(),tEdorNo,mLCPolSchema);
			if(tLCContStateSet==null || tLCContStateSet.size()< 1)
			{
				return false;
			}
			mLCContStateSet.add(tLCContStateSet);
		} else {

			
			LOLoanSchema tLOLoanSchema = null;
			String tCurrentDate = tCurMakeDate;
			String tCurrentTime = tCurMakeTime;


			// 获得本次垫款是第几次
			String tSqlS = "SELECT to_number(max(OrderNo))+1 FROM LOLoan WHERE ContNo='"
					+ "?a1?" + "' and LoanType='1'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tSqlS);
			sqlbv1.put("a1",mLCContSchema.getContNo());
			int tOrderNo = 1;
			try {
				SSRS rSSRS = new SSRS();
				rSSRS = tExeSQL.execSQL(sqlbv1);
				if (tSSRS != null && rSSRS.MaxRow > 0) {
					tOrderNo = Integer.parseInt(rSSRS.GetText(1, 1));
				}
			} catch (Exception e) {
				tOrderNo = 1;
			}
			
			// 生成记录
			tLOLoanSchema = new LOLoanSchema();
			tLOLoanSchema.setContNo(mLCContSchema.getContNo());
			tLOLoanSchema.setPolNo(mLCPolSchema.getPolNo());
			tLOLoanSchema.setEdorNo(tEdorNo);
			tLOLoanSchema.setSerialNo("");
			tLOLoanSchema.setActuGetNo(tActuGetNo);
			tLOLoanSchema.setLoanType("1"); // 垫交
			tLOLoanSchema.setOrderNo(String.valueOf(tOrderNo));
			tLOLoanSchema.setLoanDate(mAutoPayDate);
			tLOLoanSchema.setInputFlag("1"); // 按照描述进行利息计算
//			String aLoanToDate = getLoanToDate(mLCPolSchema, mPureCashValue,
//					sumAutoPayMoney);
			tLOLoanSchema.setLoanToDate(rInvaliDate);


			tLOLoanSchema.setLeaveMoney(sumAutoPayMoney);
			tLOLoanSchema.setSumMoney(sumAutoPayMoney);
			tLOLoanSchema.setCurLoanMoney(sumAutoPayMoney);
			tLOLoanSchema.setPayOffFlag("0");
			tLOLoanSchema.setOperator(mGlobalInput.Operator);
			tLOLoanSchema.setMakeDate(tCurrentDate);
			tLOLoanSchema.setMakeTime(tCurrentTime);
			tLOLoanSchema.setModifyDate(tCurrentDate);
			tLOLoanSchema.setModifyTime(tCurrentTime);
			//存放用于保存每次还垫日期,即计息日期，首次与自垫日期相同
			tLOLoanSchema.setNewLoanDate(mAutoPayDate);
			tLOLoanSchema.setCurrency(mLCPolSchema.getCurrency());

			//根据垫至日期计算是否能垫整期的标记

			int tIntv = PubFun.calInterval(
					mLJSPayPersonSchema.getCurPayToDate(),rInvaliDate, "D");
			//
			if(tIntv>=0) //说明能够垫整期
			{
				tLOLoanSchema.setZqFlag("1");
				rPolLoanToDays=0;  //能够垫够整期，则不显示垫交天数和失效日期
				rInvaliDate="";      
			}else
			{
				tLOLoanSchema.setZqFlag("0");
				rLeaveCV=0;    //不能够垫够整期，则余额显示为0
			}
			
				

			mLOLoanSet.add(tLOLoanSchema);

			// 生成批改补退费表实付数据,与垫交保单一一对应
			LJAGetEndorseSchema tLJAGetEndorseSchema = setLJAGetEndorse(
					mLCPolSchema, tActuGetNo, tEdorNo, tSerialNo, sumAutoPayMoney);
			if(tLJAGetEndorseSchema==null)
			{
				CError tError = new CError();
				tError.moduleName = "AutoPayBL";
				tError.functionName = "dealData";
				tError.errorMessage = "处理保全补退费失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			mLJAGetEndorseSet.add(tLJAGetEndorseSchema);
			
			// 钱够，自垫------------------------------->

//			changeContState(String tContNo, String tStateType,
//					String tState, String tNewStateDate, String tPolNo)
			//保单层自垫
			String tStateStartDate=mAutoPayDate;
			if(!changeContState(mLCContSchema.getContNo(),"PayPrem","1",tStateStartDate,"000000"))
			{
				CError tError = new CError();
				tError.moduleName = "AutoPayBL";
				tError.functionName = "dealData";
				tError.errorMessage = "跟新保单状态失败！";
				this.mErrors.addOneError(tError);
				return false;
			}

			// 生成自动垫交通知书，合同级别
			tLOPRTManagerSchema = new LOPRTManagerSchema();

			tLOPRTManagerSchema = setLOPRTManager(tEdorNo, PrintManagerBL.CODE_PEdorAPPRE, mLCContSchema,String.valueOf(BqNameFun.getRound(rSPolPrem)),String.valueOf(BqNameFun.getRound(rSPolCV)),String.valueOf(BqNameFun.getRound(rAutoPayPrem)),String.valueOf(rPolLoanToDays),String.valueOf(BqNameFun.getRound(rLeaveCV)),/*tLeaveCvDays,*/rInvaliDate);
			mLOPRTManagerSet.add(tLOPRTManagerSchema);

			String strLimit = PubFun.getNoLimit(mLCContSchema.getManageCom());		
			String strEdorConfNo = PubFun1.CreateMaxNo("EdorConfNo",
					strLimit);
			
			LJAGetSchema tLJAGetSchema = setLJAGet(strEdorConfNo, tSerialNo,
					tActuGetNo, sumAutoPayMoney, mLJSPaySchema);
			mLJAGetSet.add(tLJAGetSchema);
			

			//财务实付总表
			LJFIGetSchema tLJFIGetSchema = new LJFIGetSchema();
			tLJFIGetSchema.setActuGetNo(tLJAGetSchema.getActuGetNo());
			tLJFIGetSchema.setPayMode(tLJAGetSchema.getPayMode());
			tLJFIGetSchema.setOtherNo(tLJAGetSchema.getOtherNo());
			tLJFIGetSchema.setOtherNoType(tLJAGetSchema.getOtherNoType());
			tLJFIGetSchema.setGetMoney(tLJAGetSchema.getSumGetMoney());
			tLJFIGetSchema.setShouldDate(tLJAGetSchema.getShouldDate());
			tLJFIGetSchema.setEnterAccDate(tLJAGetSchema.getEnterAccDate());
			tLJFIGetSchema.setConfDate(tLJAGetSchema.getConfDate());
			tLJFIGetSchema.setSaleChnl(tLJAGetSchema.getSaleChnl());
			tLJFIGetSchema.setManageCom(tLJAGetSchema.getManageCom());
			tLJFIGetSchema.setAPPntName(mLCContSchema.getAppntName());
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
			tLJFIGetSchema.setConfMakeTime(tCurrentTime);
			tLJFIGetSchema.setChequeNo(tLJAGetSchema.getActuGetNo());
			tLJFIGetSchema.setCurrency(tLJAGetSchema.getCurrency());	//TODO

			mMap.put(tLJFIGetSchema, "DELETE&INSERT");


            //2-查询是否已经交费
            LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
            tLJTempFeeSchema.setTempFeeNo(mGetNoticeNo);
            tLJTempFeeSchema.setTempFeeType("2");
            tLJTempFeeSchema.setRiskCode(mLCPolSchema.getRiskCode());
            if (!queryTempFee(tLJTempFeeSchema))
            {
                return false;
            }

            //3-组成暂交费纪录和暂交费分类表纪录
            tLJTempFeeSchema = new LJTempFeeSchema();
            tLJTempFeeSchema.setTempFeeNo(mGetNoticeNo);
            tLJTempFeeSchema.setTempFeeType("2");
            tLJTempFeeSchema.setRiskCode(mLCPolSchema.getRiskCode());
            tLJTempFeeSchema.setPayIntv(mLCPolSchema.getPayIntv());
            tLJTempFeeSchema.setOtherNo(mLCPolSchema.getContNo());
            tLJTempFeeSchema.setOtherNoType("0");

            tLJTempFeeSchema.setPayMoney(mLJSPaySchema.getSumDuePayMoney());

            tLJTempFeeSchema.setPayDate(mLJSPaySchema.getPayDate());
            tLJTempFeeSchema.setEnterAccDate(tCurrentDate);
            tLJTempFeeSchema.setConfDate(tCurrentDate);
            tLJTempFeeSchema.setConfMakeDate(tCurrentDate);
            tLJTempFeeSchema.setConfMakeTime(tCurrentTime);     
            tLJTempFeeSchema.setSaleChnl(mLCPolSchema.getSaleChnl());
            tLJTempFeeSchema.setManageCom(mLCPolSchema.getManageCom());
            tLJTempFeeSchema.setPolicyCom(mLCPolSchema.getManageCom());
            tLJTempFeeSchema.setAgentCom(mLJSPaySchema.getAgentCom());
            tLJTempFeeSchema.setAgentType(mLJSPaySchema.getAgentType());
            tLJTempFeeSchema.setAPPntName(mLCPolSchema.getAppntName());
            tLJTempFeeSchema.setAgentGroup(mLJSPaySchema.getAgentGroup());
            tLJTempFeeSchema.setAgentCode(mLJSPaySchema.getAgentCode());


            tLJTempFeeSchema.setConfFlag("1");
            tLJTempFeeSchema.setSerialNo(tSerialNo);
            tLJTempFeeSchema.setOperator(mGlobalInput.Operator);
            tLJTempFeeSchema.setMakeDate(tCurrentDate);
            tLJTempFeeSchema.setModifyDate(tCurrentDate);
            tLJTempFeeSchema.setMakeTime(tCurrentTime);
            tLJTempFeeSchema.setModifyTime(tCurrentTime);
            tLJTempFeeSchema.setCurrency(mLCPolSchema.getCurrency());

            mLJTempFeeSet.add(tLJTempFeeSchema);

            LJTempFeeClassSchema mLJTempFeeClassSchema = new LJTempFeeClassSchema();
            
	        mLJTempFeeClassSchema.setTempFeeNo(mGetNoticeNo);
	        mLJTempFeeClassSchema.setPayMode("5");
	        mLJTempFeeClassSchema.setChequeNo(tActuGetNo);
	        mLJTempFeeClassSchema.setPayMoney(mLJSPaySchema.getSumDuePayMoney());
	        mLJTempFeeClassSchema.setAppntName(mLCPolSchema.getAppntName());
	        mLJTempFeeClassSchema.setPayDate(mLJSPaySchema.getPayDate());
	        mLJTempFeeClassSchema.setApproveDate(tCurrentDate);
	        mLJTempFeeClassSchema.setEnterAccDate(tCurrentDate);
	        mLJTempFeeClassSchema.setConfDate(tCurrentDate);
	        mLJTempFeeClassSchema.setConfFlag("1");
	        mLJTempFeeClassSchema.setOtherNo(mLCPolSchema.getContNo());
	        mLJTempFeeClassSchema.setOtherNoType("0");
	        mLJTempFeeClassSchema.setConfMakeDate(tCurrentDate);
	        mLJTempFeeClassSchema.setSerialNo(tSerialNo);
	        mLJTempFeeClassSchema.setMakeDate(tCurrentDate);
	        mLJTempFeeClassSchema.setMakeTime(tCurrentTime);
	        mLJTempFeeClassSchema.setModifyDate(tCurrentDate);
	        mLJTempFeeClassSchema.setModifyTime(tCurrentTime);
	        mLJTempFeeClassSchema.setManageCom(mLCPolSchema.getManageCom());
	        mLJTempFeeClassSchema.setOperator(mGlobalInput.Operator);
	        mLJTempFeeClassSchema.setConfMakeTime(tCurrentTime); 
	        mLJTempFeeClassSchema.setPolicyCom(mLCPolSchema.getManageCom());
	        mLJTempFeeClassSchema.setCurrency(mLCPolSchema.getCurrency());

	        //不核销的情况下，要在这里加入
	        mLJTempFeeClassSet.add(mLJTempFeeClassSchema);		

			// 财务核销，复用续期核销类

			VData thxVDate = new VData();
			thxVDate.add(mGlobalInput);
			thxVDate.add(tLJTempFeeSchema);

			// 开始核销
			IndiFinUrgeVerifyBL tIndiFinUrgeVerifyBL = new IndiFinUrgeVerifyBL();
			tIndiFinUrgeVerifyBL.setBQUseFlag();
			tIndiFinUrgeVerifyBL.setBQGetNoticeNo(mGetNoticeNo);
			if (!tIndiFinUrgeVerifyBL.submitData(thxVDate, "VERIFY")) {
				mErrors.copyAllErrors(tIndiFinUrgeVerifyBL.mErrors);
				return false;
			}

			// 核销完毕，获得数据，包括：
			// 实收总表（插入）
			// 应收总表（删除）
			// 实收个人表（插入）
			// 个人保单表（更新）
			// 个人保单表（更新）
			// 保费项表（更新）
			// 保险责任表（更新）
			VData rhxResult = tIndiFinUrgeVerifyBL.getResult();
			MMap thxMMap = new MMap();
			thxMMap = (MMap) rhxResult.getObjectByObjectName("MMap", 0);
			mMap.add(thxMMap);

			//
			LJAPayPersonSet tLJAPayPersonSet =(LJAPayPersonSet) rhxResult.getObjectByObjectName("LJAPayPersonSet", 0);

			// 置自垫标记
			if (tLJAPayPersonSet!=null && tLJAPayPersonSet.size() > 0) {
				for (int k = 1; k <= tLJAPayPersonSet.size(); k++) {
					tLJAPayPersonSet.get(k).setInInsuAccState("3");
				}
			}
			
			mMap.put(mLOLoanSet, "DELETE&INSERT");
			mMap.put(mLJAGetEndorseSet, "DELETE&INSERT");
			mMap.put(mLJAGetSet, "DELETE&INSERT");
			mMap.put(tLJAPayPersonSet, "DELETE&INSERT");
			mMap.put(mLJTempFeeClassSet, "DELETE&INSERT");
			mMap.put(mLJTempFeeSet, "DELETE&INSERT");
		}
		// 其它操作
		mMap.put(mLCContStateSet, "DELETE&INSERT");
		mMap.put(mLOPRTManagerSet, "DELETE&INSERT");

		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	/**
	 * 生成批改补退费表实收数据
	 */
	private LJAGetEndorseSchema setLJAGetEndorse(LCPolSchema tLCPolSchema,
			String tActuGetNo, String tEdorNo, String tSerialNo,
			double loanMoney) {
		LJAGetEndorseSchema tLJAGetEndorseSchema = new LJAGetEndorseSchema();
		tLJAGetEndorseSchema.setActuGetNo(tActuGetNo);
		tLJAGetEndorseSchema.setEndorsementNo(tEdorNo);
		tLJAGetEndorseSchema.setFeeOperationType("AP"); // 自动垫交

		String finType = BqCalBL
				.getFinType("DJ", "DJ", tLCPolSchema.getPolNo());
		if ("".equals(finType)) {
			CError.buildErr(this, "在LDCode1中缺少保全财务接口转换类型编码");
			return null;
		}
		tLJAGetEndorseSchema.setFeeFinaType(finType); // 还款本金属于补费
		tLJAGetEndorseSchema.setGrpContNo(tLCPolSchema.getGrpContNo());

		
		tLJAGetEndorseSchema.setDutyCode("0"); // 必须有
		tLJAGetEndorseSchema.setPayPlanCode("0");
		tLJAGetEndorseSchema.setContNo(tLCPolSchema.getContNo());
		tLJAGetEndorseSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
		tLJAGetEndorseSchema.setPolNo(tLCPolSchema.getPolNo());
		tLJAGetEndorseSchema.setAppntNo(tLCPolSchema.getAppntNo());
		tLJAGetEndorseSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
		tLJAGetEndorseSchema.setGetDate(tCurMakeDate);
		tLJAGetEndorseSchema.setEnterAccDate(tCurMakeDate);
		tLJAGetEndorseSchema.setGetConfirmDate(tCurMakeDate);
		tLJAGetEndorseSchema.setGetMoney(loanMoney);
		tLJAGetEndorseSchema.setKindCode(tLCPolSchema.getKindCode());
		tLJAGetEndorseSchema.setRiskCode(tLCPolSchema.getRiskCode());
		tLJAGetEndorseSchema.setRiskVersion(tLCPolSchema.getRiskVersion());
		tLJAGetEndorseSchema.setManageCom(tLCPolSchema.getManageCom());
		tLJAGetEndorseSchema.setAgentCom(tLCPolSchema.getAgentCom());
		tLJAGetEndorseSchema.setAgentCode(tLCPolSchema.getAgentCode());
		tLJAGetEndorseSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
		tLJAGetEndorseSchema.setHandler(tLCPolSchema.getHandler());
		tLJAGetEndorseSchema.setSubFeeOperationType(BqCode.Get_AutoPayPrem);
		tLJAGetEndorseSchema.setPolType("1"); // 个人
		tLJAGetEndorseSchema.setSerialNo(tSerialNo);
		tLJAGetEndorseSchema.setOperator("000");
		tLJAGetEndorseSchema.setMakeDate(tCurMakeDate);
		tLJAGetEndorseSchema.setMakeTime(tCurMakeTime);
		tLJAGetEndorseSchema.setGetFlag("1"); // 1--退费,0--补费
		tLJAGetEndorseSchema.setModifyDate(tCurMakeDate);
		tLJAGetEndorseSchema.setModifyTime(tCurMakeTime);
		tLJAGetEndorseSchema.setOtherNo(tEdorNo); // 批单号
		tLJAGetEndorseSchema.setOtherNoType("3"); // 3---保全给付 ---->批单号

		return tLJAGetEndorseSchema;
	}

	/**
	 * 生成实付总表数据
	 */
	private LJAGetSchema setLJAGet(String tEdorNo, String tSerialNo,
			String tActuGetNo, double needPay, LJSPaySchema mLJSPaySchema) {
		// 产生实付数据
		LJAGetSchema tLJAGetSchema = new LJAGetSchema();
		tLJAGetSchema.setActuGetNo(tActuGetNo);
		tLJAGetSchema.setOtherNo(tEdorNo); // 批改号
		tLJAGetSchema.setOtherNoType("10");
		tLJAGetSchema.setPayMode("5"); // 内部转账
		tLJAGetSchema.setAppntNo(mLJSPaySchema.getAppntNo());
		tLJAGetSchema.setSumGetMoney(needPay);
		tLJAGetSchema.setShouldDate(tCurMakeDate);
		tLJAGetSchema.setEnterAccDate(tCurMakeDate);
		tLJAGetSchema.setConfDate(tCurMakeDate);
		tLJAGetSchema.setStartGetDate(tCurMakeDate);
		// 取得银行信息有待确认
		tLJAGetSchema.setBankCode(mLJSPaySchema.getBankCode());
		tLJAGetSchema.setBankAccNo(mLJSPaySchema.getBankAccNo());
		tLJAGetSchema.setSerialNo(tSerialNo);
		tLJAGetSchema.setOperator("000");
		tLJAGetSchema.setMakeDate(tCurMakeDate);
		tLJAGetSchema.setMakeTime(tCurMakeTime);
		tLJAGetSchema.setModifyDate(tCurMakeDate);
		tLJAGetSchema.setModifyTime(tCurMakeTime);
		tLJAGetSchema.setManageCom(mLJSPaySchema.getManageCom());
		tLJAGetSchema.setAgentCom(mLJSPaySchema.getAgentCom());
		tLJAGetSchema.setAgentCode(mLJSPaySchema.getAgentCode());
		tLJAGetSchema.setAgentGroup(mLJSPaySchema.getAgentGroup());
		
		//增加一些必要值
		tLJAGetSchema.setAppntNo(mLCPolSchema.getAppntNo());
		tLJAGetSchema.setInsuredNo(mLCPolSchema.getInsuredNo());
		tLJAGetSchema.setSaleChnl(mLCPolSchema.getSaleChnl());
		tLJAGetSchema.setPolicyCom(mLCPolSchema.getManageCom());
		tLJAGetSchema.setCurrency(mLCPolSchema.getCurrency());
		return tLJAGetSchema;
	}

	/**
	 * 不能自动垫交的处理 传入保单号码（ContNo），对应短期附加险终止，长险失效
	 * 
	 * @param tContNo
	 * @return LCContStateSet
	 */
	private LCContStateSet failAutoPayMainPolNo(String tContNo,String tEdorNo,LCPolSchema mLCPolSchema) {
		LCContStateSet rLCContStateSet = new LCContStateSet(); // 要返回的结果
		LCPolDB tLCPolDB = null;
		LCPolSet tLCPolSet = null;
		LCPolSchema tLCPolSchema = null;
		LCContStateDB tLCContStateDB = null;
		LCContStateSet tLCContStateSet = null;
		LCContStateSchema tLCContStateSchema = null;
		String tCurrentDate = tCurMakeDate;
		String tCurrentTime = tCurMakeTime;
		String tSql;
		
		String tInvaliRiskName="";
		
		//状态新开始日期
		String tStateStartDate=mAutoPayDate;

		// 主险和长期附加险失效，短期附加险终止。
		// *****************处理短期险************************************
		// 处理短期险种，将其直接置为终止状态
		tSql = "SELECT *"
				+ " FROM LCPol y"
				+ " WHERE y.appflag='1' and ContNo='"
				+ "?ContNo?"
				+ "' and InsuYearFlag='Y' and InsuYear<=1"
				+ " and not exists(select 'Y' from LCContState where PolNo=y.PolNo"
				+ " and StateType='Terminate'"
				+ " and State='1'"
				+ " and EndDate is null)"
				+ " union"
				+ " SELECT *"
				+ " FROM LCPol m"
				+ " WHERE m.appflag='1' and ContNo='"
				+ "?ContNo?"
				+ "' and InsuYearFlag='M' and InsuYear<=12"
				+ " and not exists(select 'M' from LCContState where PolNo=m.PolNo"
				+ " and StateType='Terminate'"
				+ " and State='1'"
				+ " and EndDate is null)"
				+ " union"
				+ " SELECT *"
				+ " FROM LCPol d"
				+ " WHERE d.appflag='1' and ContNo='"
				+ "?ContNo?"
				+ "' and InsuYearFlag='D' and InsuYear<=365"
				+ " and not exists(select 'D' from LCContState where PolNo=d.PolNo"
				+ " and StateType='Terminate'"
				+ " and State='1'"
				+ " and EndDate is null)"
				+ " union"
				+ " SELECT *"
				+ " FROM LCPol a"
				+ " WHERE a.appflag='1' and ContNo='"
				+ "?ContNo?"
				+ "' and InsuYearFlag='A' and (InsuYear-(months_between(CValiDate,InsuredBirthday)/12))<=1"
				+ " and not exists(select 'A' from LCContState where PolNo=a.PolNo"
				+ " and StateType='Terminate'" + " and State='1'"
				+ " and EndDate is null)";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("ContNo", tContNo);
		tLCPolDB = new LCPolDB();
		tLCPolSet = new LCPolSet();
		logger.debug("查询有效的短险："+tSql);
		tLCPolSet = tLCPolDB.executeQuery(sqlbv1);
		// 生成新状态记录,即保单终止状态
		if (tLCPolSet != null && tLCPolSet.size() > 0) {
			for (int i = 1; i <= tLCPolSet.size(); i++) {
				tLCPolSchema = new LCPolSchema();
				tLCPolSchema = tLCPolSet.get(i);
				tInvaliRiskName+=BqNameFun.getRiskShortName(tLCPolSchema.getRiskCode())+"、";
				tLCContStateSchema = new LCContStateSchema();
				tLCContStateSchema.setContNo(tLCPolSchema.getContNo());
				tLCContStateSchema.setInsuredNo("000000");
				tLCContStateSchema.setPolNo(tLCPolSchema.getPolNo());
				tLCContStateSchema.setStateType("Terminate");
				tLCContStateSchema.setState("1");
				tLCContStateSchema.setStateReason("07");
				tLCContStateSchema.setStartDate(tStateStartDate);
				tLCContStateSchema.setOperator(this.mGlobalInput.Operator);
				tLCContStateSchema.setMakeDate(tCurrentDate);
				tLCContStateSchema.setMakeTime(tCurrentTime);
				tLCContStateSchema.setModifyDate(tCurrentDate);
				tLCContStateSchema.setModifyTime(tCurrentTime);
				rLCContStateSet.add(tLCContStateSchema);
				terminateData(tLCPolSchema,tEdorNo);
			}
		}
		// *****************处理长期险将其改为失效状态，结束先前有效状态，新增失效状态****************
		// 查询需改变的状态
		tSql = "SELECT *"
				+ " FROM LCContState b"
				+ " WHERE EndDate is null"
				+ " and StateType='Available'"
				+ " and State='0'"
				+ " and exists(select 'Y'"
				+ " from (select PolNo"
				+ " from LCPol c"
				+ " where ContNo='"
				+ "?ContNo?"
				+ "'"
				+ " and not exists(select 'Y' from LCPol where PolNo=c.PolNo and InsuYearFlag='Y' and InsuYear<=1)"
				+ " and not exists(select 'M' from LCPol where PolNo=c.PolNo and InsuYearFlag='M' and InsuYear<=12)"
				+ " and not exists(select 'D' from LCPol where PolNo=c.PolNo and InsuYearFlag='D' and InsuYear<=365)"
				+ " and not exists(select 'A' from LCPol where PolNo=c.PolNo and InsuYearFlag='A' and (InsuYear-(months_between(CValiDate,InsuredBirthday)/12))<=1)) a"
				+ " where a.PolNo=b.PolNo)";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
        sqlbv2.sql(tSql);		
		sqlbv2.put("ContNo", tContNo);
		tLCContStateDB = new LCContStateDB();
		tLCContStateSet = new LCContStateSet();
		logger.debug("查询有效的短险状态："+tSql);
		tLCContStateSet = tLCContStateDB.executeQuery(sqlbv2);
		if (tLCContStateSet != null && tLCContStateSet.size() > 0) {
			for (int i = 1; i <= tLCContStateSet.size(); i++) {
				tLCContStateSchema = new LCContStateSchema();
				tLCContStateSchema = tLCContStateSet.get(i);
				tLCContStateSchema.setEndDate(PubFun.calDate(tStateStartDate, -1, "D", ""));
				tLCContStateSchema.setOperator(this.mGlobalInput.Operator);
				tLCContStateSchema.setModifyDate(tCurrentDate);
				tLCContStateSchema.setModifyTime(tCurrentTime);
				rLCContStateSet.add(tLCContStateSchema);
			}
		}
		// 生成新状态记录
		tSql = "SELECT *"
				+ " FROM LCPol a"
				+ " WHERE ContNo='"
				+ "?ContNo?"
				+ "'"
				+ " and not exists(select 'Y' from LCPol where PolNo=a.PolNo and InsuYearFlag='Y' and InsuYear<=1)"
				+ " and not exists(select 'M' from LCPol where PolNo=a.PolNo and InsuYearFlag='M' and InsuYear<=12)"
				+ " and not exists(select 'D' from LCPol where PolNo=a.PolNo and InsuYearFlag='D' and InsuYear<=365)"
				+ " and not exists(select 'A' from LCPol where PolNo=a.PolNo and InsuYearFlag='A' and (InsuYear-(months_between(CValiDate,InsuredBirthday)/12))<=1)"
				+ " and not exists(select 'X' from LCContState where PolNo=a.PolNo"
				+ " and StateType='Available'" + " and State='1'"
				+ " and EndDate is null)";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(tSql);
		sqlbv3.put("ContNo", tContNo);
		tLCPolDB = new LCPolDB();
		tLCPolSet = new LCPolSet();
		logger.debug("查询有效的长险："+tSql);
		tLCPolSet = tLCPolDB.executeQuery(sqlbv3);
		if (tLCPolSet != null && tLCPolSet.size() > 0) {
			for (int i = 1; i <= tLCPolSet.size(); i++) {
				tLCPolSchema = new LCPolSchema();
				tLCPolSchema = tLCPolSet.get(i);
				tLCContStateSchema = new LCContStateSchema();
				tLCContStateSchema.setContNo(tLCPolSchema.getContNo());
				tLCContStateSchema.setInsuredNo("000000");
				tLCContStateSchema.setPolNo(tLCPolSchema.getPolNo());
				tLCContStateSchema.setStateType("Available");
				tLCContStateSchema.setState("1");
				tLCContStateSchema.setStateReason("02");
				tLCContStateSchema.setStartDate(tStateStartDate);
				tLCContStateSchema.setOperator(this.mGlobalInput.Operator);
				tLCContStateSchema.setMakeDate(tCurrentDate);
				tLCContStateSchema.setMakeTime(tCurrentTime);
				tLCContStateSchema.setModifyDate(tCurrentDate);
				tLCContStateSchema.setModifyTime(tCurrentTime);
				rLCContStateSet.add(tLCContStateSchema);
			}			
		}
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema = setLOPRTManager(tEdorNo, PrintManagerBL.CODE_PEdorContInvalid,
				mLCPolSchema,tInvaliRiskName);
		mLOPRTManagerSet.add(tLOPRTManagerSchema);
		return rLCContStateSet;
	}

	/**
	 * 生成失效通知书打印管理表数据
	 * 
	 * @param tLCPolSchema
	 * @param tEdorNo
	 * @param type
	 * @return
	 */
	private LOPRTManagerSchema setLOPRTManager(String tEdorNo, String type,
			LCPolSchema tLCPolSchema,String tInvaliRiskName) {
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

		String mLimit = PubFun.getNoLimit(tLCPolSchema.getManageCom());
		String serNo = PubFun1.CreateMaxNo("PRTSEQNO", mLimit);
		tLOPRTManagerSchema.setPrtSeq(serNo);

		tLOPRTManagerSchema.setOtherNo(tLCPolSchema.getContNo());
		tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 个人保单号
		tLOPRTManagerSchema.setCode(type);
		tLOPRTManagerSchema.setManageCom(tLCPolSchema.getManageCom());
		tLOPRTManagerSchema.setAgentCode(tLCPolSchema.getAgentCode());
		tLOPRTManagerSchema.setReqCom(mGlobalInput.ManageCom);
		tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
		tLOPRTManagerSchema.setPrtType("1"); // 后台打印
		tLOPRTManagerSchema.setStateFlag("0"); // 提交打印
		tLOPRTManagerSchema.setMakeDate(tCurMakeDate);
		tLOPRTManagerSchema.setMakeTime(tCurMakeTime);
		tLOPRTManagerSchema.setStandbyFlag1(PubFun.calDate(tLaspDate, 1, "D", ""));
		tLOPRTManagerSchema.setStandbyFlag2(this.mGetNoticeNo);
		tLOPRTManagerSchema.setStandbyFlag4(BqCode.BQ_InvalidationStateReason_InGracePayPrem);
		tLOPRTManagerSchema.setStandbyFlag7(tEdorNo);
		tLOPRTManagerSchema.setRemark(tInvaliRiskName);

		return tLOPRTManagerSchema;
	}

	/**
	 * 生成自垫通知书打印管理表数据
	 * 
	 * @param tLCPolSchema
	 * @param tEdorNo
	 * @param type
	 * 	@param tSPolPrem 应交保费
		@param tSPolCV 现金价值
		@param tAutoPayPrem  垫交保费
		@param tPolLoanToDays 垫交天数
		@param tLeaveCV 剩余现价
		@param tLeaveCvDays 余额可垫天数
		@param tInvaliDate 保单失效日期，即垫至日期的下一天（估算）
	 * @return
	 */
	private LOPRTManagerSchema setLOPRTManager(String tEdorNo, String type,	LCContSchema tLCContSchema,
			String tSPolPrem,String tSPolCV, String tAutoPayPrem, String tPolLoanToDays, 
			String tLeaveCV, /*String tLeaveCvDays,*/String tInvaliDate)
	{
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

		String mLimit = PubFun.getNoLimit(tLCContSchema.getManageCom());
		String serNo = PubFun1.CreateMaxNo("PRTSEQNO", mLimit);
		tLOPRTManagerSchema.setPrtSeq(serNo);

		tLOPRTManagerSchema.setOtherNo(tLCContSchema.getContNo());
		tLOPRTManagerSchema.setOtherNoType("02"); // 个人保单号
		tLOPRTManagerSchema.setCode(type);
		tLOPRTManagerSchema.setManageCom(tLCContSchema.getManageCom());
		tLOPRTManagerSchema.setAgentCode(tLCContSchema.getAgentCode());
		tLOPRTManagerSchema.setReqCom(mGlobalInput.ManageCom);
		tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
		tLOPRTManagerSchema.setPrtType("1"); // 后台打印
		tLOPRTManagerSchema.setStateFlag("0"); // 提交打印
		tLOPRTManagerSchema.setMakeDate(tCurMakeDate);
		tLOPRTManagerSchema.setMakeTime(tCurMakeTime);
		// 用于关联借款记录
		tLOPRTManagerSchema.setStandbyFlag1(tSPolPrem);
		tLOPRTManagerSchema.setStandbyFlag2(tSPolCV);
		tLOPRTManagerSchema.setStandbyFlag3(tAutoPayPrem);
		tLOPRTManagerSchema.setStandbyFlag4(tPolLoanToDays);
		tLOPRTManagerSchema.setStandbyFlag5(tLeaveCV);
		//tLOPRTManagerSchema.setStandbyFlag6(tLeaveCvDays);
		tLOPRTManagerSchema.setStandbyFlag7(tInvaliDate);
		tLOPRTManagerSchema.setRemark(tEdorNo);

		return tLOPRTManagerSchema;
	}

	private LCPolSchema getPolToEnd(LCPolSchema tLCPolSchema) {
		if (tLCPolSchema != null) {
			tLCPolSchema.setAppFlag("4");
			tLCPolSchema.setModifyDate(tCurMakeDate);
			tLCPolSchema.setModifyTime(tCurMakeTime);
		}
		return tLCPolSchema;
	}

	private LCContSchema getLCCont(String tContNo) {
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(tContNo);
		if (!tLCContDB.getInfo()) {
			return null;
		}
		tLCContSchema = tLCContDB.getSchema();
		return tLCContSchema;
	}

	private LCContSchema getContToEnd(String tContNo) {
		LCContSchema tLCContSchema = null;
		if (tContNo != null) {
			tLCContSchema = getLCCont(tContNo);
			if (tLCContSchema != null) {
				tLCContSchema.setAppFlag("4");
				tLCContSchema.setModifyDate(tCurMakeDate);
				tLCContSchema.setModifyTime(tCurMakeTime);
			}
		}
		return tLCContSchema;
	}

	/**
	 * 生成终止通知书打印管理表数据
	 * 
	 * @param tContNo
	 *            String 保单号
	 * @param type
	 *            String 打印通知书类型
	 * @return LOPRTManagerSchema 打印记录
	 */
	private LOPRTManagerSchema setLOPRTManager(String tPolNo, String type,
			String tCountDate, LCPolSchema tLCPolSchema,String tEdorNo) {
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		String mLimit = PubFun.getNoLimit(tLCPolSchema.getManageCom());
		String serNo = PubFun1.CreateMaxNo("PRTSEQNO", mLimit);
		tLOPRTManagerSchema.setPrtSeq(serNo);
		tLOPRTManagerSchema.setOtherNo(tPolNo);
		tLOPRTManagerSchema.setOtherNoType("00"); // 保单号
		tLOPRTManagerSchema.setCode(type); // 通知书类型
		tLOPRTManagerSchema.setReqCom(mGlobalInput.ManageCom);
		tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);

		tLOPRTManagerSchema.setManageCom(tLCPolSchema.getManageCom());
		tLOPRTManagerSchema.setAgentCode(tLCPolSchema.getAgentCode());
		tLOPRTManagerSchema.setPrtType("1"); // 后台打印
		tLOPRTManagerSchema.setStateFlag("0"); // 提交打印
		tLOPRTManagerSchema.setMakeDate(tCurMakeDate);
		tLOPRTManagerSchema.setMakeTime(tCurMakeTime);

		// 预留字段
		tLOPRTManagerSchema.setStandbyFlag1(tEdorNo);
		// 终止日期
		tLOPRTManagerSchema.setStandbyFlag2(tCountDate);

		

		return tLOPRTManagerSchema;
	}

	/**
	 * 获得自垫日期；同时与传入日期比较，判断是否应该自垫；记录是否已经自垫标志；获得通知书号码
	 * 首次自垫为宽限期满当日，以后为交费日期（LJSPayPerson里的PayDate） 结果存入mAutoPayDate
	 */
	private boolean getAutoPayDate() {

		LJSPayDB tLJSPayDB = new LJSPayDB();

		// 除去豁免险的情况，保证所选保单需要进行续期缴费，并按缴费日期排序
		String tSCQL = "select * from ljspay where OtherNoType='2' and  OtherNo='"
				+ "?OtherNo?"
				+ "' and sumduepaymoney>0  order by PayDate";
		// tLJSPayDB.setGetNoticeNo(mGetNoticeNo);
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSCQL);
		sqlbv.put("OtherNo", mLCContSchema.getContNo());
		LJSPaySet tLJSPaySet = tLJSPayDB.executeQuery(sqlbv);
		if ((tLJSPaySet == null) || (tLJSPaySet.size() != 1)) {
			CError.buildErr(this, "查询保单(" + mLCContSchema.getContNo()
					+ ")续期应收失败!");
			return false;
		}
		mLJSPaySchema = tLJSPaySet.get(1);

		tLaspDate = mLJSPaySchema.getPayDate();

		mGetNoticeNo = mLJSPaySchema.getGetNoticeNo();



		FDate aFDate = new FDate();
		FDate bFDate = new FDate();

		// 取得宽限期延长期
		int nExtendLapseDates = 0;
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("ExtendLapseDates");
		if (!tLDSysVarDB.getInfo()) {
			nExtendLapseDates = 0;
		} else {
			nExtendLapseDates = Integer.parseInt(tLDSysVarDB.getSchema()
					.getSysVarValue());
		}
		//modify by jiaqiangli 2009-06-08
		//用于判断系统合适给保单置自垫操作，宽限期可以根据需求进行调整，现在为0
		String pPayDate = PubFun.calDate(tLaspDate, nExtendLapseDates, "D", "");
		// 此处算出来的是宽限期截止当日，自垫日期应为此日的下一天，因此这里加一
		mAutoPayDate = PubFun.calDate(tLaspDate, 1, "D", null);
		pPayDate = PubFun.calDate(tLaspDate, 1, "D", null);
		//判断入口日期是否过宽限期
		if (aFDate.getDate(mPayDate).before(bFDate.getDate(pPayDate))) {
			CError tError = new CError();
			tError.moduleName = "AutoPayBL";
			tError.functionName = "getAutoPayDate";
			tError.errorMessage = "当前保单未自垫，保单仍在宽限期内！";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 获得保单下有效的险种,
		String tSql = "select * from LCPol  a where ContNo='"
				+ "?ContNo?"
				+ "' and polno in (select polno from ljspayperson where contNo='"
				+ "?ContNo?" + "' and getnoticeno='"+"?getnoticeno?"
				+ "') and not exists(select 'B' from LCContState where PolNo=a.PolNo  and contno=a.contno and StateType='Available' and State='1' and EndDate is null)"
				+ " and not exists(select 'C' from LCContState where PolNo=a.PolNo and contno=a.contno and StateType='Terminate' and State='1' and EndDate is null)"
				//add by jiaqiangli 长期主险趸交无催收 不在ljspayperson里 但是要计算现价
				+ " union "
				+ " select * from lcpol a where payintv=0 and appflag='1' and polno=mainpolno " 
				+ " and not exists(select 'B' from LCContState where PolNo=a.PolNo  and contno=a.contno and StateType='Available' and State='1' and EndDate is null) "
				+ " and not exists(select 'C' from LCContState where PolNo=a.PolNo and contno=a.contno and StateType='Terminate' and State='1' and EndDate is null) "
				+ " and exists(select 1 from lmriskapp where riskperiod = 'L' and riskcode=a.riskcode) and ContNo='"+ "?ContNo?"+ "' "
				;
        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
        sqlbv1.sql(tSql);
        sqlbv1.put("ContNo", mLCContSchema.getContNo());
        sqlbv1.put("getnoticeno", mGetNoticeNo);
		LCPolDB tLCPolDB = new LCPolDB();
		logger.debug("获得保单下有效的险种:" + tSql);
		mLCPolSet = tLCPolDB.executeQuery(sqlbv1);

		if (mLCPolSet == null || mLCPolSet.size() <= 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AutoPayBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获得保单下有效的险种失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		String Sql = "SELECT * FROM LCPol WHERE ContNo='"
				+ "?a4?"
				+ "' and MainPolNo=PolNo and appflag=1 order by polno";
		 SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
	        sqlbv6.sql(tSql);
	        sqlbv6.put("a4", mLCContSchema.getContNo());
		LCPolSet rLCPolSet = tLCPolDB.executeQuery(sqlbv6);
		if (rLCPolSet.size() <= 0 || rLCPolSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AutoPayBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "查询主险号时产生错误！";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			mLCPolSchema = rLCPolSet.get(1);
		}
		
		return true;
	}

	private boolean terminateData(LCPolSchema tLCPolSchema,String tEdorNo) {
		tLCPolSchema = getPolToEnd(tLCPolSchema);
		mMap.put(tLCPolSchema.getSchema(), "UPDATE");
		// 生成保单终止通知书
//		LOPRTManagerSchema tLOPRTManagerSchema = setLOPRTManager(tLCPolSchema
//				.getPolNo(), PrintManagerBL.CODE_PEdorAPEND, tLaspDate,
//				tLCPolSchema,tEdorNo);
//		mLOPRTManagerSet.add(tLOPRTManagerSchema);
		return true;
	}

	/** 根据现金价值，大致估算下垫至日期
	 *  @param tCashValue 现价
	 *  @param tPreLoanMoney 需要自垫的金额
	 *  */
	private String getLoanToDate(LCPolSchema tLCPolSchema, double tCashValue,
			double tPreLoanMoney) {
		String tLoanToDate = "";
		// 算出上次交至日期
		String tCurPaytoDate = PubFun.calDate(tLCPolSchema.getPaytoDate(),
				tLCPolSchema.getPayIntv(), "M", "");
		int tIntv = PubFun.calInterval(tLCPolSchema.getPaytoDate(),
				tCurPaytoDate, "D");
		// 算出每天多少钱
		double tPerDayMoney = tPreLoanMoney / tIntv;
		double tMaxInv = tCashValue / tPerDayMoney;
		tLoanToDate = PubFun.calDate(tLCPolSchema.getPaytoDate(),
				(int) (tMaxInv), "D", "");
		return tLoanToDate;
	}

	/**
	 * 改变保单的状态（注：是保单级状态），变保单为自垫状态
	 * 
	 * @param tContNo
	 * @param tStateType
	 * @param tState
	 * @param tNewStateDate
	 * @return boolean true--成功，false--失败。结果放在mLCContStateSet变量中（累计）
	 */
	private boolean changeContState(String tContNo, String tStateType,
			String tState, String tNewStateDate, String tPolNo) {
		try {
			// 当前日期时间
			String tCurrentDate = PubFun.getCurrentDate();
			String tCurrentTime = PubFun.getCurrentTime();
			
			LCContStateSchema rLCContStateSchema = null;

			// 先查询当前状态是否是要改变的状态，如果是，则保持
			String tSql = "SELECT *" + " FROM LCContState" + " WHERE ContNo='"
					+ "?ContNo?" + "'" + " and PolNo='" + "?PolNo?" + "'"
					+ " and StateType='" + "?StateType?" + "'" + " and State='"
					+ "?State?" + "'" + " and EndDate is null";
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(tSql);
			sqlbv4.put("ContNo", tContNo);
			sqlbv4.put("PolNo", tPolNo);
			sqlbv4.put("StateType", tStateType);
			sqlbv4.put("State", tState);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv4);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				// 现在的状态就是要改变后的状态，所以，保持
				return true;
			}
			// 新状态信息
			rLCContStateSchema = new LCContStateSchema();
			rLCContStateSchema.setContNo(tContNo);
			rLCContStateSchema.setInsuredNo("000000");
			rLCContStateSchema.setPolNo(tPolNo);
			rLCContStateSchema.setStateType(tStateType);
			rLCContStateSchema.setState(tState);
			rLCContStateSchema.setStartDate(tNewStateDate);
			rLCContStateSchema.setOperator(mGlobalInput.Operator);
			rLCContStateSchema.setMakeDate(tCurrentDate);
			rLCContStateSchema.setMakeTime(tCurrentTime);
			rLCContStateSchema.setModifyDate(tCurrentDate);
			rLCContStateSchema.setModifyTime(tCurrentTime);
			mLCContStateSet.add(rLCContStateSchema);
			
			//注意更新以前就为还垫记录			
		   /* String tUpSQL="update LCContState set EndDate='" +PubFun.calDate(tNewStateDate, -1, "D", "")+ "' WHERE ContNo='"
			+ tContNo + "'" + " and PolNo='" + tPolNo + "'"
			+ " and StateType='" + tStateType + "'" + " and State='0'" + " and EndDate is null";*/
		    String tUpSQL="update LCContState set EndDate='" +"?EndDate?"+ "' WHERE ContNo='"
					+ "?ContNo?" + "'" + " and PolNo='" + "?PolNo?" + "'"
					+ " and StateType='" + "?StateType?" + "'" + " and State='0'" + " and EndDate is null";	
		    SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		    sqlbv5.sql(tUpSQL);
		    sqlbv5.put("EndDate", PubFun.calDate(tNewStateDate, -1, "D", ""));
		    sqlbv5.put("PolNo", tPolNo);
		    sqlbv5.put("ContNo", tContNo);
		    sqlbv5.put("StateType", tStateType);
		    mMap.put(sqlbv5, "UPDATE");
			return true;
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "AutoPayBL";
			tError.functionName = "changeContState";
			tError.errorMessage = "修改保单状态失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
	}
    /*
     *  查询暂交费表
     *  返回布尔值
     */
    private boolean queryTempFee(LJTempFeeSchema pLJTempFeeSchema)
    {
        String TempFeeNo = pLJTempFeeSchema.getTempFeeNo();
        String RiskCode = pLJTempFeeSchema.getRiskCode();
        if (TempFeeNo == null)
        {
            CError tError = new CError();
            tError.moduleName = "TempFeeBL";
            tError.functionName = "queryTempFee";
            tError.errorMessage = "暂交费号不能为空!";
            this.mErrors.addOneError(tError);
            return false;
        }
        //String sqlStr = "select * from LJTempFee where TempFeeNo='" + "?TempFeeNo?" + "'";
        String sqlStr = "select * from LJTempFee where TempFeeNo='" + "?TempFeeNo?" + "'";
        sqlStr = sqlStr + " and RiskCode='" + "?a11?" + "'";
        SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
        sqlbv7.sql(sqlStr);
        sqlbv7.put("TempFeeNo", TempFeeNo);
        sqlbv7.put("a11", RiskCode);
        logger.debug("查询暂交费表:" + sqlStr);
        LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
        LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv7);
        if (tLJTempFeeDB.mErrors.needDealError() == true)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLJTempFeeDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "TempFeeBL";
            tError.functionName = "queryLJTempFee";
            tError.errorMessage = "暂交费表查询失败!";
            this.mErrors.addOneError(tError);
            tLJTempFeeSet.clear();
            return false;
        }
        if (tLJTempFeeSet.size() > 0)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "TempFeeBL";
            tError.functionName = "queryLJTempFee";
            tError.errorMessage = "暂交费号为：" + TempFeeNo + " 的纪录已经存在！";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
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

	/**
	 * 测试主函数
	 * 
	 * @param arg
	 */
	public static void main(String[] arg) {
		// GlobalInput mGlobalInput = new GlobalInput();
		// mGlobalInput.ComCode = "86";
		// mGlobalInput.Operator = "001";
		//
		// VData tVData = new VData();
		// tVData.add(mGlobalInput);
		// tVData.add("2006-3-15");
		//		

		String mAutoPayDate = EdorCalZT.CalLapseDate("112207", "2009-09-18");
		// 此处算出来的是宽限期截止当日，自垫日期应为此日的下一天，因此这里加一
		mAutoPayDate = PubFun.calDate(mAutoPayDate, 1, "D", null);

		// AutoPayBL tAutoPayBL = new AutoPayBL();
		// tAutoPayBL.submitData(tVData, "");
	}
}

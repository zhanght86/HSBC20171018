package com.sinosoft.lis.get;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.lis.customer.FICustomerMain;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCInsureAccTraceDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LMDutyGetDB;
import com.sinosoft.lis.db.LMRiskBonusDB;
import com.sinosoft.lis.db.LMRiskInsuAccDB;
import com.sinosoft.lis.db.LOBonusAssignErrLogDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.InsuAccBala;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJABonusGetSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LJFIGetSchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.schema.LMDutyGetSchema;
import com.sinosoft.lis.schema.LMDutySchema;
import com.sinosoft.lis.schema.LMRiskInsuAccSchema;
import com.sinosoft.lis.schema.LOBonusPolSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.tb.CachedRiskInfo;
import com.sinosoft.lis.tb.PrepareBOMCalBL;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LMRiskInsuAccSet;
import com.sinosoft.lis.vschema.LOBonusPolSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>Title: </p>
 * <p>Description: 红利分配批处理</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author pst
 * @version 1.0
 */
public class BonusAssignBL {
private static Logger logger = Logger.getLogger(BonusAssignBL.class);

	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 错误处理类 */
	private CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private List mBomList = new ArrayList();
	/***/
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/**分红年度*/
	private int mFiscalYear;

	private Reflections tReFlection = new Reflections();

	private LCPolSchema mLCPolSchema = new LCPolSchema();

	private LOBonusPolSchema mLOBonusPolSchema = new LOBonusPolSchema();
	/**/
	private LCContSchema mLCContSchema = new LCContSchema();
	
	private LOBonusPolSet mLOBonusPolSet = new LOBonusPolSet();

	/** 打印管理表*/
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();

	/** 封装要操作的数据，以便一次提交 */
	private MMap mMap = new MMap();

	private String tCurMakeDate = PubFun.getCurrentDate();

	private String tCurMakeTime = PubFun.getCurrentTime();

	/*转换精确位数的对象   */
	private String FORMATMODOL = "0.00";//保费保额计算出来后的精确位数

	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL);//数字转换对象
	
	//本期红利领取方式	本期红利金额	红利延迟发放利息	本期红利本息合计	累积红利帐户余额（含本期）	本期红利抵交保险费金额	
	//本期红利购买增额交清的保额	红利分配后累计保额（含本期）
	
	//为了红利通知书准备数据，而字段又不够，采取拼串的方式记录即 PolNo+"-"+Data,将多张保单的数据存放在保单打印管理表的附属字段
	/**本期红利金额*/
	private	String tPolBonusMoney="";
	/**红利延迟发放利息*/
	private	String tPolBonusYCLX="";
	/**本期红利本息合计*/	
	private	String tPolSumBonus=""; 
	/**累积红利帐户余额（含本期）*/		
	private	String tPolSumAccBala="";
	/**本期红利抵交保险费金额*/		
	private	String tPolPayPrem="";
	/**本期红利购买增额交清的保额*/			
	private	String tPolGetAmnt="";
	/**红利分配后累计保额（含本期*/			
	private	String tPolSumGetAmnt="";

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
		mLOBonusPolSet = (LOBonusPolSet) mInputData
				.getObjectByObjectName("LOBonusPolSet", 0);
		mLCContSchema = (LCContSchema) mInputData
		.getObjectByObjectName("LCContSchema", 0);

		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		TransferData mTransferData = (TransferData) mInputData
				.getObjectByObjectName("TransferData", 0);
		String tFiscalYear = (String) mTransferData
				.getValueByName("FiscalYear");
		if (tFiscalYear == null || tFiscalYear.equals("")) {
			buildError("getInputData", "获得分红年度失败!");
			return false;
		}
		mFiscalYear = Integer.parseInt(tFiscalYear);


		return true;
	}

	/**
	 * 校验传入的数据的合法性目前只有主险
	 * 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		return true;
	}

	private boolean dealOnePolNo()
	{	

		String tLimit = PubFun.getNoLimit(mLCPolSchema.getManageCom());
		String tSerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);//产生流水号码
		String errDescribe = "";
		ExeSQL tExeSQL = new ExeSQL();
		String tPolNo = mLCPolSchema.getPolNo();
		//校验保单状态，如果是非自动垫交的分红险，保单失效时，并且被保险人死亡时不允许领取红利
//		String tFLag = getValiFlag(mLCPolSchema);
//		if ("0".equals(tFLag)) {
//			String tsql = "select polno from llclaimpolicy "
//					+ "where polno = '"
//					+ mLCPolSchema.getPolNo()
//					+ "' and getdutykind in('102','202') "
//					+ "and riskcode in(select riskcode from lmriskapp "
//					+ "where bonusflag = '1' and autopayflag = '0' and riskcode = '"
//					+ mLCPolSchema.getRiskCode() + "') ";
//			SSRS ttssrs = tExeSQL.execSQL(tsql);
//			if (ttssrs != null && ttssrs.getMaxRow() > 0) {
//				//纪录错误日志--往日志表中插入纪录
//				errDescribe = "保单" + tPolNo + "的状态为失效状态，暂不允许领取红利！";
//				insertErrLog(tSerialNo, tPolNo, errDescribe, mLCPolSchema
//						);
//				return false;
//			}
//		}

		int tDay = PubFun.calInterval(mLCPolSchema.getPaytoDate(),
				mLOBonusPolSchema.getSGetDate(), "D");
//		if (tDay > 0) {
//			//纪录错误日志--往日志表中插入纪录
//			errDescribe = mLOBonusPolSchema.getPolNo() + "保单的交至日期小于红利应分配日期!";
//			insertErrLog(tSerialNo, tPolNo, errDescribe, mLCPolSchema
//					);
//			return false;
//		}
//		tCurMakeDate="2015-07-01";
		int tDay2 = PubFun.calInterval(mLOBonusPolSchema.getSGetDate(),
				tCurMakeDate, "D");
		if (tDay2 < 0) {
			//纪录错误日志--往日志表中插入纪录
			errDescribe = mLOBonusPolSchema.getPolNo() + "未到红利应分配日期，不能分配该保单红利!";
			 // 日志监控,过程监控
			PubFun.logTrack (mGlobalInput,mLCPolSchema.getContNo(),"保单"+mLCPolSchema.getContNo()+"未到红利应分配日期，不能分配该保单红利!");
			insertErrLog(tSerialNo, tPolNo, errDescribe, mLCPolSchema
					);
			return false;
		}		
		logger.debug("BonusGetMode:" + mLCPolSchema.getBonusGetMode());
		//以保单表的红利方式为准，分配后会进行重置
		mLOBonusPolSchema.setBonusGetMode(mLCPolSchema.getBonusGetMode());
		
		if (mLOBonusPolSchema.getBonusGetMode() == null) {
			mLOBonusPolSchema.setBonusGetMode("1");
		}
		if (mLOBonusPolSchema.getBonusGetMode().trim().equals("")) {
			mLOBonusPolSchema.setBonusGetMode("1");
		}


		//缴费期满，则将红利的领取方式由抵交保费转为累计升息
		int Day = PubFun.calInterval(mLCPolSchema.getPayEndDate(),
				mLOBonusPolSchema.getSGetDate(), "D");
		if (Day > 0 && "3".equals(mLOBonusPolSchema.getBonusGetMode())) {
			mLOBonusPolSchema.setBonusGetMode("1");

			String strSQL = "update LOBonusPol set BonusGetMode='1', ModifyDate='?tCurMakeDate?' , ModifyTime='?tCurMakeTime?'"
					+ " where PolNo='?PolNo?'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(strSQL);
			sqlbv.put("tCurMakeDate", tCurMakeDate);
			sqlbv.put("tCurMakeTime", tCurMakeTime);
			sqlbv.put("PolNo", mLCPolSchema.getPolNo());
			String strPolSQL = "update LCPol set BonusGetMode='1', ModifyDate='?tCurMakeDate?' , ModifyTime='?tCurMakeTime?'"
					+ " where PolNo='?PolNo?'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(strPolSQL);
			sqlbv1.put("tCurMakeDate", tCurMakeDate);
			sqlbv1.put("tCurMakeTime", tCurMakeTime);
			sqlbv1.put("PolNo", mLCPolSchema.getPolNo());
			mMap.put(sqlbv, "UPDATE");
			mMap.put(sqlbv1, "UPDATE");
		}
		//如果红利领取方式是现金
		if (mLOBonusPolSchema.getBonusGetMode().equals("2")) {		 
			
			  //日志监控,过程监控
			PubFun.logTrack(mGlobalInput, mLCPolSchema.getContNo(), "保单"+mLCPolSchema.getContNo()+"红利领取方式为现金领取");
			
			if (getByCash(mLCPolSchema, String.valueOf(mLOBonusPolSchema
					.getBonusMoney()), tSerialNo, mFiscalYear,
					mLOBonusPolSchema) == false) {
				//纪录错误日志--往日志表中插入纪录
				errDescribe = "getByCash函数内部处理错误:" + mErrors.getFirstError()
						+ "";
				if (insertErrLog(tSerialNo, tPolNo, errDescribe, mLCPolSchema
						) == false)
				{
					return false;
				}
					return false;
			}
		}
		//如果红利领取方式是抵交续期保费
		if (mLOBonusPolSchema.getBonusGetMode().equals("3")) {
			  //日志监控,过程监控
			PubFun.logTrack(mGlobalInput, mLCPolSchema.getContNo(), "保单"+mLCPolSchema.getContNo()+"红利领取方式为抵交续期保费");
			if (getByRePay(mLCPolSchema, String.valueOf(mLOBonusPolSchema
					.getBonusMoney()), tSerialNo, mFiscalYear,
					mLOBonusPolSchema) == false) {
				//纪录错误日志--往日志表中插入纪录
				errDescribe = "getByRePay函数内部处理错误:" + mErrors.getFirstError()
						+ "";
				if (insertErrLog(tSerialNo, tPolNo, errDescribe, mLCPolSchema
						) == false)
				{
					return false;
				}
					return false;
			}
		}
		//如果红利领取方式是累计生息
		if (mLOBonusPolSchema.getBonusGetMode().equals("1")) {
			  //日志监控,过程监控
			PubFun.logTrack(mGlobalInput, mLCPolSchema.getContNo(), "保单"+mLCPolSchema.getContNo()+"红利领取方式为累计生息");
			if (getBySumInterest(mLCPolSchema, String.valueOf(mLOBonusPolSchema
					.getBonusMoney()), tSerialNo, mFiscalYear,
					mLOBonusPolSchema) == false) {
				//纪录错误日志--往日志表中插入纪录
				errDescribe = "getBySumInterest函数内部处理错误:"
						+ mErrors.getFirstError() + "";
				if (insertErrLog(tSerialNo, tPolNo, errDescribe, mLCPolSchema
						) == false)
				{
					return false;
				}
				return false;
			}
		}
		//如果红利领取方式是增额交清
		if (mLOBonusPolSchema.getBonusGetMode().equals("5")) {
			 //日志监控,过程监控
			PubFun.logTrack(mGlobalInput, mLCPolSchema.getContNo(), "保单"+mLCPolSchema.getContNo()+"红利领取方式为增额交清");
			if (getByAddPrem(mLCPolSchema, String.valueOf(mLOBonusPolSchema
					.getBonusMoney()), tSerialNo, mFiscalYear,
					mLOBonusPolSchema) == false) {
				//纪录错误日志--往日志表中插入纪录
				errDescribe = "getByAddPrem函数内部处理错误:" + mErrors.getFirstError()
						+ "";
				if (insertErrLog(tSerialNo, tPolNo, errDescribe, mLCPolSchema
						) == false)
				{
					return false;
				}
					return false;
			}
		}else
		{
		   tPolSumGetAmnt+=mLOBonusPolSchema.getPolNo()+"-"+PubFun.round(mLCPolSchema.getAmnt(),2)+"|";
		}

		return true;
	
	}
	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @return
	 */
	private boolean dealData() {
		//本期红利领取方式	本期红利金额	红利延迟发放利息	本期红利本息合计	累积红利帐户余额（含本期）	本期红利抵交保险费金额	
		//本期红利购买增额交清的保额	红利分配后累计保额（含本期）

		for(int k=1;k<=mLOBonusPolSet.size();k++)
		{
			mLOBonusPolSchema=mLOBonusPolSet.get(k);
			
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(mLOBonusPolSchema.getPolNo());
			if (tLCPolDB.getInfo() == false) {
				buildError("getInputData", "获得保单数据失败!");
				return false;
			}
			mLCPolSchema = tLCPolDB.getSchema();
			//本期红利金额
			tPolBonusMoney+=mLOBonusPolSchema.getPolNo()+"-"+mLOBonusPolSchema.getBonusMoney()+"|";
			//红利延迟发放利息
			tPolBonusYCLX+=mLOBonusPolSchema.getPolNo()+"-"+mLOBonusPolSchema.getBonusInterest()+"|";
			//本期红利本息合计
			tPolSumBonus+=mLOBonusPolSchema.getPolNo()+"-"+PubFun.round(mLOBonusPolSchema.getBonusMoney()+mLOBonusPolSchema.getBonusInterest(),2)+"|";

			if(!dealOnePolNo())
			{
				return false;			
			}
		}
		 if(!getPrintData(mLCContSchema,mFiscalYear,
					tPolBonusMoney,tPolBonusYCLX,tPolSumBonus,tPolSumAccBala,tPolPayPrem,tPolGetAmnt,tPolSumGetAmnt))
			{
					  return false;
		}
		mMap.put(mLOPRTManagerSet, "DELETE&INSERT");
		
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	/**
	 * 红利给付方式为领取现金时的处理--向实付总表插入一条纪录,更新
	 * @param tLCPolSchema
	 * @param tBonusMoney
	 * @return
	 */
	private boolean getByCash(LCPolSchema tLCPolSchema, String tBonusMoney,
			String tSNo, int tBonusYear, LOBonusPolSchema tLOBonusPolSchema) {

		//1-看红利帐户是否存在，如果没有生成,还要处理 Class 表
		LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		tLCInsureAccDB.setPolNo(tLCPolSchema.getPolNo());
		tLCInsureAccDB.setAccType("008"); //帐户类型为现金领取红利帐户
		LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.query();
        
		double tSumBonus=Double.parseDouble(tBonusMoney)+tLOBonusPolSchema.getBonusInterest();
		
		if (tLCInsureAccSet.size() == 0) {
			//效率做改进
			LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
			tLMRiskInsuAccDB.setAccType("008");
			LMRiskInsuAccSet tLMRiskInsuAccSet = tLMRiskInsuAccDB.query();
			if (tLMRiskInsuAccSet.size() == 0) {
				this.mErrors.copyAllErrors(tLMRiskInsuAccDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "BonusAssignBL";
				tError.functionName = "getBySumInterest";
				tError.errorMessage = "查询LMRiskInsuAcc表中帐户类型为004时失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			LMRiskInsuAccSchema tLMRiskInsuAccSchema = tLMRiskInsuAccSet.get(1);
			tLCInsureAccSchema = new LCInsureAccSchema();
			tReFlection.transFields(tLCInsureAccSchema, tLCPolSchema);
			tLCInsureAccSchema.setPolNo(tLCPolSchema.getPolNo());
			tLCInsureAccSchema
					.setInsuAccNo(tLMRiskInsuAccSchema.getInsuAccNo());
			tLCInsureAccSchema.setRiskCode(tLCPolSchema.getRiskCode());
			tLCInsureAccSchema.setAccType("008");
			tLCInsureAccSchema.setContNo(tLCPolSchema.getContNo());
			tLCInsureAccSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
			tLCInsureAccSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
			tLCInsureAccSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			tLCInsureAccSchema.setPrtNo(tLCPolSchema.getPrtNo());
			tLCInsureAccSchema.setSumPay(0);
			tLCInsureAccSchema.setInsuAccBala(tSumBonus);
			tLCInsureAccSchema.setUnitCount(0);
			tLCInsureAccSchema.setInsuAccGetMoney(tSumBonus);
			tLCInsureAccSchema.setFrozenMoney(0);
			tLCInsureAccSchema.setAccComputeFlag(tLMRiskInsuAccSchema
					.getAccComputeFlag());
			tLCInsureAccSchema.setManageCom(tLCPolSchema.getManageCom());
			tLCInsureAccSchema.setOperator(mGlobalInput.Operator);
			tLCInsureAccSchema.setBalaDate(tLOBonusPolSchema.getSGetDate());
			tLCInsureAccSchema.setBalaTime("00:00:00");
			tLCInsureAccSchema.setMakeDate(tCurMakeDate);
			tLCInsureAccSchema.setMakeTime(tCurMakeTime);
			tLCInsureAccSchema.setModifyDate(tCurMakeDate);
			tLCInsureAccSchema.setModifyTime(tCurMakeTime);
			//第一次为其建立账户即插入账户表
			mMap.put(tLCInsureAccSchema, "DELETE&INSERT");
		} else {
			tLCInsureAccSchema = tLCInsureAccSet.get(1);
			tLCInsureAccSchema.setInsuAccBala(tLCInsureAccSchema
					.getInsuAccBala()
					+ tSumBonus);
			tLCInsureAccSchema.setInsuAccGetMoney(tLCInsureAccSchema
					.getInsuAccGetMoney()
					+ tSumBonus);
			tLCInsureAccSchema.setLastAccBala(tLCInsureAccSchema
					.getInsuAccBala());
			tLCInsureAccSchema.setBalaDate(tLOBonusPolSchema.getSGetDate());
			tLCInsureAccSchema.setModifyDate(tCurMakeDate);
			tLCInsureAccSchema.setModifyTime(tCurMakeTime);
			mMap.put(tLCInsureAccSchema, "UPDATE");
		}

		//处理 Class 表
		LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();
		LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
		tLCInsureAccClassDB.setPolNo(tLCPolSchema.getPolNo());
		tLCInsureAccClassDB.setAccType("008"); //帐户类型为红利帐户
		LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB.query();

		if (tLCInsureAccClassSet.size() == 0) {
			//效率做改进
			LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
			tLMRiskInsuAccDB.setAccType("008");
			LMRiskInsuAccSet tLMRiskInsuAccSet = tLMRiskInsuAccDB.query();
			if (tLMRiskInsuAccSet.size() == 0) {
				this.mErrors.copyAllErrors(tLMRiskInsuAccDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "BonusAssignBL";
				tError.functionName = "getBySumInterest";
				tError.errorMessage = "查询LMRiskInsuAcc表中帐户类型为004时失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			LMRiskInsuAccSchema tLMRiskInsuAccSchema = tLMRiskInsuAccSet.get(1);
			tLCInsureAccClassSchema = new LCInsureAccClassSchema();
			tReFlection.transFields(tLCInsureAccClassSchema, tLCPolSchema);
			tLCInsureAccClassSchema.setPolNo(tLCPolSchema.getPolNo());
			tLCInsureAccClassSchema.setInsuAccNo(tLMRiskInsuAccSchema
					.getInsuAccNo());
			tLCInsureAccClassSchema.setRiskCode(tLCPolSchema.getRiskCode());
			tLCInsureAccClassSchema.setAccType("008");
			tLCInsureAccClassSchema.setContNo(tLCPolSchema.getContNo());
			tLCInsureAccClassSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
			tLCInsureAccClassSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
			tLCInsureAccClassSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			tLCInsureAccClassSchema.setOtherNo(tLCPolSchema.getPolNo());
			tLCInsureAccClassSchema.setPayPlanCode("000000");
			tLCInsureAccClassSchema.setAccAscription("1");
			tLCInsureAccClassSchema.setOtherType("1");
			tLCInsureAccClassSchema.setSumPay(0);
			tLCInsureAccClassSchema.setInsuAccBala(tSumBonus);
			tLCInsureAccClassSchema.setUnitCount(0);
			tLCInsureAccClassSchema.setInsuAccGetMoney(tSumBonus);
			tLCInsureAccClassSchema.setFrozenMoney(0);
			tLCInsureAccClassSchema.setAccComputeFlag(tLMRiskInsuAccSchema
					.getAccComputeFlag());
			tLCInsureAccClassSchema.setManageCom(tLCPolSchema.getManageCom());
			tLCInsureAccClassSchema.setOperator(mGlobalInput.Operator);
			tLCInsureAccClassSchema
					.setBalaDate(tLOBonusPolSchema.getSGetDate());
			tLCInsureAccClassSchema.setBalaTime("00:00:00");
			tLCInsureAccClassSchema.setMakeDate(tCurMakeDate);
			tLCInsureAccClassSchema.setMakeTime(tCurMakeTime);
			tLCInsureAccClassSchema.setModifyDate(tCurMakeDate);
			tLCInsureAccClassSchema.setModifyTime(tCurMakeTime);
			//第一次为其建立账户即插入账户表
			mMap.put(tLCInsureAccClassSchema, "DELETE&INSERT");
		} else {
			tLCInsureAccClassSchema = tLCInsureAccClassSet.get(1);
			tLCInsureAccClassSchema.setInsuAccBala(tLCInsureAccClassSchema
					.getInsuAccBala()
					+ tSumBonus);
			tLCInsureAccClassSchema.setInsuAccGetMoney(tLCInsureAccClassSchema
					.getInsuAccGetMoney()
					+ tSumBonus);
			tLCInsureAccClassSchema.setLastAccBala(tLCInsureAccClassSchema
					.getInsuAccBala());
			tLCInsureAccClassSchema.setBalaDate(tLOBonusPolSchema.getSGetDate());			
			tLCInsureAccClassSchema.setModifyDate(tCurMakeDate);
			tLCInsureAccClassSchema.setModifyTime(tCurMakeTime);
			mMap.put(tLCInsureAccClassSchema, "UPDATE");
		}

		String tLimit = PubFun.getNoLimit(tLCPolSchema.getManageCom());
		String tSerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);//产生流水号码

		//填充保险帐户表记价履历表
		LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
		tReFlection.transFields(tLCInsureAccTraceSchema, tLCInsureAccClassSchema);
		tLCInsureAccTraceSchema.setSerialNo(tSerialNo);
		tLCInsureAccTraceSchema.setPolNo(tLCInsureAccSchema.getPolNo());
		tLCInsureAccTraceSchema.setMoneyType("HL");  
		tLCInsureAccTraceSchema.setRiskCode(tLCInsureAccSchema.getRiskCode());
		tLCInsureAccTraceSchema.setOtherNo(tLCInsureAccSchema.getPolNo());
		tLCInsureAccTraceSchema.setOtherType("1");
		tLCInsureAccTraceSchema.setAccAscription("1");
		tLCInsureAccTraceSchema.setMoney(tBonusMoney);
		tLCInsureAccTraceSchema.setContNo(tLCInsureAccSchema.getContNo());
		tLCInsureAccTraceSchema.setGrpPolNo(tLCInsureAccSchema.getGrpPolNo());
		tLCInsureAccTraceSchema.setGrpContNo(tLCInsureAccSchema.getGrpContNo());
		tLCInsureAccTraceSchema.setInsuAccNo(tLCInsureAccSchema.getInsuAccNo());
		tLCInsureAccTraceSchema.setState(tLCInsureAccSchema.getState());
		tLCInsureAccTraceSchema.setManageCom(tLCInsureAccSchema.getManageCom());
		tLCInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
		tLCInsureAccTraceSchema.setMakeDate(tCurMakeDate);
		tLCInsureAccTraceSchema.setMakeTime("00:00:00");
		tLCInsureAccTraceSchema.setModifyDate(tCurMakeDate);
		tLCInsureAccTraceSchema.setModifyTime(tCurMakeTime);
		tLCInsureAccTraceSchema.setPayDate(tLOBonusPolSchema.getSGetDate());
		tLCInsureAccTraceSchema.setFeeCode("000000");

		mMap.put(tLCInsureAccTraceSchema, "DELETE&INSERT");
		
		//计算延迟利息
		if(tLOBonusPolSchema.getBonusInterest()>0)
		{
			LCInsureAccTraceSchema rLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
			String rLimit = PubFun.getNoLimit(tLCPolSchema.getManageCom());
			String rSerialNo = PubFun1.CreateMaxNo("SERIALNO", rLimit);//产生流水号码
			tReFlection.transFields(rLCInsureAccTraceSchema, tLCInsureAccClassSchema);
			rLCInsureAccTraceSchema.setSerialNo(rSerialNo);
			rLCInsureAccTraceSchema.setPolNo(tLCInsureAccSchema.getPolNo());
			rLCInsureAccTraceSchema.setMoneyType("YCLX"); //延迟利息??
			rLCInsureAccTraceSchema.setRiskCode(tLCInsureAccSchema.getRiskCode());
			rLCInsureAccTraceSchema.setOtherNo(tLCInsureAccSchema.getPolNo());
			rLCInsureAccTraceSchema.setOtherType("1");
			rLCInsureAccTraceSchema.setAccAscription("1");
			rLCInsureAccTraceSchema.setMoney(tLOBonusPolSchema.getBonusInterest());
			rLCInsureAccTraceSchema.setContNo(tLCInsureAccSchema.getContNo());
			rLCInsureAccTraceSchema.setGrpContNo(tLCInsureAccSchema.getGrpContNo());
			rLCInsureAccTraceSchema.setGrpPolNo(tLCInsureAccSchema.getGrpPolNo());
			rLCInsureAccTraceSchema.setInsuAccNo(tLCInsureAccSchema.getInsuAccNo());
			rLCInsureAccTraceSchema.setState(tLCInsureAccSchema.getState());
			rLCInsureAccTraceSchema.setManageCom(tLCInsureAccSchema.getManageCom());
			rLCInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
			rLCInsureAccTraceSchema.setMakeDate(tCurMakeDate);
			rLCInsureAccTraceSchema.setMakeTime(tCurMakeTime);
			rLCInsureAccTraceSchema.setModifyDate(tCurMakeDate);
			rLCInsureAccTraceSchema.setModifyTime(tCurMakeTime);
			rLCInsureAccTraceSchema.setPayDate(tLOBonusPolSchema.getSGetDate());
			rLCInsureAccTraceSchema.setFeeCode("000000");
			mMap.put(rLCInsureAccTraceSchema, "DELETE&INSERT");
		}

		//累计生息领取时 是否判断并自动领取延迟利息？？
		//保单红利表更新
		String strSQL = "update LOBonusPol set AGetDate='?tCurMakeDate?' , BonusFlag='1',BonusGetMode='?BonusGetMode?'";
		strSQL = strSQL + " , ModifyDate='?tCurMakeDate?' , ModifyTime='?tCurMakeTime?'";
		strSQL = strSQL + " where PolNo='?PolNo?' and FiscalYear=?tBonusYear?";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(strSQL);
		sqlbv2.put("tCurMakeDate", tCurMakeDate);
		sqlbv2.put("BonusGetMode", mLCPolSchema.getBonusGetMode());
		sqlbv2.put("tCurMakeTime", tCurMakeTime);
		sqlbv2.put("PolNo", tLCPolSchema.getPolNo());
		sqlbv2.put("tBonusYear", tBonusYear);

		mMap.put(sqlbv2, "UPDATE");


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
	private boolean getByRePay(LCPolSchema tLCPolSchema, String tBonusMoney,
			String tSNo, int tBonusYear, LOBonusPolSchema tLOBonusPolSchema) {

		//1-看红利帐户是否存在，如果没有生成,还要处理 Class 表
		LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		tLCInsureAccDB.setPolNo(tLCPolSchema.getPolNo());
		tLCInsureAccDB.setAccType("007"); //帐户类型为抵交保费帐户，但不计息
		LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.query();
		double tSumBonus=Double.parseDouble(tBonusMoney);
		if (tLCInsureAccSet.size() == 0) {
			//效率做改进
			LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
			tLMRiskInsuAccDB.setAccType("007");
			LMRiskInsuAccSet tLMRiskInsuAccSet = tLMRiskInsuAccDB.query();
			if (tLMRiskInsuAccSet.size() == 0) {
				this.mErrors.copyAllErrors(tLMRiskInsuAccDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "BonusAssignBL";
				tError.functionName = "getBySumInterest";
				tError.errorMessage = "查询LMRiskInsuAcc表中帐户类型为004时失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			LMRiskInsuAccSchema tLMRiskInsuAccSchema = tLMRiskInsuAccSet.get(1);
			tLCInsureAccSchema = new LCInsureAccSchema();
			tReFlection.transFields(tLCInsureAccSchema, tLCPolSchema);
			tLCInsureAccSchema.setPolNo(tLCPolSchema.getPolNo());
			tLCInsureAccSchema
					.setInsuAccNo(tLMRiskInsuAccSchema.getInsuAccNo());
			tLCInsureAccSchema.setRiskCode(tLCPolSchema.getRiskCode());
			tLCInsureAccSchema.setAccType("007");
			tLCInsureAccSchema.setContNo(tLCPolSchema.getContNo());
			tLCInsureAccSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
			tLCInsureAccSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			tLCInsureAccSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
			tLCInsureAccSchema.setPrtNo(tLCPolSchema.getPrtNo());
			tLCInsureAccSchema.setSumPay(0);
			tLCInsureAccSchema.setInsuAccBala(tSumBonus);
			tLCInsureAccSchema.setUnitCount(0);
			tLCInsureAccSchema.setInsuAccGetMoney(tSumBonus);
			tLCInsureAccSchema.setFrozenMoney(0);
			tLCInsureAccSchema.setAccComputeFlag(tLMRiskInsuAccSchema
					.getAccComputeFlag());
			tLCInsureAccSchema.setManageCom(tLCPolSchema.getManageCom());
			tLCInsureAccSchema.setOperator(mGlobalInput.Operator);
			tLCInsureAccSchema.setBalaDate(tLOBonusPolSchema.getSGetDate());
			tLCInsureAccSchema.setBalaTime("00:00:00");
			tLCInsureAccSchema.setMakeDate(tCurMakeDate);
			tLCInsureAccSchema.setMakeTime(tCurMakeTime);
			tLCInsureAccSchema.setModifyDate(tCurMakeDate);
			tLCInsureAccSchema.setModifyTime(tCurMakeTime);
			//第一次为其建立账户即插入账户表
			mMap.put(tLCInsureAccSchema, "DELETE&INSERT");
		} else {
			tLCInsureAccSchema = tLCInsureAccSet.get(1);
			tLCInsureAccSchema.setInsuAccBala(tLCInsureAccSchema
					.getInsuAccBala()
					+ tSumBonus);
			tLCInsureAccSchema.setInsuAccGetMoney(tLCInsureAccSchema
					.getInsuAccGetMoney()
					+ tSumBonus);
			tLCInsureAccSchema.setLastAccBala(tLCInsureAccSchema
					.getInsuAccBala());
			tLCInsureAccSchema.setBalaDate(tLOBonusPolSchema.getSGetDate());
			tLCInsureAccSchema.setBalaTime("00:00:00");
			tLCInsureAccSchema.setModifyDate(tCurMakeDate);
			tLCInsureAccSchema.setModifyTime(tCurMakeTime);
			mMap.put(tLCInsureAccSchema, "UPDATE");
		}

		//处理 Class 表
		LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();
		LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
		tLCInsureAccClassDB.setPolNo(tLCPolSchema.getPolNo());
		tLCInsureAccClassDB.setAccType("007"); //帐户类型为红利帐户
		LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB.query();

		if (tLCInsureAccClassSet.size() == 0) {
			//效率做改进
			LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
			tLMRiskInsuAccDB.setAccType("007");
			LMRiskInsuAccSet tLMRiskInsuAccSet = tLMRiskInsuAccDB.query();
			if (tLMRiskInsuAccSet.size() == 0) {
				this.mErrors.copyAllErrors(tLMRiskInsuAccDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "BonusAssignBL";
				tError.functionName = "getBySumInterest";
				tError.errorMessage = "查询LMRiskInsuAcc表中帐户类型为004时失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			LMRiskInsuAccSchema tLMRiskInsuAccSchema = tLMRiskInsuAccSet.get(1);
			tLCInsureAccClassSchema = new LCInsureAccClassSchema();
			tReFlection.transFields(tLCInsureAccClassSchema, tLCPolSchema);			
			tLCInsureAccClassSchema.setPolNo(tLCPolSchema.getPolNo());
			tLCInsureAccClassSchema.setInsuAccNo(tLMRiskInsuAccSchema
					.getInsuAccNo());
			tLCInsureAccClassSchema.setRiskCode(tLCPolSchema.getRiskCode());
			tLCInsureAccClassSchema.setAccType("007");
			tLCInsureAccClassSchema.setContNo(tLCPolSchema.getContNo());
			tLCInsureAccClassSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
			tLCInsureAccClassSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
			tLCInsureAccClassSchema.setPayPlanCode("000000");
			tLCInsureAccClassSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			tLCInsureAccClassSchema.setOtherNo(tLCPolSchema.getPolNo());
			tLCInsureAccClassSchema.setOtherType("1");
			tLCInsureAccClassSchema.setAccAscription("1");
			tLCInsureAccClassSchema.setSumPay(0);
			tLCInsureAccClassSchema.setInsuAccBala(tSumBonus);
			tLCInsureAccClassSchema.setUnitCount(0);
			tLCInsureAccClassSchema.setInsuAccGetMoney(tSumBonus);
			tLCInsureAccClassSchema.setFrozenMoney(0);
			tLCInsureAccClassSchema.setAccComputeFlag(tLMRiskInsuAccSchema
					.getAccComputeFlag());
			tLCInsureAccClassSchema.setManageCom(tLCPolSchema.getManageCom());
			tLCInsureAccClassSchema.setOperator(mGlobalInput.Operator);
			tLCInsureAccClassSchema
					.setBalaDate(tLOBonusPolSchema.getSGetDate());
			tLCInsureAccClassSchema.setBalaTime("00:00:00");
			tLCInsureAccClassSchema.setMakeDate(tCurMakeDate);
			tLCInsureAccClassSchema.setMakeTime(tCurMakeTime);
			tLCInsureAccClassSchema.setModifyDate(tCurMakeDate);
			tLCInsureAccClassSchema.setModifyTime(tCurMakeTime);
			//第一次为其建立账户即插入账户表
			mMap.put(tLCInsureAccClassSchema, "DELETE&INSERT");
		} else {
			tLCInsureAccClassSchema = tLCInsureAccClassSet.get(1);
			tLCInsureAccClassSchema.setInsuAccBala(tLCInsureAccClassSchema
					.getInsuAccBala()
					+ tSumBonus);
			tLCInsureAccClassSchema.setInsuAccGetMoney(tLCInsureAccClassSchema
					.getInsuAccGetMoney()
					+ tSumBonus);
			tLCInsureAccClassSchema.setLastAccBala(tLCInsureAccClassSchema
					.getInsuAccBala());
			tLCInsureAccClassSchema.setBalaDate(tLOBonusPolSchema.getSGetDate());
			tLCInsureAccClassSchema.setBalaTime("00:00:00");
			tLCInsureAccClassSchema.setModifyDate(tCurMakeDate);
			tLCInsureAccClassSchema.setModifyTime(tCurMakeTime);
			mMap.put(tLCInsureAccClassSchema, "UPDATE");
		}

		String tLimit = PubFun.getNoLimit(tLCPolSchema.getManageCom());
		String tSerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);//产生流水号码

		//填充保险帐户表记价履历表
		LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
		tReFlection.transFields(tLCInsureAccTraceSchema, tLCInsureAccClassSchema);		
		tLCInsureAccTraceSchema.setSerialNo(tSerialNo);
		tLCInsureAccTraceSchema.setPolNo(tLCInsureAccSchema.getPolNo());
		tLCInsureAccTraceSchema.setMoneyType("HL");
		tLCInsureAccTraceSchema.setRiskCode(tLCInsureAccSchema.getRiskCode());
		tLCInsureAccTraceSchema.setOtherNo(tLCInsureAccSchema.getPolNo());
		tLCInsureAccTraceSchema.setOtherType("1");
		tLCInsureAccTraceSchema.setAccAscription("1");
		tLCInsureAccTraceSchema.setMoney(tBonusMoney);
		tLCInsureAccTraceSchema.setContNo(tLCInsureAccSchema.getContNo());
		tLCInsureAccTraceSchema.setGrpPolNo(tLCInsureAccSchema.getGrpPolNo());
		tLCInsureAccTraceSchema.setGrpContNo(tLCInsureAccSchema.getGrpContNo());
		tLCInsureAccTraceSchema.setInsuAccNo(tLCInsureAccSchema.getInsuAccNo());
		tLCInsureAccTraceSchema.setState(tLCInsureAccSchema.getState());
		tLCInsureAccTraceSchema.setManageCom(tLCInsureAccSchema.getManageCom());
		tLCInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
		tLCInsureAccTraceSchema.setMakeDate(tCurMakeDate);
		tLCInsureAccTraceSchema.setMakeTime("00:00:00");
		tLCInsureAccTraceSchema.setModifyDate(tCurMakeDate);
		tLCInsureAccTraceSchema.setModifyTime(tCurMakeTime);
		tLCInsureAccTraceSchema.setPayDate(tLOBonusPolSchema.getSGetDate());
		tLCInsureAccTraceSchema.setFeeCode("000000");

		mMap.put(tLCInsureAccTraceSchema, "DELETE&INSERT");

		//本期红利抵交保险费金额
		tPolPayPrem+=mLOBonusPolSchema.getPolNo()+"-"+PubFun.round(mLOBonusPolSchema.getBonusMoney(),2)+"|";
		
		String strSQL = "update LOBonusPol set AGetDate='?tCurMakeDate?', BonusFlag='1',BonusGetMode='?BonusGetMode?'";
        strSQL = strSQL + " , ModifyDate='?tCurMakeDate?' , ModifyTime='?tCurMakeTime?'";
         strSQL = strSQL + " where PolNo='?PolNo?' and FiscalYear=?tBonusYear?";
         SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
         sqlbv3.sql(strSQL);
         sqlbv3.put("tCurMakeDate", tCurMakeDate);
         sqlbv3.put("BonusGetMode", mLCPolSchema.getBonusGetMode());
         sqlbv3.put("tCurMakeTime", tCurMakeTime);
         sqlbv3.put("PolNo", tLCPolSchema.getPolNo());
         sqlbv3.put("tBonusYear", tBonusYear);

        /**保单红利表更新**/
        mMap.put(sqlbv3, "UPDATE");



		return true;
	}

	/**
	 * 红利用于累计生息
	 * @param tLCPolSchema
	 * @param tBonusMoney
	 * @param tSNo
	 * @param tBonusYear
	 * @return
	 */
	private boolean getBySumInterest(LCPolSchema tLCPolSchema,
			String tBonusMoney, String tSNo, int tBonusYear,
			LOBonusPolSchema tLOBonusPolSchema) {
		//1-看红利帐户是否存在，如果没有生成,还要处理 Class 表
		LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		tLCInsureAccDB.setPolNo(tLCPolSchema.getPolNo());
		tLCInsureAccDB.setAccType("004"); //帐户类型为红利帐户
		LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.query();
		double tSumBonus=Double.parseDouble(tBonusMoney);
		
		double tInterest=0.0, mSumGetMoney=0.0;
		if (tLCInsureAccSet.size() == 0) {
			//效率做改进
			LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
			tLMRiskInsuAccDB.setAccType("004");
			LMRiskInsuAccSet tLMRiskInsuAccSet = tLMRiskInsuAccDB.query();
			if (tLMRiskInsuAccSet.size() == 0) {
				this.mErrors.copyAllErrors(tLMRiskInsuAccDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "BonusAssignBL";
				tError.functionName = "getBySumInterest";
				tError.errorMessage = "查询LMRiskInsuAcc表中帐户类型为004时失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			LMRiskInsuAccSchema tLMRiskInsuAccSchema = tLMRiskInsuAccSet.get(1);
			tLCInsureAccSchema = new LCInsureAccSchema();
			tReFlection.transFields(tLCInsureAccSchema, tLCPolSchema);	
			tLCInsureAccSchema.setPolNo(tLCPolSchema.getPolNo());
			tLCInsureAccSchema
					.setInsuAccNo(tLMRiskInsuAccSchema.getInsuAccNo());
			tLCInsureAccSchema.setRiskCode(tLCPolSchema.getRiskCode());
			tLCInsureAccSchema.setAccType("004");
			tLCInsureAccSchema.setContNo(tLCPolSchema.getContNo());
			tLCInsureAccSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
			tLCInsureAccSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
			tLCInsureAccSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			tLCInsureAccSchema.setPrtNo(tLCPolSchema.getPrtNo());
			tLCInsureAccSchema.setSumPay(0);
			tLCInsureAccSchema.setInsuAccBala(tBonusMoney);
			tLCInsureAccSchema.setUnitCount(0);
			tLCInsureAccSchema.setInsuAccGetMoney(tBonusMoney);
			tLCInsureAccSchema.setFrozenMoney(0);
			tLCInsureAccSchema.setAccComputeFlag(tLMRiskInsuAccSchema
					.getAccComputeFlag());
			tLCInsureAccSchema.setManageCom(tLCPolSchema.getManageCom());
			tLCInsureAccSchema.setOperator(mGlobalInput.Operator);
			tLCInsureAccSchema.setBalaDate(tLOBonusPolSchema.getSGetDate());
			tLCInsureAccSchema.setBalaTime("00:00:00");
			tLCInsureAccSchema.setMakeDate(tCurMakeDate);
			tLCInsureAccSchema.setMakeTime(tCurMakeTime);
			tLCInsureAccSchema.setModifyDate(tCurMakeDate);
			tLCInsureAccSchema.setModifyTime(tCurMakeTime);
			//第一次为其建立账户即插入账户表
			mMap.put(tLCInsureAccSchema, "DELETE&INSERT");
		} else {
			tLCInsureAccSchema = tLCInsureAccSet.get(1);
			
			
			//comment by jiaqiangli 2009-11-16 分两个分支进行判断处理 重要的是累计帐户总额会打到通知书上
			//判断此次分红时之前是否有帐户未结算的情况,如果有这需要对此笔红利进行扩展计息
//			String tMaxPayDate="select max(paydate) from LCInsureAccTrace where moneytype like '%LX%' and InsuAccNo='000001' "
//				              +" and polno='"+tLCInsureAccSchema.getPolNo()+"'";
//	
//			
//			ExeSQL tExeSQL = new ExeSQL();
//			String tNewBalaDate=tExeSQL.getOneValue(tMaxPayDate);
//			if(!"".equals(tNewBalaDate))
//			{   
//				//如果历史结息日期大于本次结算日期，说明要对本次红利的结息日期进行延长，至最大的结息日期
//				if(PubFun.calInterval(tLOBonusPolSchema.getSGetDate(), tNewBalaDate.substring(0, 10), "D")>0)
//				{
//					//comment by jiaqiangli 2009-11-16 此处修改这个字段很危险
					//comment by jiaqiangli 2009-11-16 对sgetdate < acc.baladate的trace.paydate置错了导致少了利息
//					//由于后面均用了此字段，置上新的结息日期
//					tLOBonusPolSchema.setSGetDate(tNewBalaDate);					
//				}
//
//			}
			
			if (tLCInsureAccSchema.getBalaDate() == null || "".equals(tLCInsureAccSchema.getBalaDate())
					||
					tLOBonusPolSchema.getSGetDate() == null || "".equals(tLOBonusPolSchema.getSGetDate())
					) { 
				CError.buildErr(this, "账户表计息日期或红利应分日期为空！");
				return false;
			}
			
			if (tLCInsureAccSchema.getBalaDate().compareTo(tLOBonusPolSchema.getSGetDate()) < 0) {
			//comment by jiaqiangli 2009-11-16 下面的程序是对acc解析说明 acc.baladate < 当前红利分配的trace.paydate(=lobonuspol.sgetdate)
			//也就是将当前账户结息到此次红利分配的sgetdate
			InsuAccBala tInsuAccBala = new InsuAccBala();
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("InsuAccNo", tLCInsureAccSchema.getInsuAccNo());
			tTransferData.setNameAndValue("PolNo", tLCInsureAccSchema.getPolNo());
			tTransferData.setNameAndValue("BalaDate", tLOBonusPolSchema.getSGetDate());
			VData tVData = new VData();
			tVData.add(mGlobalInput);
			tVData.add(tTransferData);
			// 非万能险的账户型结算
			if (!tInsuAccBala.submitData(tVData, "NonUniversal")) {
				CError.buildErr(this, "分红结算失败！");
				return false;
			}
			VData tResult = new VData();
			tResult = tInsuAccBala.getResult();
			LCInsureAccSchema rLCInsureAccSchema = new LCInsureAccSchema();
			LCInsureAccSet rLCInsureAccSet = (LCInsureAccSet) tResult
					.getObjectByObjectName("LCInsureAccSet", 0);
			if(rLCInsureAccSet!=null && rLCInsureAccSet.size()>0)
			{
				rLCInsureAccSchema=rLCInsureAccSet.get(1);			
			}
			LCInsureAccTraceSchema rLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
			LCInsureAccTraceSet rLCInsureAccTraceSet = (LCInsureAccTraceSet) tResult
					.getObjectByObjectName("LCInsureAccTraceSet", 0);
			if(rLCInsureAccTraceSet!=null && rLCInsureAccTraceSet.size()>0)
			{
				rLCInsureAccTraceSchema=rLCInsureAccTraceSet.get(1);			
			}
            //计算本次结息后帐户总额
			mSumGetMoney=rLCInsureAccSchema.getInsuAccBala()+tSumBonus;
			//本次结算的利息
			tInterest=rLCInsureAccTraceSchema.getMoney();
			
			//当前账户所产生的利息
			//comment by jiaqiangli 2009-11-16 放在这里统一处理
			if(tInterest>0)
			{
//				String tNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);//产生流水号码
//				LCInsureAccTraceSchema rLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
//				tReFlection.transFields(rLCInsureAccTraceSchema, tLCInsureAccClassSchema);	
//				rLCInsureAccTraceSchema.setSerialNo(tNo);
				rLCInsureAccTraceSchema.setPolNo(tLCInsureAccSchema.getPolNo());
				rLCInsureAccTraceSchema.setMoneyType("HLLX"); //红利利息
				rLCInsureAccTraceSchema.setRiskCode(tLCInsureAccSchema.getRiskCode());
				rLCInsureAccTraceSchema.setOtherNo(tLCInsureAccSchema.getPolNo());
				rLCInsureAccTraceSchema.setOtherType("1");
				rLCInsureAccTraceSchema.setAccAscription("1");
				rLCInsureAccTraceSchema.setMoney(tInterest);
				rLCInsureAccTraceSchema.setContNo(tLCInsureAccSchema.getContNo());
				rLCInsureAccTraceSchema.setGrpPolNo(tLCInsureAccSchema.getGrpPolNo());
				rLCInsureAccTraceSchema.setGrpContNo(tLCInsureAccSchema.getGrpContNo());
				rLCInsureAccTraceSchema.setInsuAccNo(tLCInsureAccSchema.getInsuAccNo());
				rLCInsureAccTraceSchema.setState(tLCInsureAccSchema.getState());
				rLCInsureAccTraceSchema.setManageCom(tLCInsureAccSchema.getManageCom());
				rLCInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
				rLCInsureAccTraceSchema.setMakeDate(tCurMakeDate);
				rLCInsureAccTraceSchema.setMakeTime("00:00:00");
				rLCInsureAccTraceSchema.setModifyDate(tCurMakeDate);
				rLCInsureAccTraceSchema.setModifyTime(tCurMakeTime);
				rLCInsureAccTraceSchema.setPayDate(tLOBonusPolSchema.getSGetDate());
				rLCInsureAccTraceSchema.setFeeCode("000000");
				mMap.put(rLCInsureAccTraceSchema, "DELETE&INSERT");
			}
			
			//这个是必须要改的
			tLCInsureAccSchema.setBalaDate(tLOBonusPolSchema.getSGetDate());
			
			}
			//说明是红利晚分配 将当前trace结息到acc.baladate
			else if (tLCInsureAccSchema.getBalaDate().compareTo(tLOBonusPolSchema.getSGetDate()) >= 0) {
				
				LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
				tLMRiskInsuAccDB.setInsuAccNo(tLCInsureAccSchema.getInsuAccNo());
				if(!tLMRiskInsuAccDB.getInfo())
				{
					CError.buildErr(this, "获取保险帐户信息失败！");
					return false;
				}
				//此处计息用AccountManage.calMultiRateForAccMS 保险帐户计息
				double rate= AccountManage.calMultiRateForAccMS(tLMRiskInsuAccDB.getSchema(), 
						tLOBonusPolSchema.getSGetDate(), tLCInsureAccSchema.getBalaDate(),tLCInsureAccSchema.getCurrency());
				if(rate == -1)
				{
					CError.buildErr(this, "计算利率失败！");
					return false;
				}
				
				//trace的利息
				tInterest = tSumBonus * rate;
				
				//计算本次结息后帐户总额
				mSumGetMoney = tLCInsureAccSchema.getInsuAccBala()+tSumBonus+tInterest;
				
				//等于0说明两者日期相等
				if(tInterest > 0)
				{
					String tNo = PubFun1.CreateMaxNo("SERIALNO", PubFun.getNoLimit(mLCContSchema.getManageCom()));//产生流水号码
					LCInsureAccTraceSchema rLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
					tReFlection.transFields(rLCInsureAccTraceSchema, tLCInsureAccSchema);	
					rLCInsureAccTraceSchema.setSerialNo(tNo);
					rLCInsureAccTraceSchema.setPolNo(tLCInsureAccSchema.getPolNo());
					rLCInsureAccTraceSchema.setMoneyType("HLLX"); //红利利息
					rLCInsureAccTraceSchema.setRiskCode(tLCInsureAccSchema.getRiskCode());
					rLCInsureAccTraceSchema.setOtherNo(tLCInsureAccSchema.getPolNo());
					rLCInsureAccTraceSchema.setOtherType("1");
					rLCInsureAccTraceSchema.setAccAscription("1");
					rLCInsureAccTraceSchema.setMoney(tInterest);
					rLCInsureAccTraceSchema.setContNo(tLCInsureAccSchema.getContNo());
					rLCInsureAccTraceSchema.setGrpPolNo(tLCInsureAccSchema.getGrpPolNo());
					rLCInsureAccTraceSchema.setGrpContNo(tLCInsureAccSchema.getGrpContNo());
					rLCInsureAccTraceSchema.setInsuAccNo(tLCInsureAccSchema.getInsuAccNo());
					rLCInsureAccTraceSchema.setState(tLCInsureAccSchema.getState());
					rLCInsureAccTraceSchema.setManageCom(tLCInsureAccSchema.getManageCom());
					rLCInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
					rLCInsureAccTraceSchema.setMakeDate(tCurMakeDate);
					rLCInsureAccTraceSchema.setMakeTime("00:00:00");
					rLCInsureAccTraceSchema.setModifyDate(tCurMakeDate);
					rLCInsureAccTraceSchema.setModifyTime(tCurMakeTime);
					//trace.lx的paydate置acc.baladate
					rLCInsureAccTraceSchema.setPayDate(tLCInsureAccSchema.getBalaDate());
					rLCInsureAccTraceSchema.setFeeCode("000000");
					rLCInsureAccTraceSchema.setPayPlanCode("000000");
					mMap.put(rLCInsureAccTraceSchema, "DELETE&INSERT");
				}
				
				//此分支下的acc.baladate不需要修改
			}
			
			tLCInsureAccSchema.setLastAccBala(tLCInsureAccSchema.getInsuAccBala());
			tLCInsureAccSchema.setInsuAccBala(mSumGetMoney);
			tLCInsureAccSchema.setInsuAccGetMoney(mSumGetMoney);
			tLCInsureAccSchema.setBalaTime("00:00:00");
			tLCInsureAccSchema.setModifyDate(tCurMakeDate);
			tLCInsureAccSchema.setModifyTime(tCurMakeTime);
			
			mMap.put(tLCInsureAccSchema, "UPDATE");
		}

		//处理 Class 表
		LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();
		LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
		tLCInsureAccClassDB.setPolNo(tLCPolSchema.getPolNo());
		tLCInsureAccClassDB.setAccType("004"); //帐户类型为红利帐户
		LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB.query();

		if (tLCInsureAccClassSet.size() == 0) {
			//效率做改进
			LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
			tLMRiskInsuAccDB.setAccType("004");
			LMRiskInsuAccSet tLMRiskInsuAccSet = tLMRiskInsuAccDB.query();
			if (tLMRiskInsuAccSet.size() == 0) {
				this.mErrors.copyAllErrors(tLMRiskInsuAccDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "BonusAssignBL";
				tError.functionName = "getBySumInterest";
				tError.errorMessage = "查询LMRiskInsuAcc表中帐户类型为004时失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			LMRiskInsuAccSchema tLMRiskInsuAccSchema = tLMRiskInsuAccSet.get(1);
			tLCInsureAccClassSchema = new LCInsureAccClassSchema();
			tReFlection.transFields(tLCInsureAccClassSchema, tLCPolSchema);	
			tLCInsureAccClassSchema.setPolNo(tLCPolSchema.getPolNo());
			tLCInsureAccClassSchema.setInsuAccNo(tLMRiskInsuAccSchema
					.getInsuAccNo());
			tLCInsureAccClassSchema.setRiskCode(tLCPolSchema.getRiskCode());
			tLCInsureAccClassSchema.setAccType("004");
			tLCInsureAccClassSchema.setContNo(tLCPolSchema.getContNo());
			tLCInsureAccClassSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
			tLCInsureAccClassSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
			tLCInsureAccClassSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			tLCInsureAccClassSchema.setSumPay(0);
			tLCInsureAccClassSchema.setInsuAccBala(tBonusMoney);
			tLCInsureAccClassSchema.setPayPlanCode("000000");
			tLCInsureAccClassSchema.setOtherNo(tLCPolSchema.getPolNo());
			tLCInsureAccClassSchema.setOtherType("1");
			tLCInsureAccClassSchema.setAccAscription("1");
			tLCInsureAccClassSchema.setUnitCount(0);
			tLCInsureAccClassSchema.setInsuAccGetMoney(tBonusMoney);
			tLCInsureAccClassSchema.setFrozenMoney(0);
			tLCInsureAccClassSchema.setAccComputeFlag(tLMRiskInsuAccSchema
					.getAccComputeFlag());
			tLCInsureAccClassSchema.setManageCom(tLCPolSchema.getManageCom());
			tLCInsureAccClassSchema.setOperator(mGlobalInput.Operator);
			tLCInsureAccClassSchema
					.setBalaDate(tLOBonusPolSchema.getSGetDate());
			tLCInsureAccClassSchema.setBalaTime("00:00:00");
			tLCInsureAccClassSchema.setMakeDate(tCurMakeDate);
			tLCInsureAccClassSchema.setMakeTime(tCurMakeTime);
			tLCInsureAccClassSchema.setModifyDate(tCurMakeDate);
			tLCInsureAccClassSchema.setModifyTime(tCurMakeTime);
			//第一次为其建立账户即插入账户表
			mMap.put(tLCInsureAccClassSchema, "DELETE&INSERT");
		} else {
			tLCInsureAccClassSchema = tLCInsureAccClassSet.get(1);
			//tLCInsureAccClassSchema.setBalaDate(tLOBonusPolSchema.getSGetDate());
			//modify by jiaqiangli 2009-11-16 请注意区分baladate < sgetdate和sgetdate <= baladate两种情况下的
			//第一种情况相当于置了sgetdate 第二种情况下baladate没变
			tLCInsureAccClassSchema.setBalaDate(tLCInsureAccSchema.getBalaDate());
			tLCInsureAccClassSchema.setBalaTime("00:00:00");
			tLCInsureAccClassSchema.setLastAccBala(tLCInsureAccClassSchema.getInsuAccBala());
			tLCInsureAccClassSchema.setInsuAccBala(mSumGetMoney);
			tLCInsureAccClassSchema.setInsuAccGetMoney(mSumGetMoney);
			tLCInsureAccClassSchema.setModifyDate(tCurMakeDate);
			tLCInsureAccClassSchema.setModifyTime(tCurMakeTime);
			mMap.put(tLCInsureAccClassSchema, "UPDATE");
		}
		//累积红利帐户余额（含本期)
		tPolSumAccBala+=mLOBonusPolSchema.getPolNo()+"-"+(PubFun.round(mSumGetMoney,2))+"|";	
		
		String tLimit = PubFun.getNoLimit(tLCPolSchema.getManageCom());
		String tSerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);//产生流水号码

		//填充保险帐户表记价履历表
		LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
		tReFlection.transFields(tLCInsureAccTraceSchema, tLCInsureAccClassSchema);	
		tLCInsureAccTraceSchema.setSerialNo(tSerialNo);
		tLCInsureAccTraceSchema.setPolNo(tLCInsureAccSchema.getPolNo());
		tLCInsureAccTraceSchema.setMoneyType("HL");
		tLCInsureAccTraceSchema.setRiskCode(tLCInsureAccSchema.getRiskCode());
		tLCInsureAccTraceSchema.setOtherNo(tLCInsureAccSchema.getPolNo());
		tLCInsureAccTraceSchema.setOtherType("1");
		tLCInsureAccTraceSchema.setAccAscription("1");
		tLCInsureAccTraceSchema.setMoney(tBonusMoney);
		tLCInsureAccTraceSchema.setContNo(tLCInsureAccSchema.getContNo());
		tLCInsureAccTraceSchema.setGrpPolNo(tLCInsureAccSchema.getGrpPolNo());
		tLCInsureAccTraceSchema.setInsuAccNo(tLCInsureAccSchema.getInsuAccNo());
		tLCInsureAccTraceSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
		tLCInsureAccTraceSchema.setState(tLCInsureAccSchema.getState());
		tLCInsureAccTraceSchema.setManageCom(tLCInsureAccSchema.getManageCom());
		tLCInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
		tLCInsureAccTraceSchema.setMakeDate(tCurMakeDate);
		tLCInsureAccTraceSchema.setMakeTime("00:00:00");
		tLCInsureAccTraceSchema.setModifyDate(tCurMakeDate);
		tLCInsureAccTraceSchema.setModifyTime(tCurMakeTime);
		//存入红利的应分日期
		tLCInsureAccTraceSchema.setPayDate(tLOBonusPolSchema.getSGetDate());
		tLCInsureAccTraceSchema.setFeeCode("000000");

		mMap.put(tLCInsureAccTraceSchema, "DELETE&INSERT");
		
		//comment by jiaqiangli 2009-11-16 挪到上面统一处理
		//存入本次分配的利息
//		if(tInterest>0)
//		{
//			String tNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);//产生流水号码
//			LCInsureAccTraceSchema rLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
//			tReFlection.transFields(rLCInsureAccTraceSchema, tLCInsureAccClassSchema);	
//			rLCInsureAccTraceSchema.setSerialNo(tNo);
//			rLCInsureAccTraceSchema.setPolNo(tLCInsureAccSchema.getPolNo());
//			rLCInsureAccTraceSchema.setMoneyType("HLLX"); //红利利息
//			rLCInsureAccTraceSchema.setRiskCode(tLCInsureAccSchema.getRiskCode());
//			rLCInsureAccTraceSchema.setOtherNo(tLCInsureAccSchema.getPolNo());
//			rLCInsureAccTraceSchema.setOtherType("1");
//			rLCInsureAccTraceSchema.setAccAscription("1");
//			rLCInsureAccTraceSchema.setMoney(tInterest);
//			rLCInsureAccTraceSchema.setContNo(tLCInsureAccSchema.getContNo());
//			rLCInsureAccTraceSchema.setGrpPolNo(tLCInsureAccSchema.getGrpPolNo());
//			rLCInsureAccTraceSchema.setGrpContNo(tLCInsureAccSchema.getGrpContNo());
//			rLCInsureAccTraceSchema.setInsuAccNo(tLCInsureAccSchema.getInsuAccNo());
//			rLCInsureAccTraceSchema.setState(tLCInsureAccSchema.getState());
//			rLCInsureAccTraceSchema.setManageCom(tLCInsureAccSchema.getManageCom());
//			rLCInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
//			rLCInsureAccTraceSchema.setMakeDate(tCurMakeDate);
//			rLCInsureAccTraceSchema.setMakeTime("00:00:00");
//			rLCInsureAccTraceSchema.setModifyDate(tCurMakeDate);
//			rLCInsureAccTraceSchema.setModifyTime(tCurMakeTime);
//			rLCInsureAccTraceSchema.setPayDate(tLOBonusPolSchema.getSGetDate());
//			rLCInsureAccTraceSchema.setFeeCode("000000");
//			mMap.put(rLCInsureAccTraceSchema, "DELETE&INSERT");
//		}

		
	
		//累计生息领取时

		//modify by jiaqiangli 2009-10-20 mLOBonusPolSchema已经做空处理(255行) 而mLCPolSchema有可能为空对象
		//导致LOBonusPol数据库中存的是'null'字符串 此字段的值必须是数据字典+null对象
		//红利计算时是通过transfer的tReflections.transFields(tLOBonusPolSchema, mLCPolSchema)
		String strSQL = "update LOBonusPol set AGetDate='?tCurMakeDate?', BonusFlag='1',BonusGetMode='?BonusGetMode?'";
		strSQL = strSQL + " , ModifyDate='?tCurMakeDate?' , ModifyTime='?tCurMakeTime?'";
		strSQL = strSQL + " where PolNo='?PolNo?' and FiscalYear=?tBonusYear?";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(strSQL);
		sqlbv4.put("tCurMakeDate", tCurMakeDate);
		sqlbv4.put("BonusGetMode", mLOBonusPolSchema.getBonusGetMode());
		sqlbv4.put("tCurMakeTime", tCurMakeTime);
		sqlbv4.put("PolNo", tLCPolSchema.getPolNo());
		sqlbv4.put("tBonusYear", tBonusYear);

		/**保单红利表更新**/
		mMap.put(sqlbv4, "UPDATE");



		return true;
	}

	/**
	 * 红利用于增额交清
	 * @param tLCPolSchema
	 * @param tBonusMoney
	 * @param tSNo
	 * @param tBonusYear
	 * @return
	 */
	private boolean getByAddPrem(LCPolSchema tLCPolSchema, String tBonusMoney,
			String tSNo, int tBonusYear, LOBonusPolSchema tLOBonusPolSchema) {
		//1-查询该保单的责任信息
		LCDutySchema tLCDutySchema = new LCDutySchema();
		LCPremSchema tLCPremSchema = new LCPremSchema();
		LCGetSchema tLCGetSchema = new LCGetSchema();
		LCGetSet tSaveLCGetSet = new LCGetSet();

		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(tLCPolSchema.getPolNo());
		LCDutySet tLCDutySet = tLCDutyDB.query();
		if (tLCDutySet.size() == 0) {
			this.mErrors.copyAllErrors(tLCDutyDB.mErrors);
			return false;
		}
		int serNo = 0;
		for (int i = 1; i <= tLCDutySet.size(); i++) {
			if (tLCDutySet.get(i).getDutyCode().length() != 6) {
				if (tLCDutySet.get(i).getDutyCode().length() == 10) {
					serNo = serNo + 1;
				}
				continue;
			} else {
				//得到一条正常责任项
				tLCDutySchema = tLCDutySet.get(i);
			}
		}
		LCPremDB tLCPremDB = new LCPremDB();
		tLCPremDB.setPolNo(tLCPolSchema.getPolNo());
		tLCPremDB.setDutyCode(tLCDutySchema.getDutyCode());
		LCPremSet tLCPremSet = tLCPremDB.query();
		if (tLCPremSet.size() == 0) {
			this.mErrors.copyAllErrors(tLCPremDB.mErrors);
			return false;
		}
		for (int n = 1; n <= tLCPremSet.size(); n++) {
			if (tLCPremSet.get(n).getPayPlanCode().length() != 6) {
				continue;
			}
			//得到一条正常保费项纪录
			tLCPremSchema = tLCPremSet.get(n);
			break;
		}
		LCGetDB tLCGetDB = new LCGetDB();
		tLCGetDB.setPolNo(tLCPolSchema.getPolNo());
		tLCGetDB.setDutyCode(tLCDutySchema.getDutyCode());
		LCGetSet tLCGetSet = tLCGetDB.query();
		if (tLCGetSet.size() == 0) {
			this.mErrors.copyAllErrors(tLCGetDB.mErrors);
			return false;
		}
		for (int n = 1; n <= tLCGetSet.size(); n++) {
			if (tLCGetSet.get(n).getGetDutyCode().length() != 6) {
				continue;
			}
			//得到一条正常领取项纪录
			tLCGetSchema = new LCGetSchema();
			tLCGetSchema = tLCGetSet.get(n);
			tSaveLCGetSet.add(tLCGetSchema);
		}

		//计算保单年度值--需要校验:即不满一年的需要加 1 --润年问题
		int PolYear = PubFun.calInterval(tLCPolSchema.getCValiDate(),
				tLOBonusPolSchema.getSGetDate(), "Y");
		//int ActuAge=PubFun.calInterval(tLCPolSchema.getInsuredBirthday(),tLOBonusPolSchema.getSGetDate(),"Y");
		int ActuAge = tLCPolSchema.getInsuredAppAge();
		CalBase mCalBase = new CalBase();
		//mCalBase.setAmnt(tLCPolSchema.getAmnt());
		mCalBase.setPrem(Double.parseDouble(tBonusMoney));
	  
	  mCalBase.setYears(PolYear);
		
		
		mCalBase.setAppAge(ActuAge);
		mCalBase.setSex(tLCPolSchema.getInsuredSex());
		mCalBase.setPayEndYear(tLCPolSchema.getPayEndYear());
		mCalBase.setPayEndYearFlag(tLCPolSchema.getPayEndYearFlag());
		mCalBase.setAppAge(ActuAge);
		mCalBase.setInsuYear(tLCPolSchema.getInsuYear());
		mCalBase.setInsuYearFlag(tLCPolSchema.getInsuYearFlag());
		mCalBase.setGetYear(tLCPolSchema.getGetYear());
		mCalBase.setPayIntv(tLCPolSchema.getPayIntv());
		mCalBase.setCValiDate(tLCPolSchema.getCValiDate());
		
   // PrepareBOMBonusBL  tPrepareBOMBonusBL  = new PrepareBOMBonusBL();
  //  tPrepareBOMBonusBL.setCalBase(mCalBase);
   // List mBomList = tPrepareBOMBonusBL.dealData(mLCContSchema);
    

		Calculator mCalculator = new Calculator();
		//mCalculator.setBOMList(mBomList);
		LMRiskBonusDB tLMRiskBonusDB = new LMRiskBonusDB();
		tLMRiskBonusDB.setRiskCode(tLCPolSchema.getRiskCode());
		if (tLMRiskBonusDB.getInfo() == false) {
			CError tError = new CError();
			tError.moduleName = "BonusAssignBL";
			tError.functionName = "getByAddPrem";
			tError.errorMessage = "没有找到增额交清对应的险种描述项";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tLMRiskBonusDB.getAddAmntCoefCode() == null) {
			CError tError = new CError();
			tError.moduleName = "BonusAssignBL";
			tError.functionName = "getByAddPrem";
			tError.errorMessage = "没有找到增额交清对应的险种算法";
			this.mErrors.addOneError(tError);
			return false;
		}
		mCalculator.setCalCode(tLMRiskBonusDB.getAddAmntCoefCode());

		//增加基本要素
		mCalculator.addBasicFactor("Prem", mCalBase.getPrem());
		//mCalculator.addBasicFactor("Amnt", mCalBase.getAmnt() );
		mCalculator.addBasicFactor("AppAge", mCalBase.getAppAge());
		mCalculator.addBasicFactor("Sex", mCalBase.getSex());
		mCalculator.addBasicFactor("PayEndYear", mCalBase.getPayEndYear());
		mCalculator.addBasicFactor("PayEndYearFlag", mCalBase
				.getPayEndYearFlag());
		mCalculator.addBasicFactor("PolYear", String.valueOf(PolYear));//添加保单年度值
		mCalculator.addBasicFactor("PayIntv", mCalBase.getPayIntv());
		mCalculator.addBasicFactor("InsuYear", mCalBase.getInsuYear());
		mCalculator.addBasicFactor("InsuYearFlag", mCalBase.getInsuYearFlag());
		mCalculator.addBasicFactor("GetYear", mCalBase.getGetYear());
		PrepareBOMBounsBL tPrepareBOMBounsBL=new PrepareBOMBounsBL();
		mBomList=tPrepareBOMBounsBL.dealData(tLCPolSchema, tLOBonusPolSchema);
		mCalculator.setBOMList(mBomList);
		//计算得到新的保额
		String tStr = "";
		double mValue;
		tStr = mCalculator.calculate();
		if (tStr.trim().equals(""))
			mValue = 0;
		else
			mValue = Double.parseDouble(tStr);

		if (mValue == 0)
			return false;
		//格式化，取两位精度-校验
		//PubFun.round(mValue, 2);
		String strCalPrem = mDecimalFormat.format(mValue);//转换计算后的保费(规定的精度)
		mValue = Double.parseDouble(strCalPrem);
		double douBonusMoney = Double.parseDouble(tBonusMoney);


		FDate tFDate = new FDate();

		//被保人新的年龄
		String newInsuredAge = Integer.toString(PubFun.calInterval(tLCPolSchema
				.getInsuredBirthday(), tLOBonusPolSchema.getSGetDate(), "Y"));
		//当前年龄和原年龄之间的间隔
		String intvAge = Integer.toString(Integer.parseInt(newInsuredAge)
				- tLCPolSchema.getInsuredAppAge());
		//新的保险年期
		String insuYear = Integer
				.toString(tLCPolSchema.getInsuYear() - PolYear);
		//新的交费年期
		String newPayYears = Integer.toString(tLCPolSchema.getPayYears()
				- PolYear);
		//新的交至日期=当前日期+新的保险年期
		String payToDate = tFDate.getString(PubFun.calDate(tFDate
				.getDate(tLOBonusPolSchema.getSGetDate()), Integer
				.parseInt(insuYear), "Y", null));
		//新的终交日期
		String payEndDate = payToDate;
		//新的终交年龄年期
		int newPayEndYear = tLCPolSchema.getPayEndYear();
		//终交年龄年期标志
		String payEndYearFlag = tLCPolSchema.getPayEndYearFlag();
		if (payEndYearFlag.equals("Y")) {
			newPayEndYear = tLCPolSchema.getPayEndYear()
					- Integer.parseInt(intvAge);
		}
		//新的保险年龄年期
		int newInsuYear = tLCPolSchema.getInsuYear();
		//终交年龄年期标志
		String insuYearFlag = tLCPolSchema.getInsuYearFlag();
		if (insuYearFlag.equals("Y")) {
			newInsuYear = tLCPolSchema.getInsuYear()
					- Integer.parseInt(intvAge);
		}

		//准备责任项，保费项，领取项数据--原来的责任编码为6位），第7位为1，剩余的3位为流水号
		serNo = serNo + 1;
		String newSerNo = "";
		String strSerNo = String.valueOf(serNo);
		if (strSerNo.length() == 1)
			newSerNo = "00" + strSerNo;
		if (strSerNo.length() == 2)
			newSerNo = "0" + strSerNo;
		if (strSerNo.length() == 3)
			newSerNo = strSerNo;
		tLCDutySchema.setDutyCode(tLCDutySchema.getDutyCode() + "1" + newSerNo);
		tLCDutySchema.setMult(1);
		tLCDutySchema.setPrem(tBonusMoney);
		tLCDutySchema.setStandPrem(tBonusMoney);
		tLCDutySchema.setSumPrem(tBonusMoney);
        tLCDutySchema.setAmnt(mValue);
        tLCDutySchema.setRiskAmnt(mValue);
		tLCDutySchema.setFirstPayDate(tLOBonusPolSchema.getSGetDate());
		tLCDutySchema.setPaytoDate(payToDate);
		tLCDutySchema.setPayEndDate(payToDate);
		tLCDutySchema.setPayIntv(0);
		tLCDutySchema.setPayEndYear(newPayEndYear);
		tLCDutySchema.setPayEndYearFlag(payEndYearFlag);
		tLCDutySchema.setInsuYear(newInsuYear);
		tLCDutySchema.setInsuYearFlag(insuYearFlag);
		tLCDutySchema.setPayYears(newPayYears);
		tLCDutySchema.setYears(insuYear);
		tLCDutySchema.setMakeDate(tCurMakeDate);
		tLCDutySchema.setMakeTime(tCurMakeTime);
		tLCDutySchema.setModifyDate(tCurMakeDate);
		tLCDutySchema.setModifyTime(tCurMakeTime);
		tLCDutySchema.setBonusGetMode("5");


		//准备保费项
		tLCPremSchema.setPolNo(tLCPolSchema.getPolNo());
		tLCPremSchema.setDutyCode(tLCDutySchema.getDutyCode());
		tLCPremSchema.setPayTimes(1);
		tLCPremSchema.setPayIntv(0);
		//        tLCPremSchema.setMult(1);
		tLCPremSchema.setStandPrem(tBonusMoney);
		tLCPremSchema.setPrem(tBonusMoney);
		tLCPremSchema.setSumPrem(tBonusMoney);
		tLCPremSchema.setPayStartDate(tLOBonusPolSchema.getSGetDate());
		tLCPremSchema.setPayEndDate(payEndDate);
		tLCPremSchema.setPaytoDate(payToDate);
		tLCPremSchema.setUrgePayFlag("N");
		tLCPremSchema.setState("0");
		tLCPremSchema.setMakeDate(tCurMakeDate);
		tLCPremSchema.setMakeTime(tCurMakeTime);
		tLCPremSchema.setModifyDate(tCurMakeDate);
		tLCPremSchema.setModifyTime(tCurMakeTime);

		mMap.put(tLCPremSchema, "DELETE&INSERT");

		//准备领取项
		double rValue=0;
		for (int i = 1; i <= tSaveLCGetSet.size(); i++) {
			tSaveLCGetSet.get(i).setPolNo(tLCPolSchema.getPolNo());
			tSaveLCGetSet.get(i).setDutyCode(tLCDutySchema.getDutyCode());
			tSaveLCGetSet.get(i).setStandMoney(mValue);

			double tValue = getValue(tSaveLCGetSet.get(i), tLCDutySchema,
					mCalBase);
			if (tValue < 0) {
				CError tError = new CError();
				tError.moduleName = "BonusAssignBL";
				tError.functionName = "getByAddPrem";
				tError.errorMessage = "计算增额交清保额失败";
				this.mErrors.addOneError(tError);
				return false;
			}
			//rValue+=tValue;
			tSaveLCGetSet.get(i).setStandMoney(tValue);
			//tSaveLCGetSet.get(i).setActuGet(tValue);
			if("1".equals(tSaveLCGetSet.get(i).getLiveGetType()))//意外给付 
			{
				//领取责任开始时间设置为分红对应点
				String tCvaliDateYear = tLCPolSchema.getCValiDate().substring(0, 4);
				int tCvaliDateYear_int = Integer.parseInt(tCvaliDateYear);
				String startdate =PubFun.calDate(tLCPolSchema.getCValiDate(), 12*(this.mFiscalYear-tCvaliDateYear_int+1), "M",null);
				tSaveLCGetSet.get(i).setGetStartDate(startdate);
				tSaveLCGetSet.get(i).setGettoDate(startdate);
			}
			else//生存给付
			{
				//红利应分日在生存领取开始前不需要处理
				//这里只处理红利应分日在生存领取开始后的情况
				if(tLOBonusPolSchema.getSGetDate().compareTo(tSaveLCGetSet.get(i).getGetStartDate())>0)
				{
					//红利对应的保单年度
					String tCvaliDateYear = tLCPolSchema.getCValiDate().substring(0, 4);
					int tCvaliDateYear_int = Integer.parseInt(tCvaliDateYear);
					int polyear = this.mFiscalYear-tCvaliDateYear_int+1;
					String startdate;
					if(tSaveLCGetSet.get(i).getGetIntv()!=0)
					{
					if((polyear*12)%tSaveLCGetSet.get(i).getGetIntv()==0)
					{
						int tIntv = 1;
						if(tSaveLCGetSet.get(i).getGetIntv()!=0)
						{
							tIntv = tSaveLCGetSet.get(i).getGetIntv();
						}
						int count=polyear*12/tSaveLCGetSet.get(i).getGetIntv();
						startdate=PubFun.calDate(tSaveLCGetSet.get(i).getGetStartDate(), tSaveLCGetSet.get(i).getGetIntv()*(count-1), "M",null);
						 
					}
					else
					{
						int count = (int) ((polyear*12)/tSaveLCGetSet.get(i).getGetIntv())  ;
						startdate=PubFun.calDate(tSaveLCGetSet.get(i).getGetStartDate(), tSaveLCGetSet.get(i).getGetIntv()*count, "M",null);
					}
					}
					else
					{
						int count=polyear*12/1;
						startdate=PubFun.calDate(tSaveLCGetSet.get(i).getGetStartDate(), tSaveLCGetSet.get(i).getGetIntv()*(count-1), "M",null);

					}
					tSaveLCGetSet.get(i).setGetStartDate(startdate);
					tSaveLCGetSet.get(i).setGettoDate(startdate);
				}
				else
				{
					//如果先领走了生存金，在有红利出来，且红利的应领时间是在开始生存金领取前，需要将gettodate设置为getstartdate
					tSaveLCGetSet.get(i).setGettoDate(tSaveLCGetSet.get(i).getGetStartDate());
				}
			}
			tSaveLCGetSet.get(i).setBalaDate("");
			tSaveLCGetSet.get(i).setMakeDate(tCurMakeDate);
			tSaveLCGetSet.get(i).setMakeTime(tCurMakeTime);
			tSaveLCGetSet.get(i).setModifyDate(tCurMakeDate);
			tSaveLCGetSet.get(i).setModifyTime(tCurMakeTime);
		}
		//跟新重新计算的保额
//		tLCDutySchema.setAmnt(rValue);
//		tLCDutySchema.setRiskAmnt(rValue);
		mMap.put(tLCDutySchema, "DELETE&INSERT");	
		
		mMap.put(tSaveLCGetSet, "DELETE&INSERT");
		
		
		//更新个人保单表:累计保费增加,风险保额增加 客户历史需求
		String strSQL2 = "update lcpol set SumPrem=?SumPrem?,RiskAmnt=?RiskAmnt?,ModifyDate='?tCurMakeDate?',ModifyTime='?tCurMakeTime?' where polno='?polno?'";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(strSQL2);
		sqlbv5.put("SumPrem", tLCPolSchema.getSumPrem() + douBonusMoney);
		sqlbv5.put("RiskAmnt", tLCPolSchema.getRiskAmnt() + mValue);
		sqlbv5.put("tCurMakeDate", tCurMakeDate);
		sqlbv5.put("tCurMakeTime", tCurMakeTime);
		sqlbv5.put("polno", tLCPolSchema.getPolNo());

		mMap.put(sqlbv5, "UPDATE");
		
		
		//本期红利购买增额交清的保额
		tPolGetAmnt+=mLOBonusPolSchema.getPolNo()+"-"+PubFun.round(mValue,2)+"|";
		//红利分配后累计保额（含本期)
		tPolSumGetAmnt+=mLOBonusPolSchema.getPolNo()+"-"+(PubFun.round(tLCPolSchema.getAmnt()+mValue,2))+"|";	

		
		String tLimit = PubFun.getNoLimit(tLCPolSchema.getManageCom());
		String tActuGetNo = PubFun1.CreateMaxNo("GETNO", tLimit);//产生实付号码
		String tGetNoticeNo = PubFun1.CreateMaxNo("GETNOTICENO", tLimit);//产生即付通知书号
		String tTempFeeNo = PubFun1.CreateMaxNo("PAYNO", tLimit);//产生暂交费号
		String tappntname = "";
		String tDrawerID = "";
		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(tLCPolSchema.getContNo());
		tLCAppntDB.setAppntNo(tLCPolSchema.getAppntNo());
		if (tLCAppntDB.getInfo() == false) {
			tDrawerID = "";
		} else {
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
		tLJAGetSchema.setSumGetMoney(tBonusMoney);
		tLJAGetSchema.setSaleChnl(tLCPolSchema.getSaleChnl());
		tLJAGetSchema.setShouldDate(tLOBonusPolSchema.getSGetDate());
		tLJAGetSchema.setEnterAccDate(tCurMakeDate);
		tLJAGetSchema.setConfDate(tCurMakeDate);

		tLJAGetSchema.setApproveCode(tLCPolSchema.getApproveCode());
		tLJAGetSchema.setApproveDate(tLCPolSchema.getApproveDate());
		tLJAGetSchema.setGetNoticeNo(tGetNoticeNo);
		tLJAGetSchema.setDrawer(tLCPolSchema.getAppntName());
		tLJAGetSchema.setDrawerID(tDrawerID);
		tLJAGetSchema.setSerialNo(tSNo);
		tLJAGetSchema.setOperator(mGlobalInput.Operator);
		tLJAGetSchema.setMakeDate(tCurMakeDate);
		tLJAGetSchema.setMakeTime(tCurMakeTime);
		tLJAGetSchema.setModifyDate(tCurMakeDate);
		tLJAGetSchema.setModifyTime(tCurMakeTime);
		tLJAGetSchema.setManageCom(tLCPolSchema.getManageCom());
		tLJAGetSchema.setAgentCom(tLCPolSchema.getAgentCom());
		tLJAGetSchema.setAgentType(tLCPolSchema.getAgentType());
		tLJAGetSchema.setAgentCode(tLCPolSchema.getAgentCode());
		tLJAGetSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
		tLJAGetSchema.setBankCode(tLCPolSchema.getGetBankCode());
		tLJAGetSchema.setBankAccNo(tLCPolSchema.getGetBankAccNo());
		tLJAGetSchema.setCurrency(tLCPolSchema.getCurrency()==null?"01":tLCPolSchema.getCurrency());
		mMap.put(tLJAGetSchema, "DELETE&INSERT");

		//添加实付子表-红利给付实付表
		LJABonusGetSchema tLJABonusGetSchema = new LJABonusGetSchema();
		tLJABonusGetSchema.setActuGetNo(tActuGetNo);
		tLJABonusGetSchema.setOtherNo(tLCPolSchema.getContNo());
		tLJABonusGetSchema.setOtherNoType("7");
		tLJABonusGetSchema.setPayMode("5");//内部转账
		tLJABonusGetSchema.setBonusYear(String.valueOf(tBonusYear));
		tLJABonusGetSchema.setGetMoney(tBonusMoney);
		tLJABonusGetSchema.setGetDate(tCurMakeDate);
		tLJABonusGetSchema.setEnterAccDate(tCurMakeDate);
		tLJABonusGetSchema.setConfDate(tCurMakeDate);
		tLJABonusGetSchema.setCurrency(tLCPolSchema.getCurrency()==null?"01":tLCPolSchema.getCurrency());
		tLJABonusGetSchema.setManageCom(tLCPolSchema.getManageCom());
		tLJABonusGetSchema.setAgentCom(tLCPolSchema.getAgentCom());
		tLJABonusGetSchema.setAgentType(tLCPolSchema.getAgentType());
		tLJABonusGetSchema.setAPPntName(tLCPolSchema.getAppntName());
		tLJABonusGetSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
		tLJABonusGetSchema.setAgentCode(tLCPolSchema.getAgentCode());
		tLJABonusGetSchema.setFeeFinaType("HLTF"); //红利退费
		tLJABonusGetSchema.setFeeOperationType("HLTF");
		tLJABonusGetSchema.setSerialNo(tSNo);
		tLJABonusGetSchema.setOperator(mGlobalInput.Operator);
		tLJABonusGetSchema.setState("0");
		tLJABonusGetSchema.setMakeDate(tCurMakeDate);
		tLJABonusGetSchema.setMakeTime(tCurMakeTime);
		tLJABonusGetSchema.setModifyDate(tCurMakeDate);
		tLJABonusGetSchema.setModifyTime(tCurMakeTime);
		tLJABonusGetSchema.setGetNoticeNo(tGetNoticeNo);
		tLJABonusGetSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
		tLJABonusGetSchema.setContNo(tLCPolSchema.getContNo());
		tLJABonusGetSchema.setRiskCode(tLCPolSchema.getRiskCode());
		tLJABonusGetSchema.setPolNo(tLCPolSchema.getPolNo());
		tLJABonusGetSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());

		mMap.put(tLJABonusGetSchema, "DELETE&INSERT");
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
		tLJFIGetSchema.setConfMakeDate(tCurMakeDate);
		tLJFIGetSchema.setCurrency(tLCPolSchema.getCurrency()==null?"01":tLCPolSchema.getCurrency());
		mMap.put(tLJFIGetSchema, "DELETE&INSERT");

		//添加暂交费纪录
		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
		tLJTempFeeSchema.setTempFeeNo(tTempFeeNo);
		tLJTempFeeSchema.setTempFeeType("7");
		tLJTempFeeSchema.setRiskCode(tLCPolSchema.getRiskCode());
		tLJTempFeeSchema.setPayIntv(tLCPolSchema.getPayIntv());
		tLJTempFeeSchema.setOtherNo(tLCPolSchema.getContNo());
		tLJTempFeeSchema.setOtherNoType("0");
		tLJTempFeeSchema.setPayMoney(tBonusMoney);
		tLJTempFeeSchema.setPayDate(tCurMakeDate);
		tLJTempFeeSchema.setEnterAccDate(tCurMakeDate);
		tLJTempFeeSchema.setConfDate(tCurMakeDate);
		tLJTempFeeSchema.setSaleChnl(tLCPolSchema.getSaleChnl());
		tLJTempFeeSchema.setManageCom(tLCPolSchema.getManageCom());
		tLJTempFeeSchema.setAgentCom(tLCPolSchema.getAgentCom());
		tLJTempFeeSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
		tLJTempFeeSchema.setAgentType(tLCPolSchema.getAgentType());
		tLJTempFeeSchema.setAPPntName(tLCPolSchema.getAppntName());
		tLJTempFeeSchema.setAgentCode(tLCPolSchema.getAgentCode());
		tLJTempFeeSchema.setConfFlag("1");
		tLJTempFeeSchema.setSerialNo(tSNo);
		tLJTempFeeSchema.setOperator(mGlobalInput.Operator);
		tLJTempFeeSchema.setMakeDate(tCurMakeDate);
		tLJTempFeeSchema.setMakeTime(tCurMakeTime);
		tLJTempFeeSchema.setModifyDate(tCurMakeDate);
		tLJTempFeeSchema.setModifyTime(tCurMakeTime);
		tLJTempFeeSchema.setConfMakeDate(tCurMakeDate);
		tLJTempFeeSchema.setConfMakeTime(tCurMakeTime);
		tLJTempFeeSchema.setPolicyCom(mLCPolSchema.getManageCom());
		tLJTempFeeSchema.setCurrency(tLCPolSchema.getCurrency()==null?"01":tLCPolSchema.getCurrency());
		mMap.put(tLJTempFeeSchema, "DELETE&INSERT");
		//添加暂交费子表
		LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		tLJTempFeeClassSchema.setTempFeeNo(tTempFeeNo);
		//内部转账存为实付号
		tLJTempFeeClassSchema.setChequeNo(tActuGetNo);
		tLJTempFeeClassSchema.setPayMode("5");//内部转账
		tLJTempFeeClassSchema.setPayMoney(tBonusMoney);
		//        tLJTempFeeClassSchema.setAPPntName(tLCPolSchema.getAppntName());
		tLJTempFeeClassSchema.setPayDate(tCurMakeDate);
		tLJTempFeeClassSchema.setOtherNo(tLCPolSchema.getContNo());
		tLJTempFeeClassSchema.setApproveDate(tLCPolSchema.getApproveDate());
		tLJTempFeeClassSchema.setConfDate(tCurMakeDate);
		tLJTempFeeClassSchema.setEnterAccDate(tCurMakeDate);
		tLJTempFeeClassSchema.setConfFlag("1");
		tLJTempFeeClassSchema.setSerialNo(tSNo);
		tLJTempFeeClassSchema.setOperator(mGlobalInput.Operator);
		tLJTempFeeClassSchema.setMakeDate(tCurMakeDate);
		tLJTempFeeClassSchema.setMakeTime(tCurMakeTime);
		tLJTempFeeClassSchema.setModifyDate(tCurMakeDate);
		tLJTempFeeClassSchema.setModifyTime(tCurMakeTime);
		tLJTempFeeClassSchema.setManageCom(tLCPolSchema.getManageCom());
		tLJTempFeeClassSchema.setConfMakeDate(tCurMakeDate);
		tLJTempFeeClassSchema.setConfMakeTime(tCurMakeTime);
		tLJTempFeeClassSchema.setPolicyCom(mLCPolSchema.getManageCom());
		tLJTempFeeClassSchema.setCurrency(tLCPolSchema.getCurrency()==null?"01":tLCPolSchema.getCurrency());
		mMap.put(tLJTempFeeClassSchema, "DELETE&INSERT");
		
		 //2012-02-07 modify
        //增额缴清处理时,需要更新客户账户
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
		nInputData.add(this.mGlobalInput);
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("HXFlag","1");
		nInputData.add(tTransferData);
		FICustomerMain tFICustomerMain = new FICustomerMain();
		// 调用客户账户收费接口，传入财务标志FI
		if (tFICustomerMain.submitData(nInputData, "FI"))
		{
			// 获取接口计算结果，传入MMap，方便打包直接用PubSubmit提交
			mMap.add(tFICustomerMain.getMMap());
		}
		else
		{
			mErrors.copyAllErrors(tFICustomerMain.mErrors);
			return false;
		}
		
		//增加客户账户处理
		
		
		//添加实收总表纪录
		LJAPaySchema tLJAPaySchema = new LJAPaySchema();
		tLJAPaySchema.setPayNo(tTempFeeNo);
		tLJAPaySchema.setIncomeNo(tLCPolSchema.getContNo());
		tLJAPaySchema.setIncomeType("2");
		tLJAPaySchema.setAppntNo(tLCPolSchema.getAppntNo());
		tLJAPaySchema.setSumActuPayMoney(tBonusMoney);
		tLJAPaySchema.setPayDate(tCurMakeDate);
		tLJAPaySchema.setEnterAccDate(tCurMakeDate);
		tLJAPaySchema.setConfDate(tCurMakeDate);
		tLJAPaySchema.setApproveCode(tLCPolSchema.getApproveCode());
		tLJAPaySchema.setApproveDate(tLCPolSchema.getApproveDate());
		tLJAPaySchema.setSerialNo(tSNo);
		tLJAPaySchema.setOperator(mGlobalInput.Operator);
		tLJAPaySchema.setMakeDate(tCurMakeDate);
		tLJAPaySchema.setMakeTime(tCurMakeTime);
		tLJAPaySchema.setModifyDate(tCurMakeDate);
		tLJAPaySchema.setModifyTime(tCurMakeTime);
		tLJAPaySchema.setGetNoticeNo(tGetNoticeNo);
		tLJAPaySchema.setManageCom(tLCPolSchema.getManageCom());
		tLJAPaySchema.setAgentCode(tLCPolSchema.getAgentCode());
		tLJAPaySchema.setAgentType(tLCPolSchema.getAgentType());
		tLJAPaySchema.setAgentGroup(tLCPolSchema.getAgentGroup());
		tLJAPaySchema.setBankCode(tLCPolSchema.getGetBankCode()); //银行编码
		tLJAPaySchema.setBankAccNo(tLCPolSchema.getGetBankAccNo()); //银行帐号
		tLJAPaySchema.setRiskCode(tLCPolSchema.getRiskCode()); // 险种编码
		tLJAPaySchema.setCurrency(tLCPolSchema.getCurrency()==null?"01":tLCPolSchema.getCurrency());
		mMap.put(tLJAPaySchema, "DELETE&INSERT");

		//添加实收个人子表纪录
		LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
		tLJAPayPersonSchema.setPolNo(tLCPolSchema.getPolNo());
		tLJAPayPersonSchema.setPayCount(2); //-记为续期
		tLJAPayPersonSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
		tLJAPayPersonSchema.setContNo(tLCPolSchema.getContNo());
		tLJAPayPersonSchema.setAppntNo(tLCPolSchema.getAppntNo());
		tLJAPayPersonSchema.setPayNo(tTempFeeNo);
		tLJAPayPersonSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
		if (tLCPolSchema.getGrpPolNo().equals("00000000000000000000")
				&& tLCPolSchema.getContNo().equals("00000000000000000000")) {
			tLJAPayPersonSchema.setPayAimClass("1");//交费目的分类
		}
		if (!tLCPolSchema.getGrpPolNo().equals("00000000000000000000")
				&& tLCPolSchema.getContNo().equals("00000000000000000000")) {
			tLJAPayPersonSchema.setPayAimClass("2");//交费目的分类
		}
		tLJAPayPersonSchema.setCurrency(tLCPolSchema.getCurrency()==null?"01":tLCPolSchema.getCurrency());
		tLJAPayPersonSchema.setDutyCode(tLCDutySchema.getDutyCode()); //责任编码
		tLJAPayPersonSchema.setPayPlanCode("00000000");//交费计划编码
		tLJAPayPersonSchema.setSumActuPayMoney(tBonusMoney);
		tLJAPayPersonSchema.setSumDuePayMoney(tBonusMoney);
		tLJAPayPersonSchema.setPayIntv(0);
		tLJAPayPersonSchema.setPayDate(tCurMakeDate);
		tLJAPayPersonSchema.setPayType("ZC");
		tLJAPayPersonSchema.setEnterAccDate(tCurMakeDate);
		tLJAPayPersonSchema.setConfDate(tCurMakeDate);
		tLJAPayPersonSchema.setLastPayToDate(tLCDutySchema.getFirstPayDate()); //原交至日期
		tLJAPayPersonSchema.setCurPayToDate(tLCDutySchema.getPaytoDate()); //现交至日期
		tLJAPayPersonSchema.setInInsuAccState("0");//转入保险帐户状态
		tLJAPayPersonSchema.setApproveCode(tLCPolSchema.getApproveCode()); //复核人编码
		tLJAPayPersonSchema.setApproveDate(tLCPolSchema.getApproveDate()); //复核日期
		tLJAPayPersonSchema.setSerialNo(tSNo);
		tLJAPayPersonSchema.setOperator(mGlobalInput.Operator);
		tLJAPayPersonSchema.setMakeDate(tCurMakeDate);
		tLJAPayPersonSchema.setMakeTime(tCurMakeTime);
		tLJAPayPersonSchema.setModifyDate(tCurMakeDate);
		tLJAPayPersonSchema.setModifyTime(tCurMakeTime);
		tLJAPayPersonSchema.setGetNoticeNo(tGetNoticeNo);
		tLJAPayPersonSchema.setInInsuAccState("0");//转入保险帐户状态
		tLJAPayPersonSchema.setManageCom(tLCPolSchema.getManageCom());
		tLJAPayPersonSchema.setAgentCode(tLCPolSchema.getAgentCode());
		tLJAPayPersonSchema.setAgentCom(tLCPolSchema.getAgentCom());
		tLJAPayPersonSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
		tLJAPayPersonSchema.setRiskCode(tLCPolSchema.getRiskCode());

		mMap.put(tLJAPayPersonSchema, "DELETE&INSERT");
		

		String strSQL = "update LOBonusPol set AGetDate='?tCurMakeDate?' , BonusFlag='1',BonusGetMode='?BonusGetMode?'";
		strSQL = strSQL + " , ModifyDate='?tCurMakeDate?' , ModifyTime='?tCurMakeTime?'";
		strSQL = strSQL + " where PolNo='?PolNo?' and FiscalYear=?tBonusYear?";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(strSQL);
		sqlbv6.put("tCurMakeDate", tCurMakeDate);
		sqlbv6.put("BonusGetMode", mLCPolSchema.getBonusGetMode());
		sqlbv6.put("tCurMakeTime", tCurMakeTime);
		sqlbv6.put("PolNo", tLCPolSchema.getPolNo());
		sqlbv6.put("tBonusYear", tBonusYear);
		/**保单红利表更新**/
		mMap.put(sqlbv6, "UPDATE");

		return true;
	}

	/**获取增额交清的保额
	 * */
	private double getValue(LCGetSchema tLCGetSchema,
			LCDutySchema tLCDutySchema, CalBase mCalBase) {
		CachedRiskInfo mCRI = CachedRiskInfo.getInstance();

		LMDutySchema tLMDutySchema = mCRI.findDutyByDutyCodeClone(tLCDutySchema
				.getDutyCode().substring(0, 6));
		LMDutyGetDB tLMDutyGetDB = new LMDutyGetDB();
		tLMDutyGetDB.setSchema(mCRI.findDutyGetByGetDutyCodeClone(tLCGetSchema
				.getGetDutyCode()));
		mCalBase.setGetIntv(tLCGetSchema.getGetIntv());
		mCalBase.setGDuty(tLCGetSchema.getGetDutyCode());
		mCalBase.setAddRate(tLCGetSchema.getAddRate());
		mCalBase.setGet(tLCDutySchema.getAmnt());
		mCalBase.setDutyCode(tLCDutySchema.getDutyCode());
		mCalBase.setPrem(tLCDutySchema.getStandPrem());
		mCalBase.setGet(tLCDutySchema.getAmnt());
		mCalBase.setMult(tLCDutySchema.getMult());
		//添加计算要素
		mCalBase.setGetDutyKind(tLCGetSchema.getGetDutyKind());

		double calResult = 0;
		calResult = calGetValue(tLCGetSchema, tLCDutySchema, tLMDutyGetDB,
				tLMDutySchema.getCalMode(), mCalBase);

		return calResult;
	}

	/**
	 * 计算领取项表中实际领取的值
	 * 
	 * @param mLCGetSchema
	 *            LCGetSchema
	 * @param tLCDutySchema
	 *            LCDutySchema
	 * @param tLMDutyGetSchema
	 *            LMDutyGetSchema
	 * @param calFlag
	 *            String
	 * @return double
	 */
	private double calGetValue(LCGetSchema mLCGetSchema,
			LCDutySchema tLCDutySchema, LMDutyGetSchema tLMDutyGetSchema,
			String calFlag, CalBase mCalBase) {
		double mValue = -1;
		PrepareBOMCalBL tPrepareBOMCalBL = new PrepareBOMCalBL();
		
		List tBomList = new ArrayList();
		tBomList = tPrepareBOMCalBL.dealData(mLCPolSchema,tLCDutySchema,mLCGetSchema);
		
		String mCalCode = null;
		if (StrTool.cTrim(calFlag).equals("P")) {
			mCalCode = tLMDutyGetSchema.getCalCode(); // 保费算保额
		}
		if (StrTool.cTrim(calFlag).equals("G")) {
			mCalCode = tLMDutyGetSchema.getCnterCalCode(); // 保额算保费
		}
		if (StrTool.cTrim(calFlag).equals("O")) {
			mCalCode = tLMDutyGetSchema.getOthCalCode(); // 其他算保费
		}
		if (StrTool.cTrim(calFlag).equals("A")
				&& tLCDutySchema.getStandPrem() != 0.0) {
			mCalCode = tLMDutyGetSchema.getCalCode(); // 保费、保额互算
		}
		if (StrTool.cTrim(calFlag).equals("A")
				&& tLCDutySchema.getAmnt() != 0.0) {
			mCalCode = tLMDutyGetSchema.getCnterCalCode(); // 保费、保额互算
		}
		if (StrTool.cTrim(calFlag).equals("I")) {
			mCalCode = tLMDutyGetSchema.getCnterCalCode(); // 录入保费保额
		}

		// 取默认值
		if (StrTool.cTrim(mCalCode).equals("")) {
			mValue = tLMDutyGetSchema.getDefaultVal();
			return mValue;
		}

		// 计算
		Calculator mCalculator = new Calculator();
		mCalculator.setBOMList(tBomList);
		mCalculator.setCalCode(mCalCode);
		// 增加基本要素
		mCalculator.addBasicFactor("Get", mCalBase.getGet());
		mCalculator.addBasicFactor("Mult", mCalBase.getMult());
		mCalculator.addBasicFactor("Prem", mCalBase.getPrem());
		mCalculator.addBasicFactor("PayIntv", mCalBase.getPayIntv());
		mCalculator.addBasicFactor("GetIntv", mCalBase.getGetIntv());
		mCalculator.addBasicFactor("AppAge", mCalBase.getAppAge());
		mCalculator.addBasicFactor("Sex", mCalBase.getSex());
		mCalculator.addBasicFactor("Job", mCalBase.getJob());
		mCalculator.addBasicFactor("PayEndYear", mCalBase.getPayEndYear());
		mCalculator.addBasicFactor("PayEndYearFlag", mCalBase
				.getPayEndYearFlag());
		mCalculator.addBasicFactor("GetStartDate", "");
		mCalculator.addBasicFactor("GetYear", mCalBase.getGetYear());
		mCalculator.addBasicFactor("GetYearFlag", mCalBase.getGetYearFlag());
		mCalculator.addBasicFactor("Years", mCalBase.getYears());
		mCalculator.addBasicFactor("InsuYear", mCalBase.getInsuYear());
		mCalculator.addBasicFactor("InsuYearFlag", mCalBase.getInsuYearFlag());
		mCalculator.addBasicFactor("Grp", "");
		mCalculator.addBasicFactor("GetFlag", "");
		mCalculator.addBasicFactor("CValiDate", mCalBase.getCValiDate());
		mCalculator.addBasicFactor("Count", mCalBase.getCount());
		mCalculator.addBasicFactor("FirstPayDate", "");
		mCalculator.addBasicFactor("RnewFlag", mCalBase.getRnewFlag());
		mCalculator.addBasicFactor("AddRate", mCalBase.getAddRate());
		mCalculator.addBasicFactor("GDuty", mCalBase.getGDuty());
		mCalculator.addBasicFactor("PolNo", mCalBase.getPolNo());
		mCalculator.addBasicFactor("FRate", mCalBase.getFloatRate());
		mCalculator.addBasicFactor("GetDutyKind", mCalBase.getGetDutyKind());
		mCalculator.addBasicFactor("StandbyFlag1", mCalBase.getStandbyFlag1());
		mCalculator.addBasicFactor("StandbyFlag2", mCalBase.getStandbyFlag2());
		mCalculator.addBasicFactor("StandbyFlag3", mCalBase.getStandbyFlag3());
		mCalculator.addBasicFactor("GrpPolNo", mCalBase.getGrpPolNo());
		mCalculator.addBasicFactor("GrpContNo", mCalBase.getGrpContNo());
		mCalculator.addBasicFactor("GetLimit", mCalBase.getGetLimit());
		mCalculator.addBasicFactor("GetRate", mCalBase.getGetRate());
		mCalculator.addBasicFactor("SSFlag", mCalBase.getSSFlag());
		mCalculator.addBasicFactor("PeakLine", mCalBase.getPeakLine());
		mCalculator.addBasicFactor("CalType", mCalBase.getCalType());
		mCalculator.addBasicFactor("ContNo", mCalBase.getContNo());
		mCalculator.addBasicFactor("VPU", mCalBase.getVPU());
		mCalculator.addBasicFactor("SecondInsuredNo", mCalBase
				.getSecondInsuredNo());
		mCalculator.addBasicFactor("InsuredNo", mCalBase.getInsuredNo());
		mCalculator.addBasicFactor("DutyCode", mCalBase.getDutyCode()
				.substring(0, 6));
		mCalculator.addBasicFactor("MainPolNo", mCalBase.getMainPolNo());
		mCalculator.addBasicFactor("MAmnt", mCalBase.getMAmnt());
		mCalculator.addBasicFactor("AppAge2", mCalBase.getAppAge2());
		mCalculator.addBasicFactor("ManageCom", mCalBase.getManageCom());
		mCalculator.addBasicFactor("GetStartType", mCalBase.getGetStartType());
		mCalculator.addBasicFactor("PolTypeFlag", mCalBase.getPolTypeFlag());
		logger.debug("CalBase.getAllOtherParmCount()=="
				+ mCalBase.getAllOtherParmCount());
		for (int i = 0; i < mCalBase.getAllOtherParmCount(); i++) {
			String tOtherParmName = mCalBase.getOtherParmName(i);
			String tOtherParmValue = mCalBase
					.getOtherParmVlaueByName(tOtherParmName);
			logger.debug("tOtherParmName==" + tOtherParmName
					+ " tOtherParmValue ==" + tOtherParmValue);
			mCalculator.addBasicFactor(tOtherParmName, tOtherParmValue);
		}
		logger.debug("CalBase.getAllOtherParmCount()=="
				+ mCalBase.getAllOtherParmCount());

		String tStr = "";
		tStr = mCalculator.calculate();
		if (tStr.trim().equals("")) {
			mValue = -1;
		} else {
			mValue = Double.parseDouble(tStr);
		}

		// logger.debug(mValue);
		return mValue;
	}

	/**
	 * 分配红利后插入打印数据
	 * @param tLCPolSchema 保单表
	 * @param tLOBonusPolSchema 红利数据表
	 * @param tSumAccBala 累积红利帐户余额（含本期）
	 * @param tZEAmnt  增额的保额
	 * @param tSumAmnt 累计保额
	 * @param tComonValue 公共字段，用于附属，如增额交清时，用于保存责任编码
	 * @return
	 */
	public boolean getPrintData(LCContSchema mLCContSchema,
			int tFinaYear,String tPolBonusMoney, String tPolBonusYCLX, String tPolSumBonus,
			String tPolSumAccBala,String tPolPayPrem, String tPolGetAmnt, String tPolSumGetAmnt) {

		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		try {

			//        	保险合同号码	分红险种简称	本期红利领取方式	本期红利分配日期	本期红利金额	累积红利账户余额（含本期）	
			//        	现金领取账户余额（含本期）	抵缴保费账户余额（含本期）	增额交清保额
			
			//本期红利领取方式	本期红利金额	红利延迟发放利息	本期红利本息合计	累积红利帐户余额（含本期）	本期红利抵交保险费金额	本期红利购买增额交清的保额	红利分配后累计保额（含本期）
			String tLimit = PubFun.getNoLimit(mLCContSchema.getManageCom());
			String prtSeqNo = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
			tLOPRTManagerSchema.setPrtSeq(prtSeqNo);
			tLOPRTManagerSchema.setOtherNo(mLCContSchema.getContNo());
			tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT);
			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_BONUSPAY);
			tLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
			tLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
			tLOPRTManagerSchema.setReqCom(mLCContSchema.getManageCom());
			tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
			tLOPRTManagerSchema.setPrtType("0");
			tLOPRTManagerSchema.setStateFlag("0");
			//tLOPRTManagerSchema.setStandbyFlag1(aStandbyFlag1);
			tLOPRTManagerSchema.setStandbyFlag1(tPolBonusMoney); //分红金额
			tLOPRTManagerSchema.setStandbyFlag2(tPolBonusYCLX); //红利延迟发放利息
			tLOPRTManagerSchema.setStandbyFlag3(tPolSumBonus); //本期红利本息合计
			tLOPRTManagerSchema.setStandbyFlag4(tPolSumAccBala); //累积红利帐户余额（含本期）
			tLOPRTManagerSchema.setStandbyFlag5(tPolPayPrem); //本期红利抵交保险费金额
			tLOPRTManagerSchema.setStandbyFlag6(tPolGetAmnt); //本期红利购买增额交清的保额
			tLOPRTManagerSchema.setStandbyFlag7(tPolSumGetAmnt); //红利分配后累计保额（含本期）
			//记录本次增额交清的责任编码
			tLOPRTManagerSchema.setRemark(String.valueOf(tFinaYear));
			tLOPRTManagerSchema.setMakeDate(tCurMakeDate);
			tLOPRTManagerSchema.setMakeTime(tCurMakeTime);
		} catch (Exception ex) {
			logger.debug(mLCContSchema.getContNo() + "红利分配出现异常："
					+ ex.toString());
			return false;
		}
		mLOPRTManagerSet.add(tLOPRTManagerSchema);

		return true;
	}

	/**
	 * 纪录错误信息 ,单独进行体检，与业务无关
	 * @param tSerialNo
	 * @param tPolNo
	 * @param errDescribe
	 * @param tGetMode
	 * @return 
	 */
	public boolean insertErrLog(String tSerialNo, String tPolNo,
			String errDescribe, LCPolSchema mLCPolSchema) {
		LOBonusAssignErrLogDB tLOBonusAssignErrLogDB = new LOBonusAssignErrLogDB();
		tLOBonusAssignErrLogDB.setSerialNo(tSerialNo);
		tLOBonusAssignErrLogDB.setPolNo(tPolNo);
		tLOBonusAssignErrLogDB.setGetMode(mLCPolSchema.getBonusGetMode());
		tLOBonusAssignErrLogDB.setGrpContNo(mLCPolSchema.getGrpContNo());
		tLOBonusAssignErrLogDB.setContNo(mLCPolSchema.getContNo());
		tLOBonusAssignErrLogDB.seterrMsg(errDescribe);
		tLOBonusAssignErrLogDB.setmakedate(tCurMakeDate);
		tLOBonusAssignErrLogDB.setmaketime(tCurMakeTime);
		if (tLOBonusAssignErrLogDB.insert() == false) {
			CError tError = new CError();
			tError.moduleName = "BonusAssignBL";
			tError.functionName = "insertErrLog";
			tError.errorMessage = "纪录错误日志时发生错误："
					+ tLOBonusAssignErrLogDB.mErrors.getFirstError()
					+ "；解决该问题后，请再次分配当日红利";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**获取保单有效，失效的情况，保单有效 返回 "1", 失效返回 "0"*/
	private String getValiFlag(LCPolSchema tLCPolSchema) {
		ExeSQL tExeSQL = new ExeSQL();
		String tSql = "select State from LCContState where PolNo='?PolNo?' and StateType='Available' and State='1' and EndDate is null";
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(tSql);
		sqlbv7.put("PolNo", tLCPolSchema.getPolNo());
		String tFlag = tExeSQL.getOneValue(sqlbv7); //查询的结果为空或者为0时保单有效
		if ("1".equals(tFlag)) {
			return "0";
		} else {
			return "1";
		}
	}

	/**
	 * 红利抵交续期保费的处理接口
	 * @param tLCPolSchema 
	 * @param tBonusYear 分红年度，如果本次抵交保费涉及多个保单年度则需要按年度循环
	 * 
	 * 根据帐户进行循环
	 * 
	 * @return
	 */

	public boolean dealRePay(LCPolSchema tLCPolSchema) {

		MMap tMap = new MMap();
		double tSumLeavmoney=0 ;

		String tSql = "select * from LCInsureAcc b"
				+ " where InsuAccBala >0  and polno='?polno?' polno ";
		SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
		sqlbv8.sql(tSql);
		sqlbv8.put("polno", tLCPolSchema.getPolNo());
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		tLCInsureAccSet = tLCInsureAccDB.executeQuery(sqlbv8);
		
		//说明没有未领的红利
		if (tLCInsureAccSet == null || tLCInsureAccSet.size() < 1) {
			return true;
		} else {
			for (int k = 1; k <= tLCInsureAccSet.size(); k++) {
				LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
				tLCInsureAccSchema = tLCInsureAccSet.get(k);
				tSumLeavmoney+=tLCInsureAccSchema.getInsuAccBala();
				String tLimit = PubFun.getNoLimit(tLCPolSchema.getManageCom());
				String tActuGetNo = PubFun1.CreateMaxNo("GETNO", tLimit);//产生实付号码
				String tGetNoticeNo = PubFun1
						.CreateMaxNo("GETNOTICENO", tLimit);//产生即付通知书号
				String tTempFeeNo = PubFun1.CreateMaxNo("PAYNO", tLimit);//产生暂交费号
				String tSNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);//产生流水号码
				String tappntname = "";
				String tDrawerID = "";
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(tLCPolSchema.getPolNo());
				tLCAppntDB.setAppntNo(tLCPolSchema.getAppntNo());
				if (tLCAppntDB.getInfo() == false) {
					tDrawerID = "";
				} else {
					tDrawerID = tLCAppntDB.getIDNo();
					tappntname = tLCAppntDB.getAppntName();
				}

				//添加实付数据-总表
				LJAGetSchema tLJAGetSchema = new LJAGetSchema();
				tLJAGetSchema.setActuGetNo(tActuGetNo);
				tLJAGetSchema.setOtherNo(tLCPolSchema.getPolNo());
				tLJAGetSchema.setOtherNoType("7");
				tLJAGetSchema.setPayMode("5");//内部转账
				tLJAGetSchema.setAppntNo(tLCPolSchema.getAppntNo());
				tLJAGetSchema.setSumGetMoney(tLCInsureAccSchema.getInsuAccBala());
				tLJAGetSchema.setSaleChnl(tLCPolSchema.getSaleChnl());
				tLJAGetSchema.setShouldDate(tLCInsureAccSchema.getBalaDate());
				tLJAGetSchema.setEnterAccDate(tCurMakeDate);
				tLJAGetSchema.setConfDate(tCurMakeDate);
				tLJAGetSchema.setApproveCode(tLCPolSchema.getApproveCode());
				tLJAGetSchema.setApproveDate(tLCPolSchema.getApproveDate());
				tLJAGetSchema.setGetNoticeNo(tGetNoticeNo);
				tLJAGetSchema.setDrawer(tLCPolSchema.getAppntName());
				tLJAGetSchema.setDrawerID(tDrawerID);
				tLJAGetSchema.setSerialNo(tSNo);
				tLJAGetSchema.setOperator(mGlobalInput.Operator);
				tLJAGetSchema.setMakeDate(tCurMakeDate);
				tLJAGetSchema.setMakeTime(tCurMakeTime);
				tLJAGetSchema.setModifyDate(tCurMakeDate);
				tLJAGetSchema.setModifyTime(tCurMakeTime);
				tLJAGetSchema.setManageCom(tLCPolSchema.getManageCom());
				tLJAGetSchema.setAgentCom(tLCPolSchema.getAgentCom());
				tLJAGetSchema.setAgentType(tLCPolSchema.getAgentType());
				tLJAGetSchema.setAgentCode(tLCPolSchema.getAgentCode());
				tLJAGetSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
				tLJAGetSchema.setBankCode(tLCPolSchema.getGetBankCode());
				tLJAGetSchema.setBankAccNo(tLCPolSchema.getGetBankAccNo());
				tLJAGetSchema.setPolicyCom(tLCPolSchema.getManageCom());
				

				tMap.put(tLJAGetSchema, "DELETE&INSERT");

				//添加实付子表-红利给付实付表
				LJABonusGetSchema tLJABonusGetSchema = new LJABonusGetSchema();
				tLJABonusGetSchema.setActuGetNo(tActuGetNo);
				tLJABonusGetSchema.setOtherNo(tLCPolSchema.getPolNo());
				tLJABonusGetSchema.setOtherNoType("7");
				tLJABonusGetSchema.setPayMode("5");//内部转账
				tLJABonusGetSchema.setBonusYear(String
						.valueOf(tLCInsureAccSchema.getBalaDate().subSequence(0, 4)));
				tLJABonusGetSchema.setGetMoney(tLCInsureAccSchema
						.getInsuAccBala());
				tLJABonusGetSchema.setGetDate(tCurMakeDate);
				tLJABonusGetSchema.setEnterAccDate(tCurMakeDate); //修改到帐日期、核销日期与实付总表一致
				tLJABonusGetSchema.setConfDate(tCurMakeDate);
				tLJABonusGetSchema.setManageCom(tLCPolSchema.getManageCom());
				tLJABonusGetSchema.setAgentCom(tLCPolSchema.getAgentCom());
				tLJABonusGetSchema.setAgentType(tLCPolSchema.getAgentType());
				tLJABonusGetSchema.setAPPntName(tLCPolSchema.getAppntName());
				tLJABonusGetSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
				tLJABonusGetSchema.setAgentCode(tLCPolSchema.getAgentCode());
				tLJABonusGetSchema.setFeeFinaType("DJTF"); //红利退费
				tLJABonusGetSchema.setFeeOperationType("DJTF");
				tLJABonusGetSchema.setSerialNo(tSNo);
				tLJABonusGetSchema.setOperator(mGlobalInput.Operator);
				tLJABonusGetSchema.setState("0");
				tLJABonusGetSchema.setMakeDate(tCurMakeDate);
				tLJABonusGetSchema.setMakeTime(tCurMakeTime);
				tLJABonusGetSchema.setModifyDate(tCurMakeDate);
				tLJABonusGetSchema.setModifyTime(tCurMakeTime);
				tLJABonusGetSchema.setGetNoticeNo(tGetNoticeNo);				
				tLJABonusGetSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
				tLJABonusGetSchema.setContNo(tLCPolSchema.getContNo());
				tLJABonusGetSchema.setRiskCode(tLCPolSchema.getRiskCode());
				tLJABonusGetSchema.setPolNo(tLCPolSchema.getPolNo());
				tLJABonusGetSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
				
				
				tMap.put(tLJABonusGetSchema, "DELETE&INSERT");
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

				tMap.put(tLJFIGetSchema, "DELETE&INSERT");
				//添加暂交费纪录
				LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
				tLJTempFeeSchema.setTempFeeNo(tTempFeeNo);
				tLJTempFeeSchema.setTempFeeType("7");
				tLJTempFeeSchema.setRiskCode(tLCPolSchema.getRiskCode());
				tLJTempFeeSchema.setPayIntv(tLCPolSchema.getPayIntv());
				tLJTempFeeSchema.setOtherNo(tLCPolSchema.getPolNo());
				tLJTempFeeSchema.setOtherNoType("0");
				tLJTempFeeSchema.setPayMoney(tLCInsureAccSchema.getInsuAccBala());
				tLJTempFeeSchema.setPayDate(tCurMakeDate);
				tLJTempFeeSchema.setEnterAccDate(tCurMakeDate);
				tLJTempFeeSchema.setConfDate(tCurMakeDate);
				tLJTempFeeSchema.setSaleChnl(tLCPolSchema.getSaleChnl());
				tLJTempFeeSchema.setManageCom(tLCPolSchema.getManageCom());
				tLJTempFeeSchema.setAgentCom(tLCPolSchema.getAgentCom());
				tLJTempFeeSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
				tLJTempFeeSchema.setAgentType(tLCPolSchema.getAgentType());
				tLJTempFeeSchema.setAPPntName(tLCPolSchema.getAppntName());
				tLJTempFeeSchema.setAgentCode(tLCPolSchema.getAgentCode());
				tLJTempFeeSchema.setConfFlag("1");
				tLJTempFeeSchema.setSerialNo(tSNo);
				tLJTempFeeSchema.setOperator(mGlobalInput.Operator);
				tLJTempFeeSchema.setMakeDate(tCurMakeDate);
				tLJTempFeeSchema.setMakeTime(tCurMakeTime);
				tLJTempFeeSchema.setModifyDate(tCurMakeDate);
				tLJTempFeeSchema.setModifyTime(tCurMakeTime);
				tLJTempFeeSchema.setConfMakeDate(tCurMakeDate);
				tLJTempFeeSchema.setConfMakeTime(tCurMakeTime);

				tMap.put(tLJTempFeeSchema, "DELETE&INSERT");
				//添加暂交费子表
				LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
				tLJTempFeeClassSchema.setTempFeeNo(tTempFeeNo);
				//内部转账存为实付号
				tLJTempFeeClassSchema.setChequeNo(tActuGetNo);
				tLJTempFeeClassSchema.setPayMode("5");//内部转账
				tLJTempFeeClassSchema.setPayMoney(tLCInsureAccSchema
						.getInsuAccBala());
				//tLJTempFeeClassSchema.setAPPntName(tLCPolSchema.getAppntName());
				tLJTempFeeClassSchema.setPayDate(tCurMakeDate);
				tLJTempFeeClassSchema.setApproveDate(tLCPolSchema
						.getApproveDate());
				tLJTempFeeClassSchema.setConfDate(tCurMakeDate);
				tLJTempFeeClassSchema.setEnterAccDate(tCurMakeDate);
				tLJTempFeeClassSchema.setConfFlag("1");
				tLJTempFeeClassSchema.setSerialNo(tSNo);
				tLJTempFeeClassSchema.setOperator(mGlobalInput.Operator);
				tLJTempFeeClassSchema.setMakeDate(tCurMakeDate);
				tLJTempFeeClassSchema.setMakeTime(tCurMakeTime);
				tLJTempFeeClassSchema.setModifyDate(tCurMakeDate);
				tLJTempFeeClassSchema.setModifyTime(tCurMakeTime);
				tLJTempFeeClassSchema.setManageCom(tLCPolSchema.getManageCom());
				tLJTempFeeClassSchema.setConfMakeDate(tCurMakeDate);
				tLJTempFeeClassSchema.setConfMakeTime(tCurMakeTime);
				tLJTempFeeClassSchema.setPolicyCom(tLCInsureAccSchema.getManageCom());

				tMap.put(tLJTempFeeClassSchema, "DELETE&INSERT");
				//添加实收总表纪录
				LJAPaySchema tLJAPaySchema = new LJAPaySchema();
				tLJAPaySchema.setPayNo(tTempFeeNo);
				tLJAPaySchema.setIncomeNo(tLCPolSchema.getPolNo());
				tLJAPaySchema.setIncomeType("2");
				tLJAPaySchema.setAppntNo(tLCPolSchema.getAppntNo());
				tLJAPaySchema.setSumActuPayMoney(tLCInsureAccSchema
						.getInsuAccBala());
				tLJAPaySchema.setPayDate(tCurMakeDate);
				tLJAPaySchema.setEnterAccDate(tCurMakeDate);
				tLJAPaySchema.setConfDate(tCurMakeDate);
				tLJAPaySchema.setApproveCode(tLCPolSchema.getApproveCode());
				tLJAPaySchema.setApproveDate(tLCPolSchema.getApproveDate());
				tLJAPaySchema.setSerialNo(tSNo);
				tLJAPaySchema.setOperator(mGlobalInput.Operator);
				tLJAPaySchema.setMakeDate(tCurMakeDate);
				tLJAPaySchema.setMakeTime(tCurMakeTime);
				tLJAPaySchema.setModifyDate(tCurMakeDate);
				tLJAPaySchema.setModifyTime(tCurMakeTime);
				tLJAPaySchema.setGetNoticeNo(tGetNoticeNo);
				tLJAPaySchema.setManageCom(tLCPolSchema.getManageCom());
				tLJAPaySchema.setAgentCode(tLCPolSchema.getAgentCode());
				tLJAPaySchema.setAgentType(tLCPolSchema.getAgentType());
				tLJAPaySchema.setAgentGroup(tLCPolSchema.getAgentGroup());
				tLJAPaySchema.setBankCode(tLCPolSchema.getGetBankCode()); //银行编码
				tLJAPaySchema.setBankAccNo(tLCPolSchema.getGetBankAccNo()); //银行帐号
				tLJAPaySchema.setRiskCode(tLCPolSchema.getRiskCode()); // 险种编码

				tMap.put(tLJAPaySchema, "DELETE&INSERT");

				//添加实收个人子表纪录
				LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
				tLJAPayPersonSchema.setPolNo(tLCPolSchema.getPolNo());
				tLJAPayPersonSchema.setPayCount(2); //-记为续期
				tLJAPayPersonSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
				tLJAPayPersonSchema.setContNo(tLCPolSchema.getContNo());
				tLJAPayPersonSchema.setAppntNo(tLCPolSchema.getAppntNo());
				tLJAPayPersonSchema.setPayNo(tTempFeeNo);
				tLJAPayPersonSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
				if (tLCPolSchema.getGrpPolNo().equals("00000000000000000000")
						&& tLCPolSchema.getContNo().equals(
								"00000000000000000000")) {
					tLJAPayPersonSchema.setPayAimClass("1");//交费目的分类
				}
				if (!tLCPolSchema.getGrpPolNo().equals("00000000000000000000")
						&& tLCPolSchema.getContNo().equals(
								"00000000000000000000")) {
					tLJAPayPersonSchema.setPayAimClass("2");//交费目的分类
				}
				tLJAPayPersonSchema.setDutyCode("0000000000"); //责任编码
				tLJAPayPersonSchema.setPayPlanCode("00000000");//交费计划编码
				tLJAPayPersonSchema.setSumActuPayMoney(tLCInsureAccSchema
						.getInsuAccBala());
				tLJAPayPersonSchema.setSumDuePayMoney(tLCInsureAccSchema
						.getInsuAccBala());
				tLJAPayPersonSchema.setPayIntv(tLCPolSchema.getPayIntv());
				tLJAPayPersonSchema.setPayDate(tCurMakeDate);
				tLJAPayPersonSchema.setPayType("YET");
				tLJAPayPersonSchema.setEnterAccDate(tCurMakeDate);
				tLJAPayPersonSchema.setConfDate(tCurMakeDate);
				tLJAPayPersonSchema.setSerialNo(tSNo);
				tLJAPayPersonSchema.setOperator(mGlobalInput.Operator);
				tLJAPayPersonSchema.setMakeDate(tCurMakeDate);
				tLJAPayPersonSchema.setMakeTime(tCurMakeTime);
				tLJAPayPersonSchema.setModifyDate(tCurMakeDate);
				tLJAPayPersonSchema.setModifyTime(tCurMakeTime);
				tLJAPayPersonSchema.setGetNoticeNo(tGetNoticeNo);
				tLJAPayPersonSchema.setInInsuAccState("0");//转入保险帐户状态
				tLJAPayPersonSchema.setManageCom(tLCPolSchema.getManageCom());
				tLJAPayPersonSchema.setAgentCode(tLCPolSchema.getAgentCode());
				tLJAPayPersonSchema.setAgentCom(tLCPolSchema.getAgentCom());
				tLJAPayPersonSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
				tLJAPayPersonSchema.setRiskCode(tLCPolSchema.getRiskCode());

				tMap.put(tLJAPayPersonSchema, "DELETE&INSERT");
				
				String strInsuAcc = "update LCInsureAcc set LastAccBala=?LastAccBala?";
			    strInsuAcc = strInsuAcc + ",ModifyDate='?tCurMakeDate?',ModifyTime='?tCurMakeTime?',InsuAccBala=0  where polno='?polno?' and insuaccno='?insuaccno?'";
			    SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
			    sqlbv9.sql(strInsuAcc);
			    sqlbv9.put("LastAccBala", tLCInsureAccSchema.getInsuAccBala());
			    sqlbv9.put("tCurMakeDate", tCurMakeDate);
			    sqlbv9.put("tCurMakeTime", tCurMakeTime);
			    sqlbv9.put("polno", tLCPolSchema.getPolNo());
			    sqlbv9.put("insuaccno", tLCInsureAccSchema.getInsuAccNo());
			tMap.put(sqlbv9, "UPDATE");

			String strInsuAccClass = "update LCInsureAccClass set LastAccBala=?LastAccBala?";
			strInsuAccClass = strInsuAccClass + ",ModifyDate='?tCurMakeDate?',ModifyTime='?tCurMakeTime?' ,InsuAccBala=0  where polno='?polno?' and insuaccno='?insuaccno?'";
			SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
			sqlbv10.sql(strInsuAccClass);
			sqlbv10.put("LastAccBala", tLCInsureAccSchema.getInsuAccBala());
			sqlbv10.put("tCurMakeDate", tCurMakeDate);
			sqlbv10.put("tCurMakeTime", tCurMakeTime);
			sqlbv10.put("polno", tLCPolSchema.getPolNo());
			sqlbv10.put("insuaccno", tLCInsureAccSchema.getInsuAccNo());
			tMap.put(sqlbv10, "UPDATE");
			
			String tSerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);//产生流水号码

			//填充保险帐户表记价履历表
			LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
			tLCInsureAccTraceSchema.setSerialNo(tSerialNo);
			tLCInsureAccTraceSchema.setPolNo(tLCInsureAccSchema.getPolNo());
			tLCInsureAccTraceSchema.setMoneyType("DJHL");  //
			tLCInsureAccTraceSchema.setRiskCode(tLCInsureAccSchema.getRiskCode());
			tLCInsureAccTraceSchema.setOtherNo(tLCInsureAccSchema.getPolNo());
			tLCInsureAccTraceSchema.setOtherType("1");
			tLCInsureAccTraceSchema.setPayPlanCode("000000");
			tLCInsureAccTraceSchema.setAccAscription("1");
			tLCInsureAccTraceSchema.setMoney(-tLCInsureAccSchema.getInsuAccBala());
			tLCInsureAccTraceSchema.setContNo(tLCInsureAccSchema.getContNo());
			tLCInsureAccTraceSchema.setGrpContNo(tLCInsureAccSchema.getGrpContNo());
			tLCInsureAccTraceSchema.setGrpPolNo(tLCInsureAccSchema.getGrpPolNo());
			tLCInsureAccTraceSchema.setInsuAccNo(tLCInsureAccSchema.getInsuAccNo());
			tLCInsureAccTraceSchema.setState(tLCInsureAccSchema.getState());
			tLCInsureAccTraceSchema.setManageCom(tLCInsureAccSchema.getManageCom());
			tLCInsureAccTraceSchema.setOperator(tLCInsureAccSchema.getOperator());
			tLCInsureAccTraceSchema.setMakeDate(tCurMakeDate);
			tLCInsureAccTraceSchema.setMakeTime(tCurMakeTime);
			tLCInsureAccTraceSchema.setModifyDate(tCurMakeDate);
			tLCInsureAccTraceSchema.setModifyTime(tCurMakeTime);
			tLCInsureAccTraceSchema.setPayDate(tCurMakeDate);
			tLCInsureAccTraceSchema.setFeeCode("000000");
			tMap.put(tLCInsureAccTraceSchema, "DELETE&INSERT");
			}
		}

		// 更新个人保单表
		tSumLeavmoney += tLCPolSchema.getLeavingMoney();

		String strSQL2 = "update lcpol set LeavingMoney=?tSumLeavmoney?";
		strSQL2 = strSQL2 + ",ModifyDate='?tCurMakeDate?',ModifyTime='?tCurMakeTime?'  where polno='?polno?' ";
		SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
		sqlbv11.sql(strSQL2);
		sqlbv11.put("tSumLeavmoney", tSumLeavmoney);
		sqlbv11.put("tCurMakeDate", tCurMakeDate);
		sqlbv11.put("tCurMakeTime", tCurMakeTime);
		sqlbv11.put("polno", tLCPolSchema.getPolNo());

		tMap.put(sqlbv11, "UPDATE");


		
		
		VData tResult = new VData();
		tResult.clear();
		tResult.add(tMap);
		if (tResult.size() > 0) {
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(tResult, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				return false;
			}
		}

		return true;
	}
	/**
	 * 预计算指定会计年度分红产生的利息
	 * @param cPolNo ： 保单号
	 * @param cBonusMoney ： 红利金额
	 * @param cSGetDate ： 应领日期
	 * @return
	 */
	private double calInterest( LCInsureAccSchema tLCInsureAccSchema,String cSEndDate) {

		//只有现金和累计生息进行利息的计算
		double tInterest=0.0,tRate=0.025;
		AccountManage tAccountManage = new AccountManage();
		
		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
		LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
		String tSQL = "select * from LCInsureAccTrace where polno='?polno?' and paydate >'?BalaDate?' and paydate <='?cSEndDate?'";
		logger.debug(tSQL);
		SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
		sqlbv12.sql(tSQL);
		sqlbv12.put("polno", tLCInsureAccSchema.getPolNo());
		sqlbv12.put("BalaDate", tLCInsureAccSchema.getBalaDate());
		sqlbv12.put("cSEndDate", cSEndDate);
		tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sqlbv12);
		// 如果发生了帐户进出则要逐个结息
		if (tLCInsureAccTraceSet.size() < 1) {
			tInterest = 0.0;
		} else {
			String tLastBalaDate;
			for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {
				double tGetMoney = 0.0, tTraceInterest = 0.0;
				LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
				tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(i);
				tGetMoney = tLCInsureAccTraceSchema.getMoney();
				tLastBalaDate = tLCInsureAccTraceSchema.getPayDate();

				tTraceInterest =  tAccountManage.getInterest(tGetMoney, tLastBalaDate,
						cSEndDate, tRate, "Y", "C", "D");

				tGetMoney += tTraceInterest;
				// 获得所有费用的利息和
				tInterest += tTraceInterest;
			}
		}


		return tInterest;
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

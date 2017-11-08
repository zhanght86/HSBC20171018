package com.sinosoft.lis.bq;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.bl.LPContBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDiscountDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.db.LMDutyDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LMRiskFeeDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LMDutySchema;
import com.sinosoft.lis.schema.LMRiskFeeSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDiscountSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.tb.CalBL;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vschema.LCDiscountSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LMRiskFeeSet;
import com.sinosoft.lis.vschema.LPDiscountSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 团体保全集体下个人功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @Modified by Lizhuo in 2005 for NCL
 * @version 1.1
 */
public class PEdorAADetailBL {
private static Logger logger = Logger.getLogger(PEdorAADetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	private BqCalBase mBqCalBase = new BqCalBase();

	/** 数据操作字符串 */
	private String mOperate;
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPDutySchema mLPDutySchema = new LPDutySchema();
	private LCDutySchema mLCDutySchema = new LCDutySchema();
	private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
	private LCContSchema mLCContSchema = new LCContSchema();
	/**保单折扣表*/
	private LPDiscountSet mLPDiscountSet = new LPDiscountSet();
	private String CValiDate;
	private String EndDate;
	private boolean mFlag; // 30天续保

	private double mGetMoney = 0.0;
	private double JKAdd = 0;
	private double ZYAdd = 0;


	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private Reflections ref = new Reflections();
	private MMap map = new MMap();

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();

	private String theCurrentTime = PubFun.getCurrentTime();
	public PEdorAADetailBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}
		// 数据校验操作
		if (!checkData()) {
			return false;
		}
		logger.debug("---End checkData---");
		// 数据准备操作

		if (!dealData()) {
			return false;
		}

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mLPDutySchema = (LPDutySchema) mInputData.getObjectByObjectName(
					"LPDutySchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "PEdorAADetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 校验传入的数据的合法性
	 * 
	 * @return
	 */
	private boolean checkData() {
		
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		mLPEdorItemSchema.setSchema(tLPEdorItemDB.query().get(1));
		
		if (mLPEdorItemSchema.getEdorState().trim().equals("2")) {
			CError tError = new CError();
			tError.moduleName = "PInsuredBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "该保全已经申请确认不能修改!";
			logger.debug("------" + tError);
			this.mErrors.addOneError(tError);
			return false;
		}
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mLPDutySchema.getPolNo());
		if (!tLCPolDB.getInfo()) {
			mErrors.addOneError("查询险种表失败!");
			return false;
		}
		mLCPolSchema = tLCPolDB.getSchema();

		//保单生效日为保全申请次日
		CValiDate = mLPEdorItemSchema.getEdorValiDate();
		//得责任止期
		EndDate = getNewEndDate(mLCPolSchema.getCValiDate());

		try {
			double input = mLPDutySchema.getAmnt();
			double inputmult = mLPDutySchema.getMult();
			if (mLPDutySchema.getAmnt() == 0 && mLPDutySchema.getMult() == 0) {
				mErrors.addOneError("未填写加保金额，请重新填写!");
				return false;
			}
		} catch (Exception e) {
			mErrors.addOneError("填写金额不规范!");
			return false;
		}

		String str = "select * from lpprem where edortype = 'AA' and edorno = '"
				+ "?edorno?"
				+ "' and polno <> '"
				+ "?polno?" + "'";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(str);
		sbv.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv.put("polno", mLPDutySchema.getPolNo());
		LPPremDB jLPPremDB = new LPPremDB();
		LPPremSet jLPPremSet = new LPPremSet();
		jLPPremSet = jLPPremDB.executeQuery(sbv);
		if (jLPPremSet != null && jLPPremSet.size() > 0) {
			for (int j = 1; j <= jLPPremSet.size(); j++) {
				mGetMoney += jLPPremSet.get(j).getPrem(); // 累计一次附加险保全处理的金额
			}
		}

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询保单信息失败!");
			return false;
		}
		mLCContSchema.setSchema(tLCContDB.getSchema());

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return
	 */
	private boolean dealData() {

		// 根据polno dutycode准备LCDutyBLSet 产生DutyCode
		String DutyCode = "";
		String i_sql = "select DutyCode from LCDuty where PolNo = '"
				+ "?PolNo?"
				+ "' order by makedate desc,maketime desc";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(i_sql);
		sqlbv.put("PolNo", mLPDutySchema.getPolNo());
		ExeSQL i_ExeSQL = new ExeSQL();
		DutyCode = i_ExeSQL.getOneValue(sqlbv);
		if (DutyCode.length() == 6) {
			DutyCode = DutyCode + "01";
		} else {
			DutyCode = String.valueOf(Integer.parseInt(DutyCode) + 1);
		}
		mLPDutySchema.setDutyCode(DutyCode);

		// 如果按份数销售，将份数转化成保额
		boolean tMult = false; // 按份数销售标记
		logger.debug("getMult" + mLPDutySchema.getMult());
		if (mLPDutySchema.getMult() > 0) {
			LCDutyDB tLCDutyDB = new LCDutyDB();
			LCDutySet tLCDutySet = new LCDutySet();
			String strSQL = "select * from lcduty where polno = '"
					+ "?polno?" + "' order by makedate";
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(strSQL);
			sbv.put("polno", mLPDutySchema.getPolNo());
			tLCDutySet = tLCDutyDB.executeQuery(sbv);
			if (tLCDutySet.size() < 1) {
				CError tError = new CError();
				tError.buildErr(this, "查询责任表失败!");
				return false;
			}
			LCDutySchema tLCDutySchema = new LCDutySchema();
			tLCDutySchema = tLCDutySet.get(1);
			LMDutyDB tLMDutyDB = new LMDutyDB();
			tLMDutyDB.setDutyCode(tLCDutySchema.getDutyCode());
			if (!tLMDutyDB.getInfo()) {
				CError tError = new CError();
				tError.buildErr(this, "查询责任表失败!");
				return false;
			}
			LMDutySchema tLMDutySchema = new LMDutySchema();
			tLMDutySchema = tLMDutyDB.getSchema();
			if (!tLMDutySchema.getAmntFlag().equals("2")) {
				CError tError = new CError();
				tError.buildErr(this, "此险种保额标记不一致!");
				return false;
			}
			tMult = true;
			mLPDutySchema.setAmnt(mLPDutySchema.getMult()
					* tLMDutySchema.getVPU());
		}

		// 由于附加险只对应一类责任，所以用PolNo查询(多类责任的加费信息要按DutyCode处理)
		LCDutyDB aLCDutyDB = new LCDutyDB();
		LCDutySet aLCDutySet = new LCDutySet();
		String strSQL = "select * from lcduty where polno = '"
				+ "?polno?" + "' order by makedate";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(strSQL);
		sbv.put("polno", mLPDutySchema.getPolNo());
		LCDutySchema aLCDutySchema = new LCDutySchema();
		aLCDutySet = aLCDutyDB.executeQuery(sbv);
		aLCDutySchema = aLCDutySet.get(1);
		mLCDutySchema.setSchema(aLCDutySchema);
		// double aJKRiskCode = AddFeeRiskCode(aLCDutySchema.getDutyCode(),
		// "01"); //健康加费评点
		// double aZYRiskCode = AddFeeRiskCode(aLCDutySchema.getDutyCode(),
		// "02"); //职业加费评点
		// if (aJKRiskCode == -2 || aZYRiskCode == -2) {
		// CError.buildErr(this, "查询风险评点错误!");
		// return false;
		// }
		// 加费信息－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		// if(aJKRiskCode != -1){
		// tJKAddFee = CreateAddFee(aJKRiskCode,"01");
		// if(tJKAddFee == 0){
		// CError.buildErr(this, "处理健康加费信息失败!");
		// return false;
		// }
		// }
		// if(aZYRiskCode != -1){
		// tZYAddFee = CreateAddFee(aJKRiskCode,"02");
		// if(tZYAddFee == 0){
		// CError.buildErr(this, "处理职业加费信息失败!");
		// return false;
		// }
		// }
		// LCDutyDB tLCDutyDB = new LCDutyDB();
		// LCDutySet tLCDutySet = new LCDutySet();
		// LCDutySchema tLCDutySchema = new LCDutySchema();
		// tLCDutyDB.setPolNo(mLPDutySchema.getPolNo());
		// tLCDutySet = tLCDutyDB.query();
		// tLCDutySchema = tLCDutySet.get(1); //一种附加险只有一种Duty
		String str1 = aLCDutySchema.getInsuYearFlag();
		if (PubFun.calInterval(mLPEdorItemSchema.getEdorValiDate(),
				aLCDutySchema.getEndDate(), "D") < 15) {
			aLCDutySchema.setInsuYear(PubFun.calInterval(mLPEdorItemSchema
					.getEdorValiDate(), aLCDutySchema.getEndDate(), "D"));
			aLCDutySchema.setInsuYearFlag("D");
		} else {
			aLCDutySchema.setInsuYear(PubFun.calInterval(mLPEdorItemSchema
					.getEdorValiDate(), aLCDutySchema.getEndDate(), "M"));
			aLCDutySchema.setInsuYearFlag("M");
		}
		aLCDutySchema.setGetStartDate(mLPEdorItemSchema.getEdorValiDate());
		aLCDutySchema.setDutyCode(mLPDutySchema.getDutyCode());
		aLCDutySchema.setAmnt(mLPDutySchema.getAmnt());
		aLCDutySchema.setMult(mLPDutySchema.getMult());
		aLCDutySchema.setCalRule("0");
		aLCDutySchema.setEndDate(EndDate);
		aLCDutySet.set(1, aLCDutySchema);
		LCDutyBLSet tLCDutyBLSet = new LCDutyBLSet();
		tLCDutyBLSet.set(aLCDutySet); // 新加保额计算保费准备

		// 根据polno查polschema,准备LCPolBL
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolDB.setPolNo(mLPDutySchema.getPolNo());
		tLCPolSet = tLCPolDB.query();
		tLCPolSchema = tLCPolSet.get(1);
		LCPolBL tLCPolBL = new LCPolBL();
		tLCPolBL.setSchema(tLCPolSchema);
		// 准备calFlag
		String calFlag = mLPEdorItemSchema.getEdorType();
		// 调用calbl类计算prem和get
		LCGetSchema tLCGetSchema = new LCGetSchema();
		LCPremSchema tLCPremSchema = new LCPremSchema();
		LCDutySchema bLCDutySchema = new LCDutySchema();
		LCPolSchema bLCPolSchema = new LCPolSchema();

		// 计算短期费率
		double aRate=1;
		if (mFlag == false) {
			logger.debug(mLPEdorItemSchema.getEdorValiDate());
			logger.debug(aLCDutySchema.getEndDate());
			int tAAYears = PubFun.calInterval2(mLPEdorItemSchema
					.getEdorValiDate(), aLCDutySchema.getEndDate(), "M");
			LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
			LMEdorCalSet tLMEdorCalSet = new LMEdorCalSet();
			tLMEdorCalDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLMEdorCalDB.setCalType("AddRate");
			tLMEdorCalDB.setRiskCode(mLCPolSchema.getRiskCode());
			tLMEdorCalDB.setDutyCode("000000");
			tLMEdorCalSet = tLMEdorCalDB.query();
			if (tLMEdorCalSet.size() < 1) {
				mErrors.copyAllErrors(tLMEdorCalDB.mErrors);
				mErrors.addOneError("此险种不允许进行附加险加保!");
				return false;
			}

			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
			tLCInsuredDB.setContNo(mLCContSchema.getContNo());
			tLCInsuredDB.setInsuredNo(mLCContSchema.getInsuredNo());
			if (!tLCInsuredDB.getInfo()) {
				mErrors.addOneError("查询被保人信息失败!");
				return false;
			}
			tLCInsuredSchema = tLCInsuredDB.getSchema();
			String tJob = tLCInsuredSchema.getOccupationType();

			int tAppAge = PubFun.calInterval(mLCContSchema.getAppntBirthday(),
					mLPEdorItemSchema.getEdorValiDate(), "Y");

			BqCalBase tBqCalBase = new BqCalBase();
			tBqCalBase.setAAYears(tAAYears);
			tBqCalBase.setGrpPolNo(tLCPolSchema.getGrpPolNo());
			tBqCalBase.setInsuYear(tLCPolSchema.getInsuYear());
			tBqCalBase.setJob(tJob);
			tBqCalBase.setAppAge(tAppAge);
			tBqCalBase.setContNo(tLCPolSchema.getContNo());
			tBqCalBase.setPolNo(mLPDutySchema.getPolNo());
			tBqCalBase.setInsuYearFlag("D");
			tBqCalBase.setInsuYear(PubFun.calInterval(CValiDate, EndDate, "D"));
			BqCalBL tBqCalBL = new BqCalBL();
			tBqCalBL.setLPEdorItemSchema(mLPEdorItemSchema);
			aRate = tBqCalBL.calChgMoney(tLMEdorCalSet.get(1).getCalCode(),
					tBqCalBase); // 短期费率
			if(aRate==0)
			{
				mErrors.addOneError("计算短期费率失败!");
				return false;
			}
		} else {
			aRate = 1;
		}
		// 计算基本保费信息
		CalBL aCalBL = new CalBL(tLCPolBL, tLCDutyBLSet, calFlag);
		if (!aCalBL.calPol()) {
			logger.debug("相关表信息不全，保费计算错误!");
			return false;
		}
		bLCPolSchema.setSchema(aCalBL.getLCPol().getSchema()); // 计算后的LCPol
		bLCDutySchema.setSchema(aCalBL.getLCDuty().get(1)); // 计算后的新的Duty
		bLCDutySchema.setStandPrem(bLCDutySchema.getStandPrem() * aRate); // 转换标准保费
		tLCGetSchema.setSchema(aCalBL.getLCGet().get(1)); // 计算后的新的Get
		tLCPremSchema.setSchema(aCalBL.getLCPrem().get(1)); // 计算后的新的基本保费项LCPrem

		// 更新LPPrem表
		LPPremSet tLPPremSet = new LPPremSet();

		// 如有加费信息，进行加费信息计算
		LCPremDB iLCPremDB = new LCPremDB();
		LCPremSet iLCPremSet = new LCPremSet();
		LPPremSchema iLPPremSchema = new LPPremSchema();
		String Str11 = "select * from lcprem where polno = '"
				+ "?polno?" + "' and dutycode = '"
				+ "?dutycode?"
				+ "' and payplantype in ('01','03') order by makedate desc";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(Str11);
		sbv1.put("polno", mLCDutySchema.getPolNo());
		sbv1.put("dutycode", mLCDutySchema.getDutyCode());
		iLCPremSet = iLCPremDB.executeQuery(sbv1);
		if (iLCPremSet != null && iLCPremSet.size() > 0) {
			String Str111 = "select max(payplancode) + 1 from lcprem where polno = '"
					+ "?polno?"
					+ "' and dutycode = '"
					+ "?dutycode?"
					+ "' and payplantype in ('01','03') order by makedate desc";
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql(Str111);
			sbv2.put("polno", mLCDutySchema.getPolNo());
			sbv2.put("dutycode", mLCDutySchema.getDutyCode());
			ExeSQL es1 = new ExeSQL();
			SSRS ss1 = new SSRS();
			ss1 = es1.execSQL(sbv2);
			if (ss1 == null || ss1.getMaxRow() < 1) {
				mErrors.addOneError("查询PayPlanCode失败!");
				return false;
			}
			iLPPremSchema = new LPPremSchema();
			ref.transFields(iLPPremSchema, iLCPremSet.get(1));
			if (tMult == true) {
				iLPPremSchema.setPrem((iLPPremSchema.getPrem()
						* mLPDutySchema.getMult() / mLCDutySchema.getMult())
						* aRate);
			} else {
				iLPPremSchema.setPrem((iLPPremSchema.getPrem()
						* mLPDutySchema.getAmnt() / mLCDutySchema.getAmnt())
						* aRate);
			}
			JKAdd = iLPPremSchema.getPrem();
			iLPPremSchema.setSumPrem(iLPPremSchema.getPrem());
			iLPPremSchema.setPayTimes(1);
			iLPPremSchema.setDutyCode(bLCDutySchema.getDutyCode());
			iLPPremSchema.setPayStartDate(CValiDate);
			iLPPremSchema.setPayEndDate(EndDate);
			iLPPremSchema.setPaytoDate(EndDate);
			iLPPremSchema.setStandPrem(iLPPremSchema.getPrem());
			iLPPremSchema.setMakeDate(theCurrentDate);
			iLPPremSchema.setMakeTime(theCurrentTime);
			iLPPremSchema.setModifyDate(theCurrentDate);
			iLPPremSchema.setModifyTime(theCurrentTime);
			iLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			iLPPremSchema.setPayPlanCode("0000000" + ss1.GetText(1, 1));
			iLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPremSet.add(iLPPremSchema);
		}
		String Str22 = "select * from lcprem where polno = '"
				+ "?polno?" + "' and dutycode = '"
				+ "?dutycode?"
				+ "' and payplantype in ('02','04') order by makedate desc";
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(Str22);
		sbv3.put("polno", mLCDutySchema.getPolNo());
		sbv3.put("dutycode", mLCDutySchema.getDutyCode());
		iLCPremSet.clear();
		iLCPremSet = iLCPremDB.executeQuery(sbv3);
		if (iLCPremSet != null && iLCPremSet.size() > 0) {
			iLPPremSchema = new LPPremSchema();
			ref.transFields(iLPPremSchema, iLCPremSet.get(1));
			String Str222 = "select max(payplancode) + 1 from lcprem where polno = '"
					+ "?polno?"
					+ "' and dutycode = '"
					+ "?dutycode?"
					+ "' and payplantype in ('02','04') order by makedate desc";
			SQLwithBindVariables sbv4=new SQLwithBindVariables();
			sbv4.sql(Str222);
			sbv4.put("polno", mLCDutySchema.getPolNo());
			sbv4.put("dutycode", mLCDutySchema.getDutyCode());
			ExeSQL es1 = new ExeSQL();
			SSRS ss2 = new SSRS();
			ss2 = es1.execSQL(sbv4);
			if (ss2 == null || ss2.getMaxRow() < 1) {
				mErrors.addOneError("查询PayPlanCode失败!");
				return false;
			}
			if (tMult == true) {
				iLPPremSchema.setPrem((iLPPremSchema.getPrem()
						* mLPDutySchema.getMult() / mLCDutySchema.getMult())
						* aRate);
			} else {
				iLPPremSchema.setPrem((iLPPremSchema.getPrem()
						* mLPDutySchema.getAmnt() / mLCDutySchema.getAmnt())
						* aRate);
			}
			ZYAdd = iLPPremSchema.getPrem();
			iLPPremSchema.setSumPrem(iLPPremSchema.getPrem());
			iLPPremSchema.setPayTimes(1);
			iLPPremSchema.setDutyCode(bLCDutySchema.getDutyCode());
			iLPPremSchema.setPayStartDate(CValiDate);
			iLPPremSchema.setPayEndDate(EndDate);
			iLPPremSchema.setPaytoDate(EndDate);
			iLPPremSchema.setStandPrem(iLPPremSchema.getPrem());
			iLPPremSchema.setPayPlanCode("0000000" + ss2.GetText(1, 1));
			iLPPremSchema.setMakeDate(theCurrentDate);
			iLPPremSchema.setMakeTime(theCurrentTime);
			iLPPremSchema.setModifyDate(theCurrentDate);
			iLPPremSchema.setModifyTime(theCurrentTime);
			iLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			iLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPremSet.add(iLPPremSchema);
		}

		// 调用cal one duty后更新lpduty表
		LPDutySchema tLPDutySchema = new LPDutySchema();
		ref.transFields(tLPDutySchema, bLCDutySchema);
		tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPDutySchema.setDutyCode(mLPDutySchema.getDutyCode());
		if (tMult) {
			tLPDutySchema.setMult(mLPDutySchema.getMult());
		}
		tLPDutySchema.setCValiDate(CValiDate);
		tLPDutySchema.setStandPrem(aRate * bLCDutySchema.getPrem());
		tLPDutySchema.setPrem(aRate * bLCDutySchema.getPrem() + JKAdd + ZYAdd);
		tLPDutySchema.setSumPrem(tLPDutySchema.getPrem());
		tLPDutySchema.setGetStartDate(CValiDate);
		tLPDutySchema.setFirstPayDate(mLPEdorItemSchema.getEdorValiDate());
		tLPDutySchema.setInsuYear(PubFun.calInterval(mLPEdorItemSchema
				.getEdorValiDate(), aLCDutySchema.getEndDate(), str1));
		tLPDutySchema.setEndDate(EndDate);
		mGetMoney += tLPDutySchema.getPrem();

		// 更新lppol表
		LPPolSchema tLPPolSchema = new LPPolSchema();
		LCPolDB aLCPolDB = new LCPolDB();
		LCPolSet aLCPolSet = new LCPolSet();
		LCPolSchema aLCPolSchema = new LCPolSchema();
		aLCPolDB.setPolNo(mLPDutySchema.getPolNo());
		aLCPolSet = aLCPolDB.query();
		if (aLCPolSet.size() != 1) {
			CError.buildErr(this, "查询险种表错误!");
			return false;
		}
		aLCPolSchema = aLCPolSet.get(1);
		ref.transFields(tLPPolSchema, aLCPolSchema);
		if (tMult) {
			tLPPolSchema.setMult(aLCPolSchema.getMult()
					+ mLPDutySchema.getMult());
		}
		tLPPolSchema.setStandPrem(aLCPolSchema.getStandPrem()
				+ bLCPolSchema.getStandPrem());
		tLPPolSchema.setPrem(aLCPolSchema.getPrem() + bLCPolSchema.getPrem());
		tLPPolSchema.setSumPrem(aLCPolSchema.getSumPrem()
				+ bLCPolSchema.getPrem());
		tLPPolSchema.setAmnt(aLCPolSchema.getAmnt() + tLPDutySchema.getAmnt());
		tLPPolSchema.setRiskAmnt(aLCPolSchema.getRiskAmnt()
				+ bLCPolSchema.getRiskAmnt());
		tLPPolSchema.setModifyDate(theCurrentDate);
		tLPPolSchema.setModifyTime(theCurrentTime);
		tLPPolSchema.setPaytoDate(EndDate);
		tLPPolSchema.setPayEndDate(EndDate);
		tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());

		// 基本保费
		LPPremSchema tLPPremSchema = new LPPremSchema();
		ref.transFields(tLPPremSchema, tLCPremSchema);
		tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPremSchema.setOperator(mGlobalInput.Operator);
		tLPPremSchema.setPrem(tLPDutySchema.getStandPrem());
		tLPPremSchema.setStandPrem(tLPDutySchema.getStandPrem());
		tLPPremSchema.setSumPrem(tLPDutySchema.getStandPrem());
		tLPPremSchema.setPayTimes(1);
		tLPPremSchema.setPayStartDate(CValiDate);
		tLPPremSchema.setPayEndDate(EndDate);
		tLPPremSchema.setPaytoDate(EndDate);
		tLPPremSchema.setMakeDate(theCurrentDate);
		tLPPremSchema.setMakeTime(theCurrentTime);
		tLPPremSchema.setModifyDate(theCurrentDate);
		tLPPremSchema.setModifyTime(theCurrentTime);
		tLPPremSet.add(tLPPremSchema);

		// 更新LPGet表
		LPGetSchema tLPGetSchema = new LPGetSchema();
		ref.transFields(tLPGetSchema, tLCGetSchema);
		tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPGetSchema.setOperator(mGlobalInput.Operator);
		tLPGetSchema.setGetStartDate(CValiDate);
		tLPGetSchema.setGetEndDate(EndDate);
		tLPGetSchema.setMakeDate(theCurrentDate);
		tLPGetSchema.setMakeTime(theCurrentTime);
		tLPGetSchema.setModifyDate(theCurrentDate);
		tLPGetSchema.setModifyTime(theCurrentTime);

		// 生成批改补退费表
		LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		ref.transFields(tLJSGetEndorseSchema, tLPPolSchema);
		tLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo()); // 给付通知书号码
		tLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
		tLJSGetEndorseSchema.setGrpContNo(mLCPolSchema.getGrpContNo());
		tLJSGetEndorseSchema.setGrpPolNo(mLCPolSchema.getGrpPolNo());
		tLJSGetEndorseSchema.setPolNo(mLCPolSchema.getPolNo());
		tLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema
				.getEdorType());
		String feeFinaType = BqCalBL.getFinType(
				mLPEdorItemSchema.getEdorType(), "BF", mLPEdorItemSchema
						.getPolNo());
		if (feeFinaType.equals("")) {
			CError tError = new CError();
			tError.errorMessage = "在LDCode1中缺少保全财务接口转换类型编码!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLJSGetEndorseSchema.setFeeFinaType(feeFinaType);
		tLJSGetEndorseSchema.setPayPlanCode(tLPPremSchema.getPayPlanCode());
		tLJSGetEndorseSchema.setDutyCode(mLPDutySchema.getDutyCode());
		tLJSGetEndorseSchema.setOtherNo(mLPDutySchema.getDutyCode());
		tLJSGetEndorseSchema.setOtherNoType("3");
		tLJSGetEndorseSchema.setManageCom(mLCContSchema.getManageCom());

		LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
		tLJSGetEndorseDB.setSchema(tLJSGetEndorseSchema);
		if (tLJSGetEndorseDB.getInfo()) {
			tLJSGetEndorseSchema.setAgentCode(tLJSGetEndorseDB.getAgentCode());
			tLJSGetEndorseSchema.setAgentCom(tLJSGetEndorseDB.getAgentCom());
			tLJSGetEndorseSchema
					.setAgentGroup(tLJSGetEndorseDB.getAgentGroup());
			tLJSGetEndorseSchema.setAgentType(tLJSGetEndorseDB.getAgentType());
			tLJSGetEndorseSchema.setMakeDate(tLJSGetEndorseDB.getMakeDate());
			tLJSGetEndorseSchema.setMakeTime(tLJSGetEndorseDB.getMakeTime());
		} else {
			tLJSGetEndorseSchema.setMakeDate(theCurrentDate);
			tLJSGetEndorseSchema.setMakeTime(theCurrentTime);
		}
		tLJSGetEndorseSchema.setContNo(mLPEdorItemSchema.getContNo());
		tLJSGetEndorseSchema.setFeeOperationType(mLPEdorItemSchema
				.getEdorType());
		tLJSGetEndorseSchema.setGetDate(mLPEdorItemSchema.getEdorValiDate());
		tLJSGetEndorseSchema.setGetFlag("0");
		tLJSGetEndorseSchema.setRiskCode(tLPPolSchema.getRiskCode());
		tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
		// tLJSGetEndorseSchema.setManageCom(mGlobalInput.ManageCom);
		tLJSGetEndorseSchema.setModifyDate(theCurrentDate);
		tLJSGetEndorseSchema.setModifyTime(theCurrentTime);
		tLJSGetEndorseSchema.setGetMoney(tLPDutySchema.getPrem());
		tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_Prem);

		// 生成个人应收表
		if (tLPPremSet.size() < 1) {
			CError.buildErr(this, "添加保费项信息错误!");
			return false;
		}
		LPPremSchema dLPPremSchema = new LPPremSchema();
		LJSPayPersonSchema dLJSPayPersonSchema = new LJSPayPersonSchema();
		LJSGetEndorseSchema dLJSGetEndorseSchema;
		for (int i = 1; i <= tLPPremSet.size(); i++) {
			dLJSPayPersonSchema = new LJSPayPersonSchema();
			dLPPremSchema = tLPPremSet.get(i);
			dLJSPayPersonSchema.setPolNo(dLPPremSchema.getPolNo());
			dLJSPayPersonSchema.setGrpContNo(mLCPolSchema.getGrpContNo());
			dLJSPayPersonSchema.setGrpPolNo(mLCPolSchema.getGrpPolNo());
			dLJSPayPersonSchema.setPayCount(1);
			dLJSPayPersonSchema.setGrpContNo(dLPPremSchema.getGrpContNo());
			dLJSPayPersonSchema.setGrpPolNo(bLCPolSchema.getGrpPolNo());
			dLJSPayPersonSchema.setContNo(dLPPremSchema.getContNo());
			dLJSPayPersonSchema.setManageCom(mLCContSchema.getManageCom());
			dLJSPayPersonSchema.setOperator(mGlobalInput.Operator);
			dLJSPayPersonSchema.setRiskCode(bLCPolSchema.getRiskCode());
			dLJSPayPersonSchema.setAppntNo(bLCPolSchema.getAppntNo());
			dLJSPayPersonSchema.setPayAimClass("1");
			dLJSPayPersonSchema.setDutyCode(dLPPremSchema.getDutyCode());
			dLJSPayPersonSchema.setPayPlanCode(dLPPremSchema.getPayPlanCode());
			dLJSPayPersonSchema.setSumDuePayMoney(dLPPremSchema.getPrem());
			dLJSPayPersonSchema.setSumActuPayMoney(dLPPremSchema.getPrem());
			dLJSPayPersonSchema.setPayIntv("0");
			dLJSPayPersonSchema.setPayDate(mLPEdorItemSchema.getEdorValiDate());
			dLJSPayPersonSchema.setPayType(mLPEdorItemSchema.getEdorType());
			dLJSPayPersonSchema.setMakeDate(theCurrentDate);
			dLJSPayPersonSchema.setMakeTime(theCurrentTime);
			dLJSPayPersonSchema.setLastPayToDate(mLPEdorItemSchema
					.getEdorValiDate());
			dLJSPayPersonSchema.setCurPayToDate(EndDate);
			dLJSPayPersonSchema.setModifyDate(theCurrentDate);
			dLJSPayPersonSchema.setModifyTime(theCurrentTime);
			dLJSPayPersonSchema.setAgentCode(mLCContSchema.getAgentCode());
			dLJSPayPersonSchema.setAgentGroup(mLCContSchema.getAgentGroup());
			dLJSPayPersonSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
			dLJSPayPersonSchema.setCurrency(mLCPolSchema.getCurrency());
	          //营改增 add zhangyingfeng 2016-07-14
	          //价税分离 计算器
	          TaxCalculator.calBySchema(dLJSPayPersonSchema);
	          //end zhangyingfeng 2016-07-14
			mLJSPayPersonSet.add(dLJSPayPersonSchema);

			// LJSPayPerson与LJSGetEndorse数据一一对应，以便进行核销
			dLJSGetEndorseSchema = new LJSGetEndorseSchema();
			dLJSGetEndorseSchema.setSchema(tLJSGetEndorseSchema);
			dLJSGetEndorseSchema.setDutyCode(dLPPremSchema.getDutyCode());
			dLJSGetEndorseSchema.setPayPlanCode(dLPPremSchema.getPayPlanCode());
			dLJSGetEndorseSchema.setGetMoney(dLPPremSchema.getPrem());
			if ("01".equals(dLPPremSchema.getPayPlanType())
					|| "03".equals(dLPPremSchema.getPayPlanType())) {
				dLJSGetEndorseSchema
						.setSubFeeOperationType(BqCode.Pay_InsurAddPremHealth);
			} else if ("02".equals(dLPPremSchema.getPayPlanType())
					|| "04".equals(dLPPremSchema.getPayPlanType())) {
				dLJSGetEndorseSchema
						.setSubFeeOperationType(BqCode.Pay_InsurAddPremOccupation);
			}
	          //营改增 add zhangyingfeng 2016-07-14
	          //价税分离 计算器
	          TaxCalculator.calBySchema(dLJSGetEndorseSchema);
	          //end zhangyingfeng 2016-07-14
			tLJSGetEndorseSet.add(dLJSGetEndorseSchema);
		}

		//************增加折扣处理 ****gaoph 20110125*****************start********************
		LCDiscountSet tLCDiscountSet = new LCDiscountSet();
		LCDiscountDB tLCDiscountDB = new LCDiscountDB();
		tLCDiscountDB.setPolNo(tLPPolSchema.getPolNo());
		tLCDiscountSet = tLCDiscountDB.query();
		if(tLCDiscountSet!=null && tLCDiscountSet.size()>0)
		{
			LPDiscountSet tLPDiscountSet = new LPDiscountSet();
			for(int i=1;i<=tLCDiscountSet.size();i++)
			{
				LPDiscountSchema tLPDiscountSchema = new LPDiscountSchema();
				ref.transFields(tLPDiscountSchema, tLCDiscountSet.get(i));
				tLPDiscountSet.add(tLPDiscountSchema);
			}
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("PayCount","1");
			tTransferData.setNameAndValue("PayIntv", String.valueOf(tLPPolSchema.getPayIntv()));
			tTransferData.setNameAndValue("Operator",this.mGlobalInput.Operator);
			PEdorDiscountCalBL tDiscountCalBL = new PEdorDiscountCalBL();
			VData tzkVData = new VData();
			//LPPol的保额取此次增加的保额，计算使用
			LPPolSchema tempLPPolSchema =tLPPolSchema.getSchema();
			tempLPPolSchema.setPrem(tLPPolSchema.getPrem()-aLCPolSchema.getPrem());
			tzkVData.add(tempLPPolSchema);
			tzkVData.add(tLPPremSet);//本次新增的lpprem
			tzkVData.add(tLPDiscountSet);
			tzkVData.add(mLPEdorItemSchema);
			tzkVData.add(tTransferData);
			//得到该保单折扣减去的钱 ，为负值
			if(!tDiscountCalBL.calculate(tzkVData))
			{
				CError.buildErr(this, "折扣计算失败！");
				return false;
			}
			
			//得到折扣和应收子表数据
			VData rVData = tDiscountCalBL.getResult();
			LJSPayPersonSet tLJSPayPersonSet = (LJSPayPersonSet)rVData.getObjectByObjectName("LJSPayPersonSet", 0);
			if(tLJSPayPersonSet!=null)
			{
				// 添加保费LJSPayPerson和LJSGetEndorse
		          //营改增 add zhangyingfeng 2016-07-14
		          //价税分离 计算器
		          TaxCalculator.calBySchemaSet(tLJSPayPersonSet);
		          //end zhangyingfeng 2016-07-14
				mLJSPayPersonSet.add(tLJSPayPersonSet);
				LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
				for(int i=1;i<=tLJSPayPersonSet.size();i++)
				{
					tLJSPayPersonSchema = tLJSPayPersonSet.get(i);
					// LJSPayPerson与LJSGetEndorse数据一一对应，以便进行核销
					dLJSGetEndorseSchema = new LJSGetEndorseSchema();
					dLJSGetEndorseSchema.setSchema(tLJSGetEndorseSchema);
					dLJSGetEndorseSchema.setDutyCode(tLJSPayPersonSchema.getDutyCode());
					dLJSGetEndorseSchema.setPayPlanCode(tLJSPayPersonSchema.getPayPlanCode());
					dLJSGetEndorseSchema.setFeeFinaType("ZK");
					dLJSGetEndorseSchema.setGetMoney(tLJSPayPersonSchema.getSumActuPayMoney());
					// 处理SubFeeOperationType			
					dLJSGetEndorseSchema.setSubFeeOperationType(tLJSPayPersonSchema.getPayType()
							+ String.valueOf(tLJSPayPersonSchema.getPayCount()));
			          //营改增 add zhangyingfeng 2016-07-14
			          //价税分离 计算器
			          TaxCalculator.calBySchema(dLJSGetEndorseSchema);
			          //end zhangyingfeng 2016-07-14
					tLJSGetEndorseSet.add(dLJSGetEndorseSchema);
				}
			}
			LPDiscountSet insrtLPDiscountSet = (LPDiscountSet)rVData.getObjectByObjectName("LPDiscountSet", 0);
			if(insrtLPDiscountSet!=null)
			{
				mLPDiscountSet.add(insrtLPDiscountSet);
			}										
		}		
		//************增加折扣处理 ****gaoph 20110125*****************end********************
		
	
		LPDutyDB cLPDutyDB = new LPDutyDB();
		LPDutySet cLPDutySet = new LPDutySet();
		cLPDutyDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		cLPDutyDB.setEdorType(mLPEdorItemSchema.getEdorType());
		cLPDutySet = cLPDutyDB.query();

		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		LPContSchema tLPContSchema = new LPContSchema();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();
		Reflections tRef = new Reflections();
		tRef.transFields(tLPContSchema, tLCContSchema);
		LPContBL tLPContBL = new LPContBL();
		tLPContBL.queryLPCont(mLPEdorItemSchema);
		tLPContSchema.setSchema(tLPContBL.getSchema());
		tLPContSchema
				.setAmnt(tLPContSchema.getAmnt() + tLPDutySchema.getAmnt());
		tLPContSchema
				.setMult(tLPContSchema.getMult() + tLPDutySchema.getMult());
		tLPContSchema.setPrem(tLCContSchema.getPrem() + mGetMoney);
		tLPContSchema.setSumPrem(tLCContSchema.getSumPrem() + mGetMoney);
		tLPContSchema.setModifyDate(theCurrentDate);
		tLPContSchema.setModifyTime(theCurrentTime);
		map.put(tLPContSchema, "DELETE&INSERT");

		//mLPEdorItemSchema.setEdorState("3");
		mLPEdorItemSchema.setGetMoney(mGetMoney);
		mLPEdorItemSchema.setModifyDate(theCurrentDate);
		mLPEdorItemSchema.setModifyTime(theCurrentTime);

		//map.put(tLPContSchema, "DELETE&INSERT");
		map.put(mLJSPayPersonSet, "DELETE&INSERT");
		map.put(mLPDiscountSet, "DELETE&INSERT");
		map.put(mLPEdorItemSchema, "UPDATE");
		map.put(tLPPolSchema, "DELETE&INSERT");
		map.put(tLPDutySchema, "DELETE&INSERT");
		map.put(tLPPremSet, "DELETE&INSERT");
		map.put(tLPGetSchema, "DELETE&INSERT");
		map.put(tLJSGetEndorseSet, "DELETE&INSERT");
		mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
		mBqCalBase.setPolNo(mLPDutySchema.getPolNo());
		mBqCalBase.setRiskCode(tLPPolSchema.getRiskCode());
		mBqCalBase.setEdorNo(mLPEdorItemSchema.getEdorNo());
		mBqCalBase.setEdorValiDate(mLPEdorItemSchema.getEdorValiDate());
		mBqCalBase.setMainPolNo(tLPPolSchema.getMainPolNo());
		mResult.clear();
		mResult.add(mBqCalBase);
		mResult.add(map);
		return true;
	}

	/**
	 * 根据已有的责任信息，计算已有的加费评点 责任不存在加费评点时，返回－1；出错时返回－2
	 * 
	 * @param aDutyCode
	 *            String
	 * @param aPayPlanType
	 *            String
	 * @return double
	 */
	private double AddFeeRiskCode(String aDutyCode, String aPayPlanType) {
		double aSuppRiskCode = -1;
		String strDutyCode = aDutyCode.substring(0, 6);
		String a_sql = "select 'X',SuppRiskScore from lcprem where DutyCode like concat('?strDutyCode?','%') and PayStartDate <= to_date('"
				+ "?Date?"
				+ "','YYYY-MM-DD') and PayEndDate >= to_date('"
				+ "?Date?"
				+ "','YYYY-MM-DD') "
				+ "and PayPlanType = '" + "?aPayPlanType?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(a_sql);
		sqlbv.put("strDutyCode", strDutyCode);
		sqlbv.put("Date", mLPEdorItemSchema.getEdorValiDate());
		sqlbv.put("aPayPlanType", aPayPlanType);
		SSRS tSSRS = new SSRS();
		ExeSQL texeSQL = new ExeSQL();
		tSSRS = texeSQL.execSQL(sqlbv);
		if (tSSRS.getMaxRow() < 1) {
			return aSuppRiskCode;
		} else if (tSSRS.getMaxRow() > 1) {
			aSuppRiskCode = -2;
			return aSuppRiskCode;
		}
		aSuppRiskCode = Double.parseDouble(tSSRS.GetText(1, 2));
		return aSuppRiskCode;
	}

	/**
	 * 产生加费（职业和健康加费）
	 * 
	 * @param aSuppRiskCode
	 *            double
	 * @param aPayPlanType
	 *            String
	 * @return double
	 */
	private double CreateAddFee(double aSuppRiskCode, String aPayPlanType) {
		double tAddFee = 0;
		String e_sql = "select AddFeeAMNT(LCPrem.PayPlanType,LCPol.RiskCode,LCPol.PolNo,"
				+ "?aSuppRiskCode?"
				+ ") from LCPrem,LCPol where LCPol.PolNo = LCPrem.PolNo and LCPol.PolNo = '"
				+ "?PolNo?"
				+ "' and LCPrem.PayPlanType = '"
				+ "?aPayPlanType?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(e_sql);
		sqlbv.put("aSuppRiskCode", aSuppRiskCode);
		sqlbv.put("PolNo", mLPDutySchema.getPolNo());
		sqlbv.put("aPayPlanType", aPayPlanType);
		ExeSQL exeSQL = new ExeSQL();
		tAddFee = Double.parseDouble(exeSQL.getOneValue(sqlbv));
		return tAddFee;
	}
	/**
	 * 根据责任信息，查出此类责任的有效时间
	 * 
	 * @param aDutyCode
	 *            String
	 * @param aPayPlanType
	 *            String
	 * @return SSRS（第一列为起始时间，第二列为终止时间）
	 */

	private SSRS getValiDate(String aDutyCode, String aPayPlanType) {
		SSRS i_ssrs = new SSRS();
		i_ssrs.Clear();
		String strDutyCode = aDutyCode.substring(0, 6);
		String a_sql = "select PayStartDate,PayEndDate from lcprem where DutyCode like concat('?strDutyCode?','%') and PayStartDate <= to_date('"
				+ "?date?"
				+ "','YYYY-MM-DD') and PayEndDate >= to_date('"
				+ "?date?"
				+ "','YYYY-MM-DD') "
				+ "and PayPlanType = '" + "?aPayPlanType?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(a_sql);
		sqlbv.put("strDutyCode", strDutyCode);
		sqlbv.put("date", mLPEdorItemSchema.getEdorValiDate());
		sqlbv.put("aPayPlanType", aPayPlanType);
		ExeSQL texeSQL = new ExeSQL();
		i_ssrs = texeSQL.execSQL(sqlbv);
		if (i_ssrs.getMaxRow() != 1) {
			i_ssrs.Clear();
		}
		return i_ssrs;
	}
	
	/**获得保单的责任终止日期*/
	public String getNewEndDate(String tCValiDate) {

		String tEndDate="";
		// 获取本年度生效日
		String tCurDate = calDate(Integer.parseInt(theCurrentDate
				.substring(0, 4)), tCValiDate);
		// 下年度周年日
		String tNextCValiDate = PubFun.calDate(tCurDate, 1, "Y", "");
		int tIntvC = PubFun.calInterval(mLPEdorItemSchema
				.getEdorValiDate(), tCurDate, "D");
		// 此次保全在主险保单本年度生效日之前申请则生效日为本年度生效日
		if (tIntvC > 0) {
			tEndDate = tCurDate;
		} else // 此次保全在主险保单本年度生效日之后申请则生效日为次年度生效日
		{
			tEndDate = tNextCValiDate;
		}
		return tEndDate;
	}

	/**
	 * 得到tDate在tYear这一年的对应日
	 * 
	 * @param tYear
	 *            所在年度
	 * @param tDate
	 *            日期
	 * @return String : tDate在tYear这一年的对应日
	 */
	private String calDate(int tYear, String tDate) {
		String coDate = "";
		if (tDate != null && !tDate.trim().equals("")) {
			FDate fDate = new FDate();
			GregorianCalendar tCalendar = new GregorianCalendar();
			tCalendar.setTime(fDate.getDate(tDate));
			int tMonth = tCalendar.get(Calendar.MONTH) + 1;
			int tDay = tCalendar.get(Calendar.DAY_OF_MONTH);
			// 如果tEdorValiDate是2月29日，而其上一年不是闰年
			if (tMonth == 2 && tDay == 29 && !isLeap(tYear)) {
				tMonth = 3;
				tDay = 1;
			}
			coDate = String.valueOf(tYear) + "-" + String.valueOf(tMonth) + "-"
					+ String.valueOf(tDay);
		}

		return coDate;
	}

	/**
	 * 闰年校验
	 * 
	 * @param mYear
	 *            年度
	 * @return boolean 闰年:true 平年：false
	 */
	private boolean isLeap(int tYear) {
		boolean returnFlag = (tYear % 4) == 0 ? ((tYear % 100) == 0 ? ((tYear % 400) == 0 ? true
				: false)
				: true)
				: false;

		return returnFlag;
	}
	
	  //投联险加保计算变更后的风险保费,只需要算出一个总数,不用循环算每一个帐户
	  public static double calInsuAccManageFee(LCInsureAccSchema aLCInsureAccSchema,CalBase aCalBase,String aDealDate)
		{
			double tManageFee = 0.0;
			double feeValue = 0.00;
			LCInsureAccClassSet aLCInsureAccClassSet = new LCInsureAccClassSet();
			LCInsureAccClassDB aLCInsureAccClassDB = new LCInsureAccClassDB();
			aLCInsureAccClassDB.setPolNo(aLCInsureAccSchema.getPolNo());
			aLCInsureAccClassDB.setInsuAccNo(aLCInsureAccSchema.getInsuAccNo());
			aLCInsureAccClassSet = aLCInsureAccClassDB.query();
			LCInsureAccClassSchema aLCInsureAccClassSchema = aLCInsureAccClassSet.get(1);
			// 判断该账户是属于个险还是团险
			if (aLCInsureAccClassSchema.getGrpPolNo() != null
					&& aLCInsureAccClassSchema.getGrpPolNo().equals("00000000000000000000"))
			{
				// 先获取该账户管理费信息
				String strsql = "select * from (select * from lmriskfee where feeitemtype='05' and insuaccno='"
						+ "?insuaccno?"
						+ "' and PayPlanCode='"
						+ "?PayPlanCode?"
						+ "' and feestartdate<='"
						+ "?aDealDate?"
						+ "' union "
						+ "select * from lmriskfee where feeitemtype='05' and insuaccno='"
						+ "?insuaccno?"
						+ "' and PayPlanCode='000000' and feestartdate<='"
						+ "?aDealDate?"
						+ "' union "
						+ "select * from lmriskfee where feeitemtype='05' and  insuaccno='000000' and PayPlanCode='"
						+ "?PayPlanCode?"
						+ "' and feestartdate<='"
						+ "?aDealDate?"
						+ "' union "
						+ "select * from lmriskfee where feeitemtype='05' and  insuaccno='000000' and PayPlanCode='000000' and feestartdate<='"
						+ "?aDealDate?" + "' and feetype='0') g order by FeeNum";
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(strsql);
				sqlbv.put("insuaccno", aLCInsureAccClassSchema.getInsuAccNo());
				sqlbv.put("PayPlanCode", aLCInsureAccClassSchema.getPayPlanCode());
				sqlbv.put("aDealDate", aDealDate);
				LMRiskFeeSet aLMRiskFeeSet = new LMRiskFeeSet();
				LMRiskFeeDB aLMRiskFeeDB = new LMRiskFeeDB();
				aLMRiskFeeSet = aLMRiskFeeDB.executeQuery(sqlbv);

				double feeTraceValue = 0.00; // 管理费金额
				// 对管理费表进行循环处理
				for (int k = 1; k <= aLMRiskFeeSet.size(); k++)
				{
					LMRiskFeeSchema aLMRiskFeeSchema = aLMRiskFeeSet.get(k);
					feeTraceValue = calFeeValue(aLMRiskFeeSchema.getFeeCalCode(), aCalBase);
					feeValue += feeTraceValue;
				}
				tManageFee += feeValue;
			}

			return tManageFee;
		}
	  public static double calFeeValue(String aFeeCalCode,CalBase aCalBase)
		{
			//通过sql计算得到
			ExeSQL tExeSQL = new ExeSQL();
			Calculator tCalculator = new Calculator();
			tCalculator.setCalCode(aFeeCalCode);
			//准备计算要素
			//扣除管理费之前的投资单位

			tCalculator.addBasicFactor("Amnt",String.valueOf(aCalBase.getAmnt()));
			//只需要算出一个总数
			tCalculator.addBasicFactor("AccRate", String.valueOf(1));
			//被保人年龄InsuredAge
			tCalculator.addBasicFactor("OccupationType", String.valueOf(aCalBase.getOccupation()));
			tCalculator.addBasicFactor("InsuredAge", String.valueOf(aCalBase.getAppntAge()));
			tCalculator.addBasicFactor("InsuredSex", String.valueOf(aCalBase.getSex()));
			//加费评点
			tCalculator.addBasicFactor("SuppRiskScore", String.valueOf(aCalBase.getSuppRiskScore()));

			String tfeeTraceValue = tCalculator.calculate();
			return Double.parseDouble(tfeeTraceValue);
		}
	  public static double calInsuAccManageFee(String aPolNo,String aDealData)
	  {
			// 计算风险保费
			ExeSQL tExeSQL = new ExeSQL();
			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSchema tLCPolSchema = new LCPolSchema();
			LCPolSet tLCPolSet = new LCPolSet();
			tLCPolDB.setPolNo(aPolNo);
			tLCPolSet = tLCPolDB.query();
			if(tLCPolSet.size()<1)
			{
				return -1;
			}
			tLCPolSchema=tLCPolSet.get(1);
			LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
			LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
			tLCInsureAccDB.setPolNo(aPolNo);
			tLCInsureAccSet = tLCInsureAccDB.query();
		      
			// 组织计算要素
			CalBase tCalBase = new CalBase();
			tCalBase.setAmnt(tLCPolSchema.getAmnt());
			tCalBase.setAppntAge(PubFun.calAppAge(tLCPolSchema.getInsuredBirthday(), aDealData, "Y"));
			tCalBase.setSex(tLCPolSchema.getInsuredSex());
			tCalBase.setOccupation(tLCPolSchema.getOccupationType());
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql("select (case when sum(SuppRiskScore) is not null then sum(SuppRiskScore) else 0 end) from lcprem where polno='" + "?aPolNo?" + "'");
			sbv.put("aPolNo", aPolNo);
			tCalBase.setSuppRiskScore(Double.parseDouble(tExeSQL.getOneValue(sbv)));
			return calInsuAccManageFee(tLCInsureAccSet.get(1),tCalBase,aDealData);
	  }

	
	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "zhangtao";
		tG.ManageCom = "86110000";

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo("86000020080419000716");
		tLPEdorItemSchema = tLPEdorItemDB.query().get(1);

		LPDutySchema td = new LPDutySchema();
		td.setAmnt(20000);
		td.setPolNo("210110000002009");

		VData a = new VData();
		a.add(tG);
		a.add(tLPEdorItemSchema);
		a.add(td);

		PEdorAADetailBL aa = new PEdorAADetailBL();
		aa.submitData(a, "");

		PubSubmit tSubmit = new PubSubmit();
		tSubmit.submitData(aa.getResult(), "");

	}
}

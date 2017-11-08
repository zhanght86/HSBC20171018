package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Date;

import com.sinosoft.lis.bl.LCDutyBL;
import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJSGetClaimDB;
import com.sinosoft.lis.db.LLBnfDB;
import com.sinosoft.lis.db.LLGetDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LLBnfSchema;
import com.sinosoft.lis.schema.LLDutySchema;
import com.sinosoft.lis.schema.LLGetSchema;
import com.sinosoft.lis.schema.LMDutyGetSchema;
import com.sinosoft.lis.tb.CachedRiskInfo;
import com.sinosoft.lis.tb.CalBL;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vbl.LCPremBLSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSGetClaimSet;
import com.sinosoft.lis.vschema.LLBnfSet;
import com.sinosoft.lis.vschema.LLDutySet;
import com.sinosoft.lis.vschema.LLGetSet;
import com.sinosoft.lis.vschema.LMRiskDutySet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 理赔金转年金计算
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author
 * @version 1.0
 */
public class LLClmToPensionBL implements BusinessService{
private static Logger logger = Logger.getLogger(LLClmToPensionBL.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private MMap mMMap = new MMap();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	private FDate fDate = new FDate();
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();
	private Reflections mReflections = new Reflections();
	private CachedRiskInfo mCRI = CachedRiskInfo.getInstance();
	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();// 理赔公用方法类

	/** 保单 */
	private LCPolBL mLCPolBL = new LCPolBL();
	/** 保费项表 */
	private LCPremBLSet mLCPremBLSet = new LCPremBLSet();
	/** 给付项表 */
	private LCGetBLSet mLCGetBLSet = new LCGetBLSet();
	private LCGetSchema mLCGetSchema = new LCGetSchema();
	// 一般的责任信息
	private LCDutyBL mLCDutyBL = new LCDutyBL();
	/** 责任表 */
	private LCDutyBLSet mLCDutyBLSet = new LCDutyBLSet();
	private LCDutyBLSet mLCDutyBLInSet = new LCDutyBLSet(); // 转年金责任
	/** 受益人表 */
	private LLBnfSchema mLLBnfSchema = new LLBnfSchema();

	private String mClmNo = ""; // 赔案号
	private String mPolNo = ""; // 意外事故发生日期
	private String mBnfNo = ""; // 受益人序号
	private double mStandPay = 0; // 理赔金

	public LLClmToPensionBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------理赔金转年金处理-----开始----------");

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		if (!pubSubmit()) {
			return false;
		}

		logger.debug("----------理赔金转年金处理-----结束----------");
		return true;
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mLCGetSchema = (LCGetSchema) mInputData.getObjectByObjectName(
				"LCGetSchema", 0);

		this.mClmNo = (String) mTransferData.getValueByName("ClmNo"); // 赔案号
		this.mPolNo = (String) mTransferData.getValueByName("PolNo"); // 意外事故发生日期
		this.mBnfNo = (String) mTransferData.getValueByName("bnfno"); // 受益人序号

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 查询所需信息
		if (!prepareData()) {
			return false;
		}

		// 调用calbl计算
		if (!pubCal()) {
			return false;
		}

		// 理赔信息包装
		if (!renewData()) {
			return false;
		}

		return true;
	}

	/**
	 * 查询所有需要用到的基础信息
	 * 
	 * @return boolean
	 */
	public boolean prepareData() {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0
		 * 根据赔案号、保单号、受益人序号，得到所需的理赔信息 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		// 取得险种信息
		LLBnfDB tLLBnfDB = new LLBnfDB();
		tLLBnfDB.setClmNo(this.mClmNo);
		tLLBnfDB.setPolNo(this.mPolNo);
		tLLBnfDB.setBnfNo(this.mBnfNo);
		LLBnfSet tLLBnfSet = tLLBnfDB.query();
		if (tLLBnfSet == null || tLLBnfSet.size() != 1) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClmToPensionBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "查询受益人信息失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLLBnfSchema.setSchema(tLLBnfSet.get(1));

		// 查找该保单的理赔金
		LJSGetClaimDB tLJSGetClaimDB = new LJSGetClaimDB();
		tLJSGetClaimDB.setOtherNoType("5");
		tLJSGetClaimDB.setOtherNo(this.mClmNo);
		tLJSGetClaimDB.setPolNo(this.mPolNo);
		tLJSGetClaimDB.setGetNoticeNo(mLLBnfSchema.getOtherNo());
		tLJSGetClaimDB.setFeeFinaType("PK");
		tLJSGetClaimDB.setFeeOperationType("A");
		LJSGetClaimSet tLJSGetClaimSet = tLJSGetClaimDB.query();
		if (tLLBnfSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClmToPensionBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "查询理赔金信息失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int i = 1; i <= tLJSGetClaimSet.size(); i++) {
			mStandPay = mStandPay + tLJSGetClaimSet.get(i).getPay();
			tLJSGetClaimSet.get(i).setPay(0);
		}
		mStandPay = Double.parseDouble(new DecimalFormat("0.00")
				.format(mStandPay));
		this.mMMap.put(tLJSGetClaimSet, "DELETE&INSERT");

		
		// 更新应付总表
		String tUpdate = "update ljsget b set b.sumgetmoney = "
				+ "(select (case when sum(pay) is null then 0 else sum(pay) end) from ljsgetclaim a "
				+ " where a.othernotype = '5' "
				+ " and a.getnoticeno = b.getnoticeno "
				+ " and a.feeoperationtype != 'A' "
				+ " and a.feefinatype != 'PK') ,"
				+ " b.netamount ="
				+ "(select (case when sum(netamount) is null then 0 else sum(netamount) end) from ljsgetclaim a "
				+ " where a.othernotype = '5' "
				+ " and a.getnoticeno = b.getnoticeno "
				+ " and a.feeoperationtype != 'A' "
				+ " and a.feefinatype != 'PK') ," 
				+ " b.taxamount ="
				+ "(select (case when sum(taxamount) is null then 0 else sum(taxamount) end) from ljsgetclaim a "
				+ " where a.othernotype = '5' "
				+ " and a.getnoticeno = b.getnoticeno "
				+ " and a.feeoperationtype != 'A' "
				+ " and a.feefinatype != 'PK') ," 
				
				+ " where b.getnoticeno = '"
				+ "?otherno?" + "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tUpdate);
		sqlbv.put("otherno", mLLBnfSchema.getOtherNo());
		this.mMMap.put(sqlbv, "UPDATE");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 准备公用方法的计算要素
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		// 取得险种信息
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mPolNo);
		LCPolSet tLCPolSet = tLCPolDB.query();
		if (tLCPolSet == null || tLCPolSet.size() <= 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClmToPensionBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "查询险种信息失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCPolBL.setSchema(tLCPolSet.get(1));
		// 借用被保人生日字段,因为calbl使用它计算
		mLCPolBL.setInsuredBirthday(mLLBnfSchema.getBirthday());

		// 取得现有责任信息
		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(mPolNo);
		LCDutySet tLCDutyBLSet = tLCDutyDB.query();

		if (tLCDutyBLSet == null || tLCDutyBLSet.size() <= 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClmToPensionBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "查询险种责任信息失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCDutyBLInSet.add(tLCDutyBLSet.get(1));

		// 查询打包所以产品定义的责任
		if (!getDutyStructure()) {
			return false;
		}

		return true;
	}

	/**
	 * 通过描述取得责任信息的结构
	 * 
	 * @return boolean
	 */
	private boolean getDutyStructure() {
		// 从险种责任表中取出责任的结构
		LMRiskDutySet tLMRiskDutySet = mCRI
				.findRiskDutyByRiskCodeClone(mLCPolBL.getRiskCode());

		if (tLMRiskDutySet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "CalBL";
			tError.functionName = "getDutyStructure";
			tError.errorMessage = "LMRiskDuty表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 把数据装入mLCDutyBLSet中
		LCDutySchema tLCDutySchema = new LCDutySchema();
		tLCDutySchema.setSchema(mLCDutyBLInSet.get(1));
		mLCDutyBLInSet.clear();

		int n = tLMRiskDutySet.size();
		for (int i = 1; i <= n; i++) {
			// 为区分转年金责任,增加 选择标记ChoFlag为"L"
			if (!StrTool.cTrim(tLMRiskDutySet.get(i).getChoFlag()).equals("L")) {
				continue;
			}

			LCDutyBL tLCDutyBL = new LCDutyBL();
			tLCDutyBL.setDutyCode(tLMRiskDutySet.get(i).getDutyCode());

			if (tLCDutySchema != null) // 录入优先
			{
				tLCDutyBL.setPayIntv(tLCDutySchema.getPayIntv());
				tLCDutyBL.setInsuYearFlag(tLCDutySchema.getInsuYearFlag());
				tLCDutyBL.setInsuYear(tLCDutySchema.getInsuYear());
				tLCDutyBL.setAcciYearFlag(tLCDutySchema.getAcciYearFlag());
				tLCDutyBL.setAcciYear(tLCDutySchema.getAcciYear());
				tLCDutyBL.setPayEndYear(tLCDutySchema.getPayEndYear());
				tLCDutyBL.setPayEndYearFlag(tLCDutySchema.getPayEndYearFlag());
				tLCDutyBL.setGetYear(tLCDutySchema.getGetYear()); //
				tLCDutyBL.setGetYearFlag(tLCDutySchema.getGetYearFlag());
				tLCDutyBL.setStandbyFlag1(tLCDutySchema.getStandbyFlag1());
				tLCDutyBL.setStandbyFlag2(tLCDutySchema.getStandbyFlag2());
				tLCDutyBL.setStandbyFlag3(tLCDutySchema.getStandbyFlag3());
				tLCDutyBL.setMult(tLCDutySchema.getMult());
				tLCDutyBL.setCalRule(tLCDutySchema.getCalRule());
				tLCDutyBL.setFloatRate(tLCDutySchema.getFloatRate());
				tLCDutyBL.setPremToAmnt(tLCDutySchema.getPremToAmnt());
				tLCDutyBL.setGetLimit(tLCDutySchema.getGetLimit());
				tLCDutyBL.setGetRate(tLCDutySchema.getGetRate());
				tLCDutyBL.setSSFlag(tLCDutySchema.getSSFlag());
				tLCDutyBL.setPeakLine(tLCDutySchema.getPeakLine());

				// 按受益人领取金额进行计算
				tLCDutyBL.setAmnt(mStandPay);
			}

			mLCDutyBLInSet.add(tLCDutyBL);
		}

		return true;
	}

	/**
	 * 保费保额计算调用部分
	 * 
	 * @return
	 */
	public boolean pubCal() {
		// 传入计算数据
		LCGetBLSet tLCGetBLSet = new LCGetBLSet();
		tLCGetBLSet.add(mLCGetSchema);
		String tCalFlag = "LP";
		CalBL tCalBL = new CalBL(mLCPolBL, mLCDutyBLInSet, tLCGetBLSet,
				tCalFlag);

		// 需要保费保额计算
		tCalBL.setNoCalFalg(false); // 将是否需要计算的标记传入计算类中

		// 正式计算
		if (tCalBL.calPol() == false) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClmToPensionBL";
			tError.functionName = "pubCal";
			tError.errorMessage = tCalBL.mErrors.getFirstError();
			this.mErrors.addOneError(tError);
			return false;
		}

		// 取出计算的结果
		mLCPolBL.setSchema(tCalBL.getLCPol().getSchema());
		mLCGetBLSet = tCalBL.getLCGet();
		mLCDutyBLSet = tCalBL.getLCDuty();

		// 领取项
		mLCGetBLSet.setPolNo(mLCPolBL.getPolNo());
		for (int j = 1; j <= mLCGetBLSet.size(); j++) {
			mLCGetBLSet.get(j).setOperator(mGlobalInput.Operator);
			mLCGetBLSet.get(j).setMakeDate(this.CurrentDate);
			mLCGetBLSet.get(j).setMakeTime(this.CurrentTime);
			mLCGetBLSet.get(j).setModifyDate(this.CurrentDate);
			mLCGetBLSet.get(j).setModifyTime(this.CurrentTime);

			LCGetSchema tLCGetSchema = mLCGetBLSet.get(j);
			mLCGetBLSet.set(j, tLCGetSchema);
		}

		// 责任项
		for (int j = 1; j <= mLCDutyBLSet.size(); j++) {
			LCDutySchema tLCDutySchema = mLCDutyBLSet.get(j);
			tLCDutySchema.setPrem(0);
			tLCDutySchema.setFirstPayDate(mLCPolBL.getCValiDate());
			tLCDutySchema.setOperator(this.CurrentTime);
			tLCDutySchema.setMakeDate(this.CurrentDate);
			tLCDutySchema.setMakeTime(this.CurrentTime);
			tLCDutySchema.setModifyDate(this.CurrentDate);
			tLCDutySchema.setModifyTime(this.CurrentTime);

			mLCDutyBLSet.set(j, tLCDutySchema);
		}

		return true;
	}

	/**
	 * 补充理赔数据
	 * 
	 * @return
	 */
	public boolean renewData() {
		// 对llget信息进行整理
		LLGetSchema tLLGetSchema = new LLGetSchema();
		for (int j = 1; j <= mLCGetBLSet.size(); j++) {
			if (mLCGetBLSet.get(j).getGetDutyCode().equals(
					mLCGetSchema.getGetDutyCode())) {
				mReflections.transFields(tLLGetSchema, mLCGetBLSet.get(j)
						.getSchema());
				tLLGetSchema.setClmNo(this.mClmNo);
				tLLGetSchema.setOperationType("A");
				tLLGetSchema.setBnfNo(this.mBnfNo);
				tLLGetSchema.setCustomerNo(mLLBnfSchema.getCustomerNo());
				tLLGetSchema.setName(mLLBnfSchema.getName());
				tLLGetSchema.setSex(mLLBnfSchema.getSex());
				tLLGetSchema.setBirthday(mLLBnfSchema.getBirthday());
				tLLGetSchema.setIDNo(mLLBnfSchema.getIDNo());
				tLLGetSchema.setIDType(mLLBnfSchema.getIDType());
				tLLGetSchema.setGetEndState("0"); // 止领标志
				tLLGetSchema.setUrgeGetFlag("L"); // 催付标记,按张荣要求置为"L"

				// 按条款和出险日期比较,更改起领日期
				LMDutyGetSchema tLMDutyGetSchema = mCRI
						.findDutyGetByGetDutyCodeClone(tLLGetSchema
								.getGetDutyCode());
				if (tLMDutyGetSchema == null) {
					// @@错误处理
					this.mErrors.copyAllErrors(mCRI.mErrors);
					mCRI.mErrors.clearErrors();

					CError tError = new CError();
					tError.moduleName = "LLClmToPensionBL";
					tError.functionName = "renewData";
					tError.errorMessage = "查询产品定义失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				// 计算日期
				Date baseDate = null;
				String unit = "Y"; // 目前都以年为单位
				Date getStartDate = null;
				Date getEndDate = null;
				if (StrTool.cTrim(tLMDutyGetSchema.getGetYearFlag())
						.equals("A")) {
					// 判断出险日期是否落在产品定义的起期区间内,如:144041的起始年龄要在50和70内
					baseDate = fDate.getDate(mLLBnfSchema.getBirthday());
					int tStart = tLMDutyGetSchema.getGetYear();
					int tEnd = tLMDutyGetSchema.getMinGetStartPeriod();
					Date tInsDate = fDate.getDate(mLLClaimPubFunBL
							.getInsDate(mClmNo));
					Date tD1 = PubFun.calDate(baseDate, tStart, unit, null);
					Date tD2 = PubFun.calDate(baseDate, tEnd, unit, null);
					getStartDate = fDate.getDate(PubFun.getBeforeDate(fDate
							.getString(tInsDate), fDate.getString(tD2)));
					getEndDate = fDate.getDate(PubFun.getLaterDate(fDate
							.getString(tInsDate), fDate.getString(tD1)));
					if (getStartDate.equals(getEndDate)) {
						tLLGetSchema.setGetStartDate(tInsDate); // 起领日期为出险日期
						tLLGetSchema.setGettoDate(tInsDate);
					} else {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "LLClmToPensionBL";
						tError.functionName = "renewData";
						tError.errorMessage = "受益人年龄不符合产品定义要求,不能进行转年金操作!";
						this.mErrors.addOneError(tError);
						return false;
					}
				} else {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLClmToPensionBL";
					tError.functionName = "renewData";
					tError.errorMessage = "产品定义对于起领期间单位描述错误!";
					this.mErrors.addOneError(tError);
					return false;
				}

				break;
			}
		}

		// 对llduty信息整理
		LLDutySet tLLDutySet = new LLDutySet();
		for (int j = 1; j <= mLCDutyBLSet.size(); j++) {
			LLDutySchema tLLDutySchema = new LLDutySchema();
			mReflections.transFields(tLLDutySchema, mLCDutyBLSet.get(j)
					.getSchema());
			tLLDutySchema.setClmNo(this.mClmNo);
			tLLDutySchema.setOperationType("A");

			// 取得现有责任下的所有给付项保额
			double tAmnt = 0;
			LLGetDB tLLGetDB = new LLGetDB();
			tLLGetDB.setClmNo(this.mClmNo);
			tLLGetDB.setOperationType("A");
			tLLGetDB.setPolNo(mPolNo);
			tLLGetDB.setDutyCode(tLLDutySchema.getDutyCode());
			LLGetSet tLLGetSet = new LLGetSet();
			tLLGetSet = tLLGetDB.query();
			for (int i = 1; i <= tLLGetSet.size(); i++) {
				tAmnt = tAmnt + tLLGetSet.get(i).getStandMoney();
			}
			tAmnt = tAmnt + tLLDutySchema.getAmnt();
			tLLDutySchema.setAmnt(tAmnt);

			tLLDutySet.add(tLLDutySchema);
		}

		this.mMMap.put(tLLGetSchema, "DELETE&INSERT");
		this.mMMap.put(tLLDutySet, "DELETE&INSERT");

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(mMMap);

		// mResult.clear();
		// mResult.add(mMMap);
		return true;
	}

	/**
	 * 提交数据
	 * 
	 * @return
	 */
	private boolean pubSubmit() {
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClmToPensionBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "86";

		LCGetSchema tLCGetSchema = new LCGetSchema();
		tLCGetSchema.setGetDutyCode("144042");

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ClmNo", "90000021254");
		tTransferData.setNameAndValue("PolNo", "BJ010421441000018000");
		tTransferData.setNameAndValue("bnfno", "1");

		VData tVData = new VData();
		tVData.addElement(tG);
		tVData.addElement(tTransferData);
		tVData.add(tLCGetSchema);

		LLClmToPensionBL tLLClmToPensionBL = new LLClmToPensionBL();
		tLLClmToPensionBL.submitData(tVData, "");
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}

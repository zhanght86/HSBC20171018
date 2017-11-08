package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.sinosoft.claimcalculator.cache.CachedCalculatorTrace;
import com.sinosoft.claimcalculator.ClaimCalculatorFactor;
import com.sinosoft.lis.acc.DealInsuAccPrice;
import com.sinosoft.lis.bq.PrepareBOMBQEdorBL;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LLClaimDB;
import com.sinosoft.lis.db.LLClaimDetailDB;
import com.sinosoft.lis.db.LLPrepayClaimDB;
import com.sinosoft.lis.db.LLToClaimDutyDB;
import com.sinosoft.lis.db.LLToClaimDutyFeeDB;
import com.sinosoft.lis.db.LMDutyGetFeeRelaDB;
import com.sinosoft.lis.db.LMRiskSocialInsuDB;
import com.sinosoft.lis.db.LMRiskSortDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.db.LCalculatorFactorDB;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LDExch;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubCalculator;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LLCaseSchema;
import com.sinosoft.lis.schema.LLClaimDetailSchema;
import com.sinosoft.lis.schema.LLClaimDutyFeeSchema;
import com.sinosoft.lis.schema.LLClaimPolicySchema;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.schema.LLToClaimDutyFeeSchema;
import com.sinosoft.lis.schema.LLToClaimDutySchema;
import com.sinosoft.lis.schema.LMDutyGetClmCalSchema;
import com.sinosoft.lis.schema.LMDutyGetClmSchema;
import com.sinosoft.lis.schema.LMDutyGetFeeRelaSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.schema.LCalculatorFactorSchema;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.lis.vschema.LLClaimDutyFeeSet;
import com.sinosoft.lis.vschema.LLClaimPolicySet;
import com.sinosoft.lis.vschema.LLClaimSet;
import com.sinosoft.lis.vschema.LLPrepayClaimSet;
import com.sinosoft.lis.vschema.LLRegisterSet;
import com.sinosoft.lis.vschema.LLToClaimDutyFeeSet;
import com.sinosoft.lis.vschema.LLToClaimDutySet;
import com.sinosoft.lis.vschema.LMDutyGetFeeRelaSet;
import com.sinosoft.lis.vschema.LMRiskSortSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.lis.vschema.LCalculatorFactorSet;
import com.sinosoft.lis.vschema.LCalculatorTraceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 计算步骤五：理赔正式计算
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 续涛，2005.05.24--2005.05.24
 * @version 1.0
 */
public class LLClaimCalAutoBL {
private static Logger logger = Logger.getLogger(LLClaimCalAutoBL.class);

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

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();

	private String mAccNo = ""; // 事件号
	private String mAccDate = ""; // 意外事故发生日期
	private String mInsDate = ""; // 出险时间
	private String mClmNo = ""; // 赔案号
	private String mCusNo = ""; // 客户号
	private String mContType = ""; // 总单类型,1-个人总投保单,2-集体总单
	private String mClmState = ""; // 赔案状态，20立案，30审核

	// private LCContSchema mLCContSchema = new LCContSchema(); //承保--个人合同表
	private LCPolSchema mLCPolSchema = new LCPolSchema(); // 承保--个人保单险种表
	private LCDutySchema mLCDutySchema = new LCDutySchema(); // 承保--个人保单险种责任表
	private LCGetSchema mLCGetSchema = new LCGetSchema(); // 承保--个人领取项表
	private List mBomList = new ArrayList();

	private PrepareBOMClaimBL mPrepareBOMClaimBL = new PrepareBOMClaimBL();


	
	// //立案登记信息
	private LLCaseSchema mLLCaseSchema = new LLCaseSchema(); // 立案分案信息

	// 理赔--赔案信息
	private LLClaimSchema mLLClaimSchema = new LLClaimSchema();
	
	// 理赔--立案信息
	private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();

	// 理赔--赔案保单名细
	private LLClaimPolicySchema mLLClaimPolicySchema = new LLClaimPolicySchema();
	private LLClaimPolicySet mLLClaimPolicySet = new LLClaimPolicySet();

	// 理赔--赔付明细信息
	private LLClaimDetailSchema mLLClaimDetailSchema = new LLClaimDetailSchema();
	private LLClaimDetailSet mLLClaimDetailSet = new LLClaimDetailSet();

	// 理赔--责任费用统计
	private LLClaimDutyFeeSchema mLLClaimDutyFeeSchema = new LLClaimDutyFeeSchema();
	private LLClaimDutyFeeSet mLLClaimDutyFeeSet = new LLClaimDutyFeeSet();

	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
	private ExeSQL mExeSQL = new ExeSQL();

	private double mPupilAmnt = 0; // 未成年人保额
	private String mInpFlag = ""; // 理算录入标志

	/*
	 * sl add 2015-04-22 累加器处理
	 */
	//累加器trace缓存
	private CachedCalculatorTrace mCCT = CachedCalculatorTrace.getInstance();
	// 理赔--累加器trace
	private LCalculatorTraceSet mLCalculatorTraceSet = new LCalculatorTraceSet();
	
	public LLClaimCalAutoBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------理算步骤五-----理赔正式计算-----LLClaimCalAutoBL测试-----开始----------");

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

		logger.debug("----------理算步骤五-----理赔正式计算-----LLClaimCalAutoBL测试-----结束----------");
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

		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		this.mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		this.mAccNo = (String) mTransferData.getValueByName("AccNo"); // 事件号
		this.mAccDate = (String) mTransferData.getValueByName("AccDate"); // 意外事故发生日期
		this.mClmNo = (String) mTransferData.getValueByName("ClmNo"); // 赔案号
		this.mContType = (String) mTransferData.getValueByName("ContType"); // 总单类型,1-个人投保单,2-集体总投保单
		this.mClmState = (String) mTransferData.getValueByName("ClmState"); // 赔案状态，20立案，30审核
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		
		//xuyunpeng add 首先删除缓存
		mCCT.remove(mClmNo);

		
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 先删除已经计算过的信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String strSQL1 = "delete from LLClaim where ClmNo='" + "?ClmNo?"
				+ "'";

		String strSQL2 = "delete from LLClaimPolicy where ClmNo='"
				+ "?ClmNo?" + "'";

		String strSQL3 = "delete from LLClaimDetail where ClmNo='"
				+ "?ClmNo?" + "'";

		String strSQL4 = "delete from LLClaimDutyFee where ClmNo='"
				+ "?ClmNo?" + "'";
		SQLwithBindVariables sqlbv =new SQLwithBindVariables();
		sqlbv.sql(strSQL1);
		sqlbv.put("ClmNo", this.mClmNo);
		mMMap.put(sqlbv, "DELETE");
		SQLwithBindVariables sqlbv1 =new SQLwithBindVariables();
		sqlbv1.sql(strSQL2);
		sqlbv1.put("ClmNo", this.mClmNo);
		mMMap.put(sqlbv1, "DELETE");
		SQLwithBindVariables sqlbv2 =new SQLwithBindVariables();
		sqlbv2.sql(strSQL3);
		sqlbv2.put("ClmNo", this.mClmNo);
		mMMap.put(sqlbv2, "DELETE");
		SQLwithBindVariables sqlbv3 =new SQLwithBindVariables();
		sqlbv3.sql(strSQL4);
		sqlbv3.put("ClmNo", this.mClmNo);
		mMMap.put(sqlbv3, "DELETE");

		/** 
		 *
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.5 医疗险理赔累加器处理,
		 * sl add 2015-03-24 加入累加器处理：增加对累加器轨迹表LCalculatorTrace的删除处理
		 * 每次计算前就需要把本赔案的trace从DB里删除，否则反复计算时会有问题
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
//		String strSQL5 = "delete from LCalculatorTrace where ClmNo='"
//			+ this.mClmNo + "'";
//		mMMap.put(strSQL5, "DELETE");
		
		VData tInputData = new VData();
		MMap tMMap = new MMap();
		String strSQL5 = "delete from LCalculatorTrace where ClmNo='"
			+ "?ClmNo?" + "'";
		SQLwithBindVariables sqlbv4 =new SQLwithBindVariables();
		sqlbv4.sql(strSQL5);
		sqlbv4.put("ClmNo", this.mClmNo);
		tMMap.put(sqlbv4, "DELETE");
		tInputData.add(tMMap);
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(tInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimCalAutoBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 根据赔案号，得到出险时间
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		mInsDate = mLLClaimPubFunBL.getInsDate(mClmNo);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 创建赔案信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getLLClaim()) {
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0 循环该赔案的匹配结果集,
		 * 按保单、理赔类型进行的分组信息，分别进行计算 主要针对于LLClaimPolicy表【赔案保单名细】
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql = "select risktype,polno,getdutykind,count(*) from lltoclaimduty where 1 =1 "
				+ " and caseno='"
				+ "?ClmNo?"
				+ "' group by risktype,polno,getdutykind";

		logger.debug("--按保单、理赔类型进行的分组信息，分别进行计算:"+tSql);
		SQLwithBindVariables sqlbv5 =new SQLwithBindVariables();
		sqlbv5.sql(tSql);
		sqlbv5.put("ClmNo", this.mClmNo);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv5);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "查询赔案"+this.mClmNo+"理赔类型错误,"+
					tExeSQL.mErrors.getError(0).errorMessage);
			logger.debug("------------------------------------------------------");
			logger.debug("--LLClaimCalAutoBL.dealData()--理赔计算时发生错误!"
					+ tSql);
			logger.debug("------------------------------------------------------");
			return false;
		}

		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			String pRiskType = tSSRS.GetText(i, 1); // 险种类型
			String pPolNo = tSSRS.GetText(i, 2); // 保单险种号
			String pClaimType = tSSRS.GetText(i, 3); // 理赔类型

			// 调用calDutyPay()进行计算
			if (!calDutyPay(pPolNo, pClaimType)) {
				return false;
			}

		}
		
		
		
//		/** 
//		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0 医疗险理赔累加器处理,
//		 * sl add 2015-04-22 当前赔案计算完毕，需要释放累加器trace占用的缓存空间
//		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//		 */
		
		
		mCCT.remove(mClmNo);

//		/**        累加器后置、统一update的方案已否定，暂时注掉不用，将来需要删除
//		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0 医疗险理赔累加器处理,
//		 * sl add 2015-03-24 加入累加器处理，处理结果更新之前已经生成的LLClaimDetailSet、LLClaimPolicySet、LLClaimSchema等
//		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//		 */
//		if (!dealClaimCalculator()){
//			return false;
//		}
		
		return true;
	}

	/**
	 * 医疗险理赔累加器处理——这个方法不用了
	 * 
	 * @return boolean
	 */
	private boolean dealClaimCalculator(){
		
		//只要有一个给付责任是医疗类的，就可能需要进行累加器计算
		boolean isMediClaim = false; 
		
		if (mLLClaimDetailSet==null||mLLClaimDetailSet.size()<1){
			return false;
		}

//		for (int i = 1; i <= mLLClaimDetailSet.size(); i++) {
//			LLClaimDetailSchema tLLClaimDetailSchema = mLLClaimDetailSet.get(i);
//			String tGetDutyKind = StrTool.cTrim(tLLClaimDetailSchema
//					.getGetDutyKind()); // 理赔类型
//			String tGetDutyCode = StrTool.cTrim(tLLClaimDetailSchema
//					.getGetDutyCode()); // 给付责任
//			LMDutyGetClmSchema tLMDutyGetClmSchema = mLLClaimPubFunBL
//				.getLMDutyGetClm(tGetDutyKind, tGetDutyCode);
//			if (tLMDutyGetClmSchema == null) {
//				this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
//				return false;
//			}
//			String tInpFlag = StrTool.cTrim(tLMDutyGetClmSchema.getInpFlag());
//			if (tInpFlag.length() == 0 || tInpFlag.equals("")
//					|| tInpFlag == null) {
//				tInpFlag = "0";
//			}
//			if (tInpFlag.equals("2") ) {
//				////////////////////////////用这种逐个查LMDutyGetClm的方法判断医疗险理赔太麻烦了，可能有其他更简便的方法。先这么写，将来知道了再修改
//				isMediClaim = true;
//			}
//
//		}
//
//		if (!isMediClaim){
//			//如果没有医疗险理赔责任，就直接返回true，不做累加器计算处理
//			return true;
//		}

		/*
		 * 删除该赔案之前计算过的累加器trace
		 */
		String strSQL1 = "delete from LCalculatorTrace where ClmNo='" + "?ClmNo?" + "'";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(strSQL1);
		sqlbv6.put("ClmNo", this.mClmNo);
		MMap tMMap = new MMap();
		tMMap.put(sqlbv6, "DELETE");
		VData tInputData = new VData();
		tInputData.add(tMMap);
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(tInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimCalAutoBL";
			tError.functionName = "dealClaimCalculator";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		for (int i = 1; i <= mLLClaimDetailSet.size(); i++) {
			LLClaimDetailSchema tLLClaimDetailSchema = mLLClaimDetailSet.get(i);
			double tRealPay = Double.valueOf(tLLClaimDetailSchema.getRealPay());
			if (tRealPay <= 0){
				//之前算的赔付金额已经是0了就不必再算累加器了
				continue;
			}
			String tRiskCode = tLLClaimDetailSchema.getRiskCode(); // 险种代码
			String tDutyCode = tLLClaimDetailSchema.getDutyCode(); // 责任代码
			String tGetDutyKind = tLLClaimDetailSchema.getGetDutyKind(); // 理赔类型
			String tGetDutyCode = tLLClaimDetailSchema.getGetDutyCode(); // 给付责任
			
			ClaimCalculatorFactor tCalculator = new ClaimCalculatorFactor();
			tCalculator.setLLClaimDetailSchema(tLLClaimDetailSchema);
			tCalculator.setLLRegisterSchema(mLLRegisterSchema);
			tCalculator.setApplyPay(tLLClaimDetailSchema.getRealPay());
			double tCalResult = tCalculator.getClaimCalculationResult();
			if (tCalResult < tRealPay){
				//如果算后值小于算前值，需要替换
				mLLClaimDetailSet.get(i).setRealPay(tCalResult);
				mLLClaimDetailSet.get(i).setStandPay(tCalResult);
			}
		}
		
		return true;
	}

	
	/**
	 * 创建赔案记录
	 * 
	 * @return boolean
	 */
	private boolean getLLClaim() {

		LLClaimDB tLLClaimDB = new LLClaimDB();
		tLLClaimDB.setClmNo(this.mClmNo);
		LLClaimSet tLLClaimSet = tLLClaimDB.query();

		if (tLLClaimSet == null || tLLClaimSet.size() == 0) {
			mLLClaimSchema = new LLClaimSchema();
			mLLClaimSchema.setClmNo(this.mClmNo);
			mLLClaimSchema.setRgtNo(this.mClmNo);
			mLLClaimSchema.setCaseNo(this.mClmNo);
			mLLClaimSchema.setClmState(mClmState); // 赔案状态，20立案，30审核
			mLLClaimSchema.setCheckType("0"); // 审核类型，0初次审核,1签批退回审核,2申诉审核			
			mLLClaimSchema.setCurrency((String) mTransferData.getValueByName("sumCurrency"));
		} else {
			mLLClaimSchema = tLLClaimSet.get(1);

		}
		mLLClaimSchema.setClmUWer(mGlobalInput.Operator);

		mLLClaimSchema.setGiveType("0"); // 赔付结论，0给付，1拒付

		mLLClaimSchema.setMngCom(mGlobalInput.ManageCom);
		mLLClaimSchema.setOperator(mGlobalInput.Operator);
		mLLClaimSchema.setMakeDate(PubFun.getCurrentDate());
		mLLClaimSchema.setMakeTime(PubFun.getCurrentTime());
		mLLClaimSchema.setModifyDate(PubFun.getCurrentDate());
		mLLClaimSchema.setModifyTime(PubFun.getCurrentTime());

		mLLClaimSchema.setStandPay(0); // 核算赔付金额
		mLLClaimSchema.setBalancePay(0); // 结算金额
		mLLClaimSchema.setBeforePay(getPrepaySum()); // 预付金额
		mLLClaimSchema.setRealPay(0); // 核赔赔付金额
		
		mLLRegisterSchema=mLLClaimPubFunBL.getLLRegister(this.mClmNo);

		return true;
	}

	/**
	 * 得到预付金额
	 * 
	 * @return
	 */
	private double getPrepaySum() {
		double rValue;
		LLPrepayClaimDB tLLPrepayClaimDB = new LLPrepayClaimDB();
		tLLPrepayClaimDB.setClmNo(this.mClmNo);
		LLPrepayClaimSet tLLPrepayClaimSet = tLLPrepayClaimDB.query();

		if (tLLPrepayClaimSet == null || tLLPrepayClaimSet.size() == 0) {
			rValue = 0;
		} else {
			// Double.parseDouble(new
			// DecimalFormat("0.00").format(m_Sum_ClaimFee));
			rValue = tLLPrepayClaimSet.get(1).getPrepaySum();
		}
		return rValue;

	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－开始－－－－－－－理赔计算－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 目的：按照保单,理赔类型 计算赔付
	 * 
	 * @param String
	 *            pPolNo 保单号
	 * @param String
	 *            pClaimType 理赔类型
	 */
	private boolean calDutyPay(String pPolNo, String pClaimType) {
		/**
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 定义变量
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql = "";
		String tSqlTemp = "";
		// 临时显示金额
		double t_Sum_Temp = 0;
		t_Sum_Temp = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Temp));

		// 给付总金额,用于累计计算,总赔案层面
		double t_Sum_Claim = 0;
		t_Sum_Claim = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Claim));

		// 理赔类型给付金额，用于保单理赔类型层面
		double t_Sum_DutyKind = 0;
		t_Sum_DutyKind = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_DutyKind));

		// 保项给付金额，用于保项层面
		double t_Sum_DutyCode = 0;
		t_Sum_DutyCode = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_DutyCode));

		// 保项的剩余金额，用于保项层面
		double t_Sum_DutyBalCode = 0;
		t_Sum_DutyBalCode = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_DutyBalCode));

		// 医疗费用给付基本金额，用于费用层面
		double t_Sum_DutyFee = 0;
		t_Sum_DutyFee = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_DutyFee));

		// 医疗费用给付基本金额，用于费用层面
		double t_Sum_DutyFeeFranchise = 0;
		t_Sum_DutyFeeFranchise = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_DutyFeeFranchise));

		// 用于保存理赔金额中不用于权限分配的值
		double t_Sum_PopedomPay = 0;
		t_Sum_PopedomPay = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_PopedomPay));

		// 用于保存理赔金额中的保费值
		double t_Sum_InsFeePay = 0;
		t_Sum_InsFeePay = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_InsFeePay));

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
		 * 初始化LLClaimPolicySchema【赔案保单名细】 获取LCPol【保单信息】mLCPolSchema信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getLLClaimPolicy(pPolNo, pClaimType)) {
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0
		 * 查询LLToClaimDuty中该赔案，该保单，该理赔类型下的所有给付责任, 也就是保项，分别进行计算
		 * 主要针对于LLToClaimDuty表 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		LLToClaimDutyDB tLLToClaimDutyDB = new LLToClaimDutyDB();
		tLLToClaimDutyDB.setCaseNo(this.mClmNo);
		tLLToClaimDutyDB.setPolNo(pPolNo);
		tLLToClaimDutyDB.setGetDutyKind(pClaimType);

		LLToClaimDutySet tLLToClaimDutySet = tLLToClaimDutyDB.query();

		for (int i = 1; i <= tLLToClaimDutySet.size(); i++) {

			t_Sum_DutyCode = 0; // 初始化定义保项金额
			t_Sum_DutyFee = 0; // 医疗费用给付金代码,每次循环需要初始化		
			
			//标志该GetDutyCode是否有账单理赔计算，默认为false
			boolean tDutyFeeFlag = false;

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.1
			 * 创建匹配数据记录Schema,并得到理赔类型，给付责任编码
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LLToClaimDutySchema tLLToClaimDutySchema = tLLToClaimDutySet.get(i);
			String tContNo = StrTool.cTrim(tLLToClaimDutySchema.getContNo()); // 合同号
			String tPolNo = StrTool.cTrim(tLLToClaimDutySchema.getPolNo()); // 保单险种号
			String tCustNo = StrTool
					.cTrim(tLLToClaimDutySchema.getCustomerNo()); // 客户号
			String tDutyCode = StrTool
					.cTrim(tLLToClaimDutySchema.getDutyCode()); // 责任
			String tGetDutyKind = StrTool.cTrim(tLLToClaimDutySchema
					.getGetDutyKind()); // 理赔类型
			String tGetDutyCode = StrTool.cTrim(tLLToClaimDutySchema
					.getGetDutyCode()); // 给付责任
			String tCasePolType = StrTool.cTrim(tLLToClaimDutySchema
					.getCasePolType()); // 00投保人,01被保人

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.2
			 * 创建赔付明细LLClaimDetail记录的Schema，便于后面添加内容
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */

			if (!getLLClaimDetail(tLLToClaimDutySchema)) {
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.3
			 * 判断是否是加保记录,如果不是,则取出加保的保额. 如果是加保的数据,不进行后续的计算
			 * 因为加保的DutyCode为8为,正常的DutyCode的为6位
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tRiskCode = StrTool.cTrim(mLCPolSchema.getRiskCode());
			// if (tDutyCode.length() == 6 &&
			// !mLLClaimPubFunBL.getLMRiskSort(tRiskCode,"8"))
			//zy 2010-02-03 MS不适用加保的处理，因为增额的记录会作为一个单独的给付记录
			if (tDutyCode.length() == 6) // 2006-9-9 周磊 对于225类险种也应计算加保保项
			{
				double tDAddAmnt = 0;

				tSql = "select (case when sum(Amnt) is null then 0 else sum(Amnt) end) from LLToClaimDuty where 1=1"
						+ " and CaseNo='" + "?clmno?" + "'" + " and PolNo='"
						+ "?PolNo?" + "'" + " and GetDutyCode='"
						+ "?GetDutyCode?" + "'" + " and GetDutyKind='"
						+ "?GetDutyKind?" + "'" + " and DutyCode like concat('"
						+ "?DutyCode?" + "','%')" + " and DutyCode not in ('"
						+ "?DutyCode?" + "')";
				SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
				sqlbv7.sql(tSql);
				sqlbv7.put("clmno", this.mClmNo);
				sqlbv7.put("PolNo", mLCPolSchema.getPolNo());
				sqlbv7.put("GetDutyCode", tGetDutyCode);
				sqlbv7.put("GetDutyKind", tGetDutyKind);
				sqlbv7.put("DutyCode", tDutyCode);
				ExeSQL tExeSQL = new ExeSQL();
				String tSAddAmnt = StrTool.cTrim(tExeSQL.getOneValue(sqlbv7));

				logger.debug("======================================================================================================");
				logger.debug("--查询加保的保额信息[" + tSAddAmnt + "]:" + tSql);
				logger.debug("======================================================================================================");

				if (tExeSQL.mErrors.needDealError()) {
					CError.buildErr(this, "查询加保保项的有效保额失败!"
							+ tExeSQL.mErrors.getError(0).errorMessage);
					return false;
				}

				if (tSAddAmnt == null || tSAddAmnt.length() == 0) {
					tSAddAmnt = "0";
				}
//				tDAddAmnt = Double.parseDouble(tSAddAmnt);
				tDAddAmnt=Double.parseDouble("0");
				mLLClaimDetailSchema.setAddAmnt(tDAddAmnt);
			} else if (tDutyCode.length() == 10
					&& !mLLClaimPubFunBL.getLMRiskSort(tRiskCode, "8")) {
				mLLClaimDetailSchema.setOverAmnt("0");
				mLLClaimDetailSchema.setStandPay("0");
				mLLClaimDetailSchema.setRealPay("0");
				mLLClaimDetailSchema.setAddAmnt("0");
				mLLClaimDetailSet.add(mLLClaimDetailSchema);
				continue;
			} 
			

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.4
			 * 根据出险人编号查询查询立案分案信息,作为计算要素使用
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */

			mLLCaseSchema = mLLClaimPubFunBL.getLLCase(mClmNo, tCustNo);
			if (mLLCaseSchema == null) {
				mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.5
			 * 得到LCDuty【保单险种责任表】信息,mLCDutySchema, 为后面的运算，用风险保额RiskAmnt和理算金额进行比较
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */

			mLCDutySchema = mLLClaimPubFunBL.getLCDuty(tPolNo, tDutyCode);
			if (mLCDutySchema == null) {
				this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.6
			 * 根据保单号，给付责任编码得到LCGet【领取项表】的数据，tLCGetSchema 为下一步取LCDuty的数据做准备
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */

			mLCGetSchema = mLLClaimPubFunBL.getLCGet(tPolNo, tDutyCode,
					tGetDutyCode);
			if (mLCGetSchema == null) {
				this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.7
			 * 根据理赔类型，给付责任编码得到责任给付赔付计算公式代码 以及扩充公式描述信息
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */

			LMDutyGetClmSchema tLMDutyGetClmSchema = mLLClaimPubFunBL
					.getLMDutyGetClm(tGetDutyKind, tGetDutyCode);
			if (tLMDutyGetClmSchema == null) {
				this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
				return false;
			}

			String t_CalDutyCode = tLMDutyGetClmSchema.getCalCode();// //保项计算公式代码
			
			logger.debug("*************************tGetDutyCode:"+tLMDutyGetClmSchema.getGetDutyCode()+
					 ",CalCode:"+tLMDutyGetClmSchema.getCalCode());

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.9 根据计算公式进行正常计算
			 * 判断LMDutyGetClm表中的InpFlag录入标志进行判断 给付金是否需要输入(给付间隔等):
			 * 2-给付时需要录入,1-承保需要输入,0 or null -不需
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */

			String tInpFlag = StrTool.cTrim(tLMDutyGetClmSchema.getInpFlag());
			if (tInpFlag.length() == 0 || tInpFlag.equals("")
					|| tInpFlag == null) {
				tInpFlag = "0";
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.1 承保时确定给付金金额
			 * 根据计算公式进行非医疗费用(身故和重疾)计算【InpFlag＝1，承保时录入】
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tGiveType = StrTool
					.cTrim(tLLToClaimDutySchema.getGiveType());
			if ((tInpFlag.equals("0") || tInpFlag.equals("1"))
					&& tGiveType.equals("0")) {


				/* 保存计算前金额,用于显示 */
				t_Sum_Temp = mLLClaimDetailSchema.getAmnt()
						+ mLLClaimDetailSchema.getAddAmnt();
				t_Sum_DutyCode = executePay(t_Sum_Temp, t_CalDutyCode,"");


				logger.debug("======================================================================================================");
				logger.debug("死亡重疾类,理赔类型:[" + tGetDutyKind + "],保项编码:["
						+ tGetDutyCode + "],计算公式:[" + t_CalDutyCode
						+ "],计算前金额=" + t_Sum_Temp + ",计算后金额=" + t_Sum_DutyCode);
				logger.debug("======================================================================================================");

			}// if 初次结算金额结束

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.1
			 * 根据计算公式进行医疗费用计算【InpFlag＝2，医疗费用录入】 并且给付标志为0给付
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (tInpFlag.equals("2") && tGiveType.equals("0")) {
				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.2
				 * 查询LLToClaimDutyFee中该赔案，该保单，该理赔类型,该给付责任 下的所有费用信息
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */

				LLToClaimDutyFeeDB tLLToClaimDutyFeeDB = new LLToClaimDutyFeeDB();
				tLLToClaimDutyFeeDB.setClmNo(this.mClmNo);
				tLLToClaimDutyFeeDB.setPolNo(pPolNo);
				tLLToClaimDutyFeeDB.setDutyCode(tDutyCode);
				tLLToClaimDutyFeeDB.setGetDutyType(tGetDutyKind);
				tLLToClaimDutyFeeDB.setGetDutyCode(tGetDutyCode);

				LLToClaimDutyFeeSet tLLToClaimDutyFeeSet = tLLToClaimDutyFeeDB
						.query();

				for (int m = 1; m <= tLLToClaimDutyFeeSet.size(); m++) {

					LLToClaimDutyFeeSchema tLLToClaimDutyFeeSchema = tLLToClaimDutyFeeSet
							.get(m);

					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.3
					 * 将费用信息放入mLLClaimDutyFeeSchema中 便于后面计算使用
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */

					if (!getLLClaimDutyFee(tLLToClaimDutySchema,
							tLLToClaimDutyFeeSchema)) {
						return false;
					}

					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.4
					 * 计算门诊,住院,费用补偿型 查询LLToClaimDutyFeeSchema中该费用类型
					 * 对应LMDutyGetFeeRela表中的计算公式。
					 * 
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */
					String tDutyFeeType = mLLClaimDutyFeeSchema
							.getDutyFeeType();

					if (tDutyFeeType.equals("A") || tDutyFeeType.equals("B")||tDutyFeeType.equals("C")) {
						/**
						 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.4.1
						 * 找出计算公式并初始化理算金额 费用编码前面需要增加A门诊,B住院,C费用补偿
						 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
						 */

						LMDutyGetFeeRelaDB tLMDutyGetFeeRelaDB = new LMDutyGetFeeRelaDB();
						tSql = "select * from LMDutyGetFeeRela where 1 = 1 "
								+ " and GetDutyCode = '" + "?GetDutyCode?" + "'"
								+ " and GetDutyKind = '" + "?GetDutyKind?" + "'"
								+ " and ClmFeeCode like concat('" 
								+ "?ClmFeeCode?" + "','%')";
						SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
						sqlbv8.sql(tSql);
						sqlbv8.put("GetDutyCode", tGetDutyCode);
						sqlbv8.put("GetDutyKind", tGetDutyKind);
						sqlbv8.put("ClmFeeCode", tLLToClaimDutyFeeSchema.getDutyFeeCode().substring(0, 2));
						logger.debug("--LLClaimCalAutoBL中从LMDutyGetFeeRela查询算法的sql"+m+":"+tSql);

						LMDutyGetFeeRelaSet tLMDutyGetFeeRelaSet = tLMDutyGetFeeRelaDB
								.executeQuery(sqlbv8);

						logger.debug("======================================================================================================");
						logger.debug("--查询费用定义信息["
								+ tLMDutyGetFeeRelaSet.size() + "]:" + tSql);
						logger.debug("======================================================================================================");

						if (tLMDutyGetFeeRelaSet == null
								|| tLMDutyGetFeeRelaSet.size() == 0) {
							mErrors
									.addOneError("在LMDutyGetFeeRela表中没有查找到相关的数据，不能理算");
							return false;
						}

						/**
						 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.4.2
						 * 获取计算公式 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
						 */

						LMDutyGetFeeRelaSchema tLMDutyGetFeeRelaSchema = tLMDutyGetFeeRelaSet
								.get(1);
						
						//01-录入或为空则账单金额从界面录入
						if(tLMDutyGetFeeRelaSchema.getClmFeeCalType()==null||tLMDutyGetFeeRelaSchema.getClmFeeCalType().equals("")
								||tLMDutyGetFeeRelaSchema.getClmFeeCalType().equals("01"))
						{
			
							t_Sum_DutyFee = PubFun.setPrecision(tLLToClaimDutyFeeSchema.getAdjSum(), "0.00");//待理算金额
							t_Sum_DutyFeeFranchise = PubFun.setPrecision(tLLToClaimDutyFeeSchema.getOutDutyAmnt(),"0.00");//待理算免赔额
							
							t_Sum_Temp = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;
							t_Sum_DutyFee = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;//传入的账单的给付金就是减去免陪额之后的金额
							
						}
						//调用算法进行校验,符合校验则将账单金额累计到计算金额,否则该账单金额被过滤掉
						else if(tLMDutyGetFeeRelaSchema.getClmFeeCalType().equals("02"))
						{
							String tCalCode = tLMDutyGetFeeRelaSchema.getClmFeeCalCode();
							
							if (tCalCode != null && !tCalCode.equals("")) {
								t_Sum_DutyFee = PubFun.setPrecision(tLLToClaimDutyFeeSchema.getAdjSum(), "0.00");//待理算金额
								int tSum = (int)executePay(t_Sum_DutyFee, tCalCode,tLLToClaimDutyFeeSchema.getDutyFeeStaNo());
								
								if(tSum==1)
								{

									/**
									 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.4.3
									 * 根据计算公式进行计算 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
									 */
									t_Sum_DutyFee = PubFun.setPrecision(tLLToClaimDutyFeeSchema.getAdjSum(), "0.00");//待理算金额
									t_Sum_DutyFeeFranchise = PubFun.setPrecision(tLLToClaimDutyFeeSchema.getOutDutyAmnt(),"0.00");//待理算免赔额
									
									t_Sum_Temp = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;
									t_Sum_DutyFee = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;//传入的账单的给付金就是减去免陪额之后的金额

								}
							}
						}
						else
						{
							//取默认值
							t_Sum_DutyFee = PubFun.setPrecision(Double.parseDouble(tLMDutyGetFeeRelaSchema.getClmFeeDefValue()), "0.00");//待理算金额
							t_Sum_DutyFeeFranchise = PubFun.setPrecision(Double.parseDouble(tLMDutyGetFeeRelaSchema.getClmFeeDefValue()), "0.00");//待理算免赔额
							
							t_Sum_Temp = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;
							t_Sum_DutyFee = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;//传入的账单的给付金就是减去免陪额之后的金额

						
						}

						logger.debug("ClmNo(赔案号):"+tLLToClaimDutyFeeSchema.getClmNo()+",feeItemCode(账单子类型):"+tLLToClaimDutyFeeSchema.getDutyFeeCode()+
			            " ,原理算金额："+tLLToClaimDutyFeeSchema.getAdjSum()+",t_Sum_DutyFeeFranchise(免赔额):"+t_Sum_DutyFeeFranchise 
			             +" ,减去免赔额后的参与理算金额):"+t_Sum_DutyFee);

			

					}// if 门诊，住院,费用补偿型,计算结束
					

					

					/**
					 * 比例给付,原(伤残)费用计算
					 * 
					 */
					if (tDutyFeeType.equals("L")) {
						
						double tCaseRate = 0;
						tCaseRate = Double.parseDouble(new DecimalFormat("0.00").format(tCaseRate));
						tCaseRate = mLLClaimDutyFeeSchema.getRealRate();

						t_Sum_DutyFee = Arith.round(getOamnt() + getOAddAmnt(),
								2);
						t_Sum_Temp = t_Sum_DutyFee;

						t_Sum_DutyFee = Arith.round(t_Sum_DutyFee * tCaseRate,
								2);
						
						logger.debug("ClmNo(赔案号):"+mLLClaimDutyFeeSchema.getClmNo()+",feeItemCode(账单子类型):"+mLLClaimDutyFeeSchema.getDutyFeeCode()+
								",t_Sum_DutyFee(参与理算金额):"+t_Sum_Temp+",tCaseRate(给付比例):"+tCaseRate+",t_Sum_DutyFee2(理算后金额):"+t_Sum_DutyFee);
			

					}// if 比例给付,原(伤残)结束

					/*
					 * 2008-10-17 根据MS的医疗账单类型没有手术信息表,D--手术,E--特定疾病,F--特定给付，而且代码类别已不是D,E,F，在此将这段屏蔽掉
					 * 今后如果又需要再根据设定的项目代码进行程序修改
					 */
					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.6
					 * 手术、疾病，其他费用计算
					 * 
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */
					if (//tDutyFeeType.equals("D") || tDutyFeeType.equals("E")|| tDutyFeeType.equals("F")||
							tDutyFeeType.equals("T")) {
						/**
						 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.6.1
						 * 找出计算公式并初始化理算金额
						 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
						 */

						LMDutyGetFeeRelaDB tLMDutyGetFeeRelaDB = new LMDutyGetFeeRelaDB();
						tSql = "select * from LMDutyGetFeeRela where 1 = 1 "
								+ " and GetDutyCode = '" + "?GetDutyCode?" + "'"
								+ " and GetDutyKind = '" + "?GetDutyKind?" + "'"
								+ " and ClmFeeCode like concat('" + "?ClmFeeCode?" + "','%')";
						SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
						sqlbv9.sql(tSql);
						sqlbv9.put("GetDutyCode", tGetDutyCode);
						sqlbv9.put("GetDutyKind", tGetDutyKind);
						sqlbv9.put("ClmFeeCode", tLLToClaimDutyFeeSchema.getDutyFeeCode().substring(0, 2));
						logger.debug("--LLClaimCalAutoBL中从LMDutyGetFeeRela查询算法的sql:"+tSql);

						LMDutyGetFeeRelaSet tLMDutyGetFeeRelaSet = tLMDutyGetFeeRelaDB
								.executeQuery(sqlbv9);
						logger.debug("======================================================================================================");
						logger.debug("--查询费用定义信息["
								+ tLMDutyGetFeeRelaSet.size() + "]:" + tSql);
						logger.debug("======================================================================================================");

						if (tLMDutyGetFeeRelaDB.mErrors.needDealError()) {
							mErrors.addOneError("查询产品费用定义信息出错，不能理算");
							return false;
						}

						if (tLMDutyGetFeeRelaSet == null
								|| tLMDutyGetFeeRelaSet.size() == 0) {
							mErrors.addOneError("查询该产品费用定义信息出错，不能理算");
							return false;
						}

						/**
						 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.6.2
						 * 获取计算公式 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
						 */
						LMDutyGetFeeRelaSchema tLMDutyGetFeeRelaSchema = tLMDutyGetFeeRelaSet
								.get(1);
						
						//01-录入或为空则账单金额从界面录入
						if(tLMDutyGetFeeRelaSchema.getClmFeeCalType()==null||tLMDutyGetFeeRelaSchema.getClmFeeCalType().equals("")
								||tLMDutyGetFeeRelaSchema.getClmFeeCalType().equals("01"))
						{
							/**
							 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.4.3
							 * 根据计算公式进行计算 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
							 */
							t_Sum_DutyFee = PubFun.setPrecision(tLLToClaimDutyFeeSchema.getAdjSum(), "0.00");//待理算金额
							t_Sum_DutyFeeFranchise = PubFun.setPrecision(tLLToClaimDutyFeeSchema.getOutDutyAmnt(),"0.00");//待理算免赔额
							
							t_Sum_Temp = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;
							t_Sum_DutyFee = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;//传入的账单的给付金就是减去免陪额之后的金额
							
							}
						//调用算法进行校验,符合校验则将账单金额累计到计算金额,否则该账单金额被过滤掉
						else if(tLMDutyGetFeeRelaSchema.getClmFeeCalType().equals("02"))
						{
							String tCalCode = tLMDutyGetFeeRelaSchema.getClmFeeCalCode();
							
							if (tCalCode != null && !tCalCode.equals("")) {
								
								int tSum = (int)executePay(t_Sum_DutyFee, tCalCode,tLLToClaimDutyFeeSchema.getDutyFeeStaNo());
								
								if(tSum==1)
								{

									/**
									 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.4.3
									 * 根据计算公式进行计算 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
									 */
									t_Sum_DutyFee = PubFun.setPrecision(tLLToClaimDutyFeeSchema.getAdjSum(), "0.00");//待理算金额
									t_Sum_DutyFeeFranchise = PubFun.setPrecision(tLLToClaimDutyFeeSchema.getOutDutyAmnt(),"0.00");//待理算免赔额
									
									t_Sum_Temp = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;
									t_Sum_DutyFee = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;//传入的账单的给付金就是减去免陪额之后的金额

								}
							}
						}
						else
						{
							//取默认值
							t_Sum_DutyFee = PubFun.setPrecision(Double.parseDouble(tLMDutyGetFeeRelaSchema.getClmFeeDefValue()), "0.00");//待理算金额
							t_Sum_DutyFeeFranchise = PubFun.setPrecision(Double.parseDouble(tLMDutyGetFeeRelaSchema.getClmFeeDefValue()), "0.00");//待理算免赔额
							
							t_Sum_Temp = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;
							t_Sum_DutyFee = t_Sum_DutyFee - t_Sum_DutyFeeFranchise;//传入的账单的给付金就是减去免陪额之后的金额

						
						}

						logger.debug("ClmNo(赔案号):"+tLLToClaimDutyFeeSchema.getClmNo()+",feeItemCode(账单子类型):"+tLLToClaimDutyFeeSchema.getDutyFeeCode()+
					            " ,原理算金额："+tLLToClaimDutyFeeSchema.getAdjSum()+",t_Sum_DutyFeeFranchise(免赔额):"+t_Sum_DutyFeeFranchise 
					             +" ,减去免赔额后的参与理算金额):"+t_Sum_DutyFee);

					}// if 手术、疾病，其他费用结束

					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.7
					 * 进行医疗费用层的置值 及 保项层面的金额累计
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */

					//TODO
					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.7.5 医疗险理赔累加器处理,
					 * sl add 2015-03-24 加入累加器处理，这是账单级别的累加器处理
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */
					ClaimCalculatorFactor tCalculator = new ClaimCalculatorFactor();
					tCalculator.setLLClaimDetailSchema(mLLClaimDetailSchema);
					tCalculator.setLLRegisterSchema(mLLRegisterSchema);
					tCalculator.setApplyPay(t_Sum_DutyFee);
					//只有账单理赔计算需要传入mLLClaimDutyFeeSchema：用于在累加器trace中保存响应的账单索引信息
					mLLClaimDutyFeeSchema.setCalSum(t_Sum_DutyFee);
					tCalculator.setLLClaimDutyFeeSchema(mLLClaimDutyFeeSchema);
					tCalculator.setLLCaseSchema(mLLCaseSchema);
					double tCalResult = tCalculator.getClaimCalculationResult();
					mLCalculatorTraceSet.add(tCalculator.getLCalculatorTraceSet());
					//如果在账单级别已经计算过了，就将此flag设置为true，避免后面GetDutyCode层级重复计算累加器
					tDutyFeeFlag = true;
					if (tCalResult < t_Sum_DutyFee){
						//如果算后值小于算前值，需要替换
						t_Sum_DutyFee = tCalResult;
					}
					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.7.5 医疗险理赔累加器处理, END
					 */


					mLLClaimDutyFeeSchema.setCalSum(t_Sum_DutyFee);
					mLLClaimDutyFeeSet.add(mLLClaimDutyFeeSchema);
					t_Sum_DutyCode = t_Sum_DutyCode + t_Sum_DutyFee;

					logger.debug("======================================================================================================");
					logger.debug("医疗费用层:理赔类型:["
							+ tLLToClaimDutySchema.getGetDutyKind()
							+ "],保项编码:["
							+ tLLToClaimDutySchema.getGetDutyCode()
							+ "],计算公式:[" + t_CalDutyCode + "],医疗计算金额="
							+ t_Sum_Temp + ",理算结果=" + t_Sum_DutyFee
							+ ",费用类型[" + tDutyFeeType + "],费用代码["
							+ mLLClaimDutyFeeSchema.getDutyFeeCode()
							+ "],费用名称["
							+ mLLClaimDutyFeeSchema.getDutyFeeName() + "]");
					logger.debug("======================================================================================================");

				}// for循环结束

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.8
				 * 医疗费用最后进行保项层面的计算
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				t_Sum_Temp = t_Sum_DutyCode; /* 保存计算前金额,用于显示 */
				t_Sum_DutyCode = executePay(t_Sum_DutyCode, t_CalDutyCode,"");
				
//				/**
//				 * 2008-12-9 
//				 * zhangzheng
//				 * 保项的金额最低为0,不能出现负值
//				 */
//				if(t_Sum_DutyCode<0)
//				{
//					t_Sum_DutyCode=0;
//				}

				logger.debug("======================================================================================================");
				logger.debug("医疗保项层:理赔类型:["
						+ tLLToClaimDutySchema.getGetDutyKind() + "],保项编码:["
						+ tLLToClaimDutySchema.getGetDutyCode() + "],计算公式:["
						+ t_CalDutyCode + "],计算前金额=" + t_Sum_Temp + ",计算后金额="
						+ t_Sum_DutyCode);
				logger.debug("======================================================================================================");

			}// if 7.8结束,【InpFlag＝2，医疗费用录入】

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No9.1
			 * 根据计算公式进行帐户型费用计算【InpFlag＝3，帐户处理】 大于等于18周岁 并且 非死亡,则 正常理算
			 * 根据累计帐户余额+利息 计算 保项值 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (tInpFlag.equals("3") && tGiveType.equals("0")) {
				
				mLLClaimDetailSchema.setAcctFlag("1");// 帐户标志.0非帐户,1个险帐户

				t_Sum_Temp = mLLClaimDetailSchema.getAmnt();
				t_Sum_DutyCode = executePay(t_Sum_Temp, t_CalDutyCode,"");

				logger.debug("======================================================================================================");
				logger.debug("帐户保项层:理赔类型:["
						+ tLLToClaimDutySchema.getGetDutyKind() + "],保项编码:["
						+ tLLToClaimDutySchema.getGetDutyCode() + "],计算公式:["
						+ t_CalDutyCode + "],计算前金额=" + t_Sum_Temp + ",计算后金额="
						+ t_Sum_DutyCode);
				logger.debug("======================================================================================================");

			}// if 9.4结束,【InpFlag＝3，帐户费用】

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No10.1 计算不用于权限分配的金额
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LMDutyGetClmCalSchema tLMDutyGetClmCalSchema = mLLClaimPubFunBL
					.getLMDutyGetClmCal(tGetDutyKind, tGetDutyCode);// 扩充公式
			String tCalCode2 = StrTool.cTrim(tLMDutyGetClmCalSchema
					.getCalCode2());
			logger.debug("tCalCode2:"+tCalCode2);
			if(!(tCalCode2==null||tCalCode2.equals("")))
			{
				t_Sum_PopedomPay = executePay(t_Sum_Temp, tCalCode2,"");

				logger.debug("======================================================================================================");
				logger.debug("不用于权限分配的理赔金:[" + tGetDutyKind + "],保项编码:["
						+ tGetDutyCode + "],计算公式:[" + tCalCode2 + "],计算前金额="
						+ t_Sum_Temp + ",计算后金额=" + t_Sum_PopedomPay);
				logger.debug("======================================================================================================");
			}


			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No10.2 计算出险人年龄
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			int tInsPerAge = 0;
			if (tCasePolType.equals("00")
					&& tGetDutyKind.substring(2, 3).equals("2")) {
				LCAppntSchema tLCAppntSchema = mLLClaimPubFunBL.getLCAppnt(
						tContNo, tCustNo);
				if (tLCAppntSchema == null) {
					this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
					return false;
				}
				tInsPerAge = PubFun.calInterval(tLCAppntSchema
						.getAppntBirthday(), this.mInsDate, "Y"); // 出险人的投保年龄
			} else if (tCasePolType.equals("01")
					&& tGetDutyKind.substring(2, 3).equals("2")) {
				LCInsuredSchema tLCInsuredSchema = mLLClaimPubFunBL
						.getLCInsured(tContNo, tCustNo);
				if (tLCInsuredSchema == null) {
					this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
					return false;
				}
				tInsPerAge = PubFun.calInterval(tLCInsuredSchema.getBirthday(),
						this.mInsDate, "Y"); // 出险人的投保年龄
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No10.3
			 * 小于18周岁[等于18看作成年人] 并且 死亡 , 调用扩充计算公式CalCode2进行计算
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (tInsPerAge < 18 && tGetDutyKind.substring(2, 3).equals("2")) {
				double tPupilAmnt = Arith.round(mLLClaimPubFunBL
						.getLLParaPupilAmnt(mLCPolSchema), 2);// 限制保额
				mPupilAmnt = tPupilAmnt;

				if (tPupilAmnt == -1) {
					mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
					String tSignCom = StrTool.cTrim(mLCPolSchema.getSignCom()); // 签单机构
					mErrors.addOneError("请注意,出险人为未成年人,因为合同号[" + tContNo
							+ "],险种号[" + tPolNo + "],签单机构[" + tSignCom
							+ "]没有取到未成年人限额，所以按照正常理算!!!");
				} else {
					double tStandMoney = Arith.round(mLLClaimDetailSchema
							.getAmnt(), 2); // 有效保额
					t_Sum_Temp = tStandMoney;

					// 有效保额 > 限额，取限额
					if (tStandMoney > tPupilAmnt) {
						t_Sum_Temp = tPupilAmnt;
					}

//					String tCalCode1 = StrTool.cTrim(tLMDutyGetClmCalSchema
//							.getCalCode1());
//					t_Sum_DutyCode = executePay(t_Sum_Temp, tCalCode1,"");
//
//					System.out
//							.println("======================================================================================================");
//					logger.debug("小于18周岁死亡理赔金:[" + tGetDutyKind
//							+ "],保项编码:[" + tGetDutyCode + "],计算公式:["
//							+ tCalCode1 + "],计算前金额=" + tStandMoney + ",计算后金额="
//							+ t_Sum_DutyCode);
//					System.out
//							.println("======================================================================================================");
					
					// 原来是：保额与限额取小后用Lmdutygetclmcal算法重算，现在改为前面的理算金额与限额取小，作为理算金额
					if (t_Sum_DutyCode > tPupilAmnt) {
						t_Sum_DutyCode = tPupilAmnt;
					}
					
					String tCalCode3 = StrTool.cTrim(tLMDutyGetClmCalSchema
							.getCalCode3());
					t_Sum_PopedomPay = executePay(t_Sum_Temp, tCalCode3,"");

					logger.debug("======================================================================================================");
					logger.debug("小于18周岁不用于权限分配的理赔金:[" + tGetDutyKind
							+ "],保项编码:[" + tGetDutyCode + "],计算公式:["
							+ tCalCode3 + "],计算前金额=" + t_Sum_Temp + ",计算后金额="
							+ t_Sum_PopedomPay);
					logger.debug("======================================================================================================");

					mErrors.addOneError("请注意,出险人在出险时点为[" + tInsPerAge
							+ "]岁,为未成年人,请关注赔付金额,不要超过限额!!!");
				}
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No11.1
			 * 计算出的保项金额和保额相比较，不能超过标准保额 保项理算金额 >= 标准保额 － 已领金额 则保项理算金额 ＝ 标准保额 －
			 * 已领金额 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			tRiskCode = StrTool.cTrim(mLCPolSchema.getRiskCode());
			if (mLLClaimPubFunBL.getLMRiskSort(tRiskCode, "7")) {
				t_Sum_Temp = t_Sum_DutyCode;
				t_Sum_DutyBalCode = PubFun.setPrecision(mLLClaimDetailSchema
						.getAmnt()
						+ mLLClaimDetailSchema.getAddAmnt(), "0.00");

				if (t_Sum_DutyCode >= t_Sum_DutyBalCode) {
					t_Sum_DutyCode = t_Sum_DutyBalCode;
				}

				logger.debug("======================================================================================================");
				logger.debug("判断是否冲减保额:冲减前金额=" + t_Sum_Temp + ",冲减后金额="
						+ t_Sum_DutyCode);
				logger.debug("======================================================================================================");
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No11.2
			 * 对于承保前出险，只赔付保额的20％，最多不超过5万 判断条件: 1承保前保单 2出险原因为意外 3理赔类型不能是豁免
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tPolSort = mLLClaimDetailSchema.getPolSort();
			if (tPolSort.equals("A")
					&& tGetDutyKind.substring(0, 1).equals("1")
					&& !tGetDutyKind.substring(2, 3).equals("9")) {
				t_Sum_Temp = PubFun.setPrecision(mLCGetSchema.getStandMoney()
						- mLCGetSchema.getSumMoney(), "0.00");
				t_Sum_Temp = t_Sum_Temp * 0.2;

				t_Sum_DutyCode = t_Sum_Temp;
				if (t_Sum_DutyCode >= 50000) {
					t_Sum_DutyCode = 50000;
				}
				logger.debug("======================================================================================================");
				logger.debug("承保前出险:,计算后金额=" + t_Sum_DutyCode);
				logger.debug("======================================================================================================");
			}

			
			//TODO
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No12.1 医疗险理赔累加器处理,
			 * sl add 2015-03-24 加入累加器处理，这是保项目级别的累加器处理
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (!tDutyFeeFlag){
				//tDutyFeeFlag=true表示在账单层级已经计算过累加器了，那么在GetDutyCode级别就不能再重复计算了
				ClaimCalculatorFactor tCalculator = new ClaimCalculatorFactor();
				tCalculator.setLLClaimDetailSchema(mLLClaimDetailSchema);
				tCalculator.setLLRegisterSchema(mLLRegisterSchema);
				//xuyunpeng add 
				//tCalculator.setLLClaimDutyFeeSchema(mLLClaimDutyFeeSchema);
				tCalculator.setLLCaseSchema(mLLCaseSchema);
				//xuyunpeng add 50000 change by t_Sum_DutyCode 
				tCalculator.setApplyPay(t_Sum_DutyCode);
				double tCalResult = tCalculator.getClaimCalculationResult();
				mLCalculatorTraceSet.add(tCalculator.getLCalculatorTraceSet());
				if (tCalResult < t_Sum_DutyCode){
					//如果算后值小于算前值，需要替换   
					t_Sum_DutyCode = tCalResult;
				}
			}
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No12.1 医疗险理赔累加器处理, END
			 */

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No13.1 格式化 总给付金额
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tSumMoney = new DecimalFormat("0.00").format(Arith.round(
					t_Sum_DutyCode, 2));
			t_Sum_DutyCode = Double.parseDouble(tSumMoney);

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No13.2
			 * 保单理赔类型的给付总金额,用于累计计算
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (tLLToClaimDutySchema.getGiveType().equals("0")) {
				t_Sum_DutyKind = t_Sum_DutyCode + t_Sum_DutyKind;
			} else {
				t_Sum_DutyCode = 0;
			}


			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No13.3 更新赔案保项层面记录
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			mLLClaimDetailSchema.setStatType(tLMDutyGetClmSchema.getStatType());
			mLLClaimDetailSchema.setOverAmnt(0);
			mLLClaimDetailSchema.setStandPay(t_Sum_DutyCode); // 核算赔付金额
			mLLClaimDetailSchema.setRealPay(t_Sum_DutyCode); // 核赔赔付金额
			mLLClaimDetailSchema.setPopedomPay(t_Sum_PopedomPay); // 权限理赔额
			mLLClaimDetailSchema.setInsFeePay(t_Sum_InsFeePay); // 保费理赔额
			mLLClaimDetailSet.add(mLLClaimDetailSchema);

		} // No7.0 for 结束

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No15.1 更新赔案明细记录
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		mLLClaimPolicySchema.setStandPay(t_Sum_DutyKind);
		mLLClaimPolicySchema.setRealPay(t_Sum_DutyKind);
		mLLClaimPolicySchema.setGiveType("0");
		mLLClaimPolicySchema.setGiveTypeDesc("");

		mLLClaimPolicySet.add(mLLClaimPolicySchema);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No15.2 更新赔案层面记录 的 核算赔付金额 及
		 * 最后给付金额 最后给付金额 = 赔案给付金额 - 预付金额 + 结算金额
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if(mLLClaimSchema.getCurrency().equals(mLLClaimPolicySchema.getCurrency()))
			t_Sum_Claim = t_Sum_Claim + t_Sum_DutyKind;
		else
		{
			LDExch tLDExch = new LDExch();
			t_Sum_Claim = t_Sum_Claim + tLDExch.toOtherCur(mLLClaimPolicySchema.getCurrency(),mLLClaimSchema.getCurrency(),PubFun.getCurrentDate(),t_Sum_DutyKind);
		}		
		mLLClaimSchema.setStandPay(t_Sum_Claim);

		t_Sum_Claim = Arith.round(mLLClaimSchema.getStandPay()
				- mLLClaimSchema.getBeforePay()
				+ mLLClaimSchema.getBalancePay(), 2);
		mLLClaimSchema.setRealPay(t_Sum_Claim);		

		return true;
	}

	/**
	 * 目的：得到赔案保单名细,并得到个人险种表信息
	 * 
	 * @return
	 */
	private boolean getLLClaimPolicy(String pPolNo, String pClaimType) {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0
		 * 获取LCPol【保单信息】mLCPolSchema信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		mLCPolSchema = mLLClaimPubFunBL.getLCPol(pPolNo);
		if (mLCPolSchema == null) {
			this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
		 * 初始化LLClaimPolicySchema【赔案保单名细】
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		mLLClaimPolicySchema = new LLClaimPolicySchema();
		mLLClaimPolicySchema.setClmNo(this.mClmNo); // 赔案号
		mLLClaimPolicySchema.setCaseNo(this.mClmNo); // 分案号
		mLLClaimPolicySchema.setCaseRelaNo(this.mAccNo); // 事件号
		mLLClaimPolicySchema.setRgtNo(this.mClmNo); // 赔案号
		mLLClaimPolicySchema.setGetDutyKind(pClaimType); // 理赔类型

		mLLClaimPolicySchema.setGrpContNo(mLCPolSchema.getGrpContNo()); // 集体合同号
		mLLClaimPolicySchema.setGrpPolNo(mLCPolSchema.getGrpPolNo()); // 集体险种保单号
		mLLClaimPolicySchema.setContNo(mLCPolSchema.getContNo()); // 合同号
		mLLClaimPolicySchema.setPolNo(pPolNo); // 保单号

		mLLClaimPolicySchema.setKindCode(mLCPolSchema.getKindCode()); // 险类代码
		mLLClaimPolicySchema.setRiskCode(mLCPolSchema.getRiskCode()); // 险种代码

		mLLClaimPolicySchema.setRiskVer(mLCPolSchema.getRiskVersion()); // 险种版本号
		mLLClaimPolicySchema.setPolMngCom(mLCPolSchema.getManageCom()); // 保单管理机构
		mLLClaimPolicySchema.setSaleChnl(mLCPolSchema.getSaleChnl()); // 销售渠道

		mLLClaimPolicySchema.setAgentCode(mLCPolSchema.getAgentCode()); // 代理人代码
		mLLClaimPolicySchema.setAgentGroup(mLCPolSchema.getAgentGroup()); // 代理人组别

		mLLClaimPolicySchema.setInsuredNo(mLCPolSchema.getInsuredNo()); // 被保人客户号
		mLLClaimPolicySchema.setInsuredName(mLCPolSchema.getInsuredName()); // 被保人名称
		mLLClaimPolicySchema.setAppntNo(mLCPolSchema.getAppntNo()); // 投保人客户号
		mLLClaimPolicySchema.setAppntName(mLCPolSchema.getAppntName()); // 投保人名称
		mLLClaimPolicySchema.setCValiDate(mLCPolSchema.getCValiDate()); // 保单生效日期
		mLLClaimPolicySchema.setPolState(mLCPolSchema.getPolState()); // 保单状态

		mLLClaimPolicySchema.setClmUWer(this.mGlobalInput.Operator); // 理赔员
		mLLClaimPolicySchema.setMngCom(this.mGlobalInput.ManageCom);
		mLLClaimPolicySchema.setMakeDate(PubFun.getCurrentDate());
		mLLClaimPolicySchema.setMakeTime(PubFun.getCurrentTime());
		mLLClaimPolicySchema.setModifyDate(PubFun.getCurrentDate());
		mLLClaimPolicySchema.setModifyTime(PubFun.getCurrentTime());
		mLLClaimPolicySchema.setOperator(this.mGlobalInput.Operator);
		mLLClaimPolicySchema.setCurrency(mLCPolSchema.getCurrency());

		return true;
	}

	/**
	 * 目的：得到赔付明细信息
	 * 
	 * @param tLLToClaimDutySchema
	 * @return
	 */
	private boolean getLLClaimDetail(LLToClaimDutySchema tLLToClaimDutySchema) {

		String tPolNo = StrTool.cTrim(tLLToClaimDutySchema.getPolNo());
		String tDutyCode = StrTool.cTrim(tLLToClaimDutySchema.getDutyCode());
		String tGetDutyKind = StrTool.cTrim(tLLToClaimDutySchema
				.getGetDutyKind());
		String tGetDutyCode = StrTool.cTrim(tLLToClaimDutySchema
				.getGetDutyCode());

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 查询并创建LLClaimDetail信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
		tLLClaimDetailDB.setClmNo(this.mClmNo);
		tLLClaimDetailDB.setPolNo(tPolNo);
		tLLClaimDetailDB.setDutyCode(tDutyCode);
		tLLClaimDetailDB.setGetDutyKind(tGetDutyKind);
		tLLClaimDetailDB.setGetDutyCode(tGetDutyCode);
		LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB.query();
		if (tLLClaimDetailSet != null && tLLClaimDetailSet.size() == 1) {
			mLLClaimDetailSchema = tLLClaimDetailSet.get(1);
		} else {
			mLLClaimDetailSchema = new LLClaimDetailSchema();
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 给LLClaimDetail信息置值
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		mLLClaimDetailSchema.setClmNo(this.mClmNo);
		mLLClaimDetailSchema.setCaseNo(mLLClaimPolicySchema.getCaseNo());
		mLLClaimDetailSchema.setPolNo(mLCPolSchema.getPolNo());
		mLLClaimDetailSchema.setPolSort(tLLToClaimDutySchema.getPolSort()); // 保单类型,C表保单，B表保单,A承保出单前出险
		mLLClaimDetailSchema.setPolType(tLLToClaimDutySchema.getPolType()); /*
																			 * 保单性质0
																			 * --个人单,1
																			 * --无名单,2
																			 * --（团单）公共帐户
																			 */

		mLLClaimDetailSchema.setDutyCode(tDutyCode);
		mLLClaimDetailSchema.setGetDutyKind(tLLToClaimDutySchema
				.getGetDutyKind());
		mLLClaimDetailSchema.setGetDutyCode(tLLToClaimDutySchema
				.getGetDutyCode());
		mLLClaimDetailSchema
				.setCaseRelaNo(mLLClaimPolicySchema.getCaseRelaNo());

		mLLClaimDetailSchema.setDefoType(""); /* 伤残类型 */
		mLLClaimDetailSchema.setRgtNo(this.mClmNo); /* 立案号 */
		mLLClaimDetailSchema.setDeclineNo(""); /* 拒赔号 */
		mLLClaimDetailSchema.setStatType(""); /* 统计类别 */

		mLLClaimDetailSchema.setContNo(mLCPolSchema.getContNo());
		mLLClaimDetailSchema.setGrpPolNo(mLCPolSchema.getGrpPolNo());
		mLLClaimDetailSchema.setGrpContNo(mLCPolSchema.getGrpContNo());

		mLLClaimDetailSchema.setKindCode(mLCPolSchema.getKindCode());
		mLLClaimDetailSchema.setRiskCode(mLCPolSchema.getRiskCode());
		mLLClaimDetailSchema.setRiskVer(mLCPolSchema.getRiskVersion());
		mLLClaimDetailSchema.setPolMngCom(mLCPolSchema.getManageCom());
		mLLClaimDetailSchema.setSaleChnl(mLCPolSchema.getSaleChnl());
		mLLClaimDetailSchema.setAgentCode(mLCPolSchema.getAgentCode());
		mLLClaimDetailSchema.setAgentGroup(mLCPolSchema.getAgentGroup());

		mLLClaimDetailSchema.setAmnt(tLLToClaimDutySchema.getAmnt()); // 有效保额
		mLLClaimDetailSchema.setGracePeriod(tLLToClaimDutySchema
				.getGracePeriod()); // 缴费宽限期
		mLLClaimDetailSchema.setCasePolType(tLLToClaimDutySchema
				.getCasePolType()); // 给付类型
		mLLClaimDetailSchema.setYearBonus(tLLToClaimDutySchema.getYearBonus()); // 年度红利
		mLLClaimDetailSchema.setEndBonus(tLLToClaimDutySchema.getEndBonus()); // 终了红利
		mLLClaimDetailSchema.setGiveType(tLLToClaimDutySchema.getGiveType()); // 给付标志
		mLLClaimDetailSchema
				.setCustomerNo(tLLToClaimDutySchema.getCustomerNo()); // 用于存放出险人编号
		mLLClaimDetailSchema
				.setPrepayFlag(tLLToClaimDutySchema.getPrepayFlag()); // 预付标志,0没有预付,1已经预付
		mLLClaimDetailSchema.setPrepaySum(tLLToClaimDutySchema.getPrepaySum()); // 预付金额
		mLLClaimDetailSchema.setAcctFlag("0"); // 帐户标志.0非帐户,1个险帐户
		mLLClaimDetailSchema.setPosFlag(tLLToClaimDutySchema.getPosFlag()); // 0未做保全,1已做保全
		mLLClaimDetailSchema.setPosEdorNo(tLLToClaimDutySchema.getPosEdorNo()); // 保全批单号
		mLLClaimDetailSchema.setCValiDate(mLCPolSchema.getCValiDate()); // 保单生效日期
		mLLClaimDetailSchema.setEffectOnMainRisk(tLLToClaimDutySchema
				.getEffectOnMainRisk());// 附加险影响主险标志
		mLLClaimDetailSchema.setRiskCalCode(tLLToClaimDutySchema
				.getRiskCalCode()); // 用于保存主险的公式
		mLLClaimDetailSchema.setAddAmnt("0"); // 加保保额
		mLLClaimDetailSchema.setNBPolNo(tLLToClaimDutySchema.getNBPolNo()); // 做过续期抽档之后,把原号保存起来,用于后续业务
		mLLClaimDetailSchema.setPosEdorType(tLLToClaimDutySchema
				.getPosEdorType()); // 保全业务类型
		mLLClaimDetailSchema.setRiskType(tLLToClaimDutySchema.getRiskType()); // 主附险标志
																				// M主险
																				// S附险

		mLLClaimDetailSchema.setMakeDate(PubFun.getCurrentDate());
		mLLClaimDetailSchema.setMakeTime(PubFun.getCurrentTime());
		mLLClaimDetailSchema.setModifyDate(PubFun.getCurrentDate());
		mLLClaimDetailSchema.setModifyTime(PubFun.getCurrentTime());
		mLLClaimDetailSchema.setOperator(this.mGlobalInput.Operator);
		mLLClaimDetailSchema.setMngCom(this.mGlobalInput.ManageCom);
		mLLClaimDetailSchema.setCurrency(tLLToClaimDutySchema.getCurrency());

		return true;
	}

	/**
	 * 目的：得到责任费用统计信息
	 * 
	 * @return
	 */
	private boolean getLLClaimDutyFee(LLToClaimDutySchema tLLToClaimDutySchema,
			LLToClaimDutyFeeSchema tLLToClaimDutyFeeSchema) {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		mLLClaimDutyFeeSchema = new LLClaimDutyFeeSchema();

		mLLClaimDutyFeeSchema.setClmNo(tLLToClaimDutyFeeSchema.getClmNo()); // 赔案号
		mLLClaimDutyFeeSchema.setCaseNo(tLLToClaimDutyFeeSchema.getCaseNo()); // 分案号
		mLLClaimDutyFeeSchema.setPolNo(tLLToClaimDutyFeeSchema.getPolNo()); // 保单号

		mLLClaimDutyFeeSchema.setDutyCode(tLLToClaimDutySchema.getDutyCode()); // 给付责任类型
		mLLClaimDutyFeeSchema.setGetDutyType(tLLToClaimDutyFeeSchema
				.getGetDutyType()); // 给付责任类型
		mLLClaimDutyFeeSchema.setGetDutyCode(tLLToClaimDutyFeeSchema
				.getGetDutyCode()); // 给付责任编码

		mLLClaimDutyFeeSchema.setDutyFeeType(tLLToClaimDutyFeeSchema
				.getDutyFeeType()); // 费用类型
		mLLClaimDutyFeeSchema.setDutyFeeCode(tLLToClaimDutyFeeSchema
				.getDutyFeeCode()); // 费用代码
		mLLClaimDutyFeeSchema.setDutyFeeName(tLLToClaimDutyFeeSchema
				.getDutyFeeName()); // 费用名称
		mLLClaimDutyFeeSchema.setDutyFeeStaNo(tLLToClaimDutyFeeSchema
				.getDutyFeeStaNo()); // 账单费用明细序号

		mLLClaimDutyFeeSchema
				.setKindCode(tLLToClaimDutyFeeSchema.getKindCode()); // 险类代码
		mLLClaimDutyFeeSchema
				.setRiskCode(tLLToClaimDutyFeeSchema.getRiskCode()); // 险种代码
		mLLClaimDutyFeeSchema.setRiskVer(tLLToClaimDutyFeeSchema.getRiskVer()); // 险种版本号
		mLLClaimDutyFeeSchema.setPolMngCom(tLLToClaimDutyFeeSchema
				.getPolMngCom()); // 保单管理机构

		mLLClaimDutyFeeSchema.setHosID(tLLToClaimDutyFeeSchema.getHosID()); // 医院编号
		mLLClaimDutyFeeSchema.setHosName(tLLToClaimDutyFeeSchema.getHosName()); // 医院名称
		mLLClaimDutyFeeSchema
				.setHosGrade(tLLToClaimDutyFeeSchema.getHosGrade()); // 医院等级

		mLLClaimDutyFeeSchema.setStartDate(tLLToClaimDutyFeeSchema
				.getStartDate()); // 开始时间
		mLLClaimDutyFeeSchema.setEndDate(tLLToClaimDutyFeeSchema.getEndDate()); // 结束时间
		mLLClaimDutyFeeSchema
				.setDayCount(tLLToClaimDutyFeeSchema.getDayCount()); // 天数

		mLLClaimDutyFeeSchema.setOriSum(tLLToClaimDutyFeeSchema.getOriSum()); // 原始金额
		mLLClaimDutyFeeSchema.setAdjSum(tLLToClaimDutyFeeSchema.getAdjSum()); // 调整金额
		mLLClaimDutyFeeSchema.setCalSum(tLLToClaimDutyFeeSchema.getCalSum()); // 理算金额

		mLLClaimDutyFeeSchema
				.setDefoType(tLLToClaimDutyFeeSchema.getDefoType()); // 伤残类型
		mLLClaimDutyFeeSchema
				.setDefoCode(tLLToClaimDutyFeeSchema.getDefoCode()); // 伤残代码
		mLLClaimDutyFeeSchema.setDefoeName(tLLToClaimDutyFeeSchema
				.getDefoeName()); // 伤残级别名称
		mLLClaimDutyFeeSchema
				.setDefoRate(tLLToClaimDutyFeeSchema.getDefoRate()); // 残疾给付比例
		mLLClaimDutyFeeSchema
				.setRealRate(tLLToClaimDutyFeeSchema.getRealRate()); // 实际给付比例

		mLLClaimDutyFeeSchema.setOutDutyAmnt(tLLToClaimDutyFeeSchema
				.getOutDutyAmnt());// 免赔额

		mLLClaimDutyFeeSchema.setMakeDate(PubFun.getCurrentDate());
		mLLClaimDutyFeeSchema.setModifyDate(PubFun.getCurrentDate());
		mLLClaimDutyFeeSchema.setMakeTime(PubFun.getCurrentTime());
		mLLClaimDutyFeeSchema.setModifyTime(PubFun.getCurrentTime());
		mLLClaimDutyFeeSchema.setOperator(this.mGlobalInput.Operator);
		mLLClaimDutyFeeSchema.setMngCom(this.mGlobalInput.ManageCom);

		mLLClaimDutyFeeSchema.setGrpContNo(tLLToClaimDutySchema.getGrpContNo());
		mLLClaimDutyFeeSchema.setGrpPolNo(tLLToClaimDutySchema.getGrpPolNo());
		mLLClaimDutyFeeSchema.setContNo(tLLToClaimDutySchema.getContNo());
		mLLClaimDutyFeeSchema.setDutyCode(tLLToClaimDutySchema.getDutyCode());

		mLLClaimDutyFeeSchema.setNBPolNo(tLLToClaimDutySchema.getNBPolNo());// 做过续期抽档之后,把原号保存起来,用于后续业务

		mLLClaimDutyFeeSchema.setAdjReason(tLLToClaimDutyFeeSchema
				.getAdjReason()); // 调整原因
		mLLClaimDutyFeeSchema.setAdjRemark(tLLToClaimDutyFeeSchema
				.getAdjRemark()); // 调整备注
		
		mLLClaimDutyFeeSchema.setCustomerNo(tLLToClaimDutyFeeSchema.getCustomerNo());
		mLLClaimDutyFeeSchema.setCurrency(tLLToClaimDutyFeeSchema.getCurrency());
		

		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－结束－－－－－－－理赔计算－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－开始－－－－－－－计算要素准备－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */
	/**
	 * 目的：理赔计算
	 * 
	 * @param pCalSum
	 *            计算金额
	 * @param pCalCode
	 *            计算公式
	 * @param pDutyFeeStaNo 账单明细编码
	 * @return double
	 */
	private double executePay(double pCalSum, String pCalCode,String pDutyFeeStaNo) {
		double rValue;
		if (null == pCalCode || "".equals(pCalCode) || pCalCode.length() == 0) {
			return 0;
		}
		logger.debug("\n=========================================进行公式运算=====开始=========================================\n");
		
		logger.debug("ContNo(合同):"+mLLClaimDetailSchema.getContNo()+",Clmno(赔案):"+mLLClaimDetailSchema.getClmNo()
				           +"RiskCode(险种):"+mLLClaimDetailSchema.getRiskCode()+",DutyCode(责任):"+mLLClaimDetailSchema.getDutyCode()
				           +",GetDutyCode(给付责任编码):"+mLLClaimDetailSchema.getGetDutyCode()+",GetDutyKind(给付责任类型):"
				           +mLLClaimDetailSchema.getGetDutyKind()+",pCalCode(算法):"+pCalCode+",pCalSum(给付金)"+pCalSum);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 获取出险时点之前做过保全复效的批单号
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tContNo = mLLClaimDetailSchema.getContNo(); // 合同号
		String tPolNo = mLLClaimDetailSchema.getPolNo(); // 保单险种号
		String tNBPolNo = mLLClaimDetailSchema.getNBPolNo(); // 承保时的保单险种号

		LPEdorItemSchema tLPEdorItemSchema = mLLClaimPubFunBL
				.getLPEdorItemBefore(tContNo, tNBPolNo, this.mInsDate, "RE");// 出险时点前的保全批单号
		String tPosEdorNoFront = "";
		if (tLPEdorItemSchema != null) {
			tPosEdorNoFront = StrTool.cTrim(tLPEdorItemSchema.getEdorNo());
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 设置各种计算要素
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		// 增加基本要素,计算给付金
		TransferData tTransferData = new TransferData();
		
		// 出险原因
		tTransferData.setNameAndValue("AccidentReason", String.valueOf(mLLRegisterSchema.getAccidentReason()));

		// 出险日期
		tTransferData.setNameAndValue("AccidentDate", String.valueOf(this.mInsDate));

		// 终了红利
		tTransferData.setNameAndValue("FinalBonus", String.valueOf("0"));

		// [重新计算]投保人职业类别
		tTransferData.setNameAndValue("OccupationType", String.valueOf(getOccupationType()));

		// 住院天数
		tTransferData.setNameAndValue("DaysInHos", String.valueOf(getSumDayCount(mLLClaimDetailSchema)));

		// 保险结束日期
		tTransferData.setNameAndValue("EndPolDate", String.valueOf(mLCPolSchema.getEndDate()));

		// 费用开始日期
		tTransferData.setNameAndValue("StartFeeDate", String
				.valueOf(mLLClaimDutyFeeSchema.getStartDate()));

		// 费用结束日期
		tTransferData.setNameAndValue("EndFeeDate", String
				.valueOf(mLLClaimDutyFeeSchema.getEndDate()));

		// 伤残比例
		tTransferData.setNameAndValue("DefoRate", String
				.valueOf(mLLClaimDutyFeeSchema.getRealRate()));

		// 每天床位费
		tTransferData.setNameAndValue("InHospdayFee", String
				.valueOf(mLLClaimDutyFeeSchema.getAdjSum()));


		// 治疗类型（B为住院治疗；A为门诊治疗）
		tTransferData.setNameAndValue("CureType", String
				.valueOf(mLLClaimDutyFeeSchema.getDutyFeeType()));

		// [重新计算]保费（包括健康加费和职业加费及出险时点的保全补退费）,取自出险时间,需要考虑保全
		tTransferData.setNameAndValue("TotalPrem", String
				.valueOf(getTotalPrem()));

		// [重新计算]起领日期,取自出险时点,需要考虑保全
		/* 0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化 */
		if (mLLClaimDetailSchema.getPosFlag().equals("0")
				|| mLLClaimDetailSchema.getPosFlag().equals("1")) {
			tTransferData.setNameAndValue("GetStartDate", String
					.valueOf(mLLClaimPubFunBL.getGetStartDate(null,
							mLCGetSchema)));
		} else if (mLLClaimDetailSchema.getPosFlag().equals("2")) {
			tTransferData
					.setNameAndValue("GetStartDate", String
							.valueOf(mLLClaimPubFunBL.getGetStartDate(
									mLLClaimDetailSchema.getPosEdorNo(),
									mLCGetSchema)));
		}

		// 累计红利保额
		tTransferData.setNameAndValue("SumAmntBonus", String
				.valueOf(mLLClaimDetailSchema.getYearBonus()));

		// 出险时已保整月数
		tTransferData.setNameAndValue("Rgtmonths", String.valueOf(PubFun
				.calInterval(mLCPolSchema.getCValiDate(), this.mInsDate, "M")));
		
		// 给付责任编码
		tTransferData.setNameAndValue("GetDutyCode", String
				.valueOf(mLLClaimDetailSchema.getGetDutyCode()));

		// 给付责任类型
		tTransferData.setNameAndValue("GetDutyKind", String
				.valueOf(mLLClaimDetailSchema.getGetDutyKind()));

		// 保单号
		tTransferData.setNameAndValue("PolNo", String
				.valueOf(mLLClaimDetailSchema.getPolNo()));

		// 赔案号
		tTransferData.setNameAndValue("CaseNo", String.valueOf(this.mClmNo));

		// 出险人编号
		tTransferData.setNameAndValue("InsuredNo", String.valueOf(mLLCaseSchema
				.getCustomerNo()));

		// 出险时已保年期
		tTransferData.setNameAndValue("RgtYears", String.valueOf(PubFun
				.calInterval(mLCPolSchema.getCValiDate(), this.mInsDate, "Y")));

		// 出险时已保天数
		tTransferData.setNameAndValue("RgtDays", String.valueOf(PubFun
				.calInterval(mLCPolSchema.getCValiDate(), this.mInsDate, "D")+1));

		// 被保人性别
		tTransferData.setNameAndValue("Sex", String.valueOf(mLCPolSchema
				.getInsuredSex()));

		// [重新计算]累计保费(包括健康加费和职业加费),取自出险时点,需要考虑保全
		tTransferData
				.setNameAndValue("SumPrem", String.valueOf(getPrem()));
		logger.debug("计算的累计保费是" + tTransferData.getValueByName("SumPrem"));
		
		//累计增额红利  liyan-先赋值为0
		tTransferData.setNameAndValue("Additionalbonus", String.valueOf(100));
		
		//首个生存保险金给付日 当日24时之前 liyan--先赋值为Y
		tTransferData.setNameAndValue("BeFirstSurvivalBenefit", "Y");
		
		//被保险人身故之日起至满期日未给付的生存保险金期数  liyan--先赋值为0
		tTransferData.setNameAndValue("NotPayOfAnnuity", String.valueOf(1));
		
        //年满60周岁后的首个保单周年日24时之后  liyan--先赋值为0
		tTransferData.setNameAndValue("Af60YearsOfInsured", String.valueOf("N"));
		
		//年满60周岁后的首个保单周年日24时之前 yhy --先赋值为0
		tTransferData.setNameAndValue("Be60YearsOfInsured", String.valueOf("Y"));
		
		//观察期 yhy
		// 出险时已保天数
		tTransferData.setNameAndValue("ObserveDate", String.valueOf(PubFun
				.calInterval(mLCPolSchema.getCValiDate(), this.mInsDate, "D")+1));

		
		
		//已交保费减去累计已给付的基本生存保险金   liyan--先赋值为0
		tTransferData.setNameAndValue("PremiumedMinusAnnuityed", String.valueOf(10));
		
		//首个生存保险金给付日 当日24时之后  liyan--先赋值为0
		tTransferData.setNameAndValue("AfFirstSurvivalBenefit", String.valueOf(1));
		
		//现金价值    liyan--先赋值为0
		tTransferData.setNameAndValue("CashValue", String.valueOf(100000));
		
		//累计该险种保费  liyan--先赋值为0
		tTransferData.setNameAndValue("SumThisPrem", String.valueOf(1));
		
		
		// 保单生效日期
		tTransferData.setNameAndValue("CValiDate", String.valueOf(mLCPolSchema
				.getCValiDate()));

		// [重新计算]保险年期,取自出险时点,需要考虑保全
		tTransferData.setNameAndValue("Years", String.valueOf(getYears()));

		// 已交费年期,取自出险时点,需要考虑保全
		tTransferData
				.setNameAndValue("AppAge", String.valueOf(PubFun.calInterval(
						mLCPolSchema.getCValiDate(), getPaytoDate(), "Y")));

		// 被保人投保年龄
		tTransferData.setNameAndValue("InsuredAppAge", String
				.valueOf(mLLClaimPubFunBL.getInsuranceAge(mLCPolSchema)));

		// 被保人0岁保单生效对应日
		tTransferData.setNameAndValue("InsuredvalidBirth", String
				.valueOf(mLLClaimPubFunBL.getInsuredvalideBirth(mLCPolSchema)));

		// 计算给付金
		tTransferData.setNameAndValue("Je_gf", String.valueOf(pCalSum));

		// [重新计算]份数,取自出险时点,需要考虑保全
		tTransferData.setNameAndValue("Mult", String.valueOf(getMult()));

		// 交费间隔
		tTransferData.setNameAndValue("PayIntv", String.valueOf(mLCPolSchema
				.getPayIntv()));

		// 医疗费用序号
		tTransferData.setNameAndValue("DutyFeeStaNo", String
				.valueOf(mLLClaimDutyFeeSchema.getDutyFeeStaNo()));

		// 总保费[该要素将被删除],取自出险时点,需要考虑保全
		tTransferData.setNameAndValue("Prem", String.valueOf(mLCPolSchema
				.getPrem()));

		// [重新计算]保单累计支付,取自出险时点,适用于责任下多个给付责任共享险种保额
		tTransferData.setNameAndValue("PolPay", String.valueOf(getPolPay()));

		// 医疗费用编码
		tTransferData.setNameAndValue("DutyFeeCode", String
				.valueOf(mLLClaimDutyFeeSchema.getDutyFeeCode()));

		
		//yhy 总保费减去已支付年金    2016-11-11 Start
	    tTransferData.setNameAndValue("PremiumDeductedAnnuity", String.valueOf(mLCPolSchema.getPrem()));
		//end
	    
	    
	  
		
		// [重新计算]基本保额,取自出险时点,需要考虑保全,适用于多个给付责任共享保额且各个给付的保额一致时(既lcget的standmoney一致)时
		double tDAmnt = getAmnt();
		tTransferData.setNameAndValue("Amnt", String.valueOf(tDAmnt));
		
		//yhy 总保费减去已支付年金    2016-11-11 Start
	    int age1=PubFun.calInterval(mLLClaimPubFunBL.getInsuredvalideBirth(mLCPolSchema), this.mInsDate,"Y");
	    int age2=age1-1-60>0?age1-1-60:0;
	    Double AnnAmntBeforeDeath =(0.3+(age2)*0.1)*tDAmnt;
	    tTransferData.setNameAndValue("AnnAmntBeforeDeath", String.valueOf(AnnAmntBeforeDeath));
	    
	    tTransferData.setNameAndValue("YearsOfAnnitied", String.valueOf(age2));
		//end
		
		// [重新计算]基本保额,取自出险时点,需要考虑保全,适用于多个给付责任共享保额且各个给付的保额不一致时(既lcget的standmoney不一致)时
		double tMaxAmnt = getMaxAmnt();
		tTransferData.setNameAndValue("MaxAmnt", String.valueOf(tMaxAmnt));

		// [重新计算]初始基本保额,取自出险时点,需要考虑保全
		tTransferData.setNameAndValue("Oamnt", String.valueOf(getOamnt()));

		// 交费年期
		tTransferData.setNameAndValue("PayYears", String.valueOf(mLCDutySchema
				.getPayYears()));

		// 出险时年龄
		tTransferData.setNameAndValue("GetAge", String.valueOf(PubFun
				.calInterval(mLLClaimPubFunBL.getInsuredvalideBirth(mLCPolSchema), this.mInsDate,"Y")));

		// 责任代码
		tTransferData.setNameAndValue("DutyCode", String.valueOf(mLCDutySchema
				.getDutyCode()));

		// VPU
		tTransferData.setNameAndValue("VPU", String.valueOf(mLLClaimPubFunBL
				.getLMDuty(mLCDutySchema.getDutyCode()).getVPU()));

		// 费用明细的流水号
		tTransferData.setNameAndValue("DutyFeeStaNo", String
				.valueOf(mLLClaimDutyFeeSchema.getDutyFeeStaNo()));

		// 事故号
		tTransferData.setNameAndValue("CaseRelaNo", String.valueOf(mAccNo));

		// [重新计算]每期标准保费,,取自出险时点,需要考虑保全
		double tDStandPrem = getPeriodPrem();
		tTransferData.setNameAndValue("StandPrem", String.valueOf(tDStandPrem));

		// 交费期限
		tTransferData.setNameAndValue("PayEndDate", String
				.valueOf(mLCDutySchema.getPayEndDate()));

		// 交费终止期间
		tTransferData.setNameAndValue("PayEndYear", String
				.valueOf(mLCDutySchema.getPayEndYear()));

		// 客户号
		tTransferData.setNameAndValue("CustomerNo", String
				.valueOf(mLLClaimDetailSchema.getCustomerNo()));

		// [重新计算]保单是否复效的标记
		tTransferData.setNameAndValue("LRFlag", String.valueOf(mLLClaimPubFunBL
				.getLRFlag(tPosEdorNoFront, tPolNo)));

		// [重新计算]复效时间
		String tLastRevDate = mLLClaimPubFunBL.getLastRevDate(tPosEdorNoFront,
				tContNo, tPolNo, mLCPolSchema);

		// 复效到出险时已保年期,取自出险时点,需要考虑保全
		tTransferData.setNameAndValue("LRYears", String.valueOf(PubFun
				.calInterval(tLastRevDate, this.mInsDate, "Y")));

		// 复效到出险时已保天数,取自出险时点,需要考虑保全
		tTransferData.setNameAndValue("LRDays", String.valueOf(PubFun
				.calInterval(tLastRevDate, this.mInsDate, "D")+1));
		
		
		
		// 身故到满期之前的周年个数   yhy start
		tTransferData.setNameAndValue("DieAndPolicyAnnDays", String.valueOf(10));
		
		tTransferData.setNameAndValue("BD12FThePayment", String.valueOf(0));
		
		//end 
		
		
		
		/* 续保投保标志,0未续保,1已续保 */
		tTransferData.setNameAndValue("AppFlag", String
				.valueOf(mLLClaimPubFunBL.getAppFlag(tPosEdorNoFront,
						mLCPolSchema)));

		// [重新计算] 交费次数 ,取自出险时点,需要考虑保全
//		double tDPayTimes = getPayTimes();
//		tTransferData.setNameAndValue("PayTimes", String.valueOf(tDPayTimes));

		// 利差返还后增加的保额
		tTransferData.setNameAndValue("RateAmnt", String.valueOf("0"));

		// 保单号
		tTransferData.setNameAndValue("PolNo", String.valueOf(mLCPolSchema
				.getPolNo()));

		// LCGet的开始时间
		tTransferData.setNameAndValue("LCGetStartDate", String
				.valueOf(mLCGetSchema.getGetStartDate()));

		// LCGet的终止时间
		tTransferData.setNameAndValue("LCGetEndDate", String
				.valueOf(mLCGetSchema.getGetEndDate()));

		// 合同号
		tTransferData.setNameAndValue("ContNo", String.valueOf(mLCGetSchema
				.getContNo()));

		/**
		 * 2008-12-11 zhangzheng
		 * 终了红利属于英式分红,MS采取的是美式分红,所以封住这段处理逻辑,以免报错
		 */
//		// 终了红利
//		tTransferData.setNameAndValue("EndBonus", String
//				.valueOf(mLLClaimDetailSchema.getEndBonus()));
		
		//2008-09-27  zhangzheng 社保赔付比例
		if (!mLLClaimPubFunBL.getLMRiskSort(mLLClaimDetailSchema.getRiskCode(), "18")) //投保人豁免险有可能不存在其作为被保险人的保单,所以不查询赔付比例
		{
	        tTransferData.setNameAndValue("SocialInsuRate",String.
	        		valueOf(getSocialInsuRate(mLLClaimDetailSchema.getRiskCode(),
	        				mLLClaimPubFunBL.getLCInsured(tContNo, mLLClaimDetailSchema.getCustomerNo()).getSocialInsuFlag())));

		}


		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 其中投保费率差额＝投保保额对应费率－限额保额对应费率
		 * 退还保费＝已交保费×缴费次数×（冲减后金额－给付限额）/基本保额
		 * ?PayBackPrem?=?StandPrem?*?PayTimes?*(1-?Je_gf?/?Amnt?)
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
//		double tDPayBackPrem = Arith.round(tDStandPrem * tDPayTimes
//				* (mLLClaimDetailSchema.getAmnt() - pCalSum) / tDAmnt, 4);
//		tTransferData.setNameAndValue("PayBackPrem", String
//				.valueOf(tDPayBackPrem));

		// 有效保额(对于账户型的就是账户价值)
		tTransferData.setNameAndValue("ValidAmnt", String
				.valueOf(mLLClaimDetailSchema.getAmnt()));
		logger.debug("计算的有效保额是"
				+ tTransferData.getValueByName("ValidAmnt"));

		// 已部分领取的账户价值)
		tTransferData.setNameAndValue("SumAccGet", String.valueOf(String
				.valueOf(getSumAccGet())));

		// 未成年人限额
		tTransferData.setNameAndValue("PupilAmnt", String.valueOf(mPupilAmnt));
		
		// 得到每个给付责任的累计赔付金额,适用于除费用补偿型险种之外的所有险种
		tTransferData.setNameAndValue("GetDutySumPay", String.valueOf(getGetDutySumPay()));
		
		// 得到每个给付责任的累计赔付金额,适用于费用补偿型险种
		tTransferData.setNameAndValue("CompensateDutySumPay", String.valueOf(getCompensateDutySumPay()));
		
		// 共享保额的给付责任已经算出的理赔金,理算时给付责任的相互冲减,适用于责任下多个给付责任共享险种保额
		tTransferData.setNameAndValue("CurrentDutyPay", String.valueOf(getCurrentDutyPay()));
		
		// 共享保额的给付责任已经算出的理赔金,理算时给付责任的相互冲减,适用于责任下多个给付责任分類共享不同的保额
		tTransferData.setNameAndValue("CurrentClassifiDutyPay", String.valueOf(getCurrentClassifiDutyPay()));
		
		// 给付责任的赔付次数
		tTransferData.setNameAndValue("ClaimCount", String.valueOf(getGetDutyClaimCount()));
		
		//账单费用项目编码
		tTransferData.setNameAndValue("DutyFeeCode", getDutyItemCode(mLLClaimDetailSchema.getClmNo(),pDutyFeeStaNo));
		
		//账单结束日期
		tTransferData.setNameAndValue("FeereceiEndDate", getFeereceiEndDate(mLLClaimDetailSchema.getClmNo(),pDutyFeeStaNo));
		

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.0
		 * 针对于266,267,附加险采用主险的计算要素 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tRiskCode = StrTool.cTrim(mLCPolSchema.getRiskCode());
		String tSubRiskFlag = StrTool.cTrim(mLLClaimDetailSchema.getRiskType());
		String tEffectOnMainRisk = StrTool.cTrim(mLLClaimDetailSchema
				.getEffectOnMainRisk());
		if (tSubRiskFlag.equals("S") && (tEffectOnMainRisk.equals("01")||tEffectOnMainRisk.equals("02"))) {
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.1 取出附加险所在的主险赔付信息
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			LLClaimDetailSchema tRMLLClaimDetailSchema = getRMRiskInfo();
			if (tRMLLClaimDetailSchema != null) {
				String tRMContNo = tRMLLClaimDetailSchema.getContNo();// 附加险所在主险的合同号
				String tRMPolNo = tRMLLClaimDetailSchema.getPolNo(); // 附加险所在主险的保单号

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.2
				 * 获取出险时点之前做过保全复效的批单号 保全信息
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				String tRMPosEdorNoFront = "";
				LPEdorItemSchema tRMLPEdorItemSchema = mLLClaimPubFunBL
						.getLPEdorItemBefore(tRMContNo, tRMPolNo,
								this.mInsDate, "RE");// 出险时点前的保全批单号
				if (tRMLPEdorItemSchema != null) {
					tRMPosEdorNoFront = StrTool.cTrim(tRMLPEdorItemSchema
							.getEdorNo());
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.3 添加计算要素
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */

				// 附险险所在主险的理赔给付金,为产品所加
				tTransferData.setNameAndValue("RMAmnt", String
						.valueOf(tRMLLClaimDetailSchema.getRealPay()));

				// 附加险所在主险的有效保额,为产品所加,基本保额 + 累计年度红利保额（包括非整年度部分）＋终了红利
				// 出险时点，附加该附加险的主险的有效保额（基本保额＋累计年度红利保额（包括非整年度部分）＋终了红利）
				// （需考虑减保和其他保全变更的因素对保额以及红利保额的影响）（以上所提红利相关内容适用于主险为分红险的情况）

				double t_Sum_Amnt = 0;
				double t_Sum_YearBonus = 0;
				double t_Sum_FinalBonus = 0;

				t_Sum_Amnt = tRMLLClaimDetailSchema.getAmnt();
				t_Sum_YearBonus = tRMLLClaimDetailSchema.getYearBonus();
				t_Sum_FinalBonus = tRMLLClaimDetailSchema.getEndBonus();
				t_Sum_Amnt = t_Sum_Amnt + t_Sum_YearBonus;

				t_Sum_Amnt = Arith.round(t_Sum_Amnt, 2);

				tTransferData.setNameAndValue("TRMAmnt", String
						.valueOf(t_Sum_Amnt));

				// 出险时点，主险是否复效过的标志，('0'没有, '1'有)
				tTransferData.setNameAndValue("MLRFlag", String
						.valueOf(mLLClaimPubFunBL.getLRFlag(tRMPosEdorNoFront,
								tRMPolNo)));

				// 复效时间
				String tRMLastRevDate = mLLClaimPubFunBL.getLastRevDate(
						tRMPosEdorNoFront, tRMContNo, tPolNo, mLCPolSchema);

				// 复效到出险时已保天数,取自出险时点,需要考虑保全
				tTransferData.setNameAndValue("MLRDays", String.valueOf(PubFun
						.calInterval(tRMLastRevDate, this.mInsDate, "D")+1));

				// 复效到出险时已保年期,取自出险时点,需要考虑保全
				tTransferData.setNameAndValue("MLRYears", String.valueOf(PubFun
						.calInterval(tRMLastRevDate, this.mInsDate, "Y")));

				// 主险的保单号
				tTransferData.setNameAndValue("MainPolNo", String
						.valueOf(tRMPolNo));

			}
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.6
			 * 附加险没有主险的赔付信息，则取出本身所在的险种信息
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			else {
				String tMainPolNo = mLCPolSchema.getMainPolNo(); // 得到主险的保单险种号
				LCPolSchema tRMLCPolSchema = mLLClaimPubFunBL
						.getLCPol(tMainPolNo);
				if (tRMLCPolSchema == null) {
					this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
					return 0;
				} else {
					String tRMContNo = tRMLCPolSchema.getContNo();
					String tRMPolNo = tRMLCPolSchema.getPolNo();

					/* 获取出险时点之前做过保全复效的批单号 */
					String tRMPosEdorNoFront = "";
					LPEdorItemSchema tRMLPEdorItemSchema = mLLClaimPubFunBL
							.getLPEdorItemBefore(tRMContNo, tRMPolNo,
									this.mInsDate, "RE");// 出险时点前的保全批单号
					if (tRMLPEdorItemSchema != null) {
						tRMPosEdorNoFront = StrTool.cTrim(tRMLPEdorItemSchema
								.getEdorNo());
					}

					// 附险险的理赔给付金,为产品所加
					tTransferData
							.setNameAndValue("RMAmnt", String.valueOf("0"));

					// 附险险的有效保额,为产品所加,基本保额
					tTransferData.setNameAndValue("TRMAmnt", String
							.valueOf(tRMLCPolSchema.getAmnt()));

					// 附险险的理赔给付金,为产品所加
					tTransferData.setNameAndValue("MLRFlag", String
							.valueOf(mLLClaimPubFunBL.getLRFlag(
									tRMPosEdorNoFront, tRMPolNo)));

					// 复效时间
					String tRMLastRevDate = mLLClaimPubFunBL.getLastRevDate(
							tRMPosEdorNoFront, tRMContNo, tPolNo, mLCPolSchema);

					// 复效到出险时已保年期,取自出险时点,需要考虑保全
					tTransferData.setNameAndValue("MLRDays", String
							.valueOf(PubFun.calInterval(tRMLastRevDate,
									this.mInsDate, "D")+1));

					// 复效到出险时已保天数,取自出险时点,需要考虑保全
					tTransferData.setNameAndValue("MLRYears", String
							.valueOf(PubFun.calInterval(tRMLastRevDate,
									this.mInsDate, "Y")));

					// 主险的保单号
					tTransferData.setNameAndValue("MainPolNo", String
							.valueOf(tRMLCPolSchema.getPolNo()));
				}

			}

		}
		
		//如果是投连险
		String sql = "select (case when count(*) is null then 0 else count(*) end) from lmrisktoacc c, lmriskinsuacc r "
		  + " where r.insuaccno = c.insuaccno and r.acckind = '2' "
			+ " and c.riskcode = '" + "?riskcode?" +	"' ";
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql(sql);
		sqlbv10.put("riskcode", mLCPolSchema.getRiskCode());
		ExeSQL tExeSQL = new ExeSQL();
	    int tCount=Integer.parseInt(tExeSQL.getOneValue(sqlbv10));
	    if(tCount>0)
	    {
	    	//是投连险
	    	double tSumAccBala=0;
	    	//判断是否进行了结算
	    	sql = "select (case when count(*) is null then 0 else count(*) end) from LOPolAfterDeal where busytype='CL' and AccAlterNo='"+"?AccAlterNo?"
				+"' and AccAlterType='4' order by state desc";
	    	SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql(sql);
			sqlbv11.put("AccAlterNo", mLLClaimDetailSchema.getClmNo());
	    	int tCount1=Integer.parseInt(tExeSQL.getOneValue(sqlbv11));
	    	if(tCount1>0)
		    {
	    		sql="select currency,sum(money) from lcinsureacctrace where accalterno='"+"?accalterno?"+"'"
	    			+" and AccAlterType='4' and polno = '" + "?polno?" +"' and payplancode in ( select payplancode from LMDutyPayRela where dutycode ='"+"?dutycode?"+"') group by currency";
	    		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
				sqlbv12.sql(sql);
				sqlbv12.put("accalterno", mLLClaimDetailSchema.getClmNo());
				sqlbv12.put("polno", mLLClaimDetailSchema.getPolNo());
				sqlbv12.put("dutycode", mLLClaimDetailSchema.getDutyCode());
	    		SSRS tSSRS = new SSRS();
				tSSRS = tExeSQL.execSQL(sqlbv12);
				if (tSSRS.getMaxRow() > 0) {
					LDExch tLDExch = new LDExch();
					for(int i=1;i<=tSSRS.getMaxRow();i++)
					{
						if(tSSRS.GetText(i, 1).equals(mLLClaimDetailSchema.getCurrency()))
							tSumAccBala = tSumAccBala+ Double.parseDouble(tSSRS.GetText(i, 2))*(-1);	
						else
							tSumAccBala = tSumAccBala+ tLDExch.toOtherCur(tSSRS.GetText(i, 1), mLLClaimDetailSchema.getCurrency(), PubFun.getCurrentDate(), Double.parseDouble(tSSRS.GetText(i, 2)))*(-1);	
					}								
				}
		    }
	    	else
	    	{
	    		//如果还没有计价则取估算账户价值
	    		DealInsuAccPrice tDealInsuAccPrice= new DealInsuAccPrice(mGlobalInput);
				tSumAccBala=tDealInsuAccPrice.calOnePrice(mLLClaimDetailSchema.getPolNo(), mLLClaimDetailSchema.getDutyCode(), PubFun.getCurrentDate(),mLLClaimDetailSchema.getCurrency());
	    	}	 
	    	logger.debug("账户价值: "+tSumAccBala);
			
	    	// 账户价值
			tTransferData.setNameAndValue("InsureAccBalance", String
					.valueOf(tSumAccBala));
	    }

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0
		 * 将所有设置的参数加入到PubCalculator.addBasicFactor()中
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		Vector tVector = tTransferData.getValueNames();
		PubCalculator tPubCalculator = new PubCalculator();

		for (int i = 0; i < tVector.size(); i++) {
			String tName = (String) tVector.get(i);
			String tValue = (String) tTransferData.getValueByName(tName);
			// logger.debug("PubCalculator.addBasicFactor--tName:" + tName
			// + " tValue:" + tValue);
			tPubCalculator.addBasicFactor(tName, tValue);
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0
		 * 将所有设置的参数加入到Calculator.addBasicFactor()中
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(pCalCode);

		tVector = tTransferData.getValueNames();
		logger.debug("======================================================================================================");
		for (int i = 0; i < tVector.size(); i++) {
			String tName = (String) tVector.get(i);
			String tValue = (String) tTransferData.getValueByName(tName);
			logger.debug("第[" + i + "]个理算要素名称--" + tName + "--[" + tValue
					+ "]");
			tCalculator.addBasicFactor(tName, tValue);
		}
		logger.debug("======================================================================================================");
		mBomList=mPrepareBOMClaimBL.dealData(tTransferData);
		tCalculator.setBOMList(mBomList);
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0
		 * 进行计算，Calculator.calculate()
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tStr = "";
		
		logger.debug("----------------------------------------------------------------------------------\n");
		//logger.debug("计算公式==[" + tCalculator.getCalSQL() + "]");
		logger.debug("\n----------------------------------------------------------------------------------");
		tStr = tCalculator.calculate();
		if (tStr.trim().equals("")) {
			rValue = 0;
		} else {
			rValue = Arith.round(Double.parseDouble(tStr), 2);
		}

		if (tCalculator.mErrors.needDealError()) {
			this.mErrors.addOneError("计算发生错误!原公式:" + pCalCode + "||,解析后的公式:"
					+ tCalculator.getCalSQL());
		}
		logger.debug("\n=========================================进行公式运算=====结束=========================================\n");
		return rValue;
	}
	

    /**
     * 取多张账单累计住院日期
     * add by zhangzheng at 2009-02-25
     * @return int
     */
    public int getSumDayCount(LLClaimDetailSchema qLLClaimDetailSchema){
    	
        int tDays=0;
        
        //首先按照起始日期排序,保证所有日期是按起始递增排列,方便后边比较
        String sql="select * from LLToClaimDutyFee where clmno='"+"?clmno?"
                   +"' and polno='"+"?polno?"+"'"
                   +" and dutycode='"+"?dutycode?"+"' and getdutytype in ('100','200') "
                   +" and getdutycode='"+"?getdutycode?"+"' "//+partSql
                   +" order by startdate,enddate";
        SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
        sqlbv13.sql(sql);
        sqlbv13.put("clmno", qLLClaimDetailSchema.getClmNo());
        sqlbv13.put("polno", qLLClaimDetailSchema.getPolNo());
        sqlbv13.put("dutycode", qLLClaimDetailSchema.getDutyCode());
        sqlbv13.put("getdutycode", qLLClaimDetailSchema.getGetDutyCode());
		logger.debug("--计算赔案" + qLLClaimDetailSchema.getCaseNo() + ",险种:"+qLLClaimDetailSchema.getRiskCode()+
						   ",责任:"+qLLClaimDetailSchema.getDutyCode()+",给付类型:"+qLLClaimDetailSchema.getGetDutyKind()+
						   ",给付责任编码"+qLLClaimDetailSchema.getGetDutyCode()+"下的账单累计实际住院天数的sql:"+ sql);
		
        LLToClaimDutyFeeDB tLLToClaimDutyFeeDB= new LLToClaimDutyFeeDB();

        LLToClaimDutyFeeSet tLLToClaimDutyFeeSet = tLLToClaimDutyFeeDB.executeQuery(sqlbv13);//取参与理算的账单信息
        if(tLLToClaimDutyFeeSet.size()==0){
            return 0;
        }
        
        //取得第一条记录的住院起始日期和结束日期作为标准日期
        String startdate=tLLToClaimDutyFeeSet.get(1).getStartDate();
        String enddate=tLLToClaimDutyFeeSet.get(1).getEndDate();
        
        logger.debug("第1条记录的起始日期:"+startdate+",截止日期是:"+enddate);
        
        if(tLLToClaimDutyFeeSet.size()==1){
        	
        	//如果只有一行则直接取天数
            startdate=tLLToClaimDutyFeeSet.get(1).getStartDate();
            enddate=tLLToClaimDutyFeeSet.get(1).getEndDate();
            tDays =  PubFun.calInterval(startdate,enddate, "D");
        }
        else
        {
            for (int i = 1; i <= tLLToClaimDutyFeeSet.size(); i++) 
            {
                logger.debug("本次循环的是第"+i+"条记录!");

                if(i==1)
                {
                    continue;
                }
                
                String laterDate = PubFun.getLaterDate(enddate,tLLToClaimDutyFeeSet.get(i).getEndDate());
                logger.debug("待计算的结束日期("+enddate+")和本次循环的结束日期("+
                		tLLToClaimDutyFeeSet.get(i).getEndDate()+")中大者是:"+laterDate);
  
                //如果两个日期相等,由于循环的次序是按起始日期排序的,所以如果两个中的大者依然是定的标准日期,则表示本次循环的日期是包含在标准日期范围内的
                if (laterDate.equals(enddate)) {
                	
                	//本次循环日期包含在标准日期内,被过滤掉!
                }
                else
                {
                    laterDate = PubFun.getLaterDate(enddate,tLLToClaimDutyFeeSet.get(i).getStartDate());
                    logger.debug("待比较的结束日期("+enddate+")和本次循环的起始日期("+
                    		tLLToClaimDutyFeeSet.get(i).getStartDate()+")中大者是:"+laterDate);
                    
                    //判断这个账单的起始时间是否在上一个的终止日之前
                    if (laterDate.equals(enddate)) 
                    {
                    	//进到这里,表示本次循环的结束日期比标准结束日期要大,所以扩展标准日期的范围,将本次循环的终止日期作为新的标准结束日期
                        enddate = tLLToClaimDutyFeeSet.get(i).getEndDate();//将后一个终止日期置为新的终止日期
                    } 
                    else 
                    {
                    	//进到这里,表示本次循环的起始日期和标准终止日期是有间隔的,可以理解是两段;
                    	
                        logger.debug("tDay1:"+tDays);
                        //先统计标准日期段的间隔天数
                        tDays = tDays+PubFun.calInterval(startdate,enddate, "D");
                        logger.debug("tDay2:"+tDays);
                        
                        //设置开始新的标准日期段
                        startdate = tLLToClaimDutyFeeSet.get(i).getStartDate();
                        enddate = tLLToClaimDutyFeeSet.get(i).getEndDate();
                        logger.debug("更新后的起始日期:"+startdate+",截止日期是:"+enddate);

                    }
                }

                
                //如果是最后一轮循环，取最后一个时间段的天数进行累计
                if(i==tLLToClaimDutyFeeSet.size())
                {
                    tDays = tDays+(PubFun.calInterval(startdate, enddate, "D"));
                }
            }
        }
        
    	if (tDays == 0) {
			tDays = 1;
		}
        
        logger.debug("CalDaysInHos ====================================="+tDays);
        
        return tDays;
    }

	

	/**
	 * zhangzheng 2008-09-27
	 * 得到社保赔付比例
	 * @return
	 */
    private double getSocialInsuRate(String tRiskCode,String tSocialInsuFlag)
    {
      LMRiskSocialInsuDB tLMRiskSocialInsuDB=new LMRiskSocialInsuDB();
      tLMRiskSocialInsuDB.setRiskCode(tRiskCode);
      
      if(tSocialInsuFlag==null||tSocialInsuFlag.equals("")||tSocialInsuFlag.equals("0"))
      {
    	  tLMRiskSocialInsuDB.setSocialInsuFlag("0");
      }
      else
      {
    	  tLMRiskSocialInsuDB.setSocialInsuFlag(tSocialInsuFlag);
      }
      
      if(tLMRiskSocialInsuDB.getInfo())
      {
        return tLMRiskSocialInsuDB.getSocialInsuRate();
      }
      return 1.00;
    }

	/**
	 * 得到已经领取的账户价值
	 * 
	 * @return
	 */
	private double getSumAccGet() {

		double t_SumAccGet = 0.0;
		logger.debug("----------------------------开始计算已领取的账户价值---------------------------------------------------------------");

		logger.debug("保单" + mLCPolSchema.getPolNo() + "的生效日期是"
				+ mLCPolSchema.getCValiDate());
		String sql = "select (case when sum(money) is null then 0 else -sum(money) end) from lcinsureacctrace where riskcode='"
				+ "?riskcode?"
				+ "'"
				+ " and polno='"
				+ "?polno?"
				+ "'"
				+ " and moneytype in ('LQ','LF')"
				+ " and paydate>='"
				+ "?date1?"
				+ "'"
				+ " and paydate<='"
				+ "?dat2e?" + "'";
		logger.debug("计算保单" + mLCPolSchema.getPolNo() + "的已领取的账户价值的sql="
				+ sql);
		SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
		sqlbv14.sql(sql);
		sqlbv14.put("riskcode", mLCPolSchema.getRiskCode());
		sqlbv14.put("polno", mLCPolSchema.getPolNo());
		sqlbv14.put("date1", mLCPolSchema.getCValiDate());
		sqlbv14.put("date2", PubFun.getCurrentDate());
		ExeSQL exesql = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = exesql.execSQL(sqlbv14);
		if (tSSRS.getMaxRow() > 0) {
			t_SumAccGet = Double.parseDouble(tSSRS.GetText(1, 1));
		}

		logger.debug("保单" + mLCPolSchema.getPolNo() + "的领取的账户价值是"
				+ t_SumAccGet);

		logger.debug("----------------------------计算总保费结束（包括健康加费和职业加费及出险时点的保全补退费）---------------------------------------------------------------");
		return t_SumAccGet;
	}

	/**
	 * 得到基本保费:每期保费 * 期数 pPayPlanType : 0--全部保费,基本保费 + 健康职业加费 1--基本保费
	 * 2--01,02,03,04,健康职业加费
	 * 
	 * 3--01,03健康加费 4--02,04职业加费
	 * 
	 * @return
	 */
	private double getBasePrem(String pPayPlanType) {

		double t_Sum_Return = 0;
		String tSql = "";
		String tWhereSql = "";

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 缴费状态 pPayPlanType :
		 * 0--全部保费,基本保费 + 健康职业加费 1--基本保费 2--01,02,03,04,健康职业加费
		 * 3--01,03健康加费 4--02,04职业加费
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		logger.debug("-------------------计算基本保费开始（每期保费 * 期数）-------------------");

		if (pPayPlanType.equals("0")) {
			tWhereSql = "";
		} else if (pPayPlanType.equals("1")) {
			tWhereSql = " and PayPlanType in ('0')";
		} else if (pPayPlanType.equals("2")) {
			tWhereSql = " and PayPlanType in ('01','02','03','04')";
		} else if (pPayPlanType.equals("3")) {
			tWhereSql = " and PayPlanType in ('01','03')";
		} else if (pPayPlanType.equals("4")) {
			tWhereSql = " and PayPlanType in ('02','04')";
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 得到P表的总保费
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LPPremSet tLPPremSet = new LPPremSet();
		String tPosFlag = StrTool.cTrim(mLLClaimDetailSchema.getPosFlag());
		if (!tPosFlag.equals("0")) {
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.1 查询LPPrem表的数据
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tPosEdorNo = mLLClaimDetailSchema.getPosEdorNo();

			tSql = "select * from LPPrem where 1=1 " + " and EdorNo = '"
					+ "?EdorNo?" + "'"
					+ " and ContNo = '" + "?ContNo?"
					+ "'" + " and PolNo = '" + "?PolNo?"
					+ "'";
			tSql = tSql + tWhereSql;
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			sqlbv15.sql(tSql);
			sqlbv15.put("EdorNo", mLLClaimDetailSchema.getPosEdorNo());
			sqlbv15.put("ContNo", mLLClaimDetailSchema.getContNo());
			sqlbv15.put("PolNo", mLLClaimDetailSchema.getPolNo());
			LPPremDB tLPPremDB = new LPPremDB();
			tLPPremSet = tLPPremDB.executeQuery(sqlbv15);

			if (tLPPremDB.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tLPPremDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "LLClaimCalAutoBL";
				tError.functionName = "getTotalPrem";
				tError.errorMessage = "计算P表基本保费[" + pPayPlanType + "]发生错误!";
				this.mErrors.addOneError(tError);
				return 0;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.2
			 * 循环LPPrem表的数据，计算每期保费
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			for (int i = 1; i <= tLPPremSet.size(); i++) {
				LPPremSchema tLPPremSchema = tLPPremSet.get(i);

				String tPayIntv = String.valueOf(tLPPremSchema.getPayIntv());
				double tDPeriodTimes = mLLClaimPubFunBL.getLCPremPeriodTimes(
						tLPPremSchema.getPayStartDate(), tLPPremSchema
								.getPayEndDate(), tPayIntv, mInsDate);

				double t_Sum_Prem = tLPPremSchema.getPrem(); // 实际保费
				t_Sum_Prem = t_Sum_Prem * tDPeriodTimes;
				t_Sum_Return = t_Sum_Return + t_Sum_Prem;
			}
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 如果P表无数据,则得到C表的总保费
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (tLPPremSet.size() > 0) {
			logger.debug("P表基本保费:[" + t_Sum_Return + "]:" + tSql);
			return t_Sum_Return;
		} else {

			tSql = "select * from LCPrem where 1=1 " + " and ContNo = '"
					+ "?ContNo?" + "'" + " and PolNo = '"
					+ "?PolNo?" + "'";
			tSql = tSql + tWhereSql;
			SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			sqlbv16.sql(tSql);
			sqlbv16.put("ContNo", mLLClaimDetailSchema.getContNo());
			sqlbv16.put("PolNo", mLLClaimDetailSchema.getPolNo());
			LCPremDB tLCPremDB = new LCPremDB();
			LCPremSet tLCPremSet = tLCPremDB.executeQuery(sqlbv16);

			if (tLCPremDB.mErrors.needDealError()) {
				CError.buildErr(this, "计算C表基本保费[" + pPayPlanType + "]发生错误,"+tLCPremDB.mErrors.getLastError());
				return 0;
			}

			for (int i = 1; i <= tLCPremSet.size(); i++) {
				LCPremSchema tLCPremSchema = tLCPremSet.get(i);

				String tPayIntv = String.valueOf(tLCPremSchema.getPayIntv());
				double tDPeriodTimes = mLLClaimPubFunBL.getLCPremPeriodTimes(
						tLCPremSchema.getPayStartDate(), tLCPremSchema
								.getPayEndDate(), tPayIntv, mInsDate);

				double t_Sum_Prem = tLCPremSchema.getPrem(); // 实际保费
				t_Sum_Prem = t_Sum_Prem * tDPeriodTimes;
				t_Sum_Return = t_Sum_Return + t_Sum_Prem;
			}
		}
		logger.debug("C表基本保费:[" + t_Sum_Return + "]:" + tSql);
		logger.debug("-------------------计算基本保费结束（每期保费 * 期数）-------------------");
		return t_Sum_Return;
	}

	/**
	 * 得到总保费（包括健康加费和职业加费及出险时点的保全补退费）
	 * 
	 * @return
	 */
	private double getTotalPrem() {

		double t_Sum_Return = 0;

		logger.debug("----------------------------计算总保费开始（包括健康加费和职业加费及出险时点的保全补退费）---------------------------------------------------------------");

		t_Sum_Return = getBasePrem("0");

		logger.debug("要素TotalPrem:[" + t_Sum_Return + "]");
		logger.debug("----------------------------计算总保费结束（包括健康加费和职业加费及出险时点的保全补退费）---------------------------------------------------------------");
		return t_Sum_Return;
	}

	/**
	 * 得到标准保费（不包括健康加费和职业加费）
	 * 
	 * @return
	 */
	private double getPrem() {

		double t_Sum_Return = 0;

		logger.debug("----------------------------计算总保费开始（包括健康加费和职业加费及出险时点的保全补退费）---------------------------------------------------------------");

		t_Sum_Return = getBasePrem("0");

		logger.debug("要素SumPrem[标准保费]:[" + t_Sum_Return + "]");
		logger.debug("----------------------------计算总保费结束（包括健康加费和职业加费及出险时点的保全补退费）---------------------------------------------------------------");
		return t_Sum_Return;

	}

	/**
	 * 得到基本保额,取自出险时点,需要考虑保全,适用于多个给付责任共享保额且各个给付的保额一致时(既lcget的standmoney一致)时
	 * 
	 * @return
	 */
	private double getAmnt() {
		String tReturn = "";
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));

		String tPolNo = StrTool.cTrim(mLLClaimDetailSchema.getPolNo());
		String tNBPolNo = StrTool.cTrim(mLLClaimDetailSchema.getNBPolNo());
		String tPosFlag = StrTool.cTrim(mLLClaimDetailSchema.getPosFlag());

		/* 0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化 */
		if (tPosFlag.equals("0") || tPosFlag.equals("1")) {
			t_Sum_Return = mLCGetSchema.getStandMoney();
		} else if (mLLClaimDetailSchema.getPosFlag().equals("2")) {
			ExeSQL tExeSQL = new ExeSQL();
			String tSql = "";
			tSql = "select (case when StandMoney is null then 0 else StandMoney end) from LPGet where 1=1 "
					+ " and EdorNo ='" + "?EdorNo?"
					+ "'" + " and PolNo ='" + "?PolNo?" + "'"
					+ " and DutyCode ='" + "?DutyCode?" + "'"
					+ " and GetDutyCode ='" + "?GetDutyCode?"
					+ "'";
			SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
			sqlbv17.sql(tSql);
			sqlbv17.put("EdorNo", mLLClaimDetailSchema.getPosEdorNo());
			sqlbv17.put("PolNo", mLCGetSchema.getPolNo());
			sqlbv17.put("DutyCode", mLCGetSchema.getDutyCode());
			sqlbv17.put("GetDutyCode", mLCGetSchema.getGetDutyCode());
			logger.debug("要素Amnt[基本保额]:" + tSql);
			tReturn = StrTool.cTrim(tExeSQL.getOneValue(sqlbv17));
			if (tExeSQL.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tExeSQL.mErrors);
				CError tError = new CError();
				tError.moduleName = "LLClaimCalAutoBL";
				tError.functionName = "getOamnt";
				tError.errorMessage = "得到保全基本保额发生错误!";
				this.mErrors.addOneError(tError);
			}
			if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
				t_Sum_Return = Arith.round(Double.parseDouble(tReturn), 2);
			}

		}

		return t_Sum_Return;

	}
	
	
    /**
	 * 得到基本保额,取自出险时点,需要考虑保全,适用于多个给付责任共享保额且各个给付的保额一致时(既lcget的standmoney一致)时
	 * 
	 * @return
	 */
	private double getMaxAmnt() {
		String tReturn = "";
		
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));
		
		
		LMRiskSortDB  tLMRiskSortDB=new LMRiskSortDB();
		tLMRiskSortDB.setRiskCode(mLLClaimDetailSchema.getRiskCode());
		tLMRiskSortDB.setRiskSortType("9");//责任下多个给付责任分类共享保额相互冲减
		tLMRiskSortDB.setRiskSortValue(mLLClaimDetailSchema.getGetDutyCode());
		if(tLMRiskSortDB.getInfo())
        {
			String tPolNo = StrTool.cTrim(mLLClaimDetailSchema.getPolNo());
			String tNBPolNo = StrTool.cTrim(mLLClaimDetailSchema.getNBPolNo());
			String tPosFlag = StrTool.cTrim(mLLClaimDetailSchema.getPosFlag());
			
			String tSql = "";
			ExeSQL tExeSQL = new ExeSQL();

			/* 0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化 */
			if (tPosFlag.equals("0") || tPosFlag.equals("1")) {
				t_Sum_Return = mLCGetSchema.getStandMoney();
				
				tSql =  "  select max(StandMoney)  from Lcget b where 1=1 "
					+ " and b.PolNo ='" + "?tPolNo?" + "'"
					+ " and b.GetDutyCode in (select  risksortvalue from LMRiskSort a where RiskCode = '"+"?RiskCode?"+"' " 
					+ " and a.risksorttype ='"+"?risksorttype?"+"')"
					+ " and b.DutyCode ='" + "?DutyCode?" + "'";
				
			} 
			else if (mLLClaimDetailSchema.getPosFlag().equals("2")) {

				tSql = "select max(StandMoney) from LPGet where 1=1 "
						+ " and EdorNo ='" + "?EdorNo?"
						+ "'" + " and PolNo ='" + "?PolNo?" + "'"
						+ " and DutyCode ='" + "?DutyCode?" + "'"
						+ " and GetDutyCode in (select  risksortvalue from LMRiskSort a where RiskCode = '"+"?RiskCode?"+"'" + " and a.risksorttype ='"+"?risksorttype?"+"')";
			}
			SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
			sqlbv18.sql(tSql);
			sqlbv18.put("tPolNo", tPolNo);
			sqlbv18.put("RiskCode", tLMRiskSortDB.getRiskCode());
			sqlbv18.put("risksorttype", tLMRiskSortDB.getRiskSortType());
			sqlbv18.put("DutyCode", mLCGetSchema.getDutyCode());
			sqlbv18.put("EdorNo", mLLClaimDetailSchema.getPosEdorNo());
			sqlbv18.put("PolNo", mLCGetSchema.getPolNo());
			logger.debug("要素MaxAmnt[基本保额]:" + tSql);
			tReturn = StrTool.cTrim(tExeSQL.getOneValue(sqlbv18));
			
			if (tExeSQL.mErrors.needDealError()) {
				CError.buildErr(this, "得到险种"+tPolNo+"的共享给付责任的最大保额发生错误!");
			}
			if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
				t_Sum_Return = Arith.round(Double.parseDouble(tReturn), 2);
			}
		}
		else
		{
			tLMRiskSortDB.setRiskCode(mLLClaimDetailSchema.getRiskCode());
			tLMRiskSortDB.setRiskSortType("10");//责任下多个给付责任分类共享保额相互冲减
			tLMRiskSortDB.setRiskSortValue(mLLClaimDetailSchema.getGetDutyCode());
			
			if(tLMRiskSortDB.getInfo())
	        {
				String tPolNo = StrTool.cTrim(mLLClaimDetailSchema.getPolNo());
				String tNBPolNo = StrTool.cTrim(mLLClaimDetailSchema.getNBPolNo());
				String tPosFlag = StrTool.cTrim(mLLClaimDetailSchema.getPosFlag());
				
				String tSql = "";
				ExeSQL tExeSQL = new ExeSQL();

				/* 0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化 */
				if (tPosFlag.equals("0") || tPosFlag.equals("1")) {
					t_Sum_Return = mLCGetSchema.getStandMoney();
					
					tSql =  "  select max(StandMoney)  from Lcget b where 1=1 "
						+ " and b.PolNo ='" + "?tPolNo?" + "'"
						+ " and b.GetDutyCode in (select  risksortvalue from LMRiskSort a where RiskCode = '"+"?RiskCode?"+"' " 
						+ " and a.risksorttype ='"+"?risksorttype?"+"' and remark='"+"?remark?"+"')"
						+ " and b.DutyCode ='" + "?DutyCode?" + "'";
				
				} 
				else if (mLLClaimDetailSchema.getPosFlag().equals("2")) {

					tSql = "select max(StandMoney) from LPGet where 1=1 "
							+ " and EdorNo ='" + "?EdorNo?"
							+ "'" + " and PolNo ='" + "?PolNo?" + "'"
							+ " and DutyCode ='" + "?DutyCode?" + "'"
							+ " and GetDutyCode in (select  risksortvalue from LMRiskSort a where RiskCode = '"+"?RiskCode?"+"'" 
							+ " and a.risksorttype ='"+"?risksorttype?"+"' and remark='"+"?remark?"+"')";
				}
				SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
				sqlbv19.sql(tSql);
				sqlbv19.put("tPolNo", tPolNo);
				sqlbv19.put("remark", tLMRiskSortDB.getRemark());
				sqlbv19.put("RiskCode", tLMRiskSortDB.getRiskCode());
				sqlbv19.put("risksorttype", tLMRiskSortDB.getRiskSortType());
				sqlbv19.put("DutyCode", mLCGetSchema.getDutyCode());
				sqlbv19.put("EdorNo", mLLClaimDetailSchema.getPosEdorNo());
				sqlbv19.put("PolNo", mLCGetSchema.getPolNo());
				logger.debug("要素MaxAmnt[基本保额]:" + tSql);
				tReturn = StrTool.cTrim(tExeSQL.getOneValue(sqlbv19));
				
				if (tExeSQL.mErrors.needDealError()) {
					CError.buildErr(this, "得到险种"+tPolNo+"的共享给付责任的最大保额发生错误!");
				}
				if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
					t_Sum_Return = Arith.round(Double.parseDouble(tReturn), 2);
				}
	        }		
		}
		
		return t_Sum_Return;

	}

	/**
	 * 得到保险年期,取自出险时点,需要考虑保全
	 * 
	 * @return
	 */
	private int getYears() {

		String tReturn = "";
		int tIReturn = 0;
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));

		String tPolNo = StrTool.cTrim(mLLClaimDetailSchema.getPolNo());
		String tNBPolNo = StrTool.cTrim(mLLClaimDetailSchema.getNBPolNo());
		String tPosFlag = StrTool.cTrim(mLLClaimDetailSchema.getPosFlag());

		/* 0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化 */
		if (tPosFlag.equals("0") || tPosFlag.equals("1")) {
			tIReturn = mLCDutySchema.getYears();
		} else if (tPosFlag.equals("2")) {
			String tSql = "select (case when Years is null then 0 else Years end) from LPDuty where 1=1 "
					+ " and EdorNo ='" + "?EdorNo?"
					+ "'" + " and PolNo  ='" + "?PolNo?" + "'"
					+ " and DutyCode ='" + "?DutyCode?" + "'";
			SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
			sqlbv20.sql(tSql);
			sqlbv20.put("EdorNo", mLLClaimDetailSchema.getPosEdorNo());
			sqlbv20.put("PolNo", tNBPolNo);
			sqlbv20.put("DutyCode", mLCDutySchema.getDutyCode());
			logger.debug("要素Years[保险年期]:" + tSql);
			ExeSQL tExeSQL = new ExeSQL();
			tReturn = StrTool.cTrim(tExeSQL.getOneValue(sqlbv20));
			if (tExeSQL.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tExeSQL.mErrors);
				CError tError = new CError();
				tError.moduleName = "LLClaimCalAutoBL";
				tError.functionName = "getOamnt";
				tError.errorMessage = "计算保全保险年期发生错误!";
				this.mErrors.addOneError(tError);
			}

			if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
				tIReturn = Integer.parseInt(tReturn);
			}
		}

		return tIReturn;
	}

	/**
	 * 得到已交费年期,取自出险时点,需要考虑保全
	 * 
	 * @return
	 */
	private String getPaytoDate() {

		String tReturn = "";
		int tIReturn = 0;
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));

		String tPolNo = StrTool.cTrim(mLLClaimDetailSchema.getPolNo());
		String tNBPolNo = StrTool.cTrim(mLLClaimDetailSchema.getNBPolNo());
		String tPosFlag = StrTool.cTrim(mLLClaimDetailSchema.getPosFlag());

		/* 0未做保全,1已做保全 */
		if (tPosFlag.equals("0")){
			tReturn = mLCPolSchema.getPaytoDate();
		} else {
			String tSql = "select (case when PaytoDate is null then to_date('1900-01-01','yyyy-mm-dd') else PaytoDate end) from LPPol where 1=1 "
					+ " and EdorNo ='"
					+ "?EdorNo?"
					+ "'" + " and PolNo  ='" + "?PolNo?" + "'";
			SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
			sqlbv21.sql(tSql);
			sqlbv21.put("EdorNo", mLLClaimDetailSchema.getPosEdorNo());
			sqlbv21.put("PolNo", tNBPolNo);
			logger.debug("要素PaytoDate[已交费年期]:" + tSql);
			ExeSQL tExeSQL = new ExeSQL();
			tReturn = tExeSQL.getOneValue(sqlbv21);
			if (tExeSQL.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tExeSQL.mErrors);
				CError tError = new CError();
				tError.moduleName = "LLClaimCalAutoBL";
				tError.functionName = "getOamnt";
				tError.errorMessage = "计算保全已交费年期发生错误!";
				this.mErrors.addOneError(tError);
			}
			if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
				tReturn = tReturn.substring(0, 10);
			}
		}

		return tReturn;
	}

	/**
	 * 得到份数 ,取自出险时点,需要考虑保全
	 * 
	 * @return
	 */
	private double getMult() {

		String tReturn = "";
		int tIReturn = 0;
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 0未做保全,1已做保全
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tPolNo = StrTool.cTrim(mLLClaimDetailSchema.getPolNo());
		String tNBPolNo = StrTool.cTrim(mLLClaimDetailSchema.getNBPolNo());
		String tPosFlag = StrTool.cTrim(mLLClaimDetailSchema.getPosFlag());

		/* 对于2已做保全LPGet发生变化的情况,首先判断LPDuty或LPPol中是否有数据 */
		boolean tPisNull = true;
		if (tPosFlag.equals("2")) {
			String tSql = "";
			if (mLLClaimDetailSchema.getAddAmnt() == 0) {
				tSql = "select count(*) from LPDuty where 1=1 "
						+ " and EdorNo ='"
						+ "?EdorNo?" + "'"
						+ " and PolNo  ='" + "?PolNo?" + "'"
						+ " and DutyCode  ='" + "?DutyCode?"
						+ "'";
			} else {
				tSql = "select count(*) from LPPol where 1=1 "
						+ " and EdorNo ='"
						+ "?EdorNo?" + "'"
						+ " and PolNo  ='" + "?PolNo?" + "'";
			}
			SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
			sqlbv22.sql(tSql);
			sqlbv22.put("EdorNo", mLLClaimDetailSchema.getPosEdorNo());
			sqlbv22.put("PolNo", tNBPolNo);
			sqlbv22.put("DutyCode", mLCDutySchema.getDutyCode());
			logger.debug("判断P表中是否有数据:" + tSql);
			ExeSQL tExeSQL = new ExeSQL();
			String tCount = tExeSQL.getOneValue(sqlbv22);

			if (tExeSQL.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tExeSQL.mErrors);
				CError tError = new CError();
				tError.moduleName = "LLClaimCalAutoBL";
				tError.functionName = "getMult";
				tError.errorMessage = "判断P表中是否有数据发生错误!";
				this.mErrors.addOneError(tError);
			}
			if (tCount != null && !tCount.equals("0")) {
				tPisNull = false;
			}

		}

		/* 0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化 */
		if (tPosFlag.equals("0") || tPosFlag.equals("1")
				|| (tPosFlag.equals("2") && tPisNull)) {
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
			 * 加保金额等于0,取Duty上的数据 加保金额大于0,取LCPol上的数据
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (mLLClaimDetailSchema.getAddAmnt() == 0) {
				t_Sum_Return = mLCDutySchema.getMult();
			} else {
				t_Sum_Return = mLCPolSchema.getMult();
			}
		} else if (tPosFlag.equals("2") && !tPisNull) {
			String tSql = "";
			if (mLLClaimDetailSchema.getAddAmnt() == 0) {
				tSql = "select (case when Mult is null then 0 else Mult end) from LPDuty where 1=1 "
						+ " and EdorNo ='"
						+ "?EdorNo?" + "'"
						+ " and PolNo  ='" + "?PolNo?" + "'"
						+ " and DutyCode  ='" + "?DutyCode?"
						+ "'";
			} else {
				tSql = "select (case when Mult is null then 0 else Mult end) from LPPol where 1=1 "
						+ " and EdorNo ='"
						+ "?EdorNo?" + "'"
						+ " and PolNo  ='" + "?PolNo?" + "'";
			}
			SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
			sqlbv23.sql(tSql);
			sqlbv23.put("EdorNo", mLLClaimDetailSchema.getPosEdorNo());
			sqlbv23.put("PolNo", tNBPolNo);
			sqlbv23.put("DutyCode", mLCDutySchema.getDutyCode());
			logger.debug("要素Mult[份数]:" + tSql);
			ExeSQL tExeSQL = new ExeSQL();
			tReturn = StrTool.cTrim(tExeSQL.getOneValue(sqlbv23));
			if (tExeSQL.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tExeSQL.mErrors);
				CError tError = new CError();
				tError.moduleName = "LLClaimCalAutoBL";
				tError.functionName = "getOamnt";
				tError.errorMessage = "计算保全份数发生错误!";
				this.mErrors.addOneError(tError);
			}
			if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
				t_Sum_Return = Double.parseDouble(tReturn);
			}

		}
		return t_Sum_Return;
	}

	/**
	 * 得到每期保费,取自出险时点,需要考虑保全
	 * 
	 * @return
	 */
	private double getPeriodPrem() {

		String tReturn = "";
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));

		String tSql = "";
		ExeSQL tExeSQL = new ExeSQL();

		String tPolNo = StrTool.cTrim(mLLClaimDetailSchema.getPolNo());
		String tNBPolNo = StrTool.cTrim(mLLClaimDetailSchema.getNBPolNo());
		String tPosFlag = StrTool.cTrim(mLLClaimDetailSchema.getPosFlag());

		/* 0未做保全,1已做保全 */
		if (tPosFlag.equals("0")) {
			tSql = "select (case when StandPrem is null then 0 else StandPrem end) from LCPrem where 1=1 "
					+ " and PolNo  ='" + "?PolNo?" + "'"
					+ " and DutyCode  ='" + "?DutyCode?" + "'"
					+ " and substr(PayPlanCode,1,6) not in ('000000')";
			SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
			sqlbv24.sql(tSql);
			sqlbv24.put("PolNo", mLCDutySchema.getPolNo());
			sqlbv24.put("DutyCode", mLCDutySchema.getDutyCode());
			tReturn = tExeSQL.getOneValue(sqlbv24);
			if (tExeSQL.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tExeSQL.mErrors);
				CError tError = new CError();
				tError.moduleName = "LLClaimCalAutoBL";
				tError.functionName = "getOamnt";
				tError.errorMessage = "计算每期保费发生错误!";
				this.mErrors.addOneError(tError);
			}
			if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
				t_Sum_Return = Double.parseDouble(tReturn);
			}
		} else {
			tSql = "select (case when Prem is null then 0 else Prem end) from LPPrem where 1=1 "
					+ " and EdorNo ='" + "?EdorNo?"
					+ "'" + " and PolNo  ='" + "?PolNo?" + "'"
					+ " and DutyCode  ='" + "?DutyCode?" + "'"
					+ " and substr(PayPlanCode,1,6) not in ('000000')";
			SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
			sqlbv25.sql(tSql);
			sqlbv25.put("EdorNo", mLLClaimDetailSchema.getPosEdorNo());
			sqlbv25.put("PolNo", mLCDutySchema.getPolNo());
			sqlbv25.put("DutyCode", mLCDutySchema.getDutyCode());
			tReturn = tExeSQL.getOneValue(sqlbv25);
			if (tExeSQL.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tExeSQL.mErrors);
				CError tError = new CError();
				tError.moduleName = "LLClaimCalAutoBL";
				tError.functionName = "getOamnt";
				tError.errorMessage = "计算保全每期保费发生错误!";
				this.mErrors.addOneError(tError);
			}
			if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
				t_Sum_Return = Arith.round(Double.parseDouble(tReturn), 2);
			}
		}

		logger.debug("要素PeriodPrem[每期保费]:" + tSql);
		return t_Sum_Return;
	}

	/**
	 * 得到投保人职业类别
	 * 
	 * @return
	 */
	private String getOccupationType() {

		String tReturn = "";
		String tPosFlag = StrTool.cTrim(mLLClaimDetailSchema.getPosFlag());

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 查询保全的轨迹记录
		 * 0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		if (!tPosFlag.equals("0")) {
			LPAppntDB tLPAppntDB = new LPAppntDB();
			tLPAppntDB.setEdorNo(mLLClaimDetailSchema.getPosEdorNo());
			tLPAppntDB.setContNo(mLCPolSchema.getContNo());
			LPAppntSet tLPAppntSet = tLPAppntDB.query();
			if (tLPAppntSet.size() == 1) {
				tReturn = StrTool.cTrim(tLPAppntSet.get(1).getOccupationType());
			}
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 查询当前业务数据
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (tPosFlag.equals("0") || tReturn.length() == 0) {
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCPolSchema.getContNo());
			LCAppntSet tLCAppntSet = tLCAppntDB.query();
			if (tLCAppntSet.size() == 1) {
				tReturn = StrTool.cTrim(tLCAppntSet.get(1).getOccupationType());
			}
		}

		return tReturn;
	}
	
	
	
	/**
	 * 得到保单累计支付
	 * 
	 * @return
	 */
	private double getPolPay() {

		// 计算免赔金额。
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));

		String tSql = "";
		String tCValiDate = "";
		ExeSQL tExeSQL = new ExeSQL();

		// 得到保单累计支付 正常赔付+预付赔付
		tSql =  " select (case when sum(Pay) is null then 0 else sum(Pay) end)  from ("
			    +" select (case when sum(b.RealPay) is null then 0 else sum(b.RealPay) end) Pay from LLClaim a,LLClaimDetail b where 1=1 "
				+ " and a.ClmNo = b.ClmNo"
				+ " and a.ClmState in ('50','60')" // 赔案状态为结案
				+ " and a.ClmNo <>'"+ "?ClmNo?"+ "'"
				+ " and b.GiveType = ('0')" // 给付状态为给付
				+ " and b.PolNo ='" + "?PolNo?" + "'"
				+ " and a.clmno!='"+"?ClmNo?"+"'"
				+" union"
				+" select (case when sum(d.Pay) is null then 0 else sum(d.Pay) end)  Pay"
				+" from ljagetclaim d where d.feeoperationtype='B'"
				+" and d.PolNo ='" + "?PolNo?" + "'"
				+"  ) g";
		SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
		sqlbv26.sql(tSql);
		sqlbv26.put("ClmNo", this.mClmNo);
		sqlbv26.put("PolNo", mLLClaimDetailSchema.getPolNo());
		logger.debug("要素PolPay[保单累计支付]:" + tSql);
		String tPayFranchise = tExeSQL.getOneValue(sqlbv26);

		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "计算保单累计支付发生错误!");
		}

		if (tPayFranchise != null && tPayFranchise.length() > 0) {
			t_Sum_Return = Double.parseDouble(tPayFranchise);
		}

		return t_Sum_Return;
	}
	
	
	

	/**
	 * 得到每个给付责任的累计赔付次数
	 * 
	 * @return
	 */
	private double getGetDutyClaimCount() {

		// 计算免赔金额。
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));

		String tSql = "";
		String tCValiDate = "";
		ExeSQL tExeSQL = new ExeSQL();

		// 得到每个给付责任的累计赔付金额  正常赔付+预付赔付
		tSql =  " select (case when sum(Times) is null then 0 else sum(Times) end)  from ("
			    +"  select (case when count(1) is null then 0 else count(1) end) Times from LLClaim a,LLClaimDetail b where 1=1 "
				+ " and a.ClmNo = b.ClmNo"
				+ " and a.ClmState in ('50','60')" // 赔案状态为签批和结案
				+ " and b.GiveType = ('0')" // 给付状态为给付
				+ " and b.PolNo ='" + "?PolNo?" + "'"
				+ " and b.GetDutyCode='"+"?GetDutyCode?"+"'"
				+ " and a.clmno!='"+"?clmno?"+"'"
				+ " and b.realpay>0"
				//+ " and b.GetDutyKind='"+mLLClaimDetailSchema.getGetDutyKind()+"'"
				+"  union"
				+"  select (case when count(1) is null then 0 else count(1) end)  Times"
				+"  from ljagetclaim d where d.feeoperationtype='B'"
				+ " and d.GetDutyCode='"+"?GetDutyCode?"+"'"
				//+ " and d.GetDutyKind='"+mLLClaimDetailSchema.getGetDutyKind()+"'"
				+"  and d.PolNo ='" + "?PolNo?" + "'"
				+"  and d.pay >0"
				+"  ) g";
		SQLwithBindVariables sqlbv27 =new SQLwithBindVariables();
		sqlbv27.sql(tSql);
		sqlbv27.put("PolNo", mLLClaimDetailSchema.getPolNo());
		sqlbv27.put("GetDutyCode", mLLClaimDetailSchema.getGetDutyCode());
		sqlbv27.put("clmno", this.mClmNo);
		logger.debug("--计算要素ClaimCounts[保单"+mLLClaimDetailSchema.getPolNo()+",给付责任"+mLLClaimDetailSchema.getGetDutyCode()+"累计支付次数]:" + tSql);	

		String tPayFranchise = tExeSQL.getOneValue(sqlbv27);

		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "计算保单"+mLLClaimDetailSchema.getPolNo()+",给付责任"+
					mLLClaimDetailSchema.getGetDutyCode()+"失败,原因是"+tExeSQL.mErrors.getLastError());
		}

		if (tPayFranchise != null && tPayFranchise.length() > 0) {
			t_Sum_Return = Double.parseDouble(tPayFranchise);
		}

		return t_Sum_Return;
	}
	
	
	/**
	 * 得到每个给付责任的累计赔付金额,适用于除费用补偿型险种之外的所有险种
	 * 
	 * @return
	 */
	private double getGetDutySumPay() {

		// 计算免赔金额。
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));

		String tSql = "";
		String tCValiDate = "";
		ExeSQL tExeSQL = new ExeSQL();
		
		
		LMRiskSortDB  tLMRiskSortDB=new LMRiskSortDB();
		tLMRiskSortDB.setRiskCode(mLLClaimDetailSchema.getRiskCode());
		tLMRiskSortDB.setRiskSortType("10");//责任下多个给付责任分类共享保额相互冲减
		tLMRiskSortDB.setRiskSortValue(mLLClaimDetailSchema.getGetDutyCode());
	
		
		if(!tLMRiskSortDB.getInfo())
        {
			// 得到每个给付责任的累计赔付金额  正常赔付+预付赔付
			tSql =  " select (case when sum(Pay) is null then 0 else sum(Pay) end)  from ("
			    +"  select (case when sum(b.RealPay) is null then 0 else sum(b.RealPay) end) Pay from LLClaim a,LLClaimDetail b where 1=1 "
				+ " and a.ClmNo = b.ClmNo"
				+ " and a.ClmState in ('50','60')" // 赔案状态为签批和结案
				+ " and b.GiveType = ('0')" // 给付状态为给付
				+ " and b.PolNo ='" + "?PolNo?" + "'"
				+ " and b.GetDutyCode='"+"?GetDutyCode?"+"'"
				+ " and a.clmno!='"+"?clmno?"+"'"
				//+ " and b.GetDutyKind='"+mLLClaimDetailSchema.getGetDutyKind()+"'"
				+"  union"
				+"  select (case when sum(d.Pay) is null then 0 else sum(d.Pay) end)  Pay"
				+"  from ljagetclaim d where d.feeoperationtype='B'"
				+ " and d.GetDutyCode='"+"?GetDutyCode?"+"'"
				//+ " and d.GetDutyKind='"+mLLClaimDetailSchema.getGetDutyKind()+"'"
				+"  and d.PolNo ='" + "?PolNo?" + "'"
				+"  ) g";
			
			logger.debug("--计算要素GetDutySumPay[保单"+mLLClaimDetailSchema.getPolNo()+",给付责任"+mLLClaimDetailSchema.getGetDutyCode()+"累计支付]:" + tSql);
			
        }
		else
		{
			//能够查询到记录,表示该险种是多个给付责任分类共享保额,所以查询到应该是共享统一保额的所有给负责人的既往赔付金额
			tSql =  " select (case when sum(Pay) is null then 0 else sum(Pay) end)  from ("
			    +"  select (case when sum(b.RealPay) is null then 0 else sum(b.RealPay) end) Pay from LLClaim a,LLClaimDetail b where 1=1 "
				+ " and a.ClmNo = b.ClmNo"
				+ " and a.clmno!='"+"?clmno?"+"'"
				+ " and a.ClmState in ('50','60')" // 赔案状态为签批和结案
				+ " and b.GiveType = ('0')" // 给付状态为给付
				+ " and b.PolNo ='" + "?PolNo?" + "'"
				+ " and b.GetDutyCode in (select  risksortvalue from LMRiskSort a where RiskCode = '"+"?RiskCode?"+"' " 
				+ " and a.risksorttype ='"+"?risksorttype?"+"' and remark='"+"?remark?"+"')"
				//+ " and b.GetDutyKind='"+mLLClaimDetailSchema.getGetDutyKind()+"'"
				+"  union"
				+"  select (case when sum(d.Pay) is null then 0 else sum(d.Pay) end)  Pay"
				+"  from ljagetclaim d where d.feeoperationtype='B'"
				+ " and d.GetDutyCode in (select  risksortvalue from LMRiskSort a where RiskCode = '"+"?RiskCode?"+"' " 
				+ " and a.risksorttype ='"+"?risksorttype?"+"' and remark='"+"?remark?"+"')"
				//+ " and d.GetDutyKind='"+mLLClaimDetailSchema.getGetDutyKind()+"'"
				+"  and d.PolNo ='" + "?PolNo?" + "'"
				+"  ) g";
			
			logger.debug("--计算要素GetDutySumPay[保单"+mLLClaimDetailSchema.getPolNo()+",险种:"+tLMRiskSortDB.getRiskCode()+"在LMRiskSort描述的Remark等于"+tLMRiskSortDB.getRemark()+"的所有给付责任的累计支付]:" + tSql);
			
		}
		SQLwithBindVariables sqlbv28 =new SQLwithBindVariables();
		sqlbv28.sql(tSql);
		sqlbv28.put("PolNo", mLLClaimDetailSchema.getPolNo());
		sqlbv28.put("GetDutyCode", mLLClaimDetailSchema.getGetDutyCode());
		sqlbv28.put("remark", tLMRiskSortDB.getRemark());
		sqlbv28.put("RiskCode", tLMRiskSortDB.getRiskCode());
		sqlbv28.put("risksorttype", tLMRiskSortDB.getRiskSortType());
		sqlbv28.put("clmno", this.mClmNo);
		String tPayFranchise = tExeSQL.getOneValue(sqlbv28);

		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "计算保单"+mLLClaimDetailSchema.getPolNo()+",给付责任"+
					mLLClaimDetailSchema.getGetDutyCode()+"失败,原因是"+tExeSQL.mErrors.getLastError());
		}

		if (tPayFranchise != null && tPayFranchise.length() > 0) {
			t_Sum_Return = Double.parseDouble(tPayFranchise);
		}

		return t_Sum_Return;
	}
	
	/**
	 * 得到每个给付责任的累计赔付金额,适用于费用补偿型险种
	 * 
	 * @return
	 */
	private double getCompensateDutySumPay() {

		// 计算免赔金额。
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));

		String tSql = "";
		String tCValiDate = "";
		ExeSQL tExeSQL = new ExeSQL();

		// 得到每个给付责任的累计赔付金额  正常赔付+预付赔付
		tSql =  " select (case when sum(Pay) is null then 0 else sum(Pay) end)  from ("
		    +"  select (case when sum(b.RealPay) is null then 0 else sum(b.RealPay) end) Pay from LLClaim a,LLClaimDetail b where 1=1 "
			+ " and a.ClmNo = b.ClmNo"
			+ " and a.ClmState in ('50','60')" // 赔案状态为结案
			+ " and b.GiveType = ('0')" // 给付状态为给付
			+ " and b.CaseRelaNo='"+"?mAccNo?"+"'"
			+ " and b.PolNo ='" + "?PolNo?" + "'"
			+ " and b.GetDutyCode='"+"?GetDutyCode?"+"'"
			+ " and b.GetDutyKind='"+"?GetDutyKind?"+"'"
			+ " and a.clmno!='"+"?clmno?"+"'"
			+"  union"
			+"  select (case when sum(d.Pay) is null then 0 else sum(d.Pay) end)  Pay"
			+"  from ljagetclaim d,llcaserela p where d.feeoperationtype='B'"
			+"  and d.otherno=p.caseno and p.caserelano='"+"?mAccNo?"+"'"
			+ " and d.GetDutyCode='"+"?GetDutyCode?"+"'"
			+ " and d.GetDutyKind='"+"?GetDutyKind?"+"'"
			+"  and d.PolNo ='" + "?PolNo?" + "'"
			+"  ) g";
		SQLwithBindVariables sqlbv29 =new SQLwithBindVariables();
		sqlbv29.sql(tSql);
		sqlbv29.put("PolNo", mLLClaimDetailSchema.getPolNo());
		sqlbv29.put("GetDutyCode", mLLClaimDetailSchema.getGetDutyCode());
		sqlbv29.put("GetDutyKind", mLLClaimDetailSchema.getGetDutyKind());
		sqlbv29.put("mAccNo", this.mAccNo);
		sqlbv29.put("clmno", this.mClmNo);
		logger.debug("--计算要素CompensateDutySumPay[保单"+mLLClaimDetailSchema.getPolNo()+",给付责任"+mLLClaimDetailSchema.getGetDutyCode()+"累计支付]:" + tSql);
		String tPayFranchise = tExeSQL.getOneValue(sqlbv29);

		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "计算保单"+mLLClaimDetailSchema.getPolNo()+",给付责任"+
					mLLClaimDetailSchema.getGetDutyCode()+"失败,原因是"+tExeSQL.mErrors.getLastError());
		}

		if (tPayFranchise != null && tPayFranchise.length() > 0) {
			t_Sum_Return = Double.parseDouble(tPayFranchise);
		}

		return t_Sum_Return;
	}
	
	
	/**
	 * 得到共享保额的给付责任中已经计算出的赔付金
	 * 如果不是共享保额的责任,或是第一个给付责任,则返回0
	 * 适用于责任下多个给付责任共享险种保额,理算时给付责任的相互冲减
	 * @return
	 */
	private double getCurrentDutyPay() {

		// 计算免赔金额。
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));
		
		logger.debug("开始计算案件:"+mLLClaimDetailSchema.getClmNo()+",险种:"+mLLClaimDetailSchema.getRiskCode()+
				",除给付责任:"+mLLClaimDetailSchema.getGetDutyCode()+"外的其他已经算出的理赔金总额");
		
		LMRiskSortDB  tLMRiskSortDB=new LMRiskSortDB();
		tLMRiskSortDB.setRiskCode(mLLClaimDetailSchema.getRiskCode());
		tLMRiskSortDB.setRiskSortType("9");//责任下多个给付责任共享保额相互冲减
		tLMRiskSortDB.setRiskSortValue(mLLClaimDetailSchema.getGetDutyCode());
		
		
		if(!tLMRiskSortDB.getInfo())
        {
			tLMRiskSortDB=null;
            return t_Sum_Return;
        }
		else
		{
			logger.debug("mLLClaimDetailSet.size():"+mLLClaimDetailSet.size());
			for(int i=1;i<=mLLClaimDetailSet.size();i++)
			{
				//首先是同一张保单
				if(mLLClaimDetailSet.get(i).getPolNo().equals(mLLClaimDetailSchema.getPolNo()))
				{
					//针对同一个geidutykind,不同的getdutykind的多个给付责任共享保额的情况or同一个geidutycode,不同的getdutycode的多个给付责任共享保额的情况
					//或者针对康福等多个连带被保险人共用同一个账户的情况,根据CustomerNo
					if((!mLLClaimDetailSet.get(i).getGetDutyCode().equals(mLLClaimDetailSchema.getGetDutyCode()))||
							(!mLLClaimDetailSet.get(i).getGetDutyKind().equals(mLLClaimDetailSchema.getGetDutyKind()))||
								(!mLLClaimDetailSet.get(i).getCustomerNo().equals(mLLClaimDetailSchema.getCustomerNo()))){
									
						t_Sum_Return=t_Sum_Return+mLLClaimDetailSet.get(i).getRealPay();
					}
					else
					{
						logger.debug("自己无所谓累计！");
					}
				}
			}
		}
		
		logger.debug("计算案件:"+mLLClaimDetailSchema.getClmNo()+",险种:"+mLLClaimDetailSchema.getRiskCode()+
				",除给付责任:"+mLLClaimDetailSchema.getGetDutyCode()+"外的其他已经算出的理赔金总额:"+t_Sum_Return);

		return t_Sum_Return;
	}
	
	
	
	/**
	 * 得到共享保额的给付责任中已经计算出的赔付金
	 * 如果不是共享保额的责任,或是第一个给付责任,则返回0
	 * 适用于责任下多个给付责任分類共享不同的保额,理算时给付责任的相互冲减
	 * @return
	 */
	private double getCurrentClassifiDutyPay() {

		// 计算免赔金额。
		double t_Sum_Return = 0;
		t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00")
				.format(t_Sum_Return));
		
		logger.debug("开始计算案件:"+mLLClaimDetailSchema.getClmNo()+",险种:"+mLLClaimDetailSchema.getRiskCode()+
				",除给付责任:"+mLLClaimDetailSchema.getGetDutyCode()+"外的和本次循环理算的给付责任共享保额的其他已经算出给付责任的理赔金总额");
		
		LMRiskSortDB  tLMRiskSortDB=new LMRiskSortDB();
		tLMRiskSortDB.setRiskCode(mLLClaimDetailSchema.getRiskCode());
		tLMRiskSortDB.setRiskSortType("10");//适用于责任下多个给付责任分類共享不同的保额
		//tLMRiskSortDB.setRiskSortValue(mLLClaimDetailSchema.getGetDutyCode());
		
		LMRiskSortSet tLMRiskSortSet = new LMRiskSortSet();
		tLMRiskSortSet=tLMRiskSortDB.query();
		if(tLMRiskSortSet.size()>0)
        {
			//分类数组,分别记录给付责任编码和分类数组
			String[][] classifieArray = new String[tLMRiskSortSet.size()][2];
			
			for(int i=0;i<tLMRiskSortSet.size();i++)
			{
				classifieArray[i][0] =  tLMRiskSortSet.get(i+1).getRiskSortValue();
				classifieArray[i][1] =  tLMRiskSortSet.get(i+1).getRemark();
				
				logger.debug("classifieArray["+i+"][0]:"+classifieArray[i][0]+",classifieArray["+i+"][1]:"+classifieArray[i][1]);
			}
			
			logger.debug("mLLClaimDetailSet.size():"+mLLClaimDetailSet.size());
			for(int i=1;i<=mLLClaimDetailSet.size();i++)
			{
				//首先要是同一险种
				if(mLLClaimDetailSet.get(i).getPolNo().equals(mLLClaimDetailSchema.getPolNo())){
					
					//针对同一个geidutykind,不同的getdutykind的多个给付责任共享保额的情况or同一个geidutycode,不同的getdutycode的多个给付责任共享保额的情况
					if((!mLLClaimDetailSet.get(i).getGetDutyCode().equals(mLLClaimDetailSchema.getGetDutyCode()))||
							(!mLLClaimDetailSet.get(i).getGetDutyKind().equals(mLLClaimDetailSchema.getGetDutyKind()))){
						
		
						if(whetherClassifiDuty(mLLClaimDetailSchema.getGetDutyCode(),mLLClaimDetailSet.get(i).getGetDutyCode(),classifieArray))
						{
							t_Sum_Return=t_Sum_Return+mLLClaimDetailSet.get(i).getRealPay();
						}
					}
					else
					{
						logger.debug("自己无所有共享！");
					}
						 
				}
			}
			
			classifieArray=null;
        }
		
		logger.debug("计算案件:"+mLLClaimDetailSchema.getClmNo()+",险种:"+mLLClaimDetailSchema.getPolNo()+
				",除给付责任:"+mLLClaimDetailSchema.getGetDutyCode()+"外的和本次循环理算的给付责任共享保额的其他已经算出给付责任的理赔金总额:"+t_Sum_Return);

		return t_Sum_Return;
	}
	
	

	/**
	 * 判断两个两个责任是否属于共享同一类保额
	 * 
	 * @return
	 */
	private boolean whetherClassifiDuty(String getdutycode1,String getdutycode2,String[][] mArray)
	{
		
		int k1=1000;
		int k2=1000;
		
		for(int i=0;i<mArray.length;i++)
		{
			//从数组中寻找getdutycode1所在的位置
			if(mArray[i][0].equals(getdutycode1))
			{
				k1=i;
				break;
			}
		}
		
		for(int i=0;i<mArray.length;i++)
		{
			//从数组中寻找getdutycode2所在的位置
			if(mArray[i][0].equals(getdutycode2))
			{
				k2=i;
				break;
			}
		}
		
		if(k1!=1000&&k2!=1000)
		{
			if(mArray[k1][1].equals(mArray[k2][1]))
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	
	/**
	 * zhangzheng 2009-02-18
	 * 得到门诊,住院,特种费用的费用项目编码
	 * @return
	 */
    private String getDutyItemCode(String tClmNo,String tDutyFeeStaNo)
    {
       String tItemCode="000";
            
       if(!(tDutyFeeStaNo==null||tDutyFeeStaNo.equals(""))){
    	   
           String sql=" select k from("
  	         +" select feeitemcode k from LLCaseReceipt where clmno='"+"?clmno?"+"' and feedetailno='"+"?stano?"+"'"
  	         +" union "
  	         +" select factorcode k from llotherfactor where clmno='"+"?clmno?"+"' and SerialNo='"+"?stano?"+"'"
  	         +" ) g";
           SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
           sqlbv30.sql(sql);
           sqlbv30.put("clmno", tClmNo);
           sqlbv30.put("stano", tDutyFeeStaNo);
	       logger.debug("查询案件:"+tClmNo+",账单:"+tDutyFeeStaNo+"的费用项目编码sql:"+sql);
	     
	       ExeSQL tExeSQL = new ExeSQL();
	
	       tItemCode=tExeSQL.getOneValue(sqlbv30);
	     
	       logger.debug("查询案件:"+tClmNo+",账单:"+tDutyFeeStaNo+"的费用项目编码:"+tItemCode);
       }

       
       return tItemCode;
    }
    
    /**
	 * zhangzheng 2009-02-18
	 * 得到门诊,住院,特种费用的账单结束日期
	 * @return
	 */
    private String getFeereceiEndDate(String tClmNo,String tDutyFeeStaNo)
    {
       String date="3000-01-01";
            
       if(!(tDutyFeeStaNo==null||tDutyFeeStaNo.equals(""))){
    	   
           String sql=" select k from("
  	         +" select enddate k from LLCaseReceipt where clmno='"+"?clmno?"+"' and feedetailno='"+"?stano?"+"'"
  	         +" union "
  	         +" select enddate k from llotherfactor where clmno='"+"?clmno?"+"' and SerialNo='"+"?stano?"+"'"
  	         +" ) g";
           SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
           sqlbv31.sql(sql);
           sqlbv31.put("clmno", tClmNo);
           sqlbv31.put("stano", tDutyFeeStaNo);
	       logger.debug("查询案件:"+tClmNo+",账单:"+tDutyFeeStaNo+"的结束日期sql:"+sql);
	     
	       ExeSQL tExeSQL = new ExeSQL();
	
	       date=tExeSQL.getOneValue(sqlbv31).substring(0,10);
	     
	       logger.debug("查询案件:"+tClmNo+",账单:"+tDutyFeeStaNo+"的结束日期:"+date);
       }

       
       return date;
    }

	
	
	

	/**
	 * 得到初始基本保额 先到P表去查，如果没有，认为是LCDuty的Amnt
	 * 
	 * @return
	 */
	private double getOamnt() {

		double t_Sum_Return = 0;

		t_Sum_Return = mLCGetSchema.getStandMoney();

		String tContNo = StrTool.cTrim(mLLClaimDetailSchema.getContNo());
		String tPolNo = StrTool.cTrim(mLLClaimDetailSchema.getPolNo());
		String tNBPolNo = StrTool.cTrim(mLLClaimDetailSchema.getNBPolNo());

		LPEdorItemSchema tLPEdorItemSchema = mLLClaimPubFunBL.getLPEdorItemMin(
				tContNo, tPolNo, null, null, null);// 得到保全批改项目表

		if (tLPEdorItemSchema == null) {
			return t_Sum_Return;
		}

		if (tLPEdorItemSchema != null) {
			String tPosEdorNo = StrTool.cTrim(tLPEdorItemSchema.getEdorNo());
			LPGetSchema tLPGetSchema = mLLClaimPubFunBL.getLPGet(tPosEdorNo,
					tNBPolNo, mLCGetSchema);
			if (tLPGetSchema == null) {
				return t_Sum_Return;
			} else {
				t_Sum_Return = tLPGetSchema.getStandMoney();
			}
		}

		return t_Sum_Return;
	}

	/**
	 * 得到加保保项的初始基本保额 先到P表去查，如果没有，认为是LCDuty的Amnt
	 * 
	 * @return
	 */
	private double getOAddAmnt() {

		double t_Sum_Return = 0;

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 从待匹配的责任中查找加保的保项信息
		 * 如果无信息,则返回为0
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tContNo = StrTool.cTrim(mLLClaimDetailSchema.getContNo());
		String tPolNo = StrTool.cTrim(mLLClaimDetailSchema.getPolNo());
		String tNBPolNo = StrTool.cTrim(mLLClaimDetailSchema.getNBPolNo());
		String tDutyCode = StrTool.cTrim(mLLClaimDetailSchema.getDutyCode());
		String tGetDutyKind = StrTool.cTrim(mLLClaimDetailSchema
				.getGetDutyKind());
		String tGetDutyCode = StrTool.cTrim(mLLClaimDetailSchema
				.getGetDutyCode());

		String tSql = "";
		tSql = "select * from LLToClaimDuty where 1=1" + " and CaseNo='"
				+ "?CaseNo?" + "'" + " and PolNo='" + "?PolNo?" + "'"
				+ " and GetDutyCode='" + "?GetDutyCode?" + "'"
				+ " and GetDutyKind='" + "?GetDutyKind?" + "'"
				+ " and DutyCode like concat('" + "?DutyCode?" + "','%')"
				+ " and DutyCode not in ('" + "?DutyCode?" + "')";
		SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
        sqlbv32.sql(tSql);
        sqlbv32.put("CaseNo", this.mClmNo);
        sqlbv32.put("PolNo", tPolNo);
        sqlbv32.put("GetDutyCode", tGetDutyCode);
        sqlbv32.put("GetDutyKind", tGetDutyKind);
        sqlbv32.put("DutyCode", tDutyCode);
		LLToClaimDutyDB tLLToClaimDutyDB = new LLToClaimDutyDB();
		LLToClaimDutySet tLLToClaimDutySet = tLLToClaimDutyDB
				.executeQuery(sqlbv32);

		if (tLLToClaimDutySet.size() == 0) {
			return 0;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 如果有加保的保项,取出LCDuty的数据
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		LLToClaimDutySchema tLLToClaimDutySchema = tLLToClaimDutySet.get(1);
		tDutyCode = tLLToClaimDutySchema.getDutyCode();
		tGetDutyCode = tLLToClaimDutySchema.getGetDutyCode();
		LCGetDB tLCGetDB = new LCGetDB();
		tLCGetDB.setPolNo(tPolNo);
		tLCGetDB.setDutyCode(tDutyCode);
		tLCGetDB.setGetDutyCode(tGetDutyCode);
		LCGetSet tLCGetSet = tLCGetDB.query();
		if (tLCGetSet.size() == 0) {
			this.mErrors.copyAllErrors(tLLToClaimDutyDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimCalAutoBL";
			tError.functionName = "getOAddAmnt";
			tError.errorMessage = "获取加保所在的责任发生错误!";
			logger.debug("-----------------------------------------------------------------------------------");
			logger.debug("--LLClaimCalMatchBL.getOAddAmnt()--获取加保所在的责任没有找到!");
			logger.debug("-----------------------------------------------------------------------------------");
			this.mErrors.addOneError(tError);
			return 0;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 如果有加保的保项,判断是否做过保全操作
		 * 如果无,取当前的保额
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		t_Sum_Return = tLCGetSet.get(1).getStandMoney();

		LPEdorItemSchema tLPEdorItemSchema = mLLClaimPubFunBL.getLPEdorItemMin(
				tContNo, tPolNo, null, null, null);// 得到保全批改项目表

		if (tLPEdorItemSchema == null) {
			return t_Sum_Return;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0 如果有加保的保项,判断是否做过保全操作
		 * 如果有,取出险时点的保额
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (tLPEdorItemSchema != null) {
			String tPosEdorNo = StrTool.cTrim(tLPEdorItemSchema.getEdorNo());

			LCGetSchema tLCGetSchema = new LCGetSchema();
			tLCGetSchema.setPolNo(tPolNo);
			tLCGetSchema.setDutyCode(tDutyCode);
			tLCGetSchema.setGetDutyCode(tGetDutyCode);
			LPGetSchema tLPGetSchema = mLLClaimPubFunBL.getLPGet(tPosEdorNo,
					tNBPolNo, tLCGetSchema);
			if (tLPGetSchema == null) {
				return t_Sum_Return;
			} else {
				t_Sum_Return = tLPGetSchema.getStandMoney();
			}
		}
		return t_Sum_Return;
	}

	/**
	 * 交费次数,取自出险时点,需要考虑保全
	 * 
	 * @return
	 */
	private double getPayTimes() {

		double tDReturn = 0;
		String tPolNo = StrTool.cTrim(mLLClaimDetailSchema.getPolNo());
		String tNBPolNo = StrTool.cTrim(mLLClaimDetailSchema.getNBPolNo());
		String tDutyCode = StrTool.cTrim(mLLClaimDetailSchema.getDutyCode());

		LCPremSchema tLCPremSchema = mLLClaimPubFunBL.getLCPremSql(tNBPolNo,
				tDutyCode, "0");

		if (tLCPremSchema == null) {
			mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
			logger.debug("要素PayTimes--缴费次数查找失败"
					+ mLLClaimPubFunBL.mErrors.getError(0).errorMessage);
			return 0;
		}

		String tUrgePayFlag = StrTool.cTrim(tLCPremSchema.getUrgePayFlag());

		if (tUrgePayFlag.equals("Y")) {
			String tPayIntv = String.valueOf(tLCPremSchema.getPayIntv());
			tDReturn = mLLClaimPubFunBL.getLCPremPeriodTimes(tLCPremSchema
					.getPayStartDate(), tLCPremSchema.getPayEndDate(),
					tPayIntv, mInsDate);
		} else if (tUrgePayFlag.equals("N"))// 不催缴，dutycode='115000'
		{
			String tSql = "select (case when count(*) is null then 0 else count(*) end) from LJaPayPerson a where 1=1 "
					+ " and a.PolNo ='"
					+ "?PolNo?"
					+ "'"
					+ " and (to_date('"
					+ "?date1?"
					+ "','YYYY-MM-DD') >= LastPayToDate "
					+ " or LastPayToDate < to_date('1900-01-01','yyyy-mm-dd'))"
					+ " and char_Length(Trim(PayPlanCode))=6 ";
			SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
	        sqlbv33.sql(tSql);
	        sqlbv33.put("date1", this.mInsDate);
	        sqlbv33.put("PolNo", tPolNo);
			logger.debug("--要素PayTimes[交费次数]:" + tSql);
			ExeSQL tExeSQL = new ExeSQL();
			String tCal = StrTool.cTrim(tExeSQL.getOneValue(sqlbv33));

			if (tExeSQL.mErrors.needDealError()) {
				this.mErrors.addOneError("计算不催缴的交费次数失败!!!"
						+ tExeSQL.mErrors.getError(0).errorMessage);
			}
			tDReturn = Double.parseDouble(tCal);
		}

		return tDReturn;
	}

	/**
	 * 返回附加险所在主险的保项信息
	 * 
	 * @return
	 */
	private LLClaimDetailSchema getRMRiskInfo() {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 返回主险所在的保项信息 主附险标志
		 * M主险 S附加险 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimDetailSchema tRLLClaimDetailSchema = null;

		String tCurrRiskType = mLLClaimDetailSchema.getRiskType();
		if (tCurrRiskType.equals("M")) {
			return null;
		}

		String tCurrContNo = mLLClaimDetailSchema.getContNo();
		String tCurrPolNo = mLLClaimDetailSchema.getPolNo();

		for (int i = 1; i <= mLLClaimDetailSet.size(); i++) {
			LLClaimDetailSchema tLLClaimDetailSchema = mLLClaimDetailSet.get(i);
			String tLoopContNo = tLLClaimDetailSchema.getContNo();
			String tLoopPolNo = tLLClaimDetailSchema.getPolNo();

			String tLoopRiskType = tLLClaimDetailSchema.getRiskType();

			if (tLoopRiskType.equals("M") && tCurrContNo.equals(tLoopContNo)
					&& !tCurrPolNo.equals(tLoopPolNo)) {
				return tLLClaimDetailSchema;
			}
		}

		return tRLLClaimDetailSchema;
	}

	/**
	 * 得到理算后的某项费用总和 如:床位费 getTotalFee(mLLClaimDutyFeeSchema,"CR","B") by 周磊
	 * 2007-1-10
	 * 6225 7575 4176 2218
	 * @return
	 */
	private String getTotalFee(LLClaimDutyFeeSchema pLLClaimDutyFeeSchema,
			String pFeeType, String pDutyFeeType) {
		double tFee = 0;
		for (int i = 1; i <= mLLClaimDutyFeeSet.size(); i++) {
			if (mLLClaimDutyFeeSet.get(i).getClmNo().equals(
					pLLClaimDutyFeeSchema.getClmNo())
					&& mLLClaimDutyFeeSet.get(i).getPolNo().equals(
							pLLClaimDutyFeeSchema.getPolNo())
					&& mLLClaimDutyFeeSet.get(i).getDutyCode().equals(
							pLLClaimDutyFeeSchema.getDutyCode())
					&& mLLClaimDutyFeeSet.get(i).getGetDutyCode().equals(
							pLLClaimDutyFeeSchema.getGetDutyCode())
					&& mLLClaimDutyFeeSet.get(i).getDutyFeeType().equals(
							pDutyFeeType)
					&& mLLClaimDutyFeeSet.get(i).getDutyFeeCode().substring(0,
							2).equals(pFeeType)) {
				tFee = tFee + mLLClaimDutyFeeSet.get(i).getCalSum();
			}
		}
		return new DecimalFormat("0.00").format(tFee);
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－结束－－－－－－－计算要素准备－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {

		TransferData tTransferData = new TransferData();
		if (mLLClaimPolicySet != null && mLLClaimPolicySet.size() > 0) {
			mMMap.put(mLLClaimSchema, "DELETE&INSERT");
			tTransferData.setNameAndValue("Return", "TRUE");
		} else {
			tTransferData.setNameAndValue("Return", "FALSE");
		}
		mResult.add(tTransferData);

		mMMap.put(mLLClaimPolicySet, "INSERT");
		mMMap.put(mLLClaimDetailSet, "INSERT");
		mMMap.put(mLLClaimDutyFeeSet, "INSERT");

		/*
		 * sl add 2015-04-22 累加器处理 累加器trace提交
		 */
		if (mLCalculatorTraceSet !=null && mLCalculatorTraceSet.size() > 0){
			mMMap.put(mLCalculatorTraceSet, "INSERT");
		}

		mInputData.clear();
		mInputData.add(mMMap);
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
			tError.moduleName = "LLClaimCalCheckBL";
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

	}

}

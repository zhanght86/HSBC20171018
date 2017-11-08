package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LLContStateDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LLBalanceSchema;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.schema.LLContStateSchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LLContStateSet;
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
 * Description: 险种终止处理
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
public class LLClaimContPolDealBL {
private static Logger logger = Logger.getLogger(LLClaimContPolDealBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mVData = new VData();
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
	private Reflections mReflections = new Reflections();

	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
	private LCPolSchema mLCPolSchema = new LCPolSchema();

	private String mClmNo = ""; // 赔案号
	private String mCustNo = ""; // 客户号
	private String mContType = ""; // 总单类型,1-个人总投保单,2-集体总单
	private String mContNo = ""; // 合同号
	private String mPolNo = ""; // 险种号
	private String mContStateReason = "";// 合同险种终止处理意见
	private String mContEndDate = ""; // 合同险种终止时间
	private String mInsDate = ""; // 出险时间

	public LLClaimContPolDealBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------险种终止处理-----LLClaimContDealBLF测试-----开始----------");

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!checkData()) {
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

		logger.debug("----------险种终止处理-----LLClaimContDealBLF测试-----结束----------");
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

		this.mClmNo = (String) mTransferData.getValueByName("ClmNo"); // 赔案号
		this.mCustNo = (String) mTransferData.getValueByName("CustNo"); // 客户号
		this.mContType = (String) mTransferData.getValueByName("ContType"); // 总单类型,1-个人总投保单,2-集体总单
		this.mContNo = (String) mTransferData.getValueByName("ContNo"); // 合同号
		this.mPolNo = (String) mTransferData.getValueByName("PolNo"); // 险种号
		this.mContStateReason = (String) mTransferData
				.getValueByName("ContStateReason");// 合同终止处理意见
		this.mContEndDate = (String) mTransferData
				.getValueByName("ContEndDate");// 合同终止时间

		return true;
	}

	/**
	 * 处理数据前做数据校验
	 * 
	 * @return
	 */
	private boolean checkData() {
		String tSql = "select * from LLContState where 1 =1 " + " and ClmNo='"
				+ "?ClmNo?" + "'" + " and PolNo  ='" + "?PolNo?" + "'"
				+ " and ClmState='1'"; // 1--赔案审批通过涉及处理的保单
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("ClmNo", this.mClmNo);
		sqlbv.put("PolNo", this.mPolNo);
		LLContStateDB tLLContStateDB = new LLContStateDB();
		LLContStateSet tLLContStateSet = tLLContStateDB.executeQuery(sqlbv);

		if (tLLContStateDB.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLLContStateDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimCalAutoBL";
			tError.functionName = "getPolPay";
			tError.errorMessage = "查询合同状态暂存信息失败!";
			this.mErrors.addOneError(tError);
			logger.debug("------------------------------------------------------");
			logger.debug("--LLClaimContPolDealBL.checkData()--查询合同状态暂存信息失败!"
							+ tSql);
			logger.debug("------------------------------------------------------");
			return false;
		}

		if (tLLContStateSet.size() > 0) {
			/*
			 * 2005-08-15应曾军要求,去掉此校验,目的是为了更改处理结论,因为审批不通过,可能回到 审核作个解除合同XXX的结论
			 * 
			 */
			// CError tError = new CError();
			// tError.moduleName = "LLClaimContPolDealBL";
			// tError.functionName = "getLLContState";
			// tError.errorMessage = "保单险种号:["+this.mPolNo+"]已经被系统自动终止,不能人工操作!";
			// this.mErrors.addOneError(tError);
			// return false;
		}

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 先删除相关数据,在进行后续操作
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tSql1 = "delete from LLBalance where ClmNo='"
				+ "?ClmNo?"
				+ "' and substr(FeeOperationType,1,1) in ('C','D')  and substr(FeeOperationType,1,3) not in ('C05','C30') and  PolNo = '"
				+ "?PolNo?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql1);
		sqlbv1.put("ClmNo", this.mClmNo);
		sqlbv1.put("PolNo", this.mPolNo);
		this.mMMap.put(sqlbv1, "DELETE");

		String tSql2 = "delete from LLContState where ClmNo  ='" + "?ClmNo?"
				+ "' and PolNo  ='" + "?PolNo?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSql2);
		sqlbv2.put("ClmNo", this.mClmNo);
		sqlbv2.put("PolNo", this.mPolNo);
		this.mMMap.put(sqlbv2, "DELETE");

		String tSql3 = "delete from LLBnf where ClmNo='" + "?ClmNo?"
				+ "' and BnfKind = 'A'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSql3);
		sqlbv3.put("ClmNo", this.mClmNo);
		mMMap.put(sqlbv3, "DELETE");
		
		String tSql6 = "delete from LLBnfGather where ClmNo='" + "?ClmNo?"
		+ "' and BnfKind = 'A'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSql6);
		sqlbv4.put("ClmNo", this.mClmNo);
		mMMap.put(sqlbv4, "DELETE");

		String tSql4 = "delete from LJSGet where OtherNo='" + "?ClmNo?"
				+ "' and OtherNoType='5' ";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tSql4);
		sqlbv5.put("ClmNo", this.mClmNo);
		mMMap.put(sqlbv5, "DELETE");

		String tSql5 = "delete from LJSGetClaim where OtherNo='" + "?ClmNo?"
				+ "' and OtherNoType='5' ";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(tSql5);
		sqlbv6.put("ClmNo", this.mClmNo);
		mMMap.put(sqlbv6, "DELETE");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 得到出险时间
		 * 
		 * 将赔案表的合同处理状态置为1[已处理]
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		this.mInsDate = mLLClaimPubFunBL.getInsDate(mClmNo);

		LLClaimSchema tLLClaimSchema = mLLClaimPubFunBL.getLLClaim(mClmNo);
		if (tLLClaimSchema == null) {
			mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
			return false;
		}
		tLLClaimSchema.setConDealFlag("1");
		this.mMMap.put(tLLClaimSchema, "UPDATE");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0
		 * 对合同险种进行终止操作,P99撤销终止结论
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!mContStateReason.equals("P99")) {

			if (!getLLContState()) {
				return false;
			}

			if (!getLLContDeal()) {
				return false;
			}

		}

		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－险种终止处理－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 根据终止结论,对理赔的合同状态表进行操作
	 * 
	 * @return boolean
	 */
	private boolean getLLContState() {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0
		 * 查询出合同状态表,状态为Available的所有信息 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		mLCPolSchema = mLLClaimPubFunBL.getLCPol(mPolNo);
		if (mLCPolSchema == null) {
			mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
			return false;
		}

		if (mLCPolSchema.getAppFlag().equals("4")) {
			this.mErrors.addOneError("请注意:险种保单号:[" + this.mPolNo
					+ "]的状态已经终止,系统将不再生成终止记录!");
			return true;
		} else if (mLCPolSchema.getAppFlag().equals("9")) {
			this.mErrors.addOneError("请注意:险种保单号:[" + this.mPolNo
					+ "]的状态为抽档未核销,系统将不再做任何处理!");
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 根据合同终止结论对合同进行操作
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		LLContStateSchema tLLContStateSchema = null;
		LLContStateSet tLLContStateSet = new LLContStateSet();

		if (!mContStateReason.equals("P99")) {
			tLLContStateSchema = new LLContStateSchema();

			tLLContStateSchema.setClmNo(this.mClmNo);
			tLLContStateSchema.setContNo(mLCPolSchema.getContNo());
			tLLContStateSchema.setInsuredNo("000000");
			tLLContStateSchema.setPolNo(mLCPolSchema.getPolNo());
			tLLContStateSchema.setStateType("Terminate");
			tLLContStateSchema.setState("1");
			tLLContStateSchema.setStateReason("04");
			tLLContStateSchema.setStartDate(this.mInsDate);
			tLLContStateSchema.setEndDate("");
			tLLContStateSchema.setRemark("[" + mClmNo + "]--理赔单个险种终止处理");
			tLLContStateSchema.setOperator(mGlobalInput.Operator);
			tLLContStateSchema.setMakeDate(this.CurrentDate);
			tLLContStateSchema.setMakeTime(this.CurrentTime);
			tLLContStateSchema.setModifyDate(this.CurrentDate);
			tLLContStateSchema.setModifyTime(this.CurrentTime);
			tLLContStateSchema.setDealState(this.mContStateReason);
			tLLContStateSchema.setClmState("0"); // 0合同处理涉及的保单
		}

		tLLContStateSet.add(tLLContStateSchema);

		this.mMMap.put(tLLContStateSet, "DELETE&INSERT");

		return true;
	}

	/**
	 * 对险种进行退费处理
	 * 
	 * @param pLCPolSchema
	 * @return
	 */
	private boolean getLLContDeal() {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 P02--险种终止退全部保费
		 * P03--险种终止退现金价值
		 * 
		 * P05--险种终止退当期保费 P06--险种终止退当期保费
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.1 P01--险种终止
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (mContStateReason.equals("P01")) {
			if (!dealPolFee01(mLCPolSchema)) {
				return false;
			}
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.2 P02--险种终止退全部保费
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (mContStateReason.equals("P02")) {
			if (!dealPolFee02(mLCPolSchema)) {
				return false;
			}
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.3 P03--险种终止退现金价值
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (mContStateReason.equals("P03")) {
			if (!dealPolFee03(mLCPolSchema)) {
				return false;
			}
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.4 P05--险种终止退未满期保费
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (mContStateReason.equals("P05")) {
			if (!dealPolFee05(mLCPolSchema)) {
				return false;
			}
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.5 P06--险种终止退当期保费
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (mContStateReason.equals("P06")) {
			if (!dealPolFee06(mLCPolSchema)) {
				return false;
			}
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.1 退财务的应收费用信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		if (!getRecedeFee(mLCPolSchema)) {
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.2 计算终了红利
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
//		if (!dealFindBonus(mLCPolSchema)) {
//			return false;
//		}

		return true;
	}

	/**
	 * P01--险种终止
	 * 
	 * @return
	 */
	private boolean dealPolFee01(LCPolSchema tLCPolSchema) {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 退出险日期之后的保费
		 * DealType:处理方式,1-指定日期之后,2-指定日期之前 DealDate:处理时间,1-包括计算时点,2-不包括计算时点
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getLJAPayPerson(tLCPolSchema, mInsDate, "1", "2", "C01", "C0101",
				"TF")) {
			return false;
		}
		return true;
	}

	/**
	 * P02--险种终止退全部保费
	 * 
	 * @return
	 */
	private boolean dealPolFee02(LCPolSchema tLCPolSchema) {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0
		 * D01：合同终止退还保费[客户所有已交保费，含出险前后的已交保费]
		 * 
		 * 目前在保单结算功能中会计算退出险以后的保费,对应的业务类型编码为C01,C0101
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 退出险日期之后的保费
		 * DealType:处理方式,1-指定日期之后,2-指定日期之前 DealDate:处理时间,1-包括计算时点,2-不包括计算时点
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getLJAPayPerson(tLCPolSchema, mInsDate, "1", "2", "D01", "D0102",
				"TF")) {
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 退出险日期之前的保费
		 * DealType:处理方式,1-指定日期之后,2-指定日期之前 DealDate:处理时间,1-包括计算时点,其它不包括计算时点
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getLJAPayPerson(tLCPolSchema, mInsDate, "2", "1", "D01", "D0101",
				"TF")) {
			return false;
		}

		return true;
	}

	/**
	 * P03--险种终止退现金价值
	 * 
	 * @return
	 */
	private boolean dealPolFee03(LCPolSchema tLCPolSchema) {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 对附加险长期险退现金价值
		 * 
		 * 11.长期附加险应退现金价值,277,280,332
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tRiskCode = StrTool.cTrim(tLCPolSchema.getRiskCode());

		LMRiskAppSchema tLMRiskAppSchema = mLLClaimPubFunBL
				.getLMRiskApp(tRiskCode);
		String tRiskType = StrTool.cTrim(tLMRiskAppSchema.getSubRiskFlag());

		if (tRiskType.equals("S")
				&& !mLLClaimPubFunBL.getLMRiskSort(tRiskCode, "11")) {
			return true;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 D02：基本保额应退金额[现金价值]
		 * D0201：退出险日期以前的保费 调用保全公式 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		double tCal = mLLClaimPubFunBL.getCashValue(tLCPolSchema.getPolNo(),
				tRiskCode, this.mInsDate);

		if (tCal == -1) {
			this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
			return false;
		} else {
			LLBalanceSchema tLLBalanceSchema = setLLBalance("D02", "D0201",
					"TB", tCal, tLCPolSchema);
			this.mMMap.put(tLLBalanceSchema, "DELETE&INSERT");
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 退出险日期之后的保费
		 * DealType:处理方式,1-指定日期之后,2-指定日期之前 DealDate:处理时间,1-包括计算时点,2-不包括计算时点
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getLJAPayPerson(tLCPolSchema, mInsDate, "1", "2", "C01", "C0101",
				"TF")) {
			return false;
		}

		return true;
	}

	/**
	 * P05--险种终止退未满期保费
	 * 
	 * @return
	 */
	private boolean dealPolFee05(LCPolSchema tLCPolSchema) {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0
		 * 取出保单所在险种,进行险种判断,是否需要退未满期保费
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		String tRiskCode = StrTool.cTrim(tLCPolSchema.getRiskCode());

		if (!mLLClaimPubFunBL.getPolNoAddPay(this.mClmNo, "", tLCPolSchema)) {
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.1 调用保全公式
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			double tCal = mLLClaimPubFunBL.getCashValue(
					tLCPolSchema.getPolNo(), tRiskCode, this.mInsDate);

			if (tCal == -1) {
				logger.debug("------------------------------------------------------");
				logger.debug("--LLClaimContPolDealBL.dealPolFee05()--附加险退还未满期保费发生错误!"
								+ mLLClaimPubFunBL.mErrors.needDealError());
				logger.debug("------------------------------------------------------");
				this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
				return false;
			} else {
				LLBalanceSchema tLLBalanceSchema = setLLBalance("D05", "D0501",
						"TF", tCal, tLCPolSchema);
				this.mMMap.put(tLLBalanceSchema, "DELETE&INSERT");
			}
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 退出险日期之后的保费
		 * DealType:处理方式,1-指定日期之后,2-指定日期之前 DealDate:处理时间,1-包括计算时点,2-不包括计算时点
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getLJAPayPerson(tLCPolSchema, mInsDate, "1", "2", "C01", "C0101",
				"TF")) {
			return false;
		}

		return true;
	}

	/**
	 * P06--险种终止退当期保费
	 * 
	 * @return
	 */
	private boolean dealPolFee06(LCPolSchema tLCPolSchema) {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 取出保费项表的数据,对保费进行累加
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		double tCal = 0;
		String tRiskCode = StrTool.cTrim(tLCPolSchema.getRiskCode());
		String tPolNo = StrTool.cTrim(tLCPolSchema.getPolNo());
		LCPremDB tLCPremDB = new LCPremDB();
		tLCPremDB.setPolNo(tPolNo);
		LCPremSet tLCPremSet = tLCPremDB.query();

		for (int i = 1; i <= tLCPremSet.size(); i++) {
			double tPrem = tLCPremSet.get(i).getPrem();
			tCal = tCal + tPrem;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 添加退当期保费数据
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLBalanceSchema tLLBalanceSchema = setLLBalance("D06", "D0601", "TF",
				tCal, tLCPolSchema);
		this.mMMap.put(tLLBalanceSchema, "DELETE&INSERT");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 退出险日期之后的保费
		 * DealType:处理方式,1-指定日期之后,2-指定日期之前 DealDate:处理时间,1-包括计算时点,2-不包括计算时点
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getLJAPayPerson(tLCPolSchema, mInsDate, "1", "2", "C01", "C0101",
				"TF")) {
			return false;
		}

		return true;
	}

	/**
	 * 进行个人实收表退费处理
	 * 
	 * @param tLLExemptSchema
	 * @return
	 */
	private boolean getLJAPayPerson(LCPolSchema pLCPolSchema, String pDate,
			String pDealType, String pDealDate, String pFeeType,
			String pSubFeeType, String pFinaType) {

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("LCPolSchema", pLCPolSchema);

		tTransferData.setNameAndValue("ClmNo", mClmNo); // 赔案号
		tTransferData.setNameAndValue("Date", pDate); // 指定日期
		tTransferData.setNameAndValue("DealType", pDealType); // 处理方式,1-指定日期之后,2-指定日期之前
		tTransferData.setNameAndValue("DealDate", pDealDate); // 处理时间,1-包括计算时点,其它不包括计算时点
		tTransferData.setNameAndValue("Operator", this.mGlobalInput.Operator); // 操作人员

		tTransferData.setNameAndValue("FeeType", pFeeType); // 业务类型
		tTransferData.setNameAndValue("SubFeeType", pSubFeeType);// 子业务类型
		tTransferData.setNameAndValue("FinaType", pFinaType); // 财务类型

		LLBalanceSchema tLLBalanceSchema = mLLClaimPubFunBL
				.getLJAPayPerson(tTransferData);

		if (tLLBalanceSchema == null) {
			mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
		} else {
			mMMap.put(tLLBalanceSchema, "DELETE&INSERT");
		}

		return true;
	}

	/**
	 * 计算终了红利
	 * 
	 * @return
	 */
	private boolean dealFindBonus(LCPolSchema tLCPolSchema) {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		double tCal = 0;

		if (mLLClaimPubFunBL.getBonus(tLCPolSchema).equals("Y")) {
			tCal = mLLClaimPubFunBL.getFinalBonus(tLCPolSchema.getPolNo(),
					tLCPolSchema.getRiskCode(), this.mInsDate);

			if (tCal == -1) {
				this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
				return false;
			} else {
				LLBalanceSchema tLLBalanceSchema = setLLBalance("C05", "C0502",
						"EF", tCal, tLCPolSchema);
				this.mMMap.put(tLLBalanceSchema, "DELETE&INSERT");
			}
		}

		return true;
	}

	/**
	 * 退财务的应收记录
	 * 
	 * @param tLCPolSchema
	 * @return
	 */
	private boolean getRecedeFee(LCPolSchema tLCPolSchema) {
		logger.debug("----------退财务的应收记录-----getRecedeFee-----开始----------");

		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ClmNo", this.mClmNo); // 赔案号
		tTransferData.setNameAndValue("CalDate", this.mInsDate); // 计算时点
		tTransferData.setNameAndValue("DealDate", "2"); // 处理时间,1-包括计算时点,2不包括计算时点
		tTransferData.setNameAndValue("DealType", "1"); // 处理方式,1-指定日期之后,2-指定日期之前
		tTransferData.setNameAndValue("DealMode", "2"); // 处理模式,1-直接删除,2-不删除
		tVData.add(tTransferData);
		tVData.add(tLCPolSchema);

		LLBalRecedeFeeBL tLLBalRecedeFeeBL = new LLBalRecedeFeeBL();
		if (tLLBalRecedeFeeBL.submitData(tVData, mOperate) == false) {
			this.mErrors.copyAllErrors(tLLBalRecedeFeeBL.mErrors);
			return false;
		} else {
			VData tempVData = tLLBalRecedeFeeBL.getResult();
			MMap tMMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
			this.mMMap.add(tMMap);
		}

		logger.debug("----------处理出险时点[包括该时点]之前的应收数据----------");
		tVData = new VData();
		tTransferData = new TransferData();
		tTransferData.setNameAndValue("ClmNo", this.mClmNo); // 赔案号
		tTransferData.setNameAndValue("CalDate", this.mInsDate); // 计算时点
		tTransferData.setNameAndValue("DealDate", "1"); // 处理时间,1-包括计算时点,2不包括计算时点
		tTransferData.setNameAndValue("DealType", "2"); // 处理方式,1-指定日期之后,2-指定日期之前
		tTransferData.setNameAndValue("DealMode", "2"); // 处理模式,1-直接删除,2-不删除
		tVData.add(tTransferData);
		tVData.add(tLCPolSchema);

		tLLBalRecedeFeeBL = new LLBalRecedeFeeBL();
		if (tLLBalRecedeFeeBL.submitData(tVData, mOperate) == false) {
			this.mErrors.copyAllErrors(tLLBalRecedeFeeBL.mErrors);
			return false;
		} else {
			VData tempVData = tLLBalRecedeFeeBL.getResult();
			MMap tMMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
			this.mMMap.add(tMMap);
		}

		logger.debug("----------退财务的应收记录-----getRecedeFee-----结束----------");
		return true;
	}

	/**
	 * 目的:收回已经给客户的--附加险退还未满期保费 判断本次赔案是否有,9.附加险退未满期保费[225,264]的记录
	 * 如果有,判断已经结案的赔案是否有过该记录,如果有,退费,存负值
	 */
	private boolean getRSRiskRecedeFee(LCPolSchema pLCPolSchema) {

		logger.debug("--------------------------------收回已经退给客户的附加险未满期保费-----开始--------------------------------");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 定义变量
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tPolNo = StrTool.cTrim(pLCPolSchema.getPolNo());
		String tRiskCode = StrTool.cTrim(pLCPolSchema.getRiskCode());

		if (mLLClaimPubFunBL.getLMRiskSort(tRiskCode, "9")) {
			double tCal = mLLClaimPubFunBL.getPolClmBalPay(pLCPolSchema,
					mClmNo, "D0501");

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0 插入新的数据
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			if (tCal == -1) {
				this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
				return false;
			} else if (tCal > 0) {
				LLBalanceSchema tLLBalanceSchema = setLLBalance("D05", "D0501",
						"TF", -tCal, pLCPolSchema);
				this.mMMap.put(tLLBalanceSchema, "DELETE&INSERT");
			}

		}

		logger.debug("--------------------------------收回已经退给客户的附加险未满期保费-----结束--------------------------------");
		return true;
	}

	/**
	 * 设置结算信息
	 * 
	 * @param pFeeType:业务类型
	 * @param pSubFeeType:子业务类型
	 * @param pFeeFinaType:财务类型
	 * @param pCalValue:计算金额
	 * @param pLCPolSchema:
	 * @return
	 */
	private LLBalanceSchema setLLBalance(String pFeeType, String pSubFeeType,
			String pFeeFinaType, double pCalValue, LCPolSchema pLCPolSchema) {

		LLBalanceSchema tLLBalanceSchema = new LLBalanceSchema();
		tLLBalanceSchema.setClmNo(this.mClmNo);
		tLLBalanceSchema.setFeeOperationType(pFeeType); // 业务类型
		tLLBalanceSchema.setSubFeeOperationType(pSubFeeType); // 子业务类型
		tLLBalanceSchema.setFeeFinaType(pFeeFinaType); // 财务类型

		tLLBalanceSchema.setBatNo("0"); // 批次号
		tLLBalanceSchema.setOtherNo("0"); // 其它号码
		tLLBalanceSchema.setOtherNoType("0"); // 财务类型

		tLLBalanceSchema.setGrpContNo(pLCPolSchema.getGrpContNo()); // 集体合同号码
		tLLBalanceSchema.setContNo(pLCPolSchema.getContNo()); // 合同号码
		tLLBalanceSchema.setGrpPolNo(pLCPolSchema.getGrpPolNo()); // 集体保单号码
		tLLBalanceSchema.setPolNo(pLCPolSchema.getPolNo()); // 保单号码

		tLLBalanceSchema.setDutyCode("0"); // 责任编码
		tLLBalanceSchema.setGetDutyKind("0"); // 给付责任类型
		tLLBalanceSchema.setGetDutyCode("0"); // 给付责任编码

		tLLBalanceSchema.setKindCode(pLCPolSchema.getKindCode()); // 险类编码
		tLLBalanceSchema.setRiskCode(pLCPolSchema.getRiskCode()); // 险种编码
		tLLBalanceSchema.setRiskVersion(pLCPolSchema.getRiskVersion()); // 险种版本

		tLLBalanceSchema.setSaleChnl(pLCPolSchema.getSaleChnl()); // 销售渠道
		tLLBalanceSchema.setAgentCode(pLCPolSchema.getAgentCode()); // 代理人编码
		tLLBalanceSchema.setAgentGroup(pLCPolSchema.getAgentGroup()); // 代理人组别

		tLLBalanceSchema.setGetDate(this.CurrentDate); // 给付日期
		tLLBalanceSchema.setPay(pCalValue); // 赔付金额

		tLLBalanceSchema.setPayFlag("0"); // 支付标志,0未支付,1已支付
		tLLBalanceSchema.setState("0"); // 状态,0有效
		tLLBalanceSchema.setDealFlag("0"); // 处理标志,0未处理

		tLLBalanceSchema.setManageCom(pLCPolSchema.getManageCom()); // 管理机构
		tLLBalanceSchema.setAgentCom(pLCPolSchema.getAgentCom()); // 代理机构
		tLLBalanceSchema.setAgentType(pLCPolSchema.getAgentType()); // 代理机构内部分类

		tLLBalanceSchema.setOperator(mGlobalInput.Operator); // 操作员
		tLLBalanceSchema.setMakeDate(this.CurrentDate); // 入机日期
		tLLBalanceSchema.setMakeTime(this.CurrentTime); // 入机时间
		tLLBalanceSchema.setModifyDate(this.CurrentDate); // 入机日期
		tLLBalanceSchema.setModifyTime(this.CurrentTime); // 入机时间
		
		tLLBalanceSchema.setCustomerNo(pLCPolSchema.getInsuredNo());

		tLLBalanceSchema.setOriPay(pCalValue); // 原始金额

		return tLLBalanceSchema;

	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－险种终止处理－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
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
			tError.moduleName = "LLClaimContDealBLF";
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

		String tClaimFeeCode = "abcdefg";
		tClaimFeeCode = tClaimFeeCode.substring(1);

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86000000";
		tG.ComCode = "86110000";

		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("ClmNo", "90000003621");
		// tTransferData.setNameAndValue("CustNo","");
		tTransferData.setNameAndValue("ConType", "1");
		tTransferData.setNameAndValue("ContNo", "230110000003347");
		tTransferData.setNameAndValue("PolNo", "210110000006186");
		tTransferData.setNameAndValue("ContStateReason", "P03");

		VData tVData = new VData();
		tVData.addElement(tG);
		tVData.addElement(tTransferData);

		LLClaimContPolDealBL tLLClaimContPolDealBL = new LLClaimContPolDealBL();

		if (tLLClaimContPolDealBL.submitData(tVData, "") == false) {
			logger.debug("-------false-------");
		}

		int n = tLLClaimContPolDealBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLClaimContPolDealBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}

}

package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.sinosoft.lis.acc.DealInsuAccPrice;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description: 计算步骤二：找出匹配保项
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: SinoSoft Co. Ltd,
 * </p>
 * 
 * @author 续涛
 * @version 6.0
 */
public class LLClaimCalMatchBL {
private static Logger logger = Logger.getLogger(LLClaimCalMatchBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();
	private MMap mMMap = new MMap();
	/** 往后面传输的数据库操作 */

	// 立案表
	private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();

	// 立案份案信息
	private LLCaseSet mLLCaseSet = new LLCaseSet();
	private LLCaseSchema mLLCaseSchema = new LLCaseSchema();

	// 理赔类型
	private LLAppClaimReasonSet mLLAppClaimReasonSet = new LLAppClaimReasonSet();

	// 给付责任信息
	private LCGetSet mLCGetSet = new LCGetSet();
	/** 用于获取领取项数据的集合 */
	private LBGetSet mLBGetSet = new LBGetSet();
	/** 用于获取领取项数据的集合 */

	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LCDutySchema mLCDutySchema = new LCDutySchema();
	private LCGetSchema mLCGetSchema = new LCGetSchema();

	// 待匹配保项信息
	private LLToClaimDutySet mLLToClaimDutySet = new LLToClaimDutySet();
	private LLToClaimDutySchema mLLToClaimDutySchema = new LLToClaimDutySchema();
	private List mBomList = new ArrayList();

	private PrepareBOMClaimBL mPrepareBOMClaimBL = new PrepareBOMClaimBL();
	// 待匹配保项下费用信息
	private LLToClaimDutyFeeSchema mLLToClaimDutyFeeSchema;
	private LLToClaimDutyFeeSet mLLToClaimDutyFeeSet = new LLToClaimDutyFeeSet();

	// 保全批改项目
	private LPEdorItemSchema mLPEdorItemSchema = null;

	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
	private ExeSQL mExeSQL = new ExeSQL();

	private String mAccNo = ""; // 事件号
	private String mAccDate = ""; // 意外事故发生日期
	private String mInsDate = ""; // 出险时间
	private String mRgtDate = ""; // 立案时间
	private String mClmNo = ""; // 赔案号
	private String mContType = ""; // 总单类型,1-个人总投保单,2-集体总单
	private String mGetDutyKind; // 理赔类型
	private String mGetDutyCode; // 责任编码

	private String mMaxCalDate = null; // 费用类型的最大计算日期，就是费用明细的结束时间

	private String mNBPolNo = ""; // 承保时的保单号
	private LDExch mLDExch = new LDExch();

	public LLClaimCalMatchBL() {

	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------理算步骤二-----匹配保项-----LLClaimCalMatchBL测试-----开始----------");

		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("----------理算步骤二-----匹配保项-----LLClaimCalMatchBL测试-----结束----------");

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		this.mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		this.mAccNo = (String) mTransferData.getValueByName("AccNo"); // 事件号
		this.mAccDate = (String) mTransferData.getValueByName("AccDate"); // 意外事故发生日期
		this.mClmNo = (String) mTransferData.getValueByName("ClmNo"); // 赔案号
		this.mContType = (String) mTransferData.getValueByName("ContType"); // 总单类型,1-个人投保单,2-集体总投保单

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 得到匹配需要用到的计算要素信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getMatchFactor()) {
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 判断是否是连生险
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getMutiInsuredInfo()) {
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0
		 * 根据一个赔案下的出险人的个数得到相应的匹配数据 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		for (int i = 1; i <= mLLCaseSet.size(); i++) {
			mLLCaseSchema = mLLCaseSet.get(i);

			// 犹豫期内退保
			if (!getPosBack()) {
				return false;
			}

			if (!getMatchInfo()) {
				return false;
			}

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.0 打印出提示信息
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			logger.debug("======================================================================================================");
			for (int m = 1; m <= mLCGetSet.size(); m++) {
				String tContNo = StrTool.cTrim(mLCGetSet.get(m).getContNo());
				String tPolNo = StrTool.cTrim(mLCGetSet.get(m).getPolNo());
				String tDutyCode = StrTool
						.cTrim(mLCGetSet.get(m).getDutyCode());
				String tGetDutyKind = StrTool.cTrim(mLCGetSet.get(m)
						.getGetDutyKind());
				String tGetDutyCode = StrTool.cTrim(mLCGetSet.get(m)
						.getGetDutyCode());
				String tTBFlag = StrTool.cTrim(mLCGetSet.get(m).getState());
				String tDataS = StrTool.cTrim(mLCGetSet.get(m).getOperator());

				logger.debug("-----初步匹配的保项信息-----合同号:[" + tContNo
						+ "],险种号:[" + tPolNo + "],理赔类型:[" + tGetDutyKind
						+ "],责任:[" + tDutyCode + "],给付责任:[" + tGetDutyCode
						+ "],投保标志:[" + tTBFlag + "],数据来源:[" + tDataS + "]");
			}
			logger.debug("======================================================================================================");

		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.0 打印出提示信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		logger.debug("======================================================================================================");
		for (int i = 1; i <= mLLToClaimDutySet.size(); i++) {
			String tContNo = StrTool
					.cTrim(mLLToClaimDutySet.get(i).getContNo());
			String tPolNo = StrTool.cTrim(mLLToClaimDutySet.get(i).getPolNo());
			String tDutyCode = StrTool.cTrim(mLLToClaimDutySet.get(i)
					.getDutyCode());
			String tGetDutyKind = StrTool.cTrim(mLLToClaimDutySet.get(i)
					.getGetDutyKind());
			String tGetDutyCode = StrTool.cTrim(mLLToClaimDutySet.get(i)
					.getGetDutyCode());
			String tTBFlag = StrTool.cTrim(mLLToClaimDutySet.get(i)
					.getCasePolType());

			logger.debug("-----匹配出的保项信息-----合同号:[" + tContNo + "],险种号:["
					+ tPolNo + "],理赔类型:[" + tGetDutyKind + "],责任:[" + tDutyCode
					+ "],给付责任:[" + tGetDutyCode + "],投保被保信息:[" + tTBFlag + "]");

		}
		logger.debug("======================================================================================================");

		return true;

	}

	/**
	 * 目的：得到匹配需要用到的计算要素信息表
	 * 
	 * @return
	 */
	private boolean getMatchFactor() {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0
		 * 从LLCaseReceipt表中得到医疗费用明细的最大结束日期
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
//		String tSql = "select max(enddate) from LLCaseReceipt where 1 = 1"
//				+ " and ClmNo = '" + this.mClmNo + "'";
//
//		ExeSQL tExeSQL = new ExeSQL();
//		SSRS tSSRS = tExeSQL.execSQL(tSql);
//
//		this.mMaxCalDate = tExeSQL.getOneValue(tSql);
//		if (this.mMaxCalDate == null || this.mMaxCalDate.length() == 0) {
//			this.mMaxCalDate = null;
//		} else {
//			this.mMaxCalDate = this.mMaxCalDate.substring(0, 10);
//		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
		 * 根据赔案号，从LLRegister表中取出立案登记信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		mLLRegisterSchema = mLLClaimPubFunBL.getLLRegister(mClmNo);

		if (mLLRegisterSchema == null) {
			mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0
		 * 根据赔案号，从LLCaseSet表中取出所有出险人信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		mLLCaseSet = mLLClaimPubFunBL.getLLCaseSet(mClmNo);

		if (mLLCaseSet == null) {
			mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0 根据赔案号，得到出险时间
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		mInsDate = mLLClaimPubFunBL.getInsDate(mClmNo);
		mRgtDate = mLLRegisterSchema.getRgtDate().substring(0, 10);
		return true;
	}

	/**
	 * 判断匹配后的保单中是否符合联生险的条件
	 * 
	 * @return
	 */
	private boolean getMutiInsuredInfo() {

		// 团体报案不用判断
		if (this.mContType.equals("2")) {
			return true;
		}

		// 出险人数量只有一个不用判断
		if (mLLCaseSet.size() == 1) {
			return true;
		}

		// 出险人数量大于2个时返回
		if (mLLCaseSet.size() > 2) {
			this.mErrors.addOneError("个险下的出险人不能超过2个,不能进行责任匹配!!!");
			return false;
		}

		String tPer1 = "";
		String tPer2 = "";
		if (mLLCaseSet.size() == 2) {
			tPer1 = mLLCaseSet.get(1).getCustomerNo();
			tPer2 = mLLCaseSet.get(2).getCustomerNo();

			String tSql1 = "select * from LCInsuredRelated where 1=1 "
					+ " and (CustomerNo in ('" + "?tPer1?" + "')"
					+ " or MainCustomerNo in ('" + "?tPer2?" + "') )";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(tSql1);
			sqlbv.put("tPer1", tPer1);
			sqlbv.put("tPer2", tPer2);
			LCInsuredRelatedDB tLCInsuredRelatedDB1 = new LCInsuredRelatedDB();
			LCInsuredRelatedSet tLCInsuredRelatedSet1 = tLCInsuredRelatedDB1
					.executeQuery(sqlbv);

			if (tLCInsuredRelatedDB1.mErrors.needDealError()) {
				this.mErrors
						.addOneError("查询连带被保人信息失败,不能进行责任匹配!"
								+ tLCInsuredRelatedDB1.mErrors.getError(0).errorMessage);
				return false;
			}

			String tSql2 = "select * from LCInsuredRelated where 1=1 "
					+ " and (CustomerNo in ('" + "?tPer2?" + "')"
					+ " or MainCustomerNo in ('" + "?tPer1?" + "') )";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSql2);
			sqlbv1.put("tPer1", tPer1);
			sqlbv1.put("tPer2", tPer2);
			
			LCInsuredRelatedDB tLCInsuredRelatedDB2 = new LCInsuredRelatedDB();
			LCInsuredRelatedSet tLCInsuredRelatedSet2 = tLCInsuredRelatedDB2
					.executeQuery(sqlbv1);

			if (tLCInsuredRelatedDB2.mErrors.needDealError()) {
				this.mErrors
						.addOneError("查询连带被保人信息失败,不能进行责任匹配!"
								+ tLCInsuredRelatedDB2.mErrors.getError(0).errorMessage);
				return false;
			}

			if (tLCInsuredRelatedSet1.size() == 0
					&& tLCInsuredRelatedSet2.size() == 0) {
				this.mErrors.addOneError("该赔案下的2个出险人没有在同一张保单上,不能进行责任匹配!");

				return false;
			}
		}

		return true;
	}

	/**
	 * 犹豫期内退保
	 * 
	 * @return
	 */
	private boolean getPosBack() {
		String tSql = "";
		String tMsg = "";
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No11.0 给出犹豫期内退保的提示信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		tSql = "select LCCont.* from lccont,LPEdorItem where 1 = 1"
				+ " and LCCont.ContNo=LPEdorItem.ContNo "
				+ " and LPEdorItem.EdorType in ('CT')" // 退报
				+ " and LPEdorItem.StandByFlag1 = '1' " // 犹豫内退保
				+ " and LPEdorItem.EdorValiDate <= to_date('" + "?insdate?"
				+ "','yyyy-mm-dd') "
				+ " and LPEdorItem.EdorValiDate >= to_date('" + "?insdate?"
				+ "','yyyy-mm-dd') " + " and ( LCCont.AppntNo    = '"
				+ "?CustomerNo?" + "' "
				+ " or LCCont.InsuredNo = '" + "?CustomerNo?"
				+ "' )";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("insdate", this.mInsDate);
		sqlbv2.put("CustomerNo", mLLCaseSchema.getCustomerNo());
		logger.debug("-----犹豫期退保的Sql语句-----" + tSql);
		LCContDB tLCContDB = new LCContDB();
		LCContSet tLCContSet = tLCContDB.executeQuery(sqlbv2);
		if (tLCContDB.mErrors.needDealError()) {
			mErrors.addOneError("出险人查询在犹豫退保内的情况发生错误!!!"
					+ tLCContDB.mErrors.getError(0).errorMessage);
			return false;
		}

		tMsg = "合同号:";
		for (int i = 1; i <= tLCContSet.size(); i++) {
			LCContSchema tLCContSchema = tLCContSet.get(i);
			tMsg = tMsg + "[" + tLCContSchema.getContNo() + "]";
		}

		if (tLCContSet.size() > 0) {
			mErrors.addOneError(tMsg + "在犹豫期内退保!!!");
		}

		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－进行匹配－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 得到单个出险人的保项、费用的匹配信息
	 * 
	 * @return
	 */
	public boolean getMatchInfo() {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 根据出险人获取理赔类型信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getLLAppClaimReason()) {
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 根据出险人得到C表所有的保项--领取项表
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (!getLCLBGet()) {
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0 循环立案记录的该出险人的所有理赔类型，
		 * 在lmdutygetclm【责任给付赔付】找出该理赔类型下的给付代码
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		for (int i = 1; i <= mLLAppClaimReasonSet.size(); i++) {
			String tGetDutyKind = mLLAppClaimReasonSet.get(i).getReasonCode();// 理赔类型

			logger.debug("\n============================开始========执行理赔类型为========["
							+ tGetDutyKind
							+ "]=====的匹配算法============================\n");

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.1 循环取得LCGet的所有保项,
			 * 将符合条件的保项添加到mLCGetSet集合中
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			for (int j = 1; j <= mLCGetSet.size(); j++) {

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0 定义变量
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				mLCGetSchema = mLCGetSet.get(j);
				mLCGetSchema.setGetDutyKind(tGetDutyKind);

				String tContNo = StrTool.cTrim(mLCGetSchema.getContNo()); // 合同号
				String tPolNo = StrTool.cTrim(mLCGetSchema.getPolNo()); // 险种号
				String tDutyCode = StrTool.cTrim(mLCGetSchema.getDutyCode()); // 责任编码
				String tGetDutyCode = StrTool.cTrim(mLCGetSchema
						.getGetDutyCode());// 给付责任编码
				String tCaseType = StrTool.cTrim(mLCGetSchema.getState()); // 给付类型,00-
																			// 投保人,01-被保人
				String tPolType = StrTool.cTrim(mLCGetSchema.getOperator()); // 保单类型：C表保单，B表保单,A承保出单前出险

				String tMssage = "";
				String tSql = "";

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.1 进行产品的匹配校验工作
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				logger.debug("\n=========================================开始执行第["
								+ j
								+ "]个给付责任算法=========================================\n");

				LMDutyGetClmSchema tLMDutyGetClmSchema = mLLClaimPubFunBL
						.getLMDutyGetClm(tGetDutyKind, tGetDutyCode);

				if (tLMDutyGetClmSchema == null) {
					logger.debug("--匹配前--产品无定义将被过滤掉,理赔类型" + "["
							+ tGetDutyKind + "],给付责任[" + tGetDutyCode + "]");
					mLLClaimPubFunBL.mErrors.clearErrors();
					continue;
				}

				tMssage = "" + j + "/" + mLCGetSet.size() + "保项,合同号:["
						+ tContNo + "],险种号:[" + tPolNo + "],责任:[" + tDutyCode
						+ "],责任编码:[" + tGetDutyCode + "],投保标志:[" + tCaseType
						+ "];取自:[" + tPolType + "]表.";
				String tMssageSql = tMssage;

				logger.debug("--------------------------------------------------------------------------------------------------");
				logger.debug("\n--匹配过程一,判断条件前：[" + tMssage + "]\n");
				logger.debug("--------------------------------------------------------------------------------------------------");

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.3
				 * 判断LCGet表的责任终止标志,GetEndState = 0 有效,GetEndState = 1终止
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				String tGetEndState = StrTool.cTrim(mLCGetSchema
						.getGetEndState());
				if (tGetEndState.equals("1")) {
					logger.debug("--------------------------------------------------------------------------------------------------");
					logger.debug("-----LCGet保险责任被终止,将被过滤掉-----\n"
							+ tMssage);
					logger.debug("--------------------------------------------------------------------------------------------------");
					continue;
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.4
				 * LCGet表的该字段属于借用，保单类型,C表保单，B表保单,A承保出单前出险 承保出险,出险原因为疾病,不匹配，过滤掉
				 * 2009-01-09 zhangzheng 只要是承保前出险都要被过滤掉!
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				if (tPolType.equals("A")
						//&& tGetDutyKind.substring(0, 1).equals("2")
						) {
					logger.debug("--------------------------------------------------------------------------------------------------");
					logger.debug("-----承保前疾病出险,将被过滤掉-----\n" + tMssage);
					logger.debug("--------------------------------------------------------------------------------------------------");
					continue;
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.7
				 * 执行过滤算法getFilterMatch()--校验投保人，被保人的情况 如果没有通过，则不符合匹配条件,继续循环
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				logger.debug("\n--------------------开始---------------保险责任进行投保被保人判断--------------------\n");
				if (!getFilterMatch(tLMDutyGetClmSchema, mLCGetSchema)) {
					logger.debug("--------------------------------------------------------------------------------------------------");
					logger.debug("-----保险责任进行投保被保人判断时不符合条件,将被过滤掉-----\n"
							+ tMssage);
					logger.debug("--------------------------------------------------------------------------------------------------");
					logger.debug("\n--------------------结束---------------保险责任进行投保被保人判断--------------------\n");
					continue;
				}
				logger.debug("\n--------------------结束---------------保险责任进行投保被保人判断--------------------\n");

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.8 得到保单的全局信息
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				mLCPolSchema = mLLClaimPubFunBL.getLCPol(tPolNo);
				if (mLCPolSchema == null) {
					mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
					return false;
				}

				String tMainPolNo = StrTool.cTrim(mLCPolSchema.getMainPolNo()); // 主险保单号
				String tRiskCode = StrTool.cTrim(mLCPolSchema.getRiskCode()); // 险种编码

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.9 得到责任的全局信息
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				mLCDutySchema = mLLClaimPubFunBL.getLCDuty(tPolNo, tDutyCode);

				if (mLCDutySchema == null) {
					mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
					return false;
				}
				String tPrint = "合同号:[" + tContNo + "],险种号:[" + tPolNo + "]";

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.1
				 * 获取出险时点以后的指定保全项目 1-[加保AA] 2-[减保PT] 3-[退保CT] 4-[减额缴清PU]
				 * 5-[附加特约的终止操作DT] 6-[投保人变更AC]
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				this.mNBPolNo = getNBPolNo(this.mLCPolSchema); // 得到承保时的保单号
				mLPEdorItemSchema = mLLClaimPubFunBL.getLPEdorItemAfter(
						tContNo, mNBPolNo, this.mInsDate,
						"AA','PT','CT','PU','DT','AC");// 得到保全的批单号

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.2
				 * 执行过滤算法getFilterCalCode()-- 如果没有通过，则不符合匹配条件,继续循环
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				// logger.debug("\n--------------------开始---------------产品定义过滤算法判断--------------------\n");
				// String tCalCode = tLMDutyGetClmSchema.getFilterCalCode();
				// if( !getFilterCalCode(tCalCode) )
				// {
				// logger.debug("--------------------------------------------------------------------------------------------------");
				// logger.debug("-----保险责任不符合产品定义的过滤条件,将被过滤掉-----\n"+tMssage);
				// logger.debug("--------------------------------------------------------------------------------------------------");
				// logger.debug("\n--------------------结束---------------产品定义过滤算法判断--------------------\n");
				// /*mErrors.addOneError("保险责任不符合产品定义条件,将被过滤掉!"+tMssage);*/
				// continue;
				// }
				logger.debug("\n--------------------结束---------------产品定义过滤算法判断--------------------\n");

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.3
				 * 执行过滤算法getFilterGetDutyCode()--清除重复的责任信息 如果没有通过，则不符合匹配条件,继续循环
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				if (!getFilterGetDutyCode(mLCGetSchema, mLLToClaimDutySet)) {
					logger.debug("--------------------------------------------------------------------------------------------------");
					logger.debug("-----两个出险人的保险责任相同,将被过滤掉一个责任-----\n"
							+ tMssage);
					logger.debug("--------------------------------------------------------------------------------------------------");

					/* mErrors.addOneError("保险责任进行投保被保人判断时不符合条件,将被过滤掉!"+tMssage); */
					continue;
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.1 判断合同是否被挂起
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				LCContHangUpBL tLCContHangUpBL = new LCContHangUpBL(
						mGlobalInput, mClmNo, "4", tContNo);

				if (!tLCContHangUpBL.queryHungUpForContNo()) {
					//mErrors.copyAllErrors(tLCContHangUpBL.mErrors);
					mErrors.addOneError("合同!"
							+ tContNo+"已被挂起,被过滤!");
					continue;
				}

				// /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				// * No7.2 LCContState表的终止标志如果为"1",代表该合同现被终止掉,不再匹配
				// * Terminate:1为终止,04：代表理赔终止
				// * 目前处理只要出险时点合同有效即可，
				// * 张领导强烈要求加入：主险如果在出险时点后被理赔终止，则被过滤掉
				// *
				// * 出险时点后，查询是否有被理赔终止的保单,
				// * 事故日期：2005-04-01，理赔终止：2005-09-04
				// * 如果是主险，不可以理赔；如果是附加险，可以理赔
				// * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				// */
				// //2006-3-31应399赔付要求，续涛和李婷婷沟通结果，去掉此处校验
				if (tPolNo.equals(tMainPolNo)) {
					tSql = "select * from LCContState where 1=1 "
							+ " and ContNo = '" + "?ContNo?" + "'"
							+ " and PolNo  = '" + "?PolNo?" + "'"
							+ " and StateType = 'Terminate'"
							+ " and State = '1'" + " and StateReason ='04'"
							+ " and lccontstate.StartDate >= to_date('"
							+ "?insdate?" + "','yyyy-mm-dd') ";
					SQLwithBindVariables sqlbv3 =new SQLwithBindVariables();
					sqlbv3.sql(tSql);
					sqlbv3.put("ContNo", tContNo);
					sqlbv3.put("PolNo", tPolNo);
					sqlbv3.put("insdate", this.mInsDate);
					LCContStateDB tLPLCContStateDB = new LCContStateDB();
					LCContStateSet tLPLCContStateSet = tLPLCContStateDB
							.executeQuery(sqlbv3);
					if (tLPLCContStateDB.mErrors.needDealError()) {
						mErrors
								.addOneError("得到主险出险时点在[理赔合同状态终止]之前状态失败!"
										+ tLPLCContStateDB.mErrors.getError(0).errorMessage);
						return false;
					}

					if (tLPLCContStateSet.size() > 0) {
						logger.debug("--------------------------------------------------------------------------------------------------");
						logger.debug("-----主险出险时点在[理赔合同状态终止]之前,将被过滤掉-----\n"
										+ tMssage);
						logger.debug("--------------------------------------------------------------------------------------------------");
						/* mErrors.addOneError("合同号["+tLPLCContStateSet.get(1).getContNo()+"],保单险种号["+tLPLCContStateSet.get(1).getPolNo()+"],出险时点在[理赔合同终止"+tLPLCContStateSet.get(1).getStartDate()+"]之前,将不参与理算!"); */
						continue;
					}
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.3
				 * LCContState表的终止标志如果为"1",代表该合同现被终止掉,不再匹配 Terminate:1为终止
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */

				tSql = "select * from LCContState where 1=1 "
						+ " and ContNo = '" + "?ContNo?" + "'" + " and PolNo  = '"
						+ "?PolNo?" + "'" + " and StateType = 'Terminate'"
						+ " and State = '1'"
						+ " and lccontstate.StartDate <= to_date('"
						+ "?insdate?" + "','yyyy-mm-dd') "
						+ " and (lccontstate.enddate  >= to_date('"
						+ "?insdate?"
						+ "','yyyy-mm-dd') or lccontstate.enddate is null )";
				SQLwithBindVariables sqlbv3 =new SQLwithBindVariables();
				sqlbv3.sql(tSql);
				sqlbv3.put("ContNo", tContNo);
				sqlbv3.put("PolNo", tPolNo);
				sqlbv3.put("insdate", this.mInsDate);
				LCContStateDB tLCContStateDB = new LCContStateDB();
				LCContStateSet tLCContStateSet = tLCContStateDB
						.executeQuery(sqlbv3);
				if (tLCContStateDB.mErrors.needDealError()) {
					mErrors.addOneError("得到合同终止状态失败!"
							+ tLCContStateDB.mErrors.getError(0).errorMessage);
					return false;
				}

				if (tLCContStateSet.size() > 0) {
					logger.debug("--------------------------------------------------------------------------------------------------");
					logger.debug("-----SQL:" + tSql);
					logger.debug("-----合同为终止状态,将被过滤掉-----\n" + tMssage);
					logger.debug("--------------------------------------------------------------------------------------------------");

					/* mErrors.addOneError("合同状态为终止,将被过滤掉!"+tMssage); */
					continue;
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.4
				 * LCContState表的有效标志如果为"1",代表该合同现已失效,不再匹配 Available: 0-有效 1-失效
				 * （险种状态） －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */

				tSql = "select * from LCContState where 1=1 "
						+ " and ContNo = '" + "?ContNo?" + "'" + " and PolNo  = '"
						+ "?PolNo?" + "'" + " and StateType = 'Available'"
						+ " and State = '1'"
						+ " and lccontstate.StartDate <= to_date('"
						+ "?insdate?" + "','yyyy-mm-dd') "
						+ " and (lccontstate.enddate  >= to_date('"
						+ "?insdate?"
						+ "','yyyy-mm-dd') or lccontstate.enddate is null )";
				SQLwithBindVariables sqlbv4 =new SQLwithBindVariables();
				sqlbv4.sql(tSql);
				sqlbv4.put("ContNo", tContNo);
				sqlbv4.put("PolNo", tPolNo);
				sqlbv4.put("insdate", this.mInsDate);
				tLCContStateDB = new LCContStateDB();
				tLCContStateSet = tLCContStateDB.executeQuery(sqlbv4);
				if (tLCContStateDB.mErrors.needDealError()) {
					mErrors.addOneError("得到合同有效状态失败!"
							+ tLCContStateDB.mErrors.getError(0).errorMessage);
					return false;
				}

				if (tLCContStateSet.size() > 0) {
					logger.debug("--------------------------------------------------------------------------------------------------");
					logger.debug("-----SQL:" + tSql);
					logger.debug("-----合同状态为失效,将被过滤掉-----\n" + tMssage);
					logger.debug("--------------------------------------------------------------------------------------------------");

					/* mErrors.addOneError("合同状态为失效,将被过滤掉!"+tMssage); */
					continue;
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.5
				 * LCContState表的标志 BankLoan: 0-未质押银行贷款 1-质押银行贷款 （险种状态和保单状态）
				 * RPU：0-未减额缴清 1-减额缴清 （险种状态）
				 * 
				 * 修改1: 判断日期从事故日期修改为当前日期,因为质押贷款的状态特殊;但会引起一个
				 * 问题是如果事故日期时未做贷款而现时点却处在贷款期间则需要业务来约定也要 先作清偿。 by zhoulei
				 * 2006-04-19 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */

				tSql = "select * from LCContState where 1=1 "
						+ " and ContNo = '" + "?ContNo?" + "'"
						+ " and (PolNo = '000000' or PolNo = '" + "?PolNo?" + "')"
						+ " and StateType = 'BankLoan'" + " and State = '1'"
						+ " and lccontstate.StartDate <= to_date('"
						+ "?insdate?" + "','yyyy-mm-dd') "
						+ " and (lccontstate.enddate  >= to_date('"
						+ "?insdate?"
						+ "','yyyy-mm-dd') or lccontstate.enddate is null )";
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql(tSql);
				sqlbv5.put("ContNo", tContNo);
				sqlbv5.put("PolNo", tPolNo);
				sqlbv5.put("insdate", this.mInsDate);
				tLCContStateDB = new LCContStateDB();
				tLCContStateSet = tLCContStateDB.executeQuery(sqlbv5);
				if (tLCContStateDB.mErrors.needDealError()) {
					mErrors.addOneError("得到合同处于质押银行贷款状态失败!"
							+ tLCContStateDB.mErrors.getError(0).errorMessage);
					return false;
				}

				if (tLCContStateSet.size() > 0) {
					logger.debug("--------------------------------------------------------------------------------------------------");
					logger.debug("-----SQL:" + tSql);
					logger.debug("-----合同状态为质押银行贷款,将被过滤掉-----\n"
							+ tMssage);
					logger.debug("--------------------------------------------------------------------------------------------------");

					mErrors.addOneError("合同状态为质押银行贷款,将被过滤掉!" + tMssage);
					continue;
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.6
				 * LCContState表的标志 查询出险时点后,合同状态是否为失效
				 * 事故日期：2005-04-01，失效：2005-09-04
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				tSql = "select * from LCContState where 1=1 "
						+ " and ContNo = '" + "?ContNo?" + "'"
						+ " and (PolNo = '000000' or PolNo = '" + "?PolNo?" + "')"
						+ " and StateType in ('Lost')" + " and State = '1'"
						+ " and lccontstate.StartDate <= to_date('"
						+ "?insdate?" + "','yyyy-mm-dd') "
						+ " and (lccontstate.enddate  >= to_date('"
						+ "?insdate?"
						+ "','yyyy-mm-dd') or lccontstate.enddate is null )";
				SQLwithBindVariables sqlbv6  = new SQLwithBindVariables();
				sqlbv6.sql(tSql);
				sqlbv6.put("ContNo", tContNo);
				sqlbv6.put("PolNo", tPolNo);
				sqlbv6.put("insdate", mInsDate);
				tLCContStateDB = new LCContStateDB();
				tLCContStateSet = tLCContStateDB.executeQuery(sqlbv6);
				if (tLCContStateDB.mErrors.needDealError()) {
					mErrors.addOneError("得到合同挂失状态失败!"
							+ tLCContStateDB.mErrors.getError(0).errorMessage);
					return false;
				}

				if (tLCContStateSet.size() > 0) {
					logger.debug("--------------------------------------------------------------------------------------------------");
					logger.debug("-----SQL:" + tSql);
					logger.debug("-----目前合同状态为挂失!!!!-----\n" + tMssage);
					logger.debug("--------------------------------------------------------------------------------------------------");
					mErrors.addOneError("合同号[" + mLCGetSchema.getContNo()
							+ "]的状态为挂失，被过滤掉，不参与理算!!!");
					continue;
				}
				
				
				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.6
				 * LCContState表的标志 查询出险时点后,合同状态是否为终止
				 * 出险日期：2005-04-01，失效：2005-09-04
				 * zhangzheng 2009-06-16
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				tSql = "select * from LCContState where 1=1 "
						+ " and ContNo = '" + "?ContNo?" + "'"
						+ " and (PolNo = '000000' or PolNo = '" + "?PolNo?" + "')"
						+ " and StateType in ('Terminate')" + " and State = '1'"
						+ " and lccontstate.StartDate <= to_date('"
						+ "?insdate?" + "','yyyy-mm-dd') "
						+ " and (lccontstate.enddate  >= to_date('"
						+ "?insdate?"
						+ "','yyyy-mm-dd') or lccontstate.enddate is null )";
				SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
				sqlbv7.sql(tSql);
				sqlbv7.put("ContNo", tContNo);
				sqlbv7.put("PolNo", tPolNo);
				sqlbv7.put("insdate", mInsDate);
				tLCContStateDB = new LCContStateDB();
				tLCContStateSet = tLCContStateDB.executeQuery(sqlbv7);
				if (tLCContStateDB.mErrors.needDealError()) {
					mErrors.addOneError("得到合同挂失状态失败!"
							+ tLCContStateDB.mErrors.getError(0).errorMessage);
					return false;
				}

				if (tLCContStateSet.size() > 0) {
					logger.debug("--------------------------------------------------------------------------------------------------");
					logger.debug("-----SQL:" + tSql);
					logger.debug("-----目前合同状态为挂失!!!!-----\n" + tMssage);
					logger.debug("--------------------------------------------------------------------------------------------------");
					mErrors.addOneError("合同号[" + mLCGetSchema.getContNo()
							+ "]的状态为挂失，被过滤掉，不参与理算!!!");
					continue;
				}

				logger.debug("--------------------------------------------------------------------------------------------------");
				logger.debug("\n--匹配过程二,所有条件过滤之后：[" + tMssage + "]\n");
				logger.debug("--------------------------------------------------------------------------------------------------");
				
				
				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.0
				 * 2009-01-13 zhangzheng
				 * 增加对豁免险的责任匹配处理,如果出险的理赔类型不包括条款规定的引起豁免责任的理赔类型,则将
				 * 匹配的豁免险的单子过滤掉;
				 * 如果豁免险不限制引起的类型,则也可以通过!
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				if(tGetDutyKind.substring(1).trim().equals("09"))
				{					
					String riskSql=" select risksortvalue from lmrisksort where riskcode='"+"?riskcode?"+"'"
					              +" and risksorttype in('18','19')";	
					logger.debug("--查询豁免险类型sql"+riskSql);
					SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
					sqlbv8.sql(riskSql);
					sqlbv8.put("riskcode", tRiskCode);
					ExeSQL tExeSQL = new ExeSQL();
					SSRS tSSRS = new SSRS();
					
					tSSRS = tExeSQL.execSQL(sqlbv8);
					
					boolean flag=false;
					
					//循环可以引起豁免的理赔类型
					for(int n=1;n<=tSSRS.getMaxRow();n++)
					{
						//只要有符合的就不用再循环
						if(flag==true)
						{
							break;
						}
						else
						{
							String mGetDutyKind=tSSRS.GetText(n, 1);
							
							for(int g=1;g<=mLLAppClaimReasonSet.size();g++)
							{
								if(mGetDutyKind.equals(mLLAppClaimReasonSet.get(g).getReasonCode().substring(1).trim()))
								{
									flag=true;
									mGetDutyKind=null;
									break;
								}
							}
							
							mGetDutyKind=null;
						}
			
					}
					
					riskSql=null;
					
					if(flag==false)
					{
						logger.debug("没有符合规定的可以引起豁免责任的理赔类型!");
						continue;
					}
				}
				
				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.1
				 * 进行正式匹配后的数据操作，获取给付责任，设置LLToClaimDuty信息
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				if (!getLLToClaimDuty(mLCGetSchema, tLMDutyGetClmSchema)) {
					continue;
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.2
				 * 判断是否是加保记录,如果是加保的数据,不进行后续的费用数据提取计算
				 * 因为加保的DutyCode为8为,正常的DutyCode的为6位
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				if (tDutyCode.length() == 8) {
					continue;
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.3
				 * 设置LLToClaimDutyFee信息
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */

				if (!getLLToClaimDutyFee(tGetDutyKind,mLCGetSchema.getInsuredNo())) {
					return false;
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No9.1
				 * 匹配完保项后,给出出险后作过的保全项目的提示信息 1-判断出险以后该保项是否做过[加保AA]
				 * 2-判断出险以后该保项是否做过[减保PT] 3-判断出险以后该保项是否做过[退保CT]
				 * 4-判断出险以后该保项是否做过[减额缴清PU] 5-判断出险以后该保项是否做过[附加特约的终止操作DT]
				 * 6-判断出险以后该保项是否做过[投保人变更AC]
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				if (mLPEdorItemSchema != null) {
					String tEdorNo = StrTool.cTrim(mLPEdorItemSchema
							.getEdorNo()); // 保全受理号
					String tEdorType = StrTool.cTrim(mLPEdorItemSchema
							.getEdorType()); // 保全受理类型
					String tEdorValiDate = StrTool.cTrim(
							mLPEdorItemSchema.getEdorValiDate()).substring(0,
							10);// 保全生效时间

					String tPTMessage = "合同号:[" + tContNo + "],险种号:[" + tPolNo
							+ "],保全受理号:[" + tEdorNo + "],保全生效时间:["
							+ tEdorValiDate + "]";
					if (tEdorType.equals("AA")) {
						tPTMessage = tPTMessage + ",在出险日期之后进行了保全加保操作!!!";
					}

					if (tEdorType.equals("PT")) {
						tPTMessage = tPTMessage
								+ ",在出险日期之后进行了保全减保操作,请先进行保全回退后再进行匹配理算!!!";
					}

					if (tEdorType.equals("CT")) {
						tPTMessage = tPTMessage
								+ ",在出险日期之后进行了保全退保操作,请先进行保全回退后再进行匹配理算!!!";
					}

					if (tEdorType.equals("PU")) {
						tPTMessage = tPTMessage
								+ ",在出险日期之后进行了减额缴清操作,请先进行保全回退后再进行匹配理算!!!";
					}

					if (tEdorType.equals("DT")) {
						tPTMessage = tPTMessage
								+ ",在出险日期之后进行了保全解约操作,请先进行保全回退后再进行匹配理算!!!";
					}

					if (tEdorType.equals("AC")) {
						tPTMessage = tPTMessage + ",在出险日期之后进行了保全投保人变更操作!!!";
					}
					mErrors.addOneError(tPTMessage);
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No9.1
				 * 匹配完保项后,给出出险前作过的保全项目的提示信息 1-判断出险以后该保项是否做过[加保AA]
				 * 2-判断出险以后该保项是否做过[减保PT] 3-判断出险以后该保项是否做过[退保CT]
				 * 4-判断出险以后该保项是否做过[减额缴清PU] 5-判断出险以后该保项是否做过[附加特约的终止操作DT]
				 * 6-判断出险以后该保项是否做过[投保人变更AC]
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				LPEdorItemSchema tLPEdorItemSchema = mLLClaimPubFunBL
						.getLPEdorItemBefore(tContNo, mNBPolNo, this.mInsDate,
								"AA','PT','CT','PU','DT','AC");// 得到保全的批单号
				if (tLPEdorItemSchema != null) {
					String tEdorNo = StrTool.cTrim(tLPEdorItemSchema
							.getEdorNo()); // 保全受理号
					String tEdorType = StrTool.cTrim(tLPEdorItemSchema
							.getEdorType()); // 保全受理类型
					String tEdorValiDate = StrTool.cTrim(
							tLPEdorItemSchema.getEdorValiDate()).substring(0,
							10);// 保全生效时间

					String tPTMessage = "合同号:[" + tContNo + "],险种号:[" + tPolNo
							+ "],保全受理号:[" + tEdorNo + "],保全生效时间:["
							+ tEdorValiDate + "]";
					if (tEdorType.equals("AA")) {
						tPTMessage = tPTMessage + ",在出险日期之前进行了保全加保操作!!!";
					}

					if (tEdorType.equals("PT")) {
						tPTMessage = tPTMessage + ",在出险日期之前进行了保全减保操作!!!";
					}

					if (tEdorType.equals("CT")) {
						tPTMessage = tPTMessage + ",在出险日期之前进行了保全退保操作!!!";
					}

					if (tEdorType.equals("PU")) {
						tPTMessage = tPTMessage + ",在出险日期之前进行了减额缴清操作!!!";
					}

					if (tEdorType.equals("DT")) {
						tPTMessage = tPTMessage + ",在出险日期之前进行了保全解约操作!!!";
					}

					if (tEdorType.equals("AC")) {
						tPTMessage = tPTMessage + ",在出险日期之前进行了保全投保人变更操作!!!";
					}
					mErrors.addOneError(tPTMessage);
				}

				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No9.3 匹配完保项后
				 * 1-判断主险在宽限期内出险 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				String tSPyaToDate = mLCPolSchema.getPaytoDate(); // 缴费交至日期
				// int tIGPDays = mLLToClaimDutySchema.getGracePeriod(); //宽限天数
				// String tStandDate =
				// PubFun.calDate(tSPyaToDate,tIGPDays,"D",tSPyaToDate);
				String tStandDate = tSPyaToDate;
				int tCalDay = PubFun.calInterval(tStandDate, mInsDate, "D");
				String tKXQSQL = "select sysvarvalue from LDSysVar where sysvar='aheaddays'";//查询宽限期
				SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
				sqlbv9.sql(tKXQSQL);
				int tKXQ = Integer.parseInt(mExeSQL.getOneValue(sqlbv9));
				if (tCalDay >= 0) {
					if(tCalDay<=tKXQ){
						mErrors.addOneError("请注意:合同号[" + tContNo + "]主险在宽限期内出险!!!");
					}
				}

				logger.debug("--------------------------------------------------------------------------------------------------");
				logger.debug("\n--匹配过程三,最终完成：[" + tMssage + "]\n");
				logger.debug("--------------------------------------------------------------------------------------------------");

				logger.debug("\n===========================================结束执行第["
								+ j
								+ "]个给付责任算法===========================================\n");
			}// for--mLCGetSet结束

			logger.debug("\n==========================结束========执行理赔类型为========["
							+ tGetDutyKind
							+ "]=====的匹配算法==========================\n");
		}// 理赔类型循环结束

		return true;
	}

	/**
	 * 目的：获取理赔类型信息
	 * 
	 * @return
	 */
	private boolean getLLAppClaimReason() {
		LLAppClaimReasonDB tLLAppClaimReasonDB = new LLAppClaimReasonDB();
		tLLAppClaimReasonDB.setCaseNo(this.mClmNo); // 赔案号
		tLLAppClaimReasonDB.setCustomerNo(mLLCaseSchema.getCustomerNo()); // 客户号

		mLLAppClaimReasonSet = tLLAppClaimReasonDB.query();
		if (mLLAppClaimReasonSet == null || mLLAppClaimReasonSet.size() == 0) {
			this.mErrors.addOneError("没有找到到立案的理赔类型信息，不能进行责任匹配!");
			return false;
		}
		return true;
	}

	/**
	 * 获取出险人作为投保人和被保人的所有的给付责任信息 从[领取项表]C表或轨迹表中取得给付项记录
	 */
	public boolean getLCLBGet() {
		logger.debug("================开始========获取LCLBGet领取项信息==============================================");
		String tSql = "";
		String tSqlTemp = "";

		LCGetDB tLCGetDB = new LCGetDB();
		LCGetSet tLCGetSet = null;
		mLCGetSet.clear();

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0
		 * 承保出单前出险--对LCGet表--准备公用查询SQL LCGet表存放当期记录，LBGet表存放上期记录
		 * AppFlag=0[投保单],根据意外事故日期 >= 投保日期 AppFlag=1[保单], 根据意外事故日期 >= 投保日期 and
		 * 保单生效对应日 > 意外事故日期 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.1
		 * 承保出单前出险--对LCGet表进行作为被保人的判断,个险只查询个单 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		tSql = "select lcget.* from lcget,lcpol,lccont where 1 = 1"
				+ " and lcpol.contno=lccont.contno "
				+ " and lcget.contno=lccont.contno "
				+ " and lccont.grpcontno='00000000000000000000'"
				+ " and lcget.polno=lcpol.polno " + " and lcpol.conttype  = '"
				+ "?conttype?"
				+ "'" // 总单类型,1-个人投保单,2-集体总投保单
				+ " and lcpol.AppFlag  in ('0') " // 投保单
			//	+ " and lccont.state not in ('1002&&&&','1003&&&&') " // 1001&&&&-通知书逾期
																		// 1002&&&&-通知书逾期作废
																		// 1003&&&&-签单逾期作废
				+ " and lcpol.PolApplyDate<=to_date('"
				+ "?insdate?"
				+ "','yyyy-mm-dd') " // 事故日期必须在投保日期和生效日期之间，这样可以找出收费后承保前出险的保项
				+ " and lcpol.insuredno  in ( '"
				+ "?CustomerNo?"
				+ "')"
				+ " and Upper(lccont.UwFlag) not in ('A')"
				+ " union "
				+ " select lcget.* from lcget,lcpol where 1 = 1"
				+ " and lcget.polno=lcpol.polno "
				+ " and lcpol.grpcontno='00000000000000000000'"
				+ " and lcpol.AppFlag  in ('1') " // 保单
				+ " and RenewCount = 0 " // 续保次数为0,代表没有续保过
				+ " and lcpol.conttype  = '"
				+ "?conttype?"
				+ "'" // 总单类型,1-个人投保单,2-集体总投保单
				+ " and lcpol.PolApplyDate<=to_date('"
				+ "?insdate?"
				+ "','yyyy-mm-dd') " // 事故日期必须在投保日期和生效日期之间，这样可以找出收费后承保前出险的保项
				+ " and lcpol.CValiDate   > to_date('"
				+ "?insdate?"
				+ "','yyyy-mm-dd') "
				+ " and lcpol.insuredno  in ( '"
				+ "?CustomerNo?" + "')";
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql(tSql);
		sqlbv10.put("insdate", this.mInsDate);
		sqlbv10.put("conttype", this.mContType);
		sqlbv10.put("CustomerNo", mLLCaseSchema.getCustomerNo());
		logger.debug("-----承保出单前出险--getLCGet的被保人Sql语句-----" + tSql);
		tLCGetSet = tLCGetDB.executeQuery(sqlbv10);

		if (tLCGetDB.mErrors.needDealError()) {
			mErrors.addOneError("出险人作为被保人,查询承保出单前的责任发生错误!!!"
					+ tLCGetDB.mErrors.getError(0).errorMessage);
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.2
		 * 承保出单前出险--对LCGet表进行作为被保人的判断 给付类型,00- 投保人,01-被保人,02-受益人，03-连带被保人
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		for (int i = 1; i <= tLCGetSet.size(); i++) {
			LCGetSchema tLCGetSchema = tLCGetSet.get(i);
			tLCGetSchema.setState("01"); // LCGet表的该字段属于借用，用于存放给付类型：00-
											// 投保人,01-被保人,02-受益人，03-连带被保人
			tLCGetSchema.setOperator("A"); // LCGet表的该字段属于借用，用于存放保单类型：C表保单，B表保单,A承保出单前出险
			tLCGetSchema.setInsuredNo(mLLCaseSchema.getCustomerNo());// LCGet表的该字段属于借用，用于存放出险人编号
			mLCGetSet.add(tLCGetSchema);
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.3
		 * 承保出单前出险--对LCGet表进行作为投保人的判断 个险只查询个单－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		tSql = "select lcget.* from lcget,lcpol,lccont where 1 = 1"
				+ " and lcget.polno=lcpol.polno "
				+ " and lcpol.contno=lccont.contno "
				+ " and lccont.grpcontno='00000000000000000000'"
				+ " and lcpol.conttype  = '"
				+ "?contype?"
				+ "'" // 总单类型,1-个人投保单,2-集体总投保单
				+ " and lcpol.AppFlag  in ('0') " // 投保单
				+ " and lccont.state not in ('1002&&&&','1003&&&&') " // 1001&&&&-通知书逾期
																		// 1002&&&&-通知书逾期作废
																		// 1003&&&&-签单逾期作废
				+ " and lcpol.PolApplyDate<=to_date('"
				+ "?insdate?"
				+ "','yyyy-mm-dd') " // 事故日期必须在投保日期和生效日期之间，这样可以找出收费后承保前出险的保项
				+ " and lcpol.AppntNo  in ( '"
				+ "?CustomerNo?"
				+ "')"
				+ " union "
				+ " select lcget.* from lcget,lcpol where 1 = 1"
				+ " and lcget.polno=lcpol.polno "
				+ " and lcpol.grpcontno='00000000000000000000'"
				+ " and lcpol.AppFlag  in ('1') " // 保单
				+ " and RenewCount = 0 " // 续保次数为0,代表没有续保过
				+ " and lcpol.conttype  = '"
				+ "?contype?"
				+ "'" // 总单类型,1-个人投保单,2-集体总投保单
				+ " and lcpol.PolApplyDate<=to_date('"
				+ "?insdate?"
				+ "','yyyy-mm-dd') " // 事故日期必须在投保日期和生效日期之间，这样可以找出收费后承保前出险的保项
				+ " and lcpol.CValiDate   > to_date('"
				+ "?insdate?"
				+ "','yyyy-mm-dd') "
				+ " and lcpol.AppntNo  in ( '"
				+ "?CustomerNo?" + "')";
		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		sqlbv11.sql(tSql);
		sqlbv11.put("insdate", this.mInsDate);
		sqlbv11.put("contype", this.mContType);
		sqlbv11.put("CustomerNo", mLLCaseSchema.getCustomerNo());
		logger.debug("-----承保出单前出险--getLCGet的投保人Sql语句-----" + tSql);
		tLCGetSet = tLCGetDB.executeQuery(sqlbv11);

		if (tLCGetDB.mErrors.needDealError()) {
			mErrors.addOneError("出险人作为投保人,查询承保出单前的责任发生错误!!!"
					+ tLCGetDB.mErrors.getError(0).errorMessage);
			return false;
		}
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.4
		 * 承保出单前出险--对LCGet表进行作为投保人的判断 给付类型,00- 投保人,01-被保人,02-受益人，03-连带被保人
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		for (int i = 1; i <= tLCGetSet.size(); i++) {
			LCGetSchema tLCGetSchema = tLCGetSet.get(i);
			tLCGetSchema.setState("00"); // LCGet表的该字段属于借用，用于存放给付类型：00-
											// 投保人,01-被保人,02-受益人，03-连带被保人
			tLCGetSchema.setOperator("A"); // LCGet表的该字段属于借用，用于存放保单类型：C表保单，B表保单,A承保出单前出险
			tLCGetSchema.setInsuredNo(mLLCaseSchema.getCustomerNo());// LCGet表的该字段属于借用，用于存放出险人编号
			mLCGetSet.add(tLCGetSchema);
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 对LCGet表，准备公用查询SQL
		 * 最大计算日期，也就是费用结束日期为空，认为是没有费用录入信息 否则，认为有费用信息，则根据该日期进行判断
		 * 此种情况针对于类似于238主险产品，跨期出险，生成了续期记录， LCGet表存放当期记录，LBGet表存放上期记录
		 * 如果事故发生在上期,治疗时间在当期结束,也要把当期的保项匹配出来, 因为当期也要赔付一部分
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		if (this.mMaxCalDate == null) {
			tSqlTemp = "select lcget.* from lcget,lcpol where 1 = 1"
					+ " and lcget.polno=lcpol.polno "
					+ " and lcpol.grpcontno='00000000000000000000'"
					+ " and lcget.LiveGetType = '1' " // 生存意外给付标志,1意外
					+ " and lcpol.AppFlag  in ('1','4')" // 0-投保单,1-保单
					+ " and not exists(select 1 from lmrisksort t where t.riskcode=lcpol.riskcode and t.RiskSortType='18')"
					+ " and lcpol.conttype  = '"
					+ "?contype?"
					+ "'" // 总单类型,1-个人投保单,2-集体总投保单
					+ " and lcget.getstartdate<=to_date('" + "?insdate?"
					+ "','yyyy-mm-dd') " + " and lcget.getenddate  > to_date('"
					+ "?insdate?" + "','yyyy-mm-dd') ";
		} else {
			tSqlTemp = "select lcget.* from lcget,lcpol where 1 = 1"
					+ " and lcget.polno=lcpol.polno "
					+ " and lcpol.grpcontno='00000000000000000000'"
					+ " and lcget.LiveGetType = '1' " // 生存意外给付标志,1意外
					+ " and lcpol.AppFlag  in ('1','4')" // 0-投保单,1-保单
					+ " and not exists(select 1 from lmrisksort t where t.riskcode=lcpol.riskcode and t.RiskSortType='18')"
					+ " and lcpol.conttype  = '"
					+ "?contype?"
					+ "'" // 总单类型,1-个人投保单,2-集体总投保单
					+ " and (  (lcget.getstartdate<=to_date('" + "?insdate?"
					+ "','yyyy-mm-dd')    and lcget.getenddate > to_date('"
					+ "?insdate?" + "','yyyy-mm-dd') ) "
					+ " or (lcget.getstartdate<=to_date('" + "?caldate?"
					+ "','yyyy-mm-dd') and lcget.getenddate > to_date('"
					+ "?caldate?"
					+ "','yyyy-mm-dd') and char_length(trim(lcget.DutyCode))=6 ) )"
					+ " and to_date('" + "?insdate?"
					+ "','yyyy-mm-dd')<=to_date('" + "?caldate?"
					+ "','yyyy-mm-dd')";
		}
		
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.1 对LCGet表进行作为被保人的判断
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		tSql = tSqlTemp + " and lcpol.insuredno  in ('"
				+ "?CustomerNo?" + "')"; // 作为被保人
		logger.debug("-----getLCGet的被保人Sql语句-----" + tSql);
		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		sqlbv12.sql(tSql);
		sqlbv12.put("insdate", this.mInsDate);
		sqlbv12.put("contype", this.mContType);
		sqlbv12.put("caldate", this.mMaxCalDate);
		sqlbv12.put("CustomerNo", mLLCaseSchema.getCustomerNo());
		tLCGetDB = new LCGetDB();
		tLCGetSet = tLCGetDB.executeQuery(sqlbv12);

		if (tLCGetDB.mErrors.needDealError()) {
			mErrors.addOneError("出险人作为被保人,查询出单后的责任发生错误!!!"
					+ tLCGetDB.mErrors.getError(0).errorMessage);
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.2 对LCGet表进行作为被保人的判断
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		for (int i = 1; i <= tLCGetSet.size(); i++) {

			LCGetSchema tLCGetSchema = tLCGetSet.get(i);
			tLCGetSchema.setState("01"); // LCGet表的该字段属于借用，用于存放给付类型：00-
											// 投保人,01-被保人,02-受益人，03-连带被保人
			tLCGetSchema.setOperator("C"); // LCGet表的该字段属于借用，用于存放保单类型：C表保单，B表保单,A承保出单前出险
			tLCGetSchema.setInsuredNo(mLLCaseSchema.getCustomerNo());// LCGet表的该字段属于借用，用于存放出险人编号
			mLCGetSet.add(tLCGetSchema);

		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.2 对LCGet表进行作为投保人的判断
		 * 2009-01-15 zhangzheng 只有当理赔责任包括豁免时才查询,用于应对投保人豁免险
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		boolean mFlag=false;
		for(int k=1;k<=mLLAppClaimReasonSet.size();k++)
		{
			if(mLLAppClaimReasonSet.get(k).getReasonCode().substring(1).trim().equals("09"))
			{
				mFlag=true;
				break;
			}
		}
		
		if(mFlag==true)
		{
			if (this.mMaxCalDate == null) {
				tSqlTemp = "select lcget.* from lcget,lcpol where 1 = 1"
						+ " and lcget.polno=lcpol.polno "
						+ " and lcpol.grpcontno='00000000000000000000'"
						+ " and lcget.LiveGetType = '1' " // 生存意外给付标志,1意外
						+ " and lcpol.AppFlag  in ('1','4')" // 0-投保单,1-保单
						+ " and exists(select 1 from lmrisksort t where t.riskcode=lcpol.riskcode and t.RiskSortType='18')"
						+ " and lcpol.conttype  = '"
						+ "?contype?"
						+ "'" // 总单类型,1-个人投保单,2-集体总投保单
						+ " and lcget.getstartdate<=to_date('" + "?insdate?"
						+ "','yyyy-mm-dd') " + " and lcget.getenddate  > to_date('"
						+ "?insdate?" + "','yyyy-mm-dd') ";
			} else {
				tSqlTemp = "select lcget.* from lcget,lcpol where 1 = 1"
						+ " and lcget.polno=lcpol.polno "
						+ " and lcpol.grpcontno='00000000000000000000'"
						+ " and lcget.LiveGetType = '1' " // 生存意外给付标志,1意外
						+ " and lcpol.AppFlag  in ('1','4')" // 0-投保单,1-保单
						+ " and exists(select 1 from lmrisksort t where t.riskcode=lcpol.riskcode and t.RiskSortType='18')"
						+ " and lcpol.conttype  = '"
						+ "?contype?"
						+ "'" // 总单类型,1-个人投保单,2-集体总投保单
						+ " and (  (lcget.getstartdate<=to_date('" + "?insdate?"
						+ "','yyyy-mm-dd')    and lcget.getenddate > to_date('"
						+ "?insdate?" + "','yyyy-mm-dd') ) "
						+ " or (lcget.getstartdate<=to_date('" + "?caldate?"
						+ "','yyyy-mm-dd') and lcget.getenddate > to_date('"
						+ "?caldate?"
						+ "','yyyy-mm-dd') and char_length(trim(lcget.DutyCode))=6 ) )"
						+ " and to_date('" + "?insdate?"
						+ "','yyyy-mm-dd')<=to_date('" + "?caldate?"
						+ "','yyyy-mm-dd')";
			}
			
			tSql = tSqlTemp + " and lcpol.AppntNo in ('"
			+ "?CustomerNo?" + "')"; // 作为投保人
			logger.debug("-----getLCGet的投保人Sql语句-----" + tSql);
			SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
			sqlbv14.sql(tSql);
			sqlbv14.put("insdate", this.mInsDate);
			sqlbv14.put("contype", this.mContType);
			sqlbv14.put("caldate", this.mMaxCalDate);
			sqlbv14.put("CustomerNo", mLLCaseSchema.getCustomerNo());
			tLCGetDB = new LCGetDB();
			tLCGetSet = tLCGetDB.executeQuery(sqlbv14);
			if (tLCGetDB.mErrors.needDealError()) {
				mErrors.addOneError("出险人作为投保人,查询出单后的责任发生错误!!!"
						+ tLCGetDB.mErrors.getError(0).errorMessage);
				return false;
		
			}
		
			/* 给付类型,00- 投保人,01-被保人,02-受益人，03-连带被保人 */
			for (int i = 1; i <= tLCGetSet.size(); i++) {
				LCGetSchema tLCGetSchema = tLCGetSet.get(i);
				tLCGetSchema.setState("00"); // LCGet表的该字段属于借用，用于存放给付类型：00-
												// 投保人,01-被保人,02-受益人，03-连带被保人
				tLCGetSchema.setOperator("C"); // LCGet表的该字段属于借用，用于存放保单类型：C表保单，B表保单,A承保出单前出险
				tLCGetSchema.setInsuredNo(mLLCaseSchema.getCustomerNo());// LCGet表的该字段属于借用，用于存放出险人编号
				mLCGetSet.add(tLCGetSchema);
			}
		}
		

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0
		 * 判断出险人在LCInsuredRelated连带被保人表中是否有数据 用于144联生险的情况
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (this.mMaxCalDate == null) {
//			tSql = "select /*+RULE*/ lcget.* from lcget,lcpol,LCInsuredRelated where 1 = 1"
//					+ " and lcget.polno=lcpol.polno "
//					+ " and lcget.polno=LCInsuredRelated.polno "
//					+ " and lcpol.grpcontno='00000000000000000000'"
//					+ " and lcget.LiveGetType = '1' " // 生存意外给付标志,1意外
//					+ " and lcpol.AppFlag  in ('1','4')" // 0-投保单,1-保单
//					+ " and lcpol.conttype  = '"
//					+ this.mContType
//					+ "'" // 总单类型,1-个人投保单,2-集体总投保单
//					+ " and lcget.getstartdate<=to_date('"
//					+ this.mInsDate
//					+ "','yyyy-mm-dd') "
//					+ " and lcget.getenddate  > to_date('"
//					+ this.mInsDate
//					+ "','yyyy-mm-dd') "
//					+ " and LCInsuredRelated.CustomerNo = '"
//					+ mLLCaseSchema.getCustomerNo() + "'";
			tSql = "select * from lcget a where 1 = 1"
				+ " and LiveGetType = '1' and  getstartdate<=to_date('"+ "?insdate?"+ "','yyyy-mm-dd') " 
				+ " and getenddate  > to_date('"+ "?insdate?"+ "','yyyy-mm-dd') "
				+ " and exists (select 1 from lcpol where polno = a.polno and grpcontno = '00000000000000000000' "
				+ " and AppFlag in ('1', '4') and conttype = '"+ "?contype?"+ "' )"
				+ " and exists (select 1 from LCInsuredRelated where polno = a.polno and CustomerNo = '"
				+ "?CustomerNo?" + "')";
				
		} else {
//			tSql = "select lcget.* from lcget,lcpol,LCInsuredRelated where 1 = 1"
//					+ " and lcget.polno=lcpol.polno "
//					+ " and lcget.polno=LCInsuredRelated.polno "
//					+ " and lcpol.grpcontno='00000000000000000000'"
//					+ " and lcget.LiveGetType = '1' " // 生存意外给付标志,1意外
//					+ " and lcpol.AppFlag  in ('1','4')" // 0-投保单,1-保单
//					+ " and lcpol.conttype  = '"
//					+ this.mContType
//					+ "'" // 总单类型,1-个人投保单,2-集体总投保单
//					+ " and (  (lcget.getstartdate<=to_date('"
//					+ this.mInsDate
//					+ "','yyyy-mm-dd')    and lcget.getenddate > to_date('"
//					+ this.mInsDate
//					+ "','yyyy-mm-dd') ) "
//					+ " or (lcget.getstartdate<=to_date('"
//					+ this.mMaxCalDate
//					+ "','yyyy-mm-dd') and lcget.getenddate > to_date('"
//					+ this.mMaxCalDate
//					+ "','yyyy-mm-dd') ) )"
//					+ " and to_date('"
//					+ this.mInsDate
//					+ "','yyyy-mm-dd')<=to_date('"
//					+ this.mMaxCalDate
//					+ "','yyyy-mm-dd')"
//					+ " and LCInsuredRelated.CustomerNo = '"
//					+ mLLCaseSchema.getCustomerNo() + "'";
			tSql = "select * from lcget a where 1 = 1 "
				 + "and LiveGetType = '1' and ((getstartdate<=to_date('"+ "?insdate?"+ "','yyyy-mm-dd')    "
				 + "and a.getenddate > to_date('"+ "?insdate?"+ "','yyyy-mm-dd') "
				 + "and a.getenddate > to_date('"+ "?insdate?"+ "','yyyy-mm-dd') ) "
				+ " or (a.getstartdate<=to_date('"+ "?caldate?"+ "','yyyy-mm-dd') "
				+ "and a.getenddate > to_date('"+ "?caldate?"+ "','yyyy-mm-dd') ) )"
				+ " and to_date('"+ "?insdate?"+ "','yyyy-mm-dd')<=to_date('"+ "?caldate?"+ "','yyyy-mm-dd')"
				+ "and exists(select 1 from lcpol where polno=a.polno and grpcontno='00000000000000000000' "
				+"and AppFlag  in ('1','4') and conttype= '"+ "?contype?"+ "' )" 
                + "and exists(select 1 from LCInsuredRelated where polno=a.polno and CustomerNo='"+"?CustomerNo?" +"')";
		}

		logger.debug("-----连带被保人的Sql语句-----" + tSql);
		SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
		sqlbv16.sql(tSql);
		sqlbv16.put("insdate", this.mInsDate);
		sqlbv16.put("contype", this.mContType);
		sqlbv16.put("caldate", this.mMaxCalDate);
		sqlbv16.put("CustomerNo", mLLCaseSchema.getCustomerNo());
		tLCGetDB = new LCGetDB();
		tLCGetSet = tLCGetDB.executeQuery(sqlbv16);
		if (tLCGetDB.mErrors.needDealError()) {
			mErrors.addOneError("出险人作为连带被保人,查询出单后的责任发生错误!!!"
					+ tLCGetDB.mErrors.getError(0).errorMessage);
			return false;
		}

		/* 给付类型,00- 投保人,01-被保人,02-受益人，03-连带被保人 */
		for (int i = 1; i <= tLCGetSet.size(); i++) {
			LCGetSchema tLCGetSchema = tLCGetSet.get(i);
			tLCGetSchema.setState("01"); // LCGet表的该字段属于借用，用于存放给付类型：00-
											// 投保人,01-被保人,02-受益人，03-连带被保人
			tLCGetSchema.setOperator("C"); // LCGet表的该字段属于借用，用于存放保单类型：C表保单，B表保单,A承保出单前出险
			tLCGetSchema.setInsuredNo(mLLCaseSchema.getCustomerNo());// LCGet表的该字段属于借用，用于存放出险人编号
			mLCGetSet.add(tLCGetSchema);
		}
		


		logger.debug("================结束========获取LCLBGet领取项信息==============================================");
		return true;

	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－进行匹配－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 找出换号前的保单险种号,也就是承保时的保单号
	 * 
	 * @param pLCPolSchema
	 * @return
	 */
	private String getNBPolNo(LCPolSchema pLCPolSchema) {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 续期抽档时 原保单的 AppFlag =
		 * "1" 新保单的 AppFlag = "9" 抽档回销成功时 原保单的 AppFlag = "4" 新保单的 AppFlag = "1"
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tReturn = "";
		String tAppFlag = StrTool.cTrim(pLCPolSchema.getAppFlag());
		String tContNo = StrTool.cTrim(pLCPolSchema.getContNo());
		String tRiskCode = StrTool.cTrim(pLCPolSchema.getRiskCode());
		if (tAppFlag.equals("4")) {
			String tSql = "select * from LCPol where 1=1 "
					+ " and ContNo = '"
					+ "?ContNo?"
					+ "'"
					+ " and RiskCode='"
					+ "?RiskCode?"
					+ "'"
					+ " and PayToDate in (select max(PayToDate) from LCPol where 1=1 "
					+ " and ContNo = '" + "?ContNo?" + "'" + " and RiskCode='"
					+ "?RiskCode?" + "' )";
			SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
			sqlbv17.sql(tSql);
			sqlbv17.put("ContNo", tContNo);
			sqlbv17.put("RiskCode", tRiskCode);
			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv17);
			if (tLCPolDB.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tLCPolDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "LLClaimCalMatchBL";
				tError.functionName = "getYearBonus";
				tError.errorMessage = "得到承保时的保单险种号失败!";
				logger.debug("--------------------------------------------------------------------------------------------------");
				logger.debug("--LLClaimCalMatchBL.getNBPolNo()--得到承保时的保单险种号失败!"
								+ tSql);
				logger.debug("--------------------------------------------------------------------------------------------------");
				this.mErrors.addOneError(tError);
			}

			if (tLCPolSet.size() == 1) {
				tReturn = StrTool.cTrim(tLCPolSet.get(1).getPolNo());
			} else {
				tReturn = StrTool.cTrim(pLCPolSchema.getPolNo());
			}
		} else {
			tReturn = StrTool.cTrim(pLCPolSchema.getPolNo());
		}
		return StrTool.cTrim(tReturn);
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－执行过滤算法－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 执行过滤算法，进一步判断
	 * 
	 * @param string
	 *            String
	 * @return boolean
	 */
	private boolean getFilterCalCode(String pCalCode) {
		if (null == pCalCode || "".equals(pCalCode)) {
			return true;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 获取出险时点之前做过保全复效的批单号
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tContNo = mLCGetSchema.getContNo(); // 合同号
		String tPolNo = mLCGetSchema.getPolNo(); // 保单险种号

		LPEdorItemSchema tLPEdorItemSchema = mLLClaimPubFunBL
				.getLPEdorItemBefore(tContNo, tPolNo, this.mInsDate, "RE");// 出险时点前的保全批单号
		String tPosEdorNoFront = "";
		if (tLPEdorItemSchema != null) {
			tPosEdorNoFront = StrTool.cTrim(tLPEdorItemSchema.getEdorNo());
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 设置各种过滤算法的计算要素
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		TransferData tTransferData = new TransferData();

		// 出险日期
		tTransferData.setNameAndValue("AccidentDate", String
				.valueOf(mInsDate));

		// 交费期限
		tTransferData.setNameAndValue("PayEndDate", String
				.valueOf(mLCDutySchema.getPayEndDate()));

		//
		tTransferData.setNameAndValue("PayEndYear", String
				.valueOf(mLCDutySchema.getPayEndYear()));

		// 保单号
		tTransferData.setNameAndValue("PolNo", String.valueOf(mLCPolSchema
				.getPolNo()));

		// 被保人0岁保单生效对应日
		tTransferData.setNameAndValue("InsuredvalidBirth", mLLClaimPubFunBL
				.getInsuredvalideBirth(mLCPolSchema));

		// 出险时已保天数
		tTransferData.setNameAndValue("RgtDays", String.valueOf(PubFun
				.calInterval(mLCPolSchema.getCValiDate(), this.mInsDate, "D")));

		// 出险时已保年期
		tTransferData.setNameAndValue("RgtYears", String.valueOf(PubFun
				.calInterval(mLCPolSchema.getCValiDate(), this.mInsDate, "Y")));

		// 被保险人客户号
		tTransferData.setNameAndValue("InsuredNo", String.valueOf(mLCPolSchema
				.getInsuredNo()));

		// 赔案号
		tTransferData.setNameAndValue("CaseNo", String.valueOf(this.mClmNo));

		// 保单生效日期
		tTransferData.setNameAndValue("CValiDate", String.valueOf(mLCDutySchema
				.getCValiDate()));

		// 死亡日期,也就是出险时间
		tTransferData.setNameAndValue("DeathDate", String.valueOf(mLLCaseSchema
				.getAccDate()));

		// 意外伤害发生日期
		tTransferData.setNameAndValue("InjuryDate", String
				.valueOf(this.mAccDate));

		// 交费间隔
		tTransferData.setNameAndValue("PayIntv", String.valueOf(mLCPolSchema
				.getPayIntv()));

		// 交费期限
		tTransferData.setNameAndValue("PayEndDate", String
				.valueOf(mLCDutySchema.getPayEndDate()));

		// 起领日期,取自出险时点,需要考虑保全
		if (mLPEdorItemSchema == null) {
			tTransferData.setNameAndValue("GetStartDate", String
					.valueOf(mLLClaimPubFunBL.getGetStartDate(null,
							mLCGetSchema)));
		} else {
			tTransferData.setNameAndValue("GetStartDate", String
					.valueOf(mLLClaimPubFunBL.getGetStartDate(mLPEdorItemSchema
							.getEdorNo(), mLCGetSchema)));
		}

		// 保单是否复效的标记
		tTransferData.setNameAndValue("LRFlag", String.valueOf(mLLClaimPubFunBL
				.getLRFlag(tPosEdorNoFront, tPolNo)));

		// 复效时间
		String tLastRevDate = mLLClaimPubFunBL.getLastRevDate(tPosEdorNoFront,
				tContNo, tPolNo, mLCPolSchema);

		// 复效到出险时已保年期,取自出险时点,需要考虑保全
		tTransferData.setNameAndValue("LRYears", String.valueOf(PubFun
				.calInterval(tLastRevDate, this.mInsDate, "Y")));

		// 复效到出险时已保天数,取自出险时点,需要考虑保全
		tTransferData.setNameAndValue("LRDays", String.valueOf(PubFun
				.calInterval(tLastRevDate, this.mInsDate, "D")));

		// 投保标志,1正常,4终止,9续保未生效
		tTransferData.setNameAndValue("AppFlag", String
				.valueOf(mLLClaimPubFunBL.getAppFlag("", mLCPolSchema)));

		// 责任
		tTransferData.setNameAndValue("DutyCode", String.valueOf(mLCDutySchema
				.getDutyCode()));

		// LCGet的开始时间
		tTransferData.setNameAndValue("LCGetStartDate", String
				.valueOf(mLCGetSchema.getGetStartDate()));

		// LCGet的终止时间
		tTransferData.setNameAndValue("LCGetEndDate", String
				.valueOf(mLCGetSchema.getGetEndDate()));

		// 续保次数,用于医疗类附加险计算
		tTransferData.setNameAndValue("RenewCount", String.valueOf(mLCPolSchema
				.getRenewCount()));

		// 出险细节，用于过滤像397这样多责任的险种
		tTransferData.setNameAndValue("AccidentDetail", String.valueOf(StrTool
				.cTrim(mLLCaseSchema.getAccidentDetail())));

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
		 * 责任筛选算法.用于匹配算法的描述,返回值为1--匹配,0--不匹配
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		Calculator tcalculator = new Calculator();
		Vector tv = tTransferData.getValueNames();
		logger.debug("-----------------------执行过滤算法-----打印过滤要素--------------------");
		for (int i = 0; i < tv.size(); i++) {
			String tName = (String) tv.get(i);
			String tValue = (String) tTransferData.getValueByName(tName);
			logger.debug("第[" + i + "]个匹配要素名称--" + tName + "--[" + tValue
					+ "]");
			tcalculator.addBasicFactor(tName, tValue);
		}

		logger.debug("-----------------------执行过滤算法-----调用公式计算--------------------");
		tcalculator.setCalCode(pCalCode);
		mBomList=mPrepareBOMClaimBL.dealData(tTransferData);
		tcalculator.setBOMList(mBomList);
		String tRet = tcalculator.calculate();
		if (tcalculator.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tcalculator.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimCalAutoBL";
			tError.functionName = "getFilterCalCode";
			tError.errorMessage = "过滤算法计算发生错误!原公式:" + pCalCode + "||,解析后的公式:"
					+ tcalculator.getCalSQL();
			this.mErrors.addOneError(tError);
		}

		boolean tbl = "1".equals(tRet);

		if ("1".equals(tRet)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 目的：进行保项的过滤，针对多出险人的情况
	 * 
	 * @param pLMDutyGetClmSchema
	 * @param pPolNo
	 * @param pCasePolType
	 * @return
	 */
	private boolean getFilterMatch(LMDutyGetClmSchema pLMDutyGetClmSchema,
			LCGetSchema pLCGetSchema) {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 * 给付类型,pLMDutyGetClmSchema.getCasePolType() 12位,00--表示投保人,01--表示被保人
		 * 56位,01--第一顺序（投保人或被保险人等）,02第二顺序
		 * 
		 * 给付类型pCasePolType,00- 投保人,01-被保人,02-受益人，03-连带被保人
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		String tCasePolType = pLMDutyGetClmSchema.getCasePolType();
		String tRiskRole = "";
		String tRiskRoleNo = "";
		if (tCasePolType != null && tCasePolType.length() == 6) {
			tRiskRole = tCasePolType.substring(0, 2);
			tRiskRoleNo = tCasePolType.substring(4, 6);
		}

		String pCasePolType = pLCGetSchema.getState(); // LCGet表的该字段属于借用，用于存放给付类型：00-
														// 投保人,01-被保人,02-受益人，03-连带被保人

		logger.debug("--------第一过滤算法:LCGet的投保标志:[" + pCasePolType
				+ "],产品定义的标志:[" + tCasePolType + "]");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 一定要先判断符合匹配条件的信息
		 * 进行12位判断，也就是投保人判断
		 * 
		 * 给付类型pCasePolType,00- 投保人,01-被保人,02-受益人，03-连带被保人
		 * 
		 * 传进来的参数与产品LMDutyGetClmSchema表中CasePolType的12位进行比较
		 * 
		 * pCasePolType = 00 and tRiskRole = 00 符合匹配条件 pCasePolType = 00 and
		 * tRiskRole = 01 不符合匹配条件 pCasePolType = 00 and tRiskRole = null 不符合匹配条件
		 * 
		 * pCasePolType = 01 and tRiskRole = 空 符合匹配条件 pCasePolType = 01 and
		 * tRiskRole = 00 不符合匹配条件 pCasePolType = 01 and tRiskRole = 01 进行下一步判断
		 * 
		 * pCasePolType = 01 and tRiskRole = 01 进行下一步判断
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

//		if (tRiskRole.equals("00") || tRiskRole.equals("01")
//				|| tRiskRole.equals("") || tRiskRole == null) {
//
//		} else {
//			return false;
//		}
//
//		if (pCasePolType.equals("00") && tRiskRole.equals("00")) {
//			return true;
//		}
//		if (pCasePolType.equals("00") && tRiskRole.equals("01")) {
//			return false;
//		}
//		if (pCasePolType.equals("00")
//				&& (tRiskRole == null || tRiskRole.equals(""))) {
//			return false;
//		}
//
//		if (pCasePolType.equals("01")
//				&& (tRiskRole == null || tRiskRole.equals(""))) {
//			return true;
//		}
//
//		if (pCasePolType.equals("01") && tRiskRole.equals("00")) {
//			return false;
//		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 * 进行56位判断，也就是被保人判断，01--第一顺序（投保人或被保险人等）,02第二顺序
		 * 
		 * pCasePolType = 01 and tRiskRole = 01 进行下一步判断
		 * 
		 * 1. 先从LCInsured表中找出是否有数据 1.2 如果有，将tSequenceNo =
		 * 01，与LCInsured.被保人顺序号相比较，如果不等，则不符合匹配条件
		 * 
		 * 1.3 如果无，从LCInsuredRelated个单连带被保人表找是否数据
		 * 1.3.1如果有，LCInsuredRelated.SequenceNo(连带被保人表的序号) 与
		 * LMDutyGetClmSchema.CasePolType(5,6) , 相比较，如果不等，则不符合匹配条件
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (pCasePolType.equals("01") && tRiskRole.equals("01")) {
			String tSequenceNo = "";
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			tLCInsuredDB.setContNo(pLCGetSchema.getContNo());
			tLCInsuredDB.setInsuredNo(mLLCaseSchema.getCustomerNo());
			LCInsuredSet tLCInsuredSet = tLCInsuredDB.query();

			// LCInsured表有数据
			if (tLCInsuredSet != null && tLCInsuredSet.size() == 1) {
				tSequenceNo = "01";
				String tNBSequenceNo = "0"
						+ StrTool.cTrim(tLCInsuredSet.get(1).getSequenceNo());

				if (tSequenceNo.equals(tNBSequenceNo)
						&& tSequenceNo.equals(tRiskRoleNo)) {
					return true;
				}

				if (!tSequenceNo.equals(tNBSequenceNo)) {
					String pPolNo = pLCGetSchema.getPolNo();

					LCInsuredRelatedDB tLCInsuredRelatedDB = new LCInsuredRelatedDB();
					tLCInsuredRelatedDB.setPolNo(pPolNo);
					tLCInsuredRelatedDB.setCustomerNo(mLLCaseSchema
							.getCustomerNo());
					LCInsuredRelatedSet tLCInsuredRelatedSet = tLCInsuredRelatedDB
							.query();

					if (tLCInsuredRelatedSet != null
							&& tLCInsuredRelatedSet.size() == 1) {
						tSequenceNo = tLCInsuredRelatedSet.get(1)
								.getSequenceNo();

						if (tSequenceNo.equals(tRiskRoleNo)) {
							return true;
						} else {
							return false;
						}

					} else {
						return false;
					}

				} else {
					return false;
				}
			} else {
				return false;
			}

		}
		return true;

	}

	/**
	 * 对于两个被保人的情况过滤掉相同的保项信息
	 * 
	 * @param pLCGetSchema
	 * @param pLLToClaimDutySet
	 * @return
	 */
	private boolean getFilterGetDutyCode(LCGetSchema pLCGetSchema,
			LLToClaimDutySet pLLToClaimDutySet) {

		String tLCPolNo = StrTool.cTrim(pLCGetSchema.getPolNo());
		String tLCDutyCode = StrTool.cTrim(pLCGetSchema.getDutyCode());
		String tLCGetDutyKind = StrTool.cTrim(pLCGetSchema.getGetDutyKind());
		String tLCGetDutyCode = StrTool.cTrim(pLCGetSchema.getGetDutyCode());

		for (int i = 1; i <= pLLToClaimDutySet.size(); i++) {
			LLToClaimDutySchema tLLToClaimDutySchema = pLLToClaimDutySet.get(i);
			String tLLPolNo = StrTool.cTrim(tLLToClaimDutySchema.getPolNo());
			String tLLDutyCode = StrTool.cTrim(tLLToClaimDutySchema
					.getDutyCode());
			String tLLGetDutyKind = StrTool.cTrim(tLLToClaimDutySchema
					.getGetDutyKind());
			String tLLGetDutyCode = StrTool.cTrim(tLLToClaimDutySchema
					.getGetDutyCode());

			if (tLCPolNo.equals(tLLPolNo) && tLCDutyCode.equals(tLLDutyCode)
					&& tLCGetDutyKind.equals(tLLGetDutyKind)
					&& tLCGetDutyCode.equals(tLLGetDutyCode)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－执行过滤算方－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－进行匹配后保项的添加－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 目的：根据LCGet设置LLToClaimDuty信息
	 * 
	 * @return
	 */
	private boolean getLLToClaimDuty(LCGetSchema pLCGetSchema,
			LMDutyGetClmSchema pLMDutyGetClmSchema) {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 定义变量
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tContNo = StrTool.cTrim(pLCGetSchema.getContNo());
		String tPolNo = StrTool.cTrim(pLCGetSchema.getPolNo());

		mLLToClaimDutySchema = new LLToClaimDutySchema();
		mLLToClaimDutySchema.setCaseNo(this.mClmNo); /* 分案号 */
		mLLToClaimDutySchema.setCaseRelaNo(this.mClmNo); /* 受理事故号 */
		mLLToClaimDutySchema.setSubRptNo(this.mClmNo); /* 事件号 */

		mLLToClaimDutySchema.setPolNo(pLCGetSchema.getPolNo()); /* 保单号 */
		mLLToClaimDutySchema.setPolType(mLCPolSchema.getPolTypeFlag()); /*
																		 * 保单性质0
																		 * --个人单,1
																		 * --无名单,2
																		 * --（团单）公共帐户
																		 */

		/* LCGet表的该字段属于借用，保单类型,C表保单，B表保单,A承保出单前出险 */
		mLLToClaimDutySchema.setPolSort(pLCGetSchema.getOperator());

		mLLToClaimDutySchema.setDutyCode(pLCGetSchema.getDutyCode()); /* 责任编码 */
		mLLToClaimDutySchema.setGetDutyCode(pLCGetSchema.getGetDutyCode()); /* 给付责任编码 */
		mLLToClaimDutySchema.setGetDutyKind(pLCGetSchema.getGetDutyKind()); /* 给付责任类型 */

		mLLToClaimDutySchema.setGrpContNo(mLCPolSchema.getGrpContNo()); /* 集体合同号 */
		mLLToClaimDutySchema.setGrpPolNo(mLCPolSchema.getGrpContNo()); /* 集体保单号 */
		mLLToClaimDutySchema.setContNo(mLCPolSchema.getContNo()); /* 个单合同号 */
		mLLToClaimDutySchema.setKindCode(mLCPolSchema.getKindCode()); /* 险类代码 */
		mLLToClaimDutySchema.setRiskCode(mLCPolSchema.getRiskCode()); /* 险种代码 */
		mLLToClaimDutySchema.setRiskVer(mLCPolSchema.getRiskVersion()); /* 险种版本号 */
		mLLToClaimDutySchema.setPolMngCom(mLCPolSchema.getManageCom()); /* 保单管理机构 */
		mLLToClaimDutySchema.setClaimCount(0); /* 理算次数 */
		mLLToClaimDutySchema.setGiveType("0"); /* 赔付结论 */
		mLLToClaimDutySchema.setCurrency(mLCPolSchema.getCurrency());

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 取出险种的帐户标志
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
	

		String tInsuredFlag = StrTool.cTrim(pLMDutyGetClmSchema.getInpFlag());// 帐户标志

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 取出主附险[M],附加险标志[S]
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tRiskCode = StrTool.cTrim(mLLToClaimDutySchema.getRiskCode());
		LMRiskAppSchema tLMRiskAppSchema = mLLClaimPubFunBL
				.getLMRiskApp(tRiskCode);
		if (tLMRiskAppSchema == null) {
			this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
			return false;
		}
		String tSubRiskFlag = StrTool.cTrim(tLMRiskAppSchema.getSubRiskFlag());// 主附险标志

		/* 主附险标志 M主险 S附险 */
		mLLToClaimDutySchema.setRiskType(tSubRiskFlag);
		mLLToClaimDutySchema.setEffectOnMainRisk("000000");

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0
		 * 如果出附加险影响主险,且附加险只是扩展主险责任,赔付也是赔付主险责任的情况:如老系统的两个附加提前给付险种,
		 * 则取出主险所在的LCGet信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tEffectOnMainRisk = StrTool.cTrim(pLMDutyGetClmSchema
				.getEffectOnMainRisk());// 附险影响主险标志

		LCGetSchema tMRiskLCGetSchema = null;
		if (tSubRiskFlag.equals("S") && (tEffectOnMainRisk.equals("01"))) {
			tMRiskLCGetSchema = getMRiskLCGetSchema(pLCGetSchema);
			if (tMRiskLCGetSchema == null) {
				this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
				return false;
			}

			mLLToClaimDutySchema.setEffectOnMainRisk(tEffectOnMainRisk);
			/* 该字段属于借用,用于保存主险的公式 */
			mLLToClaimDutySchema
					.setRiskCalCode(tMRiskLCGetSchema.getOperator());
		}
		
        if (tSubRiskFlag.equals("S") && (tEffectOnMainRisk.equals("02"))) {

            mLLToClaimDutySchema.setEffectOnMainRisk(tEffectOnMainRisk);
        }

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0
		 * 判断该险种的计算保额，是取基本保额，还是有效保额 13.匹配时需要取基本保额当作有效保额的险种[107],用于匹配
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		double tDAvaAmnt = 0;
		boolean tBAmntFlag = true;

		if (mLLClaimPubFunBL.getLMRiskSort(tRiskCode, "13")) {
			tBAmntFlag = false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0 没有发生过保全业务
		 * 1.如果是附险并影响主险,那么取主险的保额. 2.否则正常计算
		 * 
		 * 有效保额 = 标准给付金额 - 已领金额 有效保额 = 基本保额
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		if (mLPEdorItemSchema == null && !tInsuredFlag.equals("3")) {
			if (tSubRiskFlag.equals("S") && (tEffectOnMainRisk.equals("01"))) {
				tDAvaAmnt = tMRiskLCGetSchema.getStandMoney()
						- tMRiskLCGetSchema.getSumMoney();

				if (tBAmntFlag == false) {
					tDAvaAmnt = tMRiskLCGetSchema.getStandMoney();
				}

			} else {
				tDAvaAmnt = pLCGetSchema.getStandMoney()
						- pLCGetSchema.getSumMoney();

				if (tBAmntFlag == false) {
					tDAvaAmnt = pLCGetSchema.getStandMoney();
				}

			}
			mLLToClaimDutySchema.setPosFlag("0"); /* 0未做保全,1已做保全 */
			mLLToClaimDutySchema.setPosEdorNo(""); /* 保全批单号 */

		}
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.0 发生过保全业务
		 * 1.为附险并影响主险,判断是否有保全批单号,如果有,找LPGet
		 * 2.未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		else if (mLPEdorItemSchema != null && !tInsuredFlag.equals("3")) {
			LPGetSchema tLPGetSchema = null;

			if (tSubRiskFlag.equals("S") && tEffectOnMainRisk.equals("01")) {
				/* 取出主险的保全批改类型表 */
				LPEdorItemSchema tMRLPEdorItemSchema = mLLClaimPubFunBL
						.getLPEdorItemAfter(tMRiskLCGetSchema.getContNo(),
								tMRiskLCGetSchema.getPolNo(), this.mInsDate,
								null);// 得到保全批改项目表

				String tMRiskPosEdorNo = "";
				if (tMRLPEdorItemSchema != null) {
					tMRiskPosEdorNo = StrTool.cTrim(tMRLPEdorItemSchema
							.getEdorNo());
				}

				tLPGetSchema = mLLClaimPubFunBL.getLPGet(tMRiskPosEdorNo,
						tMRiskLCGetSchema.getPolNo(), tMRiskLCGetSchema);
				if (tLPGetSchema != null) {
					tDAvaAmnt = tLPGetSchema.getStandMoney()
							- tLPGetSchema.getSumMoney();
					if (tBAmntFlag == false) {
						tDAvaAmnt = tLPGetSchema.getStandMoney();
					}

					mLLToClaimDutySchema.setPosFlag("2"); /* 0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化 */

				} else {
					tDAvaAmnt = pLCGetSchema.getStandMoney()
							- pLCGetSchema.getSumMoney();
					if (tBAmntFlag == false) {
						tDAvaAmnt = pLCGetSchema.getStandMoney();
					}
					mLLToClaimDutySchema.setPosFlag("1"); /* 0未做保全,1已做保全 */
				}
				mLLToClaimDutySchema.setPosEdorNo(tMRiskPosEdorNo); /* 保全批单号 */
				mLLToClaimDutySchema.setPosEdorType(tMRLPEdorItemSchema
						.getEdorType()); /* 保全业务类型 */
			} else {
				/* 有效保额 = 标准给付金额 - 已领金额 */
				mLCGetSchema.setPolNo(this.mNBPolNo);
				String tPosEdorNo = StrTool
						.cTrim(mLPEdorItemSchema.getEdorNo());

				tLPGetSchema = mLLClaimPubFunBL.getLPGet(tPosEdorNo,
						this.mNBPolNo, mLCGetSchema);

				if (tLPGetSchema != null) {
					tDAvaAmnt = tLPGetSchema.getStandMoney()
							- tLPGetSchema.getSumMoney();
					if (tBAmntFlag == false) {
						tDAvaAmnt = tLPGetSchema.getStandMoney();
					}
					mLLToClaimDutySchema.setPosFlag("2"); /* 0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化 */

				} else {
					tDAvaAmnt = pLCGetSchema.getStandMoney()
							- pLCGetSchema.getSumMoney();
					if (tBAmntFlag == false) {
						tDAvaAmnt = pLCGetSchema.getStandMoney();
					}
					mLLToClaimDutySchema.setPosFlag("1"); /* 0未做保全,1已做保全 */
				}
				mLLToClaimDutySchema.setPosEdorNo(tPosEdorNo); /* 保全批单号 */
				mLLToClaimDutySchema.setPosEdorType(mLPEdorItemSchema
						.getEdorType()); /* 保全业务类型 */

			}

		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.0
		 * 如果是帐户型险种,取出本金,利息作为有效保额
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (tInsuredFlag.equals("3")) {
			String sql = " select count(*) from lmrisktoacc c, lmriskinsuacc r "
                + " where r.insuaccno = c.insuaccno and r.acckind = '2' "
                + " and c.riskcode = '" + "?riskcode?" +
                "' ";
			SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
			sqlbv18.sql(sql);
			sqlbv18.put("riskcode", mLLToClaimDutySchema.getRiskCode());
			
		   ExeSQL tExeSQL = new ExeSQL();
		   int tTLCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv18));
		   if (tTLCount > 0) { //投连产品			 
		       tDAvaAmnt = getInsureAcc_TL(pLCGetSchema);	       
		   } else { //非投连产品
			   tDAvaAmnt = getInsureAcc(pLCGetSchema);
		   }
			
			mLLToClaimDutySchema.setPosFlag("0"); /* 0未做保全,1已做保全 */
			mLLToClaimDutySchema.setPosEdorNo(""); /* 保全批单号 */
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No9.0 判断是否是分红险
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		double tDYearBonus = 0; // 年度红利
		double tDFinalBonus = 0; // 终了红利
		if (mLLClaimPubFunBL.getBonus(mLCPolSchema).equals("Y")) {
			tPolNo = StrTool.cTrim(mLCPolSchema.getPolNo());
			tDYearBonus = mLLClaimPubFunBL.getYearBonus(tPolNo, tRiskCode,
					this.mInsDate);
			if (tDYearBonus == -1) {
				mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
				tDYearBonus = 0;
			}
			mLLToClaimDutySchema.setYearBonus(tDYearBonus); /* 年度红利 */

			/**
			 * 2008-12-11 zhangzheng
			 * 终了红利属于英式分红,MS采取的是美式分红,所以封住这段处理逻辑,以免报错
			 */
//			tDFinalBonus = mLLClaimPubFunBL.getFinalBonus(tPolNo, tRiskCode,
//					this.mInsDate);
//			if (tDFinalBonus == -1) {
//				mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
//				tDFinalBonus = 0;
//			}
			mLLToClaimDutySchema.setEndBonus(tDFinalBonus); /* 终了红利 */
		}

		else {
			mLLToClaimDutySchema.setYearBonus("0"); /* 年度红利 */
			mLLToClaimDutySchema.setEndBonus("0"); /* 终了红利 */
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No9.1
		 * 如果RiskSort=12，附加险等同于主险[293,294]，用于匹配时计算主险的终了红利 先找出主险所在的LCPol
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		if (mLLClaimPubFunBL.getLMRiskSort(tRiskCode, "12")) {
			LCPolSchema tMRLCPolSchema = mLLClaimPubFunBL
					.getLCPolForMRisk(mLCPolSchema.getContNo());
			if (tMRLCPolSchema == null) {
				this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
				return false;
			}

			tDFinalBonus = mLLClaimPubFunBL.getFinalBonus(tMRLCPolSchema
					.getPolNo(), tRiskCode, this.mInsDate);
			if (tDFinalBonus == -1) {
				mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
				tDFinalBonus = 0;
			}
			mLLToClaimDutySchema.setEndBonus(tDFinalBonus); /* 终了红利 */
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No10.0
		 * 如果有效保额小于等于0,则被过滤掉,不再匹配
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		tDYearBonus = mLLToClaimDutySchema.getYearBonus();
		if (tDAvaAmnt + tDYearBonus <= 0) {
			logger.debug("--------------------------------------------------------------------------------------------------");
			logger.debug("-----合同号[" + tContNo + "]的有效保额为[" + tDAvaAmnt
					+ "]小于等于0，将被过滤掉-----\n");
			logger.debug("--------------------------------------------------------------------------------------------------");
			mErrors.addOneError("合同号[" + tContNo + "]的有效保额为[" + tDAvaAmnt
					+ "]小于等于0，将被过滤掉!!!");
			return false;
		}
		mLLToClaimDutySchema.setAmnt(tDAvaAmnt); // 有效保额

		/* 缴费宽限期 */
		mLLToClaimDutySchema.setGracePeriod(mLLClaimPubFunBL
				.getGracePeriod(mLCPolSchema.getRiskCode()));

		/* LCGet表的该字段属于借用，给付类型,00- 投保人,01-被保人,02-受益人，03-连带被保人 */
		mLLToClaimDutySchema.setCasePolType(pLCGetSchema.getState());

		/* LCGet表的该字段属于借用，用于存放出险人编号 */
		mLLToClaimDutySchema.setCustomerNo(pLCGetSchema.getInsuredNo());

		/* 预付标志,0没有预付,1已经预付 */
		mLLToClaimDutySchema.setPrepayFlag("0");

		/* 预付金额 */
		mLLToClaimDutySchema.setPrepaySum("0");
		/* 加保后的保额 */
		mLLToClaimDutySchema.setAddAmnt("0");

		/* 做过续期抽档之后,把原号保存起来,用于后续业务 */
		mLLToClaimDutySchema.setNBPolNo(this.mNBPolNo);

		mLLToClaimDutySet.add(mLLToClaimDutySchema);

		return true;
	}

	/**
	 * 得到主险所在的LCGet信息
	 * 
	 * @return
	 */
	private LCGetSchema getMRiskLCGetSchema(LCGetSchema pLCGetSchema) {

		logger.debug("--------------------得到主险所在的LCGet信息-----getMRiskLCGetSchema-----开始--------------------\n");

		String tConNo = StrTool.cTrim(pLCGetSchema.getContNo());
		String tGetDutyKind = pLCGetSchema.getGetDutyKind();
		LCPolSchema tLCPolSchema=mLLClaimPubFunBL.getLCPol(pLCGetSchema.getPolNo());

		String tSql = "";
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 根据附加险的合同号找出主险的PolNo
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		tSql = "select * from LCPol where 1=1" + " and ContNo = '" + "?ContNo?"
				+ "'" + " and PolNo = MainPolNo"
		// +" and RiskCode in (select riskcode from LMRiskApp where SubRiskFlag
		// in ('M') )"
		;
		SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
		sqlbv19.sql(tSql);
		sqlbv19.put("ContNo", tConNo);
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv19);

		logger.debug("------------------------------------------------------");
		logger.debug("--查找合同号:[" + tConNo + "]的主险信息[" + tLCPolSet.size()
				+ "]：" + tSql);
		logger.debug("------------------------------------------------------");

		if (tLCPolDB.mErrors.needDealError()) {
			mErrors.addOneError("查找合同号:[" + tConNo + "]的主险信息失败,不能进行理赔计算!"
					+ tLCPolDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCPolSet.size() != 1) {
			mErrors.addOneError("查找合同号:[" + tConNo + "]的主险信息为["
					+ tLCPolSet.size() + "]个,不唯一,不能进行理算!!!");
			return null;
		}

		String tPolNo = StrTool.cTrim(tLCPolSet.get(1).getPolNo());
		
		/**
		 * 2009-03-19 zhangzheng
		 * 附加险影响主险的险种的给付责任是扩展主险的给付责任,所以不能以附加险的给付责任类型来查询主险的给付责任
		 */
		LMRiskSortDB  tLMRiskSortDB=new LMRiskSortDB();
		tLMRiskSortDB.setRiskCode(tLCPolSchema.getRiskCode());
		tLMRiskSortDB.setRiskSortType("21");//附加险影响主险
		tLMRiskSortDB.setRiskSortValue(tGetDutyKind.substring(1));
		LMRiskSortSet tLMRiskSortSet=tLMRiskSortDB.query();
		
		if (tLMRiskSortDB.mErrors.needDealError()) {
			mErrors.addOneError("查找险种号:[" + tLCPolSchema.getPolNo() + "]的附加险影响主险描述信息失败,不能进行理赔计算!"
					+ tLMRiskSortDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLMRiskSortSet.size() != 1) {
			mErrors.addOneError("查找险种号:[" + tLCPolSchema.getPolNo() + "]的附加险影响主险描述信息信息为["
					+ tLMRiskSortSet.size() + "]个,不唯一,不能进行理算!!!");
			return null;
		}
		
		String mGetDutyKind=tGetDutyKind.substring(0,1)+tLMRiskSortSet.get(1).getRemark().trim();//关联的主险理赔类型

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
		 * 根据主险的PolNo和附加险所在理赔类型,在定义表找出相应的GetDutyCode信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		tSql = "select * from LCGet where 1=1 " + " and PolNo = '" + "?PolNo?"
				+ "'" + " and GetDutyCode in "
				+ " (select GetDutyCode from lmdutygetclm where getdutykind ='"
				+ "?getdutykind?" + "')";
		SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
		sqlbv20.sql(tSql);
		sqlbv20.put("PolNo", tPolNo);
		sqlbv20.put("getdutykind", mGetDutyKind);
		LCGetDB tLCGetDB = new LCGetDB();
		LCGetSet tLCGetSet = tLCGetDB.executeQuery(sqlbv20);

		logger.debug("------------------------------------------------------");
		logger.debug("--查找险种号:[" + tPolNo + "]的领取项信息[" + tLCGetSet.size()
				+ "]：" + tSql);
		logger.debug("------------------------------------------------------");

		if (tLCGetDB.mErrors.needDealError()) {
			mErrors.addOneError("查找主险保单号:[" + tPolNo + "]的领取项信息失败!"
					+ tLCGetDB.mErrors.getError(0).errorMessage);
			return null;
		}

		if (tLCGetSet.size() != 1) {
			mErrors.addOneError("查找主险保单号:[" + tPolNo + "]的领取项信息为["
					+ tLCGetSet.size() + "]个,不唯一,不能进行理算!!!");
			return null;
		}

		LCGetSchema tLCGetSchema = null;
		tLCGetSchema = tLCGetSet.get(1);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0 找出主险所在公式
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LMDutyGetClmDB tLMDutyGetClmDB = new LMDutyGetClmDB();
		tLMDutyGetClmDB.setGetDutyKind(mGetDutyKind);
		tLMDutyGetClmDB.setGetDutyCode(tLCGetSchema.getGetDutyCode());
		if (!tLMDutyGetClmDB.getInfo()) {
			mErrors.addOneError("查找理赔类型:[" + mGetDutyKind + "],给付责任编码:["
					+ tLCGetSchema.getGetDutyCode() + "],的产品定义信息失败!");
			return null;
		}

		LMDutyGetClmSchema tLMDutyGetClmSchema = tLMDutyGetClmDB.getSchema();

		// 保项计算公式代码
		String t_CalDutyCode = StrTool.cTrim(tLMDutyGetClmSchema.getCalCode());
		tLCGetSchema.setOperator(t_CalDutyCode);/* 该字段属于借用,用于保存主险的公式 */

		logger.debug("\n--------------------得到主险所在的LCGet信息-----getMRiskLCGetSchema-----结束--------------------");
		return tLCGetSchema;
	}

	/**
	 * 计算帐户型的有效保额（账户价值）
	 * 
	 * @return
	 */
	private double getInsureAcc(LCGetSchema pLCGetSchema) {

		logger.debug("============================开始========帐户型险种保额计算============================\n");

		String tContNo = StrTool.cTrim(pLCGetSchema.getContNo());
		String tPolNo = StrTool.cTrim(pLCGetSchema.getPolNo());

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0
		 * 查询LCInsureAcc保险帐户表中的所有数据 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		tLCInsureAccDB.setPolNo(tPolNo);
		LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.query();

		if (tLCInsureAccSet == null || tLCInsureAccSet.size() == 0) {
			this.mErrors.addOneError("没有查询到合同号[" + tContNo + "],保单号[" + tPolNo
					+ "]的帐户信息,理算发生错误!");
			return 0;
		}

		String lastBalaDate = tLCInsureAccSet.get(1).getBalaDate();
		logger.debug("保单" + tPolNo + "最近一次的结算日期是" + lastBalaDate);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
		 * 循环处理LCInsureAcc保险帐户表中的所有数据,计算账户价值
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		double sumInsuAccBala = 0.0;// 当前保单的账户价值
		double drawInsuAccBala = 0.0;// 已领取的账户价值
		LCInsureAccSchema tLCInsureAccSchema = null;// 保险帐户表
		LCInsureAccSet tdLCInsureAccSet = new LCInsureAccSet();// 保险帐户表
		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();// 保险帐户轨迹表
		InsuAccBala tInsuAccBala = new InsuAccBala();
		TransferData tTransferData = new TransferData();

		// 立案日期和出险日期都在两个结算日之间，不需要进行回算
		for (int i = 1; i <= tLCInsureAccSet.size(); i++) {
			tLCInsureAccSchema = tLCInsureAccSet.get(i);
			logger.debug("保单" + tPolNo + "的第" + i + "账户是"
					+ tLCInsureAccSchema.getInsuAccNo());
			tTransferData.setNameAndValue("InsuAccNo", tLCInsureAccSchema
					.getInsuAccNo());
			tTransferData.setNameAndValue("PolNo", tLCInsureAccSchema
					.getPolNo());
			tTransferData.setNameAndValue("BalaDate", mInsDate);

			VData tVData = new VData();
			tVData.add(mGlobalInput);
			tVData.add(tTransferData);

			if (!tInsuAccBala.submitData(tVData, "CashValue")) {
				mErrors.copyAllErrors(tInsuAccBala.mErrors);
				return 0;
			}

			tVData = tInsuAccBala.getResult();
			LCInsureAccSchema mLCInsureAccSchema = (LCInsureAccSchema) tVData
					.getObjectByObjectName("LCInsureAccSchema", 0);

			sumInsuAccBala += tLCInsureAccSchema.getInsuAccBala();
		}

		logger.debug("保单" + tPolNo + "的账户价值是" + sumInsuAccBala);

		logger.debug("\n============================结束========帐户型险种保额计算============================");

		return sumInsuAccBala;

	}
	
	/** 计算帐户型投资连结产品的保额，帐户价值=单位数*理算日期下一个资产评估日的单位价格
	    * @param pLCGetSchema LCGetSchema
	    * @return double
	    */
	   private double getInsureAcc_TL(LCGetSchema pLCGetSchema) {
		   double tReturn = 0.00; //定义返回值
		   
		 //是投连险
	    	double tSumAccBala=0;
	    	ExeSQL tExeSQL = new ExeSQL();
	    	//判断是否进行了结算
	    	String sql = "select (case when count(*) is null then 0 else count(*) end) from LOPolAfterDeal where busytype='CL' and AccAlterNo='"+"?AccAlterNo?"
				+"' and AccAlterType='4' order by state desc";
	    	SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
	    	sqlbv21.sql(sql);
	    	sqlbv21.put("AccAlterNo", mLLToClaimDutySchema.getCaseNo());
	    	int tCount1=Integer.parseInt(tExeSQL.getOneValue(sqlbv21));
	    	if(tCount1>0)
		    {
	    		sql="select currency,sum(money) from lcinsureacctrace where accalterno='"+"?caseno?"+"'"
	    			+" and AccAlterType='4' and polno = '" + "?polno?" +"' and payplancode in ( select payplancode from LMDutyPayRela where dutycode ='"+"?dutycode?"+"') group by currency";
	    		SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
		    	sqlbv22.sql(sql);
		    	sqlbv22.put("caseno", mLLToClaimDutySchema.getCaseNo());
		    	sqlbv22.put("polno", pLCGetSchema.getPolNo());
		    	sqlbv22.put("dutycode", pLCGetSchema.getDutyCode());
	    		SSRS tSSRS = new SSRS();
				tSSRS = tExeSQL.execSQL(sqlbv22);
				if (tSSRS.getMaxRow() > 0) {
					LDExch tLDExch = new LDExch();
					for(int i=1;i<=tSSRS.getMaxRow();i++)
					{
						if(tSSRS.GetText(i, 1).equals(pLCGetSchema.getCurrency()))
							tSumAccBala = tSumAccBala+ Double.parseDouble(tSSRS.GetText(i, 2))*(-1);	
						else
							tSumAccBala = tSumAccBala+ tLDExch.toOtherCur(tSSRS.GetText(i, 1), pLCGetSchema.getCurrency(), PubFun.getCurrentDate(), Double.parseDouble(tSSRS.GetText(i, 2)))*(-1);	
					}								
				}
		    }
	    	else
	    	{
	    		//如果还没有计价则取估算账户价值
	    		DealInsuAccPrice tDealInsuAccPrice= new DealInsuAccPrice(mGlobalInput);
				tSumAccBala=tDealInsuAccPrice.calOnePrice(pLCGetSchema.getPolNo(),pLCGetSchema.getDutyCode(), PubFun.getCurrentDate(),pLCGetSchema.getCurrency());
	    	}
	    	
	       tReturn = Arith.round(tSumAccBala, 2);
	       logger.debug("=========================险种保单号=[" + pLCGetSchema.getPolNo() + 
	    		   				"]责任=["+pLCGetSchema.getDutyCode()+
	                          "]涉及帐户的理赔金额为=[" + tReturn +
	                          "]======================");
	       logger.debug("\n============================结束getInsureAcc_TL========帐户型投连险种帐户价值计算============================");
		   return tReturn;
	   }

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－进行匹配后保项的添加－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－－－－得到医疗单证信息－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */
	private boolean getLLToClaimDutyFee(String pClaimType,String tCustomerNo) {

		// 当理赔类型为100意外医疗，200疾病医疗
		if (pClaimType.equals("100") || pClaimType.equals("200")) {
			
			// 医疗费用明细
			if (!getLLCaseReceiptInfo(pClaimType,tCustomerNo)) {
				return false;
			}

			// 特种费用(如救援费用)
			if (!getLLOtherFactorInfo("T",tCustomerNo)) {
				return false;
			}

			
		}

		// 当理赔类型为101意外伤残,201伤残医疗,103  意外高残,203  疾病高残
		if (pClaimType.equals("101") || pClaimType.equals("201")||pClaimType.equals("103") || pClaimType.equals("203")) {
			// 分案疾病伤残明细
			if (!getLLCaseInfo(tCustomerNo)) {
				return false;
			}
		}

		/*
		 * 2008-10-17 根据MS的医疗账单类型没有手术信息表,D--手术,E--特定疾病,F--特定给付，而且代码类别已不是D,E,F，在此将这段屏蔽掉
		 */
		/*
		// 当理赔类型为105意外特种疾病，205疾病特种疾病
		if (pClaimType.equals("105") || pClaimType.equals("205")) {
			// 手术信息表,D--手术,E--特定疾病,F--特定给付
			if (!getLLOperationInfo("D")) {
				return false;
			}

			// 手术信息表,D--手术,E--特定疾病,F--特定给付
			if (!getLLOperationInfo("E")) {
				return false;
			}

			// 手术信息表,D--手术,E--特定疾病,F--特定给付(如产妇抚恤金)
			if (!getLLOperationInfo("F")) {
				return false;
			}

		}
		*/

		return true;
	}

	/**
	 * 目的：获取LLCaseReceipt门诊，住院医疗费用明细表信息
	 * 
	 * @return
	 */
	private boolean getLLCaseReceiptInfo(String pClaimType,String pCustomerNo) {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 根据给付责任编码和理赔类型
		 * 在对应的LMDutyGetFeeRela费用表中找到符合该编码条件的医疗费用项
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tGetDutyCode = mLLToClaimDutySchema.getGetDutyCode();

		LMDutyGetFeeRelaDB tLMDutyGetFeeRelaDB = new LMDutyGetFeeRelaDB();
		tLMDutyGetFeeRelaDB.setGetDutyCode(tGetDutyCode);
		tLMDutyGetFeeRelaDB.setGetDutyKind(pClaimType);
		LMDutyGetFeeRelaSet tLMDutyGetFeeRelaSet = tLMDutyGetFeeRelaDB.query();

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
		 * 循环LMDutyGetFeeRela表中的医疗费用项 取出费用编码，然后在对应的医疗费用明细记录中进行查找
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		for (int i = 1; i <= tLMDutyGetFeeRelaSet.size(); i++) {
			LMDutyGetFeeRelaSchema tLMDutyGetFeeRelaSchema = tLMDutyGetFeeRelaSet
					.get(i);
			String tClaimFeeCode = tLMDutyGetFeeRelaSchema.getClmFeeCode();
			String tFeeItemType = tClaimFeeCode.substring(0, 1);
			String tSql = "";

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.1
			 * 如果产品中费用类型的开始头一个字符为A或B，代表需要区分门诊，住院
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */

			if (tFeeItemType.equals("A") || tFeeItemType.equals("B")|| tFeeItemType.equals("C")) {
			
				tSql = "select * from LLCaseReceipt where 1 = 1"
						+ " and ClmNo = '"
						+ "?ClmNo?"
						+ "'"
						+ " and FeeItemCode like concat('"
						+ "?FeeItemCode?"
						+ "','%')"
						+ " and MainFeeNo in (select MainFeeNo from LLFeeMain where 1 = 1 "
						+ " and ClmNo = '" + "?ClmNo?" + "'" + ")"
		                + " and CustomerNo = '"+"?CustomerNo?"+"'";
				
				
				logger.debug("--账单查询:案件"+ this.mClmNo+",GetDutyCode:"+tGetDutyCode+",tFeeItemType:"
						+tFeeItemType+",tClaimFeeCode:"+tClaimFeeCode+",tClaimFeeName:"+tLMDutyGetFeeRelaSchema.getClmFeeName()+":"+tSql);
				SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
				sqlbv23.sql(tSql);
				sqlbv23.put("ClmNo", this.mClmNo);
				sqlbv23.put("FeeItemCode", tClaimFeeCode);
				sqlbv23.put("CustomerNo", pCustomerNo);
				LLCaseReceiptDB tLLCaseReceiptDB = new LLCaseReceiptDB();
				LLCaseReceiptSet tLLCaseReceiptSet = tLLCaseReceiptDB
						.executeQuery(sqlbv23);

				if (tLLCaseReceiptDB.mErrors.needDealError()) {
					// @@错误处理
					CError.buildErr(this, "查询医疗费用明细信息失败,"+tLLCaseReceiptDB.mErrors.getLastError());
					logger.debug("------------------------------------------------------");
					logger.debug("--LLClaimCalMatchBL.getLLCaseReceiptInfo()--在医疗费用主信息中查询失败，不能理算!"
									+ tSql);
					logger.debug("------------------------------------------------------");
					return false;
				}
				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.0
				 * 循环LLCaseReceipt表中的医疗费用项
				 * 
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				String tMainFeeNoNew = "";
				String tMainFeeNoOld = "";
				double tRealAdjSum=0.0;//账单金额-自费/自付金额后的实际参加理算的金额
				LLFeeMainSchema tLLFeeMainSchema = new LLFeeMainSchema();
				LLCaseReceiptSchema tLLCaseReceiptSchema =null;
				

				for (int j = 1; j <= tLLCaseReceiptSet.size(); j++) {
		
					tLLCaseReceiptSchema = new LLCaseReceiptSchema();
					tLLCaseReceiptSchema= tLLCaseReceiptSet.get(j);

					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.1 找出对应的费用主记录,
					 * 如果上一次帐单号和本次不一致，则需要重新进行查询
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */
					tMainFeeNoNew = StrTool.cTrim(tLLCaseReceiptSchema
							.getMainFeeNo());

					if (!tMainFeeNoNew.equals(tMainFeeNoOld)) {
						tSql = "select * from LLFeeMain where 1 = 1 "
								+ " and ClmNo = '" + "?ClmNo?" + "'"
								+ " and trim(MainFeeNo) = '" + "?MainFeeNo?" + "'";
					
						logger.debug("--查询赔案"+tLLCaseReceiptSchema.getClmNo()+"的医疗费用主信息(LLFeeMain)sql:"+tSql);
						SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
						sqlbv24.sql(tSql);
						sqlbv24.put("ClmNo", this.mClmNo);
						sqlbv24.put("MainFeeNo", tMainFeeNoNew);
						LLFeeMainDB tLLFeeMainDB = new LLFeeMainDB();
						LLFeeMainSet tLLFeeMainSet = tLLFeeMainDB
								.executeQuery(sqlbv24);

						if (tLLFeeMainSet.size() > 0) {
							tLLFeeMainSchema = tLLFeeMainSet.get(1);

						} else {
							// @@错误处理
							CError.buildErr(this, "查询医疗费用主信息失败,"+tLLFeeMainDB.mErrors.getLastError());
							logger.debug("------------------------------------------------------");
							logger.debug("--LLClaimCalMatchBL.getLLCaseReceiptInfo()--在医疗费用主信息中没有费用主记录，不能理算!"
											+ tSql);
							logger.debug("------------------------------------------------------");
							return false;
						}
					}

					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No3.2
					 * 加入到LLToClaimDutyFee中
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */
					tRealAdjSum=0.0;//重置初始化金额
					tRealAdjSum=tLLCaseReceiptSchema.getAdjSum()-tLLCaseReceiptSchema.getSelfAmnt();
					logger.debug("clmno(赔案):"+this.mClmNo+",FEEDETAILNO(账单明细号):"+tLLCaseReceiptSchema.getFeeDetailNo()
							 		+",adjSum(待理算金额):"+tLLCaseReceiptSchema.getAdjSum()+",selfAmnt(自费/自付金额):"+tLLCaseReceiptSchema.getSelfAmnt()
							 		+",realAdjSum(减去自费/自付金额后的实际参与计算的理赔金额):"+tRealAdjSum);
					
					mLLToClaimDutyFeeSchema = new LLToClaimDutyFeeSchema();

					mLLToClaimDutyFeeSchema.setClmNo(this.mClmNo); // 赔案号
					mLLToClaimDutyFeeSchema.setCaseNo(this.mClmNo); // 分案号
					mLLToClaimDutyFeeSchema.setPolNo(mLLToClaimDutySchema
							.getPolNo()); // 保单号

					mLLToClaimDutyFeeSchema.setGetDutyType(mLLToClaimDutySchema
							.getGetDutyKind()); // 给付责任类型
					mLLToClaimDutyFeeSchema.setGetDutyCode(mLLToClaimDutySchema
							.getGetDutyCode()); // 给付责任编码
		            mLLToClaimDutyFeeSchema.setCustomerNo(tLLCaseReceiptSchema.getCustomerNo());//出险人编码

					mLLToClaimDutyFeeSchema.setDutyFeeType(tLLCaseReceiptSchema
							.getFeeItemType());// 费用类型
					mLLToClaimDutyFeeSchema.setDutyFeeCode(tLLCaseReceiptSchema
							.getFeeItemCode());// 费用代码
					mLLToClaimDutyFeeSchema.setDutyFeeName(tLLCaseReceiptSchema
							.getFeeItemName());// 费用名称

					mLLToClaimDutyFeeSchema.setDutyFeeStaNo(tLLCaseReceiptSchema
							.getFeeDetailNo());// 账单费用明细序号

					mLLToClaimDutyFeeSchema.setKindCode(mLLToClaimDutySchema
							.getKindCode()); // 险类代码
					mLLToClaimDutyFeeSchema.setRiskCode(mLLToClaimDutySchema
							.getRiskCode()); // 险种代码
					mLLToClaimDutyFeeSchema.setRiskVer(mLLToClaimDutySchema
							.getRiskVer()); // 险种版本号
					mLLToClaimDutyFeeSchema.setPolMngCom(mLLToClaimDutySchema
							.getPolMngCom()); // 保单管理机构

					mLLToClaimDutyFeeSchema.setHosID(tLLFeeMainSchema
							.getHospitalCode()); // 医院编号
					mLLToClaimDutyFeeSchema.setHosName(tLLFeeMainSchema
							.getHospitalName()); // 医院名称
					mLLToClaimDutyFeeSchema.setHosGrade(tLLFeeMainSchema
							.getHospitalGrade()); // 医院等级

					mLLToClaimDutyFeeSchema.setStartDate(tLLCaseReceiptSchema
							.getStartDate()); // 开始时间
					mLLToClaimDutyFeeSchema.setEndDate(tLLCaseReceiptSchema
							.getEndDate()); // 结束时间
					mLLToClaimDutyFeeSchema.setDayCount(tLLCaseReceiptSchema
							.getDayCount()); // 天数

					// mLLToClaimDutyFeeSchema.setDefoRate() //残疾给付比例
					// mLLToClaimDutyFeeSchema.setRealRate() //实际给付比例

					if(tLLCaseReceiptSchema.getCurrency().equals(mLLToClaimDutySchema.getCurrency()))
					{
						mLLToClaimDutyFeeSchema.setOriSum(tLLCaseReceiptSchema.getFee()); // 原始金额
						//String tDutyFeeCode = mLLToClaimDutyFeeSchema.getDutyFeeCode().substring(0, 2);

						mLLToClaimDutyFeeSchema.setAdjSum(tRealAdjSum);// 减去自费/自付金额后的实际参与计算的理赔金额
						mLLToClaimDutyFeeSchema.setCalSum(tRealAdjSum);// 减去自费/自付金额后的实际参与计算的理赔金额
					}						
					else
					{
						mLLToClaimDutyFeeSchema.setOriSum(mLDExch.toOtherCur(tLLCaseReceiptSchema.getCurrency(),mLLToClaimDutySchema.getCurrency(),PubFun.getCurrentDate(),tLLCaseReceiptSchema.getFee())); // 原始金额
						mLLToClaimDutyFeeSchema.setAdjSum(mLDExch.toOtherCur(tLLCaseReceiptSchema.getCurrency(),mLLToClaimDutySchema.getCurrency(),PubFun.getCurrentDate(),tRealAdjSum));// 减去自费/自付金额后的实际参与计算的理赔金额
						mLLToClaimDutyFeeSchema.setCalSum(mLDExch.toOtherCur(tLLCaseReceiptSchema.getCurrency(),mLLToClaimDutySchema.getCurrency(),PubFun.getCurrentDate(),tRealAdjSum));// 减去自费/自付金额后的实际参与计算的理赔金额
					}					
					
					mLLToClaimDutyFeeSchema.setCurrency(mLLToClaimDutySchema.getCurrency());
						
					mLLToClaimDutyFeeSchema.setAdjReason(tLLCaseReceiptSchema
							.getAdjReason()); // 调整原因
					mLLToClaimDutyFeeSchema.setAdjRemark(tLLCaseReceiptSchema
							.getAdjRemark()); // 调整备注

					mLLToClaimDutyFeeSchema.setOutDutyAmnt(0); // 免赔额
					mLLToClaimDutyFeeSchema.setDutyCode(mLLToClaimDutySchema
							.getDutyCode());
					/* 做过续期抽档之后,把原号保存起来,用于后续业务 */
					mLLToClaimDutyFeeSchema.setNBPolNo(this.mNBPolNo);
					mLLToClaimDutyFeeSchema.setCustomerNo(pCustomerNo);

					mLLToClaimDutyFeeSet.add(mLLToClaimDutyFeeSchema);

					tMainFeeNoOld = tMainFeeNoNew;
					
					//释放强引用
					mLLToClaimDutyFeeSchema=null;
					tLLCaseReceiptSchema=null;

				}// 内层FOR循环

			}

		}// 外层FOR循环

		return true;
	}

	/**
	 * 目的：获取LLCaseInfo分案疾病伤残明细信息
	 * 
	 * @return
	 */
	private boolean getLLCaseInfo(String pCustomerNo) {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 根据给付责任编码和理赔类型
		 * 在对应的费用表中找到符合该编码条件的医疗费用项 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLCaseInfoDB tLLCaseInfoDB = new LLCaseInfoDB();
		tLLCaseInfoDB.setClmNo(this.mClmNo);
        tLLCaseInfoDB.setCustomerNo(pCustomerNo);
		LLCaseInfoSet tLLCaseInfoSet = tLLCaseInfoDB.query();
		
		LMDutyGetFeeRelaDB tLMDutyGetFeeRelaDB=null;
		LMDutyGetFeeRelaSchema tLMDutyGetFeeRelaSchema=null;

		for (int i = 1; i <= tLLCaseInfoSet.size(); i++) {

			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
			 * 判断产品定义中是否定义了该费用信息
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tGetDutyKind = mLLToClaimDutySchema.getGetDutyKind();
			String tGetDutyCode = mLLToClaimDutySchema.getGetDutyCode();
			String tDutyFeeCode = tLLCaseInfoSet.get(i).getDefoType();

			tLMDutyGetFeeRelaDB = new LMDutyGetFeeRelaDB();
			tLMDutyGetFeeRelaDB.setGetDutyKind(tGetDutyKind);
			tLMDutyGetFeeRelaDB.setGetDutyCode(tGetDutyCode);
			tLMDutyGetFeeRelaDB.setClmFeeCode(tDutyFeeCode);

			LMDutyGetFeeRelaSet tLMDutyGetFeeRelaSet = tLMDutyGetFeeRelaDB
					.query();
			if (tLMDutyGetFeeRelaSet.size() == 1) {
				
				mLLToClaimDutyFeeSchema = new LLToClaimDutyFeeSchema();

				mLLToClaimDutyFeeSchema.setClmNo(this.mClmNo); // 赔案号
				mLLToClaimDutyFeeSchema.setCaseNo(this.mClmNo); // 分案号
				mLLToClaimDutyFeeSchema.setPolNo(mLLToClaimDutySchema.getPolNo()); // 保单号

				mLLToClaimDutyFeeSchema.setGetDutyType(mLLToClaimDutySchema
						.getGetDutyKind()); // 给付责任类型
				mLLToClaimDutyFeeSchema.setGetDutyCode(mLLToClaimDutySchema
						.getGetDutyCode()); // 给付责任编码

				mLLToClaimDutyFeeSchema.setDutyFeeType("L"); // 费用类型为伤残
				mLLToClaimDutyFeeSchema.setDutyFeeCode(tLLCaseInfoSet.get(i)
						.getDefoCode()); // 费用代码
				mLLToClaimDutyFeeSchema.setDutyFeeName(tLLCaseInfoSet.get(i)
						.getDefoName()); // 费用名称

				mLLToClaimDutyFeeSchema.setDutyFeeStaNo(tLLCaseInfoSet.get(i)
						.getSerialNo()); // 账单费用明细序号

				mLLToClaimDutyFeeSchema.setKindCode(mLLToClaimDutySchema
						.getKindCode()); // 险类代码
				mLLToClaimDutyFeeSchema.setRiskCode(mLLToClaimDutySchema
						.getRiskCode()); // 险种代码
				mLLToClaimDutyFeeSchema.setRiskVer(mLLToClaimDutySchema
						.getRiskVer()); // 险种版本号
				mLLToClaimDutyFeeSchema.setPolMngCom(mLLToClaimDutySchema
						.getPolMngCom()); // 保单管理机构
				mLLToClaimDutyFeeSchema.setStartDate(tLLCaseInfoSet.get(i)
						.getJudgeDate()); // 伤残鉴定时间
				mLLToClaimDutyFeeSchema
						.setEndDate(tLLCaseInfoSet.get(i).getJudgeDate()); // 伤残鉴定时间

				mLLToClaimDutyFeeSchema
						.setDefoType(tLLCaseInfoSet.get(i).getDefoType()); // 伤残类型
				mLLToClaimDutyFeeSchema
						.setDefoCode(tLLCaseInfoSet.get(i).getDefoCode()); // 伤残代码
				mLLToClaimDutyFeeSchema.setDefoeName(tLLCaseInfoSet.get(i)
						.getDefoName()); // 伤残级别名称

				mLLToClaimDutyFeeSchema.setDefoRate(tLLCaseInfoSet.get(i)
						.getDeformityRate()); // 残疾给付比例
				mLLToClaimDutyFeeSchema
						.setRealRate(tLLCaseInfoSet.get(i).getRealRate()); // 实际给付比例

				mLLToClaimDutyFeeSchema.setAdjReason(tLLCaseInfoSet.get(i)
						.getAdjReason()); // 调整原因
				mLLToClaimDutyFeeSchema.setAdjRemark(tLLCaseInfoSet.get(i)
						.getAdjRemark()); // 调整备注

				mLLToClaimDutyFeeSchema.setOutDutyAmnt(0);
				mLLToClaimDutyFeeSchema.setDutyCode(mLLToClaimDutySchema
						.getDutyCode());
				mLLToClaimDutyFeeSchema.setCurrency(mLLToClaimDutySchema.getCurrency());
				
	            mLLToClaimDutyFeeSchema.setCustomerNo(tLLCaseInfoSet.get(i)
	            		.getCustomerNo());//出险人客户号
	            
				/* 做过续期抽档之后,把原号保存起来,用于后续业务 */
				mLLToClaimDutyFeeSchema.setNBPolNo(this.mNBPolNo);
				mLLToClaimDutyFeeSchema.setCustomerNo(pCustomerNo);
				
				mLLToClaimDutyFeeSet.add(mLLToClaimDutyFeeSchema);
				
				
				mLLToClaimDutyFeeSchema=null;
			}
			

		}
		
		//释放强引用
		tLLCaseInfoSet=null;
		tLLCaseInfoDB=null;
		
		return true;
	}

	/**
	 * 目的：获取LLOperation手术信息表信息
	 * 
	 * @return
	 */
	private boolean getLLOperationInfo(String pType) {

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 找出手术和特殊疾病信息
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		LLOperationDB tLLOperationDB = new LLOperationDB();
		tLLOperationDB.setClmNo(this.mClmNo);
		tLLOperationDB.setOperationType(pType);
		LLOperationSet tLLOperationSet = tLLOperationDB.query();
		
		LMDutyGetFeeRelaDB tLMDutyGetFeeRelaDB=null;
		LMDutyGetFeeRelaSchema tLMDutyGetFeeRelaSchema=null;

		for (int i = 1; i <= tLLOperationSet.size(); i++) {
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
			 * 判断产品定义中是否定义了该费用信息
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tGetDutyKind = mLLToClaimDutySchema.getGetDutyKind();
			String tGetDutyCode = mLLToClaimDutySchema.getGetDutyCode();
			String tDutyFeeCode = tLLOperationSet.get(i).getOperationCode();

			tLMDutyGetFeeRelaDB = new LMDutyGetFeeRelaDB();
			tLMDutyGetFeeRelaDB.setGetDutyKind(tGetDutyKind);
			tLMDutyGetFeeRelaDB.setGetDutyCode(tGetDutyCode);
			tLMDutyGetFeeRelaDB.setClmFeeCode(tDutyFeeCode);

			LMDutyGetFeeRelaSet tLMDutyGetFeeRelaSet = tLMDutyGetFeeRelaDB
					.query();
			if (tLMDutyGetFeeRelaSet.size() == 1) {
				

				mLLToClaimDutyFeeSchema = new LLToClaimDutyFeeSchema();

				mLLToClaimDutyFeeSchema.setClmNo(this.mClmNo); // 赔案号
				mLLToClaimDutyFeeSchema.setCaseNo(this.mClmNo); // 分案号
				mLLToClaimDutyFeeSchema.setPolNo(mLLToClaimDutySchema
						.getPolNo()); // 保单号

				mLLToClaimDutyFeeSchema.setGetDutyType(mLLToClaimDutySchema
						.getGetDutyKind()); // 给付责任类型
				mLLToClaimDutyFeeSchema.setGetDutyCode(mLLToClaimDutySchema
						.getGetDutyCode()); // 给付责任编码

				mLLToClaimDutyFeeSchema.setDutyFeeType(pType); // 费用类型为伤残
				mLLToClaimDutyFeeSchema.setDutyFeeCode(tLLOperationSet.get(i)
						.getOperationCode()); // 费用代码
				mLLToClaimDutyFeeSchema.setDutyFeeName(tLLOperationSet.get(i)
						.getOperationName()); // 费用名称

				mLLToClaimDutyFeeSchema.setDutyFeeStaNo(tLLOperationSet.get(i)
						.getSerialNo()); // 账单费用明细序号

				mLLToClaimDutyFeeSchema.setKindCode(mLLToClaimDutySchema
						.getKindCode()); // 险类代码
				mLLToClaimDutyFeeSchema.setRiskCode(mLLToClaimDutySchema
						.getRiskCode()); // 险种代码
				mLLToClaimDutyFeeSchema.setRiskVer(mLLToClaimDutySchema
						.getRiskVer()); // 险种版本号
				mLLToClaimDutyFeeSchema.setPolMngCom(mLLToClaimDutySchema
						.getPolMngCom()); // 保单管理机构

				mLLToClaimDutyFeeSchema.setOutDutyAmnt(0);
				mLLToClaimDutyFeeSchema.setDutyCode(mLLToClaimDutySchema
						.getDutyCode());
				mLLToClaimDutyFeeSchema.setCurrency(mLLToClaimDutySchema.getCurrency());

				mLLToClaimDutyFeeSchema.setAdjReason(tLLOperationSet.get(i)
						.getAdjReason()); // 调整原因
				mLLToClaimDutyFeeSchema.setAdjRemark(tLLOperationSet.get(i)
						.getAdjRemark()); // 调整备注

				/* 做过续期抽档之后,把原号保存起来,用于后续业务 */
				mLLToClaimDutyFeeSchema.setNBPolNo(this.mNBPolNo);
//				mLLToClaimDutyFeeSchema.setCustomerNo(pCustomerNo);
				
				mLLToClaimDutyFeeSet.add(mLLToClaimDutyFeeSchema);
				
				
				mLLToClaimDutyFeeSchema=null;			

			}
			
			//释放强引用

			tLMDutyGetFeeRelaSet=null;
			tLMDutyGetFeeRelaDB=null;
			tGetDutyKind = null;
			tGetDutyCode = null;
			tDutyFeeCode = null;


		}
		
		tLLOperationSet=null;
		tLLOperationDB=null;
		
		return true;
	}

	/**
	 * 目的：获取LLOtherFactor其它录入要素表信息
	 * 
	 * @return
	 */
	private boolean getLLOtherFactorInfo(String pType,String pCustomerNo) {
		LLOtherFactorDB tLLOtherFactorDB = new LLOtherFactorDB();
		tLLOtherFactorDB.setClmNo(this.mClmNo);
        tLLOtherFactorDB.setCustomerNo(pCustomerNo);

		String tSql = "select * from LLOtherFactor where ClmNo='"
				+ "?ClmNo?" + "'" + " and Feeitemtype='T'";
		
		logger.debug("--LLClaimCalMatchBL.java:查询录入的特种费用(如救援费用)的sql:"+tSql);
		SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
		sqlbv25.sql(tSql);
		sqlbv25.put("ClmNo", this.mClmNo);
		LLOtherFactorSet tLLOtherFactorSet = tLLOtherFactorDB
				.executeQuery(sqlbv25);
		
		double tRealAdjSum=0.0;//重置初始化金额
		

		for (int i = 1; i <= tLLOtherFactorSet.size(); i++) {
			/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
			 * 判断产品定义中是否定义了该费用信息
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */

			LMDutyGetFeeRelaDB tLMDutyGetFeeRelaDB = new LMDutyGetFeeRelaDB();
			tLMDutyGetFeeRelaDB.setGetDutyKind(mLLToClaimDutySchema.getGetDutyKind());
			tLMDutyGetFeeRelaDB.setGetDutyCode(mLLToClaimDutySchema.getGetDutyCode());
			tLMDutyGetFeeRelaDB.setClmFeeCode(tLLOtherFactorSet.get(i).getFactorType());

			LMDutyGetFeeRelaSet tLMDutyGetFeeRelaSet = tLMDutyGetFeeRelaDB
					.query();
			if (tLMDutyGetFeeRelaSet.size() == 1) {
				
				
				mLLToClaimDutyFeeSchema = new LLToClaimDutyFeeSchema();
				
				tRealAdjSum=tLLOtherFactorSet.get(i).getAdjSum()-tLLOtherFactorSet.get(i).getSelfAmnt();
				logger.debug("clmno(赔案):"+this.mClmNo+",FactorCode(费用代码):"+tLLOtherFactorSet.get(i).getFactorCode()
						 		+",adjSum(待理算金额):"+tLLOtherFactorSet.get(i).getAdjSum()+",selfAmnt(自费/自付金额):"+tLLOtherFactorSet.get(i).getSelfAmnt()
						 		+",realAdjSum(减去自费/自付金额后的实际参与计算的理赔金额):"+tRealAdjSum);
				
				mLLToClaimDutyFeeSchema.setClmNo(this.mClmNo); // 赔案号
				mLLToClaimDutyFeeSchema.setCaseNo(this.mClmNo); // 分案号
				mLLToClaimDutyFeeSchema.setPolNo(mLLToClaimDutySchema
						.getPolNo()); // 保单号

				mLLToClaimDutyFeeSchema.setGetDutyType(mLLToClaimDutySchema
						.getGetDutyKind()); // 给付责任类型
				mLLToClaimDutyFeeSchema.setGetDutyCode(mLLToClaimDutySchema
						.getGetDutyCode()); // 给付责任编码

				mLLToClaimDutyFeeSchema.setDutyFeeType(pType); // 费用类型为伤残
				mLLToClaimDutyFeeSchema.setDutyFeeCode(tLLOtherFactorSet.get(i)
						.getFactorCode()); // 费用代码
				mLLToClaimDutyFeeSchema.setDutyFeeName(tLLOtherFactorSet.get(i)
						.getFactorName()); // 费用名称

				mLLToClaimDutyFeeSchema.setDutyFeeStaNo(tLLOtherFactorSet.get(i)
						.getSerialNo()); // 账单费用明细序号

				mLLToClaimDutyFeeSchema.setKindCode(mLLToClaimDutySchema
						.getKindCode()); // 险类代码
				mLLToClaimDutyFeeSchema.setRiskCode(mLLToClaimDutySchema
						.getRiskCode()); // 险种代码
				mLLToClaimDutyFeeSchema.setRiskVer(mLLToClaimDutySchema
						.getRiskVer()); // 险种版本号
				mLLToClaimDutyFeeSchema.setPolMngCom(mLLToClaimDutySchema
						.getPolMngCom()); // 保单管理机构
				
				if(tLLOtherFactorSet.get(i).getCurrency().equals(mLLToClaimDutySchema.getCurrency()))
				{
					mLLToClaimDutyFeeSchema.setAdjSum(tRealAdjSum);// 减去自费/自付金额后的实际参与计算的理赔金额
					mLLToClaimDutyFeeSchema.setCalSum(tRealAdjSum);// 减去自费/自付金额后的实际参与计算的理赔金额
				}
				else
				{
					mLLToClaimDutyFeeSchema.setAdjSum(mLDExch.toOtherCur(tLLOtherFactorSet.get(i).getCurrency(),mLLToClaimDutySchema.getCurrency(),PubFun.getCurrentDate(),tRealAdjSum));// 减去自费/自付金额后的实际参与计算的理赔金额
					mLLToClaimDutyFeeSchema.setCalSum(mLDExch.toOtherCur(tLLOtherFactorSet.get(i).getCurrency(),mLLToClaimDutySchema.getCurrency(),PubFun.getCurrentDate(),tRealAdjSum));// 减去自费/自付金额后的实际参与计算的理赔金额
				}				
				mLLToClaimDutyFeeSchema.setCurrency(mLLToClaimDutySchema.getCurrency());

				mLLToClaimDutyFeeSchema.setAdjReason(tLLOtherFactorSet.get(i)
						.getAdjReason()); // 调整原因
				mLLToClaimDutyFeeSchema.setAdjRemark(tLLOtherFactorSet.get(i)
						.getAdjRemark()); // 调整备注
				
                mLLToClaimDutyFeeSchema.setCustomerNo(tLLOtherFactorSet.get(i)
                		.getCustomerNo());//出险人客户号
				mLLToClaimDutyFeeSchema.setOutDutyAmnt(0);
				mLLToClaimDutyFeeSchema.setDutyCode(mLLToClaimDutySchema
						.getDutyCode());
				/* 做过续期抽档之后,把原号保存起来,用于后续业务 */
				mLLToClaimDutyFeeSchema.setNBPolNo(this.mNBPolNo);
				mLLToClaimDutyFeeSchema.setCustomerNo(pCustomerNo);

				mLLToClaimDutyFeeSet.add(mLLToClaimDutyFeeSchema);
				
				mLLToClaimDutyFeeSchema=null;
			}

			
			//释放强引用
			tLMDutyGetFeeRelaSet=null;
			tLMDutyGetFeeRelaDB=null;
		}

		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－－－－得到医疗单证信息－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {

		mResult.add(mLLToClaimDutyFeeSet);
		mResult.add(mLLToClaimDutySet);
		mResult.add(this.mTransferData);
		mResult.add(this.mGlobalInput);

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
			tError.moduleName = "LLClaimCalMatchFilterBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	/**
	 * 得到返回的结果集
	 * 
	 * @return
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 测试主方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

	}
}

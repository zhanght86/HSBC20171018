/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLClaimDutyKindDB;
import com.sinosoft.lis.db.LLClaimUserDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LLClaimDutyKindSchema;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.schema.LLClaimUserSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.vschema.LLClaimDutyKindSet;
import com.sinosoft.lis.vschema.LLClaimUserSet;
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
 * Description: 核赔师权限匹配类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author: zhangzheng
 * @version 1.0
 */

public class LLClaimPopedomSetBL {
private static Logger logger = Logger.getLogger(LLClaimPopedomSetBL.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private MMap map = new MMap();
	/** 往后面传输的数据库操作 */

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();

	// 前台传入变量
	private String mClmNo = "";
	private String mPopedom = ""; // 审核A，审批B
	private double mInterval = 0; // 预估金额调整差值
	// 处理变量
	private String mGetDutyKind = ""; // 最高赔付的理赔类型
	private double mRealPay = 0; // 最高赔付理赔类型的实赔金额
	private String mMaxLevel = ""; // 最高权限
	private String mPremium = ""; // 豁免
	private String mComFlag = "0"; // 权限越界标志
	private String mUser = "";// 审批人
	private String mCasePayType = "";// 案件给付类型
	private LLRegisterSchema mLLRegisterSchema = null;// 立案信息
	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();

	public LLClaimPopedomSetBL() {
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
		logger.debug("----------LLClaimPopedomSetBL Begin----------");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 检查数据合法性
		if (!checkData()) {
			return false;
		}

		/**
		 * zhangzheng 2009-02-20 按照MS新的权限管理办法进行权限处理
		 * 
		 */
		// 进行业务处理
		// if (!dealData()) {
		// return false;
		// }
		if (!dealNewData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		mInputData = null;

		logger.debug("----------LLClaimPopedomSetBL End----------");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {

		mInputData = cInputData;
		this.mOperate = cOperate;

		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		mClmNo = (String) mTransferData.getValueByName("RptNo");
		mPopedom = (String) mTransferData.getValueByName("PopedomPhase");
		// 如果是审核权限
		if (mPopedom.equals("A")) {
			String tStandpay = (String) mTransferData
					.getValueByName("standpay");
			String tAdjpay = (String) mTransferData.getValueByName("adjpay");

			double tD1 = 0.0;
			double tD2 = 0.0;
			// 求预估金额
			if (!(tStandpay == null || tStandpay.equals(""))) {
				tD1 = Double.parseDouble(tStandpay);
			}

			if (!(tAdjpay == null || tAdjpay.equals(""))) {
				tD2 = Double.parseDouble(tAdjpay);
			}

			mInterval = tD2 - tD1;
		}

		return true;
	}

	/**
	 * 校验传入的信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		String tOperator = mGlobalInput.Operator;
		if (tOperator == null || tOperator.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据[操作员编码]失败!");
			return false;
		}

		mLLRegisterSchema = mLLClaimPubFunBL.getLLRegister(mClmNo);
		logger.debug("案件:" + mLLRegisterSchema.getRgtNo() + ",案件给付类型:"
				+ mLLRegisterSchema.getCasePayType());

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealNewData() {

		// 审核
		if (mPopedom.equals("A")) {

			// 确定审核人
			mUser = ChooseNewAssessor(mLLRegisterSchema.getCasePayType(),
					mGlobalInput.ManageCom);
			logger.debug("案件" + mClmNo + "的所确定的审核人是" + mUser);
			if (mUser == null) {
				CError.buildErr(this, "无法确定审核人");
				return false;
			}

			mUser.trim();

			/**
			 * zhangzheng 2008-12-30 为了用测试用户测试流程,保证002立案之后审核人依然可以是002,特设此接口
			 */
			if (mGlobalInput.Operator.trim().equals("002")) {
				mUser = "002";
			}
		}

		// ---------------------------------------------------------------------
		// 审批
		if (mPopedom.equals("B")) {

			// 确定审批人
			mUser = ConfirmNewPopedom(mLLRegisterSchema.getCasePayType(),
					mGlobalInput.Operator);
			logger.debug("案件" + mClmNo + "的所确定的审批人是" + mUser);

			if (mUser == null) {
				CError.buildErr(this, "无法确定签批人");
				return false;
			}
			mUser.trim();
		}

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		//----------------------------------------------------------------------
		// BEG
		// 功 能 1：通过赔案号查找给付责任类型和赔付金额以及所有理赔类型最高权限级别
		// 所需变量：mClmNo，mPopedom
		// 更新变量：mGetDutyKind，mRealPay，mMaxLevel
		// 修 改一: 首次查找除去豁免权限,最后单独与最小豁免权限比较取最大
		//----------------------------------------------------------------------
		LLClaimDutyKindDB tLLClaimDutyKindDB = new LLClaimDutyKindDB();
		String sql = "select * from LLClaimDutyKind where" + " ClmNo = '"
				+ "?ClmNo?" + "'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("ClmNo", mClmNo);
		LLClaimDutyKindSet tLLClaimDutyKindSet = tLLClaimDutyKindDB
				.executeQuery(sqlbv);

		if (tLLClaimDutyKindSet == null || tLLClaimDutyKindSet.size() == 0) {
			// @@错误处理
			CError.buildErr(this, "查询赔案" + mClmNo + "的给付责任失败!");
			return false;

		} else {
			int tMax = 0; // 最高级别
			int tNo = 0; // 当前级别
			for (int j = 1; j <= tLLClaimDutyKindSet.size(); j++) {
				LLClaimDutyKindSchema tLLClaimDutyKindSchema = new LLClaimDutyKindSchema();
				tLLClaimDutyKindSchema = tLLClaimDutyKindSet.get(j);
				String tGetDutyKind = tLLClaimDutyKindSchema.getGetDutyKind()
						.substring(1, 3); // 给付责任类型(理赔类型)

				// 首次查找除去豁免权限
				if (tGetDutyKind.equals("09")) {
					mPremium = "09";
				} else {
					double tRealPay = tLLClaimDutyKindSchema.getRealPay(); // 核赔赔付金额
					//----------------------------------------------------------
					// 2005-8-14
					// 11:57
					tRealPay = tRealPay
							+ tLLClaimDutyKindSchema.getDeclinePay();
					//----------------------------------------------------------
					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0
					 * 续涛,2005-08-24 扣除该理赔类型下的年度累计红利保
					 * 
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */
					String tSql = "select (case when sum(YearBonus) is null then 0 else sum(YearBonus) end) from LLClaimDetail where 1 = 1 "
							+ " and ClmNo ='"
							+ "?ClmNo?"
							+ "'"
							+ " and substr(GetDutyKind,2,2) = '"
							+ "?GetDutyKind?"
							+ "'" + " and GiveType = '0'";
					SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
					sqlbv1.sql(tSql);
					sqlbv1.put("ClmNo", this.mClmNo );
					sqlbv1.put("GetDutyKind", tGetDutyKind );
					ExeSQL tExeSQL = new ExeSQL();
					String tSYearBonus = StrTool.cTrim(tExeSQL
							.getOneValue(sqlbv1));
					logger.debug("------------------------------------------------------");
					logger.debug("--LLClaimPopedomSetBL--["
							+ tGetDutyKind + "]理赔类型下的年度红利:[" + tSYearBonus
							+ "]" + tSql);
					logger.debug("------------------------------------------------------");
					if (tExeSQL.mErrors.needDealError()) {
						this.mErrors.copyAllErrors(tExeSQL.mErrors);
						CError.buildErr(this, "计算[" + tGetDutyKind
								+ "]理赔类型下的年度红利发生错误!");

						logger.debug("------------------------------------------------------");
						logger.debug("--LLClaimPopedomSetBL--["
								+ tGetDutyKind + "]理赔类型下的年度红利:[" + tSYearBonus
								+ "]" + tSql);
						logger.debug("------------------------------------------------------");

					}

					/**
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ tp,2007-08-08
					 * ST1354,银代趸缴保单权限分配特殊处理,银代趸交的金额*0.15
					 * 
					 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					 */
					String tSql1 = "select (case when sum(Amnt) is null then 0 else sum(Amnt) end) from lcpol where 1=1 "
							+ " and polno in (select polno from LLClaimDetail where   "
							+ " ClmNo ='"
							+ "?ClmNo?"
							+ "')"
							+ " and salechnl = '3' and payintv = 0 ";
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					sqlbv2.sql(tSql1);
					sqlbv2.put("ClmNo", this.mClmNo );
					ExeSQL tExeSQL1 = new ExeSQL();
					String tBankAmnt = StrTool.cTrim(tExeSQL1
							.getOneValue(sqlbv2));
					logger.debug("------------------------------------------------------");
					logger.debug("--LLClaimPopedomSetBL--[" + tBankAmnt
							+ "]理赔:[" + mClmNo + "]" + tSql1);
					logger.debug("------------------------------------------------------");
					if (tExeSQL1.mErrors.needDealError()) {
						this.mErrors.copyAllErrors(tExeSQL1.mErrors);
						CError.buildErr(this, "计算[" + tBankAmnt
								+ "]理赔类型下的银代趸缴保单发生错误!");
						logger.debug("------------------------------------------------------");
						logger.debug("--LLClaimPopedomSetBL--["
								+ tBankAmnt + "]理赔类型下的银代趸缴保单:[" + mClmNo + "]"
								+ tSql1);
						logger.debug("------------------------------------------------------");
					}

					// tRealPay = tRealPay - Double.parseDouble(tBankAmnt)+
					// Double.parseDouble(tBankAmnt)*0.15;
					tRealPay = tRealPay - Double.parseDouble(tSYearBonus);

					//----------------------------------------------------------

					// 同一理赔类型取得最接近的权限级别
					String strSql = "select JobCategory from LLClaimPopedom where"
							+ " JobCategory like concat('"
							+ "?mPopedom?"
							+ "','%')"
							+ " and ClaimType = '"
							+ "?dutykind?"
							+ "'"
							+ " and StartDate = to_date('2005-06-01','yyyy-mm-dd')"
							// + " and MngCom = 86"
							+ " and basemin <= "
							+ "?realpay?"
							+ " and basemax >= "
							+ "?realpay?"
							+ " order by JobCategory asc";
					SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
					sqlbv3.sql(strSql);
					sqlbv3.put("mPopedom", mPopedom);
					sqlbv3.put("dutykind", tGetDutyKind);
					sqlbv3.put("realpay", tRealPay);
					ExeSQL exesql = new ExeSQL();
					String tResult = exesql.getOneValue(sqlbv3);
					if (tResult.length() > 0) {
						String tLevel = tResult.substring(1, 3);
						Integer tInteger = new Integer(tLevel);
						tNo = tInteger.intValue();
						if (tNo > tMax) {
							tMax = tNo;
							mGetDutyKind = tGetDutyKind;
							mRealPay = tRealPay;
						}
					} else {
						logger.debug(strSql);
						// @@错误处理
						CError.buildErr(this, "查询权限级别失败!");
						return false;
					}
				}
			}
			// 取得所有理赔类型的最高权限
			String sMax = "";
			sMax = sMax.valueOf(tMax);
			if (tMax < 10) {
				sMax = "0" + sMax;
			}
			mMaxLevel = mPopedom + sMax;
			logger.debug("赔案" + mClmNo + "去处豁免类型后初次找到的最高核赔权限为:"
					+ mMaxLevel);
		}
		//----------------------------------------------------------------------
		// END

		//----------------------------------------------------------------------
		// BEG
		// 功 能 2：实赔金额+调整差额后再次查找核赔权限，并与初次比较得到最大权限；
		// 只有审核权限使用
		// 所需变量：mInterval，mRealPay，mGetDutyKind，mMaxLevel
		// 更新变量：mRealPay，mMaxLevel
		//----------------------------------------------------------------------
		if (mPopedom.equals("A") && !mGetDutyKind.equals("")) {
			mRealPay = mRealPay + mInterval;
			String strSql2 = "select JobCategory from LLClaimPopedom where"
					+ " JobCategory like concat('" + "?mPopedom?" + "','%')"
					+ " and ClaimType = '" + "?dutykind?"
					+ "'"
					+ " and StartDate = to_date('2005-06-01','yyyy-mm-dd')"
					// + " and MngCom like '"
					// + mGlobalInput.ManageCom + "%'"
					+ " and basemin <= " + "?realpay?" + " and basemax >= "
					+ "?realpay?" + " order by JobCategory asc";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(strSql2);
			sqlbv4.put("mPopedom", mPopedom);
			sqlbv4.put("dutykind", mGetDutyKind);
			sqlbv4.put("realpay", mRealPay);
			ExeSQL exesql = new ExeSQL();
			String tResult2 = exesql.getOneValue(sqlbv4);
			if (tResult2.length() > 0) {
				String tLevel1 = mMaxLevel.substring(1, 3);
				String tLevel2 = tResult2.substring(1, 3);
				Integer tInteger1 = new Integer(tLevel1);
				int tL1 = tInteger1.intValue();
				Integer tInteger2 = new Integer(tLevel2);
				int tL2 = tInteger2.intValue();
				if (tL2 > tL1) {
					mMaxLevel = tResult2;
				}
			} else {
				logger.debug(strSql2);
				// @@错误处理
				CError.buildErr(this, "查询权限级别失败!");
				return false;
			}
			logger.debug("赔案" + mClmNo + "调整金额后的最高核赔权限为:" + mMaxLevel);
		}
		//----------------------------------------------------------------------
		// END

		//----------------------------------------------------------------------
		// BEG
		// 功 能 3：如有豁免，找到最小豁免权限，并与上面得到的权限比较得到最大权限；
		// 所需变量：mInterval，mRealPay，mGetDutyKind，mMaxLevel
		// 更新变量：mRealPay，mMaxLevel
		//----------------------------------------------------------------------
		if (mPremium.equals("09")) {

			String strSql3 = "select JobCategory from LLClaimPopedom where"
					+ " JobCategory like concat('" + "?mPopedom?" + "','%')"
					+ " and ClaimType = '" + "?mPremium?"
					+ "'"
					+ " and StartDate = to_date('2005-06-01','yyyy-mm-dd')"
					// + " and MngCom like '"
					// + mGlobalInput.ManageCom + "%'"
					+ " and basemin <= " + "?realpay?" + " and basemax >= "
					+ "?realpay?" + " order by JobCategory asc";
			logger.debug("--查找最小豁免权限sql:" + strSql3);
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(strSql3);
			sqlbv5.put("mPopedom", mPopedom);
			sqlbv5.put("mPremium", mPremium);
			sqlbv5.put("realpay", mRealPay);
			ExeSQL exesql = new ExeSQL();
			String tResult3 = exesql.getOneValue(sqlbv5);
			if (tResult3.length() > 0) {
				String tLevel1 = mMaxLevel.substring(1, 3);
				String tLevel2 = tResult3.substring(1, 3);
				Integer tInteger1 = new Integer(tLevel1);
				int tL1 = tInteger1.intValue();
				Integer tInteger2 = new Integer(tLevel2);
				int tL2 = tInteger2.intValue();
				if (tL2 > tL1) {
					mMaxLevel = tResult3;
				}
			} else {
				logger.debug(strSql3);
				// @@错误处理
				CError.buildErr(this, "查询权限级别失败!");
				return false;
			}
		}
		//----------------------------------------------------------------------
		// END

		//----------------------------------------------------------------------
		// BEG
		// 功 能 4：权限筛选
		// 所需变量：mGlobalInput,mMaxLevel
		// 更新变量：mMaxLevel
		//----------------------------------------------------------------------
		if (!CheckComPopdem()) {
			return false;
		}
		//----------------------------------------------------------------------
		// END

		return true;
	}

	/**
	 * 功 能 4：进行三层机构权限筛选,对下级特殊案件权限置特殊标志: 三级(6位)越界到二级(4位)置2,二级越界到一级(2位)置1
	 * 前提条件：总公司代码为2位,分公司为4位,中支为6位 目的：根据每个机构的审核审批级别判断是否越界，如有越界，则置越界标志 mComFLag
	 * 所需变量：mGlobalInput,mMaxLevel 更新变量：mMaxLevel
	 * 
	 * @return boolean
	 */
	private boolean CheckComPopdem() {

		// 审核
		if (mPopedom.equals("A")) {

			String mAppGrade = "A" + mMaxLevel.substring(1, 3);
			logger.debug("案件" + mClmNo + "的所确定的审核级别是" + mAppGrade);
			// 确定审核人
			mUser = ChooseAssessor(mAppGrade, mGlobalInput.ManageCom);
			logger.debug("案件" + mClmNo + "的所确定的审核人是" + mUser);
			if (mUser == null) {
				CError.buildErr(this, "无法确定审核人");
				return false;
			}

			mUser.trim();

			/**
			 * zhangzheng 2008-12-30 为了用测试用户测试流程,保证002立案之后审核人依然可以是002,特设此接口
			 */
			if (mGlobalInput.Operator.trim().equals("002")) {
				mUser = "002";
			}
		}

		// ---------------------------------------------------------------------
		// 审批
		if (mPopedom.equals("B")) {

			String mAppGrade = "B" + mMaxLevel.substring(1, 3);
			logger.debug("案件" + mClmNo + "的所确定的审批级别是" + mAppGrade);
			// 确定审批人
			mUser = ConfirmPopedom(mAppGrade, mGlobalInput.Operator);
			logger.debug("案件" + mClmNo + "的所确定的审批人是" + mUser);

			if (mUser == null) {
				CError.buildErr(this, "无法确定签批人");
				return false;
			}
			mUser.trim();
		}
		//----------------------------------------------------------------------
		// BEG

		return true;
	}

	/**
	 * 返回工作量最少的核赔员编码
	 * 
	 * @param ManageCom
	 *            登陆机构
	 * @return 核赔员编码
	 */
	public String ChooseAssessor(String tAppGrade, String tManageCom) {

		if (tAppGrade == null || tAppGrade.equals("")) {
			// @@错误处理
			CError.buildErr(this, "传入案件审核级别参数不能为空!");
			return null;
		}

		if (tManageCom == null || tManageCom.equals("")) {
			// @@错误处理
			CError.buildErr(this, "传入登陆机构参数不能为空!");
			return null;
		}

		LLClaimUserDB tLLClaimUserDB = new LLClaimUserDB();
		LLClaimUserSet tLLClaimUserSet = new LLClaimUserSet();
		String checkflag = "1";// 审核人标志
		String strSql = "";

		// update by ck
		// 1.三级机构报的案件如果该机构有审核人则分配给该审核人处理,没有则分给二级处理
		// 2.二级机构的案件还是由二级机构的审核人处理
		// 3.对于同一机构的多个审核人，按照工作量分配给手头最少的审核人
		if ((tManageCom.length() == 8)
				|| ((tManageCom.length() == 6) && checkflag.equals("1"))) {
			strSql = "select * from llclaimuser where checkflag='1' and comcode='"
					+ "?comcode?"
					+ "' and usercode in(select usercode from lduser where userstate=0 or userstate is null)"
					+ " and CheckLevel>='" + "?appgrade?" + "' order by usercode";
			SQLwithBindVariables sqlbv6 = new  SQLwithBindVariables();
			sqlbv6.sql(strSql);
			sqlbv6.put("comcode", tManageCom.substring(0, 6));
			sqlbv6.put("appgrade", tAppGrade);
			logger.debug("strSql:" + strSql);

			tLLClaimUserSet = tLLClaimUserDB.executeQuery(sqlbv6);

			if (tLLClaimUserSet.size() == 0) {
				checkflag = "1";
			} else {
				checkflag = "0";
			}
		}

		if ((tManageCom.length() >= 4) && checkflag.equals("1")) {
			tLLClaimUserSet.clear();
			strSql = "select * from llclaimuser where checkflag='1' and comcode='"
					+ "?comcode?"
					+ "' and usercode in(select usercode from lduser where userstate=0 or userstate is null)"
					+ " and CheckLevel>='" + "?appgrade?" + "' order by usercode";
			SQLwithBindVariables sqlbv7 = new  SQLwithBindVariables();
			sqlbv7.sql(strSql);
			sqlbv7.put("comcode", tManageCom.substring(0, 4));
			sqlbv7.put("appgrade", tAppGrade);
			logger.debug("strSql:" + strSql);
			tLLClaimUserSet = tLLClaimUserDB.executeQuery(sqlbv7);

			if (tLLClaimUserSet.size() == 0) {
				checkflag = "1";
			} else {
				checkflag = "0";
			}
		}

		if ((tManageCom.length() >= 2) && checkflag.equals("1")) {
			tLLClaimUserSet.clear();
			strSql = "select * from llclaimuser where checkflag='1' and comcode='"
					+ "?comcode?"
					+ "' and usercode in(select usercode from lduser where userstate=0 or userstate is null)"
					+ " and CheckLevel>='" + "?appgrade?" + "' order by usercode";
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(strSql);
			sqlbv8.put("comcode", tManageCom.substring(0, 2));
			sqlbv8.put("appgrade", tAppGrade);
			logger.debug("strSql:" + strSql);
			tLLClaimUserSet = tLLClaimUserDB.executeQuery(sqlbv8);

			if (tLLClaimUserSet.size() == 0) {
				// @@错误处理
				CError.buildErr(this, "用户表中没有查到符合条件的纪录!");
				return null;
			}
		}

		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = null;

		int count = 0;
		int record = 10000;
		String saveOper = "";

		for (int n = 1; n <= tLLClaimUserSet.size(); n++) {
			// 查询审核人正在处理的案件数，返回工作量最小的一个作为本次案件的审核人
			String sqlStr = "select * from lwmission where activityid in(select activityid from lwactivity where functionid in('10030005','10030007')) and DefaultOperator='"
					+ "?usercode?" + "'";

			logger.debug("查询审核人" + tLLClaimUserSet.get(n).getUserCode()
					+ "的工作量的sql:" + sqlStr);
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql(sqlStr);
			sqlbv9.put("usercode", tLLClaimUserSet.get(n).getUserCode());
			tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv9);

			if (n == 1) {
				saveOper = tLLClaimUserSet.get(n).getUserCode();
			}

			count = tSSRS.getMaxRow();

			if (count == 0) {
				return tLLClaimUserSet.get(n).getUserCode();
			} else {
				if (count < record) {
					record = count;
					saveOper = tLLClaimUserSet.get(n).getUserCode();
				}
			}
		}

		return saveOper;
	}

	/**
	 * 方法:确定审批人 由于MS需要对分公司进行部分授权管理，即使分公司没有权限也必须先看到这个案件，然后选择上报，特对权限部分进行改造，
	 * 利用llclaimuser的必须经手人字段实现案件必须汇集到分公司的功能 参数:案件所需权限级别,当前操作员
	 * 
	 * @return boolean
	 */
	public String ConfirmPopedom(String tAppGrade, String tUserCode) {
		logger.debug("本次案件所需的审批权限是" + tAppGrade);
		logger.debug("当前审核人是" + tUserCode);

		// 只有本机构没有权限审批这个案件的时候才会去查询必须经手人,
		LLClaimUserDB tLLClaimUserDB = new LLClaimUserDB();
		tLLClaimUserDB.setUserCode(tUserCode);

		if (!tLLClaimUserDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "在理赔用户中查询不到" + tUserCode + "的信息");
			return null;
		}

		// 查询上级信息
		LLClaimUserSchema tLowerLLClaimUserSchema = new LLClaimUserSchema();
		tLowerLLClaimUserSchema.setSchema(tLLClaimUserDB.getSchema());

		if ((tLowerLLClaimUserSchema.getUpUserCode() == null)
				|| (tLowerLLClaimUserSchema.getUpUserCode().length() == 0)) {
			// @@错误处理
			CError.buildErr(this, "理赔用户" + tUserCode + "没有上级信息");
			return null;
		}

		logger.debug(tUserCode + "的上级是"
				+ tLowerLLClaimUserSchema.getUpUserCode());
		tLLClaimUserDB = new LLClaimUserDB();
		tLLClaimUserDB.setUserCode(tLowerLLClaimUserSchema.getUpUserCode());

		if (!tLLClaimUserDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "在理赔用户中查询不到"
					+ tLowerLLClaimUserSchema.getUpUserCode() + "的信息");
			return null;
		}

		LLClaimUserSchema tUpperLLClaimUserSchema = new LLClaimUserSchema();
		tUpperLLClaimUserSchema.setSchema(tLLClaimUserDB.getSchema());

		if ((tAppGrade == null) || (tAppGrade.length() == 0)) // 如果没有申请级别要求，
																// 按照权限树方向确定审核人
		{
			// 如果没有审核级别要求，直接返回当前审核人的上级做为审批人
			return tUpperLLClaimUserSchema.getUserCode();
		} else // 如果有申请级别要求
		{
			logger.debug(tUpperLLClaimUserSchema.getUserCode() + "的审批级别是"
					+ tUpperLLClaimUserSchema.getUWLevel());
			if (tUpperLLClaimUserSchema.getUWLevel().toUpperCase().compareTo(
					tAppGrade.toUpperCase()) >= 0)
			// 上级审核人权限符合
			{
				return tUpperLLClaimUserSchema.getUserCode();
			} else // 上级审核人员权限不符
			{
				logger.debug(tUpperLLClaimUserSchema.getUserCode()
								+ "的必须经手人标志是"
								+ tUpperLLClaimUserSchema.getHandleFlag());
				if ((tUpperLLClaimUserSchema.getHandleFlag() != null)
						&& tUpperLLClaimUserSchema.getHandleFlag().equals("1"))
				// 如果是必须经手人员
				{
					return tUpperLLClaimUserSchema.getUserCode();
				}

				return ConfirmPopedom(tAppGrade, tUpperLLClaimUserSchema
						.getUserCode());
			}
		}
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("Popedom", mMaxLevel);
			tTransferData.setNameAndValue("ComFlag", mComFlag);
			tTransferData.setNameAndValue("User", mUser);
			mResult.clear();
			mResult.add(map);
			mResult.add(mMaxLevel);
			mResult.add(tTransferData);
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
			return false;
		}
		return true;
	}

	/**
	 * 提供返回前台界面的数据
	 * 
	 * @return
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回工作量最少的核赔员编码
	 * 
	 * @param ManageCom
	 *            登陆机构
	 * @return 核赔员编码
	 */
	public String ChooseNewAssessor(String pCasePayType, String tManageCom) {

		if (pCasePayType == null || pCasePayType.equals("")) {
			// @@错误处理
			CError.buildErr(this, "传入案件类型参数不能为空!");
			return null;
		}

		if (tManageCom == null || tManageCom.equals("")) {
			// @@错误处理
			CError.buildErr(this, "传入登陆机构参数不能为空!");
			return null;
		}

		LLClaimUserDB tLLClaimUserDB = new LLClaimUserDB();
		LLClaimUserSet tLLClaimUserSet = new LLClaimUserSet();
		String checkflag = "1";// 审核人标志
		String strSql = "";

		// update by ck
		// 1.三级机构报的案件如果该机构有审核人则分配给该审核人处理,没有则分给二级处理
		// 2.二级机构的案件还是由二级机构的审核人处理
		// 3.对于同一机构的多个审核人，按照工作量分配给手头最少的审核人
		if ((tManageCom.length() == 8)
				|| ((tManageCom.length() == 6) && checkflag.equals("1"))) {
			strSql = "select * from llclaimuser where checkflag='1' and comcode='"
					+ "?comcode?"
					+ "' and jobcategory='F' and usercode in(select usercode from lduser where userstate=0 or userstate is null)"
					+ " order by usercode";

			logger.debug("strSql:" + strSql);
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(strSql);
			sqlbv10.put("comcode", tManageCom.substring(0, 6));
			tLLClaimUserSet = tLLClaimUserDB.executeQuery(sqlbv10);

			if (tLLClaimUserSet.size() == 0) {
				checkflag = "1";
			} else {
				checkflag = "0";
			}
		}

		if ((tManageCom.length() >= 4) && checkflag.equals("1")) {
			tLLClaimUserSet.clear();
			strSql = "select * from llclaimuser where checkflag='1' and comcode='"
					+ "?comcode?"
					+ "' and jobcategory='D' and usercode in(select usercode from lduser where userstate=0 or userstate is null)"
					+ "  order by usercode";
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql(strSql);
			sqlbv11.put("comcode", tManageCom.substring(0, 4));
			logger.debug("strSql:" + strSql);
			tLLClaimUserSet = tLLClaimUserDB.executeQuery(sqlbv11);

			if (tLLClaimUserSet.size() == 0) {
				// @@错误处理
				CError.buildErr(this, "用户表中没有查到符合条件的纪录!");
				return null;
			}
		}

	//	if ((tManageCom.length() >= 2) && checkflag.equals("1")) {
	//		tLLClaimUserSet.clear();
	//		strSql = "select * from llclaimuser where checkflag='1' and comcode='"
	//				+ tManageCom.substring(0, 2)
	//				+ "' and jobcategory='F' and usercode in(select usercode from lduser where userstate=0 or userstate is null)"
	//				+ "  order by usercode";

	//		logger.debug("strSql:" + strSql);
	//		tLLClaimUserSet = tLLClaimUserDB.executeQuery(strSql);


   //		}

		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = null;

		int count = 0;
		int record = 10000;
		String saveOper = "";

		for (int n = 1; n <= tLLClaimUserSet.size(); n++) {
			// 查询审核人正在处理的案件数，返回工作量最小的一个作为本次案件的审核人
			String sqlStr = "select * from lwmission where activityid in(select activityid from lwactivity where functionid in('10030005','10030007')) and (DefaultOperator='"
					+ "?usercode?" + "' or MissionProp19='"+ "?usercode?" +"')";

			logger.debug("查询审核人" + tLLClaimUserSet.get(n).getUserCode()
					+ "的工作量的sql:" + sqlStr);
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.sql(sqlStr);
			sqlbv12.put("usercode", tLLClaimUserSet.get(n).getUserCode());
			tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv12);

			if (n == 1) {
				saveOper = tLLClaimUserSet.get(n).getUserCode();
			}

			count = tSSRS.getMaxRow();

			if (count == 0) {
				saveOper = tLLClaimUserSet.get(n).getUserCode();
				// 保证演示时审核的人是demo，有权限操作
				saveOper = "demo";
				return saveOper;
			} else {
				if (count < record) {
					record = count;
					saveOper = tLLClaimUserSet.get(n).getUserCode();
				}
			}
		}
		// 保证演示时审核的人是demo，有权限操作
		saveOper = "demo";
		return saveOper;
	}

	/**
	 * 方法:确定审批人 由于MS需要对分公司进行部分授权管理，即使分公司没有权限也必须先看到这个案件，然后选择上报，特对权限部分进行改造，
	 * 利用llclaimuser的必须经手人字段实现案件必须汇集到分公司的功能 参数:案件所需权限级别,当前操作员 根据案件类型和理赔金额去确定签批人
	 * 
	 * @return boolean
	 */
	public String ConfirmNewPopedom(String tCasePayType, String tUserCode) {

		logger.debug("本次案件的案件类型是" + tCasePayType);
		logger.debug("当前审核人是" + tUserCode);

		// 只有本机构没有权限审批这个案件的时候才会去查询必须经手人,
		LLClaimUserDB tLLClaimUserDB = new LLClaimUserDB();
		tLLClaimUserDB.setUserCode(tUserCode);

		if (!tLLClaimUserDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "在理赔用户中查询不到" + tUserCode + "的信息");
			return null;
		}

		// 查询上级信息
		LLClaimUserSchema tLowerLLClaimUserSchema = new LLClaimUserSchema();
		tLowerLLClaimUserSchema.setSchema(tLLClaimUserDB.getSchema());

		if ((tLowerLLClaimUserSchema.getUpUserCode() == null)
				|| (tLowerLLClaimUserSchema.getUpUserCode().length() == 0)) {
			// @@错误处理
			CError.buildErr(this, "理赔用户" + tUserCode + "没有上级信息");
			return null;
		}

		logger.debug(tUserCode + "的上级是"
				+ tLowerLLClaimUserSchema.getUpUserCode());
		tLLClaimUserDB = new LLClaimUserDB();
		tLLClaimUserDB.setUserCode(tLowerLLClaimUserSchema.getUpUserCode());

		if (!tLLClaimUserDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "在理赔用户中查询不到"
					+ tLowerLLClaimUserSchema.getUpUserCode() + "的信息");
			return null;
		}

		LLClaimUserSchema tUpperLLClaimUserSchema = new LLClaimUserSchema();
		tUpperLLClaimUserSchema.setSchema(tLLClaimUserDB.getSchema());
		
		logger.debug(tUpperLLClaimUserSchema.getUserCode()
				+ "的必须经手人标志是"
				+ tUpperLLClaimUserSchema.getHandleFlag());
		
		if ((tUpperLLClaimUserSchema.getHandleFlag() != null)
				&& tUpperLLClaimUserSchema.getHandleFlag().equals("1"))
			// 如果是必须经手人员
		{
			return tUpperLLClaimUserSchema.getUserCode();
		}

		// 判断计算出的审批人是否有权限，如果没有则继续调用本函数
		String tempUserCode = "";
		tempUserCode = tUpperLLClaimUserSchema.getUserCode();
//		String tPopedomSql = "select 1 from LLClaimPopedom a where"
//				+ " a.JobCategory=(select b.JobCategory from llclaimuser b where usercode='"
//				+ tempUserCode
//				+ "' ) and a.basemax>=(select nvl(sum(realpay),0) from llclaim where clmno='"
//				+ mClmNo + "')";
//		logger.debug("判断是否够审批权限的sql:/n"+tPopedomSql);
//		ExeSQL tExeSql = new ExeSQL();
//		String tResult = tExeSql.getOneValue(tPopedomSql);
//		if(!"1".equals(tResult)){
//			tempUserCode = ConfirmNewPopedom(tCasePayType,tempUserCode);
//		}

		return tempUserCode;

	}
	
	
	/**
	 * 用于上报功能的调用
	 * 方法:确定审批人 由于MS需要对分公司进行部分授权管理，即使分公司没有权限也必须先看到这个案件，然后选择上报，特对权限部分进行改造，
	 * 利用llclaimuser的必须经手人字段实现案件必须汇集到分公司的功能 参数:案件所需权限级别,当前操作员 根据案件类型和理赔金额去确定签批人
	 * 
	 * @return boolean
	 */
	public String PubConfirmNewPopedom(String pClmNo, String tUserCode) {

		logger.debug("本次案件的案件号码是" + pClmNo);
		logger.debug("当前操作人是" + tUserCode);

		// 只有本机构没有权限审批这个案件的时候才会去查询必须经手人,
		LLClaimUserDB tLLClaimUserDB = new LLClaimUserDB();
		tLLClaimUserDB.setUserCode(tUserCode);

		if (!tLLClaimUserDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "在理赔用户中查询不到" + tUserCode + "的信息");
			return null;
		}

		// 查询上级信息
		LLClaimUserSchema tLowerLLClaimUserSchema = new LLClaimUserSchema();
		tLowerLLClaimUserSchema.setSchema(tLLClaimUserDB.getSchema());

		if ((tLowerLLClaimUserSchema.getUpUserCode() == null)
				|| (tLowerLLClaimUserSchema.getUpUserCode().length() == 0)) {
			// @@错误处理
			CError.buildErr(this, "理赔用户" + tUserCode + "没有上级信息");
			return null;
		}

		logger.debug(tUserCode + "的上级是"
				+ tLowerLLClaimUserSchema.getUpUserCode());
		tLLClaimUserDB = new LLClaimUserDB();
		tLLClaimUserDB.setUserCode(tLowerLLClaimUserSchema.getUpUserCode());

		if (!tLLClaimUserDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "在理赔用户中查询不到"
					+ tLowerLLClaimUserSchema.getUpUserCode() + "的信息");
			return null;
		}

		LLClaimUserSchema tUpperLLClaimUserSchema = new LLClaimUserSchema();
		tUpperLLClaimUserSchema.setSchema(tLLClaimUserDB.getSchema());
		
		logger.debug(tUpperLLClaimUserSchema.getUserCode()
				+ "的必须经手人标志是"
				+ tUpperLLClaimUserSchema.getHandleFlag());
		
		if ((tUpperLLClaimUserSchema.getHandleFlag() != null)
				&& tUpperLLClaimUserSchema.getHandleFlag().equals("1"))
			// 如果是必须经手人员
		{
			return tUpperLLClaimUserSchema.getUserCode();
		}

		
		String tempUserCode = tUpperLLClaimUserSchema.getUserCode();
		LLClaimSchema tLLClaimSchema=mLLClaimPubFunBL.getLLClaim(pClmNo);

		 
		 /**
		  * 2009-05-08 zhangzheng
		  * 如果审核结论是拒付,由于拒付金额是存放在declinepay字段不是存放的realpay,所以权限校验金额字段取declinepay
		  */
		 
	    String tPopedomSql="";
		 
	    //拒付件
	    if(tLLClaimSchema.getCasePayType().trim().equals("4"))
		 {			 
			 tPopedomSql = "select distinct 1 from LLClaimPopedom a where"
					+ " a.JobCategory=(select b.JobCategory from llclaimuser b where usercode='"
					+ "?usercode?"
					+ "' ) and a.basemax>=(select (case when sum(DeclinePay) is null then 0 else sum(DeclinePay) end) from llclaim where clmno='"
					+ "?clmno?" + "')";
			 
			logger.debug("责任免除件:判断是否够审批权限的sql:" + tPopedomSql);
	
		 }
		 else
		 {		 
			 tPopedomSql = "select distinct 1 from LLClaimPopedom a where"
					+ " a.JobCategory=(select b.JobCategory from llclaimuser b where usercode='"
					+ "?usercode?"
					+ "' ) and a.basemax>=(select (case when sum(realpay) is null then 0 else sum(realpay) end) from llclaim where clmno='"
					+ "?clmno?" + "')";
			 
			 logger.debug("判断是否够审批权限的sql:" + tPopedomSql);
		} 
	    SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
	    sqlbv14.sql(tPopedomSql);
	    sqlbv14.put("usercode", tempUserCode);
	    sqlbv14.put("clmno", pClmNo);
		ExeSQL tExeSql = new ExeSQL();
		String tResult = tExeSql.getOneValue(sqlbv14);
		if(!"1".equals(tResult)){
			tempUserCode = PubConfirmNewPopedom(pClmNo,tempUserCode);
		}

		return tempUserCode;

	}

	/**
	 * 用于测试
	 * 
	 * @return
	 */
	public static void main(String[] args) {
		// 准备数据，测试权限部分
		LLClaimPopedomSetBL mLLClaimPopedomSetBL = new LLClaimPopedomSetBL();

		TransferData tTransferData = new TransferData();
		String tClmNo = "86110020080519000092";
		String tPopedom = "B";// 审批
		tTransferData.setNameAndValue("RptNo", tClmNo);
		tTransferData.setNameAndValue("PopedomPhase", tPopedom);

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ComCode = "8611";
		tGlobalInput.ManageCom = "8611";
		tGlobalInput.Operator = "gp";

		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(tGlobalInput);

		// 调用submit方法,开始测试
		mLLClaimPopedomSetBL.submitData(tVData, "");
	}
}

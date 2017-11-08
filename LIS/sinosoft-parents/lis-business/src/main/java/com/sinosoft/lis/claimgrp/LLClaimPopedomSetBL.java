/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.claim.LLClaimPubFunBL;
import com.sinosoft.lis.db.LLClaimDutyKindDB;
import com.sinosoft.lis.db.LLGrpClaimUserDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LLClaimDutyKindSchema;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.schema.LLClaimUWMainSchema;
import com.sinosoft.lis.schema.LLClaimUserSchema;
import com.sinosoft.lis.schema.LLGrpClaimUserSchema;
import com.sinosoft.lis.vschema.LLClaimDutyKindSet;
import com.sinosoft.lis.vschema.LLClaimUserSet;
import com.sinosoft.lis.vschema.LLGrpClaimUserSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
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
 * @author: zl
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
	private String Operator = ""; // 操作员
	// 处理变量
	private String mGetDutyKind = ""; // 最高赔付的理赔类型
	private double mRealPay = 0; // 最高赔付理赔类型的实赔金额
	private String mMaxLevel = ""; // 最高权限
	private String mPremium = ""; // 豁免
	private String mComFlag = "0"; // 权限越界标志
	private String mUser = "";// 审批人
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
		logger.debug("----------LLClaimPopedomSetBL after getInputData----------");
		// 检查数据合法性
		if (!checkData()) {
			return false;
		}
		logger.debug("----------LLClaimPopedomSetBL after checkData----------");
		// 进行业务处理
		// if (!dealData())
		// {
		// return false;
		// }
		if (!dealNewData()) {
			return false;
		}
		logger.debug("----------LLClaimPopedomSetBL after dealData----------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------LLClaimPopedomSetBL after prepareOutputData----------");
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
		Operator = mGlobalInput.Operator;
		// 如果是审核权限
//		if (mPopedom.equals("A")) {
//			String tStandpay = (String) mTransferData
//					.getValueByName("standpay");
//			String tAdjpay = (String) mTransferData.getValueByName("adjpay");
//			// 求预估金额
//			Double tDouble1 = new Double(tStandpay);
//			double tD1 = tDouble1.doubleValue();
//			Double tDouble2 = new Double(tAdjpay);
//			double tD2 = tDouble2.doubleValue();
//			mInterval = tD2 - tD1;
//		}

		return true;
	}

	/**
	 * 校验传入的信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimPopedomSetBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		String tOperator = mGlobalInput.Operator;
		if (tOperator == null || tOperator.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimPopedomSetBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输全局公共数据[操作员编码]失败!";
			this.mErrors.addOneError(tError);
			return false;
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
				+ mClmNo + "'";
		LLClaimDutyKindSet tLLClaimDutyKindSet = tLLClaimDutyKindDB
				.executeQuery(sql);

		if (tLLClaimDutyKindSet == null || tLLClaimDutyKindSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimRegisterBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询给付责任类型失败!";
			this.mErrors.addOneError(tError);
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
					// 2005-8-14 11:57
					tRealPay = tRealPay
							+ tLLClaimDutyKindSchema.getDeclinePay();
					//----------------------------------------------------------
					//----------------------------------------------------------
					// 2005-12-05 TK理赔权限控制 P.D
					ExeSQL exesql = new ExeSQL();
					String strSq4 = "select checklevel from llclaimuser where  UserCode = '"
							+ Operator + "'";
					String CheckLevel = exesql.getOneValue(strSq4); // 取到登陆用户的核赔权限
					String strSql = "";
					if (CheckLevel.length() > 0) {
						// if(mPopedom.equals("A")){
						// strSql =
						// "select PopedomLevel from LLClaimPopedom where"
						// + " PopedomLevel like '" + CheckLevel + "%'"
						// + " and ClaimType = '30'"
						// +
						// " and StartDate = to_date('2005-06-01','yyyy-mm-dd')"
						// + " and basemin <= " + tRealPay
						// + " and basemax >= " + tRealPay
						// + " order by PopedomLevel asc";
						// }else{
						strSql = "select JobCategory from LLClaimPopedom where"
								+ " JobCategory like '"
								+ CheckLevel
								+ "%'"
								// + " and ClaimType = '10'"
								+ " and StartDate = to_date('2005-06-01','yyyy-mm-dd')"
								+ " and basemin <= "
								+ tRealPay
								+ " and basemax >= "
								+ tRealPay
								+ " order by JobCategory asc";
						// }
						String tResult = exesql.getOneValue(strSql);
						if (tResult.length() > 0) {
							mMaxLevel = CheckLevel;
							return true;
						} else {
							String strSq2 = "select JobCategory,PopedomName from LLClaimPopedom where"
									+ " StartDate = to_date('2005-06-01','yyyy-mm-dd')"
									+ " and basemin <= "
									+ tRealPay
									+ " and basemax >= "
									+ tRealPay
									+ " order by JobCategory asc";
							ExeSQL tExeSQL = new ExeSQL();
							SSRS tSSRS = tExeSQL.execSQL(strSq2);
							String PopedomLevel = tSSRS.GetText(1, 1); // 权限级别
							String PopedomName = tSSRS.GetText(1, 2); // 权限名称

							String strSq3 = " update lwmission set DefaultOperator = ''"
									+ " where missionprop1 = '" + mClmNo + "'";
							exesql.execUpdateSQL(strSq3);

							// @@错误处理
							CError tError = new CError();
							tError.moduleName = "LLClaimPopedomSetBL";
							tError.functionName = "dealData";
							tError.errorMessage = "您的权限不足!该案件需要权限在 "
									+ PopedomName + " 以上才可以处理。";
							this.mErrors.addOneError(tError);
							return false;
						}
					} else {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "LLClaimPopedomSetBL";
						tError.functionName = "dealData";
						tError.errorMessage = "未查询到该用户的核赔权限";
						this.mErrors.addOneError(tError);
						return false;
					}
					//----------------------------------------------------------
					// END P.D
					/****************************** （以下注释的暂时不用）2005-12-05 *****************************/
					// /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					// * No1.0 续涛,2005-08-24
					// * 扣除该理赔类型下的年度累计红利保
					// *
					// * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
					// */
					// String tSql =
					// "select nvl(sum(YearBonus),0) from LLClaimDetail where 1 = 1 "
					// +" and ClmNo ='" +this.mClmNo+"'"
					// +" and GetDutyKind = '"+tGetDutyKind+"'"
					// +" and GiveType = '0'"
					// ;
					//
					// ExeSQL tExeSQL = new ExeSQL();
					// String tSYearBonus=
					// StrTool.cTrim(tExeSQL.getOneValue(tSql));
					// logger.debug(
					// "------------------------------------------------------"
					// );
					//logger.debug("--LLClaimPopedomSetBL--["+tGetDutyKind
					// +"]理赔类型下的年度红利:["+tSYearBonus+"]"+tSql);
					// logger.debug(
					// "------------------------------------------------------"
					// );
					// if (tExeSQL.mErrors.needDealError())
					// {
					// this.mErrors.copyAllErrors(tExeSQL.mErrors);
					// CError tError = new CError();
					// tError.moduleName = "LLClaimCalAutoBL";
					// tError.functionName = "calDutyKind";
					// tError.errorMessage =
					// "计算["+tGetDutyKind+"]理赔类型下的年度红利发生错误!";
					// logger.debug(
					// "------------------------------------------------------"
					// );
					//logger.debug("--LLClaimPopedomSetBL--["+tGetDutyKind
					// +"]理赔类型下的年度红利:["+tSYearBonus+"]"+tSql);
					// logger.debug(
					// "------------------------------------------------------"
					// );
					// this.mErrors.addOneError(tError);
					// }
					//
					// tRealPay = tRealPay - Double.parseDouble(tSYearBonus);
					//
					////--------------------------------------------------------
					// --
					//
					// //同一理赔类型取得最接近的权限级别
					// String strSql =
					// "select PopedomLevel from LLClaimPopedom where"
					// + " PopedomLevel like '" + mPopedom + "%'"
					// + " and ClaimType = '" + tGetDutyKind + "'"
					// + " and StartDate = to_date('2005-06-01','yyyy-mm-dd')"
					// // + " and MngCom = 86"
					// + " and basemin <= " + tRealPay
					// + " and basemax >= " + tRealPay
					// + " order by PopedomLevel asc";
					// ExeSQL exesql = new ExeSQL();
					// String tResult = exesql.getOneValue(strSql);
					// if (tResult.length() > 0)
					// {
					// String tLevel = tResult.substring(1, 3);
					// Integer tInteger = new Integer(tLevel);
					// tNo = tInteger.intValue();
					// if (tNo > tMax)
					// {
					// tMax = tNo;
					// mGetDutyKind = tGetDutyKind;
					// mRealPay = tRealPay;
					// }
					// }
					// else
					// {
					// logger.debug(strSql);
					// //@@错误处理
					// CError tError = new CError();
					// tError.moduleName = "LLClaimPopedomSetBL";
					// tError.functionName = "dealData";
					// tError.errorMessage = "查询权限级别失败!";
					// this.mErrors.addOneError(tError);
					// return false;
					// }
				}
			}
			// //取得所有理赔类型的最高权限
			// String sMax = "";
			// sMax = sMax.valueOf(tMax);
			// if (tMax < 10)
			// {
			// sMax = "0" + sMax;
			// }
			// mMaxLevel = mPopedom + sMax;
			// logger.debug("赔案" + mClmNo + "去处豁免类型后初次找到的最高核赔权限为:" +
			// mMaxLevel);
		}
		//----------------------------------------------------------------------
		// END

		////--------------------------------------------------------------------
		// --BEG
		// //功 能 2：实赔金额+调整差额后再次查找核赔权限，并与初次比较得到最大权限；
		// // 只有审核权限使用
		// //所需变量：mInterval，mRealPay，mGetDutyKind，mMaxLevel
		// //更新变量：mRealPay，mMaxLevel
		////--------------------------------------------------------------------
		// --
		// if (mPopedom.equals("A") && !mGetDutyKind.equals(""))
		// {
		// mRealPay = mRealPay + mInterval;
		// String strSql2 = "select PopedomLevel from LLClaimPopedom where"
		// + " PopedomLevel like '" + mPopedom + "%'"
		// + " and ClaimType = '" + mGetDutyKind + "'"
		// + " and StartDate = to_date('2005-06-01','yyyy-mm-dd')"
		// // + " and MngCom like '"
		// // + mGlobalInput.ManageCom + "%'"
		// + " and basemin <= " + mRealPay
		// + " and basemax >= " + mRealPay
		// + " order by PopedomLevel asc";
		// ExeSQL exesql = new ExeSQL();
		// String tResult2 = exesql.getOneValue(strSql2);
		// if (tResult2.length() > 0)
		// {
		// String tLevel1 = mMaxLevel.substring(1,3);
		// String tLevel2 = tResult2.substring(1,3);
		// Integer tInteger1 = new Integer(tLevel1);
		// int tL1 = tInteger1.intValue();
		// Integer tInteger2 = new Integer(tLevel2);
		// int tL2 = tInteger2.intValue();
		// if (tL2 > tL1)
		// {
		// mMaxLevel = tResult2;
		// }
		// }
		// else
		// {
		// logger.debug(strSql2);
		// //@@错误处理
		// CError tError = new CError();
		// tError.moduleName = "LLClaimPopedomSetBL";
		// tError.functionName = "dealData";
		// tError.errorMessage = "查询权限级别失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// logger.debug("赔案" + mClmNo + "调整金额后的最高核赔权限为:" + mMaxLevel);
		// }
		////--------------------------------------------------------------------
		// --END
		//
		////--------------------------------------------------------------------
		// --BEG
		// //功 能 3：如有豁免，找到最小豁免权限，并与上面得到的权限比较得到最大权限；
		// //所需变量：mInterval，mRealPay，mGetDutyKind，mMaxLevel
		// //更新变量：mRealPay，mMaxLevel
		////--------------------------------------------------------------------
		// --
		// if (mPremium.equals("09"))
		// {
		// String strSql3 = "select PopedomLevel from LLClaimPopedom where"
		// + " PopedomLevel like '" + mPopedom + "%'"
		// + " and ClaimType = '" + mPremium + "'"
		// + " and StartDate = to_date('2005-06-01','yyyy-mm-dd')"
		// // + " and MngCom like '"
		// // + mGlobalInput.ManageCom + "%'"
		// + " and basemin <= " + mRealPay
		// + " and basemax >= " + mRealPay
		// + " order by PopedomLevel asc";
		// ExeSQL exesql = new ExeSQL();
		// String tResult3 = exesql.getOneValue(strSql3);
		// if (tResult3.length() > 0)
		// {
		// String tLevel1 = mMaxLevel.substring(1, 3);
		// String tLevel2 = tResult3.substring(1, 3);
		// Integer tInteger1 = new Integer(tLevel1);
		// int tL1 = tInteger1.intValue();
		// Integer tInteger2 = new Integer(tLevel2);
		// int tL2 = tInteger2.intValue();
		// if (tL2 > tL1)
		// {
		// mMaxLevel = tResult3;
		// }
		// }
		// else
		// {
		// logger.debug(strSql3);
		// //@@错误处理
		// CError tError = new CError();
		// tError.moduleName = "LLClaimPopedomSetBL";
		// tError.functionName = "dealData";
		// tError.errorMessage = "查询权限级别失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// }
		////--------------------------------------------------------------------
		// --END
		//
		////--------------------------------------------------------------------
		// --BEG
		// //功 能 4：进行三层机构权限筛选,对下级特殊案件权限置特殊标志:
		// // 三级(6位)越界到二级(4位)置2,二级越界到一级(2位)置1
		// //前提条件：总公司代码为2位,分公司为4位,中支为6位
		// //所需变量：mGlobalInput,mMaxLevel
		// //更新变量：mMaxLevel
		////--------------------------------------------------------------------
		// --
		// if (!CheckComPopdem())
		// {
		// return false;
		// }
		////--------------------------------------------------------------------
		// --END

		return true;
	}

	/**
	 * 功 能 4：进行三层机构权限筛选,对下级特殊案件权限置特殊标志: 三级(6位)越界到二级(4位)置2,二级越界到一级(2位)置1
	 * 前提条件：总公司代码为2位,分公司为4位,中支为6位 所需变量：mGlobalInput,mMaxLevel 更新变量：mMaxLevel
	 * 
	 * @return boolean
	 */
	private boolean CheckComPopdem() {
		int tMaxP1 = 0; // 总公司最高权限
		int tMaxP2 = 0; // 分公司最高权限
		int tMaxP3 = 0; // 中支最高权限

		// 获取当前立案单位代码,只有三级机构有核赔师,超过则截取6位
		String tComCode = mGlobalInput.ComCode;
		int tLength = tComCode.length();
		if (tLength > 6) {
			tComCode = tComCode.substring(0, 6);
		}

		//----------------------------------------------------------------------
		// BEG
		// 按审核和审批区分
		//----------------------------------------------------------------------

		// 审核
		if (mPopedom.equals("A")) {
			// 根据单位代码检索当前公司最高权限
			String strSql4 = "select checklevel from llclaimuser where "
					+ " checkflag = '1'" + " and comcode = '" + tComCode + "'"
					+ " order by checklevel desc";
			ExeSQL exesql = new ExeSQL();
			String tResult4 = exesql.getOneValue(strSql4);
			if (tResult4 == null || tResult4.equals("")) {
				tResult4 = "A00"; // 当前公司无其他用户时置为最小
			}

			// 根据单位代码长度分别判断
			switch (tLength) {
			case 2: // 总公司
			{
				tMaxP1 = Integer.parseInt(tResult4.substring(1, 3));
				int tP = Integer.parseInt(mMaxLevel.substring(1, 3));
				if (tMaxP1 < tP) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLClaimPopedomSetBL";
					tError.functionName = "CheckComPopdem";
					tError.errorMessage = "此案件超过总公司最高审核师权限,请确认!";
					this.mErrors.addOneError(tError);
					return false;
				}
				break;
			}
			case 4: // 分公司
			{
				tMaxP2 = Integer.parseInt(tResult4.substring(1, 3));
				int tP = Integer.parseInt(mMaxLevel.substring(1, 3));
				if (tMaxP2 < tP) {
					mComFlag = "1";
					logger.debug("分公司核赔师权限不足,提交总公司!");
				}
				break;
			}
			default: // 中支以下
			{
				tMaxP3 = Integer.parseInt(tResult4.substring(1, 3));
				int tP = Integer.parseInt(mMaxLevel.substring(1, 3));
				if (tMaxP3 < tP) {
					mComFlag = "2";
					logger.debug("中支公司核赔师权限不足,提交分公司!");

					// 接着查找分公司最高权限
					String strSql5 = "select checklevel from llclaimuser where "
							+ " checkflag = '1'"
							+ " and comcode = '"
							+ tComCode.substring(0, 4)
							+ "'"
							+ " order by checklevel desc";
					ExeSQL exesql5 = new ExeSQL();
					String tResult5 = exesql5.getOneValue(strSql5);
					if (tResult5 == null || tResult5.equals("")) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "LLClaimPopedomSetBL";
						tError.functionName = "CheckComPopdem";
						tError.errorMessage = "查询公司最高审核师权限失败!";
						this.mErrors.addOneError(tError);
						return false;
					}
					tMaxP2 = Integer.parseInt(tResult5.substring(1, 3));
					if (tMaxP2 < tP) {
						mComFlag = "1";
						logger.debug("分公司核赔师权限不足,提交总公司!");
					}
				}
				break;
			}
			}// end switch
		}

		// ---------------------------------------------------------------------
		// 审批
		if (mPopedom.equals("B")) {

			// 根据单位代码检索当前公司最高权限
			String strSql4 = "select uwlevel from llclaimuser where "
					+ " uwflag = '1'" + " and comcode = '" + tComCode + "'"
					+ " and usercode != '" + mGlobalInput.Operator + "'"
					+ " order by uwlevel desc";
			ExeSQL exesql = new ExeSQL();
			String tResult4 = exesql.getOneValue(strSql4);
			if (tResult4 == null || tResult4.equals("")) {
				tResult4 = "B00"; // 当前公司无其他用户时置为最小
			}

			// 根据单位代码长度分别判断
			switch (tLength) {
			case 2: // 总公司
			{
				tMaxP1 = Integer.parseInt(tResult4.substring(1, 3));
				int tP = Integer.parseInt(mMaxLevel.substring(1, 3));
				if (tMaxP1 < tP) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LLClaimPopedomSetBL";
					tError.functionName = "CheckComPopdem";
					tError.errorMessage = "此案件超过总公司最高审批师权限,请确认!";
					this.mErrors.addOneError(tError);
					return false;
				}
				break;
			}
			case 4: // 分公司
			{
				tMaxP2 = Integer.parseInt(tResult4.substring(1, 3));
				int tP = Integer.parseInt(mMaxLevel.substring(1, 3));
				if (tMaxP2 < tP) {
					mComFlag = "1";
					logger.debug("分公司核赔师权限不足,提交总公司!");
				}
				break;
			}
			default: // 中支以下
			{
				tMaxP3 = Integer.parseInt(tResult4.substring(1, 3));
				int tP = Integer.parseInt(mMaxLevel.substring(1, 3));
				if (tMaxP3 < tP) {
					mComFlag = "2";
					logger.debug("中支公司核赔师权限不足,提交分公司!");

					// 接着查找分公司最高权限
					String strSql5 = "select uwlevel from llclaimuser where "
							+ " uwflag = '1'" + " and comcode = '"
							+ tComCode.substring(0, 4) + "'"
							+ " order by uwlevel desc";
					ExeSQL exesql5 = new ExeSQL();
					String tResult5 = exesql5.getOneValue(strSql5);
					if (tResult5 == null || tResult5.equals("")) {
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "LLClaimPopedomSetBL";
						tError.functionName = "CheckComPopdem";
						tError.errorMessage = "查询公司最高审批师权限失败!";
						this.mErrors.addOneError(tError);
						return false;
					}
					tMaxP2 = Integer.parseInt(tResult5.substring(1, 3));
					if (tMaxP2 < tP) {
						mComFlag = "1";
						logger.debug("分公司核赔师权限不足,提交总公司!");
					}
				}
				break;
			}// end switch
			}
		}
		//----------------------------------------------------------------------
		// BEG

		return true;
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
			mResult.add(tTransferData);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimPopedomSetBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
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

	private boolean dealNewData() {

		// 审核
		if (mPopedom.equals("A")) {

			// 确定审核人
			mUser = ChooseNewAssessor("", mGlobalInput.ManageCom);
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
			mUser = ConfirmNewPopedom("", mGlobalInput.Operator);
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
	 * 返回工作量最少的核赔员编码
	 * 
	 * @param ManageCom
	 *            登陆机构
	 * @return 核赔员编码
	 */
	public String ChooseNewAssessor(String pCasePayType, String tManageCom) {

		if (pCasePayType == null || pCasePayType.equals("")) {
			// @@错误处理
//			CError.buildErr(this, "传入案件类型参数不能为空!");
//			return null;
		}

		if (tManageCom == null || tManageCom.equals("")) {
			// @@错误处理
			CError.buildErr(this, "传入登陆机构参数不能为空!");
			return null;
		}

		LLGrpClaimUserDB tLLGrpClaimUserDB = new LLGrpClaimUserDB();
		LLGrpClaimUserSet tLLGrpClaimUserSet = new LLGrpClaimUserSet();
		String checkflag = "1";// 审核人标志
		String strSql = "";

		// update by ck
		// 1.三级机构报的案件如果该机构有审核人则分配给该审核人处理,没有则分给二级处理
		// 2.二级机构的案件还是由二级机构的审核人处理
		// 3.对于同一机构的多个审核人，按照工作量分配给手头最少的审核人
		if ((tManageCom.length() == 8)
				|| ((tManageCom.length() == 6) && checkflag.equals("1"))) {
			strSql = "select * from llgrpclaimuser where checkflag='1' and comcode='"
					+ tManageCom.substring(0, 6)
					+ "'  and jobcategory='TF' and usercode in(select usercode from lduser where userstate=0 or userstate is null)"
					+ " order by usercode";

			logger.debug("strSql:" + strSql);

			tLLGrpClaimUserSet = tLLGrpClaimUserDB.executeQuery(strSql);

			if (tLLGrpClaimUserSet.size() == 0) {
				checkflag = "1";
			} else {
				checkflag = "0";
			}
		}

		if ((tManageCom.length() >= 4) && checkflag.equals("1")) {
			tLLGrpClaimUserSet.clear();
			strSql = "select * from llgrpclaimuser where checkflag='1' and comcode='"
					+ tManageCom.substring(0, 4)
					+ "' and jobcategory='TD' and usercode in(select usercode from lduser where userstate=0 or userstate is null)"
					+ "  order by usercode";

			logger.debug("strSql:" + strSql);
			tLLGrpClaimUserSet = tLLGrpClaimUserDB.executeQuery(strSql);

			if (tLLGrpClaimUserSet.size() == 0) {
				// @@错误处理
				CError.buildErr(this, "用户表中没有查到符合条件的纪录!");
				return null;
			}
			
		}

//		if ((tManageCom.length() >= 2) && checkflag.equals("1")) {
//			tLLGrpClaimUserSet.clear();
//			strSql = "select * from llgrpclaimuser where checkflag='1' and comcode='"
//					+ tManageCom.substring(0, 2)
//					+ "' and usercode in(select usercode from lduser where userstate=0 or userstate is null)"
//					+ "  order by usercode";
//
//			logger.debug("strSql:" + strSql);
//			tLLGrpClaimUserSet = tLLGrpClaimUserDB.executeQuery(strSql);
//
//
//		}

		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = null;

		int count = 0;
		int record = 10000;
		String saveOper = "";

		for (int n = 1; n <= tLLGrpClaimUserSet.size(); n++) {
			// 查询审核人正在处理的案件数，返回工作量最小的一个作为本次案件的审核人
			String sqlStr = "select * from lwmission where activityid in('0000009065','0000009035') and (DefaultOperator='"
					+ tLLGrpClaimUserSet.get(n).getUserCode() + "' and MissionProp19='"+tLLGrpClaimUserSet.get(n).getUserCode() +"')";

			logger.debug("查询审核人"
					+ tLLGrpClaimUserSet.get(n).getUserCode() + "的工作量的sql:"
					+ sqlStr);

			tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlStr);

			if (n == 1) {
				saveOper = tLLGrpClaimUserSet.get(n).getUserCode();
			}

			count = tSSRS.getMaxRow();

			if (count == 0) {
				return tLLGrpClaimUserSet.get(n).getUserCode();
			} else {
				if (count < record) {
					record = count;
					saveOper = tLLGrpClaimUserSet.get(n).getUserCode();
				}
			}
		}

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
		LLGrpClaimUserDB tLLGrpClaimUserDB = new LLGrpClaimUserDB();
		tLLGrpClaimUserDB.setUserCode(tUserCode);

		if (!tLLGrpClaimUserDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "在理赔用户中查询不到" + tUserCode + "的信息");
			return null;
		}

		// 查询上级信息
		LLGrpClaimUserSchema tLowerLLGrpClaimUserSchema = new LLGrpClaimUserSchema();
		tLowerLLGrpClaimUserSchema.setSchema(tLLGrpClaimUserDB.getSchema());

		if ((tLowerLLGrpClaimUserSchema.getUpUserCode() == null)
				|| (tLowerLLGrpClaimUserSchema.getUpUserCode().length() == 0)) {
			// @@错误处理
			CError.buildErr(this, "理赔用户" + tUserCode + "没有上级信息");
			return null;
		}

		logger.debug(tUserCode + "的上级是"
				+ tLowerLLGrpClaimUserSchema.getUpUserCode());
		tLLGrpClaimUserDB = new LLGrpClaimUserDB();
		tLLGrpClaimUserDB.setUserCode(tLowerLLGrpClaimUserSchema
				.getUpUserCode());

		if (!tLLGrpClaimUserDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "在理赔用户中查询不到"
					+ tLowerLLGrpClaimUserSchema.getUpUserCode() + "的信息");
			return null;
		}

		
		LLGrpClaimUserSchema tUpperLLGrpClaimUserSchema = new LLGrpClaimUserSchema();
		tUpperLLGrpClaimUserSchema.setSchema(tLLGrpClaimUserDB.getSchema());
		
		logger.debug(tUpperLLGrpClaimUserSchema.getUserCode()
				+ "的必须经手人标志是"
				+ tUpperLLGrpClaimUserSchema.getHandleFlag());
		
		if ((tUpperLLGrpClaimUserSchema.getHandleFlag() != null)
				&& tUpperLLGrpClaimUserSchema.getHandleFlag().equals("1"))
			// 如果是必须经手人员
		{
			return tUpperLLGrpClaimUserSchema.getUserCode();
		}
		

		// 判断计算出的审批人是否有权限，如果没有则继续调用本函数
		String tempUserCode = "";
		tempUserCode = tUpperLLGrpClaimUserSchema.getUserCode();
//		String tPopedomSql = "select 1 from LLGrpClaimPopedom a where"
//				+ " a.JobCategory=(select b.JobCategory from llgrpclaimuser b where usercode='"
//				+ tempUserCode
//				+ "' ) and a.basemax>=(select nvl(sum(realpay),0) from llclaim where clmno='"
//				+ mClmNo + "')";
//		logger.debug("判断是否够审批权限的sql:" + tPopedomSql);
//		ExeSQL tExeSql = new ExeSQL();
//		String tResult = tExeSql.getOneValue(tPopedomSql);
//		if (!"1".equals(tResult)) {
//			tempUserCode = ConfirmNewPopedom(tCasePayType, tempUserCode);
//		}

		return tempUserCode;

	}
	
	
	
	/**
	 * 方法:确定审批人 由于MS需要对分公司进行部分授权管理，即使分公司没有权限也必须先看到这个案件，然后选择上报，特对权限部分进行改造，
	 * 利用llgrpclaimuser的必须经手人字段实现案件必须汇集到分公司的功能 参数:案件所需权限级别,当前操作员 根据案件类型和理赔金额去确定签批人
	 * 
	 * @return boolean
	 */
	public String PubConfirmNewPopedom(String pClmNo, String tUserCode) {

		logger.debug("本次案件的案件号是" + pClmNo);
		logger.debug("当前操作人是" + tUserCode);

		// 只有本机构没有权限审批这个案件的时候才会去查询必须经手人,
		LLGrpClaimUserDB tLLGrpClaimUserDB = new LLGrpClaimUserDB();
		tLLGrpClaimUserDB.setUserCode(tUserCode);

		if (!tLLGrpClaimUserDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "在理赔用户中查询不到" + tUserCode + "的信息");
			return null;
		}

		// 查询上级信息
		LLGrpClaimUserSchema tLowerLLGrpClaimUserSchema = new LLGrpClaimUserSchema();
		tLowerLLGrpClaimUserSchema.setSchema(tLLGrpClaimUserDB.getSchema());

		if ((tLowerLLGrpClaimUserSchema.getUpUserCode() == null)
				|| (tLowerLLGrpClaimUserSchema.getUpUserCode().length() == 0)) {
			// @@错误处理
			CError.buildErr(this, "理赔用户" + tUserCode + "没有上级信息");
			return null;
		}

		logger.debug(tUserCode + "的上级是"
				+ tLowerLLGrpClaimUserSchema.getUpUserCode());
		tLLGrpClaimUserDB = new LLGrpClaimUserDB();
		tLLGrpClaimUserDB.setUserCode(tLowerLLGrpClaimUserSchema
				.getUpUserCode());

		if (!tLLGrpClaimUserDB.getInfo()) {
			// @@错误处理
			CError.buildErr(this, "在理赔用户中查询不到"
					+ tLowerLLGrpClaimUserSchema.getUpUserCode() + "的信息");
			return null;
		}

		
		LLGrpClaimUserSchema tUpperLLGrpClaimUserSchema = new LLGrpClaimUserSchema();
		tUpperLLGrpClaimUserSchema.setSchema(tLLGrpClaimUserDB.getSchema());
		
		logger.debug(tUpperLLGrpClaimUserSchema.getUserCode()
				+ "的必须经手人标志是"
				+ tUpperLLGrpClaimUserSchema.getHandleFlag());
		
		if ((tUpperLLGrpClaimUserSchema.getHandleFlag() != null)
				&& tUpperLLGrpClaimUserSchema.getHandleFlag().equals("1"))
			// 如果是必须经手人员
		{
			return tUpperLLGrpClaimUserSchema.getUserCode();
		}
		

		String tempUserCode = tUpperLLGrpClaimUserSchema.getUserCode();
		LLClaimSchema tLLClaimSchema=mLLClaimPubFunBL.getLLClaim(pClmNo);
		 
		 /**
		  * 2009-05-08 zhangzheng
		  * 如果审核结论是拒付,由于拒付金额是存放在declinepay字段不是存放的realpay,所以权限校验金额字段取declinepay
		  */
		 
	    String tPopedomSql="";
		 
	    //责任免除件
	    if(tLLClaimSchema.getCasePayType().trim().equals("4"))
		 {			 
			 tPopedomSql = "select distinct 1 from LLGrpClaimPopedom a where"
					+ " a.JobCategory=(select b.JobCategory from llGrpclaimuser b where usercode='"
					+ tempUserCode
					+ "' ) and a.basemax>(select nvl(sum(DeclinePay),0) from llclaim where clmno='"
					+ pClmNo + "')";
			 
			logger.debug("判断是否够审批权限的sql:" + tPopedomSql);
	
		 }
		 else
		 {		 
			 tPopedomSql = "select distinct 1 from LLGrpClaimPopedom a where"
					+ " a.JobCategory=(select b.JobCategory from llGrpclaimuser b where usercode='"
					+ tempUserCode
					+ "' ) and a.basemax>(select nvl(sum(realpay),0) from llclaim where clmno='"
					+ pClmNo + "')";
			 
			 logger.debug("判断是否够审批权限的sql:" + tPopedomSql);
		} 
	    
		ExeSQL tExeSql = new ExeSQL();
		String tResult = tExeSql.getOneValue(tPopedomSql);
		if (!"1".equals(tResult)) {
			tempUserCode = PubConfirmNewPopedom(pClmNo, tempUserCode);
		}

		return tempUserCode;

	}

	/**
	 * 用于测试
	 * 
	 * @return
	 */
	public static void main(String[] args) {
		LLClaimPopedomSetBL mLLClaimPopedomSetBL = new LLClaimPopedomSetBL();
		mLLClaimPopedomSetBL.dealData();
	}
}

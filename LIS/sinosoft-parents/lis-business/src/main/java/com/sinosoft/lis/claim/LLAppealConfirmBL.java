/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LJSGetClaimDB;
import com.sinosoft.lis.db.LJSGetDB;
import com.sinosoft.lis.db.LLAppealDB;
import com.sinosoft.lis.db.LLCaseDB;
import com.sinosoft.lis.db.LLClaimDB;
import com.sinosoft.lis.db.LLClaimDetailDB;
import com.sinosoft.lis.db.LLClaimUWMainDB;
import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LJAGetClaimSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LLAppealSchema;
import com.sinosoft.lis.schema.LLClaimDetailSchema;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.schema.LLClaimUWMainSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.vschema.LJAGetClaimSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJSGetClaimSet;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.lis.vschema.LLCaseSet;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.lis.vschema.LLClaimSet;
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
 * Description: 申请申诉确认业务逻辑处理类
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

public class LLAppealConfirmBL {
private static Logger logger = Logger.getLogger(LLAppealConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	private MMap map = new MMap();
	private TransferData mTransferData = new TransferData();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	private LLAppealSchema mLLAppealSchema = new LLAppealSchema();
	private LLClaimSchema mLLClaimSchema = new LLClaimSchema();
	private LLClaimUWMainSchema mLLClaimUWMainSchema = new LLClaimUWMainSchema();
	private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();
	private LJAGetSet mLJAGetSet = new LJAGetSet();
	private Reflections ref = new Reflections();
	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL(); // 理赔公用方法类

	private GlobalInput mG = new GlobalInput();
	private String mClmNo = "";
	private String mCusNo = ""; // 客户号码
	private String mMngCom = ""; // 立案管理机构
	private String mAccDate = ""; // 意外事故发生日期
	private String mRgtDate = ""; // 立案日期

	public LLAppealConfirmBL() {
	}

	public static void main(String[] args) {
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
		logger.debug("----------LLAppealConfirmBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData())
			return false;
		logger.debug("----------after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------after checkInputData----------");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("----------after dealData----------");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------after prepareOutputData----------");

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLAppealConfirmBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("---start getInputData()");
		// 获取页面信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);// 按类取值
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("ClaimNo");

		if (mClmNo == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAppealConfirmBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接受的赔案号为空!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		/**
		 * ---------------------------------------------------------------------BEG
		 * No.1 冲减保额
		 * ----------------------------------------------------------------------
		 */
		// 取得原赔案号,并更新表
		LLAppealSchema tLLAppealSchema = new LLAppealSchema();
		LLAppealDB tLLAppealDB = new LLAppealDB();
		tLLAppealDB.setAppealNo(mClmNo);
		tLLAppealDB.getInfo();
		tLLAppealSchema = tLLAppealDB.getSchema();
		tLLAppealSchema.setAppealState("1");

		map.put(tLLAppealSchema, "DELETE&INSERT");

		logger.debug("##########当前赔案号为:" + mClmNo);
		logger.debug("##########原赔案号为:" + tLLAppealSchema.getCaseNo());

		// 查询赔付明细表
		LLClaimDetailSet tLLClaimDetailSet = new LLClaimDetailSet();
		LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
		String tSql = " select * from LLClaimDetail where 1=1"
				+ " and ClmNo = '" + "?ClmNo?" + "'"
				+ " and substr(GetDutyKind,2,2) <> '09'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("ClmNo", tLLAppealSchema.getCaseNo());
		tLLClaimDetailSet = tLLClaimDetailDB.executeQuery(sqlbv);
		if (tLLClaimDetailSet == null || tLLClaimDetailSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAppealApplyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询赔付明细表信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int i = 1; i <= tLLClaimDetailSet.size(); i++) {
			// 取得新旧给付金额
			double tOldPay = tLLClaimDetailSet.get(i).getRealPay();
			LLClaimDetailSchema tLLClaimDetailSchema = new LLClaimDetailSchema();
			LLClaimDetailDB ttLLClaimDetailDB = new LLClaimDetailDB();
			ttLLClaimDetailDB.setClmNo(mClmNo);
			ttLLClaimDetailDB.setCaseNo(mClmNo);
			ttLLClaimDetailDB.setPolNo(tLLClaimDetailSet.get(i).getPolNo());
			ttLLClaimDetailDB.setCaseRelaNo(tLLClaimDetailSet.get(i)
					.getCaseRelaNo());
			ttLLClaimDetailDB.setGetDutyCode(tLLClaimDetailSet.get(i)
					.getGetDutyCode());
			ttLLClaimDetailDB.setGetDutyKind(tLLClaimDetailSet.get(i)
					.getGetDutyKind());
			ttLLClaimDetailDB.setDutyCode(tLLClaimDetailSet.get(i)
					.getDutyCode());
			ttLLClaimDetailDB.getInfo();
			tLLClaimDetailSchema.setSchema(ttLLClaimDetailDB.getSchema());
			double tCurrentPay = tLLClaimDetailSchema.getRealPay();

			// 领取项表已领金额
			LCGetSchema tLCGetSchema = new LCGetSchema();
			LCGetDB tLCGetDB = new LCGetDB();
			tLCGetDB.setPolNo(tLLClaimDetailSet.get(i).getPolNo());
			tLCGetDB.setGetDutyCode(tLLClaimDetailSet.get(i).getGetDutyCode());
			tLCGetDB.setDutyCode(tLLClaimDetailSet.get(i).getDutyCode());
			tLCGetDB.getInfo();
			tLCGetSchema.setSchema(tLCGetDB.getSchema());
			double tSumMoney = tLCGetSchema.getSumMoney();

			// 计算更新领取项表
			logger.debug("##########当前第" + i + "项申诉赔付金额为:" + tCurrentPay);
			logger.debug("##########原该项赔付金额为:" + tOldPay);
			logger.debug("##########当前项赔付金额为:" + tCurrentPay);
			logger.debug("##########当前已领金额为:" + tSumMoney);

			tSumMoney = tSumMoney + tCurrentPay - tOldPay;

			logger.debug("##########更新后的金额为:" + tSumMoney);

			// 判断保额冲减是否超过投保保额
			// 问题: (1)帐户型的未判断 (2)是否冲减其他险种未判断
			String tRiskCode = StrTool
					.cTrim(tLLClaimDetailSchema.getRiskCode());
			String tPosFlag = StrTool.cTrim(tLLClaimDetailSchema.getPosFlag());// 0未做保全,1做过保全
			String tPosPosEdorNo = StrTool.cTrim(tLLClaimDetailSchema
					.getPosEdorNo());// 保全批单号
			String tNBPolNo = StrTool.cTrim(tLLClaimDetailSchema.getNBPolNo());// 承保时的保单号

			if (mLLClaimPubFunBL.getLMRiskSort(tRiskCode, "7")) // 是7才进行冲减操作
			{
				double tBalAmnt = 0;
				/**
				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 对做过保全的进行处理
				 * 0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化 如果 tNBAmnt > tPosAmnt
				 * {[做保全前]--5万 > 减保后的保额[做保全后]--2万} tRealPay > tBalAmnt {用给付金额 与
				 * 做保全前的有效保额相比较} －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
				 */
				if (tPosFlag.equals("2")) {
					LPGetSchema tLPGetSchema = mLLClaimPubFunBL.getLPGet(
							tPosPosEdorNo, tNBPolNo, tLCGetSchema);
					if (tLPGetSchema == null) {
						mErrors.addOneError("查询保全操作失败!");
						return false;
					}
					double tNBAmnt = tLPGetSchema.getStandMoney(); // 承保时的保额[做保全前]--5万
					double tPosAmnt = tLCGetSchema.getStandMoney(); // 减保后的保额[做保全后]--2万
					tBalAmnt = tNBAmnt - tSumMoney; // 做保全前的有效保额试冲减

					if (tBalAmnt <= 0) {
						mErrors.addOneError("保单险种号:[" + tLPGetSchema.getPolNo()
								+ "]做过保全减保,减保前的有效保额为:[" + tNBAmnt
								+ "],累计本次冲减的保额为:[" + tSumMoney + "],保额不足！");
						return false;
					}
				} else {
					tBalAmnt = tLCGetSchema.getStandMoney() - tSumMoney;// 做保全前的有效保额试冲减
					if (tBalAmnt <= 0) {
						mErrors.addOneError("保单险种号:[" + tLCGetSchema.getPolNo()
								+ "]基本保额:[" + tLCGetSchema.getStandMoney()
								+ "],累计本次冲减的保额为:[" + tSumMoney + "],保额不足！");
						return false;
					}
				}

				tLCGetSchema.setSumMoney(tSumMoney);
				map.put(tLCGetSchema, "DELETE&INSERT");

			}// end if
		}
		// ----------------------------------------------------------------------END

		/**
		 * ---------------------------------------------------------------------BEG
		 * No.2 生成财务数据处理
		 * ----------------------------------------------------------------------
		 */
		if (!dealData05()) {
			return false;
		}
		// ----------------------------------------------------------------------END

		/**
		 * ---------------------------------------------------------------------BEG
		 * No.3 更新赔案状态
		 * ----------------------------------------------------------------------
		 */
		// 赔案表
		LLClaimUWMainDB tLLClaimUWMainDB = new LLClaimUWMainDB();
		tLLClaimUWMainDB.setClmNo(mClmNo);
		tLLClaimUWMainDB.getInfo();
		mLLClaimUWMainSchema = tLLClaimUWMainDB.getSchema();
		mLLClaimUWMainSchema.setExamDate(CurrentDate); // 审批日期
		mLLClaimUWMainSchema.setExamPer(mG.Operator);
		mLLClaimUWMainSchema.setExamCom(mG.ManageCom);
		mLLClaimUWMainSchema.setModifyDate(CurrentDate);
		mLLClaimUWMainSchema.setModifyTime(CurrentTime);

		// 赔案表
		LLClaimDB tLLClaimDB = new LLClaimDB();
		tLLClaimDB.setClmNo(mClmNo);
		tLLClaimDB.getInfo();
		mLLClaimSchema = tLLClaimDB.getSchema();
		mLLClaimSchema.setClmState("60"); // 赔案状态置为已结案
		mLLClaimSchema.setOperator(mG.Operator);
		mLLClaimSchema.setEndCaseDate(CurrentDate);
		mLLClaimSchema.setModifyDate(CurrentDate);
		mLLClaimSchema.setModifyTime(CurrentTime);

		// 立案表
		LLRegisterDB tLLRegisterDB = new LLRegisterDB();
		tLLRegisterDB.setRgtNo(mClmNo);
		tLLRegisterDB.getInfo();
		mLLRegisterSchema = tLLRegisterDB.getSchema();
		mLLRegisterSchema.setClmState("60"); // 赔案状态置为已结案
		mLLRegisterSchema.setEndCaseFlag("1");
		mLLRegisterSchema.setEndCaseDate(CurrentDate);
		mLLRegisterSchema.setModifyDate(CurrentDate);
		mLLRegisterSchema.setModifyTime(CurrentTime);

		map.put(mLLClaimUWMainSchema, "DELETE&INSERT");
		map.put(mLLRegisterSchema, "DELETE&INSERT");
		map.put(mLLClaimSchema, "DELETE&INSERT");
		
		//赔案明细表
		String sql1 = " update llclaimpolicy set ClmState = '60' where"
			+ " clmno = '" + "?clmno?" + "' and endcasedate='"+"?endcasedate?"+"'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(sql1);
		sqlbv1.put("clmno", mClmNo);
		sqlbv1.put("endcasedate", CurrentDate);
		map.put(sqlbv1, "UPDATE");
		/**
		 * ---------------------------------------------------------------------BEG
		 * No.4 生成打印数据
		 * ----------------------------------------------------------------------
		 */
		// 查询赔付结论
		LLClaimDB ttLLClaimDB = new LLClaimDB();
		ttLLClaimDB.setClmNo(mClmNo);
		LLClaimSet tLLClaimSet = ttLLClaimDB.query();
		String tGType = tLLClaimSet.get(1).getGiveType();
		if (tGType.equals("1")) // 赔案拒付
		{
			if (!insertLOPRTManager("PCT005")) // 插入理赔决定通知书－拒付[赔案层面]
			{
				return false;
			}
		}
		// 不管为给付拒付,均出给付批注
		if (!insertLOPRTManager("PCT010")) // 插入批单-理赔给付批注[个人]
		{
			return false;
		}

		return true;
	}

	/**
	 * 生成财务数据处理 a) 将LJSGet应付总表、LJSGetClaim赔付应付表的数据先转移到LJAGet实付总表，
	 * LJAGetClaim赔付实付表后，删除应付数据 b) 实付号：PubFun1.CreateMaxNo("GETNO", StrLimit);
	 * c) 将LLbnf表中的，CasePayFlag保险金支付标志置为1，已支付 d)
	 * 将LLBalance表中的，PayFlag金支付标志置为1，已支付
	 */
	private boolean dealData05() {
		/**
		 * ---------------------------------------------------------------------BEG
		 * No.1 将LJSGet应付总表、LJSGetClaim赔付应付表的数据先转移到LJAGet实付总表，
		 * LJAGetClaim赔付实付表后，删除应付数据
		 * ----------------------------------------------------------------------
		 */
		// 应付总表
		LJSGetDB tLJSGetDB = new LJSGetDB();
		tLJSGetDB.setOtherNo(mClmNo);
		tLJSGetDB.setOtherNoType("5");
		LJSGetSet tLJSGetSet = new LJSGetSet();
		tLJSGetSet = tLJSGetDB.query();
		if (tLJSGetSet != null && tLJSGetSet.size() != 0) {
			LJAGetSchema tLJAGetSchema = new LJAGetSchema();
			mLJAGetSet.add(tLJAGetSchema);
			ref.transFields(mLJAGetSet, tLJSGetSet);
			for (int j = 1; j <= tLJSGetSet.size(); j++) {
				String tNo = PubFun1.CreateMaxNo("GETNO", 10); // 生成实付号
				mLJAGetSet.get(j).setActuGetNo(tNo);
				mLJAGetSet.get(j).setShouldDate(CurrentDate); // 应付日期
				mLJAGetSet.get(j).setOperator(mG.Operator);
				mLJAGetSet.get(j).setMakeDate(CurrentDate);
				mLJAGetSet.get(j).setMakeTime(CurrentTime);
				mLJAGetSet.get(j).setModifyDate(CurrentDate);
				mLJAGetSet.get(j).setModifyTime(CurrentTime);
				// mLJAGetSet.get(j).setConfDate(CurrentDate);//财务确认日期

				// 赔付应付表
				LJAGetClaimSet tLJAGetClaimSet = new LJAGetClaimSet();
				LJSGetClaimDB tLJSGetClaimDB = new LJSGetClaimDB();
				tLJSGetClaimDB.setOtherNo(mClmNo);
				tLJSGetClaimDB.setOtherNoType("5");
				tLJSGetClaimDB.setGetNoticeNo(tLJSGetSet.get(j)
						.getGetNoticeNo());
				LJSGetClaimSet tLJSGetClaimSet = new LJSGetClaimSet();
				tLJSGetClaimSet = tLJSGetClaimDB.query();
				if (tLJSGetClaimSet != null && tLJSGetClaimSet.size() != 0) {
					LJAGetClaimSchema tLJAGetClaimSchema = new LJAGetClaimSchema();
					tLJAGetClaimSet.add(tLJAGetClaimSchema);
					ref.transFields(tLJAGetClaimSet, tLJSGetClaimSet);
					for (int k = 1; k <= tLJSGetClaimSet.size(); k++) {
						tLJAGetClaimSet.get(k).setActuGetNo(tNo);
						tLJAGetClaimSet.get(k).setOPConfirmCode(mG.Operator);
						tLJAGetClaimSet.get(k).setOPConfirmDate(CurrentDate);
						tLJAGetClaimSet.get(k).setOPConfirmTime(CurrentTime);
						tLJAGetClaimSet.get(k).setOperator(mG.Operator);
						tLJAGetClaimSet.get(k).setMakeDate(CurrentDate);
						tLJAGetClaimSet.get(k).setMakeTime(CurrentTime);
						tLJAGetClaimSet.get(k).setModifyDate(CurrentDate);
						tLJAGetClaimSet.get(k).setModifyTime(CurrentTime);
						// tLJAGetClaimSet.get(k).setConfDate(CurrentDate);//财务确认日期

						// 如果支付金额为零,就不添加到实付表
						if (tLJAGetClaimSet.get(k).getPay() != 0) {
							map.put(tLJAGetClaimSet.get(k), "DELETE&INSERT");
						}

					}
					map.put(tLJSGetClaimSet, "DELETE");

					// 如果总给付金额为零,就不添加到实付表
					if (mLJAGetSet.get(j).getSumGetMoney() != 0) {
						map.put(mLJAGetSet.get(j), "DELETE&INSERT");
					}

				}
			}
			map.put(tLJSGetSet, "DELETE");
		}
		// ----------------------------------------------------------------------END
		/**
		 * ---------------------------------------------------------------------BEG
		 * No.2 将LLbnf表中的，CasePayFlag保险金支付标志置为1，已支付
		 * --------------------------------------------------------------------
		 */
		String tUpdateSql1 = "";
		tUpdateSql1 = "update llbnf a set a.casepayflag = '1' where "
				+ "a.clmno = '" + "?clmno?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tUpdateSql1);
		sqlbv2.put("clmno", mClmNo);
		map.put(sqlbv2, "UPDATE");
		// ----------------------------------------------------------------------END

		/**
		 * ---------------------------------------------------------------------BEG
		 * No.3 将LLBalance表中的，PayFlag金支付标志置为1，已支付
		 * --------------------------------------------------------------------
		 */
		String tUpdateSql2 = "";
		tUpdateSql2 = "update LLBalance a set a.Payflag = '1' where "
				+ "a.clmno = '" + "?clmno?" + "'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tUpdateSql2);
		sqlbv3.put("clmno", mClmNo);
		map.put(sqlbv3, "UPDATE");
		// ----------------------------------------------------------------------END
		return true;
	}

	/**
	 * 添加打印数据 2005-8-16 14:49
	 * 
	 * @return boolean
	 */
	private boolean insertLOPRTManager(String tCode) {
		// 查询立案信息
		LLCaseDB tLLCaseDB = new LLCaseDB();
		String tSSql = "select * from llcase where 1=1 " + " and caseno = '"
				+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSSql);
		sqlbv4.put("clmno", mClmNo);
		LLCaseSet tLLCaseSet = tLLCaseDB.executeQuery(sqlbv4);
		mCusNo = tLLCaseSet.get(1).getCustomerNo();
		mMngCom = tLLCaseSet.get(1).getMngCom();

		LLRegisterDB tLLRegisterDB = new LLRegisterDB();
		LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
		tLLRegisterDB.setRgtNo(mClmNo);
		if (tLLRegisterDB.getInfo()) {
			tLLRegisterSchema.setSchema(tLLRegisterDB.getSchema());
		}
		mRgtDate = tLLRegisterSchema.getRgtDate(); // 立案日期

		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

		// 生成印刷流水号
		String strNolimit = PubFun.getNoLimit(mMngCom);
		logger.debug("strlimit=" + strNolimit);
		String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNolimit);
		logger.debug("-----生成的LOPRTManager的印刷流水号= " + tPrtSeq);

		tLOPRTManagerSchema.setPrtSeq(tPrtSeq); // 印刷流水号
		tLOPRTManagerSchema.setOtherNo(mClmNo); // 对应其它号码
		tLOPRTManagerSchema.setOtherNoType("05"); // 其它号码类型--05赔案号
		tLOPRTManagerSchema.setCode(tCode); // 单据编码--单证通知书
		tLOPRTManagerSchema.setManageCom(mMngCom); // 立案管理机构
		tLOPRTManagerSchema.setReqCom(mG.ComCode); // 发起机构
		tLOPRTManagerSchema.setReqOperator(mG.Operator); // 发起人
		tLOPRTManagerSchema.setPrtType("0"); // 打印方式
		tLOPRTManagerSchema.setStateFlag("0"); // 打印状态
		tLOPRTManagerSchema.setMakeDate(CurrentDate); // 入机日期(报案日期)
		tLOPRTManagerSchema.setMakeTime(CurrentTime); // 入机时间
		tLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
		tLOPRTManagerSchema.setStandbyFlag1(CurrentDate); // 批打检索日期
		tLOPRTManagerSchema.setStandbyFlag4(mCusNo); // 客户号码
		tLOPRTManagerSchema.setStandbyFlag5(mRgtDate); // 立案日期
		tLOPRTManagerSchema.setStandbyFlag6("50"); // 赔案状态

		map.put(tLOPRTManagerSchema, "INSERT");
		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAppealConfirmBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

}

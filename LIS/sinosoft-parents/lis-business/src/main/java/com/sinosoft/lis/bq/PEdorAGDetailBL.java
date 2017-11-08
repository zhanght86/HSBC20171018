package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LPGetBL;
import com.sinosoft.lis.bl.LPPolBL;
import com.sinosoft.lis.db.LJABonusGetDB;
import com.sinosoft.lis.db.LJSGetDrawDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LJSGetDrawSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LJABonusGetSet;
import com.sinosoft.lis.vschema.LJSGetDrawSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 年金，满期金给付BL类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Nicholas
 * @version 1.0
 */

public class PEdorAGDetailBL {
private static Logger logger = Logger.getLogger(PEdorAGDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 计算要素 */
	private BqCalBase mBqCalBase = new BqCalBase();

	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private LJSGetDrawSet mLJSGetDrawSet = new LJSGetDrawSet(); // 把这些笔钱做状态标记
	private LPPolSchema mLPPolSchema = new LPPolSchema();

	// ==add=======liuxiaosong=======2006-12-26=========保全磁盘导入=========start====
	private TransferData mTransferData = new TransferData();

	// ==add=======liuxiaosong=======2006-12-26=========保全磁盘导入========end=======

	public PEdorAGDetailBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		logger.debug("after getInputData...");

		// 数据校验
		if (!checkData()) {
			return false;
		}

		logger.debug("after checkData...");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("after dealData...");

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mLJSGetDrawSet = (LJSGetDrawSet) mInputData.getObjectByObjectName(
					"LJSGetDrawSet", 0);
			mLPPolSchema = (LPPolSchema) mInputData.getObjectByObjectName(
					"LPPolSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);

			if (mLJSGetDrawSet == null || mGlobalInput == null
					|| mLPEdorItemSchema == null || mLPPolSchema == null) {
				CError.buildErr(this, "传入的数据不完整！");
				return false;
			}
			// ====add========liuxiaosong======2006-12-26=====================start===============
			logger.debug("保全磁盘导入数据补全");
			mTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
			if (mTransferData != null) {
				String tISDiskImport = (String) mTransferData
						.getValueByName("ISDiskImport");
				if ("yes".equals(tISDiskImport)) {
					if (!diskImprotReGetData()) {
						CError.buildErr(this, "目前还没有领取项目！");
						return false;
					}
				}
			}
			// ====add========liuxiaosong======2006-12-26=====================start===============
			// 获得mLPEdorItemSchema的其它信息
			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			tLPEdorItemDB.setSchema(mLPEdorItemSchema);
			if (!tLPEdorItemDB.getInfo()) {
				logger.debug("---Error:在个险保全项目表中无匹配记录！---");
				CError tError = new CError();
				tError.moduleName = "PEdorAGDetailBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "在个险保全项目表中无匹配记录！";
				this.mErrors.addOneError(tError);
				return false;
			}
			this.mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());

			// 获得mLJSGetDrawSet的其它信息
			LJSGetDrawSet tLJSGetDrawSet = new LJSGetDrawSet();
			LJSGetDrawSchema tLJSGetDrawSchema = null;
			tLJSGetDrawSet.set(this.mLJSGetDrawSet);
			this.mLJSGetDrawSet.clear();
			LJSGetDrawDB tLJSGetDrawDB = new LJSGetDrawDB();
			for (int i = 1; i <= tLJSGetDrawSet.size(); i++) {
				tLJSGetDrawSchema = new LJSGetDrawSchema();
				tLJSGetDrawSchema = tLJSGetDrawSet.get(i);
				tLJSGetDrawDB.setSchema(tLJSGetDrawSchema);
				if (!tLJSGetDrawDB.getInfo()) {
					logger.debug("---Error:在个险给付表(生存领取_应付)中无匹配记录！---");
					CError tError = new CError();
					tError.moduleName = "PEdorAGDetailBL";
					tError.functionName = "getInputData";
					tError.errorMessage = "在个险给付表(生存领取_应付)中无匹配记录！";
					this.mErrors.addOneError(tError);
					return false;
				}
				this.mLJSGetDrawSet.add(tLJSGetDrawDB.getSchema());
			}
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败！");
			return false;
		}

		return true;
	}

	// =====add======liuxiaosong====2006-12-26====保全磁盘导入特殊处理===========start=====
	/**
	 * 保全磁盘导入数据补全处理
	 * 
	 * @return boolean
	 */
	private boolean diskImprotReGetData() {
		logger.debug("\t=======@>GrpEdorIRDetailBL->diskImprotReGetData():开始磁盘导入数据补全========");
		String tEdorNO = mLPEdorItemSchema.getEdorNo();
		String tEdorType = "IR";
		String tRiskCode = (String) mTransferData.getValueByName("RiskCode");
		String tPolNo = mLPPolSchema.getPolNo();
		String tCurDate = PubFun.getCurrentDate();

		// mLPPolSchema数据补全---------------------------------------------------
		mLPPolSchema.setEdorNo(tEdorNO);
		mLPPolSchema.setEdorType(tEdorType);
		// ----------------------------------------------------------------------

		// mLJSGetDrawSchema数据补全----------------------------------------------
		// 其实mLJSGetDrawSet在磁盘导入过程中只包含一个PolNo，在此需要补充所有的
		mLJSGetDrawSet.clear();
		// 查询符合标准的领取项信息，该SQL与PEdorTypeAG.js->queryPolGrid()保持一致
		String tSQL = "SELECT distinct "
				+ " b.GetNoticeNo,b.DutyCode,b.GetDutyCode,b.GetDutyKind"
				+ " FROM LJSGetDraw b"
				+ " WHERE b.PolNo='"
				+ "?tPolNo?"
				+ "'"
				+ " and (b.RReportFlag='1' or b.ComeFlag='1')"
				+ " and b.GetDate<=to_date('"
				+ "?tCurDate?"
				+ "','YYYY-MM-DD')"
				+ " and not exists(select 'A' from LCContState where ContNo=b.ContNo and StateType='Loan' and State='1' and EndDate is null and StartDate<=b.GetDate)"
				+ " and not exists(select 'B' from LCContState where PolNo='"
				+ "?tPolNo?"
				+ "' and StateType='PayPrem' and State='1' and EndDate is null and StartDate<=b.GetDate)";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(tSQL);
        sqlbv.put("tPolNo", tPolNo);
        sqlbv.put("tCurDate", tCurDate);
		logger.debug("磁盘导入数据补全SQL:\n" + tSQL);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv);
		int count = tSSRS.getMaxRow(); // 最大行
		if (count == 0) {
			// 目前还没有领取项目
			return false;
		}
		for (int i = 1; i <= count; i++) {
			LJSGetDrawSchema tLJSGetDrawSchema = new LJSGetDrawSchema();
			tLJSGetDrawSchema.setGetNoticeNo(tSSRS.GetText(i, 1));
			tLJSGetDrawSchema.setDutyCode(tSSRS.GetText(i, 2));
			tLJSGetDrawSchema.setGetDutyKind(tSSRS.GetText(i, 4));
			tLJSGetDrawSchema.setGetDutyCode(tSSRS.GetText(i, 3));
			tLJSGetDrawSchema.setPolNo(tPolNo);
			mLJSGetDrawSet.add(tLJSGetDrawSchema);
		}
		// ----------------------------------------------------------------------
		logger.debug("\t=======@>GrpEdorIRDetailBL->diskImprotReGetData():完成磁盘导入数据补全========");
		return true;
	}

	// =====add======liuxiaosong====2006-12-26====保全磁盘导入特殊处理===========END=======

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(mLPEdorItemSchema);

		if (!tLPEdorItemDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorAGDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "无保全申请数据！";
			logger.debug("------" + tError);
			this.mErrors.addOneError(tError);

			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());

		if (tLPEdorItemDB.getEdorState().trim().equals("2")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorAGDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "该保全已经申请确认不能修改！";
			logger.debug("------" + tError);
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		// 获得此时的日期和时间
		String strCurrentDate = PubFun.getCurrentDate();
		String strCurrentTime = PubFun.getCurrentTime();
		String tSql = "";
		SSRS tSSRS = null;
		ExeSQL tExeSQL = null;
		// 记录本次给付的总金额，存入保全项目表
		double tAGMoney = 0.0;

		// 修改对应LPGet里的相应的记录的信息，界面初始化时会在LPGet里校验记录，已选的就不显示了
		LPGetBL tLPGetBL = new LPGetBL();
		LPGetSchema tLPGetSchema = null;
		LJSGetDrawSchema tLJSGetDrawSchema = null;
		int tLJSGDSSize = mLJSGetDrawSet.size();

		LPGetSet tLPGetSet = new LPGetSet();
		for (int i = 1; i <= tLJSGDSSize; i++) {
			tLJSGetDrawSchema = new LJSGetDrawSchema();
			tLJSGetDrawSchema = mLJSGetDrawSet.get(i);
			tLPGetSchema = new LPGetSchema();
			tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPGetSchema.setPolNo(tLJSGetDrawSchema.getPolNo());
			tLPGetSchema.setDutyCode(tLJSGetDrawSchema.getDutyCode());
			tLPGetSchema.setGetDutyCode(tLJSGetDrawSchema.getGetDutyCode());
			if (!tLPGetBL.queryLastLPGet(this.mLPEdorItemSchema, tLPGetSchema)) {
				logger.debug("---Error:未找到应付项对应的领取信息！---");
				return false;
			}
			tLPGetSchema = new LPGetSchema();
			tLPGetSchema = tLPGetBL.getSchema();
			// 准备要修改的信息，暂时先改变这两项
			tLPGetSchema.setGetMode("1");
			tLPGetSchema.setSumMoney(tLPGetSchema.getSumMoney()
					+ tLJSGetDrawSchema.getGetMoney());
			tLPGetSchema.setGettoDate(tLJSGetDrawSchema.getCurGetToDate()); // 改变“领至日期”
			// ****************2005-07-19日讨论**********************************************************
			// 比较新的领至日期是否到达了止领日期，如果到了，无论是年金还是满期金，都要修改“止领标志”为“1 领取终止”
			// 这里只处理应付的情况，实付由PayPlanBL直接处理
			// --2005-07-28日彭谦说如果是趸领，则领取时将止领日期改成起领日期，因为原起止日期不等，做如下修改-->
			if (tLPGetSchema.getGetIntv() == 0) {
				// 趸领
				tLPGetSchema.setGetEndDate(tLPGetSchema.getGetStartDate());
				tLPGetSchema.setGettoDate(tLPGetSchema.getGetStartDate());
				tLPGetSchema.setGetEndState("1");
			} else {

				// FDate tDateGetEndDate = new FDate();
				// FDate tDateGetToDate = new FDate();
				// if
				// (tDateGetToDate.getDate(tLPGetSchema.getGettoDate()).compareTo(tDateGetEndDate.getDate(tLPGetSchema.getGetEndDate()))
				// > 0)
				// {
				// tLPGetSchema.setGetEndState("1");
				// }

				String sGetToDate = tLPGetSchema.getGettoDate(); // 本次领至日期
				String sGetEndDate = tLPGetSchema.getGetEndDate(); // 止领日期
				// 止领日期往后推11个月，为了兼容月领描述
				String sYearLaterGetEndDate = PubFun.calDate(sGetEndDate, 11,
						"M", null);

				int iIntv = PubFun.calInterval(sYearLaterGetEndDate,
						sGetToDate, "D");
				if (iIntv > 0) {
					tLPGetSchema.setGetEndState("1");
				}
			}
			// *****************************************************************************************
			tLPGetSchema.setOperator(this.mGlobalInput.Operator);
			tLPGetSchema.setModifyDate(strCurrentDate);
			tLPGetSchema.setModifyTime(strCurrentTime);
			tLPGetSet.add(tLPGetSchema);
			// 累加金额
			tAGMoney += tLJSGetDrawSchema.getGetMoney();
		}
		mMap.put(tLPGetSet, "DELETE&INSERT");

		// 修改LPPol的领取方式和帐户相关信息
		LPPolBL tLPPolBL = new LPPolBL();
		if (!tLPPolBL.queryLastLPPol(this.mLPEdorItemSchema, this.mLPPolSchema)) {
			return false;
		}
		LPPolSchema tLPPolSchema = new LPPolSchema();
		tLPPolSchema.setSchema(tLPPolBL.getSchema());
		// 更新保全相关信息
		tLPPolSchema.setGetForm(this.mLPPolSchema.getGetForm());
		if (this.mLPPolSchema.getGetForm().equals("0")) {
			tLPPolSchema.setGetBankCode(this.mLPPolSchema.getGetBankCode());
			tLPPolSchema.setGetBankAccNo(this.mLPPolSchema.getGetBankAccNo());
			tLPPolSchema.setGetAccName(this.mLPPolSchema.getGetAccName());
		}
		tLPPolSchema.setOperator(this.mGlobalInput.Operator);
		tLPPolSchema.setModifyDate(strCurrentDate);
		tLPPolSchema.setModifyTime(strCurrentTime);
		mMap.put(tLPPolSchema, "DELETE&INSERT");

		// 准备“批改补退费表（应收/应付）表”信息
		LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
		LJSGetEndorseSchema tLJSGetEndorseSchema = null;
		// LJSGetDrawSet tLJSGetDrawSet = new LJSGetDrawSet();
		// tLJSGetDrawSet = mLJSGetDrawSet;
		LJSGetDrawSchema tempLJSGetDrawSchema = null;
		LJSGetDrawSet tempLJSGetDrawSet = null;
		// boolean haveOneFlag;
		String nowGetType = new String(); // 给付分类1: 0 －－ 满期金 1 －－ 年金
		int i;
		// String nowGetNoticeNo = new String();
		// String tempString = null;

		// while (tLJSGetDrawSet.size() > 0)
		// {
		// i = 1;
		// haveOneFlag = false;
		// do
		// {
		// tempLJSGetDrawSchema = new LJSGetDrawSchema();
		// tempLJSGetDrawSchema = tLJSGetDrawSet.get(i);
		// if (!haveOneFlag)
		// {
		// tempLJSGetDrawSet = new LJSGetDrawSet();
		// tempLJSGetDrawSet.add(tempLJSGetDrawSchema);
		// tLJSGetDrawSet.remove(tempLJSGetDrawSchema);
		// nowGetNoticeNo = tempLJSGetDrawSchema.getGetNoticeNo();
		// haveOneFlag = true;
		// //记录当前“给付分类1”
		// nowGetType =
		// getGetTypeByGetDutyCode(tempLJSGetDrawSchema.getGetDutyCode());
		// if (nowGetType == null)
		// {
		// return false;
		// }
		// }
		// else
		// {
		// tempString =
		// getGetTypeByGetDutyCode(tempLJSGetDrawSchema.getGetDutyCode());
		// if (tempString == null)
		// {
		// return false;
		// }
		// if (tempLJSGetDrawSchema.getGetNoticeNo().equals(nowGetNoticeNo) &&
		// nowGetType.equals(tempString))
		// {
		// tempLJSGetDrawSet.add(tempLJSGetDrawSchema);
		// tLJSGetDrawSet.remove(tempLJSGetDrawSchema);
		// }
		// else
		// {
		// i++;
		// }
		// }
		// }
		// while(i<=tLJSGetDrawSet.size());
		// //生成“批改补退费表（应收/应付）表”信息”
		// tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		// tLJSGetEndorseSchema =
		// getLJSGetEndorseSchemaBySet(tempLJSGetDrawSet,nowGetType);
		// if (tLJSGetEndorseSchema == null)
		// {
		// return false;
		// }
		// tLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		// }

		// 生成“批改补退费表（应收/应付）表”信息，一条LJSGetDraw记录对应一条LJSGetEndorse记录，2005-08-09日修改
		for (i = 1; i <= mLJSGetDrawSet.size(); i++) {
			tempLJSGetDrawSchema = new LJSGetDrawSchema();
			tempLJSGetDrawSchema = mLJSGetDrawSet.get(i);
			tempLJSGetDrawSet = new LJSGetDrawSet();
			tempLJSGetDrawSet.add(tempLJSGetDrawSchema);
			// 记录当前“给付分类1”
			nowGetType = getGetTypeByGetDutyCode(tempLJSGetDrawSchema
					.getGetDutyCode());
			tLJSGetEndorseSchema = new LJSGetEndorseSchema();
			tLJSGetEndorseSchema = getLJSGetEndorseSchemaBySet(
					tempLJSGetDrawSet, nowGetType, i);
			tLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		}
        //营改增 add zhangyingfeng 2016-07-14
        //价税分离 计算器
        TaxCalculator.calBySchemaSet(tLJSGetEndorseSet);
        //end zhangyingfeng 2016-07-14
		mMap.put(tLJSGetEndorseSet, "DELETE&INSERT");

		LJABonusGetDB tLJABonusGetDB = new LJABonusGetDB();
		LJABonusGetSet tLJABonusGetSet = new LJABonusGetSet();
		String tsql = "SELECT * FROM LJABonusGet WHERE OtherNo='"
				+ "?OtherNo?"
				+ "'"
				+ " and Enteraccdate is null and Confdate is null and state='0'"
				+ " and GetDate<= '" + "?GetDate?"
				+ "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tsql);
		sqlbv.put("OtherNo", mLPEdorItemSchema.getPolNo());
		sqlbv.put("GetDate", mLPEdorItemSchema.getEdorValiDate());
		tLJABonusGetSet = tLJABonusGetDB.executeQuery(sqlbv);
		LJSGetEndorseSet rLJSGetEndorseSet = new LJSGetEndorseSet();
		if (tLJABonusGetSet != null && tLJABonusGetSet.size() > 0) {
			for (int j = 1; j <= tLJABonusGetSet.size(); j++) {
				tLJSGetEndorseSchema = new LJSGetEndorseSchema();
				tLJSGetEndorseSchema.setGetNoticeNo(this.mLPEdorItemSchema
						.getEdorAcceptNo());
				tLJSGetEndorseSchema.setEndorsementNo(this.mLPEdorItemSchema
						.getEdorNo());
				tLJSGetEndorseSchema.setFeeOperationType("AG");
				tLJSGetEndorseSchema.setFeeFinaType(tLJABonusGetSet.get(j)
						.getFeeFinaType());
				tLJSGetEndorseSchema.setGrpContNo(mLJSGetDrawSet.get(1)
						.getGrpContNo());
				tLJSGetEndorseSchema.setContNo(this.mLPEdorItemSchema
						.getContNo());
				tLJSGetEndorseSchema.setGrpPolNo(mLJSGetDrawSet.get(1)
						.getGrpPolNo());
				tLJSGetEndorseSchema
						.setPolNo(this.mLPEdorItemSchema.getPolNo());
				tLJSGetEndorseSchema.setOtherNo(tLJABonusGetSet.get(j)
						.getGetNoticeNo());
				tLJSGetEndorseSchema.setOtherNoType("5");
				tLJSGetEndorseSchema.setDutyCode(mLJSGetDrawSet.get(1)
						.getDutyCode());
				tLJSGetEndorseSchema.setPayPlanCode("000000");
				tLJSGetEndorseSchema.setAppntNo(mLJSGetDrawSet.get(1)
						.getAppntNo());
				tLJSGetEndorseSchema.setInsuredNo(mLJSGetDrawSet.get(1)
						.getInsuredNo());
				tLJSGetEndorseSchema.setGetDate(tLJABonusGetSet.get(j)
						.getGetDate());
				tLJSGetEndorseSchema.setGetMoney(-tLJABonusGetSet.get(j)
						.getGetMoney()); // 付钱，所以为负数
				tLJSGetEndorseSchema.setKindCode(mLJSGetDrawSet.get(1)
						.getKindCode());
				tLJSGetEndorseSchema.setRiskCode(mLJSGetDrawSet.get(1)
						.getRiskCode());
				tLJSGetEndorseSchema.setRiskVersion(mLJSGetDrawSet.get(1)
						.getRiskVersion());
				tLJSGetEndorseSchema.setManageCom(tLJABonusGetSet.get(j)
						.getManageCom());
				tLJSGetEndorseSchema.setAgentCom(tLJABonusGetSet.get(j)
						.getAgentCom());
				tLJSGetEndorseSchema.setAgentType(tLJABonusGetSet.get(j)
						.getAgentType());
				tLJSGetEndorseSchema.setAgentCode(tLJABonusGetSet.get(j)
						.getAgentCode());
				tLJSGetEndorseSchema.setAgentGroup(tLJABonusGetSet.get(j)
						.getAgentGroup());
				tLJSGetEndorseSchema.setGrpName(mLJSGetDrawSet.get(1)
						.getGrpName());
				tLJSGetEndorseSchema.setHandler(mLJSGetDrawSet.get(1)
						.getHandler());
				tLJSGetEndorseSchema.setPolType(mLJSGetDrawSet.get(1)
						.getPolType());
				tLJSGetEndorseSchema.setApproveCode("");
				tLJSGetEndorseSchema.setApproveDate("");
				tLJSGetEndorseSchema.setApproveTime("");
				tLJSGetEndorseSchema.setGetFlag("");
				tLJSGetEndorseSchema.setSerialNo("");
				tLJSGetEndorseSchema.setOperator(this.mGlobalInput.Operator);
				tLJSGetEndorseSchema.setMakeDate(strCurrentDate);
				tLJSGetEndorseSchema.setMakeTime(strCurrentTime);
				tLJSGetEndorseSchema.setModifyDate(strCurrentDate);
				tLJSGetEndorseSchema.setModifyTime(strCurrentTime);
				tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Get_GetDraw
						+ String.valueOf(j));

		          //营改增 add zhangyingfeng 2016-07-14
		          //价税分离 计算器
		          TaxCalculator.calBySchema(tLJSGetEndorseSchema);
		          //end zhangyingfeng 2016-07-14
				rLJSGetEndorseSet.add(tLJSGetEndorseSchema);
				tAGMoney += tLJABonusGetSet.get(j).getGetMoney();
			}
		}
		mMap.put(rLJSGetEndorseSet, "DELETE&INSERT");

		// 修改“个险保全项目表”相应信息
		mLPEdorItemSchema.setEdorState("3");
		mLPEdorItemSchema.setOperator(this.mGlobalInput.Operator);
		mLPEdorItemSchema.setModifyDate(strCurrentDate);
		mLPEdorItemSchema.setModifyTime(strCurrentTime);
		mLPEdorItemSchema.setGetMoney(-tAGMoney);
		mMap.put(mLPEdorItemSchema, "UPDATE");

		mResult.clear();
		mResult.add(mMap);
		mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
		mBqCalBase.setRiskCode(tLPPolSchema.getRiskCode());
		mBqCalBase.setEdorNo(mLPEdorItemSchema.getEdorNo());
		// 计算要素“保单贷款本息和”
		BqPolBalBL tBqPolBalBL = new BqPolBalBL();
		if (tBqPolBalBL.calContLoanAndAutoPay(mLPEdorItemSchema.getContNo(),
				mLPEdorItemSchema.getEdorValiDate())) {
			mBqCalBase.setContSumLoan(tBqPolBalBL.getCalResult());
		}
		mResult.add(mBqCalBase);

		return true;
	}

	// 通过传入的LJSGetDrawSet生成一个LJSGetEndorseSchema，这些LJSGetDrawSet的GetNoticeNo都是相同的
	private LJSGetEndorseSchema getLJSGetEndorseSchemaBySet(
			LJSGetDrawSet aLJSGetDrawSet, String aGetType, int aSerial) {
		try {
			if (aLJSGetDrawSet == null) {
				return null;
			}

			String strCurrentDate = PubFun.getCurrentDate();
			String strCurrentTime = PubFun.getCurrentTime();

			LJSGetDrawSchema tLJSGetDrawSchema = new LJSGetDrawSchema();
			tLJSGetDrawSchema = aLJSGetDrawSet.get(1);
			LJSGetEndorseSchema rLJSGetEndorseSchema = new LJSGetEndorseSchema();
			// rLJSGetEndorseSchema.setGetNoticeNo(this.mLPEdorItemSchema.getEdorAcceptNo());
			rLJSGetEndorseSchema.setGetNoticeNo(this.mLPEdorItemSchema
					.getEdorAcceptNo());
			rLJSGetEndorseSchema.setEndorsementNo(this.mLPEdorItemSchema
					.getEdorNo());
			rLJSGetEndorseSchema.setFeeOperationType("AG");
			// 查询“补/退费财务类型”，如所传RiskCode没有对应的，就查000000的，再没有就返回null，报错
			String tSql = null;
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			if (aGetType.equals("1")) {
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tSql = "SELECT CodeName"
						+ " FROM ((select '1' as flag,CodeName"
						+ " from LDCode1"
						+ " where CodeType='AG' and Code='NJJF' and Code1='"
						+ "?Code1?"
						+ "')"
						+ " union"
						+ " (select '2' as flag,CodeName"
						+ " from LDCode1"
						+ " where CodeType='AG' and Code='NJJF' and Code1='000000')) g"
						+ " WHERE rownum=1" + " ORDER BY flag";
				}else if(SysConst.DBTYPE_MYSQL.equals(SysConst.DBTYPE_MYSQL)){
					tSql = "SELECT CodeName"
							+ " FROM ((select '1' as flag,CodeName"
							+ " from LDCode1"
							+ " where CodeType='AG' and Code='NJJF' and Code1='"
							+ "?Code1?"
							+ "')"
							+ " union"
							+ " (select '2' as flag,CodeName"
							+ " from LDCode1"
							+ " where CodeType='AG' and Code='NJJF' and Code1='000000')) g"
							+ " ORDER BY flag"+ " limit 0,1" ;
				}
				sqlbv.sql(tSql);
				sqlbv.put("Code1", tLJSGetDrawSchema.getRiskCode());
			} else {
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
					tSql = "SELECT CodeName"
							+ " FROM ((select '1' as flag,CodeName"
							+ " from LDCode1"
							+ " where CodeType='AG' and Code='MQJJF' and Code1='"
							+ "?Code1?"
							+ "')"
							+ " union"
							+ " (select '2' as flag,CodeName"
							+ " from LDCode1"
							+ " where CodeType='AG' and Code='MQJJF' and Code1='000000')) g"
							+ " WHERE rownum=1" + " ORDER BY flag";
					}else if(SysConst.DBTYPE_MYSQL.equals(SysConst.DBTYPE_MYSQL)){
						tSql = "SELECT CodeName"
								+ " FROM ((select '1' as flag,CodeName"
								+ " from LDCode1"
								+ " where CodeType='AG' and Code='MQJJF' and Code1='"
								+ "?Code1?"
								+ "')"
								+ " union"
								+ " (select '2' as flag,CodeName"
								+ " from LDCode1"
								+ " where CodeType='AG' and Code='MQJJF' and Code1='000000')) g"
								+ " ORDER BY flag"+ " limit 0,1" ;	
					}
				sqlbv.sql(tSql);
				sqlbv.put("Code1", tLJSGetDrawSchema.getRiskCode());
		
			}

			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null) {
				CError tError = new CError();
				tError.moduleName = "PEdorAGDetailBL";
				tError.functionName = "getLJSGetEndorseSchemaBySet";
				tError.errorMessage = "未发现匹配的“补/退费财务类型”！";
				mErrors.addOneError(tError);
				return null;
			}
			rLJSGetEndorseSchema.setFeeFinaType(tSSRS.GetText(1, 1));
			rLJSGetEndorseSchema.setGrpContNo(tLJSGetDrawSchema.getGrpContNo());
			rLJSGetEndorseSchema.setContNo(this.mLPEdorItemSchema.getContNo());
			rLJSGetEndorseSchema.setGrpPolNo(tLJSGetDrawSchema.getGrpPolNo());
			rLJSGetEndorseSchema.setPolNo(this.mLPEdorItemSchema.getPolNo());
			rLJSGetEndorseSchema.setOtherNo(tLJSGetDrawSchema.getGetNoticeNo());
			rLJSGetEndorseSchema.setOtherNoType("5");
			rLJSGetEndorseSchema.setDutyCode(tLJSGetDrawSchema.getDutyCode());
			rLJSGetEndorseSchema.setPayPlanCode("000000");
			rLJSGetEndorseSchema.setAppntNo(tLJSGetDrawSchema.getAppntNo());
			rLJSGetEndorseSchema.setInsuredNo(tLJSGetDrawSchema.getInsuredNo());
			rLJSGetEndorseSchema.setGetDate(strCurrentDate);
			// 计算“补/退费金额”
			double sumMoney = 0;
			for (int i = 1; i <= aLJSGetDrawSet.size(); i++) {
				sumMoney = sumMoney + aLJSGetDrawSet.get(i).getGetMoney();
			}
			rLJSGetEndorseSchema.setGetMoney(-sumMoney); // 付钱，所以为负数
			rLJSGetEndorseSchema.setKindCode(tLJSGetDrawSchema.getKindCode());
			rLJSGetEndorseSchema.setRiskCode(tLJSGetDrawSchema.getRiskCode());
			rLJSGetEndorseSchema.setRiskVersion(tLJSGetDrawSchema
					.getRiskVersion());
			// 获得保单管理机构
			tSql = "SELECT ManageCom FROM LCCont WHERE ContNo='"
					+ "?ContNo?" + "'";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("ContNo", this.mLPEdorItemSchema.getContNo());
			SSRS tempSSRS = new SSRS();
			tempSSRS = tExeSQL.execSQL(sqlbv);
			if (tempSSRS != null && tempSSRS.MaxRow > 0) {
				rLJSGetEndorseSchema.setManageCom(tempSSRS.GetText(1, 1));
			}
			rLJSGetEndorseSchema.setAgentCom(tLJSGetDrawSchema.getAgentCom());
			rLJSGetEndorseSchema.setAgentType(tLJSGetDrawSchema.getAgentType());
			rLJSGetEndorseSchema.setAgentCode(tLJSGetDrawSchema.getAgentCode());
			rLJSGetEndorseSchema.setAgentGroup(tLJSGetDrawSchema
					.getAgentGroup());
			rLJSGetEndorseSchema.setGrpName(tLJSGetDrawSchema.getGrpName());
			rLJSGetEndorseSchema.setHandler(tLJSGetDrawSchema.getHandler());
			rLJSGetEndorseSchema.setPolType(tLJSGetDrawSchema.getPolType());
			rLJSGetEndorseSchema.setApproveCode("");
			rLJSGetEndorseSchema.setApproveDate("");
			rLJSGetEndorseSchema.setApproveTime("");
			rLJSGetEndorseSchema.setGetFlag("");
			rLJSGetEndorseSchema.setSerialNo("");
			rLJSGetEndorseSchema.setOperator(this.mGlobalInput.Operator);
			rLJSGetEndorseSchema.setMakeDate(strCurrentDate);
			rLJSGetEndorseSchema.setMakeTime(strCurrentTime);
			rLJSGetEndorseSchema.setModifyDate(strCurrentDate);
			rLJSGetEndorseSchema.setModifyTime(strCurrentTime);
			rLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Get_GetDraw
					+ String.valueOf(aSerial)); // 保证主键不重复
			return rLJSGetEndorseSchema;
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "PEdorAGDetailBL";
			tError.functionName = "getLJSGetEndorseSchemaBySet";
			tError.errorMessage = e.toString();
			mErrors.addOneError(tError);
			return null;
		}
	}

	// 通过“GetDutyCode”查询“给付分类1”
	private String getGetTypeByGetDutyCode(String gGetDutyCode) {
		try {
			if (gGetDutyCode == null) {
				logger.debug("通过“GetDutyCode”查询“给付分类1”时传入的“GetDutyCode”为null！");
				CError tError = new CError();
				tError.moduleName = "PEdorAGDetailBL";
				tError.functionName = "getGetTypeByGetDutyCode";
				tError.errorMessage = "通过“GetDutyCode”查询“给付分类1”时传入的“GetDutyCode”为null！";
				this.mErrors.addOneError(tError);
				return null;
			}
			String tSql = "SELECT GetType1 FROM LMDutyGet WHERE GetDutyCode='"
					+ "?GetDutyCode?" + "'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("GetDutyCode", gGetDutyCode.trim());
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS == null || tSSRS.GetText(1, 1) == null) {
				logger.debug("通过“GetDutyCode”查询“给付分类1”时未找到记录！");
				CError tError = new CError();
				tError.moduleName = "PEdorAGDetailBL";
				tError.functionName = "getGetTypeByGetDutyCode";
				tError.errorMessage = "通过“GetDutyCode”查询“给付分类1”时未找到记录！";
				this.mErrors.addOneError(tError);
				return null;
			}
			return tSSRS.GetText(1, 1);
		} catch (Exception e) {
			logger.debug("通过“GetDutyCode”查询“给付分类1”时未找到记录！");
			CError tError = new CError();
			tError.moduleName = "PEdorAGDetailBL";
			tError.functionName = "getGetTypeByGetDutyCode";
			tError.errorMessage = e.toString();
			this.mErrors.addOneError(tError);
			return null;
		}
	}
}

/*
 * @(#)AddEdorItemCheckBL.java	2005-05-25
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LMRiskEdorItemDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LMRiskEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全-添加保全项目校验处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：zhangtao
 * @version：1.0
 * @CreateDate：2005-05-25
 */
public class AddEdorItemCheckBL {
private static Logger logger = Logger.getLogger(AddEdorItemCheckBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();
	/** 用户登陆信息 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 传输数据 */
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
	LPEdorItemSchema mLPEdorItemSchema=new LPEdorItemSchema();
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
	private TransferData mTransferData = new TransferData();
	private List mBomList = new ArrayList();
	private Boolean mBomListFlag=false;
	private PrepareBOMBQEdorBL mPrepareBOMBQEdorBL = new PrepareBOMBQEdorBL();
	private String mCurrentDate = PubFun.getCurrentDate();

	public AddEdorItemCheckBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作符
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		logger.debug("after getInputData...");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("after dealData...");

		// 准备要返回的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("after prepareOutputData...");

		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {
		// 校验保全申请当前处理状态
		if (!checkEdorState()) {
			return false;
		}

		for (int i = 1; i <= mLPEdorItemSet.size(); i++) {
			
			mLPEdorItemSchema=mLPEdorItemSet.get(i);
			// 校验当前用户是否有该保全项目的申请权限
			String sLoadFlag = (String) mTransferData
					.getValueByName("LoadFlag");
			if (sLoadFlag == null || !sLoadFlag.equals("edorTest")) {
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("EdorType", mLPEdorItemSet.get(i)
						.getEdorType());
				tTransferData.setNameAndValue("EdorAcceptNo", mLPEdorItemSet
						.get(i).getEdorAcceptNo());
				tTransferData.setNameAndValue("EdorAppDate", mLPEdorItemSet
						.get(i).getEdorAppDate());
				tTransferData.setNameAndValue("EdorValiDate", mLPEdorItemSet
						.get(i).getEdorValiDate());
				VData tVData = new VData();
				tVData.add(mGlobalInput);
				tVData.add(tTransferData);
				PEdorPopedomCheckBL tPEdorPopedomCheckBL = new PEdorPopedomCheckBL();
				if (!tPEdorPopedomCheckBL.submitData(tVData, "Apply")) {
					mErrors.copyAllErrors(tPEdorPopedomCheckBL.mErrors);
					return false;
				}
			}

			// 日期校验
			int intv = PubFun.calInterval(mCurrentDate, mLPEdorItemSet.get(i)
					.getEdorAppDate(), "D");
			if (intv > 0) {
				CError.buildErr(this, "保全申请日期不能晚于系统当前日期!");
				return false;
			}
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mLPEdorItemSet.get(i).getContNo());
			// tLCContDB.setAppFlag("1");
			LCContSet tLCContSet = tLCContDB.query();
			if (tLCContDB.mErrors.needDealError()) {
				CError.buildErr(this, "保单查询失败!");
				return false;
			}
			if (tLCContSet == null || tLCContSet.size() != 1) {
				CError.buildErr(this, "该保单号不存在! 保单号："
						+ mLPEdorItemSet.get(i).getContNo());
				return false;
			}
			if (mLPEdorAppSchema.getAppType() != null
					&& (mLPEdorAppSchema.getAppType().trim().equals("6") || mLPEdorAppSchema
							.getAppType().trim().equals("7"))) {
				// 部门转办可以不用校验客户签收日期
			} else {
				if (tLCContSet.get(1).getCustomGetPolDate() == null
						|| tLCContSet.get(1).getCustomGetPolDate().equals("")) {
					CError.buildErr(this, "该保单客户尚未签收! 保单号："
							+ mLPEdorItemSet.get(i).getContNo());
					return false;
				}

				intv = PubFun.calInterval(tLCContSet.get(1)
						.getCustomGetPolDate(), mLPEdorItemSet.get(i)
						.getEdorAppDate(), "D");
				if (intv < 0) {
					CError.buildErr(this, "保全申请日期不能早于客户签收日期! " + "保单号："
							+ mLPEdorItemSet.get(i).getContNo());
					return false;
				}
			}

			if (!tLCContSet.get(1).getAppFlag().equals("1")) {
				//
			}

			// 校验保全项目不能重复
			if (!checkItemRepeat(mLPEdorItemSet.get(i))) {
				return false;
			}

			// 校验是否可以添加该保全项目
			if (!canAddEdorItem(mLPEdorItemSet.get(i))) {
				return false;
			}

			// 执行各保全项目校验规则
			if (!exeCheckCode(mLPEdorItemSet.get(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 校验保全项目基本信息
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean checkEdorState() {
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLPEdorItemSet.get(1).getEdorAcceptNo());
		if (!tLPEdorAppDB.getInfo()) {
			// @@错误处理
			mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全受理信息查询失败!";
			mErrors.addOneError(tError);
			return false;
		}
		mLPEdorAppSchema = tLPEdorAppDB.getSchema();
		String sEdorState = mLPEdorAppSchema.getEdorState();

		if (sEdorState.equals("1") || sEdorState.equals("3")
				|| sEdorState.equals("5")) {
			// 1-录入完成 3-等待录入 5-复核修改
			return true;
		} else {
			// 给出错误提示
			if (sEdorState.equals("2")) {
				CError tError = new CError();
				tError.errorMessage = "保全受理已申请确认，不能添加保全项目!";
				mErrors.addOneError(tError);
				return false;
			}
			if (sEdorState.equals("4")) {
				CError tError = new CError();
				tError.errorMessage = "保全受理已逾期终止，不能添加保全项目!";
				mErrors.addOneError(tError);
				return false;
			}
			if (sEdorState.equals("0")) {
				CError tError = new CError();
				tError.errorMessage = "保全受理已保全确认，不能添加保全项目!";
				mErrors.addOneError(tError);
				return false;
			}
			CError tError = new CError();
			tError.errorMessage = "保全受理在目前状态下不能添加保全项目!";
			mErrors.addOneError(tError);
			return false;
		}
	}

	/**
	 * 校验是否可以添加该保全项目
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean canAddEdorItem(LPEdorItemSchema tLPEdorItemSchema) {
		// *******************************************************
		// 保单级-该保单下所有险种必须都可以添加该保全项目
		// 客户级-该保单该被保人下所有险种必须都可以添加该保全项目
		// 险种级-该险种必须可以添加该保全项目
		// *******************************************************

		String sRiskCode = "000000"; // 险种代码
		String sEdorCode = tLPEdorItemSchema.getEdorType(); // 保全项目编码
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		if (checkRiskEdorItem(sRiskCode, sEdorCode)) {
			// 该保全项目对所有险种都可以添加
			mErrors.clearErrors();
			return true;
		}

		StringBuffer sbSQL = new StringBuffer();
		String sDisplayType = tLPEdorItemSchema.getDisplayType(); // 显示级别
		if (sDisplayType.equals("1")) // 保单级
		{
			// 查询该保单下所有险种
			sbSQL.append("select distinct riskcode from lcpol ").append(
					" where contno = '").append("?contno?")
					.append("' ");
			sqlbv.put("contno", tLPEdorItemSchema.getContNo());
		}

		if (sDisplayType.equals("2")) // 客户级
		{
			// 查询该保单该被保人或者投保人下所有险种
			sbSQL.append("select distinct riskcode from lcpol ").append(
					"  where contno = '").append("?contno?")
					.append("' and ((insuredno = '").append("?insuredno?").append(
							"') or ( polno in ").append(" (select polno from LCAppnt  ").append(" where Appntno = '").
							append("?Appntno?").append("') ) or ( polno in ").
							append(" (select polno from LCInsuredRelated  ").append(" where customerno = '").
							append("?customerno?").append("') )) ");
			sqlbv.put("contno", tLPEdorItemSchema.getContNo());
			sqlbv.put("insuredno", tLPEdorItemSchema.getInsuredNo());
			sqlbv.put("Appntno", tLPEdorItemSchema.getInsuredNo());
			sqlbv.put("customerno", tLPEdorItemSchema.getInsuredNo());
			
		}

		if (sDisplayType.equals("3")) // 险种级
		{
			sbSQL.append("select distinct riskcode from lcpol ").append(
					" where polno = '").append("?polno?")
					.append("' ");
			sqlbv.put("polno", tLPEdorItemSchema.getPolNo());
		}

		logger.debug(sbSQL.toString());

		sqlbv.sql(sbSQL.toString());
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保单险种查询失败!");
			return false;
		}
		if (tSSRS == null) {
			CError.buildErr(this, "保单险种查询失败!");
			return false;
		}
		boolean canAdd = false;
		for (int j = 1; j <= tSSRS.getMaxRow(); j++) {

			sRiskCode = tSSRS.GetText(j, 1);
			if (!checkRiskEdorItem(sRiskCode, sEdorCode)) {
				if (sDisplayType.equals("3")) // 险种级
				{
					return false;
				}
			} else {
				canAdd = true; // 合同级与被保人级的只要有一个险种可以添加就允许添加
			}
		}

		mErrors.clearErrors();

		if (!canAdd) {
			String sErrorMessage = "";
			if (sDisplayType.equals("1")) // 保单级
			{
				sErrorMessage = "该保单下所有险种都不能添加该保全项目" + "保单号："
						+ tLPEdorItemSchema.getContNo() + "保全项目代码：" + sEdorCode;
			}
			if (sDisplayType.equals("2")) // 客户级
			{
				sErrorMessage = "该保单下该被保人相关所有险种都不能添加该保全项目" + "保单号："
						+ tLPEdorItemSchema.getContNo() + "被保人客户号："
						+ tLPEdorItemSchema.getInsuredNo() + "保全项目代码："
						+ sEdorCode;
			}
			CError.buildErr(this, sErrorMessage);
			return false;
		}

		return true;
	}

	/**
	 * 校验险种是否可以添加保全项目
	 * 
	 * @param sRiskCode
	 * @param sEdorType
	 * @return: boolean
	 */
	private boolean checkRiskEdorItem(String sRiskCode, String sEdorType) {
		LMRiskEdorItemDB tLMRiskEdorItemDB = new LMRiskEdorItemDB();
		tLMRiskEdorItemDB.setRiskCode(sRiskCode);
		tLMRiskEdorItemDB.setEdorCode(sEdorType);
		LMRiskEdorItemSet tLMRiskEdorItemSet = tLMRiskEdorItemDB.query();
		if (tLMRiskEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "险种保全项目定义查询失败!");
			return false;
		}
		if (tLMRiskEdorItemSet == null || tLMRiskEdorItemSet.size() < 1) {
			if (!sRiskCode.equals("000000")) {
				CError.buildErr(this, "该险种不能添加该保全项目!" + "险种代码：" + sRiskCode
						+ "保全项目代码：" + sEdorType);
			}
			return false;
		}

		return true;
	}

	/**
	 * 校验保全项目基本信息
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean checkItemRepeat(LPEdorItemSchema tLPEdorItemSchema) {
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		// ===add===zhangtao====2006-08-24====鐢熶骇搴撳彂鐜版棤鏉′欢鏌ヨ瀵艰嚧鍐呭瓨婧㈠嚭锛屾晠鍦ㄦ鍏堟牎楠屾煡璇㈡潯浠�====BGN=======
		if ((tLPEdorItemSchema.getEdorAcceptNo() == null || tLPEdorItemSchema
				.getEdorAcceptNo().equals(""))
				&& (tLPEdorItemSchema.getEdorNo() == null || tLPEdorItemSchema
						.getEdorNo().equals(""))) {
			CError.buildErr(this, "保全项目查询条件为空!");
			return false;
		}
		// ===add===zhangtao====2006-08-24====生产库发现无条件查询导致内存溢出，故在此先校验查询条件=====END=======
		// 插入前校验保全项目不能重复
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(tLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorType(tLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setContNo(tLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(tLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setPolNo(tLPEdorItemSchema.getPolNo());
		int iCont = tLPEdorItemDB.getCount();

		if (tLPEdorItemDB.mErrors.needDealError()) {
			// @@错误处理
			mErrors.copyAllErrors(tLPEdorItemDB.mErrors);
			mErrors.addOneError(new CError("保全项目查询失败!"));
			return false;
		}
		if (iCont > 0) {
			CError tError = new CError();
			tError.errorMessage = "该保全项目已经添加! " + "批改类型："
					+ tLPEdorItemSchema.getEdorType() + ",客户号："
					+ tLPEdorItemSchema.getInsuredNo() + ",险种号："
					+ tLPEdorItemSchema.getPolNo();
			mErrors.addOneError(tError);
			return false;
		}

		// 校验一个保全申请下只能添加一个保全项目
		String sql = " select * from lpedoritem where edoracceptno = '"
				+ "?edoracceptno?" + "' and edortype <> '"
				+ "?edortype?"+ "'";
		sqlbv.sql(sql);
		sqlbv.put("edoracceptno", tLPEdorItemSchema.getEdorAcceptNo());
		sqlbv.put("edortype", tLPEdorItemSchema.getEdorType());
		
		tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv);
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError tError = new CError();
			tError.errorMessage = "保全项目查询失败";
			mErrors.addOneError(tError);
			return false;
		}
		if (tLPEdorItemSet != null && tLPEdorItemSet.size() > 0) {
			CError tError = new CError();
			tError.errorMessage = "同一申请下只能添加一个保全项目";
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 执行保全项目校验规则
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	public boolean exeCheckCode(LPEdorItemSchema tLPEdorItemSchema) {
		String sDisplayType = tLPEdorItemSchema.getDisplayType();
		StringBuffer sbSQL = new StringBuffer();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		
		if (sDisplayType.equals("1")) // 淇濆崟绾�
		{
			// 查询该保单下所有险种的校验规则[包含共通的校验规则，险种编码为"000000"]
			sbSQL
					.append(
							"select distinct calcode, msg, serialno from lmcheckfield ")
					.append(" where trim(riskcode) in ")
					.append(
							"  (select distinct trim(riskcode) from lcpol where contno = '")
					.append("?contno?").append(
							"' union select '000000' from dual ) ").append(
							" and fieldname = '").append(
							"?fieldname?").append("' order by char_length(trim(serialno)), serialno ");

			sqlbv.put("contno", tLPEdorItemSchema.getContNo());
			sqlbv.put("fieldname", tLPEdorItemSchema.getEdorType()+"Insert");
			
		}
		if (sDisplayType.equals("2")) // 客户级
		{
			// 查询该被保人所有险种的校验规则[包含共通的校验规则，险种编码为"000000"]
			sbSQL
					.append(
							"select distinct calcode, msg, serialno from lmcheckfield ")
					.append(" where trim(riskcode) in ")
					.append(
							"  (select distinct trim(riskcode) from lcpol where contno = '")
					.append("?contno?").append(
							"' and insuredno = '").append(
							"?insuredno?").append(
							"' union select '000000' from dual ) ").append(
							" and fieldname = '").append(
							"?fieldname?").append("' order by char_length(trim(serialno)), serialno ");
			
			sqlbv.put("contno", tLPEdorItemSchema.getContNo());
			sqlbv.put("insuredno", tLPEdorItemSchema.getInsuredNo());
			sqlbv.put("fieldname", tLPEdorItemSchema.getEdorType()+"Insert");
			
		}
		if (sDisplayType.equals("3")) // 险种级
		{
			// 查询该险种的校验规则[包含共通的校验规则，险种编码为"000000"]
			sbSQL
					.append(
							"select distinct calcode, msg, serialno from lmcheckfield ")
					.append(" where trim(riskcode) in ")
					.append(
							"  (select distinct trim(riskcode) from lcpol where polno = '")
					.append("?polno?").append(
							"' union select '000000' from dual ) ").append(
							" and fieldname = '").append(
							"?fieldname?").append("' order by char_length(trim(serialno)), serialno ");
			
			sqlbv.put("polno", tLPEdorItemSchema.getPolNo());
			sqlbv.put("fieldname", tLPEdorItemSchema.getEdorType()+"Insert");
		
		}

		sqlbv.sql(sbSQL.toString());
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
		if (tSSRS == null) {
			CError tError = new CError();
			tError.errorMessage = "保全项目添加校验规则查询失败!";
			mErrors.addOneError(tError);
			return false;
		}

		Calculator tCalculator = new Calculator();

		String sql = " select riskcode from lcpol where " + " polno = '"
				+ "?polno?" + "' ";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(sql);
		sbv.put("polno", tLPEdorItemSchema.getPolNo());
		tExeSQL = new ExeSQL();
		String sRiskCode = tExeSQL.getOneValue(sbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "险种信息查询失败!");
			return false;
		}
		if (sRiskCode == null) {
			sRiskCode = "";
		}

		// 增加基本要素 考虑所有校验规则需要的所有计算要素
		BqCalBase mBqCalBase=new BqCalBase();
		mBqCalBase.setEdorType(tLPEdorItemSchema.getEdorType());
		mBqCalBase.setPolNo(tLPEdorItemSchema.getPolNo());
		mBqCalBase.setContNo(tLPEdorItemSchema.getContNo());
		mBqCalBase.setEdorNo(tLPEdorItemSchema.getEdorNo());
		mBqCalBase.setEdorAcceptNo(tLPEdorItemSchema.getEdorAcceptNo());
		mBqCalBase.setRiskCode(sRiskCode);
		mBqCalBase.setInsuredNo(tLPEdorItemSchema.getInsuredNo());
		mBqCalBase.setEdorAppDate(tLPEdorItemSchema.getEdorAppDate());
		mBqCalBase.setCURValidate(mCurrentDate);
		if (!prepareBOMList(mBqCalBase)) {
			CError.buildErr(this, "Prepare BOMLIST Failed...");
			return false;
		}
		tCalculator.setBOMList(this.mBomList);// 添加BOMList
		tCalculator.addBasicFactor("EdorType", tLPEdorItemSchema.getEdorType());
		tCalculator.addBasicFactor("PolNo", tLPEdorItemSchema.getPolNo());
		tCalculator.addBasicFactor("ContNo", tLPEdorItemSchema.getContNo());
		tCalculator.addBasicFactor("RiskCode", sRiskCode);
		tCalculator.addBasicFactor("InsuredNo", tLPEdorItemSchema
				.getInsuredNo());
		tCalculator.addBasicFactor("EdorAppDate", tLPEdorItemSchema
				.getEdorAppDate());
		tCalculator.addBasicFactor("CURValidate", mCurrentDate); // 系统当前日期
		// logger.debug("== 添加保全项目校验规则计算要素 == EdorType ===== " +
		// tLPEdorItemSchema.getEdorType());
		// logger.debug("== 添加保全项目校验规则计算要素 == ContNo ======= " +
		// tLPEdorItemSchema.getContNo());
		// logger.debug("== 添加保全项目校验规则计算要素 == PolNo ======== " +
		// tLPEdorItemSchema.getPolNo());
		// logger.debug("== 添加保全项目校验规则计算要素 == InsuredNo ==== " +
		// tLPEdorItemSchema.getInsuredNo());
		// logger.debug("== 添加保全项目校验规则计算要素 == RiskCode ===== " +
		// sRiskCode);
		// logger.debug("== 添加保全项目校验规则计算要素 == EdorAppDate == " +
		// tLPEdorItemSchema.getEdorAppDate());

		String sResult = ""; // 计算结果
		String sCalCode = ""; // 计算代码
		String sErroMsg = ""; // 规则信息
		for (int j = 1; j <= tSSRS.getMaxRow(); j++) {
			sCalCode = tSSRS.GetText(j, 1);
			sErroMsg = tSSRS.GetText(j, 2);

			// 循环执行校验规则
			tCalculator.setCalCode(sCalCode);
			sResult = tCalculator.calculate();
			if (tCalculator.mErrors.needDealError()) {
				mErrors.copyAllErrors(tCalculator.mErrors);
				mErrors.addOneError(new CError("执行保全项目添加校验规则失败!"));
				return false;
			}
			if (sResult == null || !sResult.equals("1")) {
				CError tError = new CError();
				tError.errorMessage = sErroMsg;
				// "不符合保全项目添加校验规则，不能添加该保全项目! " +
				// " 批改类型：" + tLPEdorItemSchema.getEdorType() +
				// ", 客户号：" + tLPEdorItemSchema.getInsuredNo() +
				// ", 险种号：" + tLPEdorItemSchema.getPolNo() +
				// " 校验规则：" + sErroMsg;
				mErrors.addOneError(tError);
				return false;
			}

		}

		return true;
	}
	/**
	 * 为全局变量mBomList赋值，如果已经赋值过，则不再赋值
	 * 
	 * @return
	 */
	private boolean prepareBOMList(BqCalBase aBqCalBase) {
		try {
			if (!this.mBomListFlag) {
				this.mPrepareBOMBQEdorBL.setBqCalBase(aBqCalBase);
				this.mBomList = this.mPrepareBOMBQEdorBL.dealData(mLPEdorItemSchema);
				this.mBomListFlag = true;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			this.mBomListFlag = false;
			return false;
		}
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		mLPEdorItemSet = (LPEdorItemSet) mInputData.getObjectByObjectName(
				"LPEdorItemSet", 0);

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mLPEdorItemSet == null || mLPEdorItemSet.size() < 1) {
			CError.buildErr(this, "没有传入批改项目信息!");
			return false;
		}

		return true;
	}

	/**
	 * 准备提交后台的数据
	 * 
	 * @return: boolean
	 */
	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "zhangtao";
		tG.ManageCom = "86110000";

		LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
		tLPEdorAppSchema.setEdorAcceptNo("86000000004545");

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorAcceptNo("86000000004545");
		tLPEdorItemSchema.setEdorType("AA");
		tLPEdorItemSchema.setContNo("230110000003199");
		tLPEdorItemSchema.setPolNo("000000");
		tLPEdorItemSchema.setInsuredNo("000000");
		tLPEdorItemSchema.setDisplayType("1");
		tLPEdorItemSchema.setEdorAppDate("2005-08-10");
		LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
		mLPEdorItemSet.add(tLPEdorItemSchema);

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(mLPEdorItemSet);
		tVData.add(tLPEdorAppSchema);

		AddEdorItemCheckBL tAddEdorItemCheckBL = new AddEdorItemCheckBL();

		if (!tAddEdorItemCheckBL.submitData(tVData, "")) {
			logger.debug(tAddEdorItemCheckBL.mErrors.getError(0).errorMessage);
		}

	}

}

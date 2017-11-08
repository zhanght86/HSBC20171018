/*
 * @(#)PersonUnionBL.java	2005-04-18
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAccountDB;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCInsuredRelatedDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LBPersonSchema;
import com.sinosoft.lis.schema.LCAccountSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.vschema.LCAccountSet;
import com.sinosoft.lis.vschema.LCAddressSet;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LCInsuredRelatedSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 新契约复核-客户合并处理类
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
 * @CreateDate：2005-04-18
 */
public class CustomerUnionBL {
private static Logger logger = Logger.getLogger(CustomerUnionBL.class);
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
	private TransferData mTransferData = new TransferData();

	// 用来替换的客户号[new]
	private String mCustomerNo_NEW;
	// 需要被替换的客户号[old]
	private String mCustomerNo_OLD;

	// 客户基本信息
	private LDPersonSchema mLDPersonSchema_OLD = new LDPersonSchema();
	private LDPersonSchema mLDPersonSchema_NEW = new LDPersonSchema();
	private LCAddressSet mLCAddressSet_OLD = new LCAddressSet();
	private LCAccountSet mLCAccountSet_OLD = new LCAccountSet();
	private LCAccountSet mLCAccountSet_NEW = new LCAccountSet();

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public CustomerUnionBL() {
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
		logger.debug("----0");
		logger.debug("after getInputData...");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("after dealData...");

		// 准备提交后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("after prepareOutputData...");

		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;

		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {
		if (mOperate.equals("CUSTOMER|UNION")) {

			// //判断是否向客户发问题件并回收问题件
			// if (!hasIssued()) return false;

			// 查询被替换的客户信息
			logger.debug("----进入dealData()");
			if (!getCustomerInfo())
				return false;

			// 备份并删除被替换的客户信息
			if (!delOldCustomerInfo())
				return false;

			// 合并客户地址信息和银行帐户信息
			if (!updNewCustomerInfo())
				return false;

			StringBuffer sbSQL = new StringBuffer();
			ExeSQL exeSQL = new ExeSQL();

			// 查出所有与客户信息相关的表
			String strCUR_TABLE_NAME;
			String strCUR_COLUMN_NAME;
			sbSQL.append(" SELECT  TABLE_NAME, COLUMN_NAME ").append(
					"   FROM  COLS ").append("  WHERE  COLUMN_NAME IN ")
					.append(" ('INSUREDNO', 'CUSTOMERNO', 'APPNTNO' ) ")
					.append(" ORDER BY  TABLE_NAME, COLUMN_NAME DESC ");

			SSRS rSSRS = exeSQL.execSQL(sbSQL.toString());
			if (rSSRS != null) {
				for (int i = 1; i <= rSSRS.getMaxRow(); i++) {
					strCUR_TABLE_NAME = rSSRS.GetText(i, 1);
					strCUR_COLUMN_NAME = rSSRS.GetText(i, 2);

					if (strCUR_TABLE_NAME.equalsIgnoreCase("LBPERSON")
							|| strCUR_TABLE_NAME.equalsIgnoreCase("LCAddress")
							|| strCUR_TABLE_NAME.equalsIgnoreCase("LCAccount")) {
						continue; // [客户信息备份表]不需要更新
					}

					sbSQL.setLength(0);
					sbSQL.append(" UPDATE " + strCUR_TABLE_NAME).append(
							" SET " + strCUR_COLUMN_NAME).append(
							" = '" + mCustomerNo_NEW + "' ").append(
							" WHERE " + strCUR_COLUMN_NAME).append(
							" = '" + mCustomerNo_OLD + "' ");

					// 添加更新日期、时间、操作员信息 ？？ 有无必要 ？？
					// 暂时取消[2005-04-19]
					map.put(sbSQL.toString(), "UPDATE");
				}
			}
		}

		return true;
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		mCustomerNo_NEW = (String) mTransferData
				.getValueByName("CustomerNo_NEW");
		if (mCustomerNo_NEW == null || "".equals(mCustomerNo_NEW)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据[CustomerNo_NEW] 失败!";
			mErrors.addOneError(tError);
			return false;
		}
		logger.debug("== mCustomerNo_NEW ==" + mCustomerNo_NEW);

		mCustomerNo_OLD = (String) mTransferData
				.getValueByName("CustomerNo_OLD");
		if (mCustomerNo_OLD == null || "".equals(mCustomerNo_OLD)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据[CustomerNo_OLD]失败!";
			mErrors.addOneError(tError);
			return false;
		}
		logger.debug("== mCustomerNo_OLD ==" + mCustomerNo_OLD);

		if (mCustomerNo_NEW.equals(mCustomerNo_OLD)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "客户号相同，不能执行合并!";
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 查询被替换的客户信息
	 * 
	 * @return: boolean
	 */
	private boolean getCustomerInfo() {
		// 客户基本信息
		LDPersonDB tLDPersonDB = new LDPersonDB();
		tLDPersonDB.setCustomerNo(mCustomerNo_OLD);
		if (!tLDPersonDB.getInfo()) {
			mErrors.copyAllErrors(tLDPersonDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "getCustomerInfo";
			tError.errorMessage = "客户信息查询失败!" + "客户号:" + mCustomerNo_OLD;
			mErrors.addOneError(tError);
			return false;
		}
		mLDPersonSchema_OLD.setSchema(tLDPersonDB.getSchema());

		tLDPersonDB = new LDPersonDB();
		tLDPersonDB.setCustomerNo(mCustomerNo_NEW);
		if (!tLDPersonDB.getInfo()) {
			mErrors.copyAllErrors(tLDPersonDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "getCustomerInfo";
			tError.errorMessage = "客户信息查询失败!" + "客户号:" + mCustomerNo_NEW;
			mErrors.addOneError(tError);
			return false;
		}
		mLDPersonSchema_NEW.setSchema(tLDPersonDB.getSchema());

		// 客户地址信息
		LCAddressDB tLCAddressDB = new LCAddressDB();
		tLCAddressDB.setCustomerNo(mCustomerNo_OLD);
		mLCAddressSet_OLD.set(tLCAddressDB.query());
		if (tLCAddressDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLCAddressDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "getCustomerInfo";
			tError.errorMessage = "客户地址信息查询失败!" + "客户号:" + mCustomerNo_OLD;
			mErrors.addOneError(tError);
			return false;
		}

		// 客户银行帐户信息
		LCAccountDB tLCAccountDB = new LCAccountDB();
		tLCAccountDB.setCustomerNo(mCustomerNo_OLD);
		mLCAccountSet_OLD.set(tLCAccountDB.query());
		if (tLCAccountDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLCAccountDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "getCustomerInfo";
			tError.errorMessage = "客户银行帐户信息查询失败!" + "客户号:" + mCustomerNo_OLD;
			mErrors.addOneError(tError);
			return false;
		}

		tLCAccountDB = new LCAccountDB();
		tLCAccountDB.setCustomerNo(mCustomerNo_NEW);
		mLCAccountSet_NEW.set(tLCAccountDB.query());
		if (tLCAccountDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLCAccountDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "getCustomerInfo";
			tError.errorMessage = "客户银行帐户信息查询失败!" + "客户号:" + mCustomerNo_NEW;
			mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 备份删除被替换的客户信息
	 * 
	 * @return: boolean
	 */
	private boolean delOldCustomerInfo() {

		// 备份客户基本信息
		Reflections rf = new Reflections();
		LBPersonSchema tLBPersonSchema = new LBPersonSchema();
		rf.transFields(tLBPersonSchema, mLDPersonSchema_OLD);

		String noLimit = PubFun.getNoLimit(this.mGlobalInput.ManageCom);
		String sEdorNo = PubFun1.CreateMaxNo("EDORNO", noLimit);
		tLBPersonSchema.setEdorNo(sEdorNo);
		tLBPersonSchema.setOperator(mGlobalInput.Operator);
		tLBPersonSchema.setModifyDate(mCurrentDate);
		tLBPersonSchema.setModifyTime(mCurrentTime);

		map.put(tLBPersonSchema, "INSERT");

		// 删除客户基本信息
		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append(" DELETE ").append(" FROM  LDPERSON ").append(
				" WHERE  CUSTOMERNO = '" + mCustomerNo_OLD + "'");

		map.put(sbSQL.toString(), "DELETE");

		return true;
	}

	/**
	 * 合并客户地址信息和银行帐户信息
	 * 
	 * @return: boolean
	 */
	private boolean updNewCustomerInfo() {
		// 取得该客户最大地址号
		int iAdsNO = getMaxAddressNo_NEW();
		if (iAdsNO == -1)
			return false;
		int iOldAdsNo = 1;

		for (int i = 1; i <= mLCAddressSet_OLD.size(); i++) {
			// 更新客户号和地址号
			try {
				iOldAdsNo = mLCAddressSet_OLD.get(i).getAddressNo();
			} catch (Exception e) {
				CError tError = new CError();
				tError.moduleName = "CustomerUnionBL";
				tError.functionName = "updNewCustomerInfo";
				tError.errorMessage = "客户地址号码格式错误!" + "客户号：" + mCustomerNo_NEW
						+ "地址号码：" + mLCAddressSet_OLD.get(i).getAddressNo()
						+ "错误信息：" + e.toString();
				mErrors.addOneError(tError);
			}
			mLCAddressSet_OLD.get(i).setCustomerNo(mCustomerNo_NEW);
			mLCAddressSet_OLD.get(i).setAddressNo(
					String.valueOf(iOldAdsNo + iAdsNO));
			mLCAddressSet_OLD.get(i).setOperator(mGlobalInput.Operator);
			mLCAddressSet_OLD.get(i).setModifyDate(mCurrentDate);
			mLCAddressSet_OLD.get(i).setModifyTime(mCurrentTime);
		}

		map.put(mLCAddressSet_OLD, "UPDATE");

		// 更新投保人、被保人、连带被保人信息中引用的[客户地址号码]

		if (!updAddressNoRelaTable(iAdsNO))
			return false;

		// 合并客户银行帐户信息
		LCAccountSet nUpdLCAccountSet = new LCAccountSet();
		LCAccountSet nDelLCAccountSet = new LCAccountSet();
		LCAccountSchema nLCAccountSchema = new LCAccountSchema();

		if (mLCAccountSet_NEW != null && mLCAccountSet_NEW.size() > 0) {
			// 新客户已经有银行帐户信息
			for (int j = 1; j <= mLCAccountSet_OLD.size(); j++) {
				nLCAccountSchema.setSchema(mLCAccountSet_OLD.get(j));
				if (exsitAccount(nLCAccountSchema)) {
					// 删除重复帐户
					nDelLCAccountSet.add(nLCAccountSchema);
				} else {
					// 更新客户号
					nLCAccountSchema.setCustomerNo(mCustomerNo_NEW);
					nLCAccountSchema.setOperator(mGlobalInput.Operator);
					nLCAccountSchema.setModifyDate(mCurrentDate);
					nLCAccountSchema.setModifyTime(mCurrentTime);
					nUpdLCAccountSet.add(nLCAccountSchema);
				}
			}

			map.put(nDelLCAccountSet, "DELETE");
			map.put(nUpdLCAccountSet, "UPDATE");
		} else {
			// 新客户尚未创建银行帐户信息
			for (int j = 1; j <= mLCAccountSet_OLD.size(); j++) {
				nLCAccountSchema.setSchema(mLCAccountSet_OLD.get(j));
				// 更新客户号
				nLCAccountSchema.setCustomerNo(mCustomerNo_NEW);
				nLCAccountSchema.setOperator(mGlobalInput.Operator);
				nLCAccountSchema.setModifyDate(mCurrentDate);
				nLCAccountSchema.setModifyTime(mCurrentTime);
				nUpdLCAccountSet.add(nLCAccountSchema);
			}

			map.put(nUpdLCAccountSet, "UPDATE");
		}

		return true;
	}

	/**
	 * 取得该客户最大地址号
	 * 
	 * @return: int
	 */
	private int getMaxAddressNo_NEW() {

		int iAdsNO = 0;
		String strAdsNO = "";
		StringBuffer sbSQL = new StringBuffer();
		ExeSQL exeSQL = new ExeSQL();

		// 查询该客户最大地址号
		sbSQL.append(" SELECT  MAX(ADDRESSNO) ").append(" FROM  LCADDRESS ")
				.append(" WHERE  CUSTOMERNO = ' ").append(mCustomerNo_NEW)
				.append("' ").append(" AND  LENGTH(TRIM(ADDRESSNO)) = ")
				.append(" (SELECT  MAX(LENGTH(TRIM(ADDRESSNO))) ").append(
						" FROM  LCADDRESS ").append(" WHERE  CUSTOMERNO = ' ")
				.append(mCustomerNo_NEW).append("')");

		try {

			SSRS rSSRS = exeSQL.execSQL(sbSQL.toString());
			if (rSSRS != null) {
				strAdsNO = rSSRS.GetText(1, 1);

				if (strAdsNO == null || strAdsNO.equals("")
						|| strAdsNO.equals("null")) {
					iAdsNO = 0;
				} else {
					iAdsNO = Integer.parseInt(strAdsNO);

				}
			}
		} catch (Exception e) {
			CError tError = new CError();

			tError.moduleName = "CustomerUnionBL";

			tError.functionName = "getMaxAddressNo_NEW";
			tError.errorMessage = "客户最大地址号查询失败!" + "客户号:" + mCustomerNo_NEW
					+ "错误信息：" + e.toString();
			mErrors.addOneError(tError);
			return -1;
		}

		return iAdsNO;
	}

	/**
	 * 判断帐户是否重复
	 * 
	 * @return: boolean
	 */
	private boolean exsitAccount(LCAccountSchema nLCAccountSchema) {
		for (int i = 1; i < mLCAccountSet_OLD.size(); i++) {
			if (nLCAccountSchema.getBankCode().equals(
					mLCAccountSet_OLD.get(i).getBankCode())
					&& nLCAccountSchema.getBankAccNo().equals(
							mLCAccountSet_OLD.get(i).getBankAccNo())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 更新投保人、被保人、连带被保人信息中引用的[客户地址号码]
	 * 
	 * @return: boolean
	 */
	private boolean updAddressNoRelaTable(int iAdsNO) {
		int iOldAdsNo = 1;
		String strOldAdsNo;

		// 投保人
		LCAppntSet tLCAppntSet = new LCAppntSet();
		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setAppntNo(mCustomerNo_OLD);
		tLCAppntSet.set(tLCAppntDB.query());
		if (tLCAppntDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLCAppntDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "updAddressNoRelaTable";
			tError.errorMessage = "投保人信息查询失败!" + "客户号:" + mCustomerNo_OLD;
			mErrors.addOneError(tError);
			return false;
		}
		if (tLCAppntSet != null && tLCAppntSet.size() > 0) {
			for (int i = 1; i <= tLCAppntSet.size(); i++) {
				strOldAdsNo = tLCAppntSet.get(i).getAddressNo();
				if (strOldAdsNo == null || "".equals(strOldAdsNo)) {
					continue;
				}
				iOldAdsNo = Integer.parseInt(strOldAdsNo);
				tLCAppntSet.get(i).setAddressNo(
						String.valueOf(iOldAdsNo + iAdsNO));
				tLCAppntSet.get(i).setOperator(mGlobalInput.Operator);
				tLCAppntSet.get(i).setModifyDate(mCurrentDate);
				tLCAppntSet.get(i).setModifyTime(mCurrentTime);
			}
			map.put(tLCAppntSet, "UPDATE");
		}

		// 被保人
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setInsuredNo(mCustomerNo_OLD);
		tLCInsuredSet.set(tLCInsuredDB.query());
		if (tLCInsuredDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLCInsuredDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "updAddressNoRelaTable";
			tError.errorMessage = "被保人信息查询失败!" + "客户号:" + mCustomerNo_OLD;
			mErrors.addOneError(tError);
			return false;
		}
		if (tLCInsuredSet != null && tLCInsuredSet.size() > 0) {
			for (int i = 1; i <= tLCInsuredSet.size(); i++) {
				strOldAdsNo = tLCAppntSet.get(i).getAddressNo();
				if (strOldAdsNo == null || "".equals(strOldAdsNo)) {
					continue;
				}
				iOldAdsNo = Integer.parseInt(tLCInsuredSet.get(i)
						.getAddressNo());
				tLCInsuredSet.get(i).setAddressNo(
						String.valueOf(iOldAdsNo + iAdsNO));
				tLCInsuredSet.get(i).setOperator(mGlobalInput.Operator);
				tLCInsuredSet.get(i).setModifyDate(mCurrentDate);
				tLCInsuredSet.get(i).setModifyTime(mCurrentTime);
			}
			map.put(tLCInsuredSet, "UPDATE");
		}

		// 连带被保人
		LCInsuredRelatedSet tLCRelatedSet = new LCInsuredRelatedSet();
		LCInsuredRelatedDB tLCRelatedDB = new LCInsuredRelatedDB();
		tLCRelatedDB.setCustomerNo(mCustomerNo_OLD);
		tLCRelatedSet.set(tLCRelatedDB.query());
		if (tLCRelatedDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLCRelatedDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "updAddressNoRelaTable";
			tError.errorMessage = "连带被保人信息查询失败!" + "客户号:" + mCustomerNo_OLD;
			mErrors.addOneError(tError);
			return false;
		}
		if (tLCRelatedSet != null && tLCRelatedSet.size() > 0) {
			for (int i = 1; i <= tLCRelatedSet.size(); i++) {
				strOldAdsNo = tLCAppntSet.get(i).getAddressNo();
				if (strOldAdsNo == null || "".equals(strOldAdsNo)) {
					continue;
				}
				iOldAdsNo = Integer.parseInt(tLCRelatedSet.get(i)
						.getAddressNo());
				tLCRelatedSet.get(i).setAddressNo(
						String.valueOf(iOldAdsNo + iAdsNO));
				tLCRelatedSet.get(i).setOperator(mGlobalInput.Operator);
				tLCRelatedSet.get(i).setModifyDate(mCurrentDate);
				tLCRelatedSet.get(i).setModifyTime(mCurrentTime);
			}
			map.put(tLCRelatedSet, "UPDATE");
		}
		return true;
	}

	/**
	 * 判断是否向客户发问题件并回收问题件
	 * 
	 * @return: boolean
	 */
	private boolean hasIssued() {
		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
		LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
		tLCIssuePolDB.setIssueType("22"); // ！！ 待定 ！！
		tLCIssuePolDB.setStandbyFlag1(mCustomerNo_NEW);
		tLCIssuePolDB.setStandbyFlag2(mCustomerNo_OLD);
		tLCIssuePolSet.set(tLCIssuePolDB.query());
		if (tLCIssuePolDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLCIssuePolDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "hasLCIssuePol";
			tError.errorMessage = "问题件信息查询失败!";
			mErrors.addOneError(tError);
			return false;
		}

		if (tLCIssuePolSet == null || tLCIssuePolSet.size() == 0) {
			mErrors.copyAllErrors(tLCIssuePolDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "hasLCIssuePol";
			tError.errorMessage = "尚未征求客户意见，不能合并客户，请先发出问题件!";
			mErrors.addOneError(tError);
			return false;
		}
		String strState;
		for (int i = 1; i <= tLCIssuePolSet.size(); i++) {
			strState = tLCIssuePolSet.get(i).getState();
			if (strState == null || !"2".equals(strState)) {
				// 问题件尚未回收
				mErrors.copyAllErrors(tLCIssuePolDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "CustomerUnionBL";
				tError.functionName = "hasLCIssuePol";
				tError.errorMessage = "客户尚未确认，不能合并客户，请先回收问题件!";
				mErrors.addOneError(tError);
				return false;
			}
		}

		return true;
	}

	/**
	 * 准备提交后台的数据
	 * 
	 * @retun: boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "CustomerUnionBL";
			tError.functionName = "prepareOutputData";
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

}

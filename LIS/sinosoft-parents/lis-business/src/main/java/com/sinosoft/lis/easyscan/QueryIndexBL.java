package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.ES_DOC_MAINDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Liuqiang
 * @version 1.0
 */
public class QueryIndexBL {
private static Logger logger = Logger.getLogger(QueryIndexBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	private VData mInputData;
	private VData mResult = new VData();
	private GlobalInput tGI = new GlobalInput();
	private ES_DOC_MAINSet inputES_DOC_MAINSet;

	// private ES_DOC_PAGESSet tES_DOC_PAGESSet;
	private ES_DOC_MAINSchema tES_DOC_MAINSchema;
	private String mManageCom;
	private String mOperator;
	private String mScanNo;
	private String mDocCode;
	private String mBussType;
	private String mSubType;
	private String mStartDate;
	private String mEndDate;
	private double mDocID;

	public QueryIndexBL() {
	}

	public static void main(String[] args) {
		QueryIndexBL queryIndexBL1 = new QueryIndexBL();
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = true;

		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		// 进行业务处理
		if (!dealData(cOperate)) {
			tReturn = false;
		}

		mResult.clear();
		mResult = mInputData;

		mInputData = null;

		return tReturn;
	}

	public VData getResult() {
		return mResult;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData(String cOperate) {
		// 获得入参
		if (!getInputData()) {
			return false;
		}

		// 数据检查
		if (!checkInputData()) {
			return false;
		}

		if (!getReturnData()) {
			return false;
		}

		return true;
	}

	// 入参处理
	private boolean getInputData() {
		boolean tReturn = true;

		logger.debug("QueryIndexBL: Start get Input Data ...");

		// 获取入参
		inputES_DOC_MAINSet = (ES_DOC_MAINSet) mInputData.get(0);

		// tES_DOC_PAGESSet = (ES_DOC_PAGESSet)mInputData.get(1);
		tES_DOC_MAINSchema = inputES_DOC_MAINSet.get(1);
		mDocID = tES_DOC_MAINSchema.getDocID();
		mManageCom = tES_DOC_MAINSchema.getManageCom();
		mOperator = tES_DOC_MAINSchema.getScanOperator();
		mScanNo = tES_DOC_MAINSchema.getScanNo();
		mDocCode = tES_DOC_MAINSchema.getDocCode();
		mBussType = tES_DOC_MAINSchema.getBussType();
		mSubType = tES_DOC_MAINSchema.getSubType();
		mStartDate = tES_DOC_MAINSchema.getMakeDate();
		mEndDate = tES_DOC_MAINSchema.getModifyDate();

		return tReturn;
	}

	// 数据校验
	private boolean checkInputData() {
		return true;
	}

	private boolean getReturnData() {
		String strDocID = "";
		int i = 0;
		logger.debug("QueryIndexBL: start get Return Data  ...");

		try {
			// 获得影像服务器信息
			ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
			// Edited by wellhi 2005.12.12
			StringBuffer bufSQL = new StringBuffer("SELECT * FROM ES_DOC_MAIN");
			bufSQL.append(" WHERE ManageCom like ");
			bufSQL.append("concat(?mManageCom?,'%')");
			if (mDocID != 0) {
				bufSQL.append(" AND DocID = ");
				bufSQL.append("?mDocID?");
				bufSQL.append(" AND '1'='1'");
			}

			if (mDocCode != null && !mDocCode.equals("") && mDocID == 0) {
				bufSQL.append(" AND DocCode = '");
				bufSQL.append("?mDocCode?'");
			}

			if (mOperator != null && !mOperator.equals("")) {
				bufSQL.append(" AND ScanOperator = '");
				bufSQL.append("?mOperator?'");
			}
			if (mScanNo != null && !mScanNo.equals("")) {
				bufSQL.append(" AND ScanNo='");
				bufSQL.append("?mScanNo?'");
			}

			if (mBussType != null && !mBussType.equals("")) {
				bufSQL.append(" AND BussType='");
				bufSQL.append("?mBussType?'");
			}
			if (mSubType != null && !mSubType.equals("")) {
				bufSQL.append(" AND SubType='");
				bufSQL.append("?mSubType?'");
			}
			if (mStartDate != null && !mStartDate.equals("")) {
				if (mEndDate != null && !mEndDate.equals("")) {
					bufSQL.append(" AND MakeDate BETWEEN'");
					bufSQL.append("?mStartDate?");
					bufSQL.append("' AND '");
					bufSQL.append("?mEndDate?'");
				} else {
					bufSQL.append(" AND MakeDate='");
					bufSQL.append("?mStartDate?'");
				}
			} else {
				if (mEndDate != null && !mEndDate.equals("")) {
					bufSQL.append(" AND MakeDate='");
					bufSQL.append("?mEndDate?'");
				}
			}
			bufSQL.append(" ORDER BY DocCode");
			logger.debug(bufSQL.toString());
			
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(bufSQL.toString());
			sqlbv1.put("mManageCom", mManageCom);
			sqlbv1.put("mDocID", mDocID);
			sqlbv1.put("mDocCode", mDocCode);
			sqlbv1.put("mOperator",mOperator);
			sqlbv1.put("mScanNo",mScanNo);
			sqlbv1.put("mBussType",mBussType);
			sqlbv1.put("mSubType",mSubType);
			sqlbv1.put("mStartDate",mStartDate);
			sqlbv1.put("mEndDate",mEndDate);
			sqlbv1.put("mStartDate",mStartDate);
			sqlbv1.put("mEndDate",mEndDate);
			inputES_DOC_MAINSet = tES_DOC_MAINDB.executeQuery(sqlbv1, 1, 100);
			/*
			 * tES_DOC_MAINDB.setManageCom(tES_DOC_MAINSchema.getManageCom());
			 * //added by wellhi 2005.07.20 double dblDocID =
			 * tES_DOC_MAINSchema.getDocID(); if (dblDocID != 0) {
			 * tES_DOC_MAINDB.setDocID(dblDocID); } //added by wellhi 2005.07.21
			 * String strDocCode = tES_DOC_MAINSchema.getDocCode(); if
			 * (strDocCode != null && dblDocID == 0) {
			 * tES_DOC_MAINDB.setDocCode(strDocCode); } //added by wellhi
			 * 2005.07.21 String strOperator = tES_DOC_MAINSchema.getOperator();
			 * if (strOperator != null) {
			 * tES_DOC_MAINDB.setOperator(strOperator); }
			 */
			// some chages by tu,more conditons;
			// tES_DOC_MAINDB.setSchema(tES_DOC_MAINSchema);
			// logger.debug("ant"+tES_DOC_MAINSchema.getDocID());
			// inputES_DOC_MAINSet = tES_DOC_MAINDB.query(1, 100);
			int intSize = inputES_DOC_MAINSet.size();
			String[] strScanType = new String[intSize];
			if (intSize == 0) {
				if (tES_DOC_MAINDB.mErrors.needDealError()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "QueryIndexBL";
					tError.functionName = "getReturnData";
					tError.errorNo = "-1";
					tError.errorMessage = "查询数据库出现错误:"
							+ tES_DOC_MAINDB.mErrors.getFirstError();
					this.mErrors.addOneError(tError);

					return false;
				}
			} else {
				// 从中心查询出来的单证,扫描类型都设置为2
				// by wellhi 2005.10.28

				for (int a = 0; a < intSize; a++) {
					strScanType[a] = "2";
				}
			}

			// 设置返回值
			mInputData.setElementAt(inputES_DOC_MAINSet, 0);
			mInputData.setElementAt(strScanType, 7);
		} catch (Exception ex) {
			logger.debug("Exception in QueryIndexBL->getReturnData");
			logger.debug("Exception:" + ex.toString());

			// ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "QueryIndexBL";
			tError.functionName = "getReturnData";
			tError.errorNo = "-99";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}
}

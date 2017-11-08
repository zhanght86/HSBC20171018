package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.Es_IssueDocDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 查询中心问题卷
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author wellhi
 * @version 1.0
 */

public class QueryIssueDocBL {
private static Logger logger = Logger.getLogger(QueryIssueDocBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	private VData mInputData;
	private VData mResult = new VData();
	private ES_DOC_MAINSet inputES_DOC_MAINSet;
	private ES_DOC_MAINSchema inputES_DOC_MAINSchema;
	private ES_DOC_MAINSet retES_DOC_MAINSet = new ES_DOC_MAINSet(); // 返回结果集
	private ES_DOC_PAGESSet retES_DOC_PAGESET = new ES_DOC_PAGESSet();
	private GlobalInput mGlobalInput;

	public QueryIssueDocBL() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = true;

		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		mResult.clear();
		mResult.setSize(10);
		// 进行业务处理
		if (!dealData(cOperate)) {
			tReturn = false;
		}

		// mInputData = null;

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
		mGlobalInput = (GlobalInput) mInputData.get(5);
		inputES_DOC_MAINSet = (ES_DOC_MAINSet) mInputData.get(0);
		inputES_DOC_MAINSchema = inputES_DOC_MAINSet.get(1);
		return true;
	}

	// 数据校验
	private boolean checkInputData() {
		return true;
	}

	private boolean getReturnData() {
		logger.debug("QueryIssueDocBL: start get Return Data  ...");

		try {
			Es_IssueDocDB nEs_IssueDocDB = new Es_IssueDocDB();
			// 查询问题卷表
			StringBuffer bufSQL = new StringBuffer("from es_doc_relation r,");
			bufSQL.append("es_issuedoc i,es_doc_main m where r.bussno=i.bussno");
			bufSQL.append(" and i.status='0' and i.subtype=r.subtype and");
			bufSQL.append(" r.docid=m.docid and m.managecom like ");
			// Edited by wellhi 2005.12.30
			// 管理机构采用查询时输入的管理机构,而不是登陆的管理机构
			// bufSQL.append(mGlobalInput.ManageCom.trim());
			String strManageCom = inputES_DOC_MAINSchema.getManageCom();
			bufSQL.append("concat(?managecom?,'%')");

			// added by wellhi 2005.11.24
			// 批次号
			String strScanNo = inputES_DOC_MAINSchema.getScanNo();
			if (strScanNo != null && !strScanNo.equals("")) {
				bufSQL.append(" AND m.ScanNo=");
				bufSQL.append("?strScanNo?");
			}

			// 业务号码
			String strBussNo = inputES_DOC_MAINSchema.getDocCode();
			if (strBussNo != null && !strBussNo.equals("")) {
				bufSQL.append(" AND i.BussNo=");
				bufSQL.append("?strBussNo?");
			}

			// 业务类型
			String strBussType = inputES_DOC_MAINSchema.getBussType();
			if (strBussType != null && !strBussType.equals("")) {
				bufSQL.append(" AND i.BussType=");
				bufSQL.append("?strBussType?");
			}

			// 单证细类
			String strSubType = inputES_DOC_MAINSchema.getSubType();
			if (strSubType != null && !strSubType.equals("")) {
				bufSQL.append(" AND i.SubType=");
				bufSQL.append("?strSubType?");
			}

			// 问题卷发起操作员
			String strOperator = inputES_DOC_MAINSchema.getScanOperator();
			if (strOperator != null && !strOperator.equals("")) {
				bufSQL.append(" AND i.PromptOperator=");
				bufSQL.append("?strOperator?");
			}

			// 问题卷发起时间
			// String strMakeDate = inputES_DOC_MAINSchema.getMakeDate();
			// if (strMakeDate != null && strMakeDate != "") {
			// bufSQL.append("' AND i.MakeDate='");
			// bufSQL.append(strMakeDate);
			// }
			String strStartDate = inputES_DOC_MAINSchema.getMakeDate();
			String strEndDate = inputES_DOC_MAINSchema.getModifyDate();
			if (strStartDate != null && !strStartDate.equals("")) {
				if (strEndDate != null && !strEndDate.equals("")) {
					bufSQL.append(" AND i.MakeDate BETWEEN");
					bufSQL.append("?strStartDate?");
					bufSQL.append(" AND ");
					bufSQL.append("?strEndDate?");
				} else {
					bufSQL.append(" AND i.MakeDate=");
					bufSQL.append("?strStartDate?");
				}
			} else {
				if (strEndDate != null && !strEndDate.equals("")) {
					bufSQL.append(" AND i.MakeDate=");
					bufSQL.append("?strEndDate?");
				}
			}
			bufSQL.append(" order by i.issuedocid");
			// ***************************************
			ExeSQL nExeSQL = new ExeSQL();
			String mSQL = "SELECT i.Issuedocid,r.DocID,r.BussNo,i.Subtype,I.IssueDesc,I.PromptOperator,I.MakeDate,m.scanno "
					+ bufSQL.toString();

			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(mSQL);
			sqlbv1.put("managecom", strManageCom.trim());
			sqlbv1.put("strScanNo", strScanNo);
			sqlbv1.put("strBussNo", strBussNo);
			sqlbv1.put("strBussType", strBussType);
			sqlbv1.put("strSubType", strSubType);
			sqlbv1.put("strOperator", strOperator);
			sqlbv1.put("strStartDate", strStartDate);
			sqlbv1.put("strEndDate", strEndDate);
			SSRS nSSRS = nExeSQL.execSQL(sqlbv1);
			String[] strScanType;
			if (nSSRS.getMaxRow() > 0) {
				strScanType = new String[nSSRS.getMaxRow()];
				for (int i = 1; i <= nSSRS.getMaxRow(); i++) {
					ES_DOC_MAINSchema nES_DOC_MAINSchema = new ES_DOC_MAINSchema();
					// 以下字段均是借用ES_DOC_MAINSchema的字段
					// 问题卷ID
					nES_DOC_MAINSchema.setPrintCode(nSSRS.GetText(i, 1));
					// 单证DocID
					nES_DOC_MAINSchema.setDocID(nSSRS.GetText(i, 2));
					// 单证业务关联号码
					nES_DOC_MAINSchema.setDocCode(nSSRS.GetText(i, 3));
					// 单证细类
					nES_DOC_MAINSchema.setSubType(nSSRS.GetText(i, 4));
					// 问题描述
					nES_DOC_MAINSchema.setDocRemark(nSSRS.GetText(i, 5));
					// 问题卷发起人
					nES_DOC_MAINSchema.setDocFlag(nSSRS.GetText(i, 6));
					// 问题卷发起日期
					nES_DOC_MAINSchema.setMakeDate(nSSRS.GetText(i, 7));
					// 扫描批次号
					nES_DOC_MAINSchema.setMakeTime(nSSRS.GetText(i, 8));
					// 设置中心查询到的单证的扫描方式为2
					strScanType[i - 1] = "2";
					retES_DOC_MAINSet.add(nES_DOC_MAINSchema);
				}
			} else {
				// 错误处理
				CError tError = new CError();
				tError.moduleName = "QueryIssueDocBL";
				tError.functionName = "getReturnData";
				tError.errorNo = "-3001";
				String subType = "";
				if (strSubType.equals("")) {
					subType = "所有类型";
				}
				tError.errorMessage = "暂时没有需要重新扫描的问题卷，" + "您的查询条件是：\n\n批次号 ["
						+ strScanNo + "]；\n" + "管理机构 [" + strManageCom
						+ "]；\n发起操作员 [" + strOperator + "]；\n" + "业务号码 ["
						+ strBussNo + "]；\n单证细类 [" + subType + "]；\n"
						+ "发起时间介于 [" + strStartDate + "] 和 [" + strEndDate
						+ "]";

				this.mErrors.addOneError(tError);

				mResult.setElementAt(retES_DOC_MAINSet, 0);
				mResult.setElementAt(retES_DOC_PAGESET, 1);
				String strPageURL[] = new String[2];
				mResult.setElementAt(strPageURL, 2);
				return true;
			}
			// 设置返回值
			ES_DOC_PAGESSet nES_DOC_PAGESET = new ES_DOC_PAGESSet();
			// 返回retES_DOC_MAINSet
			mResult.setElementAt(retES_DOC_MAINSet, 0);
			// 返回nES_DOC_PAGESET
			mResult.setElementAt(nES_DOC_PAGESET, 1);
			String strPageUrl[] = new String[2];
			// 借用现有的处理方法,PageUrl这里没有实际用处
			mResult.setElementAt(strPageUrl, 2);
			// 没发生错误,错误代码为0,错误信息为空
			mResult.setElementAt("0", 3);
			mResult.setElementAt("", 4);
			// 扫描方式
			mResult.setElementAt(strScanType, 7);
		} catch (Exception ex) {
			logger.debug("Exception in QueryIssueDocBL->getReturnData");
			logger.debug("Exception:" + ex.toString());

			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "QueryIssueDocBL";
			tError.functionName = "getReturnData";
			tError.errorNo = "-99";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		QueryIssueDocBL queryissuedocbl = new QueryIssueDocBL();
	}
}

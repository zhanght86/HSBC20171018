package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:查询中心问题卷
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

public class QueryIssueDocUI {
private static Logger logger = Logger.getLogger(QueryIssueDocUI.class);
	private VData mInputData;
	private GlobalInput tG = new GlobalInput();
	private VData mResult = new VData();
	public CErrors mErrors = new CErrors();
	private String mRtnCode = "0";
	private String mRtnDesc = "";

	public QueryIssueDocUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = true;

		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		QueryIssueDocBL tQueryIssueDocBL = new QueryIssueDocBL();
		tReturn = tQueryIssueDocBL.submitData(mInputData, cOperate);

		// 如果有需要处理的错误，则返回
		if (tQueryIssueDocBL.mErrors.needDealError()) {
			mErrors.copyAllErrors(tQueryIssueDocBL.mErrors);
			mRtnCode = mErrors.getError(0).errorNo;
			mRtnDesc = mErrors.getError(0).errorMessage;
			tReturn = false;
		}

		// 返回数据处理
		mResult.clear();
		mResult = tQueryIssueDocBL.getResult();
		// mResult.addElement(mRtnCode);
		// mResult.addElement(mRtnDesc);
		mResult.setElementAt(mRtnCode, 3);
		mResult.setElementAt(mRtnDesc, 4);
		mInputData = null;

		return tReturn;
	}

	// 返回数据的公共方法
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		QueryIssueDocUI queryissuedocui = new QueryIssueDocUI();
		VData vData = new VData();
		queryissuedocui.submitData(vData, "");
		ES_DOC_MAINSet nES_DOC_MAINSet = (ES_DOC_MAINSet) queryissuedocui
				.getResult().get(0);

		vData = queryissuedocui.getResult();

		// vData转换为字符串参数
		StringBuffer bufXML = new StringBuffer(1024);
		ParameterDataConvert convert = new ParameterDataConvert();
		convert.vDataToXML(bufXML, vData);
		logger.debug("<IndexXML>" + bufXML.toString() + "</IndexXML>");

	}
}

package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
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
public class QueryDocumentUI {
private static Logger logger = Logger.getLogger(QueryDocumentUI.class);
	private VData mInputData;
	private GlobalInput tG = new GlobalInput();
	private VData mResult = new VData();
	public CErrors mErrors = new CErrors();
	private String mRtnCode = "0";
	private String mRtnDesc = "";

	public QueryDocumentUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = true;

		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		QueryDocumentBL tQueryDocumentBL = new QueryDocumentBL();
		tReturn = tQueryDocumentBL.submitData(mInputData, cOperate);

		// 如果有需要处理的错误，则返回
		if (tQueryDocumentBL.mErrors.needDealError()) {
			mErrors.copyAllErrors(tQueryDocumentBL.mErrors);
			mRtnCode = mErrors.getError(0).errorNo;
			mRtnDesc = mErrors.getError(0).errorMessage;
			tReturn = false;
		}

		// 返回数据处理
		mResult.clear();
		mResult = tQueryDocumentBL.getResult();
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
		QueryDocumentUI queryDocumentUI1 = new QueryDocumentUI();
	}
}

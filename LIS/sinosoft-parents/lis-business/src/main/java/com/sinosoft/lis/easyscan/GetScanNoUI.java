package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: EasyScan从中心请求获取扫描批次号
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

public class GetScanNoUI {
private static Logger logger = Logger.getLogger(GetScanNoUI.class);
	private VData mResult = new VData();
	public CErrors mErrors = new CErrors();

	public GetScanNoUI() {
	}

	// 传输数据的公共方法
	// 输入ES_DOC_MAINSchema,ES_DOC_PAGESSet
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = true;

		// 首先将数据在本类中做一个备份
		VData mInputData = (VData) cInputData.clone();

		GetScanNoBL tGetScanNoBL = new GetScanNoBL();
		tReturn = tGetScanNoBL.submitData(mInputData, "");
		// 如果有需要处理的错误，则返回
		if (tGetScanNoBL.mErrors.needDealError()) {
			mErrors.copyAllErrors(tGetScanNoBL.mErrors);
			tReturn = false;
		}
		// 返回数据处理
		mResult.clear();
		mResult = tGetScanNoBL.getResult();
		return tReturn;
	}

	// 返回数据的公共方法
	public String getResult() {
		String strRet = (String) mResult.get(0);
		return strRet;
	}

	public static void main(String[] args) {
		GetScanNoUI getScanNoUI1 = new GetScanNoUI();
		VData vData = new VData();
		String cOparater = "";
		getScanNoUI1.submitData(vData, cOparater);
		logger.debug(getScanNoUI1.getResult());
	}

}

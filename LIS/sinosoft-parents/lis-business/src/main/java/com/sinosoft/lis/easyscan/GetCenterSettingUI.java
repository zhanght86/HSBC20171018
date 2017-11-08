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
 * Description: EasyScan中心设置下载
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author wellhi
 * @version 1.0
 */

public class GetCenterSettingUI {
private static Logger logger = Logger.getLogger(GetCenterSettingUI.class);
	// private VData mInputData;
	private VData mResult = new VData();
	public CErrors mErrors = new CErrors();

	public GetCenterSettingUI() {
	}

	// 传输数据的公共方法
	// 输入ES_DOC_MAINSchema,ES_DOC_PAGESSet
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = true;

		// 首先将数据在本类中做一个备份
		VData mInputData = (VData) cInputData.clone();

		GetCenterSettingBL tGetCenterSettingBL = new GetCenterSettingBL();
		tReturn = tGetCenterSettingBL.submitData(mInputData, "");
		// 如果有需要处理的错误，则返回
		if (tGetCenterSettingBL.mErrors.needDealError()) {
			mErrors.copyAllErrors(tGetCenterSettingBL.mErrors);
			tReturn = false;
		}
		// 返回数据处理
		mResult.clear();
		mResult = tGetCenterSettingBL.getResult();
		return tReturn;
	}

	// 返回数据的公共方法
	public String getResult() {
		String strRet = (String) mResult.get(0);
		return strRet;
	}

	public static void main(String[] args) {
		VData vData = new VData();
		GlobalInput nGI = new GlobalInput();
		nGI.ManageCom = "86";
		nGI.Operator = "001";
		vData.add(nGI);
		GetCenterSettingUI tGetCenterSettingUI = new GetCenterSettingUI();
		tGetCenterSettingUI.submitData(vData, "");
		logger.debug(tGetCenterSettingUI.getResult());
	}
}

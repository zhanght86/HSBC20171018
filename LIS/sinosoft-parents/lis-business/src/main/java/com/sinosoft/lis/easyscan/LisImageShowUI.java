package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
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

public class LisImageShowUI implements BusinessService{
private static Logger logger = Logger.getLogger(LisImageShowUI.class);
	private VData mResult = new VData();
	public CErrors mErrors = new CErrors();

	public LisImageShowUI() {
	}

	// 传输数据的公共方法
	// 输入ES_DOC_MAINSchema,ES_DOC_PAGESSet
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = true;

		// 首先将数据在本类中做一个备份
		VData mInputData = (VData) cInputData.clone();

		LisImageShowBL tLisImageShowBL = new LisImageShowBL();
		tReturn = tLisImageShowBL.submitData(mInputData, "");
		// 如果有需要处理的错误，则返回
		if (tLisImageShowBL.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLisImageShowBL.mErrors);
			tReturn = false;
		}
		// 返回数据处理
		mResult.clear();
		mResult = tLisImageShowBL.getResult();
		return tReturn;
	}




	@Override
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	@Override
	public VData getResult() {
		// TODO Auto-generated method stub
		return mResult;
	}

}

package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:单证管理回收回退操作（界面输入）
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 周平
 * @version 1.0
 */
public class CertReveTakeBackUI implements BusinessService {
private static Logger logger = Logger.getLogger(CertReveTakeBackUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	// 业务处理相关变量
	private VData mResult = null;

	public CertReveTakeBackUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		try {
			CertReveTakeBackBL tCertReveTakeBackBL = new CertReveTakeBackBL();

			boolean bReturn = tCertReveTakeBackBL.submitData(cInputData, cOperate);

			mResult = tCertReveTakeBackBL.getResult();

			if (!bReturn) {
				if (tCertReveTakeBackBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tCertReveTakeBackBL.mErrors);
				} else {
					buildError("submitData", "CertReveTakeBackBL发生错误，但是没有提供详细的信息");
				}
			}
			return bReturn;
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("submitData", ex.getMessage());
			return false;
		}
	}

	/*
	 * add by kevin, 2002-09-23
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "CertReveTakeBackUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public VData getResult() {
		return mResult;
	}
	
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}

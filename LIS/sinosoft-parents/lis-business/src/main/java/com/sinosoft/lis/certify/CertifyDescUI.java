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
 * Description:单证定义
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 */
public class CertifyDescUI implements BusinessService{
private static Logger logger = Logger.getLogger(CertifyDescUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private String mOperate;

	public CertifyDescUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		CertifyDescBL tCertifyDescBL = new CertifyDescBL();
		if (tCertifyDescBL.submitData(cInputData, mOperate) == false) {
			this.mErrors.copyAllErrors(tCertifyDescBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "CertifyDescUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据保存失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else
			mResult = tCertifyDescBL.getResult();
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}

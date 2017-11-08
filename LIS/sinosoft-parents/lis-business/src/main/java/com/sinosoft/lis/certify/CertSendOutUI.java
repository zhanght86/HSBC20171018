package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LZCardSchema;
import com.sinosoft.lis.vschema.LZCardSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:单证发放操作（界面输入）
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
public class CertSendOutUI implements BusinessService {
private static Logger logger = Logger.getLogger(CertSendOutUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = null;

	public CertSendOutUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		try {
			CertSendOutBL tCertSendOutBL = new CertSendOutBL();

			boolean bReturn = tCertSendOutBL.submitData(cInputData, cOperate);

			mResult = tCertSendOutBL.getResult();

			if (!bReturn) {
				if (tCertSendOutBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tCertSendOutBL.mErrors);
				} else {
					buildError("submitData", "CertSendOutBL发生错误，但是没有提供详细的信息");
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
		cError.moduleName = "CertSendOutUI";
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

	public static void main(String[] args) {
		GlobalInput globalInput = new GlobalInput();

		globalInput.Operator = "001";
		globalInput.ComCode = "8611";
		globalInput.ManageCom = "";

		CertSendOutUI tCertSendOutUI = new CertSendOutUI();

		LZCardSet setLZCard = new LZCardSet();
		LZCardSchema schemaLZCard = new LZCardSchema();

		schemaLZCard.setCertifyCode("01");
		schemaLZCard.setSendOutCom("A86");
		schemaLZCard.setReceiveCom("86110000");

		schemaLZCard.setStartNo("");
		schemaLZCard.setEndNo("");
		schemaLZCard.setSumCount(1);

		schemaLZCard.setHandler("kevin");
		schemaLZCard.setHandleDate("2002-9-01");
		schemaLZCard.setInvaliDate("2002-9-01");

		setLZCard.add(schemaLZCard);

		VData vData = new VData();
		vData.addElement(globalInput);
		vData.addElement(setLZCard);
		vData.addElement("YES");

		if (!tCertSendOutUI.submitData(vData, "INSERT")) {
			logger.debug(tCertSendOutUI.mErrors.getFirstError());
		}
	}
}

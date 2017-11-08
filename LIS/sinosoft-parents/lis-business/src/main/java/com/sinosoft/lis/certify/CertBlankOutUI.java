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
 * Description:单证作废
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author kevin
 * @version 1.0
 */
public class CertBlankOutUI implements BusinessService {
private static Logger logger = Logger.getLogger(CertBlankOutUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	// 业务处理相关变量
	private VData mResult = null;

	public CertBlankOutUI() {
	}

	/*
	 * public function used to send data
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {
			CertBlankOutBL tCertBlankOutBL = new CertBlankOutBL();

			boolean bReturn = tCertBlankOutBL.submitData(cInputData, cOperate);
			mResult = tCertBlankOutBL.getResult();

			if (!bReturn) {
				if (tCertBlankOutBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tCertBlankOutBL.mErrors);
				} else {
					buildError("submitData", "CertTakeBackBL出错，但是没有提供详细的错误信息");
				}
			}

			return bReturn;
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("submitData", "发生异常");
			return false;
		}
	}

	public VData getResult() {
		return mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "CertTakeBackUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
	
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public static void main(String[] args) {
		GlobalInput globalInput = new GlobalInput();

		globalInput.Operator = "001";
		globalInput.ComCode = "8611";
		globalInput.ManageCom = "sdd";
		
		LZCardSet setLZCard = new LZCardSet();
		LZCardSchema schemaLZCard = new LZCardSchema();
		schemaLZCard.setCertifyCode("1101");
		schemaLZCard.setStartNo("86110100000042");
		schemaLZCard.setEndNo("86110100000042");
		schemaLZCard.setSumCount(1);		
		schemaLZCard.setSendOutCom("D8611000211");
		schemaLZCard.setReceiveCom("A8611");
		setLZCard.add(schemaLZCard);

		VData vData = new VData();
		vData.addElement(globalInput);
		vData.addElement(setLZCard);
		
		CertTakeBackUI tCertTakeBackUI = new CertTakeBackUI();
		if (!tCertTakeBackUI.submitData(vData, "INSERT")) {
			// if (!tCertTakeBackUI.submitData(vData, "TAKEBACK")) {
			logger.debug(tCertTakeBackUI.mErrors.getFirstError());
		}
	}
}

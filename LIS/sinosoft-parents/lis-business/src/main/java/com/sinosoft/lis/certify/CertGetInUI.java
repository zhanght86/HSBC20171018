package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LZCardPrintSchema;
import com.sinosoft.lis.schema.LZCardSchema;
import com.sinosoft.lis.vschema.LZCardPrintSet;
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
 * Description:单证入库
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
public class CertGetInUI implements BusinessService {
private static Logger logger = Logger.getLogger(CertGetInUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = null;

	public CertGetInUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		try {
			CertGetInBL tCertGetInBL = new CertGetInBL();
			if (!tCertGetInBL.submitData(cInputData, cOperate)) {
				if (tCertGetInBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tCertGetInBL.mErrors);
				} else {
					buildError("submitData", "CertGetInBL发生错误，但是没有提供详细的信息.");
				}
				mResult = tCertGetInBL.getResult();
				return false;
			}else{
				mResult = tCertGetInBL.getResult();
				return true;
			}			
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

		LZCardSet setLZCard = new LZCardSet();
		LZCardSchema schemaLZCard = new LZCardSchema();
		schemaLZCard.setSendOutCom("A86");
		schemaLZCard.setReceiveCom("86110000");
		schemaLZCard.setCertifyCode("01");
		schemaLZCard.setStartNo("");
		schemaLZCard.setEndNo("");
		schemaLZCard.setSumCount(1);
		schemaLZCard.setAmnt("100");
		schemaLZCard.setHandler("kevin");
		schemaLZCard.setHandleDate("2002-9-01");
		schemaLZCard.setInvaliDate("2002-9-01");
		setLZCard.add(schemaLZCard);

		// 加入第二个参数
		schemaLZCard = schemaLZCard.getSchema();
		schemaLZCard.setCertifyCode("05");
		schemaLZCard.setStartNo("");
		schemaLZCard.setEndNo("");
		schemaLZCard.setSumCount(10);

		LZCardPrintSet setLZCardPrint = new LZCardPrintSet();
		LZCardPrintSchema schemaLZCardPrint = new LZCardPrintSchema();
		schemaLZCardPrint.setPrtNo("01");
		schemaLZCardPrint.setCertifyCode("01");
		setLZCardPrint.add(schemaLZCardPrint);

		VData vData = new VData();
		vData.addElement(globalInput);
		vData.addElement(setLZCard);
		vData.addElement(setLZCardPrint);
		vData.addElement("YES");

		CertGetInUI tCertGetInUI = new CertGetInUI();
		tCertGetInUI.submitData(vData, "INSERT");
	}
}

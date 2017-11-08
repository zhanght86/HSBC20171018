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
 * Description:单证回收处理功能模块
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
public class CertDestroyConfirmUI implements BusinessService {
private static Logger logger = Logger.getLogger(CertDestroyConfirmUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 数据操作字符串 */
	private String mOperate;

	// 业务处理相关变量
	private VData mResult = null;

	public CertDestroyConfirmUI() {
	}

	/*
	 * public function used to send data
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {
			CertDestroyConfirmBL tCertDestroyConfirmBL = new CertDestroyConfirmBL();

			boolean bReturn = tCertDestroyConfirmBL.submitData(cInputData, cOperate);

			mResult = tCertDestroyConfirmBL.getResult();

			if (!bReturn) {
				if (tCertDestroyConfirmBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tCertDestroyConfirmBL.mErrors);
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
	
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "CertTakeBackUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		GlobalInput globalInput = new GlobalInput();

		globalInput.Operator = "001";
		globalInput.ComCode = "8611";
		globalInput.ManageCom = "sdd";

		CertTakeBackUI tCertTakeBackUI = new CertTakeBackUI();

		LZCardSet setLZCard = new LZCardSet();
		LZCardSchema schemaLZCard = new LZCardSchema();

		schemaLZCard.setCertifyCode("1101");
		schemaLZCard.setSubCode("");
		schemaLZCard.setRiskCode("");
		schemaLZCard.setRiskVersion("");

		schemaLZCard.setStartNo("86110100000042");
		schemaLZCard.setEndNo("86110100000042");

		schemaLZCard.setSendOutCom("D8611000211");
		schemaLZCard.setReceiveCom("A8611");

		schemaLZCard.setSumCount(0);
		schemaLZCard.setPrem("");
		schemaLZCard.setAmnt("");
		schemaLZCard.setHandler("");
		schemaLZCard.setHandleDate("");
		schemaLZCard.setInvaliDate("");

		schemaLZCard.setTakeBackNo("");
		schemaLZCard.setSaleChnl("");
		schemaLZCard.setStateFlag("1");
		schemaLZCard.setOperateFlag("");
		schemaLZCard.setPayFlag("");
		schemaLZCard.setEnterAccFlag("");
		schemaLZCard.setReason("");
		schemaLZCard.setState("");
		schemaLZCard.setOperator("111");
		schemaLZCard.setMakeDate("");
		schemaLZCard.setMakeTime("");
		schemaLZCard.setModifyDate("");
		schemaLZCard.setModifyTime("");

		setLZCard.add(schemaLZCard);

		VData vData = new VData();

		vData.addElement(globalInput);
		vData.addElement(setLZCard);

		if (!tCertTakeBackUI.submitData(vData, "INSERT")) {
			// if (!tCertTakeBackUI.submitData(vData, "TAKEBACK")) {
			logger.debug(tCertTakeBackUI.mErrors.getFirstError());
		}
	}
}

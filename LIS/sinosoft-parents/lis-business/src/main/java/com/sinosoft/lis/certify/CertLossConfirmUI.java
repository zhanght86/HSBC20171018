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
public class CertLossConfirmUI implements BusinessService {
private static Logger logger = Logger.getLogger(CertLossConfirmUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 数据操作字符串 */
	private String mOperate;

	// 业务处理相关变量
	private VData mResult = null;

	public CertLossConfirmUI() {
	}

	/*
	 * public function used to send data
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {
			mOperate = cOperate;

			// 得到外部传入的数据,将数据备份到本类中
			if (!getInputData(cInputData))
				return false;

			// 进行业务处理
			if (!dealData())
				return false;

			VData vData = new VData();

			// 准备往后台的数据
			if (!prepareOutputData(vData))
				return false;

			// UI中的特殊处理
			vData = cInputData;

			CertLossConfirmBL tCertLossConfirmBL = new CertLossConfirmBL();

			boolean bReturn = tCertLossConfirmBL.submitData(vData, mOperate);

			mResult = tCertLossConfirmBL.getResult();

			if (!bReturn) {
				if (tCertLossConfirmBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tCertLossConfirmBL.mErrors);
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

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData(VData vData) {
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		return true;
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

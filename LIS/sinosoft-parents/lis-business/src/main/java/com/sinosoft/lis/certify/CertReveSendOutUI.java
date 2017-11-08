package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import java.util.Hashtable;

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
 * Description:单证管理普通单证回收操作（界面输入）
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
public class CertReveSendOutUI implements BusinessService {
private static Logger logger = Logger.getLogger(CertReveSendOutUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 数据操作字符串 */
	private String mOperate;

	// 业务处理相关变量
	private VData mResult = null;

	public CertReveSendOutUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		try {
			this.mOperate = cOperate;

			// 得到外部传入的数据,将数据备份到本类中
			if (!getInputData(cInputData))
				return false;

			// 进行业务处理
			if (!dealData())
				return false;

			VData tVData = new VData();

			// 准备往后台的数据
			if (!prepareOutputData(tVData))
				return false;

			tVData = cInputData;

			CertReveSendOutBL tCertReveSendOutBL = new CertReveSendOutBL();
			boolean bReturn = tCertReveSendOutBL.submitData(tVData, mOperate);
			mResult = tCertReveSendOutBL.getResult();

			if (!bReturn) {
				if (tCertReveSendOutBL.mErrors.needDealError()) {
					this.mErrors.copyAllErrors(tCertReveSendOutBL.mErrors);
				} else {
					buildError("submitData", "UI提交失败，但是没有提供详细的信息");
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
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData(VData vData) {
		return true;
	}

	/*
	 * add by kevin, 2002-09-23
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "CertifySendOutUI";
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

		CertReveSendOutUI tCertReveSendOutUI = new CertReveSendOutUI();

		LZCardSet setLZCard = new LZCardSet();
		LZCardSchema schemaLZCard = new LZCardSchema();

		schemaLZCard.setCertifyCode("01");
		schemaLZCard.setSubCode("");
		schemaLZCard.setRiskCode("");
		schemaLZCard.setRiskVersion("");

		schemaLZCard.setStartNo("00000000000006");
		schemaLZCard.setEndNo("00000000000006");

		schemaLZCard.setSendOutCom("A8611");
		schemaLZCard.setReceiveCom("A86110000");

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
		schemaLZCard.setOperator("001");
		schemaLZCard.setMakeDate("");
		schemaLZCard.setMakeTime("");
		schemaLZCard.setModifyDate("");
		schemaLZCard.setModifyTime("");

		setLZCard.add(schemaLZCard);

		VData vData = new VData();
		vData.addElement(globalInput);
		vData.addElement(setLZCard);

		Hashtable hashParams = new Hashtable();
		hashParams.put("CertifyClass", "P");
		vData.addElement(hashParams);

		if (!tCertReveSendOutUI.submitData(vData, "INSERT")) {
			logger.debug(tCertReveSendOutUI.mErrors.getFirstError());
		}
	}
}

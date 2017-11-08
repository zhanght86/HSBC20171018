package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

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
public class CertTakeBackUI implements BusinessService {
private static Logger logger = Logger.getLogger(CertTakeBackUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private VData mResult = null;

	public CertTakeBackUI() {
	}

	/*
	 * public function used to send data
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {
			mOperate = cOperate;

			CertTakeBackBL tCertTakeBackBL = new CertTakeBackBL();

			boolean bReturn = tCertTakeBackBL.submitData(cInputData, mOperate);

			mResult = tCertTakeBackBL.getResult();

			if (!bReturn) {
				if (tCertTakeBackBL.mErrors.needDealError()) {
					mErrors.copyAllErrors(tCertTakeBackBL.mErrors);
				} else {
					buildError("submitData", "CertTakeBackBL出错，但是没有提供详细的错误信息");
				}
			}
			return bReturn;
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("submitData", "CertTakeBackBL发生异常");
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

		CertTakeBackUI tCertTakeBackUI = new CertTakeBackUI();

		LZCardSchema schemaLZCard = new LZCardSchema();
		schemaLZCard.setCertifyCode("132408");// 单证编码
		schemaLZCard.setStartNo("000000000000000001");// 起始号
		schemaLZCard.setEndNo("000000000000000002");// 终止号
		schemaLZCard.setSumCount(1);// 数量
		schemaLZCard.setHandler("001");// 经手人
		schemaLZCard.setHandleDate("2009-02-20");// 处理日期

		LZCardSet setLZCard = new LZCardSet();
		setLZCard.add(schemaLZCard);

		VData vData = new VData();
		vData.addElement(setLZCard);

		if (!tCertTakeBackUI.submitData(vData, "TAKEBACK")) {// 外部调用回收操作
			logger.debug(tCertTakeBackUI.mErrors.getFirstError());
		}
	}
}

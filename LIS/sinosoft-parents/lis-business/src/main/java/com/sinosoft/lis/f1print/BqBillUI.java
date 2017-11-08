package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 保全清单打印公用类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Liurx
 * @version 1.0
 */
public class BqBillUI implements BusinessService{
private static Logger logger = Logger.getLogger(BqBillUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public BqBillUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		BqBillBL tBqBillBL = new BqBillBL();

		if (tBqBillBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tBqBillBL.mErrors);
			mResult.clear();
			return false;
		} else {
			mResult = tBqBillBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}
	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		logger.debug("test begin:");
		BqBillUI tBqBillUI = new BqBillUI();
		// select * from ldcode where codetype like 'bqbill%'
		VData tVData = new VData();
		String ChnlType = "bqbillperson";// 个人
		// String ChnlType = "bqbillgrp";//团体
		// String ChnlType = "bqbillbank";//银代
		String BillType = "101";
		String ManageCom = "86110000";
		String SaleChnl = "1";// 销售渠道：1，2，3
		String StartDate = "1005-05-06";
		String EndDate = "3005-05-06";
		String Bank = "";
		String BankPoint = "";
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86110000";
		tGlobalInput.Operator = "001";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ChnlType", ChnlType);
		tTransferData.setNameAndValue("ManageCom", ManageCom);
		tTransferData.setNameAndValue("Chanel", SaleChnl);
		tTransferData.setNameAndValue("StartDate", StartDate);
		tTransferData.setNameAndValue("EndDate", EndDate);
		tTransferData.setNameAndValue("Type", BillType);
		tTransferData.setNameAndValue("Bank", Bank);
		tTransferData.setNameAndValue("BankPoint", BankPoint);

		tVData.add(tGlobalInput);
		tVData.add(tTransferData);
		if (!tBqBillUI.submitData(tVData, "PRINT")) {
			logger.debug("test failed");
		}
		logger.debug("test end");
	}

}

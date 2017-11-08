package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 业务数据转换到银行系统
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 1.0
 */

public class GetSendToBankDealUI {
private static Logger logger = Logger.getLogger(GetSendToBankDealUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 传出数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public GetSendToBankDealUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		logger.debug("---GetSendToBankDealUI BL BEGIN---");
		GetSendToBankDealBL tGetSendToBankDealBL = new GetSendToBankDealBL();

		if (tGetSendToBankDealBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGetSendToBankDealBL.mErrors);
			mResult.clear();
			mResult.add(mErrors.getFirstError());
			return false;
		} else {
			mResult = tGetSendToBankDealBL.getResult();
		}

		logger.debug("---GetSendToBankBig BL END---");

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GetSendToBankDealUI tGetSendToBankDealUI = new GetSendToBankDealUI();

		TransferData transferData1 = new TransferData();
		transferData1.setNameAndValue("startDate", "2006-12-01");
		transferData1.setNameAndValue("endDate", "2006-12-20");

		String[] tstr = new String[5];
		tstr[0] = "6";
		tstr[1] = "2";

		tstr[2] = "10";
		tstr[3] = "7";
		tstr[4] = "3";

		transferData1.setNameAndValue("typeFlag", tstr);
		transferData1.setNameAndValue("bankCode", "5100010");
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "8651";
		tGlobalInput.ComCode = "8651";

		VData tVData = new VData();
		tVData.add(transferData1);
		tVData.add(tGlobalInput);

		if (!tGetSendToBankDealUI.submitData(tVData, "GETMONEY")) {
			VData rVData = tGetSendToBankDealUI.getResult();
			logger.debug("Submit Failed! " + (String) rVData.get(0));
		} else {
			VData rVData = tGetSendToBankDealUI.getResult();
			logger.debug("Submit Succed! " + (String) rVData.get(0));
			// logger.debug("Submit Succed!");
		}
	}
}

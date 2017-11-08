package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LYReturnFromBankSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 银行数据转换到业务系统
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

public class GetReturnFromBankDealUI {
private static Logger logger = Logger.getLogger(GetReturnFromBankDealUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public GetReturnFromBankDealUI() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"GETMONEY"和"PAYMONEY"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中

		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		logger.debug("---GetReturnFromBankDeal BL BEGIN---");
		logger.debug("---GetReturnFromBankDeal BL BEGIN---");
		logger.debug("---GetReturnFromBankDeal BL BEGIN---");
		logger.debug("---GetReturnFromBankDeal BL BEGIN---");
		logger.debug("---GetReturnFromBankDeal BL BEGIN---");
		logger.debug("---GetReturnFromBankDeal BL BEGIN---");
		logger.debug("---GetReturnFromBankDeal BL BEGIN---");

		GetReturnFromBankDealBL tGetReturnFromBankDealBL = new GetReturnFromBankDealBL();
		if (tGetReturnFromBankDealBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGetReturnFromBankDealBL.mErrors);
			mResult.clear();
			mResult.add(mErrors.getFirstError());
			return false;
		} else {
			mResult = tGetReturnFromBankDealBL.getResult();
		}
		logger.debug("---GetReturnFromBankBig BL END---");

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
		GetReturnFromBankDealUI GetReturnFromBankBigUI1 = new GetReturnFromBankDealUI();

		TransferData transferData1 = new TransferData();
		transferData1.setNameAndValue("totalMoney", "20795");

		LYReturnFromBankSchema tLYReturnFromBankSchema = new LYReturnFromBankSchema();
		tLYReturnFromBankSchema.setSerialNo("00000000000000018061");
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		VData tVData = new VData();
		tVData.add(transferData1);
		tVData.add(tLYReturnFromBankSchema);
		tVData.add(tGlobalInput);

		if (!GetReturnFromBankBigUI1.submitData(tVData, "GETMONEY")) {
			VData rVData = GetReturnFromBankBigUI1.getResult();
			logger.debug("Submit Failed! " + (String) rVData.get(0));
		} else {
			logger.debug("Submit Succed!");
		}
	}
}

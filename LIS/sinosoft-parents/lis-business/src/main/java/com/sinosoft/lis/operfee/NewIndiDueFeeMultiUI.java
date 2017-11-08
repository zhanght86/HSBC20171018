package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HZM
 * @version 1.0
 */

public class NewIndiDueFeeMultiUI implements BusinessService{
private static Logger logger = Logger.getLogger(NewIndiDueFeeMultiUI.class);

	// 业务处理相关变量
	private VData mInputData;
	public CErrors mErrors = new CErrors();

	public NewIndiDueFeeMultiUI() {
	}

	public static void main(String[] args) {
		NewIndiDueFeeMultiUI NewIndiDueFeeMultiUI1 = new NewIndiDueFeeMultiUI();
		LCPolSchema tLCPolSchema = new LCPolSchema(); // 个人保单表
		GlobalInput tGI = new GlobalInput();
		tGI.ComCode = "86";
		tGI.ManageCom = "86";
		tGI.Operator = "001";
		tLCPolSchema.setGetStartDate("2003-4-1");// 将判断条件设置在起领日期字段中
		tLCPolSchema.setPayEndDate("2003-4-2"); // //将判断条件设置在终交日期字段中
		// by zhanghui 2005.2.18
		TransferData transferData1 = new TransferData();
		transferData1.setNameAndValue("bankCode", "0101");

		VData tVData = new VData();
		tVData.add(tLCPolSchema);

		// by zhanghui 2005.2.18
		tVData.add(transferData1);

		tVData.add(tGI);
		NewIndiDueFeeMultiUI tNewIndiDueFeeMultiUI = new NewIndiDueFeeMultiUI();
		tNewIndiDueFeeMultiUI.submitData(tVData, "INSERT");
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		NewIndiDueFeeMultiBL tNewIndiDueFeeMultiBL = new NewIndiDueFeeMultiBL();
		logger.debug("Start NewIndiDueFeeMulti UI Submit...");
		tNewIndiDueFeeMultiBL.submitData(mInputData, cOperate);

		logger.debug("End NewIndiDueFeeMulti UI Submit...");

		mInputData = null;
		// 如果有需要处理的错误，则返回
		if (tNewIndiDueFeeMultiBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tNewIndiDueFeeMultiBL.mErrors);
			return false;
		}
		logger.debug("error num=" + mErrors.getErrorCount());
		return true;
	}
	public VData getResult() {
		return null;
	}
	public CErrors getErrors()
	{
	      return mErrors;
	}
}

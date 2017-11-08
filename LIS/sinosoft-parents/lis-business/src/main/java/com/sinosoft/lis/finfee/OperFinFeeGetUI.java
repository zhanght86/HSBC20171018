package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:应交费用类（界面输入）（暂对个人） 从错误对象处理类继承，用来保存错误对象,在每个类中都存在
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

public class OperFinFeeGetUI implements BusinessService{
private static Logger logger = Logger.getLogger(OperFinFeeGetUI.class);

	// 业务处理相关变量
	private VData mInputData;
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	public OperFinFeeGetUI() {
	}

	public static void main(String[] args) {
//		OperFinFeeGetUI OperFinFeeGetUI1 = new OperFinFeeGetUI();
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
//		OperFinFeeGetBL tOperFinFeeGetBL = new OperFinFeeGetBL();
//		logger.debug("!!!----GOGOGOGO----!!!");
//		if (!tOperFinFeeGetBL.submitData(mInputData, cOperate)) {
//			this.mErrors.copyAllErrors(tOperFinFeeGetBL.mErrors);
//			return false;
//		}

//		return true;

		OperFinFeeGetBLF tOperFinFeeGetBLF = new OperFinFeeGetBLF();
		logger.debug("Start OperFinFeeGet UI Submit...");
		tOperFinFeeGetBLF.submitData(mInputData, cOperate);

		logger.debug("End OperFinFeeGet UI Submit...");

		// 如果有需要处理的错误，则返回
		if (tOperFinFeeGetBLF.mErrors.needDealError())
			this.mErrors.copyAllErrors(tOperFinFeeGetBLF.mErrors);
		else
		{
			mResult.clear();
			mResult = tOperFinFeeGetBLF.getResult();
		}
		logger.debug("error num=" + mErrors.getErrorCount());
		mInputData = null;
		return true;
	}


	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}


	public VData getResult() {
		// TODO Auto-generated method stub
		return mResult;
	}

}

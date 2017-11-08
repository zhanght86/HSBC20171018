package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

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

public class IndiNormalPayVerifyUI {
private static Logger logger = Logger.getLogger(IndiNormalPayVerifyUI.class);

	// 业务处理相关变量
	private VData mInputData;

	public CErrors mErrors = new CErrors();

	public IndiNormalPayVerifyUI() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("Start IndiNormalPayVerify UI Submit...");

		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		IndiNormalPayVerifyBLF tIndiNormalPayVerifyBLF = new IndiNormalPayVerifyBLF();
		tIndiNormalPayVerifyBLF.submitData(mInputData, cOperate);

		mInputData = null;
		// 如果有需要处理的错误，则返回
		if (tIndiNormalPayVerifyBLF.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tIndiNormalPayVerifyBLF.mErrors);
			logger.debug("error num=" + mErrors.getErrorCount());
			return false;
		}
		logger.debug("End IndiNormalPayVerify UI Submit...");
		return true;
	}

	public static void main(String[] args) {
		IndiNormalPayVerifyUI IndiNormalPayVerifyUI1 = new IndiNormalPayVerifyUI();
	}
}

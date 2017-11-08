package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class WholeGrpOperfeeFreeUI {
private static Logger logger = Logger.getLogger(WholeGrpOperfeeFreeUI.class);

	// 业务处理相关变量
	private VData mInputData;
	public CErrors mErrors = new CErrors();

	public WholeGrpOperfeeFreeUI() {
	}

	public static void main(String[] args) {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		WholeGrpOperfeeFreeBL tWholeGrpOperfeeFreeBL = new WholeGrpOperfeeFreeBL();
		logger.debug("Start WholeGrpOperfeeFree UI Submit...");
		tWholeGrpOperfeeFreeBL.submitData(mInputData, cOperate);

		logger.debug("End WholeGrpOperfeeFree UI Submit...");

		mInputData = null;
		// 如果有需要处理的错误，则返回
		if (tWholeGrpOperfeeFreeBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tWholeGrpOperfeeFreeBL.mErrors);
			logger.debug("error num=" + mErrors.getErrorCount());
			return false;
		}
		return true;
	}

}

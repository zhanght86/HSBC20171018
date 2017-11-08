package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class LCBalContSignUI {
private static Logger logger = Logger.getLogger(LCBalContSignUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	public LCBalContSignUI() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("Start LCBalContSignUI UI Submit...");
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		this.mInputData = (VData) cInputData.clone();
		LCBalContSignBL tLCBalContSignBL = new LCBalContSignBL();
		if (!tLCBalContSignBL.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCBalContSignBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDPlanRuleUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		logger.debug("End LCBalContSignUI UI Submit...");
		return true;
	}

	public static void main(String[] args) {
		LCBalContSignUI lcbalcontsignui = new LCBalContSignUI();
	}
}

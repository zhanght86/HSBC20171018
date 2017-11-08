package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:财务收付-->财务日结-->理赔日结-->拒赔退保金日结:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author
 * @version 1.0
 */

public class LLRefusePolFeeDayCheckUI {
private static Logger logger = Logger.getLogger(LLRefusePolFeeDayCheckUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	/** 往界面传输数据的容器 */
	public LLRefusePolFeeDayCheckUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		LLRefusePolFeeDayCheckBL tLLRefusePolFeeDayCheckBL = new LLRefusePolFeeDayCheckBL();

		if (tLLRefusePolFeeDayCheckBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLRefusePolFeeDayCheckBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLRefusePolFeeDayCheckBL";
			tError.functionName = "submitData";
			tError.errorMessage = "打印“拒赔退保金日结”失败！";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLRefusePolFeeDayCheckBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

	}
}

package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:财务收付-->财务日结-->理赔日结-->理赔扣回欠交保费日结:
 * </p>
 * <p>
 * Description: C01 -----保单结算退还保费----C0101-----退出险日期以后的保费[实收数据退费]----TF
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

public class LLReImMoreFeeDayCheckUI {
private static Logger logger = Logger.getLogger(LLReImMoreFeeDayCheckUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	/** 往界面传输数据的容器 */
	public LLReImMoreFeeDayCheckUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		LLReImMoreFeeDayCheckBL tLLReImMoreFeeDayCheckBL = new LLReImMoreFeeDayCheckBL();

		if (tLLReImMoreFeeDayCheckBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLReImMoreFeeDayCheckBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLReImMoreFeeDayCheckBL";
			tError.functionName = "submitData";
			tError.errorMessage = "打印“退还多交保费日结”失败！";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLReImMoreFeeDayCheckBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

	}
}

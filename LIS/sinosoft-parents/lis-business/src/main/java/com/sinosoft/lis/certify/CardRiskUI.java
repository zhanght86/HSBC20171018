package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:定额单险种定义
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 */
public class CardRiskUI {
private static Logger logger = Logger.getLogger(CardRiskUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	public CardRiskUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		CardRiskBL tCardRiskBL = new CardRiskBL();
		if (tCardRiskBL.submitData(cInputData, cOperate) == false) {
			this.mErrors.copyAllErrors(tCardRiskBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "tCardRiskBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据保存失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else
			mResult = tCardRiskBL.getResult();
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
	}
}

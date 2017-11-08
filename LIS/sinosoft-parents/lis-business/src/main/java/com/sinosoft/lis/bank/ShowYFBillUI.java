// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ShowYFBillUI.java

package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

// Referenced classes of package com.sinosoft.lis.bank:
//            ShowBillBL

public class ShowYFBillUI {
private static Logger logger = Logger.getLogger(ShowYFBillUI.class);


	public CErrors mErrors;
	private VData mInputData;
	private VData mResult;
	private String mOperate;

	public ShowYFBillUI() {
		mErrors = new CErrors();
		mInputData = new VData();
		mResult = new VData();
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mOperate = cOperate;
		ShowBillBL tShowBillBL = new ShowBillBL();
		logger.debug("---UI BEGIN---");
		if (!tShowBillBL.submitData(cInputData, mOperate)) {
			mErrors.copyAllErrors(tShowBillBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "tShowBillBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tShowBillBL.getResult();
			return true;
		}
	}

	public VData getResult() {
		return mResult;
	}
}

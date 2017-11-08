package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:CardRateStatUI.java
 * </p>
 * <p>
 * Description:单证核销率/作废率/遗失率报表
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author mw
 * @version 1.0
 */

public class CardRateStatUI {
private static Logger logger = Logger.getLogger(CardRateStatUI.class);
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	public CardRateStatUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		CardRateStatBL tCardRateStatBL = new CardRateStatBL();

		if (!tCardRateStatBL.submitData(cInputData, cOperate)) {
			mErrors.copyAllErrors(tCardRateStatBL.mErrors);
			return false;
		} else {
			mResult = (VData) tCardRateStatBL.getResult();
		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}
}

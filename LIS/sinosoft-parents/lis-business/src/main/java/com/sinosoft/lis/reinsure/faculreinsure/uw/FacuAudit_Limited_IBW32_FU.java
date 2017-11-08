

package com.sinosoft.lis.reinsure.faculreinsure.uw;

/**
 * <p> Title: </p> 
 * <p> Description:</p>
 * <p> Copyright: Copyright (c) 2011</p>  
 * <p> Company: sinosoft</p>
 * <p> 累计风险保额</p> 
 * @zhangbin
 * @version 1.0
 */

import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class FacuAudit_Limited_IBW32_FU extends FacuAudit_Limited {

	protected boolean check() {
		String tSql = "SELECT a.firsttrialoperator,b.insuredappage FROM lccont a,lcpol b WHERE a.contno=b.contno and a.contno='"
				+ mContNo + "' and b.polno='" + mPolNo + "'";
		SSRS infoSSRS = new ExeSQL().execSQL(tSql);
		if (infoSSRS.getMaxRow() == 0) {
			buildError("InitInfo", "没有保单的信息！PolNo=" + mPolNo);
			return false;
		}
		String firstTrialOperator = infoSSRS.getAllData()[0][0];
		if (firstTrialOperator != null && "N".equals(firstTrialOperator)) {
			return true;
		}
		return false;
	}
}


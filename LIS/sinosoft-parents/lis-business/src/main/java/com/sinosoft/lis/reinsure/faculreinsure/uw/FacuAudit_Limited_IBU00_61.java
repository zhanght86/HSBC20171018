

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

public class FacuAudit_Limited_IBU00_61 extends FacuAudit_Limited {
	protected boolean check() {
		String age = "";
		String tSql = "select insuredappage from lcpol a where a.Polno ='"
				+ mPolNo + "' ";
		age = new ExeSQL().getOneValue(tSql);
		if (Integer.parseInt(age) >= 61) {
			return true;
		}
		return false;
	}
}


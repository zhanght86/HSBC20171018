/*
 * <p>ClassName: LLClaimBL </p>
 * <p>Description: LLClaimSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔
 * @CreateDate：2002-07-25
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;

public class LPGrpEdorMainBL extends LPGrpEdorMainSchema {
private static Logger logger = Logger.getLogger(LPGrpEdorMainBL.class);
	// @Constructor
	public LPGrpEdorMainBL() {
	}

	public void setDefaultFields() {
		this.setOperator("1");
		this.setMakeDate(PubFun.getCurrentDate());
		this.setMakeTime(PubFun.getCurrentTime());
		this.setModifyDate(PubFun.getCurrentDate());
		this.setModifyTime(PubFun.getCurrentTime());
	}

	public void setUpdateFields() {
		this.setModifyDate(PubFun.getCurrentDate());
		this.setModifyTime(PubFun.getCurrentTime());
	}

}

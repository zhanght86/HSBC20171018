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
import com.sinosoft.lis.schema.LLClaimSchema;

public class LLClaimBL extends LLClaimSchema {
private static Logger logger = Logger.getLogger(LLClaimBL.class);
	// @Constructor
	public LLClaimBL() {
	}

	public void setDefaultFields() {
		this.setMngCom("1");
		this.setOperator("1");
		this.setMakeDate(PubFun.getCurrentDate());
		this.setMakeTime(PubFun.getCurrentTime());
		this.setModifyDate(PubFun.getCurrentDate());
		this.setModifyTime(PubFun.getCurrentTime());
	}

}

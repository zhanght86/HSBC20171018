/*
 * <p>ClassName: LJSGetEndorseBL </p>
 * <p>Description: LJSGetEndorseSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-04-01
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;

public class LJSGetEndorseBL extends LJSGetEndorseSchema {
private static Logger logger = Logger.getLogger(LJSGetEndorseBL.class);
	// @Constructor
	public LJSGetEndorseBL() {
	}

	public void setDefaultFields() {
		this.setMakeDate(PubFun.getCurrentDate());
		this.setMakeTime(PubFun.getCurrentTime());
		this.setModifyDate(PubFun.getCurrentDate());
		this.setModifyTime(PubFun.getCurrentTime());
	}
}

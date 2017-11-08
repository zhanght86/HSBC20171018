/*
 * <p>ClassName: LJAGetBL </p>
 * <p>Description: LJAGetSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-04-01
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJSGetSchema;

public class LJAGetBL extends LJAGetSchema {
private static Logger logger = Logger.getLogger(LJAGetBL.class);
	// @Constructor
	public LJAGetBL() {
	}

	public LJAGetSchema chg(LJSGetSchema tLJSGetSchema) {
		LJAGetSchema tLJAGetSchema = new LJAGetSchema();
		// tLJAGetSchema.setApproveCode(tLJSGetSchema.getApproveCode());
		// tLJAGetSchema.setApproveDate(tLJSGetSchema.getApproveDate()) ;
		// / tLJAGetSchema.setConfDate(tLJSGetSchema.getConfDate());
		// tLJAGetSchema.setEnterAccDate(tLJSGetSchema.getEnterAccDate());
		tLJAGetSchema.setGetNoticeNo(tLJSGetSchema.getGetNoticeNo());
		tLJAGetSchema.setMakeDate(tLJSGetSchema.getMakeDate());
		tLJAGetSchema.setSumGetMoney(tLJSGetSchema.getSumGetMoney());
		tLJAGetSchema.setOtherNo(tLJSGetSchema.getOtherNo());
		tLJAGetSchema.setOtherNoType(tLJSGetSchema.getOtherNoType());
		tLJAGetSchema.setModifyDate(tLJSGetSchema.getModifyDate());
		tLJAGetSchema.setModifyTime(tLJSGetSchema.getModifyTime());
		// tLJAGetSchema.setEnterAccDate(tLJSGetSchema.getEnterAccDate());
		tLJAGetSchema.setSaleChnl(tLJSGetSchema.getSaleChnl());
		tLJAGetSchema.setOperator(tLJSGetSchema.getOperator());
		return tLJAGetSchema;
	}

}

/*
 * <p>ClassName: LDPersonBL </p>
 * <p>Description: LDPersonSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-04-01
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.schema.LDPersonSchema;

public class LDPersonBL extends LDPersonSchema {
private static Logger logger = Logger.getLogger(LDPersonBL.class);
	// @Constructor
	public LDPersonBL() {
	}

	/**
	 * 通过客户号码查询数据库，得到该客户的信息
	 * 
	 * @param: no
	 * @return: no
	 * @author: YT
	 */
	public void queryDataByDB(String cCustomerNo) {
		LDPersonDB tLDPersonDB = new LDPersonDB();
		tLDPersonDB.setCustomerNo(cCustomerNo);
		this.setSchema(tLDPersonDB.query().get(1));
	}
}

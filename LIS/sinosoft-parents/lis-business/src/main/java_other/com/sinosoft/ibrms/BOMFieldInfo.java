package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

/**
 * Copyright (c) 2008 sinosoft Co. Ltd. All right reserved.
 */


import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.SQLwithBindVariables;

/**
 * <p>
 * ClassName:
 * </p>
 * <p>
 * Description: 获取BOM信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * @author: 
 * @date: 2008-08-26
 */

public class BOMFieldInfo {
private static Logger logger = Logger.getLogger(BOMFieldInfo.class);

	private LRBOMItemDB tLRBOMItemDB = new LRBOMItemDB();
	private LRBOMItemSet tLRBOMItemSet;	

	// 获取BOM对应的词条信息
	public LRBOMItemSet getItem(LRBOMSchema tLRBOMSchema) {
		String sql = "select * from lrbomitem where bomname='?bomname?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("bomname", tLRBOMSchema.getName());
		tLRBOMItemSet = tLRBOMItemDB.executeQuery(sqlbv);
		return tLRBOMItemSet;
	}
	
	// 获取BOM的名字
	public String  getFileName(LRBOMSchema tLRBOMSchema) {
		return tLRBOMSchema.getName();
	}
}

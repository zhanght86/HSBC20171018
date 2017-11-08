package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.SQLwithBindVariables;

/**
 * <p>
 * Title: Lis_New
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author Sinosoft
 * @version 1.0
 */
public class UpdateEdorState {
private static Logger logger = Logger.getLogger(UpdateEdorState.class);
	public UpdateEdorState() {
	}

	public static SQLwithBindVariables getUpdateEdorStateSql(
			LPEdorItemSchema tLPEdorItemSchema) {
//		String updateSql = "update LPEdorItem set EdorState='3' where EdorNo='"
//				+ tLPEdorItemSchema.getEdorNo() + "' and contno='"
//				+ tLPEdorItemSchema.getContNo() + "' and (ModifyDate>'"
//				+ tLPEdorItemSchema.getModifyDate() + "' or (ModifyDate='"
//				+ tLPEdorItemSchema.getModifyDate() + "' and ModifyTime>='"
//				+ tLPEdorItemSchema.getModifyTime() + "'))";
//		return updateSql;

		String updateSql = "update LPEdorItem set EdorState='3' where EdorNo='?EdorNo?' and contno='?contno?' and (ModifyDate>'?ModifyDate1?' or (ModifyDate='?ModifyDate2?' and ModifyTime>='?ModifyTime?'))";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(updateSql);
		sqlbv.put("EdorNo", tLPEdorItemSchema.getEdorNo());
		sqlbv.put("contno", tLPEdorItemSchema.getContNo());
		sqlbv.put("ModifyDate1", tLPEdorItemSchema.getModifyDate());
		sqlbv.put("ModifyDate2", tLPEdorItemSchema.getModifyDate());
		sqlbv.put("ModifyTime", tLPEdorItemSchema.getModifyTime());
		
		return sqlbv;
	}

	public static SQLwithBindVariables UpdateEdorStateOne(LPEdorItemSchema tLPEdorItemSchema) {
//		String updateSql = "update LPEdorItem set EdorState='1' where EdorNo='"
//				+ tLPEdorItemSchema.getEdorNo() + "' and contno='"
//				+ tLPEdorItemSchema.getContNo() + "' and (ModifyDate>'"
//				+ tLPEdorItemSchema.getModifyDate() + "' or (ModifyDate='"
//				+ tLPEdorItemSchema.getModifyDate() + "' and ModifyTime>='"
//				+ tLPEdorItemSchema.getModifyTime() + "'))";
//		return updateSql;
		
		String updateSql = "update LPEdorItem set EdorState='1' where EdorNo='?EdorNo?' and contno='?contno?' and (ModifyDate>'?ModifyDate1?' or (ModifyDate='?ModifyDate2?' and ModifyTime>='?ModifyTime?'))";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(updateSql);
		sqlbv.put("EdorNo", tLPEdorItemSchema.getEdorNo());
		sqlbv.put("contno", tLPEdorItemSchema.getContNo());
		sqlbv.put("ModifyDate1", tLPEdorItemSchema.getModifyDate());
		sqlbv.put("ModifyDate2", tLPEdorItemSchema.getModifyDate());
		sqlbv.put("ModifyTime", tLPEdorItemSchema.getModifyTime());
		
		return sqlbv;

	}

}

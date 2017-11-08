/*
 * <p>ClassName: LPPersonBL </p>
 * <p>Description: LPPersonBLSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全
 * @CreateDate：2002-07-25
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPersonDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPPersonSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;

public class LPPersonBL extends LPPersonSchema {
private static Logger logger = Logger.getLogger(LPPersonBL.class);

	// @Constructor
	public CErrors mErrors = new CErrors(); // 错误信息

	public LPPersonBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setDefaultFields() {
		this.setMakeDate(PubFun.getCurrentDate());
		this.setMakeTime(PubFun.getCurrentTime());
		this.setModifyDate(PubFun.getCurrentDate());
		this.setModifyTime(PubFun.getCurrentTime());
	}

	public void setUpdateFields() {
		this.setModifyDate(PubFun.getCurrentDate());
		this.setModifyTime(PubFun.getCurrentTime());
	}

	// 查询投保险人变动信息
	public boolean queryLPPerson(LPPersonSchema aLPPersonSchema) {
		
		/* 查找本次保全申请所有的保全项目，看是否能查到对应人员信息。
		 */
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSchema aLPEdorItemSchema = new LPEdorItemSchema();
		aLPEdorItemSchema.setEdorNo(aLPPersonSchema.getEdorNo());
		aLPEdorItemSchema.setEdorType(aLPPersonSchema.getEdorType());
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);
		String sql = "select * from LPEdorItem where EdorNo='"
				+ "?EdorNo1?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("EdorNo1", aLPEdorItemSchema.getEdorNo());
		logger.debug(sql);
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv1);
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全项目错误！");
			return false;
		}

		// 遍历这些保全项目
		for (int i = 1; i <= tLPEdorItemSet.size(); i++) {
			
			// 查询项目对应的保全个人客户信息
			LPPersonDB tLPPersonDB = new LPPersonDB();
			LPPersonSchema tLPPersonSchema = new LPPersonSchema();
			LPEdorItemSchema tLPEdorItemSchema = tLPEdorItemSet.get(i);
			tLPPersonSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPPersonSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			tLPPersonSchema.setCustomerNo(aLPPersonSchema.getCustomerNo());
			tLPPersonDB.setSchema(tLPPersonSchema);
			if (!tLPPersonDB.getInfo()) {
				continue;
			} else {
				tLPPersonDB.setEdorNo(aLPPersonSchema.getEdorNo());
				tLPPersonDB.setEdorType(aLPPersonSchema.getEdorType());
				this.setSchema(tLPPersonDB.getSchema());
				return true;
			}

		}

		/* 如果没找到人，则 查找已经申请确认的保单批改信息（没有保全确认），看能否找到相应人员
		 */
		sql = "select a.* from LPEdorItem a,LPPerson b where b.CustomerNo ='"
				+ "?CustomerNo?"
				+ "' and a.edorno= b.edorno and a.EdorState='2' order by a.MakeDate desc,a.MakeTime desc";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sql);
		sqlbv2.put("CustomerNo", aLPPersonSchema.getCustomerNo());
		logger.debug(sql);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv2);
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全项目错误！");
			return false;
		}

		// 遍历这些保全项目
		for (int i = 1; i <= tLPEdorItemSet.size(); i++) {
			
			// 查询项目对应的保全个人客户信息
			LPPersonDB tLPPersonDB = new LPPersonDB();
			LPEdorItemSchema tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPPersonSchema tLPPersonSchema = new LPPersonSchema();
			tLPPersonSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPPersonSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			tLPPersonSchema.setCustomerNo(aLPPersonSchema.getCustomerNo());
			tLPPersonDB.setSchema(tLPPersonSchema);
			if (!tLPPersonDB.getInfo()) {
				continue;
			} else {
				tLPPersonDB.setEdorNo(aLPPersonSchema.getEdorNo());
				tLPPersonDB.setEdorType(aLPPersonSchema.getEdorType());
				this.setSchema(tLPPersonDB.getSchema());
				return true;
			}
		}

		/* 如果仍然没找到人，则 可能是第一次申请,查承保保单的客户信息，看能否找到相应人员
		 */
		LDPersonDB tLDPersonDB = new LDPersonDB();
		tLDPersonDB.setCustomerNo(aLPPersonSchema.getCustomerNo());

		if (tLDPersonDB.getInfo()) {
			Reflections tReflections = new Reflections();
			LPPersonSchema tLPPersonSchema = new LPPersonSchema();
			tReflections.transFields(tLPPersonSchema, tLDPersonDB.getSchema());

			tLPPersonSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPPersonSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			this.setSchema(tLPPersonSchema);
			return true;

		} else {
			CError.buildErr(this, "查询保全项目错误！");
			return false;
		}
	}

	private void jbInit() throws Exception {
	}

	// 查询上次保全投保人资料信息(Added by Nicholas)
	public boolean queryLastLPPerson(LPEdorItemSchema aLPEdorItemSchema,
			LPPersonSchema aLPPersonSchema) {
		LPPersonSchema tLPPersonSchema = new LPPersonSchema();
		LPPersonSet aLPPersonSet = new LPPersonSet();

		LDPersonSchema tLDPersonSchema = new LDPersonSchema();
		LDPersonSet tLDPersonSet = new LDPersonSet();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

		LPPersonDB tLPPersonDB = null;

		String sql;
		int m, n;
		m = 0;
		n = 0;

		// 查找最近申请的保单批改信息
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);

		sql = "select * from LPEdorItem where ContNo='"
				+ "?ContNo?"
				+ "' and edorstate <>'0' and MakeDate<='"
				+ "?ContNo1?" + "' and MakeTime<'"
				+ "?ContNo2?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("ContNo", aLPEdorItemSchema.getContNo());
		sqlbv3.put("ContNo1", aLPEdorItemSchema.getMakeDate());
		sqlbv3.put("ContNo2", aLPEdorItemSchema.getMakeTime());
		// logger.debug(sql);

		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv3);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			tLPPersonDB = new LPPersonDB();

			tLPPersonSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPPersonSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			tLPPersonSchema.setCustomerNo(aLPPersonSchema.getCustomerNo());
			// logger.debug(tLPEdorItemSchema.getEdorType());

			tLPPersonDB.setSchema(tLPPersonSchema);
			if (!tLPPersonDB.getInfo()) {
				continue;
			} else {
				tLPPersonDB.setEdorNo(aLPPersonSchema.getEdorNo());
				tLPPersonDB.setEdorType(aLPPersonSchema.getEdorType());
				this.setSchema(tLPPersonDB.getSchema());
				return true;
			}
		}

		// 如果是第一次申请,得到承保保单的客户信息
		LDPersonDB tLDPersonDB = new LDPersonDB();
		tLDPersonDB.setCustomerNo(aLPPersonSchema.getCustomerNo());

		if (!tLDPersonDB.getInfo()) {
			return false;
		}
		tLDPersonSchema.setSchema(tLDPersonDB.getSchema());
		// 转换Schema
		Reflections tReflections = new Reflections();
		tReflections.transFields(tLPPersonSchema, tLDPersonSchema);
		tLPPersonSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
		tLPPersonSchema.setEdorType(aLPEdorItemSchema.getEdorType());
		this.setSchema(tLPPersonSchema);
		return true;
	}
}

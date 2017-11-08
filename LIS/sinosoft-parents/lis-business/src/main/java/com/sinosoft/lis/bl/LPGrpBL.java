/*
 * <p>ClassName: LPGrpBL </p>
 * <p>Description: LPGrpBLSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全
 * @CreateDate：2002-07-25
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDGrpDB;
import com.sinosoft.lis.db.LPGrpDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpSchema;
import com.sinosoft.lis.vschema.LDGrpSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;

public class LPGrpBL extends LPGrpSchema {
private static Logger logger = Logger.getLogger(LPGrpBL.class);

	// @Constructor
	public CErrors mErrors = new CErrors(); // 错误信息

	public LPGrpBL() {
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
	public boolean queryLPGrp(LPGrpSchema aLPGrpSchema) {
		LPGrpSchema tLPGrpSchema = new LPGrpSchema();
		LPGrpSet aLPGrpSet = new LPGrpSet();

		LDGrpSchema tLDGrpSchema = new LDGrpSchema();
		LDGrpSet tLDGrpSet = new LDGrpSet();
		LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		LPGrpEdorItemSchema aLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		LPGrpEdorItemSet tLPGrpEdorItemSet = new LPGrpEdorItemSet();

		String sql;
		int m, n;
		m = 0;
		n = 0;

		// 查找本次申请的其他保全项目更新后得LPGrp表
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		aLPGrpEdorItemSchema.setEdorNo(aLPGrpSchema.getEdorNo());
		aLPGrpEdorItemSchema.setEdorType(aLPGrpSchema.getEdorType());
		tLPGrpEdorItemDB.setSchema(aLPGrpEdorItemSchema);
		sql = "select * from LPGrpEdorItem where EdorNo='"
				+ aLPGrpEdorItemSchema.getEdorNo()
				+ "' order by MakeDate desc,MakeTime desc";
		logger.debug(sql);
		tLPGrpEdorItemSet = tLPGrpEdorItemDB.executeQuery(sql);
		if (tLPGrpEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全项目错误！");
			return false;
		}

		m = tLPGrpEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
			tLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(i);
			LPGrpDB tLPGrpDB = new LPGrpDB();

			tLPGrpSchema.setEdorNo(tLPGrpEdorItemSchema.getEdorNo());
			tLPGrpSchema.setEdorType(tLPGrpEdorItemSchema.getEdorType());
			tLPGrpSchema.setCustomerNo(aLPGrpSchema.getCustomerNo());
			// logger.debug(tLPGrpEdorItemSchema.getEdorType());
			// tLPGrpSchema.setCustomerNo(aLPGrpSchema.getCustomerNo());

			tLPGrpDB.setSchema(tLPGrpSchema);
			if (!tLPGrpDB.getInfo()) {
				continue;
			} else {
				tLPGrpDB.setEdorNo(aLPGrpSchema.getEdorNo());
				tLPGrpDB.setEdorType(aLPGrpSchema.getEdorType());
				this.setSchema(tLPGrpDB.getSchema());
				return true;
			}

		}

		// 查找已经申请确认的保单批改信息（没有保全确认）

		tLPGrpEdorItemSet.clear();
		m = 0;
		n = 0;

		sql = "select a.* from LPGrpEdorItem a,LPGrp b where b.CustomerNo ='"
				+ aLPGrpSchema.getCustomerNo()
				+ "' and a.edorno= b.edorno and a.EdorState='2' order by a.MakeDate desc,a.MakeTime desc";
		logger.debug(sql);
		tLPGrpEdorItemSet = tLPGrpEdorItemDB.executeQuery(sql);
		if (tLPGrpEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全项目错误！");
			return false;
		}
		m = tLPGrpEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
			tLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(i);
			LPGrpDB tLPGrpDB = new LPGrpDB();

			tLPGrpSchema.setEdorNo(tLPGrpEdorItemSchema.getEdorNo());
			tLPGrpSchema.setEdorType(tLPGrpEdorItemSchema.getEdorType());
			tLPGrpSchema.setCustomerNo(aLPGrpSchema.getCustomerNo());
			tLPGrpDB.setSchema(tLPGrpSchema);
			if (!tLPGrpDB.getInfo()) {
				continue;
			} else {
				tLPGrpDB.setEdorNo(aLPGrpSchema.getEdorNo());
				tLPGrpDB.setEdorType(aLPGrpSchema.getEdorType());
				this.setSchema(tLPGrpDB.getSchema());
				return true;
			}
		}

		// 如果是第一次申请,得到承保保单的客户信息
		LDGrpDB tLDGrpDB = new LDGrpDB();
		tLDGrpDB.setCustomerNo(aLPGrpSchema.getCustomerNo());

		if (tLDGrpDB.getInfo()) {
			Reflections tReflections = new Reflections();
			tReflections.transFields(tLPGrpSchema, tLDGrpDB.getSchema());

			tLPGrpSchema.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
			tLPGrpSchema.setEdorType(aLPGrpEdorItemSchema.getEdorType());
			this.setSchema(tLPGrpSchema);
			return true;

		} else {
			CError.buildErr(this, "查询保全项目错误！");
			return false;
		}
	}

	private void jbInit() throws Exception {
	}

}

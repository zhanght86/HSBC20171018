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

import com.sinosoft.lis.db.LCGrpAddressDB;
import com.sinosoft.lis.db.LPGrpAddressDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpAddressSchema;
import com.sinosoft.lis.schema.LPGrpAddressSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LCGrpAddressSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;

public class LPGrpAddressBL extends LPGrpAddressSchema {
private static Logger logger = Logger.getLogger(LPGrpAddressBL.class);

	// @Constructor
	public CErrors mErrors = new CErrors(); // 错误信息

	public LPGrpAddressBL() {
	}

	public void setDefaultFields() {
		this.setMakeDate(PubFun.getCurrentDate());
		this.setMakeTime(PubFun.getCurrentTime());
		this.setModifyDate(PubFun.getCurrentDate());
		this.setModifyTime(PubFun.getCurrentTime());
	}

	// 查询投保险人变动信息
	public boolean queryLPGrp(LPGrpAddressSchema aLPGrpAddressSchema) {
		LPGrpAddressSchema tLPGrpAddressSchema = new LPGrpAddressSchema();
		LPGrpSet aLPGrpSet = new LPGrpSet();

		LCGrpAddressSchema tLCGrpAddressSchema = new LCGrpAddressSchema();
		LCGrpAddressSet tLCGrpAddressSet = new LCGrpAddressSet();
		LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		LPGrpEdorItemSchema aLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		LPGrpEdorItemSet tLPGrpEdorItemSet = new LPGrpEdorItemSet();

		String sql;
		int m, n;
		m = 0;
		n = 0;

		// 查找本次申请的其他保全项目更新后得LPGrp表
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		aLPGrpEdorItemSchema.setEdorNo(aLPGrpAddressSchema.getEdorNo());
		// aLPGrpEdorItemSchema.setEdorType(aLPGrpAddressSchema.getEdorType());
		// aLPGrpEdorItemSchema.setGrpContNo(aLPGrpAddressSchema.getGrpContNo());
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
			LPGrpAddressDB tLPGrpAddressDB = new LPGrpAddressDB();

			tLPGrpAddressSchema.setEdorNo(tLPGrpEdorItemSchema.getEdorNo());
			tLPGrpAddressSchema.setEdorType(tLPGrpEdorItemSchema.getEdorType());
			tLPGrpAddressSchema.setCustomerNo(aLPGrpAddressSchema
					.getCustomerNo());
			tLPGrpAddressSchema
					.setAddressNo(aLPGrpAddressSchema.getAddressNo());
			// logger.debug(tLPGrpEdorItemSchema.getEdorType());
			// tLPGrpAddressSchema.setCustomerNo(aLPGrpAddressSchema.getCustomerNo());

			tLPGrpAddressDB.setSchema(tLPGrpAddressSchema);
			if (!tLPGrpAddressDB.getInfo()) {
				continue;
			} else {
				tLPGrpAddressDB.setEdorNo(aLPGrpAddressSchema.getEdorNo());
				tLPGrpAddressDB.setEdorType(aLPGrpAddressSchema.getEdorType());
				this.setSchema(tLPGrpAddressDB.getSchema());
				return true;
			}

		}

		// 查找已经申请确认的保单批改信息（没有保全确认）

		tLPGrpEdorItemSet.clear();
		m = 0;
		n = 0;

		sql = "select a.* from LPGrpEdorItem a, LPGrpAddress b where b.addressno='"
				+ aLPGrpAddressSchema.getAddressNo()
				+ "' and a.edorno=b.edorno and a.EdorState='2' order by a.MakeDate desc,a.MakeTime desc";
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
			LPGrpAddressDB tLPGrpAddressDB = new LPGrpAddressDB();

			tLPGrpAddressSchema.setEdorNo(tLPGrpEdorItemSchema.getEdorNo());
			tLPGrpAddressSchema.setEdorType(tLPGrpEdorItemSchema.getEdorType());
			tLPGrpAddressSchema.setCustomerNo(aLPGrpAddressSchema
					.getCustomerNo());
			tLPGrpAddressSchema
					.setAddressNo(aLPGrpAddressSchema.getAddressNo());
			tLPGrpAddressDB.setSchema(tLPGrpAddressSchema);
			if (!tLPGrpAddressDB.getInfo()) {
				continue;
			} else {
				tLPGrpAddressDB.setEdorNo(aLPGrpAddressSchema.getEdorNo());
				tLPGrpAddressDB.setEdorType(aLPGrpAddressSchema.getEdorType());
				this.setSchema(tLPGrpAddressDB.getSchema());
				return true;
			}
		}

		// 如果是第一次申请,得到承保保单的客户信息
		LCGrpAddressDB tLCGrpAddressDB = new LCGrpAddressDB();
		tLCGrpAddressDB.setCustomerNo(aLPGrpAddressSchema.getCustomerNo());
		tLCGrpAddressDB.setAddressNo(aLPGrpAddressSchema.getAddressNo());

		if (tLCGrpAddressDB.getInfo()) {
			Reflections tReflections = new Reflections();
			tReflections.transFields(tLPGrpAddressSchema, tLCGrpAddressDB
					.getSchema());

			tLPGrpAddressSchema.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
			tLPGrpAddressSchema.setEdorType(aLPGrpEdorItemSchema.getEdorType());
			this.setSchema(tLPGrpAddressSchema);
			return true;

		} else {
			CError.buildErr(this, "查询团体地址信息错误！");
			return false;
		}
	}

}

/*
 * <p>ClassName: LPInsuredBL </p>
 * <p>Description: LPInsuredBLSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全
 * @CreateDate：2002-07-25
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;

public class LPInsuredBL extends LPInsuredSchema {
private static Logger logger = Logger.getLogger(LPInsuredBL.class);
	// @Constructor
	public CErrors mErrors = new CErrors(); // 错误信息

	public LPInsuredBL() {
	}

	public void setUpdateFields() {
		this.setModifyDate(PubFun.getCurrentDate());
		this.setModifyTime(PubFun.getCurrentTime());
	}

	// 查询最新保全被保险人变动信息
	public LPInsuredSet queryLPInsured(LPEdorItemSchema aLPEdorItemSchema) {
		LPInsuredSchema aLPInsuredSchema = new LPInsuredSchema();
		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		LPInsuredSet aLPInsuredSet = new LPInsuredSet();

		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

		Reflections tReflections = new Reflections();

		String sql;
		int m, n;
		m = 0;
		n = 0;

		// 查找本次申请的保单批改信息
		sql = "select * from LPEdorItem where EdorNo='"
				+ "?EdorNo1?" + "' and ContNo='"
				+ "?EdorNo2?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("EdorNo1", aLPEdorItemSchema.getEdorNo());
		sqlbv1.put("EdorNo2", aLPEdorItemSchema.getPolNo());
		logger.debug(sql);

		LPEdorItemDB iLPEdorItemDB = new LPEdorItemDB();
		iLPEdorItemDB.setSchema(aLPEdorItemSchema);
		if (iLPEdorItemDB.getInfo()) {
			// delete EdorValiDate by Minim at 2003-12-17
			sql = "select * from LPEdorItem where EdorNo='"
					+ "?EdorNo?" + "' and (MakeDate< '"
					+ "?MakeDate?" + "' or (MakeDate='"
					+ "?MakeDate1?" + "' and MakeTime<='"
					+ "?MakeTime?"
					+ "')) order by MakeDate,MakeTime desc";
			sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(sql);
			sqlbv1.put("EdorNo", aLPEdorItemSchema.getEdorNo());
			sqlbv1.put("MakeDate", aLPEdorItemSchema.getMakeDate());
			sqlbv1.put("MakeDate1", aLPEdorItemSchema.getMakeDate());
			sqlbv1.put("MakeTime", aLPEdorItemSchema.getMakeTime());
		}

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv1);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPInsuredDB tLPInsuredDB = new LPInsuredDB();

			tLPInsuredSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPInsuredSchema.setContNo(aLPEdorItemSchema.getContNo());
			tLPInsuredSchema.setEdorType(aLPEdorItemSchema.getEdorType());

			tLPInsuredDB.setSchema(tLPInsuredSchema);
			aLPInsuredSet = tLPInsuredDB.query();
			n = aLPInsuredSet.size();
			if (n == 0) {
				continue;
			}
			return aLPInsuredSet;
		}

		// 查找已经申请确认的保单批改信息（没有保全确认）
		tLPEdorItemSet.clear();
		m = 0;
		n = 0;

		sql = "select * from LPEdorItem where EdorState='2' and ContNo='"
				+ "？ContNo？"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("ContNo", aLPEdorItemSchema.getContNo());
		logger.debug(sql);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv3);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {

			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPInsuredDB tLPInsuredDB = new LPInsuredDB();

			tLPInsuredSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPInsuredSchema.setContNo(tLPEdorItemSchema.getContNo());
			tLPInsuredSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			tLPInsuredSchema.setInsuredNo(tLPEdorItemSchema.getInsuredNo());
			tLPInsuredDB.setSchema(tLPInsuredSchema);

			aLPInsuredSet.clear();
			aLPInsuredSet = tLPInsuredDB.query();
			n = aLPInsuredSet.size();
			if (n == 0) {
				continue;
			}
			return aLPInsuredSet;
		}

		n = 0;

		// 如果是第一次申请,得到承保保单的客户信息
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(aLPEdorItemSchema.getContNo());
		tLCInsuredDB.setInsuredNo(aLPEdorItemSchema.getInsuredNo());
		tLCInsuredSet = tLCInsuredDB.query();
		n = tLCInsuredSet.size();
		for (int i = 1; i <= n; i++) {
			tLCInsuredSchema = tLCInsuredSet.get(i);
			// 转换Schema
			tReflections.transFields(aLPInsuredSchema, tLCInsuredSchema);

			aLPInsuredSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			aLPInsuredSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			aLPInsuredSet.add(aLPInsuredSchema);
		}
		return aLPInsuredSet;
	}

	// 查询被保险人变动信息
	public boolean queryLPInsured(LPInsuredSchema aLPInsuredSchema) {
		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		LPInsuredSet aLPInsuredSet = new LPInsuredSet();

		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSchema aLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

		String sql;
		int m, n;
		m = 0;
		n = 0;

		// 查找本次申请的保单批改信息
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		aLPEdorItemSchema.setEdorNo(aLPInsuredSchema.getEdorNo());
		aLPEdorItemSchema.setGrpContNo(aLPInsuredSchema.getGrpContNo());
		aLPEdorItemSchema.setContNo(aLPInsuredSchema.getContNo());
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);
		tLPEdorItemSet = tLPEdorItemDB.query();
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPInsuredDB tLPInsuredDB = new LPInsuredDB();

			tLPInsuredSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPInsuredSchema.setContNo(tLPEdorItemSchema.getContNo());
			tLPInsuredSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());
			tLPInsuredSchema.setInsuredNo(aLPInsuredSchema.getInsuredNo());

			tLPInsuredDB.setSchema(tLPInsuredSchema);
			if (!tLPInsuredDB.getInfo()) {
				continue;
			} else {
				tLPInsuredDB.setEdorNo(aLPInsuredSchema.getEdorNo());
				tLPInsuredDB.setEdorType(aLPInsuredSchema.getEdorType());
				this.setSchema(tLPInsuredDB.getSchema());
				return true;
			}
		}

		// 查找已经申请确认的保单批改信息（没有保全确认）

		tLPEdorItemSet.clear();
		m = 0;
		n = 0;

		aLPEdorItemSchema = new LPEdorItemSchema();
		aLPEdorItemSchema.setGrpContNo(aLPInsuredSchema.getGrpContNo());
		aLPEdorItemSchema.setContNo(aLPInsuredSchema.getContNo());
		aLPEdorItemSchema.setEdorState("2");
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);
		tLPEdorItemSet = tLPEdorItemDB.query();
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPInsuredDB tLPInsuredDB = new LPInsuredDB();

			tLPInsuredSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPInsuredSchema.setContNo(tLPEdorItemSchema.getContNo());
			tLPInsuredSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());
			tLPInsuredSchema.setInsuredNo(aLPInsuredSchema.getInsuredNo());
			tLPInsuredDB.setSchema(tLPInsuredSchema);
			if (!tLPInsuredDB.getInfo()) {
				continue;
			} else {
				tLPInsuredDB.setEdorNo(aLPInsuredSchema.getEdorNo());
				tLPInsuredDB.setEdorType(aLPInsuredSchema.getEdorType());
				this.setSchema(tLPInsuredDB.getSchema());
				return true;
			}
		}

		n = 0;

		// 如果是第一次申请,得到承保保单的客户信息
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();

		tLCInsuredDB.setContNo(aLPInsuredSchema.getContNo());
		tLCInsuredDB.setGrpContNo(aLPInsuredSchema.getGrpContNo());

		tLCInsuredSet = tLCInsuredDB.query();
		n = tLCInsuredSet.size();
		logger.debug("------n:" + n);
		for (int i = 1; i <= n; i++) {
			tLCInsuredSchema = tLCInsuredSet.get(i);
			logger.debug("ContNo:" + aLPInsuredSchema.getContNo());
			logger.debug("InsuredNo:" + aLPInsuredSchema.getInsuredNo());

			if (tLCInsuredSchema.getContNo().equals(
					aLPInsuredSchema.getContNo())
					&& tLCInsuredSchema.getInsuredNo().equals(
							aLPInsuredSchema.getInsuredNo())) {
				// 转换Schema
				Reflections tReflections = new Reflections();
				tReflections.transFields(tLPInsuredSchema, tLCInsuredSchema);

				tLPInsuredSchema.setEdorNo(aLPInsuredSchema.getEdorNo());
				tLPInsuredSchema.setEdorType(aLPInsuredSchema.getEdorType());
				this.setSchema(tLPInsuredSchema);
				return true;
			}

		}
		return false;
	}

	/**
	 * 查询所有的客户信息
	 * 
	 * @param aLPEdorMainSchema
	 * @return
	 */
	public LPInsuredSet queryAllLPInsured(LPEdorItemSchema aLPEdorItemSchema) {
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		Reflections tReflections = new Reflections();
		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		LPInsuredSet tLPInsuredSet = new LPInsuredSet();

		LCInsuredDB tLCInsuredDB = new LCInsuredDB();

		tLCInsuredDB.setContNo(aLPEdorItemSchema.getContNo());

		tLCInsuredSet = tLCInsuredDB.query();
		for (int i = 1; i <= tLCInsuredSet.size(); i++) {
			tReflections.transFields(tLPInsuredSchema, tLCInsuredSet.get(i));
			tLPInsuredSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPInsuredSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			if (!this.queryLPInsured(tLPInsuredSchema)) {
				return tLPInsuredSet;
			}
			tLPInsuredSet.add(this.getSchema());
		}
		return tLPInsuredSet;
	}

	// 查询上次保全被保险人变动信息
	public boolean queryLastLPInsured(LPEdorItemSchema aLPEdorItemSchema,
			LPInsuredSchema aLPInsuredSchema) {
		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		LPInsuredSet aLPInsuredSet = new LPInsuredSet();

		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

		String sql;
		int m, n;
		m = 0;
		n = 0;

		// 查找最近申请的保单批改信息
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);

		sql = "select * from LPEdorItem where ContNo='"
				+ "?ContNo1?"
				+ "' and edorstate not in ('0', '4', '7', '8', '9') and (MakeDate<'"
				+ "?ContNo2?" + "' or (MakeDate='"
				+ "?ContNo3?" + "' and MakeTime<'"
				+ "?ContNo4?"
				+ "')) order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("ContNo1", aLPEdorItemSchema.getContNo());
		sqlbv3.put("ContNo2", aLPEdorItemSchema.getMakeDate());
		sqlbv3.put("ContNo3", aLPEdorItemSchema.getMakeDate());
		sqlbv3.put("ContNo4", aLPEdorItemSchema.getMakeTime());

		logger.debug(sql);

		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv3);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPInsuredDB tLPInsuredDB = new LPInsuredDB();

			tLPInsuredSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPInsuredSchema.setContNo(tLPEdorItemSchema.getContNo());
			tLPInsuredSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());
			tLPInsuredSchema.setInsuredNo(aLPInsuredSchema.getInsuredNo());

			tLPInsuredDB.setSchema(tLPInsuredSchema);
			if (!tLPInsuredDB.getInfo()) {
				continue;
			} else {
				tLPInsuredDB.setEdorNo(aLPInsuredSchema.getEdorNo());
				tLPInsuredDB.setEdorType(aLPInsuredSchema.getEdorType());
				this.setSchema(tLPInsuredDB.getSchema());
				return true;
			}
		}

		// 如果是第一次申请,得到承保保单的客户信息
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(aLPEdorItemSchema.getContNo());

		tLCInsuredSet = tLCInsuredDB.query();
		n = tLCInsuredSet.size();
		logger.debug("------n:" + n);
		for (int i = 1; i <= n; i++) {
			tLCInsuredSchema = tLCInsuredSet.get(i);
			logger.debug("ContNo:" + aLPInsuredSchema.getContNo());
			logger.debug("InsuredNo:" + aLPInsuredSchema.getInsuredNo());

			if (tLCInsuredSchema.getContNo().equals(
					aLPInsuredSchema.getContNo())
					&& tLCInsuredSchema.getInsuredNo().equals(
							aLPInsuredSchema.getInsuredNo())) {
				// 转换Schema
				Reflections tReflections = new Reflections();
				tReflections.transFields(tLPInsuredSchema, tLCInsuredSchema);

				tLPInsuredSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
				tLPInsuredSchema.setEdorType(aLPEdorItemSchema.getEdorType());
				this.setSchema(tLPInsuredSchema);
				return true;
			}

		}
		return false;
	}
}

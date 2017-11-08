/*
 * <p>ClassName: LPAppntBL </p>
 * <p>Description: LPAppntBLSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全
 * @CreateDate：2002-07-25
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;

public class LPAppntBL extends LPAppntSchema {
private static Logger logger = Logger.getLogger(LPAppntBL.class);
	// @Constructor
	public CErrors mErrors = new CErrors(); // 错误信息

	public LPAppntBL() {
	}

	public void setUpdateFields() {
		this.setModifyDate(PubFun.getCurrentDate());
		this.setModifyTime(PubFun.getCurrentTime());
	}

	// 查询被保险人变动信息
	public LPAppntSet queryLPAppnt(LPEdorItemSchema aLPEdorItemSchema) {
		LPAppntSchema aLPAppntSchema = new LPAppntSchema();
		LPAppntSchema tLPAppntSchema = new LPAppntSchema();
		LPAppntSet aLPAppntSet = new LPAppntSet();

		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		LCAppntSet tLCAppntSet = new LCAppntSet();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		Reflections tReflections = new Reflections();

		String sql;
		int m, n;
		m = 0;
		n = 0;

		// 查找本次申请的保单批改信息

		sql = "select * from LPEdorItem where EdorNo='"
				+ "?EdorNo?" + "' and ContNo='"
				+ "?ContNo?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("EdorNo", aLPEdorItemSchema.getEdorNo());
		sqlbv1.put("ContNo", aLPEdorItemSchema.getContNo());
		logger.debug(sql);

		LPEdorItemDB iLPEdorItemDB = new LPEdorItemDB();
		iLPEdorItemDB.setSchema(aLPEdorItemSchema);
		if (iLPEdorItemDB.getInfo()) {
			// delete EdorValiDate by Minim at 2003-12-17
			sql = "select * from LPEdorItem where EdorNo='"
					+ "?EdorNo1?" + "' and ContNo='"
					+ "?ContNo1?" + "' and MakeDate<='"
					+ "?MakeDate?" + "' and MakeTime<='"
					+ "?MakeTime?"
					+ "' order by MakeDate desc,MakeTime desc";
			sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(sql);
			sqlbv1.put("EdorNo1", aLPEdorItemSchema.getEdorNo());
			sqlbv1.put("ContNo1", aLPEdorItemSchema.getContNo());
			sqlbv1.put("MakeDate", iLPEdorItemDB.getMakeDate());
			sqlbv1.put("MakeTime", iLPEdorItemDB.getMakeTime());
		}

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv1);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPAppntDB tLPAppntDB = new LPAppntDB();

			tLPAppntSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPAppntSchema.setContNo(tLPEdorItemSchema.getContNo());
			tLPAppntSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());

			tLPAppntDB.setSchema(tLPAppntSchema);
			aLPAppntSet = tLPAppntDB.query();
			n = aLPAppntSet.size();
			logger.debug("------n" + n);
			if (n == 0) {
				continue;
			}
			return aLPAppntSet;
		}

		// 查找已经申请确认的保单批改信息（没有保全确认）

		tLPEdorItemSet.clear();
		m = 0;
		n = 0;

		sql = "select * from LPEdorItem where EdorState='2' and ContNo='"
				+ "?ContNo2?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("ContNo2", aLPEdorItemSchema.getContNo());
		logger.debug(sql);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv3);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {

			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPAppntDB tLPAppntDB = new LPAppntDB();

			tLPAppntSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPAppntSchema.setContNo(tLPEdorItemSchema.getContNo());
			tLPAppntSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			tLPAppntDB.setSchema(tLPAppntSchema);

			aLPAppntSet.clear();
			aLPAppntSet = tLPAppntDB.query();
			n = aLPAppntSet.size();
			if (n == 0) {
				continue;
			}
			return aLPAppntSet;
		}

		n = 0;

		// 如果是第一次申请,得到承保保单的客户信息
		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(aLPEdorItemSchema.getContNo());

		tLCAppntSet = tLCAppntDB.query();
		n = tLCAppntSet.size();
		logger.debug("------n:" + n);
		for (int i = 1; i <= n; i++) {
			tLCAppntSchema = tLCAppntSet.get(i);
			// 转换Schema
			tReflections.transFields(aLPAppntSchema, tLCAppntSchema);

			aLPAppntSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			aLPAppntSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			aLPAppntSet.add(aLPAppntSchema);
		}
		return aLPAppntSet;
	}

	// 查询投保险人变动信息
	public boolean queryLPAppnt(LPAppntSchema aLPAppntSchema) {
		LPAppntSchema tLPAppntSchema = new LPAppntSchema();
		LPAppntSet aLPAppntSet = new LPAppntSet();

		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		LCAppntSet tLCAppntSet = new LCAppntSet();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSchema aLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

		String sql;
		int m, n;
		m = 0;
		n = 0;

		// 查找本次申请的保单批改信息
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		aLPEdorItemSchema.setEdorNo(aLPAppntSchema.getEdorNo());
		aLPEdorItemSchema.setEdorType(aLPAppntSchema.getEdorType());
		aLPEdorItemSchema.setContNo(aLPAppntSchema.getContNo());
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);
		sql = "select * from LPEdorItem where EdorNo='"
				+ "?EdorNo4?" + "' and ContNo='"
				+ "?ContNo4?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(sql);
		sqlbv4.put("EdorNo4", aLPEdorItemSchema.getEdorNo());
		sqlbv4.put("ContNo4", aLPEdorItemSchema.getContNo());
		logger.debug(sql);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv4);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPAppntDB tLPAppntDB = new LPAppntDB();

			tLPAppntSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPAppntSchema.setContNo(tLPEdorItemSchema.getContNo());
			tLPAppntSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());
			tLPAppntSchema.setAppntNo(aLPAppntSchema.getAppntNo());

			tLPAppntDB.setSchema(tLPAppntSchema);
			if (!tLPAppntDB.getInfo()) {
				continue;
			} else {
				tLPAppntDB.setEdorNo(aLPAppntSchema.getEdorNo());
				tLPAppntDB.setEdorType(aLPAppntSchema.getEdorType());
				this.setSchema(tLPAppntDB.getSchema());
				return true;
			}
		}

		// 查找已经申请确认的保单批改信息（没有保全确认）

		tLPEdorItemSet.clear();
		m = 0;
		n = 0;

		sql = "select * from LPEdorItem where EdorState='2' and ContNo='"
				+ "?ContNo5?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(sql);
		sqlbv5.put("ContNo5", aLPEdorItemSchema.getContNo());
		logger.debug(sql);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv5);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPAppntDB tLPAppntDB = new LPAppntDB();

			tLPAppntSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPAppntSchema.setContNo(tLPEdorItemSchema.getContNo());
			tLPAppntSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());
			tLPAppntSchema.setAppntNo(aLPAppntSchema.getAppntNo());
			tLPAppntDB.setSchema(tLPAppntSchema);
			if (!tLPAppntDB.getInfo()) {
				continue;
			} else {
				tLPAppntDB.setEdorNo(aLPAppntSchema.getEdorNo());
				tLPAppntDB.setEdorType(aLPAppntSchema.getEdorType());
				this.setSchema(tLPAppntDB.getSchema());
				return true;
			}
		}

		n = 0;

		// 如果是第一次申请,得到承保保单的客户信息
		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(aLPEdorItemSchema.getContNo());

		tLCAppntSet = tLCAppntDB.query();
		n = tLCAppntSet.size();
		logger.debug("------n:" + n);
		for (int i = 1; i <= n; i++) {
			tLCAppntSchema = tLCAppntSet.get(i);
			logger.debug("ContNo:" + aLPAppntSchema.getContNo());
			logger.debug("AppntNo:" + aLPAppntSchema.getAppntNo());
			if (tLCAppntSchema.getContNo().equals(aLPAppntSchema.getContNo())
					&& tLCAppntSchema.getAppntNo().equals(
							aLPAppntSchema.getAppntNo())) {
				// 转换Schema
				Reflections tReflections = new Reflections();
				tReflections.transFields(tLPAppntSchema, tLCAppntSchema);

				tLPAppntSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
				tLPAppntSchema.setEdorType(aLPEdorItemSchema.getEdorType());
				this.setSchema(tLPAppntSchema);
				return true;
			}
		}
		return false;
	}

	/**
	 * 查询所有的客户信息
	 * 
	 * @param aLPEdorItemSchema
	 * @return
	 */
	public LPAppntSet queryAllLPAppnt(LPEdorItemSchema aLPEdorItemSchema) {
		LCAppntSet tLCAppntSet = new LCAppntSet();
		Reflections tReflections = new Reflections();
		LPAppntSchema tLPAppntSchema = new LPAppntSchema();
		LPAppntSet tLPAppntSet = new LPAppntSet();

		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(aLPEdorItemSchema.getContNo());
		tLCAppntSet = tLCAppntDB.query();
		for (int i = 1; i <= tLCAppntSet.size(); i++) {
			tReflections.transFields(tLPAppntSchema, tLCAppntSet.get(i));
			tLPAppntSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPAppntSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			if (!this.queryLPAppnt(tLPAppntSchema)) {
				return tLPAppntSet;
			}
			tLPAppntSet.add(this.getSchema());
		}
		return tLPAppntSet;
	}

	// 查询上次保全投保人资料信息
	public boolean queryLastLPAppnt(LPEdorItemSchema aLPEdorItemSchema,
			LPAppntSchema aLPAppntSchema) {
		LPAppntSchema tLPAppntSchema = new LPAppntSchema();
		LPAppntSet aLPAppntSet = new LPAppntSet();

		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		LCAppntSet tLCAppntSet = new LCAppntSet();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

		String sql;
		int m, n;
		m = 0;
		n = 0;

		// 查找最近申请的保单批改信息
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);

		// delete EdorValiDate by Minim at 2003-12-17
		sql = "select * from LPEdorItem where ContNo='"
				+ "?ContNo6?" + "' and EdorNo='"
				+ "?ContNo7?"
				+ "' and edorstate <>'0' and MakeDate<='"
				+ "?ContNo8?" + "' and MakeTime<'"
				+ "?ContNo9?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(sql);
		sqlbv6.put("ContNo6", aLPEdorItemSchema.getContNo());
		sqlbv6.put("ContNo7", aLPEdorItemSchema.getEdorNo());
		sqlbv6.put("ContNo8", aLPEdorItemSchema.getMakeDate());
		sqlbv6.put("ContNo9", aLPEdorItemSchema.getMakeTime());
		logger.debug(sql);

		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv6);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPAppntDB tLPAppntDB = new LPAppntDB();

			tLPAppntSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPAppntSchema.setContNo(tLPEdorItemSchema.getContNo());
			tLPAppntSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());
			tLPAppntSchema.setAppntNo(aLPAppntSchema.getAppntNo());

			tLPAppntDB.setSchema(tLPAppntSchema);
			if (!tLPAppntDB.getInfo()) {
				continue;
			} else {
				tLPAppntDB.setEdorNo(aLPAppntSchema.getEdorNo());
				tLPAppntDB.setEdorType(aLPAppntSchema.getEdorType());
				this.setSchema(tLPAppntDB.getSchema());
				return true;
			}
		}

		// 如果是第一次申请,得到承保保单的客户信息
		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(aLPEdorItemSchema.getContNo());

		tLCAppntSet = tLCAppntDB.query();
		n = tLCAppntSet.size();
		logger.debug("------n:" + n);
		for (int i = 1; i <= n; i++) {
			tLCAppntSchema = tLCAppntSet.get(i);
			logger.debug("ContNo:" + aLPAppntSchema.getContNo());
			if (tLCAppntSchema.getContNo().equals(aLPAppntSchema.getContNo())
					&& tLCAppntSchema.getAppntNo().equals(
							aLPAppntSchema.getAppntNo())) {
				// 转换Schema
				Reflections tReflections = new Reflections();
				tReflections.transFields(tLPAppntSchema, tLCAppntSchema);

				tLPAppntSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
				tLPAppntSchema.setEdorType(aLPEdorItemSchema.getEdorType());
				this.setSchema(tLPAppntSchema);
				return true;
			}
		}
		return false;
	}
}

/*
 * <p>ClassName: LPInsureAccBL </p>
 * <p>Description: LPInsuredAccBLSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全
 * @CreateDate：2002-07-25
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPInsureAccDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsureAccSchema;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPInsureAccSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;

public class LPInsureAccBL extends LPInsureAccSchema {
private static Logger logger = Logger.getLogger(LPInsureAccBL.class);
	// @Constructor
	public CErrors mErrors = new CErrors(); // 错误信息

	public LPInsureAccBL() {
	}

	public void setUpdateFields() {
		this.setModifyDate(PubFun.getCurrentDate());
		this.setModifyTime(PubFun.getCurrentTime());
	}

	/**
	 * 查询所有的客户信息
	 * 
	 * @param aLPEdorItemSchema
	 * @return
	 */
	public LPInsureAccSet queryAllLPInsureAcc(LPEdorItemSchema aLPEdorItemSchema) {
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
		Reflections tReflections = new Reflections();
		LPInsureAccSchema tLPInsureAccSchema = new LPInsureAccSchema();
		LPInsureAccSet tLPInsureAccSet = new LPInsureAccSet();

		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		tLCInsureAccDB.setPolNo(aLPEdorItemSchema.getPolNo());
		tLCInsureAccSet = tLCInsureAccDB.query();
		for (int i = 1; i <= tLCInsureAccSet.size(); i++) {
			tReflections
					.transFields(tLPInsureAccSchema, tLCInsureAccSet.get(i));
			tLPInsureAccSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPInsureAccSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			if (!this.queryLPInsureAcc(tLPInsureAccSchema)) {
				return tLPInsureAccSet;
			}
			tLPInsureAccSet.add(this.getSchema());
		}
		return tLPInsureAccSet;
	}

	// 查询帐户变动信息
	public boolean queryLPInsureAcc(LPInsureAccSchema aLPInsureAccSchema) {
		LPInsureAccSchema tLPInsureAccSchema = new LPInsureAccSchema();
		LPInsureAccSet aLPInsureAccSet = new LPInsureAccSet();

		LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSchema aLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

		String sql;
		int m, n;
		m = 0;
		n = 0;

		// 查找本次申请的保单批改信息

		aLPEdorItemSchema.setEdorNo(aLPInsureAccSchema.getEdorNo());
		aLPEdorItemSchema.setEdorType(aLPInsureAccSchema.getEdorType());
		aLPEdorItemSchema.setPolNo(aLPInsureAccSchema.getPolNo());

		sql = "select EdorNo,PolNo,EdorType,EdorValiDate,MakeTime from LPEdorItem where EdorNo='"
				+ "?polno33?"
				+ "' and PolNo='"
				+ "?polno44?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("polno33", aLPEdorItemSchema.getEdorNo());
		sqlbv1.put("polno44", aLPEdorItemSchema.getPolNo());
		logger.debug(sql);
		LPEdorItemDB iLPEdorItemDB = new LPEdorItemDB();
		iLPEdorItemDB.setSchema(aLPEdorItemSchema);
		if (iLPEdorItemDB.getInfo()) {
			// delete EdorValiDate by Minim at 2003-12-17
			sql = "select EdorNo,PolNo,EdorType,EdorValiDate,MakeTime from LPEdorItem where EdorNo='"
					+ "?EdorNo1?"
					+ "' and PolNo='"
					+ "?EdorNo2?"
					+ "' and MakeDate<='"
					+ "?EdorNo3?"
					+ "' and MakeTime<='"
					+ "?EdorNo4?"
					+ "' order by MakeDate desc,MakeTime desc";
			sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(sql);
			sqlbv1.put("EdorNo1", aLPEdorItemSchema.getEdorNo());
			sqlbv1.put("EdorNo2", aLPEdorItemSchema.getPolNo());
			sqlbv1.put("EdorNo3", aLPEdorItemSchema.getMakeDate());
			sqlbv1.put("EdorNo4", aLPEdorItemSchema.getMakeTime());
		}
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv1);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPInsureAccDB tLPInsureAccDB = new LPInsureAccDB();

			tLPInsureAccSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPInsureAccSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPInsureAccSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());
			tLPInsureAccSchema.setInsuAccNo(aLPInsureAccSchema.getInsuAccNo());
			// tLPInsureAccSchema.setOtherNo(aLPInsureAccSchema.getOtherNo());

			tLPInsureAccDB.setSchema(tLPInsureAccSchema);
			if (!tLPInsureAccDB.getInfo()) {
				continue;
			} else {
				tLPInsureAccDB.setEdorNo(aLPInsureAccSchema.getEdorNo());
				tLPInsureAccDB.setEdorType(aLPInsureAccSchema.getEdorType());
				this.setSchema(tLPInsureAccDB.getSchema());
				return true;
			}
		}

		// 查找已经申请确认的保单批改信息（没有保全确认）

		tLPEdorItemSet.clear();
		m = 0;
		n = 0;

		sql = "select EdorNo,PolNo,EdorType,EdorValiDate,MakeTime from LPEdorItem where EdorState='2' and PolNo='"
				+ "?EdorNo6?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("EdorNo6", aLPEdorItemSchema.getPolNo());
		logger.debug(sql);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv3);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPInsureAccDB tLPInsureAccDB = new LPInsureAccDB();

			tLPInsureAccSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPInsureAccSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPInsureAccSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());
			tLPInsureAccSchema.setInsuAccNo(aLPInsureAccSchema.getInsuAccNo());
			// tLPInsureAccSchema.setOtherNo(aLPInsureAccSchema.getOtherNo());

			tLPInsureAccDB.setSchema(tLPInsureAccSchema);
			if (!tLPInsureAccDB.getInfo()) {
				continue;
			} else {
				tLPInsureAccDB.setEdorNo(aLPInsureAccSchema.getEdorNo());
				tLPInsureAccDB.setEdorType(aLPInsureAccSchema.getEdorType());
				this.setSchema(tLPInsureAccDB.getSchema());
				return true;
			}
		}

		n = 0;

		// 如果是第一次申请,得到承保保单的客户信息
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		tLCInsureAccDB.setPolNo(aLPEdorItemSchema.getPolNo());

		tLCInsureAccSet = tLCInsureAccDB.query();
		n = tLCInsureAccSet.size();
		logger.debug("------n:" + n);
		for (int i = 1; i <= n; i++) {
			tLCInsureAccSchema = tLCInsureAccSet.get(i);
			if (tLCInsureAccSchema.getPolNo().equals(
					aLPInsureAccSchema.getPolNo())
					&& tLCInsureAccSchema.getInsuAccNo().equals(
							aLPInsureAccSchema.getInsuAccNo())
			// &&tLCInsureAccSchema.getOtherNo().equals(aLPInsureAccSchema.getOtherNo())
			) {
				// 转换Schema
				Reflections tReflections = new Reflections();
				tReflections
						.transFields(tLPInsureAccSchema, tLCInsureAccSchema);

				tLPInsureAccSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
				tLPInsureAccSchema.setEdorType(aLPEdorItemSchema.getEdorType());
				this.setSchema(tLPInsureAccSchema);
				return true;
			}
		}
		return false;
	}

	// 查询上次保全投保人资料信息
	public boolean queryLastLPInsureAcc(LPEdorItemSchema aLPEdorItemSchema,
			LPInsureAccSchema aLPInsureAccSchema) {
		LPInsureAccSchema tLPInsureAccSchema = new LPInsureAccSchema();
		LPInsureAccSet aLPInsureAccSet = new LPInsureAccSet();

		LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
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
		sql = "select EdorNo,PolNo,EdorType,EdorValiDate,MakeTime from LPEdorItem where PolNo='"
				+ "?EdorNo7?"
				+ "' and edorstate <>'0' and MakeDate<='"
				+ "?EdorNo8?"
				+ "' and MakeTime<'"
				+ "?EdorNo9?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(sql);
		sqlbv4.put("EdorNo7", aLPEdorItemSchema.getPolNo());
		sqlbv4.put("EdorNo8", aLPEdorItemSchema.getMakeDate());
		sqlbv4.put("EdorNo9", aLPEdorItemSchema.getMakeTime());

		logger.debug(sql);

		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv4);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPInsureAccDB tLPInsureAccDB = new LPInsureAccDB();

			tLPInsureAccSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPInsureAccSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPInsureAccSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());
			tLPInsureAccSchema.setInsuAccNo(aLPInsureAccSchema.getInsuAccNo());
			// tLPInsureAccSchema.setOtherNo(aLPInsureAccSchema.getOtherNo());

			tLPInsureAccDB.setSchema(tLPInsureAccSchema);
			if (!tLPInsureAccDB.getInfo()) {
				continue;
			} else {
				tLPInsureAccDB.setEdorNo(aLPInsureAccSchema.getEdorNo());
				tLPInsureAccDB.setEdorType(aLPInsureAccSchema.getEdorType());
				this.setSchema(tLPInsureAccDB.getSchema());
				return true;
			}
		}

		// 如果是第一次申请,得到承保保单的客户信息
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		tLCInsureAccDB.setPolNo(aLPEdorItemSchema.getPolNo());

		tLCInsureAccSet = tLCInsureAccDB.query();
		n = tLCInsureAccSet.size();
		logger.debug("------n:" + n);
		for (int i = 1; i <= n; i++) {
			tLCInsureAccSchema = tLCInsureAccSet.get(i);
			logger.debug("PolNo:" + aLPInsureAccSchema.getPolNo());
			if (tLCInsureAccSchema.getPolNo().equals(
					aLPInsureAccSchema.getPolNo())
					&& tLCInsureAccSchema.getInsuAccNo().equals(
							aLPInsureAccSchema.getInsuAccNo())
			// &&tLCInsureAccSchema.getOtherNo().equals(aLPInsureAccSchema.getOtherNo())
			) {
				// 转换Schema
				Reflections tReflections = new Reflections();
				tReflections
						.transFields(tLPInsureAccSchema, tLCInsureAccSchema);

				tLPInsureAccSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
				tLPInsureAccSchema.setEdorType(aLPEdorItemSchema.getEdorType());
				this.setSchema(tLPInsureAccSchema);
				return true;
			}
		}
		return false;
	}
}

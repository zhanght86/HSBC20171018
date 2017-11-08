/*
 * <p>ClassName: LPInsureAccClassBL </p>
 * <p>Description: LPInsuredAccBLSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全
 * @CreateDate：2002-07-25
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPInsureAccClassDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPInsureAccClassSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;

public class LPInsureAccClassBL extends LPInsureAccClassSchema {
private static Logger logger = Logger.getLogger(LPInsureAccClassBL.class);
	// @Constructor
	public CErrors mErrors = new CErrors(); // 错误信息

	public LPInsureAccClassBL() {
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
	public LPInsureAccClassSet queryAllLPInsureAccClass(
			LPEdorItemSchema aLPEdorItemSchema) {
		LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
		Reflections tReflections = new Reflections();
		LPInsureAccClassSchema tLPInsureAccClassSchema = new LPInsureAccClassSchema();
		LPInsureAccClassSet tLPInsureAccClassSet = new LPInsureAccClassSet();

		LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
		tLCInsureAccClassDB.setPolNo(aLPEdorItemSchema.getPolNo());
		tLCInsureAccClassSet = tLCInsureAccClassDB.query();
		for (int i = 1; i <= tLCInsureAccClassSet.size(); i++) {
			tLPInsureAccClassSchema = new LPInsureAccClassSchema(); 
			tReflections.transFields(tLPInsureAccClassSchema,
					tLCInsureAccClassSet.get(i));
			tLPInsureAccClassSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPInsureAccClassSchema
					.setEdorType(aLPEdorItemSchema.getEdorType());

			tLPInsureAccClassSet.add(tLPInsureAccClassSchema);
		}
		return tLPInsureAccClassSet;
	}

	// 查询帐户变动信息
	public boolean queryLPInsureAccClass(
			LPInsureAccClassSchema aLPInsureAccClassSchema) {
		LPInsureAccClassSchema tLPInsureAccClassSchema = new LPInsureAccClassSchema();
		LPInsureAccClassSet aLPInsureAccClassSet = new LPInsureAccClassSet();

		LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();
		LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSchema aLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

		String sql;
		int m, n;
		m = 0;
		n = 0;

		// 查找本次申请的保单批改信息

		aLPEdorItemSchema.setEdorNo(aLPInsureAccClassSchema.getEdorNo());
		aLPEdorItemSchema.setEdorType(aLPInsureAccClassSchema.getEdorType());
		aLPEdorItemSchema.setPolNo(aLPInsureAccClassSchema.getPolNo());

		sql = "select EdorNo,PolNo,EdorType,EdorValiDate,MakeTime from LPEdorItem where EdorNo='"
				+ "?polno55?"
				+ "' and PolNo='"
				+ "？polno66？"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("polno55", aLPEdorItemSchema.getEdorNo());
		sqlbv1.put("polno66", aLPEdorItemSchema.getPolNo());
		logger.debug(sql);
		LPEdorItemDB iLPEdorItemDB = new LPEdorItemDB();
		iLPEdorItemDB.setSchema(aLPEdorItemSchema);
		if (iLPEdorItemDB.getInfo()) {
			// delete EdorValiDate by Minim at 2003-12-17
			sql = "select EdorNo,PolNo,EdorType,EdorValiDate,MakeTime from LPEdorItem where EdorNo='"
					+ "?polno51?"
					+ "' and PolNo='"
					+ "?polno61?"
					+ "' and MakeDate<='"
					+ "?polno62?"
					+ "' and MakeTime<='"
					+ "?polno63?"
					+ "' order by MakeDate desc,MakeTime desc";
			sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(sql);
			sqlbv1.put("polno51", aLPEdorItemSchema.getEdorNo());
			sqlbv1.put("polno61", aLPEdorItemSchema.getPolNo());
			sqlbv1.put("polno62", aLPEdorItemSchema.getMakeDate());
			sqlbv1.put("polno63", aLPEdorItemSchema.getMakeTime());
		}
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv1);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPInsureAccClassDB tLPInsureAccClassDB = new LPInsureAccClassDB();

			tLPInsureAccClassSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPInsureAccClassSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPInsureAccClassSchema
					.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());
			tLPInsureAccClassSchema.setInsuAccNo(aLPInsureAccClassSchema
					.getInsuAccNo());
			tLPInsureAccClassSchema.setOtherNo(aLPInsureAccClassSchema
					.getOtherNo());

			tLPInsureAccClassDB.setSchema(tLPInsureAccClassSchema);
			if (!tLPInsureAccClassDB.getInfo()) {
				continue;
			} else {
				tLPInsureAccClassDB.setEdorNo(aLPInsureAccClassSchema
						.getEdorNo());
				tLPInsureAccClassDB.setEdorType(aLPInsureAccClassSchema
						.getEdorType());
				this.setSchema(tLPInsureAccClassDB.getSchema());
				return true;
			}
		}

		// 查找已经申请确认的保单批改信息（没有保全确认）

		tLPEdorItemSet.clear();
		m = 0;
		n = 0;

		sql = "select EdorNo,PolNo,EdorType,EdorValiDate,MakeTime from LPEdorItem where EdorState='2' and PolNo='"
				+ "?polno64?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("polno64", aLPEdorItemSchema.getPolNo());
		logger.debug(sql);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv3);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPInsureAccClassDB tLPInsureAccClassDB = new LPInsureAccClassDB();

			tLPInsureAccClassSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPInsureAccClassSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPInsureAccClassSchema
					.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());
			tLPInsureAccClassSchema.setInsuAccNo(aLPInsureAccClassSchema
					.getInsuAccNo());
			tLPInsureAccClassSchema.setOtherNo(aLPInsureAccClassSchema
					.getOtherNo());

			tLPInsureAccClassDB.setSchema(tLPInsureAccClassSchema);
			if (!tLPInsureAccClassDB.getInfo()) {
				continue;
			} else {
				tLPInsureAccClassDB.setEdorNo(aLPInsureAccClassSchema
						.getEdorNo());
				tLPInsureAccClassDB.setEdorType(aLPInsureAccClassSchema
						.getEdorType());
				this.setSchema(tLPInsureAccClassDB.getSchema());
				return true;
			}
		}

		n = 0;

		// 如果是第一次申请,得到承保保单的客户信息
		LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
		tLCInsureAccClassDB.setPolNo(aLPEdorItemSchema.getPolNo());

		tLCInsureAccClassSet = tLCInsureAccClassDB.query();
		n = tLCInsureAccClassSet.size();
		logger.debug("------n:" + n);
		for (int i = 1; i <= n; i++) {
			tLCInsureAccClassSchema = tLCInsureAccClassSet.get(i);
			if (tLCInsureAccClassSchema.getPolNo().equals(
					aLPInsureAccClassSchema.getPolNo())
					&& tLCInsureAccClassSchema.getInsuAccNo().equals(
							aLPInsureAccClassSchema.getInsuAccNo())
					&& tLCInsureAccClassSchema.getOtherNo().equals(
							aLPInsureAccClassSchema.getOtherNo())) {
				// 转换Schema
				Reflections tReflections = new Reflections();
				tReflections.transFields(tLPInsureAccClassSchema,
						tLCInsureAccClassSchema);

				tLPInsureAccClassSchema
						.setEdorNo(aLPEdorItemSchema.getEdorNo());
				tLPInsureAccClassSchema.setEdorType(aLPEdorItemSchema
						.getEdorType());
				this.setSchema(tLPInsureAccClassSchema);
				return true;
			}
		}
		return false;
	}

	// 查询上次保全投保人资料信息
	public boolean queryLastLPInsureAccClass(
			LPEdorItemSchema aLPEdorItemSchema,
			LPInsureAccClassSchema aLPInsureAccClassSchema) {
		LPInsureAccClassSchema tLPInsureAccClassSchema = new LPInsureAccClassSchema();
		LPInsureAccClassSet aLPInsureAccClassSet = new LPInsureAccClassSet();

		LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();
		LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
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
				+ "?polno65?"
				+ "' and edorstate <>'0' and MakeDate<='"
				+ "?polno66?"
				+ "' and MakeTime<'"
				+ "?polno67?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(sql);
		sqlbv4.put("polno65", aLPEdorItemSchema.getPolNo());
		sqlbv4.put("polno66", aLPEdorItemSchema.getMakeDate());
		sqlbv4.put("polno67", aLPEdorItemSchema.getMakeTime());

		logger.debug(sql);

		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv4);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPInsureAccClassDB tLPInsureAccClassDB = new LPInsureAccClassDB();

			tLPInsureAccClassSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPInsureAccClassSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPInsureAccClassSchema
					.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());
			tLPInsureAccClassSchema.setInsuAccNo(aLPInsureAccClassSchema
					.getInsuAccNo());
			tLPInsureAccClassSchema.setOtherNo(aLPInsureAccClassSchema
					.getOtherNo());

			tLPInsureAccClassDB.setSchema(tLPInsureAccClassSchema);
			if (!tLPInsureAccClassDB.getInfo()) {
				continue;
			} else {
				tLPInsureAccClassDB.setEdorNo(aLPInsureAccClassSchema
						.getEdorNo());
				tLPInsureAccClassDB.setEdorType(aLPInsureAccClassSchema
						.getEdorType());
				this.setSchema(tLPInsureAccClassDB.getSchema());
				return true;
			}
		}

		// 如果是第一次申请,得到承保保单的客户信息
		LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
		tLCInsureAccClassDB.setPolNo(aLPEdorItemSchema.getPolNo());

		tLCInsureAccClassSet = tLCInsureAccClassDB.query();
		n = tLCInsureAccClassSet.size();
		logger.debug("------n:" + n);
		for (int i = 1; i <= n; i++) {
			tLCInsureAccClassSchema = tLCInsureAccClassSet.get(i);
			logger.debug("PolNo:" + aLPInsureAccClassSchema.getPolNo());
			if (tLCInsureAccClassSchema.getPolNo().equals(
					aLPInsureAccClassSchema.getPolNo())
					&& tLCInsureAccClassSchema.getInsuAccNo().equals(
							aLPInsureAccClassSchema.getInsuAccNo())
					&& tLCInsureAccClassSchema.getOtherNo().equals(
							aLPInsureAccClassSchema.getOtherNo())) {
				// 转换Schema
				Reflections tReflections = new Reflections();
				tReflections.transFields(tLPInsureAccClassSchema,
						tLCInsureAccClassSchema);

				tLPInsureAccClassSchema
						.setEdorNo(aLPEdorItemSchema.getEdorNo());
				tLPInsureAccClassSchema.setEdorType(aLPEdorItemSchema
						.getEdorType());
				this.setSchema(tLPInsureAccClassSchema);
				return true;
			}
		}
		return false;
	}
}

/*
 * <p>ClassName: LPBnfBL </p>
 * <p>Description: LPBnfBLSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全
 * @CreateDate：2002-07-25
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LPBnfDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LPBnfSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;

public class LPBnfBL extends LPBnfSchema {
private static Logger logger = Logger.getLogger(LPBnfBL.class);
	// @Constructor
	public CErrors mErrors = new CErrors(); // 错误信息

	public LPBnfBL() {
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
	public LPBnfSet queryAllLPBnf(LPEdorItemSchema aLPEdorItemSchema) {
		LCBnfSet tLCBnfSet = new LCBnfSet();
		Reflections tReflections = new Reflections();
		LPBnfSchema tLPBnfSchema = new LPBnfSchema();
		LPBnfSet tLPBnfSet = new LPBnfSet();

		LCBnfDB tLCBnfDB = new LCBnfDB();
		// tLCBnfDB.setPolNo(aLPEdorItemSchema.getPolNo());
		tLCBnfSet = tLCBnfDB.query();
		for (int i = 1; i <= tLCBnfSet.size(); i++) {
			tReflections.transFields(tLPBnfSchema, tLCBnfSet.get(i));
			tLPBnfSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			// tLPBnfSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			if (!this.queryLPBnf(tLPBnfSchema)) {
				return tLPBnfSet;
			}
			tLPBnfSet.add(this.getSchema());
		}
		return tLPBnfSet;
	}

	// 查询被保险人变动信息
	public boolean queryLPBnf(LPBnfSchema aLPBnfSchema) {
		LPBnfSchema tLPBnfSchema = new LPBnfSchema();
		LPBnfSet aLPBnfSet = new LPBnfSet();

		LCBnfSchema tLCBnfSchema = new LCBnfSchema();
		LCBnfSet tLCBnfSet = new LCBnfSet();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSchema aLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

		String sql;
		int m, n;
		m = 0;
		n = 0;

		// 查找本次申请的保单批改信息
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		aLPEdorItemSchema.setEdorNo(aLPBnfSchema.getEdorNo());
		aLPEdorItemSchema.setEdorType(aLPBnfSchema.getEdorType());
		aLPEdorItemSchema.setPolNo(aLPBnfSchema.getPolNo());
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);
		sql = "select EdorNo,PolNo,EdorType,EdorValiDate,MakeTime from LPEdorItem where EdorNo='"
				+ "?EdorNo?"
				+ "' and PolNo='"
				+ "?PolNo?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("EdorNo", aLPEdorItemSchema.getEdorNo());
		sqlbv1.put("PolNo", aLPEdorItemSchema.getPolNo());
		logger.debug(sqlbv1);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv1);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPBnfDB tLPBnfDB = new LPBnfDB();

			tLPBnfSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPBnfSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPBnfSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());
			tLPBnfSchema.setBnfType(aLPBnfSchema.getBnfType());
			tLPBnfSchema.setBnfNo(aLPBnfSchema.getBnfNo());

			tLPBnfDB.setSchema(tLPBnfSchema);
			if (!tLPBnfDB.getInfo()) {
				continue;
			} else {
				tLPBnfDB.setEdorNo(aLPBnfSchema.getEdorNo());
				tLPBnfDB.setEdorType(aLPBnfSchema.getEdorType());
				this.setSchema(tLPBnfDB.getSchema());
				return true;
			}
		}

		// 查找已经申请确认的保单批改信息（没有保全确认）

		tLPEdorItemSet.clear();
		m = 0;
		n = 0;

		sql = "select EdorNo,PolNo,EdorType,EdorValiDate,MakeTime from LPEdorItem where EdorState='2' and PolNo='"
				+ "?EdorNo?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sql);
		sqlbv2.put("EdorNo", aLPEdorItemSchema.getPolNo());
		logger.debug(sqlbv2);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv2);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPBnfDB tLPBnfDB = new LPBnfDB();

			tLPBnfSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPBnfSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPBnfSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			// logger.debug(tLPEdorItemSchema.getEdorType());
			tLPBnfSchema.setBnfType(aLPBnfSchema.getBnfType());
			tLPBnfSchema.setBnfNo(aLPBnfSchema.getBnfNo());

			tLPBnfDB.setSchema(tLPBnfSchema);
			if (!tLPBnfDB.getInfo()) {
				continue;
			} else {
				tLPBnfDB.setEdorNo(aLPBnfSchema.getEdorNo());
				tLPBnfDB.setEdorType(aLPBnfSchema.getEdorType());
				this.setSchema(tLPBnfDB.getSchema());
				return true;
			}
		}

		n = 0;

		// 如果是第一次申请,得到承保保单的客户信息
		LCBnfDB tLCBnfDB = new LCBnfDB();
		tLCBnfDB.setPolNo(aLPEdorItemSchema.getPolNo());

		tLCBnfSet = tLCBnfDB.query();
		n = tLCBnfSet.size();
		for (int i = 1; i <= n; i++) {
			tLCBnfSchema = tLCBnfSet.get(i);
			logger.debug("PolNo:" + aLPBnfSchema.getPolNo());
			logger.debug("CustomerNo:" + aLPBnfSchema.getCustomerNo());
			if (tLCBnfSchema.getPolNo().equals(aLPBnfSchema.getPolNo())
					&& tLCBnfSchema.getBnfType().equals(
							aLPBnfSchema.getBnfType())
					&& tLCBnfSchema.getBnfNo() == aLPBnfSchema.getBnfNo()) {
				// 转换Schema
				Reflections tReflections = new Reflections();
				tReflections.transFields(tLPBnfSchema, tLCBnfSchema);

				tLPBnfSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
				// tLPBnfSchema.setEdorType(aLPEdorItemSchema.getEdorType());
				this.setSchema(tLPBnfSchema);
				return true;
			}
		}
		return false;
	}

	// 查询被保险人变动信息
	public LPBnfSet queryLPBnf(LPEdorItemSchema aLPEdorItemSchema) {
		LPBnfSchema aLPBnfSchema = new LPBnfSchema();
		LPBnfSchema tLPBnfSchema = new LPBnfSchema();
		LPBnfSet aLPBnfSet = new LPBnfSet();

		LCBnfSchema tLCBnfSchema = new LCBnfSchema();
		LCBnfSet tLCBnfSet = new LCBnfSet();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		Reflections tReflections = new Reflections();

		String sql;
		int m, n;
		m = 0;
		n = 0;

		// 查找本次申请的保单批改信息

		sql = "select EdorNo,PolNo,EdorType,EdorValiDate,MakeTime from LPEdorItem where EdorNo='"
				+ "?EdorNo1?"
				+ "' and PolNo='"
				+ "?PolNo1?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("EdorNo1", aLPEdorItemSchema.getEdorNo());
		sqlbv3.put("PolNo1", aLPEdorItemSchema.getPolNo());
		logger.debug(sql);

		LPEdorItemDB iLPEdorItemDB = new LPEdorItemDB();
		iLPEdorItemDB.setSchema(aLPEdorItemSchema);
		if (iLPEdorItemDB.getInfo()) {
			// delete EdorValiDate by Minim at 2003-12-17
			sql = "select EdorNo,PolNo,EdorType,EdorValiDate,MakeTime from LPEdorItem where EdorNo='"
					+ "?EdorNo2?"
					+ "' and PolNo='"
					+ "?PolNo2?"
					+ "' and MakeDate<='"
					+ "?MakeDate?"
					+ "' and MakeTime<='"
					+ "?MakeTime?"
					+ "' order by MakeDate desc,MakeTime desc";
			sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(sql);
			sqlbv3.put("EdorNo2", aLPEdorItemSchema.getEdorNo());
			sqlbv3.put("PolNo2", aLPEdorItemSchema.getPolNo());
			sqlbv3.put("MakeDate", iLPEdorItemDB.getMakeDate());
			sqlbv3.put("MakeTime", iLPEdorItemDB.getMakeTime());
		}

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv3);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPBnfDB tLPBnfDB = new LPBnfDB();

			tLPBnfSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPBnfSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPBnfSchema.setEdorType(tLPEdorItemSchema.getEdorType());

			tLPBnfDB.setSchema(tLPBnfSchema);
			aLPBnfSet = tLPBnfDB.query();
			n = aLPBnfSet.size();
			if (n == 0) {
				continue;
			}
			return aLPBnfSet;
		}

		// 查找已经申请确认的保单批改信息（没有保全确认）

		tLPEdorItemSet.clear();
		m = 0;
		n = 0;

		sql = "select EdorNo,PolNo,EdorType,EdorValiDate,MakeTime from LPEdorItem where EdorState='2' and PolNo='"
				+ "?PolNo3?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(sql);
		sqlbv5.put("PolNo3", aLPEdorItemSchema.getPolNo());
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv5);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {

			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPBnfDB tLPBnfDB = new LPBnfDB();

			tLPBnfSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPBnfSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPBnfSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			tLPBnfDB.setSchema(tLPBnfSchema);

			aLPBnfSet.clear();
			aLPBnfSet = tLPBnfDB.query();
			n = aLPBnfSet.size();
			if (n == 0) {
				continue;
			}
			return aLPBnfSet;
		}

		n = 0;

		// 如果是第一次申请,得到承保保单的客户信息
		LCBnfDB tLCBnfDB = new LCBnfDB();

		// Modify by Minim at 2003-9-25 for BC保全项目中的批单显示问题，因为比较新旧信息时要用到bnfNo字段
		// tLCBnfDB.setPolNo(aLPEdorItemSchema.getPolNo());
		// tLCBnfSet = tLCBnfDB.query();
		String strSql = "select * from lcbnf where polno='?polno?' order by bnfno";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("polno", aLPEdorItemSchema.getPolNo());
		tLCBnfSet = tLCBnfDB.executeQuery(sqlbv);

		n = tLCBnfSet.size();
		logger.debug("------n:" + n);
		for (int i = 1; i <= n; i++) {
			aLPBnfSchema = new LPBnfSchema();
			tLCBnfSchema = tLCBnfSet.get(i);
			// 转换Schema
			tReflections.transFields(aLPBnfSchema, tLCBnfSchema);

			aLPBnfSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			aLPBnfSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			aLPBnfSet.add(aLPBnfSchema);
			logger.debug("aLPBnfSchema :" + aLPBnfSchema.getName());
		}
		return aLPBnfSet;
	}

	// 查询上次保全受益人信息
	public LPBnfSet queryLastLPBnf(LPEdorItemSchema aLPEdorItemSchema) {
		LPBnfSchema aLPBnfSchema = new LPBnfSchema();
		LPBnfSchema tLPBnfSchema = new LPBnfSchema();
		LPBnfSet aLPBnfSet = new LPBnfSet();

		LCBnfSchema tLCBnfSchema = new LCBnfSchema();
		LCBnfSet tLCBnfSet = new LCBnfSet();

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();

		Reflections tReflections = new Reflections();

		String sql;
		int m, n;
		m = 0;
		n = 0;

		// 查找已经申请确认的保单批改信息（没有保全确认）
		tLPEdorItemSet.clear();
		sql = "select EdorNo,PolNo,EdorType,EdorValiDate,MakeTime from LPEdorItem where EdorState='2' and EdorNO<>'"
				+ "?EdorNO4?"
				+ "'and PolNo='"
				+ "?PolNo4?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(sql);
		sqlbv6.put("EdorNO4", aLPEdorItemSchema.getEdorNo());
		sqlbv6.put("PolNo4", aLPEdorItemSchema.getPolNo());
		logger.debug(sql);

		tLPEdorItemDB.setSchema(aLPEdorItemSchema);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv6);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {

			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPBnfDB tLPBnfDB = new LPBnfDB();

			tLPBnfSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPBnfSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPBnfSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			tLPBnfDB.setSchema(tLPBnfSchema);

			aLPBnfSet.clear();
			aLPBnfSet = tLPBnfDB.query();
			n = aLPBnfSet.size();
			if (n == 0) {
				continue;
			}
			return aLPBnfSet;
		}

		n = 0;

		// 如果是第一次申请,得到承保保单的客户信息
		LCBnfDB tLCBnfDB = new LCBnfDB();

		// Modify by Minim at 2003-9-25 for BC保全项目中的批单显示问题，因为比较新旧信息时要用到bnfNo字段
		// tLCBnfDB.setPolNo(aLPEdorItemSchema.getPolNo());
		// tLCBnfSet = tLCBnfDB.query();
		String strSql = "select * from lcbnf where polno='"
				+ "?polno?" + "' order by bnfno";
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(strSql);
		sqlbv7.put("polno", aLPEdorItemSchema.getPolNo());
		tLCBnfSet = tLCBnfDB.executeQuery(sqlbv7);

		n = tLCBnfSet.size();
		for (int i = 1; i <= n; i++) {
			aLPBnfSchema = new LPBnfSchema();
			tLCBnfSchema = tLCBnfSet.get(i);
			// 转换Schema
			tReflections.transFields(aLPBnfSchema, tLCBnfSchema);

			aLPBnfSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			aLPBnfSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			aLPBnfSet.add(aLPBnfSchema);
		}
		return aLPBnfSet;
	}

	// 查询被保险人当前变动信息,若LPBnf表为空则返回空的 LPBnfSet
	public LPBnfSet queryNewLPBnf(LPEdorItemSchema aLPEdorItemSchema) {
		LPBnfSchema aLPBnfSchema = new LPBnfSchema();
		LPBnfSchema tLPBnfSchema = new LPBnfSchema();
		LPBnfSet aLPBnfSet = new LPBnfSet();

		LCBnfSchema tLCBnfSchema = new LCBnfSchema();
		LCBnfSet tLCBnfSet = new LCBnfSet();

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

		Reflections tReflections = new Reflections();
		int m, n;
		m = 0;
		n = 0;
		String sql = "select EdorNo,PolNo,EdorType,EdorValiDate,MakeTime from LPEdorItem where EdorNo='"
				+ "?EdorNo5?"
				+ "' and PolNo='"
				+ "?PolNo5?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
		sqlbv8.sql(sql);
		sqlbv8.put("EdorNo5", aLPEdorItemSchema.getEdorNo());
		sqlbv8.put("PolNo5", aLPEdorItemSchema.getPolNo());
		logger.debug("SQL" + sql);

		LPEdorItemDB iLPEdorItemDB = new LPEdorItemDB();
		iLPEdorItemDB.setSchema(aLPEdorItemSchema);
		if (iLPEdorItemDB.getInfo()) {
			// delete EdorValiDate by Minim at 2003-12-17
			sql = "select EdorNo,PolNo,EdorType,EdorValiDate,MakeTime from LPEdorItem where EdorNo='"
					+ "?EdorNo7?"
					+ "' and PolNo='"
					+ "?PolNo7?"
					+ "' and MakeDate<='"
					+ "?MakeDate2?"
					+ "' and MakeTime<='"
					+ "?MakeTime2?"
					+ "' order by MakeDate desc,MakeTime desc";
			sqlbv8=new SQLwithBindVariables();
			sqlbv8.sql(sql);
			sqlbv8.put("EdorNo7", aLPEdorItemSchema.getEdorNo());
			sqlbv8.put("PolNo7", aLPEdorItemSchema.getPolNo());
			sqlbv8.put("MakeDate2", iLPEdorItemDB.getMakeDate());
			sqlbv8.put("MakeTime2", iLPEdorItemDB.getMakeTime());
		}

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv8);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPBnfDB tLPBnfDB = new LPBnfDB();

			tLPBnfSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPBnfSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPBnfSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			// logger.debug(tLPEdorItemSchema.getEdorType());

			tLPBnfDB.setSchema(tLPBnfSchema);
			aLPBnfSet = tLPBnfDB.query();
			n = aLPBnfSet.size();
			if (n == 0) {
				continue;
			}
			return aLPBnfSet;
		}
		aLPBnfSet.clear();
		return aLPBnfSet;
	}

	// 查询上次保全受益人信息
	public boolean queryLastLPBnf(LPEdorItemSchema aLPEdorItemSchema,
			LPBnfSchema aLPBnfSchema) {
		LPBnfSchema tLPBnfSchema = new LPBnfSchema();
		LPBnfSet aLPBnfSet = new LPBnfSet();

		LCBnfSchema tLCBnfSchema = new LCBnfSchema();
		LCBnfSet tLCBnfSet = new LCBnfSet();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

		String sql;
		int m, n;
		m = 0;
		n = 0;

		// 查找最近申请的保单批改信息
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);

		sql = "select EdorNo,PolNo,EdorType,EdorValiDate,MakeTime from LPEdorItem where PolNo='"
				+ "?PolNo8?"
				+ "' and edorstate <>'0' and MakeDate<='"
				+ "?MakeDate3?"
				+ "' and MakeTime<'"
				+ "?MakeTime3?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
		sqlbv10.sql(sql);
		sqlbv10.put("PolNo8", aLPEdorItemSchema.getPolNo());
		sqlbv10.put("MakeDate3", aLPEdorItemSchema.getMakeDate());
		sqlbv10.put("MakeTime3", aLPEdorItemSchema.getMakeTime());
		logger.debug(sql);

		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv10);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPBnfDB tLPBnfDB = new LPBnfDB();

			tLPBnfSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPBnfSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPBnfSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			tLPBnfSchema.setBnfType(aLPBnfSchema.getBnfType());
			tLPBnfSchema.setBnfNo(aLPBnfSchema.getBnfNo());

			tLPBnfDB.setSchema(tLPBnfSchema);
			if (!tLPBnfDB.getInfo()) {
				continue;
			} else {
				tLPBnfDB.setEdorNo(aLPBnfSchema.getEdorNo());
				tLPBnfDB.setEdorType(aLPBnfSchema.getEdorType());
				this.setSchema(tLPBnfDB.getSchema());
				return true;
			}
		}

		// 如果是第一次申请,得到承保保单的客户信息
		LCBnfDB tLCBnfDB = new LCBnfDB();
		tLCBnfDB.setPolNo(aLPEdorItemSchema.getPolNo());

		tLCBnfSet = tLCBnfDB.query();
		n = tLCBnfSet.size();
		for (int i = 1; i <= n; i++) {
			tLCBnfSchema = tLCBnfSet.get(i);
			logger.debug("PolNo:" + aLPBnfSchema.getPolNo());
			if (tLCBnfSchema.getPolNo().equals(aLPBnfSchema.getPolNo())
					&& tLCBnfSchema.getBnfType().equals(
							aLPBnfSchema.getBnfType())
					&& tLCBnfSchema.getBnfNo() == aLPBnfSchema.getBnfNo()
			// &&tLCBnfSchema.getBnfGrade().equals(aLPBnfSchema.getBnfGrade())
			// &&tLCBnfSchema.getBnfLot()==aLPBnfSchema.getBnfLot()
			// &&tLCBnfSchema.getName().equals(aLPBnfSchema.getName())
			) {
				// 转换Schema
				Reflections tReflections = new Reflections();
				tReflections.transFields(tLPBnfSchema, tLCBnfSchema);

				tLPBnfSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
				tLPBnfSchema.setEdorType(aLPEdorItemSchema.getEdorType());
				this.setSchema(tLPBnfSchema);
				return true;
			}
		}
		return false;
	}
}

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

import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;

public class LPPremBL extends LPPremSchema {
private static Logger logger = Logger.getLogger(LPPremBL.class);
	// @Constructor
	public CErrors mErrors = new CErrors(); // 错误信息

	public LPPremBL() {
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
	public LPPremSet queryAllLPPrem(LPEdorItemSchema aLPEdorItemSchema) {
		LCPremSet tLCPremSet = new LCPremSet();
		Reflections tReflections = new Reflections();
		LPPremSchema tLPPremSchema = new LPPremSchema();
		LPPremSet tLPPremSet = new LPPremSet();

		LCPremDB tLCPremDB = new LCPremDB();
		tLCPremDB.setPolNo(aLPEdorItemSchema.getPolNo());
		tLCPremSet = tLCPremDB.query();

		for (int i = 1; i <= tLCPremSet.size(); i++) {
			tReflections.transFields(tLPPremSchema, tLCPremSet.get(i));
			tLPPremSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPPremSchema.setEdorType(aLPEdorItemSchema.getEdorType());

			if (!this.queryLPPrem(tLPPremSchema)) {
				return tLPPremSet;
			}

			tLPPremSet.add(this.getSchema());
		}

		return tLPPremSet;
	}

	/**
	 * 查询所有的保全保费项表数据，为保全重算
	 * 
	 * @param aLPEdorItemSchema
	 * @return
	 */
	public LPPremSet queryAllLPPremForReCal(LPEdorItemSchema aLPEdorItemSchema) {
		Reflections tReflections = new Reflections();

		LCPremDB tLCPremDB = new LCPremDB();
		// tLCPremDB.setPolNo(aLPEdorItemSchema.getPolNo());
		String StrIO = "select * from lcprem where polno = '"
				+ "?PolNo2?" + "' and paystartdate <= '"
				+ "?PolNo3?" + "'";
		logger.debug(StrIO);
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(StrIO);
		sqlbv1.put("PolNo2", aLPEdorItemSchema.getPolNo());
		sqlbv1.put("PolNo3", PubFun.getCurrentDate());
		LCPremSet tLCPremSet = tLCPremDB.executeQuery(sqlbv1);

		LPPremSchema tLPPremSchema = new LPPremSchema();
		LPPremSet tLPPremSet = new LPPremSet();

		for (int i = 1; i <= tLCPremSet.size(); i++) {
			tReflections.transFields(tLPPremSchema, tLCPremSet.get(i));
			tLPPremSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPPremSchema.setEdorType(aLPEdorItemSchema.getEdorType());

			if (!this.queryLPPrem(tLPPremSchema)) {
				return tLPPremSet;
			}

			tLPPremSet.add(this.getSchema());
		}

		// 查询保全保费项表中，该保单，未保全确认的，责任代码不在C表中的，保全日期小于当前保全的，数据
		String sql = "select * from lpprem where edorno in (select edorno from LPEdorItem where edorstate<>'0')"
				+ " and polno='"
				+ "?PolNo2?"
				+ "' and payplancode not in (select payplancode from lcprem where polno='"
				+ "?PolNo3?"
				+ "') and MakeDate<='"
				+ "?PolNo4?"
				+ "' and MakeTime<='"
				+ "?PolNo5?"
				+ "' and payenddate < '?payenddate?' order by MakeDate desc, MakeTime desc";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sql);
		sqlbv2.put("PolNo2", aLPEdorItemSchema.getPolNo());
		sqlbv2.put("PolNo3", aLPEdorItemSchema.getPolNo());
		sqlbv2.put("PolNo4", aLPEdorItemSchema.getMakeDate());
		sqlbv2.put("PolNo5", aLPEdorItemSchema.getMakeTime());
		sqlbv2.put("payenddate", PubFun.getCurrentDate());
		LPPremDB tLPPremDB = new LPPremDB();
		LPPremSet LPPremSet2 = tLPPremDB.executeQuery(sqlbv2);

		tLPPremSet.add(LPPremSet2);

		return tLPPremSet;
	}

	/**
	 * 查询一个责任下所有的保费项信息
	 * 
	 * @param aLPEdorItemSchema
	 * @return
	 */
	public LPPremSet queryAllLPPrem(LPDutySchema pLPDutySchema) {
		LCPremSet tLCPremSet = new LCPremSet();
		Reflections tReflections = new Reflections();
		LPPremSchema tLPPremSchema = new LPPremSchema();
		LPPremSet tLPPremSet = new LPPremSet();

		// 用保单号和责任编码一起限定查找
		LCPremDB tLCPremDB = new LCPremDB();
		tLCPremDB.setPolNo(pLPDutySchema.getPolNo());
		tLCPremDB.setDutyCode(pLPDutySchema.getDutyCode());
		tLCPremSet = tLCPremDB.query();

		for (int i = 1; i <= tLCPremSet.size(); i++) {
			tReflections.transFields(tLPPremSchema, tLCPremSet.get(i));
			tLPPremSchema.setEdorNo(pLPDutySchema.getEdorNo());
			tLPPremSchema.setEdorType(pLPDutySchema.getEdorType());

			if (!this.queryLPPrem(tLPPremSchema)) {
				return tLPPremSet;
			}

			tLPPremSet.add(this.getSchema());
		}

		return tLPPremSet;
	}

	/**
	 * 查询所有该保单下的最新保费项信息。add by sxy2004-03-06
	 * 
	 * @param aLPEdorItemSchema
	 * @return
	 */
	public LPPremSet queryAllLPPrem2(LPEdorItemSchema aLPEdorItemSchema) {
		LCPremSet tLCPremSet = new LCPremSet();
		Reflections tReflections = new Reflections();
		LPPremSet mLPPremSet = new LPPremSet();

		LCPremSchema tLCPremSchema = new LCPremSchema();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

		String sql;
		int m;
		int n;
		m = 0;
		n = 0;

		// 查找本次申请的保单批改信息
		sql = "select * from LPEdorItem where EdorNo='"
				+ "?PolNo8?" + "' and PolNo='"
				+ "?PolNo9?"
				+ "' order by MakeDate desc,MakeTime desc";
		logger.debug(sql);
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("PolNo8", aLPEdorItemSchema.getEdorNo());
		sqlbv3.put("PolNo9", aLPEdorItemSchema.getPolNo());
		
		LPEdorItemDB iLPEdorItemDB = new LPEdorItemDB();
		iLPEdorItemDB.setSchema(aLPEdorItemSchema);

		if (iLPEdorItemDB.getInfo()) {
			// delete EdorValiDate by Minim at 2003-12-17
			sql = "select * from LPEdorItem where EdorNo='"
					+ "?PolNo11?" + "' and PolNo='"
					+ "?PolNo12?" + "' and MakeDate<='"
					+ "?date?" + "' and MakeTime<='"
					+ "?time?"
					+ "' order by MakeDate desc,MakeTime desc";
			sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(sql);
			sqlbv3.put("PolNo11", aLPEdorItemSchema.getEdorNo());
			sqlbv3.put("PolNo12", aLPEdorItemSchema.getPolNo());
			sqlbv3.put("date", iLPEdorItemDB.getMakeDate());
			sqlbv3.put("time", iLPEdorItemDB.getMakeTime());

		}

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv3);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);

			LPPremDB tLPPremDB = new LPPremDB();

			tLPPremDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPPremDB.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPPremDB.setEdorType(tLPEdorItemSchema.getEdorType());
			mLPPremSet = tLPPremDB.query();

			if (mLPPremSet == null) {
				return null;
			}

			if (mLPPremSet.size() == 0) {
				continue;
			} else {
				for (int j = 1; j <= mLPPremSet.size(); j++) {
					mLPPremSet.get(j).setEdorNo(aLPEdorItemSchema.getEdorNo());
					mLPPremSet.get(j).setEdorType(
							aLPEdorItemSchema.getEdorType());
				}

				return mLPPremSet;
			}
		}

		// 查找已经申请确认的保单批改信息（没有保全确认）
		tLPEdorItemSet.clear();
		m = 0;
		n = 0;

		sql = "select * from LPEdorItem where EdorState='2' and PolNo='"
				+ "?PolNo14?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(sql);
		sqlbv5.put("PolNo14", aLPEdorItemSchema.getPolNo());
		logger.debug(sql);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv5);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);

			LPPremDB tLPPremDB = new LPPremDB();

			tLPPremDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPPremDB.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPPremDB.setEdorType(tLPEdorItemSchema.getEdorType());
			mLPPremSet = tLPPremDB.query();

			if (mLPPremSet == null) {
				return null;
			}

			if (mLPPremSet.size() == 0) {
				continue;
			} else {
				for (int j = 1; j <= mLPPremSet.size(); j++) {
					mLPPremSet.get(j).setEdorNo(aLPEdorItemSchema.getEdorNo());
					mLPPremSet.get(j).setEdorType(
							aLPEdorItemSchema.getEdorType());
				}

				return mLPPremSet;
			}
		}

		n = 0;

		// 如果是第一次申请,得到承保保单的保费项信息
		LCPremDB tLCPremDB = new LCPremDB();
		tLCPremDB.setPolNo(aLPEdorItemSchema.getPolNo());
		tLCPremSet = tLCPremDB.query();
		n = tLCPremSet.size();
		logger.debug("------n:" + n);

		for (int i = 1; i <= n; i++) {
			tLCPremSchema = tLCPremSet.get(i);

			LPPremSchema tLPPremSchema = new LPPremSchema();

			// 转换Schema
			tReflections.transFields(tLPPremSchema, tLCPremSchema);

			tLPPremSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPPremSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			mLPPremSet.add(tLPPremSchema);
			;
		}

		return mLPPremSet;
	}

	// 查询被保险人变动信息
	public boolean queryLPPrem(LPPremSchema aLPPremSchema) {
		LPPremSchema tLPPremSchema = new LPPremSchema();
		LPPremSet aLPPremSet = new LPPremSet();

		LCPremSchema tLCPremSchema = new LCPremSchema();
		LCPremSet tLCPremSet = new LCPremSet();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSchema aLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

		String sql;
		int m;
		int n;
		m = 0;
		n = 0;

		// 查找本次申请的保单批改信息
		aLPEdorItemSchema.setEdorNo(aLPPremSchema.getEdorNo());
		aLPEdorItemSchema.setEdorType(aLPPremSchema.getEdorType());
		aLPEdorItemSchema.setPolNo(aLPPremSchema.getPolNo());

		sql = "select * from LPEdorItem where EdorNo='"
				+ "?PolNo15?" + "' and PolNo='"
				+ "?PolNo16?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(sql);
		sqlbv5.put("PolNo15", aLPEdorItemSchema.getEdorNo());
		sqlbv5.put("PolNo16", aLPEdorItemSchema.getPolNo());
		logger.debug(sql);

		LPEdorItemDB iLPEdorItemDB = new LPEdorItemDB();
		iLPEdorItemDB.setSchema(aLPEdorItemSchema);

		if (iLPEdorItemDB.getInfo()) {
			// delete EdorValiDate by Minim at 2003-12-17
			sql = "select * from LPEdorItem where EdorNo='"
					+ "?PolNo17?" + "' and PolNo='"
					+ "?PolNo18?" + "' and MakeDate<='"
					+ "?date1?" + "' and MakeTime<='"
					+ "?time1?"
					+ "' order by MakeDate desc,MakeTime desc";
			sqlbv5=new SQLwithBindVariables();
			sqlbv5.sql(sql);
			sqlbv5.put("PolNo17", aLPEdorItemSchema.getEdorNo());
			sqlbv5.put("PolNo18", aLPEdorItemSchema.getPolNo());
			sqlbv5.put("date1", iLPEdorItemDB.getMakeDate());
			sqlbv5.put("time1", iLPEdorItemDB.getMakeTime());
		}

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv5);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);

			LPPremDB tLPPremDB = new LPPremDB();

			tLPPremSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPPremSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPPremSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());
			tLPPremSchema.setDutyCode(aLPPremSchema.getDutyCode());
			tLPPremSchema.setPayPlanCode(aLPPremSchema.getPayPlanCode());

			tLPPremDB.setSchema(tLPPremSchema);

			if (!tLPPremDB.getInfo()) {
				continue;
			} else {
				tLPPremDB.setEdorNo(aLPPremSchema.getEdorNo());
				tLPPremDB.setEdorType(aLPPremSchema.getEdorType());
				this.setSchema(tLPPremDB.getSchema());

				return true;
			}
		}

		// 查找已经申请确认的保单批改信息（没有保全确认）
		tLPEdorItemSet.clear();
		m = 0;
		n = 0;

		sql = "select * from LPEdorItem where EdorState='2' and PolNo='"
				+ "?PolNo19?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(sql);
		sqlbv6.put("PolNo19", aLPEdorItemSchema.getPolNo());
		
		logger.debug(sql);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv6);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);

			LPPremDB tLPPremDB = new LPPremDB();

			tLPPremSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPPremSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPPremSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());
			tLPPremSchema.setDutyCode(aLPPremSchema.getDutyCode());
			tLPPremSchema.setPayPlanCode(aLPPremSchema.getPayPlanCode());

			tLPPremDB.setSchema(tLPPremSchema);

			if (!tLPPremDB.getInfo()) {
				continue;
			} else {
				tLPPremDB.setEdorNo(aLPPremSchema.getEdorNo());
				tLPPremDB.setEdorType(aLPPremSchema.getEdorType());
				this.setSchema(tLPPremDB.getSchema());

				return true;
			}
		}

		n = 0;

		// 如果是第一次申请,得到承保保单的客户信息
		LCPremDB tLCPremDB = new LCPremDB();
		tLCPremDB.setPolNo(aLPEdorItemSchema.getPolNo());

		tLCPremSet = tLCPremDB.query();
		n = tLCPremSet.size();
		logger.debug("------n:" + n);

		for (int i = 1; i <= n; i++) {
			tLCPremSchema = tLCPremSet.get(i);

			if (tLCPremSchema.getPolNo().equals(aLPPremSchema.getPolNo())
					&& tLCPremSchema.getDutyCode().equals(
							aLPPremSchema.getDutyCode())
					&& tLCPremSchema.getPayPlanCode().equals(
							aLPPremSchema.getPayPlanCode())) {
				// 转换Schema
				Reflections tReflections = new Reflections();
				tReflections.transFields(tLPPremSchema, tLCPremSchema);

				tLPPremSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
				tLPPremSchema.setEdorType(aLPEdorItemSchema.getEdorType());
				this.setSchema(tLPPremSchema);

				return true;
			}
		}

		return false;
	}

	// 查询上次保全保费项信息
	public boolean queryLastLPPrem(LPEdorItemSchema aLPEdorItemSchema,
			LPPremSchema aLPPremSchema) {
		LPPremSchema tLPPremSchema = new LPPremSchema();
		LPPremSet aLPPremSet = new LPPremSet();

		LCPremSchema tLCPremSchema = new LCPremSchema();
		LCPremSet tLCPremSet = new LCPremSet();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

		String sql;
		int m;
		int n;
		m = 0;
		n = 0;

		// 查找最近申请的保单批改信息
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);

		// delete EdorValiDate by Minim at 2003-12-17
		sql = "select * from LPEdorItem where PolNo='"
				+ "?PolNo19?"
				+ "' and edorstate <>'0' and MakeDate<='"
				+ "?PolNo20?" + "' and MakeTime<'"
				+ "?PolNo21?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(sql);
		sqlbv7.put("PolNo19", aLPEdorItemSchema.getPolNo());
		sqlbv7.put("PolNo20", aLPEdorItemSchema.getMakeDate());
		sqlbv7.put("PolNo21", aLPEdorItemSchema.getMakeTime());
		logger.debug(sql);

		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv7);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);

			LPPremDB tLPPremDB = new LPPremDB();

			tLPPremSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPPremSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPPremSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());
			tLPPremSchema.setDutyCode(aLPPremSchema.getDutyCode());
			tLPPremSchema.setPayPlanCode(aLPPremSchema.getPayPlanCode());

			tLPPremDB.setSchema(tLPPremSchema);

			if (!tLPPremDB.getInfo()) {
				continue;
			} else {
				tLPPremDB.setEdorNo(aLPPremSchema.getEdorNo());
				tLPPremDB.setEdorType(aLPPremSchema.getEdorType());
				this.setSchema(tLPPremDB.getSchema());

				return true;
			}
		}

		// 如果是第一次申请,得到承保保单的客户信息
		LCPremDB tLCPremDB = new LCPremDB();
		tLCPremDB.setPolNo(aLPEdorItemSchema.getPolNo());

		tLCPremSet = tLCPremDB.query();
		n = tLCPremSet.size();
		logger.debug("------n:" + n);

		for (int i = 1; i <= n; i++) {
			tLCPremSchema = tLCPremSet.get(i);
			logger.debug("PolNo:" + aLPPremSchema.getPolNo());

			if (tLCPremSchema.getPolNo().equals(aLPPremSchema.getPolNo())
					&& tLCPremSchema.getDutyCode().equals(
							aLPPremSchema.getDutyCode())
					&& tLCPremSchema.getPayPlanCode().equals(
							aLPPremSchema.getPayPlanCode())) {
				// 转换Schema
				Reflections tReflections = new Reflections();
				tReflections.transFields(tLPPremSchema, tLCPremSchema);

				tLPPremSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
				tLPPremSchema.setEdorType(aLPEdorItemSchema.getEdorType());
				this.setSchema(tLPPremSchema);

				return true;
			}
		}

		return false;
	}
}

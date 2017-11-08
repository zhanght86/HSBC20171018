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

import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;

public class LPGetBL extends LPGetSchema {
private static Logger logger = Logger.getLogger(LPGetBL.class);
	// @Constructor
	public CErrors mErrors = new CErrors(); // 错误信息

	public LPGetBL() {
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
	public LPGetSet queryAllLPGet(LPEdorItemSchema aLPEdorItemSchema) {
		LCGetSet tLCGetSet = new LCGetSet();
		Reflections tReflections = new Reflections();
		LPGetSchema tLPGetSchema = new LPGetSchema();
		LPGetSet tLPGetSet = new LPGetSet();

		LCGetDB tLCGetDB = new LCGetDB();
		tLCGetDB.setPolNo(aLPEdorItemSchema.getPolNo());
		tLCGetSet = tLCGetDB.query();

		for (int i = 1; i <= tLCGetSet.size(); i++) {
			tReflections.transFields(tLPGetSchema, tLCGetSet.get(i));
			tLPGetSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPGetSchema.setEdorType(aLPEdorItemSchema.getEdorType());

			if (!this.queryLPGet(tLPGetSchema)) {
				return tLPGetSet;
			}

			tLPGetSet.add(this.getSchema());
		}

		return tLPGetSet;
	}

	/**
	 * 查询所有的客户信息
	 * 
	 * @param aLPEdorItemSchema
	 * @return
	 */
	public LPGetSet queryAllLPGetForReCal(LPEdorItemSchema aLPEdorItemSchema) {
		Reflections tReflections = new Reflections();

		LCGetDB tLCGetDB = new LCGetDB();
		tLCGetDB.setPolNo(aLPEdorItemSchema.getPolNo());

		LCGetSet tLCGetSet = tLCGetDB.query();

		LPGetSchema tLPGetSchema = new LPGetSchema();
		LPGetSet tLPGetSet = new LPGetSet();

		for (int i = 1; i <= tLCGetSet.size(); i++) {
			tReflections.transFields(tLPGetSchema, tLCGetSet.get(i));
			tLPGetSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPGetSchema.setEdorType(aLPEdorItemSchema.getEdorType());

			if (!this.queryLPGet(tLPGetSchema)) {
				return tLPGetSet;
			}

			tLPGetSet.add(this.getSchema());
		}

		// 查询保全保费项表中，该保单，未保全确认的，责任代码不在C表中的，保全日期小于当前保全的，数据
		String sql = "select * from lpget where edorno in (select edorno from LPEdorItem where edorstate<>'0')"
				+ " and polno='"
				+ "?polno1?"
				+ "' and getdutycode not in (select getdutycode from lcget where polno='"
				+ "?polno2?"
				+ "') and (MakeDate<'"
				+ "?polno3?"
				+ "' or (MakeDate='"
				+ "?polno4?"
				+ "' and MakeTime<='"
				+ "?polno5?"
				+ "')) order by MakeDate,MakeTime desc";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("polno1", aLPEdorItemSchema.getPolNo());
		sqlbv1.put("polno2", aLPEdorItemSchema.getPolNo());
		sqlbv1.put("polno3", aLPEdorItemSchema.getMakeDate());
		sqlbv1.put("polno4", aLPEdorItemSchema.getMakeDate());
		sqlbv1.put("polno5", aLPEdorItemSchema.getMakeTime());

        		LPGetDB tLPGetDB = new LPGetDB();
		LPGetSet LPGetSet2 = tLPGetDB.executeQuery(sqlbv1);

		tLPGetSet.add(LPGetSet2);

		return tLPGetSet;
	}

	// 查询领取项信息
	public boolean queryLPGet(LPGetSchema aLPGetSchema) {
		LPGetSchema tLPGetSchema = new LPGetSchema();
		LPGetSet aLPGetSet = new LPGetSet();

		LCGetSchema tLCGetSchema = new LCGetSchema();
		LCGetSet tLCGetSet = new LCGetSet();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSchema aLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

		String sql;
		int m;
		int n;
		m = 0;
		n = 0;

		// 查找本次申请的保单批改信息
		aLPEdorItemSchema.setEdorNo(aLPGetSchema.getEdorNo());
		aLPEdorItemSchema.setEdorType(aLPGetSchema.getEdorType());
		aLPEdorItemSchema.setPolNo(aLPGetSchema.getPolNo());

		sql = "select * from LPEdorItem where EdorNo='"
				+ "?polno11?" + "' and PolNo='"
				+ "?polno22?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sql);
		sqlbv2.put("polno11", aLPEdorItemSchema.getEdorNo());
		sqlbv2.put("polno22", aLPEdorItemSchema.getPolNo());
		
		LPEdorItemDB iLPEdorItemDB = new LPEdorItemDB();
		iLPEdorItemDB.setSchema(aLPEdorItemSchema);

		if (iLPEdorItemDB.getInfo()) {
			// delete EdorValiDate by Minim at 2003-12-17
			sql = "select EdorNo,PolNo,EdorType,EdorValiDate,MakeTime from LPEdorItem where EdorNo='"
					+ "?polno12?"
					+ "' and PolNo='"
					+ "polno13"
					+ "' and (MakeDate<'"
					+ "?polno14?"
					+ "' or (MakeDate='"
					+ "?polno15?"
					+ "' and MakeTime<='"
					+ "?polno16?"
					+ "')) order by MakeDate,MakeTime desc";
			sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(sql);
			sqlbv2.put("polno12", aLPEdorItemSchema.getEdorNo());
			sqlbv2.put("polno13", aLPEdorItemSchema.getPolNo());
			sqlbv2.put("polno14", aLPEdorItemSchema.getMakeDate());
			sqlbv2.put("polno15", aLPEdorItemSchema.getMakeDate());
			sqlbv2.put("polno16", aLPEdorItemSchema.getMakeTime());
		}

		logger.debug(sql);

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv2);
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全项目错误！");
			return false;
		}

		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);

			LPGetDB tLPGetDB = new LPGetDB();

			tLPGetSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPGetSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPGetSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());
			tLPGetSchema.setDutyCode(aLPGetSchema.getDutyCode());
			tLPGetSchema.setGetDutyCode(aLPGetSchema.getGetDutyCode());

			tLPGetDB.setSchema(tLPGetSchema);

			if (!tLPGetDB.getInfo()) {
				continue;
			} else {
				tLPGetDB.setEdorNo(aLPGetSchema.getEdorNo());
				tLPGetDB.setEdorType(aLPGetSchema.getEdorType());
				this.setSchema(tLPGetDB.getSchema());

				return true;
			}
		}

		// 查找已经申请确认的保单批改信息（没有保全确认）
		tLPEdorItemSet.clear();
		m = 0;
		n = 0;

		sql = "select * from LPEdorItem where EdorState='2' and PolNo='"
				+ "?polno17?"
				+ "' order by MakeDate desc,MakeTime desc";
		logger.debug(sql);
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("polno17", aLPEdorItemSchema.getPolNo());
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv3);
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全项目错误！");
			return false;
		}

		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);

			LPGetDB tLPGetDB = new LPGetDB();

			tLPGetSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPGetSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPGetSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());
			tLPGetSchema.setDutyCode(aLPGetSchema.getDutyCode());
			tLPGetSchema.setGetDutyCode(aLPGetSchema.getGetDutyCode());

			tLPGetDB.setSchema(tLPGetSchema);

			if (!tLPGetDB.getInfo()) {
				continue;
			} else {
				tLPGetDB.setEdorNo(aLPGetSchema.getEdorNo());
				tLPGetDB.setEdorType(aLPGetSchema.getEdorType());
				this.setSchema(tLPGetDB.getSchema());

				return true;
			}
		}

		n = 0;

		// 如果是第一次申请,得到承保保单的客户信息
		LCGetDB tLCGetDB = new LCGetDB();
		tLCGetDB.setPolNo(aLPEdorItemSchema.getPolNo());
		tLCGetDB.setDutyCode(aLPGetSchema.getDutyCode());
		tLCGetDB.setGetDutyCode(aLPGetSchema.getGetDutyCode());
		tLCGetSet = tLCGetDB.query();
		if (tLCGetDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全项目错误！");
			return false;
		}

		n = tLCGetSet.size();
		logger.debug("------n:" + n);

		for (int i = 1; i <= n; i++) {
			tLCGetSchema = tLCGetSet.get(i);
			PubFun.copySchema(tLPGetSchema, tLCGetSchema);
			tLPGetSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPGetSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			this.setSchema(tLPGetSchema);
			return true;
		}
		return false;
	}

	// 查询上次保全给付信息
	public boolean queryLastLPGet(LPEdorItemSchema aLPEdorItemSchema,
			LPGetSchema aLPGetSchema) {
		LPGetSchema tLPGetSchema = new LPGetSchema();
		// LPGetSet aLPGetSet = new LPGetSet();
		// LCGetSchema tLCGetSchema = new LCGetSchema();
		// LCGetSet tLCGetSet = new LCGetSet();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

		String sql;
		int m = 0;
		;
		// int n = 0;

		// 查找最近申请的保单批改信息
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);

		// delete EdorValiDate by Minim at 2003-12-17
		sql = "select * from LPEdorItem where PolNo='"
				+ "?polno19?"
				+ "' and edorstate <>'0' and MakeDate<='"
				+ "?polno23?" + "' and MakeTime<'"
				+ "?polno24?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(sql);
		sqlbv4.put("polno19", aLPEdorItemSchema.getPolNo());
		sqlbv4.put("polno23", aLPEdorItemSchema.getMakeDate());
		sqlbv4.put("polno24", aLPEdorItemSchema.getMakeTime());
		logger.debug(sql);

		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv4);
		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);

			LPGetDB tLPGetDB = new LPGetDB();

			tLPGetSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPGetSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPGetSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());
			tLPGetSchema.setDutyCode(aLPGetSchema.getDutyCode());
			tLPGetSchema.setGetDutyCode(aLPGetSchema.getGetDutyCode());

			tLPGetDB.setSchema(tLPGetSchema);

			if (!tLPGetDB.getInfo()) {
				continue;
			} else {
				tLPGetDB.setEdorNo(aLPGetSchema.getEdorNo());
				tLPGetDB.setEdorType(aLPGetSchema.getEdorType());
				this.setSchema(tLPGetDB.getSchema());

				return true;
			}
		}

		// 如果是第一次申请,得到承保保单的客户信息
		LCGetDB tLCGetDB = new LCGetDB();
		tLCGetDB.setPolNo(aLPEdorItemSchema.getPolNo());
		tLCGetDB.setDutyCode(aLPGetSchema.getDutyCode());
		tLCGetDB.setGetDutyCode(aLPGetSchema.getGetDutyCode());

		if (tLCGetDB.getInfo()) {
			// 转换Schema
			Reflections tReflections = new Reflections();
			tReflections.transFields(tLPGetSchema, tLCGetDB.getSchema());

			tLPGetSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPGetSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			this.setSchema(tLPGetSchema);

			return true;
		}

		// tLCGetSet = tLCGetDB.query();
		// n = tLCGetSet.size();
		// logger.debug("------n:" + n);
		//
		// for (int i = 1; i <= n; i++)
		// {
		// tLCGetSchema = tLCGetSet.get(i);
		// logger.debug("PolNo:" + aLPGetSchema.getPolNo());
		//
		// if (tLCGetSchema.getPolNo().equals(aLPGetSchema.getPolNo()) &&
		// tLCGetSchema.getDutyCode().equals(aLPGetSchema.getDutyCode()) &&
		// tLCGetSchema.getGetDutyCode().equals(aLPGetSchema.
		// getGetDutyCode()))
		// {
		// //转换Schema
		// Reflections tReflections = new Reflections();
		// tReflections.transFields(tLPGetSchema, tLCGetSchema);
		//
		// tLPGetSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
		// tLPGetSchema.setEdorType(aLPEdorItemSchema.getEdorType());
		// this.setSchema(tLPGetSchema);
		//
		// return true;
		// }
		// }

		return false;
	}
}

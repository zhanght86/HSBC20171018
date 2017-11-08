/*
 * <p>ClassName: LPPolBL </p>
 * <p>Description: LLClaimSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database:
 * @CreateDate：2002-07-25
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;

public class LPPolBL extends LPPolSchema {
private static Logger logger = Logger.getLogger(LPPolBL.class);
	// @Constructor
	public LPPolBL() {
	}

	public void setDefaultFields() {
		this.setMakeDate(PubFun.getCurrentDate());
		this.setMakeTime(PubFun.getCurrentTime());
		this.setModifyDate(PubFun.getCurrentDate());
		this.setModifyTime(PubFun.getCurrentTime());
	}

	public boolean queryLPPol(LPEdorItemSchema aLPEdorItemSchema) {
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		Reflections tReflections = new Reflections();

		LPPolSet tLPPolSet = new LPPolSet();
		LPPolSchema tLPPolSchema = new LPPolSchema();
		LPPolSchema aLPPolSchema = new LPPolSchema();
		LCPolSchema tLCPolSchema = new LCPolSchema();

		String sql;
		int m;

		// 查找本次申请的保单批改信息

		sql = "select * from LPEdorItem where EdorNo='"
				+ "?PolNo?" + "' and PolNo='"
				+ "?PolNo1?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("PolNo", aLPEdorItemSchema.getEdorNo());
		sqlbv1.put("PolNo1", aLPEdorItemSchema.getPolNo());
				
		logger.debug(sql);

		LPEdorItemDB iLPEdorItemDB = new LPEdorItemDB();
		iLPEdorItemDB.setSchema(aLPEdorItemSchema);
		LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
		mLPEdorItemSet = iLPEdorItemDB.query();

		if (!iLPEdorItemDB.mErrors.needDealError()
				&& mLPEdorItemSet.size() >= 1) {
			sql = "select * from LPEdorItem where EdorNo='"
					+ "?PolNo2?" + "' and PolNo='"
					+ "?PolNo3?" + "' and (MakeDate<'"
					+ "?PolNo4?" + "' or (MakeDate='"
					+ "?PolNo5?" + "' and MakeTime<='"
					+ "?PolNo6?"
					+ "')) order by MakeDate,MakeTime desc";
			sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(sql);
			sqlbv1.put("PolNo2", aLPEdorItemSchema.getEdorNo());
			sqlbv1.put("PolNo3", aLPEdorItemSchema.getPolNo());
			sqlbv1.put("PolNo4", aLPEdorItemSchema.getMakeDate());
			sqlbv1.put("PolNo5", aLPEdorItemSchema.getMakeDate());
			sqlbv1.put("PolNo6", aLPEdorItemSchema.getMakeTime());
		} else if (iLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全项目错误！");
			return false;
		}
		logger.debug("---sqlend:" + sql);

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv1);
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全项目错误！");
			return false;
		}
		m = tLPEdorItemSet.size();
		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPPolDB tLPPolDB = new LPPolDB();
			tLPPolSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPPolSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			tLPPolDB.setSchema(tLPPolSchema);
			if (!tLPPolDB.getInfo()) {
				continue;
			}

			aLPPolSchema = tLPPolDB.getSchema();
			aLPPolSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			aLPPolSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			// 增加退保限制
			this.setSchema(aLPPolSchema);
			return true;
		}
		// 查找已经申请确认的保单批改信息（没有保全确认）

		tLPEdorItemSet.clear();
		m = 0;
		// tLPEdorItemDB.setEdorNo(aLPEdorItemSchema.getEdorNo());
		sql = "select * from LPEdorItem where EdorState='2' and PolNo='"
				+ "?PolNo4?"
				+ "' order by MakeDate,MakeTime desc";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("PolNo4", aLPEdorItemSchema.getPolNo());
		logger.debug(sql);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv3);
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询已经申请确认的保单保全项目错误！");
			return false;
		}

		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPPolDB tLPPolDB = new LPPolDB();

			tLPPolSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPPolSchema.setEdorType(tLPEdorItemSchema.getEdorType());

			tLPPolDB.setSchema(tLPPolSchema);

			if (!tLPPolDB.getInfo()) {
				continue;
			}

			aLPPolSchema = tLPPolDB.getSchema();
			aLPPolSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			aLPPolSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			// 增加退保限制
			this.setSchema(aLPPolSchema);

			return true;
		}

		// 如果是第一次申请，得到承保保单信息。
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(aLPEdorItemSchema.getPolNo());

		if (!tLCPolDB.getInfo()) {
			return false;
		}

		tLCPolSchema = tLCPolDB.getSchema();
		// 转换Schema
		tReflections.transFields(aLPPolSchema, tLCPolSchema);

		aLPPolSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
		aLPPolSchema.setEdorType(aLPEdorItemSchema.getEdorType());

		this.setSchema(aLPPolSchema);
		return true;
	}

	public boolean queryOtherLPPol(LPEdorItemSchema aLPEdorItemSchema) {
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		Reflections tReflections = new Reflections();

		LPPolSet tLPPolSet = new LPPolSet();
		LPPolSchema tLPPolSchema = new LPPolSchema();
		LPPolSchema aLPPolSchema = new LPPolSchema();
		LCPolSchema tLCPolSchema = new LCPolSchema();

		String sql;
		int m;

		// 查找本次申请的保单批改信息
		sql = "select * from LPEdorItem where EdorNo='"
				+ "?PolNo5?" + "' and PolNo='"
				+ "?PolNo6?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(sql);
		sqlbv4.put("PolNo5", aLPEdorItemSchema.getEdorNo());
		sqlbv4.put("PolNo6", aLPEdorItemSchema.getPolNo());
	
		logger.debug(sql);

		LPEdorItemDB iLPEdorItemDB = new LPEdorItemDB();
		iLPEdorItemDB.setSchema(aLPEdorItemSchema);
		LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
		mLPEdorItemSet = iLPEdorItemDB.query();

		if (!iLPEdorItemDB.mErrors.needDealError()
				&& mLPEdorItemSet.size() >= 1) {
			sql = "select * from LPEdorItem where EdorNo='"
					+ "?PolNo5?" + "' and PolNo='"
					+ "?PolNo7?" + "' and (MakeDate<'"
					+ "?PolNo8?" + "' or (MakeDate='"
					+ "?PolNo9?" + "' and MakeTime<='"
					+ "?PolNo710?"
					+ "')) order by MakeDate,MakeTime desc";
			sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(sql);
			sqlbv4.put("PolNo5", aLPEdorItemSchema.getEdorNo());
			sqlbv4.put("PolNo7", aLPEdorItemSchema.getPolNo());
			sqlbv4.put("PolNo8", aLPEdorItemSchema.getMakeDate());
			sqlbv4.put("PolNo9", aLPEdorItemSchema.getMakeDate());
			sqlbv4.put("PolNo10", aLPEdorItemSchema.getMakeTime());
		} else if (iLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全项目错误！");
			return false;
		}
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();

		// 查找已经申请确认的保单批改信息（没有保全确认）
		tLPEdorItemSet.clear();
		m = 0;
		// tLPEdorItemDB.setEdorNo(aLPEdorItemSchema.getEdorNo());
		sql = "select * from LPEdorItem where EdorState='2' and PolNo='"
				+ "?PolNo11?"
				+ "' order by MakeDate,MakeTime desc";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(sql);
		sqlbv5.put("PolNo11", aLPEdorItemSchema.getPolNo());
	
		logger.debug(sql);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv5);
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询已经申请确认的保单保全项目错误！");
			return false;
		}

		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPPolDB tLPPolDB = new LPPolDB();

			tLPPolSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPPolSchema.setEdorType(tLPEdorItemSchema.getEdorType());

			tLPPolDB.setSchema(tLPPolSchema);

			if (!tLPPolDB.getInfo()) {
				continue;
			}

			aLPPolSchema = tLPPolDB.getSchema();
			aLPPolSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			aLPPolSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			// 增加退保限制
			this.setSchema(aLPPolSchema);

			return true;
		}

		// 如果是第一次申请，得到承保保单信息。
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(aLPEdorItemSchema.getPolNo());

		if (!tLCPolDB.getInfo()) {
			return false;
		}

		tLCPolSchema = tLCPolDB.getSchema();
		// 转换Schema
		tReflections.transFields(aLPPolSchema, tLCPolSchema);

		aLPPolSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
		aLPPolSchema.setEdorType(aLPEdorItemSchema.getEdorType());

		this.setSchema(aLPPolSchema);
		return true;
	}

	/**
	 * 取得附加险保单信息
	 * 
	 * @param aLPEdorItemSchema
	 * @return
	 */
	public LPPolSet queryAppendLPPol(LPEdorItemSchema aLPEdorItemSchema) {
		// 得到承保附险保单信息。
		LCPolSet tLCPolSet = new LCPolSet();
		LPPolSet tLPPolSet = new LPPolSet();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setSchema(aLPEdorItemSchema);
		LCPolDB tLCPolDB = new LCPolDB();
		String tSql = "Select * from lcpol where mainPolNo='"
				+ "?PolNo12?" + "' and MainPolNo<>PolNo";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(tSql);
		sqlbv6.put("PolNo12", aLPEdorItemSchema.getPolNo());
		tLCPolSet = tLCPolDB.executeQuery(sqlbv6);
		if (tLCPolDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询承保附险保单信息错误！");
		}

		if (tLCPolSet.size() > 0) {
			for (int i = 1; i <= tLCPolSet.size(); i++) {
				tLPEdorItemSchema.setPolNo(tLCPolSet.get(i).getPolNo());
				if (this.queryLPPol(tLPEdorItemSchema)) {
					tLPPolSet.add(this.getSchema());
				}
			}
		}
		return tLPPolSet;
	}

	/**
	 * 取得主险和附加险保单信息
	 * 
	 * @param aLPEdorItemSchema
	 * @return
	 */
	public LPPolSet queryAllLPPol2(LPEdorItemSchema aLPEdorItemSchema) {
		logger.debug("start queryAll Main retail Pol .....");

		// 得到承保附险保单信息。
		LCPolSet tLCPolSet = new LCPolSet();
		LPPolSet tLPPolSet = new LPPolSet();

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setSchema(aLPEdorItemSchema);

		LCPolDB tLCPolDB = new LCPolDB();

		String tSql1 = " select * from lcpol where polno = '"
				+ "?PolNo13?" + "'";
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(tSql1);
		sqlbv7.put("PolNo13", aLPEdorItemSchema.getPolNo());
		LCPolSet primPolSet = tLCPolDB.executeQuery(sqlbv7);
		if (tLCPolDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询主险和附加险保单信息错误！");
		}

		if (primPolSet.size() == 0) {
			return tLPPolSet;
		}
		String mainPolno = primPolSet.get(1).getMainPolNo();

		String tSql = "Select * from lcpol where mainPolNo='" + "?mainPolNo?" + "'";
		logger.debug("tSql : " + tSql);
		SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
		sqlbv8.sql(tSql);
		sqlbv8.put("mainPolNo", mainPolno);
		tLCPolSet = tLCPolDB.executeQuery(sqlbv8);
		if (tLCPolDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询主险和附加险保单信息错误！");
		}

		logger.debug("tLCPolSet size :" + tLCPolSet.size());
		if (tLCPolSet.size() > 0) {
			for (int i = 1; i <= tLCPolSet.size(); i++) {
				tLPEdorItemSchema.setPolNo(tLCPolSet.get(i).getPolNo());
				if (this.queryLPPol(tLPEdorItemSchema)) {
					logger.debug(">>>>>>>>> :"
							+ this.getSchema().getPolNo());
					logger.debug("sumPrem :"
							+ this.getSchema().getSumPrem());
					tLPPolSet.add(this.getSchema());
				}
			}
		}
		return tLPPolSet;
	}

	/**
	 * 取得附加险保单信息
	 * 
	 * @param aLPEdorItemSchema
	 * @return
	 */
	public LPPolSet queryAllLPPol(LPEdorItemSchema aLPEdorItemSchema) {
		logger.debug("start queryAllLPPol .....");
		// 得到承保附险保单信息。
		LCPolSet tLCPolSet = new LCPolSet();
		LPPolSet tLPPolSet = new LPPolSet();

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setSchema(aLPEdorItemSchema);

		LCPolDB tLCPolDB = new LCPolDB();
		String tSql = "Select * from lcpol where mainPolNo='"
				+ "?mainPolNo?" + "' or PolNo ='"
				+ "?PolNo?" + "'";
		SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
		sqlbv9.sql(tSql);
		sqlbv9.put("mainPolNo", aLPEdorItemSchema.getPolNo());
		sqlbv9.put("PolNo", aLPEdorItemSchema.getPolNo());
		
		logger.debug("tSql : " + tSql);
		tLCPolSet = tLCPolDB.executeQuery(sqlbv9);
		logger.debug("tLCPolSet size :" + tLCPolSet.size());
		if (tLCPolSet.size() > 0) {
			for (int i = 1; i <= tLCPolSet.size(); i++) {
				tLPEdorItemSchema.setPolNo(tLCPolSet.get(i).getPolNo());
				if (this.queryLPPol(tLPEdorItemSchema)) {
					logger.debug(">>>>>>>>> :"
							+ this.getSchema().getPolNo());
					logger.debug("sumPrem :"
							+ this.getSchema().getSumPrem());
					tLPPolSet.add(this.getSchema());
				}
			}
		}
		return tLPPolSet;
	}

	// 查询上次保全被保险人变动信息
	public boolean queryLastLPPol(LPEdorItemSchema aLPEdorItemSchema,
			LPPolSchema aLPPolSchema) {
		LPPolSchema tLPPolSchema = new LPPolSchema();
		LPPolSet aLPPolSet = new LPPolSet();

		LCPolSchema tLCPolSchema = new LCPolSchema();
		LCPolSet tLCPolSet = new LCPolSet();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

		String sql;
		int m, n;
		m = 0;
		n = 0;

		// 查找最近申请的保单批改信息
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);

		sql = "select * from LPEdorItem where PolNo='"
				+ "?mainPolNo1?"
				+ "' and edorstate <>'0' and (MakeDate<'"
				+ "?PolNo1?" + "' or (MakeDate='"
				+ "?PolNo2?" + "' and MakeTime<='"
				+ "?PolNo3?"
				+ "')) order by MakeDate,MakeTime desc";
		SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
		sqlbv10.sql(sql);
		sqlbv10.put("mainPolNo1", aLPEdorItemSchema.getPolNo());
		sqlbv10.put("PolNo1", aLPEdorItemSchema.getMakeDate());
		sqlbv10.put("PolNo2", aLPEdorItemSchema.getMakeDate());
		sqlbv10.put("PolNo3", aLPEdorItemSchema.getMakeTime());
	
		logger.debug(sql);

		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv10);
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询最近申请的保单批改信息错误！");
			return false;
		}

		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPPolDB tLPPolDB = new LPPolDB();

			tLPPolSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPPolSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());

			tLPPolDB.setSchema(tLPPolSchema);
			if (!tLPPolDB.getInfo()) {
				continue;
			} else {
				tLPPolDB.setEdorNo(aLPPolSchema.getEdorNo());
				tLPPolDB.setEdorType(aLPPolSchema.getEdorType());
				this.setSchema(tLPPolDB.getSchema());
				return true;
			}
		}

		// 如果是第一次申请,得到承保保单的客户信息
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(aLPEdorItemSchema.getPolNo());

		tLCPolSet = tLCPolDB.query();
		n = tLCPolSet.size();
		logger.debug("------n:" + n);
		for (int i = 1; i <= n; i++) {
			tLCPolSchema = tLCPolSet.get(i);
			logger.debug("PolNo:" + aLPPolSchema.getPolNo());
			// 转换Schema
			Reflections tReflections = new Reflections();
			tReflections.transFields(tLPPolSchema, tLCPolSchema);

			tLPPolSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			this.setSchema(tLPPolSchema);
			return true;
		}
		return false;
	}
}

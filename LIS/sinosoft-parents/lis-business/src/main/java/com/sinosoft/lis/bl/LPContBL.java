/*
 * <p>ClassName: LPContBL </p>
 * <p>Description: LLClaimSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database:
 * @CreateDate：2002-07-25
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;

public class LPContBL extends LPContSchema {
private static Logger logger = Logger.getLogger(LPContBL.class);
	// @Constructor
	public LPContBL() {
	}

	public void setDefaultFields() {
		this.setMakeDate(PubFun.getCurrentDate());
		this.setMakeTime(PubFun.getCurrentTime());
		this.setModifyDate(PubFun.getCurrentDate());
		this.setModifyTime(PubFun.getCurrentTime());
	}

	public boolean queryLPCont(LPEdorItemSchema aLPEdorItemSchema) {
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		Reflections tReflections = new Reflections();

		LPContSet tLPContSet = new LPContSet();
		LPContSchema tLPContSchema = new LPContSchema();
		LPContSchema aLPContSchema = new LPContSchema();
		LCContSchema tLCContSchema = new LCContSchema();

		String sql;
		int m;

		// 查找本次申请的保单批改信息

		sql = "select * from LPEdorItem where EdorNo='"
				+ "?EdorNo?" + "' and ContNo='"
				+ "?ContNo?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
		sqlbv11.sql(sql);
		sqlbv11.put("EdorNo", aLPEdorItemSchema.getEdorNo());
		sqlbv11.put("ContNo", aLPEdorItemSchema.getContNo());
		logger.debug(sql);

		LPEdorItemDB iLPEdorItemDB = new LPEdorItemDB();
		iLPEdorItemDB.setSchema(aLPEdorItemSchema);
		LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
		mLPEdorItemSet = iLPEdorItemDB.query();

		if (!iLPEdorItemDB.mErrors.needDealError()
				&& mLPEdorItemSet.size() >= 1) {
			sql = "select * from LPEdorItem where EdorNo='"
					+ "?EdorNo?" + "' and ContNo='"
					+ "?EdorNo1?" + "' and (MakeDate<'"
					+ "?EdorNo2?" + "' or (MakeDate='"
					+ "?EdorNo3?" + "' and MakeTime<='"
					+ "?EdorNo4?"
					+ "')) order by MakeDate,MakeTime desc";
			sqlbv11=new SQLwithBindVariables();
			sqlbv11.sql(sql);
			sqlbv11.put("EdorNo", aLPEdorItemSchema.getEdorNo());
			sqlbv11.put("EdorNo1", aLPEdorItemSchema.getContNo());
			sqlbv11.put("EdorNo2", aLPEdorItemSchema.getMakeDate());
			sqlbv11.put("EdorNo3", aLPEdorItemSchema.getMakeDate());
			sqlbv11.put("EdorNo4", aLPEdorItemSchema.getMakeTime());

		} else if (iLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全项目错误！");
			return false;
		}
		logger.debug("---sqlend:" + sql);

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv11);
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全项目错误！");
			return false;
		}
		m = tLPEdorItemSet.size();
		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPContDB tLPContDB = new LPContDB();
			tLPContSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPContSchema.setContNo(tLPEdorItemSchema.getContNo());
			tLPContSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			tLPContDB.setSchema(tLPContSchema);
			if (!tLPContDB.getInfo()) {
				continue;
			}

			aLPContSchema = tLPContDB.getSchema();
			aLPContSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			aLPContSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			// 增加退保限制
			this.setSchema(aLPContSchema);
			return true;
		}
		// 查找已经申请确认的保单批改信息（没有保全确认）

		tLPEdorItemSet.clear();
		m = 0;
		// tLPEdorItemDB.setEdorNo(aLPEdorItemSchema.getEdorNo());
		sql = "select * from LPEdorItem where EdorState='2' and ContNo='"
				+ "?ContNo?"
				+ "' order by MakeDate,MakeTime desc";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("ContNo", aLPEdorItemSchema.getContNo());
		logger.debug(sql);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv1);
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询已经申请确认的保单保全项目错误！");
			return false;
		}

		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPContDB tLPContDB = new LPContDB();

			tLPContSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPContSchema.setContNo(tLPEdorItemSchema.getContNo());
			tLPContSchema.setEdorType(tLPEdorItemSchema.getEdorType());

			tLPContDB.setSchema(tLPContSchema);

			if (!tLPContDB.getInfo()) {
				continue;
			}

			aLPContSchema = tLPContDB.getSchema();
			aLPContSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			aLPContSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			// 增加退保限制
			this.setSchema(aLPContSchema);

			return true;
		}

		// 如果是第一次申请，得到承保保单信息。
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(aLPEdorItemSchema.getContNo());

		if (!tLCContDB.getInfo()) {
			return false;
		}

		tLCContSchema = tLCContDB.getSchema();
		// 转换Schema
		tReflections.transFields(aLPContSchema, tLCContSchema);

		aLPContSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
		aLPContSchema.setEdorType(aLPEdorItemSchema.getEdorType());

		this.setSchema(aLPContSchema);
		return true;
	}

	/**
	 * 取得附加险保单信息
	 * 
	 * @param aLPEdorItemSchema
	 * @return
	 */
	// public LPContSet queryAppendLPCont(LPEdorItemSchema aLPEdorItemSchema)
	// {
	// //得到承保附险保单信息。
	// LCContSet tLCContSet = new LCContSet();
	// LPContSet tLPContSet = new LPContSet();
	// LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
	// tLPEdorItemSchema.setSchema(aLPEdorItemSchema);
	// LCContDB tLCContDB = new LCContDB();
	// String tSql = "Select * from LCCont where mainContNo='" +
	// aLPEdorItemSchema.getContNo() + "' and MainContNo<>ContNo";
	// tLCContSet = tLCContDB.executeQuery(tSql);
	// if (tLCContDB.mErrors.needDealError())
	// {
	// CError.buildErr(this, "查询承保附险保单信息错误！");
	// }
	//
	// if (tLCContSet.size() > 0)
	// {
	// for (int i = 1; i <= tLCContSet.size(); i++)
	// {
	// tLPEdorItemSchema.setContNo(tLCContSet.get(i).getContNo());
	// if (this.queryLPCont(tLPEdorItemSchema))
	// {
	// tLPContSet.add(this.getSchema());
	// }
	// }
	// }
	// return tLPContSet;
	// }

	/**
	 * 取得主险和附加险保单信息
	 * 
	 * @param aLPEdorItemSchema
	 * @return
	 */
	// public LPContSet queryAllLPCont2(LPEdorItemSchema aLPEdorItemSchema)
	// {
	// logger.debug("start queryAll Main retail Pol .....");
	//
	// //得到承保附险保单信息。
	// LCContSet tLCContSet = new LCContSet();
	// LPContSet tLPContSet = new LPContSet();
	//
	// LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
	// tLPEdorItemSchema.setSchema(aLPEdorItemSchema);
	//
	// LCContDB tLCContDB = new LCContDB();
	//
	// String tSql1 = " select * from LCCont where ContNo = '" +
	// aLPEdorItemSchema.getContNo() + "'";
	// LCContSet primPolSet = tLCContDB.executeQuery(tSql1);
	// if (tLCContDB.mErrors.needDealError())
	// {
	// CError.buildErr(this, "查询主险和附加险保单信息错误！");
	// }
	//
	// if (primPolSet.size() == 0)
	// return tLPContSet;
	// String mainContNo = primPolSet.get(1).getMainContNo();
	//
	// String tSql = "Select * from LCCont where mainContNo='" + mainContNo +
	// "'";
	// logger.debug("tSql : " + tSql);
	// tLCContSet = tLCContDB.executeQuery(tSql);
	// if (tLCContDB.mErrors.needDealError())
	// {
	// CError.buildErr(this, "查询主险和附加险保单信息错误！");
	// }
	//
	// logger.debug("tLCContSet size :" + tLCContSet.size());
	// if (tLCContSet.size() > 0)
	// {
	// for (int i = 1; i <= tLCContSet.size(); i++)
	// {
	// tLPEdorItemSchema.setContNo(tLCContSet.get(i).getContNo());
	// if (this.queryLPCont(tLPEdorItemSchema))
	// {
	// logger.debug(">>>>>>>>> :" +
	// this.getSchema().getContNo());
	// logger.debug("sumPrem :" +
	// this.getSchema().getSumPrem());
	// tLPContSet.add(this.getSchema());
	// }
	// }
	// }
	// return tLPContSet;
	// }

	/**
	 * 取得附加险保单信息
	 * 
	 * @param aLPEdorItemSchema
	 * @return
	 */
	// public LPContSet queryAllLPCont(LPEdorItemSchema aLPEdorItemSchema)
	// {
	// logger.debug("start queryAllLPCont .....");
	// //得到承保附险保单信息。
	// LCContSet tLCContSet = new LCContSet();
	// LPContSet tLPContSet = new LPContSet();
	//
	// LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
	// tLPEdorItemSchema.setSchema(aLPEdorItemSchema);
	//
	// LCContDB tLCContDB = new LCContDB();
	// String tSql = "Select * from LCCont where mainContNo='" +
	// aLPEdorItemSchema.getContNo() + "' or ContNo ='" +
	// aLPEdorItemSchema.getContNo() + "'";
	// logger.debug("tSql : " + tSql);
	// tLCContSet = tLCContDB.executeQuery(tSql);
	// logger.debug("tLCContSet size :" + tLCContSet.size());
	// if (tLCContSet.size() > 0)
	// {
	// for (int i = 1; i <= tLCContSet.size(); i++)
	// {
	// tLPEdorItemSchema.setContNo(tLCContSet.get(i).getContNo());
	// if (this.queryLPCont(tLPEdorItemSchema))
	// {
	// logger.debug(">>>>>>>>> :" +
	// this.getSchema().getContNo());
	// logger.debug("sumPrem :" +
	// this.getSchema().getSumPrem());
	// tLPContSet.add(this.getSchema());
	// }
	// }
	// }
	// return tLPContSet;
	// }
	// 查询上次保全被保险人变动信息
	public boolean queryLastLPCont(LPEdorItemSchema aLPEdorItemSchema,
			LPContSchema aLPContSchema) {
		LPContSchema tLPContSchema = new LPContSchema();
		LPContSet aLPContSet = new LPContSet();

		LCContSchema tLCContSchema = new LCContSchema();
		LCContSet tLCContSet = new LCContSet();
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
				+ "?ContNo8?"
				+ "' and edorstate <>'0' and (MakeDate<'"
				+ "?MakeDate1?" + "' or (MakeDate='"
				+ "?MakeDate2?" + "' and MakeTime<='"
				+ "?MakeTime?"
				+ "')) order by MakeDate,MakeTime desc";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sql);
		sqlbv2.put("ContNo8", aLPEdorItemSchema.getContNo());
		sqlbv2.put("ContNo1", aLPEdorItemSchema.getContNo());
		sqlbv2.put("MakeDate1", aLPEdorItemSchema.getMakeDate());
		sqlbv2.put("MakeDate2", aLPEdorItemSchema.getMakeDate());
		sqlbv2.put("MakeTime", aLPEdorItemSchema.getMakeTime());

		logger.debug(sql);

		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv2);
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询最近申请的保单批改信息错误！");
			return false;
		}

		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);
			LPContDB tLPContDB = new LPContDB();

			tLPContSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPContSchema.setContNo(tLPEdorItemSchema.getContNo());
			tLPContSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());

			tLPContDB.setSchema(tLPContSchema);
			if (!tLPContDB.getInfo()) {
				continue;
			} else {
				tLPContDB.setEdorNo(aLPContSchema.getEdorNo());
				tLPContDB.setEdorType(aLPContSchema.getEdorType());
				this.setSchema(tLPContDB.getSchema());
				return true;
			}
		}

		// 如果是第一次申请,得到承保保单的客户信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(aLPEdorItemSchema.getContNo());

		tLCContSet = tLCContDB.query();
		n = tLCContSet.size();
		logger.debug("------n:" + n);
		for (int i = 1; i <= n; i++) {
			tLCContSchema = tLCContSet.get(i);
			logger.debug("ContNo:" + aLPContSchema.getContNo());
			// 转换Schema
			Reflections tReflections = new Reflections();
			tReflections.transFields(tLPContSchema, tLCContSchema);

			tLPContSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPContSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			this.setSchema(tLPContSchema);
			return true;
		}
		return false;
	}
}

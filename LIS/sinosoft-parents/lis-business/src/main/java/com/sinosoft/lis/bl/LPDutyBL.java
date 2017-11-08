/*
 * <p>ClassName: LPDutyBL </p>
 * <p>Description: LPDutyBLSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全
 * @CreateDate：2002-07-25
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;

public class LPDutyBL extends LPDutySchema {
private static Logger logger = Logger.getLogger(LPDutyBL.class);
	// @Constructor
	public CErrors mErrors = new CErrors(); // 错误信息

	public LPDutyBL() {
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
	public LPDutySet queryAllLPDuty(LPEdorItemSchema aLPEdorItemSchema) {
		LCDutySet tLCDutySet = new LCDutySet();
		Reflections tReflections = new Reflections();
		LPDutySchema tLPDutySchema = new LPDutySchema();
		LPDutySet tLPDutySet = new LPDutySet();

		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(aLPEdorItemSchema.getPolNo());
		tLCDutySet = tLCDutyDB.query();
		if (tLCDutyDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询责任错误！");
		}

		for (int i = 1; i <= tLCDutySet.size(); i++) {
			tReflections.transFields(tLPDutySchema, tLCDutySet.get(i));
			tLPDutySchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPDutySchema.setEdorType(aLPEdorItemSchema.getEdorType());

			if (!this.queryLPDuty(tLPDutySchema)) {
				// return tLPDutySet; //del by zhangtao at 2005-06-27
				tLPDutySet.add(tLPDutySchema); // add by zhangtao at 2005-06-27
			}

			tLPDutySet.add(this.getSchema());
		}

		return tLPDutySet;
	}

	/**
	 * 查询所有的保全责任表数据，为保全重算
	 * 
	 * @param aLPEdorItemSchema
	 * @return
	 */
	public LPDutySet queryAllLPDutyForReCal(LPEdorItemSchema aLPEdorItemSchema) {
		Reflections tReflections = new Reflections();

		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(aLPEdorItemSchema.getPolNo());

//		LCDutySet tLCDutySet = tLCDutyDB.query();
		//排除增额交清
		String sqlset = "select * from lcduty where polno = '" + "?polno?" + "'  and char_length(trim(dutycode))!=10";
        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
        sqlbv1.sql(sqlset);
        sqlbv1.put("polno", aLPEdorItemSchema.getPolNo());
		LCDutySet tLCDutySet = tLCDutyDB.executeQuery(sqlbv1);

		LPDutySchema tLPDutySchema = new LPDutySchema();
		LPDutySet tLPDutySet = new LPDutySet();

		for (int i = 1; i <= tLCDutySet.size(); i++) {
			tReflections.transFields(tLPDutySchema, tLCDutySet.get(i));
			tLPDutySchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPDutySchema.setEdorType(aLPEdorItemSchema.getEdorType());

			if (!this.queryLPDuty(tLPDutySchema)) {
				return tLPDutySet;
			}

			tLPDutySet.add(this.getSchema());
		}

		// 查询保全责任表中，该保单，未保全确认的，责任代码不在C表中的，保全日期小于当前保全的数据
		String sql = "select * from lpduty where edorno = '"
				+ "?edorno?"
				+ "' and polno='"
				+ "?edorno?"
				+ "' and dutycode not in (select dutycode from lcduty where polno='"
				+ "?polno1?" + "') and (MakeDate<'"
				+ "?polno2?" + "' or (MakeDate='"
				+ "?polno3?" + "' and MakeTime<='"
				+ "?polno4?"
				+ "')) order by MakeDate,MakeTime desc";
		 SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	        sqlbv2.sql(sql);
	        sqlbv2.put("edorno", aLPEdorItemSchema.getEdorNo());
	        sqlbv2.put("polno1", aLPEdorItemSchema.getPolNo());
	        sqlbv2.put("polno2", aLPEdorItemSchema.getMakeDate());
	        sqlbv2.put("polno3", aLPEdorItemSchema.getMakeDate());
	        sqlbv2.put("polno4", aLPEdorItemSchema.getMakeTime());
		LPDutyDB tLPDutyDB = new LPDutyDB();
		LPDutySet LPDutySet2 = tLPDutyDB.executeQuery(sqlbv2);
		if (tLPDutyDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询责任错误！");
		}

		tLPDutySet.add(LPDutySet2);

		return tLPDutySet;
	}

	/**
	 * 查询该保单下的最新的责任信息项集合
	 * 
	 * @param aLPEdorItemSchema
	 * @return
	 */
	public LPDutySet queryAllLPDuty2(LPEdorItemSchema aLPEdorItemSchema) {
		LCDutySet tLCDutySet = new LCDutySet();
		Reflections tReflections = new Reflections();
		LPDutySet mLPDutySet = new LPDutySet();

		LCDutySchema tLCDutySchema = new LCDutySchema();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

		String sql;
		int m;
		int n;
		m = 0;
		n = 0;

		// 查找本次申请的保单批改信息
		sql = "select * from LPEdorItem where EdorNo='"
				+ "?PolNo?" + "' and PolNo='"
				+ "?PolNo?"
				+ "' order by MakeDate desc,MakeTime desc";
		logger.debug(sql);
		 SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
	        sqlbv3.sql(sql);
	        sqlbv3.put("EdorNo", aLPEdorItemSchema.getEdorNo());
	        sqlbv3.put("PolNo", aLPEdorItemSchema.getPolNo());
	      
		LPEdorItemDB iLPEdorItemDB = new LPEdorItemDB();
		iLPEdorItemDB.setSchema(aLPEdorItemSchema);
		LPEdorItemSet tempLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemSet = iLPEdorItemDB.query();
		if (!iLPEdorItemDB.mErrors.needDealError() && tLPEdorItemSet.size() > 0) {
			// delete EdorValiDate by Minim at 2003-12-17
			sql = "select * from LPEdorItem where EdorNo='"
					+ "?EdorNo1?" + "' and PolNo='"
					//modify by jiaqiangli 2008-09-01删除掉错误代码
					+ "?PolNo1?" + "' and (MakeDate<'"
					+ "?polno5?" + "' or (MakeDate='"
					+ "?polno6?" + "' and MakeTime<='"
					+ "?polno7?"
					+ "')) order by MakeDate,MakeTime desc";
			sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(sql);
			sqlbv3.put("EdorNo1", aLPEdorItemSchema.getEdorNo());
			sqlbv3.put("PolNo1", aLPEdorItemSchema.getPolNo());
			sqlbv3.put("polno5", aLPEdorItemSchema.getMakeDate());
			sqlbv3.put("polno6", aLPEdorItemSchema.getMakeDate());
			sqlbv3.put("polno7", aLPEdorItemSchema.getMakeTime());
		} else if (iLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全项目错误！");
			return null;
		}

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv3);
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全项目错误！");
			return null;
		}

		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);

			LPDutyDB tLPDutyDB = new LPDutyDB();

			tLPDutyDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPDutyDB.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPDutyDB.setEdorType(tLPEdorItemSchema.getEdorType());
			mLPDutySet = tLPDutyDB.query();
			if (tLPDutyDB.mErrors.needDealError()) {
				CError.buildErr(this, "查询责任失败！");
				return null;
			}

			if (mLPDutySet == null) {
				return null;
			}

			if (mLPDutySet.size() == 0) {
				continue;
			} else {
				for (int j = 1; j <= mLPDutySet.size(); j++) {
					mLPDutySet.get(j).setEdorNo(aLPEdorItemSchema.getEdorNo());
					mLPDutySet.get(j).setEdorType(
							aLPEdorItemSchema.getEdorType());
				}

				return mLPDutySet;
			}
		}

		// 查找已经申请确认的保单批改信息（没有保全确认）
		tLPEdorItemSet.clear();
		m = 0;
		n = 0;

		sql = "select * from LPEdorItem where EdorState='2' and PolNo='"
				+ "?PolNo6?"
				+ "' order by MakeDate desc,MakeTime desc";
		logger.debug(sql);
		 SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
	        sqlbv5.sql(sql);
	        sqlbv5.put("PolNo6", aLPEdorItemSchema.getPolNo());
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv5);
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全项目失败！");
			return null;
		}

		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);

			LPDutyDB tLPDutyDB = new LPDutyDB();

			tLPDutyDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPDutyDB.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPDutyDB.setEdorType(tLPEdorItemSchema.getEdorType());
			mLPDutySet = tLPDutyDB.query();
			if (tLPDutyDB.mErrors.needDealError()) {
				CError.buildErr(this, "查询责任失败！");
				return null;
			}

			if (mLPDutySet == null) {
				return null;
			}

			if (mLPDutySet.size() == 0) {
				continue;
			} else {
				for (int j = 1; j <= mLPDutySet.size(); j++) {
					mLPDutySet.get(j).setEdorNo(aLPEdorItemSchema.getEdorNo());
					mLPDutySet.get(j).setEdorType(
							aLPEdorItemSchema.getEdorType());
				}

				return mLPDutySet;
			}
		}

		n = 0;

		// 如果是第一次申请,得到承保保单的保费项信息
		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(aLPEdorItemSchema.getPolNo());
		tLCDutySet = tLCDutyDB.query();
		if (tLCDutyDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询责任失败！");
			return null;
		}

		n = tLCDutySet.size();
		logger.debug("------n:" + n);

		for (int i = 1; i <= n; i++) {
			tLCDutySchema = tLCDutySet.get(i);

			LPDutySchema tLPDutySchema = new LPDutySchema();

			// 转换Schema
			tReflections.transFields(tLPDutySchema, tLCDutySchema);

			tLPDutySchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPDutySchema.setEdorType(aLPEdorItemSchema.getEdorType());
			mLPDutySet.add(tLPDutySchema);
		}

		return mLPDutySet;
	}

	// 查询被保险人变动信息
	public boolean queryLPDuty(LPDutySchema aLPDutySchema) {
		LPDutySchema tLPDutySchema = new LPDutySchema();
		LPDutySet aLPDutySet = new LPDutySet();

		LCDutySchema tLCDutySchema = new LCDutySchema();
		LCDutySet tLCDutySet = new LCDutySet();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSchema aLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

		String sql;
		int m;
		int n;
		m = 0;
		n = 0;

		// 查找本次申请的保单批改信息
		aLPEdorItemSchema.setEdorNo(aLPDutySchema.getEdorNo());
		aLPEdorItemSchema.setEdorType(aLPDutySchema.getEdorType());
		aLPEdorItemSchema.setPolNo(aLPDutySchema.getPolNo());

		LPEdorItemDB iLPEdorItemDB = new LPEdorItemDB();
		iLPEdorItemDB.setSchema(aLPEdorItemSchema);

		sql = "select * from LPEdorItem where EdorNo='"
				+ "?EdorNo2?" + "' and PolNo='"
				+ "?PolNo8?"
				+ "' order by MakeDate desc,MakeTime desc";
		 SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
	        sqlbv5.sql(sql);
	        sqlbv5.put("EdorNo2", aLPEdorItemSchema.getEdorNo());
	        sqlbv5.put("PolNo8", aLPEdorItemSchema.getPolNo());
	      
		LPEdorItemSet tempLPEdorItemSet = new LPEdorItemSet();
		tempLPEdorItemSet = iLPEdorItemDB.query();
		if (!iLPEdorItemDB.mErrors.needDealError()
				&& tempLPEdorItemSet.size() >= 1) {
			// delete EdorValiDate by Minim at 2003-12-17
			sql = "select * from LPEdorItem where EdorNo='"
					+ "?EdorNo2?" + "' and PolNo='"
					+ "?PolNo11?" + "' and (MakeDate<'"
					+ "?polno12?"
					+ "' or (MakeDate='"
					+ "?polno13?"
					+ "' and MakeTime<='"
					+ "?polno14?"
					+ "')) order by MakeDate,MakeTime desc";
			sqlbv5=new SQLwithBindVariables();
			sqlbv5.sql(sql);
			sqlbv5.put("EdorNo2", aLPEdorItemSchema.getEdorNo());
			sqlbv5.put("PolNo11", aLPEdorItemSchema.getPolNo());
			sqlbv5.put("polno12", tempLPEdorItemSet.get(1).getMakeDate());
			sqlbv5.put("polno13", tempLPEdorItemSet.get(1).getMakeDate());
			sqlbv5.put("polno14", tempLPEdorItemSet.get(1).getMakeTime());
		} else if (iLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全项目错误！");
			return false;
		}

		logger.debug(sql);

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv5);
		if (iLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全项目错误！");
			return false;
		}

		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);

			LPDutyDB tLPDutyDB = new LPDutyDB();

			tLPDutySchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPDutySchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPDutySchema.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());
			tLPDutySchema.setDutyCode(aLPDutySchema.getDutyCode());

			tLPDutyDB.setSchema(tLPDutySchema);

			if (!tLPDutyDB.getInfo()) {
				continue;
			} else {
				tLPDutyDB.setEdorNo(aLPDutySchema.getEdorNo());
				tLPDutyDB.setEdorType(aLPDutySchema.getEdorType());
				this.setSchema(tLPDutyDB.getSchema());

				return true;
			}
		}

		// 查找已经申请确认的保单批改信息（没有保全确认）
		tLPEdorItemSet.clear();
		m = 0;
		n = 0;

		sql = "select * from LPEdorItem where EdorState='2' and PolNo='"
				+ "?PolNo16?"
				+ "' order by MakeDate desc,MakeTime desc";
		logger.debug(sql);
		 SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
	        sqlbv7.sql(sql);
	        sqlbv7.put("PolNo16", aLPEdorItemSchema.getPolNo());
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv7);
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全项目错误！");
			return false;
		}

		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);

			LPDutyDB tLPDutyDB = new LPDutyDB();

			tLPDutySchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPDutySchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPDutySchema.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());
			tLPDutySchema.setDutyCode(aLPDutySchema.getDutyCode());

			tLPDutyDB.setSchema(tLPDutySchema);

			if (!tLPDutyDB.getInfo()) {
				continue;
			} else {
				tLPDutyDB.setEdorNo(aLPDutySchema.getEdorNo());
				tLPDutyDB.setEdorType(aLPDutySchema.getEdorType());
				this.setSchema(tLPDutyDB.getSchema());

				return true;
			}
		}

		n = 0;

		// 如果是第一次申请,得到承保保单的客户信息
		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(aLPEdorItemSchema.getPolNo());

		tLCDutySet = tLCDutyDB.query();
		if (tLCDutyDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询责任错误！");
			return false;
		}

		n = tLCDutySet.size();
		logger.debug("------n:" + n);

		for (int i = 1; i <= n; i++) {
			tLCDutySchema = tLCDutySet.get(i);

			if (tLCDutySchema.getPolNo().equals(aLPDutySchema.getPolNo())
					&& tLCDutySchema.getDutyCode().equals(
							aLPDutySchema.getDutyCode())) {
				// 转换Schema
				Reflections tReflections = new Reflections();
				tReflections.transFields(tLPDutySchema, tLCDutySchema);

				tLPDutySchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
				tLPDutySchema.setEdorType(aLPEdorItemSchema.getEdorType());
				this.setSchema(tLPDutySchema);

				return true;
			}
		}

		return false;
	}

	// 查询上次保全责任项信息
	public boolean queryLastLPDuty(LPEdorItemSchema aLPEdorItemSchema,
			LPDutySchema aLPDutySchema) {
		LPDutySchema tLPDutySchema = new LPDutySchema();
		LPDutySet aLPDutySet = new LPDutySet();

		LCDutySchema tLCDutySchema = new LCDutySchema();
		LCDutySet tLCDutySet = new LCDutySet();
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
		// sql = "select EdorNo,PolNo,EdorType,EdorValiDate,MakeTime from
		// LPEdorItem where PolNo='" +
		sql = "select * from LPEdorItem where PolNo='"
				+ "?PolNo16?"
				+ "' and edorstate <>'0' and (MakeDate<'"
				+ "?PolNo17?" + "' or (MakeDate='"
				+ "?PolNo18?" + "' and MakeTime<='"
				+ "?PolNo19?"
				+ "')) order by MakeDate,MakeTime desc";
		 SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
	        sqlbv8.sql(sql);
	        sqlbv8.put("PolNo16", aLPEdorItemSchema.getPolNo());
	        sqlbv8.put("PolNo17", aLPEdorItemSchema.getMakeDate());
	        sqlbv8.put("PolNo18", aLPEdorItemSchema.getMakeDate());
	        sqlbv8.put("PolNo19", aLPEdorItemSchema.getMakeTime());
        
		logger.debug(sql);

		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv8);
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全项目错误！");
			return false;
		}

		m = tLPEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);

			LPDutyDB tLPDutyDB = new LPDutyDB();

			tLPDutySchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPDutySchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPDutySchema.setEdorType(tLPEdorItemSchema.getEdorType());
			logger.debug(tLPEdorItemSchema.getEdorType());
			tLPDutySchema.setDutyCode(aLPDutySchema.getDutyCode());

			tLPDutyDB.setSchema(tLPDutySchema);

			if (!tLPDutyDB.getInfo()) {
				continue;
			} else {
				tLPDutyDB.setEdorNo(aLPDutySchema.getEdorNo());
				tLPDutyDB.setEdorType(aLPDutySchema.getEdorType());
				this.setSchema(tLPDutyDB.getSchema());

				return true;
			}
		}

		// 如果是第一次申请,得到承保保单的客户信息
		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(aLPEdorItemSchema.getPolNo());

		tLCDutySet = tLCDutyDB.query();
		if (tLPEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询责任错误！");
			return false;
		}

		n = tLCDutySet.size();
		logger.debug("------n:" + n);

		for (int i = 1; i <= n; i++) {
			tLCDutySchema = tLCDutySet.get(i);
			logger.debug("PolNo:" + aLPDutySchema.getPolNo());

			if (tLCDutySchema.getPolNo().equals(aLPDutySchema.getPolNo())
					&& tLCDutySchema.getDutyCode().equals(
							aLPDutySchema.getDutyCode())) {
				// 转换Schema
				Reflections tReflections = new Reflections();
				tReflections.transFields(tLPDutySchema, tLCDutySchema);

				tLPDutySchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
				tLPDutySchema.setEdorType(aLPEdorItemSchema.getEdorType());
				this.setSchema(tLPDutySchema);

				return true;
			}
		}

		return false;
	}
}

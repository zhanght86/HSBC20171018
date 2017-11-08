package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCInsureAccFeeDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPInsureAccFeeDB;
import com.sinosoft.lis.schema.LCInsureAccFeeSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsureAccFeeSchema;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPInsureAccFeeSet;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author lizhuo
 * @version 1.0
 */
public class LPInsureAccFeeBL extends LPInsureAccFeeSchema {
private static Logger logger = Logger.getLogger(LPInsureAccFeeBL.class);
	public LPInsureAccFeeBL() {
	}

	// 查询变动信息
	public boolean queryLPInsureAccFee(
			LPInsureAccFeeSchema aLPInsureAccFeeSchema) {
		LPInsureAccFeeSchema tLPInsureAccFeeSchema = new LPInsureAccFeeSchema();
		LPInsureAccFeeSet tLPInsureAccFeeSet = new LPInsureAccFeeSet();

		LCInsureAccFeeSchema tLCInsureAccFeeSchema = new LCInsureAccFeeSchema();
		LCInsureAccFeeSet tLCInsureAccFeeSet = new LCInsureAccFeeSet();

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSchema aLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

		String sql;
		int m, n;
		m = 0;
		n = 0;

		// 查找本次申请的保单批改信息
		aLPEdorItemSchema.setEdorNo(aLPInsureAccFeeSchema.getEdorNo());
		aLPEdorItemSchema.setPolNo(aLPInsureAccFeeSchema.getPolNo());
		aLPEdorItemSchema.setContNo(aLPInsureAccFeeSchema.getContNo());

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);

		sql = "select EdorNo,PolNo,EdorType,EdorValiDate,MakeTime from LPEdorItem where EdorNo='"
				+ "?EdorNo1?"
				+ "' and PolNo='"
				+ "?EdorNo2?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("EdorNo1", aLPEdorItemSchema.getEdorNo());
		sqlbv1.put("EdorNo2", aLPEdorItemSchema.getPolNo());

		if (!tLPEdorItemDB.getInfo()) {
			sql = "select EdorNo,PolNo,EdorType,EdorValiDate,MakeTime from LPEdorItem where EdorNo='"
					+ "?polno11?"
					+ "' and PolNo='"
					+ "?polno12?"
					+ "' and MakeDate<='"
					+ "?polno13?"
					+ "' and MakeTime<='"
					+ "?polno14?"
					+ "' order by MakeDate desc,MakeTime desc";
			sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(sql);
			sqlbv1.put("polno11", aLPEdorItemSchema.getEdorNo());
			sqlbv1.put("polno12", aLPEdorItemSchema.getPolNo());
			sqlbv1.put("polno13", aLPEdorItemSchema.getMakeDate());
			sqlbv1.put("polno14", aLPEdorItemSchema.getMakeTime());
		}
		LPEdorItemDB iLPEdorItemDB = new LPEdorItemDB();
		iLPEdorItemDB.setSchema(aLPEdorItemSchema);
		tLPEdorItemSet = iLPEdorItemDB.executeQuery(sqlbv1);
		m = tLPEdorItemSet.size();
		for (int j = 1; j <= m; j++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(j);

			LPInsureAccFeeDB tLPInsureAccFeeDB = new LPInsureAccFeeDB();

			tLPInsureAccFeeSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPInsureAccFeeSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			tLPInsureAccFeeSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPInsureAccFeeSchema.setInsuAccNo(aLPInsureAccFeeSchema
					.getInsuAccNo());

			tLPInsureAccFeeDB.setSchema(tLPInsureAccFeeSchema);
			if (!tLPInsureAccFeeDB.getInfo()) {
				continue;
			} else {
				tLPInsureAccFeeDB.setEdorNo(aLPInsureAccFeeSchema.getEdorNo());
				tLPInsureAccFeeDB.setEdorType(aLPInsureAccFeeSchema
						.getEdorType());
				this.setSchema(tLPInsureAccFeeDB.getSchema());

				return true;
			}
		}

		// 查找已经申请确认的保单批改信息（没有保全确认）
		tLPEdorItemSet.clear();
		m = 0;
		n = 0;

		sql = "select * from LPEdorItem where EdorState='2' and PolNo='"
				+ "?polno16?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("polno16", aLPEdorItemSchema.getPolNo());
		logger.debug(sql);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv3);
		m = tLPEdorItemSet.size();
		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);

			LPInsureAccFeeDB tLPInsureAccFeeDB = new LPInsureAccFeeDB();

			tLPInsureAccFeeSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPInsureAccFeeSchema.setEdorType(tLPEdorItemSchema.getEdorType());
			tLPInsureAccFeeSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPInsureAccFeeSchema.setInsuAccNo(aLPInsureAccFeeSchema
					.getInsuAccNo());

			tLPInsureAccFeeDB.setSchema(tLPInsureAccFeeSchema);
			if (!tLPInsureAccFeeDB.getInfo()) {
				continue;
			} else {
				tLPInsureAccFeeDB.setEdorNo(aLPInsureAccFeeSchema.getEdorNo());
				tLPInsureAccFeeDB.setEdorType(aLPInsureAccFeeSchema
						.getEdorType());
				this.setSchema(tLPInsureAccFeeDB.getSchema());

				return true;
			}
		}

		// 如果是第一次申请,得到承保保单的客户信息
		LCInsureAccFeeDB tLCInsureAccFeeDB = new LCInsureAccFeeDB();
		tLCInsureAccFeeDB.setPolNo(aLPEdorItemSchema.getPolNo());
		tLCInsureAccFeeSet = tLCInsureAccFeeDB.query();

		n = tLCInsureAccFeeSet.size();
		for (int i = 1; i <= n; i++) {
			tLCInsureAccFeeSchema = tLCInsureAccFeeSet.get(i);

			if (tLCInsureAccFeeSchema.getPolNo().equals(
					aLPInsureAccFeeSchema.getPolNo())
					&& tLCInsureAccFeeSchema.getInsuAccNo().equals(
							aLPInsureAccFeeSchema.getInsuAccNo())) {
				// 转换Schema
				Reflections tReflections = new Reflections();
				tReflections.transFields(tLPInsureAccFeeSchema,
						tLCInsureAccFeeSchema);

				tLPInsureAccFeeSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
				tLPInsureAccFeeSchema.setEdorType(aLPEdorItemSchema
						.getEdorType());
				this.setSchema(tLPInsureAccFeeSchema);

				return true;
			}

		}
		return false;
	}

	public LPInsureAccFeeSet queryAllLPInsureAccFee(
			LPEdorItemSchema aLPEdorItemSchema) {
		LCInsureAccFeeSet tLCInsureAccFeeSet = new LCInsureAccFeeSet();
		Reflections tReflections = new Reflections();
		LPInsureAccFeeSet tLPInsureAccFeeSet = new LPInsureAccFeeSet();
		LPInsureAccFeeSchema tLPInsureAccFeeSchema = new LPInsureAccFeeSchema();

		LCInsureAccFeeDB tLCInsureAccFeeDB = new LCInsureAccFeeDB();
		tLCInsureAccFeeDB.setPolNo(aLPEdorItemSchema.getPolNo());
		tLCInsureAccFeeSet = tLCInsureAccFeeDB.query();
		for (int j = 1; j <= tLCInsureAccFeeSet.size(); j++) {
			tReflections.transFields(tLPInsureAccFeeSchema, tLCInsureAccFeeSet
					.get(j));
			tLPInsureAccFeeSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPInsureAccFeeSchema.setEdorType(aLPEdorItemSchema.getEdorType());
			if (!this.queryLPInsureAccFee(tLPInsureAccFeeSchema)) {
				return tLPInsureAccFeeSet;
			}
			tLPInsureAccFeeSet.add(this.getSchema());
		}
		return tLPInsureAccFeeSet;
	}
}

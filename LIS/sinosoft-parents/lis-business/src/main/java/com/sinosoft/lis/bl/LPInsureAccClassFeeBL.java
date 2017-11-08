package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCInsureAccClassFeeDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPInsureAccClassFeeDB;
import com.sinosoft.lis.schema.LCInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsureAccClassFeeSchema;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPInsureAccClassFeeSet;
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
public class LPInsureAccClassFeeBL extends LPInsureAccClassFeeSchema {
private static Logger logger = Logger.getLogger(LPInsureAccClassFeeBL.class);
	public LPInsureAccClassFeeBL() {
	}

	public boolean queryLPInsureAccClassFee(
			LPInsureAccClassFeeSchema aLPInsureAccClassFeeSchema) {
		LPInsureAccClassFeeSet tLPInsureAccClassFeeSet = new LPInsureAccClassFeeSet();
		LPInsureAccClassFeeSchema tLPInsureAccClassFeeSchema = new LPInsureAccClassFeeSchema();

		LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
		LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSchema aLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();

		String sql;
		int m, n;
		m = 0;
		n = 0;

		// 查找本次申请的保单批改信息
		aLPEdorItemSchema.setEdorNo(aLPInsureAccClassFeeSchema.getEdorNo());
		aLPEdorItemSchema.setPolNo(aLPInsureAccClassFeeSchema.getPolNo());
		aLPEdorItemSchema.setContNo(aLPInsureAccClassFeeSchema.getContNo());

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(aLPEdorItemSchema);

		sql = "select EdorNo,PolNo,EdorType,EdorValiDate,MakeTime from LPEdorItem where EdorNo='"
				+ "?EdorNo?"
				+ "' and PolNo='"
				+ "?PolNo?"
				+ "' order by MakeDate desc,MakeTime desc";
		if (!tLPEdorItemDB.getInfo()) {
			sql = "select EdorNo,PolNo,EdorType,EdorValiDate,MakeTime from LPEdorItem where EdorNo='"
					+ "?Edor?"
					+ "' and PolNo='"
					+ "?Pol?"
					+ "' and MakeDate<='"
					+ "?MakeDate?"
					+ "' and MakeTime<='"
					+ "?MakeTime?"
					+ "' order by MakeDate desc,MakeTime desc";
		}
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("EdorNo", aLPEdorItemSchema.getEdorNo());
		sqlbv.put("PolNo", aLPEdorItemSchema.getPolNo());
		sqlbv.put("Edor", aLPEdorItemSchema.getEdorNo());
		sqlbv.put("Pol", aLPEdorItemSchema.getPolNo());
		sqlbv.put("MakeDate", tLPEdorItemDB.getMakeDate());
		sqlbv.put("MakeTime", tLPEdorItemDB.getMakeTime());
		
		LPEdorItemDB iLPEdorItemDB = new LPEdorItemDB();
		iLPEdorItemDB.setSchema(aLPEdorItemSchema);
		tLPEdorItemSet = iLPEdorItemDB.executeQuery(sqlbv);
		m = tLPEdorItemSet.size();
		for (int j = 1; j <= m; j++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(j);

			LPInsureAccClassFeeDB tLPInsureAccClassFeeDB = new LPInsureAccClassFeeDB();
			tLPInsureAccClassFeeSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPInsureAccClassFeeSchema.setEdorType(tLPEdorItemSchema
					.getEdorType());
			tLPInsureAccClassFeeSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPInsureAccClassFeeSchema.setInsuAccNo(aLPInsureAccClassFeeSchema
					.getInsuAccNo());
			tLPInsureAccClassFeeSchema
					.setPayPlanCode(aLPInsureAccClassFeeSchema.getPayPlanCode());
			tLPInsureAccClassFeeSchema.setOtherNo(aLPInsureAccClassFeeSchema
					.getOtherNo());
			tLPInsureAccClassFeeSchema
					.setAccAscription(aLPInsureAccClassFeeSchema
							.getAccAscription());
			tLPInsureAccClassFeeSchema.setFeeCode(aLPInsureAccClassFeeSchema.getFeeCode());
			tLPInsureAccClassFeeDB.setSchema(tLPInsureAccClassFeeSchema);
			if (!tLPInsureAccClassFeeDB.getInfo()) {
				continue;
			} else {
				tLPInsureAccClassFeeDB.setEdorNo(aLPInsureAccClassFeeSchema
						.getEdorNo());
				tLPInsureAccClassFeeDB.setEdorType(aLPInsureAccClassFeeSchema
						.getEdorType());
				this.setSchema(tLPInsureAccClassFeeDB.getSchema());

				return true;
			}

		}

		// 查找已经申请确认的保单批改信息（没有保全确认）
		tLPEdorItemSet.clear();
		m = 0;
		n = 0;

		sql = "select * from LPEdorItem where EdorState='2' and PolNo='"
				+ "?PolNo?"
				+ "' order by MakeDate desc,MakeTime desc";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("PolNo", aLPEdorItemSchema.getPolNo());
		logger.debug(sql);
		tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv1);
		m = tLPEdorItemSet.size();
		for (int i = 1; i <= m; i++) {
			tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = tLPEdorItemSet.get(i);

			LPInsureAccClassFeeDB tLPInsureAccClassFeeDB = new LPInsureAccClassFeeDB();
			tLPInsureAccClassFeeSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tLPInsureAccClassFeeSchema.setEdorType(tLPEdorItemSchema
					.getEdorType());
			tLPInsureAccClassFeeSchema.setPolNo(tLPEdorItemSchema.getPolNo());
			tLPInsureAccClassFeeSchema.setInsuAccNo(aLPInsureAccClassFeeSchema
					.getInsuAccNo());
			tLPInsureAccClassFeeSchema
					.setPayPlanCode(aLPInsureAccClassFeeSchema.getPayPlanCode());
			tLPInsureAccClassFeeSchema.setOtherNo(aLPInsureAccClassFeeSchema
					.getOtherNo());
			tLPInsureAccClassFeeSchema
					.setAccAscription(aLPInsureAccClassFeeSchema
							.getAccAscription());
			tLPInsureAccClassFeeSchema.setFeeCode(aLPInsureAccClassFeeSchema.getFeeCode());
			tLPInsureAccClassFeeDB.setSchema(tLPInsureAccClassFeeSchema);

			if (!tLPInsureAccClassFeeDB.getInfo()) {
				continue;
			} else {
				tLPInsureAccClassFeeDB.setEdorNo(aLPInsureAccClassFeeSchema
						.getEdorNo());
				tLPInsureAccClassFeeDB.setEdorType(aLPInsureAccClassFeeSchema
						.getEdorType());
				this.setSchema(tLPInsureAccClassFeeDB.getSchema());

				return true;
			}
		}

		// 如果是第一次申请,得到承保保单的客户信息
		LCInsureAccClassFeeDB tLCInsureAccClassFeeDB = new LCInsureAccClassFeeDB();
		tLCInsureAccClassFeeDB.setContNo(aLPEdorItemSchema.getContNo());
		tLCInsureAccClassFeeSet = tLCInsureAccClassFeeDB.query();

		n = tLCInsureAccClassFeeSet.size();
		for (int i = 1; i <= n; i++) {
			tLCInsureAccClassFeeSchema = tLCInsureAccClassFeeSet.get(i);

			if (tLCInsureAccClassFeeSchema.getPolNo().equals(
					aLPInsureAccClassFeeSchema.getPolNo())
					&& tLCInsureAccClassFeeSchema.getInsuAccNo().equals(
							aLPInsureAccClassFeeSchema.getInsuAccNo())
					&& tLCInsureAccClassFeeSchema.getPayPlanCode().equals(
							aLPInsureAccClassFeeSchema.getPayPlanCode())
					&& tLCInsureAccClassFeeSchema.getOtherNo().equals(
							aLPInsureAccClassFeeSchema.getOtherNo())
					&& tLCInsureAccClassFeeSchema.getAccAscription().equals(
							aLPInsureAccClassFeeSchema.getAccAscription())
							&& tLCInsureAccClassFeeSchema.getFeeCode().equals(
									aLPInsureAccClassFeeSchema.getFeeCode())							) {
				// 转换Schema
				Reflections tReflections = new Reflections();
				tReflections.transFields(tLPInsureAccClassFeeSchema,
						tLCInsureAccClassFeeSchema);

				tLPInsureAccClassFeeSchema.setEdorNo(aLPEdorItemSchema
						.getEdorNo());
				tLPInsureAccClassFeeSchema.setEdorType(aLPEdorItemSchema
						.getEdorType());
				this.setSchema(tLPInsureAccClassFeeSchema);

				return true;
			}
		}
		return false;
	}

	public LPInsureAccClassFeeSet queryAllLPInsureAccClassFee(
			LPEdorItemSchema aLPEdorItemSchema) {
		LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
		Reflections tReflections = new Reflections();
		LPInsureAccClassFeeSet tLPInsureAccClassFeeSet = new LPInsureAccClassFeeSet();
		LPInsureAccClassFeeSchema tLPInsureAccClassFeeSchema = new LPInsureAccClassFeeSchema();

		LCInsureAccClassFeeDB tLCInsureAccClassFeeDB = new LCInsureAccClassFeeDB();
		tLCInsureAccClassFeeDB.setPolNo(aLPEdorItemSchema.getPolNo());
		tLCInsureAccClassFeeSet = tLCInsureAccClassFeeDB.query();
		for (int j = 1; j <= tLCInsureAccClassFeeSet.size(); j++) {
			tReflections.transFields(tLPInsureAccClassFeeSchema,
					tLCInsureAccClassFeeSet.get(j));
			tLPInsureAccClassFeeSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
			tLPInsureAccClassFeeSchema.setEdorType(aLPEdorItemSchema
					.getEdorType());
			if (!this.queryLPInsureAccClassFee(tLPInsureAccClassFeeSchema)) {
				return tLPInsureAccClassFeeSet;
			}
			tLPInsureAccClassFeeSet.add(this.getSchema());
		}
		return tLPInsureAccClassFeeSet;
	}
}

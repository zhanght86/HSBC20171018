/*
 * <p>ClassName: LPGrpAppntBL </p>
 * <p>Description: LLClaimSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database:
 * @CreateDate：2002-07-25
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.db.LPGrpAppntDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LPGrpAppntSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LPGrpAppntSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;

public class LPGrpAppntBL extends LPGrpAppntSchema {
private static Logger logger = Logger.getLogger(LPGrpAppntBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	// @Constructor
	public LPGrpAppntBL() {

	}

	public void setDefaultFields() {
		this.setMakeDate(PubFun.getCurrentDate());
		this.setMakeTime(PubFun.getCurrentTime());
		this.setModifyDate(PubFun.getCurrentDate());
		this.setModifyTime(PubFun.getCurrentTime());
	}

	public boolean queryLPGrpAppnt(LPGrpEdorItemSchema aLPGrpEdorItemSchema) {
		LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		LPGrpEdorItemSet tLPGrpEdorItemSet = new LPGrpEdorItemSet();
		Reflections tReflections = new Reflections();

		LPGrpAppntSet tLPGrpAppntSet = new LPGrpAppntSet();
		LPGrpAppntSchema tLPGrpAppntSchema = new LPGrpAppntSchema();
		LPGrpAppntSchema aLPGrpAppntSchema = new LPGrpAppntSchema();
		LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();

		String sql;
		int m;

		// 查找本次申请的保单批改信息
		sql = "select * from LPGrpEdorMain where EdorNo='"
				+ aLPGrpEdorItemSchema.getEdorNo() + "' and GrpContNo='"
				+ aLPGrpEdorItemSchema.getGrpContNo()
				+ "' order by MakeDate desc,MakeTime desc";
		logger.debug("-----:" + sql);

		LPGrpEdorItemDB iLPGrpEdorItemDB = new LPGrpEdorItemDB();
		iLPGrpEdorItemDB.setSchema(aLPGrpEdorItemSchema);
		LPGrpEdorItemSet mLPGrpEdorItemSet = new LPGrpEdorItemSet();
		mLPGrpEdorItemSet = iLPGrpEdorItemDB.query();
		if (!iLPGrpEdorItemDB.mErrors.needDealError()
				&& mLPGrpEdorItemSet.size() >= 1) {
			iLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSet.get(1));
			logger.debug("---sql:" + sql);
			// delete EdorValiDate by Minim at 2003-12-17
			sql = "select * from LPGrpEdorItem where EdorNo='"
					+ aLPGrpEdorItemSchema.getEdorNo() + "' and GrpContNo='"
					+ aLPGrpEdorItemSchema.getGrpContNo() + "' and (MakeDate<'"
					+ iLPGrpEdorItemDB.getMakeDate() + "' or (MakeDate='"
					+ iLPGrpEdorItemDB.getMakeDate() + "' and MakeTime<='"
					+ iLPGrpEdorItemDB.getMakeTime()
					+ "')) order by MakeDate,MakeTime desc";
		} else if (iLPGrpEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全项目错误！");
			return false;
		}
		logger.debug("---sqlend:" + sql);
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		// tLPGrpEdorItemDB.setSchema(aLPGrpEdorItemSchema);
		tLPGrpEdorItemSet = tLPGrpEdorItemDB.executeQuery(sql);
		if (tLPGrpEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全项目错误！");
			return false;
		}

		m = tLPGrpEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(i);
			LPGrpAppntDB tLPGrpAppntDB = new LPGrpAppntDB();
			tLPGrpAppntSchema.setEdorNo(tLPGrpEdorItemSchema.getEdorNo());
			tLPGrpAppntSchema.setGrpContNo(tLPGrpEdorItemSchema.getGrpContNo());
			tLPGrpAppntSchema.setEdorType(tLPGrpEdorItemSchema.getEdorType());

			tLPGrpAppntDB.setSchema(tLPGrpAppntSchema);

			if (!tLPGrpAppntDB.getInfo()) {
				continue;
			}

			aLPGrpAppntSchema = tLPGrpAppntDB.getSchema();
			aLPGrpAppntSchema.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
			aLPGrpAppntSchema.setEdorType(aLPGrpEdorItemSchema.getEdorType());
			this.setSchema(aLPGrpAppntSchema);
			return true;
		}
		// 查找已经申请确认的保单批改信息（没有保全确认）

		tLPGrpEdorItemSet.clear();
		m = 0;
		// tLPGrpEdorItemDB.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
		sql = "select * from LPGrpEdorItem where EdorState='2' and GrpContNo='"
				+ aLPGrpEdorItemSchema.getGrpContNo()
				+ "' order by MakeDate desc,MakeTime desc";
		logger.debug(sql);
		tLPGrpEdorItemSet = tLPGrpEdorItemDB.executeQuery(sql);
		if (tLPGrpEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询已经申请确认的保单保全项目错误！");
			return false;
		}

		m = tLPGrpEdorItemSet.size();

		for (int i = 1; i <= m; i++) {
			tLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(i);
			LPGrpAppntDB tLPGrpAppntDB = new LPGrpAppntDB();

			tLPGrpAppntSchema.setEdorNo(tLPGrpEdorItemSchema.getEdorNo());
			tLPGrpAppntSchema.setGrpContNo(tLPGrpEdorItemSchema.getGrpContNo());
			tLPGrpAppntSchema.setEdorType(tLPGrpEdorItemSchema.getEdorType());

			tLPGrpAppntDB.setSchema(tLPGrpAppntSchema);

			if (!tLPGrpAppntDB.getInfo()) {
				continue;
			}

			aLPGrpAppntSchema = tLPGrpAppntDB.getSchema();
			aLPGrpAppntSchema.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
			aLPGrpAppntSchema.setEdorType(aLPGrpEdorItemSchema.getEdorType());

			this.setSchema(aLPGrpAppntSchema);

			return true;
		}

		// 如果是第一次申请，得到承保保单信息。
		LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB();
		tLCGrpAppntDB.setGrpContNo(aLPGrpEdorItemSchema.getGrpContNo());
		tLCGrpAppntDB.getInfo();
		if (tLCGrpAppntDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询投保人信息失败！");
			return false;
		}

		// 转换Schema
		tReflections = new Reflections();
		tReflections.transFields(tLPGrpAppntSchema, tLCGrpAppntDB.getSchema());

		tLPGrpAppntSchema.setEdorNo(aLPGrpEdorItemSchema.getEdorNo());
		tLPGrpAppntSchema.setEdorType(aLPGrpEdorItemSchema.getEdorType());
		this.setSchema(tLPGrpAppntSchema);
		return true;
	}

}

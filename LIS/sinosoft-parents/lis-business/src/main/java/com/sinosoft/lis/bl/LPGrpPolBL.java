/*
 * <p>ClassName: LPGrpPolBL </p>
 * <p>Description: LLClaimSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database:
 * @CreateDate：2002-07-25
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LPGrpEdorMainDB;
import com.sinosoft.lis.db.LPGrpPolDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LPGrpEdorMainSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.utility.Reflections;

public class LPGrpPolBL extends LPGrpPolSchema {
private static Logger logger = Logger.getLogger(LPGrpPolBL.class);
	// @Constructor
	public LPGrpPolBL() {
	}

	public void setDefaultFields() {
		this.setMakeDate(PubFun.getCurrentDate());
		this.setMakeTime(PubFun.getCurrentTime());
		this.setModifyDate(PubFun.getCurrentDate());
		this.setModifyTime(PubFun.getCurrentTime());
	}

	public boolean queryLPGrpPol(LPGrpEdorMainSchema aLPGrpEdorMainSchema) {
		LPGrpEdorMainSchema tLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
		LPGrpEdorMainSet tLPGrpEdorMainSet = new LPGrpEdorMainSet();
		Reflections tReflections = new Reflections();

		LPGrpPolSet tLPGrpPolSet = new LPGrpPolSet();
		LPGrpPolSchema tLPGrpPolSchema = new LPGrpPolSchema();
		LPGrpPolSchema aLPGrpPolSchema = new LPGrpPolSchema();
		LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();

		String sql;
		int m;

		// 查找本次申请的保单批改信息

		sql = "select EdorNo,GrpPolNo,EdorType,EdorValiDate,MakeTime from LPGrpEdorMain where EdorNo='"
				+ aLPGrpEdorMainSchema.getEdorNo() + "' and GrpPolNo='"
				// +aLPGrpEdorMainSchema.getGrpPolNo()
				+ "' order by MakeDate desc,MakeTime desc";
		logger.debug("-----:" + sql);

		LPGrpEdorMainDB iLPGrpEdorMainDB = new LPGrpEdorMainDB();
		iLPGrpEdorMainDB.setSchema(aLPGrpEdorMainSchema);
		if (iLPGrpEdorMainDB.getInfo()) {
			logger.debug("---sql:" + sql);
			// delete EdorValiDate by Minim at 2003-12-17
			sql = "select EdorNo,GrpPolNo,EdorType,EdorValiDate,MakeTime from LPGrpEdorMain where EdorNo='"
					+ aLPGrpEdorMainSchema.getEdorNo()
					+ "' and GrpPolNo='"
					// +aLPGrpEdorMainSchema.getGrpPolNo()
					+ "' and MakeDate<='"
					+ iLPGrpEdorMainDB.getMakeDate()
					+ "' and MakeTime<='"
					+ iLPGrpEdorMainDB.getMakeTime()
					+ "' order by MakeDate,MakeTime desc";
		}
		logger.debug("---sqlend:" + sql);
		LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
		tLPGrpEdorMainDB.setSchema(aLPGrpEdorMainSchema);
		tLPGrpEdorMainSet = tLPGrpEdorMainDB.executeQuery(sql);
		m = tLPGrpEdorMainSet.size();

		for (int i = 1; i <= m; i++) {
			tLPGrpEdorMainSchema = tLPGrpEdorMainSet.get(i);
			LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();
			tLPGrpPolSchema.setEdorNo(tLPGrpEdorMainSchema.getEdorNo());
			// tLPGrpPolSchema.setGrpPolNo(tLPGrpEdorMainSchema.getGrpPolNo());
			// tLPGrpPolSchema.setEdorType(tLPGrpEdorMainSchema.getEdorType());

			tLPGrpPolDB.setSchema(tLPGrpPolSchema);

			if (!tLPGrpPolDB.getInfo()) {
				continue;
			}

			aLPGrpPolSchema = tLPGrpPolDB.getSchema();
			aLPGrpPolSchema.setEdorNo(aLPGrpEdorMainSchema.getEdorNo());
			// aLPGrpPolSchema.setEdorType(aLPGrpEdorMainSchema.getEdorType());
			this.setSchema(aLPGrpPolSchema);
			return true;
		}
		// 查找已经申请确认的保单批改信息（没有保全确认）

		tLPGrpEdorMainSet.clear();
		m = 0;
		// tLPGrpEdorMainDB.setEdorNo(aLPGrpEdorMainSchema.getEdorNo());
		sql = "select EdorNo,GrpPolNo,EdorType,EdorValiDate,MakeTime from LPGrpEdorMain where EdorState='2' and GrpPolNo='"
				// +aLPGrpEdorMainSchema.getGrpPolNo()
				+ "' order by MakeDate desc,MakeTime desc";
		logger.debug(sql);
		tLPGrpEdorMainSet = tLPGrpEdorMainDB.executeQuery(sql);
		m = tLPGrpEdorMainSet.size();

		for (int i = 1; i <= m; i++) {
			tLPGrpEdorMainSchema = tLPGrpEdorMainSet.get(i);
			LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();

			tLPGrpPolSchema.setEdorNo(tLPGrpEdorMainSchema.getEdorNo());
			// tLPGrpPolSchema.setGrpPolNo(tLPGrpEdorMainSchema.getGrpPolNo());
			// tLPGrpPolSchema.setEdorType(tLPGrpEdorMainSchema.getEdorType());

			tLPGrpPolDB.setSchema(tLPGrpPolSchema);

			if (!tLPGrpPolDB.getInfo()) {
				continue;
			}

			aLPGrpPolSchema = tLPGrpPolDB.getSchema();
			aLPGrpPolSchema.setEdorNo(aLPGrpEdorMainSchema.getEdorNo());
			// aLPGrpPolSchema.setEdorType(aLPGrpEdorMainSchema.getEdorType());

			this.setSchema(aLPGrpPolSchema);

			return true;
		}

		// 如果是第一次申请，得到承保保单信息。
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		// tLCGrpPolDB.setGrpPolNo(aLPGrpEdorMainSchema.getGrpPolNo());

		if (!tLCGrpPolDB.getInfo()) {
			return false;
		}

		tLCGrpPolSchema = tLCGrpPolDB.getSchema();
		// 转换Schema
		tReflections.transFields(aLPGrpPolSchema, tLCGrpPolSchema);

		aLPGrpPolSchema.setEdorNo(aLPGrpEdorMainSchema.getEdorNo());
		// aLPGrpPolSchema.setEdorType(aLPGrpEdorMainSchema.getEdorType());

		this.setSchema(aLPGrpPolSchema);
		return true;
	}

	// 查询上次保全被保险人变动信息
	public boolean queryLastLPGrpPol(LPGrpEdorMainSchema aLPGrpEdorMainSchema,
			LPGrpPolSchema aLPGrpPolSchema) {
		LPGrpPolSchema tLPGrpPolSchema = new LPGrpPolSchema();
		LPGrpPolSet aLPGrpPolSet = new LPGrpPolSet();

		LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		LPGrpEdorMainSchema tLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
		LPGrpEdorMainSet tLPGrpEdorMainSet = new LPGrpEdorMainSet();

		String sql;
		int m, n;
		m = 0;
		n = 0;

		// 查找最近申请的保单批改信息
		LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
		tLPGrpEdorMainDB.setSchema(aLPGrpEdorMainSchema);

		// delete EdorValiDate by Minim at 2003-12-17
		sql = "select EdorNo,GrpPolNo,EdorType,EdorValiDate,MakeTime from LPGrpEdorMain where GrpPolNo='"
				// +aLPGrpEdorMainSchema.getGrpPolNo()
				+ "' and edorstate <>'0' and MakeDate<='"
				+ aLPGrpEdorMainSchema.getMakeDate()
				+ "' and MakeTime<'"
				+ aLPGrpEdorMainSchema.getMakeTime()
				+ "' order by MakeDate desc,MakeTime desc";
		logger.debug(sql);

		tLPGrpEdorMainSet = tLPGrpEdorMainDB.executeQuery(sql);
		m = tLPGrpEdorMainSet.size();

		for (int i = 1; i <= m; i++) {
			tLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
			tLPGrpEdorMainSchema = tLPGrpEdorMainSet.get(i);
			LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();

			tLPGrpPolSchema.setEdorNo(tLPGrpEdorMainSchema.getEdorNo());
			// tLPGrpPolSchema.setGrpPolNo(tLPGrpEdorMainSchema.getGrpPolNo());
			// tLPGrpPolSchema.setEdorType(tLPGrpEdorMainSchema.getEdorType());
			// logger.debug(tLPGrpEdorMainSchema.getEdorType());

			tLPGrpPolDB.setSchema(tLPGrpPolSchema);
			if (!tLPGrpPolDB.getInfo()) {
				continue;
			} else {
				tLPGrpPolDB.setEdorNo(aLPGrpPolSchema.getEdorNo());
				tLPGrpPolDB.setEdorType(aLPGrpPolSchema.getEdorType());
				this.setSchema(tLPGrpPolDB.getSchema());
				return true;
			}
		}

		// 如果是第一次申请,得到承保保单的客户信息
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		// tLCGrpPolDB.setGrpPolNo(aLPGrpEdorMainSchema.getGrpPolNo());

		tLCGrpPolSet = tLCGrpPolDB.query();
		n = tLCGrpPolSet.size();
		logger.debug("------n:" + n);
		for (int i = 1; i <= n; i++) {
			tLCGrpPolSchema = tLCGrpPolSet.get(i);
			logger.debug("PolNo:" + aLPGrpPolSchema.getGrpPolNo());
			// 转换Schema
			Reflections tReflections = new Reflections();
			tReflections.transFields(tLPGrpPolSchema, tLCGrpPolSchema);

			tLPGrpPolSchema.setEdorNo(aLPGrpEdorMainSchema.getEdorNo());
			// tLPGrpPolSchema.setEdorType(aLPGrpEdorMainSchema.getEdorType());
			this.setSchema(tLPGrpPolSchema);
			return true;
		}
		return false;
	}
}

/*
 * <p>ClassName: LCGrpPolBL </p>
 * <p>Description: LCGrpPolSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-04-01
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LBGrpPolDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LBGrpPolSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.vschema.LBGrpPolSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.Reflections;

public class LCGrpPolBL extends LCGrpPolSchema {
private static Logger logger = Logger.getLogger(LCGrpPolBL.class);
	// @Constructor
	public LCGrpPolBL() {
		this.setMakeDate(PubFun.getCurrentDate());
		this.setMakeTime(PubFun.getCurrentTime());
		this.setModifyDate(PubFun.getCurrentDate());
		this.setModifyTime(PubFun.getCurrentTime());
	}

	public void setDefaultFields() {
		this.setMakeDate(PubFun.getCurrentDate());
		this.setMakeTime(PubFun.getCurrentTime());
		this.setModifyDate(PubFun.getCurrentDate());
		this.setModifyTime(PubFun.getCurrentTime());
	}

	/**
	 * 从保单表和保单备份表读取信息 返回true或false
	 */
	public boolean getInfo() {
		Reflections tR = new Reflections();
		LCGrpPolDB tDB = new LCGrpPolDB();
		tDB.setSchema(this);
		if (!tDB.getInfo()) { // 如果查询失败，查询B表
			LBGrpPolDB tDBB = new LBGrpPolDB();
			LBGrpPolSchema tLBGrpPolSchema = new LBGrpPolSchema();
			tR.transFields(tLBGrpPolSchema, this.getSchema());
			tDBB.setSchema(tLBGrpPolSchema);
			if (!tDBB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCGrpPolBL";
				tError.functionName = "getInfo";
				tError.errorMessage = "没有查询到保单表";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				LCGrpPolSchema tS = new LCGrpPolSchema();
				tR.transFields(tS, tDBB.getSchema());
				this.setSchema(tS);
			}
		} else {
			this.setSchema(tDB.getSchema());
		}
		return true;
	}

	/**
	 * 从保单表和保单备份表读取信息 返回LCGrpPolSet
	 * 
	 */
	public LCGrpPolSet query() {
		Reflections tR = new Reflections();
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setSchema(this.getSchema());
		tLCGrpPolSet = tLCGrpPolDB.query();
		if (tLCGrpPolSet.size() == 0) {
			LBGrpPolSet tLBGrpPolSet = new LBGrpPolSet();
			LBGrpPolDB tLBGrpPolDB1 = new LBGrpPolDB();
			tR.transFields(tLBGrpPolDB1.getSchema(), this.getSchema());
			tLBGrpPolSet = tLBGrpPolDB1.query();
			if (tLBGrpPolSet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCGrpPolBL";
				tError.functionName = "query";
				tError.errorMessage = "没有查询到保单表";
				this.mErrors.addOneError(tError);
				return tLCGrpPolSet;
			} else {
				tLCGrpPolSet.add(this.getSchema());
				tR.transFields(tLCGrpPolSet, tLBGrpPolSet);
			}
		}
		return tLCGrpPolSet;
	}

	/**
	 * 从保单表和保单备份表读取信息 返回LBGrpPolSet
	 * 
	 */
	public LCGrpPolSet executeQuery(String sql) {
		Reflections tR = new Reflections();
		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setSchema(this.getSchema());
		tLCGrpPolSet = tLCGrpPolDB.executeQuery(sql);
		if (tLCGrpPolSet.size() == 0) {
			LBGrpPolSet tLBGrpPolSet = new LBGrpPolSet();
			LBGrpPolDB tLBGrpPolDB1 = new LBGrpPolDB();
			tR.transFields(tLBGrpPolDB1.getSchema(), this.getSchema());
			tLBGrpPolSet = tLBGrpPolDB1.executeQuery(sql);
			if (tLBGrpPolSet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCGrpPolBL";
				tError.functionName = "query";
				tError.errorMessage = "没有查询到保单表";
				this.mErrors.addOneError(tError);
				return tLCGrpPolSet;
			} else {
				tLCGrpPolSet.add(this.getSchema());
				tR.transFields(tLCGrpPolSet, tLBGrpPolSet);
			}
		}
		return tLCGrpPolSet;
	}

	public static void main(String[] args) {
		LCGrpPolBL t = new LCGrpPolBL();
		LCGrpPolSet tSet = new LCGrpPolSet();
		t.setMakeDate("");
		t.setMakeTime("");
		t.setModifyDate("");
		t.setModifyTime("");
		// t.setPolNo("86110020020210300207");
		// 测试getInfo
		t.setGrpPolNo("11111");
		logger.debug(t.getInfo());
		logger.debug(t.mErrors.getFirstError());
		logger.debug(t.encode());
		// 测试query
		// tSet=t.query();
		// 测试executeQuery
		// String tSQL ="select * from LCGrpPol where 1=2";
		// tSet=t.executeQuery(tSQL);
		// logger.debug(tSet.size());
		// logger.debug(tSet.encode());
		// logger.debug(t.mErrors.getFirstError());
	}

}

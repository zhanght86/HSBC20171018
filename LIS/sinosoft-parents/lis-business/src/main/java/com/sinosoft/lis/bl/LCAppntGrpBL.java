/*
 * <p>ClassName: LCAppntGrpBL </p>
 * <p>Description: LCAppntGrpSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-04-01
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LBAppntGrpDB;
import com.sinosoft.lis.db.LCAppntGrpDB;
import com.sinosoft.lis.schema.LBAppntGrpSchema;
import com.sinosoft.lis.schema.LCAppntGrpSchema;
import com.sinosoft.lis.vschema.LBAppntGrpSet;
import com.sinosoft.lis.vschema.LCAppntGrpSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.Reflections;

public class LCAppntGrpBL extends LCAppntGrpSchema {
private static Logger logger = Logger.getLogger(LCAppntGrpBL.class);
	// @Constructor
	public LCAppntGrpBL() {
	}

	/**
	 * 从多投保人（集体投保人）表和多投保人（集体投保人）备份表读取信息 返回true或false
	 */
	public boolean getInfo() {
		Reflections tR = new Reflections();
		LCAppntGrpDB tDB = new LCAppntGrpDB();
		tDB.setSchema(this);
		if (!tDB.getInfo()) // 如果查询失败，查询B表
		{
			LBAppntGrpDB tDBB = new LBAppntGrpDB();
			LBAppntGrpSchema tLBAppntGrpSchema = new LBAppntGrpSchema();
			tR.transFields(tLBAppntGrpSchema, this.getSchema());
			tDBB.setSchema(tLBAppntGrpSchema);
			if (!tDBB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCAppntGrpBL";
				tError.functionName = "getInfo";
				tError.errorMessage = "没有查询到多投保人（集体投保人）表";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				LCAppntGrpSchema tS = new LCAppntGrpSchema();
				tR.transFields(tS, tDBB.getSchema());
				this.setSchema(tS);
			}
		} else {
			this.setSchema(tDB.getSchema());
		}
		return true;
	}

	/**
	 * 从多投保人（集体投保人）表和多投保人（集体投保人）备份表读取信息 返回LCAppntGrpSet
	 * 
	 */
	public LCAppntGrpSet query() {
		Reflections tR = new Reflections();
		LCAppntGrpSet tLCAppntGrpSet = new LCAppntGrpSet();
		LCAppntGrpDB tLCAppntGrpDB = new LCAppntGrpDB();
		tLCAppntGrpDB.setSchema(this.getSchema());
		tLCAppntGrpSet = tLCAppntGrpDB.query();
		if (tLCAppntGrpSet.size() == 0) {
			LBAppntGrpSet tLBAppntGrpSet = new LBAppntGrpSet();
			LBAppntGrpDB tLBAppntGrpDB1 = new LBAppntGrpDB();
			tR.transFields(tLBAppntGrpDB1.getSchema(), this.getSchema());
			tLBAppntGrpSet = tLBAppntGrpDB1.query();
			if (tLBAppntGrpSet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCAppntGrpBL";
				tError.functionName = "query";
				tError.errorMessage = "没有查询到多投保人（集体投保人）表";
				this.mErrors.addOneError(tError);
				return tLCAppntGrpSet;
			} else {
				tLCAppntGrpSet.add(this.getSchema());
				tR.transFields(tLCAppntGrpSet, tLBAppntGrpSet);
			}
		}
		return tLCAppntGrpSet;
	}

	/**
	 * 从多投保人（集体投保人）表和多投保人（集体投保人）备份表读取信息 返回LCAppntGrpSet
	 * 
	 */
	public LCAppntGrpSet executeQuery(String sql) {
		Reflections tR = new Reflections();
		LCAppntGrpSet tLCAppntGrpSet = new LCAppntGrpSet();
		LCAppntGrpDB tLCAppntGrpDB = new LCAppntGrpDB();
		tLCAppntGrpDB.setSchema(this.getSchema());
		tLCAppntGrpSet = tLCAppntGrpDB.executeQuery(sql);
		if (tLCAppntGrpSet.size() == 0) {
			LBAppntGrpSet tLBAppntGrpSet = new LBAppntGrpSet();
			LBAppntGrpDB tLBAppntGrpDB1 = new LBAppntGrpDB();
			tR.transFields(tLBAppntGrpDB1.getSchema(), this.getSchema());
			tLBAppntGrpSet = tLBAppntGrpDB1.executeQuery(sql);
			if (tLBAppntGrpSet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCAppntGrpBL";
				tError.functionName = "query";
				tError.errorMessage = "没有查询到多投保人（集体投保人）表";
				this.mErrors.addOneError(tError);
				return tLCAppntGrpSet;
			} else {
				tLCAppntGrpSet.add(this.getSchema());
				tR.transFields(tLCAppntGrpSet, tLBAppntGrpSet);
			}
		}
		return tLCAppntGrpSet;
	}

	public static void main(String[] args) {
		// 添加测试代码
	}

}

/*
 * <p>ClassName: LCGrpAppntBL </p>
 * <p>Description: LCGrpAppntSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-04-01
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LBAppntGrpDB;
import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.schema.LBAppntGrpSchema;
import com.sinosoft.lis.schema.LCGrpAppntSchema;
import com.sinosoft.lis.vschema.LBAppntGrpSet;
import com.sinosoft.lis.vschema.LCGrpAppntSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.Reflections;

public class LCGrpAppntBL extends LCGrpAppntSchema {
private static Logger logger = Logger.getLogger(LCGrpAppntBL.class);
	// @Constructor
	public LCGrpAppntBL() {
	}

	/**
	 * 从多投保人（集体投保人）表和多投保人（集体投保人）备份表读取信息 返回true或false
	 */
	public boolean getInfo() {
		Reflections tR = new Reflections();
		LCGrpAppntDB tDB = new LCGrpAppntDB();
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
				tError.moduleName = "LCGrpAppntBL";
				tError.functionName = "getInfo";
				tError.errorMessage = "没有查询到多投保人（集体投保人）表";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				LCGrpAppntSchema tS = new LCGrpAppntSchema();
				tR.transFields(tS, tDBB.getSchema());
				this.setSchema(tS);
			}
		} else {
			this.setSchema(tDB.getSchema());
		}
		return true;
	}

	/**
	 * 从多投保人（集体投保人）表和多投保人（集体投保人）备份表读取信息 返回LCGrpAppntSet
	 * 
	 */
	public LCGrpAppntSet query() {
		Reflections tR = new Reflections();
		LCGrpAppntSet tLCGrpAppntSet = new LCGrpAppntSet();
		LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB();
		tLCGrpAppntDB.setSchema(this.getSchema());
		tLCGrpAppntSet = tLCGrpAppntDB.query();
		if (tLCGrpAppntSet.size() == 0) {
			LBAppntGrpSet tLBAppntGrpSet = new LBAppntGrpSet();
			LBAppntGrpDB tLBAppntGrpDB1 = new LBAppntGrpDB();
			tR.transFields(tLBAppntGrpDB1.getSchema(), this.getSchema());
			tLBAppntGrpSet = tLBAppntGrpDB1.query();
			if (tLBAppntGrpSet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCGrpAppntBL";
				tError.functionName = "query";
				tError.errorMessage = "没有查询到多投保人（集体投保人）表";
				this.mErrors.addOneError(tError);
				return tLCGrpAppntSet;
			} else {
				tLCGrpAppntSet.add(this.getSchema());
				tR.transFields(tLCGrpAppntSet, tLBAppntGrpSet);
			}
		}
		return tLCGrpAppntSet;
	}

	/**
	 * 从多投保人（集体投保人）表和多投保人（集体投保人）备份表读取信息 返回LCGrpAppntSet
	 * 
	 */
	public LCGrpAppntSet executeQuery(String sql) {
		Reflections tR = new Reflections();
		LCGrpAppntSet tLCGrpAppntSet = new LCGrpAppntSet();
		LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB();
		tLCGrpAppntDB.setSchema(this.getSchema());
		tLCGrpAppntSet = tLCGrpAppntDB.executeQuery(sql);
		if (tLCGrpAppntSet.size() == 0) {
			LBAppntGrpSet tLBAppntGrpSet = new LBAppntGrpSet();
			LBAppntGrpDB tLBAppntGrpDB1 = new LBAppntGrpDB();
			tR.transFields(tLBAppntGrpDB1.getSchema(), this.getSchema());
			tLBAppntGrpSet = tLBAppntGrpDB1.executeQuery(sql);
			if (tLBAppntGrpSet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCGrpAppntBL";
				tError.functionName = "query";
				tError.errorMessage = "没有查询到多投保人（集体投保人）表";
				this.mErrors.addOneError(tError);
				return tLCGrpAppntSet;
			} else {
				tLCGrpAppntSet.add(this.getSchema());
				tR.transFields(tLCGrpAppntSet, tLBAppntGrpSet);
			}
		}
		return tLCGrpAppntSet;
	}

	public static void main(String[] args) {
		// 添加测试代码
	}

}

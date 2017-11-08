/*
 * <p>ClassName: LCPremBL </p>
 * <p>Description: LCPremSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-04-01
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LBPremDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LBPremSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.vschema.LBPremSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;

public class LCPremBL extends LCPremSchema {
private static Logger logger = Logger.getLogger(LCPremBL.class);
	// @Constructor
	public LCPremBL() {
		this.setModifyDate(PubFun.getCurrentDate());
		this.setModifyTime(PubFun.getCurrentTime());
	}

	/**
	 * 设置默认的字段属性
	 */
	public void setDefaultFields() {
		this.setModifyDate(PubFun.getCurrentDate());
		this.setModifyTime(PubFun.getCurrentTime());
	}

	/**
	 * 从保费项表和保费项备份表读取信息 返回true或false
	 */
	public boolean getInfo() {
		Reflections tR = new Reflections();
		LCPremDB tDB = new LCPremDB();
		tDB.setSchema(this);
		if (!tDB.getInfo()) // 如果查询失败，查询B表
		{
			LBPremDB tDBB = new LBPremDB();
			LBPremSchema tLBPremSchema = new LBPremSchema();
			tR.transFields(tLBPremSchema, this.getSchema());
			tDBB.setSchema(tLBPremSchema);
			if (!tDBB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCPremBL";
				tError.functionName = "getInfo";
				tError.errorMessage = "没有查询到保费项表";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				LCPremSchema tS = new LCPremSchema();
				tR.transFields(tS, tDBB.getSchema());
				this.setSchema(tS);
			}
		} else {
			this.setSchema(tDB.getSchema());
		}
		return true;
	}

	/**
	 * 从保费项表和保费项备份表读取信息 返回LCPremSet
	 * 
	 */
	public LCPremSet query() {
		Reflections tR = new Reflections();
		LCPremSet tLCPremSet = new LCPremSet();
		LCPremDB tLCPremDB = new LCPremDB();
		tLCPremDB.setSchema(this.getSchema());
		tLCPremSet = tLCPremDB.query();
		if (tLCPremSet.size() == 0) {
			LBPremSet tLBPremSet = new LBPremSet();
			LBPremDB tLBPremDB1 = new LBPremDB();
			tR.transFields(tLBPremDB1.getSchema(), this.getSchema());
			tLBPremSet = tLBPremDB1.query();
			if (tLBPremSet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCPremBL";
				tError.functionName = "query";
				tError.errorMessage = "没有查询到保费项表";
				this.mErrors.addOneError(tError);
				return tLCPremSet;
			} else {
				tLCPremSet.add(this.getSchema());
				tR.transFields(tLCPremSet, tLBPremSet);
			}
		}
		return tLCPremSet;
	}

	/**
	 * 从保费项表和保费项备份表读取信息 返回LCPremSet
	 * 
	 */
	public LCPremSet executeQuery(SQLwithBindVariables sql) {
		Reflections tR = new Reflections();
		LCPremSet tLCPremSet = new LCPremSet();
		LCPremDB tLCPremDB = new LCPremDB();
		tLCPremDB.setSchema(this.getSchema());
		tLCPremSet = tLCPremDB.executeQuery(sql);
		if (tLCPremSet.size() == 0) {
			LBPremSet tLBPremSet = new LBPremSet();
			LBPremDB tLBPremDB1 = new LBPremDB();
			tR.transFields(tLBPremDB1.getSchema(), this.getSchema());
			tLBPremSet = tLBPremDB1.executeQuery(sql);
			if (tLBPremSet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCPremBL";
				tError.functionName = "query";
				tError.errorMessage = "没有查询到保费项表";
				this.mErrors.addOneError(tError);
				return tLCPremSet;
			} else {
				tLCPremSet.add(this.getSchema());
				tR.transFields(tLCPremSet, tLBPremSet);
			}
		}
		return tLCPremSet;
	}

	public static void main(String[] args) {
		// 添加测试代码
	}

}

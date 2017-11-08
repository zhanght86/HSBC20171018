/*
 * <p>ClassName: LCDutyBL </p>
 * <p>Description: LCDutySchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-04-01
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LBDutyDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LBDutySchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.vschema.LBDutySet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;

public class LCDutyBL extends LCDutySchema {
private static Logger logger = Logger.getLogger(LCDutyBL.class);

	// @Constructor
	public LCDutyBL() {
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
	 * 从保险责任表和保险责任备份表读取信息 返回true或false
	 */
	public boolean getInfo() {
		Reflections tR = new Reflections();
		LCDutyDB tDB = new LCDutyDB();
		tDB.setSchema(this);
		if (!tDB.getInfo()) // 如果查询失败，查询B表
		{
			LBDutyDB tDBB = new LBDutyDB();
			LBDutySchema tLBDutySchema = new LBDutySchema();
			tR.transFields(tLBDutySchema, this.getSchema());
			tDBB.setSchema(tLBDutySchema);
			if (!tDBB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCDutyBL";
				tError.functionName = "getInfo";
				tError.errorMessage = "没有查询到保险责任表";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				LCDutySchema tS = new LCDutySchema();
				tR.transFields(tS, tDBB.getSchema());
				this.setSchema(tS);
			}
		} else {
			this.setSchema(tDB.getSchema());
		}
		return true;
	}

	/**
	 * 从保险责任表和保险责任备份表读取信息 返回LCDutySet
	 * 
	 */
	public LCDutySet query() {
		Reflections tR = new Reflections();
		LCDutySet tLCDutySet = new LCDutySet();
		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setSchema(this.getSchema());
		tLCDutySet = tLCDutyDB.query();
		if (tLCDutySet.size() == 0) {
			LBDutySet tLBDutySet = new LBDutySet();
			LBDutyDB tLBDutyDB1 = new LBDutyDB();
			tR.transFields(tLBDutyDB1.getSchema(), this.getSchema());
			tLBDutySet = tLBDutyDB1.query();
			if (tLBDutySet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCDutyBL";
				tError.functionName = "query";
				tError.errorMessage = "没有查询到保险责任表";
				this.mErrors.addOneError(tError);
				return tLCDutySet;
			} else {
				tLCDutySet.add(this.getSchema());
				tR.transFields(tLCDutySet, tLBDutySet);
			}
		}
		return tLCDutySet;
	}

	/**
	 * 从保险责任表和保险责任备份表读取信息 返回LCDutySet
	 * 
	 */
	public LCDutySet executeQuery(SQLwithBindVariables sql) {
		Reflections tR = new Reflections();
		LCDutySet tLCDutySet = new LCDutySet();
		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setSchema(this.getSchema());
		tLCDutySet = tLCDutyDB.executeQuery(sql);
		if (tLCDutySet.size() == 0) {
			LBDutySet tLBDutySet = new LBDutySet();
			LBDutyDB tLBDutyDB1 = new LBDutyDB();
			tR.transFields(tLBDutyDB1.getSchema(), this.getSchema());
			tLBDutySet = tLBDutyDB1.executeQuery(sql);
			if (tLBDutySet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCDutyBL";
				tError.functionName = "query";
				tError.errorMessage = "没有查询到保险责任表";
				this.mErrors.addOneError(tError);
				return tLCDutySet;
			} else {
				tLCDutySet.add(this.getSchema());
				tR.transFields(tLCDutySet, tLBDutySet);
			}
		}
		return tLCDutySet;
	}

	public static void main(String[] args) {
		// 添加测试代码
	}

}

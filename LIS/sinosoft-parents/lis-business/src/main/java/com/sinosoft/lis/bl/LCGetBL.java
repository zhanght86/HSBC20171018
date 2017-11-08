/*
 * <p>ClassName: LCGetBL </p>
 * <p>Description: LCGetSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-04-01
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LBGetDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LBGetSchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.vschema.LBGetSet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;

public class LCGetBL extends LCGetSchema {
private static Logger logger = Logger.getLogger(LCGetBL.class);
	// @Constructor
	public LCGetBL() {
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
	 * 从领取项表和领取项备份表读取信息 返回true或false
	 */
	public boolean getInfo() {
		Reflections tR = new Reflections();
		LCGetDB tDB = new LCGetDB();
		tDB.setSchema(this);
		if (!tDB.getInfo()) // 如果查询失败，查询B表
		{
			LBGetDB tDBB = new LBGetDB();
			LBGetSchema tLBGetSchema = new LBGetSchema();
			tR.transFields(tLBGetSchema, this.getSchema());
			tDBB.setSchema(tLBGetSchema);
			if (!tDBB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCGetBL";
				tError.functionName = "getInfo";
				tError.errorMessage = "没有查询到领取项表";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				LCGetSchema tS = new LCGetSchema();
				tR.transFields(tS, tDBB.getSchema());
				this.setSchema(tS);
			}
		} else {
			this.setSchema(tDB.getSchema());
		}
		return true;
	}

	/**
	 * 从领取项表和领取项备份表读取信息 返回LCGetSet
	 * 
	 */
	public LCGetSet query() {
		Reflections tR = new Reflections();
		LCGetSet tLCGetSet = new LCGetSet();
		LCGetDB tLCGetDB = new LCGetDB();
		tLCGetDB.setSchema(this.getSchema());
		tLCGetSet = tLCGetDB.query();
		if (tLCGetSet.size() == 0) {
			LBGetSet tLBGetSet = new LBGetSet();
			LBGetDB tLBGetDB1 = new LBGetDB();
			tR.transFields(tLBGetDB1.getSchema(), this.getSchema());
			tLBGetSet = tLBGetDB1.query();
			if (tLBGetSet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCGetBL";
				tError.functionName = "query";
				tError.errorMessage = "没有查询到领取项表";
				this.mErrors.addOneError(tError);
				return tLCGetSet;
			} else {
				tLCGetSet.add(this.getSchema());
				tR.transFields(tLCGetSet, tLBGetSet);
			}
		}
		return tLCGetSet;
	}

	/**
	 * 从领取项表和领取项备份表读取信息 返回LCGetSet
	 * 
	 */
	public LCGetSet executeQuery(SQLwithBindVariables sql) {
		Reflections tR = new Reflections();
		LCGetSet tLCGetSet = new LCGetSet();
		LCGetDB tLCGetDB = new LCGetDB();
		tLCGetDB.setSchema(this.getSchema());
		tLCGetSet = tLCGetDB.executeQuery(sql);
		if (tLCGetSet.size() == 0) {
			LBGetSet tLBGetSet = new LBGetSet();
			LBGetDB tLBGetDB1 = new LBGetDB();
			tR.transFields(tLBGetDB1.getSchema(), this.getSchema());
			tLBGetSet = tLBGetDB1.executeQuery(sql);
			if (tLBGetSet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCGetBL";
				tError.functionName = "query";
				tError.errorMessage = "没有查询到领取项表";
				this.mErrors.addOneError(tError);
				return tLCGetSet;
			} else {
				tLCGetSet.add(this.getSchema());
				tR.transFields(tLCGetSet, tLBGetSet);
			}
		}
		return tLCGetSet;
	}

	public static void main(String[] args) {
		// 添加测试代码
	}

}

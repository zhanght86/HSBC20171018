/*
 * <p>ClassName: LCCustomerImpartBL </p>
 * <p>Description: LCCustomerImpartSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-04-01
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LBCustomerImpartDB;
import com.sinosoft.lis.db.LCCustomerImpartDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LBCustomerImpartSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.vschema.LBCustomerImpartSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;

public class LCCustomerImpartBL extends LCCustomerImpartSchema {
private static Logger logger = Logger.getLogger(LCCustomerImpartBL.class);
	// @Constructor
	public LCCustomerImpartBL() {
		this.setModifyDate(PubFun.getCurrentDate());
		this.setModifyDate(PubFun.getCurrentTime());
	}

	/**
	 * 设置默认的字段属性
	 */
	public void setDefaultFields() {
		this.setModifyDate(PubFun.getCurrentDate());
		this.setModifyTime(PubFun.getCurrentTime());
	}

	/**
	 * 从客户告知表和客户告知备份表读取信息 返回true或false
	 */
	public boolean getInfo() {
		Reflections tR = new Reflections();
		LCCustomerImpartDB tDB = new LCCustomerImpartDB();
		tDB.setSchema(this);
		if (!tDB.getInfo()) // 如果查询失败，查询B表
		{
			LBCustomerImpartDB tDBB = new LBCustomerImpartDB();
			LBCustomerImpartSchema tLBCustomerImpartSchema = new LBCustomerImpartSchema();
			tR.transFields(tLBCustomerImpartSchema, this.getSchema());
			tDBB.setSchema(tLBCustomerImpartSchema);
			if (!tDBB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCCustomerImpartBL";
				tError.functionName = "getInfo";
				tError.errorMessage = "没有查询到客户告知表";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				LCCustomerImpartSchema tS = new LCCustomerImpartSchema();
				tR.transFields(tS, tDBB.getSchema());
				this.setSchema(tS);
			}
		} else {
			this.setSchema(tDB.getSchema());
		}
		return true;
	}

	/**
	 * 从客户告知表和客户告知备份表读取信息 返回LCCustomerImpartSet
	 * 
	 */
	public LCCustomerImpartSet query() {
		Reflections tR = new Reflections();
		LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
		LCCustomerImpartDB tLCCustomerImpartDB = new LCCustomerImpartDB();
		tLCCustomerImpartDB.setSchema(this.getSchema());
		tLCCustomerImpartSet = tLCCustomerImpartDB.query();
		if (tLCCustomerImpartSet.size() == 0) {
			LBCustomerImpartSet tLBCustomerImpartSet = new LBCustomerImpartSet();
			LBCustomerImpartDB tLBCustomerImpartDB1 = new LBCustomerImpartDB();
			tR.transFields(tLBCustomerImpartDB1.getSchema(), this.getSchema());
			tLBCustomerImpartSet = tLBCustomerImpartDB1.query();
			if (tLBCustomerImpartSet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCCustomerImpartBL";
				tError.functionName = "query";
				tError.errorMessage = "没有查询到客户告知表";
				this.mErrors.addOneError(tError);
				return tLCCustomerImpartSet;
			} else {
				tLCCustomerImpartSet.add(this.getSchema());
				tR.transFields(tLCCustomerImpartSet, tLBCustomerImpartSet);
			}
		}
		return tLCCustomerImpartSet;
	}

	/**
	 * 从客户告知表和客户告知备份表读取信息 返回LCCustomerImpartSet
	 * 
	 */
	public LCCustomerImpartSet executeQuery(SQLwithBindVariables sql) {
		Reflections tR = new Reflections();
		LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
		LCCustomerImpartDB tLCCustomerImpartDB = new LCCustomerImpartDB();
		tLCCustomerImpartDB.setSchema(this.getSchema());
		tLCCustomerImpartSet = tLCCustomerImpartDB.executeQuery(sql);
		if (tLCCustomerImpartSet.size() == 0) {
			LBCustomerImpartSet tLBCustomerImpartSet = new LBCustomerImpartSet();
			LBCustomerImpartDB tLBCustomerImpartDB1 = new LBCustomerImpartDB();
			tR.transFields(tLBCustomerImpartDB1.getSchema(), this.getSchema());
			tLBCustomerImpartSet = tLBCustomerImpartDB1.executeQuery(sql);
			if (tLBCustomerImpartSet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCCustomerImpartBL";
				tError.functionName = "query";
				tError.errorMessage = "没有查询到客户告知表";
				this.mErrors.addOneError(tError);
				return tLCCustomerImpartSet;
			} else {
				tLCCustomerImpartSet.add(this.getSchema());
				tR.transFields(tLCCustomerImpartSet, tLBCustomerImpartSet);
			}
		}
		return tLCCustomerImpartSet;
	}

	public static void main(String[] args) {
		// 添加测试代码
	}

}

/*
 * <p>ClassName: LCBnfBL </p>
 * <p>Description: LCBnfSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-04-01
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LBBnfDB;
import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LBBnfSchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.vschema.LBBnfSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;

public class LCBnfBL extends LCBnfSchema {
private static Logger logger = Logger.getLogger(LCBnfBL.class);
	// @Constructor
	public LCBnfBL() {
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
	 * 通过客户号码查询数据库，得到该客户的信息，放到投保人对应的字段中。
	 * 
	 * @param: cCustomerNo 客户号 ,cType 查询类型，1，表示对于输入的字段不覆盖
	 * @return: no
	 * @author: YT
	 */
	public void queryCustomerDataByDB(String cCustomerNo, String cType) {
		// 需要区分是主被保人还是从被保人
	}

	/**
	 * 从受益人表和受益人备份表读取信息 返回true或false
	 */
	public boolean getInfo() {
		Reflections tR = new Reflections();
		LCBnfDB tDB = new LCBnfDB();
		tDB.setSchema(this);
		if (!tDB.getInfo()) // 如果查询失败，查询B表
		{
			LBBnfDB tDBB = new LBBnfDB();
			LBBnfSchema tLBBnfSchema = new LBBnfSchema();
			tR.transFields(tLBBnfSchema, this.getSchema());
			tDBB.setSchema(tLBBnfSchema);
			if (!tDBB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCBnfBL";
				tError.functionName = "getInfo";
				tError.errorMessage = "没有查询到受益人表";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				LCBnfSchema tS = new LCBnfSchema();
				tR.transFields(tS, tDBB.getSchema());
				this.setSchema(tS);
			}
		} else {
			this.setSchema(tDB.getSchema());
		}
		return true;
	}

	/**
	 * 从受益人表和受益人备份表读取信息 返回LCBnfSet
	 * 
	 */
	public LCBnfSet query() {
		Reflections tR = new Reflections();
		LCBnfSet tLCBnfSet = new LCBnfSet();
		LCBnfDB tLCBnfDB = new LCBnfDB();
		tLCBnfDB.setSchema(this.getSchema());
		tLCBnfSet = tLCBnfDB.query();
		if (tLCBnfSet.size() == 0) {
			LBBnfSet tLBBnfSet = new LBBnfSet();
			LBBnfDB tLBBnfDB1 = new LBBnfDB();
			tR.transFields(tLBBnfDB1.getSchema(), this.getSchema());
			tLBBnfSet = tLBBnfDB1.query();
			if (tLBBnfSet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCBnfBL";
				tError.functionName = "query";
				tError.errorMessage = "没有查询到受益人表";
				this.mErrors.addOneError(tError);
				return tLCBnfSet;
			} else {
				tLCBnfSet.add(this.getSchema());
				tR.transFields(tLCBnfSet, tLBBnfSet);
			}
		}
		return tLCBnfSet;
	}

	/**
	 * 从受益人表和受益人备份表读取信息 返回LCBnfSet
	 * 
	 */
	public LCBnfSet executeQuery(SQLwithBindVariables sql) {
		Reflections tR = new Reflections();
		LCBnfSet tLCBnfSet = new LCBnfSet();
		LCBnfDB tLCBnfDB = new LCBnfDB();
		tLCBnfDB.setSchema(this.getSchema());
		tLCBnfSet = tLCBnfDB.executeQuery(sql);
		if (tLCBnfSet.size() == 0) {
			LBBnfSet tLBBnfSet = new LBBnfSet();
			LBBnfDB tLBBnfDB1 = new LBBnfDB();
			tR.transFields(tLBBnfDB1.getSchema(), this.getSchema());
			tLBBnfSet = tLBBnfDB1.executeQuery(sql);
			if (tLBBnfSet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCBnfBL";
				tError.functionName = "query";
				tError.errorMessage = "没有查询到受益人表";
				this.mErrors.addOneError(tError);
				return tLCBnfSet;
			} else {
				tLCBnfSet.add(this.getSchema());
				tR.transFields(tLCBnfSet, tLBBnfSet);
			}
		}
		return tLCBnfSet;
	}

	public static void main(String[] args) {
		// 添加测试代码
	}

}

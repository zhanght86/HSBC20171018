/*
 * <p>ClassName: LCPolBL </p>
 * <p>Description: LCPolSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-07-09
 * @modify: YT 2002-11-29
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LBPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LBPolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vschema.LBPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;

public class LCPolBL extends LCPolSchema {
private static Logger logger = Logger.getLogger(LCPolBL.class);
	// @Constructor
	public LCPolBL() {

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
		LCPolDB tDB = new LCPolDB();
		tDB.setSchema(this);
		if (!tDB.getInfo()) // 如果查询失败，查询B表
		{
			LBPolDB tDBB = new LBPolDB();
			LBPolSchema tLBPolSchema = new LBPolSchema();
			tR.transFields(tLBPolSchema, this.getSchema());
			tDBB.setSchema(tLBPolSchema);
			LBPolSet tPolSet = new LBPolSet();
			tPolSet =tDBB.query();
			if(tPolSet.size()<=0)
			{
				CError.buildErr(this, "没有查询到保单表");
				return false;
			}
			else
			{
				LCPolSchema tS = new LCPolSchema();
				tR.transFields(tS, tPolSet.get(1));
				this.setSchema(tS);
				
			}
//			if (!tDBB.getInfo()) {
//				// @@错误处理
//				CError tError = new CError();
//				tError.moduleName = "LCPolBL";
//				tError.functionName = "getInfo";
//				tError.errorMessage = "没有查询到保单表";
//				this.mErrors.addOneError(tError);
//				return false;
//			} else {
//				LCPolSchema tS = new LCPolSchema();
//				tR.transFields(tS, tDBB.getSchema());
//				this.setSchema(tS);
//			}
		} else {
			this.setSchema(tDB.getSchema());
		}
		return true;
	}

	/**
	 * 从保单表和保单备份表读取信息 返回LCPolSet
	 * 
	 */
	public LCPolSet query() {
		Reflections tR = new Reflections();
		LCPolSet tLCPolSet = new LCPolSet();
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setSchema(this);
		tLCPolSet = tLCPolDB.query();
		if (tLCPolSet.size() == 0) {
			LBPolSet tLBPolSet = new LBPolSet();
			LBPolDB tLBPolDB1 = new LBPolDB();
			LBPolSchema tLBPolSchema = new LBPolSchema();
			tR.transFields(tLBPolSchema, this.getSchema());
			tLBPolDB1.setSchema(tLBPolSchema);
			tLBPolSet = tLBPolDB1.query();
			if (tLBPolSet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCPolBL";
				tError.functionName = "query";
				tError.errorMessage = "没有查询到保单表";
				this.mErrors.addOneError(tError);
				return tLCPolSet;
			} else {
				tLCPolSet.add(this.getSchema());
				tR.transFields(tLCPolSet, tLBPolSet);
			}
		}
		return tLCPolSet;
	}

	/**
	 * 从保单表和保单备份表读取信息 返回LCPolSet
	 * 
	 */
	public LCPolSet executeQuery(SQLwithBindVariables sql) {
		Reflections tR = new Reflections();
		LCPolSet tLCPolSet = new LCPolSet();
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setSchema(this.getSchema());
		tLCPolSet = tLCPolDB.executeQuery(sql);
		if (tLCPolSet.size() == 0) {
			LBPolSet tLBPolSet = new LBPolSet();
			LBPolDB tLBPolDB1 = new LBPolDB();
			tR.transFields(tLBPolDB1.getSchema(), this.getSchema());
			tLBPolSet = tLBPolDB1.executeQuery(sql);
			if (tLBPolSet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCPolBL";
				tError.functionName = "query";
				tError.errorMessage = "没有查询到保单表";
				this.mErrors.addOneError(tError);
				return tLCPolSet;
			} else {
				tLCPolSet.add(this.getSchema());
				tR.transFields(tLCPolSet, tLBPolSet);
			}
		}
		return tLCPolSet;
	}

	public static void main(String[] args) {
		LCPolBL t = new LCPolBL();
		LCPolSet tSet = new LCPolSet();
		t.setProposalNo("86110020030110001802");
		// t.setPolNo("86110020020210300207");
		// 测试getInfo
		// t.setPolNo("86110020020110000680");
		// logger.debug(t.getInfo());
		// logger.debug(t.mErrors.getFirstError());
		// logger.debug(t.encode());
		// 测试query
		tSet = t.query();
		logger.debug(tSet.size());
		// 测试executeQuery
		// String tSQL ="select * from lcpol where
		// proposalno='86110020030110001802'";
		// tSet=t.executeQuery(tSQL);
		// logger.debug(tSet.size());
		// logger.debug(tSet.encode());
		// logger.debug(t.mErrors.getFirstError());
	}

}

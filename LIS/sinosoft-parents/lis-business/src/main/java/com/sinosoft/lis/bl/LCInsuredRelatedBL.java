/*
 * <p>ClassName: LCInsuredRelatedBL </p>
 * <p>Description: LCInsuredRelatedSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-04-01
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LBInsuredDB;
import com.sinosoft.lis.db.LCInsuredRelatedDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LBInsuredSchema;
import com.sinosoft.lis.schema.LCInsuredRelatedSchema;
import com.sinosoft.lis.vschema.LBInsuredSet;
import com.sinosoft.lis.vschema.LCInsuredRelatedSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;

public class LCInsuredRelatedBL extends LCInsuredRelatedSchema {
private static Logger logger = Logger.getLogger(LCInsuredRelatedBL.class);
	// @Constructor
	public LCInsuredRelatedBL() {
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
		boolean isGrp = false;

		if (!isGrp) // 处理个人客户
		{
			LDPersonBL tBL = new LDPersonBL();
			tBL.queryDataByDB(cCustomerNo);

			this.setName(tBL.getName());
			// this.setAddressNo(tBL.);
			this.setBirthday(tBL.getBirthday());
			this.setIDNo(tBL.getIDNo());
			this.setIDType(tBL.getIDType());

		}
	}

	/**
	 * 从多被保人表和多被保人备份表读取信息 返回true或false
	 */
	public boolean getInfo() {
		Reflections tR = new Reflections();
		LCInsuredRelatedDB tDB = new LCInsuredRelatedDB();
		tDB.setSchema(this);
		if (!tDB.getInfo()) // 如果查询失败，查询B表
		{
			LBInsuredDB tDBB = new LBInsuredDB();
			LBInsuredSchema tLBInsuredSchema = new LBInsuredSchema();
			tR.transFields(tLBInsuredSchema, this.getSchema());
			tDBB.setSchema(tLBInsuredSchema);
			if (!tDBB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCInsuredRelatedBL";
				tError.functionName = "getInfo";
				tError.errorMessage = "没有查询到多被保人表";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				LCInsuredRelatedSchema tS = new LCInsuredRelatedSchema();
				tR.transFields(tS, tDBB.getSchema());
				this.setSchema(tS);
			}
		} else {
			this.setSchema(tDB.getSchema());
		}
		return true;
	}

	/**
	 * 从多被保人表和多被保人备份表读取信息 返回LCInsuredRelatedSet
	 * 
	 */
	public LCInsuredRelatedSet query() {
		Reflections tR = new Reflections();
		LCInsuredRelatedSet tLCInsuredRelatedSet = new LCInsuredRelatedSet();
		LCInsuredRelatedDB tLCInsuredRelatedDB = new LCInsuredRelatedDB();
		tLCInsuredRelatedDB.setSchema(this.getSchema());
		tLCInsuredRelatedSet = tLCInsuredRelatedDB.query();
		if (tLCInsuredRelatedSet.size() == 0) {
			LBInsuredSet tLBInsuredSet = new LBInsuredSet();
			LBInsuredDB tLBInsuredDB1 = new LBInsuredDB();
			tR.transFields(tLBInsuredDB1.getSchema(), this.getSchema());
			tLBInsuredSet = tLBInsuredDB1.query();
			if (tLBInsuredSet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCInsuredRelatedBL";
				tError.functionName = "query";
				tError.errorMessage = "没有查询到多被保人表";
				this.mErrors.addOneError(tError);
				return tLCInsuredRelatedSet;
			} else {
				tLCInsuredRelatedSet.add(this.getSchema());
				tR.transFields(tLCInsuredRelatedSet, tLBInsuredSet);
			}
		}
		return tLCInsuredRelatedSet;
	}

	/**
	 * 从多被保人表和多被保人备份表读取信息 返回LCInsuredRelatedSet
	 * 
	 */
	public LCInsuredRelatedSet executeQuery(SQLwithBindVariables sql) {
		Reflections tR = new Reflections();
		LCInsuredRelatedSet tLCInsuredRelatedSet = new LCInsuredRelatedSet();
		LCInsuredRelatedDB tLCInsuredRelatedDB = new LCInsuredRelatedDB();
		tLCInsuredRelatedDB.setSchema(this.getSchema());
		tLCInsuredRelatedSet = tLCInsuredRelatedDB.executeQuery(sql);
		if (tLCInsuredRelatedSet.size() == 0) {
			LBInsuredSet tLBInsuredSet = new LBInsuredSet();
			LBInsuredDB tLBInsuredDB1 = new LBInsuredDB();
			tR.transFields(tLBInsuredDB1.getSchema(), this.getSchema());
			tLBInsuredSet = tLBInsuredDB1.executeQuery(sql);
			if (tLBInsuredSet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCInsuredRelatedBL";
				tError.functionName = "query";
				tError.errorMessage = "没有查询到多被保人表";
				this.mErrors.addOneError(tError);
				return tLCInsuredRelatedSet;
			} else {
				tLCInsuredRelatedSet.add(this.getSchema());
				tR.transFields(tLCInsuredRelatedSet, tLBInsuredSet);
			}
		}
		return tLCInsuredRelatedSet;
	}

	public static void main(String[] args) {
		// 添加测试代码
	}

}

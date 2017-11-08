/*
 * <p>ClassName: LCInsuredBL </p>
 * <p>Description: LCInsuredSchemaBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-04-01
 */
package com.sinosoft.lis.bl;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LBInsuredDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LBInsuredSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.vschema.LBInsuredSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;

public class LCInsuredBL extends LCInsuredSchema {
private static Logger logger = Logger.getLogger(LCInsuredBL.class);
	// @Constructor
	public LCInsuredBL() {
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

			this.setMarriageDate(tBL.getMarriageDate());
			/*
			 * Lis5.3 upgrade set 这里是get出的错，但是get是判断，为了set if
			 * (this.getInsuredGrade().equals("S"))//如果是从被保人 {
			 * this.setMarriage(tBL.getMarriage() );
			 * this.setOccupationType(tBL.getOccupationType() ); }
			 */
			this.setStartWorkDate(tBL.getStartWorkDate());
			this.setSalary(tBL.getSalary());
			this.setStature(tBL.getStature());
			this.setAvoirdupois(tBL.getAvoirdupois());
			this.setCreditGrade(tBL.getCreditGrade());
			this.setJoinCompanyDate(tBL.getJoinCompanyDate());
			this.setPosition(tBL.getPosition());
			/*
			 * Lis5.3 upgrade set this.setPassword(tBL.getPassword() );
			 * this.setNativePlace(tBL.getNativePlace() );
			 * this.setNationality(tBL.getNationality() );
			 * this.setProterty(tBL.getProterty() );
			 * this.setOthIDType(tBL.getOthIDType() );
			 * this.setOthIDNo(tBL.getOthIDNo() ); this.setICNo(tBL.getICNo() );
			 * this.setHomeAddressCode(tBL.getHomeAddressCode() );
			 * this.setHomeAddress(tBL.getHomeAddress() );
			 * this.setPostalAddress(tBL.getPostalAddress() );
			 * this.setZipCode(tBL.getZipCode() ); this.setPhone(tBL.getPhone() );
			 * this.setBP(tBL.getBP() ); this.setMobile(tBL.getMobile() );
			 * this.setEMail(tBL.getEMail() ); this.setGrpNo(tBL.getGrpNo() );
			 * this.setGrpName(tBL.getGrpName() );
			 * this.setGrpPhone(tBL.getGrpPhone() );
			 * this.setGrpAddressCode(tBL.getGrpAddressCode() );
			 * this.setGrpAddress(tBL.getGrpAddress() );
			 * this.setDeathDate(tBL.getDeathDate() );
			 * this.setRemark(tBL.getRemark() ); this.setState(tBL.getState() );
			 */
		}
	}

	/**
	 * 从多被保人表和多被保人备份表读取信息 返回true或false
	 */
	public boolean getInfo() {
		Reflections tR = new Reflections();
		LCInsuredDB tDB = new LCInsuredDB();
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
				tError.moduleName = "LCInsuredBL";
				tError.functionName = "getInfo";
				tError.errorMessage = "没有查询到多被保人表";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				LCInsuredSchema tS = new LCInsuredSchema();
				tR.transFields(tS, tDBB.getSchema());
				this.setSchema(tS);
			}
		} else {
			this.setSchema(tDB.getSchema());
		}
		return true;
	}

	/**
	 * 从多被保人表和多被保人备份表读取信息 返回LCInsuredSet
	 * 
	 */
	public LCInsuredSet query() {
		Reflections tR = new Reflections();
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setSchema(this.getSchema());
		tLCInsuredSet = tLCInsuredDB.query();
		if (tLCInsuredSet.size() == 0) {
			LBInsuredSet tLBInsuredSet = new LBInsuredSet();
			LBInsuredDB tLBInsuredDB1 = new LBInsuredDB();
			tR.transFields(tLBInsuredDB1.getSchema(), this.getSchema());
			tLBInsuredSet = tLBInsuredDB1.query();
			if (tLBInsuredSet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCInsuredBL";
				tError.functionName = "query";
				tError.errorMessage = "没有查询到多被保人表";
				this.mErrors.addOneError(tError);
				return tLCInsuredSet;
			} else {
				tLCInsuredSet.add(this.getSchema());
				tR.transFields(tLCInsuredSet, tLBInsuredSet);
			}
		}
		return tLCInsuredSet;
	}

	/**
	 * 从多被保人表和多被保人备份表读取信息 返回LCInsuredSet
	 * 
	 */
	public LCInsuredSet executeQuery(SQLwithBindVariables sql) {
		Reflections tR = new Reflections();
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setSchema(this.getSchema());
		tLCInsuredSet = tLCInsuredDB.executeQuery(sql);
		if (tLCInsuredSet.size() == 0) {
			LBInsuredSet tLBInsuredSet = new LBInsuredSet();
			LBInsuredDB tLBInsuredDB1 = new LBInsuredDB();
			tR.transFields(tLBInsuredDB1.getSchema(), this.getSchema());
			tLBInsuredSet = tLBInsuredDB1.executeQuery(sql);
			if (tLBInsuredSet.size() == 0) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCInsuredBL";
				tError.functionName = "query";
				tError.errorMessage = "没有查询到多被保人表";
				this.mErrors.addOneError(tError);
				return tLCInsuredSet;
			} else {
				tLCInsuredSet.add(this.getSchema());
				tR.transFields(tLCInsuredSet, tLBInsuredSet);
			}
		}
		return tLCInsuredSet;
	}

	public static void main(String[] args) {
		// 添加测试代码
	}

}

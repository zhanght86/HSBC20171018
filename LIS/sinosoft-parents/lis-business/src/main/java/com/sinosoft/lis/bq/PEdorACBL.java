package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LPAddressBL;
import com.sinosoft.lis.bl.LPAppntBL;
import com.sinosoft.lis.bl.LPContBL;
import com.sinosoft.lis.bl.LPPersonBL;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPAddressSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPPersonSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 承保暂交费业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @version 1.0
 */
public class PEdorACBL {
private static Logger logger = Logger.getLogger(PEdorACBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPAppntSchema mLPAppntSchema = new LPAppntSchema();
	private LPAppntSet saveLPAppntSet = new LPAppntSet();
	private LPAddressSchema mLPAddressSchema = new LPAddressSchema();
	private LPAddressSet saveLPAddressSet = new LPAddressSet();
	private LPPersonSet saveLPPersonSet = new LPPersonSet();
	private LPContSet saveLPContSet = new LPContSet();
	private LPPolSet saveLPPolSet = new LPPolSet();
	private GlobalInput mGlobalInput = new GlobalInput();

	public PEdorACBL() {
	}

	/**
	 * 设置个单投保人表,该数据项非空
	 * 
	 * @param: LPAppntSchema 保全投保人信息表
	 * 
	 * @return:
	 */
	public void setLPAppntSchema(LPAppntSchema tLPAppntSchema) {
		this.mLPAppntSchema = tLPAppntSchema;
	}

	/**
	 * 设置个单地址表
	 * 
	 * @param: LPAppntSchema 保全投保人信息表
	 * 
	 * @return:
	 */
	public void setLPAddressSchema(LPAddressSchema tLPAddresschema) {
		this.mLPAddressSchema = tLPAddresschema;
	}

	/**
	 * 设置个单批改项目表,该数据项非空
	 * 
	 * @param: LPEdorItemSchema 个单批改项目表
	 * 
	 * @return:
	 */

	public void setLPEdorItemSchema(LPEdorItemSchema tLPEdorItemSchema) {
		this.mLPEdorItemSchema = tLPEdorItemSchema;
	}

	/**
	 * 设置全局变量,该数据项非空
	 * 
	 * @param: tGlobalInput 全局变量
	 * 
	 * @return:
	 */
	public void setGlobalInput(GlobalInput tGlobalInput) {
		this.mGlobalInput = tGlobalInput;
	}

	/**
	 * 获得变更后的个人批改项目表
	 * 
	 * @param:
	 * 
	 * @return: LPEdorItemSchema 个人批改项目表
	 */
	public LPEdorItemSchema getLPEdorItemSchema() {
		return this.mLPEdorItemSchema;
	}

	/**
	 * 获得变更后的保全个单投保人表集合
	 * 
	 * @param:
	 * 
	 * @return: LPAppntSet 个单投保人表集合
	 */
	public LPAppntSet getLPAppntSet() {
		return this.saveLPAppntSet;
	}

	/**
	 * 获得变更后的保全个人客户地址表集合
	 * 
	 * @param:
	 * 
	 * @return: LPAddressSet 个人批改项目表
	 */
	public LPAddressSet getLPAddressSet() {
		return this.saveLPAddressSet;
	}

	/**
	 * 获得变更后的保全个人客户表集合
	 * 
	 * @param:
	 * 
	 * @return: LPPersonSet 个人客户表集合
	 */
	public LPPersonSet getLPPersonSet() {
		return this.saveLPPersonSet;
	}

	/**
	 * 获得变更后的保全个人保单表集合
	 * 
	 * @param:
	 * 
	 * @return: LPPersonSet 个人客户表集合
	 */
	public LPContSet getLPContSet() {
		return this.saveLPContSet;
	}

	/**
	 * 获得变更后的保全个人保单险种表集合
	 * 
	 * @param:
	 * 
	 * @return: LPPersonSet 个人客户表集合
	 */
	public LPPolSet getLPPolSet() {
		return this.saveLPPolSet;
	}

	/**
	 * 对传入的数据进行处理,产生个人保单,个单投保人,个人保单险种,个人客户,个人客户地址等表的数据
	 * 
	 * @param:
	 * 
	 * @return: boolean 成功操作与否
	 */
	public boolean dealData() {

		// 数据校验操作
		// if (!checkData())
		// {
		// return false;
		// }
		// 组织操作员和修改日期信息
		// 统一更新日期，时间
		String theCurrentDate = PubFun.getCurrentDate();
		String theCurrentTime = PubFun.getCurrentTime();

		mLPEdorItemSchema.setModifyDate(theCurrentDate);
		mLPEdorItemSchema.setModifyTime(theCurrentTime);
		if (mLPEdorItemSchema.getMakeDate() == null
				|| mLPEdorItemSchema.getMakeDate().equals("")) {
			mLPEdorItemSchema.setMakeDate(theCurrentDate);
		}
		if (mLPEdorItemSchema.getMakeTime() == null
				|| mLPEdorItemSchema.getMakeTime().equals("")) {
			mLPEdorItemSchema.setMakeTime(theCurrentTime);
		}
		mLPEdorItemSchema.setOperator(this.mGlobalInput.Operator);

		// 准备个人批改主表的信息
		LPAppntSchema tLPAppntSchema = new LPAppntSchema();
		LPAddressSchema tLPAddressSchema = new LPAddressSchema();
		int m;

		// 准备个人保单（保全）的信息
		// tLPAppntSchema.setSchema(mLPAppntSchema);
		LPAppntBL tLPAppntBL = new LPAppntBL();
		// LPAppntSet tLPAppntSet = new LPAppntSet();
		// tLPAppntSet = tLPAppntBL.queryLPAppnt(mLPEdorItemSchema);
		if (!tLPAppntBL.queryLastLPAppnt(mLPEdorItemSchema, mLPAppntSchema)) {
			// @@错误处理
			CError.buildErr(this, "查询投保人信息失败！");
			return false;
		}
		tLPAppntSchema.setSchema(tLPAppntBL.getSchema());
		// logger.debug("test info 1");

		// if (tLPAppntSet == null || tLPAppntSet.size() <= 0)
		// {
		// // @@错误处理
		// CError.buildErr(this, "查询投保人信息失败！");
		// return false;
		// }
		// else
		// {
		// tLPAppntSchema.setSchema(tLPAppntSet.get(1));
		// }

		tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAppntSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAppntSchema.setAppntName(mLPAppntSchema.getAppntName());
		tLPAppntSchema.setAppntSex(mLPAppntSchema.getAppntSex());
		tLPAppntSchema.setAppntBirthday(mLPAppntSchema.getAppntBirthday());
		tLPAppntSchema.setIDType(mLPAppntSchema.getIDType());
		tLPAppntSchema.setIDNo(mLPAppntSchema.getIDNo());
		// tLPAppntSchema.setOccupationType(mLPAppntSchema.getOccupationType());
		tLPAppntSchema.setOccupationCode(mLPAppntSchema.getOccupationCode());
		tLPAppntSchema.setMarriage(mLPAppntSchema.getMarriage());
		// tLPAppntSchema.setNationality(mLPAppntSchema.getNationality());
		tLPAppntSchema.setNativePlace(mLPAppntSchema.getNativePlace());
		tLPAppntSchema.setLicenseType(mLPAppntSchema.getLicenseType());

		mLPAppntSchema.setSchema(tLPAppntSchema);
		mLPAppntSchema.setModifyDate(PubFun.getCurrentDate());
		mLPAppntSchema.setModifyTime(PubFun.getCurrentTime());
		saveLPAppntSet.add(mLPAppntSchema);

		// logger.debug("test info 1");
		if ((mLPAddressSchema.getAddressNo() != 0)
				&& (mLPAddressSchema.getCustomerNo() != null)
				&& (!mLPAddressSchema.getCustomerNo().equals(""))) {
			tLPAddressSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPAddressSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPAddressSchema.setAddressNo(mLPAddressSchema.getAddressNo());
			tLPAddressSchema.setCustomerNo(mLPAddressSchema.getCustomerNo());

			// logger.debug("test info 2");

			LPAddressBL tLPAddressBL = new LPAddressBL();
			if (!tLPAddressBL.queryLPAddress(tLPAddressSchema)) {

				// logger.debug("test info 3");

				LCAddressDB tLCAddressDB = new LCAddressDB();
				tLCAddressDB.setAddressNo(mLPAddressSchema.getAddressNo());
				tLCAddressDB.setCustomerNo(mLPAddressSchema.getCustomerNo());
				if (!tLCAddressDB.getInfo()) {

					// logger.debug("test info 4");

					// logger.debug("asdkjfkljasdkj" +
					// tLPAppntSchema.getAddressNo());
					// logger.debug("asdfasdfasdfasd" +
					// tLPAppntSchema.getAppntNo());
					CError.buildErr(this, "查询投保人地址信息失败！");
					return false;
				}
				// 转换Schema

				// logger.debug("test info 5");

				LCAddressSchema tLCAddressSchema = new LCAddressSchema();
				tLCAddressSchema = tLCAddressDB.getSchema();
				Reflections tReflections = new Reflections();
				tReflections.transFields(mLPAddressSchema, tLCAddressSchema);
				mLPAddressSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				mLPAddressSchema.setEdorType(mLPEdorItemSchema.getEdorType());

				// logger.debug("test info 6");

			}
			tLPAddressSchema.setSchema(tLPAddressBL.getSchema());

			// logger.debug("test info 7");

			if (!StrTool.compareString(tLPAddressSchema.getPostalAddress(),
					mLPAddressSchema.getPostalAddress())) {
				ExeSQL tExeSQL = new ExeSQL();
				String sql = "Select Case When max(AddressNo) Is Null Then 1 Else max(AddressNo) + 1 End from LCAddress where CustomerNo='"
						+ "?CustomerNo?" + "'";
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(sql);
				sqlbv.put("CustomerNo", mLPAppntSchema.getAppntNo());
				tLPAddressSchema.setAddressNo(tExeSQL.getOneValue(sqlbv));
			}

			// logger.debug("test info 8");

			// tLPAddressSchema.setHomeAddress(mLPAddressSchema.getHomeAddress());
			// tLPAddressSchema.setHomeZipCode(mLPAddressSchema.getHomeZipCode());
			tLPAddressSchema.setHomePhone(mLPAddressSchema.getHomePhone());
			// tLPAddressSchema.setHomeFax(mLPAddressSchema.getHomeFax());

			// tLPAddressSchema.setCompanyAddress(mLPAddressSchema.getCompanyAddress());
			// tLPAddressSchema.setCompanyZipCode(mLPAddressSchema.getCompanyZipCode());
			tLPAddressSchema
					.setCompanyPhone(mLPAddressSchema.getCompanyPhone());
			// tLPAddressSchema.setCompanyFax(mLPAddressSchema.getCompanyFax());

			tLPAddressSchema.setPostalAddress(mLPAddressSchema
					.getPostalAddress());
			tLPAddressSchema.setZipCode(mLPAddressSchema.getZipCode());
			// tLPAddressSchema.setPhone(mLPAddressSchema.getPhone());
			tLPAddressSchema.setFax(mLPAddressSchema.getFax());
			tLPAddressSchema.setMobile(mLPAddressSchema.getMobile());

			tLPAddressSchema.setEdorType(mLPAddressSchema.getEdorType());
			mLPAddressSchema.setSchema(tLPAddressSchema);
			mLPAddressSchema.setModifyDate(PubFun.getCurrentDate());
			mLPAddressSchema.setModifyTime(PubFun.getCurrentTime());
			saveLPAddressSet.add(mLPAddressSchema);
		}

		LPPersonSchema tLPPersonSchema = new LPPersonSchema();
		tLPPersonSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPersonSchema.setEdorType(mLPEdorItemSchema.getEdorType());

		logger.debug("tLPPersonSchema.getEdorType()="
				+ tLPPersonSchema.getEdorType());
		tLPPersonSchema.setCustomerNo(mLPAppntSchema.getAppntNo());
		LPPersonBL tLPPersonBL = new LPPersonBL();
		if (!tLPPersonBL.queryLPPerson(tLPPersonSchema)) {
			// 转换Schema
			LDPersonDB tLDPersonDB = new LDPersonDB();
			tLDPersonDB.setCustomerNo(mLPAppntSchema.getAppntNo());
			if (!tLDPersonDB.getInfo()) {
				CError.buildErr(this, "查询客户信息失败！");
				return false;
			}
			LDPersonSchema tLDPersonSchema = new LDPersonSchema();
			tLDPersonSchema = tLDPersonDB.getSchema();
			Reflections tReflections = new Reflections();
			tReflections.transFields(tLPPersonSchema, tLDPersonSchema);

			tLPPersonSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPersonSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		}
		tLPPersonSchema.setSchema(tLPPersonBL.getSchema());
		tLPPersonSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPersonSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPersonSchema.setName(mLPAppntSchema.getAppntName());
		tLPPersonSchema.setSex(mLPAppntSchema.getAppntSex());
		tLPPersonSchema.setBirthday(mLPAppntSchema.getAppntBirthday());
		tLPPersonSchema.setIDType(mLPAppntSchema.getIDType());
		tLPPersonSchema.setIDNo(mLPAppntSchema.getIDNo());
		tLPPersonSchema.setOccupationType(mLPAppntSchema.getOccupationType());
		tLPPersonSchema.setOccupationCode(mLPAppntSchema.getOccupationCode());
		tLPPersonSchema.setMarriage(mLPAppntSchema.getMarriage());
		tLPPersonSchema.setNationality(mLPAppntSchema.getNationality());

		tLPPersonSchema.setModifyDate(PubFun.getCurrentDate());
		tLPPersonSchema.setModifyTime(PubFun.getCurrentTime());
		saveLPPersonSet.add(tLPPersonSchema);

		LPContBL tLPContBL = new LPContBL();
		LPContSchema tLPContSchema = new LPContSchema();
		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContSchema.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLPContBL.queryLastLPCont(mLPEdorItemSchema, tLPContSchema)) {
			CError.buildErr(this, "查询合同保单信息失败!");
			return false;
		}
		tLPContBL.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContBL.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContBL.setAppntName(mLPAppntSchema.getAppntName());
		tLPContBL.setAppntSex(mLPAppntSchema.getAppntSex());
		tLPContBL.setAppntBirthday(mLPAppntSchema.getAppntBirthday());
		tLPContBL.setModifyDate(PubFun.getCurrentDate());
		tLPContBL.setModifyTime(PubFun.getCurrentTime());
		saveLPContSet.add(tLPContBL.getSchema());

		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
		LCPolSet tLCPolSet = tLCPolDB.query();
		if (tLCPolDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询险种保单信息失败!");
			return false;
		}
		Reflections tReflections = new Reflections();
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			LCPolSchema tLCPolSchema = tLCPolSet.get(i);
			LPPolSchema tLPPolSchema = new LPPolSchema();
			tReflections.transFields(tLPPolSchema, tLCPolSchema);
			tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPolSchema.setAppntName(mLPAppntSchema.getAppntName());
			tLPPolSchema.setOccupationType(mLPAppntSchema.getOccupationType());
			tLPPolSchema.setModifyDate(PubFun.getCurrentDate());
			tLPPolSchema.setModifyTime(PubFun.getCurrentTime());
			saveLPPolSet.add(tLPPolSchema);
		}

		// 修改“个险保全项目表”相应信息
		mLPEdorItemSchema.setEdorState("1");
		mLPEdorItemSchema.setOperator(this.mGlobalInput.Operator);
		mLPEdorItemSchema.setModifyDate(theCurrentDate);
		mLPEdorItemSchema.setModifyTime(theCurrentTime);

		return true;

	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		return true;
	}
}

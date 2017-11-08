package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAccountDB;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCAccountSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;

/**
 * Title: Web业务系统 LDPersonBL.java Description:个人信息类（界面输入）客户管理模块新增客户 Copyright:
 * Copyright (c) 2005 Company: Sinosoft
 * 
 * @author wangyan
 * @version 1.0
 */

public class LDPersonBL {
private static Logger logger = Logger.getLogger(LDPersonBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	private VData mInputData;
	private VData mResult = new VData();
	private VData tResult = new VData();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private MMap map = new MMap();

	// 个人信息
	private LDPersonSchema mLDPersonSchema = new LDPersonSchema();
	private LCAddressSchema mLCAddressSchema = new LCAddressSchema();
	private LCAccountSchema mLCAccountSchema = new LCAccountSchema();
	private GlobalInput tGI = new GlobalInput();

	public LDPersonBL() {

	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		// 得到输入数据
		if (!getInputData()) {
			return false;
		}
		/*
		 * //检查数据合法性 if (!checkInputData()) { return false; }
		 */
		// 进行业务处理
		if (!dealData(cOperate)) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		return true;
	}

	// 从输入数据中得到所有对象
	// 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	private boolean getInputData() {
		// 个人信息
		mLDPersonSchema = (LDPersonSchema) mInputData.getObjectByObjectName(
				"LDPersonSchema", 0);
		mLCAddressSchema = (LCAddressSchema) mInputData.getObjectByObjectName(
				"LCAddressSchema", 0);
		mLCAccountSchema = (LCAccountSchema) mInputData.getObjectByObjectName(
				"LCAccountSchema", 0);
		tGI = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);

		if ((mLDPersonSchema == null)) {
			CError.buildErr(this, "在个人信息接受时没有得到足够的数据，请您确认有：个人的完整信息!");
			return false;
		}
		return true;
	}

	// 校验前面得到的数据
	// 输出：如果输入数据有错，则返回false,否则返回true
	private boolean checkInputData() {
		try {
			if (mLCAddressSchema != null) {
				if (this.checkLCAddress() == false) {
					return false;
				}
			}
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPersonBL";
			tError.functionName = "checkData";
			tError.errorMessage = "在校验输入的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData(String cOperate) {
		boolean tReturn = false;
		String tNo = "";

		// if (mLCAddressSchema != null)
		// {
		// logger.debug("----mLCAddressSchema is not null----");
		int mAddressNo = mLCAddressSchema.getAddressNo();
		Integer integer1 = new Integer(mAddressNo);
		String mmAddressNo = integer1.toString();

		logger.debug("AddressNo is " + mmAddressNo);

		// 生成地址码,在添加用户或者更新用户时都可能生成
		if (!StrTool.compareString(mLCAddressSchema.getPostalAddress(), "")
				&& (mmAddressNo == null || ("").equals(mmAddressNo))) {
			try {
				SSRS tSSRS = new SSRS();
				String sql = "Select Case When max(AddressNo) Is Null Then 0 Else max(AddressNo) End from LCAddress where CustomerNo='"
						+ "?mLDPersonSchema?" + "'";
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql(sql);
				sqlbv1.put("mLDPersonSchema", mLDPersonSchema.getCustomerNo());
				ExeSQL tExeSQL = new ExeSQL();
				tSSRS = tExeSQL.execSQL(sqlbv1);
				Integer firstinteger = Integer.valueOf(tSSRS.GetText(1, 1));
				int ttNo = firstinteger.intValue() + 1;
				Integer integer2 = new Integer(ttNo);
				tNo = integer2.toString();
				mLCAddressSchema.setAddressNo(ttNo);
				logger.debug("AddressNo is " + tNo);
			} catch (Exception e) {
				CError tError = new CError();
				tError.moduleName = "LDPersonBL";
				tError.functionName = "createAddressNo";
				tError.errorMessage = "地址码超长,生成号码失败,请先删除原来的超长地址码!";
				this.mErrors.addOneError(tError);
				mLCAddressSchema.setAddressNo("");
			}
			// }
			// logger.debug("----mLCAddressSchema is null----");
			mLCAddressSchema.setModifyDate(CurrentDate);
			mLCAddressSchema.setModifyTime(CurrentTime);
			mLCAddressSchema.setMakeDate(CurrentDate);
			mLCAddressSchema.setMakeTime(CurrentTime);
		}

		// 添加记录
		if (cOperate.equals("INSERT")) {
			String CustomerNo = "";
			CustomerNo = PubFun1.CreateMaxNo("CUSTOMERNO", "SN");

			mLDPersonSchema.setCustomerNo(CustomerNo);
			mLDPersonSchema.setMakeDate(CurrentDate);
			mLDPersonSchema.setMakeTime(CurrentTime);
			mLDPersonSchema.setModifyDate(CurrentDate);
			mLDPersonSchema.setModifyTime(CurrentTime);
			mLDPersonSchema.setOperator(tGI.Operator);

			mLCAddressSchema.setCustomerNo(CustomerNo);
			mLCAddressSchema.setAddressNo(1);
			mLCAddressSchema.setMakeDate(CurrentDate);
			mLCAddressSchema.setMakeTime(CurrentTime);
			mLCAddressSchema.setModifyDate(CurrentDate);
			mLCAddressSchema.setModifyTime(CurrentTime);
			mLCAddressSchema.setOperator(tGI.Operator);

			mLCAccountSchema.setCustomerNo(CustomerNo);
			mLCAccountSchema.setAccKind("Y");
			mLCAccountSchema.setMakeDate(CurrentDate);
			mLCAccountSchema.setMakeTime(CurrentTime);
			mLCAccountSchema.setModifyDate(CurrentDate);
			mLCAccountSchema.setModifyTime(CurrentTime);
			mLCAccountSchema.setOperator(tGI.Operator);

			map.put(mLDPersonSchema, "INSERT");
			map.put(mLCAddressSchema, "INSERT");

			if (mLCAccountSchema.getAccName() != null
					&& !mLCAccountSchema.getAccName().equals("")
					&& mLCAccountSchema.getBankAccNo() != null
					&& !mLCAccountSchema.getBankAccNo().equals("")
					&& mLCAccountSchema.getBankCode() != null
					&& !mLCAccountSchema.getBankCode().equals("")) {
				map.put(mLCAccountSchema, "INSERT");
			}

			tReturn = true;
		}

		// 更新记录
		if (cOperate.equals("UPDATE")) {
			// 更新个人信息表中的不能为空的字段(MakeDate,MakeTime,ModifyDate,ModifyTime)
			// 入机日期,入机时间, 最后一次修改日期,最后一次修改时间
			mLDPersonSchema.setMakeDate(CurrentDate);
			mLDPersonSchema.setMakeTime(CurrentTime);
			mLDPersonSchema.setModifyDate(CurrentDate);
			mLDPersonSchema.setModifyTime(CurrentTime);
			mLDPersonSchema.setOperator(tGI.Operator);

			mLCAddressSchema.setCustomerNo(mLDPersonSchema.getCustomerNo());
			mLCAddressSchema.setModifyDate(CurrentDate);
			mLCAddressSchema.setModifyTime(CurrentTime);
			mLCAddressSchema.setMakeDate(CurrentDate);
			mLCAddressSchema.setMakeTime(CurrentTime);
			mLCAddressSchema.setOperator(tGI.Operator);

			mLCAccountSchema.setCustomerNo(mLDPersonSchema.getCustomerNo());
			mLCAccountSchema.setMakeDate(CurrentDate);
			mLCAccountSchema.setMakeTime(CurrentTime);
			mLCAccountSchema.setModifyDate(CurrentDate);
			mLCAccountSchema.setModifyTime(CurrentTime);
			mLCAccountSchema.setOperator(tGI.Operator);

			map.put(mLDPersonSchema, "UPDATE");

			if (tNo.equals("")
					&& mLCAddressSchema != null
					&& !StrTool.compareString(mLCAddressSchema
							.getPostalAddress(), "")) {
				map.put(mLCAddressSchema, "DELETE&INSERT");
			} else if (!tNo.equals("")) {
				// map.put(mLCAddressSchema, "UPDATE");
				map.put(mLCAddressSchema, "DELETE&INSERT");
			}
			if (!mLCAccountSchema.getAccName().equals("")
					&& !mLCAccountSchema.getBankAccNo().equals("")
					&& !mLCAccountSchema.getBankCode().equals("")
					&& !mLCAccountSchema.getAccKind().equals("")) {
				LCAccountDB tLCAccountDB = new LCAccountDB();
				tLCAccountDB.setCustomerNo(mLCAccountSchema.getCustomerNo());
				tLCAccountDB.setBankCode(mLCAccountSchema.getBankCode());
				tLCAccountDB.setBankAccNo(mLCAccountSchema.getBankAccNo());

				if (!tLCAccountDB.getInfo()) {
					map.put(mLCAccountSchema, "INSERT");
				} else {
					map.put(mLCAccountSchema, "UPDATE");
				}
			}
			tReturn = true;
		}

		// 删除记录
		if (cOperate.equals("DELETE")) {
			map.put(mLDPersonSchema, "DELETE");
			String sqlDlete = "delete from LCAddress where CustomerNo='"+ "?getCustomerNo?" + "'";
			SQLwithBindVariables sqlbvq = new SQLwithBindVariables();
			sqlbvq.sql(sqlDlete);
			sqlbvq.put("getCustomerNo",mLDPersonSchema.getCustomerNo());
			map.put(sqlbvq, "DELETE");
			
			String sqlDelete2 ="delete from LCAccount where CustomerNo='"
					+ "?getCustomerNo?" + "'" ;
			SQLwithBindVariables sqlbvw = new SQLwithBindVariables();
			sqlbvw.sql(sqlDelete2);
			sqlbvw.put("getCustomerNo",mLDPersonSchema.getCustomerNo());
			map.put(sqlbvw, "DELETE");

			// map.put(mLCAccountSchema, "DELETE");
			tReturn = true;
		}
		return tReturn;
	}

	// 准备往后层输出所需要的数据
	// 输出：如果准备数据时发生错误则返回false,否则返回true
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
			mResult.add(map);
			tResult.add(mLDPersonSchema);
			tResult.add(mLCAddressSchema);
			tResult.add(mLCAccountSchema);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPersonBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// 检查地址数据是否正确
	// 如果在处理过程中出错或者数据有错误，则返回false,否则返回true
	private boolean checkLCAddress() {
		if (mLCAddressSchema != null) {
			int tAddressNo = mLCAddressSchema.getAddressNo();
			Integer integer = new Integer(tAddressNo);
			String ttAddressNo = integer.toString();
			if ((ttAddressNo != null) && (!ttAddressNo.equals(""))) {
				LCAddressDB tLCAddressDB = new LCAddressDB();

				tLCAddressDB.setAddressNo(mLCAddressSchema.getAddressNo()); // 地址号
				tLCAddressDB.setCustomerNo(mLCAddressSchema.getCustomerNo()); // 客户号
				if (tLCAddressDB.getInfo() == false) {
					CError tError = new CError();
					tError.moduleName = "LDPersonBL";
					tError.functionName = "checkAddress";
					tError.errorMessage = "数据库查询失败!";
					this.mErrors.addOneError(tError);

					return false;
				}
				if (!StrTool.compareString(mLCAddressSchema.getCustomerNo(),
						tLCAddressDB.getCustomerNo())
						|| (mLCAddressSchema.getAddressNo() != tLCAddressDB
								.getAddressNo())
						|| !StrTool.compareString(mLCAddressSchema
								.getPostalAddress(), tLCAddressDB
								.getPostalAddress())
						|| !StrTool.compareString(
								mLCAddressSchema.getZipCode(), tLCAddressDB
										.getZipCode())
						|| !StrTool.compareString(mLCAddressSchema.getPhone(),
								tLCAddressDB.getPhone())
						|| !StrTool.compareString(mLCAddressSchema.getFax(),
								tLCAddressDB.getFax())
						|| !StrTool.compareString(mLCAddressSchema
								.getHomeAddress(), tLCAddressDB
								.getHomeAddress())
						|| !StrTool.compareString(mLCAddressSchema
								.getHomeZipCode(), tLCAddressDB
								.getHomeZipCode())
						|| !StrTool.compareString(mLCAddressSchema
								.getHomePhone(), tLCAddressDB.getHomePhone())
						|| !StrTool.compareString(
								mLCAddressSchema.getHomeFax(), tLCAddressDB
										.getHomeFax())
						|| !StrTool.compareString(mLCAddressSchema
								.getCompanyAddress(), tLCAddressDB
								.getCompanyAddress())
						|| !StrTool.compareString(mLCAddressSchema
								.getCompanyZipCode(), tLCAddressDB
								.getCompanyZipCode())
						|| !StrTool.compareString(mLCAddressSchema
								.getCompanyPhone(), tLCAddressDB
								.getCompanyPhone())
						|| !StrTool.compareString(mLCAddressSchema
								.getCompanyFax(), tLCAddressDB.getCompanyFax())
						|| !StrTool.compareString(mLCAddressSchema.getMobile(),
								tLCAddressDB.getMobile())
						|| !StrTool.compareString(mLCAddressSchema
								.getMobileChs(), tLCAddressDB.getMobileChs())
						|| !StrTool.compareString(mLCAddressSchema.getEMail(),
								tLCAddressDB.getEMail())
						|| !StrTool.compareString(mLCAddressSchema.getBP(),
								tLCAddressDB.getBP())
						|| !StrTool.compareString(
								mLCAddressSchema.getMobile2(), tLCAddressDB
										.getMobile2())
						|| !StrTool.compareString(mLCAddressSchema
								.getMobileChs2(), tLCAddressDB.getMobileChs2())
						|| !StrTool.compareString(mLCAddressSchema.getEMail2(),
								tLCAddressDB.getEMail2())
						|| !StrTool.compareString(mLCAddressSchema.getBP2(),
								tLCAddressDB.getBP2())) {
					CError tError = new CError();
					tError.moduleName = "LDPersonBL";
					tError.functionName = "checkAddress";
					tError.errorMessage = "您输入的地址信息与数据库里对应的信息不符，请去掉地址号，重新生成!";
					this.mErrors.addOneError(tError);

					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return this.mResult;
	}

	public VData getBack() {
		return this.tResult;
	}

	// 主函数
	public static void main(String[] args) {
		LDPersonSchema mLDPersonSchema = new LDPersonSchema();
		LCAddressSchema mLCAddressSchema = new LCAddressSchema();
		LCAccountSchema mLCAccountSchema = new LCAccountSchema();
		GlobalInput tGI = new GlobalInput();

		mLDPersonSchema.setName("张涛");
		mLDPersonSchema.setBirthday("1983-03-27");
		mLDPersonSchema.setSex("0");

		VData tVData = new VData();
		tVData.add(mLDPersonSchema);
		tVData.add(mLCAddressSchema);
		tVData.add(mLCAccountSchema);
		tVData.add(tGI);
		LDPersonBL tLDPersonBL = new LDPersonBL();
		tLDPersonBL.submitData(tVData, "INSERT");
	}
}

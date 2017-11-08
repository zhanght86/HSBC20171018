package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpAddressDB;
import com.sinosoft.lis.db.LDGrpDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCGrpAddressSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.vschema.LDGrpSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HZM
 * @version 1.0
 */

public class LDGrpBL {
private static Logger logger = Logger.getLogger(LDGrpBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	private VData mInputData;
	private LDGrpSet mLDGrpSet;
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private GlobalInput tGI = new GlobalInput();
	private VData mResult = new VData();
	// 业务处理相关变量
	/** 集体信息 */
	private LDGrpSchema mLDGrpSchema = new LDGrpSchema();
	/** 地址信息 */
	private LCGrpAddressSchema mLCGrpAddressSchema = new LCGrpAddressSchema();

	public LDGrpBL() {
	}

	public static void main(String[] args) {
		LDGrpBL LDGrpBL1 = new LDGrpBL();
		LDGrpSchema mLDGrpSchema = new LDGrpSchema();
		LCGrpAddressSchema mLCGrpAddressSchema = new LCGrpAddressSchema();
		mLDGrpSchema.setCustomerNo("0000004000");
		mLDGrpSchema.setGrpName("测试单位");
		mLDGrpSchema.setGrpNature("04");
		mLDGrpSchema.setFax("010");
		// mLCGrpAddressSchema.setCustomerNo("0000004000");
		mLCGrpAddressSchema.setAddressNo("86000000000034 ");
		mLCGrpAddressSchema.setGrpAddress("随便2");
		// mLCGrpAddressSchema.setGrpAddress("zhongguancun");
		VData tv = new VData();

		tv.add(mLDGrpSchema);
		tv.add(mLCGrpAddressSchema);
		LDGrpBL1.submitData(tv, "UPDATE");
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		logger.debug("OperateData:" + cOperate);
		// 进行业务处理
		if (!dealData(cOperate)) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("Start LDGrp BL Submit...");

		LDGrpBLS tLDGrpBLS = new LDGrpBLS();
		tLDGrpBLS.submitData(mInputData, cOperate);

		logger.debug("End LDGrp BL Submit...");

		// 如果有需要处理的错误，则返回
		if (tLDGrpBLS.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLDGrpBLS.mErrors);
		}

		mInputData = null;
		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData(String cOperate) {
		boolean tReturn = false;
		String tNo = "";

		if (!getInputData()) {
			return false;
		}
		logger.debug("After getinputdata");
		logger.debug(cOperate);

		boolean mNeedAddressNo = chkAddress();
		// 处理集体信息数据
		// 添加纪录
		if (cOperate.equals("INSERT")) {
			String tLimit = "SN";
			String tGrpNo = PubFun1.CreateMaxNo("GrpNo", tLimit);
			mLDGrpSchema.setCustomerNo(tGrpNo);
			// 添加集体信息表中的不能为空的字段(MakeDate,MakeTime,ModifyDate,ModifyTime)
			// 入机日期,入机时间, 最后一次修改日期,最后一次修改时间
			mLDGrpSchema.setMakeDate(CurrentDate);
			mLDGrpSchema.setMakeTime(CurrentTime);
			mLDGrpSchema.setModifyDate(CurrentDate);
			mLDGrpSchema.setModifyTime(CurrentTime);

			if (mNeedAddressNo) {
				mLCGrpAddressSchema.setCustomerNo(tGrpNo);
				mLCGrpAddressSchema.setAddressNo(createAddressNo(tGrpNo));
				mLCGrpAddressSchema.setOperator(mLDGrpSchema.getOperator());
				mLCGrpAddressSchema.setMakeDate(CurrentDate);
				mLCGrpAddressSchema.setMakeTime(CurrentTime);
				mLCGrpAddressSchema.setModifyDate(CurrentDate);
				mLCGrpAddressSchema.setModifyTime(CurrentTime);
			}
			logger.debug("mNeedAddress:" + mNeedAddressNo + "   grpno:"
					+ mLDGrpSchema.getCustomerNo());

			if (mLCGrpAddressSchema == null) {
				logger.debug("---------mLCGrpAddressSchema is null----------");
			}
			tReturn = true;
		}
		// 更新纪录
		if (cOperate.equals("UPDATE")) {
			// 更新集体信息表中的不能为空的字段(MakeDate,MakeTime,ModifyDate,ModifyTime)
			// 入机日期,入机时间, 最后一次修改日期,最后一次修改时间

			LDGrpDB tLDGrpDB = new LDGrpDB();
			tLDGrpDB.setCustomerNo(mLDGrpSchema.getCustomerNo());
			if (!tLDGrpDB.getInfo()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLDGrpDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "LDGrpBl";
				tError.functionName = "updateData";
				tError.errorMessage = "查询信息失败!";
				this.mErrors.addOneError(tError);
				tReturn = false;
			}

			tLDGrpDB.setPassword(mLDGrpSchema.getPassword());
			tLDGrpDB.setGrpName(mLDGrpSchema.getGrpName());

			tLDGrpDB.setBusinessType(mLDGrpSchema.getBusinessType());
			tLDGrpDB.setGrpNature(mLDGrpSchema.getGrpNature());
			tLDGrpDB.setPeoples(mLDGrpSchema.getPeoples());
			tLDGrpDB.setRgtMoney(mLDGrpSchema.getRgtMoney());
			tLDGrpDB.setAsset(mLDGrpSchema.getAsset());
			tLDGrpDB.setNetProfitRate(mLDGrpSchema.getNetProfitRate());
			tLDGrpDB.setMainBussiness(mLDGrpSchema.getMainBussiness());
			tLDGrpDB.setCorporation(mLDGrpSchema.getCorporation());
			tLDGrpDB.setComAera(mLDGrpSchema.getComAera());

			tLDGrpDB.setFax(mLDGrpSchema.getFax());
			tLDGrpDB.setPhone(mLDGrpSchema.getPhone());
			tLDGrpDB.setGetFlag(mLDGrpSchema.getGetFlag());
			tLDGrpDB.setSatrap(mLDGrpSchema.getSatrap());
			tLDGrpDB.setEMail(mLDGrpSchema.getEMail());
			tLDGrpDB.setFoundDate(mLDGrpSchema.getFoundDate());
			tLDGrpDB.setBankCode(mLDGrpSchema.getBankCode());
			tLDGrpDB.setBankAccNo(mLDGrpSchema.getBankAccNo());
			tLDGrpDB.setGrpGroupNo(mLDGrpSchema.getGrpGroupNo());
			tLDGrpDB.setState(mLDGrpSchema.getState());
			tLDGrpDB.setRemark(mLDGrpSchema.getRemark());
			tLDGrpDB.setBlacklistFlag(mLDGrpSchema.getBlacklistFlag());
			tLDGrpDB.setVIPValue(mLDGrpSchema.getVIPValue());
			tLDGrpDB.setSubCompanyFlag(mLDGrpSchema.getSubCompanyFlag());
			tLDGrpDB.setSupCustoemrNo(mLDGrpSchema.getSupCustoemrNo());
			tLDGrpDB.setOperator(mLDGrpSchema.getOperator());

			mLDGrpSchema.setSchema(tLDGrpDB);
			mLDGrpSchema.setModifyDate(CurrentDate);
			mLDGrpSchema.setModifyTime(CurrentTime);

			LCGrpAddressDB tLCGrpAddressDB = new LCGrpAddressDB();
			if (mNeedAddressNo) {
				mLCGrpAddressSchema.setCustomerNo(mLDGrpSchema.getCustomerNo());
				mLCGrpAddressSchema.setAddressNo(createAddressNo(mLDGrpSchema
						.getCustomerNo()));
			}
			if (mLCGrpAddressSchema.getAddressNo() != null
					&& !mLCGrpAddressSchema.getAddressNo().trim().equals("")) {
				mLCGrpAddressSchema.setCustomerNo(mLDGrpSchema.getCustomerNo());
				mLCGrpAddressSchema.setModifyDate(CurrentDate);
				mLCGrpAddressSchema.setModifyTime(CurrentTime);
			}
			logger.debug("----update---LCGrpAddress:"
					+ mLCGrpAddressSchema.encode());

			tReturn = true;
		}
		// 删除纪录
		if (cOperate.equals("DELETE")) {
			// 为删除操作处理数据
			logger.debug("delete is done!!!");
			tReturn = true;
		}

		return tReturn;
	}

	// 从输入数据中得到所有对象
	// 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	private boolean getInputData() {
		// 集体信息
		mLDGrpSchema = (LDGrpSchema) mInputData.getObjectByObjectName(
				"LDGrpSchema", 0);
		mLCGrpAddressSchema = (LCGrpAddressSchema) mInputData
				.getObjectByObjectName("LCGrpAddressSchema", 0);
		tGI = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		logger.debug(mLDGrpSchema);
		if (mLDGrpSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDGrpBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "在集体信息接受时没有得到足够的数据，请您确认有：集体的完整信息!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// 准备往后层输出所需要的数据
	// 输出：如果输入数据有错，则返回false,否则返回true
	private boolean checkInputData() {
		try {
			// mLDGrpSchema;

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDGrpBL";
			tError.functionName = "checkData";
			tError.errorMessage = "在校验输入的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean chkAddress() {
		if ((mLCGrpAddressSchema.getGrpAddress() == null || mLCGrpAddressSchema
				.getGrpAddress().equals(""))
				&& (mLCGrpAddressSchema.getGrpZipCode() == null || mLCGrpAddressSchema
						.getGrpZipCode().equals(""))
				&& (mLCGrpAddressSchema.getLinkMan1() == null || mLCGrpAddressSchema
						.getLinkMan1().equals(""))
				&& (mLCGrpAddressSchema.getDepartment1() == null || mLCGrpAddressSchema
						.getDepartment1().equals(""))
				&& (mLCGrpAddressSchema.getHeadShip1() == null || mLCGrpAddressSchema
						.getHeadShip1().equals(""))
				&& (mLCGrpAddressSchema.getPhone1() == null || mLCGrpAddressSchema
						.getPhone1().equals(""))
				&& (mLCGrpAddressSchema.getE_Mail1() == null || mLCGrpAddressSchema
						.getE_Mail1().equals(""))
				&& (mLCGrpAddressSchema.getFax1() == null || mLCGrpAddressSchema
						.getFax1().equals(""))
				&& (mLCGrpAddressSchema.getLinkMan2() == null || mLCGrpAddressSchema
						.getLinkMan2().equals(""))
				&& (mLCGrpAddressSchema.getDepartment2() == null || mLCGrpAddressSchema
						.getDepartment2().equals(""))
				&& (mLCGrpAddressSchema.getHeadShip2() == null || mLCGrpAddressSchema
						.getHeadShip2().equals(""))
				&& (mLCGrpAddressSchema.getPhone2() == null || mLCGrpAddressSchema
						.getPhone2().equals(""))
				&& (mLCGrpAddressSchema.getE_Mail2() == null || mLCGrpAddressSchema
						.getE_Mail2().equals(""))
				&& (mLCGrpAddressSchema.getFax2() == null || mLCGrpAddressSchema
						.getFax2().equals(""))) {
			return false;
		}
		if (mLCGrpAddressSchema.getAddressNo() != null
				&& !mLCGrpAddressSchema.getAddressNo().trim().equals("")) {
			return false;
		}
		logger.debug("return true");
		return true;
	}

	private String createAddressNo(String tCustomerNo) {
		String tAddressNo = "";
		SSRS tSSRS = new SSRS();
		String sql = "Select Case When max(AddressNo) Is Null Then '0' Else max(AddressNo) End from LCGrpAddress where CustomerNo='"
				+ tCustomerNo + "'";
		logger.debug("--------sql:" + sql);
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sql);
		Integer firstinteger = Integer.valueOf(tSSRS.GetText(1, 1));
		int ttNo = firstinteger.intValue() + 1;
		Integer integer = new Integer(ttNo);
		tAddressNo = integer.toString();
		logger.debug("得到的地址码是：" + tAddressNo);
		if (!tAddressNo.equals("")) {
			mLCGrpAddressSchema.setAddressNo(tAddressNo);
		} else {
			mErrors.addOneError(new CError("客户地址号码生成失败"));
			return "";
		}
		return tAddressNo;
	}

	// 准备往后层输出所需要的数据
	// 输出：如果准备数据时发生错误则返回false,否则返回true
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(mLDGrpSchema);
			mInputData.add(mLCGrpAddressSchema);
			mResult.add(mLDGrpSchema);
			mResult.add(mLCGrpAddressSchema);

			logger.debug("prepareOutputData:");
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDGrpBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// 查询纪录的公共方法
	public LDGrpSet queryData(VData cInputData) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		logger.debug("LDGrpBL:QueryData");
		// 进行业务处理

		// 准备往后台的数据

		logger.debug("Start LDGrp BL Query...");

		LDGrpBLS tLDGrpBLS = new LDGrpBLS();
		mLDGrpSet = tLDGrpBLS.queryData(mInputData);

		logger.debug("End LDGrp BL Query...");

		// 如果有需要处理的错误，则返回
		if (tLDGrpBLS.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLDGrpBLS.mErrors);
			mLDGrpSet = null;
		}

		mInputData = null;
		return mLDGrpSet;
	}

	public VData getResult() {

		return this.mResult;
	}

}

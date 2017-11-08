package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LCGrpAddressSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.vschema.LDGrpSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:集体信息类（界面输入） 从错误对象处理类继承，用来保存错误对象,在每个类中都存在
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

public class LDGrpUI {
private static Logger logger = Logger.getLogger(LDGrpUI.class);

	private VData mInputData;
	public CErrors mErrors = new CErrors();
	private LDGrpSet mLDGrpSet;
	private VData mResult = new VData();

	public LDGrpUI() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		LDGrpBL tLDGrpBL = new LDGrpBL();
		logger.debug("Start LDGrp UI Submit...");
		tLDGrpBL.submitData(mInputData, cOperate);

		logger.debug("End LDGrp UI Submit...");

		// 如果有需要处理的错误，则返回
		if (tLDGrpBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLDGrpBL.mErrors);
		}
		logger.debug(mErrors.getErrorCount());
		if (cOperate.equals("INSERT") || cOperate.equals("QUERY")
				|| cOperate.equals("UPDATE")) {
			this.mResult.clear();
			this.mResult = tLDGrpBL.getResult();
			LDGrpSchema tLDGrpSchema = new LDGrpSchema();
			LCGrpAddressSchema tLCGrpAddressSchema = new LCGrpAddressSchema();
			tLDGrpSchema = (LDGrpSchema) mResult.getObjectByObjectName(
					"LDGrpSchema", 0);
			tLCGrpAddressSchema = (LCGrpAddressSchema) mResult
					.getObjectByObjectName("LCGrpAddressSchema", 0);
			// logger.debug("UIresult"+tLDGrpSchema.getGrpNo());
		}
		mInputData = null;
		return true;
	}

	// 查询纪录的公共方法
	public LDGrpSet queryData(VData cInputData) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		LDGrpBL tLDGrpBL = new LDGrpBL();
		logger.debug("Start LDGrp UI Query...");
		mLDGrpSet = tLDGrpBL.queryData(mInputData);

		logger.debug("End LDGrp UI Query...");

		// 如果有需要处理的错误，则返回
		if (tLDGrpBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLDGrpBL.mErrors);
		}
		logger.debug(mErrors.getErrorCount());
		mInputData = null;
		return mLDGrpSet;

	}

	public VData getResult() {
		return this.mResult;
	}

	public static void main(String[] args) {
		// GlobalInput tG = new GlobalInput();
		// tG.ManageCom="86110000";
		LDGrpSchema mLDGrpSchema = new LDGrpSchema();
		mLDGrpSchema.setGrpName("111");
		// mLDGrpSchema.setGrpNo("86000020020990000005");
		mLDGrpSchema.setOperator("admin");
		LDGrpUI mLDGrpUI = new LDGrpUI();
		VData mVData = new VData();
		mVData.addElement(mLDGrpSchema);
		mLDGrpUI.submitData(mVData, "UPDATE");
		LDGrpSchema tLDGrpSchema = new LDGrpSchema();
		tLDGrpSchema = (LDGrpSchema) mVData.getObjectByObjectName(
				"LDGrpSchema", 0);
	}
}

package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCAccountSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * Title: Web业务系统 LDPersonUI.java Description:个人信息类（界面输入）客户管理模块新增客户 Copyright:
 * Copyright (c) 2005 Company: Sinosoft
 * 
 * @author wangyan
 * @version 1.0
 */

public class LDPersonUI  implements BusinessService{
private static Logger logger = Logger.getLogger(LDPersonUI.class);
	public CErrors mErrors = new CErrors();
	private VData mInputData;
	private VData mResult = new VData();
	private String mOperate;

	private LDPersonSchema mLDPersonSchema = new LDPersonSchema();
	private LCAddressSchema mLCAddressSchema = new LCAddressSchema();
	private LCAccountSchema mLCAccountSchema = new LCAccountSchema();
	private GlobalInput tGI = new GlobalInput();

	public LDPersonUI() {

	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		mLDPersonSchema = (LDPersonSchema) mInputData.getObjectByObjectName(
				"LDPersonSchema", 0);
		mLCAddressSchema = (LCAddressSchema) mInputData.getObjectByObjectName(
				"LCAddressSchema", 0);
		mLCAccountSchema = (LCAccountSchema) mInputData.getObjectByObjectName(
				"LCAccountSchema", 0);
		tGI = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		logger.debug("CustomerNo is " + mLDPersonSchema.getCustomerNo());
		logger.debug("AddressNo is " + mLCAddressSchema.getAddressNo());

		LDPersonBLF tLDPersonBLF = new LDPersonBLF();
		tLDPersonBLF.submitData(mInputData, mOperate);

		// 如果有需要处理的错误，则返回
		if (tLDPersonBLF.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLDPersonBLF.mErrors);
		}

		// 从BLF层取得结果返回前台
		this.mResult.clear();
		this.mResult = tLDPersonBLF.getResult();
		mInputData = null;

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

	// 主函数
	public static void main(String[] args) {

	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}

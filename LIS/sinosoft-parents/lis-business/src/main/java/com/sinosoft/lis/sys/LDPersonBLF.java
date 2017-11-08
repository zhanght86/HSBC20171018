package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCAccountSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * Title: Web业务系统 LDPersonBLF.java Description:个人信息类（界面输入）客户管理模块新增客户 Copyright:
 * Copyright (c) 2005 Company: Sinosoft
 * 
 * @author wangyan
 * @version 1.0
 */

public class LDPersonBLF {
private static Logger logger = Logger.getLogger(LDPersonBLF.class);
	public LDPersonBLF() {

	}

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后台传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private VData tResult = new VData();;

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 个人信息 */
	private LDPersonSchema mLDPersonSchema = new LDPersonSchema();
	private LCAddressSchema mLCAddressSchema = new LCAddressSchema();
	private LCAccountSchema mLCAccountSchema = new LCAccountSchema();
	private GlobalInput tGI = new GlobalInput();

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据
	 * @param cOperate
	 *            数据操作字符串
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		logger.debug("Start Data Submit..." + mOperate);

		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug(mOperate);
		// logger.debug(((LDPersonSchema)mInputData.get(1)).getCustomerNo());
		if (!tPubSubmit.submitData(mResult, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDPersonBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			logger.debug("---fail commitData---");
			return false;
		}

		logger.debug("---success commitData---");

		mResult = null;

		logger.debug("End Data Submit..." + mOperate);

		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLDPersonSchema = (LDPersonSchema) mInputData
					.getObjectByObjectName("LDPersonSchema", 0);
			mLCAddressSchema = (LCAddressSchema) mInputData
					.getObjectByObjectName("LCAddressSchema", 0);
			mLCAccountSchema = (LCAccountSchema) mInputData
					.getObjectByObjectName("LCAccountSchema", 0);
			tGI = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput",
					0);

			if (mLDPersonSchema == null) {
				return false;
			}
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}
		return true;
	}

	/**
	 * 准备需要保存的数据 调用新增客户业务逻辑处理类LDPersonBL进行处理
	 */
	private boolean dealData() {
		LDPersonBL tLDPersonBL = new LDPersonBL();
		if (!tLDPersonBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tLDPersonBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDPersonBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 从BL层取得结果返回前台
		mResult.clear();
		mResult = tLDPersonBL.getResult();
		tResult = tLDPersonBL.getBack();

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return tResult;
	}

	// 主函数
	public static void main(String[] args) {

	}
}

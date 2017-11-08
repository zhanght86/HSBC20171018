package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 个单被保人基本信息变更
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @ReWrite pst
 * @version 1.0
 */
public class PEdorAMDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorAMDetailBLF.class);
	public PEdorAMDetailBLF() {
	}

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPAddressSchema mLPAddressSchema = new LPAddressSchema();
	private LPInsuredSchema mLPInsuredSchema = new LPInsuredSchema();
	private GlobalInput mGlobalInput = new GlobalInput();

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

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorAMDetailBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回处理错误
	 * 
	 * @return: CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			if (mLPEdorItemSchema == null || mGlobalInput == null) {
				return false;
			}
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}
		return true;
	}

	/**
	 * 准备需要保存的数据 调用个人保全投保人信息变更业务逻辑处理类PEdorACBL进行处理
	 */
	private boolean dealData() {
		PEdorAMDetailBL tPEdorAMDetailBL = new PEdorAMDetailBL();
		if (!tPEdorAMDetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorAMDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorAMDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		mResult = tPEdorAMDetailBL.getResult();
		return true;
	}
}

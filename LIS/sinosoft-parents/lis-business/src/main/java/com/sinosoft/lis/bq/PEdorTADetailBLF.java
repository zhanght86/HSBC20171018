package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.vschema.LPAGetDrawSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:保全
 * </p>
 * <p>
 * Description:转养老金BLF层
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Nicholas
 * @version 1.0
 */

public class PEdorTADetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorTADetailBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 封装将要提交数据 */
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 增加的养老金金额 */
	private String mSumAmnt;
	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPAGetDrawSet mLPAGetDrawSet = new LPAGetDrawSet();
	private LPDutySchema mLPDutySchema = new LPDutySchema();
	private LPGetSchema mLPGetSchema = new LPGetSchema();
	private GlobalInput mGlobalInput = new GlobalInput();

	public PEdorTADetailBLF() {
	}

	public VData getResult() {
		return mResult;
	}

	public String getSumAmnt() {
		return this.mSumAmnt;
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
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return: boolean
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		this.mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorTADetailBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 把增加的养老金金额结果加入mResult
		mResult.clear();
		mResult.add(this.mSumAmnt);
		return true;
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
			mLPAGetDrawSet = (LPAGetDrawSet) mInputData.getObjectByObjectName(
					"LPAGetDrawSet", 0);
			mLPDutySchema = (LPDutySchema) mInputData.getObjectByObjectName(
					"LPDutySchema", 0);
			mLPGetSchema = (LPGetSchema) mInputData.getObjectByObjectName(
					"LPGetSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);

			if (mLPAGetDrawSet == null || mLPDutySchema == null
					|| mGlobalInput == null || mLPGetSchema == null
					|| mLPEdorItemSchema == null) {
				return false;
			}
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}
		return true;
	}

	/**
	 * 准备需要保存的数据 调用交费形式变更业务逻辑处理类PEdorTADetailBL进行处理
	 */
	private boolean dealData() {
		PEdorTADetailBL tPEdorTADetailBL = new PEdorTADetailBL();
		if (!tPEdorTADetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorTADetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorTADetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		mResult = tPEdorTADetailBL.getResult();
		mMap.add((MMap) mResult.getObjectByObjectName("MMap", 0));
		mInputData.clear();
		mInputData.add(mMap);
		mSumAmnt = tPEdorTADetailBL.getSumAmnt();
		return true;
	}
}

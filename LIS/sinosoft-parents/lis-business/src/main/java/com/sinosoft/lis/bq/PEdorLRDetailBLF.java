package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class PEdorLRDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorLRDetailBLF.class);

	private VData mInputData = new VData();
	private String mOperate;

	public VData mResult = new VData();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap mMap = new MMap();

	public PEdorLRDetailBLF() {
	}

	public boolean submitData(VData aInputData, String aOperator) {
		mInputData = (VData) aInputData.clone();
		mOperate = aOperator;
		if (!getinputdata(mInputData)) {
			return false;
		}

		if (!checkdata()) {
			return false;
		}

		if (!dealdata()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorPTDetailBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	private boolean getinputdata(VData cInputData) {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);

		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "PEdorLRDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mGlobalInput == null || mLPEdorItemSchema == null) {
			CError tError = new CError();
			tError.moduleName = "PEdorLRDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "输入数据有误!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	private boolean checkdata() {
		return true;
	}

	private boolean dealdata() {
		// UpdateEdorState tUpdateEdorState = new UpdateEdorState();
		// MMap mMap1 = new MMap();
		// mMap1.put(tUpdateEdorState.getUpdateEdorStateSql(mLPEdorItemSchema),
		// "UPDATE");
		// mMap.add(mMap1);
		PEdorLRDetailBL tPEdorLRDetailBL = new PEdorLRDetailBL();
		if (!tPEdorLRDetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorLRDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorLRDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		VData tResult = tPEdorLRDetailBL.getResult();
		// mMap.add((MMap) tResult.getObjectByObjectName("MMap", 0));
		// mResult.add(mMap);
		mResult = (VData) tResult.clone();

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

}

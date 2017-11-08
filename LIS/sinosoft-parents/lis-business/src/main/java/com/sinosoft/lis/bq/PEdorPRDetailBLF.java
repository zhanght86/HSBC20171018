package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
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
public class PEdorPRDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorPRDetailBLF.class);
	public PEdorPRDetailBLF() {
	}

	public CErrors mErrors = new CErrors();

	public VData mResult = new VData();

	private MMap mMap = new MMap();

	private VData mInputData = new VData();
	private String mOperate;

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPAppntSchema mLPAppntSchema = new LPAppntSchema();
	private LPAddressSchema mLPAddressSchema = new LPAddressSchema();
	private String manageCom = "";
	private BqCalBase mBqCalBase = new BqCalBase();

	public boolean submitData(VData aInputData, String aOperator) {

		logger.debug("RE=====================>");
		mInputData = (VData) aInputData.clone();
		mOperate = aOperator;
		// if(!getinputdata(mInputData)){
		// return false;
		// }
		//
		// if(!checkdata()){
		// return false;
		// }

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
		mLPEdorItemSchema = (LPEdorItemSchema) cInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mLPAppntSchema = (LPAppntSchema) mInputData.getObjectByObjectName(
				"LPAppntSchema", 0);
		mLPAddressSchema = (LPAddressSchema) mInputData.getObjectByObjectName(
				"LPAddressSchema", 0);

		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		manageCom = (String) cInputData.getObjectByObjectName("String", 0);
		if (mLPEdorItemSchema == null || mGlobalInput == null
				|| manageCom == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}

		return true;
	}

	private boolean checkdata() {
		return true;
	}

	private boolean dealdata() {
		PEdorPRDetailBL tPEdorPRDetailBL = new PEdorPRDetailBL();
		if (!tPEdorPRDetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorPRDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorPRDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		mResult = tPEdorPRDetailBL.getResult();
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

}

package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
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
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class IndiNormalPayVerifyBLF {
private static Logger logger = Logger.getLogger(IndiNormalPayVerifyBLF.class);
	// 错误处理
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	private MMap mMap = new MMap();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	private GlobalInput tGI = new GlobalInput();

	public IndiNormalPayVerifyBLF() {
	}

	public VData getResult() {
		return mResult;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
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
			tError.moduleName = "IndiNormalPayVerifyBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean getInputData(VData mInputData) {
		tGI = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		return true;
	}

	private boolean dealData() {
		IndiNormalPayVerifyBL tIndiNormalPayVerifyBL = new IndiNormalPayVerifyBL();
		if (!tIndiNormalPayVerifyBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tIndiNormalPayVerifyBL.mErrors);
			return false;
		}
		mResult.clear();
		mResult = tIndiNormalPayVerifyBL.getResult();
		return true;
	}
}

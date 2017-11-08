package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class QualityDifferentiatedUI {
private static Logger logger = Logger.getLogger(QualityDifferentiatedUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public QualityDifferentiatedUI() {
	}


	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		QualityDifferentiatedBL tQualityDifferentiatedBL = new QualityDifferentiatedBL();
		logger.debug("size0:" + mResult.size());
		logger.debug("---QualityDifferentiatedUI UI BEGIN2---");
		if (tQualityDifferentiatedBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tQualityDifferentiatedBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "QualityDifferentiatedUI";
			tError.functionName = "submitData";
			tError.errorMessage = "操作失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		}

		mResult.clear();
		mResult = tQualityDifferentiatedBL.getResult();
		logger.debug("size:" + mResult.size());
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	// @Main
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
	}
}

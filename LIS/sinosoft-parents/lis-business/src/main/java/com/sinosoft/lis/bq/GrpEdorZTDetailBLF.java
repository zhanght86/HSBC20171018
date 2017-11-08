package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 团体保全减人操作
 * </p>
 * 
 * <p>
 * Description: 团体保全减人操作 保全明细BLF层接口
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Lizhuo
 * @version 1.0
 */
public class GrpEdorZTDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(GrpEdorZTDetailBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public GrpEdorZTDetailBLF() {
	}

	public boolean submitData(VData aInputData, String aOperator) {
		mInputData = (VData) aInputData.clone();
		mOperate = aOperator;

		if (!dealdata()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpEdorZTDetailBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean dealdata() {
		GrpEdorZTDetailBL tGrpEdorZTDetailBL = new GrpEdorZTDetailBL();
		if (!tGrpEdorZTDetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tGrpEdorZTDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpEdorZTDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		mResult = tGrpEdorZTDetailBL.getResult();
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		GrpEdorZTDetailBLF grpedorztdetailblf = new GrpEdorZTDetailBLF();
	}

}

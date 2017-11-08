package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:异常件错误原因查询以及差错原因处理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 */
public class AbnormityErrAndRecordErrUI {
private static Logger logger = Logger.getLogger(AbnormityErrAndRecordErrUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mInputData = new VData();
	private VData mResult = new VData();
	private String mOperate;

	public AbnormityErrAndRecordErrUI() {

	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		AbnormityErrAndRecordErrBL tAbnormityErrAndRecordErrBL = new AbnormityErrAndRecordErrBL();
		logger.debug("---开始执行AbnormityErrAndRecordErrUI---");
		if (tAbnormityErrAndRecordErrBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tAbnormityErrAndRecordErrBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "AbnormityErrAndRecordErrUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据保存失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tAbnormityErrAndRecordErrBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
	}
}

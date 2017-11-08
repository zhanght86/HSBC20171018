package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:保全申请确认功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Tjj
 * @version 1.0
 */
public class PEdorAcceptConfirmUI {
private static Logger logger = Logger.getLogger(PEdorAcceptConfirmUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public PEdorAcceptConfirmUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		PEdorAcceptConfirmBL tPEdorAcceptConfirmBL = new PEdorAcceptConfirmBL();
		logger.debug("---UI BEGIN---" + mOperate);
		if (tPEdorAcceptConfirmBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPEdorAcceptConfirmBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorAcceptConfirmUI";
			tError.functionName = "submitData";
			tError.errorMessage = "提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else
			mResult = tPEdorAcceptConfirmBL.getResult();
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		VData tInputData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		PEdorAcceptConfirmBL aPEdorAcceptConfirmBL = new PEdorAcceptConfirmBL();
		LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";

		tLPEdorAppSchema.setEdorAcceptNo("86000000001019");
		String strTemplatePath = "xerox/printdata/";

		tInputData.addElement(strTemplatePath);
		tInputData.addElement(tLPEdorAppSchema);
		tInputData.addElement(tGlobalInput);

		if (!aPEdorAcceptConfirmBL.submitData(tInputData,
				"INSERT||EDORACPTCONFIRM")) {
			logger.debug(aPEdorAcceptConfirmBL.mErrors.getErrContent());
		}
	}
}

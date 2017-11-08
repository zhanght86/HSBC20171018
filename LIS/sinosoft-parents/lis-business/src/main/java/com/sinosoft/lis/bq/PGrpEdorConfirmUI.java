package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
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
public class PGrpEdorConfirmUI {
private static Logger logger = Logger.getLogger(PGrpEdorConfirmUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public PGrpEdorConfirmUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		PGrpEdorConfirmBL tPGrpEdorConfirmBL = new PGrpEdorConfirmBL();
		logger.debug("---UI BEGIN---" + mOperate);
		if (tPGrpEdorConfirmBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPGrpEdorConfirmBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PGrpEdorConfirmUI";
			tError.functionName = "submitData";
			tError.errorMessage = "提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else
			mResult = tPGrpEdorConfirmBL.getResult();
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		VData tInputData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		PGrpEdorConfirmUI aPGrpEdorConfirmUI = new PGrpEdorConfirmUI();
		LPGrpEdorMainSchema tLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";

		tLPGrpEdorMainSchema.setGrpContNo("240110000000207");
		tLPGrpEdorMainSchema.setEdorNo("430110000000395");
		String strTemplatePath = "xerox/printdata/";

		tInputData.addElement(strTemplatePath);
		tInputData.addElement(tLPGrpEdorMainSchema);
		tInputData.addElement(tGlobalInput);

		if (!aPGrpEdorConfirmUI
				.submitData(tInputData, "INSERT||GRPEDORCONFIRM")) {
			logger.debug(aPGrpEdorConfirmUI.mErrors.getErrContent());
		} else {
			logger.debug(aPGrpEdorConfirmUI.getResult().get(0));
		}
	}
}

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
public class PGrpEdorAppConfirmUI {
private static Logger logger = Logger.getLogger(PGrpEdorAppConfirmUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	private String mPayPrintParams = "";

	public PGrpEdorAppConfirmUI() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mOperate = cOperate;

		PGrpEdorAppConfirmBL tPGrpEdorAppConfirmBL = new PGrpEdorAppConfirmBL();
		logger.debug("\nStart pGrpEdorAppconfirmBL Submit ...");
		if (tPGrpEdorAppConfirmBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPGrpEdorAppConfirmBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PGrpEdorAppConfirmUI";
			tError.functionName = "submitData";
			tError.errorMessage = "提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		}
		logger.debug("End pGrpEdorAppconfirmBL Submit ...\n");

		mResult = tPGrpEdorAppConfirmBL.getResult();
		mPayPrintParams = tPGrpEdorAppConfirmBL.getPrtParams();
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public String getPrtParams() {
		return mPayPrintParams;
	}

	public static void main(String[] args) {
		LPGrpEdorMainSchema tLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
		tLPGrpEdorMainSchema.setGrpContNo("240110000000269"); // 86330020030220000012
		tLPGrpEdorMainSchema.setEdorNo("6320051009000001");
		tLPGrpEdorMainSchema.setEdorAcceptNo("6320051009000001");
		PGrpEdorAppConfirmUI tPGrpEdorAppConfirmUI = new PGrpEdorAppConfirmUI();

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ComCode = "86";
		tGlobalInput.ManageCom = "86";

		VData tVData = new VData();
		tVData.addElement(tLPGrpEdorMainSchema);
		tVData.addElement(tGlobalInput);
		tPGrpEdorAppConfirmUI.submitData(tVData, "INSERT||EDORAPPCONFIRM");
	}
}

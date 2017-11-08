package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPCUWMasterSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:保全个单人工核保功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author FanX
 * @version 1.0
 */
public class PEdorMainManuUWUI {
private static Logger logger = Logger.getLogger(PEdorMainManuUWUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public PEdorMainManuUWUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		PEdorMainManuUWBL tPEdorMainManuUWBL = new PEdorMainManuUWBL();
		if (tPEdorMainManuUWBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPEdorMainManuUWBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorMainManuUWUI";
			tError.functionName = "submitData";
			tError.errorMessage = "提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tPEdorMainManuUWBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		VData tInputData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		PEdorMainManuUWUI aPEdorMainManuUWUI = new PEdorMainManuUWUI();
		LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
		LPCUWMasterSchema tLPCUWMasterSchema = new LPCUWMasterSchema();

		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";

		tLPEdorMainSchema.setEdorNo("410000000000186");
		tLPEdorMainSchema.setUWState("9");

		tLPCUWMasterSchema.setUWIdea("temp test");
		tLPCUWMasterSchema.setPassFlag("9");

		tInputData.addElement(tLPEdorMainSchema);
		tInputData.addElement(tLPCUWMasterSchema);
		tInputData.addElement(tGlobalInput);

		aPEdorMainManuUWUI.submitData(tInputData, "");
		logger.debug(aPEdorMainManuUWUI.mErrors.getErrContent());
	}
}

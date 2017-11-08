package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统个人单人工核保送打印队列部分
 * </p>
 * <p>
 * Description:接口功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WHN
 * @version 1.0
 */
public class UWSendPrintUI {
private static Logger logger = Logger.getLogger(UWSendPrintUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public UWSendPrintUI() {
	}

	// @Main
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "05";

		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setOtherNo("130110000013702");
		tLOPRTManagerSchema.setOtherNoType("02");
		tLOPRTManagerSchema.setCode("83");

		VData tVData = new VData();
		tVData.add(tLOPRTManagerSchema);
		tVData.add(tG);

		UWSendPrintUI ui = new UWSendPrintUI();
		if (ui.submitData(tVData, "") == true) {
			logger.debug("---ok---");
		} else {
			logger.debug("---NO---");
		}
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		UWSendPrintBL tUWSendPrintBL = new UWSendPrintBL();

		logger.debug("---UWSendPrintUI BEGIN---");
		if (tUWSendPrintBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tUWSendPrintBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWSendPrintUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		}
		return true;
	}
}

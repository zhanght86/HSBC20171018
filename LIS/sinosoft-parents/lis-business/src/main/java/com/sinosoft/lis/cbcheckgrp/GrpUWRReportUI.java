package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCRReportItemSchema;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCRReportItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统体发起团体生调任务
 * </p>
 * <p>
 * Description:承保个人单自动核保功能类
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
public class GrpUWRReportUI {
private static Logger logger = Logger.getLogger(GrpUWRReportUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public GrpUWRReportUI() {
	}

	// @Main
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";

		LCGrpContSchema p = new LCGrpContSchema();
		p.setGrpContNo("140110000000549");

		LCRReportItemSchema tLCRReportItemSchema = new LCRReportItemSchema();

		tLCRReportItemSchema.setRReportItemCode("001");
		tLCRReportItemSchema.setRReportItemName("普通体检");

		LCRReportItemSet tLCRReportItemSet = new LCRReportItemSet();
		tLCRReportItemSet.add(tLCRReportItemSchema);

		tLCRReportItemSchema = new LCRReportItemSchema();
		tLCRReportItemSchema.setRReportItemCode("002");
		tLCRReportItemSchema.setRReportItemName("X光");

		tLCRReportItemSet.add(tLCRReportItemSchema);

		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		tLCGrpContSet.add(p);

		VData tVData = new VData();
		tVData.add(tLCGrpContSet);
		tVData.add(tLCRReportItemSet);

		tVData.add(tG);
		GrpUWRReportUI ui = new GrpUWRReportUI();
		if (ui.submitData(tVData, "") == true)
			logger.debug("---ok---");
		else
			logger.debug("---NO---");
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		GrpUWRReportBL tGrpUWRReportBL = new GrpUWRReportBL();

		logger.debug("---GrpUWAutoHealthBL UI BEGIN---");
		if (tGrpUWRReportBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGrpUWRReportBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpUWAutoHealthUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		}
		return true;
	}
}

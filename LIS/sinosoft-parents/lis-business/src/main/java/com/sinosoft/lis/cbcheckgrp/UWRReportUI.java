package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCRReportItemSchema;
import com.sinosoft.lis.schema.LCRReportSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCRReportItemSet;
import com.sinosoft.lis.vschema.LCRReportSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统体检资料录入部分
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
public class UWRReportUI {
private static Logger logger = Logger.getLogger(UWRReportUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public UWRReportUI() {
	}

	// @Main
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";

		LCContSchema p = new LCContSchema();
		p.setProposalContNo("130110000009338");
		p.setContNo("130110000009338");

		LCContSet tLCContSet = new LCContSet();
		tLCContSet.add(p);

		//
		LCRReportSchema tLCRReportSchema = new LCRReportSchema();

		tLCRReportSchema.setContNo("130110000013233");
		tLCRReportSchema.setProposalContNo("130110000013233");
		tLCRReportSchema.setCustomerNo("0000488050");

		LCRReportSet tLCRReportSet = new LCRReportSet();
		tLCRReportSet.add(tLCRReportSchema);

		//
		LCRReportItemSchema tLCRReportItemSchema = new LCRReportItemSchema();

		tLCRReportItemSchema.setRReportItemCode("001");
		tLCRReportItemSchema.setRReportItemName("普通体检");

		LCRReportItemSet tLCRReportItemSet = new LCRReportItemSet();
		tLCRReportItemSet.add(tLCRReportItemSchema);

		tLCRReportItemSchema = new LCRReportItemSchema();
		tLCRReportItemSchema.setRReportItemCode("002");
		tLCRReportItemSchema.setRReportItemName("X光");

		tLCRReportItemSet.add(tLCRReportItemSchema);

		VData tVData = new VData();
		tVData.add(tLCContSet);
		tVData.add(tLCRReportSet);
		tVData.add(tLCRReportItemSet);
		tVData.add(tG);
		UWRReportUI ui = new UWRReportUI();
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

		UWRReportBL tUWRReportBL = new UWRReportBL();

		logger.debug("---UWAutoHealthBL UI BEGIN---");
		if (tUWRReportBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tUWRReportBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		}
		return true;
	}
}

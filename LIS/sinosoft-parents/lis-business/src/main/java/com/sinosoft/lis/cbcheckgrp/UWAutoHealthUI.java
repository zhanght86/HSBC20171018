package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPENoticeItemSchema;
import com.sinosoft.lis.schema.LCPENoticeSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCPENoticeItemSet;
import com.sinosoft.lis.vschema.LCPENoticeSet;
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
public class UWAutoHealthUI {
private static Logger logger = Logger.getLogger(UWAutoHealthUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public UWAutoHealthUI() {
	}

	// @Main
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";

		LCContSchema p = new LCContSchema();
		p.setProposalContNo("130110000013300");
		p.setContNo("130110000013300");

		LCContSet tLCContSet = new LCContSet();
		tLCContSet.add(p);

		//
		LCPENoticeSchema tLCPENoticeSchema = new LCPENoticeSchema();

		tLCPENoticeSchema.setContNo("130110000013300");
		tLCPENoticeSchema.setProposalContNo("130110000013300");
		tLCPENoticeSchema.setCustomerNo("0000488540");
		tLCPENoticeSchema.setPEAddress("11003001");
		tLCPENoticeSchema.setPEDate("2005-3-8");
		tLCPENoticeSchema.setPEBeforeCond("N");
		tLCPENoticeSchema.setRemark("13215");

		LCPENoticeSet tLCPENoticeSet = new LCPENoticeSet();
		tLCPENoticeSet.add(tLCPENoticeSchema);

		//
		LCPENoticeItemSchema tLCPENoticeItemSchema = new LCPENoticeItemSchema();

		tLCPENoticeItemSchema.setPEItemCode("001");
		tLCPENoticeItemSchema.setPEItemName("普通体检");
		tLCPENoticeItemSchema.setFreePE("N");

		LCPENoticeItemSet tLCPENoticeItemSet = new LCPENoticeItemSet();
		tLCPENoticeItemSet.add(tLCPENoticeItemSchema);

		tLCPENoticeItemSchema = new LCPENoticeItemSchema();
		tLCPENoticeItemSchema.setPEItemCode("002");
		tLCPENoticeItemSchema.setPEItemName("X光");
		tLCPENoticeItemSchema.setFreePE("N");
		tLCPENoticeItemSet.add(tLCPENoticeItemSchema);

		VData tVData = new VData();
		tVData.add(tLCContSet);
		tVData.add(tLCPENoticeSet);
		tVData.add(tLCPENoticeItemSet);
		tVData.add(tG);
		UWAutoHealthUI ui = new UWAutoHealthUI();
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

		UWAutoHealthBL tUWAutoHealthBL = new UWAutoHealthBL();

		logger.debug("---UWAutoHealthBL UI BEGIN---");
		if (tUWAutoHealthBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tUWAutoHealthBL.mErrors);
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

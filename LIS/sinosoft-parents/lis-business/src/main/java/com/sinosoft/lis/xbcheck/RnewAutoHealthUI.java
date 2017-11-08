package com.sinosoft.lis.xbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.service.BusinessService;
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
public class RnewAutoHealthUI implements BusinessService{
private static Logger logger = Logger.getLogger(RnewAutoHealthUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public RnewAutoHealthUI() {
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
		RnewPENoticeSchema tRnewPENoticeSchema = new RnewPENoticeSchema();

		tRnewPENoticeSchema.setContNo("130110000013300");
		tRnewPENoticeSchema.setProposalContNo("130110000013300");
		tRnewPENoticeSchema.setCustomerNo("0000488540");
		tRnewPENoticeSchema.setPEAddress("11003001");
		tRnewPENoticeSchema.setPEDate("2005-3-8");
		tRnewPENoticeSchema.setPEBeforeCond("N");
		tRnewPENoticeSchema.setRemark("13215");

		RnewPENoticeSet tRnewPENoticeSet = new RnewPENoticeSet();
		tRnewPENoticeSet.add(tRnewPENoticeSchema);

		//
		RnewPENoticeItemSchema tRnewPENoticeItemSchema = new RnewPENoticeItemSchema();

		tRnewPENoticeItemSchema.setPEItemCode("001");
		tRnewPENoticeItemSchema.setPEItemName("普通体检");
		tRnewPENoticeItemSchema.setFreePE("N");

		RnewPENoticeItemSet tRnewPENoticeItemSet = new RnewPENoticeItemSet();
		tRnewPENoticeItemSet.add(tRnewPENoticeItemSchema);

		tRnewPENoticeItemSchema = new RnewPENoticeItemSchema();
		tRnewPENoticeItemSchema.setPEItemCode("002");
		tRnewPENoticeItemSchema.setPEItemName("X光");
		tRnewPENoticeItemSchema.setFreePE("N");
		tRnewPENoticeItemSet.add(tRnewPENoticeItemSchema);

		VData tVData = new VData();
		tVData.add(tLCContSet);
		tVData.add(tRnewPENoticeSet);
		tVData.add(tRnewPENoticeItemSet);
		tVData.add(tG);
		RnewAutoHealthUI ui = new RnewAutoHealthUI();
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

		RnewAutoHealthBL tRnewAutoHealthBL = new RnewAutoHealthBL();

		logger.debug("---UWAutoHealthBL UI BEGIN---");
		if (tRnewAutoHealthBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tRnewAutoHealthBL.mErrors);
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

	@Override
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	@Override
	public VData getResult() {
		// TODO Auto-generated method stub
		return mResult;
	}
}

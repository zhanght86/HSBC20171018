package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title: Web业务系统体检资料查询部分
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
public class UWAutoHealthQueryUI  implements BusinessService{
private static Logger logger = Logger.getLogger(UWAutoHealthQueryUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public UWAutoHealthQueryUI() {
	}

	// @Main
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "aaa";

		LCPolSchema p = new LCPolSchema();
		p.setContNo("130110000009144");
		p.setPolNo("86110020030110002729");
		p.setInsuredNo("0000387790");

		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet.add(p);

		//
		// LCPENoticeSchema tLCPENoticeSchema = new LCPENoticeSchema();
		//
		// tLCPENoticeSchema.setContNo("130110000009144");
		// tLCPENoticeSchema.setCustomerNo("33");
		// tLCPENoticeSchema.setPEAddress("001000000");
		// tLCPENoticeSchema.setPEDate("2002-10-31");
		// tLCPENoticeSchema.setPEBeforeCond("0");
		// tLCPENoticeSchema.setRemark("TEST");
		//
		// LCPENoticeSet tLCPENoticeSet = new LCPENoticeSet();
		// tLCPENoticeSet.add(tLCPENoticeSchema);
		//
		// //
		// LCPENoticeItemSchema tLCPENoticeItemSchema = new
		// LCPENoticeItemSchema();
		//
		// tLCPENoticeItemSchema.setPEItemCode("001");
		// tLCPENoticeItemSchema.setPEItemName("普通体检");
		//
		// LCPENoticeItemSet tLCPENoticeItemSet = new LCPENoticeItemSet();
		// tLCPENoticeItemSet.add(tLCPENoticeItemSchema);
		//
		// tLCPENoticeItemSchema = new LCPENoticeItemSchema();
		// tLCPENoticeItemSchema.setPEItemCode("002");
		// tLCPENoticeItemSchema.setPEItemName("X光");
		// tLCPENoticeItemSet.add(tLCPENoticeItemSchema);

		VData tVData = new VData();
		tVData.add(p);
		// tVData.add( tLCPENoticeSet);
		// tVData.add( tLCPENoticeItemSet);
		tVData.add(tG);
		UWAutoHealthQueryUI ui = new UWAutoHealthQueryUI();
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

		UWAutoHealthQueryBL tUWAutoHealthQueryBL = new UWAutoHealthQueryBL();

		logger.debug("---UWAutoHealthQueryBL UI BEGIN---");
		if (tUWAutoHealthQueryBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tUWAutoHealthQueryBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthQueryUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		}

		mResult.clear();
		mResult = tUWAutoHealthQueryBL.getResult();
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}

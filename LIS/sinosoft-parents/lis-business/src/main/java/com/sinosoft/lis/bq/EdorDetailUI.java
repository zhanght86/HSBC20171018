/*
 * @(#)EdorDetailUI.java	2005-06-28
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:保全项目明细保存公用处理接口
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhangtao
 * @version 1.0
 */
public class EdorDetailUI implements BusinessService{
private static Logger logger = Logger.getLogger(EdorDetailUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	public EdorDetailUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		EdorDetailBL tEdorDetailBL = new EdorDetailBL();

		if (!tEdorDetailBL.submitData(cInputData, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tEdorDetailBL.mErrors);
			return false;
		}

		mResult = tEdorDetailBL.getResult();

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		CErrors tError = new CErrors();
		String FlagStr = "";
		String Content = "";

		GlobalInput tG = new GlobalInput();
		tG.Operator = "zhangtao";
		tG.ManageCom = "86110000";

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorAcceptNo("6120050926000016");
		tLPEdorItemSchema.setEdorNo("6020050926000023");
		tLPEdorItemSchema.setEdorType("CT");
		tLPEdorItemSchema.setContNo("HB240526231000032");
		tLPEdorItemSchema.setInsuredNo("000000");
		tLPEdorItemSchema.setPolNo("000000");

		// LPEdorItemSet bLPEdorItemSet = new LPEdorItemSet();
		// LPEdorItemSchema bLPEdorItemSchema = new LPEdorItemSchema();
		// bLPEdorItemSchema.setEdorAcceptNo("6120050906000029");
		// bLPEdorItemSchema.setEdorNo("6020050906000023");
		// bLPEdorItemSchema.setEdorType("CT");
		// bLPEdorItemSchema.setContNo("HB010536131001541");
		// bLPEdorItemSchema.setInsuredNo("000000");
		// bLPEdorItemSchema.setPolNo("000000");
		// bLPEdorItemSet.add(bLPEdorItemSchema);
		LPPolSet tLPPolSet = new LPPolSet();
		LPPolSchema tLPPolSchema = new LPPolSchema();
		tLPPolSchema.setPolNo("HB240526231000032000");
		tLPPolSet.add(tLPPolSchema);

		VData tVData = new VData();
		EdorDetailUI tEdorDetailUI = new EdorDetailUI();
		try {
			tVData.add(tG);
			tVData.add(tLPEdorItemSchema);
			// tVData.add(bLPEdorItemSet);
			tVData.add(tLPPolSet);
			tEdorDetailUI.submitData(tVData, "EDORITEM|INPUT");
		} catch (Exception ex) {
			Content = "保存失败，原因是:" + ex.toString();
			FlagStr = "Fail";
		}
		if ("".equals(FlagStr)) {
			tError = tEdorDetailUI.mErrors;
			if (tError.needDealError()) {
				Content = "保存成功！";
				FlagStr = "Succ";
			} else {
				Content = "保存失败，原因是:" + tError.getFirstError();
				FlagStr = "Fail";
			}
		}
		// String CurrentDate = PubFun.getCurrentDate();
		// Date dt = PubFun.calDate(new FDate().getDate(CurrentDate), -1, "M",
		// null);
		// String dayAfterCurrent = new FDate().getString(dt);
		// logger.debug("====" + dayAfterCurrent);
	}
}

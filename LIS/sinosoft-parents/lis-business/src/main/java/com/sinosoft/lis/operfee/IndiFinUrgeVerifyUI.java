package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.finfee.TempFeeQueryForUrgeGetUI;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:应交费用类（界面输入）（暂对个人） 从错误对象处理类继承，用来保存错误对象,在每个类中都存在
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HZM
 * @version 1.0
 */

public class IndiFinUrgeVerifyUI implements BusinessService {
private static Logger logger = Logger.getLogger(IndiFinUrgeVerifyUI.class);

	// 业务处理相关变量
	private VData mInputData;

	public CErrors mErrors = new CErrors();

	public IndiFinUrgeVerifyUI() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("Start IndiFinUrgeVerifyUI.Submit...");

		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		IndiFinUrgeVerifyBL tIndiFinUrgeVerifyBL = new IndiFinUrgeVerifyBL();
		tIndiFinUrgeVerifyBL.submitData(mInputData, cOperate);

		mInputData = null;
		// 如果有需要处理的错误，则返回
		if (tIndiFinUrgeVerifyBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tIndiFinUrgeVerifyBL.mErrors);
			logger.debug("error num=" + mErrors.getErrorCount());
			return false;
		}
		logger.debug("End IndiFinUrgeVerifyUI.Submit...");
		return true;
	}
	
	public VData getResult() {
		return mInputData;
	}
	
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public static void main(String[] args) {
		VData tVData = new VData();
		String TempFeeNo = "310110000000970";
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";
		tGlobalInput.ComCode = "86";

		// 查询暂交费表
		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		tLJTempFeeSchema.setTempFeeNo(TempFeeNo);
		tLJTempFeeSchema.setTempFeeType("2");// 交费类型为2：续期催收交费
		tVData.add(tLJTempFeeSchema);
		tVData.add(tGlobalInput);
		TempFeeQueryForUrgeGetUI tTempFeeQueryForUrgeGetUI = new TempFeeQueryForUrgeGetUI();
		tTempFeeQueryForUrgeGetUI.submitData(tVData, "QUERY");
		tVData.clear();
		tVData = tTempFeeQueryForUrgeGetUI.getResult();
		tLJTempFeeSet.set((LJTempFeeSet) tVData.getObjectByObjectName(
				"LJTempFeeSet", 0));
		tLJTempFeeSchema = tLJTempFeeSet.get(1);
		// 查询应收总表
		tVData.clear();
		LJSPaySchema tLJSPaySchema = new LJSPaySchema();
		LJSPaySet tLJSPaySet = new LJSPaySet();
		VerDuePayFeeQueryUI tVerDuePayFeeQueryUI = new VerDuePayFeeQueryUI();
		tLJSPaySchema.setGetNoticeNo(TempFeeNo);
		tVData.add(tLJSPaySchema);
		tVData.add(tGlobalInput);
		tVerDuePayFeeQueryUI.submitData(tVData, "QUERY");
		tVData.clear();
		tVData = tVerDuePayFeeQueryUI.getResult();
		tLJSPaySet
				.set((LJSPaySet) tVData.getObjectByObjectName("LJSPaySet", 0));
		tLJSPaySchema = tLJSPaySet.get(1);

		tVData.add(tLJSPaySchema);
		tVData.add(tLJTempFeeSchema);
		tVData.add(tGlobalInput);
		IndiFinUrgeVerifyUI IndiFinUrgeVerifyUI1 = new IndiFinUrgeVerifyUI();
		IndiFinUrgeVerifyUI1.submitData(tVData, "VERIFY");

	}
}

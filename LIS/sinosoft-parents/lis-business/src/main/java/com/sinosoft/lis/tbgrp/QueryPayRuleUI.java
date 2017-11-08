package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import java.util.Vector;

import com.sinosoft.lis.schema.LCPayRuleFactorySchema;
import com.sinosoft.lis.vschema.LCPayRuleFactorySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Lis_New
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Caihy
 * @version 1.0
 */

public class QueryPayRuleUI {
private static Logger logger = Logger.getLogger(QueryPayRuleUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往前面传输数据的容器 */
	private Vector mResult = new Vector();

	/** 接受前台传输数据的容器 */
	private TransferData mTransferData = new TransferData();

	private LCPayRuleFactorySchema mLCPayRuleFactorySchema = new LCPayRuleFactorySchema();
	private LCPayRuleFactorySet mLCPayRuleFactorySet = new LCPayRuleFactorySet();

	public QueryPayRuleUI() {
	}

	public boolean submitData(VData cInputData) {
		QueryPayRuleBL mQueryPayRuleBL = new QueryPayRuleBL();
		mQueryPayRuleBL.submitData(cInputData);
		// 如果有需要处理的错误，则返回
		if (mQueryPayRuleBL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(mQueryPayRuleBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "QueryPayRuleBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;

		mResult = mQueryPayRuleBL.getResult();

		return true;
	}

	public Vector getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		QueryPayRuleUI tQueryPayRuleUI = new QueryPayRuleUI();

		LCPayRuleFactorySchema tLCPayRuleFactorySchema = new LCPayRuleFactorySchema();
		tLCPayRuleFactorySchema.setGrpContNo("140110000000029");
		tLCPayRuleFactorySchema.setRiskCode("212403");
		tLCPayRuleFactorySchema.setPayRuleCode("A");

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("JoinCompanyDate", "2003-07-01");
		tTransferData.setNameAndValue("AppFlag", "0"); // 首期
		tTransferData.setNameAndValue("InsuredNo", "0000000010");

		VData tVData = new VData();
		tVData.addElement(tLCPayRuleFactorySchema);
		tVData.addElement(tTransferData);
		tQueryPayRuleUI.submitData(tVData);
		Vector tResult = tQueryPayRuleUI.getResult();
		for (int i = 0; i < tResult.size(); i++) {
			logger.debug("mResult" + i + ":" + tResult.get(i).toString());
		}
	}
}

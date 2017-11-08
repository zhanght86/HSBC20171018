package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPayRuleFactorySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vschema.LCPayRuleFactorySet;
import com.sinosoft.lis.vschema.LCPremSet;
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
	private VData mResult = new VData();

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

	public VData getResult() {
		return this.mResult;
	}

	public static void main(String[] args) {
		QueryPayRuleUI tQueryPayRuleUI = new QueryPayRuleUI();

		LCPayRuleFactorySchema tLCPayRuleFactorySchema = new LCPayRuleFactorySchema();
		tLCPayRuleFactorySchema.setGrpContNo("20060608000002");
		tLCPayRuleFactorySchema.setRiskCode("212403");
		tLCPayRuleFactorySchema.setPayRuleCode("A");

		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema.setInterestDifFlag("0");
		tLCPolSchema.setInsuredNo("0000009080");

		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		// logger.debug("JoinCompanyDate->"+request.getParameter("JoinCompanyDate"));
		tLCInsuredSchema.setJoinCompanyDate("2004-06-01");
		tLCInsuredSchema.setInsuredNo("0000009080");
		tLCInsuredSchema.setSalary("2000");

		VData tVData = new VData();
		tVData.addElement(tLCPayRuleFactorySchema);
		// tVData.addElement(tTransferData);
		tVData.addElement(tLCPolSchema);
		tVData.addElement(tLCInsuredSchema);

		if (!tQueryPayRuleUI.submitData(tVData)) {
			logger.debug(tQueryPayRuleUI.mErrors.getFirstError());
		} else {
			LCPremSet tLCPremSet = new LCPremSet();
			tLCPremSet = (LCPremSet) tQueryPayRuleUI.mResult
					.getObjectByObjectName("LCPremSet", -1);
			for (int i = 0; i < tLCPremSet.size(); i++) {
				String tPayPlanCode = "";
				String tPrem = "";
				tPayPlanCode = tLCPremSet.get(i + 1).getPayPlanCode()
						.toString();
				tPrem = String.valueOf(tLCPremSet.get(i + 1).getPrem());
				logger.debug("paypalncode:" + tPayPlanCode + "rate:"
						+ tPrem);
			}
		}

	}
}

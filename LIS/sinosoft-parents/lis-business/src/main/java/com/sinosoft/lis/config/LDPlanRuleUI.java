package com.sinosoft.lis.config;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LDPlanRuleSchema;
import com.sinosoft.lis.vschema.LDPlanRuleSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class LDPlanRuleUI {
private static Logger logger = Logger.getLogger(LDPlanRuleUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	private String mQueryResult = "";

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	public LDPlanRuleUI() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("Start LDPlanRuleUI UI Submit...");
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		this.mInputData = (VData) cInputData.clone();
		LDPlanRuleBL tLDPlanRuleBL = new LDPlanRuleBL();
		if (!tLDPlanRuleBL.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLDPlanRuleBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDPlanRuleUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		if ("QUERY||PLANRULE".equals(mOperate)) {
			mQueryResult = tLDPlanRuleBL.getQueryResult();
		}
		logger.debug("End LDPlan UI Submit...");
		return true;
	}

	public String getQueryResult() {
		return mQueryResult;
	}

	public static void main(String[] args) {
		LDPlanRuleUI ldplanruleui = new LDPlanRuleUI();
		LDPlanRuleBL ldplanrulebl = new LDPlanRuleBL();
		GlobalInput tG = new GlobalInput();
		tG.ComCode = "86";
		tG.ManageCom = "86";
		tG.Operator = "001";
		LDPlanRuleSchema tLDPlanRuleSchema = new LDPlanRuleSchema();
		LDPlanRuleSet tLDPlanRuleSet = new LDPlanRuleSet();

		tLDPlanRuleSchema.setContPlanCode("86000006");
		tLDPlanRuleSchema.setFactoryType("000009");

		tLDPlanRuleSchema.setFactoryCode("zh0001");
		tLDPlanRuleSchema.setFactorySubCode("1");
		tLDPlanRuleSchema.setParam("13,16,1,1");
		tLDPlanRuleSchema.setFactoryName("年龄与投保份数的限制 ");

		tLDPlanRuleSet.add(tLDPlanRuleSchema);
		VData tVData = new VData();

		tVData.add(tG);
		tVData.add(tLDPlanRuleSet);

		if (!ldplanruleui.submitData(tVData, "QUERY||PLANRULE")) {
			logger.debug(ldplanrulebl.mErrors.toString());
		}
		logger.debug(ldplanruleui.getQueryResult());
	}
}

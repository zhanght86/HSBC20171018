package com.sinosoft.workflow.plan;
import org.apache.log4j.Logger;

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
public class PlanWorkFlowUI {
private static Logger logger = Logger.getLogger(PlanWorkFlowUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	public PlanWorkFlowUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		PlanWorkFlowBL tPlanWorkFlowBL = new PlanWorkFlowBL();

		logger.debug("---AskWorkFlowBL UI BEGIN---");
		if (tPlanWorkFlowBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPlanWorkFlowBL.mErrors);
			mResult.clear();
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		PlanWorkFlowUI planworkflowui = new PlanWorkFlowUI();
	}
}

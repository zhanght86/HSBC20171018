package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LWNotePadSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class MSQuestMistakeUI {
private static Logger logger = Logger.getLogger(MSQuestMistakeUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public MSQuestMistakeUI() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作符
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		MSQuestMistakeBL tMSQuestMistakeBL = new MSQuestMistakeBL();

		if (!tMSQuestMistakeBL.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tMSQuestMistakeBL.mErrors);
			return false;
		}

		mResult = tMSQuestMistakeBL.getResult();

		return true;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";

		LWNotePadSchema tLWNotePadSchema = new LWNotePadSchema();
		tLWNotePadSchema.setActivityID("");
		tLWNotePadSchema.setMissionID("");
		tLWNotePadSchema.setSubMissionID("1");
		tLWNotePadSchema.setOtherNo("");
		tLWNotePadSchema.setOtherNoType("");
		tLWNotePadSchema
				.setNotePadCont("fgfhkljjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");

		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tLWNotePadSchema);

		WorkFlowNotePadUI tWorkFlowNotePadUI = new WorkFlowNotePadUI();

		if (tWorkFlowNotePadUI.submitData(tVData, "NOTEPAD|INSERT")) {
			logger.debug("== test succ ==");
		} else {
			logger.debug("== test fail ==");
			logger.debug("== error message:"
					+ tWorkFlowNotePadUI.mErrors.getFirstError().toString());
		}

	}
}

package com.sinosoft.lis.xbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.RnewIssuePolSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.RnewIssuePolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统问题件录入部分
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
 * @modified by ZhangRong 2004.11
 * @version 1.0
 */
public class RnewQuestInputChkUI {
private static Logger logger = Logger.getLogger(RnewQuestInputChkUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	public RnewQuestInputChkUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		RnewQuestInputChkBL tQuestInputChkBL = new RnewQuestInputChkBL();

		logger.debug("---RnewQuestInputChkBL UI BEGIN---");

		if (tQuestInputChkBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tQuestInputChkBL.mErrors);

			CError tError = new CError();
			tError.moduleName = "QuestInputChkUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();

			return false;
		}

		return true;
	}

	// @Main
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";

		LCContSchema p = new LCContSchema();
		p.setProposalContNo("32123434565454");
		p.setContNo("32123434565454");

		LCContSet tLCContSet = new LCContSet();
		tLCContSet.add(p);

		RnewIssuePolSchema tRnewIssuePolSchema = new RnewIssuePolSchema();

		tRnewIssuePolSchema.setBackObjType("2");
		tRnewIssuePolSchema.setOperatePos("1");
		tRnewIssuePolSchema.setIssueCont("1234567");

		tRnewIssuePolSchema.setContNo("00200507040001");
		tRnewIssuePolSchema.setIssueType("22");

		RnewIssuePolSet tRnewIssuePolSet = new RnewIssuePolSet();
		tRnewIssuePolSet.add(tRnewIssuePolSchema);

		VData tVData = new VData();
		tVData.add(tLCContSet);

		// tVData.add( tLCPENoticeSet);
		// tVData.add( tLCPENoticeItemSet);
		tVData.add(tRnewIssuePolSet);
		tVData.add(tG);

		RnewQuestInputChkUI ui = new RnewQuestInputChkUI();

		if (ui.submitData(tVData, "") == true) {
			logger.debug("---ok---");
		} else {
			logger.debug("---NO---");
		}
	}
}

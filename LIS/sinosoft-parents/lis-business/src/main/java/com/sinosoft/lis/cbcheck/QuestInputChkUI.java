package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCIssuePolSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
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
public class QuestInputChkUI implements BusinessService {
private static Logger logger = Logger.getLogger(QuestInputChkUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	public QuestInputChkUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		QuestInputChkBL tQuestInputChkBL = new QuestInputChkBL();

		logger.debug("---QuestInputChkBL UI BEGIN---");

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

		LCIssuePolSchema tLCIssuePolSchema = new LCIssuePolSchema();

		tLCIssuePolSchema.setBackObjType("2");
		tLCIssuePolSchema.setOperatePos("1");
		tLCIssuePolSchema.setIssueCont("1234567");

		tLCIssuePolSchema.setContNo("00200507040001");
		tLCIssuePolSchema.setIssueType("22");

		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
		tLCIssuePolSet.add(tLCIssuePolSchema);

		VData tVData = new VData();
		tVData.add(tLCContSet);

		// tVData.add( tLCPENoticeSet);
		// tVData.add( tLCPENoticeItemSet);
		tVData.add(tLCIssuePolSet);
		tVData.add(tG);

		QuestInputChkUI ui = new QuestInputChkUI();

		if (ui.submitData(tVData, "") == true) {
			logger.debug("---ok---");
		} else {
			logger.debug("---NO---");
		}
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return null;
	}
}

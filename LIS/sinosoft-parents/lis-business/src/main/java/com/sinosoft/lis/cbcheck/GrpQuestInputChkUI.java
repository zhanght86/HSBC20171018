package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpIssuePolSchema;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpIssuePolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title: Web业务系统团单问题件录入
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
 * @author Zhangrong
 * @version 1.0
 */
public class GrpQuestInputChkUI implements BusinessService{
private static Logger logger = Logger.getLogger(GrpQuestInputChkUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	public GrpQuestInputChkUI() {
	}

	// @Main

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		GrpQuestInputChkBL tGrpQuestInputChkBL = new GrpQuestInputChkBL();

		logger.debug("---GrpQuestInputChkBL UI BEGIN---");

		if (tGrpQuestInputChkBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGrpQuestInputChkBL.mErrors);

			CError tError = new CError();
			tError.moduleName = "GrpQuestInputChkUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();

			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";

		LCGrpContSchema p = new LCGrpContSchema();
		p.setGrpContNo("86110020040120000104");

		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		tLCGrpContSet.add(p);

		LCGrpIssuePolSchema tLCGrpIssuePolSchema = new LCGrpIssuePolSchema();

		tLCGrpIssuePolSchema.setBackObjType("2");
		tLCGrpIssuePolSchema.setIssueCont("1234567");
		tLCGrpIssuePolSchema.setOperatePos("1");
		tLCGrpIssuePolSchema.setGrpContNo("86110020040120000104");
		tLCGrpIssuePolSchema.setIssueType("100");

		LCGrpIssuePolSet tLCGrpIssuePolSet = new LCGrpIssuePolSet();
		tLCGrpIssuePolSet.add(tLCGrpIssuePolSchema);

		VData tVData = new VData();
		tVData.add(tLCGrpContSet);

		// tVData.add( tLCPENoticeSet);
		// tVData.add( tLCPENoticeItemSet);
		tVData.add(tLCGrpIssuePolSet);
		tVData.add(tG);

		GrpQuestInputChkUI ui = new GrpQuestInputChkUI();

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

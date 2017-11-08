package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCIssuePolSchema;
import com.sinosoft.lis.schema.LCPENoticeItemSchema;
import com.sinosoft.lis.schema.LCPENoticeSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LCPENoticeItemSet;
import com.sinosoft.lis.vschema.LCPENoticeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title: Web业务系统问题件打印标记设置部分
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
 * @version 1.0
 */
public class QuestPrintFlagUI  implements BusinessService{
private static Logger logger = Logger.getLogger(QuestPrintFlagUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public QuestPrintFlagUI() {
	}

	// @Main
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";

		LCContSchema p = new LCContSchema();
		p.setProposalContNo("00000000000000000005");
		p.setContNo("00000000000000000005");

		LCContSet tLCContSet = new LCContSet();
		tLCContSet.add(p);

		//
		LCPENoticeSchema tLCPENoticeSchema = new LCPENoticeSchema();

		tLCPENoticeSchema.setContNo("86110020030110004225");
		tLCPENoticeSchema.setCustomerNo("33");
		tLCPENoticeSchema.setPEAddress("001000000");
		tLCPENoticeSchema.setPEDate("2002-10-31");
		tLCPENoticeSchema.setPEBeforeCond("0");
		tLCPENoticeSchema.setRemark("TEST");

		LCPENoticeSet tLCPENoticeSet = new LCPENoticeSet();
		tLCPENoticeSet.add(tLCPENoticeSchema);

		//
		LCPENoticeItemSchema tLCPENoticeItemSchema = new LCPENoticeItemSchema();

		tLCPENoticeItemSchema.setPEItemCode("001");
		tLCPENoticeItemSchema.setPEItemName("普通体检");

		LCPENoticeItemSet tLCPENoticeItemSet = new LCPENoticeItemSet();
		tLCPENoticeItemSet.add(tLCPENoticeItemSchema);

		tLCPENoticeItemSchema = new LCPENoticeItemSchema();
		tLCPENoticeItemSchema.setPEItemCode("002");
		tLCPENoticeItemSchema.setPEItemName("X光");
		tLCPENoticeItemSet.add(tLCPENoticeItemSchema);

		LCIssuePolSchema tLCIssuePolSchema = new LCIssuePolSchema();

		tLCIssuePolSchema.setBackObjType("1");
		tLCIssuePolSchema.setIssueCont("1234567");
		tLCIssuePolSchema.setOperatePos("1");
		tLCIssuePolSchema.setContNo("00000000000000000005");
		tLCIssuePolSchema.setSerialNo("00000000000000000143");
		tLCIssuePolSchema.setIssueType("100");
		tLCIssuePolSchema.setNeedPrint("N");

		LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
		tLCIssuePolSet.add(tLCIssuePolSchema);

		VData tVData = new VData();
		tVData.add(tLCContSet);
		// tVData.add( tLCPENoticeSet);
		// tVData.add( tLCPENoticeItemSet);
		tVData.add(tLCIssuePolSet);
		tVData.add(tG);
		QuestPrintFlagUI ui = new QuestPrintFlagUI();
		if (ui.submitData(tVData, "") == true) {
			logger.debug("---ok---");
		} else {
			logger.debug("---NO---");
		}
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		QuestPrintFlagBL tQuestPrintFlagBL = new QuestPrintFlagBL();

		logger.debug("---QuestPrintFlagUI BEGIN---");
		if (tQuestPrintFlagBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tQuestPrintFlagBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "QuestPrintFlagUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		}
		return true;
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

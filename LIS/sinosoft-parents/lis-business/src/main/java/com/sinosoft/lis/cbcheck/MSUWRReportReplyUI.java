package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCPENoticeSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统个人单人工核保生调回复部分
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
public class MSUWRReportReplyUI implements BusinessService{
private static Logger logger = Logger.getLogger(MSUWRReportReplyUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public MSUWRReportReplyUI() {
	}

	// @Main
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";

		LCPolSchema p = new LCPolSchema();
		p.setProposalNo("86110020020110000411");
		p.setPolNo("86110020020110000411");

		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet.add(p);

		//
		LCPENoticeSchema tLCPENoticeSchema = new LCPENoticeSchema();

	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		MSUWRReportReplyBL tUWRReportReplyBL = new MSUWRReportReplyBL();

		logger.debug("---UWRReportReplyUI BEGIN---");
		if (tUWRReportReplyBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tUWRReportReplyBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWRReportReplyUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		}
		return true;
	}

	@Override
	public CErrors getErrors() {
		return mErrors;
	}

	@Override
	public VData getResult() {
		return mResult;
	}
}

package com.sinosoft.lis.xbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title: Web业务系统保全人工核保保单申请功能部分
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
public class PRnewUWManuApplyChkUI implements BusinessService {
private static Logger logger = Logger.getLogger(PRnewUWManuApplyChkUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public PRnewUWManuApplyChkUI() {
	}

	// @Main
	public static void main(String[] args) {
		GlobalInput mGlobalInput = new GlobalInput();
		TransferData mTransferData = new TransferData();

		/** 全局变量 */
		mGlobalInput.Operator = "S001";
		mGlobalInput.ComCode = "86";
		mGlobalInput.ManageCom = "86";
		LCPolSchema tLCPolSchema = new LCPolSchema();
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSchema.setPolNo("86330020030210002179");
		tLCPolSchema.setProposalNo("86330020030210002179");

		tLCPolSet.add(tLCPolSchema);
		VData tVData = new VData();
		tVData.add(tLCPolSet);
		tVData.add(mGlobalInput);

		// 数据传输
		PRnewUWManuApplyChkUI tPRnewUWManuApplyChkUI = new PRnewUWManuApplyChkUI();
		if (tPRnewUWManuApplyChkUI.submitData(tVData, "INSERT") == false) {
		}

	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		PRnewUWManuApplyChkBL tPRnewUWManuApplyChkBL = new PRnewUWManuApplyChkBL();

		logger.debug("---PEdorUWManuApplyChkUI BEGIN---");
		if (tPRnewUWManuApplyChkBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPRnewUWManuApplyChkBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorUWManuApplyChkUI";
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
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return mResult;
	}
}

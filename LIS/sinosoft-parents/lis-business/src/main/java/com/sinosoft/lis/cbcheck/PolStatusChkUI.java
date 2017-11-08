package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统承保个人单状态查询部分
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
public class PolStatusChkUI {
private static Logger logger = Logger.getLogger(PolStatusChkUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public PolStatusChkUI() {
	}

	// @Main
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		LCContSchema p = new LCContSchema();
		p.setProposalContNo("82070000000000");
		p.setContNo("82070000000000");

		LCContSet tLCContSet = new LCContSet();
		tLCContSet.add(p);

		VData tVData = new VData();
		tVData.add(tLCContSet);
		tVData.add(tG);
		PolStatusChkUI ui = new PolStatusChkUI();
		if (ui.submitData(tVData, "") == true) {
			logger.debug("---ok---");
		} else {
			logger.debug("---NO---");
		}

		CErrors tError = ui.mErrors;
		int n = tError.getErrorCount();

	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		PolStatusChkBL tPolStatusChkBL = new PolStatusChkBL();

		logger.debug("---PolStatusChkUI BEGIN---");
		if (tPolStatusChkBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPolStatusChkBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PolStatusChkUI";
			tError.functionName = "submitData";
			// tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tPolStatusChkBL.getResult();
			this.mErrors.copyAllErrors(tPolStatusChkBL.mErrors);
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}
}

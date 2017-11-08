

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.reinsure;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:承保暂交费功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 */
public class CalPolRetentUI {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public CalPolRetentUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		CalPolRetentBL tCalPolRetentBL = new CalPolRetentBL();

		System.out.println("---UI BEGIN---");
		if (tCalPolRetentBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tCalPolRetentBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "CaseUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提取失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		}
		mResult=tCalPolRetentBL.getResult();
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/*
	 * public static void main(String[] args) { CaseUI tCaseUI=new CaseUI();
	 * VData tVData=new VData(); LLCaseSchema tLLCaseSchema=new LLCaseSchema();
	 * LLCaseSet tLLCaseSet=new LLCaseSet(); tLLCaseSchema.setCaseNo("1");
	 * tLLCaseSchema.setRgtNo("sdf"); tLLCaseSet.add(tLLCaseSchema);
	 * tVData.add(tLLCaseSet); tCaseUI.submitData(tVData,"UPDATE"); }
	 */
}

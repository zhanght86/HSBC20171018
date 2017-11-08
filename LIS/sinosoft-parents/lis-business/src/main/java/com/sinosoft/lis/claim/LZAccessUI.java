/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LZAccessSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:提交理赔用户管理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author:lihf
 * @version 1.0
 */

public class LZAccessUI {
private static Logger logger = Logger.getLogger(LZAccessUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;

	/** 数据操作字符串 */

	public LZAccessUI() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将传入的数据和操作符号在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		LZAccessBL tLZAccessBL = new LZAccessBL();
		logger.debug("----------LZAccessUI Begin----------");
		if (tLZAccessBL.submitData(mInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLZAccessBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LZAccessBLUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tLZAccessBL.getResult();
		}
		logger.debug("----------LZAccessUI End----------");

		mInputData = null;
		return true;

	}

	public VData getResult() // 获得结果
	{
		return mResult;
	}

	public static void main(String[] args) {
		CErrors tError = null;
		String FlagStr = "Fail";
		String Content = "";

		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "001";
		// tGI.ManageCom = "86110000";
		tGI.ManageCom = "86";
		tGI.ComCode = "86";
		String transact = "LZAccess||DELETE";
		LZAccessSchema tLZAccessSchema = new LZAccessSchema();
		LZAccessUI tLZAccessUI = new LZAccessUI();
		// 准备后台数据
		tLZAccessSchema.setReceiveCom("123"); // 接受机构
		tLZAccessSchema.setSendOutCom("123"); // 发放机构

		VData tVData = new VData();
		tVData.add(tGI);
		tVData.add(tLZAccessSchema);

		if (!tLZAccessUI.submitData(tVData, transact)) {
			Content = "提交LZAccessUI失败，原因是: "
					+ tLZAccessUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		} else {
			Content = "数据提交成功";
			FlagStr = "Succ";
		}

	}

}

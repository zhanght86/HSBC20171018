package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 续期冲正，回退
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author yyf
 * @version 1.0
 */

public class XQChargeRollBackUI {
private static Logger logger = Logger.getLogger(XQChargeRollBackUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public XQChargeRollBackUI() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"GETMONEY"和"PAYMONEY"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中

		logger.debug("---tXQChargeRollBack BL BEGIN---");
		XQChargeRollBackBL tXQChargeRollBackBL = new XQChargeRollBackBL();

		if (!tXQChargeRollBackBL.submitData(cInputData, cOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tXQChargeRollBackBL.mErrors);
			mResult.clear();
			mResult.add(mErrors.getFirstError());
			return false;
		} else {

		}
		logger.debug("---tXQChargeRollBackBL BL END---");

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}
}

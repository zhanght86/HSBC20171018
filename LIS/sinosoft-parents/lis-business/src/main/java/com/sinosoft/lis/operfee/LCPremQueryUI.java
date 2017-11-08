package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:保单查询功能类
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
public class LCPremQueryUI {
private static Logger logger = Logger.getLogger(LCPremQueryUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public LCPremQueryUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		LCPremQueryBL tLCPremQueryBL = new LCPremQueryBL();

		if (tLCPremQueryBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPremQueryBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCPremQueryUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else
			mInputData = tLCPremQueryBL.getResult();

		return true;
	}

	public VData getResult() {
		return mInputData;
	}

}

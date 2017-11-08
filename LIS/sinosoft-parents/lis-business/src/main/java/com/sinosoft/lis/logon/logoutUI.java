/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.logon;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:登出处理函数
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author dingzhong
 * @version 1.0
 */

public class logoutUI  implements BusinessService {
private static Logger logger = Logger.getLogger(logoutUI.class);
	private VData mInputData;
	public CErrors mErrors = new CErrors();

	public logoutUI() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		// 向BL层传输数据
		// logger.debug("Start logout UI Submit...");
		logoutBL tlogoutBL = new logoutBL();
		tlogoutBL.submitData(mInputData, cOperate);
		// logger.debug("End logout UI Submit...");

		// 如果有需要处理的错误，则返回
		if (tlogoutBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tlogoutBL.mErrors);
		}

		// logger.debug(mErrors.getErrorCount());
		mInputData = null;
		return true;
	}

	public static void main(String[] args) {
	}
	
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return null;
	}
}

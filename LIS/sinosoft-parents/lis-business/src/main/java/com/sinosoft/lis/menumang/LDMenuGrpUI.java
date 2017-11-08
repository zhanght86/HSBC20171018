/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.menumang;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 从错误对象处理类继承，用来保存错误对象,在每个类中都存在
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author DingZhong
 * @version 1.0
 */

public class LDMenuGrpUI implements BusinessService{
private static Logger logger = Logger.getLogger(LDMenuGrpUI.class);

	// 业务处理相关变量
	private VData mInputData;
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	public LDMenuGrpUI() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		LDMenuGrpBL tLDMenuGrpBL = new LDMenuGrpBL();
		// logger.debug("Start LDMenuGrp UI Submit...");
		boolean tag = tLDMenuGrpBL.submitData(mInputData, cOperate);
		if (!tag) {
			return false;
		}
		// logger.debug("End LDMenuGrp UI Submit...");
		// 如果有需要处理的错误，则返回
		if (tLDMenuGrpBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLDMenuGrpBL.mErrors);
			return false;
		}
		// logger.debug("error num=" + mErrors.getErrorCount());
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
		return mResult;
	}
}

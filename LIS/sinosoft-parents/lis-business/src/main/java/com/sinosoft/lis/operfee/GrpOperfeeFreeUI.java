package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:续期解挂类（界面输入）（暂对个人） 从错误对象处理类继承，用来保存错误对象,在每个类中都存在
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WL
 * @version 1.0
 */

public class GrpOperfeeFreeUI {
private static Logger logger = Logger.getLogger(GrpOperfeeFreeUI.class);

	// 业务处理相关变量
	private VData mInputData;
	public CErrors mErrors = new CErrors();

	public GrpOperfeeFreeUI() {
	}

	public static void main(String[] args) {
		GrpOperfeeFreeUI tGrpOperfeeFreeUI = new GrpOperfeeFreeUI();
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		GrpOperfeeFreeBL tGrpOperfeeFreeBL = new GrpOperfeeFreeBL();
		logger.debug("Start GrpOperfeeFree UI Submit...");
		tGrpOperfeeFreeBL.submitData(mInputData, cOperate);

		logger.debug("End GrpOperfeeFree UI Submit...");

		mInputData = null;
		// 如果有需要处理的错误，则返回
		if (tGrpOperfeeFreeBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tGrpOperfeeFreeBL.mErrors);
			logger.debug("error num=" + mErrors.getErrorCount());
			return false;
		}
		return true;
	}

}

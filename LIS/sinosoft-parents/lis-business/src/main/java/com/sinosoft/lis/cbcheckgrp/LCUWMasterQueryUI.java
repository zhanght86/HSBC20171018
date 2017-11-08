package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class LCUWMasterQueryUI {
private static Logger logger = Logger.getLogger(LCUWMasterQueryUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public LCUWMasterQueryUI() {
	}

	public static void main(String[] args) {
		LCPolSchema p = new LCPolSchema();
		p.setPolNo("00010220020110000003");
		VData v = new VData();
		v.add(p);
		LCUWMasterQueryUI PUI = new LCUWMasterQueryUI();
		PUI.submitData(v, "QUERY||MAIN");
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		LCUWMasterQueryBL tLCUWMasterQueryBL = new LCUWMasterQueryBL();
		logger.debug("begin LCUWMasterQueryUI");
		if (tLCUWMasterQueryBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWMasterQueryBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalQueryUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mInputData = tLCUWMasterQueryBL.getResult();
		}
		logger.debug("end  LCUWMasterQueryUI");
		return true;
	}

	public VData getResult() {
		return mInputData;
	}

}

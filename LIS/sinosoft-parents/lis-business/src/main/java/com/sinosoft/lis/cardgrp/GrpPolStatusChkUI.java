package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
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
public class GrpPolStatusChkUI  implements BusinessService{
private static Logger logger = Logger.getLogger(GrpPolStatusChkUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public GrpPolStatusChkUI() {
	}

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		LCGrpContSchema p = new LCGrpContSchema();
		p.setProposalGrpContNo("140110000000159");
		p.setGrpContNo("140110000000159");

		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		tLCGrpContSet.add(p);

		// p = new LCPolSchema();
		// p.setProposalNo( "86220020020110000001" );
		// p.setPolNo("86220020020110000001");
		// tLCPolSet.add(p);

		VData tVData = new VData();
		tVData.add(tLCGrpContSet);
		tVData.add(tG);
		GrpPolStatusChkUI ui = new GrpPolStatusChkUI();
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

		GrpPolStatusChkBL tGrpPolStatusChkBL = new GrpPolStatusChkBL();

		logger.debug("---PolStatusChkUI BEGIN---");
		if (tGrpPolStatusChkBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理

			this.mErrors.copyAllErrors(tGrpPolStatusChkBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PolStatusChkUI";
			tError.functionName = "submitData";
			// tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tGrpPolStatusChkBL.getResult();
			this.mErrors.copyAllErrors(tGrpPolStatusChkBL.mErrors);
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}

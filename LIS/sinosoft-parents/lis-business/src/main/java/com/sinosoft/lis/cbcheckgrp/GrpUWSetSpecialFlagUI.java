package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
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

public class GrpUWSetSpecialFlagUI implements BusinessService{
private static Logger logger = Logger.getLogger(GrpUWSetSpecialFlagUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	public GrpUWSetSpecialFlagUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		GrpUWSetSpecialFlagBL tGrpUWSetSpecialFlagBL = new GrpUWSetSpecialFlagBL();

		logger.debug("---GrpUWSetSpecialFlagUI  BEGIN---");
		if (tGrpUWSetSpecialFlagBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGrpUWSetSpecialFlagBL.mErrors);
			// CError tError = new CError();
			// tError.moduleName = "GrpUWSetSpecialFlagUI ";
			// tError.functionName = "submitData";
			// tError.errorMessage = "数据查询失败!";
			// this.mErrors .addOneError(tError) ;
			mResult.clear();
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		LCGrpPolSchema g = new LCGrpPolSchema();

		g.setPrtNo("20030730009004");
		// g.setGrpPolNo("86110020050120000002");

		LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();

		tLCGrpPolSet.add(g);

		VData tVData = new VData();
		// tVData.add( tLCPolSet );
		tVData.add(g);
		tVData.add(tG);

		GrpUWSetSpecialFlagUI ui = new GrpUWSetSpecialFlagUI();
		if (ui.submitData(tVData, "UPDATE") == true) {
			logger.debug("---ok---");
		} else {
			logger.debug("---NO---");
		}
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}

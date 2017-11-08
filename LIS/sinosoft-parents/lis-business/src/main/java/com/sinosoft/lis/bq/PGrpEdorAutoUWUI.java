package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:团单自动核保业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Tjj
 * @ReWrite ZhangRong
 * @version 1.0
 */
public class PGrpEdorAutoUWUI {
private static Logger logger = Logger.getLogger(PGrpEdorAutoUWUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public PGrpEdorAutoUWUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		PGrpEdorAutoUWBL tPGrpEdorAutoUWBL = new PGrpEdorAutoUWBL();
		logger.debug("---UI BEGIN---" + mOperate);
		if (tPGrpEdorAutoUWBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPGrpEdorAutoUWBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PGrpEdorAutoUWUI";
			tError.functionName = "submitData";
			tError.errorMessage = "提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tPGrpEdorAutoUWBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";

		/*
		 * LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
		 * tLPEdorMainSchema.setEdorAcceptNo("000001");
		 * tLPEdorMainSchema.setEdorNo("000001");
		 * 
		 * VData tInputData = new VData(); tInputData.add(tG);
		 * tInputData.add(tLPEdorMainSchema); EdorAutoUWBL tEdorAutoUWBL = new
		 * EdorAutoUWBL(); if (!tEdorAutoUWBL.submitData(tInputData, "")) {
		 * logger.debug("核保失败!");
		 * logger.debug(tEdorAutoUWBL.mErrors.getErrContent()); }
		 */

		LPGrpEdorMainSchema tLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
		tLPGrpEdorMainSchema.setGrpContNo("240110000000051");
		tLPGrpEdorMainSchema.setEdorNo("430110000000003");

		VData tInputData = new VData();
		tInputData.add(tG);
		tInputData.add(tLPGrpEdorMainSchema);
		PGrpEdorAutoUWBL tPGrpEdorAutoUWBL = new PGrpEdorAutoUWBL();
		if (!tPGrpEdorAutoUWBL.submitData(tInputData, "")) {
			logger.debug("核保失败!");
			logger.debug(tPGrpEdorAutoUWBL.mErrors.getErrContent());
		}

	}
}

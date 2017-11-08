package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:承保暂交费功能类
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
public class PGrpEdorMainUI {
private static Logger logger = Logger.getLogger(PGrpEdorMainUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	public PGrpEdorMainUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		PGrpEdorMainBL tPGrpEdorMainBL = new PGrpEdorMainBL();

		logger.debug("---UI BEGIN---");
		if (tPGrpEdorMainBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPGrpEdorMainBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PGrpEdorMainUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else
			mResult = tPGrpEdorMainBL.getResult();
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		PGrpEdorMainUI tPGrpEdorMainUI = new PGrpEdorMainUI();
		VData tVData = new VData();
		// LPGrpEdorMainBLSet tLPGrpEdorMainBLSet = new LPGrpEdorMainBLSet();
		// LPGrpEdorMainSchema tLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
		// LPGrpEdorMainSet tLPGrpEdorMainSet = new LPGrpEdorMainSet();
		// tLPGrpEdorMainSchema.setGrpPolNo("86110020030220000023");
		// tLPGrpEdorMainSchema.setEdorType("AC");
		// tLPGrpEdorMainSet.add(tLPGrpEdorMainSchema);
		// tVData.addElement(tLPGrpEdorMainSet);
		//
		// LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
		// LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
		// tLCGrpPolSchema.setGrpPolNo("86110020030220000023");
		// tLCGrpPolSet.add(tLCGrpPolSchema);
		// tVData.add(tLCGrpPolSet);
		//
		// GlobalInput tGlobalInput = new GlobalInput();
		// tGlobalInput.Operator = "001";
		// tGlobalInput.ComCode = "86";
		// tGlobalInput.ManageCom = "86";
		// tVData.addElement(tGlobalInput);
		//
		// tPGrpEdorMainUI.submitData(tVData, "INSERT||EDOR");
		// tLPGrpEdorMainBLSet = ( (LPGrpEdorMainBLSet)
		// (tPGrpEdorMainUI.getResult().
		// getObjectByObjectName(
		// "LPGrpEdorMainBLSet", 0)));
		// tLPGrpEdorMainSchema = tLPGrpEdorMainBLSet.get(1);
		// logger.debug(tLPGrpEdorMainSchema.getEdorNo());
	}
}

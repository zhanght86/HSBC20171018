package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统相同客户查询部分
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
public class PersonChkUI {
private static Logger logger = Logger.getLogger(PersonChkUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public PersonChkUI() {
	}

	// @Main
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";

		// 校验重复客户
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema.setContNo("19000001000148");
		tLCContSchema.setInsuredNo("");
		tLCContSchema.setAppFlag("A");
		tLCContSchema.setRemark("3");
		VData tVData = new VData();
		tVData.add(tLCContSchema);

		tVData.add(tG);
		PersonChkUI ui = new PersonChkUI();
		if (ui.submitData(tVData, "") == true) {
			logger.debug("---OK---");
		} else {
			logger.debug("---NO---");
		}

		VData tVdata = new VData();
		tVdata = ui.getResult();

		LDPersonSet tLDPersonSet = new LDPersonSet();
		tLDPersonSet = (LDPersonSet) tVdata.getObjectByObjectName(
				"LDPersonSet", 0);
		logger.debug("ss:" + tLDPersonSet.size());
		int n = ui.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			logger.debug("Error: " + ui.mErrors.getError(i).errorMessage);
		}

	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		PersonChkBL tPersonChkBL = new PersonChkBL();
		logger.debug("size0:" + mResult.size());
		logger.debug("---PersonChkBL UI BEGIN2---");
		if (tPersonChkBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPersonChkBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PersonChkUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		}

		mResult.clear();
		mResult = tPersonChkBL.getResult();
		logger.debug("size:" + mResult.size());
		return true;
	}

	public VData getResult() {
		return mResult;
	}

}

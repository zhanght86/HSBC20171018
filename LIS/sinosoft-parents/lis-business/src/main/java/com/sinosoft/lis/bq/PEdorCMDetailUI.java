package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:被保险人资料变更功能类
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
public class PEdorCMDetailUI {
private static Logger logger = Logger.getLogger(PEdorCMDetailUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public PEdorCMDetailUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		PEdorCMDetailBL tPEdorCMDetailBL = new PEdorCMDetailBL();
		if (tPEdorCMDetailBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPEdorCMDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PInsuredUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tPEdorCMDetailBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		logger.debug("-------test...");
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPPersonSchema tLPPersonSchema = new LPPersonSchema();
		tLPEdorItemSchema.setEdorAcceptNo("6120060208000059");
		tLPEdorItemSchema.setEdorType("CM");

		tLPPersonSchema.setEdorType("CM");
		tLPPersonSchema.setCustomerNo("002494341");
		tLPPersonSchema.setName("许涛");
		tLPPersonSchema.setSex("0");
		tLPPersonSchema.setBirthday("1993-11-08");
		tLPPersonSchema.setIDType("9");
		tLPPersonSchema.setIDNo("DIN000000000461832");

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";

		VData tVData = new VData();
		tVData.addElement(tLPEdorItemSchema);
		tVData.addElement(tLPPersonSchema);
		tVData.addElement(tGlobalInput);
		PEdorCMDetailUI tPEdorCMDetailUI = new PEdorCMDetailUI();
		if (!tPEdorCMDetailUI.submitData(tVData, "INSERT||MAIN")) {
			logger.debug(tPEdorCMDetailUI.mErrors.getErrContent());
		} else {
			logger.debug("CM Submit OK!");
		}
	}
}

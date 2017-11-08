package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:保全申请确认功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Tjj
 * @version 1.0
 */
public class PEdorConfirmUI {
private static Logger logger = Logger.getLogger(PEdorConfirmUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;
	private String mStrLJAGetSet = "";
	private String mResultQueryString = "";
	private LJAGetSet mLJAGetSet = new LJAGetSet();

	public PEdorConfirmUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		PEdorConfirmBL tPEdorConfirmBL = new PEdorConfirmBL();
		logger.debug("---UI BEGIN---" + mOperate);
		if (tPEdorConfirmBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPEdorConfirmBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorConfirmUI";
			tError.functionName = "submitData";
			tError.errorMessage = "提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tPEdorConfirmBL.getResult();
			// mStrLJAGetSet = tPEdorConfirmBL.getLJAGetSet();
			// mResultQueryString = tPEdorConfirmBL.getQueryResult();
		}
		return true;
	}

	public String getLJAGetSet() {
		return mStrLJAGetSet;
	}

	public String getQueryResult() {
		return mResultQueryString;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		logger.debug("-------test...");

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ComCode = "86";
		tGlobalInput.ManageCom = "86";

		String transact = "INSERT||EDORCONFIRM";

		LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
		// tLPEdorMainSchema.setPolNo("86110020050210000006");

		VData tVData = new VData();
		String strTemplatePath = "xerox/printdata/";
		tVData.addElement(strTemplatePath);
		tVData.addElement(tLPEdorMainSchema);
		tVData.addElement(tGlobalInput);

		PEdorConfirmUI tPEdorConfirmUI = new PEdorConfirmUI();
		tPEdorConfirmUI.submitData(tVData, transact);
	}
}

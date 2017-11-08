package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPEdorMainSchema;
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
public class PEdorAppConfirmUI {
private static Logger logger = Logger.getLogger(PEdorAppConfirmUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */

	private String mPayPrintParams = "";

	private String mOperate;

	public PEdorAppConfirmUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		PEdorAppConfirmBLF tPEdorAppConfirmBL = new PEdorAppConfirmBLF();

		if (tPEdorAppConfirmBL.submitData(cInputData, mOperate) == false) {
			logger.debug("错误原因："
					+ tPEdorAppConfirmBL.mErrors.getFirstError());
			this.mErrors.copyAllErrors(tPEdorAppConfirmBL.mErrors);
			return false;
		} else {
			mResult = tPEdorAppConfirmBL.getResult();
			mPayPrintParams = tPEdorAppConfirmBL.getPrtParams();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public String getPrtParams() {
		return mPayPrintParams;
	}

	public static void main(String[] args) {
		logger.debug("-------test...");

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ComCode = "86";
		tGlobalInput.ManageCom = "86";

		String transact = "INSERT||EDORAPPCONFIRM";

		LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
		// tLPEdorMainSchema.setPolNo("86110020030210009985");

		VData tVData = new VData();
		String strTemplatePath = "xerox/printdata/";
		tVData.addElement(strTemplatePath);
		tVData.addElement(tLPEdorMainSchema);
		tVData.addElement(tGlobalInput);

		PEdorAppConfirmUI tPEdorAppConfirmUI = new PEdorAppConfirmUI();
		tPEdorAppConfirmUI.submitData(tVData, transact);
	}
}

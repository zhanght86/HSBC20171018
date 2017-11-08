package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:个单保全申请撤销UI类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Alex
 * @version 1.0
 */

public class PEdorItemCancelUI {
private static Logger logger = Logger.getLogger(PEdorItemCancelUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 撤销申请原因 */
	private String delReason;
	private String reasonCode;

	public PEdorItemCancelUI() {
	}

	/**
	 * 接收页面提交的数据
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// logger.debug(cInputData.size());
		this.delReason = (String) cInputData.getObjectByObjectName("String", 2);
		// this.rea = (String) cInputData.getObjectByObjectName("String",3);
		logger.debug("删除原因是：" + delReason);
		PEdorItemCancelBL tPEdorItemCancelBL = new PEdorItemCancelBL();
		logger.debug("---UI BEGIN---" + mOperate);
		logger.debug("--------------------------");
		if (tPEdorItemCancelBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPEdorItemCancelBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "tPEdorItemCancelUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据撤销失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		}
		return true;
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

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorNo("86110020040430000258");
		tLPEdorItemSchema.setEdorType("BB");
		// tLPGrpEdorMainSchema.setEdorType("AC");

		VData tVData = new VData();
		tVData.addElement(tGlobalInput);
		tVData.addElement(tLPEdorItemSchema);

		// 执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
		PEdorItemCancelUI tPEdorItemCancelUI = new PEdorItemCancelUI();
		tPEdorItemCancelUI.submitData(tVData, "DELETE||EDOR");

	}
}

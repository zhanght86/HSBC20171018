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
 * @version 1.0
 */

public class GEdorMainCancelUI {
private static Logger logger = Logger.getLogger(GEdorMainCancelUI.class);
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

	public GEdorMainCancelUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		this.delReason = (String) cInputData.getObjectByObjectName("String", 2);
		logger.debug("删除原因是：" + delReason);

		GEdorMainCancelBL tGEdorMainCancelBL = new GEdorMainCancelBL();
		logger.debug("---UI BEGIN---" + mOperate);
		logger.debug("--------------------------");
		if (tGEdorMainCancelBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGEdorMainCancelBL.mErrors);
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

		LPGrpEdorMainSchema tLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
		tLPGrpEdorMainSchema.setEdorNo("430110000000184");
		tLPGrpEdorMainSchema.setGrpContNo("240110000000192");
		// tLPGrpEdorMainSchema.setEdorType("AC");

		VData tVData = new VData();
		tVData.addElement(tGlobalInput);
		tVData.addElement(tLPGrpEdorMainSchema);

		// 执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
		GEdorMainCancelUI tGEdorMainCancelUI = new GEdorMainCancelUI();
		tGEdorMainCancelUI.submitData(tVData, "DELETE||EDOR");

	}
}

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:校验功能类
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
public class CheckFieldUI {
private static Logger logger = Logger.getLogger(CheckFieldUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public CheckFieldUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		CheckFieldBL tCheckFieldBL = new CheckFieldBL();
		logger.debug("---UI BEGIN---" + mOperate);
		if (tCheckFieldBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tCheckFieldBL.mErrors);
			// CError tError = new CError();
			// tError.moduleName = "CheckFieldUI";
			// tError.functionName = "submitData";
			// tError.errorMessage = "数据查询失败!";
			// this.mErrors .addOneError(tError) ;
			mResult.clear();
			return false;
		} else
			mResult = tCheckFieldBL.getResult();
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		logger.debug("-------test...");
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "001";
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemSchema.setEdorAcceptNo("86000000000347");
		tLPEdorItemSchema.setPolNo("000000");
		tLPEdorItemSchema.setEdorNo("");
		tLPEdorItemSchema.setEdorType("ZT");
		// tLPEdorMainSchema.setEdorValiDate("2004-04-08");
		tLPEdorItemSchema.setChgPrem("1000.0");
		tLPEdorItemSchema.setChgAmnt("");
		tLPEdorItemSchema.setOperator("001");
		// tLPEdorMainSchema.setEdorAppDate();
		tLPEdorItemSet.add(tLPEdorItemSchema);

		VData tVCheckData = new VData();
		tVCheckData.addElement(tG);
		tVCheckData.addElement(tLPEdorItemSchema);
		tVCheckData.addElement("VERIFY||BEGIN");
		tVCheckData.addElement("PEDORINPUT#EDORTYPE");

		CheckFieldUI tCheckFieldUI = new CheckFieldUI();
		if (tCheckFieldUI.submitData(tVCheckData, "CHECK||FIELD")) {
		}
	}
}

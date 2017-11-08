package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:现金批量导入交费
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author yangyf
 * @version 1.0
 */
public class CashSendBankUI {
private static Logger logger = Logger.getLogger(CashSendBankUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public CashSendBankUI() {
	}

	private String mFileName = "";

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		CashSendBankBL tCashSendBankBL = new CashSendBankBL();
		logger.debug("---UI BEGIN---" + mOperate);
		if (tCashSendBankBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tCashSendBankBL.mErrors);
			mResult.clear();
			mResult.add(mErrors.getFirstError());
			return false;
		} else {
			this.mFileName = tCashSendBankBL.getFileName();
			logger.debug("=====================================");
			logger.debug("=====================================");
			logger.debug("=====================================");
			logger.debug("==========" + mFileName + "=========");
			logger.debug("=====================================");
			logger.debug("=====================================");
			logger.debug("=====================================");

			mResult = tCashSendBankBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public String getFileName() {
		return mFileName;
	}

	public static void main(String[] args) {
		logger.debug("-------test...");
	}
}

package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import java.util.Vector;

import com.sinosoft.lis.db.LOPRTDefineSubDB;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;

/**
 * <p>
 * Title: 打印配置公共类
 * </p>
 * <p>
 * Description: 对于不同地域打印的配置
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: ChinaSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class PubPrtConfigure {
private static Logger logger = Logger.getLogger(PubPrtConfigure.class);
	/** 全局变量 */
	private GlobalInput tGI = new GlobalInput();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 打印要素容器类 */
	private TransferData mTransferData = new TransferData();
	/** 打印管理代码 */
	private String mCalCode;
	private String mValue;

	public PubPrtConfigure() {
	}

	/**
	 * 获取不同地域的打印模版路径
	 * 
	 * @return String
	 */
	public String getPrtPath(TransferData tTransferData, String tCode) {
		mTransferData = tTransferData;
		LOPRTDefineSubDB tLOPRTDefineSubDB = new LOPRTDefineSubDB();
		tLOPRTDefineSubDB.setCode(tCode);
		tLOPRTDefineSubDB.setRelaAttr("TemplatePath");
		if (!tLOPRTDefineSubDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PubPrtConfigure";
			tError.functionName = "CalculatePrt";
			tError.errorMessage = "计算要素传输失败！";
			this.mErrors.addOneError(tError);
			return "";
		}
		mCalCode = tLOPRTDefineSubDB.getSchema().getRelAttrSQL();
		if (!CalculatePrt())
			return null;
		logger.debug("模版路径为：" + mValue);
		return mValue;
	}

	/**
	 * 打印属性计算方法
	 */
	private boolean CalculatePrt() {

		// 计算
		Calculator mCalculator = new Calculator();
		mCalculator.setCalCode(mCalCode);
		/** @todo 得到TransferData中的要素名 */
		Vector tVector = new Vector();
		tVector = mTransferData.getValueNames();
		if (tVector.size() > 0) {
			// 获得要素名称并赋值到Calculator类中
			for (int i = 0; i < tVector.size(); i++) {
				String tFactorName = new String();
				tFactorName = (String) tVector.get(i);
				mCalculator.addBasicFactor(tFactorName, (String) mTransferData
						.getValueByName(tFactorName));
			}

			mValue = mCalculator.calculate();

		} else {
			logger.debug("计算要素传输失败！");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PubPrtConfigure";
			tError.functionName = "CalculatePrt";
			tError.errorMessage = "计算要素传输失败！";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		PubPrtConfigure tPubPrtConfigure = new PubPrtConfigure();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ManageCom", "86320000");
		tPubPrtConfigure.getPrtPath(tTransferData, "32");
	}
}

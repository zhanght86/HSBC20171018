package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LYSendToBankSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 银行数据转换到文件模块
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 1.0
 */

public class WriteToFileBigUI {
private static Logger logger = Logger.getLogger(WriteToFileBigUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public WriteToFileBigUI() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"WRITE"和""
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		logger.debug("---WriteToFileBig BL BEGIN---");
		WriteToFileBigBL tWriteToFileBigBL = new WriteToFileBigBL();

		if (tWriteToFileBigBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tWriteToFileBigBL.mErrors);
			mResult.clear();
			mResult.add(mErrors.getFirstError());
			return false;
		} else {
			mResult = tWriteToFileBigBL.getResult();
		}
		logger.debug("---WriteToFileBig BL END---");

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		WriteToFileBigUI WriteToFileBigUI1 = new WriteToFileBigUI();

		LYSendToBankSchema tLYSendToBankSchema = new LYSendToBankSchema();
		tLYSendToBankSchema.setSerialNo("00000000000000013217");
		// TransferData transferData1 = new TransferData();
		// transferData1.setNameAndValue("fileName", "e:\\SendToBankFile.txt");

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		VData inVData = new VData();
		inVData.add(tLYSendToBankSchema);
		// inVData.add(transferData1);
		inVData.add(tGlobalInput);

		if (!WriteToFileBigUI1.submitData(inVData, "WRITE")) {
			VData rVData = WriteToFileBigUI1.getResult();
			logger.debug("Submit Failed! " + (String) rVData.get(0));
		} else {
			VData rVData = WriteToFileBigUI1.getResult();
			logger.debug("Submit Succed!" + (String) rVData.get(0));
		}

	}
}

/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LLParaDeformitySchema;
import com.sinosoft.lis.vschema.LLParaDeformitySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:提交理赔伤残等级参数信息类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author:赵雁
 * @version 1.0
 */
public class LLParaDeformityUI {
private static Logger logger = Logger.getLogger(LLParaDeformityUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;

	/** 数据操作字符串 */

	public LLParaDeformityUI() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将传入的数据和操作符号在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		LLParaDeformityBL tLLParaDeformityBL = new LLParaDeformityBL();

		logger.debug("----------LLParaDeformityUI Begin----------");
		if (tLLParaDeformityBL.submitData(mInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLParaDeformityBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLParaDeformityBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tLLParaDeformityBL.getResult();
		}
		logger.debug("----------LLParaDeformityUI End----------");

		mInputData = null;
		return true;

	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "001";
		tGI.ManageCom = "86110000";
		tGI.ComCode = "01";

		// String tOperate = "Deformity||UPDATE";
		String tOperate = "Deformity||INSERT";

		LLParaDeformitySet tLLParaDeformitySet = new LLParaDeformitySet();
		LLParaDeformitySchema tLLParaDeformitySchema = new LLParaDeformitySchema();

		// 伤残等级参数信息

		// 准备后台数据
		tLLParaDeformitySchema.setDefoType("C2");
		tLLParaDeformitySchema.setDefoGrade("C2医疗");
		tLLParaDeformitySchema.setDefoCode("00");
		tLLParaDeformitySchema.setDefoName("aa");
		tLLParaDeformitySchema.setDefoRate(50);

		tLLParaDeformitySet.add(tLLParaDeformitySchema);

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("Operate", tOperate);

		VData tVData = new VData();
		tVData.add(tGI);
		tVData.add(tLLParaDeformitySchema);

		tVData.add(tTransferData);

		LLParaDeformityUI tLLParaDeformityUI = new LLParaDeformityUI();
		if (tLLParaDeformityUI.submitData(tVData, tOperate) == false) {

		}

	}

}

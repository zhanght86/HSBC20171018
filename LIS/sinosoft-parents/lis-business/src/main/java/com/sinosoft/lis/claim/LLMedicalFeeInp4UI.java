/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LLCaseReceiptSchema;
import com.sinosoft.lis.schema.LLFeeMainSchema;
import com.sinosoft.lis.vschema.LLCaseReceiptSet;
import com.sinosoft.lis.vschema.LLFeeMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:提交理赔医疗费用信息类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author:续涛
 * @version 1.0
 */
public class LLMedicalFeeInp4UI implements BusinessService{
private static Logger logger = Logger.getLogger(LLMedicalFeeInp4UI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public LLMedicalFeeInp4UI() {
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

		LLMedicalFeeInp4BL tLLMedicalFeeInp4BL = new LLMedicalFeeInp4BL();

		logger.debug("----------LLMedicalFeeInp4UI Begin----------");
		if (tLLMedicalFeeInp4BL.submitData(mInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLMedicalFeeInp4BL.mErrors);
			mResult.clear();
			return false;
		} else {
			mResult = tLLMedicalFeeInp4BL.getResult();
		}
		logger.debug("----------LLMedicalFeeInp4UI End----------");

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

		String tOperate = "CLINIC||UPDATE";

		LLFeeMainSet tLLFeeMainSet = new LLFeeMainSet();
		LLFeeMainSchema tLLFeeMainSchema = new LLFeeMainSchema();

		LLCaseReceiptSet tLLCaseReceiptSet = new LLCaseReceiptSet();
		LLCaseReceiptSchema tLLCaseReceiptSchema = new LLCaseReceiptSchema();

		// 门诊信息

		// 准备后台数据
		tLLFeeMainSchema.setClmNo("90001");
		tLLFeeMainSchema.setCaseNo("90001");
		tLLFeeMainSchema.setCustomerNo("90001");
		tLLFeeMainSchema.setHospitalCode("1006");

		tLLCaseReceiptSchema.setClmNo("9001");
		tLLCaseReceiptSchema.setCaseNo("9001");

		tLLCaseReceiptSchema.setMainFeeNo("0000000002");
		tLLCaseReceiptSchema.setFeeDetailNo("0000000002");

		tLLCaseReceiptSchema.setFeeItemType("C");
		tLLCaseReceiptSchema.setFeeItemCode("C001");
		tLLCaseReceiptSchema.setFeeItemName("西药费");
		tLLCaseReceiptSchema.setFee("91");

		tLLCaseReceiptSchema.setStartDate("2005-05-20");
		tLLCaseReceiptSchema.setEndDate("2005-05-20");

		tLLFeeMainSet.add(tLLFeeMainSchema);
		tLLCaseReceiptSet.add(tLLCaseReceiptSchema);

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("Operate", tOperate);

		VData tVData = new VData();
		tVData.add(tGI);
		tVData.add(tLLFeeMainSchema);
		tVData.add(tLLCaseReceiptSchema);
		tVData.add(tTransferData);

		LLMedicalFeeInp4UI tLLMedicalFeeInp1UI = new LLMedicalFeeInp4UI();
		if (tLLMedicalFeeInp1UI.submitData(tVData, tOperate) == false) {

		}

	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}

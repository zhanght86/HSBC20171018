package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LLClaimDutyFeeSchema;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 医疗费用调整显示信息
 * </p>
 * <p>
 * Description: 医疗费用调整显示信息修改提交类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author quyang
 * @version 1.0
 */

public class LLMedicalFeeAdjUI implements BusinessService{
private static Logger logger = Logger.getLogger(LLMedicalFeeAdjUI.class);

	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	
	private VData mResult = new VData();

	public LLMedicalFeeAdjUI() {
	}

	public static void main(String[] args) {
		/** 全局变量 */
		GlobalInput mGlobalInput = new GlobalInput();
		mGlobalInput.Operator = "001";
		mGlobalInput.ComCode = "86";
		mGlobalInput.ManageCom = "86";

		String operationType = "A";
		LLClaimDutyFeeSchema tLLClaimDutyFeeSchema = new LLClaimDutyFeeSchema();

		if ("A".equalsIgnoreCase(operationType)
				|| "B".equalsIgnoreCase(operationType)) {
			tLLClaimDutyFeeSchema.setAdjSum("88888");// 调整金额
			tLLClaimDutyFeeSchema.setAdjReason("调整原因");// 调整原因
			tLLClaimDutyFeeSchema.setAdjRemark("调整备注");// 调整备注
			tLLClaimDutyFeeSchema.setClmNo("90000004140");// 赔案号
			tLLClaimDutyFeeSchema.setDutyFeeType("A");// 费用类型
			tLLClaimDutyFeeSchema.setDutyFeeCode("CC001");// 费用代码
			tLLClaimDutyFeeSchema.setDutyFeeStaNo("0000000177");// 序号
		}
		// else if("C".equalsIgnoreCase(operationType))
		// {
		//
		// tLLClaimDutyFeeSchema.setRealRate(RealRate);//实际给付比例
		// tLLClaimDutyFeeSchema.setAdjReason(AdjReason1);//调整原因
		// tLLClaimDutyFeeSchema.setAdjRemark(AdjRemark1);//调整备注
		// tLLClaimDutyFeeSchema.setClmNo(ClmNo);//赔案号
		// tLLClaimDutyFeeSchema.setDutyFeeType(DutyFeeType);//费用类型
		// tLLClaimDutyFeeSchema.setDutyFeeCode(DutyFeeCode);//费用代码
		// tLLClaimDutyFeeSchema.setDutyFeeStaNo(DutyFeeStaNo);//序号
		//
		// }
		// else if("F".equalsIgnoreCase(operationType) ||
		// "D".equalsIgnoreCase(operationType) || "E".equalsIgnoreCase(operationType))
		// {
		// tLLClaimDutyFeeSchema.setAdjSum(AdjSum1);//调整金额
		// tLLClaimDutyFeeSchema.setAdjReason(AdjReason2);//调整原因
		// tLLClaimDutyFeeSchema.setAdjRemark(AdjRemark2);//调整备注
		// tLLClaimDutyFeeSchema.setClmNo(ClmNo);//赔案号
		// tLLClaimDutyFeeSchema.setDutyFeeType(DutyFeeType);//费用类型
		// tLLClaimDutyFeeSchema.setDutyFeeCode(DutyFeeCode);//费用代码
		// tLLClaimDutyFeeSchema.setDutyFeeStaNo(DutyFeeStaNo);//序号
		// }

		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add(mGlobalInput);
		tVData.add(tLLClaimDutyFeeSchema);

		// 数据传输
		LLMedicalFeeAdjUI tLLMedicalFeeAdjUI = new LLMedicalFeeAdjUI();
		tLLMedicalFeeAdjUI.submitData(tVData, "");

	}

	// @Method
	/**
	 * 数据提交方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return: boolean
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中

		this.mOperate = cOperate;

		LLMedicalFeeAdjBL tLLMedicalFeeAdjBL = new LLMedicalFeeAdjBL();

		logger.debug("----UI BEGIN---");
		if (tLLMedicalFeeAdjBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLMedicalFeeAdjBL.mErrors);
			return false;
		}
		logger.debug("----UI END---");
		return true;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return mResult;
	}

}

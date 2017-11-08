/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LLAffixSchema;
import com.sinosoft.lis.vschema.LLAffixSet;
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
public class LLMAffixListUI implements BusinessService{
private static Logger logger = Logger.getLogger(LLMAffixListUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;

	/** 数据操作字符串 */

	public LLMAffixListUI() {
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

		LLMAffixListBL tLLMAffixListBL = new LLMAffixListBL();

		logger.debug("----------LLMAffixListUI Begin----------");
		if (tLLMAffixListBL.submitData(mInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLMAffixListBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLMAffixListUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tLLMAffixListBL.getResult();
		}
		logger.debug("----------LLMAffixListUI End----------");

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

		String tOperate = "Rgt||UPDATE";

		LLAffixSet tLLAffixSet = new LLAffixSet();
		// 门诊信息

		String[][] tAffixGridValues = { { "07", "户口注销证明", "是", "是" },
				{ "9902", "其他单证", "否", "否" } };
		// 准备后台数据
		for (int j = 0; j < 2; j++) {
			LLAffixSchema tLLAffixSchema = new LLAffixSchema();
			tLLAffixSchema.setRgtNo("001");// 赔案号
			tLLAffixSchema.setCustomerNo("01");// 客户号
			tLLAffixSchema.setAffixNo("00");// 单证号码
			tLLAffixSchema.setAffixCode(tAffixGridValues[j][0]);// 单证代码
			tLLAffixSchema.setSubFlag(tAffixGridValues[j][2]);// 是否提交
			// tLLAffixSchema.setAffixName(tAffixGridValues[j][1]);//单证名称
			tLLAffixSchema.setReturnFlag(tAffixGridValues[j][3]);// 是否退还原件
			tLLAffixSet.add(tLLAffixSchema);
		}

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("Operate", tOperate);
		tTransferData.setNameAndValue("RgtNo", "001");
		tTransferData.setNameAndValue("CustomerNo", "01");

		VData tVData = new VData();
		tVData.add(tGI);
		tVData.add(tLLAffixSet);
		tVData.add(tTransferData);

		LLMAffixListUI tLLMAffixListUI = new LLMAffixListUI();
		if (tLLMAffixListUI.submitData(tVData, tOperate) == false) {

		}

	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}

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
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:单证回销信息类
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
public class LLIssueReplyUI implements BusinessService{
private static Logger logger = Logger.getLogger(LLIssueReplyUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;

	/** 数据操作字符串 */

	public LLIssueReplyUI() {
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

		LLIssueReplyBL tLLIssueReplyBL = new LLIssueReplyBL();

		logger.debug("----------LLIssueReplyUI Begin----------");
		if (tLLIssueReplyBL.submitData(mInputData, mOperate) == false) {
			// @@错误处理
			CError.buildErr(this, "单证回销失败,"+tLLIssueReplyBL.mErrors.getLastError());
			mResult.clear();
			return false;
		} else {
			mResult = tLLIssueReplyBL.getResult();
		}
		logger.debug("----------LLIssueReplyUI End----------");

		mInputData = null;
		return true;

	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		String Content = "";
		String FlagStr = "";
		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "001";
		tGI.ManageCom = "86110000";
		tGI.ComCode = "01";

		String tOperate = "Rgt||UPDATE";

		LLAffixSet tLLAffixSet = new LLAffixSet();
		// 门诊信息

		String[][] tAffixGridValues = {
				{ "0000000533", "CLM003", "索赔申请书", "1", "0" },
				{ "0000000534", "CLM008", "受益人身份证明原件和户籍证明原件(身故保险金)", "1", "0" },
				{ "0000000228", "CLM005", "赔款给付协议书", "1", "0" } };
		// 准备后台数据ClmNo=90000002260&CustomerNo=0000497310&Proc=Rgt
		for (int j = 0; j < 3; j++) {
			LLAffixSchema tLLAffixSchema = new LLAffixSchema();
			tLLAffixSchema.setRgtNo("90000002260");// 赔案号
			tLLAffixSchema.setCustomerNo("0000497310");// 客户号
			tLLAffixSchema.setAffixNo(tAffixGridValues[j][0]);// 附件号码
			tLLAffixSchema.setAffixCode(tAffixGridValues[j][1]);// 单证代码
			tLLAffixSchema.setSubFlag(tAffixGridValues[j][3]);// 是否提交
			tLLAffixSchema.setReturnFlag(tAffixGridValues[j][3]);// 是否退还原件
			tLLAffixSet.add(tLLAffixSchema);
		}
		VData tVData = new VData();
		tVData.add(tGI);
		tVData.add(tLLAffixSet);

		LLIssueReplyUI tLLIssueReplyUI = new LLIssueReplyUI();
		if (tLLIssueReplyUI.submitData(tVData, tOperate) == false) {
			Content = Content + "单证信息保存错误，原因是: ";
			FlagStr = "FAIL";
		}
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}

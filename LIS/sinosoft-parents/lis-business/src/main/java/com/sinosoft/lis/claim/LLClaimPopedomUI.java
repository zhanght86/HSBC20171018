/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LLClaimPopedomSchema;
import com.sinosoft.lis.vschema.LLClaimPopedomSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:提交理赔权限信息类
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
public class LLClaimPopedomUI {
private static Logger logger = Logger.getLogger(LLClaimPopedomUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;

	/** 数据操作字符串 */

	public LLClaimPopedomUI() {
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

		LLClaimPopedomBL tLLClaimPopedomBL = new LLClaimPopedomBL();

		logger.debug("----------LLClaimPopedomUI Begin----------");
		
		if (tLLClaimPopedomBL.submitData(mInputData, mOperate) == false) {
			// @@错误处理
			CError.buildErr(this, "数据提交失败!");
			mResult.clear();
			return false;
		} 
		else {
			mResult = tLLClaimPopedomBL.getResult();
		}
		logger.debug("----------LLClaimPopedomUI End----------");

		mInputData = null;
		return true;

	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "001";
		// tGI.ManageCom = "86110000";
		tGI.ManageCom = "86";
		tGI.ComCode = "01";

		// String tOperate = "Popedom||UPDATE";
		String tOperate = "Popedom||INSERT";

		LLClaimPopedomSet tLLClaimPopedomSet = new LLClaimPopedomSet();
		LLClaimPopedomSchema tLLClaimPopedomSchema = new LLClaimPopedomSchema();

		// 门诊信息

		// 准备后台数据
		tLLClaimPopedomSchema.setPopedomName("C2医疗");
		tLLClaimPopedomSchema.setClaimType("00");
		tLLClaimPopedomSchema.setBaseMin(0);
		tLLClaimPopedomSchema.setBaseMax(5000);
		// tLLClaimPopedomSchema.setMngCom("86");
		tLLClaimPopedomSchema.setStartDate("2005-06-22");
		tLLClaimPopedomSchema.setEndDate("2005-06-30");

		tLLClaimPopedomSet.add(tLLClaimPopedomSchema);

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("Operate", tOperate);

		VData tVData = new VData();
		tVData.add(tGI);
		tVData.add(tLLClaimPopedomSchema);

		tVData.add(tTransferData);

		LLClaimPopedomUI tLLClaimPopedomUI = new LLClaimPopedomUI();
		if (tLLClaimPopedomUI.submitData(tVData, tOperate) == false) {

		}

	}

}

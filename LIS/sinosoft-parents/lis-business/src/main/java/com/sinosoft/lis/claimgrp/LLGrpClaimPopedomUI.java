/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LLGrpClaimPopedomSchema;
import com.sinosoft.lis.vschema.LLGrpClaimPopedomSet;
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
public class LLGrpClaimPopedomUI {
private static Logger logger = Logger.getLogger(LLGrpClaimPopedomUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;

	/** 数据操作字符串 */

	public LLGrpClaimPopedomUI() {
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

		LLGrpClaimPopedomBL tLLGrpClaimPopedomBL = new LLGrpClaimPopedomBL();

		logger.debug("----------LLGrpClaimPopedomUI Begin----------");
		
		if (tLLGrpClaimPopedomBL.submitData(mInputData, mOperate) == false) {
			// @@错误处理
			CError.buildErr(this, "数据提交失败!");
			mResult.clear();
			return false;
		} 
		else {
			mResult = tLLGrpClaimPopedomBL.getResult();
		}
		logger.debug("----------LLGrpClaimPopedomUI End----------");

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

		LLGrpClaimPopedomSet tLLGrpClaimPopedomSet = new LLGrpClaimPopedomSet();
		LLGrpClaimPopedomSchema tLLGrpClaimPopedomSchema = new LLGrpClaimPopedomSchema();

		// 门诊信息

		// 准备后台数据
		tLLGrpClaimPopedomSchema.setPopedomName("C2医疗");
		tLLGrpClaimPopedomSchema.setClaimType("00");
		tLLGrpClaimPopedomSchema.setBaseMin(0);
		tLLGrpClaimPopedomSchema.setBaseMax(5000);
		// tLLGrpClaimPopedomSchema.setMngCom("86");
		tLLGrpClaimPopedomSchema.setStartDate("2005-06-22");
		tLLGrpClaimPopedomSchema.setEndDate("2005-06-30");

		tLLGrpClaimPopedomSet.add(tLLGrpClaimPopedomSchema);

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("Operate", tOperate);

		VData tVData = new VData();
		tVData.add(tGI);
		tVData.add(tLLGrpClaimPopedomSchema);

		tVData.add(tTransferData);

		LLGrpClaimPopedomUI tLLGrpClaimPopedomUI = new LLGrpClaimPopedomUI();
		if (tLLGrpClaimPopedomUI.submitData(tVData, tOperate) == false) {

		}

	}

}

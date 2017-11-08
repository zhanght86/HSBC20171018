/**
 * Copyright (c) 2006 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LJCertifyTempFeeSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:单证保证金管理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author:wl
 * @version 1.0
 */
public class LJCertifyTempFeeUI {
private static Logger logger = Logger.getLogger(LJCertifyTempFeeUI.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mLimit = "";
	private String mOperate;

	/** 数据操作字符串 */

	public LJCertifyTempFeeUI() {
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

		LJCertifyTempFeeBL tLJCertifyTempFeeBL = new LJCertifyTempFeeBL();
		logger.debug("----------LJCertifyTempFeeUI Begin----------");
		if (tLJCertifyTempFeeBL.submitData(mInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJCertifyTempFeeBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "tLJCertifyTempFeeBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mLimit = tLJCertifyTempFeeBL.getLimit();
			mResult = tLJCertifyTempFeeBL.getResult();
		}
		logger.debug("----------LJCertifyTempFeeUI End----------");

		mInputData = null;
		return true;

	}

	public String getLimit() {
		return mLimit;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		CErrors tError = null;
		String FlagStr = "Fail";
		String Content = "";

		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "001";
		tGI.ManageCom = "86";
		tGI.ComCode = "86";
		String transact = "CertifyTempFee||INSERT";
		LJCertifyTempFeeSchema tLJCertifyTempFeeSchema = new LJCertifyTempFeeSchema();
		LJCertifyTempFeeUI tLJCertifyTempFeeUI = new LJCertifyTempFeeUI();
		// //准备后台数据
		tLJCertifyTempFeeSchema.setPayMoney("222.00"); // 交费金额
		// tLJCertifyTempFeeSchema.setAgentCode("222"); //工号
		tLJCertifyTempFeeSchema.setName("222"); // 业务员姓名
		tLJCertifyTempFeeSchema.setIDType("1"); // 证件类型
		tLJCertifyTempFeeSchema.setIDNo("014"); // 证件号码
		tLJCertifyTempFeeSchema.setPolicyCom("666"); // 管理机构
		tLJCertifyTempFeeSchema.setPayDate("2006-5-15"); // 交费日期
		TransferData mTransferData = new TransferData();
		// tTransferData.setNameAndValue("AgentGroupQ", "222.00"); //交费金额
		// tTransferData.setNameAndValue("AgIDTypeQ", "1"); //证件类型
		// tTransferData.setNameAndValue("AgIDQ", "014");//证件号码
		// mTransferData.setNameAndValue("PolicyComQ", "86210000");//管理机构
		mTransferData.setNameAndValue("ManageComQ", "8621");// 收付机构

		VData tVData = new VData();
		tVData.add(tGI);
		tVData.add(tLJCertifyTempFeeSchema);
		tVData.add(mTransferData);

		if (!tLJCertifyTempFeeUI.submitData(tVData, transact)) {
			Content = "提交LJCertifyTempFeeUI失败，原因是: "
					+ tLJCertifyTempFeeUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		} else {
			Content = "数据提交成功";
			FlagStr = "Succ";
		}

	}

}

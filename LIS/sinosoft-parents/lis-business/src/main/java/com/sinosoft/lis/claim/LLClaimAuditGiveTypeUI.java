/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

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
 * Description:提交理赔审核赔付保项结论信息类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author
 * @version 1.0
 */
public class LLClaimAuditGiveTypeUI implements BusinessService{
private static Logger logger = Logger.getLogger(LLClaimAuditGiveTypeUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	private TransferData mTransferData = new TransferData();
	private String mClmNo = "";

	public LLClaimAuditGiveTypeUI() {
	}

	public static void main(String[] args) {
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
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("ClmNo");

		LLClaimAuditGiveTypeBL tLLClaimAuditGiveTypeBL = new LLClaimAuditGiveTypeBL();

		logger.debug("----------UI BEGIN----------");
		if (tLLClaimAuditGiveTypeBL.submitData(mInputData, mOperate) == false) {
			// @@错误处理
			CError.buildErr(this, "保存修改保项金额失败,"+tLLClaimAuditGiveTypeBL.mErrors.getLastError());
			mResult.clear();
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0 计算步骤六：计算理赔类型赔付
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimCalDutyKindBL tLLClaimCalDutyKindBL = new LLClaimCalDutyKindBL();
		if (tLLClaimCalDutyKindBL.submitData(mInputData, "") == false) {
			int n = tLLClaimCalDutyKindBL.mErrors.getErrorCount();
			String tContent = "";

			for (int i = 0; i < n; i++) {
				tContent = tContent
						+ tLLClaimCalDutyKindBL.mErrors.getError(i).errorMessage;

			}
			this.mErrors.copyAllErrors(tLLClaimCalDutyKindBL.mErrors);
			logger.debug("-----理算步骤六-----计算理赔类型的赔付-----LLClaimCalDutyKindBL测试错误提示信息-----"
							+ tContent);
			return false;
		}

		// //加入判断条件:如果为全部为拒付结论,就不需要重新理算
		// String tSql = "select count(1) from LLClaimPolicy where GiveType='0' "
		// + " and ClmNo='" + mClmNo + "'";
		// ExeSQL tExeSQL = new ExeSQL();
		// String tCount = tExeSQL.getOneValue(tSql);
		// logger.debug("############################"+tCount);
		// if (tCount.equals("0"))
		// {
		// return true;
		// }

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No7.0 计算步骤七：产生结算信息
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LLClaimCalShouldPayBL tLLClaimCalShouldPayBL = new LLClaimCalShouldPayBL();
		if (tLLClaimCalShouldPayBL.submitData(mInputData, "") == false) {
			int n = tLLClaimCalShouldPayBL.mErrors.getErrorCount();
			String tContent = "";

			for (int i = 0; i < n; i++) {
				tContent = tContent
						+ tLLClaimCalShouldPayBL.mErrors.getError(i).errorMessage;

			}
			this.mErrors.copyAllErrors(tLLClaimCalShouldPayBL.mErrors);
			logger.debug("-----理算步骤七-----产生赔付应付数据-----LLClaimCalShouldPayBL测试错误提示信息-----"
							+ tContent);
			return false;
		}
		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}

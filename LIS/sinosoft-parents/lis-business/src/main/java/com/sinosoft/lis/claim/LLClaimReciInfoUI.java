package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author niuzj,2005-10-25 收件人信息录入完毕后,执行"保存"操作
 * @version 1.0
 */

public class LLClaimReciInfoUI implements BusinessService{
private static Logger logger = Logger.getLogger(LLClaimReciInfoUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	private VData mInputData;

	/** 数据操作字符串 */
	private String mOperate;

	public LLClaimReciInfoUI() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		this.mOperate = cOperate;

		LLClaimReciInfoBL tLLClaimReciInfoBL = new LLClaimReciInfoBL();

		logger.debug("--------LLClaimReciInfoUI Start---------");
		tLLClaimReciInfoBL.submitData(cInputData, cOperate);
		logger.debug("--------LLClaimReciInfoUI End---------");

		// 如果有需要处理的错误，则返回
		if (tLLClaimReciInfoBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLLClaimReciInfoBL.mErrors);
		}
		logger.debug("error num=" + mErrors.getErrorCount());
		mInputData = null;

		return true;
	}

	/**
	 * 返回结果方法
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "002";
		tG.ManageCom = "86";
		tG.ComCode = "01";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ClmNo", "90000001122");
		tTransferData.setNameAndValue("ReciCode", "1122");
		tTransferData.setNameAndValue("ReciName", "阿牛");
		tTransferData.setNameAndValue("Relation", "");
		tTransferData.setNameAndValue("ReciAddress", "");
		tTransferData.setNameAndValue("Remark", "");

		VData tVData = new VData();

		tVData.add(tG);
		tVData.add(tTransferData);

		LLClaimReciInfoUI tLLClaimReciInfoUI = new LLClaimReciInfoUI();
		if (tLLClaimReciInfoUI.submitData(tVData, "") == true) {
			logger.debug("---保存收件人信息成功---");
		} else {
			logger.debug("---保存收件人信息失败---");
		}
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}

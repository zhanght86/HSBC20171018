package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 单证打印：补交保费 -- PCT002,RemFeeNoticeC000190.vts
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author xutao,2005-07-14
 * @version 1.0
 */

public class LLPRTPatchFeeUI implements BusinessService{
private static Logger logger = Logger.getLogger(LLPRTPatchFeeUI.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private TransferData mTransferData = new TransferData();
	private String mClmNo = ""; // 赔案号
	private String tWebPath = ""; // 模板路径
	private String mPrtSeq = ""; // 流水号

	public LLPRTPatchFeeUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		LLPRTPatchFeeBL tLLPRTPatchFeeBL = new LLPRTPatchFeeBL();
		if (tLLPRTPatchFeeBL.submitData(cInputData, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLPRTPatchFeeBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimContDealBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mInputData.clear();
			return false;
		} else {
			mResult = tLLPRTPatchFeeBL.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public boolean getInputData(VData cInputData, String cOperate) {
		this.mInputData = cInputData;
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		this.tWebPath = (String) mTransferData.getValueByName("Path");
		this.mPrtSeq = (String) mTransferData.getValueByName("PrtSeq");
		logger.debug("######------模版路径------" + this.tWebPath);
		logger.debug("######------流水号------" + this.mPrtSeq);

		// this.mClmNo = (String) mTransferData.getValueByName("ClmNo");
		// //从 打印管理表（LOPRTManager）中查询出 “印刷流水号---PrtSeq”传入打印类中
		// LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		// LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();
		// LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		// String tPrtSeq="";//打印流水号--印刷流水号《》
		//
		// String tLOPRTManagerSql="select * from loprtmanager where 1=1 "
		// +" and otherno='"+mClmNo+"' "
		// +" and trim(code)='PCT008' "
		// +" and stateflag !='1' ";
		// logger.debug(tLOPRTManagerSql);
		// tLOPRTManagerSet.set(tLOPRTManagerDB.executeQuery(tLOPRTManagerSql));
		// if(tLOPRTManagerSet.size()!=1)
		// {
		// CError tError = new CError();
		// tError.moduleName = "LLPRTPatchFeeBL";
		// tError.functionName = "dealdata";
		// tError.errorMessage = "在打印管理表查询打印流水号参数失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// tLOPRTManagerSchema=tLOPRTManagerSet.get(1);
		// tPrtSeq=tLOPRTManagerSchema.getPrtSeq();
		// mTransferData.setNameAndValue("PrtSeq",tPrtSeq);
		// logger.debug("打印流水号为"+tPrtSeq);
		// logger.debug("######------在打印管理表查询打印流水号成功------########");

		return true;
	}

	public static void main(String[] args) {
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ClmNo", "90000000003");
		tTransferData.setNameAndValue("PrtCode", "PCT008");
		// tTransferData.setNameAndValue("CustNo","0000535120");
		VData tVData = new VData();
		tVData.add(tTransferData);

		LLPRTPatchFeeUI tLLPRTPatchFeeUI = new LLPRTPatchFeeUI();

		tLLPRTPatchFeeUI.submitData(tVData, "");
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}

package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @version 1.0
 */
// 此程序临时测试用
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class ClaimPrintManageBL {
private static Logger logger = Logger.getLogger(ClaimPrintManageBL.class);
	private VData mInputData = new VData();
	private TransferData mTransferData = new TransferData();
	private String mPrtSeq = ""; // 流水号
	private MMap mMap = new MMap();

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	// 业务处理相关变量
	/** 全局数据 */
	private String mOperate = "";
	private VData mResult = new VData();

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	public ClaimPrintManageBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("#########更改打印状态开始########");
		mOperate = cOperate;

		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimPolDealBLF";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		logger.debug("#########更改打印状态结束########");
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();

		tGlobalInput.ComCode = "8611";
		tGlobalInput.ManageCom = "8611";
		tGlobalInput.Operator = "001";

		// LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		// tLOPRTManagerSchema.setPrtSeq("0000001310");
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("PrtSeq", "0000002463");

		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tTransferData);
		ClaimPrintManageBL tClaimPrintManageBL = new ClaimPrintManageBL();

		tClaimPrintManageBL.submitData(tVData, "PRINT");

	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		this.mInputData = cInputData;

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		this.mPrtSeq = (String) mTransferData.getValueByName("PrtSeq");
		logger.debug(mPrtSeq);
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		logger.debug("#################打印后的处理开始#############");
		// 打印后的处理
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);
		tLOPRTManagerDB.getInfo();
		tLOPRTManagerSchema.setSchema(tLOPRTManagerDB.getSchema());
		tLOPRTManagerSchema.setStateFlag("1");
		tLOPRTManagerSchema.setDoneDate(PubFun.getCurrentDate());
		tLOPRTManagerSchema.setDoneTime(PubFun.getCurrentTime());
		mMap.put(tLOPRTManagerSchema, "DELETE&INSERT");
		logger.debug("#################打印后的处理完毕#############");
		return true;
	}

	// 往后台准备数据
	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(mMap);
		return true;
	}

}

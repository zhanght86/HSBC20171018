package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LDCodeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Lizhuo
 * @version 1.0
 */
public class BqNoticeBL {
private static Logger logger = Logger.getLogger(BqNoticeBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	private TransferData tTransferData = null;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	public BqNoticeBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 数据校验
		if (!checkData()) {
			return false;
		}

		// 执行服务
		if (!callService()) {
			return false;
		}

		return true;
	}

	private boolean getInputData() {
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) mInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));
		tTransferData = (TransferData) mInputData.getObjectByObjectName(
				"transferData", 0);

		if (mGlobalInput == null || mLOPRTManagerSchema == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		if (mLOPRTManagerSchema.getPrtSeq() == null) {
			mErrors.addOneError(new CError("没有得到足够的信息:流水号不能为空！"));
			return false;
		}
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			mErrors.addOneError(new CError("在取得打印队列中数据时发生错误"));
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		return true;
	}

	private boolean checkData() {
		return true;
	}

	private boolean callService() {
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		// 查找打印服务
		String strSQL = "SELECT * FROM LDCode WHERE CodeType = 'bqnotice'";
		strSQL += " AND Code = '" + "?Code?" + "'";
		// strSQL += " AND OtherSign = '0'";
		sqlbv.sql(strSQL);
		sqlbv.put("Code", mLOPRTManagerSchema.getCode());
		LDCodeSet tLDCodeSet = new LDCodeDB().executeQuery(sqlbv);

		if (tLDCodeSet.size() == 0) {
			mErrors.addOneError("找不到对应的打印服务类(Code = '"
					+ mLOPRTManagerSchema.getCode() + "')");
			return false;
		}

		// 调用打印服务
		LDCodeSchema tLDCodeSchema = tLDCodeSet.get(1);

		try {
			Class cls = Class.forName(tLDCodeSchema.getCodeAlias());
			PrintService bn = (PrintService) cls.newInstance();

			// 准备数据
			String strOperate = "PRINT";

			VData vData = new VData();

			vData.add(mGlobalInput);
			vData.add(mLOPRTManagerSchema);
			vData.add(tTransferData);

			if (!bn.submitData(vData, strOperate)) {
				mErrors.copyAllErrors(bn.getErrors());
				return false;
			}

			mResult = bn.getResult();

		} catch (Exception ex) {
			ex.printStackTrace();
			mErrors.addOneError("callPrintService" + ex.toString());
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
	/**
	 * 测试主程序业务方法
	 * 
	 * @param String[]
	 */
	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq("86000020080819000077");
		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tLOPRTManagerSchema);
		BqNoticeBL tBfNoticePrintBL = new BqNoticeBL();
		if (!tBfNoticePrintBL.submitData(tVData, "PRINT")) {
			logger.debug("\t@> BfNoticePrintBL 打印失败");
		}
	} // function main end

}

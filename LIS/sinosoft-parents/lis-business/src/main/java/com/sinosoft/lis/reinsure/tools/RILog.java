

package com.sinosoft.lis.reinsure.tools;

import java.io.IOException;
import java.util.Calendar;
import java.io.*;

import com.sinosoft.utility.VData;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.CErrors;
import com.sinosoft.lis.reinsure.calculate.init.RIInitData;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.utility.ExeSQL;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @Zhang Bin
 * @version 1.0
 */

public class RILog {
	private TransferData mTransferData = new TransferData();
	private RIWFLogSchema mRIWFLogSchema = new RIWFLogSchema();
	private RIInitData mRIInitData;
	private String mOperate;
	public CErrors mErrors = new CErrors();

	public RILog() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		this.mOperate = cOperate;
		if (!getInputData(cInputData)) {
			return false;
		}
		if (!init()) {
			return false;
		}
		if (!deal()) {
			return false;
		}
		return true;
	}

	private boolean init() {
		try {
			mRIInitData = RIInitData.getObject(mRIWFLogSchema.getTaskCode());
		} catch (Exception ex) {
			buildError("initInfo", "记录日志文件时运行初始化程序时出错。" + ex.getMessage());
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mRIWFLogSchema = (RIWFLogSchema) cInputData.getObjectByObjectName(
				"RIWFLogSchema", 0);
		mTransferData = ((TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0));
		if (mTransferData == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		return true;
	}

	private boolean deal() {
		RecordLog recordLog = new RecordLog();
		String path = getLogName();
		String log = (String) mTransferData.getValueByName("LOG");
		if (!recordLog.WriteLog(path, log)) {
			buildError("getInputData", recordLog.mErrors.getFirstError());
			return false;
		}

		if (mRIWFLogSchema.getLogPath() == null
				|| mRIWFLogSchema.getLogPath().equals("")) {
			mRIWFLogSchema.setLogPath(path);
			if (!updateRIWFLogSchema()) {
				return false;
			}
		}
		return true;
	}

	private String getLogName() {
		String path = "";
		/****** Start ********/
		path = RILog.class.getClassLoader().getResource("")
				.toString();
		path = path.substring(6, path.lastIndexOf("WEB-INF"));
		/******* End *******/

		String[][] logPathArr = mRIInitData.getRILogPath();
		for (int i = 0; i < logPathArr.length; i++) {
			if (logPathArr[i][0].equals("01")) {
				path = path + logPathArr[i][1];
			}
		}
		// String currentClassPath =
		// (String)mTransferData.getValueByName("PATH");
		// String currentClassPath =
		// Class.class.getResource("").toString().replace('\\','/');
		// System.out.println("***************************** currentClassPath: "
		// + currentClassPath);
		// File file = new File(currentClassPath);
		// String filePath = (file + path).replace('\\','/');
		String filePath = (path).replace('\\', '/');
		System.out
				.println("*****************************filePath: " + filePath);
		if (mOperate.equals("01")) {
			filePath = filePath + "_CAL_" + mRIWFLogSchema.getBatchNo()
					+ ".log";
		}
		return filePath;
	}

	public boolean WriteLog(String fileLogPath, String log) {
		try {
			System.out
					.println(" WriteLog -> ************************** fileLogPath: "
							+ fileLogPath);
			FileWriter fw = new FileWriter(fileLogPath, true); // 写进日志文件中
			fw.write("[" + Calendar.getInstance().getTime() + "]  " + log); // 写进日志文件中
			fw.write("\r\n");
			fw.flush(); // 刷新该流的缓冲
			fw.close(); // 关闭此流，但要先刷新它
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean updateRIWFLogSchema() {
		ExeSQL tExeSQL = new ExeSQL();
		String strSQL = " update RIWFLog a set a.LogPath= '"
				+ mRIWFLogSchema.getLogPath() + "' where a.batchno='"
				+ mRIWFLogSchema.getBatchNo() + "'";
		if (!tExeSQL.execUpdateSQL(strSQL)) {
			buildError("initInfo", "保存日志文件路径时出错。");
			return false;
		}

		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "CalLegalRetentBL";

		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) throws Exception {
		RILog tRILog = new RILog();
		// // tRILog.deal();
		// //
		// tRILog.WriteLog("D:\\hainiu\\ui\\temp\\reinsure\\log\\rilog1.log","aaaaa");
		// RIWFLogSchema tRIWFLogSchema = new RIWFLogSchema();
		// tRIWFLogSchema.setBatchNo("000031");
		// tRIWFLogSchema.setTaskCode("L001000001");
		// TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("LOG", "cccccccccccccccccccccccc");
		// VData tVData = new VData();
		// tVData.add(tTransferData);
		// tVData.add(tRIWFLogSchema);
		// tRILog.submitData(tVData, "01");
		// // tRILog.getLogName();
		tRILog.mRIInitData = RIInitData.getObject("L000000001");
		tRILog.mOperate = "01";
		String logName = tRILog.getLogName();
		System.out.println(logName);
		System.out.println(System.getProperties().toString());
	}
}

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1print;
import java.io.File;

import org.apache.log4j.Logger;

import com.sinosoft.lis.taskservice.MultiTaskServer;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;

/**
 * <p>
 * Title:
 * </p>
 * 动态生成临时vts文件
 * <p>
 * Description:
 * </p>
 * 用来存储打印数据流信息
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SINOSOFT
 * </p>
 * 
 * @author 朱向峰
 * @version 1.0
 */
public class FileQueue {
private static Logger logger = Logger.getLogger(FileQueue.class);
	private static int nHead = 0;
	private static int nTotal = 0;

	static {
		// set nTotal
		// LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String tSql = "select SysVarValue from ldsysvar where Sysvar='FileQueueLength'";
		sqlbv1.sql(tSql);
		// LDSysVarSet tLDSysVarSet = tLDSysVarDB.executeQuery(tSql);
		// LDSysVarSchema tLDSysVarSchema = tLDSysVarSet.get(1);
		// String strLength = tLDSysVarSchema.getV(2);
		String tLength = tExeSQL.getOneValue(sqlbv1);
		nTotal = Integer.parseInt(tLength);

		// check VTS file path
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		tSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
		sqlbv2.sql(tSql);
		// tLDSysVarSet = tLDSysVarDB.executeQuery(strSql2);
		// tLDSysVarSchema = tLDSysVarSet.get(1);
		// String strFilePath = tLDSysVarSchema.getV(2);
		String tFilePath = tExeSQL.getOneValue(sqlbv2);
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		tSql = "select SysVarValue from ldsysvar where Sysvar='VTSRealPath'";
		sqlbv3.sql(tSql);
		// tLDSysVarSet = tLDSysVarDB.executeQuery(strSql3);
		// tLDSysVarSchema = tLDSysVarSet.get(1);
		// String strRealPath = tLDSysVarSchema.getV(2) + strFilePath;
		String tRealPath = tExeSQL.getOneValue(sqlbv3);

		File tPathName = new File(tRealPath);
		if (tPathName.exists()) {
			logger.debug("VTS path exists");
		} else {
			// Doesn't exist
			// 默认下会根据这个来创建vts临时目录，但是如果不这样的话，也就有其他的问题
			tPathName.mkdirs();
			logger.debug("Created " + tPathName);
		}
	}

	public FileQueue() {
	}

	public static synchronized String getFileName() {
		int nTHead = nHead;
		if (nHead >= (nTotal - 1)) {
			nHead = 0;
		} else {
			nHead++;
		}
		return MultiTaskServer.getServerPort()+"_"+nTHead;
	}
}

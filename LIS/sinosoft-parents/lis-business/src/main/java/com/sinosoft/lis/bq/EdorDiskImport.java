/*
 * @(#)EdorDiskImport.java	2006-05-17
 *
 * Copyright 2006 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPParseGuideDataDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGrpImportLogSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGrpImportLogSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPParseGuideDataSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/*
 * <p>Title: Web业务系统</p> <p>ClassName:EdorDiskImport </p> <p>Description:
 * 保全磁盘导入 </p> <p>Copyright: Copyright (c) 2006</p> <p>Company: Sinosoft </p>
 * @author：zhangtao @version：1.0 @CreateDate：2006-05-17
 */
public class EdorDiskImport {
private static Logger logger = Logger.getLogger(EdorDiskImport.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	private String mFilePath = "";
	private String mFileName;
	private String mImportFileName;

	private String mEdorType;
	private String mEdorAcceptNo;
	private String mBatchNo;
	private LPGrpEdorItemSet mLPGrpEdorItemSet;

	/** 错误日志容器* */
	private LCGrpImportLogSet mLCGrpImportLogSet = null;

	public EdorDiskImport() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public EdorDiskImport(String fileName) {
		mFileName = fileName;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();

		if (!getInputData()) {
			return false;
		}
		logger.debug("\t======@保全磁盘导入->EdorDiskImport.java->获得待处理数据=====");
		if (!parseVTS()) // 解析excel文件
		{
			return false;
		}
		logger.debug("\t======@保全磁盘导入->EdorDiskImport.java->完成解析excel文件=====");
		try {
			logger.debug("\t======@保全磁盘导入->EdorDiskImport.java->开始重组数据=====");
			if (!parseTempData()) // 重组临时数据，并调用业务逻辑
			{
				return false;
			}
		} catch (Exception ex) {
			CError.buildErr(this, "临时数据重组异常!");
			return false;
		} finally {
			// 删除临时数据重新上载
			logger.debug("\t======@保全磁盘导入->EdorDiskImport.java->临时数据删除=====");
			String delSQL = " delete from Lpparseguidedata where edortype = '"
					+ "?mEdorType?" + "' and edoracceptno = '" + "?mEdorAcceptNo?"
					+ "' ";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(delSQL);
			sqlbv.put("mEdorType", mEdorType);
			sqlbv.put("mEdorAcceptNo", mEdorAcceptNo);
			MMap delMMap = new MMap();
			delMMap.put(sqlbv, "DELETE");
			batchPubSubmit(delMMap);
		}

		return true;
	}

	/**
	 * 得到传入数据
	 */
	private boolean getInputData() {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		mFilePath = (String) mTransferData.getValueByName("FilePath");
		mFileName = (String) mTransferData.getValueByName("FileName");
		// ===add========liuxiaosong======2006-12-22=======start=========================
		// 不允许同一批次文件重复导入
		String sql = "select count(*) from LCGrpImportLog where StandbyFlag3='"
				+ "?mFileName?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("mFileName", mFileName);
		ExeSQL tExeSQL = new ExeSQL();
		int sqlResult = Integer.parseInt(tExeSQL.getOneValue(sqlbv));
		if (sqlResult > 0) {
			CError tError = new CError();
			tError.moduleName = "EdorDiskImport";
			tError.functionName = "getInputData";
			tError.errorMessage = "该文件已完成导入，请使用新的批次号!";
			mErrors.addOneError(tError);
			return false;
		}
		// ===add========liuxiaosong======2006-12-22=======start=========================

		return true;
	}

	/**
	 * 解析excel，将数据存入临时数据表
	 * 
	 * @return
	 */
	private boolean parseVTS() {
		// 获取导入文件上传相对路径
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("ImportPath");
		if (!tLDSysVarDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorDiskImport";
			tError.functionName = "parseVTS";
			tError.errorMessage = "缺少文件导入路径!";
			mErrors.addOneError(tError);
			return false;
		}
		String sDiskPath = tLDSysVarDB.getSysVarValue();

		// 生成导入文件全路径名称
		mImportFileName = mFilePath + sDiskPath + mFileName;
		// 转换excel
		EdorVTSParser tEdorVTSParser = new EdorVTSParser(mImportFileName,
				mGlobalInput);
		if (!tEdorVTSParser.transform()) {
			mErrors.copyAllErrors(tEdorVTSParser.mErrors);
			return false;
		}

		return true;
	}

	/**
	 * 查询临时数据表，将临时数据重组为各个业务逻辑数据结构
	 * 
	 * @return boolean
	 */
	private boolean parseTempData() {
		// 根据导入文件名提取批改类型、保全受理号、导入文件批次号
		mEdorType = mFileName.substring(0, mFileName.indexOf("_"));
		mEdorAcceptNo = mFileName.substring(mFileName.indexOf("_") + 1,
				mFileName.lastIndexOf("_"));
		mBatchNo = mFileName.substring(mFileName.lastIndexOf("_") + 1,
				mFileName.lastIndexOf("."));
		int iBatchNo = Integer.parseInt(mBatchNo);

		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setEdorAcceptNo(mEdorAcceptNo);
		tLPGrpEdorItemDB.setEdorType(mEdorType);
		mLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		if (tLPGrpEdorItemDB.mErrors.needDealError()) {
			CError tError = new CError();
			tError.moduleName = "EdorDiskImport";
			tError.functionName = "parseTempData";
			tError.errorMessage = "团体保全项目查询失败!";
			mErrors.addOneError(tError);
			return false;
		}
		if (mLPGrpEdorItemSet == null || mLPGrpEdorItemSet.size() < 1) {
			CError tError = new CError();
			tError.moduleName = "EdorDiskImport";
			tError.functionName = "parseTempData";
			tError.errorMessage = "未查到团体保全项目!";
			mErrors.addOneError(tError);

			return false;
		}

		// 查出临时表中单位业务数量
		String sql = " select max(indexno) + 1 from Lpparseguidedata "
				+ " where edortype = '" + "?mEdorType?" + "' and edoracceptno = '"
				+ "?mEdorAcceptNo?" + "' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("mEdorType", mEdorType);
		sqlbv.put("mEdorAcceptNo", mEdorAcceptNo);
		ExeSQL tExeSQL = new ExeSQL();
		String sqlResult = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError() || sqlResult == null
				|| sqlResult.equals("")) {
			CError.buildErr(this, "导入临时数据查询失败!");
			return false;
		}
		int iIndexCount = Integer.parseInt(sqlResult);

		EdorDataParser tEdorDataParser = new EdorDataParser(mEdorType);

		// 读取临时数据
		// advise: LPParseGuideDataSet容量将会很大
		LPParseGuideDataDB tLPParseGuideDataDB = new LPParseGuideDataDB();
		tLPParseGuideDataDB.setEdorType(mEdorType);
		tLPParseGuideDataDB.setEdorAcceptNo(mEdorAcceptNo);
		LPParseGuideDataSet allLPParseGuideDataSet = tLPParseGuideDataDB
				.query();

		if (tLPParseGuideDataDB.mErrors.needDealError()) {
			CError tError = new CError();
			tError.moduleName = "EdorDiskImport";
			tError.functionName = "parseTempData";
			tError.errorMessage = "导入临时数据失败!";
			mErrors.addOneError(tError);

			return false;
		}
		if (allLPParseGuideDataSet == null || allLPParseGuideDataSet.size() < 1) {
			CError tError = new CError();
			tError.moduleName = "EdorDiskImport";
			tError.functionName = "parseTempData";
			tError.errorMessage = "数据缺失!";
			mErrors.addOneError(tError);

			return false;
		}

		LPParseGuideDataSet curLPParseGuideDataSet; // 辅助临时数据

		VData tempResult;

		for (int iIndexNo = 1; iIndexNo < iIndexCount; iIndexNo++) // 逐条业务处理
		{
			// 获得当前一个业务单位需要的临时数据
			curLPParseGuideDataSet = getCurIndexData(allLPParseGuideDataSet,
					iIndexNo);

			// 数据重组
			if (!tEdorDataParser.transform(curLPParseGuideDataSet)) {
				// 记录错误日至继续处理其他数据
				// 系统不允许出错
				try {
					mErrors.clearErrors();
					mErrors.copyAllErrors(tEdorDataParser.mErrors);
					createErrorLog(iIndexNo, mErrors.getFirstError(), "1");
				} catch (Exception ex) {
					logger.debug("创建错误日志出错");
				}

				continue;
			}

			// 取得重组程序组合后的业务数据
			tempResult = tEdorDataParser.getResult();
			// ===add======liuxiaosong=======2006-12-23==========================start=======
			// 容错处理：校验excel中录入的员工姓名是否与系统中的一致
			TransferData tTransferData = new TransferData();
			tTransferData = (TransferData) tempResult.getObjectByObjectName(
					"TransferData", 0);
			String tFilledName = (String) tTransferData
					.getValueByName("FilledName");
			String tOldName = (String) tTransferData.getValueByName("OldName");
			if (!tOldName.equals(tFilledName)) {
				try {
					createErrorLog(iIndexNo, "填写的被保人姓名错误", "1");
				} catch (Exception ex) {
					logger.debug("创建错误日志出错");
				}
				continue;
			}
			// ===add======liuxiaosong=======2006-12-23==========================end=======

			if (!dealBL(tempResult)) // 调用业务逻辑
			{
				// 校验错误日志是否存在
				// 创建错误日志
				try {
					createErrorLog(iIndexNo, mErrors.getFirstError(), "1");
				} catch (Exception ex) {
					logger.debug("创建错误日志出错");
				}
				continue;
			} else {
				// 创建成功日志
				try {
					createErrorLog(iIndexNo, "导入成功", "1");
				} catch (Exception ex) {
					logger.debug("创建日志出错");
				}
			}
			mErrors.clearErrors();
		}
		// 错误处理
		MMap tMMap = new MMap();
		if (mLCGrpImportLogSet == null || mLCGrpImportLogSet.size() < 1) {
			logger.debug("==============保全磁盘导入成功，无错误处理================");
		} else {
			tMMap.put(mLCGrpImportLogSet, "INSERT");
		}
		batchPubSubmit(tMMap); // 提交错误日志

		return true;
	}

	/**
	 * 调用业务逻辑
	 * 
	 * @return boolean
	 */
	private boolean dealBL(VData pInputData) {
		// 插入LPEdorItem记录并提交（保证复用正常调用）
		LCContSchema tLCContSchema = (LCContSchema) // 所有需要导入的保全项目导入数据都含有LCCont
		pInputData.getObjectByObjectName("LCContSchema", 0);
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(tLCContSchema.getContNo());
		LCContSet tLCContSet = tLCContDB.query();
		if (tLCContDB.mErrors.needDealError()) {
			CError tError = new CError();
			tError.moduleName = "EdorDiskImport";
			tError.functionName = "dealBL";
			tError.errorMessage = "查询保单号为" + tLCContSchema.getContNo()
					+ "的相关业务数据时出错!";
			mErrors.addOneError(tError);
			return false;
		}
		if (tLCContSet == null || tLCContSet.size() < 1) {
			CError tError = new CError();
			tError.moduleName = "EdorDiskImport";
			tError.functionName = "dealBL";
			tError.errorMessage = "保单号 " + tLCContSchema.getContNo()
					+ "在系统中不存在!";
			mErrors.addOneError(tError);
			return false;
		}
		tLCContSchema = tLCContSet.get(1);

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorAcceptNo(mEdorAcceptNo);
		tLPEdorItemSchema.setEdorNo(mLPGrpEdorItemSet.get(1).getEdorNo());
		tLPEdorItemSchema.setEdorAppNo(mLPGrpEdorItemSet.get(1).getEdorNo());
		tLPEdorItemSchema.setEdorType(mEdorType);
		tLPEdorItemSchema.setGrpContNo(mLPGrpEdorItemSet.get(1).getGrpContNo());
		tLPEdorItemSchema.setContNo(tLCContSchema.getContNo());
		tLPEdorItemSchema.setInsuredNo(tLCContSchema.getInsuredNo());
		tLPEdorItemSchema.setEdorAppDate(PubFun.getCurrentDate());
		// tLPEdorItemSchema.setPolNo("210210000001114"); //GM，需要另外处理
		// ======add=======liuxiaosong========2006-12-25==============start==============
		if ("5".equals(mLPGrpEdorItemSet.get(1).getDisplayType())
				|| "6".equals(mLPGrpEdorItemSet.get(1).getDisplayType())) {
			TransferData tTransferData = new TransferData();
			tTransferData = (TransferData) pInputData.getObjectByObjectName(
					"TransferData", 0);
			String tPolNo = (String) tTransferData.getValueByName("PolNo");
			tLPEdorItemSchema.setPolNo(tPolNo);
		} else {
			tLPEdorItemSchema.setPolNo("000000");
		}
		// ======add=======liuxiaosong========2006-12-25==============start==============

		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemSet.add(tLPEdorItemSchema);
		VData tVData = new VData();
		tVData.add(tLPEdorItemSet);
		tVData.add(mTransferData);
		tVData.add(mGlobalInput);

		// 申请保全项目
		GEdorAppItemBL tGEdorAppItemBL = new GEdorAppItemBL();
		if (!tGEdorAppItemBL.submitData(tVData, "PEDORITEM")) {
			// 捕获服务类错误信息
			mErrors.clearErrors();
			mErrors.copyAllErrors(tGEdorAppItemBL.mErrors);
			return false;
		}

		// 加入批改信息和环境信息
		pInputData.add(mGlobalInput);
		pInputData.add(mLPGrpEdorItemSet.get(1));
		pInputData.add(tLPEdorItemSet.get(1));

		// ======add=======liuxiaosong======某些特殊项目中需要set=====2006-12-20=========start=====
		pInputData.add(tLPEdorItemSet);
		// ======add=======liuxiaosong======某些特殊项目中需要set=====2006-12-20=========start=====

		// 调用相应的保全项目处理逻辑进行处理
		GEdorDetailBL tGEdorDetailBL = new GEdorDetailBL();
		if (!tGEdorDetailBL.submitData(pInputData, "G&Detail")) {
			mErrors.clearErrors();
			mErrors.copyAllErrors(tGEdorDetailBL.mErrors);
			// Q：直接删除LPEdorItem记录？ 还是调用撤销程序撤销LPEdorItem？ zhangtao 2006-05-27
			// A：创建错误日至并删除LPEdorItem记录以便重新导入 zhangtao 2006-05-27
			MMap delMMap = new MMap();
			delMMap.put(tLPEdorItemSchema, "DELETE");
			batchPubSubmit(delMMap);
			return false;
		}

		return true;
	}

	/**
	 * 取出当前业务序号的数据
	 * 
	 * @param allLPParseGuideDataSet
	 * @param iIndexNo
	 * @return LPParseGuideDataSet
	 */
	private LPParseGuideDataSet getCurIndexData(
			LPParseGuideDataSet allLPParseGuideDataSet, int iIndexNo) {
		LPParseGuideDataSet rLPParseGuideDataSet = new LPParseGuideDataSet();
		for (int i = 1; i <= allLPParseGuideDataSet.size(); i++) {
			if (allLPParseGuideDataSet.get(i).getIndexNo() == iIndexNo) {
				rLPParseGuideDataSet.add(allLPParseGuideDataSet.get(i));
			}
		}
		return rLPParseGuideDataSet;
	}

	/**
	 * 创建一条错误日志记录
	 * 
	 * @param indexNo
	 *            String 业务号
	 * @param errorInfo
	 *            String 错误详细信息
	 * @return boolean
	 */
	private boolean createErrorLog(int indexNo, String errorInfo,
			String sErrorState) {
		LCGrpImportLogSchema tLCGrpImportLogSchema = new LCGrpImportLogSchema();
		if (mLCGrpImportLogSet == null) {
			mLCGrpImportLogSet = new LCGrpImportLogSet();
		}
		tLCGrpImportLogSchema.setGrpContNo(mLPGrpEdorItemSet.get(1)
				.getGrpContNo()); // 团单号
		tLCGrpImportLogSchema.setOtherNo(mEdorAcceptNo); // 保全受理号
		tLCGrpImportLogSchema.setOtherNoType("10"); // 保全错误
		tLCGrpImportLogSchema.setBatchNo(mEdorAcceptNo + "_" + mBatchNo); // 批次号：保全受理号+批次编码
		// tLCGrpImportLogSchema.setBatchNo(mFileName); //批次号(暂时以导入文件名作为批次号)
		tLCGrpImportLogSchema.setID("" + indexNo); // index号
		tLCGrpImportLogSchema.setErrorType("1");
		tLCGrpImportLogSchema.setErrorState(sErrorState);
		tLCGrpImportLogSchema.setErrorInfo(errorInfo); // 错误信息
		tLCGrpImportLogSchema.setStandbyFlag1(mEdorType); // 批改类型
		tLCGrpImportLogSchema.setStandbyFlag2("");
		tLCGrpImportLogSchema.setStandbyFlag3(mFileName); // 导入文件名
		tLCGrpImportLogSchema.setOperator(mGlobalInput.Operator);
		tLCGrpImportLogSchema.setMakeDate(PubFun.getCurrentDate());
		tLCGrpImportLogSchema.setMakeTime(PubFun.getCurrentTime());
		mLCGrpImportLogSet.add(tLCGrpImportLogSchema);
		return true;
	}

	/**
	 * 提交保存临时数据和错误日志
	 * 
	 * @param map
	 * @return boolean
	 */
	private boolean batchPubSubmit(MMap map) {
		PubSubmit pubSubmit = new PubSubmit();
		VData sData = new VData();
		sData.add(map);
		if (!pubSubmit.submitData(sData, "")) {
			CError.buildErr(this, "数据提交失败!");
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		EdorDiskImport tPGI = new EdorDiskImport();

		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("FileName", "AG_6120061226000005_01.xls");
		tTransferData.setNameAndValue("FilePath", "D:/shuidrinking/");
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";

		tVData.add(tTransferData);
		tVData.add(tG);

		String strStartTime = PubFun.getCurrentTime();

		tPGI.submitData(tVData, ""); // 提交

		String strEndTime = PubFun.getCurrentTime();

		logger.debug("===============================================");
		logger.debug("      the start time is : " + strStartTime);
		logger.debug("===============================================");

		logger.debug("===============================================");
		logger.debug("       the end time is : " + strEndTime);
		logger.debug("===============================================");

	}

	private void jbInit() throws Exception {
	}

}

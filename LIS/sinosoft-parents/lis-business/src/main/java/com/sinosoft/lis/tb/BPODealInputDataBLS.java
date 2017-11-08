package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import com.sinosoft.lis.db.BPOMissionDetailStateDB;
import com.sinosoft.lis.db.BPOMissionStateDB;
import com.sinosoft.lis.db.ES_DOC_MAINDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.BPOMissionDetailStateSchema;
import com.sinosoft.lis.schema.BPOMissionStateSchema;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.vdb.BPOMissionDetailErrorDBSet;
import com.sinosoft.lis.vdb.LCIssuePolDBSet;
import com.sinosoft.lis.vschema.BPOMissionDetailErrorSet;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CMySQLBlob;
import com.sinosoft.utility.COracleBlob;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.tb.TbWorkFlowUI;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 录入外包数据公共提交功能
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author Fuqx
 * @version 1.0
 */

public class BPODealInputDataBLS {
private static Logger logger = Logger.getLogger(BPODealInputDataBLS.class);
	public CErrors mErrors = new CErrors();
	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();
	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();

	public BPODealInputDataBLS() {
	}

	/**
	 * 
	 * @param tResult
	 *            数据
	 * @param tOpetate
	 *            操作类型
	 * @return
	 */
	public boolean submitData(VData tResult, String tOpetate) {
		BPOMissionStateSchema tBPOMissionStateSchema = (BPOMissionStateSchema) tResult
				.getObjectByObjectName("BPOMissionStateSchema", 0);
		BPOMissionDetailStateSchema tBPOMissionDetailStateSchema = (BPOMissionDetailStateSchema) tResult
				.getObjectByObjectName("BPOMissionDetailStateSchema", 0);
		GlobalInput tGI = (GlobalInput) tResult.getObjectByObjectName(
				"GlobalInput", 0);
		Connection conn = DBConnPool.getConnection();
		try {
			if (conn == null) {
				// @@错误处理
				CError.buildErr(this, "数据库连接失败");
				return false;
			}

			conn.setAutoCommit(false);

			logger.debug("Start BPOMissionState...");
			BPOMissionStateDB tBPOMissionStateDB = new BPOMissionStateDB(conn);
			tBPOMissionStateDB.setSchema(tBPOMissionStateSchema);

			// 先删除，后插入
			if (tBPOMissionStateDB.getInfo()) {
				if (tBPOMissionStateDB.delete() == false) {
					// @@错误处理
					CError.buildErr(this, "任务处理状态主表删除失败!"
							+ tBPOMissionStateSchema.getBPOID() + " ***  "
							+ tBPOMissionStateSchema.getBussNo());
					conn.rollback();
					conn.close();
					return false;
				}
			}
			tBPOMissionStateDB.setSchema(tBPOMissionStateSchema);
			if (tBPOMissionStateDB.insert() == false) {
				// @@错误处理
				CError.buildErr(this, "任务处理状态主表插入失败!"
						+ tBPOMissionStateSchema.getBPOID() + " ***  "
						+ tBPOMissionStateSchema.getBussNo());
				conn.rollback();
				conn.close();
				return false;
			}

			logger.debug("Start BPOMissionDetailState...");
			BPOMissionDetailStateDB tBPOMissionDetailStateDB = new BPOMissionDetailStateDB(
					conn);
			tBPOMissionDetailStateDB.setSchema(tBPOMissionDetailStateSchema);

			if (tBPOMissionDetailStateDB.insert() == false) {
				// @@错误处理
				CError.buildErr(this, "任务处理状态子表插入失败!"
						+ tBPOMissionDetailStateSchema.getBPOID() + " ***  "
						+ tBPOMissionDetailStateSchema.getBussNo());
				conn.rollback();
				conn.close();
				return false;
			}
			
			logger.debug("Start lcissuepol delete...");
			LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB(conn);
			String sql = "select * from lcissuepol where contno='"+"?contno?"+"'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("contno", tBPOMissionStateSchema.getBussNo());
			LCIssuePolSet tLCIssuePolSet = tLCIssuePolDB.executeQuery(sqlbv);			
			if(tLCIssuePolSet==null || tLCIssuePolSet.size()<1)
			{
				logger.debug("************没有录入问题件，不用删除*************");
			}
			else
			{
				LCIssuePolDBSet tLCIssuePolDBSet = new LCIssuePolDBSet();
				tLCIssuePolDBSet.set(tLCIssuePolSet);
				if (tLCIssuePolDBSet.delete() == false) {
					// @@错误处理
					CError.buildErr(this, "问题件删除失败!");
					conn.rollback();
					conn.close();
					return false;
				}
			}

			// 如果已经成功导入系统，更新扫描状态
			if (tBPOMissionStateSchema != null
					&& "1".equals(tBPOMissionStateSchema.getState())) {
				ES_DOC_MAINSchema tES_DOC_MAINSchema = new ES_DOC_MAINSchema();
				ES_DOC_MAINSet tES_DOC_MAINSet = new ES_DOC_MAINSet();
				ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB(conn);
				tES_DOC_MAINDB.setDocCode(tBPOMissionStateSchema.getBussNo());
				tES_DOC_MAINSet = tES_DOC_MAINDB.query();
				if (tES_DOC_MAINSet == null || tES_DOC_MAINSet.size() == 0) {
					conn.rollback();
					conn.close();
					CError.buildErr(this, "扫描信息查询失败！");
					return false;
				}
				tES_DOC_MAINSchema = tES_DOC_MAINSet.get(1).getSchema();
				tES_DOC_MAINSchema.setInputState("1");
				tES_DOC_MAINSchema.setOperator(tBPOMissionStateSchema
						.getOperator());
				tES_DOC_MAINSchema.setInputStartDate(PubFun.getCurrentDate());
				tES_DOC_MAINSchema.setInputStartTime(PubFun.getCurrentTime());
				tES_DOC_MAINDB = new ES_DOC_MAINDB(conn);

				tES_DOC_MAINDB.setSchema(tES_DOC_MAINSchema);
				if (!tES_DOC_MAINDB.update()) {
					conn.rollback();
					conn.close();
					CError.buildErr(this, "扫描件录入状态更新失败！");
					return false;
				}
			}

			conn.commit();
			conn.close();

		} catch (Exception ex) {
			try {
				conn.rollback();
				conn.close();
			} catch (Exception ex1) {
			}
			ex.printStackTrace();
			CError.buildErr(this, "修改数据失败！" + ex.toString());
			return false;

		}

		return true;
	}

	public boolean submitData(VData tResult, Element tOnePolData) {
		String tDealType = ""; // 处理类型
		String tBussNoType = ""; // 业务类型
		BPOMissionStateSchema tBPOMissionStateSchema = (BPOMissionStateSchema) tResult
				.getObjectByObjectName("BPOMissionStateSchema", 0);
		BPOMissionDetailErrorSet tBPOMissionDetailErrorSet = (BPOMissionDetailErrorSet) tResult
				.getObjectByObjectName("BPOMissionDetailErrorSet", 0);
		BPOMissionDetailStateSchema tBPOMissionDetailStateSchema = (BPOMissionDetailStateSchema) tResult
				.getObjectByObjectName("BPOMissionDetailStateSchema", 0);
		GlobalInput tGI = (GlobalInput) tResult.getObjectByObjectName(
				"GlobalInput", 0);
		Document doc = new Document(tOnePolData);
		COracleBlob tCOracleBlob = new COracleBlob();
		CMySQLBlob tCMySQLBlob = new CMySQLBlob();
		
		Connection conn = DBConnPool.getConnection();
		try {
			if (conn == null) {
				// @@错误处理
				CError.buildErr(this, "数据库连接失败");
				return false;

			}

			conn.setAutoCommit(false);

			// 00－清洁件，01－抽检件，02－外包方返回可处理异常件，03－外包方无法处理的异常件（如整个扫描件无法识别），
			// 04－清洁件导入系统出错所致的异常件，05－重复导入
			tDealType = tBPOMissionStateSchema.getDealType();
			tBussNoType = tBPOMissionStateSchema.getBussNoType();
			logger.debug("tDealType: " + tDealType);
			if (tDealType == null
					|| "".equals(tDealType)
					|| (!"00".equals(tDealType) && !"01".equals(tDealType)
							&& !"02".equals(tDealType)
							&& !"03".equals(tDealType)
							&& !"04".equals(tDealType) && !"05"
							.equals(tDealType))) {
				// @@错误处理
				CError.buildErr(this, "处理类型错误!"
						+ tBPOMissionStateSchema.getBPOID() + " ***  "
						+ tBPOMissionStateSchema.getBussNo());
				conn.rollback();
				conn.close();
				return false;
			}

			logger.debug("Start BPOMissionState...");
			BPOMissionStateDB tBPOMissionStateDB = new BPOMissionStateDB(conn);
			tBPOMissionStateDB.setSchema(tBPOMissionStateSchema);

			// 先删除，后插入
			if (tBPOMissionStateDB.getInfo()) {
				if (tBPOMissionStateDB.delete() == false) {
					// @@错误处理
					CError.buildErr(this, "任务处理状态主表删除失败!"
							+ tBPOMissionStateSchema.getBPOID() + " ***  "
							+ tBPOMissionStateSchema.getBussNo());
					conn.rollback();
					conn.close();
					return false;
				}
			}
			tBPOMissionStateDB.setSchema(tBPOMissionStateSchema);
			if (tBPOMissionStateDB.insert() == false) {
				// @@错误处理
				CError.buildErr(this, "任务处理状态主表插入失败!"
						+ tBPOMissionStateSchema.getBPOID() + " ***  "
						+ tBPOMissionStateSchema.getBussNo());
				conn.rollback();
				conn.close();
				return false;
			}

			logger.debug("Start BPOMissionDetailState...");
			BPOMissionDetailStateDB tBPOMissionDetailStateDB = new BPOMissionDetailStateDB(
					conn);
			tBPOMissionDetailStateDB.setSchema(tBPOMissionDetailStateSchema);

			if (tBPOMissionDetailStateDB.insert() == false) {
				// @@错误处理
				CError.buildErr(this, "任务处理状态子表插入失败!"
						+ tBPOMissionDetailStateSchema.getBPOID() + " ***  "
						+ tBPOMissionDetailStateSchema.getBussNo());
				conn.rollback();
				conn.close();
				return false;
			}

			// 如果是异常件，插入任务处理错误信息表
			if ("02".equals(tDealType) || "03".equals(tDealType)
					|| "04".equals(tDealType)) {
				logger.debug("Start BPOMissionDetailError...");
				BPOMissionDetailErrorDBSet tBPOMissionDetailErrorDBSet = new BPOMissionDetailErrorDBSet(
						conn);
				tBPOMissionDetailErrorDBSet.set(tBPOMissionDetailErrorSet);
				if (tBPOMissionDetailErrorDBSet.insert() == false) {
					// @@错误处理
					CError.buildErr(this, "任务处理错误信息表插入失败!"
							+ tBPOMissionDetailStateSchema.getBPOID()
							+ " ***  "
							+ tBPOMissionDetailStateSchema.getBussNo());
					conn.rollback();
					conn.close();
					return false;
				}
			}

			// 如果是异常件和抽检件，插入外包投保数据表
			if (("01".equals(tDealType) || "02".equals(tDealType)
					|| "03".equals(tDealType) || "04".equals(tDealType))
					&& ("TB".equals(tBussNoType))) {
				String szSQL = "INSERT INTO BPOPolData(BussNo, BussNoType, PolData,SerialNo, Operator, MakeDate,MakeTime) VALUES('"
						+ tBPOMissionStateSchema.getBussNo()
						+ "', '"
						+ tBPOMissionStateSchema.getBussNoType()
						+ "', "
						+ " empty_blob(),'"
						+ tBPOMissionStateSchema.getSerialNo()
						+ "', '"
						+ tBPOMissionStateSchema.getOperator()
						+ "', '"
						+ theCurrentDate + "','" + theCurrentTime + "')";
//				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
//				sqlbv.sql(szSQL);
//				sqlbv.put("contno", tBPOMissionStateSchema.getBussNo());
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
					if (!tCOracleBlob.InsertBlankBlobRecord(szSQL, conn)) {
						conn.rollback();
						conn.close();
						// @@错误处理
						CError.buildErr(this, "外包投保数据表插入失败！" + szSQL);
						return false;
					}
				}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					if (!tCMySQLBlob .InsertBlankBlobRecord(szSQL, conn)) {
						conn.rollback();
						conn.close();
						// @@错误处理
						CError.buildErr(this, "外包投保数据表插入失败！" + szSQL);
						return false;
					}		
				}
				szSQL = " and BussNo = '" + tBPOMissionStateSchema.getBussNo()
						+ "' and BussNoType = 'TB'";

				XMLOutputter outputter = new XMLOutputter("  ", true, "GB2312");

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				outputter.output(doc, baos);
				baos.close();
				InputStream ins = new ByteArrayInputStream(baos.toByteArray());
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
					if (!tCOracleBlob.UpdateBlob(doc, "BPOPolData", "PolData",
							szSQL, conn)) {
						conn.rollback();
						conn.close();
						CError.buildErr(this, "外包投保数据表修改数据失败！" + szSQL);
						return false;
					}
				}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					if (!tCMySQLBlob.UpdateBlob(doc, "BPOPolData", "PolData",
							szSQL, conn)) {
						conn.rollback();
						conn.close();
						CError.buildErr(this, "外包投保数据表修改数据失败！" + szSQL);
						return false;
					}		
				}
			}

			// 如果已经成功导入系统，更新扫描状态
			if (tBPOMissionStateSchema != null
					&& "1".equals(tBPOMissionStateSchema.getState())){
//					&& ("TB".equals(tBussNoType))) {
				ES_DOC_MAINSchema tES_DOC_MAINSchema = new ES_DOC_MAINSchema();
				ES_DOC_MAINSet tES_DOC_MAINSet = new ES_DOC_MAINSet();
				ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB(conn);
				tES_DOC_MAINDB.setDocCode(tBPOMissionStateSchema.getBussNo());
				tES_DOC_MAINSet = tES_DOC_MAINDB.query();
				if (tES_DOC_MAINSet == null || tES_DOC_MAINSet.size() == 0) {
					conn.rollback();
					conn.close();
					CError.buildErr(this, "扫描信息查询失败！");
					return false;
				}
				tES_DOC_MAINSchema = tES_DOC_MAINSet.get(1).getSchema();
				tES_DOC_MAINSchema.setInputState("1");
				tES_DOC_MAINSchema.setOperator(tBPOMissionStateSchema
						.getOperator());
				tES_DOC_MAINSchema.setInputStartDate(PubFun.getCurrentDate());
				tES_DOC_MAINSchema.setInputStartTime(PubFun.getCurrentTime());
				tES_DOC_MAINDB = new ES_DOC_MAINDB(conn);

				tES_DOC_MAINDB.setSchema(tES_DOC_MAINSchema);
				if (!tES_DOC_MAINDB.update()) {
					conn.rollback();
					conn.close();
					CError.buildErr(this, "扫描件录入状态更新失败！");
					return false;
				}
			}
			
			logger.debug("...Start dealWorkFlow...");
			if(!dealWorkFlow(tBPOMissionStateSchema, tGI, conn))
			{
				CError.buildErr(this, "工作流处理失败！");
				return false;
			}
			logger.debug("...End dealWorkFlow...");

			conn.commit();
			conn.close();

		} catch (Exception ex) {
			try {
				conn.rollback();
				conn.close();
			} catch (Exception ex1) {
			}
			ex.printStackTrace();
			CError.buildErr(this, "修改数据失败！" + ex.toString());
			return false;

		}	
        
		return true;
	}

	public boolean dealWorkFlow(BPOMissionStateSchema tBPOMissionStateSchema,
			GlobalInput tGI, Connection conn) {
		try {
			String wFlag = "";
			String wDealType = tBPOMissionStateSchema.getDealType();
			TransferData mTransferData = new TransferData();
			VData tVData = new VData();
//			if (StrTool.cTrim(tBPOMissionStateSchema.getBussNoType()).equals(
//					"TB")) {
				// 当是正常件（此次正常导入）或者重复导入、或者未录入完成的异常件和抽检件时，其工作流起始节点为外包录入节点0000001089
				if (((StrTool.cTrim(tBPOMissionStateSchema.getDealType())
						.equals("00") || StrTool.cTrim(
						tBPOMissionStateSchema.getDealType()).equals("05")) && StrTool
						.cTrim(tBPOMissionStateSchema.getState()).equals("1"))
						|| (!StrTool
								.cTrim(tBPOMissionStateSchema.getDealType())
								.equals("00") && StrTool.cTrim(
								tBPOMissionStateSchema.getState()).equals("0"))) {					
					if (StrTool.cTrim(tBPOMissionStateSchema.getBussNoType()).equals(
							"TB")) 
						wFlag = "0000001089";
				    else if (StrTool.cTrim(tBPOMissionStateSchema.getBussNoType())
						.equals("JM")) 
				    	wFlag = "0000001403";
					
				}

				// 抽检件已完成状态
				if (StrTool.cTrim(tBPOMissionStateSchema.getDealType()).equals(
						"01")
						&& StrTool.cTrim(tBPOMissionStateSchema.getState())
								.equals("1")) {
					wDealType = "00";
					wFlag = "0000001091";
				}

				// 异常件已完成状态
				if ((StrTool.cTrim(tBPOMissionStateSchema.getDealType())
						.equals("02")
						|| StrTool.cTrim(tBPOMissionStateSchema.getDealType())
								.equals("03") || StrTool.cTrim(
						tBPOMissionStateSchema.getDealType()).equals("04"))
						&& StrTool.cTrim(tBPOMissionStateSchema.getState())
								.equals("1")) {
					wDealType = "00";
					wFlag = "0000001090";
				}

				// 外包方无法处理的异常件（如整个扫描件无法识别）,已删除状态
				if (StrTool.cTrim(tBPOMissionStateSchema.getDealType()).equals(
						"03")
						&& StrTool.cTrim(tBPOMissionStateSchema.getState())
								.equals("2")) {
					wFlag = "0000001090";
				}
//			} else if (StrTool.cTrim(tBPOMissionStateSchema.getBussNoType())
//					.equals("JM")) {
//				// 当是正常件（此次正常导入）其工作流起始节点为外包录入节点0000001403
//				if (StrTool.cTrim(tBPOMissionStateSchema.getDealType())
//						.equals("00")) {
//					wFlag = "0000001403";
//					mTransferData.setNameAndValue("ErrFlag", "0");
//					mTransferData.setNameAndValue("AutoUWFlag", "1");
//				}
//				
//				// 当是正常件（此次正常导入）或者重复导入、或者未录入完成的异常件时，其工作流起始节点为外包录入节点0000001403
//				if ((( StrTool.cTrim(
//						tBPOMissionStateSchema.getDealType()).equals("05")) && StrTool
//						.cTrim(tBPOMissionStateSchema.getState()).equals("1"))
//						|| (!StrTool
//								.cTrim(tBPOMissionStateSchema.getDealType())
//								.equals("00") && StrTool.cTrim(
//								tBPOMissionStateSchema.getState()).equals("0"))) {
//					wFlag = "0000001403";
//					mTransferData.setNameAndValue("ErrFlag", "1");
//				}
//
//				// 异常件已完成状态
//				if ((StrTool.cTrim(tBPOMissionStateSchema.getDealType())
//						.equals("02")
//						|| StrTool.cTrim(tBPOMissionStateSchema.getDealType())
//								.equals("03") || StrTool.cTrim(
//						tBPOMissionStateSchema.getDealType()).equals("04"))
//						&& StrTool.cTrim(tBPOMissionStateSchema.getState())
//								.equals("1")) {
//					wFlag = "0000001090";
//				}
//
//				// 外包方无法处理的异常件（如整个扫描件无法识别）,已删除状态
//				if (StrTool.cTrim(tBPOMissionStateSchema.getDealType()).equals(
//						"03")
//						&& StrTool.cTrim(tBPOMissionStateSchema.getState())
//								.equals("2")) {
//					wFlag = "0000001090";
//				}
//			}

			logger.debug("***wFlag : " + wFlag + "***BussNoType："
					+ tBPOMissionStateSchema.getBussNoType());

			LCContSchema tLCContSchema = new LCContSchema();
			// 查询合同信息，不一定存在（正常导入的保单，有LCCont数据，其他情况都没有LCCont的数据）
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(tBPOMissionStateSchema.getBussNo());
			if (tLCContDB.getInfo()) {
				tLCContSchema.setSchema(tLCContDB.getSchema());
			}

			String tSQL = "select MissionID,SubMissionID from lwmission where activityid='"
					+ "?contno?"
					+ "' and MissionProp1='"
					+ "?MissionProp1?" + "'";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(tSQL);
			sqlbv.put("contno", wFlag);
			sqlbv.put("MissionProp1", tBPOMissionStateSchema.getBussNo());
			logger.debug("tSQL: " + tSQL);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = tExeSQL.execSQL(sqlbv);
			if ("".equals(StrTool.cTrim(tSSRS.GetText(1, 1)))
					|| "".equals(StrTool.cTrim(tSSRS.GetText(1, 2)))) {
				logger.debug("工作流数据查询失败!");
				conn.rollback();
				conn.close();
				CError.buildErr(this, "工作流数据查询失败！");
				return false;
			}

			ES_DOC_MAINSet tES_DOC_MAINSet = new ES_DOC_MAINSet();
//			if(StrTool.cTrim(tBPOMissionStateSchema.getBussNoType()).equals(
//			"TB"))
//			{
			 String tScanDate = "";
			 String tScanTime = "";
			 String tManageCom = "86";
				String tScanSQL = "select * from es_doc_main where doccode='"
					+ "?doccode?"
					+ "' order by makedate,maketime";
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(tScanSQL);
				sqlbv1.put("doccode", tBPOMissionStateSchema.getBussNo());
				logger.debug("tScanSQL: " + tScanSQL);				
				ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
				tES_DOC_MAINSet.set(tES_DOC_MAINDB.executeQuery(sqlbv1));
				if (tES_DOC_MAINSet == null || tES_DOC_MAINSet.size() == 0) {
//					logger.debug("扫描数据查询失败!");
//					CError.buildErr(this, "扫描数据查询失败!");
//					return false;
				}
				else
				{
					tScanDate = tES_DOC_MAINSet.get(1).getMakeDate();
					tScanTime = tES_DOC_MAINSet.get(1).getMakeTime();
					tManageCom = tES_DOC_MAINSet.get(1).getManageCom();
				}
//			}			

			mTransferData.setNameAndValue("ContNo", tBPOMissionStateSchema
					.getBussNo());
			mTransferData.setNameAndValue("PrtNo", tBPOMissionStateSchema
					.getBussNo());
			mTransferData.setNameAndValue("DealType", tBPOMissionStateSchema
					.getDealType());
			mTransferData.setNameAndValue("BussNoType", tBPOMissionStateSchema
					.getBussNoType());
			mTransferData.setNameAndValue("State", tBPOMissionStateSchema
					.getState());
			mTransferData.setNameAndValue("Operator", tBPOMissionStateSchema
					.getOperator());
			mTransferData.setNameAndValue("BPOID", tBPOMissionStateSchema
					.getBPOID());
			mTransferData.setNameAndValue("AppntNo", StrTool
					.cTrim(tLCContSchema.getAppntNo()));
			mTransferData.setNameAndValue("AppntName", StrTool
					.cTrim(tLCContSchema.getAppntName()));
			mTransferData.setNameAndValue("InsuredNo", StrTool
					.cTrim(tLCContSchema.getInsuredNo()));
			mTransferData.setNameAndValue("InsuredName", StrTool
					.cTrim(tLCContSchema.getInsuredName()));
			mTransferData.setNameAndValue("AgentCode", StrTool
					.cTrim(tLCContSchema.getAgentCode()));
//			if(StrTool.cTrim(tBPOMissionStateSchema.getBussNoType()).equals(
//			"TB"))
//			{
				mTransferData.setNameAndValue("ManageCom", StrTool
						.cTrim(tManageCom));
				mTransferData.setNameAndValue("ScanMakeDate", tScanDate);
				mTransferData.setNameAndValue("ScanMakeTime", tScanTime);
//			}	
			mTransferData.setNameAndValue("RiskCode", StrTool
					.cTrim(tBPOMissionStateSchema.getRiskCode()));
			mTransferData.setNameAndValue("SpecType", "0");
			mTransferData.setNameAndValue("MissionID", StrTool.cTrim(tSSRS
					.GetText(1, 1)));
			mTransferData.setNameAndValue("SubMissionID", StrTool.cTrim(tSSRS
					.GetText(1, 2)));

			tVData.add(mTransferData);
			tVData.add(tGI);
			logger.debug("wFlag=" + wFlag);
			logger.debug("-------------------start workflow---------------------");
			TbWorkFlowUI tTbWorkFlowUI = new TbWorkFlowUI();
			if (!tTbWorkFlowUI.submitData(tVData, wFlag)) {
				logger.debug("工作流数据处理失败，原因："
						+ tTbWorkFlowUI.mErrors.getError(0).errorMessage);
				conn.rollback();
				conn.close();
				CError.buildErr(this, "工作流数据处理失败，原因："
						+ tTbWorkFlowUI.mErrors.getError(0).errorMessage);
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		
		return true;
	}

	public static void main(String[] args) {
		BPODealInputDataBLS BPODealInputDataBLS1 = new BPODealInputDataBLS();
	}
}

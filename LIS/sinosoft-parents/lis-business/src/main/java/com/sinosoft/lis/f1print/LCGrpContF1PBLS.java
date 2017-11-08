/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCPolPrintDB;
import com.sinosoft.lis.db.LDContInvoiceMapDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LDContInvoiceMapSchema;
import com.sinosoft.utility.CBlob;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XMLDatasets;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 数据提交及xml流处理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author zz
 * @version 1.0
 */
public class LCGrpContF1PBLS {
private static Logger logger = Logger.getLogger(LCGrpContF1PBLS.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private File mFile = null;

	// 业务处理相关变量
	/** 全局数据 */
	private String mOperate = "";
	private String mGrpContNo = "";
	private VData mInputData;

	// private VData mResult = new VData();

	public LCGrpContF1PBLS() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		mOperate = cOperate;
		mInputData = (VData) cInputData.clone();

		if (mOperate.equals("PRINT")) {
			return save(mInputData);
		} else if (mOperate.equals("REPRINT")) {
			return update(mInputData);
		}

		return true;
	}

	/**
	 * 打印操作
	 * 
	 * @param mInputData
	 *            VData
	 * @return boolean
	 */
	private boolean save(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Save...");
		Connection conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpContF1PBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);
			logger.debug("Start ....");
			// 获取要保存的数据
			GlobalInput tGlobalInput = (GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0);
			// 更新LCGrpCont表数据
			LCGrpContDB tLCGrpContDB = new LCGrpContDB(conn);
			tLCGrpContDB.setSchema((LCGrpContSchema) mInputData
					.getObjectByObjectName("LCGrpContSchema", 0));
			String tPrtSeq = (String) mInputData.get(2);
			String tStartNo = (String) mInputData.get(3);
			mGrpContNo = tLCGrpContDB.getGrpContNo();

			LDContInvoiceMapSchema tLDMapSchema = new LDContInvoiceMapSchema();
			LDContInvoiceMapDB tLDMapDB = new LDContInvoiceMapDB(conn);
			tLDMapSchema.setBatchNo(tPrtSeq);
			tLDMapSchema.setContNo(mGrpContNo);
			tLDMapSchema.setInvoiceNo(tStartNo);
			tLDMapSchema.setMakeDate(PubFun.getCurrentDate());
			tLDMapSchema.setMakeTime(PubFun.getCurrentTime());
			tLDMapSchema.setModifyDate(PubFun.getCurrentDate());
			tLDMapSchema.setModifyTime(PubFun.getCurrentTime());
			tLDMapDB.setSchema(tLDMapSchema);
			if (!tLDMapDB.insert()) {
				conn.rollback();
				conn.close();
				CError tError = new CError();
				tError.moduleName = "LCGrpContF1PBLS";
				tError.functionName = "saveData";
				tError.errorMessage = tLDMapDB.mErrors.getFirstError();
				this.mErrors.addOneError(tError);
				return false;
			}

			// if (!tLCGrpContDB.update()) //先注释掉，便于测试，以后要恢复，yaory
			// {
			// // @@错误处理
			// this.mErrors.copyAllErrors(tLCGrpContDB.mErrors);
			// CError tError = new CError();
			// tError.moduleName = "GrpFeeBLS";
			// tError.functionName = "saveData";
			// tError.errorMessage = "删除管理费主表信息失败!";
			// this.mErrors.addOneError(tError);
			// conn.rollback();
			// conn.close();
			// return false;
			// }
			// 将数据流的信息存入blob字段中，更换了数据库，目前只支持db2的blob数据插入
			// saveDataStream(tXMLDatasets, tGlobalInput, conn);

			// 文件路径
			// 管理机构一层
			// String FilePath = tWebPath + "/printdata";
			// mFile = new File(FilePath);
			// if (!mFile.exists())
			// {
			// mFile.mkdir();
			// }
			// //根据合同号生成打印数据存放文件夹
			// // FilePath = tWebPath + "/printdata" + "/" + mGrpContNo;
			// FilePath = tWebPath + "/printdata" + "/data";
			// mFile = new File(FilePath);
			// if (!mFile.exists())
			// {
			// mFile.mkdir();
			// }

			// InputStream ins = tXMLDatasets.getInputStream();//换成自己的数据 yaory
			// InputStream ins = xmlexport.getInputStream();
			// InputStream ins1 = xmlexport1.getInputStream();
			// //根据团单合同号生成文件
			// String XmlFile = FilePath + "/" + mGrpContNo + ".xml";
			// String XmlFile1 = FilePath + "/" + mGrpContNo +"_2"+ ".xml";
			// FileOutputStream fos = new FileOutputStream(XmlFile);
			// FileOutputStream fos1 = new FileOutputStream(XmlFile1);
			// int n = 0;
			// while ((n = ins.read()) != -1)
			// {
			// fos.write(n);
			// }
			// fos.close();//yaory 测试时用，以后不用了，就把校验路径与写文件功能去掉
			//
			// n=0;
			// while ((n = ins1.read()) != -1)
			// {
			// fos1.write(n);
			// }
			// fos1.close();

			// 获取保单影印件列表信息
			// String[][] tScanPic = (String[][]) mInputData.get(4);
			// if (tScanPic != null)
			// {
			// for (int i = 0; i < tScanPic.length; i++)
			// {
			// URL jspUrl = new URL(tScanPic[i][0]);
			// URLConnection uc = jspUrl.openConnection();
			// logger.debug("URL===" + uc.getURL());
			// fos = new FileOutputStream(FilePath + "/" + tScanPic[i][1]);
			// int fn = 0;
			// while ((fn = uc.getInputStream().read()) != -1)
			// {
			// fos.write(fn);
			// }
			// fos.close();
			// }
			// }

			// //获取协议影印件列表信息 现在应该没有协议影象信息
			// String[][] tScanDoc = (String[][]) mInputData.get(5);
			// if (tScanDoc != null)
			// {
			// for (int i = 0; i < tScanDoc.length; i++)
			// {
			// URL jspUrl = new URL(tScanDoc[i][0]);
			// URLConnection uc = jspUrl.openConnection();
			// fos = new FileOutputStream(FilePath + "/" + tScanDoc[i][1]);
			// int fn = 0;
			// while ((fn = uc.getInputStream().read()) != -1)
			// {
			// fos.write(fn);
			// }
			// fos.close();
			// }
			// }
			conn.commit();
			conn.close();
			logger.debug("commit end");
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpContF1PBLS";
			tError.functionName = "submitData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
			tReturn = false;
		}

		return tReturn;
	}

	/**
	 * 将所有得到的打印数据按照主险保单号存入数据库，为以后的补打保单做准备
	 * 
	 * @param cXMLDataSets
	 *            XMLDatasets
	 * @param cGlobalInput
	 *            GlobalInput
	 * @param conn
	 *            Connection
	 * @throws Exception
	 */
	private void saveDataStream(XMLDatasets cXMLDataSets,
			GlobalInput cGlobalInput, Connection conn) throws Exception {

		InputStream tIns = cXMLDataSets.getInputStream();
		saveOneDataStream(tIns, cGlobalInput, conn);
		// Document doc = aXMLDataSets.getDocument();
		// List list = doc.getRootElement().getChildren("DATASET");
		// //转换xml流数据为document对象
		// for (int nIndex = 0; nIndex < list.size(); nIndex++)
		// {
		// Element ele = (Element) list.get(nIndex);
		// // Build a new element from the content of old element.
		// // By doing this, we can detach element from document
		// ele = new Element("DATASET").setMixedContent(ele.getMixedContent());
		// // Document newDoc = new Document(new
		// Element("DATASETS").addContent(ele));
		// Document newDoc = new Document(ele);
		// }
	}

	/**
	 * 保存一条保单打印的数据
	 * 
	 * @param cIns
	 *            InputStream
	 * @param cGlobalInput
	 *            GlobalInput
	 * @param conn
	 *            Connection
	 * @throws Exception
	 */
	private void saveOneDataStream(InputStream cIns, GlobalInput cGlobalInput,
			Connection conn) throws Exception {
		try {
			// 获得合同单号
			String strCurDate = PubFun.getCurrentDate();
			String strCurTime = PubFun.getCurrentTime();
			// 数据库中删除旧有数据，针对保单重打时候的操作
			LCPolPrintDB tLCPolPrintDB = new LCPolPrintDB(conn);
			tLCPolPrintDB.setMainPolNo(mGrpContNo);
			// 删除操作改用db的delete方法执行，通用化
			if (!tLCPolPrintDB.delete()) {
				throw new Exception("删除数据失败！");
			}

			String[] parmStrArr = new String[4];
			StringBuffer tSBql = new StringBuffer(256);
			// 插入数据操作
			// parmStrArr[0] = "INSERT INTO LCPolPrint(MainPolNo, PrtTimes,
			// Operator, MakeDate, MakeTime, ModifyDate, ModifyTime,PolInfo,
			// PolType) VALUES('"
			// + mGrpContNo + "', 1, '" +
			// cGlobalInput.Operator +
			// "', '" + strCurDate + "', '" + strCurTime + "', '" +
			// strCurDate + "', '" + strCurTime +
			// "',empty_blob() ,'2')";
			tSBql
					.append("INSERT INTO LCPolPrint(MainPolNo, PrtTimes, Operator, MakeDate, MakeTime, ModifyDate, ModifyTime,PolInfo, PolType) VALUES('");
			tSBql.append(mGrpContNo);
			tSBql.append("', 1, '");
			tSBql.append(cGlobalInput.Operator);
			tSBql.append("', '");
			tSBql.append(strCurDate);
			tSBql.append("', '");
			tSBql.append(strCurTime);
			tSBql.append("', '");
			tSBql.append(strCurDate);
			tSBql.append("', '");
			tSBql.append(strCurTime);
			tSBql.append("',empty_blob() ,'2')");

			parmStrArr[0] = tSBql.toString();
			parmStrArr[1] = "LCPolPrint";
			parmStrArr[2] = "PolInfo";
			parmStrArr[3] = " and MainPolNo = '" + mGrpContNo + "' ";

			// CBlob tCBlob = new CBlob();
			if (!CBlob.BlobInsert(cIns, parmStrArr, conn)) {
				throw new Exception("Blob数据操作失败！");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	/**
	 * Kevin 2003-03-27 补打保单时对应的保存函数
	 * 
	 * @param vData
	 *            VData
	 * @return boolean
	 */
	private boolean update(VData vData) {
		LCGrpContSchema tLCGrpContSchema = (LCGrpContSchema) vData
				.getObjectByObjectName("LCGrpContSchema", 0);

		Connection conn = null;
		Statement stmt = null;
		// String strSQL = "";

		try {
			conn = DBConnPool.getConnection();
			stmt = conn.createStatement();

			if (conn == null) {
				throw new Exception("连接数据库失败！");
			}
			conn.setAutoCommit(false);
			// strSQL = "UPDATE LCPolPrint SET ModifyDate = '" +
			// PubFun.getCurrentDate() + "' , ModifyTime = '" +
			// PubFun.getCurrentTime()
			// + "' WHERE MainPolNo = '" + tLCGrpContSchema.getGrpContNo() +
			// "'";
			StringBuffer tSBql = new StringBuffer(256);
			tSBql.append("UPDATE LCPolPrint SET ModifyDate = '");
			tSBql.append(PubFun.getCurrentDate());
			tSBql.append("' , ModifyTime = '");
			tSBql.append(PubFun.getCurrentTime());
			tSBql.append("' WHERE MainPolNo = '");
			tSBql.append(tLCGrpContSchema.getGrpContNo());
			tSBql.append("'");

			stmt.executeUpdate(tSBql.toString());
			conn.commit();
			if (stmt != null) {
				stmt.close();
			}
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				// do nothing
			}
			buildError("getRePrintData", ex.getMessage());
			return false;
		}

		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "LCPolF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
}

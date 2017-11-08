package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author zz
 * @version 1.0
 */
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CMySQLBlob;
import com.sinosoft.utility.COracleBlob;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XMLDatasets;

public class LCPolF1PBLS {
private static Logger logger = Logger.getLogger(LCPolF1PBLS.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	// 业务处理相关变量
	/** 全局数据 */
	private String mOperate = "";

	private VData mInputData;

	public LCPolF1PBLS() {
	}

	public static void main(String[] args) {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		mInputData = (VData) cInputData.clone();

		if (cOperate.equals("PRINT") || cOperate.equals("PRINTEX")) {
			return save(cInputData);
		} else if (cOperate.equals("REPRINT")) {
			return update(cInputData);
		} else {
			CError tError = new CError();
			tError.moduleName = "LCPolF1PBLS";
			tError.functionName = "submitData";
			tError.errorMessage = "不支持的操作字符串";
			mErrors.addOneError(tError);
			return false;
		}
	}

	// 保存操作
	private boolean save(VData mInputData) {
		boolean tReturn = true;
		logger.debug("Start Save...");
		Connection conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPolF1PBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			conn.setAutoCommit(false);

			logger.debug("Start ....");
			LCContDB tLCContDB = new LCContDB(conn);

			// 获取所需要的数据
			LCContSet tLCContSet = (LCContSet) mInputData
					.getObjectByObjectName("LCContSet", 0);
			XMLDatasets tXMLDatasets = (XMLDatasets) mInputData
					.getObjectByObjectName("XMLDatasets", 0);
			GlobalInput tGlobalInput = (GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0);

			for (int i = 0; i < tLCContSet.size(); i++) {
				tLCContDB.setSchema(tLCContSet.get(i + 1));

				if (!tLCContDB.update()) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLCContDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "LCPolF1PBLS";
					tError.functionName = "saveData";
					tError.errorMessage = "数据更新失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}// end for ()

			// 保存数据流
			saveDataStream(tXMLDatasets, tGlobalInput, conn);

			conn.commit();
			conn.close();
			logger.debug("commit end");
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPolF1PBLS";
			tError.functionName = "submitData";
			tError.errorMessage = ex.getMessage();
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
	 * @param aXMLDataSets
	 * @param aGlobalInput
	 * @param conn
	 * @throws Exception
	 */
	private void saveDataStream(XMLDatasets aXMLDataSets,
			GlobalInput aGlobalInput, Connection conn) throws Exception {

		Document doc = aXMLDataSets.getDocument();
		List list = doc.getRootElement().getChildren("DATASET");

		for (int nIndex = 0; nIndex < list.size(); nIndex++) {
			Element ele = (Element) list.get(nIndex);

			//
			// Build a new element from the content of old element.
			// By doing this, we can detach element from document
			//
			ele = new Element("DATASET").setMixedContent(ele.getMixedContent());

			// Document newDoc = new Document(new
			// Element("DATASETS").addContent(ele));
			Document newDoc = new Document(ele);
			saveOneDataStream(newDoc, aGlobalInput, conn);
		}

	}

	/**
	 * 保存一条保单打印的数据
	 * 
	 * @param doc
	 * @param aGlobalInput
	 * @param conn
	 * @throws Exception
	 */
	private void saveOneDataStream(Document doc, GlobalInput aGlobalInput,
			Connection conn) throws Exception {
		// modify by yt
		COracleBlob tCOracleBlob = new COracleBlob();
		CMySQLBlob tCMySQLBlob = new CMySQLBlob();
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
						
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
						
		}
		try {
			String strContNo = doc.getRootElement()
					.getChildText("LCCont.ContNo"); // 标签而已，实际存放的是保单号

			String strCurDate = PubFun.getCurrentDate();
			String strCurTime = PubFun.getCurrentTime();

			// 得到数据输出对象
			String szSQL = "DELETE FROM LCContPrint WHERE ContNo = '"
					+ strContNo + "'";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				if (!tCOracleBlob.DeleteBlobRecord(szSQL, conn)) {
					throw new Exception("删除数据失败！" + szSQL);
				}
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				if (!tCMySQLBlob.DeleteBlobRecord(szSQL, conn)) {
					throw new Exception("删除数据失败！" + szSQL);
				}		
			}
			szSQL = "INSERT INTO LCContPrint(ContNo, PrtTimes, Operator, MakeDate, MakeTime, ModifyDate, ModifyTime, ContInfo, ContType) VALUES('"
					+ strContNo
					+ "', "
					+ "1, '"
					+ aGlobalInput.Operator
					+ "', '"
					+ strCurDate
					+ "', '"
					+ strCurTime
					+ "', '"
					+ strCurDate
					+ "', '"
					+ strCurTime
					+ "', empty_blob(), '1')";

			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				if (!tCOracleBlob.InsertBlankBlobRecord(szSQL, conn)) {
					throw new Exception("插入数据失败！" + szSQL);
				}		
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				if (!tCMySQLBlob.InsertBlankBlobRecord(szSQL, conn)) {
					throw new Exception("插入数据失败！" + szSQL);
				}		
			}
			szSQL = " and ContNo = '" + strContNo + "' ";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				if (!tCOracleBlob.UpdateBlob(doc, "LCContPrint", "ContInfo", szSQL,
						conn)) {
					throw new Exception("修改数据失败！" + szSQL);
				}		
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				if (!tCMySQLBlob.UpdateBlob(doc, "LCContPrint", "ContInfo", szSQL,
						conn)) {
					throw new Exception("修改数据失败！" + szSQL);
				}		
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	private boolean update(VData vData) {
		LCContSet tLCContSet = (LCContSet) vData.getObjectByObjectName(
				"LCContSet", 0);

		Connection conn = null;
		Statement stmt = null;
		String strSQL = "";

		try {
			conn = DBConnPool.getConnection();
			stmt = conn.createStatement();

			if (conn == null) {
				throw new Exception("连接数据库失败！");
			}

			conn.setAutoCommit(false);

			for (int nIndex = 0; nIndex < tLCContSet.size(); nIndex++) {
				LCContSchema tLCContSchema = tLCContSet.get(nIndex + 1);

				strSQL = "UPDATE LCContPrint SET PrtTimes = "
						+ tLCContSchema.getPrintCount() + ", ModifyDate = '"
						+ tLCContSchema.getModifyDate() + "' , ModifyTime = '"
						+ tLCContSchema.getModifyTime() + "' WHERE ContNo = '"
						+ tLCContSchema.getContNo() + "'";

				stmt.executeUpdate(strSQL);
			}

			conn.commit();

			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
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

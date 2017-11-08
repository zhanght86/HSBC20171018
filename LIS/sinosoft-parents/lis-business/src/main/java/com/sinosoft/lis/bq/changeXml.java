package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;

import com.sinosoft.lis.db.LDCode1DB;
import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.db.LMEdorItemDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPUWMasterMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.XmlFun;
import com.sinosoft.lis.schema.LPEdorPrint2Schema;
import com.sinosoft.lis.schema.LPEdorPrintSchema;
import com.sinosoft.lis.schema.LPUWMasterMainSchema;
import com.sinosoft.lis.vschema.LDCode1Set;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LMEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPUWMasterMainSet;
import com.sinosoft.utility.CMySQLBlob;
import com.sinosoft.utility.COracleBlob;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.VData;

public class changeXml {
private static Logger logger = Logger.getLogger(changeXml.class);
	public static int num = 0;
	private Document myDocument;
	private DOMBuilder myBuilder;
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mAppEdorNo = "";
	private String mNewEdorNo = "";
	private EdorNoUpdate mEdornoUpdate = new EdorNoUpdate();
	private InputStream ins = null;
	private InputStream ins1 = null;
	private VData mResult = new VData();
	private MMap map = new MMap();

	/** 构造函数 */
	public changeXml(String appEdorno, String edorNo, GlobalInput globalInput) {
		mAppEdorNo = appEdorno;
		mNewEdorNo = edorNo;
		mGlobalInput = globalInput;
	}

	public boolean changeGrpEdor() {
		logger.debug("start changeGrpEdor....");
		myBuilder = new DOMBuilder();

		Connection conn = DBConnPool.getConnection();

		try {
			conn.setAutoCommit(false);

			// 读取保全批单的内容
			COracleBlob tCOracleBlob = new COracleBlob();
			CMySQLBlob tCMySQLBlob = new CMySQLBlob();
			java.sql.Blob tBlob = null;
			String tSQL = " and EdorNo = '" + mAppEdorNo + "'";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tBlob = tCOracleBlob.SelectBlob("LPEDORPRINT", "edorinfo", tSQL,
						conn);
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tBlob = tCMySQLBlob.SelectBlob("LPEDORPRINT", "edorinfo", tSQL,
						conn);
			}
			

			if (tBlob == null) {
				conn.rollback();
				conn.close();
				return false;
				// throw new Exception("找不到打印数据");
			}

			// BLOB blob = ((OracleResultSet)rs).getBLOB("edorinfo");//del by yt
			// BLOB blob = (oracle.sql.BLOB) tBlob;
			myDocument = myBuilder.build(tBlob.getBinaryStream());

			Element rootElement = myDocument.getRootElement();
			Element edorElm = rootElement.getChild("EdorNo");
			logger.debug("chageTest: " + edorElm.getText());
			logger.debug("mNewEdorno : " + mNewEdorNo);
			edorElm.setText(mNewEdorNo);

			// replace template from PrtGrpEndorsementapp.vts to
			// PrtGrpEndorsement.vts
			Element controlElm = rootElement.getChild("CONTROL");
			Element templateElm = controlElm.getChild("TEMPLATE");
			templateElm.setText("PrtGrpEndorsement.vts");

			updateGrpEdorValidDate();

			// 转换Document为InputStream
			XMLOutputter outputter = new XMLOutputter("  ", true, "GB2312");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			outputter.output(myDocument, baos);
			baos.close();
			ins = new ByteArrayInputStream(baos.toByteArray());

			// 取得新的LPEDORPRINTSCHEMA存入map
			if (!insertEdor()) {
				return false;
			}

			if (!changeGrpEdorBill(conn)) {
				return false;
			}

			mResult.addElement(map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean changePEdor() {
		logger.debug("start changePEdor....");
		myBuilder = new DOMBuilder();

		Connection conn = DBConnPool.getConnection();

		try {
			conn.setAutoCommit(false);

			// 读取保全批单的内容
			COracleBlob tCOracleBlob = new COracleBlob();
			CMySQLBlob tCMySQLBlob = new CMySQLBlob();
			java.sql.Blob tBlob = null;
			String tSQL = " and EdorAcceptNo = '" + mAppEdorNo + "'";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tBlob = tCOracleBlob.SelectBlob("LPEDORAPPPRINT", "edorinfo", tSQL,conn);
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tBlob = tCMySQLBlob.SelectBlob("LPEDORAPPPRINT", "edorinfo", tSQL,conn);
			}


			if (tBlob == null) {
				conn.rollback();
				conn.close();
				return false;
				// throw new Exception("找不到打印数据");
			}

			// BLOB blob = ((OracleResultSet)rs).getBLOB("edorinfo");//del by yt
			// BLOB blob = (oracle.sql.BLOB) tBlob;
			myDocument = myBuilder.build(tBlob.getBinaryStream());

			Element rootElement = myDocument.getRootElement();
			// Element edorElm = rootElement.getChild("EdorNo"); delete by lizhuo at
			// 2005.8.2
			// logger.debug("chageTest: " + edorElm.getText());
			// logger.debug("mNewEdorno : " + mNewEdorNo);
			// edorElm.setText(mNewEdorNo);

			// replace template from PrtEndorsementapp.vts to PrtEndorsement.vts
			Element controlElm = rootElement.getChild("CONTROL");
			Element templateElm = controlElm.getChild("TEMPLATE");
			templateElm.setText("PrtEndorsement.vts");

			// updateEdorValidDate(); delete by lizhuo at 2005.8.2

			// 转换Document为InputStream
			XMLOutputter outputter = new XMLOutputter("  ", true, "GB2312");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			outputter.output(myDocument, baos);
			baos.close();
			ins = new ByteArrayInputStream(baos.toByteArray());

			// 取得新的LPEDORPRINTSCHEMA存入map
			if (!insertEdor()) {
				return false;
			}

			// if (!changeGrpEdorBill(conn)) {
			// return false;
			// }

			mResult.clear();
			mResult.addElement(map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void setAppEdorNo(String appEdorno) {
		mAppEdorNo = appEdorno;
	}

	private void updateGrpEdorValidDate() {
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String strSql = "select * from lpgrpedoritem where edorno = '"
				+ "?mNewEdorNo?" + "'";
		sqlbv.sql(strSql);
		sqlbv.put("mNewEdorNo", mNewEdorNo);
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		LPGrpEdorItemSet tSet = tLPGrpEdorItemDB.executeQuery(sqlbv);

		Element rootElm = myDocument.getRootElement();

		// update validdate text
		for (int i = 1; i <= tSet.size(); i++) {
			String strEdorValidDate = tSet.get(i).getEdorType()
					+ "_EdorValiDate";

			Element validDateElm = rootElm.getChild(strEdorValidDate);

			if (validDateElm == null) {
				continue;
			}

			validDateElm.setText(tSet.get(i).getEdorValiDate());
		}
	}

	private void updateEdorValidDate() {
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String strSql = "select * from lpedoritem where edorno = '"
				+ "?mNewEdorNo?" + "'";
		sqlbv.sql(strSql);
		sqlbv.put("mNewEdorNo", mNewEdorNo);
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tSet = tLPEdorItemDB.executeQuery(sqlbv);

		Element rootElm = myDocument.getRootElement();

		// update validdate text
		for (int i = 1; i <= tSet.size(); i++) {
			String strEdorValidDate = tSet.get(i).getEdorType()
					+ "_EdorValiDate";

			Element validDateElm = rootElm.getChild(strEdorValidDate);

			if (validDateElm == null) {
				continue;
			}

			validDateElm.setText(tSet.get(i).getEdorValiDate());

		}
	}

	// 生成新的LPEdorPrintSchema
	public boolean insertEdor() {
		// if (conn == null) {
		// return false;
		// }

		LPEdorPrintSchema tLPEdorPrintSchema = new LPEdorPrintSchema();
		tLPEdorPrintSchema.setEdorNo(mNewEdorNo);
		tLPEdorPrintSchema.setManageCom(mGlobalInput.ManageCom);
		tLPEdorPrintSchema.setPrtFlag("N");
		tLPEdorPrintSchema.setPrtTimes(0);
		tLPEdorPrintSchema.setMakeDate(PubFun.getCurrentDate());
		tLPEdorPrintSchema.setMakeTime(PubFun.getCurrentTime());
		tLPEdorPrintSchema.setOperator(mGlobalInput.Operator);
		tLPEdorPrintSchema.setModifyDate(PubFun.getCurrentDate());
		tLPEdorPrintSchema.setModifyTime(PubFun.getCurrentTime());
		tLPEdorPrintSchema.setEdorInfo(ins);
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String del = "delete from lpedorprint where edorno = '" + "?mNewEdorNo?"
				+ "'";
		sqlbv.sql(del);
		sqlbv.put("mNewEdorNo", mNewEdorNo);
		map.put(sqlbv, "DELETE");
		map.put(tLPEdorPrintSchema, "BLOBINSERT");
		// map.put(tLPEdorPrintSchema, "BLOBUPDATE");
		// boolean mflag = false;
		//
		// //modify by yt ,about Blob operate
		// COracleBlob tCOracleBlob = new COracleBlob();
		//
		// try {
		// logger.debug("try begin....");
		//
		// // String szSQL = "delete from LPEdorPrint where edorno =
		// '"+tLPEdorPrintSchema.getEdorNo()+"'";
		// // logger.debug("----sql:"+szSQL);
		// // if (!tCOracleBlob.DeleteBlobRecord(szSQL,conn))
		// // {
		// // throw new Exception("delete数据失败！"+szSQL);
		// // }
		// String szSQL = "INSERT INTO LPEdorPrint VALUES ('" +
		// tLPEdorPrintSchema.getEdorNo() + "','" +
		// tLPEdorPrintSchema.getPrtFlag() + "','" +
		// tLPEdorPrintSchema.getPrtTimes() + "','" +
		// tLPEdorPrintSchema.getManageCom() + "','" +
		// tLPEdorPrintSchema.getOperator() + "','" +
		// tLPEdorPrintSchema.getMakeDate() + "','" +
		// tLPEdorPrintSchema.getMakeTime() + "','" +
		// tLPEdorPrintSchema.getModifyDate() + "','" +
		// tLPEdorPrintSchema.getModifyTime() +
		// "',empty_blob())";
		// logger.debug("----sql:" + szSQL);
		//
		// if (!tCOracleBlob.InsertBlankBlobRecord(szSQL, conn)) {
		// throw new Exception("插入数据失败！" + szSQL);
		// }
		//
		// // displayDocument(myDocument.getRootElement());
		// szSQL = " and EdorNo = '" + tLPEdorPrintSchema.getEdorNo() + "' ";
		//
		// if (!tCOracleBlob.UpdateBlob(ins, "LPEdorPrint", "EdorInfo",
		// szSQL, conn)) {
		// throw new Exception("修改数据失败！" + szSQL);
		// }
		//
		// //显示转换后的批单内容
		// // COracleBlob blob = new COracleBlob();
		// // Blob b = blob.SelectBlob("LPEdorPrint", "edorinfo",
		// // " and edorno='" + tLPEdorPrintSchema.getEdorNo() + "' ",
		// // conn);
		//
		// // XmlFun.displayBlob(b);
		// // OutputStream os1 = new FileOutputStream("C:\\ddd.xml");//del by yt,
		// // xmlOutputter.output(myDocument.getRootElement(),os1);
		// logger.debug("----------success------------------");
		// } catch (Exception ex1) {
		// ex1.printStackTrace();
		//
		// return false;
		// }

		return true;
	}

	public boolean insertEdorBill() {

		LPEdorPrint2Schema tLPEdorPrint2Schema = new LPEdorPrint2Schema();
		tLPEdorPrint2Schema.setEdorNo(mNewEdorNo);
		tLPEdorPrint2Schema.setManageCom(mGlobalInput.ManageCom);
		tLPEdorPrint2Schema.setPrtFlag("N");
		tLPEdorPrint2Schema.setPrtTimes(0);
		tLPEdorPrint2Schema.setMakeDate(PubFun.getCurrentDate());
		tLPEdorPrint2Schema.setMakeTime(PubFun.getCurrentTime());
		tLPEdorPrint2Schema.setOperator(mGlobalInput.Operator);
		tLPEdorPrint2Schema.setModifyDate(PubFun.getCurrentDate());
		tLPEdorPrint2Schema.setModifyTime(PubFun.getCurrentTime());
		tLPEdorPrint2Schema.setEdorInfo(ins1);
		map.put(tLPEdorPrint2Schema, "BLOBUPDATE");

		return true;
	}

	public boolean changeGrpEdorBill(Connection conn) {
		if (conn == null) {
			return false;
		}

		myBuilder = new DOMBuilder();

		try {
			// 读取保全批单的内容
			COracleBlob tCOracleBlob = new COracleBlob();
			CMySQLBlob tCMySQLBlob = new CMySQLBlob();
			java.sql.Blob tBlob = null;
			String tSQL = " and EdorNo = '" + mAppEdorNo + "'";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tBlob = tCOracleBlob.SelectBlob("LPEDORPRINT2", "edorinfo", tSQL,
						conn);
				
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tBlob = tCMySQLBlob.SelectBlob("LPEDORPRINT2", "edorinfo", tSQL,
						conn);

			}

			if (tBlob == null) {
				// 如果查询不到，可能是没有变动清单，暂不处理
				return true;
			}

			// BLOB blob = ((OracleResultSet)rs).getBLOB("edorinfo");//del by yt
			// BLOB blob = (oracle.sql.BLOB) tBlob;
			myDocument = myBuilder.build(tBlob.getBinaryStream());

			Element rootElement = myDocument.getRootElement();
			Element edorElm = rootElement.getChild("EdorNo");
			logger.debug("OldEdorno : " + edorElm.getText());
			logger.debug("mNewEdorno : " + mNewEdorNo);
			edorElm.setText(mNewEdorNo);

			XMLOutputter outputter = new XMLOutputter("  ", true, "GB2312");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			outputter.output(myDocument, baos);
			baos.close();
			ins1 = new ByteArrayInputStream(baos.toByteArray());

			if (!insertEdorBill()) {
				return false;
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean changeTest() {
		logger.debug("\nStart Change XML");
		myBuilder = new DOMBuilder();

		Connection conn = DBConnPool.getConnection();

		try {
			conn.setAutoCommit(false);

			// 读取打印的批单信息
			COracleBlob tCOracleBlob = new COracleBlob();
			CMySQLBlob tCMySQLBlob = new CMySQLBlob();
			String tSQL = " and EdorNo = '" + mAppEdorNo + "'";
			Blob tBlob = null;
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tBlob = tCOracleBlob.SelectBlob("LPEDORPRINT", "edorinfo", tSQL,
						conn);
							
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tBlob = tCMySQLBlob.SelectBlob("LPEDORPRINT", "edorinfo", tSQL,
						conn);
			}
			// XmlFun.displayBlob(tBlob);
			if (tBlob == null) {
				conn.rollback();
				conn.close();
				return false;
				// throw new Exception("找不到打印数据");
			}

			// BLOB blob = ((OracleResultSet)rs).getBLOB("edorinfo");
			// BLOB blob = (oracle.sql.BLOB) tBlob;
			myDocument = myBuilder.build(tBlob.getBinaryStream());
			logger.debug("get stream object:");

			Element rootElement = myDocument.getRootElement();

			// 增加特约显示控制
			// addSpecElement(rootElement);

			// 增加加费显示控制
			// addAddPremElement(rootElement);

			// 调整财务统计信息
			modifyFinFee(rootElement);

			// 增加核保意见显示控制
			addUWIdeaElement(rootElement);

			Element edorElm = rootElement.getChild("EdorNo");
			logger.debug("EdorAppNo: " + edorElm.getText());
			logger.debug("mNewEdorno : " + mNewEdorNo);
			edorElm.setText(mNewEdorNo);

			// replace template from PrtEndorsementapp.vts to PrtEndorsement.vts
			Element controlElm = rootElement.getChild("CONTROL");
			Element templateElm = controlElm.getChild("TEMPLATE");
			templateElm.setText("PrtEndorsement.vts");

			updateEdorValidDate();

			// displayDocument(rootElement);
			// insert the new to the database
			if (!insertEdor()) {
				logger.debug("insertEdor failure");
				conn.rollback();
				conn.close();

				return false;
			}

			conn.commit();
			conn.close();
			logger.debug("changetest() success");

			return true;
		} catch (Exception e) {
			e.printStackTrace();

			try {
				if (conn != null) {
					conn.rollback();
					conn.close();
				}
			} catch (Exception e1) {
			}

			logger.debug("exception error :" + e.getMessage());

			return false;
		}
	}

	/**
	 * 为批单增加保全核保特约内容
	 * 
	 * @param root
	 */
	// public void addSpecElement(Element root) {
	// LPUWMasterMainDB tLPUWMasterMainDB = new LPUWMasterMainDB();
	// String strSql = "select * from LPUWMasterMain where EdorNo='" +
	// mAppEdorNo + "' and SpecFlag<>'0'";
	// LPUWMasterMainSet tLPUWMasterMainSet = tLPUWMasterMainDB.executeQuery(
	// strSql);
	//
	// if (tLPUWMasterMainSet.size() > 0) {
	// LPUWMasterMainSchema tLPUWMasterMainSchema = tLPUWMasterMainSet.get(
	// 1);
	//
	// Element eControl = root.getChild("CONTROL");
	// Element eDisplay = eControl.getChild("DISPLAY");
	// Element eDisplaySpec = new Element("displaySpec");
	// eDisplaySpec.setText("1");
	// eDisplay.addContent(eDisplaySpec);
	//
	// Element eSpec = new Element("Spec");
	// Element eHEAD = new Element("HEAD");
	//
	// // Element eCOL1 = new Element("COL1");
	// // eCOL1.setText("PolNo");
	// // eHEAD.addContent(eCOL1);
	// Element eCOL1 = new Element("COL2");
	// eCOL1.setText("SpecContent");
	// eHEAD.addContent(eCOL1);
	// eSpec.addContent(eHEAD);
	//
	// LCSpecDB tLCSpecDB = new LCSpecDB();
	// strSql = " select * from LCSpec where PolNo = '" +
	// tLPUWMasterMainSchema.getPolNo() + "' ";
	//
	// LCSpecSet tLCSpecSet = tLCSpecDB.executeQuery(strSql);
	//
	// if (tLCSpecSet.size() > 0) {
	// root.addContent(eSpec);
	// }
	//
	// for (int j = 0; j < tLCSpecSet.size(); j++) {
	// strSql = "select * from lpspec where polno='" +
	// tLCSpecSet.get(j + 1).getPolNo() +
	// "' and endorsementno='" +
	// mAppEdorNo + "'";
	//
	// LPSpecDB tLPSpecDB = new LPSpecDB();
	// LPSpecSet tLPSpecSet = tLPSpecDB.executeQuery(strSql);
	//
	// String specContent = tLCSpecSet.get(j + 1).getSpecContent();
	// int lpSpec = 0;
	//
	// if (tLPSpecSet.size() > 0) {
	// lpSpec = specContent.indexOf(tLPSpecSet.get(1)
	// .getSpecContent());
	//
	// if (lpSpec != -1) {
	// specContent = specContent.substring(0, lpSpec);
	// }
	// }
	//
	// Element eROW = new Element("ROW");
	//
	// // eCOL1 = new Element("COL1");
	// // eCOL1.setText(tLCSpecSet.get(j + 1).getPolNo());
	// // eROW.addContent(eCOL1);
	// eCOL1 = new Element("COL2");
	// eCOL1.setText(specContent);
	// eROW.addContent(eCOL1);
	// eSpec.addContent(eROW);
	// }
	// }
	//
	// // displayDocument(root);
	// }
	/**
	 * 为批单增加保全核保加费内容
	 * 
	 * @param root
	 */
	// public void addAddPremElement(Element root) {
	// LPUWMasterMainDB tLPUWMasterMainDB = new LPUWMasterMainDB();
	// String strSql = "select * from LPUWMasterMain where EdorNo='" +
	// mAppEdorNo + "' and ChangePolFlag='1'";
	// LPUWMasterMainSet tLPUWMasterMainSet = tLPUWMasterMainDB.executeQuery(
	// strSql);
	//
	// if (tLPUWMasterMainSet.size() > 0) {
	// LPUWMasterMainSchema tLPUWMasterMainSchema = tLPUWMasterMainSet.get(
	// 1);
	//
	// Element eControl = root.getChild("CONTROL");
	// Element eDisplay = eControl.getChild("DISPLAY");
	// Element eDisplaySpec = new Element("displayAddPrem");
	// eDisplaySpec.setText("1");
	// eDisplay.addContent(eDisplaySpec);
	//
	// Element eSpec = new Element("AddPrem");
	// Element eHEAD = new Element("HEAD");
	//
	// Element eCOL1 = new Element("COL1");
	// eCOL1.setText("RiskCode");
	// eHEAD.addContent(eCOL1);
	//
	// Element eCOL2 = new Element("COL2");
	// eCOL2.setText("AddPrem");
	// eHEAD.addContent(eCOL2);
	//
	// eSpec.addContent(eHEAD);
	// root.addContent(eSpec);
	//
	// //互换后的P表数据，即原保单数据
	// LPPremDB tLPPremDB = new LPPremDB();
	// tLPPremDB.setEdorNo(mNewEdorNo);
	//
	// // tLPPremDB.setPolNo(tLPUWMasterMainSchema.getPolNo());
	// LPPremSet tLPPremSet = tLPPremDB.query();
	//
	// //C表数据为包括加费项的数据
	// LCPremDB tLCPremDB = new LCPremDB();
	// String sql =
	// "select * from lcprem where polno in (select polno from lcpol where
	// mainpolno='" +
	// tLPUWMasterMainSchema.getPolNo() + "')";
	//
	// // tLCPremDB.setPolNo(tLPUWMasterMainSchema.getPolNo());
	// LCPremSet tLCPremSet = tLCPremDB.executeQuery(sql);
	//
	// //获取该次保全加费数据
	// LCPremSet addLCPremSet = new LCPremSet();
	//
	// for (int i = 0; i < tLCPremSet.size(); i++) {
	// LCPremSchema tLCPremSchema = tLCPremSet.get(i + 1);
	//
	// boolean f = true;
	//
	// for (int j = 0; j < tLPPremSet.size(); j++) {
	// if (tLCPremSchema.getDutyCode().equals(tLPPremSet.get(j +
	// 1).getDutyCode()) &&
	// tLCPremSchema.getPayPlanCode().equals(tLPPremSet.get(j +
	// 1).getPayPlanCode())) {
	// f = false;
	//
	// break;
	// }
	// }
	//
	// if (f) {
	// addLCPremSet.add(tLCPremSchema);
	// }
	// }
	//
	// for (int j = 0; j < addLCPremSet.size(); j++) {
	// LCPremSchema tLCPremSchema = addLCPremSet.get(j + 1);
	// LCPolDB tLCPolDB = new LCPolDB();
	// tLCPolDB.setPolNo(tLCPremSchema.getPolNo());
	// tLCPolDB.getInfo();
	//
	// //从险种定义表获取险种名称
	// LMRiskDB tLMRiskDB = new LMRiskDB();
	// tLMRiskDB.setRiskCode(tLCPolDB.getRiskCode());
	// tLMRiskDB.getInfo();
	//
	// Element eROW = new Element("ROW");
	//
	// eCOL1 = new Element("COL1");
	// eCOL1.setText(tLMRiskDB.getRiskName());
	// eROW.addContent(eCOL1);
	//
	// eCOL2 = new Element("COL2");
	// eCOL2.setText(String.valueOf(tLCPremSchema.getPrem()));
	// eROW.addContent(eCOL2);
	//
	// eSpec.addContent(eROW);
	// }
	// }
	//
	// // displayDocument(root);
	// }
	/**
	 * 调整批单上的财务统计信息
	 * 
	 * @param root
	 */
	public void modifyFinFee(Element root) {
		logger.debug("\nStart 调整批单上的财务统计信息\n");

		// 获取批改补退费表数据
		LJSGetEndorseDB tLJSGetEndorsementDB = new LJSGetEndorseDB();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String strSql1 = " select * from ljagetendorse where endorsementno = '"
				+ "?mNewEdorNo?" + "' order by polno, feefinatype";
		sqlbv.sql(strSql1);
		sqlbv.put("mNewEdorNo", mNewEdorNo);
		LJSGetEndorseSet tLJSGetEndorsementSet = tLJSGetEndorsementDB
				.executeQuery(sqlbv);

		if (tLJSGetEndorsementSet.size() == 0) {
			return;
		}

		Element eControl = root.getChild("CONTROL");
		Element eDisplay = eControl.getChild("DISPLAY");

		// 判断原来是否有财务信息，如果有，则删除
		if (eDisplay.getChild("displayFeeDetail") == null) {
			Element eDisplayFeeDetail = new Element("displayFeeDetail");
			eDisplayFeeDetail.setText("1");
			eDisplay.addContent(eDisplayFeeDetail);
		} else {
			root.removeChild("FeeDetail");
			root.removeChild("FeeSum");
		}

		// 描述财务头信息
		Element eFeeDetail = new Element("FeeDetail");
		Element eFeeHEAD = new Element("HEAD");

		Element eFeeCOL1 = new Element("COL1");
		eFeeCOL1.setText("RiskCode");
		eFeeHEAD.addContent(eFeeCOL1);

		Element eFeeCOL2 = new Element("COL2");
		eFeeCOL2.setText("EdorType");
		eFeeHEAD.addContent(eFeeCOL2);

		Element eFeeCOL3 = new Element("COL3");
		eFeeCOL3.setText("FeefinaType");
		eFeeHEAD.addContent(eFeeCOL3);

		Element eFeeCOL4 = new Element("COL4");
		eFeeCOL4.setText("PayMoney");
		eFeeHEAD.addContent(eFeeCOL4);

		Element eFeeCOL5 = new Element("COL5");
		eFeeCOL5.setText("GetMoney");
		eFeeHEAD.addContent(eFeeCOL5);

		eFeeDetail.addContent(eFeeHEAD);
		root.addContent(eFeeDetail);

		double grossGetMoney = 0;

		for (int j = 1; j <= tLJSGetEndorsementSet.size(); j++) {
			double getMoney = 0.0;
			double payMoney = 0.0;

			if (tLJSGetEndorsementSet.get(j).getGetFlag().compareTo("0") == 0) {
				payMoney = tLJSGetEndorsementSet.get(j).getGetMoney();
				grossGetMoney -= payMoney;
			} else {
				getMoney = tLJSGetEndorsementSet.get(j).getGetMoney();
				grossGetMoney += getMoney;
			}

			if ((getMoney == 0.0) && (payMoney == 0.0)) {
				continue;
			}

			// 获取险种名称
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			String strSql = " select * from lmriskapp where riskcode = '"
					+ "?riskcode?" + "'";
			sqlbv1.sql(strSql);
			sqlbv1.put("riskcode", tLJSGetEndorsementSet.get(j).getRiskCode());
			LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
			String strRiskName = tLMRiskAppDB.executeQuery(sqlbv1).get(1)
					.getRiskName();

			// 获取批改类型
			String edortype = tLJSGetEndorsementSet.get(j)
					.getFeeOperationType().trim();
			LMEdorItemDB tEdorItemDB = new LMEdorItemDB();

			// tEdorItemDB.setRiskCode(tLJSGetEndorsementSet.get(j).getRiskCode());

			// 区分是团险项目还是个险项目
			// modify by JL
			if (tLJSGetEndorsementSet.get(j).getGrpPolNo().equals(
					"00000000000000000000")) {
				tEdorItemDB.setAppObj("I");
			} else {
				tEdorItemDB.setAppObj("G");
			}

			tEdorItemDB.setEdorCode(edortype);

			LMEdorItemSet tItemSet = tEdorItemDB.query();
			String edorname = "unknow";

			if ((tItemSet == null) || (tItemSet.size() == 0)) {
				edorname = edortype;
			} else {
				edorname = tItemSet.get(1).getEdorName();
			}

			// 新建一行
			Element eFeeROW = new Element("ROW");

			eFeeCOL1 = new Element("COL1");
			eFeeCOL1.setText(strRiskName);
			eFeeROW.addContent(eFeeCOL1);

			eFeeCOL2 = new Element("COL2");
			eFeeCOL2.setText(edorname);
			eFeeROW.addContent(eFeeCOL2);

			eFeeCOL3 = new Element("COL3");
			eFeeCOL3.setText(getFeeFinaName(tLJSGetEndorsementSet.get(j)
					.getFeeFinaType()));
			eFeeROW.addContent(eFeeCOL3);

			eFeeCOL4 = new Element("COL4");
			eFeeCOL4.setText(String.valueOf(payMoney));
			eFeeROW.addContent(eFeeCOL4);

			eFeeCOL5 = new Element("COL5");
			eFeeCOL5.setText(String.valueOf(getMoney));
			eFeeROW.addContent(eFeeCOL5);

			eFeeDetail.addContent(eFeeROW);
		}

		// 描述财务合计
		String strGetMoneyInfo = "";

		if (grossGetMoney > 0.0) {
			strGetMoneyInfo = "应付合计："
					+ new DecimalFormat("0.00").format(grossGetMoney); // String.valueOf(grossGetMoney)
		} else {
			strGetMoneyInfo = "应交合计："
					+ new DecimalFormat("0.00").format(-grossGetMoney); // String.valueOf(-grossGetMoney)
		}

		Element eFeeSum = new Element("FeeSum");
		eFeeSum.setText(strGetMoneyInfo);
		root.addContent(eFeeSum);

		// displayDocument(root);
	}

	private String getFeeFinaName(String strFeeFinaType) {
		logger.debug("++++++++++" + strFeeFinaType);

		LDCode1DB tLDCode1DB = new LDCode1DB();
		tLDCode1DB.setCode(strFeeFinaType);

		LDCode1Set tLDCode1Set = tLDCode1DB.query();

		if ((tLDCode1Set != null) && (tLDCode1Set.size() > 0)) {
			return tLDCode1Set.get(1).getCodeAlias();
		} else {
			return strFeeFinaType;
		}
	}

	/**
	 * 为批单增加保全核保意见
	 * 
	 * @param root
	 */
	public void addUWIdeaElement(Element root) {
		LPUWMasterMainDB tLPUWMasterMainDB = new LPUWMasterMainDB();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String strSql = "select * from LPUWMasterMain where EdorNo='"
				+ "?mAppEdorNo?" + "'";
		sqlbv.sql(strSql);
		sqlbv.put("mAppEdorNo", mAppEdorNo);
		LPUWMasterMainSet tLPUWMasterMainSet = tLPUWMasterMainDB
				.executeQuery(sqlbv);

		if (tLPUWMasterMainSet.size() > 0) {
			LPUWMasterMainSchema tLPUWMasterMainSchema = tLPUWMasterMainSet
					.get(1);

			if (tLPUWMasterMainSchema.getUWIdea() != null) {
				Element eControl = root.getChild("CONTROL");
				Element eDisplay = eControl.getChild("DISPLAY");
				Element eDisplaySpec = new Element("displayUWIdea");
				eDisplaySpec.setText("1");
				eDisplay.addContent(eDisplaySpec);

				Element eSpec = new Element("UWIdea");
				eSpec.setText(tLPUWMasterMainSchema.getUWIdea());
				root.addContent(eSpec);
			}
		}

		// displayDocument(root);
	}

	/**
	 * 显示JDOM对象，用XML的方式，add by Minim at 2004-01-07
	 * 
	 * @param root
	 *            Document对象的Root节点
	 */
	public static void displayDocument(Element root) {
		num += 2;

		logger.debug("");

		for (int j = 0; j < num; j++) {
			logger.debug(" ");
		}

		logger.debug("<" + root.getName() + ">");

		List childList = root.getChildren();
		Iterator it = childList.iterator();

		while (it.hasNext()) {
			Element el = (Element) it.next();

			displayDocument(el);
		}

		if (!root.getTextTrim().equals("")) {
			logger.debug(root.getTextTrim());
			logger.debug("</" + root.getName() + ">");
		} else {
			logger.debug("");

			for (int j = 0; j < num; j++) {
				logger.debug(" ");
			}

			logger.debug("</" + root.getName() + ">");
		}

		num -= 2;

		if (num == 0) {
			logger.debug("\n");
		}
	}

	/**
	 * 显示JDOM对象，用XML的方式，add by Minim at 2004-01-07
	 * 
	 * @param root
	 *            Document对象的Root节点
	 */
	public static String getDocumentToString(Element root) {
		num += 2;

		String result = "\r\n";

		for (int j = 0; j < num; j++) {
			result = result + " ";
		}

		result = result + "<" + root.getName() + ">";

		List childList = root.getChildren();
		Iterator it = childList.iterator();

		while (it.hasNext()) {
			Element el = (Element) it.next();

			result = result + getDocumentToString(el);
		}

		if (!root.getTextTrim().equals("")) {
			result = result + root.getTextTrim();
			result = result + "</" + root.getName() + ">";
		} else {
			result = result + "\r\n";

			for (int j = 0; j < num; j++) {
				result = result + " ";
			}

			result = result + "</" + root.getName() + ">";
		}

		num -= 2;

		if (num == 0) {
			result = result + "\r\n";
		}

		return result;
	}

	/**
	 * 显示JDOM对象，用XML的方式，add by Minim at 2004-01-07
	 * 
	 * @param root
	 *            Document对象的Root节点
	 */
	public static void getDocumentToFile(Element root, PrintWriter out) {
		String result = "<?xml version=\"1.0\" encoding=\"GB2312\"?>" + "\r\n"
				+ getDocumentToString(root);
		out.write(result);
	}

	/**
	 * 获取保全批单并生成XML文件
	 * 
	 * @param fileName
	 * @param edorNo
	 */
	public static String getBqPrintToXml(String edorNo) throws Exception {
		Connection conn = DBConnPool.getConnection();

		try {
			// 从数据库的Blob字段中取出申请书或批单的内容
			COracleBlob blob = new COracleBlob();
			CMySQLBlob tCMySQLBlob = new CMySQLBlob();
			logger.debug("and edorno=" + edorNo);
			Blob b  = null;
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				
				 b = blob.SelectBlob("LPEdorPrint", "edorinfo", " and edorno='"
						+ edorNo + "' ", conn);
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				 b = tCMySQLBlob.SelectBlob("LPEdorPrint", "edorinfo", " and edorno='"
						+ edorNo + "' ", conn);
			}
			// XmlFun.displayBlob(b);
			InputStream in = b.getBinaryStream();
			// BufferedReader brin = new BufferedReader(new InputStreamReader(in));

			DOMBuilder myBuilder = new DOMBuilder();
			Document myDocument = myBuilder.build(in);

			// String s = "";
			//
			// while((s = brin.readLine()) != null) {
			// result = result + s + "\n";
			// }
			String result = changeXml.getDocumentToString(myDocument
					.getRootElement());
			// logger.debug(result);

			// byte[] arr = result.getBytes();
			//
			// ByteArrayInputStream in2 = new ByteArrayInputStream(result.getBytes());
			// DOMBuilder myBuilder2 = new DOMBuilder();
			// Document myDocument2 = myBuilder.build(in2);
			//
			// changeXml.displayDocument(myDocument2.getRootElement());

			// 转换JavaScript解析不了的特殊字符，失败了：（
			// char[] arr = result.toCharArray();
			// result = "";
			// for (int i=0; i<arr.length; i++) {
			// if (arr[i]=='"' || arr[i]=='\'' || arr[i]=='\r' || arr[i]=='\n')
			// {
			// result = result + "\\";
			// }
			//
			// result = result + arr[i];
			// }
			conn.close();

			return "<?xml version=\"1.0\" encoding=\"GB2312\"?>" + "\r\n"
					+ result;
		} catch (Exception ex) {
			ex.printStackTrace();

			try {
				conn.rollback();
			} catch (Exception ex1) {
			}

			throw ex;
		}
	}

	/**
	 * 将XML文件的内容写入到保全打印表的blob字段中
	 * 
	 * @param fileName
	 * @param edorNo
	 * @param manageCom
	 * @param operator
	 */
	public static void setXmlToBqPrint2(String content, String edorNo,
			String manageCom, String operator) throws Exception {
		Connection conn = DBConnPool.getConnection();

		try {
			// 将文件生成XML
			content = StrTool.GBKToUnicode(content);

			// byte[] arr = content.getBytes();

			ByteArrayInputStream in = new ByteArrayInputStream(content
					.getBytes());
			DOMBuilder myBuilder = new DOMBuilder();
			Document myDocument = myBuilder.build(in);

			// changeXml.displayDocument(myDocument.getRootElement());

			COracleBlob tCOracleBlob = new COracleBlob();
			CMySQLBlob tCMySQLBlob = new CMySQLBlob();
			
			// 复用了changeXml中的插入Blob字段的部分
			LPEdorPrintSchema tLPEdorPrintSchema = new LPEdorPrintSchema();
			tLPEdorPrintSchema.setEdorNo(edorNo);
			tLPEdorPrintSchema.setManageCom(manageCom);
			tLPEdorPrintSchema.setPrtFlag("N");
			tLPEdorPrintSchema.setPrtTimes(0);
			tLPEdorPrintSchema.setMakeDate(PubFun.getCurrentDate());
			tLPEdorPrintSchema.setMakeTime(PubFun.getCurrentTime());
			tLPEdorPrintSchema.setOperator(operator);
			tLPEdorPrintSchema.setModifyDate(PubFun.getCurrentDate());
			tLPEdorPrintSchema.setModifyTime(PubFun.getCurrentTime());

			conn.setAutoCommit(false);
			String szSQL = "delete from LPEdorPrint where EdorNo='"	+ tLPEdorPrintSchema.getEdorNo() + "'";
			logger.debug("----sql:" + szSQL);
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				if (!tCOracleBlob.DeleteBlobRecord(szSQL, conn)) {
					conn.rollback();
					throw new Exception("删除数据失败！" + szSQL);
				}
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				if (!tCMySQLBlob.DeleteBlobRecord(szSQL, conn)) {
					conn.rollback();
					throw new Exception("删除数据失败！" + szSQL);
				}		
			}

			szSQL = "INSERT INTO LPEdorPrint VALUES ('"
					+ tLPEdorPrintSchema.getEdorNo() + "','"
					+ tLPEdorPrintSchema.getPrtFlag() + "','"
					+ tLPEdorPrintSchema.getPrtTimes() + "','"
					+ tLPEdorPrintSchema.getManageCom() + "','"
					+ tLPEdorPrintSchema.getOperator() + "','"
					+ tLPEdorPrintSchema.getMakeDate() + "','"
					+ tLPEdorPrintSchema.getMakeTime() + "','"
					+ tLPEdorPrintSchema.getModifyDate() + "','"
					+ tLPEdorPrintSchema.getModifyTime() + "',empty_blob())";
			logger.debug("----sql:" + szSQL);
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				if (!tCOracleBlob.InsertBlankBlobRecord(szSQL, conn)) {
					conn.rollback();
					throw new Exception("插入数据失败！" + szSQL);
				}
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				if (!tCMySQLBlob.InsertBlankBlobRecord(szSQL, conn)) {
					conn.rollback();
					throw new Exception("插入数据失败！" + szSQL);
				}		
			}
			szSQL = " and EdorNo = '" + tLPEdorPrintSchema.getEdorNo() + "' ";
			logger.debug("----sql:" + szSQL);

			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				if (!tCOracleBlob.UpdateBlob(myDocument, "LPEdorPrint", "EdorInfo",
						szSQL, conn)) {
					conn.rollback();
					throw new Exception("修改数据失败！" + szSQL);
				}
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				if (!tCMySQLBlob.UpdateBlob(myDocument, "LPEdorPrint", "EdorInfo",
						szSQL, conn)) {
					conn.rollback();
					throw new Exception("修改数据失败！" + szSQL);
				}	
			}
			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();

			try {
				conn.rollback();
			} catch (Exception ex1) {
			}

			throw ex;
		}
	}

	/**
	 * 获取保全批单并生成XML文件
	 * 
	 * @param fileName
	 * @param edorNo
	 */
	public static void getBqPrintToXml(String fileName, String edorNo)
			throws Exception {
		Connection conn = DBConnPool.getConnection();
		PrintWriter out = null;
		InputStream in = null;

		try {
			// 从数据库的Blob字段中取出申请书或批单的内容
			COracleBlob blob = new COracleBlob();
			Blob b = blob.SelectBlob("LPEdorPrint", "edorinfo", " and edorno='"
					+ edorNo + "' ", conn);

			XmlFun.displayBlob(b);

			in = b.getBinaryStream();

			BufferedReader brin = new BufferedReader(new InputStreamReader(in));

			logger.debug("Start creating file ......");
			logger.debug("File Name : " + fileName);

			out = new PrintWriter(new FileWriter(new File(fileName)), true);

			DOMBuilder myBuilder = new DOMBuilder();
			Document myDocument = myBuilder.build(in);
			changeXml.getDocumentToFile(myDocument.getRootElement(), out);

			// String s = "";
			// while((s = brin.readLine()) != null) {
			// out.println(s);
			// }
			out.close();
			in.close();
			logger.debug("End creating file ......");
		} catch (Exception ex) {
			ex.printStackTrace();

			try {
				conn.rollback();
			} catch (Exception ex1) {
			}

			out.close();
			in.close();
			throw ex;
		}
	}

	/**
	 * 将XML文件的内容写入到保全打印表的blob字段中
	 * 
	 * @param fileName
	 * @param edorNo
	 * @param manageCom
	 * @param operator
	 */
	public static void setXmlToBqPrint(String fileName, String edorNo,
			String manageCom, String operator) throws Exception {
		Connection conn = DBConnPool.getConnection();

		try {
			// 将文件生成XML
			// File f = new
			// File("E:\\lis\\ui\\bank\\SendToBankFile\\B0101S00000000000000000096(2003-06-24).xml");
File f = new File(fileName);
			DOMBuilder myBuilder = new DOMBuilder();
			Document myDocument = myBuilder.build(f);

			// changeXml.displayDocument(myDocument.getRootElement());

			COracleBlob tCOracleBlob = new COracleBlob();
			CMySQLBlob tCMySQLBlob = new CMySQLBlob();
			
			// 复用了changeXml中的插入Blob字段的部分
			LPEdorPrintSchema tLPEdorPrintSchema = new LPEdorPrintSchema();
			tLPEdorPrintSchema.setEdorNo(edorNo);
			tLPEdorPrintSchema.setManageCom(manageCom);
			tLPEdorPrintSchema.setPrtFlag("N");
			tLPEdorPrintSchema.setPrtTimes(0);
			tLPEdorPrintSchema.setMakeDate(PubFun.getCurrentDate());
			tLPEdorPrintSchema.setMakeTime(PubFun.getCurrentTime());
			tLPEdorPrintSchema.setOperator(operator);
			tLPEdorPrintSchema.setModifyDate(PubFun.getCurrentDate());
			tLPEdorPrintSchema.setModifyTime(PubFun.getCurrentTime());

			conn.setAutoCommit(false);

			String szSQL = "delete from LPEdorPrint where EdorNo='"
					+ tLPEdorPrintSchema.getEdorNo() + "'";

			logger.debug("----sql:" + szSQL);

			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				if (!tCOracleBlob.DeleteBlobRecord(szSQL, conn)) {
					conn.rollback();
					throw new Exception("删除数据失败！" + szSQL);
				}
				
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				if (!tCMySQLBlob.DeleteBlobRecord(szSQL, conn)) {
					conn.rollback();
					throw new Exception("删除数据失败！" + szSQL);
				}
		
			}
			szSQL = "INSERT INTO LPEdorPrint VALUES ('"
					+ tLPEdorPrintSchema.getEdorNo() + "','"
					+ tLPEdorPrintSchema.getPrtFlag() + "','"
					+ tLPEdorPrintSchema.getPrtTimes() + "','"
					+ tLPEdorPrintSchema.getManageCom() + "','"
					+ tLPEdorPrintSchema.getOperator() + "','"
					+ tLPEdorPrintSchema.getMakeDate() + "','"
					+ tLPEdorPrintSchema.getMakeTime() + "','"
					+ tLPEdorPrintSchema.getModifyDate() + "','"
					+ tLPEdorPrintSchema.getModifyTime() + "',empty_blob())";
			logger.debug("----sql:" + szSQL);

			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				if (!tCOracleBlob.InsertBlankBlobRecord(szSQL, conn)) {
					conn.rollback();
					throw new Exception("插入数据失败！" + szSQL);
				}
							
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					if (!tCMySQLBlob.InsertBlankBlobRecord(szSQL, conn)) {
					conn.rollback();
					throw new Exception("插入数据失败！" + szSQL);
				}
			}

			szSQL = " and EdorNo = '" + tLPEdorPrintSchema.getEdorNo() + "' ";

			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				if (!tCOracleBlob.UpdateBlob(myDocument, "LPEdorPrint", "EdorInfo",
						szSQL, conn)) {
					conn.rollback();
					throw new Exception("修改数据失败！" + szSQL);
				}
				
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					if (!tCMySQLBlob.UpdateBlob(myDocument, "LPEdorPrint", "EdorInfo",
						szSQL, conn)) {
					conn.rollback();
					throw new Exception("修改数据失败！" + szSQL);
				}	
			}
			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();

			try {
				conn.rollback();
			} catch (Exception ex1) {
			}

			throw ex;
		}
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		tGlobalInput.ComCode = "86";

		changeXml tChangeXml = new changeXml("410110000000070",
				"440000000000007", tGlobalInput);
		try {
			tChangeXml.setXmlToBqPrint("c:\\bb.xml", "440000000000007",
					tGlobalInput.ManageCom, tGlobalInput.Operator);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// if (!tChangeXml.changeGrpEdor()) {
		// logger.debug("changexml failure");
		// }
		// VData vdata = tChangeXml.getResult();
		// PubSubmit ps = new PubSubmit();
		// if (ps.submitData(vdata, "")) {
		// logger.debug("succeed in pubsubmit");
		// }

	}
}

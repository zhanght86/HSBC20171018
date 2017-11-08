package com.sinosoft.lis.f1print;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.f1j.ss.BookModelImpl;
import com.f1j.util.F1Exception;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author 裴真
 * function :print InputEfficiency or print CheckEfficiency Business Logic layer
 * @version 1.0
 * @date 2004-04-06
 */

public class BodyCheckBillBL {
private static Logger logger = Logger.getLogger(BodyCheckBillBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strMngCom;
	private String comGrade = "";
	SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
	private String strSQL;
	private String strQGrpProposalNo;
	private String strAgentCode;
	private String nodeName = "";
	Vector finalAeon = new Vector();
	Vector departmentBin = new Vector();
	Vector groupBin = new Vector();
	Vector tryNewWay = new Vector();
	private int sumBills = 0;
	private int areaNo = 0;
	private int sumBillCom = 0;
	String[] nodeStore = new String[9];
	private XmlExport newXmlExport = new XmlExport();
	private int getJ = 1;
	private GlobalInput mGlobalInput = new GlobalInput();
	private int nodeNo = 0;
	private String strSaleCh = "0";
	private String saleChannel = "0";
	private String subtype = "";
	private String strRiskT = "";
	private String twebpath = "";

	public BodyCheckBillBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();

		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	/***************************************************************************
	 * Name : getInputData() function :receive data from jsp and check date
	 * check :judge whether startdate begin enddate return :true or false
	 */
	private boolean getInputData(VData cInputData) {
		strMngCom = (String) cInputData.get(0);
		strQGrpProposalNo = (String) cInputData.get(1);
		strAgentCode = (String) cInputData.get(2);
		twebpath = (String) cInputData.get(3);

		for (int i = 0; i < cInputData.size(); i++) {
			logger.debug("&&&" + cInputData.get(i).toString());
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	/***************************************************************************
	 * name : buildError function : Prompt error message return :error message
	 */

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "BodyCheckBillBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/***************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	private boolean getPrintData() {

		ExeSQL exeSql = new ExeSQL();
		String strTemplatePath = twebpath;
		ExeSQL searchOneSQL = new ExeSQL();
		SSRS searchOneSsrs = new SSRS();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql("Select substr(Comgrade,2,1) From ldcom Where comcode='"
				+ "?strMngCom?" + "'");
		sqlbv.put("strMngCom", strMngCom);
		searchOneSsrs = searchOneSQL.execSQL(sqlbv);
		
		int errd = searchOneSsrs.getMaxRow();
		if (errd == 0) {
			buildError("getprintData", "机构不存在");
			logger.debug("机构不存在");
			return false;

		}
		comGrade = searchOneSsrs.GetText(1, 1);
		if (comGrade.equals("1")) // 总公司
		{
			buildError("getprintData", "只能打印分公司、中心支公司或者营销服务部的清单");
			logger.debug("只能打印分公司、中心支公司或者营销服务部的清单");
			return false;
		} else {
			// 查询已经打印体检通知书，但是还没有回销（回收）的通知书
			strSQL = "Select a.grpcontno,a.appname,"
					+ " (select d.customerseqno from lcinsured d where d.grpcontno=a.grpcontno and d.insuredno=a.customerno),"
					+ " (select l.name from lcinsured l where l.grpcontno=a.grpcontno and l.insuredno=a.customerno),"
					+ " a.PrtSeq,a.agentcode,a.makedate"
					+ " From lcpenotice a,lzsyscertify c"
					+ " Where 1=1"
					+ " And  trim(a.Prtseq)=c.certifyno And c.certifycode = '0G03' And stateflag = '0'";
			if ((strQGrpProposalNo != null)
					&& !strQGrpProposalNo.trim().equals("")) {
				strSQL = strSQL + " and a.grpcontno ='" + "?strQGrpProposalNo?"
						+ "'";
			}
			if ((strAgentCode != null) && !strAgentCode.trim().equals("")) {
				strSQL = strSQL + " and a.agentcode ='" + "?strAgentCode?" + "'";
			}
			strSQL = strSQL + " order by a.grpcontno,a.agentcode";
		}
		sqlbv1.sql(strSQL);
		sqlbv1.put("strQGrpProposalNo", strQGrpProposalNo);
		sqlbv1.put("strAgentCode", strAgentCode);

		ExeSQL exeSQL = new ExeSQL();
		SSRS ssrs = exeSql.execSQL(sqlbv1);
		int i_count = ssrs.getMaxRow();

		if (i_count == 0) {
			logger.debug("没有可打印的信息");
			buildError("getprintData", "没有需要打印的信息");
			return false;
		}
		int areaNoBefore = 0;
		int areaNoAfter = 0;
		areaNo = 1;

		/*
		 * for (int i = 1; i < i_count; i++) { try { areaNoBefore =
		 * Integer.parseInt(ssrs.GetText(i, 4)); areaNoAfter =
		 * Integer.parseInt(ssrs.GetText(i + 1, 4));
		 * 
		 * if (areaNoBefore != areaNoAfter) { areaNo++; } } catch (Exception ex) {
		 * buildError("getprintData", "后台有脏数据！！"); logger.debug("后台有脏数据");
		 * return false;
		 *  }
		 *  }
		 */
		int countk = -1;
		String[] strVFPathName = new String[areaNo]; // 临时文件个数。
		// newXmlExport = new XmlExport();
		// 新建一个XmlExport的实例
		String remark = "newBodyCheckBill.vts";
		newXmlExport.createDocuments(remark, mGlobalInput);

		String newContno = "";// 投保单号
		String newAgentCode = "";// 业务员号
		String newPrtDate = "";
		newPrtDate = PubFun.getCurrentDate();

		boolean display = false;
		for (int k = 0; k < areaNo; k++) {
			countk = countk + 1;
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("BodyCheckBill.vts", "printer");
			TextTag texttag = new TextTag();
			ListTable alistTable = new ListTable();
			alistTable.setName("RISK");
			sumBills = 0;
			// Appoint to a sepcial flag

			int numberCount = 1;
			for (int j = getJ; j < i_count + 1; j++) {
				newAgentCode = ssrs.GetText(j, 6).trim();
				logger.debug(newAgentCode);
				try {
					String[] cols = new String[8];
					cols[0] = numberCount + "";
					cols[1] = ssrs.GetText(j, 1);
					cols[2] = ssrs.GetText(j, 2);
					cols[3] = ssrs.GetText(j, 3);
					cols[4] = ssrs.GetText(j, 4);
					cols[5] = ssrs.GetText(j, 5);
					cols[6] = ssrs.GetText(j, 6);
					cols[7] = ssrs.GetText(j, 7);
					alistTable.add(cols);
					logger.debug("取数成功" + cols[0] + " " + cols[1] + " "
							+ cols[2] + "  " + cols[3] + "  " + cols[4] + "  "
							+ cols[5] + " " + cols[6] + " " + cols[7]);

				} catch (Exception ex) {
					buildError("getprintData", "没有需要打印的信息");
					logger.debug("取数失败");
					return false;
				}
				numberCount++;
			}

			String[] col = new String[1];

			// logger.debug("====="+alistTable.size());
			xmlexport.addListTable(alistTable, col);
			if (k == 0) {

				xmlexport.addDisplayControl("displayTitle1");

			}
			if (k == areaNo - 1) {
				xmlexport.addDisplayControl("displayTitle2");

			}
			display = false;
			texttag.add("AgentCode", newAgentCode);
			texttag.add("PrtDate", newPrtDate);
			texttag.add("SumAll", i_count); // 总件数

			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}

			CombineVts tcombineVts = null;
			tcombineVts = new CombineVts(xmlexport.getInputStream(),
					strTemplatePath);

			ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			tcombineVts.output(dataStream);
			strVFPathName[countk] = strTemplatePath + countk
					+ "BodyCheckBill.vts";
			logger.debug(strVFPathName[0]);
			// 把dataStream存储到磁盘文件
			// logger.debug("存储文件到"+strVFPathName);
			AccessVtsFile.saveToFile(dataStream, strVFPathName[countk]);

			// 把所有xml文件拼成一个xml文件。
			newXmlExport.addDataSet(
					newXmlExport.getDocument().getRootElement(), xmlexport
							.getDocument().getRootElement());

		}
		VtsFileCombine vtsfilecombine = new VtsFileCombine();

		String[] strNewVFPathName = new String[countk + 1];
		for (int b = 0; b < countk + 1; b++) {
			strNewVFPathName[b] = strVFPathName[b];
		}
		try {
			BookModelImpl tb = vtsfilecombine.dataCombine(strNewVFPathName);
			vtsfilecombine.write(tb, strTemplatePath + "newBodyCheckBill.vts");
		} catch (IOException ex) {
		} catch (F1Exception ex) {
		} catch (Exception ex) {
			buildError("getprintData", "没有需要打印的信息");
			logger.debug("3没有需要打印的信息");
			return false;
		}

		// newXmlExport.outputDocumentToFile("d:\\", "BodyCheckBil");
		// //输出xml文档到文件
		mResult.clear();
		mResult.addElement(strTemplatePath + "newBodyCheckBill.vts");
		mResult.addElement(newXmlExport);
		return true;

	}

	/*
	 * @function :in order to debug
	 */
	public static void main(String[] args) {

	}
}

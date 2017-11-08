package com.sinosoft.lis.f1print;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author 刘岩松
 * function :print InputEfficiency or print CheckEfficiency Business Logic layer
 * @version 1.0
 * @date 2003-04-04
 */

public class BankAgentBillBL implements PrintService {
private static Logger logger = Logger.getLogger(BankAgentBillBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private int printLisNo = 0;

	private int intTotalQ = 0;
	private int intComTotalQ = 0;
	private int flag = 0;
	private int districtNo = 1;
	private int subComNo = 1;
	private String strSQL = "";
	Vector tryNewWay = new Vector();
	private String strLength = "";
	private String strConNoSet = "";
	private String prtSeq = "";
	private String strManageCom = "";
	private GlobalInput mGlobalInput = new GlobalInput();
	private String subCom = "";
	private String districtCode = "";
	private String comName = "";
	private String strIssueDate = PubFun.getCurrentDate();
	private String districtName = "";
	private XmlExport xmlexport;
	private XmlExport newXmlExport;

	public BankAgentBillBL() {
	}

	public CErrors getErrors() {
		return this.mErrors;
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
		strLength = (String) cInputData.get(0);
		strConNoSet = (String) cInputData.get(1);
		mGlobalInput = (GlobalInput) cInputData.get(2);
		prtSeq = (String) cInputData.get(3);
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
		cError.moduleName = "PersonBillBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private String getContNo(String[] str) {
		String[] tempStr = str;
		String output = "";

		String gaOne = " a.contNo=";
		String gaTwo = "'";
		String gaThree = "or";

		for (int i = 0; i < str.length; i++) {
			if (i == 0) {
				output = output + gaOne + gaTwo + str[i] + gaTwo;
			}
			if (i > 0) {
				output = output + gaThree + gaOne + gaTwo + str[i] + gaTwo;

			}

		}
		// logger.debug("历史性时刻conNo "+output+" "+str.length);
		return output;
	}

	/***************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	private boolean getPrintData() {

		// 选择临时文件读取目录
		ExeSQL exeSql = new ExeSQL();
		int intLength = Integer.parseInt(strLength);

		String[] conNoSet = new String[intLength];
		conNoSet = strConNoSet.split(":", intLength);

		strManageCom = mGlobalInput.ManageCom;
		// 创建公司各层机构列

		int countk = -1;
		newXmlExport = new XmlExport();
		xmlexport = null; // 新建一个XmlExport的实例
		String remark = "BankAgentBill.vts";

		newXmlExport.createDocuments(remark, mGlobalInput); // 最好紧接着就初始化xml文档
		newXmlExport.setTemplateName("BankAgentBill.vts");

		// String sCn = getContNo(conNoSet);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			strSQL = "SELECT  a.proposalcontno,a.contno,a.appntname,a.insuredname,a.prem,e.name, "
					+ " a.agentcode,b.Name,"
					+ "(select case c.branchlevel when '52' then c.name when '51' then (select la.name from labranchgroup la where la.agentgroup = c.upbranch) end from ldsysvar where sysvar = 'onerow') branchname"
					+ ",e.agentcom,to_char(a.signdate,'yyyy-mm-dd'),"
					+ " (Select comcode From ldcom where a.managecom like concat('',concat(trim(comcode),'%')) and comgrade = '02' and rownum = 1) subcom,"
					+ " (Select Name From ldcom where a.managecom like concat('',concat(trim(comcode),'%')) and comgrade = '02' and rownum = 1) subcomname, "
					+ " (select comcode from ldcom where a.managecom like concat('',concat(trim(comcode),'%')) and comgrade = '03' and rownum = 1) subcenter, "
					+ " (select name from ldcom where a.managecom like concat('',concat(trim(comcode),'%')) and comgrade = '03' and rownum = 1) subcentername, "
					+ " (select f.agentcom from lacom f,lacom g where f.banktype = '00' and g.agentcom like concat(concat(trim(f.agentcom),'%')) and  g.agentcom = e.agentcom and rownum = 1) agentcom1,"
					+ " (select f.name from lacom f,lacom g where g.agentcom like concat('',concat(trim(f.agentcom),'%')) and f.banktype = '00' and g.agentcom = e.agentcom and rownum = 1) agentccomname1 ,"
					+ "g.riskcode,a.PolApplyDate,h.Mobile,h.PostalAddress,h.companyphone,"
					+ "(select case c.branchlevel when '52' then c.branchattr when '51' then (select la.branchattr from labranchgroup la where la.agentgroup = c.upbranch) end  from ldsysvar where sysvar = 'onerow') branchattr,"
					+ "l.invoiceno "
					+ " from lccont a,laagent b,LABranchGroup c,Lacom e,Lcpol g,Lcaddress h,Lcappnt f,ldcontinvoicemap l where l.batchno = '"
					+ "?prtSeq?"
					+ "' and a.contno = l.contno "
					+ " and b.agentcode = trim(a.agentcode)"
					+ " And e.agentcom= a.agentcom "
					+ " And c.agentgroup = b.branchcode And a.contno=g.contno and g.PolNo=g.MainPolNo And a.contno=f.contno  And f.AppntNo=h.CustomerNo And f.AddressNo=h.AddressNo"
					+ " order by subcom,subcenter,a.managecom,agentcom1,branchattr,a.agentcode,a.prtno";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			strSQL = "SELECT  a.proposalcontno,a.contno,a.appntname,a.insuredname,a.prem,e.name, "
					+ " a.agentcode,b.Name,"
					+ "(select case c.branchlevel when '52' then c.name when '51' then (select la.name from labranchgroup la where la.agentgroup = c.upbranch) end  from ldsysvar where sysvar = 'onerow') branchname"
					+ ",e.agentcom,to_char(a.signdate,'yyyy-mm-dd'),"
					+ " (Select comcode From ldcom where a.managecom like concat('',concat(trim(comcode),'%')) and comgrade = '02'  limit 0,1) subcom,"
					+ " (Select Name From ldcom where a.managecom like concat('',concat(trim(comcode),'%')) and comgrade = '02'  limit 0,1) subcomname, "
					+ " (select comcode from ldcom where a.managecom like concat('',concat(trim(comcode),'%')) and comgrade = '03'  limit 0,1) subcenter, "
					+ " (select name from ldcom where a.managecom like concat('',concat(trim(comcode),'%')) and comgrade = '03'  limit 0,1) subcentername, "
					+ " (select f.agentcom from lacom f,lacom g where f.banktype = '00' and g.agentcom like concat(concat(trim(f.agentcom),'%')) and  g.agentcom = e.agentcom  limit 0,1) agentcom1,"
					+ " (select f.name from lacom f,lacom g where g.agentcom like concat('',concat(trim(f.agentcom),'%')) and f.banktype = '00' and g.agentcom = e.agentcom  limit 0,1) agentccomname1 ,"
					+ "g.riskcode,a.PolApplyDate,h.Mobile,h.PostalAddress,h.companyphone,"
					+ "(select case c.branchlevel when '52' then c.branchattr when '51' then (select la.branchattr from labranchgroup la where la.agentgroup = c.upbranch) end  from ldsysvar where sysvar = 'onerow') branchattr,"
					+ "l.invoiceno "
					+ " from lccont a,laagent b,LABranchGroup c,Lacom e,Lcpol g,Lcaddress h,Lcappnt f,ldcontinvoicemap l where l.batchno = '"
					+ "?prtSeq?"
					+ "' and a.contno = l.contno "
					+ " and b.agentcode = trim(a.agentcode)"
					+ " And e.agentcom= a.agentcom "
					+ " And c.agentgroup = b.branchcode And a.contno=g.contno and g.PolNo=g.MainPolNo And a.contno=f.contno  And f.AppntNo=h.CustomerNo And f.AddressNo=h.AddressNo"
					+ " order by subcom,subcenter,a.managecom,agentcom1,branchattr,a.agentcode,a.prtno";
		}
		sqlbv.sql(strSQL);
		sqlbv.put("prtSeq", prtSeq);
		try {
			SSRS ssrs = exeSql.execSQL(sqlbv);
			int i_count = ssrs.getMaxRow();
			if (i_count == 0) {
				this.buildError("getPrintData", "没有查询到相关数据！");
				return false;
			}
			// logger.debug("i_count is "+i_count);
			intTotalQ = 0;
			// 设置公司信息

			countk = countk + 1;
			TextTag texttag = new TextTag(); // Create a TextTag instance
			// Create a XmlExport instance

			// xmlexport.createDocument(remark, ""); //最好紧接着就初始化xml文档
			ListTable alistTable = new ListTable();

			String oldBank = ""; // 区
			String oldBankName = "";
			String newBank = "";
			String newBankName = "";
			String oldSubCom = ""; // 支公司
			String oldSubComName = "";
			String newSubCom = "";
			String newSubComName = "";
			String oldMagName = ""; // 分公司
			String newMagName = "";
			String oldMagCom = "";
			String newMagCom = "";

			for (int i = 1; i <= i_count; i++) {
				newBank = ssrs.GetText(i, 16).trim();
				newBankName = ssrs.GetText(i, 17).trim();
				newMagCom = ssrs.GetText(i, 12).trim();
				newSubCom = ssrs.GetText(i, 14).trim();
				newMagName = ssrs.GetText(i, 13).trim();
				newSubComName = ssrs.GetText(i, 15).trim();
				intTotalQ++;
				intComTotalQ++;

				if (!newBank.equals(oldBank)) // 银行总数
				{
					texttag = new TextTag();
					if (xmlexport != null) {
						if (!newSubCom.equals(oldSubCom)) // 中支公司总数
						{
							texttag.add("SumBillCom", intComTotalQ - 1);
							intComTotalQ = 1;
							xmlexport.addDisplayControl("displayTotal2");
						}
						texttag.add("SumBills", intTotalQ - 1);
						texttag.add("BankName", oldBankName);
						texttag.add("ManageComName", oldMagName);
						texttag.add("ManageComName1", oldSubComName);
						intTotalQ = 1;
						String[] col = new String[16];

						xmlexport.addTextTag(texttag);
						xmlexport.addDisplayControl("displayTotal1");
						xmlexport.addListTable(alistTable, col);
						newXmlExport.addDataSet(xmlexport.getDocument()
								.getRootElement());
					}
					xmlexport = new XmlExport();
					xmlexport.createDocument("BankAgentBill.vts", "printer");
					if (i == 1) // 加标题，日期，总数
					{
						texttag.add("SysDate", strIssueDate);
						texttag.add("SumAll", i_count);
						texttag.add("PrtSeq", prtSeq);
						xmlexport.addTextTag(texttag);
						xmlexport.addDisplayControl("displayTitle");
					}
					alistTable = new ListTable();
					alistTable.setName("BILL");
				}
				ExeSQL exeSqlPM = new ExeSQL();
				String[] cols = new String[16];
				cols[0] = i + "";
				cols[1] = ssrs.GetText(i, 1);
				cols[2] = ssrs.GetText(i, 2);
				cols[3] = ssrs.GetText(i, 3);
				cols[4] = ssrs.GetText(i, 4);
				cols[5] = ssrs.GetText(i, 5);
				cols[6] = ssrs.GetText(i, 6);
				cols[7] = ssrs.GetText(i, 7);
				cols[8] = ssrs.GetText(i, 8);
				cols[9] = ssrs.GetText(i, 9);
				cols[10] = ssrs.GetText(i, 11);
				cols[11] = ssrs.GetText(i, 18);
				cols[12] = ssrs.GetText(i, 19);
				if (ssrs.GetText(i, 20) == null
						|| ssrs.GetText(i, 20).equals("")) {
					if (ssrs.GetText(i, 22) != null
							|| !ssrs.GetText(i, 20).equals(""))
						cols[13] = ssrs.GetText(i, 22);
					else {
						cols[13] = "";
					}
				} else
					cols[13] = ssrs.GetText(i, 20);
				cols[14] = ssrs.GetText(i, 21);
				String InvoNo = ssrs.GetText(i, 24);
				if (InvoNo.length() >= 12) {
					cols[15] = InvoNo.substring(0, 12);
					logger.debug(cols[10]);
				} else {
					cols[15] = InvoNo;
				}

				alistTable.add(cols);
				oldBank = newBank;
				oldSubCom = newSubCom;
				oldMagName = newMagName;
				oldSubComName = newSubComName;
				oldMagCom = newMagCom;
				oldBankName = newBankName;
			}
			texttag = new TextTag();
			xmlexport.addDisplayControl("displayTotal1");
			xmlexport.addDisplayControl("displayTotal2");
			texttag.add("SumBillCom", intComTotalQ);
			texttag.add("SumBills", intTotalQ);
			texttag.add("BankName", oldBankName);
			texttag.add("ManageComName", oldMagName);
			texttag.add("ManageComName1", oldSubComName);

			String[] col = new String[16];
			xmlexport.addTextTag(texttag);
			xmlexport.addListTable(alistTable, col);
			newXmlExport.addDataSet(xmlexport.getDocument().getRootElement());
			logger.debug("清单打印成功！");
			// newXmlExport.outputDocumentToFile("d:\\",
			// "BankAgentBillSucc"+PubFun.getCurrentTime().toString().replaceAll(":","_"));
			// //输出xml文档到文件
			mResult.addElement(newXmlExport);
		} catch (Exception ex) {
			this.buildError("getPrintData", "打印清分清单出现错误！");
			mResult.addElement(newXmlExport);
			return false;
		}
		return true;
	}

	/*
	 * @function :in order to debug
	 */
	// 容易出问题的地方保单的agentgroup 最多只有两个上级部门, 这样的保单会丢失。
	public static void main(String[] args) {
		String tStrLength = "2";
		String tContNoSet = "3534535345:000020792544"; // String tContNoSet =
														// "2301190000000759";
		VData tVData = new VData();
		GlobalInput tg = new GlobalInput();
		tg.ManageCom = "8632";
		tg.ComCode = "86110000";
		tg.Operator = "110001";
		tVData.addElement(tStrLength);
		tVData.addElement(tContNoSet);
		tVData.addElement(tg);
		tVData.addElement("83210004340");

		BankAgentBillBL tPersonBillBL = new BankAgentBillBL();
		tPersonBillBL.submitData(tVData, "PRINT");
	}
}

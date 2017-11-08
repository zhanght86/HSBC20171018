package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
import java.util.Vector;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class GrpBillBL implements PrintService {
private static Logger logger = Logger.getLogger(GrpBillBL.class);
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
	private String SignDate = "";

	private String strManageCom = "";
	private GlobalInput mGlobalInput = new GlobalInput();
	private String subCom = "";
	private String districtCode = "";
	private String comName = "";
	private String strIssueDate = PubFun.getCurrentDate();
	private String districtName = "";
	private XmlExport xmlexport;
	private XmlExport newXmlExport;

	public GrpBillBL() {
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
		cError.moduleName = "GrpBillBL";
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
		// int intLength = Integer.parseInt(strLength);
		//
		// String[] conNoSet = new String[intLength];
		// conNoSet = strConNoSet.split(":", intLength);

		strManageCom = mGlobalInput.ManageCom;
		// 创建公司各层机构列

		logger.debug("flag=" + flag);
		logger.debug("分公司=" + subCom);
		logger.debug("支公司=" + comName);

		int countk = -1;
		newXmlExport = new XmlExport();
		xmlexport = null; // 新建一个XmlExport的实例
		String remark = "GrpBill.vts";

		newXmlExport.createDocuments(remark, mGlobalInput); // 最好紧接着就初始化xml文档
		newXmlExport.setTemplateName("GrpBill.vts");

		// String sCn = getContNo(conNoSet);

		strSQL =
		// "select (select max(g.tempfeeno) from ljtempfeeclass g where g.othernotype =
		// '6'"
		// + " and g.otherno = a.contno and paymoney > 0 and managecom is not
		// null),a.proposalcontno,a.contno,a.appntname,a.insuredname,a.prem, "
		"select '',a.proposalgrpcontno,a.grpcontno,a.grpname,'',a.prem, "
				+ " a.agentcode,d.Name,h.name,d.branchcode,to_char(a.signdate,'yyyy-mm-dd'),h.branchattr,"
				+ " (Select comcode From ldcom where a.managecom like trim(comcode)||'%' and comgrade = '02' and rownum = 1) subcom,"
				+ " (Select Name From ldcom where a.managecom like trim(comcode)||'%' and comgrade = '02' and rownum = 1) subcomname,"
				+ " (select comcode from ldcom where a.managecom like trim(comcode)||'%' and comgrade = '03' and rownum = 1) subcenter,"
				+ " (select name from ldcom where a.managecom like trim(comcode)||'%' and comgrade = '03' and rownum = 1) subcentername,"
				+ " (select b.branchattr from labranchgroup b,labranchgroup c"
				+ " where b.agentgroup = c.upbranch and c.agentgroup = h.upbranch) branchattr1, (select b.name from labranchgroup b,labranchgroup c"
				+ " where b.agentgroup = c.upbranch and c.agentgroup = h.upbranch) branchattrname1,"
				+ " (select e.branchattr from labranchgroup e where e.agentgroup = h.upbranch) branchattr2,"
				+ " (select e.name from labranchgroup e where e.agentgroup = h.upbranch) branchattrname2 ,"
				+ " a.signdate,l.invoiceno"
				+ " from lcgrpcont a,laagent d,labranchgroup h,ldcontinvoicemap l where l.batchno = '"
				+ prtSeq
				+ "' and a.grpcontno = l.contno"
				+ " and d.agentcode = trim(a.agentcode) and h.agentgroup = d.branchcode"
				+ " order by subcom,subcenter,a.managecom,branchattr1,branchattr2,h.branchattr,a.agentcode,a.prtno";
		try {
			SSRS ssrs = exeSql.execSQL(strSQL);
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

			String oldGroup = ""; // 区
			String oldGroupName = "";
			String newGroup = "";
			String newGroupName = "";
			String oldSubCom = ""; // 支公司
			String oldSubComName = "";
			String newSubCom = "";
			String newSubComName = "";
			String oldMagName = ""; // 分公司
			String newMagName = "";
			String oldMagCom = "";
			String newMagCom = "";
			String MngDepartment = "";

			for (int i = 1; i <= i_count; i++) {
				newGroup = ssrs.GetText(i, 17).trim();
				newGroupName = ssrs.GetText(i, 18).trim();
				newMagCom = ssrs.GetText(i, 13).trim();
				newSubCom = ssrs.GetText(i, 15).trim();
				newMagName = ssrs.GetText(i, 14).trim();
				newSubComName = ssrs.GetText(i, 16).trim();
				MngDepartment = ssrs.GetText(i, 20).trim();
				SignDate = ssrs.GetText(i, 21).trim();
				intTotalQ++;
				intComTotalQ++;

				if (newGroup == null || newGroup.trim().equals("")
						|| !newGroup.equals(oldGroup)) // 区总数
				{
					texttag = new TextTag();
					if (xmlexport != null) {
						if (!newSubCom.equals(oldSubCom)) // 中支公司总数
						{
							texttag.add("SubComTotalQ", intComTotalQ - 1);
							xmlexport.addDisplayControl("displayTotal2");
							intComTotalQ = 1;
						}
						texttag.add("TotalQ", intTotalQ - 1);
						texttag.add("MngArea", oldGroupName);
						texttag.add("MngCom", oldMagName);
						texttag.add("SubCom", oldSubComName);
						intTotalQ = 1;
						String[] col = new String[11];

						xmlexport.addTextTag(texttag);
						xmlexport.addDisplayControl("displayTotal1");
						xmlexport.addListTable(alistTable, col);
						newXmlExport.addDataSet(xmlexport.getDocument()
								.getRootElement());
					}
					xmlexport = new XmlExport();
					xmlexport.createDocument("GrpBill.vts", "printer");
					if (i == 1) // 加标题，日期，总数
					{
						texttag.add("IssueDate", strIssueDate);
						texttag.add("ComTotalQ", i_count);
						texttag.add("PrtSeq", prtSeq);

						xmlexport.addTextTag(texttag);
						xmlexport.addDisplayControl("displayTitle");
					}
					alistTable = new ListTable();
					alistTable.setName("BILL");
				}
				String[] cols = new String[11];
				cols[0] = i + "";
				cols[1] = ssrs.GetText(i, 2);
				cols[2] = ssrs.GetText(i, 3);
				cols[3] = ssrs.GetText(i, 4);
				cols[4] = ssrs.GetText(i, 5);
				cols[5] = ssrs.GetText(i, 6);
				cols[6] = ssrs.GetText(i, 7);
				cols[7] = ssrs.GetText(i, 8);
				cols[8] = MngDepartment + ssrs.GetText(i, 9);
				cols[9] = SignDate;
				String InvoNo = ssrs.GetText(i, 22).trim();
				if (InvoNo != null && InvoNo.trim().length() >= 12) {
					cols[10] = InvoNo.substring(0, 12);
					logger.debug(cols[10]);
				} else {
					cols[10] = InvoNo;
					logger.debug("cols[10]========" + cols[10]);
				}
				alistTable.add(cols);
				oldGroup = newGroup;
				oldSubCom = newSubCom;
				oldMagName = newMagName;
				oldSubComName = newSubComName;
				oldMagCom = newMagCom;
				oldGroupName = newGroupName;
			}
			texttag = new TextTag();
			xmlexport.addDisplayControl("displayTotal1");
			xmlexport.addDisplayControl("displayTotal2");
			texttag.add("SubComTotalQ", intComTotalQ);
			texttag.add("TotalQ", intTotalQ);
			texttag.add("MngArea", oldGroupName);
			texttag.add("MngCom", oldMagName);
			texttag.add("SubCom", oldSubComName);

			String[] col = new String[11];
			xmlexport.addTextTag(texttag);
			xmlexport.addListTable(alistTable, col);
			newXmlExport.addDataSet(xmlexport.getDocument().getRootElement());
			logger.debug("清单打印成功！");
			// newXmlExport.outputDocumentToFile("d:\\","GrpBillSucc"+PubFun.getCurrentTime().toString().replaceAll(":","_"));//输出xml文档到文件
			mResult.addElement(newXmlExport);
		} catch (Exception ex) {
			this.buildError("getPrintData", "没有查询到相关数据！");
			mResult.addElement(newXmlExport);
			return false;
		}
		return true;
	}

	/*
	 * @function :in order to debug
	 */
	public static void main(String[] args) {
		String tStrLength = "10";
		String tContNoSet = "1234:HB010522651000296:HB010522651000297:HB010522651000298:HB010523971015095:HB010523971015787:HB010523971015788:HB010526111000016:HB010522651000068:HB010522651000152";// 2301190000000524:2301190000000540:230110000003308:230110000003290:230110000003300";
		// String tContNoSet = "2301190000000759";
		VData tVData = new VData();
		GlobalInput tg = new GlobalInput();
		tg.ManageCom = "86";
		tg.ComCode = "86";
		tg.Operator = "110001";
		tVData.addElement(tStrLength);
		tVData.addElement(tContNoSet);
		tVData.addElement(tg);
		tVData.addElement("83210000066");

		GrpBillBL tGrpBillBL = new GrpBillBL();
		tGrpBillBL.submitData(tVData, "PRINT");
	}
}

/**
 * <p>Title:需要输入统计项的的扫描报表</p>
 * <p>Description: 1张报表</p>
 * <p>bq1：保全日结清单（个险）</p>
 * <p>bq2: 保全月结清单（个险)</p>
 * <p>bq3: 保全日结清单（法人)</p>
 * <p>bq4: 保全月结清单（法人)</p>
 * <p>bq5: 保全日结清单（银代)</p>
 * <p>bq6: 保全月结清单(（银代)</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: sinosoft</p>
 * <p>add date and  月结 加金额</p>
 * <p>Company: sinosoft</p>
 * @author sunsx
 * @version 1.0
 */
package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;


import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.LCGrpContSchema;


import com.sinosoft.utility.*;

public class BQGrpReportBL {
private static Logger logger = Logger.getLogger(BQGrpReportBL.class);




	/** 全局数据 */
	private GlobalInput mGlobalInput;


	private XmlExport mXmlExport;

	

	public XmlExport getXmlExport() {
		return mXmlExport;
	}





	public void setXmlExport(XmlExport xmlExport) {
		mXmlExport = xmlExport;
	}





	public GlobalInput getGlobalInput() {
		return mGlobalInput;
	}





	public void setGlobalInput(GlobalInput globalInput) {
		mGlobalInput = globalInput;
	}





	/**构造函数*/
	public BQGrpReportBL() {
	}
	
	public boolean printGrpLoanInfo(String tManageCom,String tPayOffFlag,String tStartDate,String tEndDate){
		XmlExport xmlexport = new XmlExport();
		ListTable tlistTable = new ListTable();
		xmlexport.createDocument("BQGrpLoanInfo.vts", "printer");
		xmlexport.addDisplayControl("displayGL");
		tlistTable.setName("BQ");
		String tSql = " select b.grpcontno,(select name from ldcom where comcode = b.managecom),b.grpname,"
			+ " nvl((select sum(insuaccbala) from lcinsureacc where grpcontno = b.grpcontno and state not in (1, 4)),0),"
			+ " a.summoney,nvl((select sum(getmoney) from ljagetendorse where EndorsementNo = a.edorno and FeeFinaType = 'RV'),0),"
			+ " a.loandate,nvl(c.returnmoney, 0),nvl(c.returninterest, 0),nvl(c.payoffdate, ''),''"
			+ " from lcgrpcont b,loloan a left outer join loreturnloan c on a.contno = c.contno and a.edorno = c.loanno "
			+ " where 1 = 1 and a.contno = b.grpcontno " 
			+ ReportPubFun.getWherePartLike("b.ManageCom", tManageCom)
    		+ ReportPubFun.getWherePart("a.PayOffFlag", tPayOffFlag);

		if(tStartDate!=null && !tStartDate.trim().equals(""))
		{
			tSql += " and a.loandate >= '"+tStartDate+"'";

		}

		if(tEndDate!=null && !tEndDate.trim().equals(""))
		{

			tSql += " and a.loandate <= '"+tEndDate+"'";
		}    
		tSql += " order by a.loandate desc,b.GrpContNo";

		ExeSQL BQExeSQL = new ExeSQL();
		SSRS BQSSRS = new SSRS();
		logger.debug(tSql);
		BQSSRS = BQExeSQL.execSQL(tSql);

		int tArrLen = BQSSRS.MaxRow;
		if(tArrLen<1){
			return false;
		}
		for (int i = 1; i <= tArrLen; i++) {
			String[] strBQ = new String[12] ;
			strBQ[0] = String.valueOf(i);
			strBQ[1] = BQSSRS.GetText(i, 1);
			strBQ[2] = BQSSRS.GetText(i, 2);
			strBQ[3] = BQSSRS.GetText(i, 3);
			strBQ[4] = BQSSRS.GetText(i, 4);
			strBQ[5] = BQSSRS.GetText(i, 5);
			strBQ[6] = BQSSRS.GetText(i, 6);
			strBQ[7] = BQSSRS.GetText(i, 7);
			strBQ[8] = BQSSRS.GetText(i, 8);
			strBQ[9] = BQSSRS.GetText(i, 9);
			strBQ[10] = BQSSRS.GetText(i, 10);
			strBQ[11] = BQSSRS.GetText(i, 11);
			tlistTable.add (strBQ) ;
		}

		String[] b_strBQ = new String[10];
		xmlexport.addListTable(tlistTable, b_strBQ);

		TextTag texttag = new TextTag(); //新建一个TextTag的实例
		if(tStartDate!=null && !tStartDate.trim().equals(""))
		{
			texttag.add("StartDate", tStartDate);

		}else{
			texttag.add("StartDate", "");
		}

		if(tEndDate!=null && !tEndDate.trim().equals(""))
		{
			texttag.add("EndDate", tEndDate);

		}else{
			texttag.add("EndDate", "");
		}
		texttag.add("GLManageCom", ReportPubFun.getMngName(mGlobalInput.ManageCom));
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		this.setXmlExport(xmlexport);

		return true;
	}


	public boolean printGrpSingleAcc(String tGrpContNo,String tContNo,String tStartDate,String tEndDate){
		XmlExport xmlexport = new XmlExport();
		ListTable tlistTable = new ListTable();
		xmlexport.createDocument("BQGrpSingleAcc.vts", "printer");
		xmlexport.addDisplayControl("displayGSA");
		tlistTable.setName("BQ");
		String tSql = " select b.contno,c.name,c.idno,c.sequenceno,a.money,a.moneytype,a.paydate, "
			+ " (select payplanname from lmriskaccpay "
			+ " where riskcode = b.riskcode and payplancode = a.PayPlanCode and insuaccno = a.insuaccno),"
			+ " a.operator from lcinsureacctrace a, lcpol b, lcinsured c"
			+ "  where a.polno = b.polno and b.insuredno = c.insuredno and b.contno = c.contno "
			+ ReportPubFun.getWherePart("b.GrpContNo", tGrpContNo)
    		+ ReportPubFun.getWherePart("b.ContNo", tContNo);

		if(tStartDate!=null && !tStartDate.trim().equals(""))
		{
			tSql += " and a.paydate >= '"+tStartDate+"'";

		}

		if(tEndDate!=null && !tEndDate.trim().equals(""))
		{

			tSql += " and a.paydate <= '"+tEndDate+"'";
		}    
		tSql += " order by b.ContNo,a.paydate";

		ExeSQL BQExeSQL = new ExeSQL();
		SSRS BQSSRS = new SSRS();
		logger.debug(tSql);
		BQSSRS = BQExeSQL.execSQL(tSql);

		int tArrLen = BQSSRS.MaxRow;
		if(tArrLen<1){
			return false;
		}
		for (int i = 1; i <= tArrLen; i++) {
			String[] strBQ = new String[10] ;
			strBQ[0] = String.valueOf(i);
			strBQ[1] = BQSSRS.GetText(i, 1);
			strBQ[2] = BQSSRS.GetText(i, 2);
			strBQ[3] = BQSSRS.GetText(i, 3);
			strBQ[4] = BQSSRS.GetText(i, 4);
			strBQ[5] = BQSSRS.GetText(i, 5);
			strBQ[6] = BQSSRS.GetText(i, 6);
			strBQ[7] = BQSSRS.GetText(i, 7);
			strBQ[8] = BQSSRS.GetText(i, 8);
			strBQ[9] = BQSSRS.GetText(i, 9);
;
			tlistTable.add (strBQ) ;
		}

		String[] b_strBQ = new String[10];
		xmlexport.addListTable(tlistTable, b_strBQ);

		TextTag texttag = new TextTag(); //新建一个TextTag的实例
		if(tStartDate!=null && !tStartDate.trim().equals(""))
		{
			texttag.add("StartDate", tStartDate);

		}else{
			texttag.add("StartDate", "");
		}

		if(tEndDate!=null && !tEndDate.trim().equals(""))
		{
			texttag.add("EndDate", tEndDate);

		}else{
			texttag.add("EndDate", "");
		}
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(tGrpContNo);
		if(!tLCGrpContDB.getInfo()){
			return false;
		}
		LCGrpContSchema tLCGrpContSchema = tLCGrpContDB.getSchema();
		texttag.add("GrpName", tLCGrpContSchema.getGrpName());
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		
		this.setXmlExport(xmlexport);

		return true;
	}


	public boolean printGrpSingleInfo(String tGrpContNo,String tAppFlag,String tStartDate,String tEndDate){
		XmlExport xmlexport = new XmlExport();
		ListTable tlistTable = new ListTable();
		xmlexport.createDocument("BQGrpSingleInfo.vts", "printer");
		xmlexport.addDisplayControl("displayGS");
		tlistTable.setName("BQ");
		String tSql = "select a.contno,b.name,decode(b.sex, '0', '男', '1', '女', '不详'),b.birthday,"
			+ "decode(b.idtype, '0','身份证','1','护照','2','军官证','3','驾照','4','户口本','5','学生证','6','工作证','8','其它','9','无证件','其它'),"
			+ "b.idno,a.cvalidate,c.riskname,a.amnt,a.prem,decode(a.appflag,'1','有效','4','失效','未生效') "
			+ " from lcpol a, lcinsured b, lmriskapp c"
			+ " where a.contno = b.contno and a.insuredno = b.insuredno and a.riskcode = c.riskcode and a.grpcontno = '"+tGrpContNo+"'"
			+ ReportPubFun.getWherePart("a.AppFlag", tAppFlag);
		if(tStartDate!=null && !tStartDate.trim().equals(""))
		{
			tSql += " and a.cvalidate >= '"+tStartDate+"'";

		}

		if(tEndDate!=null && !tEndDate.trim().equals(""))
		{

			tSql += " and a.cvalidate <= '"+tEndDate+"'";
		}    
		tSql += " order by a.ContNo";

		ExeSQL BQExeSQL = new ExeSQL();
		SSRS BQSSRS = new SSRS();
		logger.debug(tSql);
		BQSSRS = BQExeSQL.execSQL(tSql);

		int tArrLen = BQSSRS.MaxRow;
		if(tArrLen<1){
			return false;
		}
		for (int i = 1; i <= tArrLen; i++) {
			String[] strBQ = new String[13] ;
			strBQ[0] = String.valueOf(i);
			strBQ[1] = BQSSRS.GetText(i, 1);
			strBQ[2] = BQSSRS.GetText(i, 2);
			strBQ[3] = BQSSRS.GetText(i, 3);
			strBQ[4] = BQSSRS.GetText(i, 4);
			strBQ[5] = BQSSRS.GetText(i, 5);
			strBQ[6] = BQSSRS.GetText(i, 6);
			strBQ[7] = BQSSRS.GetText(i, 7);
			strBQ[8] = BQSSRS.GetText(i, 8);
			strBQ[9] = BQSSRS.GetText(i, 9);
			strBQ[10] = BQSSRS.GetText(i, 10);
			strBQ[11] = BQSSRS.GetText(i, 11);
			strBQ[12] = "";
			tlistTable.add (strBQ) ;
		}

		String[] b_strBQ = new String[13];
		xmlexport.addListTable(tlistTable, b_strBQ);

		TextTag texttag = new TextTag(); //新建一个TextTag的实例
		if(tStartDate!=null && !tStartDate.trim().equals(""))
		{
			texttag.add("StartDate", tStartDate);

		}else{
			texttag.add("StartDate", "");
		}

		if(tEndDate!=null && !tEndDate.trim().equals(""))
		{
			texttag.add("EndDate", tEndDate);

		}else{
			texttag.add("EndDate", "");
		}
		texttag.add("GSGrpContNo", tGrpContNo);
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		
		this.setXmlExport(xmlexport);

		return true;
	}

	public boolean printGrpEdorInfo(String tGrpContNo,String tOperator,String tEdorType,String tManageCom,String tStartDate,String tEndDate){
		XmlExport xmlexport = new XmlExport();
		ListTable tlistTable = new ListTable();
		xmlexport.createDocument("BQGrpEdorInfo.vts", "printer");
		xmlexport.addDisplayControl("displayGE");
		tlistTable.setName("BQ");
		String tSql = "select b.edortype,b.grpcontno,nvl(a.edorconfno, b.edorno),nvl((select m.riskcode from lcgrppol m,lmriskapp n where m.grpcontno = b.grpcontno and m.riskcode = n.riskcode and n.SubRiskFlag <> 'S' and rownum = 1),"
			+ "(select riskcode from lcgrppol where grpcontno = b.grpcontno and rownum = 1)),a.managecom,"	
			+ "(select grpname from lcgrpcont where grpcontno = b.grpcontno), "
			+ " a.getmoney,(select nvl(count(distinct contno), '0') from lpedoritem where edorno = b.edorno and edortype = b.edortype),"
	        + " a.makedate,a.operator,a.confdate,'',''from lpedorapp a, lpgrpedoritem b "
	        + "  where 1=1 and a.edoracceptno = b.edoracceptno and b.edorstate = '0' "
			+ ReportPubFun.getWherePart("b.GrpContNo", tGrpContNo)
			+ ReportPubFun.getWherePart("b.EdorType", tEdorType)
			+ ReportPubFun.getWherePartLike("a.ManageCom", tManageCom)
			+ ReportPubFun.getWherePart("a.Operator", tOperator);
		if(tStartDate!=null && !tStartDate.trim().equals(""))
		{
			tSql += " and a.confdate >= '"+tStartDate+"'";

		}

		if(tEndDate!=null && !tEndDate.trim().equals(""))
		{

			tSql += " and a.confdate <= '"+tEndDate+"'";
		}    
		tSql += " order by a.makedate desc,b.GrpContNo";

		ExeSQL BQExeSQL = new ExeSQL();
		SSRS BQSSRS = new SSRS();
		logger.debug(tSql);
		BQSSRS = BQExeSQL.execSQL(tSql);

		int tArrLen = BQSSRS.MaxRow;
		if(tArrLen<1){
			return false;
		}
		for (int i = 1; i <= tArrLen; i++) {
			String[] strBQ = new String[14] ;
			strBQ[0] = String.valueOf(i);
			strBQ[1] = BQSSRS.GetText(i, 1);
			strBQ[2] = BQSSRS.GetText(i, 2);
			strBQ[3] = BQSSRS.GetText(i, 3);
			strBQ[4] = BQSSRS.GetText(i, 4);
			strBQ[5] = BQSSRS.GetText(i, 5);
			strBQ[6] = BQSSRS.GetText(i, 6);
			strBQ[7] = BQSSRS.GetText(i, 7);
			strBQ[8] = BQSSRS.GetText(i, 8);
			strBQ[9] = BQSSRS.GetText(i, 9);
			strBQ[10] = BQSSRS.GetText(i, 10);
			strBQ[11] = BQSSRS.GetText(i, 11);
			strBQ[12] = "";
			strBQ[13] = "";
			tlistTable.add (strBQ) ;
		}

		String[] b_strBQ = new String[14];
		xmlexport.addListTable(tlistTable, b_strBQ);

		TextTag texttag = new TextTag(); //新建一个TextTag的实例
		if(tStartDate!=null && !tStartDate.trim().equals(""))
		{
			texttag.add("StartDate", tStartDate);

		}else{
			texttag.add("StartDate", "");
		}

		if(tEndDate!=null && !tEndDate.trim().equals(""))
		{
			texttag.add("EndDate", tEndDate);

		}else{
			texttag.add("EndDate", "");
		}
		texttag.add("GEManageCom", ReportPubFun.getMngName(mGlobalInput.ManageCom));
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		
		this.setXmlExport(xmlexport);

		return true;
	}
	
	public boolean printGrpEdorUserInfo(String tEdorPopedom,String tManageCom,String tUserCode,String tUserName,String tUserState,String tStartDate,String tEndDate){
		XmlExport xmlexport = new XmlExport();
		ListTable tlistTable = new ListTable();
		xmlexport.createDocument("BQGrpEdorUserInfo.vts", "printer");
		xmlexport.addDisplayControl("displayGU");
		tlistTable.setName("BQ");
		String tSql = "select a.usercode, a.username,"
			 		+ " (select shortname from ldcom where comcode = a.comcode),"
			 		+ " b.edorpopedom,decode(a.userstate, '0', '有效', '1', '失效', '未知'),"
			 		+ " '',nvl(b.validstartdate,''),nvl(a.validenddate,'')"
			 		+ " from lduser a, ldedoruser b"
			 		+ "  where a.usercode = b.usercode and b.usertype = '2' "
			 		+ ReportPubFun.getWherePart("b.edorpopedom", tEdorPopedom)
			 		+ ReportPubFun.getWherePartLike("a.usercode", tUserCode)
			 		+ ReportPubFun.getWherePartLike("a.username", tUserName)
			 		+ ReportPubFun.getWherePartLike("a.comcode", tManageCom)
			 		+ ReportPubFun.getWherePart("a.userstate", tUserState);
		if(tStartDate!=null && !tStartDate.trim().equals(""))
		{
			tSql += " and b.validstartdate >= '"+tStartDate+"'";

		}

		if(tEndDate!=null && !tEndDate.trim().equals(""))
		{

			tSql += " and b.validstartdate <= '"+tEndDate+"'";
		}    
		tSql += " order by a.usercode";

		ExeSQL BQExeSQL = new ExeSQL();
		SSRS BQSSRS = new SSRS();
		logger.debug(tSql);
		BQSSRS = BQExeSQL.execSQL(tSql);

		int tArrLen = BQSSRS.MaxRow;
		if(tArrLen<1){
			return false;
		}
		for (int i = 1; i <= tArrLen; i++) {
			String[] strBQ = new String[10] ;
			strBQ[0] = String.valueOf(i);
			strBQ[1] = BQSSRS.GetText(i, 1);
			strBQ[2] = BQSSRS.GetText(i, 2);
			strBQ[3] = BQSSRS.GetText(i, 3);
			strBQ[4] = BQSSRS.GetText(i, 4);
			strBQ[5] = BQSSRS.GetText(i, 5);
			strBQ[6] = BQSSRS.GetText(i, 6);
			strBQ[7] = BQSSRS.GetText(i, 7);
			strBQ[8] = BQSSRS.GetText(i, 8);
			strBQ[9] = "";
			tlistTable.add (strBQ);
		}

		String[] b_strBQ = new String[10];
		xmlexport.addListTable(tlistTable, b_strBQ);

		TextTag texttag = new TextTag(); //新建一个TextTag的实例
		if(tStartDate!=null && !tStartDate.trim().equals(""))
		{
			texttag.add("StartDate", tStartDate);

		}else{
			texttag.add("StartDate", "");
		}

		if(tEndDate!=null && !tEndDate.trim().equals(""))
		{
			texttag.add("EndDate", tEndDate);

		}else{
			texttag.add("EndDate", "");
		}
		texttag.add("GUManageCom", ReportPubFun.getMngName(mGlobalInput.ManageCom));
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		
		this.setXmlExport(xmlexport);

		return true;
	}

	
	public boolean printGrpInfo(String tGrpContNo,String tAppFlag,String tRiskCode,String tManageCom,String tStartDate,String tEndDate){
		XmlExport xmlexport = new XmlExport();
		ListTable tlistTable = new ListTable();
		xmlexport.createDocument("BQGrpInfo.vts", "printer");
		xmlexport.addDisplayControl("displayGI");
		tlistTable.setName("BQ");
		String tSql = "select a.grpcontno,a.prtno,b.riskname,a.managecom,"
			+ "a.grpname,nvl(a.Peoples2,'0'),nvl(a.prem,'0'),nvl(a.amnt,'0'),a.cvalidate,ceil(months_between(a.payenddate,a.cvalidate))||'月',decode(a.appflag,'1','有效','4','失效','未生效') "
			+ " from lcgrppol a,lmriskapp b"
			+ " where a.riskcode = b.riskcode and a.appflag not in ('0','2')"
			+ ReportPubFun.getWherePart("a.GrpContNo", tGrpContNo)
			+ ReportPubFun.getWherePart("a.RiskCode", tRiskCode)
			+ ReportPubFun.getWherePartLike("a.ManageCom", tManageCom)
			+ ReportPubFun.getWherePart("a.AppFlag", tAppFlag);
		if(tStartDate!=null && !tStartDate.trim().equals(""))
		{
			tSql += " and a.cvalidate >= '"+tStartDate+"'";

		}

		if(tEndDate!=null && !tEndDate.trim().equals(""))
		{

			tSql += " and a.cvalidate <= '"+tEndDate+"'";
		}    
		tSql += " order by a.cvalidate desc,a.GrpContNo";

		ExeSQL BQExeSQL = new ExeSQL();
		SSRS BQSSRS = new SSRS();
		logger.debug(tSql);
		BQSSRS = BQExeSQL.execSQL(tSql);

		int tArrLen = BQSSRS.MaxRow;
		if(tArrLen<1){
			return false;
		}
		for (int i = 1; i <= tArrLen; i++) {
			String[] strBQ = new String[13] ;
			strBQ[0] = String.valueOf(i);
			strBQ[1] = BQSSRS.GetText(i, 1);
			strBQ[2] = BQSSRS.GetText(i, 2);
			strBQ[3] = BQSSRS.GetText(i, 3);
			strBQ[4] = BQSSRS.GetText(i, 4);
			strBQ[5] = BQSSRS.GetText(i, 5);
			strBQ[6] = BQSSRS.GetText(i, 6);
			strBQ[7] = BQSSRS.GetText(i, 7);
			strBQ[8] = BQSSRS.GetText(i, 8);
			strBQ[9] = BQSSRS.GetText(i, 9);
			strBQ[10] = BQSSRS.GetText(i, 10);
			strBQ[11] = BQSSRS.GetText(i, 11);
			strBQ[12] = "";
			tlistTable.add (strBQ) ;
		}

		String[] b_strBQ = new String[13];
		xmlexport.addListTable(tlistTable, b_strBQ);

		TextTag texttag = new TextTag(); //新建一个TextTag的实例
		if(tStartDate!=null && !tStartDate.trim().equals(""))
		{
			texttag.add("StartDate", tStartDate);

		}else{
			texttag.add("StartDate", "");
		}

		if(tEndDate!=null && !tEndDate.trim().equals(""))
		{
			texttag.add("EndDate", tEndDate);

		}else{
			texttag.add("EndDate", "");
		}
		texttag.add("GIManageCom", ReportPubFun.getMngName(mGlobalInput.ManageCom));
		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		
		this.setXmlExport(xmlexport);

		return true;
	}


	public static void main(String[] args) {

	}
}

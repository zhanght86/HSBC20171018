package com.sinosoft.lis.f1print;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsuredRelatedDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskRoleDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCInsuredRelatedSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.schema.LMRiskRoleSchema;
import com.sinosoft.lis.tb.CalBL;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsuredRelatedSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LMRiskRoleSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
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

public class NoPayRateLisBL {
private static Logger logger = Logger.getLogger(NoPayRateLisBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/*
	 * @define globe variable
	 */
	private String strMngCom;
	private String comName;
	private String strStartDate;
	private String strSubCom;
	private String strAgentgroup;
	private String strAgentGroup;
	private int departmentNo;
	private String strDepartment;
	private String strSQL;
	private String strNewSQL;
	private String strbirthdaySQL;
	private String strEndDate;
	private String cValidate;
	private String insuredNo;
	private String birthday;
	private static int age;
	private int minAge;
	private int maxAge;
	private String strSaleChnl;
	FDate fDate = new FDate();
	private String mBack = ""; // 签单追溯标记 "0"--不追溯 "1"--追溯到签单次日 "2"--追溯到交费到帐最大次日
								// "3"--追溯到交费到帐最小次日
	private Date mValiDate = null;
	private String mPolType = "1"; // 1-个人单 2-集体下的个人单 3-合同下的个人单
	private String mark;
	private int mNewAge;

	public NoPayRateLisBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
		strStartDate = (String) cInputData.get(1);
		// strAgentGroup= (String) cInputData.get(2);
		strSaleChnl = (String) cInputData.get(2);

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
		cError.moduleName = "NoPayRateLisBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/***************************************************************************
	 * name : getPrintData() function :get print data parameter : null return
	 * :true or false
	 */

	// private boolean getPrintData()
	// {
	// //选择临时文件读取目录
	// ExeSQL exeSql = new ExeSQL();
	// SSRS testSSRS = new SSRS();
	// testSSRS = exeSql.execSQL("select sysvarvalue From ldsysvar where
	// sysvar='DataPrintCombine'");
	// String strTemplatePath = testSSRS.GetText(1, 1);
	// //数据库中的路径-模板路径D:/lis/ui/f1print/NCLtemplate/
	// // String strTemplatePath = "D:/vsssino/ui/f1print/NCLtemplate/";
	//
	// ExeSQL comdSQL = new ExeSQL();
	// SSRS comSsrs = comdSQL.execSQL("Select Name From labranchgroup Where
	// trim(branchattr)='"+strAgentGroup+"'");
	// try{
	// comName = comSsrs.GetText(1, 1);
	// }
	// catch(Exception ex)
	// {
	// buildError("getprintData", "机构编码有误");
	// logger.debug("机构编码有误");
	// return false;
	// }
	//
	// //evo1
	// ExeSQL searchOneSQL = new ExeSQL();
	// SSRS searchOneSsrs = searchOneSQL.execSQL("Select agentgroup From
	// labranchgroup Where trim(branchattr)='" + strAgentGroup + "'");
	// String str = searchOneSsrs.GetText(1, 1);
	// Vector emptyV = new Vector();
	// emptyV = getSearchResult(str, emptyV);
	// String input = "";
	// input = getAgentGroup(emptyV);
	//
	// strSQL = "Select
	// distinct(a.MissionProp1),a.MissionProp5,b.riskcode,c.agentcode,c.Name,d.Name,b.makedate,b.CValiDate,b.insuredno"
	// +" From lwmission a,lcpol b,laagent c,labranchgroup d"
	// +" Where a.ProcessID = '0000000003'and a.ActivityID = '0000001150'"
	// +" And trim(a.missionprop1)=Trim(b.prtno) And
	// Trim(a.missionprop3)=trim(c.agentcode) "
	// +" And trim(b.polno)=trim(b.mainpolno) And c.agentgroup=d.agentgroup "
	// +" And (d.agentgroup='"+str+"'"+input+")";
	//
	//
	// ExeSQL exeSQL = new ExeSQL();
	// SSRS ssrs = exeSql.execSQL(strSQL);
	// int i_count = ssrs.getMaxRow();
	//
	// if (i_count == 0)
	// {
	// logger.debug("没有可打印的信息");
	// buildError("getprintData", "没有需要打印的信息");
	// return false;
	// }
	// TextTag texttag = new TextTag(); //Create a TextTag instance
	// XmlExport xmlexport = new XmlExport(); //Create a XmlExport instance
	// xmlexport.createDocument("NoPayRateLis.vts", "printer"); //appoint to a
	// special output .vts file
	// ListTable alistTable = new ListTable(); //Create a ListTable instance
	// alistTable.setName("RISK"); //Appoint to a sepcial flag
	// int smalli=1;
	// //将数据装入表单
	// for (int i = 1; i <= i_count; i++)
	// {
	// String[] cols = new String[8];
	//
	//
	// cValidate= ssrs.GetText(i,8);
	//
	// insuredNo=ssrs.GetText(i,9);
	//
	// strbirthdaySQL="Select birthday From lcinsured where
	// insuredno='"+insuredNo+"'";
	//
	// SSRS newbirthSSRS = exeSql.execSQL(strbirthdaySQL);
	//
	// birthday=newbirthSSRS.GetText(1,1);
	// age=getAge(cValidate,birthday);
	// // logger.debug("cValidate "+cValidate+" AND "+birthday);
	// // logger.debug("age=== "+age);
	// strNewSQL=" Select minappage,maxappage From lmriskrole Where
	// riskcode='"+ssrs.GetText(i,3)+"' ";
	// SSRS newSSRS = exeSql.execSQL(strNewSQL);
	// int f_count = newSSRS.getMaxRow();
	//
	// if (f_count == 0)
	// {
	// // logger.debug("险种无年龄限制");
	// continue;
	// }
	// minAge = Integer.parseInt(newSSRS.GetText(1, 1));
	// maxAge = Integer.parseInt(newSSRS.GetText(1, 2));
	// // logger.debug("min======"+minAge+" and MAX====== "+maxAge);
	// if (maxAge > age && age > minAge)
	// {
	// continue;
	// }
	//
	// cols[0] = smalli + "";
	// cols[1] = ssrs.GetText(i, 1);
	// cols[2] = ssrs.GetText(i, 2);
	// cols[3] = ssrs.GetText(i, 3);
	// cols[4] = ssrs.GetText(i, 4);
	// cols[5] = ssrs.GetText(i, 5);
	// cols[6] = ssrs.GetText(i, 6);
	// cols[7] = ssrs.GetText(i, 7);
	// smalli++;
	// // logger.debug("2005-08-09描述表中取出信息！！！！");
	// alistTable.add(cols);
	// }
	// String[] col = new String[1];
	//
	// xmlexport.addListTable(alistTable, col);
	// texttag.add("IssueDate", strStartDate);
	// texttag.add("MngCom", comName);
	// if (texttag.size() > 0)
	// {
	// xmlexport.addTextTag(texttag);
	// }
	//
	// xmlexport.outputDocumentToFile("D:\\", "testHZM"); //输出xml文档到文件
	// mResult.clear();
	// mResult.addElement(xmlexport);
	// return true;
	//
	// }
	private boolean getPrintData() {

		ListTable ListTable = new ListTable();
		ListTable.setName("RISK");
		String[] Title = new String[10];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		Title[5] = "";
		Title[6] = "";
		Title[7] = "";
		Title[8] = "";
		Title[9] = "";
		String FenCom = "";
		String tSql = "";

		ExeSQL exeSql = new ExeSQL();
		SSRS FenSSRS = new SSRS();
		SSRS ZhongSSRS = new SSRS();
		SSRS YingSSRS = new SSRS();
		// 查询分公司SQL
		String tFenSQL = "";
		// 查询中支SQL
		String tZhongSQL = "";
		// 查询营销服务部SQL
		String tYingSQL = "";

		/**
		 * @todo 如果录入的机构为总公司，则循环取总公司下的所有分公司。 如果没有选择总公司则将分公司的数量设为1
		 */
		if (strMngCom.equals("86")) {
			tFenSQL = " select comcode,name from ldcom where upcomcode = '"
					+ "?strMngCom?" + "'";
		} else {
			if (strMngCom.length() == 4) {
				tFenSQL = " select comcode,name from ldcom where comcode = '"
						+ "?strMngCom?" + "'";
			} else {
				tFenSQL = " select comcode,name from ldcom where comcode = '"
						+ "?strMngCom1?" + "'";
			}

		}
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tFenSQL);
		sqlbv1.put("strMngCom", strMngCom);
		sqlbv1.put("strMngCom1", strMngCom.substring(0, 4));
		FenSSRS = exeSql.execSQL(sqlbv1);

		/**
		 * @todo 如果录入的机构为总公司，则循环取总公司下的所有分公司。 如果没有选择总公司则将分公司的数量设为1
		 */
		if (strMngCom.equals("86")) {
			tFenSQL = " select comcode,name from ldcom where upcomcode = '"
					+ "?strMngCom?" + "'";
		} else {
			if (strMngCom.length() == 4) {
				tFenSQL = " select comcode,name from ldcom where comcode = '"
						+ "?strMngCom?" + "'";
			} else {
				tFenSQL = " select comcode,name from ldcom where comcode = '"
						+ "?strMngCom1?" + "'";
			}

		}
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tFenSQL);
		sqlbv2.put("strMngCom", strMngCom);
		sqlbv2.put("strMngCom1", strMngCom.substring(0, 4));
		FenSSRS = exeSql.execSQL(sqlbv2);

		/**
		 * @todo 循环分公司
		 */
		for (int i = 0; i < FenSSRS.getMaxRow(); i++) {
			// 取得分公司名称
			String tFenName = FenSSRS.GetText(i + 1, 2);
			if (strMngCom.length() <= 4) {
				tZhongSQL = " select comcode,name from ldcom where upcomcode = '"
						+ "?upcomcode?" + "'";
			} else {
				tZhongSQL = " select comcode,name from ldcom where comcode = '"
						+ "?comcode?" + "'";
			}
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tZhongSQL);
			sqlbv3.put("upcomcode", FenSSRS.GetText(i + 1, 1));
			sqlbv3.put("comcode", strMngCom.substring(0, 6));
			ZhongSSRS = exeSql.execSQL(sqlbv3);
			// 循环中支
			for (int j = 0; j < ZhongSSRS.getMaxRow(); j++) {
				// 中支名称
				String tZhongName = ZhongSSRS.GetText(j + 1, 2);
				if (strMngCom.length() <= 6) {
					tYingSQL = " select comcode,name from ldcom where upcomcode = '"
							+ "?upcomcode?" + "'";
				} else {
					tYingSQL = " select comcode,name from ldcom where comcode = '"
							+ "?comcode?" + "'";
				}
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(tYingSQL);
				sqlbv4.put("upcomcode", ZhongSSRS.GetText(j + 1, 1));
				sqlbv4.put("comcode", strMngCom.substring(0, 8));
				YingSSRS = exeSql.execSQL(sqlbv4);
				// 循环营销部
				for (int k = 0; k < YingSSRS.getMaxRow(); k++) {
					String tYingName = YingSSRS.GetText(k + 1, 2);

					String[] str1 = new String[8];
					str1[0] = "分公司:";
					str1[1] = tFenName;
					str1[2] = "中心支公司:";
					str1[3] = tZhongName;
					str1[4] = "营业区(营销服务部):";
					str1[5] = tYingName;
					str1[6] = "日期：";
					str1[7] = PubFun.getCurrentDate();

					String[] str2 = new String[8];
					str2[0] = "  ";
					str2[1] = "  ";
					str2[2] = "  ";
					str2[3] = "  ";
					str2[4] = "  ";
					str2[5] = "  ";
					str2[6] = "  ";
					str2[7] = "  ";

					String[] str3 = new String[8];
					str3[0] = "序号";
					str3[1] = "投保单号";
					str3[2] = "投保人";
					str3[3] = "主险";
					str3[4] = "业务员号";
					str3[5] = "业务员姓名";
					str3[6] = "营业部";
					str3[7] = "录单日期";

					tSql = " select a.contno, a.riskcode"
							+ " from lcpol a"
							+ " where a.managecom = '"
							+ "?managecom?"
							+ "'"
							+ " and a.salechnl = '"
							+ "?strSaleChnl?"
							+ "'"
							+ " and a.polno = a.mainpolno"
							+ " and exists (select 'X'"
							+ " from LJTempFee"
							+ " where otherno = a.contno"
							+ " and EnterAccDate is not null)"
//							+ " and contno in (select rpad(b.missionprop1,lislen('lcpol','contno'),' ') from lwmission b where b.processid = '0000000003' and b.activityid = '0000001150' and b.missionprop7 = '"
                            + " and contno in (select rpad(b.missionprop1,lislen('lcpol','contno'),' ') from lwmission b where  b.activityid in (select activityid from lwactivity  where functionid ='10010042') and b.missionprop7 = '" 					
                            + "?managecom?" + "')";
					SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
					sqlbv5.sql(tSql);
					sqlbv5.put("managecom", YingSSRS.GetText(k + 1, 1));
					sqlbv5.put("strSaleChnl", strSaleChnl);
					SSRS temp2SSRS = new SSRS();
					temp2SSRS = exeSql.execSQL(sqlbv5); // 查询该营销服务部下的待签单保单。

					if (temp2SSRS != null && temp2SSRS.getMaxRow() > 0
							&& temp2SSRS.getMaxCol() > 0) {

						ListTable.add(str1);
						ListTable.add(str2);
						ListTable.add(str3);

						for (int l = 1; l <= temp2SSRS.getMaxRow(); l++) { // 对每张保单进行循环。

							if (!this.reCal(temp2SSRS.GetText(l, 1))) { // 符合列出条件。
								tSql = "select a.proposalcontno, a.appntname,(select riskcode from"
										+ " lcpol where contno=a.contno and polno=mainpolno), a.agentcode,"
										+ "(select name from laagent where agentcode=trim(a.agentcode)),"
										+ "(select name from labranchgroup where agentgroup=a.agentgroup),"
										+ "a.makedate from lccont a where a.contno = '"
										+ "?contno?" + "'";
								SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
								sqlbv6.sql(tSql);
								sqlbv6.put("contno", temp2SSRS.GetText(l, 1));
								SSRS ListSSRS = new SSRS();
								ListSSRS = exeSql.execSQL(sqlbv6);
								int x = 1;
								if (ListSSRS != null
										&& ListSSRS.getMaxRow() > 0
										&& ListSSRS.getMaxCol() > 0) {

									String[] stra = new String[8];
									stra[0] = "" + x++;
									stra[1] = ListSSRS.GetText(1, 1);
									stra[2] = ListSSRS.GetText(1, 2);
									stra[3] = ListSSRS.GetText(1, 3);
									stra[4] = ListSSRS.GetText(1, 4);
									stra[5] = ListSSRS.GetText(1, 5);
									stra[6] = ListSSRS.GetText(1, 6);
									stra[7] = ListSSRS.GetText(1, 7);
									ListTable.add(stra);
								}
							} // 如果该保单符合列出条件。
						} // end of for 对每张保单进行循环。

					} // end of 如果有待签单保单。

				} // end of 查询条件为中心支公司。
			}
		}

		// else if (strMngCom.length() == 8) { //查询条件为营销服务部。
		// tSql =
		// " select a.name" +
		// " from ldcom a where comcode ='" +
		// strMngCom +
		// "'";
		// ExeSQL tempExeSQL = new ExeSQL();
		// SSRS TwoSSRS = new SSRS();
		// TwoSSRS = tempExeSQL.execSQL(tSql); // 营销服务部名称。
		// YingCom = TwoSSRS.GetText(1, 1);
		// tSql = "select name from ldcom where trim(comcode)=trim(substr('" +
		// strMngCom +
		// "',1,4))";
		// TwoSSRS = tempExeSQL.execSQL(tSql); // 分公司名称。
		// FenCom = TwoSSRS.GetText(1, 1);
		//
		// tSql = "select name from ldcom where trim(comcode)=trim(substr('" +
		// strMngCom +
		// "',1,6))";
		// TwoSSRS = tempExeSQL.execSQL(tSql); // 支公司名称。
		// ZhongCom = TwoSSRS.GetText(1, 1);
		//
		// String[] str1 = new String[8];
		// str1[0] = "分公司:";
		// str1[1] = FenCom;
		// str1[2] = "中心支公司:";
		// str1[3] = ZhongCom;
		// str1[4] = "营业区(营销服务部):";
		// str1[5] = YingCom;
		// str1[6] = " ";
		// str1[7] = " ";
		//
		// String[] str2 = new String[8];
		// str2[0] = " ";
		// str2[1] = " ";
		// str2[2] = " ";
		// str2[3] = " ";
		// str2[4] = " ";
		// str2[5] = " ";
		// str2[6] = " ";
		// str2[7] = " ";
		//
		// String[] str3 = new String[8];
		// str3[0] = "序号";
		// str3[1] = "投保单号";
		// str3[2] = "投保人";
		// str3[3] = "主险";
		// str3[4] = "业务员号";
		// str3[5] = "业务员姓名";
		// str3[6] = "营业部";
		// str3[7] = "录单日期";
		//
		// tSql =
		// " select a.contno, a.riskcode" +
		// " from lcpol a" +
		// " where a.managecom like '" + strMngCom + "%'" +
		// " and a.salechnl = '" + strSaleChnl + "'" +
		// " and a.polno = a.mainpolno" +
		// " and exists (select 'X'" +
		// " from LJTempFee" +
		// " where otherno = a.contno" +
		// " and EnterAccDate is not null)" +
		// " and exists (select 'X'" +
		// " from lwmission b" +
		// " where b.processid = '0000000003'" +
		// " and b.activityid = '0000001150'" +
		// " and trim(b.missionprop1) = trim(a.contno))";
		//
		// SSRS temp2SSRS = new SSRS();
		// temp2SSRS = tempExeSQL.execSQL(tSql); //查询该营销服务部下的待签单保单。
		//
		// if (temp2SSRS != null && temp2SSRS.getMaxRow() > 0 &&
		// temp2SSRS.getMaxCol() > 0) {
		//
		// ListTable.add(str1);
		// ListTable.add(str2);
		// ListTable.add(str3);
		//
		// for (int j = 1; j <= temp2SSRS.getMaxRow(); j++) { //对每张保单进行循环。
		//
		// if (!this.reCal(temp2SSRS.GetText(j, 1))) { //符合列出条件。
		// tSql =
		// "select a.proposalcontno, a.appntname,(select riskcode from" +
		// " lcpol where contno=a.contno and polno=mainpolno), a.agentcode," +
		// "(select name from laagent where trim(agentcode)=trim(a.agentcode)),"
		// +
		// "(select name from labranchgroup where agentgroup=a.agentgroup)," +
		// "a.makedate from lccont a where a.contno = '" +
		// temp2SSRS.GetText(j, 1) + "'";
		//
		// SSRS ListSSRS = new SSRS();
		// ListSSRS = tempExeSQL.execSQL(tSql);
		// int x = 1;
		// if (ListSSRS != null &&
		// ListSSRS.getMaxRow() > 0 &&
		// ListSSRS.getMaxCol() > 0) {
		//
		// String[] stra = new String[8];
		// stra[0] = "" + x++;
		// stra[1] = ListSSRS.GetText(1, 1);
		// stra[2] = ListSSRS.GetText(1, 2);
		// stra[3] = ListSSRS.GetText(1, 3);
		// stra[4] = ListSSRS.GetText(1, 4);
		// stra[5] = ListSSRS.GetText(1, 5);
		// stra[6] = ListSSRS.GetText(1, 6);
		// stra[7] = ListSSRS.GetText(1, 7);
		// ListTable.add(stra);
		// }
		// } //如果该保单符合列出条件。
		// } //end of for 对每张保单进行循环。
		//
		// } // end of 如果有待签单保单。
		//
		// }
		// else {
		// buildError("getprintData", "请正确选择管理机构！");
		// }

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例

		xmlexport.createDocument("NoPayRateLis.vts", "");

		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		if (strSaleChnl != null && strSaleChnl.equals("1")) {
			texttag.add("ListTitle", "个人业务无费率清单");
		} else if (strSaleChnl != null && strSaleChnl.equals("3")) {
			texttag.add("ListTitle", "银代业务无费率清单");
		}

		// texttag.add("BarCode1", mLOPRTManagerSchema.getCode());
		// texttag.add("BarCodeParam1",
		// "BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		// texttag.add("BarCode2",mLOPRTManagerSchema.getPrtSeq());
		// texttag.add("BarCodeParam2","BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");

		texttag.add("Date", strStartDate);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		xmlexport.addListTable(ListTable, Title);
		// xmlexport.outputDocumentToFile("d:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		logger.debug("xmlexport=" + xmlexport);
		return true;
	}

	private Vector getSearchResult(String str, Vector v) {
		String upAgentgroup = str;
		Vector vd = new Vector();
		vd = v;
		String result1 = "";
		int search_count = 0;
		ExeSQL searchOneSQL = new ExeSQL();
		SSRS searchOneSsrs = new SSRS();
		// vd.add(upAgentgroup);
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql("Select agentgroup From labranchgroup Where upbranch='"
				+ "?upAgentgroup?" + "'");
		sqlbv7.put("upAgentgroup", upAgentgroup);
		searchOneSsrs = searchOneSQL.execSQL(sqlbv7);
		search_count = searchOneSsrs.getMaxRow();

		for (int search = 1; search < search_count + 1; search++) {
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql("Select agentgroup From labranchgroup Where upbranch='"
					+ "?upAgentgroup?" + "'");
			sqlbv8.put("upAgentgroup", upAgentgroup);
			searchOneSsrs = searchOneSQL.execSQL(sqlbv8);

			int deb = searchOneSsrs.getMaxRow();
			if (deb == 0) {
				break;
			}

			result1 = searchOneSsrs.GetText(search, 1);
			vd.add(result1);
			this.getSearchResult(result1, vd);

		}
		return vd;
	}

	private String getAgentGroup(Vector v) {

		Vector agent = new Vector();
		agent = v;
		String goOne = " or";
		String goTwo = " d.agentGroup='";
		String goThree = "' ";
		String output = "";
		String value = "";
		if (agent.isEmpty()) {
			return "";
		}
		for (int i = 0; i < agent.size(); i++) {
			value = (String) agent.get(i);
			if (agent.size() == 1) {
				output = goOne + goTwo + value + goThree;
				return output;
			}
			if (i == 0) {
				output = goOne + goTwo + value;
			} else if (i > 0 && i < agent.size() - 1) {
				output = output + goThree + goOne + goTwo + value;
			} else if (i == agent.size() - 2) {
				output = output + goThree + goOne + goTwo + value;
			} else {
				output = output + goThree + goOne + goTwo + value + goThree;
			}

		}
		// logger.debug("jieguoxianshi"+output);
		return output;
	}

	// //计算被保人在保单生效日的年龄。
	// private int getAge(String s, String v) {
	// int age = 0;
	// int vYear = 0;
	// int vDay = 0;
	// int vMonth = 0;
	// int bYear = 0;
	// int bDay = 0;
	// int bMonth = 0;
	// String tempVdate = "";
	// String tempBirthday = "";
	// String[] tempV = new String[3];
	// String[] tempB = new String[3];
	// tempVdate = s;
	// tempBirthday = v;
	// tempB = tempBirthday.split("-");
	// tempV = tempVdate.split("-");
	// vYear = Integer.parseInt(tempV[0]);
	// vMonth = Integer.parseInt(tempV[1]);
	// vDay = Integer.parseInt(tempV[2]);
	// bYear = Integer.parseInt(tempB[0]);
	// bMonth = Integer.parseInt(tempB[1]);
	// bDay = Integer.parseInt(tempB[2]);
	// age = vYear - bYear - 1;
	// // logger.debug(vYear+" "+vMonth+" "+vDay);
	// // logger.debug(bYear+" "+bMonth+" "+bDay);
	// if (vMonth > bMonth) {
	// age = age + 1;
	// return age;
	// } else if (vMonth < bMonth) {
	// return age;
	// } else if (vDay >= bDay) {
	// age = age + 1;
	// return age;
	// } else {
	// return age;
	// }
	// }

	public static boolean CheckAge(String tContNo) {

		String tSql = "select (min(enteraccdate+1)) from ljtempfee where otherno='"
				+ "?tContNo?" + "' and enteraccdate is not null";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(tSql);
		sqlbv9.put("tContNo", tContNo);
		SSRS AccSSRS = new SSRS();
		ExeSQL tempExeSQL = new ExeSQL();
		AccSSRS = tempExeSQL.execSQL(sqlbv9);

		if (AccSSRS == null || AccSSRS.getMaxRow() == 0
				|| AccSSRS.GetText(1, 1).equals("")) {
			return false; // 该保单没有缴费，不计入统计队列。
		}
		FDate fd = new FDate();
		Date AccDate = fd.getDate(AccSSRS.GetText(1, 1));
		logger.debug("入账日期：" + AccDate);

		LCPolSet tLCPolSet = new LCPolSet();
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(tContNo);
		tLCPolSet = tLCPolDB.query();
		for (int p = 1; p <= tLCPolSet.size(); p++) { // 对每个险种进行校验。

			tSql = "select birthday,sex from ldperson where customerno='"
					+ "?customerno?" + "' ";
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(tSql);
			sqlbv10.put("customerno", tLCPolSet.get(p).getInsuredNo());
			SSRS insuSSRS = new SSRS();
			insuSSRS = tempExeSQL.execSQL(sqlbv10);
			Date BirthDay = fd.getDate(insuSSRS.GetText(1, 1));
			logger.debug("出生日期：" + BirthDay);

			tSql = "select maxappage,maxappageflag from lmriskrole where riskcode='"
					+ "?riskcode?"
					+ "' and riskrole='01'"
					+ " and (riskrolesex='2' or riskrolesex='"
					+ "?riskrolesex?" + "')" + " and riskroleno='01'";
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql(tSql);
			sqlbv11.put("riskcode", tLCPolSet.get(p).getRiskCode());
			sqlbv11.put("riskrolesex", insuSSRS.GetText(p, 2));
			SSRS maxSSRS = new SSRS();
			maxSSRS = tempExeSQL.execSQL(sqlbv11);
			if (maxSSRS != null && maxSSRS.getMaxRow() > 0) {
				logger.debug("被保人年龄："
						+ PubFun.calInterval(BirthDay, AccDate, maxSSRS
								.GetText(1, 2)));
				if (Integer.parseInt(maxSSRS.GetText(1, 1)) < PubFun
						.calInterval(BirthDay, AccDate, maxSSRS.GetText(1, 2))) {
					return true;
				}
			} else { // 到lmriskapp查找。
				tSql = "select maxinsuredage from lmriskapp where riskcode='"
						+ "?riskcode?" + "'";
				SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
				sqlbv12.sql(tSql);
				sqlbv12.put("riskcode", tLCPolSet.get(p).getRiskCode());
				SSRS max2SSRS = new SSRS();
				max2SSRS = tempExeSQL.execSQL(sqlbv12);
				if (max2SSRS != null && max2SSRS.getMaxRow() > 0) {
					logger.debug("被保人年龄："
							+ PubFun.calInterval(BirthDay, AccDate, "Y"));
					if (Integer.parseInt(max2SSRS.GetText(1, 1)) < PubFun
							.calInterval(BirthDay, AccDate, "Y")) {

						return true;
					}
				}

			}

			// 开始校验联生被保人。
			LCInsuredRelatedDB tLCInsuredRelatedDB = new LCInsuredRelatedDB();
			LCInsuredRelatedSchema tLCInsuredRelatedSchema = new LCInsuredRelatedSchema();
			LCInsuredRelatedSet tLCInsuredRelatedSet = new LCInsuredRelatedSet();
			tLCInsuredRelatedDB.setPolNo(tLCPolSet.get(p).getPolNo());
			tLCInsuredRelatedSet = tLCInsuredRelatedDB.query();
			tLCInsuredRelatedSchema = tLCInsuredRelatedSet.get(1);
			if (tLCInsuredRelatedSchema != null) {
				if (tLCInsuredRelatedSchema.getSequenceNo().equals("01")) {
					LMRiskRoleDB tLMRiskRoleDB = new LMRiskRoleDB();
					LMRiskRoleSet tLMRiskRoleSet = new LMRiskRoleSet();
					LMRiskRoleSchema tLMRiskRoleSchema = new LMRiskRoleSchema();

					tLMRiskRoleDB.setRiskCode(tLCPolSet.get(p).getRiskCode());
					tLMRiskRoleSet = tLMRiskRoleDB.query();
					tLMRiskRoleSchema = tLMRiskRoleSet.get(1);
					if (tLMRiskRoleSchema.getMAXAppAge() < PubFun.calInterval(
							BirthDay, AccDate, "Y")) {
						return true;
					}
				} else {
					LMRiskRoleDB tLMRiskRoleDB = new LMRiskRoleDB();
					LMRiskRoleSet tLMRiskRoleSet = new LMRiskRoleSet();
					LMRiskRoleSchema tLMRiskRoleSchema = new LMRiskRoleSchema();

					tLMRiskRoleDB.setRiskCode(tLCPolSet.get(p).getRiskCode());
					tLMRiskRoleDB.setRiskRoleNo("02");
					tLMRiskRoleSet = tLMRiskRoleDB.query();
					tLMRiskRoleSchema = tLMRiskRoleSet.get(1);
					if (tLMRiskRoleSchema != null) {
						logger.debug("被保人年龄："
								+ PubFun.calInterval(BirthDay, AccDate,
										tLMRiskRoleSchema.getMinAppAgeFlag()));
						if (tLMRiskRoleSchema.getMAXAppAge() < PubFun
								.calInterval(BirthDay, AccDate,
										tLMRiskRoleSchema.getMinAppAgeFlag())) {
							return true;
						}
					} else { // 到LMRiskApp表查询。
						tSql = "select maxinsuredage from lmriskapp where riskcode='"
								+ "?riskcode?" + "'";
						SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
						sqlbv13.sql(tSql);
						sqlbv13.put("riskcode", tLCPolSet.get(p).getRiskCode());
						SSRS max2SSRS = new SSRS();
						max2SSRS = tempExeSQL.execSQL(sqlbv13);
						if (max2SSRS != null && max2SSRS.getMaxRow() > 0) {
							logger.debug("被保人年龄："
											+ PubFun.calInterval(BirthDay,
													AccDate, "Y"));
							if (Integer.parseInt(max2SSRS.GetText(1, 1)) < PubFun
									.calInterval(BirthDay, AccDate, "Y")) {
								return true;
							}
						}

					}
				} // 第二被保险人。

			} // 如果有联生险。

		}
		return false;
	} // end of function.

	/**
	 * 判断是否需要追溯险种保单生效日期 输出：boolean
	 */
	private boolean needReCal(LCPolSchema tLCPolSchema) {

		if (mPolType.equals("1")) { // 个人单才需要进行追溯
			LDSysVarDB tLDSysVarDB = new LDSysVarDB();
			tLDSysVarDB.setSysVar("SignValiDateBackFlag");
			tLDSysVarDB.getInfo();
			mBack = tLDSysVarDB.getSysVarValue().trim();

			if (mBack.equals("1") || mBack.equals("2") || mBack.equals("3")) {
				if (tLCPolSchema.getSpecifyValiDate() == null) { // 如果为空，置为N
					tLCPolSchema.setSpecifyValiDate("N");
				}

				if ((tLCPolSchema.getSpecifyValiDate() != null)
						&& tLCPolSchema.getSpecifyValiDate().equals("N")) {
					mValiDate = this.calValiDate(tLCPolSchema, mBack);
					if (mValiDate == null) {
						return false;
					}
					mNewAge = PubFun.calInterval(fDate.getDate(tLCPolSchema
							.getInsuredBirthday()), mValiDate, "Y");

					if (mNewAge == tLCPolSchema.getInsuredAppAge()
							|| (mark != null && mark.equals("card"))) {
						return false;
					} else {
						// 年龄变化返回true

						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * 重新计算投保单 输出：VData
	 */
	private boolean reCal(String tContNo) {
		VData tReturn = new VData();
		LCPolBL tLCPolBL = new LCPolBL();
		LCDutyBLSet tLCDutyBLSet = new LCDutyBLSet();
		LCGetBLSet tLCGetBLSet = new LCGetBLSet();

		LCPolSet tLCPolSet = new LCPolSet();
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setContNo(tContNo);
		tLCPolSet = tLCPolDB.query();

		for (int j = 0; j < tLCPolSet.size(); j++) {

			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(j + 1);
			// 判断是否在签单时生日变化，如果是则重新计算保费
			if (needReCal(tLCPolSchema)) {
				// 将新的保单生效日赋到LCPOL中
				tLCPolSchema.setCValiDate(mValiDate);
				// 将新的被保人年龄赋到LCPOL中
				tLCPolSchema.setInsuredAppAge(mNewAge);

				LCDutySet tLCDutySet = new LCDutySet();
				LCDutyDB tLCDutyDB = new LCDutyDB();
				tLCDutyDB.setPolNo(tLCPolSchema.getPolNo());
				tLCDutyDB.setContNo(tContNo);
				tLCDutySet = tLCDutyDB.query();

				LCGetSet tLCGetSet = new LCGetSet();
				LCGetDB tLCGetDB = new LCGetDB();
				tLCGetDB.setContNo(tContNo);
				tLCGetDB.setPolNo(tLCPolSchema.getPolNo());
				tLCGetSet = tLCGetDB.query();

				LCPremSet tLCPremSet = new LCPremSet();
				LCPremDB tLCPremDB = new LCPremDB();
				tLCPremDB.setPolNo(tLCPolSchema.getPolNo());
				tLCPremDB.setContNo(tContNo);
				tLCPremSet = tLCPremDB.query();

				// 把保费项中加费的部分提出来
				String newStart = tLCPolSchema.getCValiDate();
				int premCount = tLCPremSet.size();

				for (int i = 1; i <= premCount; i++) {
					LCPremSchema tLCPremSchema = tLCPremSet.get(i);

					if (!tLCPremSchema.getPayPlanCode().substring(0, 6).equals(
							"000000")) {
						tLCPremSet.remove(tLCPremSchema);
						premCount--;
						i--;
					} else {
						int chg = PubFun.calInterval(tLCPremSchema
								.getPayStartDate(), newStart, "D");
						Date payStartDate = PubFun.calDate(fDate
								.getDate(tLCPremSchema.getPayStartDate()), chg,
								"D", null);
						Date payEndDate = PubFun.calDate(fDate
								.getDate(tLCPremSchema.getPayEndDate()), chg,
								"D", null);
						tLCPremSchema.setPayStartDate(fDate
								.getString(payStartDate));
						tLCPremSchema
								.setPayEndDate(fDate.getString(payEndDate));
					}
				}

				int dutyCount = tLCDutySet.size();

				if (dutyCount == 1) {
					LCDutySchema tLCDutySchema = tLCDutySet.get(1);
					// tLCDutySchema.setDutyCode(null);

					tLCDutySet.set(1, tLCDutySchema);
				}

				int getCount = tLCGetSet.size();

				for (int i = 1; i <= getCount; i++) {
					LCGetSchema tLCGetSchema = tLCGetSet.get(i);

					if (tLCGetSchema.getGetDutyKind() == null) {
						tLCGetSet.remove(tLCGetSchema);
						getCount--;
						i--;
					}
				}

				tLCPolBL.setSchema(tLCPolSchema);
				tLCDutyBLSet.set(tLCDutySet);
				tLCGetBLSet.set(tLCGetSet);

				CalBL tCalBL;

				if (getCount == 0) {
					tCalBL = new CalBL(tLCPolBL, tLCDutyBLSet, "");
				} else {
					tCalBL = new CalBL(tLCPolBL, tLCDutyBLSet, tLCGetBLSet, "");
				}

				if (tCalBL.calPol() == false) {
					// CError.buildErr(this, "险种保单重新计算时失败:" +
					// tCalBL.mErrors.getFirstError());

					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 改变险种保单生效日期 输出：LCPolSchema
	 */
	private Date calValiDate(LCPolSchema tLCPolSchema, String flag) {
		Date tValiDate = null;

		// 选择签单次日
		if (flag.equals("1")) {
			LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
			tLMRiskAppDB.setRiskCode(tLCPolSchema.getRiskCode());
			if (!tLMRiskAppDB.getInfo()) {
				CError.buildErr(this, "查询LMRiskApp表失败!");
				return null;
			}
			int tSignDateCalMode = tLMRiskAppDB.getSignDateCalMode();
			/**
			 * 0--签单当天生效 1--签单次日生效 2--首期交费次日 3--投保次日
			 * 
			 */

			logger.debug("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			logger.debug("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			logger.debug("%%%%%%%%%%%%%%%%%%%%%%%%tSignDateCalMode=="
					+ tSignDateCalMode);

			if (tSignDateCalMode == 0) {
				tValiDate = fDate.getDate(PubFun.getCurrentDate());
			} else if (tSignDateCalMode == 1) {
				tValiDate = fDate.getDate(PubFun.getCurrentDate());
				tValiDate = PubFun.calDate(tValiDate, 1, "D", null);
			} else if (tSignDateCalMode == 2) {
				ExeSQL tExeSQL = new ExeSQL();
				SSRS tempSSRS = new SSRS();
				String aSQL = "select paymode from ljtempfeeclass where otherno='"
						+ "?otherno?"
						+ "' order by makedate ,maketime ";
				SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
				sqlbv14.sql(aSQL);
				sqlbv14.put("otherno", tLCPolSchema.getPrtNo());
				tempSSRS = tExeSQL.execSQL(sqlbv14);
				String payMode = "1";
				if (tempSSRS != null && tempSSRS.getMaxRow() > 0) {
					payMode = tempSSRS.GetText(1, 1);
				}

				String sql = " select EnterAccDate from LJTempFee where otherno = '"
						+ "?otherno?"
						+ "'"
						+ " and EnterAccDate is not null order by makedate ,maketime ";

				// 如果是支票方式则追溯到交费日
				if (payMode != null && payMode.equals("3")) {
					sql = " select PayDate from LJTempFee where otherno = '"
							+ "?otherno?"
							+ "'"
							+ " and EnterAccDate is not null order by makedate ,maketime ";

				}
				SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
				sqlbv15.sql(sql);
				sqlbv15.put("otherno", tLCPolSchema.getPrtNo());
				tempSSRS = tExeSQL.execSQL(sqlbv15);
				// logger.debug(tempSSRS.GetText(1, 1));
				if (tempSSRS.getMaxRow() == 0) {
					return null;
				}
				if ((tempSSRS.GetText(1, 1).equals("0")
						|| tempSSRS.GetText(1, 1).trim().equals("") || tempSSRS
						.GetText(1, 1).equals("null"))) {
					CError.buildErr(this, "在取得财物缴费日期时发生错误!");

					return null;

				}

				tValiDate = fDate.getDate(tempSSRS.GetText(1, 1));
				tValiDate = PubFun.calDate(tValiDate, 1, "D", null);
			} else if (tSignDateCalMode == 3) {
				tValiDate = fDate.getDate(tLCPolSchema.getPolApplyDate());
				tValiDate = PubFun.calDate(tValiDate, 1, "D", null);
			}

		}

		// 选择交费的到帐日期的最大值
		if (flag.equals("2") || flag.equals("3")) {
			Date maxDate = fDate.getDate("1900-01-01");
			Date minDate = fDate.getDate("3000-01-01");

			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			tLJTempFeeDB.setOtherNo(tLCPolSchema.getProposalNo());
			tLJTempFeeDB.setOtherNoType("0");
			tLJTempFeeDB.setTempFeeType("1");
			tLJTempFeeDB.setConfFlag("0");

			LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.query();

			if (tLJTempFeeDB.mErrors.needDealError()) {
				CError.buildErr(this, "财务信息取数失败!");

				return null;
			}

			int n = tLJTempFeeSet.size();

			for (int i = 1; i <= n; i++) {
				LJTempFeeSchema tLJTempFeeSchema = tLJTempFeeSet.get(i);

				Date enterAccDate = fDate.getDate(tLJTempFeeSchema
						.getEnterAccDate());

				if (enterAccDate.before(minDate)) {
					minDate = enterAccDate;
				}

				if (enterAccDate.after(maxDate)) {
					maxDate = enterAccDate;
				}
			}

			if (flag.equals("2")) {
				tValiDate = PubFun.calDate(maxDate, 1, "D", null);
			}

			if (flag.equals("3")) {
				tValiDate = PubFun.calDate(minDate, 1, "D", null);
			}
		}
		logger.debug("%%%%%%%%%%%%%%%%");
		logger.debug("%%%%%%%%%%tValiDate==" + tValiDate);
		return tValiDate;
	}

	/*
	 * @function :in order to debug
	 */
	public static void main(String[] args) {
		String tStrMngCom = "86320000";
		String tIssueDate = "2005-10-09";
		// String tAgentGroup = "0003";
		String tSaleChnl = "1";

		VData tVData = new VData();
		tVData.addElement(tStrMngCom);
		tVData.addElement(tIssueDate);
		tVData.addElement(tSaleChnl);

		NoPayRateLisUI tNoPayRateLisUI = new NoPayRateLisUI();
		tNoPayRateLisUI.submitData(tVData, "PRINT");
	}

	private void jbInit() throws Exception {
	}

}

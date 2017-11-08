<%@include file="/i18n/language.jsp"%>
<%
	 /************************
	 /*再保业务数据导出功能*/
	/*页面:LRBsnzDataExportSave.jsp*/
	/*create:zhangbin*/
	/*创建时间:2007 version 1.0*/
	/*******************************/
%>
<%@page contentType="text/html;charset=GBK"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%
	//在此设置导出Excel的列名，应与sql语句取出的域相对应
	GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput = (GlobalInput) session.getAttribute("GI");

	ExportExcel.Format format = new ExportExcel.Format();
	ArrayList listCell = new ArrayList();
	ArrayList listLB = new ArrayList();
	ArrayList listColWidth = new ArrayList();
	format.mListCell = listCell;
	format.mListBL = listLB;
	format.mListColWidth = listColWidth;

	ExportExcel.Cell tCell = null;
	ExportExcel.ListBlock tLB = null;

	String tManageCom = tGlobalInput.ComCode;
	String startDate = request.getParameter("StartDate");
	String endDate = request.getParameter("EndDate");

	listColWidth.add(new String[] { "0", "5000" });
	String sql = "";
	sql="select case when a.conttype='1' then a.contno else a.grpcontno end ,"
		+ " b.dutycode,a.insuredno,a.cvalidate,(select max(IDNo) from lcinsured where insuredno=a.insuredno),InsuredAppAge,"
		+ " insuredsex,a.occupationtype,0,a.InsuredBirthday,a.riskcode,b.amnt,b.prem ,"
		+ " (case when conttype='1' then (select last_year_reser01 from ReportActu_temp  where i_info_cntr_no=a.contno and pol_code=a.riskcode) "
		+ " Else (select last_year_reser01 from ReportActu_temp  where ipsn_no=a.contno and pol_code=a.riskcode) end),"
		+ " (case when appflag='1' then '"+"有效"+"' else '"+"无效"+"' end) from lcpol a,lcduty b "
		+ " where a.polno=b.polno and appflag in ('1','4') and a.signdate between '"+startDate+"' and '"+endDate+"' "
		+ " order by signdate "
		;
			
  /***排序通过前台传过来的OTHERNOTYPE　来判断用什么排序***/
	tLB = new ExportExcel.ListBlock("001");
  tLB.colName = new String[] { ""+"保单号"+"",""+"契约控制编号"+"",""+"客户编码"+"",""+"生效日期"+"",""+"身份证号"+"",""+"投保年龄"+"",""+"性别"+"",
  	""+"职业类别"+"",""+"次标准体加费率"+"",""+"出生日期"+"",""+"险种"+"",""+"保额"+"",""+"保费"+"",""+"准备金"+"",""+"保单状态"+"" };
	tLB.sql = sql;
	tLB.row1 = 0;
	tLB.col1 = 0;
	tLB.InitData();
	listLB.add(tLB);

	try {
		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition",
		"attachment; filename=LRBsnzDataExport_List.xls");

		OutputStream outOS = response.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(outOS);

		ExportExcel excel = new ExportExcel();
		excel.write(format, bos);
		bos.flush();
		bos.close();
	} catch (Exception e) {
		System.out.println("导出Excel失败！");
	}
%>

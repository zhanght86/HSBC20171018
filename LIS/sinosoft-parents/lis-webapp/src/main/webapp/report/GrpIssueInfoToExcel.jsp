<%
	/************************
	/*承保清单数据导出Excel功能*/
	/*页面:GrpPolInfoToExcel.jsp*/
	/*创建人  ：袁亦方*/
	/*创建时间:20090118*/
	/*******************************/
%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	//GlobalInput tGlobalInput = new GlobalInput();
	//tGlobalInput = (GlobalInput) session.getAttribute("GI");

	ExportExcel.Format format = new ExportExcel.Format();
	ArrayList listCell = new ArrayList();
	ArrayList listLB = new ArrayList();
	ArrayList listColWidth = new ArrayList();
	format.mListCell = listCell;
	format.mListBL = listLB;
	format.mListColWidth = listColWidth;

	//ExportExcel.Cell tCell = null;
	ExportExcel.ListBlock tLB = null;	
	
	listColWidth.add(new String[]{"0","3800"}); //管理机构代码
    listColWidth.add(new String[]{"1","5000"}); //团体合同号
    listColWidth.add(new String[]{"2","5000"}); //投保单号
    listColWidth.add(new String[]{"3","3000"}); //问题件类型
    listColWidth.add(new String[]{"4","12000"}); //问题件内容
    listColWidth.add(new String[]{"5","3000"}); //录入人员
    listColWidth.add(new String[]{"6","3000"}); //复核人员
    listColWidth.add(new String[]{"7","3000"}); //录入日期
    listColWidth.add(new String[]{"8","3000"}); //复核日期
    listColWidth.add(new String[]{"9","3000"}); //签单日期
    listColWidth.add(new String[]{"10","2500"}); //操作位置
    listColWidth.add(new String[]{"11","3000"}); //返回对象
	
	String sql = request.getParameter("sql");
	
	listColWidth.add(new String[] { "0", "5000" });		

    /***排序通过前台传过来的OTHERNOTYPE　来判断用什么排序***/
	tLB = new ExportExcel.ListBlock("001");
	//在此设置导出Excel的列名，应与sql语句取出的域相对应
    tLB.colName = new String[] { "管理机构代码", "团体合同号", "投保单号", "问题件类型","问题件内容","录入人员","复核人员","录入日期","复核日期","签单日期",
    		"操作位置","返回对象"}; 

	tLB.sql = sql;
		
	tLB.row1 = 0;
	tLB.col1 = 0;
	tLB.InitData();
	listLB.add(tLB);
	try {
		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition",
		"attachment; filename=GrpIssueInfoToExcel.xls");

		OutputStream outOS = response.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(outOS);

		ExportExcel excel = new ExportExcel();
		excel.write(format, bos);
		bos.flush();
		bos.close();
	} catch (Exception e) {
		loggerDebug("GrpIssueInfoToExcel","导出Excel失败！");
	}
%>

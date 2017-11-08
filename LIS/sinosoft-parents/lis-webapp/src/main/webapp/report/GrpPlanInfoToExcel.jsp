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
	
	listColWidth.add(new String[]{"0","5000"}); //管理机构
    listColWidth.add(new String[]{"1","3800"}); //管理机构代码
    listColWidth.add(new String[]{"2","5000"}); //团单合同号
    listColWidth.add(new String[]{"3","5000"}); //投保单号
    listColWidth.add(new String[]{"4","3000"}); //被保人姓名
    listColWidth.add(new String[]{"5","2000"}); //性别
    listColWidth.add(new String[]{"6","3000"}); //出生日期
    listColWidth.add(new String[]{"7","5000"}); //套餐计划编码
    listColWidth.add(new String[]{"8","3000"}); //保费
    listColWidth.add(new String[]{"9","3000"}); //份数
    listColWidth.add(new String[]{"10","3000"}); //生效日期
    listColWidth.add(new String[]{"11","3000"}); //签单日期 
	
	String sql = request.getParameter("sql");
	
	listColWidth.add(new String[] { "0", "5000" });		

    /***排序通过前台传过来的OTHERNOTYPE　来判断用什么排序***/
	tLB = new ExportExcel.ListBlock("001");
	//在此设置导出Excel的列名，应与sql语句取出的域相对应
    tLB.colName = new String[] { "管理机构", "管理机构代码", "团体合同号", "投保单号","被保人姓名","性别","出生日期","套餐计划编码","保费","份数",
    		"生效日期","签单日期"}; 

	tLB.sql = sql;
		
	tLB.row1 = 0;
	tLB.col1 = 0;
	tLB.InitData();
	listLB.add(tLB);
	try {
		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition",
		"attachment; filename=GrpPlanInfoToExcel.xls");

		OutputStream outOS = response.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(outOS);

		ExportExcel excel = new ExportExcel();
		excel.write(format, bos);
		bos.flush();
		bos.close();
	} catch (Exception e) {
		loggerDebug("GrpPlanInfoToExcel","导出Excel失败！");
	}
%>

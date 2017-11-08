<%
	 /************************
	/*页面:QueryForPayFeeToExcel.jsp*/
	/*create:lijs*/
	/*创建时间:2007 version 1.0*/
	/*******************************/
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
	//在此设置导出Excel的列名，应与sql语句取出的域相对应
	GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput = (GlobalInput) session.getValue("GI");
	VData tVData = new VData();
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
	String strSQL = request.getParameter("strSQLValueErr");

	listColWidth.add(new String[] { "0", "5500" });
	listColWidth.add(new String[] { "1", "5000" });
	listColWidth.add(new String[] { "2", "15000" });
	listColWidth.add(new String[] { "3", "3000" });
	listColWidth.add(new String[] { "4", "3000" });

    /***排序通过前台传过来的OTHERNOTYPE　来判断用什么排序***/
	tLB = new ExportExcel.ListBlock("001");
	tLB.colName = new String[] { "校验批次号码", "校验规则", "错误日志", "校验人", "校验日期"};		
	tLB.sql = strSQL.trim();
	tLB.row1 = 0;
	tLB.col1 = 0;
	tLB.InitData();
	listLB.add(tLB);
	
	

	try
	{
		response.reset();
		response.setContentType("application/x-download");
		
		//设置导出的xls文件名默认值
		String HeaderParam = "\""+"attachment;filename="+"000001"+".xls"+"\"";
		
		response.addHeader("Content-Disposition",
				"attachment;filename=" + HeaderParam);
		String busiName="ExportExcel";
				
		
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		OutputStream outOS = response.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(outOS);
		tVData.add(format);
		tVData.add(bos);
		
    	if(tBusinessDelegate.submitData(tVData,"write",busiName))
		{  
			bos = (BufferedOutputStream)tBusinessDelegate.getResult().getObjectByObjectName("BufferedOutputStream", 0);
			out.clear();
	 		out  =  pageContext.pushBody(); 
			bos.flush();
			bos.close();
		}
		//ExportExcel excel = new ExportExcel();
		//excel.write(format, bos);
	
		//ExportExcel excel = new ExportExcel();
		//excel.write(format, bos);
	} catch (Exception e) {
		loggerDebug("FIRuleErrorLogToExcel","导出Excel失败！");
	}
%>

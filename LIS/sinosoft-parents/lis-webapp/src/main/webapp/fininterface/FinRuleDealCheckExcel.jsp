<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
	String FlagStr = "";
        String Content = "";
  
	//在此设置导出Excel的列名，应与sql语句取出的域相对应
	GlobalInput tGlobalInput = new GlobalInput(); 
	tGlobalInput = (GlobalInput)session.getValue("GI"); 
	VData tVData = new VData();
	ExportExcel.Format format = new ExportExcel.Format();
	ArrayList listCell = new ArrayList();
	ArrayList listLB = new ArrayList();
	ArrayList listColWidth = new ArrayList();
	format.mListCell=listCell;
	format.mListBL=listLB;
	format.mListColWidth=listColWidth;
	
	ExportExcel.Cell tCell=null;
	ExportExcel.ListBlock tLB=null;
	
	String tManageCom = tGlobalInput.ComCode;
	
	listColWidth.add(new String[]{"0","5000"});  
	String sql = request.getParameter("ExpSQL");
	loggerDebug("FinRuleDealCheckExcel","Excel语句="+sql);
	
	tLB = new ExportExcel.ListBlock("001");
	tLB.colName = new String[]{"质检批次号", "数据采集批次号码", "凭证类型", "错误信息", "业务号码", "质检计划", "质检规则", "处理状态", "错误流水号码"};
	tLB.sql = sql;
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
		if(bos==null){
		loggerDebug("FinRuleDealCheckExcel","---------------++++++++-----------");
		}
		
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
	}

	
	catch(Exception e)
	{
	  Content = "导出Excel失败!";
    FlagStr = "Fail"; 
	}
	if (!FlagStr.equals("Fail"))
	{
		Content = " 导出Excel成功! ";
		FlagStr = "Succ";
	}
%>

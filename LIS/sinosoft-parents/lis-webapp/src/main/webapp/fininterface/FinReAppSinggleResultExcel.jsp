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
        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = new SSRS();
        tSSRS = tExeSQL.execSQL("select (case when (select columnmean from fitablecolumndef where tableid = 'FIDataTransResult' and upper(columnid) = upper(column_name)) is not null then (select columnmean from fitablecolumndef where tableid = 'FIDataTransResult' and upper(columnid) = upper(column_name)) else column_name end) from all_tab_cols where table_name = 'FIDATATRANSRESULT' order by column_id");
        String[] tt = new String[tSSRS.getMaxRow()];
        for(int i =1 ;i<=tSSRS.getMaxRow();i++)
        {
          tt[i-1]= tSSRS.GetText(i, 1);
        }

	
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
	
	String BusinessNo = request.getParameter("BusinessNo");
	String CertificateId = request.getParameter("CertificateId");
	String sql = "select * from fidatatransresult where exists  (select * from fiaboriginaldata where  fiaboriginaldata.aserialno = fidatatransresult.aserialno and fiaboriginaldata.businessno = '" + BusinessNo + "' and fiaboriginaldata.certificateid = '" + CertificateId + "')   order by aserialno";	

	loggerDebug("FinReAppSinggleResultExcel","Excel语句="+sql);
	
	tLB = new ExportExcel.ListBlock("001");
	tLB.colName = tt;
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

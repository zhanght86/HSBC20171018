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
  
	//�ڴ����õ���Excel��������Ӧ��sql���ȡ���������Ӧ
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
	String sql = request.getParameter("ExportExcelSQL");
	loggerDebug("FinInterfaceCheckExcel","Excel���="+sql);
	
	tLB = new ExportExcel.ListBlock("001");
	tLB.colName = new String[]{"���κ�", "����", "�����־", "��Ŀ��Ϣ", "���ݲɼ����", "����", "����", "ҵ������", "��������","Ʊ�ݺ���","Ԥ����","�ɱ�����","���","ƾ֤����","ҵ���������","ҵ�����"};
	tLB.sql = sql;
	tLB.row1 = 0;
	tLB.col1 = 0;
	tLB.InitData();
	listLB.add(tLB);

	try
	{
		response.reset();
		response.setContentType("application/octet-stream");
		
		//���õ�����xls�ļ���Ĭ��ֵ
		String HeaderParam = "\""+"attachment;filename="+"000001"+".xls"+"\"";
		response.setHeader("Content-Disposition",HeaderParam);
		
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
	  Content = "����Excelʧ��!";
    FlagStr = "Fail"; 
	}
	if (!FlagStr.equals("Fail"))
	{
		Content = " ����Excel�ɹ�! ";
		FlagStr = "Succ";
	}
%>

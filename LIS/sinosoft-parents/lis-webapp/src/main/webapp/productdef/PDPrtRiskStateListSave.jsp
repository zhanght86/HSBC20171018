<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2009-09-10
//������  ��sunyu
//���¼�¼��  ������    ��������     ����ԭ��/����
%>


<%@page import="java.io.*"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="com.sinosoft.lis.f1print.Readhtml"%>
<%@page import="com.sinosoft.productdef.PDRiskDefReportListPrtBL" %>

<%
  boolean operFlag = true;
  String FlagStr = "";
  String Content = "";
  XmlExport txmlExport = null; 
  String outname = ""; 
  String outpathname = ""; 
      	 
  GlobalInput tG = (GlobalInput)session.getAttribute("GI");
  
  TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("RiskState", request.getParameter("RiskState"));
  tTransferData.setNameAndValue("StartDate", request.getParameter("StartDate"));
  tTransferData.setNameAndValue("EndDate", request.getParameter("EndDate"));
  
  VData tVData = new VData();
  tVData.addElement(tG);
  tVData.addElement(tTransferData);
         
  PDRiskDefReportListPrtBL tPDRiskDefReportListPrtBL = new PDRiskDefReportListPrtBL();
  if(!tPDRiskDefReportListPrtBL.submitData(tVData,"RiskStateList"))
  {          
     operFlag = false;
     Content = tPDRiskDefReportListPrtBL.mErrors.getFirstError(); 
  }
  else
  {             
     VData mResult = tPDRiskDefReportListPrtBL.getResult();			
     txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
    
     if(txmlExport==null)
     {
    	operFlag=false;
    	Content=""+"û�еõ�Ҫ��ʾ�������ļ�"+"";	  
     }
  }
	
	if (operFlag==true)
	{
	  Readhtml rh = new Readhtml();
	  rh.XmlParse(txmlExport.getInputStream());
		
	  String realpath=application.getRealPath("/").substring(0,application.getRealPath("/").length());//UI��ַ  
	  String temp=realpath.substring(realpath.length()-1,realpath.length());
	  if(!temp.equals("/"))
	  {
		  realpath=realpath+"/"; 
	  }
	  
	  String templatename=rh.getTempLateName();//ģ������
	  String templatepathname=realpath+"productdef/template/"+templatename;//ģ�����ֺ͵�ַ
	  System.out.println("*********************templatepathname= " + templatepathname);
	  System.out.println("************************realpath="+realpath);
	  
	  String date=PubFun.getCurrentDate().replaceAll("-","");
	  String time=PubFun.getCurrentTime().replaceAll(":","");
	  outname=""+"��Ʒ���������ͳ��"+""+tG.Operator+date+time+".xls";
	  outpathname=realpath+"vtsfile/"+outname;//���ļ�Ŀ¼�������,Ӧ��Լ����,ͳһ���,���ڶ������ļ������� Commented By Qisl At 2008.10.23
	 
	  rh.setReadFileAddress(templatepathname);
	  rh.setWriteFileAddress(outpathname);
	  rh.start("vtsmuch");
	  try {
			outname = java.net.URLEncoder.encode(outname, "UTF-8");
			outname = java.net.URLEncoder.encode(outname, "UTF-8");
			outpathname = java.net.URLEncoder.encode(outpathname, "UTF-8");
			outpathname = java.net.URLEncoder.encode(outpathname, "UTF-8");
	  } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
	  }	
	
%>	
	<a href="../f1print/download.jsp?filename=<%=outname%>&filenamepath=<%=outpathname%>">�������</a>
<%	
	}
	else
	{
    	FlagStr = "Fail";
    	if(Content==null || Content.equals(""))
    	{
    		Content=""+"û�з��ϲ�ѯ���������ݣ�"+"";
    	}    	
%>
<html>
<script type="text/javascript">	
	myAlert("<%=Content%>");
	top.close();
</script>
</html>
<%
  	}
%>
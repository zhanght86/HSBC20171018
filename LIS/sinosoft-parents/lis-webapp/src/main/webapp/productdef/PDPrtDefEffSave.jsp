<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：
//程序功能：
//创建日期：2009-09-10
//创建人  ：sunyu
//更新记录：  更新人    更新日期     更新原因/内容
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
  tTransferData.setNameAndValue("RiskCode", request.getParameter("RiskCode"));
  tTransferData.setNameAndValue("StartDate", request.getParameter("StartDate"));
  tTransferData.setNameAndValue("EndDate", request.getParameter("EndDate"));
  
  VData tVData = new VData();
  tVData.addElement(tG);
  tVData.addElement(tTransferData);
         
  PDRiskDefReportListPrtBL tPDRiskDefReportListPrtBL = new PDRiskDefReportListPrtBL();
  if(!tPDRiskDefReportListPrtBL.submitData(tVData,"RiskDefineEff"))
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
    	Content=""+"没有得到要显示的数据文件"+"";	  
     }
  }
	
	if (operFlag==true)
	{
	  Readhtml rh = new Readhtml();
	  rh.XmlParse(txmlExport.getInputStream());
		
	  String realpath=application.getRealPath("/").substring(0,application.getRealPath("/").length());//UI地址  
	  String temp=realpath.substring(realpath.length()-1,realpath.length());
	  if(!temp.equals("/"))
	  {
		  realpath=realpath+"/"; 
	  }
	  
	  String templatename=rh.getTempLateName();//模板名字
	  String templatepathname=realpath+"productdef/template/"+templatename;//模板名字和地址
	  System.out.println("*********************templatepathname= " + templatepathname);
	  System.out.println("************************realpath="+realpath);
	  
	  String date=PubFun.getCurrentDate().replaceAll("-","");
	  String time=PubFun.getCurrentTime().replaceAll(":","");
	  outname=""+"产品定义时效统计"+""+tG.Operator+date+time+".xls";
	  outpathname=realpath+"vtsfile/"+outname;//该文件目录必须存在,应该约定好,统一存放,便于定期做文件清理工作 Commented By Qisl At 2008.10.23
	 
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
	<a href="../f1print/download.jsp?filename=<%=outname%>&filenamepath=<%=outpathname%>">点击下载</a>
<%	
	}
	else
	{
    	FlagStr = "Fail";
    	if(Content==null || Content.equals(""))
    	{
    		Content=""+"没有符合查询条件的数据！"+"";
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
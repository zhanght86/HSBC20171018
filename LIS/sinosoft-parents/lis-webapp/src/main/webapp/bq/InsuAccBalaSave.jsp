<%
//�������ƣ�
//�����ܣ����������������
//�������ڣ�2007-11-09 17:55:57
//������  ������ͥ

%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
 <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="com.sinosoft.lis.bq.*"%>
 <%@page import="com.sinosoft.lis.taskservice.taskinstance.*"%>
 <%@page import="java.util.HashMap"%>;
 <%@page import="com.sinosoft.service.*" %>
 
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%

  String busiName="bqInsuAccBalaManu";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //�������
  CErrors tError = null;
               
  String FlagStr = "";
  String Content = "";
  GlobalInput tG = new GlobalInput();      
  tG = (GlobalInput)session.getValue("GI");
  String tBalaDate=request.getParameter("BalaDate");
  String tRiskCode=request.getParameter("RiskCode");
  TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("BalaDate",tBalaDate);
  tTransferData.setNameAndValue("RiskCode",tRiskCode);
  tTransferData.setNameAndValue("ManageCom", tG.ManageCom);

  VData tVData = new VData();   
  tVData.addElement(tTransferData);
  
	  try
	  {
	      //InsuAccBalaService tInsuAccBalaService = new InsuAccBalaService();
	      //InsuAccBalaService.main();
		  
		  if(!tBusinessDelegate.submitData(tVData,"",busiName));
		  {
		  	  tError = tBusinessDelegate.getCErrors();
		  	  Content = " ����ʧ��ԭ���ǣ�ԭ����:" + tError.getFirstError();
		      FlagStr = "Fail";
		  }
          Content=(String)tBusinessDelegate.getResult().getObjectByObjectName("String",0);
	      FlagStr = "Succ";
	  }
	  catch(Exception ex)
	  {
	    Content = "����ʧ��ʧ�ܣ�ԭ����:" + ex.toString();
	    FlagStr = "Fail";
	  } 
  

  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  //��Ӹ���Ԥ����

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>


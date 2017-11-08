<%
//程序名称：
//程序功能：万能利率输入界面
//创建日期：2007-11-09 17:55:57
//创建人  ：彭送庭

%>
<!--用户校验类-->
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
  //输出参数
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
		  	  Content = " 处理失败原因是，原因是:" + tError.getFirstError();
		      FlagStr = "Fail";
		  }
          Content=(String)tBusinessDelegate.getResult().getObjectByObjectName("String",0);
	      FlagStr = "Succ";
	  }
	  catch(Exception ex)
	  {
	    Content = "结算失败失败，原因是:" + ex.toString();
	    FlagStr = "Fail";
	  } 
  

  //如果在Catch中发现异常，则不从错误类中提取错误信息
  //添加各种预处理

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>


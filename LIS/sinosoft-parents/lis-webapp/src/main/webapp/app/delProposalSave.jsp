<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
  GlobalInput tG = new GlobalInput(); 
  tG=(GlobalInput)session.getValue("GI");
  TransferData tTransferData=new TransferData();
  String transact = request.getParameter("fmAction");
  String Content = "";
  String FlagStr ="";
  tTransferData.setNameAndValue("PrtNo",request.getParameter("PrtNoHide"));
     
  VData tVData = new VData();
  tVData.add(tTransferData);
  tVData.add(tG);
 
 // DelPrtUI tDelPrtUI = new DelPrtUI();
  String busiName="cbcheckDelPrtUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  if(tBusinessDelegate.submitData(tVData,transact,busiName))
  {
  	 Content = "保存成功!请重新录单! ";
     FlagStr = "Success";
  }
  else
  {
     Content = "保存失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
     FlagStr = "Fail";
  }
%>                      

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：FIRuleDealErrLogSave.jsp
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.fininterface.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.fininterface.checkdata.*" %>
  <%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  String strDelErrSerialNo=request.getParameter("DelErrSerialNo");
 loggerDebug("FIRuleDealErrLogSave","得到流水号码" + strDelErrSerialNo);

  String FlagStr = "Fail";
  String Content = "";
  
  FIRuleDealErrLogSchema oDealErrLogSchema = new FIRuleDealErrLogSchema();
  oDealErrLogSchema.setErrSerialNo(strDelErrSerialNo);
  
  loggerDebug("FIRuleDealErrLogSave",oDealErrLogSchema.encode());
  VData oData = new VData();
  oData.addElement(oDealErrLogSchema);
  //FIRuleDealErrDataBL oDealErrDataBL = new FIRuleDealErrDataBL();
  BusinessDelegate blBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  if(blBusinessDelegate.submitData(oData,"INSERT","FIRuleDealErrDataBL")){
     //处理成功
     loggerDebug("FIRuleDealErrLogSave","Del Succ");
     Content = " 保存成功! ";
     FlagStr = "Succ";
  }else{
  	loggerDebug("FIRuleDealErrLogSave","Del Fail");
  	Content = " 保存失败，原因是:" + blBusinessDelegate.getCErrors().getFirstError();
    FlagStr = "Fail";
  }

%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
</script>
</html>


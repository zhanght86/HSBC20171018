<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  //LLCasePolicySet tLLCasePolicySet=new LLCasePolicySet();
  //CasePolicyUI tCasePolicyUI   = new CasePolicyUI();
  ClaimGetQueryUI mClaimGetQueryUI = new ClaimGetQueryUI();

  CErrors tError = null;
  String transact = "QUERY";
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
	//读取保单信息
  String strPolNo = request.getParameter("PolNo");
  loggerDebug("AllClaimGetQuerySave","该用户的保单号码时"+strPolNo);

  try
  {
    VData tVData = new VData();
    tVData.addElement(strPolNo);
    mClaimGetQueryUI.submitData(tVData,transact);
  }
  catch(Exception ex)
  {
    Content = transact+"失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

  if (FlagStr=="")
  {
    tError = tCasePolicyUI.mErrors;
    if (!tError.needDealError())
    {
      Content = " 保存成功";
      FlagStr = "Succ";
    }
    else
    {
      Content = " 保存失败，原因是:" + tError.getFirstError();
      FlagStr = "Fail";
    }
  }
%>
<html>
<script language="javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

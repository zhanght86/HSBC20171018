<!--�û�У����-->
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
<%@page import="com.sinosoft.service.*" %>
<%
  //LLCasePolicySet tLLCasePolicySet=new LLCasePolicySet();
  //CasePolicyUI tCasePolicyUI   = new CasePolicyUI();
  ClaimGetQueryUI mClaimGetQueryUI = new ClaimGetQueryUI();

  CErrors tError = null;
  String transact = "QUERY";
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
	//��ȡ������Ϣ
  String strPolNo = request.getParameter("PolNo");
  loggerDebug("ClaimGetQuerySave","���û��ı�������ʱ"+strPolNo);

  try
  {
    VData tVData = new VData();
    tVData.addElement(strPolNo);
    mClaimGetQueryUI.submitData(tVData,transact);
  }
  catch(Exception ex)
  {
    Content = transact+"ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }

  if (FlagStr=="")
  {
    tError = tCasePolicyUI.mErrors;
    if (!tError.needDealError())
    {
      Content = " ����ɹ�";
      FlagStr = "Succ";
    }
    else
    {
      Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
      FlagStr = "Fail";
    }
  }
%>
<html>
<script language="javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

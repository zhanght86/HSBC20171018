<%
//�������� :FIRuleEngineServiceSave.jsp
//
%>

<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.fininterface.*"%>
  <%@page import="com.sinosoft.lis.fininterface.checkdata.*" %>
  <%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  loggerDebug("FIRuleEngineServiceSave","1��ʼִ��Saveҳ��");
  //FIRuleEngineService1 mFIRuleEngineService1 = new FIRuleEngineService1();
  BusinessDelegate uiBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");              //����û���Ϣ
  CErrors tError = null;
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String Operator = "difference";


  
  VData tVData = new VData();
  try
  {
  	tVData.clear();
		tVData.addElement(tG);
		tVData.addElement(request.getParameter("StartDate"));
		tVData.addElement(request.getParameter("EndDate"));
		tVData.addElement(request.getParameter("VersionNo"));
		tVData.addElement(request.getParameter("callpoint"));
		tVData.addElement(request.getParameter("StartDay"));
		tVData.addElement(request.getParameter("EndDay"));
    uiBusinessDelegate.submitData(tVData,Operator,"FIRuleEngineService1");
  }
  catch(Exception ex)
  {
    Content = "ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }

  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
     tError = uiBusinessDelegate.getCErrors();
    	   if (!tError.needDealError())
    		{     
    				Content = "�����ѳɹ�!"+tError.getFirstError();
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

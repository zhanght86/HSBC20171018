<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GroupUWAutoChk.jsp
//�����ܣ���������Լɨ�������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����
loggerDebug("ScanContInputSave","Auto-begin:");
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.workflow.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>

<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  if(tG == null) 
  {
	loggerDebug("ScanContInputSave","session has expired");
	return;
   }
	//��ҳ����ʱ����
  
%>                      
<html>
<script language="javascript">
    //top.close();
	//alert("<%=Content%>");
	//top.close();
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

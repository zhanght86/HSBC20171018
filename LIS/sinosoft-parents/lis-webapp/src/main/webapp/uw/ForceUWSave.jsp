<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GroupPolInput.jsp
//�����ܣ�
//�������ڣ�2002-08-15 11:48:43
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="java.text.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
	//�������
	CErrors tError = null;
	String tRela  = "";                
	String FlagStr="";
	String Content = "";
	String tAction = "";
	String tOperate = "";

	//�������
	LCContSchema tLCContSchema   = new LCContSchema();
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");

  loggerDebug("ForceUWSave","ContNo=="+request.getParameter("ContNo"));
  loggerDebug("ForceUWSave","ForceUWOpt=="+request.getParameter("ForceUWOpt"));
  loggerDebug("ForceUWSave","ForceUWOptRemark=="+request.getParameter("ForceUWRemark"));


  tLCContSchema.setContNo(request.getParameter("ContNo"));
  tLCContSchema.setForceUWFlag(request.getParameter("ForceUWOpt"));
  if(request.getParameter("ForceUWOpt").equals("1")){
    tLCContSchema.setForceUWReason(request.getParameter("ForceUWRemark"));
  }
  else{
    tLCContSchema.setForceUWReason("");
  }

	VData tVData = new VData();
  tVData.add(tLCContSchema);
  tVData.add(tG);
  String busiName1="tbForceUWUI";
  BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
  //ForceUWUI tForceUWUI = new ForceUWUI();

        
  if(!tBusinessDelegate1.submitData(tVData,"UPDATE",busiName1)){
		Content = " ����ʧ�ܣ�ԭ����: " + tBusinessDelegate1.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = " ����ɹ�! ";
		FlagStr = "Succ";
	}
  loggerDebug("ForceUWSave","Content:"+Content);	

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
    

<!--
*******************************************************
* �������ƣ�AccessCheck.jsp
* �����ܣ�ҳ�����У��ҳ��
* �������ڣ�2002-11-25
* ���¼�¼��  ������    ��������     ����ԭ��/����
*******************************************************
-->
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.service.*" %>
<%@page import="com.sinosoft.utility.*"%>
<%@include file="../jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
try
{

	   StringBuffer strURL = request.getRequestURL();

	  GlobalInput tG1 = (GlobalInput)session.getValue("GI");
	  String  userCode = tG1.Operator;

		TransferData mTransferData = new TransferData();
		mTransferData.setNameAndValue("UserCode", userCode);
    mTransferData.setNameAndValue("strURL", strURL.toString());
    
    //VData
    VData tVData = new VData();
    tVData.add(tG1);
    tVData.add(mTransferData);	
	
   String sOperate = ""; 
   String busiName="Access";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

	 boolean canAccess = tBusinessDelegate.submitData(tVData,sOperate,busiName);
	 if (!canAccess)
	 {
	  	out.println("����Ȩ���ʴ���ҳ");
		  return;
	 }
}
catch(Exception exception){
	String ContentErr = " exception:�������µ�¼��";
	loggerDebug("AccessCheck",ContentErr);
	out.println("��ҳ�����������µ�¼");
%>
<script language=javascript>
	top.window.location ="../indexlis.jsp";
</script>
<%
	return;
}
%>

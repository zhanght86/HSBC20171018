
<%
//�������ƣ�UnLockTable.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@include file="../jsp/Log4jUI.jsp"%>  

<%
  GlobalInput tG = new GlobalInput();	
  tG = ( GlobalInput )session.getValue( "GI" );
		
  //��ӡˢ��
  LDSysTraceSchema tLDSysTraceSchema = new LDSysTraceSchema();
  tLDSysTraceSchema.setPolNo(request.getParameter("PrtNo"));
  tLDSysTraceSchema.setCreatePos(request.getParameter("CreatePos"));
  tLDSysTraceSchema.setPolState(request.getParameter("PolState"));
  LDSysTraceSet inLDSysTraceSet = new LDSysTraceSet();
  inLDSysTraceSet.add(tLDSysTraceSchema);
  VData VData3 = new VData();
  VData3.add(tG);
  VData3.add(inLDSysTraceSet);
  
  //LockTableUI LockTableUI1 = new LockTableUI();
  String busiName="pubfunLockTableUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
   
  if (!tBusinessDelegate.submitData(VData3, request.getParameter("Action"),busiName)) {
    VData rVData = tBusinessDelegate.getResult();
    loggerDebug("UnLockTable","LockTable Failed! " + (String)rVData.get(0));
  }
  else {
    loggerDebug("UnLockTable","UnLockTable Succed!");
  }
%>

<script>
  window.close();
</script>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�UWNotePadSave.jsp
//�����ܣ�
//�������ڣ�2002-11-18 11:10:36
//������  ���� ��
//���¼�¼��  ������ zhangrong   �������� 2004.11.20    ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  
<%
  loggerDebug("UWNotePadSave","\n\n---UWNotePadSave Start---");
  loggerDebug("UWNotePadSave","PrtNo:" + request.getParameter("PrtNo"));
  loggerDebug("UWNotePadSave","ContNo:" + request.getParameter("ContNo"));
  loggerDebug("UWNotePadSave","OperatePos:" + request.getParameter("OperatePos"));

  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  
  LCNotePadSchema tLCNotePadSchema = new LCNotePadSchema();
  tLCNotePadSchema.setContNo(request.getParameter("ContNo"));
  tLCNotePadSchema.setPrtNo(request.getParameter("PrtNo"));
  tLCNotePadSchema.setOperatePos(request.getParameter("OperatePos"));
  tLCNotePadSchema.setNotePadCont(request.getParameter("Content"));
  LCNotePadSet inLCNotePadSet = new LCNotePadSet();
  inLCNotePadSet.add(tLCNotePadSchema);

  VData inVData = new VData();
  inVData.add(inLCNotePadSet);
  inVData.add(tGlobalInput);
  
  String Content = "";
  String FlagStr = "";
  
  UWNotePadUI UWNotePadUI1 = new UWNotePadUI();

  if (!UWNotePadUI1.submitData(inVData, "INSERT")) {
    VData rVData = UWNotePadUI1.getResult();
    Content = " ����ʧ�ܣ�ԭ����:" + (String)rVData.get(0);
  	FlagStr = "Fail";
  }
  else {
    Content = " ����ɹ�! ";
  	FlagStr = "Succ";
  }

	loggerDebug("UWNotePadSave",Content + "\n" + FlagStr + "\n---UWNotePadSave End---\n\n");
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
	parent.fraInterface.queryClick();
</script>
</html>


<%
//�������ƣ�GEdorTypeNISubmit.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��Minim
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=gb2312"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
 String busiName="bqgrpGrpEdorNIDetailUI";
 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//����Ҫִ�еĶ�������ӣ��޸�
String FlagStr = "";
String Content = "";
  //����������Ϣ
  //loggerDebug("GEdorTypeNISubmit","=====This is GEdorTypeNISubmit.jsp=====\n");

GlobalInput tGlobalInput = new GlobalInput();
tGlobalInput = (GlobalInput)session.getValue("GI");
LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();

String transact = request.getParameter("fmtransact");
String EdorNo = request.getParameter("EdorNo");
String EdorAcceptNo = request.getParameter("EdorAcceptNo");
String EdorType = request.getParameter("EdorType");
String GrpContNo = request.getParameter("GrpContNo");

tLPGrpEdorItemSchema.setEdorNo(EdorNo);
tLPGrpEdorItemSchema.setEdorType(EdorType);
tLPGrpEdorItemSchema.setGrpContNo(GrpContNo);
tLPGrpEdorItemSchema.setEdorAcceptNo(EdorAcceptNo);

VData tVData = new VData();
tVData.add(tGlobalInput);
tVData.add(tLPGrpEdorItemSchema);	
  	
  	if (!tBusinessDelegate.submitData(tVData, transact,busiName))
  	{
  	  VData rVData = tBusinessDelegate.getResult();
  	  //loggerDebug("GEdorTypeNISubmit","Submit Failed! " + tBusinessDelegate.getCErrors().getErrContent());
  	  Content = transact + "ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
  	  FlagStr = "Fail";
  	}
  	else 
  	{
  	  //loggerDebug("GEdorTypeNISubmit","Submit Succed!");
  	  Content = "����ɹ���";
  	  FlagStr = "Success";  	
  	}
	
%>
<html>
<script language="javascript">
  parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>


<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�FIRuleDealErrLogSave.jsp
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
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
 loggerDebug("FIRuleDealErrLogSave","�õ���ˮ����" + strDelErrSerialNo);

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
     //����ɹ�
     loggerDebug("FIRuleDealErrLogSave","Del Succ");
     Content = " ����ɹ�! ";
     FlagStr = "Succ";
  }else{
  	loggerDebug("FIRuleDealErrLogSave","Del Fail");
  	Content = " ����ʧ�ܣ�ԭ����:" + blBusinessDelegate.getCErrors().getFirstError();
    FlagStr = "Fail";
  }

%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
</script>
</html>


<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GrpContCarAllDelSave.jsp
//�����ܣ�
//�������ڣ�2006-10-23 15:12:33
//������ ��chenrong
//���¼�¼�� ������  ��������   ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.LCGrpContSchema"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
//������Ϣ������У�鴦��
//�������
LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
//GrpContCarAllDelUI tGrpContCarAllDelUI = new GrpContCarAllDelUI();

//�������
CErrors tError = null;

String FlagStr = "Fail";
String Content = "";

//ȫ�ֱ���
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

loggerDebug("GrpContCarAllDelSave","begin ...");

String tGrpContNo = request.getParameter("ProposalGrpContNo");	//�����ͬ����

//���ռƻ�������Ϣ
tLCGrpContSchema.setGrpContNo(tGrpContNo);

// ׼���������� VData
VData tVData = new VData();
FlagStr="";

tVData.add(tG);
tVData.add(tLCGrpContSchema);
String busiName="tbGrpContCarAllDelUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
try{
	loggerDebug("GrpContCarAllDelSave","this will save the data!!!");
	tBusinessDelegate.submitData(tVData,"",busiName);
}
catch(Exception ex){
	Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	FlagStr = "Fail";
}

if (!FlagStr.equals("Fail")){
	tError = tBusinessDelegate.getCErrors();
	if (!tError.needDealError()){
		Content = " ����ɹ�! ";
		FlagStr = "Succ";
	}
	else{
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

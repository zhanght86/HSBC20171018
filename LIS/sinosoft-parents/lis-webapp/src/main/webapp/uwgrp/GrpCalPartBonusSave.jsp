<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�CutBonusChk.jsp
//�����ܣ��ֺ촦��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
  
<!--�û�У����-->
<%@page import="com.sinosoft.lis.tbgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>

<%
	loggerDebug("GrpCalPartBonusSave","------------ start save.jsp ----------");
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

try
{
	GlobalInput tG = (GlobalInput)session.getValue("GI");
	if(tG == null) 
	{
		out.println("session has expired");
		return;
	}
	
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("FiscalYear",request.getParameter("FiscalYear"));
	tTransferData.setNameAndValue("GrpPolNo",request.getParameter("GrpPolNo"));
	tTransferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
	tTransferData.setNameAndValue("BDate",request.getParameter("BDate"));
	tTransferData.setNameAndValue("EDate",request.getParameter("EDate"));
	tTransferData.setNameAndValue("PolNo",request.getParameter("PolNo"));

	VData tVData = new VData();
	tVData.add( tG );
	tVData.add( tTransferData );
	
	GrpAssignBonus tGrpAssignBonus = new GrpAssignBonus();
	if (!tGrpAssignBonus.submitData(tVData,"CALC||PART"))
	{			
			Content = "����������ɣ���������������鿴������Ϣ��!";
			FlagStr = "Fail";
	}
	else
	{
			Content = "�����������! ";
			FlagStr = "Succ";
	}
}
catch(Exception e)
{
	e.printStackTrace();
	Content = Content.trim() +" ��ʾ:�쳣�˳�.";
}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

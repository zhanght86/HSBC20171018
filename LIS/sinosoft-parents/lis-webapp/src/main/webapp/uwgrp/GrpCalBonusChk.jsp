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
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>

<%
  
  String busiName="tbgrpGrpAssignBonus";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
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
	tTransferData.setNameAndValue("GrpContNo",request.getParameter("GrpContNo1"));
	tTransferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
	tTransferData.setNameAndValue("BDate",request.getParameter("BDate"));
	tTransferData.setNameAndValue("EDate",request.getParameter("EDate"));
	tTransferData.setNameAndValue("ContNo","");                           //�ýӿ�Ϊ���ű����ֺ������

	VData tVData = new VData();
	tVData.add( tG );
	tVData.add( tTransferData );
	
 	String opertype = request.getParameter("opertype");
 	loggerDebug("GrpCalBonusChk","Operate : " + opertype);
	
	if (tBusinessDelegate.submitData(tVData,opertype,busiName) == false)
	{			
			Content = "��������ʧ�ܣ�" + tBusinessDelegate.getCErrors().getFirstError();
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
	parent.fraInterface.initPolGrid();
</script>
</html>

<!---###��ӡԤ��ҳ���� [��ӡ��ť]�����ļ������ڴ�ӡ��ɺ� �޸ġ���ӡ������Ĵ�ӡ״̬ ##--->
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--�û�У����-->
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.certify.*"%>
<%@page import="com.sinosoft.utility.*"%>

<%
	GlobalInput tGI = new GlobalInput();
	tGI=(GlobalInput)session.getValue("GI");
	String FlagStr = "";
	String Content = "";
	String PrintNo=(String)session.getValue("PrtSeq");
	loggerDebug("LLClaimoperPrintTable","################PrtSeq:#############" + PrintNo) ;
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("PrtSeq",PrintNo);

	VData tVData = new VData();
	tVData.add(tTransferData);
	tVData.add(tGI);
	ClaimPrintManageBL tClaimPrintManageBL = new ClaimPrintManageBL();
	if(!tClaimPrintManageBL.submitData(tVData,""))
	{
		FlagStr="Fail";
		Content="���´�ӡ����ʧ�ܣ�";
		loggerDebug("LLClaimoperPrintTable","�ύʧ��");
	}
	else
	{	
		FlagStr="Succ";
		Content="���´�ӡ���ݳɹ���";
		loggerDebug("LLClaimoperPrintTable","���³ɹ�");
	}
%>

<html>
<script language="javascript">
	if("<%=FlagStr%>"=='Fail')
	{
		alert("���´�ӡ�����ʧ�ܣ�");
	}
	window.returnValue="<%=FlagStr%>";
	window.close();
</script>
</html>


<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLSecondUWRiskChk.jsp
//�����ܣ����ֺ˱���Ϣ----�����ύ
//�������ڣ�2005-01-19 11:10:36
//������  ��zhangxing
//���¼�¼��  ������  yuejw  ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.claimgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
//  String Flag = request.getParameter("flag");
	GlobalInput tGI = new GlobalInput();
	tGI=(GlobalInput)session.getValue("GI");
	if(tGI == null)
	{
		System.out.println("session has expired");
		return;
	}
	LLUWMasterSchema tLLUWMasterSchema = new LLUWMasterSchema();
	tLLUWMasterSchema.setCaseNo(request.getParameter("CaseNo"));
	tLLUWMasterSchema.setBatNo(request.getParameter("BatNo"));
	tLLUWMasterSchema.setPolNo(request.getParameter("PolNo"));
//	tLLUWMasterSchema.setProposalNo(request.getParameter("PolNo"));
	tLLUWMasterSchema.setPassFlag(request.getParameter("UWState")); //���ֺ˱����� 
	tLLUWMasterSchema.setUWIdea(request.getParameter("UWIdea")); //�˱����
	try
	{
		// ׼���������� VData
		VData tVData = new VData();
	//	FlagStr="";
		tVData.add(tGI);
		tVData.add(tLLUWMasterSchema);	
		LLSecondUWRiskUI tLLSecondUWRiskUI = new LLSecondUWRiskUI();
		 if(!tLLSecondUWRiskUI.submitData(tVData,""))
        {           
            Content = "�ύ������ʧ�ܣ�ԭ����: " + tLLSecondUWRiskUI.mErrors.getError(0).errorMessage;
            FlagStr = "Fail";
        }
		else 
		{
		    Content = " ����ɹ�! ";
		    FlagStr = "SUCC";
		}		
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Content = Content.trim()+".��ʾ���쳣��ֹ!";
	}

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�PlanManuNormChk.jsp
//�����ܣ��˹��˱����ս���¼�뱣��
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //�������
  CErrors tError = null;
  //CErrors tErrors = null;
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  if(tG == null) 
  {
  	out.println("session has expired");
	  return;
	}
  
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("GrpContNo",request.getParameter("GrpContNo"));
	tTransferData.setNameAndValue("ContNo",request.getParameter("ContNo"));
	tTransferData.setNameAndValue("ContPlanCode",request.getParameter("ContPlanCode"));
	tTransferData.setNameAndValue("PlanType",request.getParameter("PlanType"));
	tTransferData.setNameAndValue("InsuredNo",request.getParameter("InsuredNo"));
	tTransferData.setNameAndValue("UWState",request.getParameter("UWState"));
	tTransferData.setNameAndValue("UWIdea",request.getParameter("UWIdea"));
	
	
 try
  {
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(tG);
		// ���ݴ���
		//PlanUWManuNormGChkUI tPlanUWManuNormGChkUI   = new PlanUWManuNormGChkUI();
		
		String busiName="cbcheckPlanUWManuNormGChkUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		if (tBusinessDelegate.submitData(tVData,"INSERT",busiName) == false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " �˹��˱�ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{    
		   tError = tBusinessDelegate.getCErrors();
		   if (!tError.needDealError())
		   {                     
		    	Content = " �˹��˱������ɹ�!";
	      	FlagStr = "Succ";
		   }
	     else                                                              
	     {
	      	FlagStr = "Fail";
		   }
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

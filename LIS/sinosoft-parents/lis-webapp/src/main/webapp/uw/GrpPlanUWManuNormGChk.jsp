<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GrpPlanUWResulSave.jsp
//�����ܣ�����Լ�˹��˱��Լ����Ʒ����½���
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
	tTransferData.setNameAndValue("ContPlanCode",request.getParameter("ContPlanCode"));
	tTransferData.setNameAndValue("PlanType",request.getParameter("PlanType"));
	tTransferData.setNameAndValue("GPlanUWState",request.getParameter("GPlanUWState"));
	tTransferData.setNameAndValue("GPlanUWIdea",request.getParameter("GPlanUWIdea"));
	
try
{
		 // ׼���������� VData
		 VData tVData = new VData();
		 tVData.add(tTransferData);
		 tVData.add(tG);
		
	    //GrpPlanUWManuNormGChkUI tGrpPlanUWManuNormGChkUI  = new GrpPlanUWManuNormGChkUI();
	    
	    String busiName="cbcheckGrpPlanUWManuNormGChkUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	    
		 if (tBusinessDelegate.submitData(tVData,"",busiName) == false)
		 {
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			 for (int i = 0; i < n; i++)
			 Content = " �¼������ֱ�������ʧ��,ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			 FlagStr = "Fail";
		 }
		 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		 if (FlagStr == "Fail")
		 {
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
		    	Content = " ����ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " �¼������ֱ�������ʧ��,ԭ����:" + tError.getFirstError();
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


<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuSpecChk.jsp
//�����ܣ��˹��˱���Լ�б�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
//modify by zhangxing
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 
  <%@page import="com.sinosoft.workflowengine.*"%>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  
  boolean flag = true;
  GlobalInput tG = new GlobalInput();  
  tG=(GlobalInput)session.getValue("GI");  
  if(tG == null) {
		out.println("session has expired");
		return;
   } 
    //������Ϣ
  	LCCSpecSchema tLCCSpecSchema = new LCCSpecSchema();
  	LCIndUWMasterSchema tLCIndUWMasterSchema = new LCIndUWMasterSchema();
  	TransferData tTransferData = new TransferData();
  String tContNo = request.getParameter("ContNo");
	
    String tNeedPrintFlag = request.getParameter("NeedPrintFlag"); //2008-11-27 ln add
	String tRemark = request.getParameter("Remark");
	String tSpecReason = request.getParameter("SpecReason");
	String tPrtNo = request.getParameter("PrtNo");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	
	//String tPolNo = request.getParameter("PolNo");
	String tOperatetype = request.getParameter("operate");
	String tProposalContNo = request.getParameter("proposalContNo");
	String tSerialno = request.getParameter("serialno");
	String tInsuredNo = request.getParameter("InsuredNo");
	String tSpecType = request.getParameter("HealthSpecTemp");
	String tSpecCode = request.getParameter("SpecTemp");
	loggerDebug("UWChangeRiskPlanChk","PrtNo:"+tPrtNo);
	loggerDebug("UWChangeRiskPlanChk","ContNo:"+tContNo);
	loggerDebug("UWChangeRiskPlanChk","remark:"+tRemark);
	//loggerDebug("UWChangeRiskPlanChk","PolNo:"+tPolNo);
	loggerDebug("UWChangeRiskPlanChk","Operate:"+tOperatetype);
	loggerDebug("UWChangeRiskPlanChk","Proposalno:"+tProposalContNo);
	loggerDebug("UWChangeRiskPlanChk","Serialno:"+tSerialno);
	loggerDebug("UWChangeRiskPlanChk","InsuredNo:"+tInsuredNo);
	loggerDebug("UWChangeRiskPlanChk","SpecType:"+tSpecType);
	loggerDebug("UWChangeRiskPlanChk","SpecCode:"+tSpecCode);
	if (tContNo == "" || (tRemark == "" ) )
	{
		Content = "��¼�������ر�Լ����Ϣ��������ע��Ϣ!";
		FlagStr = "Fail";
		flag = false;
	}
	else
	{     
	      if(tContNo != null && tPrtNo != null && tMissionID != null && tSubMissionID != null)
	      {
		   //׼����Լ��Ϣ
		   	tLCCSpecSchema.setContNo(tContNo); 
		   	//tLCSpecSchema.setSpecType("1");
		   	tLCCSpecSchema.setProposalContNo(tProposalContNo);
		   	tLCCSpecSchema.setSpecContent(tRemark);
		   	tLCCSpecSchema.setSpecType(tSpecType);
		   	tLCCSpecSchema.setSpecCode(tSpecCode);
		   	tLCCSpecSchema.setNeedPrint(tNeedPrintFlag);

		   //׼����Լԭ��
		tLCIndUWMasterSchema.setSpecReason(tSpecReason);
		//tongmeng 2007-12-17 add
		//��¼��Լԭ��
		tLCCSpecSchema.setSpecReason(tSpecReason);	
    tLCCSpecSchema.setCustomerNo(tInsuredNo);            
		   } // End of if
		  else
		  {
			Content = "��������ʧ��!";
			flag = false;
		  }
	}
try
{
  	if (flag == true)
  	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add(tLCCSpecSchema);
		tVData.add( tLCIndUWMasterSchema );
		TransferData mTransferData = new TransferData();
		//tTransferData.setNameAndValue("PolNo",tPolNo);
		tTransferData.setNameAndValue("Operatetype",tOperatetype);
		tTransferData.setNameAndValue("ProposalContNo",tProposalContNo);
		tTransferData.setNameAndValue("Serialno",tSerialno);
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("CustomerNo",tInsuredNo);
		
		tVData.add(tTransferData);
		tVData.add( tG );
		
		// ���ݴ���
		UWSpecUI tUWSpecUI   = new UWSpecUI();
		if (!tUWSpecUI.submitData(tVData,""))
		  {     
		        
			int n = tUWSpecUI.mErrors.getErrorCount();
			Content = " �˱���Լʧ�ܣ�ԭ����: " + tUWSpecUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tUWSpecUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " �����˱���Լ�ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	FlagStr = "Fail";
		    }
		}
	} 
}
catch(Exception e)
{
	e.printStackTrace();
	Content = Content.trim()+"��ʾ���쳣��ֹ!";
}
%>                    
                 
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>


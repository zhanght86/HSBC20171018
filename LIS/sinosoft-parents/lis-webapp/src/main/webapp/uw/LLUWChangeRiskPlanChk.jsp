
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
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 
  <%@page import="com.sinosoft.workflowengine.*"%>
  <%@page import="com.sinosoft.service.*" %>
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
  	LLUWSpecMasterSchema tLLUWSpecMasterSchema = new LLUWSpecMasterSchema();
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
	String tClmNo = request.getParameter("ClmNo");
	String tBatNo = request.getParameter("BatNo");
	loggerDebug("LLUWChangeRiskPlanChk","PrtNo:"+tPrtNo);
	loggerDebug("LLUWChangeRiskPlanChk","ContNo:"+tContNo);
	loggerDebug("LLUWChangeRiskPlanChk","remark:"+tRemark);
	//loggerDebug("LLUWChangeRiskPlanChk","PolNo:"+tPolNo);
	loggerDebug("LLUWChangeRiskPlanChk","Operate:"+tOperatetype);
	loggerDebug("LLUWChangeRiskPlanChk","Proposalno:"+tProposalContNo);
	loggerDebug("LLUWChangeRiskPlanChk","Serialno:"+tSerialno);
	loggerDebug("LLUWChangeRiskPlanChk","InsuredNo:"+tInsuredNo);
	loggerDebug("LLUWChangeRiskPlanChk","SpecType:"+tSpecType);
	loggerDebug("LLUWChangeRiskPlanChk","SpecCode:"+tSpecCode);
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
		   	tLLUWSpecMasterSchema.setContNo(tContNo); 
		   	tLLUWSpecMasterSchema.setClmNo(tClmNo);
		   	//tLCSpecSchema.setSpecType("1");
		   	tLLUWSpecMasterSchema.setProposalContNo(tProposalContNo);
		   	tLLUWSpecMasterSchema.setSpecContent(tRemark);
		   	tLLUWSpecMasterSchema.setClmNo(tClmNo);
		   	tLLUWSpecMasterSchema.setBatNo(tBatNo);
		   	tLLUWSpecMasterSchema.setSpecType(tSpecType);
		   	tLLUWSpecMasterSchema.setSpecCode(tSpecCode);
		   	tLLUWSpecMasterSchema.setSerialNo(tSerialno);
		   	tLLUWSpecMasterSchema.setNeedPrint(tNeedPrintFlag);

		   //׼����Լԭ��
		tLCIndUWMasterSchema.setSpecReason(tSpecReason);
		//tongmeng 2007-12-17 add
		//��¼��Լԭ��
		tLLUWSpecMasterSchema.setSpecReason(tSpecReason);	
		tLLUWSpecMasterSchema.setCustomerNo(tInsuredNo);            
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
		tVData.add(tLLUWSpecMasterSchema);
		tVData.add( tLCIndUWMasterSchema );
		TransferData mTransferData = new TransferData();
		//tTransferData.setNameAndValue("PolNo",tPolNo);
		tTransferData.setNameAndValue("Operatetype",tOperatetype);
		tTransferData.setNameAndValue("ProposalContNo",tProposalContNo);
		tTransferData.setNameAndValue("SerialNo",tSerialno);
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("CustomerNo",tInsuredNo);
		tTransferData.setNameAndValue("ClmNo",tClmNo);
		tTransferData.setNameAndValue("BatNo",tBatNo);
		
		tVData.add(tTransferData);
		tVData.add( tG );
		
		// ���ݴ���
		/*LLUWSpecUI tLLUWSpecUI   = new LLUWSpecUI();
		if (!tLLUWSpecUI.submitData(tVData,""))
		  {     
		        
			int n = tLLUWSpecUI.mErrors.getErrorCount();
			Content = " �˱���Լʧ�ܣ�ԭ����: " + tLLUWSpecUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
		if (FlagStr == "Fail")
		{
		    tError = tLLUWSpecUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " �����˱���Լ�ɹ�! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	FlagStr = "Fail";
		    }
		}*/
		String busiName="LLUWSpecUI";
		  String mDescType="����";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			  if(!tBusinessDelegate.submitData(tVData,"",busiName))
			  {    
			       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
			       { 
						Content = mDescType+"ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
						FlagStr = "Fail";
				   }
				   else
				   {
						Content = mDescType+"ʧ��";
						FlagStr = "Fail";				
				   }
			  }
			  else
			  {
			     	Content = mDescType+"�ɹ�! ";
			      	FlagStr = "Succ";  
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


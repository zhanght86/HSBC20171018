<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLSecondUWSave.jsp
//�����ܣ���ͬ���˹��˱�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.workflow.claimgrp.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
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
		System.out.println("session has expired");
		return;
	}
	System.out.println("Start Save...");
	String tCaseNo = request.getParameter("CaseNo"); //�ⰸ��
	String tInsuredNo = request.getParameter("InsuredNo"); //�����˿ͻ���
	String tInsuredName = request.getParameter("InsuredName");	//����������
	String tSendUW[] = request.getParameterValues("LCContGridChk");
	String tContNo[] = request.getParameterValues("LCContGrid1"); //��ͬ��
	String tAppntNo[] = request.getParameterValues("LCContGrid2"); //Ͷ���˿ͻ�����
	String tAppntName[] = request.getParameterValues("LCContGrid3"); //Ͷ��������
	String tClaimRelFlag[] = request.getParameterValues("LCContGrid5"); //�ⰸ��ر�־
	String tHealthImpartNo1[] = request.getParameterValues("LCContGrid6"); //Ͷ���齡����֪��ѯ�ʺ�
	String tHealthImpartNo2[] = request.getParameterValues("LCContGrid7"); //��콡����֪��
	String tNoImpartDesc[] = request.getParameterValues("LCContGrid8"); //��Ӧδ��֪���
    String tChk[] = request.getParameterValues("InpLCContGridChk");; //������ʽ=�� Inp+MulLine������+Chk��
	LLCUWBatchSet tLLCUWBatchSet = new LLCUWBatchSet();
	if (tChk!=null&&tChk.length>0)
	{
		for(int index=0;index<tChk.length;index++)
		{
			if(tChk[index].equals("1")) 
			{         
				 LLCUWBatchSchema tLLCUWBatchSchema = new LLCUWBatchSchema();
				 tLLCUWBatchSchema.setCaseNo(tCaseNo); //�ⰸ��
				 tLLCUWBatchSchema.setInsuredNo(tInsuredNo); //�����˿ͻ���
				 tLLCUWBatchSchema.setInsuredName(tInsuredName); //����������
			     tLLCUWBatchSchema.setContNo(tContNo[index]); //��ͬ��
			     tLLCUWBatchSchema.setAppntNo(tAppntNo[index]); //Ͷ���˺���			     
			     tLLCUWBatchSchema.setAppntName(tAppntName[index]); //Ͷ��������
			     tLLCUWBatchSchema.setClaimRelFlag(tClaimRelFlag[index]); //�ⰸ��ر�־			     
			     tLLCUWBatchSchema.setHealthImpartNo1(tHealthImpartNo1[index]); //Ͷ���齡����֪��ѯ�ʺ�
			     tLLCUWBatchSchema.setHealthImpartNo2(tHealthImpartNo2[index]); //��콡����֪��
			     tLLCUWBatchSchema.setNoImpartDesc(tNoImpartDesc[index]); //��Ӧδ��֪���
			     tLLCUWBatchSchema.setRemark1(request.getParameter("Note")); //������Ŀǰ����״������ 
			     tLLCUWBatchSet.add( tLLCUWBatchSchema );
			}
		}          	
	}
  	
  	//Stringʹ��TransferData������ύ
    TransferData mTransferData = new TransferData();
    mTransferData.setNameAndValue("CaseNo",request.getParameter("CaseNo")); //�ⰸ��
    mTransferData.setNameAndValue("BatNo",""); //�������κ� ---��̨����   
    mTransferData.setNameAndValue("InsuredNo",request.getParameter("InsuredNo")); //�����˺���
    mTransferData.setNameAndValue("InsuredName",request.getParameter("InsuredName")); //����������   
    mTransferData.setNameAndValue("ClaimRelFlag",""); //��ر�־----��̨����
    mTransferData.setNameAndValue("MngCom",request.getParameter("ManageCom")); //����
    
	try
	{
		// ׼���������� VData
		VData tVData = new VData();
		tVData.add(tLLCUWBatchSet);
		tVData.add(mTransferData);		
		tVData.add(tG);
		String wFlag="Create||ScondUWNode"; //�����ڵ�
//    	ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI();		
//        if(!tClaimWorkFlowUI.submitData(tVData,wFlag))
//        {           
//            Content = "�ύ������ʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//            FlagStr = "Fail";
//
//        }
String busiName="grpClaimWorkFlowUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
if(!tBusinessDelegate.submitData(tVData,wFlag,busiName))
{    
    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
    { 
		Content = "�ύ������ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{
		Content = "�ύ������ʧ��";
		FlagStr = "Fail";				
	}
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


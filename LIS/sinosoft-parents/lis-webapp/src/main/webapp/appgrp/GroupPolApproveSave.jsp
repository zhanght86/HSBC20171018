<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GroupPolApproveSave.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��HST
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.workflow.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
	            
	TransferData mTransferData = new TransferData();
  mTransferData.setNameAndValue("GrpContNo",request.getParameter("ProposalGrpContNo"));
  mTransferData.setNameAndValue("PrtNo",request.getParameter("PrtNo"));
  mTransferData.setNameAndValue("SaleChnl",request.getParameter("SaleChnl"));
  mTransferData.setNameAndValue("ManageCom",request.getParameter("ManageCom"));
  mTransferData.setNameAndValue("GrpName",request.getParameter("GrpName"));
  mTransferData.setNameAndValue("CValiDate",request.getParameter("CValiDate"));
  mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
  mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
  //mTransferData.setNameAndValue("Operator",tG.Operator); //add by yaory
  //mTransferData.setNameAndValue("MakeDate",PubFun.getCurrentDate()); //add by yaory
  //mTransferData.setNameAndValue("AgentCode",request.getParameter("AgentCode")); //add by yaory
	
	loggerDebug("GroupPolApproveSave","GrpContNo   =="+request.getParameter("ProposalGrpContNo"));
	loggerDebug("GroupPolApproveSave","PrtNo       =="+request.getParameter("PrtNo"));
	loggerDebug("GroupPolApproveSave","SaleChnl    =="+request.getParameter("SaleChnl"));
	loggerDebug("GroupPolApproveSave","ManageCom   =="+request.getParameter("ManageCom"));
	loggerDebug("GroupPolApproveSave","GrpName     =="+request.getParameter("GrpName"));
	loggerDebug("GroupPolApproveSave","CValiDate   =="+request.getParameter("CValiDate"));
	loggerDebug("GroupPolApproveSave","MissionID   =="+request.getParameter("MissionID"));
	loggerDebug("GroupPolApproveSave","SubMissionID=="+request.getParameter("SubMissionID"));
  	//������Ϣ
	LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
    String tProposalGrpContNo = request.getParameter("ProposalGrpContNo");
    String flag=request.getParameter("Flag1");
     
	if( tProposalGrpContNo!= null&flag!=null )
	{    
	 loggerDebug("GroupPolApproveSave","jsp��GrpPolNo:"+tProposalGrpContNo);

	// ׼���������� VData
	VData tVData = new VData();
	tVData.add( mTransferData );
	tVData.add( tG );
	
	// ���ݴ���
	GrpTbWorkFlowUI tGrpTbWorkFlowUI= new GrpTbWorkFlowUI();
	if (tGrpTbWorkFlowUI.submitData( tVData, "0000002001" ) == false)
	{
		Content = " ����ʧ�ܣ�ԭ����: " + tGrpTbWorkFlowUI.mErrors.getError(0).errorMessage;
		FlagStr = "Fail";
	}
	//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
	
	if (FlagStr == "Fail")
	{
	    tError = tGrpTbWorkFlowUI.mErrors;
	    if (!tError.needDealError())
	    {
	                                
	    	Content = " ���˳ɹ�! ";
	    	FlagStr = "Succ";
	    	
	    	//tongmeng 2008-07-23 modify
	    	//�����ٱ�
	    	/*
	    	      //���˳ɹ�������ٱ��ճ���
	    	      //ReLifeNeed tReLifeNeed=new ReLifeNeed();
	    	      //����׼��
	    	      VData tmpvdata=new VData();
              TransferData tTransferData = new TransferData(); //����ҳ����Ϣ	    	
              tTransferData.setNameAndValue("ManageCom",request.getParameter("ManageCom"));
              tTransferData.setNameAndValue("Operator",tG.Operator);        
              tTransferData.setNameAndValue("OtherNo",request.getParameter("PrtNo"));
              tmpvdata.add(tTransferData);
              
	    	      if (!tReLifeNeed.submitdata(tmpvdata,"1"))   //1---��ʾ�±�
	    	      {
	    	      	//�ٱ��ռ���ʧ��,UNDO���˹���,�ȴ��û����¸���
	    	        tReLifeNeed.UNDOApp();
	    	      
	    	      }
	    	      else//�ٱ��ռ���ɹ�
	    	      {
	    	        Content = " ���˳ɹ�,�ٱ��������ύ�ɹ�! ";
	    	        FlagStr = "Succ";
	    	      }
	    	      */
	    	
	    }
	    else                                                                           
	    {
	    	Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
	    	FlagStr = "Fail";
	    }
	}
	}
%>                      
<html>
<script language="javascript">
try {
        
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>")
	} catch(ex) {}
</script>
</html>

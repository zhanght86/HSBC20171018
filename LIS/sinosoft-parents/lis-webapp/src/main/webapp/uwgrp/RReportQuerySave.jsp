<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ManuHealthQChk.jsp
//�����ܣ��˹��˱�������ϲ�ѯ
//�������ڣ�2005-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tbgrp.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  String tGrpContNo = "000000000000";
  String tPrtNo = request.getParameter("ContNo");
 	String tContNo = request.getParameter("ContNo");
 	String tPrtSeq=request.getParameter("PrtSeq");

 	String tProposalContNo =request.getParameter("ContNo");
 	
 	String tReplyContente = request.getParameter("ReplyContente");
 	String tRReportFee = request.getParameter("RReportFee");
 	loggerDebug("RReportQuerySave","ReplyContente = :"+tReplyContente);
 	
 	String tMissionID = request.getParameter("MissionID");

 	String tSubMissionID = request.getParameter("SubMissionID");
  
 	
 	
 	
 	LCRReportSchema tLCRReportSchema = new LCRReportSchema();
 	LCRReportSet tLCRReportSet = new LCRReportSet();
 	tLCRReportSchema.setReplyContente(tReplyContente);
 	tLCRReportSchema.setRReportFee(tRReportFee);
 	tLCRReportSchema.setProposalContNo(tContNo);
 	tLCRReportSchema.setPrtSeq(tPrtSeq);

 	
 	TransferData mTransferData = new TransferData();
 	
 	mTransferData.setNameAndValue("LCRReportSchema",tLCRReportSchema);
 	
 	mTransferData.setNameAndValue("PrtNo",tPrtNo);
 	mTransferData.setNameAndValue("ContNo",tContNo);
 	mTransferData.setNameAndValue("PrtSeq",tPrtSeq);
 
 	
 	mTransferData.setNameAndValue("MissionID",tMissionID);
 	mTransferData.setNameAndValue("SubMissionID",tSubMissionID);


		
		// ׼���������� VData
		VData tVData = new VData();
		
		FlagStr="";
  	
		tVData.add(tG);

		tVData.add(mTransferData);
	
		
		String busiName="tbTbWorkFlowUI";
		   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

     // TbWorkFlowUI  tTbWorkFlowUI = new TbWorkFlowUI();
		
		try{
			loggerDebug("RReportQuerySave","this will save the data!!!");

			tBusinessDelegate.submitData(tVData,"0000001120",busiName);
		}
		catch(Exception ex){
			Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
			FlagStr = "Fail";
		}
		
		if (!FlagStr.equals("Fail")){
			tError = tBusinessDelegate.getCErrors();
			if (!tError.needDealError()){
				Content = " ����ɹ�! ";
				FlagStr = "Succ";
			}
			else{
				Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
				FlagStr = "Fail";
		    	}
		}

	

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

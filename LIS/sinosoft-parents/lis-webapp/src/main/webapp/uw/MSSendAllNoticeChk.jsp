<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWSendPrintChk.jsp
//�����ܣ��ʹ�ӡ����
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.f1print.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>

  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
  
<%
  //�������
  CErrors tError = null;
  //CErrors tErrors = null;
  String FlagStr = "Fail";
  String Content = "����֪ͨ�鷢��ʧ��,ԭ����:";

	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  //tongmeng 2007-11-09 modify
  //�޸�Ϊͳһ���ź˱�֪ͨ��,һ��������ԼΪ��,�����ٶ��������ô˹��ܳ������޸�.
  //У�鴦��
  //���ݴ����	
			
  	//������Ϣ
  	// Ͷ�����б�	
	String tProposalNo = request.getParameter("ContNo");
	String tOtherNoType = "02";
	
	String tCode = request.getParameter("hiddenNoticeType");
	
	loggerDebug("MSSendAllNoticeChk","tCode="+ tCode);
	String tMissionID=request.getParameter("MissionID");
	String tSubMissionID=request.getParameter("SubMissionID");
  loggerDebug("MSSendAllNoticeChk","@@@@@@@@@@@@@@@@tSubMissionID:"+tSubMissionID+"################");
	boolean flag = true;
try
{
  	if (flag == true)
  	{
  		
		// ׼���������� VData
		VData tVData = new VData();
		//tVData.add( tLOPRTManagerSchema);
		tVData.add( tG );
		TransferData mTransferData = new TransferData();
		mTransferData.setNameAndValue("ContNo",tProposalNo);
		mTransferData.setNameAndValue("NoticeType",tCode);
		//mTransferData.setNameAndValue("MissionID",tMissionID);
			//mTransferData.setNameAndValue("SubMissionID",tSubMissionID); 
			 


			tVData.add(mTransferData);
		 	String busiName="cbcheckUWSendNoticeBL";
	        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	        
	        if(tBusinessDelegate.submitData(tVData,"",busiName)==false)
	        {
	        	 int n = tBusinessDelegate.getCErrors().getErrorCount();
	             Content = "����ʧ�ܣ�ԭ����:";
	             Content = Content + tBusinessDelegate.getCErrors().getError(0).errorMessage;
	             FlagStr = "Fail";
	        }else{
	        	
	        }
	      //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
			 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
        if (FlagStr == "Fail")
        {
            tError = tBusinessDelegate.getCErrors();
            if (!tError.needDealError())
            {
            	Content = "�����ɹ�! ";
               
                FlagStr = "Succ";
            }
            else
            {
                Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
                FlagStr = "Fail";
            }
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

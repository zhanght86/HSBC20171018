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
  <%@page import="com.sinosoft.workflow.claim.*"%>
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
    String tClmNo = request.getParameter("ClmNo");
    String tBatNo = request.getParameter("BatNo");
	//String tOtherNoType = "02";
	
	String tCode = request.getParameter("hiddenNoticeType");
	
	loggerDebug("LLSendAllNoticeChk","tCode="+ tCode);
	String tMissionID=request.getParameter("MissionID");
	String tSubMissionID=request.getParameter("SubMissionID");
  loggerDebug("LLSendAllNoticeChk","@@@@@@@@@@@@@@@@tSubMissionID:"+tSubMissionID+"################");
	boolean flag = true;
try
{
  	if (flag == true)
  	{
  		/**********������������Ϊѭ������5510������contno�������ﲻ��Ҫcontno��Ϊ����*************/
  	  	String tSQL = "select activityid,missionid,SubMissionID from lwmission where 1=1"
  	  				+" and activityid in(select activityid from lwactivity where functionid='10030019') "
  	  				+" and missionprop1='"+tClmNo+"'";// and missionprop3='"+tBatNo+"'
  			SSRS tSSRS = new SSRS();
  			ExeSQL tExeSQL = new ExeSQL();
  			tSSRS = tExeSQL.execSQL(tSQL);
  			if(tSSRS.MaxRow==0){
  				FlagStr = "Fail";
  				Content = " ����ʧ��,ԭ����:�����㹤�����ڵ㣡";
  			}else{
  				String tActivityID= tSSRS.GetText(1,1);
  				String tMissionId = tSSRS.GetText(1,2);
  				String tSubMissionId = tSSRS.GetText(1,3);
  				// ׼���������� VData
  				VData tVData = new VData();
  				//tVData.add( tLOPRTManagerSchema);
  				tVData.add( tG );
  				TransferData mTransferData = new TransferData();
  				mTransferData.setNameAndValue("ContNo",tProposalNo);
  				mTransferData.setNameAndValue("NoticeType",tCode);
  				mTransferData.setNameAndValue("ActivityID",tActivityID); 
  				mTransferData.setNameAndValue("MissionID",tMissionId);
  	   			mTransferData.setNameAndValue("SubMissionID",tSubMissionId); 
  	   			mTransferData.setNameAndValue("mSubMissionID",tSubMissionID); 
  	   			mTransferData.setNameAndValue("ClmNo",tClmNo); 
  	   			mTransferData.setNameAndValue("BatNo",tBatNo); 
  	    
  	    		tVData.add(mTransferData);
  	    		String busiName="tWorkFlowUI";
  	    		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  	    		  if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
  	    		  {    
  	    		       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
  	    		       { 
  	    					Content = "�ύ������ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
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
  	    		     	Content = "�����ύ�ɹ�! ";
  	    		      	FlagStr = "Succ";  
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

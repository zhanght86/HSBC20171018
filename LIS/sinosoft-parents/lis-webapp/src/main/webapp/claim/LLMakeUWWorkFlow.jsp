<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�BQManuUWChk.jsp
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.claim.*"%>
  <%@page import="com.sinosoft.workflow.claim.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //�������
  CErrors tError = null;
  //CErrors tErrors = new CErrors();
  String FlagStr = "Fail";
  String Content = "";
	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  
  //У�鴦��
  //���ݴ����
  
  	//������Ϣ
  	// Ͷ�����б�

	String tMissionID = request.getParameter("MissionID");
  	String tSubMissionID = request.getParameter("SubMissionID");
  	String tContNo = request.getParameter("ContNo");
  	String tClmNo  =request.getParameter("CaseNo");
  	String tBatNo  =request.getParameter("BatNo");
  	String tInsuredNo  =request.getParameter("tInsuredNo");
  	loggerDebug("LLMakeUWWorkFlow","tClmNo=="+tClmNo+"  tContNo=="+tContNo);
  	String tCreateFlag ="0";
	boolean flag = false;
  	TransferData tTransferData = new TransferData();
  	LCContSchema tLCContSchema = new LCContSchema();
	if (!tContNo.equals("") )
	{
		loggerDebug("LLMakeUWWorkFlow","ContNo:"+tContNo);
	  		
		tLCContSchema.setContNo( tContNo );
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("ClmNo",tClmNo);
		tTransferData.setNameAndValue("BatNo",tBatNo);
		tTransferData.setNameAndValue("CustomerNo",tInsuredNo);
		tTransferData.setNameAndValue("MissionID",tMissionID);
		tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
		    
	    //tTransferData.setNameAndValue("CreateFlag",tCreateFlag);
		flag = true;

}
		try
		{
		loggerDebug("LLMakeUWWorkFlow","flag=="+flag);
		  	if (flag == true)
		  	{
		  
				// ׼���������� VData
				VData tVData = new VData();
				tVData.add( tTransferData );
				tVData.add( tG );
				// ���ݴ���
				/*ClaimWorkFlowUI tClaimWorkFlowUI   = new ClaimWorkFlowUI();
				if (tClaimWorkFlowUI.submitData(tVData,"0000005505") == false)
				{
					int n = tClaimWorkFlowUI.mErrors.getErrorCount();
					loggerDebug("LLMakeUWWorkFlow","n=="+n);
					for (int j = 0; j < n; j++)
					loggerDebug("LLMakeUWWorkFlow","Error: "+tClaimWorkFlowUI.mErrors.getError(j).errorMessage);
					Content = " ��ʼ�������ڵ�ʧ�ܣ�ԭ����: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
					FlagStr = "Fail";
				}*/
				String busiName="ClaimWorkFlowUI";
					BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
					  if(!tBusinessDelegate.submitData(tVData,"0000005505",busiName))
					  {    
					       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
					       { 
								Content = "��ʼ�������ڵ�ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getFirstError();
								FlagStr = "Fail";
						   }
						   else
						   {
								Content = "��ʼ�������ڵ�ʧ��";
								FlagStr = "Fail";				
						   }
					  }
				//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
				if (FlagStr == "Fail")
				{
				    //tError = tClaimWorkFlowUI.mErrors;
				    tError = tBusinessDelegate.getCErrors();
				    //tErrors = tTbWorkFlowUI.mErrors;
				    Content = "";
				    if (!tError.needDealError())
				    {                          
				    	//Content = "�Զ��˱��ɹ�!";
				    	int n = tError.getErrorCount();
		    			if (n > 0)
		    			{    			      
					      for(int j = 0;j < n;j++)
					      {
					        //tError = tErrors.getError(j);
					        Content = Content.trim() +j+". "+ tError.getError(j).errorMessage.trim()+".";
					      }
					    }
		
				    	FlagStr = "Succ";
				    }
				    else                                                                           
				    {
				    	int n = tError.getErrorCount();
				    	//Content = "�Զ��˱�ʧ��!";
		    			if (n > 0)
		    			{
					      for(int j = 0;j < n;j++)
					      {
					        //tError = tErrors.getError(j);
					        Content = Content.trim() +j+". "+ tError.getError(j).errorMessage.trim()+".";
					      }
							}
				    	FlagStr = "Fail";
				    }
				}
			}
			else
			{
				Content = "��ѡ�񱣵���";
			}  
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Content = Content.trim() +" ��ʾ:�쳣�˳�.";
		}
	
	
loggerDebug("LLMakeUWWorkFlow","AUTO UW END!");
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");
	//parent.fraInterface.initPolGrid();
	<%
	//parent.fraInterface.fm.submit();
	%>
</script>
</html>

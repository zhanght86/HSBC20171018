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
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.claim.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
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
	LPContSet tLPContSet = new LPContSet();

	String tMissionID = request.getParameter("MissionID");
  	String tSubMissionID = request.getParameter("SubMissionID");
  	String tContNo = request.getParameter("ContNo");
  	String tCaseNo  =request.getParameter("CaseNo");
  	String tBatNo  =request.getParameter("BatNo");
  	loggerDebug("LLMakeUWMaster","clmno=="+tCaseNo+"  tContNo=="+tContNo+"   tBatNo="+tBatNo);
  	String tCreateFlag ="0";
	boolean flag = false;
  	TransferData tTransferData = new TransferData();
  	LCContSchema tLCContSchema = new LCContSchema();
	if (!tContNo.equals("") )
	{
		loggerDebug("LLMakeUWMaster","ContNo:"+tContNo);
	  		
		tLCContSchema.setContNo( tContNo );
		tTransferData.setNameAndValue("ContNo",tContNo);
		tTransferData.setNameAndValue("CaseNo",tCaseNo);
		tTransferData.setNameAndValue("BatNo",tBatNo);
		    
	    //tTransferData.setNameAndValue("CreateFlag",tCreateFlag);
		flag = true;

}
		try
		{
		loggerDebug("LLMakeUWMaster","flag=="+flag);
		  	if (flag == true)
		  	{
		  
				// ׼���������� VData
				VData tVData = new VData();
				tVData.add( tTransferData );
				tVData.add( tG );
				// ���ݴ���
				LLMakeUWMasterUI tLLMakeUWMasterUI   = new LLMakeUWMasterUI();
				if (tLLMakeUWMasterUI.submitData(tVData,"0000000005") == false)
				{
					int n = tLLMakeUWMasterUI.mErrors.getErrorCount();
					loggerDebug("LLMakeUWMaster","n=="+n);
					for (int j = 0; j < n; j++)
					loggerDebug("LLMakeUWMaster","Error: "+tLLMakeUWMasterUI.mErrors.getError(j).errorMessage);
					Content = " �Զ��˱�ʧ�ܣ�ԭ����: " + tLLMakeUWMasterUI.mErrors.getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
				if (FlagStr == "Fail")
				{
				    tError = tLLMakeUWMasterUI.mErrors;
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
	
	
loggerDebug("LLMakeUWMaster","AUTO UW END!");
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

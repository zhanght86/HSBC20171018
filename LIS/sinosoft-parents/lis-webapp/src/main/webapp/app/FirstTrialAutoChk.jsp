<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�FirstTrialAutoChk.jsp
//�����ܣ������Զ��ڵ�����
//�������ڣ�2005-03-19 11:10:36
//������  ��tuqiang
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
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
	LCContSet tLCContSet = new LCContSet();

	String tMissionID = request.getParameter("MissionID");
  String tSubMissionID = request.getParameter("SubMissionID");
  String tContNo = request.getParameter("ContNo");
  String tPrtNo = request.getParameter("PrtNo");
	boolean flag = false;
  TransferData tTransferData = new TransferData();
  LCContSchema tLCContSchema = new LCContSchema();
  if (!tContNo.equals("") )
		{	
		    tLCContSchema.setContNo( tContNo );
		    tTransferData.setNameAndValue("ContNo",tContNo);
		    tTransferData.setNameAndValue("PrtNo",tPrtNo);
	    	tTransferData.setNameAndValue("MissionID",tMissionID);
	      tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
	      tTransferData.setNameAndValue("OldPrtSeq","0");
		    flag = true;

		}
		try
		{
		  	if (flag == true)
		  	{
				// ׼���������� VData
				VData tVData = new VData();
				tVData.add( tTransferData );
				tVData.add( tG );
				// ���ݴ���
				TbWorkFlowUI tTbWorkFlowUI   = new TbWorkFlowUI();
				if (tTbWorkFlowUI.submitData(tVData,"9999991061") == false)
				{
					int n = tTbWorkFlowUI.mErrors.getErrorCount();
					loggerDebug("FirstTrialAutoChk","n=="+n);
					for (int j = 0; j < n; j++)
					loggerDebug("FirstTrialAutoChk","Error: "+tTbWorkFlowUI.mErrors.getError(j).errorMessage);
					Content = " �Զ��˱�ʧ�ܣ�ԭ����: " + tTbWorkFlowUI.mErrors.getError(0).errorMessage;
					FlagStr = "Fail";
				}
				//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
				if (FlagStr == "Fail")
				{
				    tError = tTbWorkFlowUI.mErrors;
				    //tErrors = tTbWorkFlowUI.mErrors;
				    Content = " �Զ��˱��ɹ�! ";
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
	
loggerDebug("FirstTrialAutoChk","AUTO UW END!");
%>                      
<html>

</html>

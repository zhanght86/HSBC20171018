<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ManuUWChk.jsp
//�����ܣ������˹��˱�
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
  <%@page import="com.sinosoft.workflow.xb.*"%>
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
  //String tPrtNo = request.getParameter("PrtNo");
  String tCreateFlag ="0";
	boolean flag = false;
  TransferData tTransferData = new TransferData();
  LCContSchema tLCContSchema = new LCContSchema();
  if (!tContNo.equals("") )
   {
			loggerDebug("RnewUWChk","ContNo:"+tContNo);
	  		
		    tLCContSchema.setContNo( tContNo );
		    tTransferData.setNameAndValue("ContNo",tContNo);
		//    tTransferData.setNameAndValue("PrtNo",tPrtNo);
	    	tTransferData.setNameAndValue("MissionID",tMissionID);
	     	tTransferData.setNameAndValue("SubMissionID",tSubMissionID);
		    flag = true;

   }
	try
	{
		  loggerDebug("RnewUWChk","flag=="+flag);
		  if (flag == true)
		  {
		      String   tSQL =
	        //ȡͶ��������	
	        " select appntno,'A' from lccont a where contno='"+tContNo+"' "
					+ " and not exists " 
					+ " (select 1 from lwmission where missionid='"+tMissionID+"' and activityid='0000007002' "
					+ " and missionprop15=a.appntno and missionprop16='A') " 
					+ " union "
		            //ȡ����������
					+ " select insuredno,'I' from lccont a where contno='"+tContNo+"'"
					+ " and not exists " 
					+ " (select 1 from lwmission where missionid='"+tMissionID+"' and activityid='0000007002' "
					+ " and missionprop15=a.insuredno and missionprop16='I') " 
					;
         loggerDebug("RnewUWChk","####tSQL:"+tSQL);
				 SSRS tSSRS = new SSRS();
				 ExeSQL tExeSQL = new ExeSQL();
				 tSSRS = tExeSQL.execSQL(tSQL);
	       for (int i = 1; i <= tSSRS.getMaxRow(); i++) 
			   {
							// ׼���������� VData
							tCreateFlag="1";
							String tCustomerNo = tSSRS.GetText(i,1);
							String tCustomerNoType = tSSRS.GetText(i,2);
							tTransferData.removeByName("CustomerNo");
							tTransferData.removeByName("CustomerNoType");
							tTransferData.setNameAndValue("CustomerNo", tCustomerNo);
							tTransferData.setNameAndValue("CustomerNoType", tCustomerNoType);
							tTransferData.setNameAndValue("CreateFlag", tCreateFlag); 
							VData tVData = new VData();
							tVData.add( tTransferData );
							tVData.add( tG );
							
							// ���ݴ���
								String busiName="WorkFlowUI";
					            BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
					            if(!tBusinessDelegate.submitData(tVData,"execute",busiName)){
					            	int n = tBusinessDelegate.getCErrors().getErrorCount();
					            	loggerDebug("RnewUWChk","n=="+n);
					            	for (int j = 0; j < n; j++)
									loggerDebug("RnewUWChk","Error: "+tBusinessDelegate.getCErrors().getError(j).errorMessage);
									Content = " �������˷��ź˱�֪ͨ��ʧ�ܣ�ԭ����: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
									FlagStr = "Fail";
					            }
							/*
							RnewWorkFlowUI tRnewWorkFlowUI   = new RnewWorkFlowUI();
							if (tRnewWorkFlowUI.submitData(tVData,"0000007001") == false)
							{
								int n = tRnewWorkFlowUI.mErrors.getErrorCount();
								loggerDebug("RnewUWChk","n=="+n);
								for (int j = 0; j < n; j++)
								loggerDebug("RnewUWChk","Error: "+tRnewWorkFlowUI.mErrors.getError(j).errorMessage);
								Content = " �������˷��ź˱�֪ͨ��ʧ�ܣ�ԭ����: " + tRnewWorkFlowUI.mErrors.getError(0).errorMessage;
								FlagStr = "Fail";
							}
							*/
							//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
							if (FlagStr == "Fail")
							{
							    tError = tBusinessDelegate.getCErrors();
							    Content = " �������˷��ź˱�֪ͨ��ɹ�! ";
							    if (!tError.needDealError())
							    {                          
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
 	      if(tSSRS.getMaxRow()==0)
 	       {
 	    	   FlagStr = "Succ";
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
	
	
loggerDebug("RnewUWChk","AUTO UW END!");
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	parent.fraInterface.initPolGrid();
	<%
	//parent.fraInterface.fm.submit();
	%>
</script>
</html>

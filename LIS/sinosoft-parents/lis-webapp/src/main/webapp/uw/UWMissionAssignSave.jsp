<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWMissionAssignSave.jsp
//�����ܣ��˱�����
//�������ڣ�2005-4-15
//������  ��HWM
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  if(tG == null) 
  {
		loggerDebug("UWMissionAssignSave","session has expired");
		return;
  }
     
 	// Ͷ�����б�

  //String sApplyType = request.getParameter("ApplyType");
  
  String UWKind = request.getParameter("UWKind");
  String UWName = request.getParameter("UWName");
  String sDefaultOperator = request.getParameter("DefaultOperator");
  
  boolean flag = false;
  
  String tChk[]=request.getParameterValues("InpAllPolGridChk");
	String sMissionID[]= request.getParameterValues("AllPolGrid6");
	String sSubMissionID[]=request.getParameterValues("AllPolGrid7");
	String sActivityID[]=request.getParameterValues("AllPolGrid8");

    
  if(UWKind=="2"&&UWName=="bq")
  { 
  	/************************************************************************************/
  	/*******************************��ȫ�˱��������***********************************/
  	/************************************************************************************/	
     tChk = request.getParameterValues("InpBqPolGridChk");
     sMissionID = request.getParameterValues("BqPolGrid1");
     sSubMissionID = request.getParameterValues("BqPolGrid2");
     sActivityID = request.getParameterValues("BqPolGrid11");
	 
  }

	 if(UWKind=="3"&&UWName=="lp")
   {
   	 /************************************************************************************/
  	/*******************************��������������***********************************/
  	/************************************************************************************/	
  	 tChk = request.getParameterValues("InpLpPolGridChk");
  	 sMissionID = request.getParameterValues("LpPolGrid8");
     sSubMissionID = request.getParameterValues("LpPolGrid9");
     sActivityID = request.getParameterValues("LpPolGrid10");
  	
	 }
       
  
		  int tcontCount = tChk.length;	
		  loggerDebug("UWMissionAssignSave","-------------------------------- new mission designate-----��ʼ--------------------------------");
		        
			for (int j = 0; j < tcontCount; j++)
			{
		    loggerDebug("UWMissionAssignSave","--------------------------------------------------------------------------");
		    
			  TransferData mTransferData = new TransferData();
			  if (tChk[j].equals("1"))
				{
				  //mTransferData.setNameAndValue("ApplyType", sApplyType[j]);	
				  mTransferData.setNameAndValue("MissionID", sMissionID[j]);
				  mTransferData.setNameAndValue("SubMissionID", sSubMissionID[j]);
				  mTransferData.setNameAndValue("ActivityID", sActivityID[j]);
				  mTransferData.setNameAndValue("DefaultOperator",sDefaultOperator);
				  flag = true;
				}
				
			  // loggerDebug("UWMissionAssignSave","== sMissionID == " + sMissionID[j]);    	   
	      // loggerDebug("UWMissionAssignSave","== sSubMissionID == " + sSubMissionID[j]); 
	      // loggerDebug("UWMissionAssignSave","== sActivityID == " + sActivityID[j]); 
		        
			  try
			  {
			    loggerDebug("UWMissionAssignSave","flag=="+flag);
				  if (flag == true && tChk[j].equals("1") )
				  {	
								// ׼���������� VData
								VData tVData = new VData();
								tVData.add( mTransferData );
								tVData.add( tG );
				
								// ���ݴ���
								UWMissionAssignUI tUWMissionAssignUI = new UWMissionAssignUI();
								if (tUWMissionAssignUI.submitData(tVData,"") == false)
								{
									int n = tUWMissionAssignUI.mErrors.getErrorCount();
									for (int i = 0; i < n; i++)
									{
									  loggerDebug("UWMissionAssignSave","Error: "+tUWMissionAssignUI.mErrors.getError(i).errorMessage);
									  Content = " ����ʧ�ܣ�ԭ����: " + tUWMissionAssignUI.mErrors.getError(0).errorMessage;
									}
									FlagStr = "Fail";
								}
								//�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
								if (FlagStr == "Fail")
								{
								    tError = tUWMissionAssignUI.mErrors;
								    loggerDebug("UWMissionAssignSave","tError.getErrorCount:"+tError.getErrorCount());
								    if (!tError.needDealError())
								    {                          
								    	Content = " ����ɹ�! ";
								    	FlagStr = "Succ";
								    }
								    else                                                                           
								    {
								    	Content = " ����ʧ�ܣ�ԭ����:";
								    	int n = tError.getErrorCount();
						    			if (n > 0)
						    			{
									      for(int i = 0;i < n;i++)
									      {
									        //tError = tErrors.getError(i);
									        Content = Content.trim() +i+". "+ tError.getError(i).errorMessage.trim()+".";
									      }
											}
								    	FlagStr = "Fail";
								    }
								 }
					} //end if
		  } //try end
		  catch(Exception e)
		  {
					e.printStackTrace();
					Content = Content.trim()+".��ʾ���쳣��ֹ!";
		  }
		  loggerDebug("UWMissionAssignSave","--------------------------------------------------------------------------");
	  } //for end 
		loggerDebug("UWMissionAssignSave","----------------------------------------����--------------------------------");
//	} //flagT 
%>                      
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWMissionAssignSave.jsp
//程序功能：核保调配
//创建日期：2005-4-15
//创建人  ：HWM
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.workflow.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  //输出参数
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
     
 	// 投保单列表

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
  	/*******************************保全核保任务分配***********************************/
  	/************************************************************************************/	
     tChk = request.getParameterValues("InpBqPolGridChk");
     sMissionID = request.getParameterValues("BqPolGrid1");
     sSubMissionID = request.getParameterValues("BqPolGrid2");
     sActivityID = request.getParameterValues("BqPolGrid11");
	 
  }

	 if(UWKind=="3"&&UWName=="lp")
   {
   	 /************************************************************************************/
  	/*******************************理赔二核任务分配***********************************/
  	/************************************************************************************/	
  	 tChk = request.getParameterValues("InpLpPolGridChk");
  	 sMissionID = request.getParameterValues("LpPolGrid8");
     sSubMissionID = request.getParameterValues("LpPolGrid9");
     sActivityID = request.getParameterValues("LpPolGrid10");
  	
	 }
       
  
		  int tcontCount = tChk.length;	
		  loggerDebug("UWMissionAssignSave","-------------------------------- new mission designate-----开始--------------------------------");
		        
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
								// 准备传输数据 VData
								VData tVData = new VData();
								tVData.add( mTransferData );
								tVData.add( tG );
				
								// 数据传输
								UWMissionAssignUI tUWMissionAssignUI = new UWMissionAssignUI();
								if (tUWMissionAssignUI.submitData(tVData,"") == false)
								{
									int n = tUWMissionAssignUI.mErrors.getErrorCount();
									for (int i = 0; i < n; i++)
									{
									  loggerDebug("UWMissionAssignSave","Error: "+tUWMissionAssignUI.mErrors.getError(i).errorMessage);
									  Content = " 调配失败，原因是: " + tUWMissionAssignUI.mErrors.getError(0).errorMessage;
									}
									FlagStr = "Fail";
								}
								//如果在Catch中发现异常，则不从错误类中提取错误信息
								if (FlagStr == "Fail")
								{
								    tError = tUWMissionAssignUI.mErrors;
								    loggerDebug("UWMissionAssignSave","tError.getErrorCount:"+tError.getErrorCount());
								    if (!tError.needDealError())
								    {                          
								    	Content = " 调配成功! ";
								    	FlagStr = "Succ";
								    }
								    else                                                                           
								    {
								    	Content = " 调配失败，原因是:";
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
					Content = Content.trim()+".提示：异常终止!";
		  }
		  loggerDebug("UWMissionAssignSave","--------------------------------------------------------------------------");
	  } //for end 
		loggerDebug("UWMissionAssignSave","----------------------------------------结束--------------------------------");
//	} //flagT 
%>                      
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

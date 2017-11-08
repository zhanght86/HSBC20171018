<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ManuUWChk.jsp
//程序功能：个人人工核保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.xb.*"%>
  <%@page import="com.sinosoft.workflowengine.*"%>
<%
  //输出参数
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
  
  //校验处理
  //内容待填充
  
  	//接收信息
  	// 投保单列表
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
	        //取投保人数量	
	        " select appntno,'A' from lccont a where contno='"+tContNo+"' "
					+ " and not exists " 
					+ " (select 1 from lwmission where missionid='"+tMissionID+"' and activityid='0000007002' "
					+ " and missionprop15=a.appntno and missionprop16='A') " 
					+ " union "
		            //取被保人数量
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
							// 准备传输数据 VData
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
							
							// 数据传输
								String busiName="WorkFlowUI";
					            BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
					            if(!tBusinessDelegate.submitData(tVData,"execute",busiName)){
					            	int n = tBusinessDelegate.getCErrors().getErrorCount();
					            	loggerDebug("RnewUWChk","n=="+n);
					            	for (int j = 0; j < n; j++)
									loggerDebug("RnewUWChk","Error: "+tBusinessDelegate.getCErrors().getError(j).errorMessage);
									Content = " 续保二核发放核保通知书失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
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
								Content = " 续保二核发放核保通知书失败，原因是: " + tRnewWorkFlowUI.mErrors.getError(0).errorMessage;
								FlagStr = "Fail";
							}
							*/
							//如果在Catch中发现异常，则不从错误类中提取错误信息
							if (FlagStr == "Fail")
							{
							    tError = tBusinessDelegate.getCErrors();
							    Content = " 续保二核发放核保通知书成功! ";
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
				Content = "请选择保单！";
			}  
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Content = Content.trim() +" 提示:异常退出.";
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

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//EdorApproveSave.jsp
//程序功能：保全复核
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%

   String busiName="EdorWorkFlowUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //输出参数
  String FlagStr = "";
  String Content = ""; 
  String Flag = "0";
  
  CErrors tError = null;
  
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");
  
  VData tVData = new VData();
  TransferData tTransferData = new TransferData();
  
  String tMissionID 	= request.getParameter("MissionID");
  String tSubMissionID 	= request.getParameter("SubMissionID");  
  String tApproveFlag 	= request.getParameter("ApproveFlag");
  String tApproveContent= request.getParameter("ApproveContent");
  
  String tEdorAcceptNo	= request.getParameter("EdorAcceptNo");
  String tOtherNo		= request.getParameter("OtherNo");
  String tOtherNoType	= request.getParameter("OtherNoType");
  String tEdorAppName	= request.getParameter("EdorAppName");
  String tApptype		= request.getParameter("Apptype");
  String tManageCom		= request.getParameter("ManageCom");
  String tAppntName		= request.getParameter("AppntName");
  String tPaytoDate		= request.getParameter("PaytoDate");
  String sModifyReason  = request.getParameter("ModifyReason");    //XinYQ addedon 2005-11-28

  tTransferData.setNameAndValue("MissionID", tMissionID);
  tTransferData.setNameAndValue("SubMissionID", tSubMissionID);
  
  //创建保全确认、复核修改节点的数据元素
  tTransferData.setNameAndValue("EdorAcceptNo", tEdorAcceptNo);
  tTransferData.setNameAndValue("OtherNo", tOtherNo);
  tTransferData.setNameAndValue("OtherNoType", tOtherNoType);
  tTransferData.setNameAndValue("EdorAppName", tEdorAppName);
  tTransferData.setNameAndValue("Apptype", tApptype);
  tTransferData.setNameAndValue("ManageCom", tManageCom);
  tTransferData.setNameAndValue("AppntName", tAppntName);
  tTransferData.setNameAndValue("PaytoDate", tPaytoDate);
  tTransferData.setNameAndValue("ApproveFlag", tApproveFlag);
  tTransferData.setNameAndValue("ApproveContent", tApproveContent);    //待处理
  tTransferData.setNameAndValue("CheckFlag", "N");
  if (sModifyReason != null) tTransferData.setNameAndValue("ModifyReason", sModifyReason);    //XinYQ addedon 2005-11-28

 
    
	try
	{		   
		tVData.add(tG);		
		tVData.add(tTransferData);
		tBusinessDelegate.submitData(tVData, "0000008007",busiName);
	}
	catch(Exception ex)
	{
	      Content = "审批失败，原因是:" + ex.toString();
	      FlagStr = "Fail";
	}
	if ("".equals(FlagStr))
	{
		    tError = tBusinessDelegate.getCErrors();
		    if (!tError.needDealError())
		    {                          
				
				FlagStr = "";
//=========复核成功后判断如果不需要财务交费则自动运行保全确认================BGN======================

				VData mResult = tBusinessDelegate.getResult();
				
               
                
				TransferData mTransferData = 
					(TransferData) mResult.getObjectByObjectName("TransferData", 0);
                
                String sCheckFlag = 
                	(String) mTransferData.getValueByName("CheckFlag");
                loggerDebug("GEdorApproveSave","==============CheckFlag=" + sCheckFlag);
                if (sCheckFlag.equals("Y"))
                {
					Content = "该保全需要抽检，请等待抽检结束再做审批!";
					FlagStr = "Fail";
                }
                else
                {
                		String sNeedPayFlag = 
                			(String) mTransferData.getValueByName("NeedPayFlag");
                		loggerDebug("GEdorApproveSave","==============NeedPayFlag=" + sNeedPayFlag);
                		if(sNeedPayFlag==null || sNeedPayFlag.equals("N"))
                		{
                			Flag = "0"; //不收费
                		}else{
                			Flag = "1"; //收费
                		}
                		//复核通过并且不需要财务交费
                		if (tApproveFlag.equals("1") && (sNeedPayFlag==null || sNeedPayFlag.equals("N")))  
                		{
                			Content = "审批成功！";
					    	FlagStr = "Succ";
					    	
//				        	//需要重新查询保全确认节点的子任务ID
//						      String sql = " select submissionid from lwmission " +
//						                   " where activityid = '0000008009' " +
//						                   " and missionid = '" + tMissionID + "'";
//				
//						      ExeSQL exeSQL = new ExeSQL();
//						      String sNewSubMissionID = exeSQL.getOneValue(sql);
//						      tTransferData.removeByName("SubMissionID");
//							  tTransferData.setNameAndValue("SubMissionID", sNewSubMissionID);
//
//						      tTransferData.removeByName("ActivityID");
//							  tTransferData.setNameAndValue("ActivityID", "0000008009");
//							  
//							String sTemplatePath = application.getRealPath("xerox/printdata") + "/";
//						    tTransferData.setNameAndValue("TemplatePath", sTemplatePath);
//							
//							tEdorWorkFlowUI = new EdorWorkFlowUI(); 
//						    
//							try
//							{
//								tVData.clear();
//								tVData.add(tG);
//								tVData.add(tTransferData);
//								tEdorWorkFlowUI.submitData(tVData, "0000008009");
//							}
//							catch(Exception ex)
//							{
//							      Content = "审批成功！但是保全确认失败，原因是:" + ex.toString();
//							      FlagStr = "Fail";
//							}
//							if ("".equals(FlagStr))
//							{
//								    tError = tEdorWorkFlowUI.mErrors;
//								    if (!tError.needDealError())
//								    {
//										
////=========保全确认成功后自动执行确认生效处理================BGN======================
//										GEdorValidBL tGEdorValidBL = new GEdorValidBL();
//										try
//										{
//											tGEdorValidBL.submitData(tVData, "");
//										}
//										catch(Exception ex)
//										{
//										      Content = "复核成功！并且保全确认成功！但是保全确认生效失败，原因是:" + ex.toString();
//										      FlagStr = "Fail";
//										}
//										if ("".equals(FlagStr))
//										{
//											    tError = tGEdorValidBL.mErrors;
//											    if (!tError.needDealError())
//											    {
//											    	Content ="审批成功！并且保全确认成功！";
//											    
//											    	//取出是否需要付费标志
//													sql = " select 1 from dual where exists (select 'X' from LJaGet " +
//														  " where othernotype = '10' and otherno = '" + tEdorAcceptNo + 
//														  "' and sumgetmoney <> 0)";
//													exeSQL = new ExeSQL();
//													loggerDebug("GEdorApproveSave","-----------------------------\n"+sql);
//													String sNeedGetFlag = exeSQL.getOneValue(sql);
//													if (sNeedGetFlag == null) sNeedGetFlag = "";
//													loggerDebug("GEdorApproveSave","-----------------------------\n"+sNeedGetFlag);
//													if (sNeedGetFlag.equals("1"))
//													{
//														Content += "请打印付费通知书！";
//													}
//											    	FlagStr = "Succ";
//											    	Flag = "1";
//											    }
//											    else                                                                           
//											    {
//											    	Content = "审批成功！并且保全确认成功！但是保全确认生效失败，原因是:" + tError.getFirstError();
//											    	FlagStr = "Fail";
//											    }
//										}
////=========保全确认成功后自动执行确认生效处理================END======================
//								    }
//								    else                                                                           
//								    {
//								    	Content = "审批成功！但是保全确认失败，原因是:" + tError.getFirstError();
//								    	FlagStr = "Fail";
//								    }
//							}
                
                		}
                		else
                		{
                			Content = "审批成功！";
                			FlagStr = "Succ";
                			if (tApproveFlag.equals("1"))
                			{
                				Content += "请打印补费通知书，并去财务交费";
                			}
                			
                		}
//=========复核成功后判断如果不需要财务交费则自动运行保全确认================END======================
				}
		    }
		    else                                                                           
		    {
		    	Content = "复核失败，原因是:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
	}   
%>
   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Flag%>");
</script>
</html>
   
   
   
 

<%
//**************************************************************************************************
//程序名称：LLClaimAuditShiftPreSave.jsp
//程序功能：审核管理转向预付管理
//创建日期：2008-12-27 
//创建人  ：zhangzheng
//更新记录：
//**************************************************************************************************
%>

<!--用户校验类-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.workflow.claim.*"%>
<%@page import="com.sinosoft.lis.claim.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>
<%

	//输出参数
	CErrors tError = null;
	String FlagStr = "Fail";
	String Content = "";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	//ClaimWorkFlowUI tClaimWorkFlowUI = new ClaimWorkFlowUI(); //理赔工作流引擎
	String busiName="tWorkFlowUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	
	//页面有效性
	if(tG == null)
	{
	    loggerDebug("LLClaimAuditShiftPreSave","session has expired");
	    return;
	}
	
	
	//打包数据,用于生成“预付节点”
	TransferData mTransferData = new TransferData();
	mTransferData.setNameAndValue("RptNo",request.getParameter("RptNo")); //"赔案号" 
	mTransferData.setNameAndValue("RptorState","35"); //案件状态:35-预付
	mTransferData.setNameAndValue("CustomerNo",request.getParameter("CustomerNo")); //"出险人编码"
	mTransferData.setNameAndValue("CustomerName",request.getParameter("CustomerName")); //"出险人姓名 
	mTransferData.setNameAndValue("CustomerSex",request.getParameter("CustomerSex")); //"出险人性别"
	mTransferData.setNameAndValue("AccDate",request.getParameter("AccidentDate")); //"事故日期" 
	mTransferData.setNameAndValue("RgtClass",request.getParameter("tRgtClass")); //"申请类型" 
	mTransferData.setNameAndValue("RgtObj",request.getParameter("tRgtObj")); //"号码类型" 
	mTransferData.setNameAndValue("RgtObjNo",request.getParameter("RptNo")); //"其他号码" 
	//mTransferData.setNameAndValue("Popedom",request.getParameter("tPopedom")); //案件核赔级别
	mTransferData.setNameAndValue("ComeWhere","B"); //"来自" A' then '立案' when 'B' then '审核' when 'C' then '审批' when 'D' then '简易案件'
	mTransferData.setNameAndValue("Auditer",tG.Operator); //"审核操作人" 
	mTransferData.setNameAndValue("MngCom",tG.ManageCom); //"机构" 
	mTransferData.setNameAndValue("ComFlag",request.getParameter("tComFlag")); //权限跨级标志
	mTransferData.setNameAndValue("BudgetFlag","1");
    mTransferData.setNameAndValue("PopedomPhase","B"); //权限阶段标示A:审核B:审批
	mTransferData.setNameAndValue("MissionID",request.getParameter("MissionID"));
	mTransferData.setNameAndValue("SubMissionID",request.getParameter("SubMissionID"));
	mTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
	mTransferData.setNameAndValue("IsRunBL","0"); //用于判断是否运行BL，0--不运行
	mTransferData.setNameAndValue("PrepayFlag","1"); //预付结论
	mTransferData.setNameAndValue("BusiType","1003");
	String tAuditConclusion=request.getParameter("AuditConclusion"); //审核结论
	loggerDebug("LLClaimAuditShiftPreSave","案件"+request.getParameter("RptNo")+"的审核结论:"+tAuditConclusion);
	mTransferData.setNameAndValue("AuditConclusion",tAuditConclusion); //审核结论

	try
	{
	    // 准备传输数据 VData
	    VData tVData = new VData();
	    tVData.add(mTransferData);
	    tVData.add(tG);
	    
	    //预付上报
	    if(tAuditConclusion.equals("0"))
	    {
		    try
		    {
		        //if(!tClaimWorkFlowUI.submitData(tVData,"0000005035"))
//		        {
//		            Content = " 提交工作流失败，原因是: " + tClaimWorkFlowUI.mErrors.getError(0).errorMessage;
//		            FlagStr = "Fail";
//		        }
				if(!tBusinessDelegate.submitData(tVData,"execute",busiName))
				{    
				    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
				    { 
						Content = "提交工作流失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
						FlagStr = "Fail";
					}
					else
					{
						Content = "提交工作流失败";
						FlagStr = "Fail";				
					}
				}
				
		        else
		    	{
		    		//从结果集中取出前台需要数据
		            tVData.clear();
		            //tVData = tClaimWorkFlowUI.getResult();
		            //loggerDebug("LLClaimAuditShiftPreSave","tVData="+tVData);	    
		            String sql="select a.missionid,a.submissionid,a.activityid,a.MissionProp1,a.MissionProp3 from lwmission a where a.missionprop1='"+mTransferData.getValueByName("RptNo")+"'";
		            loggerDebug("LLClaimAuditShiftPreSave","查询预付当前节点sql:"+sql);
		            //ExeSQL tExeSQL=new ExeSQL();
		            SSRS tSSRS=new SSRS();
		            //tSSRS=tExeSQL.execSQL(sql);
		            TransferData sqlTransferData = new TransferData();
	  				VData sqlVData = new VData();
				    sqlTransferData.setNameAndValue("SQL",sql);
				    sqlVData.add(sqlTransferData);
				  	BusinessDelegate tsqlBusinessDelegate=BusinessDelegate.getBusinessDelegate();
				  	  if(!tsqlBusinessDelegate.submitData(sqlVData,"execSQL","ExeSQLUI"))
				  	  {    
				  	       if(tsqlBusinessDelegate.getCErrors()!=null&&tsqlBusinessDelegate.getCErrors().getErrorCount()>0)
				  	       { 
				  				loggerDebug("LLClaimAuditShiftPreSave","查询失败，原因是:" + tsqlBusinessDelegate.getCErrors().getFirstError());
				  		   }
				  		   else
				  		   {
				  			 	loggerDebug("LLClaimAuditShiftPreSave","查询失败");				
				  		   }
				  	  }
				  	tSSRS=(SSRS)tsqlBusinessDelegate.getResult().get(0);
		            if(tSSRS.getMaxRow()==0)
		            {
			            Content = " 查询案件: " +mTransferData.getValueByName("RptNo")+"当前工作流节点失败!";
			            FlagStr = "Fail";
		            }
		            else
		            {
		            	loggerDebug("LLClaimAuditShiftPreSave","案件"+tSSRS.GetText(1,4)+",missionid:"+tSSRS.GetText(1,1));
		            	loggerDebug("LLClaimAuditShiftPreSave","案件"+tSSRS.GetText(1,4)+",submissionid:"+tSSRS.GetText(1,2));
		            	loggerDebug("LLClaimAuditShiftPreSave","案件"+tSSRS.GetText(1,4)+",activityid:"+tSSRS.GetText(1,3));
		            	loggerDebug("LLClaimAuditShiftPreSave","案件"+tSSRS.GetText(1,4)+",Customerno:"+tSSRS.GetText(1,5));
	%>
	<html>
	<script language="javascript">
		//fm.MissionID.value=<%=tSSRS.GetText(1,1)%>;
		//fm.SubMissionID.value=<%=tSSRS.GetText(1,2)%>;
		//fm.ActivityID.value=<%=tSSRS.GetText(1,3)%>;
		//fm.ClmNo.value=<%=tSSRS.GetText(1,4)%>;
		//fm.customerNo.value=<%=tSSRS.GetText(1,5)%>;
	</script>
	</html>
	<%	            	
			            Content = "数据提交成功";
			            FlagStr = "Succ";  
		            }
	          
		    	}
			}
			catch(Exception ex)
		    {
		    	Content = "数据提交ClaimWorkFlowUI失败，原因是:" + ex.toString();
		    	FlagStr = "Fail";
		    }	
	    }
	    else
	    {
	    	//回退不走工作流,提交业务处理
//			LLClaimPrepayAuditRetrunBL tLLClaimPrepayAuditRetrunBL = new LLClaimPrepayAuditRetrunBL();
//			if (!tLLClaimPrepayAuditRetrunBL.submitData(tVData, "INSERT")) {
//				// @@错误处理
//				CError.buildErr(this, "预付审核上报确认失败,"+tLLClaimPrepayAuditRetrunBL.mErrors.getLastError());
//	            Content = " 预付回退失败，原因是: " + tLLClaimPrepayAuditRetrunBL.mErrors.getError(0).errorMessage;
//	            FlagStr = "Fail";
//				tVData = null;
//			} 
  
			String busiName1="LLClaimPrepayAuditRetrunBL";
			BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
			if(!tBusinessDelegate.submitData(tVData,"INSERT",busiName1))
			{    
			    if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
			    { 
			    	CError.buildErr(this, "预付审核上报确认失败,"+tBusinessDelegate1.getCErrors().getLastError());
					Content = "预付回退失败，原因是:" + tBusinessDelegate1.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					Content = "预付回退失败";
					FlagStr = "Fail";				
				}
			}


			else 
			{
	            Content = "数据提交成功";
	            FlagStr = "Succ";  
			}
	    }

	}
	catch(Exception ex)
	{
	    Content = "数据提交失败，原因是:" + ex.toString();
	    FlagStr = "Fail";
	}
	loggerDebug("LLClaimAuditShiftPreSave","------LLClaimPrepayMissSave.jsp end------");
	
	%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit2("<%=FlagStr%>","<%=Content%>");
</script>
</html>

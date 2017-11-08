<%--
    保存集体保单信息 2004-11-16 wzw
--%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.net.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.service.*" %>

<%	         
	
	String FlagStr="";      //操作结果
	String Content = "";    //控制台信息
	String tAction = "";    //操作类型：delete update insert
	String tOperate = "";   //操作代码
	String mAction = request.getParameter("Action");

	VData tData = new VData();

	GlobalInput tGI = new GlobalInput();
	tGI=(GlobalInput)session.getValue("GI");
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("AssignType","QAC");
	
	
	LDAssignPlanDBSet tLDAssignPlanDBSet = new LDAssignPlanDBSet();
	LDAssignPlanSchema tLDAssignPlanSchema = new LDAssignPlanSchema();
	LDAssignPlanSet tLDAssignPlanSet = new LDAssignPlanSet();
	String starttime = request.getParameter("StartTime");
	String endtime = request.getParameter("EndTime");
	loggerDebug("SelAssignDutySave","开始时间"+starttime);
	loggerDebug("SelAssignDutySave","终止时间"+endtime);
	String tChk[] = request.getParameterValues("InpAssignGridChk");
	String tGridNo[] = request.getParameterValues("AssignGridNo");
    String tAssignNo [] = request.getParameterValues("AssignGrid1");
	//String tOperatorName [] = request.getParameterValues("AssignGrid2");
	String tQuest[] = request.getParameterValues("AssignGrid3");
    String tStateQ[] = request.getParameterValues("AssignGrid4");
	String tApprove[] = request.getParameterValues("AssignGrid5");
	String tStateA[] = request.getParameterValues("AssignGrid6");
	String tCustomer[] = request.getParameterValues("AssignGrid7");
	String tStateC[] = request.getParameterValues("AssignGrid8");
	int m = tGridNo.length;
	if(mAction.equals("INSERT"))
	{
	   for(int i=0;i<m;i++)
	   {
		   if(!tQuest[i].equals("")) //异常件
		   {
			   LDAssignPlanSchema tldSchema = new LDAssignPlanSchema();
			   tldSchema.setActivityid("0000001090");
			   tldSchema.setAssignNo(tAssignNo[i]);
			   tldSchema.setServiceName("异常件处理");
			   tldSchema.setTaskStartTime(starttime);
			   tldSchema.setTaskEndTime(endtime);
			   tldSchema.setPlanAmount(tQuest[i]);
			   tldSchema.setState(tStateQ[i]);
			   tLDAssignPlanSet.add(tldSchema);
			   //tLDAssignPlanDBSet.add(tLDAssignPlanSchema);
		   }
		   if(!tApprove[i].equals("")) //问题件
		   {
			   LDAssignPlanSchema tldSchema = new LDAssignPlanSchema();
			   tldSchema.setActivityid("0000001002");
			   tldSchema.setAssignNo(tAssignNo[i]);
			   tldSchema.setServiceName("问题件处理");
			   tldSchema.setTaskStartTime(starttime);
			   tldSchema.setTaskEndTime(endtime);
			   tldSchema.setPlanAmount(tApprove[i]);
			   tldSchema.setState(tStateA[i]);
			   tLDAssignPlanSet.add(tldSchema);
			  // tLDAssignPlanDBSet.add(tLDAssignPlanSchema);
		   }
		   if(!tCustomer[i].equals("")) //人工合并
		   {
			   LDAssignPlanSchema tldSchema = new LDAssignPlanSchema();
			   tldSchema.setActivityid("0000001404");
			   tldSchema.setAssignNo(tAssignNo[i]);
			   tldSchema.setServiceName("客户号合并");
			   tldSchema.setTaskStartTime(starttime);
			   tldSchema.setTaskEndTime(endtime);
			   tldSchema.setPlanAmount(tCustomer[i]);
			   tldSchema.setState(tStateC[i]);
			   tLDAssignPlanSet.add(tldSchema);
			  // tLDAssignPlanDBSet.add(tLDAssignPlanSchema);
		   }
	   }
	}else if (mAction.equals("UPDATE"))
	{
		for (int i=0;i<tChk.length;i++)
		{
			if(tChk[i].equals("1"))
			{
				if(!tQuest[i].equals("")) //异常件
				   {
					LDAssignPlanSchema tldSchema = new LDAssignPlanSchema();
					   tldSchema.setActivityid("0000001090");
					   tldSchema.setAssignNo(tAssignNo[i]);
					   tldSchema.setServiceName("异常件处理");
					   tldSchema.setTaskStartTime(starttime);
					   tldSchema.setTaskEndTime(endtime);
					   tldSchema.setPlanAmount(tQuest[i]);
					   tldSchema.setState(tStateQ[i]);
					   tLDAssignPlanSet.add(tldSchema);
					   //tLDAssignPlanDBSet.add(tLDAssignPlanSchema);
				   }
				   if(!tApprove[i].equals("")) //问题件
				   {
					   LDAssignPlanSchema tldSchema = new LDAssignPlanSchema();
					   tldSchema.setActivityid("0000001002");
					   tldSchema.setAssignNo(tAssignNo[i]);
					   tldSchema.setServiceName("问题件处理");
					   tldSchema.setTaskStartTime(starttime);
					   tldSchema.setTaskEndTime(endtime);
					   tldSchema.setPlanAmount(tApprove[i]);
					   tldSchema.setState(tStateA[i]);
					   tLDAssignPlanSet.add(tldSchema);
					  // tLDAssignPlanDBSet.add(tLDAssignPlanSchema);
				   }
				   if(!tCustomer[i].equals("")) //人工合并
				   {
					   LDAssignPlanSchema tldSchema = new LDAssignPlanSchema();
					   tldSchema.setActivityid("0000001404");
					   tldSchema.setAssignNo(tAssignNo[i]);
					   tldSchema.setServiceName("客户号合并");
					   tldSchema.setTaskStartTime(starttime);
					   tldSchema.setTaskEndTime(endtime);
					   tldSchema.setPlanAmount(tCustomer[i]);
					   tldSchema.setState(tStateC[i]);
					   tLDAssignPlanSet.add(tldSchema);
					  // tLDAssignPlanDBSet.add(tLDAssignPlanSchema);
				   }
			}
		}
	}else if (mAction.equals("STARTALL"))
	{
		tLDAssignPlanSchema.setState("0"); //	启动任务
		tLDAssignPlanSchema.setModifyDate(PubFun.getCurrentDate());
		tLDAssignPlanSchema.setModifyTime(PubFun.getCurrentTime());
		tLDAssignPlanSet.add(tLDAssignPlanSchema);
	}else if (mAction.equals("STARTSEL"))
	{
		for(int i=0;i<tChk.length;i++)
		{
			if(tChk[i].equals("1"))
			{
				LDAssignPlanSchema tldSchema = new LDAssignPlanSchema();
				tldSchema.setAssignNo(tAssignNo[i]);
				//tLDAssignPlanSchema.setState("0");
				tldSchema.setModifyDate(PubFun.getCurrentDate());
				tldSchema.setModifyTime(PubFun.getCurrentTime());
				tLDAssignPlanSet.add(tldSchema);
			}
		}
	}
	else if (mAction.equals("TERMINATESEL"))
	{
		for(int j=0;j<tChk.length;j++)
		{
			if(tChk[j].equals("1"))
			{
				LDAssignPlanSchema tldSchema = new LDAssignPlanSchema();
				tldSchema.setAssignNo(tAssignNo[j]);
				tldSchema.setState("1");
				tLDAssignPlanSet.add(tldSchema);
			}
		}
	}else
	{
		tLDAssignPlanSchema.setState("1");
		tLDAssignPlanSchema.setModifyDate(PubFun.getCurrentDate());
		tLDAssignPlanSchema.setModifyTime(PubFun.getCurrentTime());
		tLDAssignPlanSet.add(tLDAssignPlanSchema);
	}

  // 准备传输数据 VData
	tData.add( tGI );
    tData.add(tTransferData);
	tData.add(tLDAssignPlanSet);
	//SelAssignPlanUI tSelAssignPlanUI = new SelAssignPlanUI();
	String busiName="tbSelAssignPlanUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if(!tBusinessDelegate.submitData( tData, mAction ,busiName))
	{
		Content = " 操作失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
	}
	else
	{   
		Content = " 操作成功! ";
		FlagStr = "Succ";
		tData.clear();
		/*
		VData mResult = new VData();
        mResult = tSelAssignPlanUI.getResult();
        String[][] tResult = (String[][])mResult.get(0);
		String tQueryResult = "";
		tQueryResult = "0|"+tResult.length;
		 loggerDebug("SelAssignDutySave","@@@@@查询！"+tResult.length);
		     for(int i=0;i<tResult.length;i++)
		     {
		     		String tTemp = "";
				for(int n=0;n<2;n++)
				{
				 if(n==0)
				   tTemp = "^"+tResult[i][n];
				 else 
				 {
				   tTemp = tTemp + "|"+	tResult[i][n];
				 }  
				}
			tQueryResult = tQueryResult + 	tTemp;
		     }
		     loggerDebug("SelAssignDutySave",tQueryResult);
		%>
		<Script>
		  parent.fraInterface.displayData("<%=tQueryResult%>");
		</Script>
		<%
      */
	}
	

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

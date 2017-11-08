<%--
    保存集体保单信息 2004-11-16 wzw
--%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.taskservice.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
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
	tTransferData.setNameAndValue("AssignType","UW");
	
	LDAssignPlanSchema tLDAssignPlanSchema = new LDAssignPlanSchema();
	LDAssignPlanSet tLDAssignPlanSet = new LDAssignPlanSet();
	String starttime = request.getParameter("StartTime");
	String endtime = request.getParameter("EndTime");
	loggerDebug("SelUWAssignDutySave","开始时间"+starttime);
	loggerDebug("SelUWAssignDutySave","终止时间"+endtime);
	String tChk[] = request.getParameterValues("InpAssignGridChk");
	String tGridNo[] = request.getParameterValues("AssignGridNo");
    String tUWUserNo [] = request.getParameterValues("AssignGrid1");
	//String tOperatorName [] = request.getParameterValues("AssignGrid2");
	String tPlanAmount[] = request.getParameterValues("AssignGrid5");
	String tUWPopedom[] = request.getParameterValues("AssignGrid3");
    //String tStateQ[] = request.getParameterValues("AssignGrid4");
	//String tApprove[] = request.getParameterValues("AssignGrid5");
	//String tStateA[] = request.getParameterValues("AssignGrid6");
	//String tCustomer[] = request.getParameterValues("AssignGrid7");
	String tState[] = request.getParameterValues("AssignGrid7");
	int m = tGridNo.length;
	if(mAction.equals("INSERT"))
	{
	   for(int i=0;i<m;i++)
	   {
		  
			   LDAssignPlanSchema tldSchema = new LDAssignPlanSchema();
			   tldSchema.setActivityid("0000001100");
			   tldSchema.setAssignNo(tUWUserNo[i]);
			   tldSchema.setServiceName("核保");
			   tldSchema.setTaskStartTime(starttime);
			   tldSchema.setTaskEndTime(endtime);
			   tldSchema.setPlanAmount(tPlanAmount[i]);
			   tldSchema.setState(tState[i]);
			   tldSchema.setUWPopedom(tUWPopedom[i]);
			   tLDAssignPlanSet.add(tldSchema);
			   //tLDAssignPlanDBSet.add(tLDAssignPlanSchema);
		   
		   
	   }
	}else if (mAction.equals("UPDATE"))
	{
		for (int i=0;i<tChk.length;i++)
		{
			if(tChk[i].equals("1"))
			{
				
					LDAssignPlanSchema tldSchema = new LDAssignPlanSchema();
					   tldSchema.setActivityid("0000001100");
					   tldSchema.setAssignNo(tUWUserNo[i]);
					   tldSchema.setServiceName("核保");
					   tldSchema.setTaskStartTime(starttime);
					   tldSchema.setTaskEndTime(endtime);
					   tldSchema.setPlanAmount(tPlanAmount[i]);
					   tldSchema.setUWPopedom(tUWPopedom[i]);
					   tldSchema.setState(tState[i]);
					   tLDAssignPlanSet.add(tldSchema);
					   //tLDAssignPlanDBSet.add(tLDAssignPlanSchema);
				
				  
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
				tldSchema.setAssignNo(tUWUserNo[i]);
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
				tldSchema.setAssignNo(tUWUserNo[j]);
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

	}

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

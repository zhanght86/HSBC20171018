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
<%@page import="java.net.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.taskservice.crontab.CrontabParser"%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%	         
	
	String FlagStr="";      //操作结果
	String Content = "";    //控制台信息
	String tAction = "";    //操作类型：delete update insert
	String tOperate = "";   //操作代码

	VData tData = new VData();

	GlobalInput tGI = new GlobalInput();
	tAction = request.getParameter("fmAction");
	
	
	LDTaskSchema tLDTaskSchema = new LDTaskSchema();
	LDTaskSet tLDTaskSet = new LDTaskSet();

	String busiName = "TaskServiceUI";
	BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
	if(!FlagStr.equals("Fail"))
	{

		tLDTaskSchema.setTaskCode(request.getParameter("BaseTaskCode"));
		tLDTaskSchema.setTaskDescribe(request.getParameter("TaskDescribe"));
		tLDTaskSchema.setTaskClass(request.getParameter("TaskClass"));
		tLDTaskSet.add(tLDTaskSchema);

		GlobalInput tG = new GlobalInput();
		tG=(GlobalInput)session.getValue("GI");



  	    // 准备传输数据 VData
		tData.add( tG );
		tData.add(tLDTaskSet);
	
		//TaskService tTaskService = new TaskService();
		//if( tTaskService.submitData( tData, tAction ) < 0 )
		if (!tBusinessDelegate.submitData(tData, tAction,busiName))
		{
			Content = " 操作失败，原因是: " + tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
		}
		else
		{
			Content = " 操作成功! ";
			FlagStr = "Succ";

			tData.clear();

%>
<%		
		}
	}
%>                      
<html>
<script language="javascript">
	//parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	//parent.afterSubmit("<%=FlagStr%>","<%=Content%>");
	//alert(parent.document.frames['taskB']);
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

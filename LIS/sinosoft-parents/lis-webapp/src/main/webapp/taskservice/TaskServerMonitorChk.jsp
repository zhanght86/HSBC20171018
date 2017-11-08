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
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<%	         
	
	String FlagStr="";      //操作结果
	String Content = "";    //控制台信息
	String tAction = "";    //操作类型：delete update insert
	String tOperate = "";   //操作代码

	VData tData = new VData();

	GlobalInput tGI = new GlobalInput();
	tAction = request.getParameter("fmAction");
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");
	
	tData.add(tG);

	//保存配置信息
	
	if(tAction.equals("ServerConfig"))
	{
		LDTaskParamSet tLDTaskParamSet = new LDTaskParamSet();
		
		String tParamName[] = request.getParameterValues("ServerGrid1");
	    String tParamValue[] = request.getParameterValues("ServerGrid3");
	    for(int i=0;i<tParamName.length;i++)
	    {
	    	LDTaskParamSchema tLDTaskParamSchema = new LDTaskParamSchema();
	    	tLDTaskParamSchema.setTaskCode("000000");
	    	tLDTaskParamSchema.setTaskPlanCode("000000");
	    	tLDTaskParamSchema.setParamName(tParamName[i]);
	    	tLDTaskParamSchema.setParamValue(tParamValue[i]);
	    	tLDTaskParamSet.add(tLDTaskParamSchema);
	    }
	    tData.add(tLDTaskParamSet);
		
	}
	else if(tAction.equals("TaskServerConfig"))
	{
		LDTaskServerParamSet tLDTaskServerParamSet = new LDTaskServerParamSet();
		
		String tParamName[] = request.getParameterValues("TaskServerConfigGrid1");
	    String tParamValue[] = request.getParameterValues("TaskServerConfigGrid3");
	    String tServerIp = request.getParameter("hiddenServerIP");
	    String tServerPort = request.getParameter("hiddenServerPort");
	    for(int i=0;i<tParamName.length;i++)
	    {
	    	LDTaskServerParamSchema tLDTaskServerParamSchema = new LDTaskServerParamSchema();
	    	tLDTaskServerParamSchema.setServerIP(tServerIp);
	    	tLDTaskServerParamSchema.setServerPort(tServerPort);
	    	tLDTaskServerParamSchema.setParamName(tParamName[i]);
	    	tLDTaskServerParamSchema.setParamValue(tParamValue[i]);
	    	tLDTaskServerParamSet.add(tLDTaskServerParamSchema);
	    }
	    tData.add(tLDTaskServerParamSet);
	}

	
	String busiName = "TaskServiceUI";
	BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
	
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
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

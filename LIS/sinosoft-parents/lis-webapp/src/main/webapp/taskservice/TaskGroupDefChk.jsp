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

	String tTaskGroupCode = request.getParameter("TaskGroupCode");
	String tTaskGroupDescribe =  request.getParameter("TaskGroupDescribe");
	
	LDTaskGroupSchema tLDTaskGroupSchema = new LDTaskGroupSchema();
	LDTaskGroupSet tLDTaskGroupSet = new LDTaskGroupSet();

	tLDTaskGroupSchema.setTaskGroupCode(tTaskGroupCode);
	tLDTaskGroupSchema.setTaskDescribe(tTaskGroupDescribe);
	tLDTaskGroupSet.add(tLDTaskGroupSchema);
	
	String busiName = "TaskServiceUI";
	BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
	
	if(tAction.equals("INSERTTASKGROUP")||tAction.equals("DELETETASKGROUP"))
	{
		
		 // 准备传输数据 VData
		tData.add( tG );
		tData.add(tLDTaskGroupSet);
	}
	else if(tAction.equals("INSERTTASKGROUPDETAIL"))
	{
		LDTaskGroupDetailSchema tLDTaskGroupDetailSchema = new LDTaskGroupDetailSchema();
		LDTaskGroupDetailSet tLDTaskGroupDetailSet = new LDTaskGroupDetailSet();
		//并发控制组控制模块配置
		String tParamValue[] = request.getParameterValues("TaskGroupDetailGrid1");
		
		String tFailActionType[] = request.getParameterValues("TaskGroupDetailGrid5");
		
		int n = tParamValue.length;
		for (int i = 0; i < n; i++)
		{ 
			//判断参数信息是否为空，省去删除所有空行的必要
			if (tParamValue[i] != null && !tParamValue[i].equals(""))
			{   
				tLDTaskGroupDetailSchema = new LDTaskGroupDetailSchema();
				tLDTaskGroupDetailSchema.setTaskGroupCode(tTaskGroupCode);
				tLDTaskGroupDetailSchema.setTaskCode(tParamValue[i]);
				tLDTaskGroupDetailSchema.setDependTaskCode("000000");//暂时不提供该功能
				tLDTaskGroupDetailSchema.setDependType("00");
				
				loggerDebug("TaskGroupDefChk","tTaskCode:"+tParamValue[i]+":FailAction:"+tFailActionType[i]);
				if(tFailActionType[i]==null||tFailActionType[i].equals(""))
				{
					tFailActionType[i] = "00";
				}
				tLDTaskGroupDetailSchema.setActionAfterFail(tFailActionType[i]);
				int tOrder= i+1;
				tLDTaskGroupDetailSchema.setTaskOrder(tOrder);
				tLDTaskGroupDetailSet.add(tLDTaskGroupDetailSchema);
			}
		}
		
		tData.add( tG );
		tData.add(tLDTaskGroupSet);
		tData.add(tLDTaskGroupDetailSet);
	}
	if(tAction.equals("DELETETASKGROUPDETAIL"))
	{
		
		 // 准备传输数据 VData
		tData.add( tG );
		tData.add(tLDTaskGroupSet);
	}
	
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
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

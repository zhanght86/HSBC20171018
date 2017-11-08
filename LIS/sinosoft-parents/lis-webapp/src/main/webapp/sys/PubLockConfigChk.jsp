<%--
    
--%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.sys.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.net.*"%>
<%@page import="com.sinosoft.service.*" %>

<%	         
	
	String FlagStr="";      //操作结果
	String Content = "";    //控制台信息
	String tAction = "";    //操作类型：delete update insert
	String tOperate = "";   //操作代码

	VData tData = new VData();

	
	//公共锁表数据
	LockAppGroupSchema tLockAppGroupSchema = new LockAppGroupSchema();
	LockAppGroupSet tLockAppGroupSet = new LockAppGroupSet();
	//基本模块
	LockBaseSchema tLockBaseSchema = new LockBaseSchema();
	LockBaseSet tLockBaseSet = new LockBaseSet();
	//并发控制组模块
	LockGroupSchema tLockGroupSchema = new LockGroupSchema();
	LockGroupSet tLockGroupSet = new LockGroupSet();
	
	//锁表配置
	LockConfigSchema tLockConfigSchema = new LockConfigSchema();
	LockConfigSet tLockConfigSet = new LockConfigSet();

	tAction = request.getParameter("hiddenAction");
	String tHiddenLockData = request.getParameter("hiddenLockData");
	String tHiddenLockGroup = request.getParameter("hiddenLockGroup");
	
	//解锁部分
	tLockAppGroupSchema.setOperatedNo(tHiddenLockData);
	tLockAppGroupSchema.setLockGroup(tHiddenLockGroup);
	tLockAppGroupSet.add(tLockAppGroupSchema);
	
	String tUnLockReason = request.getParameter("UnLockReason");
	
	//基本模块配置部分
	String tBaseModuleCode = request.getParameter("BaseModuleCode");
	String tBaseModuleName = request.getParameter("BaseModuleName");
	String tModuleDescribe = request.getParameter("ModuleDescribe");
	tLockBaseSchema.setLockModule(tBaseModuleCode);
	tLockBaseSchema.setModuleName(tBaseModuleName);
	tLockBaseSchema.setRemark(tModuleDescribe);
	tLockBaseSet.add(tLockBaseSchema);
	//并发控制组增删改
	String tLockGroupID = request.getParameter("hiddenLockGroupConfig");
	String tLockGroupName = request.getParameter("LockGroupName");
	String tLockGroupDescribe = request.getParameter("LockGroupDescribe");
	tLockGroupSchema.setLockGroup(tLockGroupID);
	tLockGroupSchema.setLockGroupName(tLockGroupName);
	tLockGroupSchema.setRemark(tLockGroupDescribe);
	tLockGroupSet.add(tLockGroupSchema);
	
	
	//并发控制组控制模块配置
	String tParamValue[] = request.getParameterValues("LockGroupConfigGrid2");
	int n = 0;
	if(tParamValue != null){
		n = tParamValue.length;
	}
	for (int i = 0; i < n; i++)
	{ 
		//判断参数信息是否为空，省去删除所有空行的必要
		if (tParamValue[i] != null && !tParamValue[i].equals(""))
		{   
			tLockConfigSchema = new LockConfigSchema();
			tLockConfigSchema.setLockGroup(tLockGroupID);
			tLockConfigSchema.setLockModule(tParamValue[i]);
			tLockConfigSet.add(tLockConfigSchema);
		}
	}
	
	
	GlobalInput tG = new GlobalInput();
	tG=(GlobalInput)session.getValue("GI");

	
  // 准备传输数据 VData
  tData.add(tUnLockReason);
	tData.add( tG );
	tData.add(tLockAppGroupSet);
	tData.add(tLockBaseSet);
	tData.add(tLockGroupSet);
	tData.add(tLockConfigSet);
//	tData.add(tLDTaskSet);
	
//	DealLockAppGroupBL tDealLockAppGroupBL = new DealLockAppGroupBL();
//	if(!tDealLockAppGroupBL.submitData(tData,tAction ))
//	{
//		Content = " 操作失败，原因是: " + tDealLockAppGroupBL.mErrors.getError(0).errorMessage;
//		FlagStr = "Fail";
//	}
	String busiName="DealLockAppGroupBL";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	if(!tBusinessDelegate.submitData(tData,tAction,busiName))
	{    
	    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	    { 
			Content = "操作失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else
		{
			Content = "操作失败";
			FlagStr = "Fail";				
		}
	}

	else
	{
		Content = " 操作成功! ";
		FlagStr = "Succ";

		tData.clear();
	}

%>
<%		


%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

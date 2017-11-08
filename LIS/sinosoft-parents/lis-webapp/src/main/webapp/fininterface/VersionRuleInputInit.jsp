<%
//程序名称：VersionRuleInputInit.jsp
//程序功能：财务规则版本控制
//创建日期：2008-08-21
//创建人  ：范昕  
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
  GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) 
	{
		out.println("网页超时，请重新登录");
		return;
	}
	String strOperator = globalInput.Operator;
%>                          
<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
  	fm.reset();
  	document.all('VersionNo').value = '';
  	document.all('VersionState').value = '';
  	document.all('StartDate').value = '';
  	document.all('EndDate').value = '';
  	document.all('VersionReMark').value = '';
  	document.all('Maintenanceno').value = '';
  	document.all('TraceVersionNo').value = '';
  	document.all('MaintenanceState').value = '';
  	document.all('MaintenanceReMark').value = '';
  }
  catch(ex)
  {
    alert("程序名称：VersionRuleInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
  }
  catch(re)
  {
    alert("程序名称：VersionRuleInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>

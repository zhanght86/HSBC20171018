 <%
//程序名称：FIRuleDefInputInit.jsp
//程序功能：校验规则定义
//创建日期：2008-08-18
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
    document.all('VersionState2').value = '';
    document.all('RuleID').value = '';
    document.all('RuleName').value = '';
    document.all('RuleDealMode').value = '';
    document.all('RuleDealModeName').value = '';
    document.all('RuleReturnMean').value = '';
    document.all('RuleDealClass').value = '';
    document.all('RuleDealSQL').value = '';
    
    document.all('updatebutton').disabled = true;	
    document.all('deletebutton').disabled = true;	
  }
  catch(ex)
  {
    alert("在FIRuleDefInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在FIRuleDefInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>

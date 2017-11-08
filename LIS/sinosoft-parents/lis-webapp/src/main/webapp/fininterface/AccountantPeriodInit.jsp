<%
//程序名称：AccountantPeriodInit.jsp
//程序功能：会计期间管理
//创建日期：2008-08-04
//创建人  ：范昕  
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
  GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
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
    document.all('Year').value = '';
    document.all('Month').value = '';  
  	fm.Operator.value = '<%= strOperator %>';
  	document.all('StartDay').value = '';
    document.all('EndDay').value='';           
    document.all('State').value = '';   
  }
  catch(ex)
  {
    alert("在AccountantPeriodInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在AccountantPeriodInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>

<%
//程序名称：FinDayListInit.jsp
//程序功能：
//创建日期：2002-08-16 15:39:06
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	if(globalInput == null) 
	{
		out.println("session has expired");
		return;
	}
	String strOperator = globalInput.Operator;
	String strcode= globalInput.ManageCom;
  String CurrentDate = PubFun.getCurrentDate();
%>

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
    document.all('StartDate').value = '<%=CurrentDate%>';
    document.all('EndDate').value = '<%=CurrentDate%>';
    document.all('Operator').value = '<%=strOperator%>';
    document.all('ManageCom').value = '<%=strcode%>';
  }
  catch(ex)
  {
    alert("在FinDayListInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("FinDayListInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>

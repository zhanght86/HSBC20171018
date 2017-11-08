<%
//程序名称：SysCertTakeBackInit.jsp
//程序功能：
//创建日期：2002-08-07
//创建人  ：周平
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.lis.pubfun.*"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<%
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("网页超时，请重新登录");
		return;
	}

	String strOperator = globalInput.Operator;
	String strCurDate = PubFun.getCurrentDate();	
	String strCurTime = PubFun.getCurrentTime();
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
  try
  {
  	fm.reset();
  	document.all('TakeBackMakeDate').value = '<%= strCurDate %>';
  	document.all('TakeBackMakeTime').value = '<%= strCurTime %>';
  	document.all('TakeBackOperator').value = '<%= strOperator %>';
  }
  catch(ex)
  {
    alert("在SysCertTakeBackInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("SysCertTakeBackInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

</script>

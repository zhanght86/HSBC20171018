<%
//程序名称：NBCListInit.jsp
//程序功能：
//创建日期：2003-05-14 15:39:06
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	//String strOperator = globalInput.Operator;
%>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
    document.all('StartDay').value = '';
    document.all('EndDay').value = '';
    document.all('SaleChnl').value = '';
  }
  catch(ex)
  {
    alert("在NBCListInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("NBCListInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>

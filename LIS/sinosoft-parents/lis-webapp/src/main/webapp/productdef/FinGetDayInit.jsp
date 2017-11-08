<%@include file="../i18n/language.jsp"%>
<%
//程序名称：FinPayDayInit.jsp
//程序功能：
//创建日期：2007-11-15 15:39:06
//创建人  ：sunyu程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getAttribute("GI");	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	//String strOperator = globalInput.Operator;
%>
<%
     //添加页面控件的初始化。
%>                            

<script type="text/javascript">
function initInpBox()
{ 
  try
  {     
    fm.all('StartDay').value = '';
    fm.all('EndDay').value = '';

  }
  catch(ex)
  {
    myAlert("在FinGetDayInit.jsp-->"+"初始化界面错误!");
  }      
}

function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=男&1=女&2=不详");      
//    setOption("sex","0=男&1=女&2=不详");        
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");   
  }
  catch(ex)
  {
    myAlert("在FinGetDayInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
  }
  catch(re)
  {
    myAlert("FinGetDayInit.jsp-->"+"初始化界面错误!");
  }
}
</script>
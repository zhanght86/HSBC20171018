<%@include file="/i18n/language.jsp"%>
<%
//Creator :张斌
//Date :2006-10-24
%>
<!--用户校验类-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>
<%
 GlobalInput tG = new GlobalInput();
 tG=(GlobalInput)session.getAttribute("GI");
 String CurrentDate	= PubFun.getCurrentDate();   
 String CurrentTime	= PubFun.getCurrentTime();
 String Operator   	= tG.Operator;
%>

<script type="text/javascript">
function initInpBox()
{
  try
  { 
		fm.StartDate.value='';
		fm.EndDate.value='';
		fm.ContNo.value='';
		fm.ContName.value='';
		fm.RIcomCode.value='';
		fm.RIcomName.value='';
  }
  catch(ex)
  {
    myAlert("初始化界面错误!");
  }
}

// 下拉框的初始化
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    myAlert("2在CertifyDescInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
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
		myAlert("3CertifyDescInit.jsp-->"+"初始化界面错误!");
	}
}

</script>
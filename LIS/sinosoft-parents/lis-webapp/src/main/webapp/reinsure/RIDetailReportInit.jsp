<%@include file="/i18n/language.jsp"%>
<%
//Creator :张斌
//Date :2007－7－5
%>
<!--用户校验类-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

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
		fm.RValidate.value='';
		fm.RInvalidate.value='';
		fm.RIComCode.value='';
		
//  fm.ContNo.value='';
//	fm.ReRiskCode.value='';
//	fm.ReRiskName.value='';	
  }
  catch(ex)
  {
    myAlert("进行初始化是出现错误！！！！");
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
    myAlert("3CertifyDescInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
  }
}

</script>

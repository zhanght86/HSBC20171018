<%
//程序名称：CertifyPrintInput.jsp
//程序功能：
//创建日期：2002-10-14 10:20:44
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	String strManageCom = globalInput.ComCode;
	String strOperator = globalInput.Operator;
	String strCurTime = PubFun.getCurrentDate();
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try {                                   
  	fm.reset();
  	fm.ManageCom.value = '<%= strManageCom %>';
  	fm.OperatorInput.value = '<%= strOperator %>';
  	fm.InputMakeDate.value = '<%= strCurTime %>';
  	fm.GetMakeDate.value = '<%= strCurTime %>';
  } catch(ex) {
    alert("在CertifyPrintInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try {
    initInpBox();
  } catch(re) {
    alert("CertifyPrintInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 设置提单信息
function setGetInfo()
{
	try {
		fm.GetMakeDate.value = '<%= strCurTime %>';
		fm.OperatorGet.value = '<%= strOperator %>';
	} catch(ex) {
		alert("在CertifyPrintInputInit.jsp-->setGetInfo函数中发生异常:初始化界面错误!");
	}
}
</script>

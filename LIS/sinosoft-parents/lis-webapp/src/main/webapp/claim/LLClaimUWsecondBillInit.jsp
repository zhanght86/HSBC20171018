
<%
	//程序名称：LLClaimUWsecondBillInput.jsp
	//程序功能：个险理赔未回销二核清单
	//创建日期：2009-04-15
	//创建人  ：mw
	//更新记录：
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%

%>
<script language="JavaScript">

//接收页面传递过来的参数
function initParam()
{
    document.all('MngCom').value = nullToEmpty("<%= tG.ManageCom %>");
    showOneCodeName("station","MngCom","MngComName"); 
    document.all('StartDate').value = '';
    document.all('EndDate').value = '';
}

function initForm()
{
	try
	{
		initParam();
	}
	catch(re)
	{
	  alert("在LLClaimNoRgtBillInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
}

//把null的字符串转为空
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}
</script>

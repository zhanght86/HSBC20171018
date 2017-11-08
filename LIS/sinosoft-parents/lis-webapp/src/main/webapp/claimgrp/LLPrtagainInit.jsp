<%
//**************************************************************************************************
//文件名称：LLPrtagainInit.jsp
//文件功能：单证补打原因录入页面初始化
//建立人：yuejw
//建立日期: 2005-08-21
//页面描述: 用于在补打单证时录入补打原因
//**************************************************************************************************
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">
//接收报案页面传递过来的参数
function initParam()
{
	fm.all('PrtSeq').value =nullToEmpty("<%=tPrtSeq%>");
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

function initForm()
{
    try
    {
    	initParam();
    	initLoprtQuery();
    }
    catch(re)
    {
        alert("在LLPrtagainInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}
</script>

<%
//**************************************************************************************************
//Name：CerifyBatchGetInputInit.jsp
//Function：单证批量导入作废功能
//Author：zhangzheng
//Date: 2009-08-10
//**************************************************************************************************
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">

var mCurrentDate = "";
var mNowYear = "";
var mNowMonth = "";
var mNowDay = "";
var mManageCom = "";

//接收报案页面传递过来的参数
function initParam()
{
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

function initInpBox()
{
   
}

function initSelBox()
{
    try
    {
    }
    catch(ex)
    {
        alert("在CerifyBatchGetInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
    }
}

function initForm()
{
        initParam();
        initInpBox();
        initSelBox();
}


</script>

<%
//**************************************************************************************************
//Name��CerifyBatchGetInputInit.jsp
//Function����֤�����������Ϲ���
//Author��zhangzheng
//Date: 2009-08-10
//**************************************************************************************************
%>
<!--�û�У����-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">

var mCurrentDate = "";
var mNowYear = "";
var mNowMonth = "";
var mNowDay = "";
var mManageCom = "";

//���ձ���ҳ�洫�ݹ����Ĳ���
function initParam()
{
}

//��null���ַ���תΪ��
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
        alert("��CerifyBatchGetInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
    }
}

function initForm()
{
        initParam();
        initInpBox();
        initSelBox();
}


</script>

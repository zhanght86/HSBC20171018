<%@include file="/i18n/language.jsp"%>
<%
//Creator :�ű�
//Date :2006-10-24
%>
<!--�û�У����-->

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
    myAlert("��ʼ���������!");
  }
}

// ������ĳ�ʼ��
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    myAlert("2��CertifyDescInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
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
		myAlert("3CertifyDescInit.jsp-->"+"��ʼ���������!");
	}
}

</script>
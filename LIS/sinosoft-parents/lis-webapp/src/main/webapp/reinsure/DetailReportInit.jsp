<%@include file="/i18n/language.jsp"%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
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
		fm.RValidate.value='';
		fm.RInvalidate.value='';
		fm.RIcomCode.value='';
		
  }
  catch(ex)
  {
    myAlert("��ʼ���������!");
  }
}

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
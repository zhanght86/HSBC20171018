<%@include file="/i18n/language.jsp"%>
<%
//Creator :�ű�
//Date :2007��7��5
%>
<!--�û�У����-->

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
    myAlert("���г�ʼ���ǳ��ִ��󣡣�����");
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
    myAlert("3CertifyDescInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
  }
}

</script>

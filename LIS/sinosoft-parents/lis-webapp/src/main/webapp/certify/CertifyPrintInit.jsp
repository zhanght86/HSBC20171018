<%
//�������ƣ�CertifyPrintInput.jsp
//�����ܣ�
//�������ڣ�2002-10-14 10:20:44
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//���ҳ��ؼ��ĳ�ʼ����
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
    alert("��CertifyPrintInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try {
    initInpBox();
  } catch(re) {
    alert("CertifyPrintInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// �����ᵥ��Ϣ
function setGetInfo()
{
	try {
		fm.GetMakeDate.value = '<%= strCurTime %>';
		fm.OperatorGet.value = '<%= strOperator %>';
	} catch(ex) {
		alert("��CertifyPrintInputInit.jsp-->setGetInfo�����з����쳣:��ʼ���������!");
	}
}
</script>

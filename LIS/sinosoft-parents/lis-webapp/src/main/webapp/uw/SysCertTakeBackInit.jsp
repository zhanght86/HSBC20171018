<%
//�������ƣ�SysCertTakeBackInit.jsp
//�����ܣ�
//�������ڣ�2002-08-07
//������  ����ƽ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.lis.pubfun.*"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<%
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("��ҳ��ʱ�������µ�¼");
		return;
	}

	String strOperator = globalInput.Operator;
	String strCurDate = PubFun.getCurrentDate();	
	String strCurTime = PubFun.getCurrentTime();
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
  try
  {
  	fm.reset();
  	document.all('TakeBackMakeDate').value = '<%= strCurDate %>';
  	document.all('TakeBackMakeTime').value = '<%= strCurTime %>';
  	document.all('TakeBackOperator').value = '<%= strOperator %>';
  }
  catch(ex)
  {
    alert("��SysCertTakeBackInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
  }
  catch(re)
  {
    alert("SysCertTakeBackInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

</script>

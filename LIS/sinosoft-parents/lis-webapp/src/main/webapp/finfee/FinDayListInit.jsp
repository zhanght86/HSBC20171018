<%
//�������ƣ�FinDayListInit.jsp
//�����ܣ�
//�������ڣ�2002-08-16 15:39:06
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	if(globalInput == null) 
	{
		out.println("session has expired");
		return;
	}
	String strOperator = globalInput.Operator;
	String strcode= globalInput.ManageCom;
  String CurrentDate = PubFun.getCurrentDate();
%>

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
    document.all('StartDate').value = '<%=CurrentDate%>';
    document.all('EndDate').value = '<%=CurrentDate%>';
    document.all('Operator').value = '<%=strOperator%>';
    document.all('ManageCom').value = '<%=strcode%>';
  }
  catch(ex)
  {
    alert("��FinDayListInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("FinDayListInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>

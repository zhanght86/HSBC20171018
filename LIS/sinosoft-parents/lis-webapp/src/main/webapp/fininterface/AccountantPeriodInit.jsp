<%
//�������ƣ�AccountantPeriodInit.jsp
//�����ܣ�����ڼ����
//�������ڣ�2008-08-04
//������  �����  
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
  GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("��ҳ��ʱ�������µ�¼");
		return;
	}
	String strOperator = globalInput.Operator;
%>                          
<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
  	fm.reset();     
    document.all('Year').value = '';
    document.all('Month').value = '';  
  	fm.Operator.value = '<%= strOperator %>';
  	document.all('StartDay').value = '';
    document.all('EndDay').value='';           
    document.all('State').value = '';   
  }
  catch(ex)
  {
    alert("��AccountantPeriodInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��AccountantPeriodInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>

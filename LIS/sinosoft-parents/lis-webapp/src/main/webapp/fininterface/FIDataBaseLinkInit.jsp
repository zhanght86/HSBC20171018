<%
//�������ƣ�FIDataBaseLinkInit.jsp
//�����ܣ����ݽӿ����ù���
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
  	document.all('InterfaceCode').value = '';
	fm.Operator.value = '<%= strOperator %>';
  	document.all('InterfaceName').value = '';
  	document.all('DBType').value = '';
  	document.all('IP1').value = '';
  	document.all('Port1').value = '';
  	document.all('DBName1').value = '';
  	document.all('ServerName1').value = '';
  	document.all('UserName1').value = '';
    document.all('PassWord1').value = '';
    
    document.all('IP2').value = '';
  	document.all('Port2').value = '';
  	document.all('DBName2').value = '';
  	document.all('ServerName2').value = '';
  	document.all('UserName2').value = '';
    document.all('PassWord2').value = '';
    
    document.all('IP3').value = '';
  	document.all('Port3').value = '';
  	document.all('DBName3').value = '';
  	document.all('ServerName3').value = '';
  	document.all('UserName3').value = '';
    document.all('PassWord3').value = '';
    
    document.all('IP4').value = '';
  	document.all('Port4').value = '';
  	document.all('DBName4').value = '';
  	document.all('ServerName4').value = '';
  	document.all('UserName4').value = '';
    document.all('PassWord4').value = '';
  }
  catch(ex)
  {
    alert("��FIDataBaseLinkInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��FIDataBaseLinkInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>

<%
//�������ƣ�VersionRuleInputInit.jsp
//�����ܣ��������汾����
//�������ڣ�2008-08-21
//������  �����  
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
  GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) 
	{
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
  	document.all('VersionNo').value = '';
  	document.all('VersionState').value = '';
  	document.all('StartDate').value = '';
  	document.all('EndDate').value = '';
  	document.all('VersionReMark').value = '';
  	document.all('Maintenanceno').value = '';
  	document.all('TraceVersionNo').value = '';
  	document.all('MaintenanceState').value = '';
  	document.all('MaintenanceReMark').value = '';
  }
  catch(ex)
  {
    alert("�������ƣ�VersionRuleInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("�������ƣ�VersionRuleInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>

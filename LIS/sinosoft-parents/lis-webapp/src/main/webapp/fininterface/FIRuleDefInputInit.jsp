 <%
//�������ƣ�FIRuleDefInputInit.jsp
//�����ܣ�У�������
//�������ڣ�2008-08-18
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
    document.all('VersionState2').value = '';
    document.all('RuleID').value = '';
    document.all('RuleName').value = '';
    document.all('RuleDealMode').value = '';
    document.all('RuleDealModeName').value = '';
    document.all('RuleReturnMean').value = '';
    document.all('RuleDealClass').value = '';
    document.all('RuleDealSQL').value = '';
    
    document.all('updatebutton').disabled = true;	
    document.all('deletebutton').disabled = true;	
  }
  catch(ex)
  {
    alert("��FIRuleDefInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��FIRuleDefInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>

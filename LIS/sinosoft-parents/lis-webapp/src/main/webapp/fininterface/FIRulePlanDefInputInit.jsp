 <%
//�������ƣ�FIRulePlanDefInputInit.jsp
//�����ܣ�У��ƻ�����
//�������ڣ�2008-09-16
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
	  document.all('RulesPlanID').value = '';        
    document.all('RulesPlanName').value = '';
    document.all('RulePlanState').value = '';        
    document.all('RulePlanStateName').value = '';  
    document.all('CallPointID').value = '';        
    document.all('CallPointIDName').value = '';    
    document.all('MarkInfo').value = '';
    document.all('Sequence').value = '';
    
    document.all('updatebutton').disabled = true;	
    document.all('deletebutton').disabled = true;	
  }
  catch(ex)
  {
    alert("��FIRulePlanDefInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��FIRulePlanDefInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>

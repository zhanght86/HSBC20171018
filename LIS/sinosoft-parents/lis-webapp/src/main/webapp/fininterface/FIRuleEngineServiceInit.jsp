<%
//�������ƣ�FIRuleEngineServiceInit.jsp
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
                       
<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
  	fm.reset();     
  	document.all('StartDate').value = '';
    document.all('EndDate').value='';           
    document.all('VersionNo').value='';
    document.all('callpoint').value='';
    document.all('callpointName').value='';
    document.all('VersionState').value='';
    document.all('VersionState2').value='';
  }
  catch(ex)
  {
    alert("��FIRuleEngineServiceInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��FIRuleEngineServiceInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>

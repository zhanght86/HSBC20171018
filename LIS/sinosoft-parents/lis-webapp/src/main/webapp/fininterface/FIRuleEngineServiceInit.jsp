<%
//程序名称：FIRuleEngineServiceInit.jsp
%>
<!--用户校验类-->
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
    alert("在FIRuleEngineServiceInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在FIRuleEngineServiceInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>

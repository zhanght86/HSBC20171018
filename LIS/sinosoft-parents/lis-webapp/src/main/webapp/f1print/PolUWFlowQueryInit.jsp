<script language="JavaScript">
// 输入框的初始化（单记录部分）
function initInpBox()
{ 
  try
  {                                   
	// 保单查询条件
    document.all('StartDate').value = '';
    document.all('EndDate').value = '';
    document.all('UWReportType').value = '';
   // document.all('UWManageType').value = '';
  //  document.all('AppSalechnl').value = '';
    
  }
  catch(ex)
  {
    alert("在PolAppStatInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("PolAppStatInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>
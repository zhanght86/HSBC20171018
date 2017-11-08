<script language="JavaScript">
// 输入框的初始化（单记录部分）
function initInpBox()
{ 
  try
  {                                   
	// 保单查询条件
    document.all('StartDate').value = '';
    document.all('EndDate').value = '';
    document.all('ManageCom').value = '';
    document.all('HQReportType').value = 'HQ05';    
  }
  catch(ex)
  {
    alert("HospitalQualityQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("HospitalQualityQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>

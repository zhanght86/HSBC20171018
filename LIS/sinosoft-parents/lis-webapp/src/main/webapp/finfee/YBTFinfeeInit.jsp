<%
//程序名称：YBTFinfeeInit.jsp
//程序功能：
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%> 

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
    document.all('FeeSum').value = '';
    document.all('FileName').value = '';
  }
  catch(ex)
  {
    alert("在YBTFinfeeInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("YBTFinfeeInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>

<%
//程序名称：LDExOtherRateInit.jsp
//程序功能：
//创建日期：2009-10-13 9:42:43
//创建人  ：ZhanPeng程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {                                   
    document.all('CurrCode').value = '';
    document.all('CurrCodeName').value = '';
    document.all('Per').value = '';
    document.all('DestCurrCode').value = '';
    document.all('DestCodeName').value = '';
    document.all('ExchRate').value = '';
    document.all('StartDate').value = '';
    document.all('EndDate').value = '';

  }
  catch(ex)
  {
    alert("在LDExOtherRateInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("LDExOtherRateInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>

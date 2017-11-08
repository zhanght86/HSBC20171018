<%
//程序名称：LDMthMidRateInit.jsp
//程序功能：
//创建日期：2009-10-12 19:29:43
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
    document.all('DestCode').value = '';
    document.all('DestCodeName').value = '';
    document.all('Average').value = '';
    document.all('ValidYear').value = '';
    document.all('ValidMonth').value = '';

  }
  catch(ex)
  {
    alert("在LDMthMidRateInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("LDMthMidRateInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>

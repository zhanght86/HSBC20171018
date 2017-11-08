<%
//程序名称：LDExRateInit.jsp
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
    document.all('ExchBid').value = '';
    document.all('ExchOffer').value = '';
    document.all('CashBid').value = '';
    document.all('CashOffer').value = '';
    document.all('Middle').value = '';
    document.all('MakeDate').value = '';
    document.all('MakeTime').value = '';
    document.all('EndDate').value = '';
    document.all('EndTime').value = '';

  }
  catch(ex)
  {
    alert("在LDExRateInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("LDExRateInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>

<%
//程序名称：LDCodeInput.jsp
//程序功能：
//创建日期：2005-01-26 13:18:17
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%
     //添加页面控件的初始化。
%>                            
<script language="JavaScript">
function initInpBox()
{ 
  try
  {                                   
    document.all('CodeType').value = "";
    document.all('Code').value = "";
    document.all('CodeName').value = "";
    document.all('CodeAlias').value = "";
    document.all('ComCode').value = "";
    document.all('OtherSign').value = "";
  }
  catch(ex)
  {
    alert("在LDCodeInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=男&1=女&2=不详");      
//    setOption("sex","0=男&1=女&2=不详");        
  }
  catch(ex)
  {
    alert("在LDCodeInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        
function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
  }
  catch(re)
  {
    alert("LDCodeInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>

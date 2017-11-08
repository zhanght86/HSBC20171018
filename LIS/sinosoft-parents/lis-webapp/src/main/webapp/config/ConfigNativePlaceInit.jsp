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
    document.all('CurCountry').value = "";
  }
  catch(ex)
  {
    alert("ConfigNativePlaceInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("ConfigNativePlaceInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        
function initForm()
{
  try
  {
    initInpBox();
    initSelBox();   
    queryCurrentCountry(); 
  }
  catch(re)
  {
    alert("ConfigNativePlaceInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>

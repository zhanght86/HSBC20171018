<%
//程序名称：PEdorTypeCTTestInit.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  ：Supl
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->

<%
  String CurrentDate = PubFun.getCurrentDate();
%>                         

<script language="JavaScript">  
function initInpBox()
{ 

  try
  {        
    fm.ContNo.value = "";
    
  }
  catch(ex)
  {
    alert("在PEdorTypeCTInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

//把null的字符串转为空
function nullToEmpty(string)
{
    if ((string == "null") || (string == "undefined"))
    {
        string = "";
    }
    return string;
}

function initSelBox()
{  
  try                 
  {  
  }
  catch(ex)
  {
    alert("在PEdorTypeCTInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
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
    alert("PEdorTypeCTInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

</script>

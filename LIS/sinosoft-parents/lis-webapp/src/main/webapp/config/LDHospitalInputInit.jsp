<%
//程序名称：LDUWUserInput.jsp
//程序功能：
//创建日期：2005-01-24 18:15:01
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
   document.all('HospitalCode').disabled=false;    
   //document.all('deletebutton').disabled=true;              
    document.all('HospitalCode').value = "";
    document.all('HospitalName').value = "";
    document.all('HospitalGrade').value = "";
    document.all('ManageCom').value = "";
    document.all('ValidityDate').value = "";
    document.all('Address').value = "";
    document.all('HospitalGradeName').value = "";
    document.all('ManageComName').value = "";
     document.all('HosStateName').value = "";
      document.all('HosState').value = "";
       document.all('Remark').value = "";
  }
  catch(ex)
  {
    alert("在LDUWUserInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在LDUWUserInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
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
    alert("LDUWUserInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


</script>

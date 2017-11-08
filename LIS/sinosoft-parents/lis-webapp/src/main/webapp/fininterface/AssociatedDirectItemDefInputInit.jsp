 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//创建日期：2008-09-16
//创建人  ：Fanxin
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>                    

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  { 	
    document.all('ColumnID').value = '';
    document.all('SourceTableID').value = 'FIAboriginalData';
    document.all('SourceColumnID').value = '';              
    document.all('ReMark').value = '';                                             
  	document.all('ColumnID1').value = '';
		document.all('updatebutton').disabled = true;		
		document.all('deletebutton').disabled = true;  	       
  }
  catch(ex)
  {
    alert("在AssociatedDirectItemDefInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}



function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("在AssociatedDirectItemDefInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
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
    alert("在AssociatedDirectItemDefInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}



</script>

<%
//程序名称：
//程序功能：客户联系方式变更
//创建日期：2008-12-5 12:49下午
//创建人  ：pst
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->

<%
    
%>                            

<script language="JavaScript">  
function initInpBox()
{ 

  try
  {  
    //Edorquery();
    document.all('CustomerNo').value = top.opener.document.all('InsuredNo').value;
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value; 
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value; 
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value; 
    showOneCodeName('EdorCode','EdorType','EdorTypeName');
  }
  catch(ex)
  {
    alert("在PEdorTypeBBInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

                                       

function initForm()
{
  try
  {
   
    initInpBox();  
    initInpRole();  
    initInpCustomerInfo();
    initInpPCustomerInfo();
  }
  catch(re)
  {
    alert("PEdorTypeBBInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


</script>
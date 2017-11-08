


<%@page import="com.sinosoft.lis.pubfun.*"%>
 
 
<%
	//添加页面控件的初始化。
    GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	 String  strOperator =  globalInput.Operator;
	 String  tManageCom =  globalInput.ManageCom;
	
	
%>
        

<script language="JavaScript">


function initInpBox()
{ 
  try
  
  {  
  
		 document.all('ManageCom').value = '';
		 document.all('SaleChnl').value = '';
		 document.all('PayIntv').value = '';
		 document.all('PolType').value = '';
		 document.all('StartDate').value ='';
		 document.all('EndDate').value ='';
       
	}
  catch(ex)
  {
    alert("AppBirthdayBillInit.jspInitInpBox函数中发生异常:初始化界面错误!");
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
    alert("AppBirthdayBillInit.jspInitForm函数中发生异常:初始化界面错误!");
  }
}
</script>


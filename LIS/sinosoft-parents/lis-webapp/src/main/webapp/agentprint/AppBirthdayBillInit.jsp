


<%@page import="com.sinosoft.lis.pubfun.*"%>
 
 
<%
	//���ҳ��ؼ��ĳ�ʼ����
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
    alert("AppBirthdayBillInit.jspInitInpBox�����з����쳣:��ʼ���������!");
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
    alert("AppBirthdayBillInit.jspInitForm�����з����쳣:��ʼ���������!");
  }
}
</script>


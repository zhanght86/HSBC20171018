<%
//程序名称：LCPolBillInit.jsp
//程序功能：
//创建日期：2003-1-15
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
	PubFun tpubFun = new PubFun();
	String strCurDay = tpubFun.getCurrentDate();
	
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		loggerDebug("LCPolBillInit","session has expired");
		return;
	}
	
	String strOperator = globalInput.Operator;
%>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
    document.all('StartDay').value = "<%=strCurDay%>";
    document.all('EndDay').value = "<%=strCurDay%>";
    document.all('StartTime').value = '00:00:00';
    document.all('EndTime').value = '23:59:59';
    document.all('ManageCom').value = '';    
    document.all('SaleChnl').value = '';
    document.all('StartContNo').value = "";
    document.all('EndContNo').value = "";
    document.all('ManageGrade').value = "4";
    document.all('MangeComGradeName').value = "二级机构";  
    document.all('ManageCom').value = <%=globalInput.ComCode%>;
  }
  catch(ex)
  {
    alert("在LCPolBillInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=男&1=女&2=不详");      
//    setOption("sex","0=男&1=女&2=不详");        
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");   
  }
  catch(ex)
  {
    alert("在LCPolBillInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
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
    alert("LCPolBillInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>

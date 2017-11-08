<%
//程序名称：EdorCLaimBackBillPrintInit.jsp
//程序功能：
//创建日期：2003-05-14 15:39:06
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.BqNameFun"%>

<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	String strOperator = globalInput.Operator;
	String strManageCom = globalInput.ComCode;
	////默认统计起止期为上一财务月份
	//String[] dateArr = BqNameFun.getPreFinaMonth(PubFun.getCurrentDate());
	//默认日期现改为自然月份，2006-05-08张超提出
    String[] dateArr = BqNameFun.getNomalMonth(PubFun.getCurrentDate());	
	//String strOperator = globalInput.Operator;
%>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
   document.all('StartDay').value = '<%=dateArr[0]%>';
	 document.all('EndDay').value = '<%=dateArr[1]%>';
	 document.all('ManageCom').value = "<%=strManageCom%>";
    document.all('SaleChnl').value = '';
  }
  catch(ex)
  {
    alert("在EdorCLaimBackBillPrintInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("EdorCLaimBackBillPrintInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>

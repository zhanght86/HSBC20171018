<%
//程序名称：EdorFormPrintInit.jsp
//程序功能：报表打印
//创建日期：2005-08-13 15:39:06
//创建人  ：liurx
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
%>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {
     
    document.all('StartDate').value = '<%=dateArr[0]%>';
	document.all('EndDate').value = '<%=dateArr[1]%>';
	document.all('SaleChnl').value = '';
	document.all('BillType').value = '';
	document.all('BillTypeName').value = '';
	document.all('ManageCom').value = <%=strManageCom%>;
	document.all('ChnlType').value = '';	
	document.all('ChnlSel').value = '';	
	document.all('RiskFlag').value = "NO";
  }
  catch(ex)
  {
    alert("在EdorFormPrintInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initSelBox()
{  
  try                 
  {
 
  }
  catch(ex)
  {
    alert("在EdorFormPrintInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox(); 
    //initDate(); //统计起期和止期置默认值 
    //initRiskCode();

  }
  catch(re)
  {
    alert("EdorFormPrintInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

</script>

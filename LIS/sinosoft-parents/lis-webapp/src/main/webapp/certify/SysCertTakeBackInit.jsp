

<%@page import="com.sinosoft.lis.pubfun.*"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<%
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");	
	if(globalInput == null) {
		out.println("网页超时，请重新登录");
		return;
	}

	String strOperator = globalInput.Operator;
	String strCurDate = PubFun.getCurrentDate();	
	String strCurTime = PubFun.getCurrentTime();
	String tManageCom = PubFun.RCh(globalInput.ManageCom, "0",6);
	String ttManageCom = globalInput.ManageCom;
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
  try
  {
  	fm.reset();
  	//fm.TakeBackDate.value = '<%= strCurDate %>';
  	fm.TakeBackMakeDate.value = '<%= strCurDate %>';
  	fm.TakeBackMakeTime.value = '<%= strCurTime %>';
  	fm.TakeBackOperator.value = '<%= strOperator %>';
  	fm.hideTakeBackDate.value = '';
  	fm.CertifyNo.value = '<%= tManageCom %>'+sysdate.substring(0,4);
  	fm.ManageCom.value = '<%= ttManageCom %>';
  	fm.CertifyNo.value = '<%= tContNo%>';
  	fm.AgentName.value = "<%= tAgentName %>";
  }
  catch(ex)
  {
    alert("在SysCertTakeBackInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
    checkScan();
    queryagent();
  }
  catch(re)
  {
    alert("SysCertTakeBackInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

</script>

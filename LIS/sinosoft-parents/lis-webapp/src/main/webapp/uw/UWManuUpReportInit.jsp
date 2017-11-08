<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuRReportInit.jsp
//程序功能：新契约人工核保生存调查报告录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String tContNo = "";
  String tPrtNo = "";
  String tMissionID = "";
  String tSubMissionID = "";
  String tFlag = "";
  tContNo = request.getParameter("ContNo");  
   tPrtNo = request.getParameter("PrtNo");  
  tMissionID = request.getParameter("MissionID");  
  tSubMissionID = request.getParameter("SubMissionID");
  tFlag = request.getParameter("Flag");
%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	
	String strOperator = globalInput.Operator;
	//loggerDebug("UWManuUpReportInit","1:"+strOperator);
%>

<script language="JavaScript">

var str = "";

// 输入框的初始化（单记录部分）
function initInpBox(tContNo)
{ 
try
  {                                   

  }
  catch(ex)
  {
    alert("在PEdorUWManuRReportInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }   
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("在PEdorUWManuRReportInit-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm(tContNo,tPrtNo,tMissionID,tSubMissionID)
{
  var str = "";
  try
  {
	initInpBox(tContNo);
 
	initHide(tContNo,tMissionID,tPrtNo,tSubMissionID);
	
	
	easyQueryClick();
	
	initLWMission();

  }
  catch(re)
  {
    alert("PEdorUWManuRReportInit-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initHide(tContNo,tMissionID,tPrtNo,tSubMissionID)
{

	document.all('ProposalNoHide').value=tContNo;
	document.all('MissionID').value=tMissionID;
	document.all('SubMissionID').value=tSubMissionID;	
	document.all('PrtNo').value=tPrtNo;
	document.all('Flag').value=<%=tFlag%>;
	
}




</script>



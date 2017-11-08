<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：PEdorUWManuRReportInit.jsp
//程序功能：保全人工核保生存调查报告录入
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
  String tPolNo = "";
  String tEdorNo = "";
  String tMissionID = "";
  String tSubMissionID = "";
  String tFlag = "";
  tPolNo = request.getParameter("PolNo");  
  tEdorNo = request.getParameter("EdorNo");  
  tMissionID = request.getParameter("MissionID");  
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
	//System.out.println("1:"+strOperator);
%>

<script language="JavaScript">

var str = "";

// 输入框的初始化（单记录部分）
function initInpBox(tPolNo)
{ 
try
  {                                   
	// 延长日期天数
    //document.all('Prem').value = '';
    //document.all('SumPrem').value = '';
    //document.all('Mult').value = '';
    //document.all('RiskAmnt').value = '';
    document.all('PolNo').value = tPolNo;
    document.all('Operator').value = '<%= strOperator %>';
    document.all('Content').value = '';
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
//    setOption("t_sex","0=男&1=女&2=不详");      
//    setOption("sex","0=男&1=女&2=不详");        
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");   
  }
  catch(ex)
  {
    alert("在PEdorUWManuRReportInit-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm(tPolNo,tEdorNo,tMissionID,tSubMissionID)
{
  var str = "";
  try
  {

	initInpBox(tPolNo);

	initHide(tPolNo,tEdorNo,tMissionID);

	easyQueryClick();
  }
  catch(re)
  {
    alert("PEdorUWManuRReportInit-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initHide(tPolNo,tEdorNo,tMissionID)
{

	document.all('ProposalNoHide').value=tPolNo;
	document.all('EdorNo').value=tEdorNo;
	document.all('MissionID').value=tMissionID;
	document.all('Flag').value=<%=tFlag%>;
	
}

</script>



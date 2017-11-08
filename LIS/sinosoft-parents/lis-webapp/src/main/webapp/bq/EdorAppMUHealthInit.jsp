<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：EdorAppMUHealthInit.jsp
//程序功能：保全人工核保体检资料录入
//创建日期：2005-06-13 13:09:00
//创建人  ：liurongxiao
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="java.util.*"%>
  <%@page import="java.lang.Math.*"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  
  String tContNo = "";
  String tMissionID = "";
  String tSubMissionID = "";
  String tFlag = "";
  String tUWIdea = "";  
  String tPrtNo = "";
  String tEdorNo = "";
  String tEdorAcceptNo= "";
  Date today = new Date();
  today = PubFun.calDate(today,15,"D",null);
  String tday = UWPubFun.getFixedDate(today);
  tContNo = request.getParameter("ContNo");
  tMissionID = request.getParameter("MissionID");
  tSubMissionID = request.getParameter("SubMissionID");
  tPrtNo = request.getParameter("PrtNo");
  tEdorNo = request.getParameter("EdorNo");
  tEdorAcceptNo = request.getParameter("EdorAcceptNo");
%>                 

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
try
  {                                   
	
    document.all('ContNo').value = '';
    document.all('MissionID').value = '';
    document.all('SubMissionID').value = '';
    document.all('EDate').value = '<%=tday%>';
    document.all('PrintFlag').value = '';
    document.all('Hospital').value = '';
    document.all('IfEmpty').value = '';
    document.all('InsureNo').value = '';
    document.all('Note').value = '';
    document.all('bodyCheck').value = '';
  }
  catch(ex)
  {
    alert("在EdorAppMUHealthInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在EdorAppMUHealthInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm(tContNo,tMissionID,tSubMissionID,tPrtNo,tEdorNo,tEdorAcceptNo)
{
  try
  { 
	initInpBox();

	initHide(tContNo,tMissionID,tSubMissionID,tPrtNo,tEdorNo,tEdorAcceptNo);
	
	initHospital(tContNo,tEdorNo);
	
	initInsureNo(tContNo,tEdorNo);
	
	easyQueryClickSingle();
	


 }
  catch(re) {
    alert("EdorAppMUHealthInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}





function initHide(tContNo,tMissionID,tSubMissionID,tPrtNo,tEdorNo,tEdorAcceptNo)
{
	document.all('ContNo').value = tContNo;
    document.all('MissionID').value = tMissionID;
	document.all('SubMissionID').value = tSubMissionID;
	document.all('PrtNo').value = tPrtNo ;
	document.all('EdorNo').value = tEdorNo;
	document.all('EdorAcceptNo').value = tEdorAcceptNo;
	
}

</script>



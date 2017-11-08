<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：QuestReplyInit.jsp
//程序功能：问题件回复
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
<%
  String tContNo = "";
  String tFlag = "";
  String tQuest = "";

  tContNo = request.getParameter("ContNo");
  tFlag = request.getParameter("Flag");
  tQuest = request.getParameter("Quest");

  loggerDebug("QuestReplyInit","ContNo:"+tContNo);
  loggerDebug("QuestReplyInit","Flag:"+tFlag);
  loggerDebug("QuestReplyInit","issuetype:"+tQuest);

%>                            

<script language="JavaScript">


// 输入框的初始化（单记录部分）
function initInpBox(tContNo)
{ 
try
  {                                   
    document.all('ContNo').value = tContNo;
    document.all('Content').value = '';
    document.all('ReplyResult').value = '';
  }
  catch(ex)
  {
    alert("在UWManuDateInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }   
}

function initForm(tContNo,tFlag,tQuest)
{
  try
  {
	//alert(3);
	initInpBox(tContNo);	
	//alert(2);
	initHide(tContNo,tFlag,tQuest);	
	//alert(tQuest);
	QueryCont(tContNo,tFlag,tQuest);

  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


function initHide(tContNo,tFlag,tQuest)
{
	document.all('ContNo').value=tContNo;
	document.all('Flag').value=tFlag;
	document.all('Quest').value=tQuest;
	//alert("pol:"+tContNo);
}

</script>



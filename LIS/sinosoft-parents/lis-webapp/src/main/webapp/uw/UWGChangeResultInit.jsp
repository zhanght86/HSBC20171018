<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
  String tGrpContNo = "";
  String tMissionID = "";
 
  tGrpContNo = request.getParameter("GrpContNo");
  tMissionID = request.getParameter("MissionID");
  String tEdorNo=request.getParameter("EdorNo"); // 业务类型，捕获后如果是保全则按保全处理，否则按新契约处理add by liuxiaosong 2006-11-15 
  String tPrtNo=request.getParameter("PrtNo"); //印刷号，add  by liuxiaosong 2006-11-17
  String tEdorType=request.getParameter("EdorType"); //保全类型 ,add by liuxiaosong 2006-11-16
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>                      
<script language="JavaScript">   

function initForm()
{
  try
  {
	initInpBox();
  }
  catch(re)
  {
    alert("UWGChangeResultInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 输入框的初始化（单记录部分）
function initInpBox()
{
	document.all('GrpContNo').value="<%=tGrpContNo%>";
	document.all('MissionID').value="<%=tMissionID%>";
	fm.EdorNo.value="<%=tEdorNo%>";
	fm.EdorType.value="<%=tEdorType%>";
	fm.PrtNo.value="<%=tPrtNo%>";

}

</script>



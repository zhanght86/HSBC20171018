<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
  String tGrpContNo = "";
  String tMissionID = "";
 
  tGrpContNo = request.getParameter("GrpContNo");
  tMissionID = request.getParameter("MissionID");
  String tEdorNo=request.getParameter("EdorNo"); // ҵ�����ͣ����������Ǳ�ȫ�򰴱�ȫ������������Լ����add by liuxiaosong 2006-11-15 
  String tPrtNo=request.getParameter("PrtNo"); //ӡˢ�ţ�add  by liuxiaosong 2006-11-17
  String tEdorType=request.getParameter("EdorType"); //��ȫ���� ,add by liuxiaosong 2006-11-16
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
    alert("UWGChangeResultInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{
	document.all('GrpContNo').value="<%=tGrpContNo%>";
	document.all('MissionID').value="<%=tMissionID%>";
	fm.EdorNo.value="<%=tEdorNo%>";
	fm.EdorType.value="<%=tEdorType%>";
	fm.PrtNo.value="<%=tPrtNo%>";

}

</script>



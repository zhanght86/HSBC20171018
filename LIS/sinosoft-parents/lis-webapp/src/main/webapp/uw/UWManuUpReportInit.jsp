<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuRReportInit.jsp
//�����ܣ�����Լ�˹��˱�������鱨��¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
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
	//���ҳ��ؼ��ĳ�ʼ����
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

// �����ĳ�ʼ��������¼���֣�
function initInpBox(tContNo)
{ 
try
  {                                   

  }
  catch(ex)
  {
    alert("��PEdorUWManuRReportInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("��PEdorUWManuRReportInit-->InitSelBox�����з����쳣:��ʼ���������!");
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
    alert("PEdorUWManuRReportInit-->InitForm�����з����쳣:��ʼ���������!");
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



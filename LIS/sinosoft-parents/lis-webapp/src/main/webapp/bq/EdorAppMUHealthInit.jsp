<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�EdorAppMUHealthInit.jsp
//�����ܣ���ȫ�˹��˱��������¼��
//�������ڣ�2005-06-13 13:09:00
//������  ��liurongxiao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
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

// �����ĳ�ʼ��������¼���֣�
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
    alert("��EdorAppMUHealthInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��EdorAppMUHealthInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
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
    alert("EdorAppMUHealthInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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



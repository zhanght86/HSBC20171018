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
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
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
	//loggerDebug("UWManuUpReportReplyInit","1:"+strOperator);
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
    alert("��UWManuUpReportReplyInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��UWManuUpReportReplyInit-->InitSelBox�����з����쳣:��ʼ���������!");
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
	
	

  }
  catch(re)
  {
    alert("UWManuUpReportReplyInit-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initHide(tContNo,tMissionID,tPrtNo,tSubMissionID)
{

	fm.all('ProposalNoHide').value=tContNo;
	fm.all('MissionID').value=tMissionID;
	fm.all('SubMissionID').value=tSubMissionID;	
	fm.all('PrtNo').value=tPrtNo;
	fm.all('Flag').value=<%=tFlag%>;
	
}




</script>



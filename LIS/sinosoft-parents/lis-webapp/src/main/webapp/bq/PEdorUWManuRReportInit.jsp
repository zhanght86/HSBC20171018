<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�PEdorUWManuRReportInit.jsp
//�����ܣ���ȫ�˹��˱�������鱨��¼��
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
	//���ҳ��ؼ��ĳ�ʼ����
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

// �����ĳ�ʼ��������¼���֣�
function initInpBox(tPolNo)
{ 
try
  {                                   
	// �ӳ���������
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
    alert("��PEdorUWManuRReportInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }   
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=��&1=Ů&2=����");      
//    setOption("sex","0=��&1=Ů&2=����");        
//    setOption("reduce_flag","0=����״̬&1=�����");
//    setOption("pad_flag","0=����&1=�潻");   
  }
  catch(ex)
  {
    alert("��PEdorUWManuRReportInit-->InitSelBox�����з����쳣:��ʼ���������!");
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
    alert("PEdorUWManuRReportInit-->InitForm�����з����쳣:��ʼ���������!");
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



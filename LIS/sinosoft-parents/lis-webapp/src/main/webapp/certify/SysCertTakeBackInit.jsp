

<%@page import="com.sinosoft.lis.pubfun.*"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<%
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");	
	if(globalInput == null) {
		out.println("��ҳ��ʱ�������µ�¼");
		return;
	}

	String strOperator = globalInput.Operator;
	String strCurDate = PubFun.getCurrentDate();	
	String strCurTime = PubFun.getCurrentTime();
	String tManageCom = PubFun.RCh(globalInput.ManageCom, "0",6);
	String ttManageCom = globalInput.ManageCom;
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
  try
  {
  	fm.reset();
  	//fm.TakeBackDate.value = '<%= strCurDate %>';
  	fm.TakeBackMakeDate.value = '<%= strCurDate %>';
  	fm.TakeBackMakeTime.value = '<%= strCurTime %>';
  	fm.TakeBackOperator.value = '<%= strOperator %>';
  	fm.hideTakeBackDate.value = '';
  	fm.CertifyNo.value = '<%= tManageCom %>'+sysdate.substring(0,4);
  	fm.ManageCom.value = '<%= ttManageCom %>';
  	fm.CertifyNo.value = '<%= tContNo%>';
  	fm.AgentName.value = "<%= tAgentName %>";
  }
  catch(ex)
  {
    alert("��SysCertTakeBackInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
    checkScan();
    queryagent();
  }
  catch(re)
  {
    alert("SysCertTakeBackInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

</script>

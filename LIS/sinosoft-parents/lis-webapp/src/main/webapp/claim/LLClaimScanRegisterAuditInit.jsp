<%
//�������ƣ�LLClaimScanRegisterAuditInit.jsp
//�����ܣ�
//�������ڣ�2005-6-6 11:30
//������  ��zl
//���¼�¼��
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
   String tCurrentDate = PubFun.getCurrentDate();
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">
var CurrentDate="<%=tCurrentDate%>";

//���ղ���
function initParam()
{
    document.all('Operator').value = nullToEmpty("<%= tGlobalInput.Operator %>");
    document.all('ComCode').value = nullToEmpty("<%= tGlobalInput.ComCode %>");
    document.all('ManageCom').value = nullToEmpty("<%= tGlobalInput.ManageCom %>");
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
    document.all('CurDate').value = CurrentDate;
    document.all('MissionID').value = nullToEmpty("<%= tMissionID %>");
    document.all('SubMissionID').value = nullToEmpty("<%= tSubMissionID %>");
    document.all('ActivityID').value = nullToEmpty("<%= tActivityID %>");
}

//��null���ַ���תΪ��
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

// �����ĳ�ʼ��
function initInpBox()
{

  try
  {                                   
  }
  catch(ex)
  {
    alert("��LLClaimRegisterMissInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
//    setOption("sex","0=��&1=Ů&2=����");        
  }
  catch(ex)
  {
    alert("��LLClaimRegisterMissInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initParam();
    initInpBox();
    initSelBox();  

	
  }
  catch(re)
  {
    alert("LLClaimRegisterMissInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}




</script>

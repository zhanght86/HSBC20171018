<%
//�������ƣ�EdorWorkloadCountBillPrintInit.jsp
//�����ܣ��嵥��ӡ
//�������ڣ�2005-08-08 15:39:06
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.BqNameFun"%>

<%
	//����ҳ��ؼ��ĳ�ʼ����
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");

	if(globalInput == null) {
		out.println("session has expired");
		return;
	}

	String strOperator = globalInput.Operator;
	String strManageCom = globalInput.ComCode;
	////Ĭ��ͳ����ֹ��Ϊ��һ�����·�
	//String[] dateArr = BqNameFun.getPreFinaMonth(PubFun.getCurrentDate());
	//Ĭ�������ָ�Ϊ��Ȼ�·ݣ�2006-05-08�ų����
    String[] dateArr = BqNameFun.getNomalMonth(PubFun.getCurrentDate());
%>
<%
     //����ҳ��ؼ��ĳ�ʼ����
%>

<script language="JavaScript">
function initInpBox()
{
  try
  {
  document.all('StartDate').value = '<%=dateArr[0]%>';
	document.all('EndDate').value = '<%=dateArr[1]%>';
	document.all('SaleChnl').value = "";
	document.all('ManageCom').value = "<%=strManageCom%>";
  }
  catch(ex)
  {
    alert("��EdorWorkloadCountBillPrintInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

function initSelBox()
{
  try
  {

  }
  catch(ex)
  {
    alert("��EdorWorkloadCountBillPrintInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    //initDate(); //ͳ�����ں�ֹ����Ĭ��ֵ

  }
  catch(re)
  {
    alert("EdorWorkloadCountBillPrintInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

</script>
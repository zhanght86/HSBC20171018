<%
//�������ƣ�EdorCLaimBackBillPrintInit.jsp
//�����ܣ�
//�������ڣ�2003-05-14 15:39:06
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.BqNameFun"%>

<%
	//���ҳ��ؼ��ĳ�ʼ����
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
	//String strOperator = globalInput.Operator;
%>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {     
   document.all('StartDay').value = '<%=dateArr[0]%>';
	 document.all('EndDay').value = '<%=dateArr[1]%>';
	 document.all('ManageCom').value = "<%=strManageCom%>";
    document.all('SaleChnl').value = '';
  }
  catch(ex)
  {
    alert("��EdorCLaimBackBillPrintInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
                                      

function initForm()
{
  try
  {
    initInpBox();        
  }
  catch(re)
  {
    alert("EdorCLaimBackBillPrintInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>

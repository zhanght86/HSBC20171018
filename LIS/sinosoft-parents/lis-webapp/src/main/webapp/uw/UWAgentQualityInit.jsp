<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWCustomerQualityInit.jsp
//�����ܣ��ͻ�Ʒ�ʹ���
//�������ڣ�2005-06-18 11:10:36
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
           
<script language="JavaScript">
	
function initAll() {
    //document.all('customername').value = '';
    document.all('ManageCom').value = '';
    document.all('Name').value = '';
    document.all('Sex').value = '';
    document.all('EmployDate').value = '';
    document.all('IDType').value = '';
    document.all('IDNumber').value = '';
    document.all('QualityFlag').value = '';
    document.all('UnusualType').value = '';
    document.all('Remark').value = '';
}

function initForm() {
  try {
	initAll();
	initData();
  }
  catch(re) {
    alert("UWCustomerQualityInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

</script>



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
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
           
<script language="JavaScript">
	
function initAll() {
    fm.all('customer').value = '';
    fm.all('customername').value = '';
    fm.all('CustomerNo').value = '';
    fm.all('Name').value = '';
    fm.all('Sex').value = '';
    fm.all('BirthDay').value = '';
    fm.all('IDType').value = '';
    fm.all('IDNumber').value = '';
    fm.all('BlacklistFlagNo').value = '';
    fm.all('BlacklistFlagName').value = '';
    fm.all('Remark').value = '';
}

function initForm() {
  try {

	initAll();
  }
  catch(re) {
    alert("UWCustomerQualityInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

</script>



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
   // document.all('customer').value = '';
   // document.all('customername').value = '';
    document.all('HospitalCode').value = '';
    document.all('HospitalCode1').value = '';
    document.all('HospitalName').value = '';
    document.all('ManageCom').value = '';
    document.all('ManageComName').value = '';
    document.all('Remark').value = '';
}

function initForm(tEdorNo,tContNo) {
  try {

	initAll();
	initHospitalCode(EdorNo,tContNo);
	initHospitCode();
	//showBodyCheck('','Score');
    //showBodyCheck('','Manage');
    //showBodyCheck('','Inner');
  
   if(QueryFlag == null || QueryFlag == 'null'|| QueryFlag == '')
   {}
   else
   {
   		fm.sure.disabled = true;
   }
  }
  catch(re) {
    alert("UWCustomerQualityInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

</script>



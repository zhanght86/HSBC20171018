<%
//�������ƣ�GetSendToBankInit.jsp
//�����ܣ�
//�������ڣ�2002-11-18 11:10:36
//������  ���� ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>


<%@page import="com.sinosoft.lis.pubfun.PubFun"%><SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox() { 
	fm.EndDate.value=formartDate(getCurrentDate());
	fm.StartDate.value=calSpecDate(getCurrentDate(),'D',-60);
}                                     

function initForm() {
  try {
  	initInpBox();
  	
  }
  catch(re) {
    alert("InitForm�����з����쳣:��ʼ���������!");
  }
}

</script>

	

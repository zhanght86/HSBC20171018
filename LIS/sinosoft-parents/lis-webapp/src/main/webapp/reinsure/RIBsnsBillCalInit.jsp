<%@include file="/i18n/language.jsp"%>

<%
	//Creator :�ű�
	//Date :2006-10-24
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<script type="text/javascript">
	function initInpBox() {
		try {
			fm.RIComCode.value = '';
			fm.RIComName.value = '';
			fm.BillType.value = '';
			fm.BillTypeName.value = '';
			fm.StartDate.value = '';
			fm.EndDate.value = '';
		} catch (ex) {
			myAlert("���г�ʼ��ʱ���ִ��󣡣�����");
		}
	}

	// �����б�ĳ�ʼ��
	function initSelBox() {
		try {
		} catch (ex) {
			myAlert("2��CertifyDescInit.jspInitSelBox�����з����쳣:��ʼ���������!");
		}
	}

	function initForm() {
		try {
			initInpBox();
			initSelBox();
		} catch (re) {
			myAlert("3CertifyDescInit.jspInitForm�����з����쳣:��ʼ���������!");
		}
	}
</script>

<%@include file="../i18n/language.jsp"%>
<%
  //�������ƣ�PDAlgoTempLibInit.jsp
  //�����ܣ��㷨ģ���
  //�������ڣ�2009-3-17
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm()
{
	fm.all("AlgoTempCode").value = "";
	fm.all("AlgoTempType").value = "";
	fm.all("AlgoTempName").value = "";
	fm.all("AlgoTempTypeName").value = "";
	fm.all("AlgoTempContent").value = "";
	fm.all("AlgoTempDescription").value = "";
	
	fm.all("AlgoTempCode").readOnly = false;
	fm.all("btnSave").disabled = false;	
}

</script>

<%@include file="../i18n/language.jsp"%>
<%
  //�������ƣ�PDCalFactorInit.jsp
  //�����ܣ��㷨Ҫ�ؿ�
  //�������ڣ�2009-3-17
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm()
{
	fm.all("FactorType").value = "";
	fm.all("Module").value = "";
	fm.all("FactorCode").value = "";
	fm.all("FactorValType").value = "";
	fm.all("FactorDesc").value = "";
	fm.all("Kind").value = "";
	
	fm.ModuleName.value = "";
	fm.PropertyName.value = "";
	fm.FactorTypeName.value = "";
	
	fm.all("FactorCode").readOnly = false;
	fm.all("btnSave").disabled = false;
}

</script>

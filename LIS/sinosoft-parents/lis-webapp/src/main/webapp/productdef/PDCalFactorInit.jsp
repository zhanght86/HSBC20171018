<%@include file="../i18n/language.jsp"%>
<%
  //程序名称：PDCalFactorInit.jsp
  //程序功能：算法要素库
  //创建日期：2009-3-17
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
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

<%@include file="../i18n/language.jsp"%>
<%
  //程序名称：PDAlgoTempLibInit.jsp
  //程序功能：算法模板库
  //创建日期：2009-3-17
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
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

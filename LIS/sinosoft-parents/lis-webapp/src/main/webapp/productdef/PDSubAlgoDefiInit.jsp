<%@include file="../i18n/language.jsp"%>
<%
  //程序名称：PDSubAlgoDefiInit.jsp
  //程序功能：子算法定义界面
  //创建日期：2009-3-17
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function initForm()
{
	fm.all("AlgoCode").value = '<%=request.getParameter("algocode")%>';
	fm.all("SubAlgoCode").value = '<%=request.getParameter("subalgocode")%>';
	fm.all("SubAlgoName").value = '<%=new String(request.getParameter("subalgoname").getBytes("ISO-8859-1"),"GBK")%>';
	fm.all("RiskCode").value = '<%=request.getParameter("riskcode")%>';
	
	if('<%=request.getParameter("subalgograde")%>' != 'null' && '<%=request.getParameter("subalgograde")%>' != 0)
	{
		fm.all("SubAlgoGrade").value = '<%=request.getParameter("subalgograde")%>';		
	}
	
	fm.all("SubAlgoType").value = '2';
	fm.all("SubAlgoTypeName").value = '扩展要素';
}

</script>

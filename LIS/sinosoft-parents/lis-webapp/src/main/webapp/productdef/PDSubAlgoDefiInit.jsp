<%@include file="../i18n/language.jsp"%>
<%
  //�������ƣ�PDSubAlgoDefiInit.jsp
  //�����ܣ����㷨�������
  //�������ڣ�2009-3-17
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
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
	fm.all("SubAlgoTypeName").value = '��չҪ��';
}

</script>

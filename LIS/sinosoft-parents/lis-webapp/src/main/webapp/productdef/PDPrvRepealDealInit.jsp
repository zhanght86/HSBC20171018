<%@include file="../i18n/language.jsp"%>
<%
  //�������ƣ�PDPrvRepealDealInit.jsp
  //�����ܣ���Լ��Ϣ�������
  //�������ڣ�2009-3-13
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%
	String currentDate = PubFun.getCurrentDate();
%>	
<script type="text/javascript">

function initForm()
{
	try
	{
		fm.all("RiskCode").value = "<%=request.getParameter("riskcode")%>";	
		var requdate = "<%=request.getParameter("requdate")%>";	

		if(requdate==null || requdate == "null" )
		{
			fm.all("RequDate").value = "<%=currentDate%>" ;
		}
		else
		{
			fm.all("RequDate").value = 	requdate;
		}
	}
	catch(re){
		myAlert("PDPrvRepealDealInit.jsp-->"+"��ʼ���������!");
	}
}

</script>

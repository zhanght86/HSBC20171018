<%@include file="../i18n/language.jsp"%>
<%
  //程序名称：PDPrvRepealDealInit.jsp
  //程序功能：契约信息定义入口
  //创建日期：2009-3-13
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
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
		myAlert("PDPrvRepealDealInit.jsp-->"+"初始化界面错误!");
	}
}

</script>

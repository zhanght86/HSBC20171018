<%@include file="../i18n/language.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<script type="text/javascript">
function getQueryState()
{
var loadFlagButton=<%=session.getAttribute("LoadFlagButton")%>;
//alert("返回标记是："+loadFlagButton);
if(loadFlagButton==null||loadFlagButton=='undefined')loadFlagButton=1;
return  loadFlagButton;
}
function getQueryState1()
{
var loadFlagButton=<%=request.getParameter("pdflag")%>;
//alert("返回标记是："+loadFlagButton);
return  loadFlagButton;
}
</script>
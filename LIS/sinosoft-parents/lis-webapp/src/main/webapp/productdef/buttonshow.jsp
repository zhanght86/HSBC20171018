<%@include file="../i18n/language.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<script type="text/javascript">
function getQueryState()
{
var loadFlagButton=<%=session.getAttribute("LoadFlagButton")%>;
//alert("���ر���ǣ�"+loadFlagButton);
if(loadFlagButton==null||loadFlagButton=='undefined')loadFlagButton=1;
return  loadFlagButton;
}
function getQueryState1()
{
var loadFlagButton=<%=request.getParameter("pdflag")%>;
//alert("���ر���ǣ�"+loadFlagButton);
return  loadFlagButton;
}
</script>
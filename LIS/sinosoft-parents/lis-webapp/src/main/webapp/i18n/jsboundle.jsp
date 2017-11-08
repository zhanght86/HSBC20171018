<%@page contentType='text/html;charset=UTF-8'%>
<%@page import="java.util.*"%>
<%@page import="java.io.InputStream"%>
<%@page import="com.sinosoft.utility.*"%>
<%
String base=request.getParameter("base");
System.out.println(base);
String msgCode=request.getParameter("msgCode");
System.out.println(msgCode);
Locale locale = (Locale)session.getAttribute("locale");
if(locale==null)
{
	locale=Locale.getDefault();
}
if(base==null)
{
	base = "i18n";
}
StringManager stringManager = StringManager.getManager(base,locale);
response.getWriter().print(stringManager.getString(msgCode));
%>


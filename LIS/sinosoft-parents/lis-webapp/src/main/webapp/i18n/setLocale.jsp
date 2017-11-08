<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="java.util.Locale"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
String lang=request.getParameter("language");

System.out.println("language:"+lang);
Locale locale = Locale.getDefault();
if("zh".equals(lang))
{
	locale = Locale.SIMPLIFIED_CHINESE;
}
if("en".equals(lang))
{
	locale = Locale.ENGLISH;
}
if("ja".equals(lang))
{
	locale = Locale.JAPANESE;
}
if("vi".equals(lang))
{
	locale = new Locale("vi","VN","");
}
if("tr".equals(lang))
{
	locale = new Locale("tr","TR","");
}
//added by lijian 2010-03-24
GlobalInput tG = new GlobalInput();
tG.locale = locale;
session.setAttribute("GI", tG);


session.setAttribute("locale",locale);
String cookieName="lang";
Cookie cookie=new Cookie(cookieName, lang);
response.addCookie(cookie); 
System.out.println("select:"+locale.getLanguage());
%>



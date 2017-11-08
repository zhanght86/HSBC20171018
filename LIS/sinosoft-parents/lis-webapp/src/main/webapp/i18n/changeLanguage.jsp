<%@page import="java.util.*"%>
<%
//Locale.setDefault(Locale.JAPANESE);
//modified by huan guangen 2010-2-1 18:50:01
Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
Locale locale = (Locale)session.getAttribute("locale");
if(locale==null)
{
	Cookie cookies[]=request.getCookies();
	Cookie sCookie=null;
	String svalue=null;
	String sname=null;
	for(int i=0;i<cookies.length;i++)
	{
		sCookie=cookies[i];
		svalue=sCookie.getValue();
		sname=sCookie.getName(); 
		if(sname.equals("lang"))
		{
			if("zh".equals(svalue))
			{
				locale = Locale.SIMPLIFIED_CHINESE;
			}
			if("en".equals(svalue))
			{
				locale = Locale.ENGLISH;
			}
			if("ja".equals(svalue))
			{
				locale = Locale.JAPANESE;
			}
			if("vi".equals(svalue))
			{
				locale = new Locale("vi","VN","");
			}
		}
	}
}
if(locale==null)
{
	locale=Locale.getDefault();
}
session.setAttribute("locale",locale);
System.out.println(locale.getLanguage());
%>
<%@taglib uri="http://jakarta.apache.org/taglibs/i18n-1.0" prefix="i18n" %>
<i18n:bundle baseName="i18n.LocalStrings" scope="session" id="bundle" locale='<%=locale%>' changeResponseLocale="true"/>


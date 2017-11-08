<%@page import="java.util.*"%>
<%@taglib uri="http://jakarta.apache.org/taglibs/i18n-1.0" prefix="i18n" %>
<i18n:bundle baseName="i18n.LocalStrings" scope="session" id="bundle" locale='<%=(Locale)session.getAttribute("locale")%>' changeResponseLocale="true"/>


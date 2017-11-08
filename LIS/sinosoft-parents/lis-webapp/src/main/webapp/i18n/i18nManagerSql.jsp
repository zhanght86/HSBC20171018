<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=UTF-8" %>
<%@include file="../common/easyQueryVer3/EasyQueryFunc.jsp"%>
<%

 if(SQLID.equals("i18nManagerSql_1"))    
 { 
     SQL=" and a.msg_cn like '%?msg_cn?%'";
 }
 if(SQLID.equals("i18nManagerSql_2"))   
 { 
     SQL="  and a.msg_ja like '%?msg_ja?%'";
 }
%>
<%
System.out.println("InputSQL===:"+SQL);
request.setAttribute("EASYQUERYSQL",SQL);
%>

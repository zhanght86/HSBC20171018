<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PDSetSession.jsp
//程序功能：控制页面的提交按钮
//创建日期：2009-4-13
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.utility.*"%>

<%
 
 // 控制所有打开页面的提交按钮是否可用
 session.setAttribute("IsReadOnly", (String)request.getParameter("IsReadOnly"));


 // 控制第一个打开的页面中提交按钮是否可用,和上面冲突时，优先采用IsReadOnlyFirstOpen
 if(request.getParameter("IsReadOnlyFirstOpen") == null)
 {
	 session.setAttribute("IsReadOnlyFirstOpen", "1");
 }
 else
 {
	session.setAttribute("IsReadOnlyFirstOpen", (String)request.getParameter("IsReadOnlyFirstOpen"));
 }
  
 String FlagStr = "";
 String Content = "";
 String operator = (String)request.getParameter("Operator");
 
 
 if (FlagStr=="")
 {
   FlagStr = "Success";
  }
  else                                                                           
  {
   Content = ""+"权限设置失败"+"";
   FlagStr = "Fail";
 }

%>                      
<html>
<script type="text/javascript">
if("<%=operator%>" == "query")
{
 	parent.fraInterface.queryAfterSetSession("<%=FlagStr%>","<%=Content%>");
}
else if("<%=operator%>" == "define")
{
	parent.fraInterface.definAfterSetSession("<%=FlagStr%>","<%=Content%>");
}
</script>
</html>


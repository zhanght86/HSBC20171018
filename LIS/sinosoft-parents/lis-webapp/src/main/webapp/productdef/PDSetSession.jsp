<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDSetSession.jsp
//�����ܣ�����ҳ����ύ��ť
//�������ڣ�2009-4-13
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.utility.*"%>

<%
 
 // �������д�ҳ����ύ��ť�Ƿ����
 session.setAttribute("IsReadOnly", (String)request.getParameter("IsReadOnly"));


 // ���Ƶ�һ���򿪵�ҳ�����ύ��ť�Ƿ����,�������ͻʱ�����Ȳ���IsReadOnlyFirstOpen
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
   Content = ""+"Ȩ������ʧ��"+"";
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


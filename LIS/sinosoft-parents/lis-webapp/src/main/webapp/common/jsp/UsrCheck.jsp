<!--
*******************************************************
* �������ƣ�UsrCheck.jsp
* �����ܣ��û���ϢУ��ҳ��
* �������ڣ�2002-11-25
* ���¼�¼��  ������    ��������     ����ԭ��/����
*******************************************************
-->
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
try
{
	if (session == null) {
		System.out.println("session is null");
%>
<script language=javascript>
try
{
	top.window.location ="../indexlis.jsp";
}
catch (exception)
{
	top.window.location ="../indexlis.jsp";
}
</script>
<%
		return;
	}
	GlobalInput tG1 = (GlobalInput)session.getValue("GI");
	if (tG1 == null) {
		session.putValue("GI",null);
		out.println("��ҳ��ʱ���������µ�¼");
%>
<script language=javascript>
try
{
	top.window.location ="../indexlis.jsp";
}
catch (exception)
{
	top.window.location ="../indexlis.jsp";
}
</script>
<%
		return;
	}
	String  userCode = tG1.Operator;
	String comCode =tG1.ComCode;
	String manageCom = tG1.ManageCom;
	if ((userCode.length()==0) || (userCode.compareTo("")==0)||(comCode.length()==0) || (comCode.compareTo("")==0) ||(manageCom.length()==0) || (manageCom.compareTo("") == 0))
	{
		session.putValue("GI",null);
		String ContentErr = " �������µ�¼��";
		System.out.println(ContentErr);
%>
<script language=javascript>
try
{
	top.window.location ="../indexlis.jsp";
}
catch (exception)
{
	top.window.location ="../indexlis.jsp";
}
</script>
<%
		return;
	}
}
catch(Exception exception)
{
	String ContentErr = " exception:�������µ�¼��";
	System.out.println(ContentErr);
	out.println("��ҳ��ʱ���������µ�¼");
%>
<script language=javascript>
top.window.location ="../indexlis.jsp";
</script>
<%
	return;
}
%>
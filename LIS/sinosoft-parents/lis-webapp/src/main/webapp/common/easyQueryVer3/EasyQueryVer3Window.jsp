<%@page contentType="text/html;charset=GBK" %>
<%@include file="../jsp/Log4jUI.jsp"%>  
<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
%>
<%
//�������ƣ�EasyQueryVer3Window.jsp
//�����ܣ���ѯ�ȴ�ҳ�棬������ú�̨��ѯ�������շ��ؽ��
//          ������window.showModalDialog��ʽ���������
//�������ڣ�2002-10-19
//������  ������
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.utility.*"%>
<%@include file="./EasyQueryKernel.jsp"%>
<title>���ڲ�ѯ����</title>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<%

boolean error = false;
GlobalInput gi = null;
try {
    gi = (GlobalInput)session.getValue("GI");
}
catch(Exception ex) { }
if(gi == null || gi.Operator == null || "".equals(gi.Operator)) {
    error = true;
}
if(error){
    out.println("<script language=javascript>");
    out.println("  session = null;");
    out.println("  try {");
    out.println("    CollectGarbage();");
    out.println("  ) catch(ex) {}");
    out.println("  parent.window.location =\"../../indexlis.jsp\";");
    out.println("</script>");
    session.invalidate();
    return;
}


String strResult = "";

//String strSql = new String (request.getParameter("strSql").getBytes("ISO8859_1"));
String strSql = request.getParameter("strSql");
String strStart = request.getParameter("strStart");
String strLargeFlag = request.getParameter("LargeFlag");
String strLimitFlag = request.getParameter("LimitFlag");

//loggerDebug("EasyQueryVer3Window","---EasyQuery BGN---");
try
{
    strResult = easyQueryKernel(strSql, strStart, strLargeFlag, strLimitFlag);
}
catch(Exception ex)
{
    loggerDebug("EasyQueryVer3Window","easyQueryKernel throw Errors!\n" + "easyQuerySql:" + strSql +"\nStartRecord:" +strStart);
}
//loggerDebug("EasyQueryVer3Window","---EasyQuery END---");

try
{
    //����һ�������ַ��滻���ɷ������ж��Ƿ��������ַ���Ȼ����������
    //�����лس�������ȡ���Ŀ�����̫С��
    if(strResult.indexOf("\"")!= -1 || strResult.indexOf("'")!= -1)
    {
        String strPath = application.getRealPath("config//Conversion.config");
        strResult = StrTool.Conversion(strResult, strPath);
    }
}
catch(Exception ex)
{
    loggerDebug("EasyQueryVer3Window","not found Conversion.config ");
}

//loggerDebug("EasyQueryVer3Window","strResult is : "+strResult);

if (strResult.equals(""))
{
%>
<script language="JavaScript">
if (typeof(window.opener) == "object")
{
    window.opener.afterEasyQueryVer3("Easy Query Faile");
    window.close();
}
else
{
    //window.returnValue = "Easy Query Faile";
    window.returnValue = false;
    window.close();
//  strQueryResult = false;
}
</script>
<%
}
else
{
%>
<script language="JavaScript">
if (typeof(window.opener) == "object")
{
    window.opener.transferQueryResult('<%=strResult%>');
//  window.opener.afterEasyQueryVer3('<%=strResult%>');
    window.opener.afterEasyQueryVer3("ok");
    window.close();
}
else
{
    window.returnValue = '<%=strResult%>';
    window.close();
//  strQueryResult = '<%=strResult%>';
}
</script>
<%
}
%>

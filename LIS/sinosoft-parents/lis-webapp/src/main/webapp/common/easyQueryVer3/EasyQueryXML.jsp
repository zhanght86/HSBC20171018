<%@page contentType="text/html;charset=GBK" %>
<%@include file="../jsp/Log4jUI.jsp"%>  
<%@page import="java.io.*"%>
<%@page import="javax.servlet.*"%>
<%@page import="javax.servlet.http.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@include file="./EasyQueryKernel.jsp"%>
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

//�������ƣ�EasyQueryXML.jsp
//�����ܣ���ѯ�ȴ�ҳ�棬������ú�̨��ѯ�������շ��ؽ��
//�������ڣ�2005-6-3 8:48
//������  ��Ϳǿ
//�޸���  �������  2006-4-13   ����Referer��Host��У��
//tongmeng 2007-09-14 modify
//ÿ���������á�
response.reset();
response.setContentType("text/html;charset=GBK");
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);


String strResult = "";
//loggerDebug("EasyQueryXML","$$$$$$$$$$$$$$$$$begin EasyQueryXML $$$$$$$$$$$$$$");
String tReferer = request.getHeader("Referer");//��������ҳ��url��Ϣ
//loggerDebug("EasyQueryXML",request.getHeader("Referer"));
String tHost = "http://" + request.getHeader("host");//��ȡӦ�õ�url��Ϣ
String tHost1 = "https://" + request.getHeader("host");//��ȡӦ�õ�url��Ϣ
//loggerDebug("EasyQueryXML",tHost);
//loggerDebug("EasyQueryXML",tHost1);
//У�������ҳ���url�Ƿ���Ӧ�ÿ���ڵ�ҳ��
//loggerDebug("EasyQueryXML","tReferer:" + tReferer);
//loggerDebug("EasyQueryXML","tHost:" + tHost);
//loggerDebug("EasyQueryXML","tHost1:" + tHost1);
if(tReferer == null || (!tReferer.startsWith(tHost) && !tReferer.startsWith(tHost1)))
{
    loggerDebug("EasyQueryXML","����һ����ַ���������");
%>
<script language=javascript>
session = null;
try
{
    CollectGarbage();
}
catch(ex)
{
    alert(ex.message);
}
top.window.location ="../../indexlis.jsp";
</script>
<%
//  response.sendRedirect("../../indexlis.jsp");
}
else
{
    InputStream ins = request.getInputStream();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    int nChar = 0;

    nChar = ins.read();
    while( nChar != -1 ) {
        baos.write(nChar);
        nChar = ins.read();
    }

    /* Kevin 2006-08-04
     * The default encoding used by XMLHTTP is UTF-8
     *
     * Pay attention, don't use getParameter().
     * Because that you have to encode the query string in application/x-www-form-urlencoded form in js
     * to make sure you can get the correct content here, and I can't find a method to encode Chinese
     * Character well in js, so I use getInputStream() instead.
     *
     * BTW: You can find VBScript that can perform this task in the internet.
     *
     */

    String[] strParams = new String(baos.toByteArray(), "UTF-8").split("&");

    //����request�Ľ����ַ���
    String strSql = strParams[0];
    String strStart =strParams[1];
    String strLargeFlag = strParams[2];
    String strLimitFlag = strParams[3];
    request.setAttribute("USEFULINFO",strSql); //used by TimeFilter to collect big sql. added by zzm
    if(strSql.indexOf(".jsp")!=-1)
    {
    EasyQuerySql tEasyQuerySql=new EasyQuerySql();
    if (tEasyQuerySql.parsePara(strSql))
    {
    //loggerDebug("EasyQueryXML","EASYQUERY RAW SQL==="+strSql);
    String jspName=tEasyQuerySql.getJspName();
    //loggerDebug("EasyQueryXML","EASYQUERY JSP'S Name IS===="+jspName);
    request.setAttribute("EASYQUERYSQLID",tEasyQuerySql.getSqlId());
    for (int i = 0; i < tEasyQuerySql.getParaCount(); i++)
    {
        request.setAttribute(tEasyQuerySql.getParaName(i).toUpperCase(),tEasyQuerySql.getParaValue(i));
    }
  %>


  <%
    strSql=(String)request.getAttribute("EASYQUERYSQL");

    //loggerDebug("EasyQueryXML",strSql);
    strSql=tEasyQuerySql.convertToValue(strSql);
    loggerDebug("EasyQueryXML","===EASYQUERY CONVERT SQL==="+strSql);
    }
    }
    try
    {
    	//loggerDebug("EasyQueryXML","===begin==="+strSql);
        strResult = easyQueryKernel(strSql, strStart, strLargeFlag, strLimitFlag);
        //loggerDebug("EasyQueryXML","===end===");
        
    }
    catch(Exception ex)
    {
        loggerDebug("EasyQueryXML","easyQueryKernel throw Errors!\n" + "easyQuerySql:" + strSql +"\nStartRecord:" +strStart);
    }

    try
    {
        //����һ�������ַ��滻���ɷ������ж��Ƿ��������ַ���Ȼ����������
        //�����лس�������ȡ���Ŀ�����̫С��
        if(strResult.indexOf("\"")!= -1 || strResult.indexOf("'")!= -1 || strResult.indexOf("\n")!= -1)
        {
            String strPath = application.getRealPath("config//Conversion.config");
            strResult = StrTool.Conversion(strResult, strPath);
        }
    }
    catch(Exception ex)
    {
        loggerDebug("EasyQueryXML","not found Conversion.config ");
    }
    //loggerDebug("EasyQueryXML","$$$$$$$$$$$$$$$$$end EasyQueryXML $$$$$$$$$$$$$$");
}
%>
<%=strResult%>

<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ���� <HuBo@sinosoft.com.cn>, ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.10
 * @date     : 2002-10-18, 2006-06-30
 * @direction: ���ò�ѯ LDCode �ȴ�ҳ��
 ******************************************************************************/
 
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
 
//tongmeng 2008-04-30 modify
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0);

%>


<%@ page import="com.sinosoft.lis.pubfun.*" %>

<%@ include file="CodeQueryKernel.jsp" %>

<%
//tongmeng 2008-05-07 modify
//�޸�verfyInput��У�����

    //�������ݱ���
    String sCodeType = request.getParameter("codeType");
	
    String sCodeField = request.getParameter("codeField");
    String sCodeConditon = request.getParameter("codeConditon");
    String sCodeValue = request.getParameter("verifyValue");
    String sQueryResult = new String("");

		loggerDebug("CodeQueryWindow","sCodeValue:"+sCodeValue);
    //�ռ���������
    //GlobalInput
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");

    //��ʾ��־��Ϣ
    loggerDebug("CodeQueryWindow","");
    loggerDebug("CodeQueryWindow","========== CodeQuery BGN ==========");
    loggerDebug("CodeQueryWindow","CodeType  : " + sCodeType);

    //���ú�̨��ѯ
    try
    {
        if ((sCodeField == null || sCodeField.equals("") || sCodeConditon == null || sCodeConditon.equals(""))&&(sCodeValue==null||sCodeValue.equals("")))
        {
            sQueryResult = codeQueryKernel(sCodeType, tGlobalInput);
        }
        else
        {
            sQueryResult = codeQueryKernel2(sCodeType, sCodeField, sCodeConditon, tGlobalInput,sCodeValue);
        }
    }
    catch (Exception ex)
    {
        loggerDebug("CodeQueryWindow","@> Errors occured when CodeQuery executing !");
    }

    loggerDebug("CodeQueryWindow","========== CodeQuery END ==========");
    loggerDebug("CodeQueryWindow","");
    tGlobalInput = null;

    //ҳ�淴����Ϣ
//    out.println(sQueryResult);
    out.println("<script language='JavaScript'>");
    out.println("    try");
    out.println("    {");
    if (sQueryResult == null || sQueryResult.equals("")) {
        out.println("        window.returnValue = 'Code Query Failed';");
    } else {
        out.println("        window.returnValue = '" + sQueryResult + "';");
    }
    out.println("\n window.close();}catch(ex){}</script>");
%>

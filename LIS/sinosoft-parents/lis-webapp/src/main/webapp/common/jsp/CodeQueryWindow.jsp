<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 胡博 <HuBo@sinosoft.com.cn>, 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.10
 * @date     : 2002-10-18, 2006-06-30
 * @direction: 公用查询 LDCode 等待页面
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
//修改verfyInput的校验策略

    //接收数据变量
    String sCodeType = request.getParameter("codeType");
	
    String sCodeField = request.getParameter("codeField");
    String sCodeConditon = request.getParameter("codeConditon");
    String sCodeValue = request.getParameter("verifyValue");
    String sQueryResult = new String("");

		loggerDebug("CodeQueryWindow","sCodeValue:"+sCodeValue);
    //收集整理数据
    //GlobalInput
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");

    //显示日志信息
    loggerDebug("CodeQueryWindow","");
    loggerDebug("CodeQueryWindow","========== CodeQuery BGN ==========");
    loggerDebug("CodeQueryWindow","CodeType  : " + sCodeType);

    //调用后台查询
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

    //页面反馈信息
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

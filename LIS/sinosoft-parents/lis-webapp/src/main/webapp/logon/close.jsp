<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : ����ǿ <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2006-12-13
 * @direction: �ر�ϵͳʱʹ�ỰʧЧ, ��ʡ��������Դ
 ******************************************************************************/
%>

<%
    session.invalidate();
    loggerDebug("close","\t@> ���� session, �˳�ϵͳ ****************************************");
%>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>�˳�ϵͳ</title>
</head>
<body topmargin="0" ondragstart="return false" oncontextmenu="return false">
    <br><center><font size="2">�����˳������Ժ�...</font></center>
</body>
</html>
<script language="JavaScript">
session = null;
try{self.close();}catch(ex){}
try{top.window.close();}catch(ex){}
</script>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
/**
* <p>Title: EasyScanӰ��ϵͳ</p>
* <p>Description: EasyScan��ѯ��֤��ϸ���ݴ���</p>
* <p>Copyright: Copyright (c) 2005</p>
* <p>Company: Sinosoft</p>
* @author wellhi
* @version 1.0
* @Date 2005-11-09
*/
%>
<%@page contentType="text/html;charset=gb2312" %>
<%@page import="com.sinosoft.lis.easyscan.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>EasyScanӰ��ϵͳ</title>
    
    <script src="../common/javascript/Common.js" type="text/javascript"></script>
    <script src="../common/cvar/CCodeOperate.js" type="text/javascript"></script>
    <!-- ҳ����ʽ  -->
    <link rel='stylesheet' type='text/css' href='../common/css/Project.css'>
	<link rel='stylesheet' type='text/css' href='../common/css/Project3.css'>
      <%
      try
      {
        //��ȡsession
        GlobalInput tG1 = (GlobalInput)session.getValue("GI");
        if (tG1 == null) {
          session.putValue("GI",null);
          out.println("��ҳ��ʱ���������µ�¼��");
        }
        //��ȡURL
        String clientURL = (String)session.getValue("ClientURL");
        int iEnd = clientURL.indexOf("logon/main.jsp");
        if(iEnd == -1) iEnd = clientURL.indexOf("logon/mainNew.jsp");
        clientURL = clientURL.substring(0,iEnd);
        //��ȡӰ�񱣴�����
        String imageType = EasyScanConfig.getInstance().imageType;
        //�����Ĵ��������Ԥ����
        String otherConfig = "";
        %>

        <script language="JavaScript" type="text/javascript">
          var objWUpdate;
          var objEasyScan;
          var strRet;
          var strVersion;
          //EasyScan��ǰ�汾
          var CON_CURRENT_VERSION;
          CON_CURRENT_VERSION="V4.0.8.0";
          try {
            //�������߸��³������
            objWUpdate = new ActiveXObject ("Update.WUpdate");
            //�����ɹ������������߸��³������
            strRet=objWUpdate.sfUpdate("<%= clientURL%>", "EasyScan��֤ɨ��ϵͳ");

            /*
            //����������Ϣ
            if(strRet=="") {
              alert("���³ɹ���");
            }
            else{
              alert("����ʧ�ܣ�" + strRet);
            }
            */
            //            objWUpdate=null;
            if(objWUpdate!=null){
              objWUpdate=null;
            }
          }
          catch(e) {
            alert( "�Զ����³�������ʧ�ܣ���\n" + e);
            if(objWUpdate!=null){
              objWUpdate=null;
            }
          }

          try {
            //����EasyScan����
            objEasyScan = new ActiveXObject ("EasyScan.SinoEasyScan");
            strVersion=objEasyScan.dfGetVersion();
            //����ͻ�����������˵İ汾һ��
            if(strVersion == CON_CURRENT_VERSION)
            {
              //�����ɹ������ʼ��EasyScan
              objEasyScan.dfControlInit("<%=tG1.Operator%>", "<%=tG1.ManageCom%>", "<%=clientURL%>", "<%=imageType%>", "<%=otherConfig%>");
              //����EasyScan
              objEasyScan.dfShowMain();
              //              alert(objEasyScan.dfGetVersion());
            }
            else
            {
              //����ͻ��˰汾�ȷ������˵İ汾��
              if(strVersion < CON_CURRENT_VERSION)
              {
                alert( "��ʹ�õĲ������°汾��EasyScan���� [" + strVersion + "]�������ذ�װ���°汾��EasyScan���� [" + CON_CURRENT_VERSION + "] ��");
              }
              //����ͻ��˰汾�ȷ������˵İ汾��
              else if(strVersion > CON_CURRENT_VERSION)
              {
                alert( "��ʹ�õ�EasyScan����汾�� [" + strVersion + "] ���������˵İ汾�� [" + CON_CURRENT_VERSION + "] �������汾��ƥ�䣬����ϵͳ����Ա��ϵ��");
              }
            }
            if(objEasyScan!=null){
              objEasyScan=null;
            }
          }
          catch(e) {
            alert( "EasyScanɨ��Ӱ��ϵͳ����ʧ�ܣ���������δ��װEasyScan���°汾 [" + CON_CURRENT_VERSION + "] �ĳ��򣬻���û����ȷ��װ��\n" + e);
            if(objEasyScan!=null){
              objEasyScan=null;
            }
          }
          </script>
          </head>
          <body>
            <%
            }
            catch(Exception exception)
            {
              out.println("��ҳ��ʱ���������µ�¼");
              return;
            }
            %>
            </body>
          </html>

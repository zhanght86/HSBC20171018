
<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
/*******************************************************************************
 * <p>Title: Lis 6.5</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : sinosoft   
 * @version  : 1.00
 * @date     : 2006-10-30
 * @direction: ����ҵ�����ϵͳ������
 ******************************************************************************/
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  
    <title>���ٱ��պ���ҵ��ϵͳ</title>
    <link href="static/css/imdock.css" rel="stylesheet" />
    <script language="JavaScript">
    <!--
        //�ж�ҳ���Ƿ��ʼ�����
        var achieveEX = false;
        var achieveVD = false;
      	var intPageWidth=screen.availWidth;
	      var intPageHeight=screen.availHeight;
	      //alert("intPageWidth:"+intPageWidth+"!!"+"intPageHeight:"+intPageHeight);
	      window.resizeTo(intPageWidth,intPageHeight);
	      window.focus();
	
        //��������������, ���ڴ���ѡ���ܲ���������, ��������
        window.name = "Lis";
        try
        {
            //window.resizeTo(screen.availWidth, screen.availHeight);
        }
        catch (ex) {}
        window.focus();
    -->
    </script>
<style>body {

font: tahoma,verdana,arial,helvetica,sans-serif;

font-size: 12px; margin-top:0px;

SCROLLBAR-FACE-COLOR: #97CBFF; SCROLLBAR-HIGHLIGHT-COLOR:#fff; 

SCROLLBAR-SHADOW-COLOR:#97CBFF; SCROLLBAR-DARKSHADOW-COLOR:#819FD3; 

SCROLLBAR-3DLIGHT-COLOR:#819FD3; SCROLLBAR-ARROW-COLOR:#3F65AD;

SCROLLBAR-TRACK-COLOR: #E4E4E4;

}</style>
</head>
<frameset id="fraMain" framespacing="0" frameborder="no" rows="0,0,0,0,0,*" cols="*" border="1">
    <!-- ����ͻ��˱��������� -->
    <frame id="VD" name="VD" src="common/cvar/CVarData.jsp"  >
    <!-- ʵ�ַ��������������� -->
    <frame id="EX" name="EX" src="common/cvar/CExec.jsp"  >
    <!-- �ύ�ͻ��˲�ѯ������ -->
    <frame id="fraSubmit" name="fraSubmit" src="about:blank" scrolling="no"  >
    <!-- ������ʾ������������ -->
    <frame id="fraTitle" name="fraTitle" src="logon/Title.jsp" scrolling="no"  >
 	<!-- ����head���� -->
	<frame id="head" name="head" src="" scrolling="no"  >
    <!-- ��ʾ�˵��ͽ��������� -->
    <frameset id="fraSetColor" framespacing="0" frameborder="no" rows="*" cols="0,*" border="0" > 
	    <frame id="fraLeftColor" name="fraLeftColor" src="" scrolling="no">
	    <frameset id="fraSet" framespacing="0" frameborder="no" rows="*" cols="0%,*,0%,0%" border="1" >
		    <!-- ��ʾ�˵������� -->
		    <frameset id="fraMenuMain" framespacing="0" frameborder="no" rows="5,*" border="1">
		        <frame id="fraMenuTop" name="fraMenuTop" src="logon/menutop.jsp" scrolling="no" >
		        <frame id="fraMenu" name="fraMenu" src="about:blank" scrolling="no"  >
		    </frameset>
		    <!-- ʵ�ֽ��������� -->
		    <frameset id="fraTalk" framespacing="0" frameborder="no" rows="0,*" border="1">
		        <frame id="fraQuick" name="fraQuick" src="logon/quick.jsp" scrolling="no"  >
		        <frameset id="fraTalkCol" framespacing="0" frameborder="no" cols="0,*" border="1">
		            <frame id="fraPic" name="fraPic" src="about:blank" scrolling="auto"  >
		            <frame id="fraInterface" name="fraInterface" src="logon/mainNew.jsp" scrolling="auto">
		        </frameset>
		    </frameset>
		    
		    <!--���촰�ڵ�����-->
		    <frameset id="fraChatMain" framespacing="0" frameborder="no" rows="*" border="1">
		        <frame id="fraChat" name="fraChat" src="static/chat.html" scrolling="no" >
		    </frameset>
		    
	        <!-- ��һ���������� -->
	        <frame id="fraNext" name="fraNext" src="about:blank" scrolling="auto"  >
	    </frameset>
	    <!--<frame id="fraRightColor" name="fraRightColor" src="" scrolling="no">-->
    </frameset>
   
</frameset>
<noframes>
    <body bgcolor="#FFFFFF">
        <br><br><br><br><br>
        <center>
            <font size="2">�Բ��������������֧�ֿ����ҳ��</font>
            <br><br>
            <font size="2">��ʹ�� IE 5.0 �������������ϵͳ��</font>
        </center>
    </body>
</noframes>


</html>
